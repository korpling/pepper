/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package org.corpus_tools.pepper.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.PepperJob;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.modules.exceptions.PepperModulePropertyException;
import org.eclipse.emf.common.util.URI;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * This class reads the 'old' pepperParams file and fills a {@link PepperJob}
 * with the content of the file. The pepperParams file is a relict of the times
 * where Pepper was based on EMF. Therefore the pepperParams file is an xmi
 * file.
 * 
 * @author Florian Zipser
 *
 */
public class PepperParamsReader extends DefaultHandler2 {
	public static final String NS_XMI = "http://www.omg.org/XMI";
	public static final String PREFIX_XMI = "xmi";
	public static final String NS_PEPPERPARAMS = "de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams";
	public static final String PREFIX_PEPPERPARAMS = "pepperParams";
	public static final String ELEMENT_PEPPERPARAMS = "PepperParams";
	public static final String ELEMENT_PEPPER_JOB_PARAMS = "pepperJobParams";
	public static final String ELEMENT_IMPORTER_PARAMS = "importerParams";
	public static final String ELEMENT_MODULE_PARAMS = "moduleParams";
	public static final String ELEMENT_EXPORTER_PARAMS = "exporterParams";
	public static final String ATT_ID = "id";
	public static final String ATT_MODULE_NAME = "moduleName";
	public static final String ATT_FORMAT_NAME = "formatName";
	public static final String ATT_FORMAT_VERSION = "formatVersion";
	public static final String ATT_SOURCE_PATH = "sourcePath";
	public static final String ATT_DEST_PATH = "destinationPath";
	public static final String ATT_SPECIAL_PARAMS = "specialParams";

	/** {@link PepperJob} object to be filled. **/
	private PepperJob job = null;

	/**
	 * Returns {@link PepperJob} to be filled.
	 * 
	 * @return
	 */
	public PepperJob getJob() {
		return job;
	}

	/**
	 * Sets {@link PepperJob} to be filled.
	 * 
	 * @param job
	 */
	public void setJob(PepperJob job) {
		this.job = job;
	}

	/** location of the current parsed xml file **/
	private URI location = null;

	public URI getLocation() {
		return location;
	}

	public void setLocation(URI location) {
		this.location = location;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		StepDesc stepDesc = null;
		if (ELEMENT_IMPORTER_PARAMS.equals(qName)) {
			stepDesc = new StepDesc();
			stepDesc.setModuleType(MODULE_TYPE.IMPORTER);
			if (attributes.getValue(ATT_MODULE_NAME) != null)
				stepDesc.setName(attributes.getValue(ATT_MODULE_NAME));
			if (attributes.getValue(ATT_FORMAT_NAME) != null) {
				stepDesc.getCorpusDesc().getFormatDesc().setFormatName(attributes.getValue(ATT_FORMAT_NAME));
				if (attributes.getValue(ATT_FORMAT_VERSION) != null)
					stepDesc.getCorpusDesc().getFormatDesc().setFormatVersion(attributes.getValue(ATT_FORMAT_VERSION));
			}
			if (attributes.getValue(ATT_SOURCE_PATH) != null) {
				File pathFile = resolveFile(attributes.getValue(ATT_SOURCE_PATH));
				if (pathFile != null) {
					URI path = URI.createFileURI(pathFile.getAbsolutePath());
					stepDesc.getCorpusDesc().setCorpusPath(path);
				}
			}
		} else if (ELEMENT_MODULE_PARAMS.equals(qName)) {
			stepDesc = new StepDesc();
			stepDesc.setModuleType(MODULE_TYPE.MANIPULATOR);
			if (attributes.getValue(ATT_MODULE_NAME) != null)
				stepDesc.setName(attributes.getValue(ATT_MODULE_NAME));
		} else if (ELEMENT_EXPORTER_PARAMS.equals(qName)) {
			stepDesc = new StepDesc();
			stepDesc.setModuleType(MODULE_TYPE.EXPORTER);
			if (attributes.getValue(ATT_MODULE_NAME) != null)
				stepDesc.setName(attributes.getValue(ATT_MODULE_NAME));
			if (attributes.getValue(ATT_FORMAT_NAME) != null) {
				stepDesc.getCorpusDesc().getFormatDesc().setFormatName(attributes.getValue(ATT_FORMAT_NAME));
				if (attributes.getValue(ATT_FORMAT_VERSION) != null)
					stepDesc.getCorpusDesc().getFormatDesc().setFormatVersion(attributes.getValue(ATT_FORMAT_VERSION));
			}
			if (attributes.getValue(ATT_DEST_PATH) != null) {
				File pathFile = resolveFile(attributes.getValue(ATT_DEST_PATH));
				if (pathFile != null) {
					String pathStr = pathFile.getAbsolutePath();
					URI path = URI.createFileURI(pathStr);
					stepDesc.getCorpusDesc().setCorpusPath(path);
				}
			}
		}
		if (stepDesc != null) {
			if (attributes.getValue(ATT_SPECIAL_PARAMS) != null) {
				File propFile = resolveFile(attributes.getValue(ATT_SPECIAL_PARAMS));
				Properties props = new Properties();
				try {
					props.load(new FileInputStream(propFile));
				} catch (FileNotFoundException e) {
					throw new PepperModulePropertyException("Cannot load property file.", e);
				} catch (IOException e) {
					throw new PepperModulePropertyException("Cannot load property file.", e);
				}
				stepDesc.setProps(props);
			}
			getJob().addStepDesc(stepDesc);
		}
	}

	public File resolveFile(String fileStr) {
		File retFile = null;
		if ((fileStr != null) && (!fileStr.isEmpty())) {
			// clean str in case of it was uri
			fileStr = fileStr.replace("file:", "");
			fileStr = fileStr.replace("///", "/");
			fileStr = fileStr.replace("//", "/");
			// clean str in case of it was uri

			File file = new File(fileStr);

			if (file != null) {
				if (!file.isAbsolute()) {
					if (getLocation() != null) {
						File location = new File(getLocation().toFileString());
						if (!location.isDirectory()) {
							location = location.getParentFile();
						}
						file = new File(fileStr.replace("./", ""));
						retFile = new File(location.getAbsolutePath() + "/" + file);
					} else {
						throw new PepperFWException("An error reading pepper-params file occured, there was an relative uri '" + fileStr + "', but the base path to resolve it (via setLocation()) was not set. ");
					}
				} else {
					retFile = new File(fileStr);
				}
			}
		}
		return (retFile);
	}
}

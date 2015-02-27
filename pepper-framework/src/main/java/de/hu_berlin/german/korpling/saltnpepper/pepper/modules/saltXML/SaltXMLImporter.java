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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.saltXML;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * This is a {@link PepperImporter} which imports the SaltXML format into a salt
 * model. This module assumes, that each document is stored in a separate file.
 * Such a file must contain the document structure. The corpus structure is
 * stored in a single file called saltProject +
 * {@value SaltFactory#FILE_ENDING_SALT}. The value
 * {@value SaltFactory#FILE_ENDING_SALT} can be gettet by method
 * getSaltFileEnding(). <br/>
 * 
 * @author Florian Zipser
 * @version 1.0
 * 
 */
@Component(name = "SaltXMLImporterComponent", factory = "PepperImporterComponentFactory")
public class SaltXMLImporter extends PepperImporterImpl implements PepperImporter {
	public SaltXMLImporter() {
		// setting name of module
		super("SaltXMLImporter");
		setSupplierContact(URI.createURI("saltnpepper@lists.hu-berlin.de"));
		setDesc("This importer imports a Salt model from a SaltXML representation. SaltXML is the native format to persist Salt. ");
		// set list of formats supported by this module
		this.addSupportedFormat("SaltXML", "1.0", null);
		setProperties(new PepperModuleProperties());
	}

	/**
	 * Reads recursively first found file and returns 1.0 if file contains:
	 * <ul>
	 *  <li>&lt;?xml</li>
	 *  <li>xmi:version=\"2.0\"</li>
	 *  <li>salt</li>
	 * </ul>
	 */
	@Override
	public Double isImportable(URI corpusPath) {
		Double retVal= null;
		File file= new File(corpusPath.toFileString());
		while (file.isDirectory()){
			file= file.listFiles()[0];
		}
		String content= readFirstLines(URI.createFileURI(file.getAbsolutePath()), 20);
		if (	(content.contains("<?xml"))&&
				(content.contains("xmi:version=\"2.0\""))&&
				(content.contains("salt"))){
			retVal= 1.0;
		}
		return(retVal);
	}

	/**
	 * Imports the corpus-structure by a call of
	 * {@link SaltProject#loadSCorpusStructure(URI)}
	 */
	@Override
	public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException {
		setSCorpusGraph(corpusGraph);
		// compute position of SCorpusGraph in Saltproject
		corpusGraph.load(this.getCorpusDesc().getCorpusPath());
	}

	/**
	 * Creates a mapper of type {@link EXMARaLDA2SaltMapper}. {@inheritDoc
	 * PepperModule#createPepperMapper(SElementId)}
	 */
	@Override
	public PepperMapper createPepperMapper(SElementId sElementId) {
		SaltXMLMapper mapper = new SaltXMLMapper();
		if (sElementId.getSIdentifiableElement() instanceof SDocument) {
			SDocument sDocument = (SDocument) sElementId.getSIdentifiableElement();
			URI location = getCorpusDesc().getCorpusPath();
			location = location.appendSegments(sDocument.getSElementPath().segments());
			location = location.appendFileExtension(SaltFactory.FILE_ENDING_SALT);
			mapper.setResourceURI(location);
		}
		return (mapper);
	}

	private class SaltXMLMapper extends PepperMapperImpl {
		/**
		 * {@inheritDoc PepperMapper#setSDocument(SDocument)}
		 * 
		 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
		 */
		@Override
		public DOCUMENT_STATUS mapSDocument() {
			getSDocument().loadSDocumentGraph(getResourceURI());
			return (DOCUMENT_STATUS.COMPLETED);
		}
	}
}

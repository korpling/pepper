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
package org.corpus_tools.pepper.modules.coreModules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.PepperConfiguration;
import org.corpus_tools.pepper.core.SelfTestDesc;
import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.common.STextualDS;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

/**
 * This is a PepperExporter which extracts and exports the primary text of a
 * Salt model and stores it into a text file.
 * 
 * @author Florian Zipser
 * @version 1.0
 * 
 */
@Component(name = "TextExporterComponent", factory = "PepperExporterComponentFactory")
public class TextExporter extends PepperExporterImpl implements PepperExporter {
	public static final String MODULE_NAME = "TextExporter";
	public static final String FORMAT_NAME = ENDING_TXT;
	public static final String FORMAT_VERSION = "1.0";

	public TextExporter() {
		// setting name of module
		super(MODULE_NAME);
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		setDesc("This is a PepperExporter which extracts and exports the primary text of a Salt model and stores it into a text file. ");
		// set list of formats supported by this module
		this.addSupportedFormat(FORMAT_NAME, FORMAT_VERSION, null);

		setExportMode(EXPORT_MODE.DOCUMENTS_IN_FILES);
		setDocumentEnding(ENDING_TXT);

	}

	@Override
	public SelfTestDesc getSelfTestDesc() {
		return new SelfTestDesc(
				getResources().appendSegment("modules").appendSegment("selfTests").appendSegment("txtExporter")
						.appendSegment("in"),
				getResources().appendSegment("modules").appendSegment("selfTests").appendSegment("txtExporter")
						.appendSegment("expected"));
	}

	/**
	 * Creates a mapper to export primary texts.
	 */
	@Override
	public PepperMapper createPepperMapper(Identifier sElementId) {
		PepperMapper mapper = new PepperMapperImpl() {
			@Override
			public DOCUMENT_STATUS mapSDocument() {
				if (getDocument() != null && getResourceURI() != null) {
					Iterator<STextualDS> iterator = getDocument().getDocumentGraph().getTextualDSs().iterator();
					while (iterator.hasNext()) {
						STextualDS text = iterator.next();
						File outFile = null;
						String uriStr = getResourceURI().toFileString();

						if (uriStr == null) {
							uriStr = getResourceURI().toString();
						}
						if (getDocument().getDocumentGraph().getTextualDSs().size() > 1) {
							String extension = "." + getResourceURI().fileExtension();
							int pos = uriStr.lastIndexOf(extension);
							uriStr = uriStr.substring(0, pos) + "_" + text.getPath().fragment() + extension;
						}
						outFile = new File(uriStr);
						PrintWriter out = null;
						try {
							out = new PrintWriter(outFile);
						} catch (FileNotFoundException e) {
							throw new PepperModuleException(this, "Cannot write primary text '" + text.getIdentifier()
									+ "' to file '" + outFile.getAbsolutePath() + "'. ", e);
						}
						if (out != null) {
							out.print(text.getText());
							out.flush();
							out.close();
						}
					}
				}
				return (DOCUMENT_STATUS.COMPLETED);
			}
		};
		mapper.setResourceURI(getIdentifier2ResourceTable().get(sElementId));
		return (mapper);
	}
}

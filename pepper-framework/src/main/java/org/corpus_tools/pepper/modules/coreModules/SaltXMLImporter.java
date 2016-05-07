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

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.PepperConfiguration;
import org.corpus_tools.pepper.impl.PepperImporterImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.graph.Identifier;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

/**
 * This is a {@link PepperImporter} which imports the SaltXML format into a salt
 * model. This module assumes, that each document is stored in a separate file.
 * Such a file must contain the document structure. The corpus structure is
 * stored in a single file called saltProject +
 * {@value SaltFactory#FILE_ENDING_SALT}. The value
 * {@value SaltFactory#FILE_ENDING_SALT} can be got by method
 * getSaltFileEnding(). <br/>
 * 
 * @author Florian Zipser
 * @version 1.0
 * 
 */
@Component(name = "SaltXMLImporterComponent", factory = "PepperImporterComponentFactory")
public class SaltXMLImporter extends PepperImporterImpl implements PepperImporter {
	public static final String MODULE_NAME = "SaltXMLImporter";
	public static final String FORMAT_NAME_SALTXML = "SaltXML";
	public static final String FORMAT_VERSION_SALTXML = "1.0";

	public SaltXMLImporter() {
		// setting name of module
		super(MODULE_NAME);
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		setDesc("This importer imports a Salt model from a SaltXML representation. SaltXML is the native format to persist Salt. ");
		// set list of formats supported by this module
		this.addSupportedFormat(FORMAT_NAME_SALTXML, FORMAT_VERSION_SALTXML, null);
		setProperties(new PepperModuleProperties());
	}

	/**
	 * Reads recursively first found file and returns 1.0 if file contains:
	 * <ul>
	 * <li>&lt;?xml</li>
	 * <li>xmi:version=\"2.0\"</li>
	 * <li>salt</li>
	 * </ul>
	 */
	@Override
	public Double isImportable(URI corpusPath) {
		Double retValue = 0.0;

		if (corpusPath == null) {
			return retValue;
		}
		int numberOfSampledFiles = 10;
		int numberOfLines = 10;

		for (String content : sampleFileContent(corpusPath, numberOfSampledFiles, numberOfLines, SaltUtil.FILE_ENDING_SALT_XML, ENDING_XML)) {
			if ((content.contains("<?xml")) && (content.contains("xmi:version=\"2.0\"")) && (content.contains("salt"))) {
				retValue = 1.0;
				break;
			}
		}
		return retValue;
	}

	/**
	 * Imports the corpus-structure by a call of
	 * {@link SaltProject#loadSCorpusStructure(URI)}
	 */
	@Override
	public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException {
		setCorpusGraph(corpusGraph);
		// compute position of SCorpusGraph in Saltproject
		corpusGraph.load(this.getCorpusDesc().getCorpusPath());
	}

	/**
	 * Creates a mapper of type {@link EXMARaLDA2SaltMapper}. {@inheritDoc
	 * PepperModule#createPepperMapper(Identifier)}
	 */
	@Override
	public PepperMapper createPepperMapper(Identifier id) {
		SaltXMLMapper mapper = new SaltXMLMapper();
		if (id.getIdentifiableElement() instanceof SDocument) {
			SDocument sDocument = (SDocument) id.getIdentifiableElement();
			URI location = getCorpusDesc().getCorpusPath();
			location = location.appendSegments(sDocument.getPath().segments());
			location = location.appendFileExtension(SaltUtil.FILE_ENDING_SALT_XML);
			mapper.setResourceURI(location);
		}
		return (mapper);
	}

	private static class SaltXMLMapper extends PepperMapperImpl {
		/**
		 * {@inheritDoc PepperMapper#setDocument(SDocument)}
		 * 
		 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
		 */
		@Override
		public DOCUMENT_STATUS mapSDocument() {
			getDocument().loadDocumentGraph(getResourceURI());
			return (DOCUMENT_STATUS.COMPLETED);
		}
	}
}

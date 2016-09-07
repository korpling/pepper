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

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.PepperConfiguration;
import org.corpus_tools.pepper.core.SelfTestDesc;
import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.graph.Identifier;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;

/**
 * This is a PepperExporter which exports a salt model to the SaltXML format.
 * This module stores every document in a separate file. These files then
 * contains the document structure. The corpus structure is stored in a single
 * file called saltProject + SALT_FILE_ENDING. The value SALT_FILE_ENDING can be
 * got by method getSaltFileEnding(). <br/>
 * <br/>
 * When method start() is called, the saltProject will be attached to a resource
 * with the uri "this.getCorpusDefinition().getCorpusPath().toFileString() +"/
 * "+ " saltProject"+ SALT_ENDING". Before it can be stored, all documents have
 * to be processed. <br/>
 * The module now waits for documents which can be exported. When a document
 * finished all previous modules, it can be exported. This means, that 1) every
 * document will also get a resource with the uri
 * "this.getCorpusDefinition().getCorpusPath().toFileString() + " / "
 * sElementId.getSElementPath()+ SALT_FILE_ENDING". And 2) the document
 * structure will be stored to file. <br/>
 * After all was done, the saltProject will be exported.
 * 
 * @author Florian Zipser
 * @version 1.0
 * 
 */
@Component(name = "SaltXMLExporterComponent", factory = "PepperExporterComponentFactory")
public class SaltXMLExporter extends PepperExporterImpl implements PepperExporter {
	public static final String MODULE_NAME = "SaltXMLExporter";
	public static final String FORMAT_NAME = "SaltXML";
	public static final String FORMAT_VERSION = "1.0";

	public SaltXMLExporter() {
		// setting name of module
		super(MODULE_NAME);
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		setDesc("This exporter exports a Salt model to a SaltXML representation. SaltXML is the native format to persist Salt. ");
		// set list of formats supported by this module
		this.addSupportedFormat(FORMAT_NAME, FORMAT_VERSION, null);
	}

	@Override
	public SelfTestDesc getSelfTestDesc() {
		return new SelfTestDesc(
				getResources().appendSegment("modules").appendSegment("selfTests").appendSegment("saltXmlExporter")
						.appendSegment("in"),
				getResources().appendSegment("modules").appendSegment("selfTests").appendSegment("saltXmlExporter")
						.appendSegment("expected")) {
			@Override
			public boolean compare(final URI actualCorpusPath, final URI expectedCorpusPath) {
				final SaltProject actual = SaltUtil.loadCompleteSaltProject(actualCorpusPath);
				final SaltProject expected = SaltUtil.loadCompleteSaltProject(expectedCorpusPath);
				return (SaltUtil.compare(actual).with(expected).andCheckIsomorphie());
			}

		};
	}

	private class SaltXMLExporterMapper extends PepperMapperImpl {

		/**
		 * {@inheritDoc PepperMapper#setDocument(SDocument)}
		 * 
		 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
		 */
		@Override
		public DOCUMENT_STATUS mapSDocument() {
			// creating uri for exporting document
			URI sDocumentURI = getCorpusDesc().getCorpusPath().appendSegments(getDocument().getPath().segments());
			sDocumentURI = sDocumentURI.appendFileExtension(SaltUtil.FILE_ENDING_SALT_XML);
			SaltUtil.saveDocumentGraph(getDocument().getDocumentGraph(), sDocumentURI);
			return (DOCUMENT_STATUS.COMPLETED);
		}
	}

	/**
	 * Creates a mapper of type {@link SaltXMLExporterMapper}.
	 * {@inheritDoc PepperModule#createPepperMapper(Identifier)}
	 */
	@Override
	public PepperMapper createPepperMapper(Identifier sElementId) {
		SaltXMLExporterMapper mapper = new SaltXMLExporterMapper();
		return (mapper);
	}

	/**
	 * Creates a {@link Resource} for the {@link SaltProject} to persist
	 * contained objects like {@link SDocumentGraph} etc. This is caused by an
	 * EMF constraint.
	 */
	@Override
	public void start() throws PepperModuleException {
		if (this.getSaltProject() == null) {
			throw new PepperModuleException("Cannot export the SaltProject, because the saltProject is null.");
		}
		if (this.getCorpusDesc() == null) {
			throw new PepperModuleException(
					"Cannot export the SaltProject, because no corpus definition is given for export.");
		}
		if (this.getCorpusDesc().getCorpusPath() == null) {
			throw new PepperModuleException(
					"Cannot export the SaltProject, because no corpus path is given for export.");
		}

		// create export URI
		URI saltProjectURI = URI
				.createFileURI(this.getCorpusDesc().getCorpusPath().toFileString() + "/" + SaltUtil.FILE_SALT_PROJECT);
		SaltUtil.saveSaltProject(saltProject, saltProjectURI);
		super.start();
	}
}

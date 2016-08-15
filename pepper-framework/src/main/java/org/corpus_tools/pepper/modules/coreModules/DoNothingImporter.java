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
import org.corpus_tools.pepper.impl.PepperImporterImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

@Component(name = "DoNothingImporterComponent", factory = "PepperImporterComponentFactory")
public class DoNothingImporter extends PepperImporterImpl implements PepperImporter {
	public static final String MODULE_NAME = "DoNothingImporter";
	public static final String FORMAT_NAME = "doNothing";
	public static final String FORMAT_VERSION = "0.0";

	@Override
	public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException {
		// do nothing
	}

	/**
	 * Specifies the separator, which has to be set between to the texts of two
	 * token.
	 */
	public DoNothingImporter() {
		// setting name of module
		super(MODULE_NAME);
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		setDesc("This is a dummy importer which imports nothing. ");
		// set list of formats supported by this module
		this.addSupportedFormat(FORMAT_NAME, FORMAT_VERSION, null);
	}

	@Override
	public Double isImportable(URI corpusPath) {
		return 0.1;
	}
	
	@Override
	public SelfTestDesc getSelfTestDesc() {
		return new SelfTestDesc(
				getResources().appendSegment("modules").appendSegment("selfTests").appendSegment("doNothingImporter")
						.appendSegment("in"),
				getResources().appendSegment("modules").appendSegment("selfTests").appendSegment("doNothingImporter")
						.appendSegment("expected"));
	}
	
	/**
	 * Creates a mapper of type {@link EXMARaLDA2SaltMapper}.
	 * {@inheritDoc PepperModule#createPepperMapper(Identifier)}
	 */
	@Override
	public PepperMapper createPepperMapper(Identifier sElementId) {
		PepperMapper mapper = new PepperMapperImpl() {
			/**
			 * {@inheritDoc PepperMapper#setDocument(SDocument)}
			 * 
			 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
			 */
			@Override
			public DOCUMENT_STATUS mapSDocument() {
				return (DOCUMENT_STATUS.COMPLETED);
			}
		};
		return (mapper);
	}
}

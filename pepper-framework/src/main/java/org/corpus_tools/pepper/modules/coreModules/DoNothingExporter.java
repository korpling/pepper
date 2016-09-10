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
import org.corpus_tools.pepper.core.SelfTestDesc;
import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(name = "DoNothingExporterComponent", factory = "PepperExporterComponentFactory")
public class DoNothingExporter extends PepperExporterImpl implements PepperExporter {
	public static final String MODULE_NAME = "DoNothingExporter";
	public static final String FORMAT_NAME = "doNothing";
	public static final String FORMAT_VERSION = "0.0";

	@Override
	@Activate
	public void activate(ComponentContext componentContext) {
		super.activate(componentContext);
	}

	public DoNothingExporter() {
		// setting name of module
		super(MODULE_NAME);
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		setDesc("This is a dummy exporter which exports nothing. This exporter can be used to check if a corpus is importable. ");
		this.addSupportedFormat(FORMAT_NAME, FORMAT_VERSION, null);
	}

	@Override
	public SelfTestDesc getSelfTestDesc() {
		URI expectedURI = getResources().appendSegment("modules").appendSegment("selfTests")
				.appendSegment("doNothingExporter").appendSegment("expected");

		// We have to make sure the output directory exists, even if this is the
		// "do nothing" exporter
		if (!new File(expectedURI.toFileString()).mkdirs()) {
			logger.warn("Can't create output directory '" + expectedURI + "' for DoNothingExporter");
		}

		return new SelfTestDesc(getResources().appendSegment("modules").appendSegment("selfTests")
				.appendSegment("doNothingExporter").appendSegment("in"), expectedURI);
	}

	@Override
	public void start() throws PepperModuleException {
		final URI corpusPath = getCorpusDesc().getCorpusPath();
		if (corpusPath != null) {
			new File(corpusPath.toFileString()).mkdirs();
		}
		super.start();
	}

	/**
	 * Creates a mapper which does nothing.
	 */
	@Override
	public PepperMapper createPepperMapper(Identifier id) {
		final PepperMapper mapper = new PepperMapperImpl() {
			@Override
			public DOCUMENT_STATUS mapSDocument() {
				return (DOCUMENT_STATUS.COMPLETED);
			}
		};
		return (mapper);
	}
}

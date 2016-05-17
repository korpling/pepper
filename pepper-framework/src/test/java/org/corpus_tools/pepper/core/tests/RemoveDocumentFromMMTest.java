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
package org.corpus_tools.pepper.core.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.PepperConfiguration;
import org.corpus_tools.pepper.core.PepperJobImpl;
import org.corpus_tools.pepper.core.Step;
import org.corpus_tools.pepper.core.tests.PepperJobImplTest.MyImporter;
import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class RemoveDocumentFromMMTest extends PepperJobImpl {
	public RemoveDocumentFromMMTest() {
		super("myJob");
	}

	private PepperJobImpl fixture = null;

	public PepperJobImpl getFixture() {
		return fixture;
	}

	public void setFixture(PepperJobImpl fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp() {
		setFixture(this);
	}

	private URI dummyResourceURI = URI.createFileURI(new File(System.getProperty("java.io.tmpdir")).getAbsolutePath());

	/**
	 * Checks if a {@link SDocumentGraph} is not in memory any more, after all
	 * {@link PepperModule}'s have processed it. Remove means, that the
	 * connection between {@link SDocument} and {@link SDocumentGraph} is
	 * removed.
	 */
	@Test
	public void testDocumentRemoveFromMM() {
		
		List<SDocument> expectedSDocuments = new Vector<SDocument>();
		for (int i = 0; i < 30; i++) {
			SDocument sDoc = SaltFactory.createSDocument();
			sDoc.setDocumentGraph(SaltFactory.createSDocumentGraph());
			expectedSDocuments.add(sDoc);
		}

		Step importStep1 = new Step("im1");
		MyImporter myImporter1 = new PepperJobImplTest.MyImporter("myImporter1");
		myImporter1.setResources(dummyResourceURI);
		myImporter1.expectedSDocuments = expectedSDocuments;
		importStep1.setPepperModule(myImporter1);
		importStep1.getCorpusDesc().setCorpusPath(dummyResourceURI);
		getFixture().addStep(importStep1);

		Step exportStep1 = new Step("ex1");
		MyExporter myExporter1 = new MyExporter("ex1");
		myExporter1.setResources(dummyResourceURI);
		exportStep1.setPepperModule(myExporter1);
		getFixture().addStep(exportStep1);

		for (SDocument sDoc : expectedSDocuments) {
			assertNotNull(sDoc.getDocumentGraph());
		}
		PepperConfiguration conf = new PepperConfiguration();
		conf.setProperty(PepperConfiguration.PROP_MAX_AMOUNT_OF_SDOCUMENTS, "2");
		
		getFixture().convert();

		for (SDocument sDoc : expectedSDocuments) {
			assertNull(sDoc.getDocumentGraph());
		}
	}

	static class MyExporter extends PepperExporterImpl {
		public MyExporter(String name) {
			setName(name);
		}

		@Override
		public PepperMapper createPepperMapper(Identifier sElementId) {
			if (sElementId.getIdentifiableElement() instanceof SDocument) {
				if (((SDocument) sElementId.getIdentifiableElement()).getDocumentGraph() == null) {
					throw new PepperModuleException(this, "An error in test occured, because the SDocumentGraph was null.");
				}
			}
			return (new PepperMapperImpl() {
				@Override
				public DOCUMENT_STATUS mapSDocument() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						throw new PepperModuleException(this, "", e);
					}
					return DOCUMENT_STATUS.COMPLETED;
				}
			});
		}
	}
}

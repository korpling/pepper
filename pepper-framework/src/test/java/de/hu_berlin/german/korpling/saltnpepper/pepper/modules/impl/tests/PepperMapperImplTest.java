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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.tests;

import static org.junit.Assert.assertEquals;

import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;

public class PepperMapperImplTest {

	private PepperMapperImpl fixture = null;

	public PepperMapperImpl getFixture() {
		return fixture;
	}

	public void setFixture(PepperMapperImpl fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp() {
		setFixture(new PepperMapperImpl());
	}

	@Test
	public void testSetGetSDocument() {
		SDocument sDocument = SaltFactory.createSDocument();
		Identifier sElementId = SaltFactory.createIdentifier(sDocument, "d1");
		getFixture().setDocument(sDocument);

		assertEquals(sDocument, getFixture().getDocument());
	}

	@Test
	public void testSetGetSCorpus() {
		SCorpus sCorpus = SaltFactory.createSCorpus();
		Identifier sElementId = SaltFactory.createIdentifier(sCorpus, "c1");
		getFixture().setCorpus(sCorpus);

		assertEquals(sCorpus, getFixture().getCorpus());
	}

	@Test
	public void testSetGetResourceURI() {
		URI resourceURI = URI.createFileURI("./");
		getFixture().setResourceURI(resourceURI);

		assertEquals(resourceURI, getFixture().getResourceURI());
	}

	@Test
	public void testSetGetMappingResult() {
		DOCUMENT_STATUS mappingResult = DOCUMENT_STATUS.COMPLETED;
		getFixture().setMappingResult(mappingResult);

		assertEquals(mappingResult, getFixture().getMappingResult());
	}
}

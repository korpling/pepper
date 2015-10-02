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
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperModuleImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperModuleTest;
import de.hu_berlin.u.saltnpepper.salt.SaltFactory;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpus;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpusGraph;
import de.hu_berlin.u.saltnpepper.salt.common.SDocument;
import de.hu_berlin.u.saltnpepper.salt.core.SLayer;
import de.hu_berlin.u.saltnpepper.salt.core.SNode;
import de.hu_berlin.u.saltnpepper.salt.core.SRelation;
import de.hu_berlin.u.saltnpepper.salt.samples.SampleGenerator;

public class PepperModuleImplTest extends PepperImporterImpl {

	private PepperModuleImpl fixture = null;

	public PepperModuleImpl getFixture() {
		return fixture;
	}

	public void setFixture(PepperModuleImpl fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp() throws Exception {
		setFixture(this);
		getFixture().setProperties(new PepperModuleProperties());
	}

	@Test
	public void testPropAddSLayer() {
		SDocument sDoc = SaltFactory.createSDocument();
		SampleGenerator.createSDocumentStructure(sDoc);
		int layersBefore = sDoc.getDocumentGraph().getLayers().size();
		getFixture().getProperties().setPropertyValue(PepperModuleProperties.PROP_AFTER_ADD_SLAYER, "layer1; layer2");
		SaltFactory.createIdentifier(sDoc, "doc1");
		getFixture().after(sDoc.getIdentifier());

		assertEquals(layersBefore + 2, sDoc.getDocumentGraph().getLayers().size());
		SLayer layer1 = sDoc.getDocumentGraph().getLayerByName("layer1").get(0);
		SLayer layer2 = sDoc.getDocumentGraph().getLayerByName("layer2").get(0);
		for (SNode sNode : sDoc.getDocumentGraph().getNodes()) {
			assertTrue(sNode.getLayers().contains(layer1));
			assertTrue(sNode.getLayers().contains(layer2));
		}
		for (SRelation sRel : sDoc.getDocumentGraph().getRelations()) {
			assertTrue(sRel.getLayers().contains(layer1));
			assertTrue(sRel.getLayers().contains(layer2));
		}
	}

	@Test
	public void testPropReadMeta() throws IOException {
		File corpusPath = new File(PepperModuleTest.getTestResources() + "/readMeta/");
		URI corpusURI = URI.createFileURI(corpusPath.getCanonicalPath());
		SCorpusGraph graph = SaltFactory.createSCorpusGraph();
		SCorpus corpus = graph.createSCorpus(null, "corpus");
		SCorpus subCorpus = graph.createSCorpus(corpus, "subcorpus");
		SDocument document = graph.createSDocument(subCorpus, "document");

		this.getIdentifier2ResourceTable().put(corpus.getIdentifier(), corpusURI.appendSegment("corpus"));
		this.getIdentifier2ResourceTable().put(subCorpus.getIdentifier(), corpusURI.appendSegment("corpus").appendSegment("subcorpus"));
		this.getIdentifier2ResourceTable().put(document.getIdentifier(), corpusURI.appendSegment("corpus").appendSegment("subcorpus").appendSegment("document.txt"));

		getFixture().getProperties().getProperty(PepperModuleProperties.PROP_BEFORE_READ_META).setValueString("meta");

		getFixture().readMeta(corpus.getIdentifier());
		assertEquals(2, corpus.getMetaAnnotations().size());
		assertEquals("b", corpus.getMetaAnnotation("a").getValue());
		assertEquals("d", corpus.getMetaAnnotation("c").getValue());

		getFixture().readMeta(subCorpus.getIdentifier());
		assertEquals(2, subCorpus.getMetaAnnotations().size());
		assertEquals("2", subCorpus.getMetaAnnotation("1").getValue());
		assertEquals("4", subCorpus.getMetaAnnotation("3").getValue());

		getFixture().readMeta(document.getIdentifier());
		assertEquals(2, document.getMetaAnnotations().size());
		assertEquals("Bart", document.getMetaAnnotation("name").getValue());
		assertEquals("Springfield", document.getMetaAnnotation("place").getValue());
	}

	@Test
	public void test_PropAddSLayer() {
		SDocument sDoc = SaltFactory.createSDocument();
		SampleGenerator.createSDocumentStructure(sDoc);
		int layersBefore = sDoc.getDocumentGraph().getLayers().size();
		getFixture().getProperties().setPropertyValue(PepperModuleProperties.PROP_AFTER_ADD_SLAYER, "layer1; layer2");
		SaltFactory.createIdentifier(sDoc, "doc1");

		getFixture().after(sDoc.getIdentifier());

		assertEquals(layersBefore + 2, sDoc.getDocumentGraph().getLayers().size());
		SLayer layer1 = sDoc.getDocumentGraph().getLayerByName("layer1").get(0);
		SLayer layer2 = sDoc.getDocumentGraph().getLayerByName("layer2").get(0);
		for (SNode sNode : sDoc.getDocumentGraph().getNodes()) {
			assertTrue(sNode.getLayers().contains(layer1));
			assertTrue(sNode.getLayers().contains(layer2));
		}
		for (SRelation sRel : sDoc.getDocumentGraph().getRelations()) {
			assertTrue(sRel.getLayers().contains(layer1));
			assertTrue(sRel.getLayers().contains(layer2));
		}
	}
}

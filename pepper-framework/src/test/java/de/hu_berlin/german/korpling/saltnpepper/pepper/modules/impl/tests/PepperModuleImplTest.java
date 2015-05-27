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
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;

public class PepperModuleImplTest extends PepperImporterImpl{

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
		SDocument sDoc = SaltFactory.eINSTANCE.createSDocument();
		SampleGenerator.createSDocumentStructure(sDoc);
		int layersBefore = sDoc.getSDocumentGraph().getSLayers().size();
		getFixture().getProperties().setPropertyValue(PepperModuleProperties.PROP_AFTER_ADD_SLAYER, "layer1; layer2");
		sDoc.setSElementId(SaltFactory.eINSTANCE.createSElementId());
		getFixture().after(sDoc.getSElementId());

		assertEquals(layersBefore + 2, sDoc.getSDocumentGraph().getSLayers().size());
		SLayer layer1 = sDoc.getSDocumentGraph().getSLayers().get(layersBefore);
		SLayer layer2 = sDoc.getSDocumentGraph().getSLayers().get(layersBefore + 1);
		for (SNode sNode : sDoc.getSDocumentGraph().getSNodes()) {
			assertTrue(sNode.getSLayers().contains(layer1));
			assertTrue(sNode.getSLayers().contains(layer2));
		}
		for (SRelation sRel : sDoc.getSDocumentGraph().getSRelations()) {
			assertTrue(sRel.getSLayers().contains(layer1));
			assertTrue(sRel.getSLayers().contains(layer2));
		}
	}

	@Test
	public void testPropReadMeta() throws IOException{
		File corpusPath = new File(PepperModuleTest.getTestResources() + "/readMeta/");
		URI corpusURI= URI.createFileURI(corpusPath.getCanonicalPath());
		SCorpusGraph graph= SaltFactory.eINSTANCE.createSCorpusGraph();
		SCorpus corpus= graph.createSCorpus(null, "corpus");
		SCorpus subCorpus= graph.createSCorpus(corpus, "subcorpus");
		SDocument document= graph.createSDocument(subCorpus, "document");
		
		this.getSElementId2ResourceTable().put(corpus.getSElementId(), corpusURI.appendSegment("corpus"));
		this.getSElementId2ResourceTable().put(subCorpus.getSElementId(), corpusURI.appendSegment("corpus").appendSegment("subcorpus"));
		this.getSElementId2ResourceTable().put(document.getSElementId(), corpusURI.appendSegment("corpus").appendSegment("subcorpus").appendSegment("document.txt"));
		
		getFixture().getProperties().getProperty(PepperModuleProperties.PROP_BEFORE_READ_META).setValueString("meta");
		
		getFixture().readMeta(corpus.getSElementId());
		assertEquals(2, corpus.getSMetaAnnotations().size());
		assertEquals("b", corpus.getSMetaAnnotation("a").getSValue());
		assertEquals("d", corpus.getSMetaAnnotation("c").getSValue());
		
		getFixture().readMeta(subCorpus.getSElementId());
		assertEquals(2, subCorpus.getSMetaAnnotations().size());
		assertEquals("2", subCorpus.getSMetaAnnotation("1").getSValue());
		assertEquals("4", subCorpus.getSMetaAnnotation("3").getSValue());
		
		getFixture().readMeta(document.getSElementId());
		assertEquals(2, document.getSMetaAnnotations().size());
		assertEquals("Bart", document.getSMetaAnnotation("name").getSValue());
		assertEquals("Springfield", document.getSMetaAnnotation("place").getSValue());
	}
}

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
package org.corpus_tools.pepper.modules.impl.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.core.DocumentBus;
import org.corpus_tools.pepper.core.ModuleControllerImpl;
import org.corpus_tools.pepper.core.PepperJobImpl;
import org.corpus_tools.pepper.impl.PepperImporterImpl;
import org.corpus_tools.pepper.impl.PepperManipulatorImpl;
import org.corpus_tools.pepper.modules.BeforeAfterAction;
import org.corpus_tools.pepper.modules.DocumentController;
import org.corpus_tools.pepper.modules.ModuleController;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.testFramework.PepperModuleTest;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.core.SLayer;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.core.SRelation;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class BeforeAfterTest {

	private BeforeAfterAction fixture= null; 
	
	public BeforeAfterAction getFixture() {
		return fixture;
	}


	public void setFixture(BeforeAfterAction fixture) {
		this.fixture = fixture;
	}


	@Before
	public void setUp() throws Exception {
		setFixture(new BeforeAfterAction(new PepperImporterImpl() {
		}));
		getFixture().getPepperModule().setProperties(new PepperModuleProperties());
	}

	
	@Test
	public void testCopyRes() throws IOException {
		File fromPath = new File(PepperModuleTest.getTestResources() + "/copyRes/");
		File toPath = PepperUtil.getTempTestFile("to");

		FileUtils.deleteDirectory(toPath);
		getFixture().getPepperModule().setPepperModuleController(new ModuleControllerImpl("1"));
		getFixture().getPepperModule().getModuleController().setJob(new PepperJobImpl("1"));
		getFixture().getPepperModule().getModuleController().getJob().setBaseDir(URI.createFileURI(fromPath.getCanonicalPath()));
		String prop = "file1.txt    ->   " + toPath + ";  file2.txt-> " + toPath.getAbsolutePath() + "; ./folder-> " + toPath;
		getFixture().copyResources(prop);

		File file1 = new File(toPath.getCanonicalPath() + "/file1.txt");
		File file2 = new File(toPath.getCanonicalPath() + "/file2.txt");
		File file3 = new File(toPath.getCanonicalPath() + "/folder/file3.txt");

		assertTrue(file1.getCanonicalPath() + " does not exist", file1.exists());
		assertTrue(file2.getCanonicalPath() + " does not exist", file2.exists());
		assertTrue(file3.getCanonicalPath() + " does not exist", file3.exists());
	}
	
	@Test
	public void testPropAddSLayer() {
		SDocument sDoc = SaltFactory.createSDocument();
		SampleGenerator.createSDocumentStructure(sDoc);
		int layersBefore = sDoc.getDocumentGraph().getLayers().size();
		getFixture().getPepperModule().getProperties().setPropertyValue(PepperModuleProperties.PROP_AFTER_ADD_SLAYER, "layer1; layer2");
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
		SCorpus corpus = graph.createCorpus(null, "corpus");
		SCorpus subCorpus = graph.createCorpus(corpus, "subcorpus");
		SDocument document = graph.createDocument(subCorpus, "document");

		((PepperImporter)getFixture().getPepperModule()).getIdentifier2ResourceTable().put(corpus.getIdentifier(), corpusURI.appendSegment("corpus"));
		((PepperImporter)getFixture().getPepperModule()).getIdentifier2ResourceTable().put(subCorpus.getIdentifier(), corpusURI.appendSegment("corpus").appendSegment("subcorpus"));
		((PepperImporter)getFixture().getPepperModule()).getIdentifier2ResourceTable().put(document.getIdentifier(), corpusURI.appendSegment("corpus").appendSegment("subcorpus").appendSegment("document.txt"));

		getFixture().getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_BEFORE_READ_META).setValueString("meta");

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
		getFixture().getPepperModule().getProperties().setPropertyValue(PepperModuleProperties.PROP_AFTER_ADD_SLAYER, "layer1; layer2");
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

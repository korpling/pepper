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

import static org.corpus_tools.pepper.common.PepperUtil.compare;
import static org.corpus_tools.pepper.testFramework.PepperTestUtil.createTestTempPathAsUri;
import static org.corpus_tools.pepper.testFramework.PepperTestUtil.getTestResources;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.testFramework.PepperExporterTest;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.pepper.testFramework.RunFitnessCheck;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class TextExporterTest extends PepperExporterTest<TextExporter> implements RunFitnessCheck {
	private static final FormatDesc FORMAT_DESC = new FormatDesc().setFormatName(TextExporter.FORMAT_NAME)
			.setFormatVersion(TextExporter.FORMAT_VERSION);

	@Before
	public void beforeEach() {
		setTestedModule(new TextExporter());
		addFormatWhichShouldBeSupported(FORMAT_DESC);
	}

	/**
	 * Tests the export of one document and one text.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test1Doc1Text() throws IOException {
		SCorpus sCorpus = testedModule.getSaltProject().getCorpusGraphs().get(0).createCorpus(URI.createURI("/corp1"))
				.get(0);
		SDocument sDoc = testedModule.getSaltProject().getCorpusGraphs().get(0).createDocument(sCorpus, "doc1");
		SampleGenerator.createPrimaryData(sDoc);
		testedModule.setCorpusDesc(new CorpusDesc().setFormatDesc(FORMAT_DESC)
				.setCorpusPath(createTestTempPathAsUri("TextExporterTest/test1")));

		start();

		String parent = getTestResources() + "/TextExporterTest/test1";

		assertTrue(compare(new File(parent + "/corp1/doc1.txt"))
				.with(new File(testedModule.getCorpusDesc().getCorpusPath().toFileString() + "/corp1/doc1.txt")));
	}

	/**
	 * Tests the export of multiple documents and one text each.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test3Doc1Text() throws IOException {
		SCorpus sCorpus = testedModule.getSaltProject().getCorpusGraphs().get(0).createCorpus(URI.createURI("/corp1"))
				.get(0);
		SDocument sDoc = testedModule.getSaltProject().getCorpusGraphs().get(0).createDocument(sCorpus, "doc1");
		SampleGenerator.createPrimaryData(sDoc);

		SDocument sDoc2 = testedModule.getSaltProject().getCorpusGraphs().get(0).createDocument(sCorpus, "doc2");
		sDoc2.setDocumentGraph(SaltFactory.createSDocumentGraph());
		sDoc2.getDocumentGraph().createTextualDS(
				"With SaltNPepper we provide two powerful frameworks for dealing with linguistically annotated data. SaltNPepper is an Open Source project developed at the Humboldt-Universität zu Berlin and INRIA (Institut national de recherche en informatique et automatique). In linguistic research a variety of formats exists, but no unified way of processing them. To fill that gap, we developed a meta model called Salt which abstracts over linguistic data. Based on this model, we also developed the pluggable universal converter framework Pepper to convert linguistic data between various formats. ");

		SDocument sDoc3 = testedModule.getSaltProject().getCorpusGraphs().get(0).createDocument(sCorpus, "doc3");
		sDoc3.setDocumentGraph(SaltFactory.createSDocumentGraph());
		sDoc3.getDocumentGraph().createTextualDS(
				"Black pepper (Piper nigrum) is a flowering vine in the family Piperaceae, cultivated for its fruit, which is usually dried and used as a spice and seasoning. When dried, the fruit is known as a peppercorn. When fresh and fully mature, it is approximately 5 millimetres (0.20 in) in diameter, dark red, and, like all drupes, contains a single seed. Peppercorns, and the ground pepper derived from them, may be described simply as pepper, or more precisely as black pepper (cooked and dried unripe fruit), green pepper (dried unripe fruit) and white pepper (ripe fruit seeds).\n\nBlack pepper is native to south India, and is extensively cultivated there and elsewhere in tropical regions. Currently Vietnam is the world's largest producer and exporter of pepper, producing 34% of the world's Piper nigrum crop as of 2008.\n\nDried ground pepper has been used since antiquity for both its flavour and as a traditional medicine. Black pepper is the world's most traded spice. It is one of the most common spices added to European cuisine and its descendants. The spiciness of black pepper is due to the chemical piperine, not to be confused with the capsaicin that gives fleshy peppers theirs. It is ubiquitous in the modern world as a seasoning and is often paired with salt.");

		testedModule.setCorpusDesc(new CorpusDesc().setFormatDesc(FORMAT_DESC)
				.setCorpusPath(PepperTestUtil.createTestTempPathAsUri("TextExporterTest/test2")));

		start();

		String parent = getTestResources() + "/TextExporterTest/test2";

		assertTrue(compare(new File(parent + "/corp1/doc1.txt"))
				.with(new File(testedModule.getCorpusDesc().getCorpusPath().toFileString() + "/corp1/doc1.txt")));
		assertTrue(compare(new File(parent + "/corp1/doc2.txt"))
				.with(new File(testedModule.getCorpusDesc().getCorpusPath().toFileString() + "/corp1/doc2.txt")));
		assertTrue(compare(new File(parent + "/corp1/doc3.txt"))
				.with(new File(testedModule.getCorpusDesc().getCorpusPath().toFileString() + "/corp1/doc3.txt")));
	}

	@Test
	public void whenExporting3DocumentsWithMultipleTexts_thenExport4Texts() throws IOException {
		SCorpus sCorpus = testedModule.getSaltProject().getCorpusGraphs().get(0).createCorpus(URI.createURI("/corp1"))
				.get(0);
		SDocument sDoc = testedModule.getSaltProject().getCorpusGraphs().get(0).createDocument(sCorpus, "doc1");
		SampleGenerator.createPrimaryData(sDoc);

		SDocument sDoc2 = testedModule.getSaltProject().getCorpusGraphs().get(0).createDocument(sCorpus, "doc2");
		sDoc2.setDocumentGraph(SaltFactory.createSDocumentGraph());
		sDoc2.getDocumentGraph().createTextualDS(
				"With SaltNPepper we provide two powerful frameworks for dealing with linguistically annotated data. SaltNPepper is an Open Source project developed at the Humboldt-Universität zu Berlin and INRIA (Institut national de recherche en informatique et automatique). In linguistic research a variety of formats exists, but no unified way of processing them. To fill that gap, we developed a meta model called Salt which abstracts over linguistic data. Based on this model, we also developed the pluggable universal converter framework Pepper to convert linguistic data between various formats. ");
		sDoc2.getDocumentGraph().createTextualDS("This is text number 2.  ");

		SDocument sDoc3 = testedModule.getSaltProject().getCorpusGraphs().get(0).createDocument(sCorpus, "doc3");
		sDoc3.setDocumentGraph(SaltFactory.createSDocumentGraph());
		sDoc3.getDocumentGraph().createTextualDS(
				"Black pepper (Piper nigrum) is a flowering vine in the family Piperaceae, cultivated for its fruit, which is usually dried and used as a spice and seasoning. When dried, the fruit is known as a peppercorn. When fresh and fully mature, it is approximately 5 millimetres (0.20 in) in diameter, dark red, and, like all drupes, contains a single seed. Peppercorns, and the ground pepper derived from them, may be described simply as pepper, or more precisely as black pepper (cooked and dried unripe fruit), green pepper (dried unripe fruit) and white pepper (ripe fruit seeds).\n\nBlack pepper is native to south India, and is extensively cultivated there and elsewhere in tropical regions. Currently Vietnam is the world's largest producer and exporter of pepper, producing 34% of the world's Piper nigrum crop as of 2008.\n\nDried ground pepper has been used since antiquity for both its flavour and as a traditional medicine. Black pepper is the world's most traded spice. It is one of the most common spices added to European cuisine and its descendants. The spiciness of black pepper is due to the chemical piperine, not to be confused with the capsaicin that gives fleshy peppers theirs. It is ubiquitous in the modern world as a seasoning and is often paired with salt.");

		testedModule.setCorpusDesc(new CorpusDesc().setFormatDesc(FORMAT_DESC)
				.setCorpusPath(createTestTempPathAsUri("TextExporterTest/test3")));
		start();
		String parent = getTestResources() + "/TextExporterTest/test3";

		assertTrue(compare(new File(parent + "/corp1/doc1.txt"))
				.with(new File(testedModule.getCorpusDesc().getCorpusPath().toFileString() + "/corp1/doc1.txt")));
		assertTrue(compare(new File(parent + "/corp1/doc2_sText1.txt")).with(
				new File(testedModule.getCorpusDesc().getCorpusPath().toFileString() + "/corp1/doc2_sText1.txt")));
		assertTrue(compare(new File(parent + "/corp1/doc2_sText2.txt")).with(
				new File(testedModule.getCorpusDesc().getCorpusPath().toFileString() + "/corp1/doc2_sText2.txt")));
		assertTrue(compare(new File(parent + "/corp1/doc3.txt"))
				.with(new File(testedModule.getCorpusDesc().getCorpusPath().toFileString() + "/corp1/doc3.txt")));
	}
}

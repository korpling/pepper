package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules.tests;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules.TextExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperExporterTest;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;

public class TextExporterTest extends PepperExporterTest {

	@Override
	public TextExporter getFixture() {
		return (TextExporter) fixture;
	}

	FormatDesc formatDesc = new FormatDesc().setFormatName(TextExporter.FORMAT_NAME).setFormatVersion(TextExporter.FORMAT_VERSION);

	@Before
	public void setUp() {
		setFixture(new TextExporter());
		addSupportedFormat(formatDesc);
	}

	/**
	 * Tests the export of one document and one text.
	 * @throws IOException 
	 */
	@Test
	public void test1Doc1Text() throws IOException {
		SCorpus sCorpus = getFixture().getSaltProject().getSCorpusGraphs().get(0).createSCorpus(URI.createURI("corp1")).get(0);
		SDocument sDoc = getFixture().getSaltProject().getSCorpusGraphs().get(0).createSDocument(sCorpus, "doc1");
		SampleGenerator.createPrimaryData(sDoc);
		getFixture().setCorpusDesc(new CorpusDesc().setFormatDesc(formatDesc).setCorpusPath(getTempURI("TextExporterTest/test1")));

		start();
		
		String parent= getTestResources()+"/TextExporterTest/test1";
		
		assertTrue(compareFiles(new File(parent+"/corp1/doc1.txt"), new File(getFixture().getCorpusDesc().getCorpusPath().toFileString()+"/corp1/doc1.txt")));
	}

	/**
	 * Tests the export of multiple documents and one text each.
	 * @throws IOException 
	 */
	@Test
	public void test3Doc1Text() throws IOException {
		SCorpus sCorpus = getFixture().getSaltProject().getSCorpusGraphs().get(0).createSCorpus(URI.createURI("corp1")).get(0);
		SDocument sDoc = getFixture().getSaltProject().getSCorpusGraphs().get(0).createSDocument(sCorpus, "doc1");
		SampleGenerator.createPrimaryData(sDoc);
		
		SDocument sDoc2 = getFixture().getSaltProject().getSCorpusGraphs().get(0).createSDocument(sCorpus, "doc2");
		sDoc2.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		sDoc2.getSDocumentGraph().createSTextualDS("With SaltNPepper we provide two powerful frameworks for dealing with linguistically annotated data. SaltNPepper is an Open Source project developed at the Humboldt-Universität zu Berlin and INRIA (Institut national de recherche en informatique et automatique). In linguistic research a variety of formats exists, but no unified way of processing them. To fill that gap, we developed a meta model called Salt which abstracts over linguistic data. Based on this model, we also developed the pluggable universal converter framework Pepper to convert linguistic data between various formats. ");
		
		SDocument sDoc3 = getFixture().getSaltProject().getSCorpusGraphs().get(0).createSDocument(sCorpus, "doc3");
		sDoc3.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		sDoc3.getSDocumentGraph().createSTextualDS("Black pepper (Piper nigrum) is a flowering vine in the family Piperaceae, cultivated for its fruit, which is usually dried and used as a spice and seasoning. When dried, the fruit is known as a peppercorn. When fresh and fully mature, it is approximately 5 millimetres (0.20 in) in diameter, dark red, and, like all drupes, contains a single seed. Peppercorns, and the ground pepper derived from them, may be described simply as pepper, or more precisely as black pepper (cooked and dried unripe fruit), green pepper (dried unripe fruit) and white pepper (ripe fruit seeds).\n\nBlack pepper is native to south India, and is extensively cultivated there and elsewhere in tropical regions. Currently Vietnam is the world's largest producer and exporter of pepper, producing 34% of the world's Piper nigrum crop as of 2008.\n\nDried ground pepper has been used since antiquity for both its flavour and as a traditional medicine. Black pepper is the world's most traded spice. It is one of the most common spices added to European cuisine and its descendants. The spiciness of black pepper is due to the chemical piperine, not to be confused with the capsaicin that gives fleshy peppers theirs. It is ubiquitous in the modern world as a seasoning and is often paired with salt.");
		
		getFixture().setCorpusDesc(new CorpusDesc().setFormatDesc(formatDesc).setCorpusPath(getTempURI("TextExporterTest/test2")));

		start();
		
		String parent= getTestResources()+"/TextExporterTest/test2";
		
		assertTrue(compareFiles(new File(parent+"/corp1/doc1.txt"), new File(getFixture().getCorpusDesc().getCorpusPath().toFileString()+"/corp1/doc1.txt")));
		assertTrue(compareFiles(new File(parent+"/corp1/doc2.txt"), new File(getFixture().getCorpusDesc().getCorpusPath().toFileString()+"/corp1/doc2.txt")));
		assertTrue(compareFiles(new File(parent+"/corp1/doc3.txt"), new File(getFixture().getCorpusDesc().getCorpusPath().toFileString()+"/corp1/doc3.txt")));
	}

	/**
	 * Tests the export of multiple documents with multiple texts.
	 * @throws IOException 
	 */
	@Test
	public void test3DocMultiText() throws IOException {
		SCorpus sCorpus = getFixture().getSaltProject().getSCorpusGraphs().get(0).createSCorpus(URI.createURI("corp1")).get(0);
		SDocument sDoc = getFixture().getSaltProject().getSCorpusGraphs().get(0).createSDocument(sCorpus, "doc1");
		SampleGenerator.createPrimaryData(sDoc);
		
		SDocument sDoc2 = getFixture().getSaltProject().getSCorpusGraphs().get(0).createSDocument(sCorpus, "doc2");
		sDoc2.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		sDoc2.getSDocumentGraph().createSTextualDS("With SaltNPepper we provide two powerful frameworks for dealing with linguistically annotated data. SaltNPepper is an Open Source project developed at the Humboldt-Universität zu Berlin and INRIA (Institut national de recherche en informatique et automatique). In linguistic research a variety of formats exists, but no unified way of processing them. To fill that gap, we developed a meta model called Salt which abstracts over linguistic data. Based on this model, we also developed the pluggable universal converter framework Pepper to convert linguistic data between various formats. ");
		sDoc2.getSDocumentGraph().createSTextualDS("This is text number 2.  ");
		
		SDocument sDoc3 = getFixture().getSaltProject().getSCorpusGraphs().get(0).createSDocument(sCorpus, "doc3");
		sDoc3.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		sDoc3.getSDocumentGraph().createSTextualDS("Black pepper (Piper nigrum) is a flowering vine in the family Piperaceae, cultivated for its fruit, which is usually dried and used as a spice and seasoning. When dried, the fruit is known as a peppercorn. When fresh and fully mature, it is approximately 5 millimetres (0.20 in) in diameter, dark red, and, like all drupes, contains a single seed. Peppercorns, and the ground pepper derived from them, may be described simply as pepper, or more precisely as black pepper (cooked and dried unripe fruit), green pepper (dried unripe fruit) and white pepper (ripe fruit seeds).\n\nBlack pepper is native to south India, and is extensively cultivated there and elsewhere in tropical regions. Currently Vietnam is the world's largest producer and exporter of pepper, producing 34% of the world's Piper nigrum crop as of 2008.\n\nDried ground pepper has been used since antiquity for both its flavour and as a traditional medicine. Black pepper is the world's most traded spice. It is one of the most common spices added to European cuisine and its descendants. The spiciness of black pepper is due to the chemical piperine, not to be confused with the capsaicin that gives fleshy peppers theirs. It is ubiquitous in the modern world as a seasoning and is often paired with salt.");
		
		getFixture().setCorpusDesc(new CorpusDesc().setFormatDesc(formatDesc).setCorpusPath(getTempURI("TextExporterTest/test3")));

		start();
		
		String parent= getTestResources()+"/TextExporterTest/test3";
		
		assertTrue(compareFiles(new File(parent+"/corp1/doc1.txt"), new File(getFixture().getCorpusDesc().getCorpusPath().toFileString()+"/corp1/doc1.txt")));
		assertTrue(compareFiles(new File(parent+"/corp1/doc2_sText1.txt"), new File(getFixture().getCorpusDesc().getCorpusPath().toFileString()+"/corp1/doc2_sText1.txt")));
		assertTrue(compareFiles(new File(parent+"/corp1/doc2_sText2.txt"), new File(getFixture().getCorpusDesc().getCorpusPath().toFileString()+"/corp1/doc2_sText2.txt")));
		assertTrue(compareFiles(new File(parent+"/corp1/doc3.txt"), new File(getFixture().getCorpusDesc().getCorpusPath().toFileString()+"/corp1/doc3.txt")));
	}
}

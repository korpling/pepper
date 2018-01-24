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

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.modules.coreModules.SaltXMLExporter;
import org.corpus_tools.pepper.testFramework.PepperExporterTest;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class SaltXMLExporterTest extends PepperExporterTest {
	
	@Override
	public SaltXMLExporter getFixture() {
		return (SaltXMLExporter) fixture;
	}

	private static final FormatDesc FORMAT_DESC = new FormatDesc().setFormatName(SaltXMLExporter.FORMAT_NAME)
			.setFormatVersion(SaltXMLExporter.FORMAT_VERSION);
	
	
	@Before
	public void setUp() throws Exception {
		setFixture(new SaltXMLExporter());
		addFormatWhichShouldBeSupported(SaltXMLExporter.FORMAT_NAME, SaltXMLExporter.FORMAT_VERSION);
	}
	
	/**
	 * Tests the export of document annotations.
	 * 
	 * @see <a href="https://github.com/korpling/pepper/issues/115">korpling/pepper#115</a>
	 * 
	 * @throws IOException
	 * 
	 * @author Stephan Druskat
	 */
	@Test
	public void testDocumentAnnotations() throws IOException {
		SCorpus sCorpus = getFixture().getSaltProject().getCorpusGraphs().get(0).createCorpus(URI.createURI("/corp1"))
				.get(0);
		SDocument sDoc = getFixture().getSaltProject().getCorpusGraphs().get(0).createDocument(sCorpus, "doc1");
		final String id1 = sDoc.getIdentifier().toString();
		sDoc.createAnnotation("TEST", "ANNOTATION", id1);

		SDocument sDoc2 = getFixture().getSaltProject().getCorpusGraphs().get(0).createDocument(sCorpus, "doc2");
		sDoc2.setDocumentGraph(SaltFactory.createSDocumentGraph());
		final String id2 = sDoc2.getIdentifier().toString();
		sDoc2.createAnnotation("TEST", "ANNOTATION", id2);

		SDocument sDoc3 = getFixture().getSaltProject().getCorpusGraphs().get(0).createDocument(sCorpus, "doc3");
		sDoc3.setDocumentGraph(SaltFactory.createSDocumentGraph());
		final String id3 = sDoc3.getIdentifier().toString();
		sDoc3.createAnnotation("TEST", "ANNOTATION", id3);

		getFixture().setCorpusDesc(
				new CorpusDesc().setFormatDesc(FORMAT_DESC).setCorpusPath(getTempURI("SaltXMLExporterTest/testDocumentAnnotations")));

		start();

		List<String> lines = Files.readAllLines(Paths.get(getFixture().getCorpusDesc().getCorpusPath().toFileString() + "/saltProject.salt"));
		boolean lines1Correct = false;
		boolean lines2Correct = false;
		boolean lines3Correct = false;
		for (String s : lines) {
			if (s.trim().equals("<labels xsi:type=\"saltCore:SAnnotation\" namespace=\"TEST\" name=\"ANNOTATION\" value=\"T::" + id1 + "\"/>")) {
				lines1Correct = true;
				break;
			}
		 }
		for (String s : lines) {
			if (s.trim().equals("<labels xsi:type=\"saltCore:SAnnotation\" namespace=\"TEST\" name=\"ANNOTATION\" value=\"T::" + id2 + "\"/>")) {
				lines2Correct = true;
				break;
			}
		 }
		for (String s : lines) {
			if (s.trim().equals("<labels xsi:type=\"saltCore:SAnnotation\" namespace=\"TEST\" name=\"ANNOTATION\" value=\"T::" + id3 + "\"/>")) {
				lines3Correct = true;
				break;
			}
		 }
		assertTrue(lines1Correct);
		assertTrue(lines2Correct);
		assertTrue(lines3Correct);
	}
}
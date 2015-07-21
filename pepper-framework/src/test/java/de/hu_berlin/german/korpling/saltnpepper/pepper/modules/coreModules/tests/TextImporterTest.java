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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules.TextImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules.TextImporter.TextMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperImporterTest;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

public class TextImporterTest extends PepperImporterTest {

	@Before
	public void setUp() throws Exception {
		setFixture(new TextImporter());
		// set formats to support
		FormatDesc formatDef = new FormatDesc();
		formatDef.setFormatName(TextImporter.FORMAT_NAME);
		formatDef.setFormatVersion(TextImporter.FORMAT_VERSION);
		this.supportedFormatsCheck.add(formatDef);
	}

	private File testPath = getTempPath("textImporterTest");

	/**
	 * Tests the import of a single txt file containing characters linebreaks
	 * and tabs.
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void testTextMapper() throws FileNotFoundException {
		String text = "A sample text\n spread \n through several \n lines\t and tabs.";
		File txtFile = new File(testPath.getAbsolutePath() + System.getProperty("file.separator") + "text.txt");

		PrintWriter writer = new PrintWriter(txtFile);
		writer.print(text);
		writer.flush();
		writer.close();

		TextMapper fixture = new TextMapper();
		SDocument sDocument = SaltFactory.eINSTANCE.createSDocument();
		fixture.setSDocument(sDocument);
		fixture.setResourceURI(URI.createFileURI(txtFile.getAbsolutePath()));
		fixture.mapSDocument();

		assertNotNull(sDocument.getSDocumentGraph());
		assertNotNull(sDocument.getSDocumentGraph().getSTextualDSs());
		assertEquals(1, sDocument.getSDocumentGraph().getSTextualDSs().size());
		assertNotNull(sDocument.getSDocumentGraph().getSTextualDSs().get(0));
		assertEquals(text, sDocument.getSDocumentGraph().getSTextualDSs().get(0).getSText());
	}

	/**
	 * Tests the corpus-structure
	 * 
	 * <pre>
	 * 	      c1
	 *      /    \
	 *    c1     c3
	 *   /  \    / \
	 *   d1 d2  d3  d4
	 * </pre>
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void testCorpusStructure() throws FileNotFoundException {
		File c1 = new File(testPath.getAbsolutePath() + System.getProperty("file.separator") + "c1");
		c1.mkdirs();
		File c2 = new File(c1.getAbsolutePath() + System.getProperty("file.separator") + "c2");
		c2.mkdirs();
		File c3 = new File(c1.getAbsolutePath() + System.getProperty("file.separator") + "c3");
		c3.mkdirs();

		File d1 = new File(c2.getAbsolutePath() + System.getProperty("file.separator") + "d1.txt");
		PrintWriter writer = new PrintWriter(d1);
		writer.flush();
		writer.close();

		File d2 = new File(c2.getAbsolutePath() + System.getProperty("file.separator") + "d2.txt");
		writer = new PrintWriter(d2);
		writer.flush();
		writer.close();

		File d3 = new File(c3.getAbsolutePath() + System.getProperty("file.separator") + "d3.txt");
		writer = new PrintWriter(d3);
		writer.flush();
		writer.close();

		File d4 = new File(c3.getAbsolutePath() + System.getProperty("file.separator") + "d4.txt");
		writer = new PrintWriter(d4);
		writer.flush();
		writer.close();

		// start: creating and setting corpus definition
		CorpusDesc corpDesc = new CorpusDesc();
		FormatDesc formatDesc = new FormatDesc();
		formatDesc.setFormatName(TextImporter.FORMAT_NAME);
		formatDesc.setFormatVersion(TextImporter.FORMAT_VERSION);
		corpDesc.setFormatDesc(formatDesc);
		corpDesc.setCorpusPath(URI.createFileURI(c1.getAbsolutePath()));
		this.getFixture().setCorpusDesc(corpDesc);

		start();

		assertNotNull(getFixture().getSaltProject());
		assertEquals(1, getFixture().getSaltProject().getSCorpusGraphs().size());
		assertNotNull(getFixture().getSaltProject().getSCorpusGraphs().get(0));

		assertEquals(3, getFixture().getSaltProject().getSCorpusGraphs().get(0).getSCorpora().size());
		assertEquals(4, getFixture().getSaltProject().getSCorpusGraphs().get(0).getSDocuments().size());
	}
}

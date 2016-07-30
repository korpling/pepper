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
package org.corpus_tools.pepper.modules.coreModules.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.core.ModuleFitnessChecker;
import org.corpus_tools.pepper.exceptions.PepperTestException;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.SelfTestDesc;
import org.corpus_tools.pepper.modules.coreModules.DoNothingImporter;
import org.corpus_tools.pepper.modules.coreModules.TextImporter;
import org.corpus_tools.pepper.modules.coreModules.TextImporter.TextMapper;
import org.corpus_tools.pepper.testFramework.PepperImporterTest;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

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
		SDocument sDocument = SaltFactory.createSDocument();
		fixture.setDocument(sDocument);
		fixture.setResourceURI(URI.createFileURI(txtFile.getAbsolutePath()));
		fixture.mapSDocument();

		assertNotNull(sDocument.getDocumentGraph());
		assertNotNull(sDocument.getDocumentGraph().getTextualDSs());
		assertEquals(1, sDocument.getDocumentGraph().getTextualDSs().size());
		assertNotNull(sDocument.getDocumentGraph().getTextualDSs().get(0));
		assertEquals(text, sDocument.getDocumentGraph().getTextualDSs().get(0).getText());
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
		if (!c1.exists() && !c1.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + c1 + "'. ");
		}
		File c2 = new File(c1.getAbsolutePath() + System.getProperty("file.separator") + "c2");
		if (!c2.exists() && !c2.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + c2 + "'. ");
		}
		File c3 = new File(c1.getAbsolutePath() + System.getProperty("file.separator") + "c3");
		if (!c3.exists() && !c3.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + c3 + "'. ");
		}

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
		getFixture().setCorpusDesc(corpDesc);

		start();

		assertNotNull(getFixture().getSaltProject());
		assertEquals(1, getFixture().getSaltProject().getCorpusGraphs().size());
		assertNotNull(getFixture().getSaltProject().getCorpusGraphs().get(0));

		assertEquals(3, getFixture().getSaltProject().getCorpusGraphs().get(0).getCorpora().size());
		assertEquals(4, getFixture().getSaltProject().getCorpusGraphs().get(0).getDocuments().size());
	}

	@Test
	public void whenSelfTestingModule_thenResultShouldBeTrue() {
		final ModuleFitness fitness = new ModuleFitnessChecker(null).selfTest(fixture, PepperTestUtil.createDefaultPepper());

		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).describedAs(""+SaltUtil.compare(SaltUtil.loadSaltProject(fixture.getSelfTestDesc().getExpectedCorpusPath()).getCorpusGraphs().get(0)).with(fixture.getSaltProject().getCorpusGraphs().get(0)).andFindDiffs()).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
	}
}

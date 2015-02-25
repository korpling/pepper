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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.sampleModules.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperExporterTest;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.sampleModules.SampleExporter;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;

/**
 * This is a dummy implementation of a JUnit test for testing the
 * {@link SampleExporter} class. Feel free to adapt and enhance this test class
 * for real tests to check the work of your importer. If you are not confirm
 * with JUnit, please have a look at <a
 * href="http://www.vogella.com/tutorials/JUnit/article.html">
 * http://www.vogella.com/tutorials/JUnit/article.html</a>. <br/>
 * Please note, that the test class is derived from {@link PepperExporterTest}.
 * The usage of this class should simplfy your work and allows you to test only
 * your single importer in the Pepper environment.
 * 
 * @author Florian Zipser
 * 
 */
public class SampleExporterTest extends PepperExporterTest {
	/**
	 * This method is called by the JUnit environment each time before a test
	 * case starts. So each time a method annotated with @Test is called. This
	 * enables, that each method could run in its own environment being not
	 * influenced by before or after running test cases.
	 */
	@Before
	public void setUp() {
		setFixture(new SampleExporter());

		FormatDesc formatDef = new FormatDesc();
		formatDef.setFormatName("dot");
		formatDef.setFormatVersion("1.0");
		this.supportedFormatsCheck.add(formatDef);
	}

	/**
	 * This is a test to check the correct work of our dummy implementation.
	 * This test is supposed to show the usage of JUnit and to give some
	 * impressions how to check simple things of the created salt model. <br/>
	 * You can create as many test cases as you like, just create further
	 * methods having the annotation "@Test". Note that it is very helpful, to
	 * give them self explaining names and a short JavaDoc explaining their
	 * purpose. This could help very much, when searching for bugs or extending
	 * the tests. <br/>
	 * In our case, we just test, if correct number of corpora and documents was
	 * created, if all corpora have got a meta-annotation and if each
	 * document-structure contains the right number of nodes and relations.
	 */
	@Test
	public void test_DummyImplementation() {
		// create a sample corpus, the class SampleGenerator provides a bunch of
		// helpful methods to create sample documents and corpora
		getFixture().setSaltProject(SampleGenerator.createSaltProject());

		// determine location, to where the corpus should be exported
		getFixture().setCorpusDesc(new CorpusDesc().setCorpusPath(URI.createFileURI(getTempPath("sampleExporter").getAbsolutePath())));
		
		// starts the Pepper framework and the conversion process
		start();

		File superCorpus = new File(getTempPath("sampleExporter").getAbsolutePath() + "/rootCorpus");
		assertTrue(superCorpus.exists());
		File subCorpus1 = new File(superCorpus.getAbsolutePath() + "/subCorpus1");
		assertTrue(subCorpus1.exists());
		File document1 = new File(subCorpus1.getAbsolutePath() + "/doc1.dot");
		assertTrue(document1.exists());
		File document2 = new File(subCorpus1.getAbsolutePath() + "/doc2.dot");
		assertTrue(document2.exists());
		File subCorpus2 = new File(superCorpus.getAbsolutePath() + "/subCorpus2");
		assertTrue(subCorpus1.exists());
		File document3 = new File(subCorpus2.getAbsolutePath() + "/doc3.dot");
		assertTrue(document3.exists());
		File document4 = new File(subCorpus2.getAbsolutePath() + "/doc4.dot");
		assertTrue(document4.exists());
	}
	// TODO add further tests for any test cases you can think of and which are
	// necessary
}

/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperManipulatorImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperManipulatorTest;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.sampleModules.SampleImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.sampleModules.SampleManipulator;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;
/**
 * This is a dummy implementation of a JUnit test for testing the {@link SampleImporter} class.
 * Feel free to adapt and enhance this test class for real tests to check the work of your importer.
 * If you are not confirm with JUnit, please have a look at <a href="http://www.vogella.com/tutorials/JUnit/article.html">
 * http://www.vogella.com/tutorials/JUnit/article.html</a>.
 * <br/>
 * Please note, that the test class is derived from {@link PepperImporterTest}. The usage of this class
 * should simplfy your work and allows you to test only your single importer in the Pepper environment.
 * @author Florian Zipser
 *
 */
public class SampleManipulatorTest extends PepperManipulatorTest{
	/**
	 * This method is called by the JUnit environment each time before a test case starts. 
	 * So each time a method annotated with @Test is called. This enables, that each method 
	 * could run in its own environment being not influenced by before or after running test 
	 * cases. 
	 */
	@Before
	public void setUp(){
		setFixture(new SampleManipulator());
	}

	/**
	 * This is a test to check the correct work of our dummy implementation. This test is supposed to 
	 * show the usage of JUnit and to give some impressions how to check simple things of the created 
	 * salt model.
	 * <br/>
	 * You can create as many test cases as you like, just create further methods having the annotation
	 * @Test. Note that it is very helpful, to give them self explaining names and a short JavaDoc explaining
	 * their purpose. This could help very much, when searching for bugs or extending the tests.
	 * <br/>
	 * In our case, we just test, if correct number of corpora and documents was created, if all corpora have
	 * got a meta-annotation and if each document-structure contains the right number of nodes and relations.
	 */
	@Test
	public void test_DummyImplementation() {
		
//		SaltProject  SampleGenerator.createCompleteSaltproject();
		
		
		//starts the Pepper framework and the conversion process
		start();
		
		//checks if the salt project, which is a container for the created salt model exists.
		assertNotNull(getFixture().getSaltProject());
		//checks if really one corpus-structure was created in the target salt model
		assertEquals(1, getFixture().getSaltProject().getSCorpusGraphs().size());
		//checks that the corpus-structure contains 3 corpora
		assertEquals(3, getFixture().getSaltProject().getSCorpusGraphs().get(0).getSCorpora().size());
		//checks that the corpus-structure contains 4 documents
		assertEquals(4, getFixture().getSaltProject().getSCorpusGraphs().get(0).getSDocuments().size());
		
		//checks that each corpus contains a date annotation and that its value is 1989-12-17
		for (SCorpus sCorpus: getFixture().getSaltProject().getSCorpusGraphs().get(0).getSCorpora()){
			assertNotNull(sCorpus.getSMetaAnnotation("date"));
			assertEquals("1989-12-17", sCorpus.getSMetaAnnotation("date").getSValue());
		}
		
		//checks for each document-structure, that all kinds of nodes and relations are contained
		for (SDocument sDocument: getFixture().getSaltProject().getSCorpusGraphs().get(0).getSDocuments()){
			//since in Pepper processed document-structures are stored to file, for main memory issues, we have to load them for checking
			sDocument.loadSDocumentGraph();
			//checks that all nodes are contained
			assertEquals(27, sDocument.getSDocumentGraph().getSNodes().size());
			//checks that all relations are contained
			assertEquals(46, sDocument.getSDocumentGraph().getSRelations().size());
			//checks that all tokens (subclass of nodes) are contained
			assertEquals(11, sDocument.getSDocumentGraph().getSTokens().size());
			//checks that all spans (subclass of nodes) are contained
			assertEquals(3, sDocument.getSDocumentGraph().getSSpans().size());
			//checks that all structures (subclass of nodes) are contained
			assertEquals(12, sDocument.getSDocumentGraph().getSStructures().size());
			//checks that all pointing relations (subclass of relations) are contained
			assertEquals(1, sDocument.getSDocumentGraph().getSPointingRelations().size());
		}
		SaltFactory.eINSTANCE.save_DOT(getFixture().getSaltProject(), URI.createFileURI("/home/florian/Test/SampleImportTest/"));
	}

	//TODO add further tests for any test cases you can think of and which are necessary
}

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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.modules.coreModules.DOTExporter;
import org.corpus_tools.pepper.testFramework.PepperExporterTest;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SCorpusDocumentRelation;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public class DOTExporterTest extends PepperExporterTest {
	Logger logger = LoggerFactory.getLogger(DOTExporter.class);

	URI resourceURI = URI.createFileURI("src/test/resources/resources");
	
	@Before
	public void setUp() throws Exception {
		super.setFixture(new DOTExporter());
		super.getFixture().setSaltProject(SaltFactory.createSaltProject());
		super.setResourcesURI(resourceURI);
		// setting temproraries and resources

		File resourceDir = new File(resourceURI.toFileString());
		if (!resourceDir.exists())
			resourceDir.mkdirs();
		getFixture().setResources(resourceURI);
		// setting temproraries and resources

		// set formats to support
		FormatDesc formatDef = new FormatDesc();
		formatDef.setFormatName("dot");
		formatDef.setFormatVersion("1.0");
		this.supportedFormatsCheck.add(formatDef);
	}

	// TODO incomment this test in next version
	public void testCreateCorpusStructure() throws IOException {

		File corpusPathFile = getTempPath("pepperModules.DotModules/Exporter/testcase1");
		File currentFile = getTempPath(".pepperModules.DotModules/Exporter/testcase1/corp1.dot");
		File expectedFile = new File("./src/test/resources/expected/Exporter/testcase1/corp1.dot");

		URI corpusPath = URI.createFileURI(corpusPathFile.getCanonicalPath());
		URI currentURI = URI.createFileURI(currentFile.getCanonicalPath());
		URI expectedURI = URI.createFileURI(expectedFile.getCanonicalPath());

		this.removeDirRec(new File(corpusPath.toFileString()));

		{// creating and setting corpus definition
			CorpusDesc corpDef = new CorpusDesc();
			FormatDesc formatDef = new FormatDesc();
			formatDef.setFormatName("dot");
			formatDef.setFormatVersion("1.0");
			corpDef.setFormatDesc(formatDef);
			corpDef.setCorpusPath(corpusPath);
			getFixture().setCorpusDesc(corpDef);

		}
		logger.debug("created Corpus Def for testcase 1");

		// start: create sample
		// start:create corpus structure

		SDocument sDoc = this.createCorpusStructure(null);
		sDoc.setDocumentGraph(null);
		// logger.debug("Created corpus structure for sDocument");
		// end: create sample

		// start: exporting document
		this.start();
		// end: exporting document
		// logger.debug("Finished Export");

		{// checking if export was correct
			assertTrue("The Corpus Structure was not created", currentFile.isFile());
			assertTrue("The Corpus Structure is wrong (" + currentFile + ")", this.compareFiles(expectedURI, currentURI));
		}
	}

	/**
	 * Creates a corpus structure with one corpus and one document. It returns
	 * the created document. corp1 | doc1
	 * 
	 * @param corpGraph
	 * @return
	 */
	private SDocument createCorpusStructure(SCorpusGraph corpGraph) {
		{// creating corpus structure
			corpGraph = SaltFactory.createSCorpusGraph();
			getFixture().getSaltProject().getCorpusGraphs().add(corpGraph);
			// corp1
			// |
			// doc1

			// corp1

			SCorpus corp1 = SaltFactory.createSCorpus();
			SaltFactory.createIdentifier(corp1, "corp1");
			corp1.setName("corp1");
			corp1.setId("corp1");
			corpGraph.addNode(corp1);

			// doc1
			SDocument doc1 = SaltFactory.createSDocument();
			SaltFactory.createIdentifier(doc1, "corp1/doc1");
			doc1.setName("doc1");
			corpGraph.addNode(doc1);
			doc1.setDocumentGraph(SaltFactory.createSDocumentGraph());
			// CorpDocRel
			SCorpusDocumentRelation corpDocRel1 = SaltFactory.createSCorpusDocumentRelation();
			SaltFactory.createIdentifier(corpDocRel1, "rel1");
			corpDocRel1.setName("rel1");
			corpDocRel1.setSource(corp1);
			corpDocRel1.setTarget(doc1);
			corpGraph.addRelation(corpDocRel1);
			return (doc1);
		}
	}

	private void removeDirRec(File dir) {
		if (dir != null && dir.listFiles() != null && dir.listFiles().length != 0) {
			for (File subDir : dir.listFiles()) {
				this.removeDirRec(subDir);
			}
			dir.delete();
		}
	}
}

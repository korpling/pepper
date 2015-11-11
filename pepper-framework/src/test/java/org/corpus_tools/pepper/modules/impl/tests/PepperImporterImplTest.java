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
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.exceptions.PepperTestException;
import org.corpus_tools.pepper.impl.PepperImporterImpl;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PepperImporterImplTest {

	static class FixtureImporter extends PepperImporterImpl {

	}

	private FixtureImporter fixture = null;

	public void setFixture(FixtureImporter fixture) {
		this.fixture = fixture;
	}

	public FixtureImporter getFixture() {
		return fixture;
	}

	@Before
	public void setUp() {
		this.setFixture(new FixtureImporter());
		sCorpusGraph = SaltFactory.createSCorpusGraph();
	}

	@After
	public void tearDown() {
		this.getTempFolder().deleteOnExit();
	}

	private SCorpusGraph sCorpusGraph = null;

	private File getTempFolder() {
		return (PepperUtil.getTempTestFile("pepperImporterTests"));
	}

	/**
	 * Checks, that .svn file are ignored.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testImportCorpusStructure_SVN1() throws IOException {

		File tmpFolder = new File(this.getTempFolder().getAbsolutePath() + "/case_SVN1");
		if (!tmpFolder.exists() && !tmpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + tmpFolder + "'. ");
		}
		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(tmpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		File svnDir = new File(tmpFolder.getCanonicalPath() + "/.svn");
		if (!svnDir.exists() && !svnDir.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + svnDir + "'. ");
		}
		getFixture().importCorpusStructure(sCorpusGraph);

		assertEquals("sNodes: " + sCorpusGraph.getNodes(), 1, sCorpusGraph.getNodes().size());
		assertEquals(0, sCorpusGraph.getRelations().size());
	}

	/**
	 * Checks, that .svn file are ignored.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testImportCorpusStructure_SVN2() throws IOException {
		File tmpFolder = new File(this.getTempFolder().getAbsolutePath() + "/case_SVN2");
		if (!tmpFolder.exists() && !tmpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + tmpFolder + "'. ");
		}
		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(tmpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		File corpDir = new File(tmpFolder.getCanonicalPath() + "/corp1");
		if (!corpDir.exists() && !corpDir.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corpDir + "'. ");
		}
		File svnDir = new File(tmpFolder.getCanonicalPath() + "/corp1/.svn");
		if (!svnDir.exists() && !svnDir.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + svnDir + "'. ");
		}
		sCorpusGraph = SaltFactory.createSCorpusGraph();
		getFixture().importCorpusStructure(sCorpusGraph);

		assertEquals(2, sCorpusGraph.getNodes().size());
		assertEquals(2, sCorpusGraph.getCorpora().size());
		assertEquals(1, sCorpusGraph.getRelations().size());
	}

	/**
	 * Checks, that name of {@link SCorpusGraph} is set correctly.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testImportCorpusStructure_CORP_GRAPH_NAME() throws IOException {
		File tmpFolder = new File(this.getTempFolder().getAbsolutePath() + "/case_CORP_GRAPH_NAME");
		if (!tmpFolder.exists() && !tmpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + tmpFolder + "'. ");
		}
		File corpFolder = new File(tmpFolder.getCanonicalPath() + "/corp1");
		if (!corpFolder.exists() && !corpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corpFolder + "'. ");
		}

		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals("corp1", sCorpusGraph.getName());

		File corp2Dir = new File(tmpFolder.getCanonicalPath() + "/corp1/corp2/");
		if (!corp2Dir.exists() && !corp2Dir.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corp2Dir + "'. ");
		}
		sCorpusGraph = SaltFactory.createSCorpusGraph();
		getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals("corp1", sCorpusGraph.getName());
	}

	/**
	 * Checks, that the structure is imported correctly:
	 * 
	 * <pre>
	 * |-corp1
	 *   |-doc1.xml
	 *   |-doc2.xml
	 * </pre>
	 * 
	 * @throws IOException
	 */
	@Test
	public void testImportCorpusStructure_STRUCTURE1() throws IOException {
		File tmpFolder = new File(this.getTempFolder().getAbsolutePath() + "/case_STRUCTURE1");
		if (!tmpFolder.exists() && !tmpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + tmpFolder + "'. ");
		}
		File corpFolder = new File(tmpFolder.getCanonicalPath() + "/corp1");
		if (!corpFolder.exists() && !corpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corpFolder + "'. ");
		}

		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		File.createTempFile("doc1", "." + PepperImporter.ENDING_XML, corpFolder).deleteOnExit();
		File.createTempFile("doc2", "." + PepperImporter.ENDING_XML, corpFolder).deleteOnExit();
		getFixture().getDocumentEndings().add(PepperImporter.ENDING_XML);

		getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals(3, sCorpusGraph.getNodes().size());
		assertEquals(2, sCorpusGraph.getRelations().size());
		assertEquals(1, sCorpusGraph.getCorpora().size());
		assertEquals(2, sCorpusGraph.getDocuments().size());

	}

	/**
	 * Checks, that the structure is imported correctly:
	 * 
	 * <pre>
	 * |-corp1
	 *   |-corp2
	 *     |-doc1.xml
	 *     |-doc2.xml
	 *   |-corp3
	 *     |-doc1.xml
	 *     |-doc2.xml
	 * </pre>
	 * 
	 * @throws IOException
	 */
	@Test
	public void testImportCorpusStructure_STRUCTURE2() throws IOException {
		File tmpFolder = new File(this.getTempFolder().getAbsolutePath() + "/case_STRUCTURE2");
		if (!tmpFolder.exists() && !tmpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + tmpFolder + "'. ");
		}
		File corpFolder = new File(tmpFolder.getCanonicalPath() + "/corp1");
		if (!corpFolder.exists() && !corpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corpFolder + "'. ");
		}

		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		File corp2 = new File(tmpFolder.getCanonicalPath() + "/corp1/corp2");
		if (!corp2.exists() && !corp2.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corp2 + "'. ");
		}
		File corp3 = new File(tmpFolder.getCanonicalPath() + "/corp1/corp3");
		if (!corp3.exists() && !corp3.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corp3 + "'. ");
		}

		File.createTempFile("doc1", "." + PepperImporter.ENDING_XML, corp2).deleteOnExit();
		File.createTempFile("doc2", "." + PepperImporter.ENDING_XML, corp2).deleteOnExit();
		File.createTempFile("doc1", "." + PepperImporter.ENDING_XML, corp3).deleteOnExit();
		File.createTempFile("doc2", "." + PepperImporter.ENDING_XML, corp3).deleteOnExit();
		getFixture().getDocumentEndings().add(PepperImporter.ENDING_XML);

		getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals(7, sCorpusGraph.getNodes().size());
		assertEquals(6, sCorpusGraph.getRelations().size());
		assertEquals(2, sCorpusGraph.getCorpusRelations().size());
		assertEquals(4, sCorpusGraph.getCorpusDocumentRelations().size());
		assertEquals(3, sCorpusGraph.getCorpora().size());
		assertEquals(4, sCorpusGraph.getDocuments().size());
	}

	/**
	 * Checks, that the structure is imported correctly:
	 * 
	 * <pre>
	 * |-corp1
	 *   |-corp2
	 *     |-doc1
	 *     |-doc2
	 *   |-corp3
	 *     |-doc1
	 *     |-doc2
	 * </pre>
	 * 
	 * @throws IOException
	 */
	@Test
	public void testImportCorpusStructure_STRUCTURE3() throws IOException {
		File tmpFolder = new File(this.getTempFolder().getAbsolutePath() + "/case_STRUCTURE3");
		if (!tmpFolder.exists() && !tmpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + tmpFolder + "'. ");
		}
		File corpFolder = new File(tmpFolder.getCanonicalPath() + "/corp1");
		if (!corpFolder.exists() && !corpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corpFolder + "'. ");
		}

		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		File corp2 = new File(tmpFolder.getCanonicalPath() + "/corp1/corp2");
		if (!corp2.exists() && !corp2.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corp2 + "'. ");
		}
		File corp3 = new File(tmpFolder.getCanonicalPath() + "/corp1/corp3");
		if (!corp3.exists() && !corp3.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corp3 + "'. ");
		}
		File doc21 = new File(corp2 + "/doc1");
		if (!doc21.exists() && !doc21.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + doc21 + "'. ");
		}
		File doc22 = new File(corp2 + "/doc2");
		if (!doc22.exists() && !doc22.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + doc22 + "'. ");
		}
		File doc31 = new File(corp3 + "/doc1");
		if (!doc31.exists() && !doc31.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + doc31 + "'. ");
		}
		File doc32 = new File(corp3 + "/doc2");
		if (!doc32.exists() && !doc32.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + doc32 + "'. ");
		}
		getFixture().getDocumentEndings().add(PepperImporter.ENDING_LEAF_FOLDER);

		getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals(7, sCorpusGraph.getNodes().size());
		assertEquals(6, sCorpusGraph.getRelations().size());
		assertEquals(2, sCorpusGraph.getCorpusRelations().size());
		assertEquals(4, sCorpusGraph.getCorpusDocumentRelations().size());
		assertEquals(3, sCorpusGraph.getCorpora().size());
		assertEquals(4, sCorpusGraph.getDocuments().size());
	}

	/**
	 * Checks, that the structure is imported correctly (one folder contains a
	 * '.'):
	 * 
	 * <pre>
	 * |-corp1
	 *   |-corp2.1
	 *     |-doc1.xml
	 *     |-doc2.xml
	 * </pre>
	 * 
	 * @throws IOException
	 */
	@Test
	public void testImportCorpusStructure_STRUCTURE4() throws IOException {
		File tmpFolder = new File(this.getTempFolder().getAbsolutePath() + "/case_STRUCTURE4");
		if (!tmpFolder.exists() && !tmpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + tmpFolder + "'. ");
		}
		File corpFolder = new File(tmpFolder.getCanonicalPath() + "/corp1");
		if (!corpFolder.exists() && !corpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corpFolder + "'. ");
		}

		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		File corp2 = new File(tmpFolder.getCanonicalPath() + "/corp1/corp2.1");
		if (!corp2.exists() && !corp2.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + corp2 + "'. ");
		}

		File.createTempFile("doc1", "." + PepperImporter.ENDING_XML, corp2).deleteOnExit();
		File.createTempFile("doc2", "." + PepperImporter.ENDING_XML, corp2).deleteOnExit();
		getFixture().getDocumentEndings().add(PepperImporter.ENDING_XML);

		getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals(4, sCorpusGraph.getNodes().size());
		assertEquals(3, sCorpusGraph.getRelations().size());
		assertEquals(1, sCorpusGraph.getCorpusRelations().size());
		assertEquals(2, sCorpusGraph.getCorpusDocumentRelations().size());
		assertEquals(2, sCorpusGraph.getCorpora().size());
		assertEquals(2, sCorpusGraph.getDocuments().size());
	}

	/**
	 * Checks, that the structure is imported correctly, even if no corpus
	 * folder exists. Endings set to {@link PepperImporter#ENDING_LEAF_FOLDER}.
	 * 
	 * <pre>
	 * |-doc1
	 *   |-text.xml
	 *   |-token.xml
	 * </pre>
	 * 
	 * @throws IOException
	 */
	@Test
	public void testImportCorpusStructure_STRUCTURE5() throws IOException {
		File tmpFolder = new File(this.getTempFolder().getAbsolutePath() + "/case_STRUCTURE5");
		if (!tmpFolder.exists() && !tmpFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + tmpFolder + "'. ");
		}
		File docFolder = new File(tmpFolder.getCanonicalPath() + "/doc1");
		if (!docFolder.exists() && !docFolder.mkdirs()) {
			throw new PepperTestException("Cannot create folder '" + docFolder + "'. ");
		}
		File.createTempFile("text", "." + PepperImporter.ENDING_XML, docFolder).deleteOnExit();
		File.createTempFile("token", "." + PepperImporter.ENDING_XML, docFolder).deleteOnExit();

		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(docFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		getFixture().getDocumentEndings().add(PepperImporter.ENDING_LEAF_FOLDER);
		getFixture().importCorpusStructure(sCorpusGraph);

		assertNotNull(sCorpusGraph);
		assertNotNull(sCorpusGraph.getCorpora());
		assertEquals(1, sCorpusGraph.getCorpora().size());
		assertNotNull(sCorpusGraph.getDocuments());
		assertEquals(1, sCorpusGraph.getDocuments().size());
	}
}

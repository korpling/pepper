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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperImporterImpl;
import de.hu_berlin.u.saltnpepper.salt.SaltFactory;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpusGraph;

public class PepperImporterImplTest {

	class FixtureImporter extends PepperImporterImpl {

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
		tmpFolder.mkdirs();
		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(tmpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		new File(tmpFolder.getCanonicalPath() + "/.svn").mkdirs();
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
		tmpFolder.mkdirs();
		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(tmpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		new File(tmpFolder.getCanonicalPath() + "/corp1").mkdirs();
		new File(tmpFolder.getCanonicalPath() + "/corp1/.svn").mkdirs();
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
		tmpFolder.mkdirs();
		File corpFolder = new File(tmpFolder.getCanonicalPath() + "/corp1");
		corpFolder.mkdirs();

		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals("corp1", sCorpusGraph.getName());

		new File(tmpFolder.getCanonicalPath() + "/corp1/corp2/").mkdirs();
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
		tmpFolder.mkdirs();
		File corpFolder = new File(tmpFolder.getCanonicalPath() + "/corp1");
		corpFolder.mkdirs();

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
		tmpFolder.mkdirs();
		File corpFolder = new File(tmpFolder.getCanonicalPath() + "/corp1");
		corpFolder.mkdirs();

		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		File corp2 = new File(tmpFolder.getCanonicalPath() + "/corp1/corp2");
		corp2.mkdirs();
		File corp3 = new File(tmpFolder.getCanonicalPath() + "/corp1/corp3");
		corp3.mkdirs();

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
		tmpFolder.mkdirs();
		File corpFolder = new File(tmpFolder.getCanonicalPath() + "/corp1");
		corpFolder.mkdirs();

		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		File corp2 = new File(tmpFolder.getCanonicalPath() + "/corp1/corp2");
		corp2.mkdirs();
		File corp3 = new File(tmpFolder.getCanonicalPath() + "/corp1/corp3");
		corp3.mkdirs();
		new File(corp2 + "/doc1").mkdirs();
		new File(corp2 + "/doc2").mkdirs();
		new File(corp3 + "/doc1").mkdirs();
		new File(corp3 + "/doc2").mkdirs();
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
		tmpFolder.mkdirs();
		File corpFolder = new File(tmpFolder.getCanonicalPath() + "/corp1");
		corpFolder.mkdirs();

		CorpusDesc corpDef = new CorpusDesc();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		getFixture().setCorpusDesc(corpDef);

		File corp2 = new File(tmpFolder.getCanonicalPath() + "/corp1/corp2.1");
		corp2.mkdirs();

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
		tmpFolder.mkdirs();
		File docFolder = new File(tmpFolder.getCanonicalPath() + "/doc1");
		docFolder.mkdirs();
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

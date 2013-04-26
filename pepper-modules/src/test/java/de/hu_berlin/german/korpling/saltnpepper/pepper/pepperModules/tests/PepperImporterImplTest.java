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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.MAPPING_RESULT;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapperController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

public class PepperImporterImplTest extends TestCase{

	class FixtureImporter extends PepperImporterImpl
	{
		
	}
	
	private FixtureImporter fixture= null;

	public void setFixture(FixtureImporter fixture) {
		this.fixture = fixture;
	}

	public FixtureImporter getFixture() {
		return fixture;
	}
	
	public void setUp()
	{
		this.setFixture(new FixtureImporter());
//		this.getFixture().setSCorpusGraph(SaltFactory.eINSTANCE.createSCorpusGraph());
		sCorpusGraph= SaltFactory.eINSTANCE.createSCorpusGraph();
	}
	
	public void tearDown()
	{
		this.getTempFolder().deleteOnExit();
	}
	
	private SCorpusGraph sCorpusGraph= null;
	private File getTempFolder()
	{
		return (new File(System.getProperty("java.io.tmpdir")+"/pepperImporterTests"));
	}
	
	/**
	 * Checks, that .svn file are ignored.
	 * @throws IOException 
	 */
	public void testImportCorpusStructure_SVN1() throws IOException
	{
		
		File tmpFolder= new File(this.getTempFolder().getAbsolutePath()+"/case_SVN1");
		tmpFolder.mkdirs();
		CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setCorpusPath(URI.createFileURI(tmpFolder.getCanonicalPath()));
		this.getFixture().setCorpusDefinition(corpDef);
		
		new File(tmpFolder.getCanonicalPath()+"/.svn").mkdirs();
		this.getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals("sNodes: "+ sCorpusGraph.getSNodes(), 1, sCorpusGraph.getSNodes().size());
		assertEquals(0, sCorpusGraph.getSRelations().size());
	}
	
	/**
	 * Checks, that .svn file are ignored.
	 * @throws IOException 
	 */
	public void testImportCorpusStructure_SVN2() throws IOException
	{
		File tmpFolder= new File(this.getTempFolder().getAbsolutePath()+"/case_SVN2");
		tmpFolder.mkdirs();
		CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setCorpusPath(URI.createFileURI(tmpFolder.getCanonicalPath()));
		this.getFixture().setCorpusDefinition(corpDef);
		
		new File(tmpFolder.getCanonicalPath()+"/corp1").mkdirs();
		new File(tmpFolder.getCanonicalPath()+"/corp1/.svn").mkdirs();
		sCorpusGraph= SaltFactory.eINSTANCE.createSCorpusGraph();
		this.getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals(2, sCorpusGraph.getSNodes().size());
		assertEquals(2, sCorpusGraph.getSCorpora().size());
		assertEquals(1, sCorpusGraph.getSRelations().size());
	}
	
	/**
	 * Checks, that name of {@link SCorpusGraph} is set correctly.
	 * @throws IOException 
	 */
	public void testImportCorpusStructure_CORP_GRAPH_NAME() throws IOException
	{
		File tmpFolder= new File(this.getTempFolder().getAbsolutePath()+"/case_CORP_GRAPH_NAME");
		tmpFolder.mkdirs();
		File corpFolder= new File(tmpFolder.getCanonicalPath()+"/corp1");
		corpFolder.mkdirs();
		
		CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		this.getFixture().setCorpusDefinition(corpDef);
		
		this.getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals("corp1", sCorpusGraph.getSName());
		
		new File(tmpFolder.getCanonicalPath()+"/corp1/corp2/").mkdirs();
		sCorpusGraph= SaltFactory.eINSTANCE.createSCorpusGraph();
		this.getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals("corp1", sCorpusGraph.getSName());
	}
	
	/**
	 * Checks, that the structure is imported correctly:
	 * <pre>
	 * |-corp1
	 *   |-doc1.xml
	 *   |-doc2.xml
	 * </pre>
	 * @throws IOException 
	 */
	public void testImportCorpusStructure_STRUCTURE1() throws IOException
	{
		File tmpFolder= new File(this.getTempFolder().getAbsolutePath()+"/case_STRUCTURE1");
		tmpFolder.mkdirs();
		File corpFolder= new File(tmpFolder.getCanonicalPath()+"/corp1");
		corpFolder.mkdirs();
		
		CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		this.getFixture().setCorpusDefinition(corpDef);
		
		File.createTempFile("doc1", "."+PepperImporter.ENDING_XML, corpFolder).deleteOnExit();
		File.createTempFile("doc2", "."+PepperImporter.ENDING_XML, corpFolder).deleteOnExit();
		this.getFixture().getSDocumentEndings().add(PepperImporter.ENDING_XML);
		
		this.getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals(3, sCorpusGraph.getSNodes().size());
		assertEquals(2, sCorpusGraph.getSRelations().size());
		assertEquals(1, sCorpusGraph.getSCorpora().size());
		assertEquals(2, sCorpusGraph.getSDocuments().size());

	}
	
	/**
	 * Checks, that the structure is imported correctly:
	 * <pre>
	 * |-corp1
	 *   |-corp2
	 *     |-doc1.xml
	 *     |-doc2.xml
	 *   |-corp3
	 *     |-doc1.xml
	 *     |-doc2.xml
	 * </pre>
	 * @throws IOException 
	 */
	public void testImportCorpusStructure_STRUCTURE2() throws IOException
	{
		File tmpFolder= new File(this.getTempFolder().getAbsolutePath()+"/case_STRUCTURE2");
		tmpFolder.mkdirs();
		File corpFolder= new File(tmpFolder.getCanonicalPath()+"/corp1");
		corpFolder.mkdirs();
		
		CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		this.getFixture().setCorpusDefinition(corpDef);
		
		File corp2= new File(tmpFolder.getCanonicalPath()+"/corp1/corp2");
		corp2.mkdirs();
		File corp3= new File(tmpFolder.getCanonicalPath()+"/corp1/corp3");
		corp3.mkdirs();
		
		File.createTempFile("doc1", "."+PepperImporter.ENDING_XML, corp2).deleteOnExit();
		File.createTempFile("doc2", "."+PepperImporter.ENDING_XML, corp2).deleteOnExit();
		File.createTempFile("doc1", "."+PepperImporter.ENDING_XML, corp3).deleteOnExit();
		File.createTempFile("doc2", "."+PepperImporter.ENDING_XML, corp3).deleteOnExit();
		this.getFixture().getSDocumentEndings().add(PepperImporter.ENDING_XML);
		
		this.getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals(7, sCorpusGraph.getSNodes().size());
		assertEquals(6, sCorpusGraph.getSRelations().size());
		assertEquals(2, sCorpusGraph.getSCorpusRelations().size());
		assertEquals(4, sCorpusGraph.getSCorpusDocumentRelations().size());
		assertEquals(3, sCorpusGraph.getSCorpora().size());
		assertEquals(4, sCorpusGraph.getSDocuments().size());
	}
	
	/**
	 * Checks, that the structure is imported correctly:
	 * <pre>
	 * |-corp1
	 *   |-corp2
	 *     |-doc1
	 *     |-doc2
	 *   |-corp3
	 *     |-doc1
	 *     |-doc2
	 * </pre>
	 * @throws IOException 
	 */
	public void testImportCorpusStructure_STRUCTURE3() throws IOException
	{
		File tmpFolder= new File(this.getTempFolder().getAbsolutePath()+"/case_STRUCTURE3");
		tmpFolder.mkdirs();
		File corpFolder= new File(tmpFolder.getCanonicalPath()+"/corp1");
		corpFolder.mkdirs();
		
		CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		this.getFixture().setCorpusDefinition(corpDef);
		
		File corp2= new File(tmpFolder.getCanonicalPath()+"/corp1/corp2");
		corp2.mkdirs();
		File corp3= new File(tmpFolder.getCanonicalPath()+"/corp1/corp3");
		corp3.mkdirs();
		new File(corp2+"/doc1").mkdirs();
		new File(corp2+"/doc2").mkdirs();
		new File(corp3+"/doc1").mkdirs();
		new File(corp3+"/doc2").mkdirs();
		this.getFixture().getSDocumentEndings().add(PepperImporter.ENDING_LEAF_FOLDER);
		
		this.getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals(7, sCorpusGraph.getSNodes().size());
		assertEquals(6, sCorpusGraph.getSRelations().size());
		assertEquals(2, sCorpusGraph.getSCorpusRelations().size());
		assertEquals(4, sCorpusGraph.getSCorpusDocumentRelations().size());
		assertEquals(3, sCorpusGraph.getSCorpora().size());
		assertEquals(4, sCorpusGraph.getSDocuments().size());
	}
	
	/**
	 * Checks, that the structure is imported correctly (one folder contains a '.'):
	 * <pre>
	 * |-corp1
	 *   |-corp2.1
	 *     |-doc1.xml
	 *     |-doc2.xml
	 * </pre>
	 * @throws IOException 
	 */
	public void testImportCorpusStructure_STRUCTURE4() throws IOException
	{
		File tmpFolder= new File(this.getTempFolder().getAbsolutePath()+"/case_STRUCTURE4");
		tmpFolder.mkdirs();
		File corpFolder= new File(tmpFolder.getCanonicalPath()+"/corp1");
		corpFolder.mkdirs();
		
		CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setCorpusPath(URI.createFileURI(corpFolder.getCanonicalPath()));
		this.getFixture().setCorpusDefinition(corpDef);
		
		File corp2= new File(tmpFolder.getCanonicalPath()+"/corp1/corp2.1");
		corp2.mkdirs();
		
		File.createTempFile("doc1", "."+PepperImporter.ENDING_XML, corp2).deleteOnExit();
		File.createTempFile("doc2", "."+PepperImporter.ENDING_XML, corp2).deleteOnExit();
		this.getFixture().getSDocumentEndings().add(PepperImporter.ENDING_XML);
		
		this.getFixture().importCorpusStructure(sCorpusGraph);
		assertEquals(4, sCorpusGraph.getSNodes().size());
		assertEquals(3, sCorpusGraph.getSRelations().size());
		assertEquals(1, sCorpusGraph.getSCorpusRelations().size());
		assertEquals(2, sCorpusGraph.getSCorpusDocumentRelations().size());
		assertEquals(2, sCorpusGraph.getSCorpora().size());
		assertEquals(2, sCorpusGraph.getSDocuments().size());
	}
}

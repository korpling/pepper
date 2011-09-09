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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.JobCases;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.JobCases.PepperJobDelegatorTest.ImporterGraphPairDelegator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Pepper Convert Job</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start() <em>Start</em>}</li>
 * </ul>
 * </p>
 */
public class ImportCorpusStructureTest extends TestCase 
{
	/**
	 * The fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	protected PepperJobDelegatorTest fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	public static void main(String[] args) {
		TestRunner.run(ImportCorpusStructureTest.class);
	}

	/**
	 * Constructs a new Pepper Job test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	public ImportCorpusStructureTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	protected void setFixture(PepperJobDelegatorTest fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	protected PepperJobDelegatorTest getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(new PepperJobDelegatorTest());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 *
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}
	
	/**
	 * starts a runner thread and checks, that if test needs longer than given max time, 
	 * it will fail.
	 * @param MAX_WAITING_TIME
	 * @param runner
	 * @throws Exception
	 */
	private void startRunner(long MAX_WAITING_TIME, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.FinishableRunner runner) throws Exception
	{
		runner.setFixture(this.getFixture());
		Thread runnerThread= new Thread(runner, "Runner");
		runnerThread.start();
		
		boolean maxTimeReached= false;
		long t1 = System.currentTimeMillis();
		while((!runner.isFinished()) && (!maxTimeReached))
		{
			if (runner.exceptions!= null)
				for (Exception ex: runner.exceptions)
					throw ex;
			Thread.sleep(100);
			if ((System.currentTimeMillis() - t1) > MAX_WAITING_TIME)
			{
				maxTimeReached= true;
			}
		}
		assertFalse("TIMEOUT while testing...", maxTimeReached);
	}
	
	private class TestImporter extends PepperImporterImpl
	{
		{
			this.name= "importer1";
		}
		public EList<SElementId> elementIds= new BasicEList<SElementId>();
		@SuppressWarnings("unused")
		public SCorpusGraph corpusGraph= null;
		@Override
		public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException 
		{
			this.corpusGraph= corpusGraph;
			//create the following corpus structure
			//	corpgraph
			//	|
			//	|- doc1 .. doc10
			for (int i= 0; i < 10; i++)
			{	
				SDocument sDocument= SaltCommonFactory.eINSTANCE.createSDocument();
				SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
				sElementId.setSId("id"+ i);
				elementIds.add(sElementId);
				sDocument.setSElementId(sElementId);
				corpusGraph.addSNode(sDocument);
			}
		}
	}
	
	/**
	 * checks if corpusStructure is correctly imported and pairs of graph and 
	 * importer are correctly created.
	 *
	 */
	public void testCorpusStructure()
	{
		SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
		this.getFixture().setSaltProject(saltProject);
		TestImporter importer= new TestImporter();
		
		this.getFixture().getPepperImporters().add(importer);
		EList<ImporterGraphPairDelegator> igPairList= this.getFixture().importCorpusStructureTest();
		if (igPairList.size()!= 1)
			throw new RuntimeException("There are more than one corpus-graphs in project");
		boolean importerFound= false;
		for (ImporterGraphPairDelegator pair: igPairList)
		{
			if (pair.getImporter().equals(importer))
			{
				importerFound= true;
				for (SElementId elementId: importer.elementIds)
				{
					boolean idInGraph= false;
					for (SDocument document: pair.getSCorpusGraph().getSDocuments())
					{
						if (document.getSElementId().equals(elementId))
						{
							idInGraph= true;
							break;
						}
					}	
					if (!idInGraph)
						throw new RuntimeException("One id is not in corpus-graph: "+elementId);
				}	
				importer.elementIds.containsAll(pair.getSCorpusGraph().getSDocuments());
			}	
		}	
		if (!importerFound)
			throw new RuntimeException("One importer is not in pair-list: "+importer.getName());
		
	}
	
	/**
	 * checks if corpusStructure is correctly imported and pairs of graph and 
	 * importer are correctly created. Number of importers= 2
	 *
	 */
	public void testCorpusStructureWith2Importers()
	{
		SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
		this.getFixture().setSaltProject(saltProject);
		
		EList<TestImporter> importers= new BasicEList<TestImporter>();
		
		TestImporter importer1= new TestImporter();
		importers.add(importer1);
		this.getFixture().getPepperImporters().add(importer1);
		
		TestImporter importer2= new TestImporter();
		importers.add(importer2);
		this.getFixture().getPepperImporters().add(importer2);
		
		EList<ImporterGraphPairDelegator> igPairList= this.getFixture().importCorpusStructureTest();
		if (igPairList.size()!= 2)
			throw new RuntimeException("There are more than one corpus-graphs in project");
		for (ImporterGraphPairDelegator pair: igPairList)
		{
			boolean importerFound= false;
			for (TestImporter importer: importers)
			{	
				if (pair.getImporter().equals(importer))
				{
					importerFound= true;
					for (SElementId elementId: importer.elementIds)
					{
						boolean idInGraph= false;
						for (SDocument document: pair.getSCorpusGraph().getSDocuments())
						{
							if (document.getSElementId().equals(elementId))
							{
								idInGraph= true;
								break;
							}
						}	
						if (!idInGraph)
							throw new RuntimeException("One id is not in corpus-graph: "+elementId);
					}	
					importer.elementIds.containsAll(pair.getSCorpusGraph().getSDocuments());
					break;
				}
			}
			if (!importerFound)
				throw new RuntimeException("One importer is not in pair-list: "+pair.getImporter().getName());
		}	
	}
	
	/**
	 * checks if corpusStructure is correctly imported and pairs of graph and 
	 * importer are correctly created.
	 *
	 */
	public void testNoImporters() throws Exception
	{
		class Runner extends de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				((PepperJobDelegatorTest)this.getFixture()).importCorpusStructureTest();
				this.finished= true;
			}
		}
		try
		{
			this.startRunner(5000l, new Runner());
			fail("no importers given");
		}
		catch (PepperFWException e)
		{}
	}
} //PepperConvertJobTest

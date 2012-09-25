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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperManipulatorImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

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
 * @generated
 */
public class PepperJobTest extends TestCase {
	@SuppressWarnings("unused")
	private Logger logger= Logger.getLogger(PepperJobTest.class);
	{
		PropertyConfigurator.configureAndWatch("settings/log4j.properties", 60*1000 );
	}
	
	/**
	 * The fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperJob fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PepperJobTest.class);
	}

	/**
	 * Constructs a new Pepper Job test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperJobTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PepperJob fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperJob getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(PepperFWFactory.eINSTANCE.createPepperJob());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	public void testAddGetPepperImporterTest()
	{
		EList<TestImporter1> importers= new BasicEList<TestImporter1>();
		importers.add(new TestImporter1());
		//adding all importers
		for (TestImporter1 importer: importers)
			this.getFixture().getPepperImporters().add(importer);
		//test getting all importers
		assertTrue(importers.containsAll(this.getFixture().getPepperImporters()));
		assertTrue(this.getFixture().getPepperImporters().containsAll(importers));
	}
	
	public void testAddGetPepperExporterTest()
	{
		EList<TestExporter1> exporters= new BasicEList<TestExporter1>();
		exporters.add(new TestExporter1());
		//adding all exporters
		for (TestExporter1 exporter: exporters)
			this.getFixture().getPepperExporters().add(exporter);
		//test getting all exporters
		assertTrue(exporters.containsAll(this.getFixture().getPepperExporters()));
		assertTrue(this.getFixture().getPepperExporters().containsAll(exporters));
	}
	
	public void testAddGetPepperModulesTest()
	{
		EList<TestModule1> modules= new BasicEList<TestModule1>();
		modules.add(new TestModule1());
		//adding all modules
		for (TestModule1 module: modules)
			this.getFixture().getPepperModules().add(module);
		//test getting all modules
		assertTrue(modules.containsAll(this.getFixture().getPepperModules()));
		assertTrue(this.getFixture().getPepperModules().containsAll(modules));
	}
	
	/**
	 * Tests setting and getting document controller.
	 */
	public void testSetPepperDocumentController()
	{
		PepperDocumentController docController= PepperFWFactory.eINSTANCE.createPepperDocumentController();
		this.getFixture().setPepperDocumentController(docController);
		assertEquals(docController, this.getFixture().getPepperDocumentController());
	}
	
	/**
	 * Tests if the document controller is pre set and if it can be overriden. 
	 */
	public void testGetPepperDocumentController()
	{
		assertNotNull(this.getFixture().getPepperDocumentController());		
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start() <em>Start</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	public void testStart() 
	{
		//methode created by EMF, but not necassary, look at package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.JobCases for tests
	}
	
	private class TestImporter1 extends PepperImporterImpl
	{
		{ this.name= "TestImporter1"; }
		public EList<SElementId> elementIds= null;
		
		@Override
		public void importCorpusStructure(SCorpusGraph corpusGraph)
		{
			for (SElementId sElementId: elementIds)
			{
				//copying element id, because it will be destroyed if creator of elementIds list is destroyed
				SElementId sElementId2= SaltCommonFactory.eINSTANCE.createSElementId();
				sElementId2.setSId(sElementId.getSId());
				
				SDocument document= SaltCommonFactory.eINSTANCE.createSDocument();
				document.setSElementId(sElementId2);
				corpusGraph.addSNode(document);
			}	
			
		} 
		
		@Override
		public void start(SElementId sElementId) 
		{
			SElementId removeSElementId= null;
			for (SElementId sElementId2: elementIds)
				if (sElementId2.getSId().equalsIgnoreCase(sElementId.getSId()))
				{	
					removeSElementId= sElementId2;
					break;
				}
			this.elementIds.remove(removeSElementId);
		}
	}
	
	private class TestModule1 extends PepperManipulatorImpl
	{
		{ this.name= "TestModule1"; }
		EList<SElementId> elementIds= null;
		
		@Override
		public void start(SElementId sElementId) 
		{
			this.elementIds.remove(sElementId);
		}
		
		@Override
		public void setSaltProject(SaltProject saltProject)
		{
			super.setSaltProject(saltProject);
		}
	}
	
	private class TestExporter1 extends PepperExporterImpl
	{
		{ this.name= "TestExporter1"; }
		EList<SElementId> elementIds= null;
		
		@Override
		public void start(SElementId sElementId) 
		{
			this.elementIds.remove(sElementId);
		}
	}
	
	/**
	 * Tests if a job passes all documents through steps.
	 * 
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	//TODO fixme
//	public void testStartOneImporterOneExporter() throws Exception
//	{
//		class Runner extends FinishableRunner 
//		{			
//			@Override
//			public void myRunning()
//			{
//				//generate a list of element-ids, all module has to see
//				EList<SElementId> elementIds= new BasicEList<SElementId>();
//				for (int i= 0; i < 10; i++)
//				{
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					elementIds.add(sElementId);
//				}	
//				
//				URI uri= null;
//				uri= URI.createFileURI("./data/pepperJob/testCorpus1");
//				
//				CorpusDefinition corpDef= null;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				
//				//init job
//				SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
//				((PepperJob)this.getFixture()).setSaltProject(saltProject);
//				((PepperJob)this.getFixture()).setId(0);
//				
//				//init importer
//				TestImporter1 importer1= new TestImporter1();
//				importer1.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				importer1.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperImporters().add(importer1);
//				
//				//init exporter
//				TestExporter1 exporter1= new TestExporter1();
//				exporter1.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				exporter1.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperExporters().add(exporter1);
//				
//				PepperFinishableMonitor jobMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
//				((PepperJob)this.getFixture()).setPepperJ2CMonitor(jobMonitor);
//				((PepperJob)this.getFixture()).start();
//				
//				//checking if all element-ids were seen
//				assertEquals(0, importer1.elementIds.size());
//				assertEquals(0, exporter1.elementIds.size());
//				
//				this.finished= true;
//			}
//		}
//		FinishableRunner.startRunner(5000l, new Runner(), this.getFixture());
//	}
	
	/**
	 * Tests if a job passes all documents through steps.
	 * 
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	//TODO fixme
//	public void testStartOneOfAll() throws Exception
//	{
//		class Runner extends FinishableRunner 
//		{			
//			@Override
//			public void myRunning()
//			{
//				//generate a list of element-ids, all module has to see
//				EList<SElementId> elementIds= new BasicEList<SElementId>();
//				for (int i= 0; i < 10; i++)
//				{
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					elementIds.add(sElementId);
//				}	
//				
//				URI uri= null;
////				try {
////					uri= new URI("./data/pepperJob/testCorpus1");
//					uri= URI.createFileURI("./data/pepperJob/testCorpus1");
////				} catch (URISyntaxException e) {
////					throw new RuntimeException(e);
////				}
//				CorpusDefinition corpDef= null;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				
//				//init job
//				SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
//				((PepperJob)this.getFixture()).setSaltProject(saltProject);
//				((PepperJob)this.getFixture()).setId(0);
//				
//				//init importer
//				TestImporter1 importer1= new TestImporter1();
//				importer1.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				importer1.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperImporters().add(importer1);
//				
//				//init module
//				TestModule1 module1= new TestModule1();
//				module1.elementIds= elementIds;
//				((PepperJob)this.getFixture()).getPepperModules().add(module1);
//				
//				//init exporter
//				TestExporter1 exporter1= new TestExporter1();
//				exporter1.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				exporter1.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperExporters().add(exporter1);
//				PepperFinishableMonitor jobMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
//				((PepperJob)this.getFixture()).setPepperJ2CMonitor(jobMonitor);
//				((PepperJob)this.getFixture()).start();
//				
//				//checking if all element-ids were seen
//				assertEquals(0, importer1.elementIds.size());
//				assertEquals(0, module1.elementIds.size());
//				assertEquals(0, exporter1.elementIds.size());
//				
//				this.finished= true;
//			}
//		}
//		FinishableRunner.startRunner(7000l, new Runner(), this.getFixture());
//	}
	
	/**
	 * Tests if a job passes all documents through steps.
	 * 
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	//TODO fixme
//	public void testStartOneImporterMoreOfRest() throws Exception
//	{
//		class Runner extends FinishableRunner 
//		{			
//			@Override
//			public void myRunning()
//			{
//				//generate a list of element-ids, all module has to see
//				EList<SElementId> elementIds= new BasicEList<SElementId>();
//				for (int i= 0; i < 10; i++)
//				{
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					elementIds.add(sElementId);
//				}	
//				
//				URI uri= null;
////				try {
////					uri= new URI("./data/pepperJob/testCorpus1");
////				} catch (URISyntaxException e) {
////					throw new RuntimeException(e);
////				}
//				uri= URI.createFileURI("./data/pepperJob/testCorpus1");
//				CorpusDefinition corpDef= null;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				
//				//init job
//				SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
//				((PepperJob)this.getFixture()).setSaltProject(saltProject);
//				((PepperJob)this.getFixture()).setId(0);
//				
//				//init importer1
//				TestImporter1 importer1= new TestImporter1();
//				importer1.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				importer1.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperImporters().add(importer1);
//				
//				//init module
//				TestModule1 module1= new TestModule1();
//				module1.elementIds= elementIds;
//				((PepperJob)this.getFixture()).getPepperModules().add(module1);
//				
//				//init module
//				TestModule1 module2= new TestModule1();
//				module2.elementIds= elementIds;
//				((PepperJob)this.getFixture()).getPepperModules().add(module2);
//				
//				//init exporter
//				TestExporter1 exporter1= new TestExporter1();
//				exporter1.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				exporter1.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperExporters().add(exporter1);
//				
//				//init exporter
//				TestExporter1 exporter2= new TestExporter1();
//				exporter2.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				exporter2.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperExporters().add(exporter2);
//				PepperFinishableMonitor jobMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
//				((PepperJob)this.getFixture()).setPepperJ2CMonitor(jobMonitor);
//				((PepperJob)this.getFixture()).start();
//				
//				//checking if all element-ids were seen
//				assertEquals(0, importer1.elementIds.size());
////				assertEquals(0, importer2.elementIds.size());
//				assertEquals(0, module1.elementIds.size());
//				assertEquals(0, module2.elementIds.size());
//				assertEquals(0, exporter1.elementIds.size());
//				assertEquals(0, exporter2.elementIds.size());
//				
//				this.finished= true;
//			}
//		}
//		FinishableRunner.startRunner(5000l, new Runner(), this.getFixture());
//	}
	
	/**
	 * Tests if a job passes all documents through steps.
	 * 
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	//TODO fixme
//	public void testStartMoreOfAll() throws Exception
//	{
//		class Runner extends FinishableRunner 
//		{			
//			@Override
//			public void myRunning()
//			{
//				//generate a list of element-ids, all module has to see
//				EList<SElementId> elementIds= new BasicEList<SElementId>();
//				for (int i= 0; i < 10; i++)
//				{
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					elementIds.add(sElementId);
//				}	
//				
//				URI uri= null;
////				try {
////					uri= new URI("./data/pepperJob/testCorpus1");
////				} catch (URISyntaxException e) {
////					throw new RuntimeException(e);
////				}
//				uri= URI.createFileURI("./data/pepperJob/testCorpus1");
//				CorpusDefinition corpDef= null;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				
//				((PepperJob)this.getFixture()).getPepperDocumentController().setAMOUNT_OF_COMPUTABLE_SDOCUMENTS(-1);
//				
//				//init job
//				SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
//				((PepperJob)this.getFixture()).setSaltProject(saltProject);
//				((PepperJob)this.getFixture()).setId(0);
//				
//				//init importer1
//				TestImporter1 importer1= new TestImporter1();
//				importer1.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				importer1.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperImporters().add(importer1);
//				
//				//init importer2
//				TestImporter1 importer2= new TestImporter1();
//				importer2.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				importer2.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperImporters().add(importer2);
//				
//				//init module
//				TestModule1 module1= new TestModule1();
//				module1.elementIds= elementIds;
//				((PepperJob)this.getFixture()).getPepperModules().add(module1);
//				
//				//init module
//				TestModule1 module2= new TestModule1();
//				module2.elementIds= elementIds;
//				((PepperJob)this.getFixture()).getPepperModules().add(module2);
//				
//				//init exporter
//				TestExporter1 exporter1= new TestExporter1();
//				exporter1.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				exporter1.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperExporters().add(exporter1);
//				
//				//init exporter
//				TestExporter1 exporter2= new TestExporter1();
//				exporter2.elementIds= elementIds;
//				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//				corpDef.setCorpusPath(uri);
//				exporter2.setCorpusDefinition(corpDef);
//				((PepperJob)this.getFixture()).getPepperExporters().add(exporter2);
//				PepperFinishableMonitor jobMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
//				((PepperJob)this.getFixture()).setPepperJ2CMonitor(jobMonitor);
//				((PepperJob)this.getFixture()).start();
//				
//				//checking if all element-ids were seen
//				assertEquals(0, importer1.elementIds.size());
//				assertEquals(0, importer2.elementIds.size());
//				assertEquals(0, module1.elementIds.size());
//				assertEquals(0, module2.elementIds.size());
//				assertEquals(0, exporter1.elementIds.size());
//				assertEquals(0, exporter2.elementIds.size());
//				
//				this.finished= true;
//			}
//		}
//		FinishableRunner.startRunner(5000l, new Runner(), this.getFixture());
//	}
} //PepperConvertJobTest

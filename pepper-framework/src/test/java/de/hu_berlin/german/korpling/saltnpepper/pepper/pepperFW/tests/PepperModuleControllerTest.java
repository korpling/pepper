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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Pepper Module Controller</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#start() <em>Start</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#importCorpusStructure(de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph) <em>Import Corpus Structure</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperInterface.PepperModuleController#put(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId) <em>Put</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperInterface.PepperModuleController#get() <em>Get</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperInterface.PepperModuleController#finish(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId) <em>Finish</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class PepperModuleControllerTest extends TestCase {

	/**
	 * The fixture for this Pepper Module Controller test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperModuleController fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PepperModuleControllerTest.class);
	}

	/**
	 * Constructs a new Pepper Module Controller test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModuleControllerTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Pepper Module Controller test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PepperModuleController fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Pepper Module Controller test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperModuleController getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception 
	{
		setFixture(PepperFWFactory.eINSTANCE.createPepperModuleController());
		this.getFixture().setPepperDocumentController(PepperFWFactory.eINSTANCE.createPepperDocumentController());
		this.getFixture().getPepperDocumentController().setAMOUNT_OF_COMPUTABLE_SDOCUMENTS(-1);
		this.getFixture().getPepperDocumentController().setCOMPUTE_PERFORMANCE(false);
		this.getFixture().getPepperDocumentController().setREMOVE_SDOCUMENT_AFTER_PROCESSING(false);
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

	//============================================= start: nested classes =============================================
	/**
	 * a class which defines a runner, which has a finish method.
	 * @author Administrator
	 *
	 */
	private class FinishableRunner implements Runnable 
	{
		private PepperModuleController fixture= null;
		protected boolean finished= false;
		public EList<Exception> exceptions= new BasicEList<Exception>();
		
		public Boolean isFinished()
		{ return(this.finished); }
		
		public PepperModuleController getFixture()
		{return(this.fixture);}
		
		@SuppressWarnings("unused")
		public void setFixture(PepperModuleController fixture)
		{this.fixture= fixture;}
		
		@Override
		public void run() 
		{
			try {
				this.myRunning();
			} catch (PepperException e) 
			{
				this.exceptions.add(e);
			}
		}
		
		public void myRunning()
		{
			throw new UnsupportedOperationException("has to be overwriten");
		}
	}
	
	
	
	
	private class Getter implements Runnable 
	{
		private PepperQueuedMonitor fixture= null;
		private boolean finished= false;
		private Integer numOfGets= null;
		
		public boolean isFinished()
		{ return(this.finished); }
		
		private PepperQueuedMonitor getFixture()
		{
			return(this.fixture);
		}
		
		public void run() 
		{
			int allOrders= 0;
			for (int i= 0; i < numOfGets; i++)
			{	
//				System.out.println("(getter: ) getter waits...");
				SElementId sDocumentId= this.getFixture().get();
				SDocument sDoc= SaltCommonFactory.eINSTANCE.createSDocument();
				sDoc.setSElementId(sDocumentId);
				assertNotNull(sDocumentId);
//				System.out.println("(getter: ) element-id gettet: "+ sElementID);
				allOrders++;
			}
			this.getFixture().finish();
			if (!this.numOfGets.equals(allOrders))
				throw new NullPointerException("not all orders were gettet (expected: "+this.numOfGets+"/ gettet: "+allOrders+")");
			this.finished= true;
		}
		
	}
	
	
	private class Putter implements Runnable
	{
		private PepperQueuedMonitor fixture= null;
		private Integer numOfPuts= null;
		private boolean finished= false;
		private long sleepTime= 100;
		public PepperDocumentController pepperDocumentController= null;
		
		public void setSleepTime(long sleepTime)
		{ this.sleepTime= sleepTime; }
		
		public boolean isFinished()
		{ return(this.finished); }
		
		private PepperQueuedMonitor getFixture()
		{
			return(this.fixture);
		}
		@Override
		public void run() 
		{
			for (int i= 0; i < numOfPuts; i++)
			{	
				try {
					Thread.sleep(this.sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				SElementId sDocumentId= SaltCommonFactory.eINSTANCE.createSElementId();
				sDocumentId.setSId("id("+this.getFixture().getId()+")"+i);
				
				SDocument sDoc= SaltCommonFactory.eINSTANCE.createSDocument();
				sDoc.setSElementId(sDocumentId);
				
				if (pepperDocumentController!= null)
				{	
					pepperDocumentController.observeSDocument(sDocumentId);
				}
				
				this.getFixture().put(sDocumentId);	
			}
			this.getFixture().finish();
			this.finished= true;
		}
	}
	
	class MyModule extends PepperModuleImpl
	{
		public void start()
		{
			SElementId sElementId= null;
			boolean isStart= true;
			while ((isStart) || (sElementId!= null))
			{
				isStart= false;
				sElementId= this.getPepperModuleController().get();
				if (sElementId== null)
					break;
				this.getPepperModuleController().put(sElementId);
			}
		}
	}
//============================================= end: nested classes =============================================	
	/**
	 * starts a runner thread and checks, that if test needs longer than given max time, 
	 * it will fail.
	 * @param MAX_WAITING_TIME
	 * @param runner
	 * @throws Exception
	 */
	private void startRunner(long MAX_WAITING_TIME, FinishableRunner runner) throws Exception
	{
		runner.fixture= this.getFixture();
		Thread runnerThread= new Thread(runner);
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
	
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#start() <em>Start</em>}' operation.
	 * Sets an input-monitor, and an output-monitor, a module, which delegates all input
	 * directly into output. Than it tests, if all puttet orders in input-monitor will 
	 * reach the output-monitor.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#start()
	 */
	public void testStart() throws Exception
	{
		class Runner extends FinishableRunner
		{			
			@Override
			public void myRunning() 
			{
				Getter getter= new Getter();
				getter.numOfGets= 10;
				PepperQueuedMonitor m2mMonitor1= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
				getter.fixture= m2mMonitor1;
				Thread getterThread= new Thread(getter);
				getterThread.start();
				
				Putter putter= new Putter();
				putter.numOfPuts= 10;
				//setting document controller
				putter.pepperDocumentController= this.getFixture().getPepperDocumentController();
				PepperQueuedMonitor m2mMonitor2= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
				putter.fixture= m2mMonitor2;
				Thread putterThread= new Thread(putter);
				putterThread.start();
				
				//setting m2j monitor
				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
				this.getFixture().setPepperM2JMonitor(m2jMonitor);
				
				//setting module
				this.getFixture().setPepperModule(new MyModule());
				
				//setting im- and output monitors
				this.getFixture().getInputPepperModuleMonitors().add(m2mMonitor2);
				this.getFixture().getOutputPepperModuleMonitors().add(m2mMonitor1);				
				
				this.getFixture().start();
				
				while ((!putter.isFinished()) || (!getter.isFinished()))
				{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				m2jMonitor.waitUntilFinished();
				this.finished= true;
			}
		}
		
		this.startRunner(5000, new Runner());
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#importCorpusStructure(de.hu_berlin.german.korpling.saltnpepper.pepper.saltTest.saltFW.SCorpusGraph) <em>Import Corpus Structure</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#importCorpusStructure(de.hu_berlin.german.korpling.saltnpepper.pepper.saltTest.saltFW.SCorpusGraph)
	 */
	public void testImportCorpusStructure__SCorpusGraph() throws Exception
	{
		class MyModule1 extends PepperImporterImpl
		{
			EList<SDocument> documents= new BasicEList<SDocument>();
			{this.name= "MyModule1";}
			
			@Override
			public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException 
			{
				//create the following corpus structure
				//	corpgraph
				//	|
				//	|- doc1 .. doc10
				for (int i= 0; i < 10; i++)
				{	
					SDocument sDocument= SaltCommonFactory.eINSTANCE.createSDocument();
					documents.add(sDocument);
					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
					sElementId.setSId("id"+ i);
					sDocument.setSElementId(sElementId);
					corpusGraph.addSNode(sDocument);
//					System.out.println("added document: "+ sDocument.getSElementId().getSId());
				}
			}
			
			public Boolean checkImport(SCorpusGraph corpusGraph)
			{
				Boolean retVal= false;
				if (	(corpusGraph.getSDocuments().containsAll(documents)) &&
						(documents.containsAll(corpusGraph.getSDocuments())))
					retVal= true;
				return(retVal);
			}
		}
		
		class Runner extends FinishableRunner
		{			
			@Override
			public void myRunning() 
			{
				SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
				SCorpusGraph corpGraph1= SaltCommonFactory.eINSTANCE.createSCorpusGraph();
				saltProject.getSCorpusGraphs().add(corpGraph1);
				
				MyModule1 myModule1 = new MyModule1();
				myModule1.setSaltProject(saltProject);
				this.getFixture().setPepperModule(myModule1);
				
				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
				this.getFixture().setPepperM2JMonitor(m2jMonitor);
				
				this.getFixture().importCorpusStructure(corpGraph1);
				
				m2jMonitor.waitUntilFinished();
				if (m2jMonitor.getExceptions()!= null)
					for (PepperException ex: m2jMonitor.getExceptions())
						throw ex;
				
				//checking if everything is there
				if (myModule1.checkImport(corpGraph1))
					this.exceptions.add(new RuntimeException("Not all documents were imported"));
				this.finished= true;
			}
		}
		this.startRunner(5000, new Runner());
	}

	/**
	 * Tests two import modules, importing documents.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#importCorpusStructure(de.hu_berlin.german.korpling.saltnpepper.pepper.saltTest.saltFW.SCorpusGraph)
	 */
	//TODO fixme
	public void testImport2CorpusStructure__SCorpusGraph() throws Exception
	{
//		class MyModule1 extends PepperImporterImpl
//		{
//			EList<SDocument> documents= new BasicEList<SDocument>();
//			{this.name= "MyModule1";}
//			
//			@Override
//			public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException 
//			{
//				//create the following corpus structure
//				//	corpgraph
//				//	|
//				//	|- doc1 .. doc10
//				for (int i= 0; i < 10; i++)
//				{	
//					SDocument sDocument= SaltCommonFactory.eINSTANCE.createSDocument();
//					documents.add(sDocument);
//					SElementId sDocumentId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sDocumentId.setSId("doc"+ i);
//					sDocument.setSElementId(sDocumentId);
//					corpusGraph.addSNode(sDocument);
//					((PepperModuleControllerImpl)this.getPepperModuleController()).getPepperDocumentController().observeSDocument(sDocumentId);
////					System.out.println("added document: "+ sDocument.getSElementId().getSId());
//				}
//			}
//			
//			public Boolean checkImport(SCorpusGraph corpusGraph)
//			{
//				Boolean retVal= false;
//				if (	(corpusGraph.getSDocuments().containsAll(documents)) &&
//						(documents.containsAll(corpusGraph.getSDocuments())))
//					retVal= true;
//				return(retVal);
//			}
//		}
//		
//		class Runner extends FinishableRunner
//		{			
//			@Override
//			public void myRunning() 
//			{
//				SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
//				SCorpusGraph corpGraph1= SaltCommonFactory.eINSTANCE.createSCorpusGraph();
//				corpGraph1.setSName("corpGraph1");
//				SCorpusGraph corpGraph2= SaltCommonFactory.eINSTANCE.createSCorpusGraph();
//				corpGraph2.setSName("corpGraph2");
//				saltProject.getSCorpusGraphs().add(corpGraph1);
//				saltProject.getSCorpusGraphs().add(corpGraph2);
//				
//				//controller and module 1
//				MyModule1 myModule1 = new MyModule1();
//				myModule1.setSaltProject(saltProject);
//				
//				PepperModuleController controller1= PepperFWFactory.eINSTANCE.createPepperModuleController();
//				controller1.setPepperModule(myModule1);
//				//setting document controller
//				controller1.setPepperDocumentController(this.getFixture().getPepperDocumentController());
//				
//				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
//				controller1.setPepperM2JMonitor(m2jMonitor);
//				
//				//controller and module 1
//				MyModule1 myModule2 = new MyModule1();
//				myModule2.setSaltProject(saltProject);
//				
//				PepperModuleController controller2= PepperFWFactory.eINSTANCE.createPepperModuleController();
//				controller2.setPepperDocumentController(this.getFixture().getPepperDocumentController());
//				controller2.setPepperModule(myModule2);
//				
//				PepperFinishableMonitor m2jMonitor2= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
//				controller2.setPepperM2JMonitor(m2jMonitor2);
//				
//				//importing
//				controller1.importCorpusStructure(corpGraph1);
//				controller2.importCorpusStructure(corpGraph2);
//				
//				m2jMonitor.waitUntilFinished();
//				m2jMonitor2.waitUntilFinished();
//				if (m2jMonitor.getExceptions()!= null)
//					for (PepperException ex: m2jMonitor.getExceptions())
//						throw ex;
//				if (m2jMonitor2.getExceptions()!= null)
//					for (PepperException ex: m2jMonitor2.getExceptions())
//						throw ex;
//				
//				//checking if everything is there
//				if (myModule1.checkImport(corpGraph1))
//					this.exceptions.add(new RuntimeException("Not all documents were imported"));
//				if (myModule2.checkImport(corpGraph1))
//					this.exceptions.add(new RuntimeException("Not all documents were imported"));
//				this.finished= true;
//			}
//		}
//		this.startRunner(5000, new Runner());
	}

	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#start() <em>Start</em>}' operation.
	 * Sets an input-monitor, and an output-monitor, a module, which delegates all input
	 * directly into output. Than it tests, if all puttet orders in input-monitor will 
	 * reach the output-monitor.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#start()
	 */
	public void testStart2InputMonitors() throws Exception
	{	
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				Getter getter= new Getter();
				getter.numOfGets= 20;
				PepperQueuedMonitor m2mMonitor1= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
				getter.fixture= m2mMonitor1;
				Thread getterThread= new Thread(getter);
				getterThread.start();
				
				Putter putter= new Putter();
				putter.numOfPuts= 10;
				//setting document controller
				putter.pepperDocumentController= this.getFixture().getPepperDocumentController();
				PepperQueuedMonitor m2mMonitor2= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
				putter.fixture= m2mMonitor2;
				Thread putterThread= new Thread(putter);
				putterThread.start();
				
				Putter putter2= new Putter();
				//setting document controller
				putter2.pepperDocumentController= this.getFixture().getPepperDocumentController();
				putter2.numOfPuts= 10;
				PepperQueuedMonitor m2mMonitor3= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
				putter2.fixture= m2mMonitor3;
				Thread putterThread2= new Thread(putter2);
				putterThread2.start();
				
				//setting m2j monitor
				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
				this.getFixture().setPepperM2JMonitor(m2jMonitor);
				
				//setting module
				this.getFixture().setPepperModule(new MyModule());
				
				//setting im- and output monitors
				this.getFixture().getInputPepperModuleMonitors().add(m2mMonitor2);
				this.getFixture().getInputPepperModuleMonitors().add(m2mMonitor3);
				this.getFixture().getOutputPepperModuleMonitors().add(m2mMonitor1);
				
				this.getFixture().start();
				
				m2jMonitor.waitUntilFinished();
				if (m2jMonitor.getExceptions()!= null)
					for (PepperException ex: m2jMonitor.getExceptions())
						throw ex;
				
				while ((!getter.isFinished()))
				{
					try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
				this.finished= true;
			}
		}
		this.startRunner(5000l, new Runner());
	}
	
	/**
	 * Tests start two output monitors at one controller.
	 * Sets an input-monitor, and an output-monitor, a module, which delegates all input
	 * directly into output. Than it tests, if all puttet orders in input-monitor will 
	 * reach the output-monitor.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#start()
	 */
	//TODO fixme
	public void testStart2OutputMonitors() throws Exception
	{	
//		class Runner extends FinishableRunner 
//		{			
//			@Override
//			public void myRunning() 
//			{
//				Getter getter1= new Getter();
//				getter1.numOfGets= 10;
//				PepperQueuedMonitor m2mMonitor1= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
//				getter1.fixture= m2mMonitor1;
//				Thread getterThread= new Thread(getter1);
//				getterThread.start();
//				
//				Getter getter2= new Getter();
//				getter2.numOfGets= 10;
//				PepperQueuedMonitor m2mMonitor2= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
//				getter2.fixture= m2mMonitor2;
//				Thread getterThread2= new Thread(getter2);
//				getterThread2.start();
//				
//				Putter putter= new Putter();
//				putter.numOfPuts= 10;
//				//setting document controller
//				putter.pepperDocumentController= this.getFixture().getPepperDocumentController();
//				PepperQueuedMonitor m2mMonitor3= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
//				putter.fixture= m2mMonitor3;
//				Thread putterThread= new Thread(putter);
//				putterThread.start();
//				
//				
//				
//				//setting m2j monitor
//				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
//				this.getFixture().setPepperM2JMonitor(m2jMonitor);
//				
//				//setting module
//				this.getFixture().setPepperModule(new MyModule());
//				
//				//setting im- and output monitors
//				
//				this.getFixture().getInputPepperModuleMonitors().add(m2mMonitor3);
//				this.getFixture().getOutputPepperModuleMonitors().add(m2mMonitor1);
//				this.getFixture().getOutputPepperModuleMonitors().add(m2mMonitor2);
//				try {
//					this.getFixture().start();
//				} catch (PepperModuleException e1) {
//					throw new RuntimeException(e1.getMessage(), e1);
//				};
//				
//				m2jMonitor.waitUntilFinished();
//				if (m2jMonitor.getExceptions()!= null)
//					for (PepperException ex: m2jMonitor.getExceptions())
//						throw ex;
//				
//				while ((!getter1.isFinished()) || (!getter1.isFinished()))
//				{
//					try {
//							Thread.sleep(100);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//				}
//				this.finished= true;
//			}
//		}
//		this.startRunner(5000l, new Runner());
	}
	
	
	/**
	 * Tests module controller who�s getting orders by two monitors. Also tests on time,
	 * does on both monitors are listened in parallel
	 * Sets an input-monitor, and an output-monitor, a module, which delegates all input
	 * directly into output. Than it tests, if all puttet orders in input-monitor will 
	 * reach the output-monitor.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#start()
	 */
	public void testStart2InputMonitorsWithTime() throws Exception
	{	
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				Getter getter= new Getter();
				getter.numOfGets= 20;
				PepperQueuedMonitor m2mMonitor1= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
//				System.out.println("id of new getter monitor: "+m2mMonitor1.getId());
				getter.fixture= m2mMonitor1;
				Thread getterThread= new Thread(getter);
				getterThread.start();
				
				Putter putter= new Putter();
				putter.setSleepTime(500);
				putter.numOfPuts= 10;
				//setting document controller
				putter.pepperDocumentController= this.getFixture().getPepperDocumentController();
				PepperQueuedMonitor m2mMonitor2= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
//				System.out.println("id of new putter1 monitor: "+m2mMonitor2.getId());
				putter.fixture= m2mMonitor2;
				Thread putterThread= new Thread(putter);
				putterThread.start();
				
				Putter putter2= new Putter();
				putter2.setSleepTime(500);
				putter2.numOfPuts= 10;
				//setting document controller
				putter2.pepperDocumentController= this.getFixture().getPepperDocumentController();
				PepperQueuedMonitor m2mMonitor3= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
//				System.out.println("id of new putter2 monitor: "+m2mMonitor3.getId());
				putter2.fixture= m2mMonitor3;
				Thread putterThread2= new Thread(putter2);
				putterThread2.start();
				
				//setting m2j monitor
				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
//				System.out.println("id of new m2j monitor: "+m2jMonitor.getId());
				this.getFixture().setPepperM2JMonitor(m2jMonitor);
				
				//setting module
				this.getFixture().setPepperModule(new MyModule());
				
				//setting im- and output monitors
				this.getFixture().getInputPepperModuleMonitors().add(m2mMonitor2);
				this.getFixture().getInputPepperModuleMonitors().add(m2mMonitor3);
				this.getFixture().getOutputPepperModuleMonitors().add(m2mMonitor1);
				try {
					this.getFixture().start();
				} catch (PepperModuleException e1) {
					throw new RuntimeException(e1.getMessage(), e1);
				};
				
				m2jMonitor.waitUntilFinished();
				if (m2jMonitor.getExceptions()!= null)
					for (PepperException ex: m2jMonitor.getExceptions())
						throw ex;
				
				while ((!getter.isFinished()))
				{
					try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
				this.finished= true;
			}
		}
		this.startRunner(7000l, new Runner());
	}
	
	/**
	 * Tests module controller who�s getting orders by two monitors. Also tests on time,
	 * does on both monitors are listened in parallel
	 * Sets an input-monitor, and an output-monitor, a module, which delegates all input
	 * directly into output. Than it tests, if all puttet orders in input-monitor will 
	 * reach the output-monitor.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#start()
	 */
	public void testStart2InputMonitorsWithThrowingAway() throws Exception
	{	
		/**
		 * throws away, the half of document-ids
		 * @author Administrator
		 *
		 */
		class MyModule1 extends MyModule
		{
			public int numberOfThrowingAways= 0;
			
			public void start()
			{
				SElementId sElementId= null;
				boolean isStart= true;
				int numOfThrownAways= 0;
				while ((isStart) || (sElementId!= null))
				{
					
					isStart= false;
					sElementId= this.getPepperModuleController().get();
					if (sElementId== null)
						break;
					if (numOfThrownAways >= numberOfThrowingAways)
					{
						this.getPepperModuleController().put(sElementId);
					}
					else
					{	
						numOfThrownAways++;
						this.getPepperModuleController().finish(sElementId);
					}	
				}
			}
		}
		
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				Getter getter= new Getter();
				getter.numOfGets= 10;
				PepperQueuedMonitor m2mMonitor1= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
	//			System.out.println("id of new getter monitor: "+m2mMonitor1.getId());
				getter.fixture= m2mMonitor1;
				Thread getterThread= new Thread(getter);
				getterThread.start();
				
				Putter putter= new Putter();
				putter.setSleepTime(500);
				putter.numOfPuts= 10;
				//setting document controller
				putter.pepperDocumentController= this.getFixture().getPepperDocumentController();
				PepperQueuedMonitor m2mMonitor2= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
	//			System.out.println("id of new putter1 monitor: "+m2mMonitor2.getId());
				putter.fixture= m2mMonitor2;
				Thread putterThread= new Thread(putter);
				putterThread.start();
				
				Putter putter2= new Putter();
				putter2.setSleepTime(500);
				putter2.numOfPuts= 10;
				//setting document controller
				putter2.pepperDocumentController= this.getFixture().getPepperDocumentController();
				PepperQueuedMonitor m2mMonitor3= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
	//			System.out.println("id of new putter2 monitor: "+m2mMonitor3.getId());
				putter2.fixture= m2mMonitor3;
				Thread putterThread2= new Thread(putter2);
				putterThread2.start();
				
				//setting m2j monitor
				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
	//			System.out.println("id of new m2j monitor: "+m2jMonitor.getId());
				this.getFixture().setPepperM2JMonitor(m2jMonitor);
				
				//setting module
				MyModule1 module= new MyModule1();
				module.numberOfThrowingAways= 10;
				this.getFixture().setPepperModule(module);
				
				//setting im- and output monitors
				this.getFixture().getInputPepperModuleMonitors().add(m2mMonitor2);
				this.getFixture().getInputPepperModuleMonitors().add(m2mMonitor3);
				this.getFixture().getOutputPepperModuleMonitors().add(m2mMonitor1);
				try {
					this.getFixture().start();
				} catch (PepperModuleException e1) {
					throw new RuntimeException(e1.getMessage(), e1);
				}
				
				m2jMonitor.waitUntilFinished();
				if (m2jMonitor.getExceptions()!= null)
					for (PepperException ex: m2jMonitor.getExceptions())
						throw ex;
				this.finished= true;
			}
		}
		this.startRunner(6000l, new Runner());
	}
	
	/**
	 * Tests module-controller, that it throws an error message, that not all document-ids
	 * of input-monitor are processed by module.
	 * Sets an input-monitor, and an output-monitor, a module, which delegates all input
	 * directly into output. Than it tests, if all puttet orders in input-monitor will 
	 * reach the output-monitor.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#start()
	 */
	public void testStartNotAllOrdersProcessed() throws Exception
	{	
		class CorruptedModule extends MyModule
		{
			{
				this.name= "MyModule";
			}
			public void start()
			{
				SElementId sElementId= null;
				boolean isStart= true;
				int numOfDocumentsProcessed= 0;
				while ((isStart) || (sElementId!= null))
				{
					
					isStart= false;
					sElementId= this.getPepperModuleController().get();
					if (sElementId== null)
						break;
					this.getPepperModuleController().put(sElementId);
					if (numOfDocumentsProcessed > 5)
						break;
					numOfDocumentsProcessed++;
				}
			}
		}
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				Getter getter= new Getter();
				getter.numOfGets= 5;
				PepperQueuedMonitor m2mMonitor1= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
		//		System.out.println("id of new getter monitor: "+m2mMonitor1.getId());
				getter.fixture= m2mMonitor1;
				Thread getterThread= new Thread(getter);
				getterThread.start();
				
				Putter putter= new Putter();
				putter.setSleepTime(100);
				putter.numOfPuts= 10;
				//setting document controller
				putter.pepperDocumentController= this.getFixture().getPepperDocumentController();
				PepperQueuedMonitor m2mMonitor2= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
		//		System.out.println("id of new putter1 monitor: "+m2mMonitor2.getId());
				putter.fixture= m2mMonitor2;
				Thread putterThread= new Thread(putter);
				putterThread.start();
		
		
				//setting m2j monitor
				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
		//		System.out.println("id of new m2j monitor: "+m2jMonitor.getId());
				this.getFixture().setPepperM2JMonitor(m2jMonitor);
				
				//setting module
				this.getFixture().setPepperModule(new CorruptedModule());
				
				//setting im- and output monitors
				this.getFixture().getInputPepperModuleMonitors().add(m2mMonitor2);
				this.getFixture().getOutputPepperModuleMonitors().add(m2mMonitor1);
				this.getFixture().start();
					
				m2jMonitor.waitUntilFinished();
				if (m2jMonitor.getExceptions()!= null)
					for (PepperException ex: m2jMonitor.getExceptions())
						throw ex;
				this.finished= true;
			}
		}
		try
		{
			this.startRunner(6000l, new Runner());
			fail("Pepper shall throw an error, because not all document ids were processed");
		}
		catch (PepperException e) 
		{}
	}
	
	/**
	 * Tests module-controller, that it throws an error message, that not all document-ids
	 * are pipelined from input-monitors to output-monitors, or thrown away by finish().
	 * Sets an input-monitor, and an output-monitor, a module, which delegates all input
	 * directly into output. Than it tests, if all puttet orders in input-monitor will 
	 * reach the output-monitor.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#start()
	 */
	//TODO fixme
	public void testStartNotAllOrdersPipelined() throws Exception
	{	
//		class CorruptedModule extends MyModule
//		{
//			public int numOfDocumentsToProcess= 0;
//			
//			public void start()
//			{
//				SElementId sElementId= null;
//				boolean isStart= true;
//				int numOfDocumentsProcessed= 0;
//				while ((isStart) || (sElementId!= null))
//				{
//					
//					isStart= false;
//					sElementId= this.getPepperModuleController().get();
//					if (sElementId== null)
//						break;
//					
//					if (numOfDocumentsProcessed >= numOfDocumentsToProcess)
//						;
//					else this.getPepperModuleController().put(sElementId);
//					numOfDocumentsProcessed++;
//				}
//			}
//		}
//		
//		class Runner extends FinishableRunner 
//		{			
//			@Override
//			public void myRunning() 
//			{
//				Getter getter= new Getter();
//				getter.numOfGets= 5;
//				PepperQueuedMonitor m2mMonitor1= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
//		//		System.out.println("id of new getter monitor: "+m2mMonitor1.getId());
//				getter.fixture= m2mMonitor1;
//				Thread getterThread= new Thread(getter);
//				getterThread.start();
//				
//				Putter putter= new Putter();
//				putter.setSleepTime(100);
//				putter.numOfPuts= 10;
//				//setting document controller
//				putter.pepperDocumentController= this.getFixture().getPepperDocumentController();
//				PepperQueuedMonitor m2mMonitor2= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
//		//		System.out.println("id of new putter1 monitor: "+m2mMonitor2.getId());
//				putter.fixture= m2mMonitor2;
//				Thread putterThread= new Thread(putter);
//				putterThread.start();
//		
//		
//				//setting m2j monitor
//				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
//		//		System.out.println("id of new m2j monitor: "+m2jMonitor.getId());
//				this.getFixture().setPepperM2JMonitor(m2jMonitor);
//				
//				//setting module
//				CorruptedModule module= new CorruptedModule();
//				module.numOfDocumentsToProcess= 5;
//				this.getFixture().setPepperModule(module);
//				
//				//setting im- and output monitors
//				this.getFixture().getInputPepperModuleMonitors().add(m2mMonitor2);
//				this.getFixture().getOutputPepperModuleMonitors().add(m2mMonitor1);
//				this.getFixture().start();
//				
//				m2jMonitor.waitUntilFinished();
//				if (m2jMonitor.getExceptions()!= null)
//					for (PepperException ex: m2jMonitor.getExceptions())
//						throw ex;
//				this.finished= true;
//			}
//		}
//		try
//		{
//			this.startRunner(6000l, new Runner());
//			fail("Pepper shall throw an error, because not all document ids were processed");
//		}
//		catch (Exception e) 
//		{}
	}
	
	public void testStartNothingSet() throws Exception
	{
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
				this.getFixture().setPepperM2JMonitor(m2jMonitor);
				this.getFixture().start();
				m2jMonitor.waitUntilFinished();
				if (m2jMonitor.getExceptions()!= null)
				{
					for (PepperException ex: m2jMonitor.getExceptions())
						throw ex;
				}	
				this.finished= true;
			}
		}
		try
		{
			this.startRunner(6000l, new Runner());	
			fail("no input input-monitor, m2jmonitor or module is set");
		} 
		catch (PepperConvertException e) 
		{}
		catch (Exception e) 
		{ throw new RuntimeException(e); }
	}
	
	public void testStartNoInputMonitorSet() throws Exception
	{
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
				this.getFixture().setPepperM2JMonitor(m2jMonitor);
				this.getFixture().start();
				m2jMonitor.waitUntilFinished();
				if (m2jMonitor.getExceptions()!= null)
					for (PepperException ex: m2jMonitor.getExceptions())
						throw ex;
				this.finished= true;
			}
		}
		try
		{
			this.startRunner(6000l, new Runner());	
			fail("no input input-monitor is set");
		} 
		catch (PepperConvertException e) 
		{}
		catch (Exception e) 
		{ throw new RuntimeException(e); }
	}
	
	public void testStartNoM2JMonitorSet() throws Exception
	{
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
				this.getFixture().setPepperM2JMonitor(m2jMonitor);
				this.getFixture().start();
				m2jMonitor.waitUntilFinished();
				if (m2jMonitor.getExceptions()!= null)
				{
					for (PepperException ex: m2jMonitor.getExceptions())
						throw ex;
				}
				this.finished= true;
			}
		}
		
		try
		{
			this.startRunner(6000l, new Runner());	
			fail("no input m2j-monitor set");
		} 
		catch (PepperConvertException e) 
		{}
		catch (Exception e) 
		{ throw new RuntimeException(e); }
	}
	
	public void testStartNoModuleSet() throws Exception
	{
		try {
			PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
			this.getFixture().setPepperM2JMonitor(m2jMonitor);
			this.getFixture().start();
			m2jMonitor.waitUntilFinished();
			if (m2jMonitor.getExceptions()!= null)
			{
				for (PepperException ex: m2jMonitor.getExceptions())
					throw ex;
			}	
			fail("no input module set");
		} 
		catch (PepperConvertException e) 
		{}
		catch (Exception e) 
		{ throw new RuntimeException(e); }
	}

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperInterface.PepperModuleController#put(de.hu_berlin.german.korpling.saltnpepper.pepper.saltTest.saltCommon.SElementId) <em>Put</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperInterface.PepperModuleController#put(de.hu_berlin.german.korpling.saltnpepper.pepper.saltTest.saltCommon.SElementId)
	 */
	public void testPut__SElementId() 
	{
		//see other put-tests
	}
	
	public void testPutEmptyParameter() 
	{
		try {
			this.getFixture().put(null);
			fail("shall not finished with empty element id");
		} catch (Exception e) {
		}	
	}
	
	public void testPutWithoutStart() 
	{
		try {
			this.getFixture().put(SaltCommonFactory.eINSTANCE.createSElementId());
			fail("shall not put an element-id, fixture wasn�t started");
		} catch (Exception e) {
		}	
	}

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperInterface.PepperModuleController#get() <em>Get</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperInterface.PepperModuleController#get()
	 */
	public void testGet() 
	{
		//see other getMethods
	}
	
	/**
	 * tests empty get, without start fixture
	 */
	public void testGetWithoutStart() 
	{
		try {
			this.getFixture().get();
			fail("shall not get an element-id, fixture wasn�t started");
		} catch (Exception e) {
		}	
	}

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperInterface.PepperModuleController#finish(de.hu_berlin.german.korpling.saltnpepper.pepper.saltTest.saltCommon.SElementId) <em>Finish</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperInterface.PepperModuleController#finish(de.hu_berlin.german.korpling.saltnpepper.pepper.saltTest.saltCommon.SElementId)
	 */
	public void testFinish__SElementId() throws Exception
	{
		//see other finish-tests
	}
	
	public void testFinishEmptyParameter() 
	{
		try {
			this.getFixture().finish(null);
			fail("shall not finished with empty element id");
		} catch (Exception e) {
		}	
	}
	
	/**
	 * tests empty get, without start fixture
	 */
	public void testFinishWithoutStart() 
	{
		try {
			this.getFixture().finish(SaltCommonFactory.eINSTANCE.createSElementId());
			fail("shall not finish an element-id, fixture wasn�t started");
		} catch (Exception e) {
		}	
	}
} //PepperModuleControllerTest

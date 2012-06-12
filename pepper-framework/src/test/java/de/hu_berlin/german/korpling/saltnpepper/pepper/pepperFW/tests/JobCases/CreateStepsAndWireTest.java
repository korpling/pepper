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

import org.eclipse.emf.common.util.EList;


import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.FinishableRunner;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl;

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
public class CreateStepsAndWireTest extends TestCase 
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
		TestRunner.run(CreateStepsAndWireTest.class);
	}

	/**
	 * Constructs a new Pepper Job test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	public CreateStepsAndWireTest(String name) {
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
	
//	/**
//	 * starts a runner thread and checks, that if test needs longer than given max time, 
//	 * it will fail.
//	 * @param MAX_WAITING_TIME
//	 * @param runner
//	 * @throws Exception
//	 */
//	private void startRunner(long MAX_WAITING_TIME, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.FinishableRunner runner) throws Exception
//	{
//		runner.setFixture(this.getFixture());
//		Thread runnerThread= new Thread(runner);
//		runnerThread.start();
//		
//		boolean maxTimeReached= false;
//		long t1 = System.currentTimeMillis();
//		while((!runner.isFinished()) && (!maxTimeReached))
//		{
//			if (runner.exceptions!= null)
//				for (Exception ex: runner.exceptions)
//					throw ex;
//			Thread.sleep(100);
//			if ((System.currentTimeMillis() - t1) > MAX_WAITING_TIME)
//			{
//				maxTimeReached= true;
//			}
//		}
//		assertFalse("TIMEOUT while testing...", maxTimeReached);
//	}
	
	private class TestImporter extends PepperImporterImpl
	{
	}
	
	private class TestModule extends PepperModuleImpl
	{
	}
	
	private class TestExporter extends PepperExporterImpl
	{
	}
	
	/**
	 * Tests one importer, one manipulation module, one exporter
	 * importer1 -> module 1-> exporter1
	 * @throws Exception
	 */
	public void testOneOfAll() throws Exception
	{
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				TestImporter importer1= new TestImporter();
				((PepperJobDelegatorTest)this.getFixture()).getPepperImporters().add(importer1);
				
				TestExporter exporter1= new TestExporter();
				((PepperJobDelegatorTest)this.getFixture()).getPepperExporters().add(exporter1);
				
				TestModule module1= new TestModule();
				((PepperJobDelegatorTest)this.getFixture()).getPepperModules().add(module1);
				
				//wiring with m2j-monitors
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(importer1);
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(module1);
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(exporter1);
				
				//wirinig with m2m-monitors
				EList<EList<PepperModuleController>> steps= ((PepperJobDelegatorTest)this.getFixture()).createPhases();
				((PepperJobDelegatorTest)this.getFixture()).wireModuleControllers(steps);
				
				//checking if wireing was successfull
				//importer1 -> module 1-> exporter1
				EList<PepperQueuedMonitor> importerMonitors= ((PepperModuleController)importer1.getPepperModuleController()).getOutputPepperModuleMonitors();
				EList<PepperQueuedMonitor> module1InMonitors= ((PepperModuleController)module1.getPepperModuleController()).getInputPepperModuleMonitors();
				EList<PepperQueuedMonitor> module1OutMonitors= ((PepperModuleController)module1.getPepperModuleController()).getOutputPepperModuleMonitors();
				EList<PepperQueuedMonitor> exporterMonitors= ((PepperModuleController)exporter1.getPepperModuleController()).getInputPepperModuleMonitors();
				//importer -> module1
				assertEquals(importerMonitors, module1InMonitors);
				assertEquals(module1InMonitors, importerMonitors);
				//module1 -> exporter1
				assertEquals(module1OutMonitors, exporterMonitors);
				assertEquals(exporterMonitors, module1OutMonitors);
				
				this.finished= true;
			}
		}
		FinishableRunner.startRunner(1000l, new Runner(), this.getFixture());
	}
	
	/**
	 * Tests two importers, two manipulation-modules and two exporters
	 * importer1										exporter1
	 * 				x	module1	->	module2			x
	 * importer2										exporter2	
	 * @throws Exception
	 */
	public void testTwoOfAll() throws Exception
	{
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				TestImporter importer1= new TestImporter();
				((PepperJobDelegatorTest)this.getFixture()).getPepperImporters().add(importer1);
				TestImporter importer2= new TestImporter();
				((PepperJobDelegatorTest)this.getFixture()).getPepperImporters().add(importer2);
				
				TestExporter exporter1= new TestExporter();
				((PepperJobDelegatorTest)this.getFixture()).getPepperExporters().add(exporter1);
				TestExporter exporter2= new TestExporter();
				((PepperJobDelegatorTest)this.getFixture()).getPepperExporters().add(exporter2);
				
				TestModule module1= new TestModule();
				((PepperJobDelegatorTest)this.getFixture()).getPepperModules().add(module1);
				TestModule module2= new TestModule();
				((PepperJobDelegatorTest)this.getFixture()).getPepperModules().add(module2);
				
				//wiring with m2j-monitors
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(importer1);
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(importer2);
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(module1);
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(module2);
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(exporter1);
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(exporter2);
				
				//wirinig with m2m-monitors
				EList<EList<PepperModuleController>> steps= ((PepperJobDelegatorTest)this.getFixture()).createPhases();
				((PepperJobDelegatorTest)this.getFixture()).wireModuleControllers(steps);
				
				//checking if wireing was successfull
				//	importer1										exporter1
				// 				x	module1	->	module2			x
				// 	importer2										exporter2	
				EList<PepperQueuedMonitor> importer1Monitors= ((PepperModuleController)importer1.getPepperModuleController()).getOutputPepperModuleMonitors();
				EList<PepperQueuedMonitor> importer2Monitors= ((PepperModuleController)importer2.getPepperModuleController()).getOutputPepperModuleMonitors();
				
				EList<PepperQueuedMonitor> module1InMonitors= ((PepperModuleController)module1.getPepperModuleController()).getInputPepperModuleMonitors();
				EList<PepperQueuedMonitor> module1OutMonitors= ((PepperModuleController)module1.getPepperModuleController()).getOutputPepperModuleMonitors();
				EList<PepperQueuedMonitor> module2InMonitors= ((PepperModuleController)module2.getPepperModuleController()).getInputPepperModuleMonitors();
				EList<PepperQueuedMonitor> module2OutMonitors= ((PepperModuleController)module2.getPepperModuleController()).getOutputPepperModuleMonitors();
				
				EList<PepperQueuedMonitor> exporter1Monitors= ((PepperModuleController)exporter1.getPepperModuleController()).getInputPepperModuleMonitors();
				EList<PepperQueuedMonitor> exporter2Monitors= ((PepperModuleController)exporter1.getPepperModuleController()).getInputPepperModuleMonitors();
//				System.out.println("number of input monitors for module1: "+module1InMonitors.size());
				//importer1 -> module1
				assertTrue(module1InMonitors.containsAll(importer1Monitors));
				//importer2 -> module1
				assertTrue(module1InMonitors.containsAll(importer2Monitors));
				
				//module1 -> module2
				assertEquals(module1OutMonitors, module2InMonitors);
				assertEquals(module2InMonitors, module1OutMonitors);
				
				//module2 -> exporter1
				assertTrue(module2OutMonitors.containsAll(exporter1Monitors));
				//module2 -> exporter2
				assertTrue(module2OutMonitors.containsAll(exporter2Monitors));
				
				this.finished= true;
			}
		}
		FinishableRunner.startRunner(1000l, new Runner(), this.getFixture());
	}
	
	/**
	 * Tests one importer and one exporter
	 * importer1 -> exporter1
	 * @throws Exception
	 */
	public void testImporterAndExporter() throws Exception
	{
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning() 
			{
				TestImporter importer1= new TestImporter();
				((PepperJobDelegatorTest)this.getFixture()).getPepperImporters().add(importer1);
				
				TestExporter exporter1= new TestExporter();
				((PepperJobDelegatorTest)this.getFixture()).getPepperExporters().add(exporter1);
				
				//wiring with m2j-monitors
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(importer1);
				((PepperJobDelegatorTest)this.getFixture()).createAndWirePepperModuleController(exporter1);
				
				//wirinig with m2m-monitors
				EList<EList<PepperModuleController>> steps= ((PepperJobDelegatorTest)this.getFixture()).createPhases();
				((PepperJobDelegatorTest)this.getFixture()).wireModuleControllers(steps);
				
				//checking if wireing was successfull
				//importer1 -> exporter1
				EList<PepperQueuedMonitor> importerMonitors= ((PepperModuleController)importer1.getPepperModuleController()).getOutputPepperModuleMonitors();
				EList<PepperQueuedMonitor> exporterMonitors= ((PepperModuleController)exporter1.getPepperModuleController()).getInputPepperModuleMonitors();
				assertEquals(importerMonitors, exporterMonitors);
				assertEquals(exporterMonitors, importerMonitors);
				
				this.finished= true;
			}
		}
		FinishableRunner.startRunner(1000l, new Runner(), this.getFixture());
	}
} //PepperConvertJobTest

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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Pepper Monitor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#finish() <em>Finish</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#waitUntilFinished() <em>Wait Until Finished</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class PepperMonitorTest extends TestCase {

	/**
	 * The fixture for this Pepper Monitor test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperMonitor fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PepperMonitorTest.class);
	}

	/**
	 * Constructs a new Pepper Monitor test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperMonitorTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Pepper Monitor test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PepperMonitor fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Pepper Monitor test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperMonitor getFixture() {
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
		setFixture(PepperFWFactory.eINSTANCE.createPepperMonitor());
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

	private class MonitorThread implements Runnable
	{
		PepperMonitor monitor= null;
		
		@Override
		public void run() 
		{	
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			monitor.finish();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class Runner implements Runnable
	{
		EList<PepperMonitor> monitors= null;
		
		public boolean isAllTerminated()
		{
			return(this.allTerminated);
		}
		
		boolean allTerminated= false;
		
		@Override
		public void run() 
		{
			if ((monitors== null) || (monitors.size()== 0))
				throw new NullPointerException("Error in Test, no monitors are given to check.");
			allTerminated= false;
			
			while(!allTerminated)
			{
				for (PepperMonitor monitor: this.monitors)
				{
					monitor.waitUntilFinished();
					assertTrue(monitor.isFinished());
				}
				this.allTerminated= true;
			}	
		}
	}
	
	public void testSetAndGetId() 
	{
		assertNotNull(this.getFixture().getId());
		String id= "newId";
		this.getFixture().setId(id);
		assertEquals(id, this.getFixture().getId());
	}
	
	private long MAX_WAITING_TIME= 2500l;
	
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#waitUntilFinished() <em>Wait Until Finished</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#waitUntilFinished()
	 */
	//TODO fixme
//	public void testWaitUntilFinished() throws Exception
//	{
//		EList<MonitorThread> monitorThreads= new BasicEList<MonitorThread>();
//		EList<PepperMonitor> monitors= new BasicEList<PepperMonitor>();
//		
//		for (int i= 0; i < 10; i++)
//		{
//			MonitorThread monitorThread= new MonitorThread();
//			monitorThreads.add(monitorThread);
//			PepperMonitor monitor= PepperFWFactory.eINSTANCE.createPepperMonitor();
//			monitors.add(monitor);
//			monitorThread.monitor= monitor;
//		}	
//		for (MonitorThread monitorThread: monitorThreads)
//		{
//			Thread th= new Thread(monitorThread);
//			th.start();
//		}
//		
//		Runner runner= new Runner();
//		runner.monitors= monitors;
//		Thread runnerThread= new Thread(runner);
//		runnerThread.start();
//		
//		long t1 = System.currentTimeMillis();
//		boolean maxTimeReached= false;
//		while((!runner.isAllTerminated()) && (!maxTimeReached))
//		{
//			Thread.sleep(100);
//			if ((System.currentTimeMillis() - t1) > MAX_WAITING_TIME)
//			{
//				maxTimeReached= true;
//			}
//		}
//		assertFalse("TIMEOUT while testing...", maxTimeReached);	
//	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#finish() <em>Finish</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#finish()
	 */
	//TODO fixme
//	public void testFinish() throws Exception
//	{
//		class Runner1 implements Runnable
//		{
//			private PepperMonitor fixture= null;
//			
//			@Override
//			public void run() 
//			{
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				this.fixture.finish();
//			}
//		}
//		
//		assertFalse(this.getFixture().isFinished());
//		Runner1 runner= new Runner1();
//		runner.fixture= this.getFixture();
//		Thread runnerThread= new Thread(runner);
//		runnerThread.start();
//		
//		long t1 = System.currentTimeMillis();
//		boolean maxTimeReached= false;
//		while( (!maxTimeReached))
//		{
//			Thread.sleep(100);
//			if ((System.currentTimeMillis() - t1) > MAX_WAITING_TIME)
//			{
//				maxTimeReached= true;
//			}
//		}
//		assertTrue(this.getFixture().isFinished());
//	}

} //PepperMonitorTest

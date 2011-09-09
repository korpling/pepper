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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Pepper Module2 Module Monitor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#isEmpty() <em>Empty</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#put(de.hu_berlin.german.korpling.saltnpepper.pepper.saltTest.saltCommon.SElementId) <em>Put</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#get() <em>Get</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class PepperModule2ModuleMonitorTest extends PepperMonitorTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PepperModule2ModuleMonitorTest.class);
	}

	/**
	 * Constructs a new Pepper Module2 Module Monitor test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModule2ModuleMonitorTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Pepper Module2 Module Monitor test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected PepperQueuedMonitor getFixture() {
		return (PepperQueuedMonitor)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(PepperFWFactory.eINSTANCE.createPepperQueuedMonitor());
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

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#isEmpty() <em>Empty</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#isEmpty()
	 */
	public void testIsEmpty() 
	{
		assertTrue(this.getFixture().isEmpty());
		SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
		this.getFixture().put(sElementId);
		assertFalse(this.getFixture().isEmpty());
	}

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#put(de.hu_berlin.german.korpling.saltnpepper.pepper.saltTest.saltCommon.SElementId) <em>Put</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#put(de.hu_berlin.german.korpling.saltnpepper.pepper.saltTest.saltCommon.SElementId)
	 */
	public void testPut__SElementId() 
	{
		//see testPuttingFirstGettingSecond()
	}
	
	public void testPutEmpty__SElementId() 
	{
		try {
			this.getFixture().put(null);
			fail("shall not put an empty element-id.");
		} catch (Exception e) {
		}
		
	}

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#get() <em>Get</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#get()
	 */
	public void testGet() 
	{
		//see testPuttingFirstGettingSecond()
	}
	
	/**
	 * Tests first putting an element-id, than getting it.
	 * @throws Exception
	 */
	public void testPuttingFirstGettingSecond() throws Exception
	{
		class Runner implements Runnable
		{
			private PepperQueuedMonitor fixture= null;
			private boolean isFinished= false;
			
			private PepperQueuedMonitor getFixture()
			{
				return(this.fixture);
			}
			
			@Override
			public void run() 
			{
				SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
				sElementId.setSId("id1");
				this.getFixture().put(sElementId);
				assertEquals(sElementId, this.getFixture().get());
				this.isFinished= true;
			}
		}
		long MAX_WAITING_TIME= 2000l;
		
		Runner runner= new Runner();
		runner.fixture= this.getFixture();
		Thread runnerThread= new Thread(runner);
		runnerThread.start();
		
		boolean maxTimeReached= false;
		long t1 = System.currentTimeMillis();
		while((!runner.isFinished) && (!maxTimeReached))
		{
			Thread.sleep(100);
			if ((System.currentTimeMillis() - t1) > MAX_WAITING_TIME)
				maxTimeReached= true;
		}
		assertFalse("TIMEOUT while testing...", maxTimeReached);
	}
	
	/**
	 * First try to get an element-id, if there is non waiting til there comes one. Than
	 * putting an element-id.
	 * @throws Exception
	 */
	//TODO fixme
	public void testGettingFirstPuttingSecond() throws Exception
	{
//		class Getter implements Runnable
//		{
//			private PepperQueuedMonitor fixture= null;
//			private boolean isFinished= false;
//			
//			private PepperQueuedMonitor getFixture()
//			{
//				return(this.fixture);
//			}
//			
//			public void run() 
//			{
//				SElementId sElementId= this.getFixture().get();
//				assertNotNull(sElementId);
//				this.isFinished= true;
//			}
//		}
//		
//		class Putter implements Runnable
//		{
//			private PepperQueuedMonitor fixture= null;
//			private boolean isFinished= false;
//			
//			private PepperQueuedMonitor getFixture()
//			{
//				return(this.fixture);
//			}
//			@Override
//			public void run() 
//			{
//				try {
//					Thread.sleep(400);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//				sElementId.setSId("id1");
//				this.getFixture().put(sElementId);
//				this.isFinished= true;
//			}
//		}
//		
//		class Runner implements Runnable
//		{
//			private PepperQueuedMonitor fixture= null;
//			private boolean isFinished= false;
//			
//			private PepperQueuedMonitor getFixture()
//			{
//				return(this.fixture);
//			}
//			
//			@Override
//			public void run() 
//			{
//				Getter getter= new Getter();
//				getter.fixture= this.getFixture();
//				Thread getterThread= new Thread(getter);
//				getterThread.start();
//				
//				Putter putter= new Putter();
//				putter.fixture= this.getFixture();
//				Thread putterThread= new Thread(putter);
//				putterThread.start();
//				
//				while ((!putter.isFinished) || (!getter.isFinished))
//				{}
//				this.isFinished= true;
//			}
//		}
//		long MAX_WAITING_TIME= 2000l;
//		
//		Runner runner= new Runner();
//		runner.fixture= this.getFixture();
//		Thread runnerThread= new Thread(runner);
//		runnerThread.start();
//		
//		boolean maxTimeReached= false;
//		long t1 = System.currentTimeMillis();
//		while((!runner.isFinished) && (!maxTimeReached))
//		{
//			Thread.sleep(100);
//			if ((System.currentTimeMillis() - t1) > MAX_WAITING_TIME)
//			{
//				maxTimeReached= true;
//			}
//		}
//		assertFalse("TIMEOUT while testing...", maxTimeReached);
	}
	
	/**
	 * First try to get an element-id, if there is non waiting til there comes one. Than
	 * putting an element-id.
	 * @throws Exception
	 */
	//TODO fixme
	public void testGettPuttMultiple() throws Exception
	{
//		class Getter implements Runnable
//		{
//			private PepperQueuedMonitor fixture= null;
//			private boolean isFinished= false;
//			private Integer numOfGets= null;
//			
//			private PepperQueuedMonitor getFixture()
//			{
//				return(this.fixture);
//			}
//			
//			public void run() 
//			{
//				for (int i= 1; i < numOfGets; i++)
//				{	
//					SElementId sElementId= this.getFixture().get();
//					assertNotNull(sElementId);
//				}
//				this.isFinished= true;
//			}
//			
//		}
//		
//		class Putter implements Runnable
//		{
//			private PepperQueuedMonitor fixture= null;
//			private boolean isFinished= false;
//			private Integer numOfPuts= null;
//			
//			private PepperQueuedMonitor getFixture()
//			{
//				return(this.fixture);
//			}
//			@Override
//			public void run() 
//			{
//				for (int i= 1; i < numOfPuts; i++)
//				{	
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					this.getFixture().put(sElementId);
//				}
//				this.isFinished= true;
//			}
//			
//		}
//		
//		class Runner implements Runnable
//		{
//			private PepperQueuedMonitor fixture= null;
//			private boolean isFinished= false;
//			
//			private PepperQueuedMonitor getFixture()
//			{
//				return(this.fixture);
//			}
//			
//			@Override
//			public void run() 
//			{
//				Getter getter= new Getter();
//				getter.numOfGets= 10;
//				getter.fixture= this.getFixture();
//				Thread getterThread= new Thread(getter);
//				getterThread.start();
//				
//				Putter putter= new Putter();
//				putter.numOfPuts= 10;
//				putter.fixture= this.getFixture();
//				Thread putterThread= new Thread(putter);
//				putterThread.start();
//				
//				while ((!putter.isFinished) || (!getter.isFinished))
//				{}
//				this.isFinished= true;
//			}
//		}
//		long MAX_WAITING_TIME= 5000l;
//		
//		Runner runner= new Runner();
//		runner.fixture= this.getFixture();
//		Thread runnerThread= new Thread(runner);
//		runnerThread.start();
//		
//		boolean maxTimeReached= false;
//		long t1 = System.currentTimeMillis();
//		while((!runner.isFinished) && (!maxTimeReached))
//		{
//			Thread.sleep(100);
//			if ((System.currentTimeMillis() - t1) > MAX_WAITING_TIME)
//			{
//				maxTimeReached= true;
//			}
//		}
//		assertFalse("TIMEOUT while testing...", maxTimeReached);
	}
	
	/**
	 * this test puts some elements in monitor and gets them back, until null is 
	 * returned.
	 */
	//TODO fixme
	public void testGettingUntilNull() throws Exception
	{
//		class Getter implements Runnable
//		{
//			private PepperQueuedMonitor fixture= null;
//			private boolean isFinished= false;
//			private Integer numOfGets= null;
//			
//			private PepperQueuedMonitor getFixture()
//			{
//				return(this.fixture);
//			}
//			
//			public void run() 
//			{
//				SElementId sElementId= this.getFixture().get();
//				int i= 1;
//				while (sElementId!= null)
//				{
//					i++;
//					sElementId= this.getFixture().get();
//				}	
//				assertTrue(this.numOfGets.equals(i));
//				this.isFinished= true;
//			}
//			
//		}
//		
//		class Putter implements Runnable
//		{
//			private PepperQueuedMonitor fixture= null;
//			private boolean isFinished= false;
//			private Integer numOfPuts= null;
//			
//			private PepperQueuedMonitor getFixture()
//			{
//				return(this.fixture);
//			}
//			@Override
//			public void run() 
//			{
//				for (int i= 1; i < numOfPuts; i++)
//				{	
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					this.getFixture().put(sElementId);
//				}
//				this.getFixture().finish();
//				this.isFinished= true;
//			}
//			
//		}
//		
//		class Runner implements Runnable
//		{
//			private PepperQueuedMonitor fixture= null;
//			private boolean isFinished= false;
//			
//			private PepperQueuedMonitor getFixture()
//			{
//				return(this.fixture);
//			}
//			
//			@Override
//			public void run() 
//			{
//				Getter getter= new Getter();
//				getter.numOfGets= 10;
//				getter.fixture= this.getFixture();
//				Thread getterThread= new Thread(getter);
//				getterThread.start();
//				
//				Putter putter= new Putter();
//				putter.numOfPuts= 10;
//				putter.fixture= this.getFixture();
//				Thread putterThread= new Thread(putter);
//				putterThread.start();
//				
//				while ((!putter.isFinished) || (!getter.isFinished))
//				{}
//				this.isFinished= true;
//			}
//		}
//		long MAX_WAITING_TIME= 5000l;
//		
//		Runner runner= new Runner();
//		runner.fixture= this.getFixture();
//		Thread runnerThread= new Thread(runner);
//		runnerThread.start();
//		
//		boolean maxTimeReached= false;
//		long t1 = System.currentTimeMillis();
//		while((!runner.isFinished) && (!maxTimeReached))
//		{
//			Thread.sleep(100);
//			if ((System.currentTimeMillis() - t1) > MAX_WAITING_TIME)
//			{
//				maxTimeReached= true;
//			}
//		}
//		assertFalse("TIMEOUT while testing...", maxTimeReached);
	}

	/**
	 * tests if this fixture returns null afeter its finished.
	 */
	public void testReturnNullAfterAll()
	{
		SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
		sElementId.setSId("id 1"); 
		this.getFixture().put(sElementId);
		this.getFixture().finish();
		assertEquals(sElementId, this.getFixture().get());
		assertNull(this.getFixture().get());
		assertNull(this.getFixture().get());
		assertNull(this.getFixture().get());
	}
} //PepperModule2ModuleMonitorTest

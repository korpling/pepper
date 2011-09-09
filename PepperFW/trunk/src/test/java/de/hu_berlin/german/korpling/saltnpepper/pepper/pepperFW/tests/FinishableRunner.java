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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
/**
 * a class which defines a runner, which has a finish method.
 * @author Administrator
 *
 */
public class FinishableRunner implements Runnable 
{
	/**
	 * starts a runner thread and checks, that if test needs longer than given max time, 
	 * it will fail.
	 * @param MAX_WAITING_TIME
	 * @param runner
	 * @throws Exception
	 */
	public static synchronized void startRunner(long MAX_WAITING_TIME, FinishableRunner runner, Object fixture) throws Exception
	{
		runner.setFixture(fixture);
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
		if (maxTimeReached)
			throw new RuntimeException("TIMEOUT while testing...");
//		assertFalse("TIMEOUT while testing...", maxTimeReached);s
	}
	
	protected Object fixture= null;
	protected boolean finished= false;
	public EList<Exception> exceptions= new BasicEList<Exception>();
	
	public Boolean isFinished()
	{ return(this.finished); }
	
	public Object getFixture()
	{return(this.fixture);}
	
	public void setFixture(Object fixture)
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

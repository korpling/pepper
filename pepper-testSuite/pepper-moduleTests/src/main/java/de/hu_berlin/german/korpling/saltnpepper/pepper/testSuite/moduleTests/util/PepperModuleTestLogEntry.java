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
package de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests.util;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.logReader.LogReader;

/**
 * This class is a dummy implementation against the {@link LogEntry} interface, which is used by the {@link PepperModuleTestLogService}
 * to delegate log messages to the corresponding {@link LogReader} object which implementation in general is {@link LogReader}.
 * An object of this class only returns the values given by the constructor call via the corresponding getter method.
 *  
 * @author Florian Zipser
 *
 */
public class PepperModuleTestLogEntry implements LogEntry {

	protected PepperModuleTestLogEntry(	Bundle bundle, 
										Throwable exception, 
										int level, 
										String message, 
										ServiceReference serviceReference,
										long time)
	{
		this.bundle= bundle;
		this.exception= exception;
		this.level= level;
		this.message= message;
		this.serviceReference= serviceReference;
		this.time= time;
		
	}
	
	private Bundle bundle= null;
	private Throwable exception= null;
	private int level= LogService.LOG_DEBUG;
	private String message= null;
	private ServiceReference serviceReference= null;
	private long time= 0l; 
	
	@Override
	public Bundle getBundle() {
		return(this.bundle);
	}

	@Override
	public Throwable getException() 
	{
		return(this.exception);
	}

	@Override
	public int getLevel() 
	{
		return(this.level);
	}

	@Override
	public String getMessage() {
		return(this.message);
	}

	@Override
	public ServiceReference getServiceReference() {
		return(this.serviceReference);
	}

	@Override
	public long getTime() {
		return(this.time);
	}

}

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

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.logReader.LogReader;

/**
 * This class emulates a LogService, as it is used in a real running OSGi environment. Here the logs are just 
 * delegated to a {@link LogReader} object  
 * @author Florian Zipser
 *
 */
public class PepperModuleTestLogService implements LogService 
{
	/**
	 * Internal {@link LogListener} object, to which the logs are delegated.
	 */
	private LogListener logListener= null;
	/**
	 * Sets the internal {@link LogListener} object, to which the logs are delegated.
	 * @param logListener
	 */
	public void setLogListener(LogListener logListener) {
		this.logListener = logListener;
	}
	/**
	 * Returns the internal {@link LogListener} object, to which the logs are delegated.
	 * @param logListener
	 */
	public LogListener getLogListener() {
		return logListener;
	}
	
	/**
	 * {@inheritDoc LogService#log(int, String)}
	 */
	@Override
	public void log(int level, String message) 
	{
		this.log(level, message, null);
	}

	/**
	 * {@inheritDoc LogService#log(ServiceReference, int, String, Throwable)}
	 */
	@Override
	public void log(int level, String message, Throwable exception) {
		this.log(null, level, message, exception);
	}

	/**
	 * {@inheritDoc LogService#log(ServiceReference, int, String)}
	 */
	@Override
	public void log(ServiceReference serviceReference, int level, String message) {
		this.log(serviceReference, level, message, null);
	}

	/**
	 *{@inheritDoc LogService#log(ServiceReference, int, String, Throwable)}
	 */
	@Override
	public void log(ServiceReference serviceReference, int level, String message, Throwable exception) {
		LogEntry logEntry= new PepperModuleTestLogEntry(null, exception, level, message, serviceReference, System.currentTimeMillis());
		this.getLogListener().logged(logEntry);
	}
}

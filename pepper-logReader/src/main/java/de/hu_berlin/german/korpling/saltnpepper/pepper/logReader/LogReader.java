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
package de.hu_berlin.german.korpling.saltnpepper.pepper.logReader;

import javax.xml.ws.ServiceMode;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

@Component(name="LogReaderComponent", immediate=true)
@ServiceMode
public class LogReader implements LogListener
{
	/**
	 * Keyword to which the LogService listens, to get the log4j file, set by the PepperStarter
	 */
	private final static String KW_LOGGER_PROPERTY="de.hu_berlin.german.korpling.saltnpepper.logger";
	private static final String symbolicName= "de.hu_berlin.german.korpling.saltnpepper.pepper-logReader";
	private static final String LOG_FILE= "log4j.properties"; 
	
	/**
	 * Location of property file for log4j.
	 */
	private String logProperties= null;
	
	public LogReader()
	{
		super();
	}
	
	/**
	 * Sets location of property file for log4j. And initializes a log4j logger.
	 * @param logProperties the logProperties to set
	 */
	public void setLogProperties(String logProperties) 
	{
		this.logProperties = logProperties;
		PropertyConfigurator.configureAndWatch(this.getLogProperties());
	}

	/**
	 * Returns location of property file for log4j.
	 * @return the logProperties
	 */
	public String getLogProperties() {
		return logProperties;
	}
	
	@Override
	public void logged(LogEntry entry) 
	{	
		if (this.logProperties== null)
		//start: if no log properties still given
			
			if (System.getProperty(KW_LOGGER_PROPERTY)!= null)
			{
				String logFile= System.getProperty(KW_LOGGER_PROPERTY);
				this.setLogProperties(logFile);
			}
			else if (System.getProperty(symbolicName+".resources")!= null)
			{	
				String logPath= System.getProperty(symbolicName+".resources");
				if (!logPath.endsWith("/"))
					logPath= logPath + "/";
				this.setLogProperties(logPath+ LOG_FILE);
			}
		//end: if no log properties still given
		String log= null;
		if (entry.getBundle()!= null)
			log= String.format("[%s] <%s> %s", getLevelAsString(entry.getLevel()), entry.getBundle().getSymbolicName(), entry.getMessage());
		else log= String.format("[%s] <%s> %s", getLevelAsString(entry.getLevel()), "no bundle given", entry.getMessage());
		switch (entry.getLevel()) 
		{
			case LogService.LOG_DEBUG:
				if (entry.getBundle()!= null)
					Logger.getLogger(entry.getBundle().getSymbolicName()).debug(log);
				else
					Logger.getLogger(this.getClass()).debug(log);
				break;
			case LogService.LOG_INFO:
				if (	(entry.getMessage().startsWith("ServiceEvent REGISTERED")) ||
						(entry.getMessage().startsWith("ServiceEvent UNREGISTERING")) ||
						(entry.getMessage().startsWith("BundleEvent INSTALLED")) ||
						(entry.getMessage().startsWith("BundleEvent RESOLVED")) ||
						(entry.getMessage().startsWith("BundleEvent STARTED")) ||
						(entry.getMessage().startsWith("BundleEvent STOPPED"))||
						(entry.getMessage().startsWith("FrameworkEvent STARTED"))||
						(entry.getMessage().startsWith("FrameworkEvent STARTLEVEL")))
					//ignore if message comes from OSGi FW
					;
				else
				{	
					String infoLog= String.format("%s", entry.getMessage());
					Logger.getLogger(entry.getBundle().getSymbolicName()).info(infoLog);
				}
				break;
			case LogService.LOG_WARNING:
				if (entry.getBundle()!= null)
					Logger.getLogger(entry.getBundle().getSymbolicName()).warn(log);
				else
					Logger.getLogger(this.getClass()).warn(log);
				break;
			case LogService.LOG_ERROR:
				if (entry.getBundle()!= null)
					Logger.getLogger(entry.getBundle().getSymbolicName()).error(log);
				else
					Logger.getLogger(this.getClass()).error(log);
				break;
			default:
				if (entry.getBundle()!= null)
					Logger.getLogger(entry.getBundle().getSymbolicName()).debug(log);
				else
					Logger.getLogger(this.getClass()).debug(log);
		}
		
		Throwable exception= entry.getException();
		if (exception!= null)
			exception.printStackTrace();
	}

	private Object getLevelAsString(int level) 
	{
		switch (level) {
		case LogService.LOG_DEBUG:
			return("DEBUG");
		case LogService.LOG_INFO:
			return("INFO");
		case LogService.LOG_WARNING:
			return("WARN");
		case LogService.LOG_ERROR:
			return("ERROR");

		default:
			return("UNKNOWN");
		}
	}
// ============================= start: LogReaderService
	private LogReaderService logReaderService;
	
	@Reference(unbind="unsetLogReaderService", cardinality=ReferenceCardinality.MANDATORY, policy=ReferencePolicy.STATIC)
	public void setLogReaderService(LogReaderService logReaderService) 
	{
		this.logReaderService = logReaderService;
	}
	
	public void unsetLogReaderService(LogReaderService logReaderService) {
		this.logReaderService = null;
	}

	public LogReaderService getLogReaderService() 
	{
		return logReaderService;
	}
// ============================= end: LogReaderService
	
	class MyBundleListener implements BundleListener
	{

		@Override
		public void bundleChanged(BundleEvent event) 
		{
			switch (event.getType()) {
			case BundleEvent.INSTALLED:
				break;
			case BundleEvent.STARTED:
				break;
			case BundleEvent.RESOLVED:
				break;
			default:
				break;
			}
		}
	}
	
	class MyServiceListener implements ServiceListener
	{
		@Override
		public void serviceChanged(ServiceEvent event) 
		{
			switch (event.getType()) {
			case ServiceEvent.REGISTERED:
				break;
			case ServiceEvent.UNREGISTERING:
				break;
			default:
				break;
			}
		}
	}
	
	class MyFrameworkListener implements FrameworkListener
	{
		@Override
		public void frameworkEvent(FrameworkEvent event) 
		{
			switch (event.getType()) {
			case FrameworkEvent.STARTED:
				break;
			case BundleEvent.STARTED:
				break;
			case BundleEvent.RESOLVED:
				break;
			default:
				break;
			}
		}
	}
	
	@Activate
	public void activate(ComponentContext componentContext)
	{
		componentContext.getBundleContext().addBundleListener(new MyBundleListener());
		componentContext.getBundleContext().addServiceListener(new MyServiceListener());
		componentContext.getBundleContext().addFrameworkListener(new MyFrameworkListener());
		
		if (logReaderService!= null)
			logReaderService.addLogListener(this);
	}
	
	public void deactivate(ComponentContext componentContext)
	{
		logReaderService.removeLogListener(this);
	}
}

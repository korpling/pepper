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

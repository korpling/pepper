package org.corpus_tools.pepper.service.interfaces;

import java.net.URI;
import java.util.HashMap;

import org.apache.commons.lang3.Conversion;
import org.corpus_tools.pepper.common.MODULE_TYPE;

public interface PepperService{
		
	/* GETTERS */
	
	/** provides a new job id when user wants to create a new job */
	public String createNewJob();
	
	/** provides conversion results as zip-file */
	public URI getResultsAsZip(String jobId);
	
	/** provides conversion results as tar.gz-file */
	public URI getResultsAsTAR(String jobId);
	
	/** provides workflow file */ //TODO delete workflow file generation by GUI, that does not make ANY sense
	public byte[] getWorkflowXML(String jobId);
	
	/** a method to retrieve a modules current progress */
	public double getProgress(String runningModuleId);	
	
	/** provides module description */
	public String getModuleDescription(String moduleName);
	
	/** provide module properties */
	public String getModuleProperties(String moduleName);
	
	/** provide module type */
	public String getModuleType(String moduleName);
		
	/* SETTERS */
	
	//TODO PepperModuleDesc mit JAXB -> json serialisieren
	
	/* TRIGGERS */
	
	/** start a configured {@link Conversion} 
	 * @return error code 
	 */
	public String startConversion(String jobId);
	
	/**
	 * cancels a job execution
	 */
	public String cancelConversion(String jobId, String DocumentId);
	
	/** declares a job as obsolete.
	 * @return error code 
	 */
	public String killJob(String jobId);
}

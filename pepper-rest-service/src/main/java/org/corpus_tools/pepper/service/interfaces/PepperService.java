package org.corpus_tools.pepper.service.interfaces;

import org.corpus_tools.pepper.connectors.PepperConnector;


public interface PepperService{
	
	public void setPepper(PepperConnector pepperConnector);
	
	/* JOB */
	
	
	/* MODULES */
	public String moduleDescription(String moduleName);
}

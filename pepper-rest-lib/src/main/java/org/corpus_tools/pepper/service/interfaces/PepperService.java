package org.corpus_tools.pepper.service.interfaces;

import javax.ws.rs.core.MediaType;

public interface PepperService{
	
	/* constants */
	
	public static final String DATA_FORMAT = MediaType.APPLICATION_XML;	
	public static final String PATH_ALL_MODULES = "modules";
	
	/* JOB */
	
	
	/* MODULES */
	
	public String allModules();
}

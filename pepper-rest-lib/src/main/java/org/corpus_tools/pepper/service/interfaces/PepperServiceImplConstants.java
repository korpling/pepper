package org.corpus_tools.pepper.service.interfaces;

import java.io.File;

import javax.ws.rs.core.MediaType;

public interface PepperServiceImplConstants {
	
	public static final String TARGET_ROOT_DIR = "target"+File.separator;
	
	public static final String DATA_FORMAT = MediaType.APPLICATION_XML;	
	public static final String LOCAL_JOB_DIR = "jobs"+File.separator;
	
	public static final long DATA_LIFETIME = 200000L; //TODO make a pepper property
	
}

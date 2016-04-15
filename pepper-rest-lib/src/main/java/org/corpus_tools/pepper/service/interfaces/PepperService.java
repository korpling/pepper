package org.corpus_tools.pepper.service.interfaces;

import java.io.File;

import javax.ws.rs.core.MediaType;

public interface PepperService{
		
	/* JOB */
	
	public String createJob(String jobDesc);
	public String getStatusReport(String jobId);		
	
	/* MODULES */
	
	public String getModuleList();
	public String getModule(String moduleId);
	
	/* DATA */
	
	public void setData(String jobId, byte[] data);
	public byte[] getConvertedDocuments(String jobId);
	
	/* ABOUT PEPPER */
	
	//NOTHING SO FAR
}

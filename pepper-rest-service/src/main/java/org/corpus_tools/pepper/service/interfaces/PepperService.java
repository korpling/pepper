package org.corpus_tools.pepper.service.interfaces;

import java.io.FileOutputStream;

public interface PepperService{
		
	/* JOB */
	
	public String createJob(String jobDesc);
	public String getStatusReport(String jobId);		
	
	/* MODULES */
	
	public String getModuleList();
	public String getModule(String moduleId);
	
	/* DATA */
	
	public void setData(String jobId, String importPath, byte[] data);
	public byte[] getConvertedDocuments(String jobId, String formatPath);
	
	/* ABOUT PEPPER */
	
	//NOTHING SO FAR
}

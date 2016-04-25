package org.corpus_tools.pepper.service.internal;

import java.util.HashMap;

import org.corpus_tools.pepper.service.interfaces.PepperServiceImplConstants;

/** This class takes care of obsolete conversion data */
public class DataManager {
	
	//TODO take care of synchronization
	
	private HashMap<String, Long> jobs = null;
	
	private DataManager(long timeOut){
		jobs = new HashMap<String, Long>();
		FilePatrol fp = new FilePatrol(this, timeOut, "FILE_PATROL");
		fp.start();
	}
	
	private static DataManager instance = null;
	
	public static DataManager getInstance(){
		if (instance==null){
			instance = new DataManager(PepperServiceImplConstants.DATA_LIFETIME);
		}
		return instance;
	}
	
	protected HashMap<String, Long> getJobsWithStartTimes(){
		return jobs;
	}
	
	public void put(String jobId){
		jobs.put(jobId, System.currentTimeMillis());
	}
	
	protected void remove(String jobId){
		jobs.remove(jobId);
	}
}

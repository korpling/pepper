package org.corpus_tools.pepper.service.internal;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.corpus_tools.pepper.service.interfaces.PepperServiceImplConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilePatrol extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(FilePatrol.class);
	
	private final long timeOut;
	
	private DataManager manager = null;
	
	private Thread thread = null;
	private String threadName = null;
	
	protected FilePatrol(DataManager manager, long timeOut, String threadName){
		this.manager = manager;
		this.timeOut = timeOut;
		this.threadName = threadName; 
	}
	
	@Override
	public void start(){
		if (thread==null){
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
	
	@Override
	public void run() {
		while(true){
			try {
				synchronized(this){
					for (Entry<String, Long> e : manager.getJobsWithStartTimes().entrySet()){
						File jobDir = null;
						if (System.currentTimeMillis()-e.getValue()>timeOut){
							jobDir = new File(PepperServiceImplConstants.LOCAL_JOB_DIR+e.getKey()+File.separator);
							if (jobDir.exists()){
								FileUtils.deleteDirectory(jobDir);
								manager.remove(e.getKey());
							}
						}
					}				
					wait(timeOut);
				}
			} catch (InterruptedException | IOException e) {
				logger.warn(e+" occured at "+System.currentTimeMillis()+" ("+e.getMessage()+").");				
			}
		}
	}

}

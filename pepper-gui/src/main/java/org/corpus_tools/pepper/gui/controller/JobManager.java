package org.corpus_tools.pepper.gui.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.corpus_tools.pepper.gui.client.ServiceConnector;
import org.corpus_tools.pepper.gui.components.ProgressDisplay;
import org.corpus_tools.pepper.service.adapters.PepperJobReportMarshallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobManager implements Runnable {
	
	public static final long UPDATE_INTERVAL = 2000L;
	private static final String WARN_INTERRUPTED_EXCEPTION = "An Interruption exception occured while checking for progress.";
	
	private static final Logger logger = LoggerFactory.getLogger(JobManager.class);
	
	private Set<String> registeredJobs = null;
	private final ServiceConnector serviceConnector;
	private boolean update = false;
	private ProgressDisplay resultsView = null;
	
	public JobManager(ServiceConnector serviceConnector, ProgressDisplay progressDisplay) {
		this.serviceConnector = serviceConnector;
		this.registeredJobs = new HashSet<String>();
		this.resultsView = progressDisplay;
	}
	
	public void add(String jobId){
		if (validId(jobId)){
			registeredJobs.add(jobId);
		}
	}
	
	private boolean validId(String jobId) {
		// TODO
		return true;
	}

	public void checkForProgress(boolean update){
		this.update = update;
	}
	
	@Override
	public void run() {
		Set<String> done;
		while (!registeredJobs.isEmpty() && update){
			done = new HashSet<String>();
			for (String jobId : registeredJobs){
				boolean allModulesDone = true;
			
				Collection<PepperJobReportMarshallable.PathProgress> progress = serviceConnector.getProgress(jobId);
				logger.info("registered jobs: " + registeredJobs);
				logger.info("Reported progess: " + progress);
				for (PepperJobReportMarshallable.PathProgress prog : progress){
					resultsView.addElement(prog.getPath());
					float p = (float) prog.getProgress();
					resultsView.setProgress(prog.getPath(), p);
					allModulesDone &= p==1.0f;					
				}
				if (allModulesDone){
					done.add(jobId);
				}
				try {
					Thread.sleep(UPDATE_INTERVAL);
				} catch (InterruptedException e) {
					logger.warn(WARN_INTERRUPTED_EXCEPTION);
				}
			}
			for (String kickId : done){
				registeredJobs.remove(kickId);
			}
		}
	}
	
}

package org.corpus_tools.pepper.service.internal;

import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperJob;

public class PepperServiceJobRunner implements Runnable{

	private final Pepper pepper;
	private final PepperJob job;
	
	public PepperServiceJobRunner(Pepper pepper, String jobId) {
		this.pepper = pepper;
		this.job = pepper.getJob(jobId);
	}
	
	@Override
	public void run() {
		job.convert();
	}

}

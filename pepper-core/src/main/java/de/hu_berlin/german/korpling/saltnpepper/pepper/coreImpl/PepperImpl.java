package de.hu_berlin.german.korpling.saltnpepper.pepper.coreImpl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.core.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJob;

public class PepperImpl implements Pepper{

	/** {@inheritDoc Pepper#createJob()} */
	public String createJob()
	{
		return null;
	}
	
	/** {@inheritDoc Pepper#getJob(String)} */
	public PepperJob getJob(String jobId)
	{
		return null;
	}
	
	/** {@inheritDoc Pepper#deleteJob(String)} */
	public boolean deleteJob(String jobId)
	{
		return false;
	}

	/** {@inheritDoc Pepper#containsJob(String)} */
	public Boolean containsJob(String jobId)
	{
		return null;
	}
}

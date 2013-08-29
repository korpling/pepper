package de.hu_berlin.german.korpling.saltnpepper.pepper.core;

public interface Pepper {

	/**
	 * Creates a {@link PepperJob} object and returns a unique identifier to identify the created job.
	 * @return unique identifier for created object
	 */
	public String createJob();
	
	/**
	 * Returns a {@link PepperJob} corresponding to the given identifier
	 * @param jobId unique identifier for the job
	 * @return returns the job
	 */
	public PepperJob getJob(String jobId);
	
	/**
	 * Returns whether this {@link Pepper} object contains a job corresponding to the given identifier. 
	 * @param jobId unique identifier for the job
	 * @return true, if such a job is contained, false otherwise
	 */
	public Boolean containsJob(String jobId);
	
	/**
	 * Removes the job corresponding to the given unique identifier.
	 * @param jobId unique identifier 
	 * @return true if job was removed successful 
	 */
	public boolean deleteJob(String jobId);
}

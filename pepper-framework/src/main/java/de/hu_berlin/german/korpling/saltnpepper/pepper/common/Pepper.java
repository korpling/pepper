package de.hu_berlin.german.korpling.saltnpepper.pepper.common;

import java.util.Collection;

import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.JobNotFoundException;

/**
 * 
 * @author Florian Zipser
 *
 */
public interface Pepper {	
	/**
	 * Creates a new {@link PepperJob} object for a new conversion process.
	 * @return identifier of the new created {@link PepperJob}
	 */
	public String createJob();
	/**
	 * Returns a {@link PepperJob} corresponding to the passed id.
	 * @param id identifier of a {@link PepperJob}
	 * @return a {@link PepperJob} corresponding to the passed id
	 */
	public PepperJob getJob(String id) throws JobNotFoundException;
	/**
	 * Removes the {@link PepperJob} corresponding to the passed identifier,
	 * if a job exists.
	 * @param id identifier of a {@link PepperJob}
	 * @return true, if job was removed successfully, false otherwise
	 * @throws JobNotFoundException
	 */
	public boolean removeJob(String id) throws JobNotFoundException;
	
	/**
	 * Returns a collection of all {@link PepperModuleDesc} corresponding to Pepper modules, which
	 * are registered in this {@link Pepper} instance.
	 * <br/>
	 * Note: Depending on the implementation, the computation of this result, can take a time. It could be 
	 * useful, to store the returned list in case of multiple calls.
	 * @return a collection of all {@link PepperModuleDesc} objects corresponding to Pepper modules 
	 */
	public Collection<PepperModuleDesc> getRegisteredModules();
	/**
	 * Returns a string representation of the method {@link #getRegisteredModules()}.
	 * @return a string representation of all registered modules
	 */
	public String getRegisteredModulesAsString();
}

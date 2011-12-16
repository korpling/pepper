package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * Objects of this class stores the status of a step in Pepper workflow.
 * A step is a tuple containing a {@link PepperModuleController} object, the current status of the processing
 * of the {@link PepperModuleController} of a specific {@link SDocument} and in case, the process has ended somehow (
 * {@link PEPPER_SDOCUMENT_STATUS#COMPLETED}, {@link PEPPER_SDOCUMENT_STATUS#FAILED} or {@link PEPPER_SDOCUMENT_STATUS#DELETED}),
 * the needed time to process is also stored.  
 */
public class StepStatus
{	
	/**
	 * {@link PepperModuleController} object which is processing the {@link SDocument} in status {@link PEPPER_SDOCUMENT_STATUS}
	 */
	private volatile PepperModuleController pModuleController= null;
	/**
	 * Returns the {@link PepperModuleController} object.
	 * @return
	 */
	public PepperModuleController getpModuleController() {
		return pModuleController;
	}
	/**
	 * Sets the {@link PepperModuleController} object
	 * @param pModuleController
	 */
	public void setpModuleController(PepperModuleController pModuleController) {
		this.pModuleController = pModuleController;
	}

	private volatile PEPPER_SDOCUMENT_STATUS moduleStatus= null;
	
	public PEPPER_SDOCUMENT_STATUS getModuleStatus() {
		return moduleStatus;
	}

	public void setModuleStatus(PEPPER_SDOCUMENT_STATUS moduleStatus) {
		if (PEPPER_SDOCUMENT_STATUS.IN_PROCESS.equals(moduleStatus))
			this.startTime= System.nanoTime();
		else if (	(PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(moduleStatus))||
					(PEPPER_SDOCUMENT_STATUS.DELETED.equals(moduleStatus)) ||
					(PEPPER_SDOCUMENT_STATUS.FAILED.equals(moduleStatus)))
		{
			this.endTime= System.nanoTime();
		}
		this.moduleStatus = moduleStatus;
	}

	/**
	 * Stores the time, when the process ended (ended by {@link PEPPER_SDOCUMENT_STATUS#COMPLETED}, {@link PEPPER_SDOCUMENT_STATUS#DELETED} or 
	 * {@link PEPPER_SDOCUMENT_STATUS#FAILED}). 
	 */
	private volatile Long endTime= null;
	/**
	 * Sores the time, when the process was started.
	 */
	private volatile Long startTime= null;

	/**
	 * Returns the time, the process is running. Running means having status {@link PEPPER_SDOCUMENT_STATUS#IN_PROCESS}.
	 * When the process has not been started, null would be returned.
	 * @return time, the process is running
	 */
	public Long getRunTime() 
	{
		if (startTime== null)
			return(null);
		else
		{
			Long time= 0l;
			if (endTime!= null)
				time= endTime;
			else
				time= System.nanoTime();
			return(time-startTime);
		}
	}
}

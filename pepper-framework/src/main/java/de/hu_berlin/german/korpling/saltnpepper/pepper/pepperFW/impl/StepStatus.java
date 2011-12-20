/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * Objects of this class stores the status of a step in Pepper workflow.
 * A step is a tuple containing a {@link PepperModuleController} object, the current status of the processing
 * of the {@link PepperModuleController} of a specific {@link SDocument} and in case, the process has ended somehow (
 * {@link PEPPER_SDOCUMENT_STATUS#COMPLETED}, {@link PEPPER_SDOCUMENT_STATUS#FAILED} or {@link PEPPER_SDOCUMENT_STATUS#DELETED}),
 * the needed time to process is also stored.  
 * A percentage of the progress (a value between 0 and 1) also can be stored by calling {@link #setModuleStatus(PEPPER_SDOCUMENT_STATUS)} and {@link #getPercentage()},
 * if a percentage is not set, it will be 0 as long as the status is {@link PEPPER_SDOCUMENT_STATUS#NOT_STARTED} or {@link PEPPER_SDOCUMENT_STATUS#IN_PROCESS}
 * and set to 1 if the status is {@link PEPPER_SDOCUMENT_STATUS#COMPLETED}, {@link PEPPER_SDOCUMENT_STATUS#DELETED} or {@link PEPPER_SDOCUMENT_STATUS#FAILED}.
 */
public class StepStatus
{	
	/**
	 * {@link SElementId} object representing the document id of the {@link SDocument} object which is processed
	 * in this step. 
	 */
	private volatile SElementId sDocumentId= null;
	
	/**
	 * Sets the {@link SElementId} object representing the document id of the {@link SDocument} object which is processed
	 * in this step.
	 * @param sDocumentId id of the {@link SDocument} object
	 */
	public void setSDocumentId(SElementId sDocumentId) {
		this.sDocumentId = sDocumentId;
	}
	/**
	 * Sets the {@link SElementId} object representing the document id of the {@link SDocument} object which is processed
	 * in this step.
	 * @return id of the {@link SDocument} object 
	 */
	public SElementId getSDocumentId() {
		return sDocumentId;
	}
	
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
	
	/**
	 * Returns the percentage of the progress, the current {@link PepperModule} has reached so far. If the value
	 * was set manually, it is a value between 0 and 1. If the value is computed automatically, the value is either 0
	 * for statuses {@link PEPPER_SDOCUMENT_STATUS#NOT_STARTED} and {@link PEPPER_SDOCUMENT_STATUS#IN_PROCESS} or 
	 * 1 for statuses {@link PEPPER_SDOCUMENT_STATUS#COMPLETED}, {@link PEPPER_SDOCUMENT_STATUS#DELETED} and 
	 * {@link PEPPER_SDOCUMENT_STATUS#FAILED}..
	 * @return a value between 0 and 1
	 */
	public double getPercentage() {
		Double retVal= 0.0;
		switch (this.getModuleStatus()) {
		case COMPLETED:
			retVal= 1.0;
			break;
		case DELETED:
			retVal= 1.0;
			break;
		case FAILED:
			retVal= 1.0;
			break;
		case IN_PROCESS:
			retVal= this.getpModuleController().getProgress(this.getSDocumentId());
			if (retVal== null)
				retVal= 0.0;
			break;
		default:
			retVal= 0.0;
			break;
		}
		return retVal;
	}
}

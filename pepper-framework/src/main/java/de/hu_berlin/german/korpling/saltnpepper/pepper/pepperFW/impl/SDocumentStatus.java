package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl;

import java.util.Vector;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * Stores the global status for a Document. And a list of tuples for all Modules and their current 
 * status.  
 */
public class SDocumentStatus
{
	/**
	 * Initializes an object of this class. The default value of {@link PEPPER_SDOCUMENT_STATUS} will be set to 
	 * {@link PEPPER_SDOCUMENT_STATUS#NOT_STARTED}
	 */
	public SDocumentStatus()
	{
		this.globalStatus= PEPPER_SDOCUMENT_STATUS.NOT_STARTED;
	}
	/**
	 * {@link SElementId} of the {@link SDocument} object, to which this object belongs to
	 */
	private volatile SElementId sDocumentId= null;
	
	/**
	 * Returns the {@link SElementId} of the {@link SDocument} object, to which this object belongs to. 
	 * @return document id
	 */
	public SElementId getsDocumentId() {
		return sDocumentId;
	}

	/**
	 * Sets the {@link SElementId} of the {@link SDocument} object, to which this object belongs to.
	 * @param sDocumentId corresponding to {@link SDocument} object sto which this object belongs to 
	 */
	public void setsDocumentId(SElementId sDocumentId) {
		this.sDocumentId = sDocumentId;
	}

	/**
	 * Represents the global status of a single document computed out of all Pepper modules, which has to process that
	 * {@link SDocument}. The global status represents the minimal status of all Pepper modules. The global status
	 * is set to {@link PEPPER_SDOCUMENT_STATUS#COMPLETED} when each module has set the status to PEPPER_SDOCUMENT_STATUS#COMPLETED. 
	 */
	private volatile PEPPER_SDOCUMENT_STATUS globalStatus= null;
	
	/**
	 * Returns the global status of this object. The global status is determined by each {@link StepStatus} object being 
	 * contained in this object. 
	 * <ul>
	 * 	<li>If one of the contained {@link StepStatus} objects status value is set to {@link PEPPER_SDOCUMENT_STATUS#IN_PROCESS}, the global status will be {@link PEPPER_SDOCUMENT_STATUS#IN_PROCESS}.</li>
	 *  <li>If one of the contained {@link StepStatus} objects status value is set to {@link PEPPER_SDOCUMENT_STATUS#DELETED}, the global status will be {@link PEPPER_SDOCUMENT_STATUS#DELETED}.</li>
	 *  <li>If one of the contained {@link StepStatus} objects status value is set to {@link PEPPER_SDOCUMENT_STATUS#FAILED}, the global status will be {@link PEPPER_SDOCUMENT_STATUS#FAILED}.</li>
	 *  <li>Only if each contained {@link StepStatus} objects status value is set to {@link PEPPER_SDOCUMENT_STATUS#COMPLETED}, the global status will be {@link PEPPER_SDOCUMENT_STATUS#COMPLETED}.</li>
	 * </ul>  
	 * @return
	 */
	public PEPPER_SDOCUMENT_STATUS getGlobalStatus() {
		return globalStatus;
	}
	
	
	/**
	 * This list contains the status of all Pepper modules corresponding to the current {@link SDocument}.
	 */
	private volatile Vector<StepStatus> stepStatuses= null;
	
	/**
	 * Creates a new step, in which the {@link SDocument} object has to be process by the given {@link PepperModuleController}
	 * object. The status of the {@link StepStatus} object will be set to {@link PEPPER_SDOCUMENT_STATUS#NOT_STARTED}.
	 * @param pepperModuleController
	 */
	public void addPepperModule(PepperModuleController pepperModuleController)
	{
		if (stepStatuses== null)
		{
			synchronized (this) {
				if (stepStatuses== null)
					stepStatuses= new Vector<StepStatus>();
			}
		}
		StepStatus stepStatus= new StepStatus();
		stepStatus.setSDocumentId(this.getsDocumentId());
		stepStatus.setpModuleController(pepperModuleController);
		stepStatus.setModuleStatus(PEPPER_SDOCUMENT_STATUS.NOT_STARTED);
		stepStatuses.add(stepStatus);
	}
	
	/**
	 * Updates the status of a specified {@link StepStatus} object. The {@link StepStatus} object is specified by the
	 * given {@link PepperModuleController} object and will be set to the given {@link PEPPER_SDOCUMENT_STATUS}.
	 * @param pModuleController determines the {@link StepStatus} object
	 * @param status the status to which the {@link StepStatus} shall be set to.
	 */
	public void update(	PepperModuleController pModuleController, 
						PEPPER_SDOCUMENT_STATUS status) 
	{
		boolean found= false;
		for (StepStatus stepStatus: this.getStepStatuses())
		{
			if (stepStatus.getpModuleController().equals(pModuleController))
			{
				this.update(stepStatus, status);
				found= true;
				break;
			}
		}
		if (!found)
			throw new PepperConvertException("Cannot add sDocument status, because the PepperModuleController is not registered. Please add it first by using getPepperModuleControllers.add().");
	}
	
	/**
	 * Updates the status of a specified {@link StepStatus} object. The {@link StepStatus} will be set to the 
	 * given {@link PEPPER_SDOCUMENT_STATUS}. {@link PEPPER_SDOCUMENT_STATUS}
	 * A step status can only be updated in following cases
	 * <ol>
	 * 	<li>{@link PEPPER_SDOCUMENT_STATUS#NOT_STARTED} --> {@link PEPPER_SDOCUMENT_STATUS#IN_PROCESS}</li>
	 *  <li>{@link PEPPER_SDOCUMENT_STATUS#IN_PROCESS} --> {@link PEPPER_SDOCUMENT_STATUS#COMPLETED}</li>
	 *  <li>{@link PEPPER_SDOCUMENT_STATUS#IN_PROCESS} --> {@link PEPPER_SDOCUMENT_STATUS#FAILED}</li>
	 *  <li>{@link PEPPER_SDOCUMENT_STATUS#IN_PROCESS} --> {@link PEPPER_SDOCUMENT_STATUS#DELETED}</li>
	 * </ol>
	 * @param stepStatus determines the {@link StepStatus} object
	 * @param status the status to which the {@link StepStatus} shall be set to.
	 */
	public void update(	StepStatus stepStatus, 
						PEPPER_SDOCUMENT_STATUS status) 
	{
		if (	(PEPPER_SDOCUMENT_STATUS.NOT_STARTED.equals(stepStatus.getModuleStatus()))&&
				(PEPPER_SDOCUMENT_STATUS.IN_PROCESS.equals(status)))
		{
			stepStatus.setModuleStatus(status);
		}
		else if (	(PEPPER_SDOCUMENT_STATUS.IN_PROCESS.equals(stepStatus.getModuleStatus()))&&
					(	(PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(status))||
						(PEPPER_SDOCUMENT_STATUS.FAILED.equals(status))||
						(PEPPER_SDOCUMENT_STATUS.DELETED.equals(status))))
		{
			stepStatus.setModuleStatus(status);
		}
		else 
			throw new PepperConvertException("Cannot update status of sDocument '"+sDocumentId+"', because the level of current status '"+stepStatus.getModuleStatus().getValue()+"' is higher or equal than the givenstatus '"+status+"'.");
		this.updateGlobalStatus();
	}
	
	/**
	 * Updates the global status of this object. This will be done, by checking all the status of
	 * the contained {@link StepStatus} objects.
	 */
	private void updateGlobalStatus()
	{
		EList<PEPPER_SDOCUMENT_STATUS> currStatus= new BasicEList<PEPPER_SDOCUMENT_STATUS>();
		//create a list of all status
		for (StepStatus stepStatus: this.getStepStatuses())
		{
			if (!currStatus.contains(stepStatus.getModuleStatus()))
			{
				currStatus.add(stepStatus.getModuleStatus());
			}
		}

		if (currStatus.contains(PEPPER_SDOCUMENT_STATUS.DELETED))
		{//if one PepperModuleController says deleted, status is deleted
			globalStatus= PEPPER_SDOCUMENT_STATUS.DELETED;
		}//if one PepperMOduleController says deleted, status is deleted
		else if (currStatus.contains(PEPPER_SDOCUMENT_STATUS.IN_PROCESS))
		{//if one PepperModuleController says IN_PROCESS, status is IN_PROCESS
			globalStatus= PEPPER_SDOCUMENT_STATUS.IN_PROCESS;
		}//if one PepperModuleController says IN_PROCESS, status is IN_PROCESS
		else if (currStatus.contains(PEPPER_SDOCUMENT_STATUS.FAILED))
		{//if one PepperModuleController says FAILED, status is FAILED
			globalStatus= PEPPER_SDOCUMENT_STATUS.FAILED;
		}//if one PepperModuleController says FAILED, status is FAILED
		else if (	(currStatus.contains(PEPPER_SDOCUMENT_STATUS.COMPLETED))&&
					(currStatus.size()== 1))
		{//only contains status COMPLETED	
			globalStatus= PEPPER_SDOCUMENT_STATUS.COMPLETED;
		}//only contains status COMPLETED
	}
	
	/**
	 * Finds and returns the {@link StepStatus} object corresponding to the given {@link PepperModuleController} object. 
	 * @param pModuleController {@link PepperModuleController} specifying the {@link StepStatus} to be found 
	 * @return {@link StepStatus} object matching to the given {@link PepperModuleController} object
	 */
	public StepStatus findStepStatus(PepperModuleController pModuleController)
	{
		StepStatus retVal= null;
		for (StepStatus stepStatus: this.getStepStatuses())
		{
			if (stepStatus.getpModuleController().equals(pModuleController))
			{
				retVal= stepStatus;
				break;
			}
		}
		return(retVal);
	}
	
	/**
	 * Returns all {@link StepStatus} object being part of this {@link SDocumentStatus} object. 
	 * @return
	 */
	public Vector<StepStatus> getStepStatuses()
	{
		return(stepStatuses);
	}
	
	/**
	 * Returns the percentage value of the process of the current {@link SDocument}. 
	 * @return percentage value between 0 and 1.
	 */
	public double getPercentage()
	{
		if (PEPPER_SDOCUMENT_STATUS.DELETED.equals(globalStatus))
			return(1.0);
		else if (PEPPER_SDOCUMENT_STATUS.FAILED.equals(globalStatus))
			return(1.0);
		else if (PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(globalStatus))
			return(1.0);
		else
		{
//			double p = 1l / Double.valueOf(stepStatuses.size());
			double p_total= 0;
			for (StepStatus status: stepStatuses)
			{
				p_total=p_total+ (status.getPercentage()/ Double.valueOf(stepStatuses.size()));
//				if (PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(status.getModuleStatus()))
//					p_total=p_total+p;
			}
			return(p_total);
		}
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.common;

import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.dmt.Uri;

import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.WorkflowException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;

public abstract class PepperJob {
	/** Identifier of this job. Should be unique in Pepper. **/
	protected String id= null;
	/**
	 * Returns the unique identifier for this job.
	 * @return unique identifier.
	 */
	public String getId() {
		return id;
	}
	/** status of job **/
	protected JOB_STATUS status= JOB_STATUS.NOT_STARTED;
	/**
	 * Returns the current status of this job.
	 * @return status of this job
	 */
	public JOB_STATUS getStatus(){
		return(status);
	}
	/**
	 * Returns a formated string as a kind of a document centric progress status. For each document its overall status
	 * and specific statuses in single modules are included. 
	 * @return a report of the progress status
	 */
	public abstract String getStatusReport();
	
	/** list of all step descriptions **/
	protected Vector<StepDesc> stepDescs= null;
	
	/**
	 * Returns a list of all step descriptions. In order of how the {@link StepDesc} objects have been added via
	 * method {@link #addStep(StepDesc)}.
	 * @return list of {@link StepDesc} objects
	 */
	public List<StepDesc> getStepDescs(){
		if (stepDescs== null){
			synchronized (this) {
				if (stepDescs== null){
					stepDescs= new Vector<StepDesc>();
				}
			}
		}
		return(stepDescs);
	}
	
	/**
	 * Adds a {@link StepDesc} object to job.
	 * @param stepDesc description object of a particular step
	 */
	public void addStepDesc(StepDesc stepDesc){
		if (stepDesc== null)
			throw new WorkflowException("Cannot deal with an empty StepDesc object for job '"+getId()+"'.");
		getStepDescs().add(stepDesc);
	}

	/**
	 * Starts the conversion of this job.
	 * <ul>
	 * <li>If the single steps of the job has not already been wired, they will be wired.<li>
	 * <li>If {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter#importCorpusStructure(de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph)} has not already been called, it will be done.<li>
	 * </ul>
	 */
	public abstract void convert();

	/**
	 * Imports a {@link SaltProject} from any format. For conversion a process can be modeled, similar to {@link #convert()}
	 * with the difference, that no {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter} could be defined. Instead, the processed {@link SaltProject} is
	 * the result. 
	 */
	public abstract void convertFrom();

	/**
	 * Exports the SaltProject into any format. For conversion, a normal process could be created, 
	 * except the use of an importer. Here the do-nothing importer is used, and it is expected, that the {@link SaltProject}
	 * is already 'filled'.
	 */
	public abstract void convertTo();
	
	/**
	 * Loads a serialization of a {@link PepperJob} and fills this object with these entries. 
	 * <strong>
	 * The current implementation only allows xml files following the schema of PepperParams.
	 * <br/>
	 * Only local files are supported. 
	 * </strong>
	 * @param uri path of file to load.
	 */
	public abstract void load(URI uri);
	
	/**
	 * Loads a job from the passed {@link Uri}. The job has to follow ???schema???.
	 */
	public void save(){
		
	}
	
	/**
	 * Returns a textual representation of this Pepper job.
	 * <strong>Note: This representation could not be used for serialization/deserialization purposes.</strong> 
	 * @return textual representation
	 */
	public String toString(){
		StringBuilder str= new StringBuilder();
		str.append(getId());
		
		if (getStepDescs()!= null){
			str.append("{");
			for (StepDesc stepDesc:getStepDescs()){
				str.append(stepDesc.getName());
				str.append(", ");
			}
			str.append("}");
		}
		return(str.toString());
	}
}
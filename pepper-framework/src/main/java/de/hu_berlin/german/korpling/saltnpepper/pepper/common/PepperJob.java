/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.pepper.common;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.core.Step;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.WorkflowException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.ModuleController;
import de.hu_berlin.u.saltnpepper.salt.common.SaltProject;

public abstract class PepperJob {
	/** Identifier of this job. Should be unique in Pepper. **/
	protected String id = null;

	/**
	 * Returns the unique identifier for this job.
	 * 
	 * @return unique identifier.
	 */
	public String getId() {
		return id;
	}

	/**
	 * The base directory is either the directory from which the Pepper workflow
	 * description was loaded or the directory from which Pepper was started.
	 **/
	private URI baseDir = null;

	/**
	 * Sets the base directory for this {@link ModuleController}. The base
	 * directory is either the directory from which the Pepper workflow
	 * description was loaded or the directory from which Pepper was started.
	 * 
	 * @param baseDir
	 *            base directory
	 */
	public void setBaseDir(URI baseDir) {
		this.baseDir = baseDir;
	}

	/**
	 * Returns the base directory for this {@link ModuleController}. The base
	 * directory is either the directory from which the Pepper workflow
	 * description was loaded or the directory from which Pepper was started.
	 * 
	 * @return base directory
	 */
	public URI getBaseDir() {
		if (baseDir == null) {
			try {
				baseDir = URI.createFileURI(new File("./").getCanonicalPath());
			} catch (IOException e) {
				baseDir = URI.createFileURI(new File("./").getAbsolutePath());
			}
		}
		return (baseDir);
	}

	/** status of job **/
	protected JOB_STATUS status = JOB_STATUS.NOT_STARTED;

	/**
	 * Returns the current status of this job.
	 * 
	 * @return status of this job
	 */
	public JOB_STATUS getStatus() {
		return (status);
	}

	/**
	 * Returns a formated string as a kind of a document centric progress
	 * status. For each document its overall status and specific statuses in
	 * single modules are included.
	 * 
	 * @return a report of the progress status
	 */
	public abstract String getStatusReport();

	/** list of all step descriptions **/
	protected Vector<StepDesc> stepDescs = null;

	/**
	 * Returns a list of all step descriptions. In order of how the
	 * {@link StepDesc} objects have been added via method
	 * {@link #addStep(StepDesc)}.
	 * 
	 * @return list of {@link StepDesc} objects
	 */
	public List<StepDesc> getStepDescs() {
		if (stepDescs == null) {
			synchronized (this) {
				if (stepDescs == null) {
					stepDescs = new Vector<StepDesc>();
				}
			}
		}
		return (stepDescs);
	}

	/**
	 * Adds a {@link StepDesc} object to job.
	 * 
	 * @param stepDesc
	 *            description object of a particular step
	 */
	public void addStepDesc(StepDesc stepDesc) {
		if (stepDesc == null) {
			throw new WorkflowException("Cannot deal with an empty StepDesc object for job '" + getId() + "'.");
		}
		getStepDescs().add(stepDesc);
	}

/**
	 * Creates a {@link StepDesc} object an returns it, without adding the created {@link StepDesc} object
	 * to this {@link PepperJob} object. For adding that object, call {@link #addStepDesc(StepDesc)}. Or if you
	 * want to do that at once call {@link #createStepDesc(MODULE_TYPE)
	 * @return created {@link StepDesc} object
	 */
	public StepDesc createStepDesc() {
		return (new StepDesc());
	}

	/**
	 * Starts the conversion of this job.
	 * <ul>
	 * <li>If the single steps of the job has not already been wired, they will
	 * be wired.
	 * <li>
	 * <li>If
	 * {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter#importCorpusStructure(de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph)}
	 * has not already been called, it will be done.
	 * <li>
	 * </ul>
	 */
	public abstract void convert();

	/**
	 * Imports a {@link SaltProject} from any format. For conversion a process
	 * can be modeled, similar to {@link #convert()} with the difference, that
	 * no
	 * {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter}
	 * could be defined. Instead, the processed {@link SaltProject} is the
	 * result.
	 */
	public abstract void convertFrom();

	/**
	 * Exports the SaltProject into any format. For conversion, a normal process
	 * could be created, except the use of an importer. Here the do-nothing
	 * importer is used, and it is expected, that the {@link SaltProject} is
	 * already 'filled'.
	 */
	public abstract void convertTo();

	/**
	 * Loads a serialization of a {@link PepperJob} and fills this object with
	 * these entries. The current implementation allows xml files following the
	 * workflow description scheme version 1.0 and xmi files following the (old)
	 * PepperParams schema. <strong> Only local files are supported. </strong>
	 * 
	 * @param uri
	 *            path of file to load.
	 */
	public abstract void load(URI uri);

	/**
	 * Stores this job to the file at passed URI. If the URI points to a
	 * directory, a file having the name {@link #getId()}.pepper is created in
	 * the passed directory.
	 * 
	 * @param uri
	 *            The location where to store this job.
	 * @return Returns the URI where the Pepper job was stored, normally this is
	 *         the passed uri, but if the passed uri is a folder, than a file
	 *         having the job-if ({@link #getId()}).
	 */
	public abstract URI save(URI uri);

	/**
	 * Removes all {@link Step}s from all internal lists.
	 */
	public abstract void clear();

	/**
	 * Returns a textual representation of this Pepper job. <strong>Note: This
	 * representation could not be used for serialization/deserialization
	 * purposes.</strong>
	 * 
	 * @return textual representation
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(getId());

		if (getStepDescs() != null) {
			str.append("{");
			for (StepDesc stepDesc : getStepDescs()) {
				str.append(stepDesc.getName());
				str.append(", ");
			}
			str.append("}");
		}
		return (str.toString());
	}
}
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
package org.corpus_tools.pepper.common;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Set;

import org.corpus_tools.pepper.exceptions.JobNotFoundException;
import org.corpus_tools.pepper.impl.PepperImporterImpl;
import org.eclipse.emf.common.util.URI;

/**
 * 
 * @author Florian Zipser
 *
 */
public interface Pepper {
	/**
	 * Returns set configuration for {@link Pepper}.
	 * 
	 * @return
	 */
	public PepperConfiguration getConfiguration();

	/**
	 * Sets the configuration for {@link Pepper}.
	 * 
	 * @param configuration
	 */
	public void setConfiguration(PepperConfiguration configuration);

	/**
	 * Returns the names of importers which can import the data located at the
	 * specified <code>corpusPath</code>. If no importer was found for importing
	 * the returned list is empty, not null.
	 * 
	 * @param corpusPath
	 *            the path which should be checked by each importer
	 * @return names of importers how can import the data located at
	 *         <code>corpusPath</code>
	 */
	public Set<String> findAppropriateImporters(final URI corpusPath) throws FileNotFoundException;

	/**
	 * Creates a new {@link PepperJob} object for a new conversion process.
	 * 
	 * @return identifier of the new created {@link PepperJob}
	 */
	public String createJob();

	/**
	 * Returns a {@link PepperJob} corresponding to the passed id.
	 * 
	 * @param id
	 *            identifier of a {@link PepperJob}
	 * @return a {@link PepperJob} corresponding to the passed id
	 */
	public PepperJob getJob(String id) throws JobNotFoundException;

	/**
	 * Removes the {@link PepperJob} corresponding to the passed identifier, if
	 * a job exists.
	 * 
	 * @param id
	 *            identifier of a {@link PepperJob}
	 * @return true, if job was removed successfully, false otherwise
	 * @throws JobNotFoundException
	 */
	public boolean removeJob(String id) throws JobNotFoundException;

	/**
	 * Returns all {@link PepperModuleDesc} corresponding to a registered
	 * importer. When no importer is registered returns an empty collection, not
	 * null.
	 * 
	 * @return all importer fingerprints
	 */
	public Collection<PepperModuleDesc> getRegisteredImporters();

	/**
	 * Returns a collection of all {@link PepperModuleDesc} corresponding to
	 * Pepper modules, which are registered in this {@link Pepper} instance.
	 * <br/>
	 * Note: Depending on the implementation, the computation of this result,
	 * can take a time. It could be useful, to store the returned list in case
	 * of multiple calls.
	 * 
	 * @return a collection of all {@link PepperModuleDesc} objects
	 *         corresponding to Pepper modules
	 */
	public Collection<PepperModuleDesc> getRegisteredModules();

	/**
	 * Returns a string representation of the method
	 * {@link #getRegisteredModules()}.
	 * 
	 * @return a string representation of all registered modules
	 */
	public String getRegisteredModulesAsString();

	/**
	 * Checks if the Pepper framework is ready to run. This means, it checks if
	 * everything necessary is given and if all registered modules could be ran.
	 * This method can be used as a kind of integration test.
	 * 
	 * @return returns an empty list, if check was positive; if list is not
	 *         empty, each entry describes a single problem.
	 */
	@Deprecated
	public Collection<String> selfTest();

	/**
	 * Checks the health of each registered Pepper module. When a module is not
	 * healthy, it could not be started.
	 * 
	 * @return a list of health entries, one per module, if no module is
	 *         registered an empty list is returned
	 */
	public Collection<ModuleFitness> checkHealth();

	/**
	 * Checks the fitness of each registered Pepper module. The fitness of a
	 * module indicates in what way a module is docking to the Pepper interface.
	 * For instance it checks whether a module provides a contact address of the
	 * module'supplier. Or when the module is an importer whether it supports
	 * the {@link PepperImporterImpl#isImportable(URI)} method. <br/>
	 * Further the fitness says whether a module is ready to start.
	 * 
	 * @return a list of fitness entries, one per module, if no module is
	 *         registered an empty list is returned
	 */
	public Collection<ModuleFitness> checkFitness();
}

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
package de.hu_berlin.german.korpling.saltnpepper.pepper.common;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.JobNotFoundException;

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
	 * Returns if a corpus located at the given {@link URI} is importable by
	 * importer described in the passed {@link PepperModuleDesc}. If yes, 1 must
	 * be returned, if no 0 must be returned. If it is not quite sure, if the
	 * given corpus is importable by this importer any value between 0 and 1 can
	 * be returned. If this method is not overridden, null is returned.
	 * 
	 * @return 1 if corpus is importable, 0 if corpus is not importable, 0 < X <
	 *         1, if no definitiv answer is possible, null if method is not
	 *         overridden
	 */
	public Double isImportable(URI corpusPath, PepperModuleDesc description);

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
	 * Returns a collection of all {@link PepperModuleDesc} corresponding to
	 * Pepper modules, which are registered in this {@link Pepper} instance. <br/>
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
	public Collection<String> selfTest();
}

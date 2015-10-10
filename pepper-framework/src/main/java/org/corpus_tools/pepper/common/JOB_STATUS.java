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

import org.omg.CORBA.INITIALIZE;

/**
 * Status of a job in Pepper, the status could be one of the following:
 * <ul>
 * <li>{@link #NOT_STARTED}, if the job was not started</li>
 * <li>{@link #IN_PROGRESS}, if the job is running</li>
 * <li>{@link #ENDED}, if the job ended successful</li>
 * <li>{@link #ENDED_WITH_ERRORS}, if the job ended with errors</li>
 * </ul>
 * 
 * @author Florian Zipser
 *
 */
public enum JOB_STATUS {
	//
	NOT_STARTED("not started"),
	//
	INITIALIZING("initializing"),
	//
	IMPORTING_CORPUS_STRUCTURE("importing corpus structure"),
	//
	IMPORTING_DOCUMENT_STRUCTURE("importing document structure"),
	//
	ENDED("ended"),
	//
	ENDED_WITH_ERRORS("ended with errors");

	private String name = null;

	private JOB_STATUS(String name) {
		this.name = name;
	}

	public String toString() {
		return (name);
	}

	/**
	 * Returns whether the status determines, that the Pepper job is still in
	 * progress.
	 * 
	 * @return true, if the type equals {@link INITIALIZE},
	 *         {@link IMPORTING_CORPUS_STRUCTURE} or
	 *         {@link IMPORTING_DOCUMENT_STRUCTURE}
	 */
	public boolean isInProgress() {
		if (INITIALIZING.equals(this) || IMPORTING_CORPUS_STRUCTURE.equals(this) || IMPORTING_DOCUMENT_STRUCTURE.equals(this)) {
			return (true);
		} else {
			return (false);
		}
	}
}

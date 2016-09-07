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

import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;

/**
 * This Enumeration shows the status of a more or less currently processed
 * document. The several status act on three levels:
 * <ol>
 * <li>{@value #NOT_STARTED}</li>
 * <li>{@value #IN_PROGRESS}</li>
 * <li>
 * <ul>
 * <li>{@value #COMPLETED} means, mapping was successful</li>
 * <li>{@value #FAILED} means, mapping was not successful and ended with an
 * error</li>
 * <li>{@value #DELETED} means, mapping was successful and Mapped object
 * {@link SDocument} or {@link SCorpus} was deleted (for instance when merging
 * objects, and no further processing of mapped object is necessary)</li>
 * </ul>
 * </ol>
 * 
 * @author Florian Zipser
 */
public enum DOCUMENT_STATUS {
	NOT_STARTED(0, "NOT_STARTED"), IN_PROGRESS(1, "IN_PROGRESS"), COMPLETED(2, "COMPLETED"), DELETED(3,
			"DELETED"), FAILED(4, "FAILED");

	/** represents the level of the type */
	private final int value;

	/** a prosa name of the types */
	private final String name;

	private DOCUMENT_STATUS(int value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Returns the level of the object in level hierarchie.
	 * 
	 * @return level
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns a prosa name of the type
	 * 
	 * @return prosa name of the type
	 */
	public String getName() {
		return name;
	}
}

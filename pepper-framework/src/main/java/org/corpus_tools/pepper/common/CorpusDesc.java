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

import org.eclipse.emf.common.util.URI;

/**
 * This class realizes a description of a corpus to be imported or exported. The
 * description consists of a path addressing the location of the corpus and a
 * format description, in which the corpus currently is or is supposed to be
 * persist in.
 * 
 * @author Florian Zipser
 */
public class CorpusDesc {
	/**
	 * Initializes a new {@link CorpusDesc} object.
	 */
	public CorpusDesc() {
		super();
	}

	/** format description belonging to this corpus */
	protected FormatDesc formatDesc = null;

	/**
	 * Returns a set format description. If no {@link FormatDesc} object was
	 * set, a new one is created.
	 * 
	 * @return a format description
	 */
	public FormatDesc getFormatDesc() {
		if (formatDesc == null) {
			synchronized (this) {
				formatDesc = new FormatDesc();
			}
		}
		return formatDesc;
	}

	/**
	 * Sets the format description for this corpus description.
	 * 
	 * @param formatDesc
	 *            format description object
	 * @return this
	 */
	public CorpusDesc setFormatDesc(FormatDesc formatDesc) {
		this.formatDesc = formatDesc;
		return (this);
	}

	/** location of corpus **/
	protected URI corpusPath = null;

	/**
	 * Returns the path of where to store or from where to load this corpus.
	 * 
	 * @return location of corpus
	 */
	public URI getCorpusPath() {
		return corpusPath;
	}

	/**
	 * Sets the path of where to store or from where to load this corpus.
	 * 
	 * @param corpusPath
	 *            location of corpus
	 * @param this
	 *            object
	 */
	public CorpusDesc setCorpusPath(URI corpusPath) {
		this.corpusPath = corpusPath;
		return (this);
	}

	/**
	 * Returns a string representation of this object. <strong>Note: This
	 * representation cannot be used for serialization/deserialization
	 * purposes.</strong>
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(getCorpusPath());
		if (getFormatDesc() != null) {
			str.append("(");
			str.append(getFormatDesc().getFormatName());
			str.append(", ");
			str.append(getFormatDesc().getFormatVersion());
			str.append(")");
		}
		return (str.toString());
	}
}

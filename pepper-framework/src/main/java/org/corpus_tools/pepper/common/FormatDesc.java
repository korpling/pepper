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
 * This class is used by Pepper to describe a linguistic data format. Such an
 * object is used for instance to describe the set of formats which can be
 * handled by a {@link org.corpus_tools.pepper.modules.PepperImporter} or a
 * {@link org.corpus_tools.pepper.modules.PepperExporter} . Or it can be used to
 * determine the format of a corpus resource as given in {@link CorpusDesc}. <br/>
 * Main fields are:
 * <ul>
 * <li>{@link #formatName} - to determine the name of the format</li>
 * <li>{@link #formatVersion} - to determine the version of the format</li>
 * <li>{@link #formatReference} - to set a link to a more detailed description,
 * in most cases this is optional</li>
 * </ul>
 * 
 * @author Florian Zipser
 */
public class FormatDesc {

	/**
	 * Initilizes an object.
	 */
	public FormatDesc() {
		super();
	}

	/**
	 * Name of the linguistic format.
	 */
	protected String formatName = null;

	/**
	 * Sets the name of the linguistic format.
	 * 
	 * @param formatName
	 *            name of the format to be used
	 * @return this
	 */
	public FormatDesc setFormatName(String formatName) {
		this.formatName = formatName;
		return (this);
	}

	/**
	 * Returns the name of the linguistic format.
	 * 
	 * @return name of the format.
	 */
	public String getFormatName() {
		return formatName;
	}

	/**
	 * The version of the format.
	 */
	protected String formatVersion = null;

	/**
	 * Returns the version of the linguistic format.
	 * 
	 * @return version of the format
	 */
	public String getFormatVersion() {
		return formatVersion;
	}

	/**
	 * Sets the version of the linguistic format.
	 * 
	 * @param formatVersion
	 *            version of the format
	 * @return this
	 */
	public FormatDesc setFormatVersion(String formatVersion) {
		this.formatVersion = formatVersion;
		return (this);
	}

	/**
	 * Documentation reference for the linguistic format.
	 */
	protected URI formatReference = null;

	/**
	 * Returns the documentation reference for the linguistic format.
	 * 
	 * @param link
	 *            to a documentation
	 */
	public URI getFormatReference() {
		return formatReference;
	}

	/**
	 * Sets the documentation reference for the linguistic format.
	 * 
	 * @param formatReference
	 *            link to documentation
	 * @return this
	 */
	public FormatDesc setFormatReference(URI formatReference) {
		this.formatReference = formatReference;
		return (this);
	}

	/**
	 * Compares a {@link CorpusDesc} object with this object. Both objects are
	 * equal, if and only if the have the same format name and format version.
	 * The comparison is case insensitive.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return (true);
		}
		if (obj != null) {
			if (obj instanceof FormatDesc) {
				if ((getFormatName() != null) && (getFormatName().equalsIgnoreCase(((FormatDesc) obj).getFormatName())) && (getFormatVersion() != null) && (getFormatVersion().equalsIgnoreCase(((FormatDesc) obj).getFormatVersion()))) {
					return (true);
				} else {
					return (false);
				}
			} else {
				return (false);
			}
		} else
			return (false);
	}

	/**
	 * Returns a String representation of this object. <strong>Note: This String
	 * cannot be used for serialization/ deserialization.</string>
	 * 
	 * @return a String representation of this object.
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (formatName: ");
		result.append(formatName);
		result.append(", formatVersion: ");
		result.append(formatVersion);
		result.append(", formatReference: ");
		result.append(formatReference);
		result.append(')');
		return result.toString();
	}
}

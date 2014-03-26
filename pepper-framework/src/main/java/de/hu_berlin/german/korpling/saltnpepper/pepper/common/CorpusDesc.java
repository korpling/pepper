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

import org.eclipse.emf.common.util.URI;

/**
 * TODO more docu
 * 
 * @author Florian Zipser
 */
public class CorpusDesc {
	/**
	 * TODO more docu
	 */
	public CorpusDesc() {
		super();
	}
	/**
	 * TODO more docu
	 */
	protected FormatDesc formatDesc= null;
	/**
	 * TODO more docu
	 * If no {@link FormatDesc} object is set, a new one is created.
	 */
	public FormatDesc getFormatDesc() {
		if (formatDesc== null){
			synchronized (this) {
				formatDesc= new FormatDesc();
			}
		}
		return formatDesc;
	}
	/**
	 * TODO more docu
	 */
	public void setFormatDesc(FormatDesc formatDesc) {
		this.formatDesc = formatDesc;
	}
	/**
	 * TODO more docu
	 */
	protected URI corpusPath= null;
	/**
	 * TODO more docu
	 */
	public URI getCorpusPath() {
		return corpusPath;
	}
	/**
	 * TODO more docu
	 */
	public void setCorpusPath(URI corpusPath) {
		this.corpusPath = corpusPath;
	}
	/**
	 * Returns a string representation of this object. 
	 * <strong>Note: This representation cannot be used for serialization/deserialization purposes.</strong>
	 */
	@Override
	public String toString(){
		StringBuilder str= new StringBuilder();
		str.append(getCorpusPath());
		if (getFormatDesc()!= null){
			str.append("(");
			str.append(getFormatDesc().getFormatName());
			str.append(", ");
			str.append(getFormatDesc().getFormatVersion());
			str.append(")");
		}
		return(str.toString());
	}
}

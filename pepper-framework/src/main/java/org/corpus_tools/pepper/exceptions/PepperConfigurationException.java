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
package org.corpus_tools.pepper.exceptions;

/**
 * Is thrown, when any exception occurs concerning the configuration of Pepper.
 * For instance a configuration file cannot be read, or an invalid value is
 * contained.
 */
public class PepperConfigurationException extends PepperException {

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 6230810240391210746L;

	public PepperConfigurationException() {
		super();
	}

	public PepperConfigurationException(String s) {
		super(s);
	}

	public PepperConfigurationException(String s, Throwable ex) {
		super(s, ex);
	}
}

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
 * This PepperException is thrown if an internal error occurs. This means if it
 * is clear, that the error reason is a bug.
 */
public class PepperTestException extends PepperException {

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -1104119182794615488L;

	public PepperTestException() {
		super();
	}

	public PepperTestException(String s) {
		super("An error occured in running Pepper test: " + s);
	}

	public PepperTestException(String s, Throwable ex) {
		super("An error occured in running Pepper test: " + s, ex);
	}
}

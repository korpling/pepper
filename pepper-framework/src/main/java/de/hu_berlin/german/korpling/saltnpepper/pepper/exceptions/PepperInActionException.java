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
package de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions;

/**
 * This {@link PepperException} is thrown if a calling method tries to start a
 * new process or an action or tries to get access to a resource, but it
 * currently is in action and therefore can not be accessed. Normally this
 * exception means, that the caller should try it again later on.
 * 
 * @author Florian Zipser
 *
 */
public class PepperInActionException extends PepperException {

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -1134119182794615488L;

	public PepperInActionException() {
		super();
	}

	public PepperInActionException(String s) {
		super("This might be an internal exception: " + s);
	}

	public PepperInActionException(String s, Throwable ex) {
		super("This might be an internal exception: " + s, ex);
	}
}

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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;

/**
 * This PepperException is thrown only by a {@link PepperModule}. And is used to
 * type an exception as an internal exception, which means, when it is thrown a
 * bug occured.
 * 
 * @author Florian Zipser
 *
 */
@SuppressWarnings("serial")
public class PepperModuleInternalException extends PepperModuleException {
	public PepperModuleInternalException() {
		super();
	}

	public PepperModuleInternalException(String s) {
		super(s);
	}

	public PepperModuleInternalException(String s, Throwable ex) {
		super(s, ex);
	}
}

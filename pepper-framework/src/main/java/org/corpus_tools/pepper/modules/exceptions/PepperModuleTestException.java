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
package org.corpus_tools.pepper.modules.exceptions;

import org.corpus_tools.pepper.modules.PepperModule;

/**
 * This Exception class can be used for Tests (i.e. JUnit tests) of
 * {@link org.corpus_tools.pepper.modules.PepperModule} classes.
 */
@SuppressWarnings("serial")
public class PepperModuleTestException extends PepperModuleException {

	public PepperModuleTestException() {
		super();
	}

	public PepperModuleTestException(String s) {
		super(s);
	}

	public PepperModuleTestException(String s, Throwable ex) {
		super(s, ex);
	}

	public PepperModuleTestException(PepperModule pepperModule, String s) {
		super("Error in Test of Pepper module '" + (pepperModule.getName() != null ? pepperModule.getName() : "NO_NAME")
				+ ", " + (pepperModule.getVersion() != null ? pepperModule.getVersion() : "NO_VERSION")
				+ "', please contact the module supplier"
				+ (pepperModule.getSupplierContact() != null ? " " + pepperModule.getSupplierContact() : "") + ". "
				+ s);
	}

	public PepperModuleTestException(PepperModule pepperModule, String s, Throwable ex) {
		super("Error in Test of Pepper module '" + (pepperModule.getName() != null ? pepperModule.getName() : "NO_NAME")
				+ ", " + (pepperModule.getVersion() != null ? pepperModule.getVersion() : "NO_VERSION")
				+ "', please contact the module supplier"
				+ (pepperModule.getSupplierContact() != null ? " " + pepperModule.getSupplierContact() : "") + ". " + s,
				ex);
	}
}

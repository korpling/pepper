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

import org.corpus_tools.pepper.exceptions.AbstractPepperModuleException;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.PepperModule;

/**
 * This exception is a concrete implementation of
 * {@link AbstractPepperModuleException} and provides constructors to pass the
 * {@link PepperModule} throwing this exception. <br/>
 * <strong>Note: This exception is underspecified and only includes the
 * information that an exception occured during the processing of a specific
 * module. It does not give any information about the specific reason, for
 * instance a data error or an internal error etc. If possible, please use a
 * more specific subclassed Exception.</strong>
 */
@SuppressWarnings("serial")
public class PepperModuleException extends AbstractPepperModuleException {

	public PepperModuleException() {
		super();
	}

	public PepperModuleException(String s) {
		super(s);
	}

	public PepperModuleException(String s, Throwable ex) {
		super(s, ex);
	}

	public PepperModuleException(PepperModule pepperModule, String s) {
		super("Error in Pepper module '" + (pepperModule.getName() != null ? pepperModule.getName() : "NO_NAME") + ", "
				+ (pepperModule.getVersion() != null ? pepperModule.getVersion() : "NO_VERSION")
				+ "', please contact the module supplier"
				+ (pepperModule.getSupplierContact() != null ? " " + pepperModule.getSupplierContact() : "") + ". "
				+ s);
	}

	public PepperModuleException(PepperModule pepperModule, String s, Throwable ex) {
		super("Error in Pepper module '" + (pepperModule.getName() != null ? pepperModule.getName() : "NO_NAME") + ", "
				+ (pepperModule.getVersion() != null ? pepperModule.getVersion() : "NO_VERSION")
				+ "', please contact the module supplier"
				+ (pepperModule.getSupplierContact() != null ? " " + pepperModule.getSupplierContact() : "") + ". " + s,
				ex);
	}

	public PepperModuleException(PepperMapper pepperMapper, String s) {
		super("A data error occured for a Pepper mapper '" + pepperMapper.getClass().getName() + "'. " + s);
	}

	public PepperModuleException(PepperMapper pepperMapper, String s, Throwable ex) {
		super("A data error occured for Pepper mapper '" + pepperMapper.getClass().getName() + "'. " + s, ex);
	}
}

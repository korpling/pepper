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

import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;

/**
 * This PepperException is thrown only by a
 * {@link org.corpus_tools.pepper.modules.PepperModule}. If such an exception
 * was thrown, it means that there are problems in the read resource.
 * 
 * @author Florian Zipser
 *
 */
@SuppressWarnings("serial")
public class PepperModuleDataException extends PepperModuleException {
	protected PepperModuleDataException(PepperModule pepperModule, String s) {
		super("Error in Pepper module '" + (pepperModule.getName() != null ? pepperModule.getName() : "NO_NAME") + ", " + (pepperModule.getVersion() != null ? pepperModule.getVersion() : "NO_VERSION") + "', please contact the module supplier" + (pepperModule.getSupplierContact() != null ? " " + pepperModule.getSupplierContact() : "") + ". " + s);
	}

	protected PepperModuleDataException(PepperModule pepperModule, String s, Throwable ex) {
		super("Error in Pepper module '" + (pepperModule.getName() != null ? pepperModule.getName() : "NO_NAME") + ", " + (pepperModule.getVersion() != null ? pepperModule.getVersion() : "NO_VERSION") + "', please contact the module supplier" + (pepperModule.getSupplierContact() != null ? " " + pepperModule.getSupplierContact() : "") + ". " + s, ex);
	}

	public PepperModuleDataException(PepperMapper pepperMapper, String s) {
		super("A data error occured for a Pepper mapper '" + pepperMapper.getClass().getName() + "'. " + s);
	}

	public PepperModuleDataException(PepperMapper pepperMapper, String s, Throwable ex) {
		super("A data error occured for Pepper mapper '" + pepperMapper.getClass().getName() + "'. " + s, ex);
	}
}

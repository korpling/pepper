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

/**
 * This PepperException is thrown only by a {@link PepperModule}. And is used to
 * type an exception as an internal exception, which means, when it is thrown a
 * bug occured.
 */
@SuppressWarnings("serial")
public class PepperModuleInternalException extends PepperModuleException {
	/**
	 * 
	 * @param module
	 *            the module, which threw the exception
	 * @param msg
	 *            original message
	 * @return
	 */
	private static String createMsg(PepperModule module, String msg) {
		return ("Error in Pepper module '" + (module.getName() != null ? module.getName() : "NO_NAME") + ", "
				+ (module.getVersion() != null ? module.getVersion() : "NO_VERSION") + "': " + msg
				+ "This might be a bug, please contact the module supplier"
				+ (module.getSupplierHomepage() != null ? " " + module.getSupplierHomepage() : "") + ". ");
	}

	/**
	 * 
	 * @param mapper
	 *            the mapper, which threw the exception
	 * @param msg
	 *            original message
	 * @return
	 */
	private static String createMsg(PepperMapper mapper, String msg) {
		return ("Error in Pepper mapper '" + mapper.getClass().getName() + "': " + msg + "This might be a bug. ");
	}

	/**
	 * 
	 * @param msg
	 *            original message
	 * @return
	 */
	private static String createMsg(String msg) {
		return ("Error in Pepper module: " + msg + "This might be a bug. ");
	}

	public PepperModuleInternalException(String s) {
		super(createMsg(s));
	}

	public PepperModuleInternalException(String s, Throwable ex) {
		super(createMsg(s), ex);
	}

	public PepperModuleInternalException(PepperModule pepperModule, String s) {
		super(createMsg(pepperModule, s));
	}

	public PepperModuleInternalException(PepperModule pepperModule, String s, Throwable ex) {
		super(createMsg(pepperModule, s), ex);
	}

	public PepperModuleInternalException(PepperMapper pepperMapper, String s) {
		super(createMsg(pepperMapper, s));
	}

	public PepperModuleInternalException(PepperMapper pepperMapper, String s, Throwable ex) {
		super(createMsg(pepperMapper, s), ex);
	}
}

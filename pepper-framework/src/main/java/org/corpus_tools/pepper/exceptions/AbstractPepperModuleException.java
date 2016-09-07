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

import org.corpus_tools.pepper.modules.PepperMapper;

/**
 * This {@link PepperException} is thrown by
 * {@link org.corpus_tools.pepper.modules.PepperModule}s or {@link PepperMapper}
 * objects. The reason of exception can be any and should be further specified
 * by subtypes. This exception just determines, that it occured during the
 * processing in a {@link org.corpus_tools.pepper.modules.PepperModule}.
 * 
 * @author Florian Zipser
 *
 */
public class AbstractPepperModuleException extends PepperException {

	private static final long serialVersionUID = -2476758519498655948L;

	public AbstractPepperModuleException() {
		super();
	}

	public AbstractPepperModuleException(String s) {
		super(s);
	}

	public AbstractPepperModuleException(String s, Throwable ex) {
		super(s, ex);
	}
}

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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;

/**
 * This PepperException is thrown only by a
 * {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}.
 * 
 * @author Florian Zipser
 * 
 */
public class PepperModuleBug extends PepperModuleException {

	private static final String MSG_PREFIX= "Oops, this seems to be a bug in Pepper module, we are sorry. Please report it. ";
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -7963807048315916615L;

	protected PepperModuleBug(PepperModule pepperModule, String s) {
		super(pepperModule, MSG_PREFIX+s);
	}

	protected PepperModuleBug(PepperModule pepperModule, String s, Throwable ex) {
		super(pepperModule, MSG_PREFIX+s, ex);
	}

	public PepperModuleBug(PepperMapper pepperMapper, String s) {
		super(pepperMapper, MSG_PREFIX+s);
	}

	public PepperModuleBug(PepperMapper pepperMapper, String s, Throwable ex) {
		super(pepperMapper, MSG_PREFIX+s, ex);
	}
}

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
package org.corpus_tools.pepper.impl;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.modules.PepperManipulator;
import org.corpus_tools.pepper.modules.PepperModule;

/**
 * <p>
 * This class is an abstract implementation of {@link PepperManipulator} and
 * cannot be instantiated directly. To implement an exporter for Pepper, the
 * easiest way is to derive this class. For further information, read the
 * javadoc of {@link PepperManipulator} and the documentation of
 * <a href="http://u.hu-berlin.de/saltnpepper">u.hu-berlin.de/saltnpepper</a>.
 * </p>
 * 
 * @see PepperManipulator
 * 
 * @author Florian Zipser
 */
public abstract class PepperManipulatorImpl extends PepperModuleImpl implements PepperManipulator {
	/**
	 * Creates a {@link PepperModule} of type {@link MODULE_TYPE#}. The name is
	 * set to "MyManipulator". <br/>
	 * We recommend to use the constructor
	 * {@link PepperManipulatorImpl#PepperManipulatorImpl(String)} and pass a
	 * proper name.
	 */
	protected PepperManipulatorImpl() {
		super("MyManipulator");
	}

	/**
	 * Creates a {@link PepperModule} of type {@link MODULE_TYPE#MANIPULATOR}
	 * and sets is name to the passed one.
	 */
	protected PepperManipulatorImpl(String name) {
		super(name);
	}
}

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
package de.hu_berlin.german.korpling.saltnpepper.pepper.connectors;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;

public interface PepperConnector extends Pepper{
	/**
	 * Starts the OSGi framework and initializes {@link Pepper} inside the framework.
	 */
	public abstract void init();
	/**
	 * Returns whether this object has been initialized.
	 * @return true, if object has been initialized, false otherwise
	 */
	public abstract boolean isInitialized();
}
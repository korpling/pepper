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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.exceptions;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperty;

/**
 * Exception is used in case of an exception occurs concerning {@link PepperModuleProperty}. 
 * Such an exception can occur while initialization or while working with properties.
 * @author Florian Zipser
 *
 */
public class PepperModulePropertyException extends PepperModuleException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8100717436792012869L;
	
	public PepperModulePropertyException()
	{ super(); }
	
    public PepperModulePropertyException(String s)
    { super(s); }
    
	public PepperModulePropertyException(String s, Throwable ex)
	{super(s, ex); }
}

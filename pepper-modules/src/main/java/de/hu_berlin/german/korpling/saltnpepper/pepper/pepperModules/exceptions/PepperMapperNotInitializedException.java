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
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;

/**
 *	This exception class is used, in case of a {@link PepperMapper} object was not correctly initialized. 
 *
 **/
public class PepperMapperNotInitializedException extends PepperModuleException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8100717436792012869L;
	
	public PepperMapperNotInitializedException()
	{ super(); }
	
    public PepperMapperNotInitializedException(String s)
    { super(s); }
    
	public PepperMapperNotInitializedException(String s, Throwable ex)
	{super(s, ex); }
}

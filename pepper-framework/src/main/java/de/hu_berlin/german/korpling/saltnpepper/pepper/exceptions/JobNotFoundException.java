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
package de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;


/**
 *	This exception class is used, in case of a {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper} object was not correctly initialized. 
 *
 **/
public class JobNotFoundException extends PepperModuleException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8100917436792012869L;
	
	public JobNotFoundException()
	{ super(); }
	
    public JobNotFoundException(String s)
    { super(s); }
    
	public JobNotFoundException(String s, Throwable ex)
	{super(s, ex); }
}

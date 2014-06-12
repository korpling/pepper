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
package de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions;

/**
 * This {@link PepperException} is thrown by {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}s or {@link {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}PepperMapper}
 * objects. The reason of exception can be any and should be further specified by subtypes.
 * This exception just determines, that it occured during the processing in a {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}. 
 * @author Florian Zipser
 *
 */
public class AbstractPepperModuleException extends PepperException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3563133484909484582L;

	public AbstractPepperModuleException()
	{ super(); }
	
    public AbstractPepperModuleException(String s)
    { super(s); }
    
	public AbstractPepperModuleException(String s, Throwable ex)
	{super(s, ex); }
}
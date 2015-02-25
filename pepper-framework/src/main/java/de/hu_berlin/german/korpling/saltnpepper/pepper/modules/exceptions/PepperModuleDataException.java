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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;

/**
 * This PepperException is thrown only by a {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}. 
 * @author Florian Zipser
 *
 */
public class PepperModuleDataException extends PepperModuleException {

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -7963907048315916615L;
	
	protected PepperModuleDataException(PepperModule pepperModule, String s)
    { 
		super("Error in Pepper module '"+(pepperModule.getName()!= null? pepperModule.getName(): "NO_NAME") + ", "+(pepperModule.getVersion()!= null? pepperModule.getVersion(): "NO_VERSION")+"', please contact the module supplier"+(pepperModule.getSupplierContact()!= null? " "+ pepperModule.getSupplierContact(): "")+". "+ s);
    }
	protected PepperModuleDataException(PepperModule pepperModule, String s, Throwable ex)
    { 
		super("Error in Pepper module '"+(pepperModule.getName()!= null? pepperModule.getName(): "NO_NAME") + ", "+(pepperModule.getVersion()!= null? pepperModule.getVersion(): "NO_VERSION")+"', please contact the module supplier"+(pepperModule.getSupplierContact()!= null? " "+ pepperModule.getSupplierContact(): "")+". "+ s, ex);
    }
	
	public PepperModuleDataException(PepperMapper pepperMapper, String s)
    { 
		super("A data error occured for a Pepper mapper '"+pepperMapper.getClass().getName()+"'. "+s); 
    }
	public PepperModuleDataException(PepperMapper pepperMapper, String s, Throwable ex)
    { 
		super("A data error occured for Pepper mapper '"+pepperMapper.getClass().getName()+"'. "+s, ex);
    }
}

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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.util;

import java.util.Dictionary;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;

public class TestComponentFactory implements ComponentFactory 
{
	private Class<? extends PepperModule> pepperModule= null;
	
	public void setClass(Class<? extends PepperModule> pepperModule)
	{
		this.pepperModule= pepperModule;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ComponentInstance newInstance(Dictionary properties)
	{
		TestComponentInstance retVal= null;
		try {
			retVal= new TestComponentInstance();
			retVal.instance= this.pepperModule.newInstance();
		} catch (Exception e) 
		{
			throw new RuntimeException(e);
		}	
		
		return(retVal);
	}

}

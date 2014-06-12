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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.tests;

import java.io.File;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperty;
import junit.framework.TestCase;

public class PepperModulePropertyTest extends TestCase
{
	/**
	 * Checks setting and returning an int value.
	 */
	public void testSetProperty_Int()
	{
		Integer value= 123;
		PepperModuleProperty<Integer> prop= new PepperModuleProperty<Integer>("prop1", Integer.class, "desc");
		prop.setValueString(value.toString());
		assertEquals(value, prop.getValue());
	}
	
	/**
	 * Checks setting and returning an int value.
	 */
	public void testSetProperty_Boolean()
	{
		Boolean value= true;
		PepperModuleProperty<Boolean> prop= new PepperModuleProperty<Boolean>("prop1", Boolean.class, "desc");
		prop.setValueString(value.toString());
		assertEquals(value, prop.getValue());
	}
	
	/**
	 * Checks setting and returning an int value.
	 */
	public void testSetProperty_File()
	{
		File value= new File("/home/me/");
		PepperModuleProperty<File> prop= new PepperModuleProperty<File>("prop1", File.class, "desc");
		prop.setValueString(value.toString());
		assertEquals(value, prop.getValue());
	}
	
	/**
	 * Checks setting and returning an int value.
	 */
	public void testSetProperty_String()
	{
		String value= "hello world";
		PepperModuleProperty<String> prop= new PepperModuleProperty<String>("prop1", String.class, "desc");
		prop.setValueString(value);
		assertEquals(value, prop.getValue());
	}
	
	/**
	 * Tests the use of the default value.
	 */
	public void testSetUsingDefault()
	{
		String defaultValue= "hello world";
		PepperModuleProperty<String> prop= new PepperModuleProperty<String>("prop1", String.class, "desc", defaultValue);
		assertEquals(defaultValue, prop.getValue());
	}
}

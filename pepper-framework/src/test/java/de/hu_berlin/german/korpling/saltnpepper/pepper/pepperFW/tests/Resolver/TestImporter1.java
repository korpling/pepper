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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.Resolver;


import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;

public class TestImporter1 extends PepperImporterImpl 
{	
	public TestImporter1() 
	{
		super.name=getModuleName();
		super.setSymbolicName(this.getName());
	}
	
	public static String name= "TestImporter1";
	
	public static String getModuleName()
	{
		return(name);
	}
	
	public EList<FormatDefinition> getSupportedFormats()
	{
		return(getFormats());
	}
	
	public static EList<FormatDefinition> getFormats()
	{
		EList<FormatDefinition> formatDefs= null;
		formatDefs= new BasicEList<FormatDefinition>();
		
		FormatDefinition formatDef= PepperModulesFactory.eINSTANCE.createFormatDefinition();
		formatDef.setFormatName("TestFormat");
		formatDef.setFormatVersion("1.0");
		
		formatDefs.add(formatDef);
		return(formatDefs);
	}
}

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
package de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;

public class PepperImporterTest extends PepperModuleTest
{
	//TODO much more tests for example setGetCorpusDefinition, testSetGetPepperModuleController
	
	/**
	 * A list of formats, which shall be supported
	 */
	protected EList<FormatDefinition> supportedFormatsCheck= null;
	
	protected void setFixture(PepperImporter fixture) {
		super.setFixture(fixture);
		this.supportedFormatsCheck= new BasicEList<FormatDefinition>();
	}
	
	protected PepperImporter getFixture() 
	{
		return((PepperImporter) super.getFixture());
	}
	
	public void testGetSupportedFormats()
	{
		assertNotNull("Cannot run test, please set fixture first.",this.getFixture());
		assertNotNull("There have to be some supported formats",this.getFixture().getSupportedFormats());
		EList<FormatDefinition> formatDefs= this.getFixture().getSupportedFormats();
		assertNotSame("Number of supported formats have to be more than 0", 0, formatDefs);
		for (FormatDefinition formatDef: formatDefs)
		{
			assertNotNull("The name of supported formats has to be set.", formatDef.getFormatName());
			assertFalse("The name of the supported formats can't be empty.", formatDef.getFormatName().equals(""));
			
			assertNotNull("The version of supported formats has to be set.", formatDef.getFormatVersion());
			assertFalse("The version of the supported formats can't be empty.", formatDef.getFormatVersion().equals(""));
		}	
		assertTrue("Cannot test the supported formats please set variable 'supportedFormatsCheck'.", this.supportedFormatsCheck.size() > 0);
		assertEquals("There is a different between the number formats which are supported by module, and the number of formats which shall be supported.", this.supportedFormatsCheck.size(), this.getFixture().getSupportedFormats().size());
		for (FormatDefinition formatCheck: this.supportedFormatsCheck)
		{
			Boolean hasOpponend= false;
			for (FormatDefinition formatDef: this.getFixture().getSupportedFormats())
			{
				if (	(formatDef.getFormatName().equalsIgnoreCase(formatCheck.getFormatName())) &&
						(formatDef.getFormatVersion().equalsIgnoreCase(formatCheck.getFormatVersion())))
					hasOpponend= true;
			}
			assertTrue("The format '"+formatCheck.getFormatName()+ " "+ formatCheck.getFormatVersion()+"' has to be supported, but does not exists in list of suppoted formats.", hasOpponend);
		}
	}
}

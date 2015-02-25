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
package de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Vector;

import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;

public abstract class PepperExporterTest extends PepperModuleTest 
{
	/**
	 * A list of formats, which shall be supported
	 */
	protected List<FormatDesc> supportedFormatsCheck= null;
	
	protected void setFixture(PepperExporter fixture) {
		super.setFixture(fixture);
		this.supportedFormatsCheck= new Vector<FormatDesc>();
	}
	@Override
	protected PepperExporter getFixture() 
	{
		return((PepperExporter) super.getFixture());
	}
	@Test
	public void testGetSupportedFormats()
	{
		assertNotNull("There have to be some supported formats",this.getFixture().getSupportedFormats());
		List<FormatDesc> formatDefs= this.getFixture().getSupportedFormats();
		assertNotSame("Number of supported formats have to be more than 0", 0, formatDefs);
		for (FormatDesc formatDef: formatDefs)
		{
			assertNotNull("The name of supported formats has to be set.", formatDef.getFormatName());
			assertFalse("The name of the supported formats can't be empty.", formatDef.getFormatName().equals(""));
			
			assertNotNull("The version of supported formats has to be set.", formatDef.getFormatVersion());
			assertFalse("The version of the supported formats can't be empty.", formatDef.getFormatVersion().equals(""));
		}	
		assertTrue("Cannot test the supported formats please set variable 'supportedFormatsCheck'.", this.supportedFormatsCheck.size() > 0);
		assertEquals("There is a different between the number formats which are supported by module, and the number of formats which shall be supported.", this.supportedFormatsCheck.size(), this.getFixture().getSupportedFormats().size());
		for (FormatDesc formatCheck: this.supportedFormatsCheck)
		{
			Boolean hasOpponend= false;
			for (FormatDesc formatDef: this.getFixture().getSupportedFormats())
			{
				if (	(formatDef.getFormatName().equalsIgnoreCase(formatCheck.getFormatName())) &&
						(formatDef.getFormatVersion().equalsIgnoreCase(formatCheck.getFormatVersion())))
					hasOpponend= true;
			}
			assertTrue("The format '"+formatCheck.getFormatName()+ " "+ formatCheck.getFormatVersion()+"' has to be supported, but does not exist in list of suppoted formats.", hasOpponend);
		}
	}
}

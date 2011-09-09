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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.TestModules;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

public class TestExporter1 extends PepperExporterImpl 
{
	Logger logger= Logger.getLogger(TestExporter1.class);
	public TestExporter1()
	{
		this.name= "TestExporter1";
		FormatDefinition formatDef= PepperFWFactory.eINSTANCE.createFormatDefinition();
		formatDef.setFormatName("TestFormat");
		formatDef.setFormatVersion("1.0");
		this.getSupportedFormats().add(formatDef);
		this.setSymbolicName(this.getClass().getName());
	}
	
	public EList<SElementId> elementIds= null;
	public void setSElementIds(EList<SElementId> elementIds)
	{
		EList<SElementId> newSElementIds= new BasicEList<SElementId>();
		for (SElementId sElementId: elementIds)
		{
			newSElementIds.add(sElementId);
		}
		this.elementIds= newSElementIds;
	}
	
	@Override
	public void start(SElementId sElementId) 
	{
		logger.info("(TestExporter1) start document: "+ sElementId.getSId());
		//remove sElementId
		SElementId removeElementId= null;
		for (SElementId elId: this.elementIds)
		{
			if (elId.getSId().equalsIgnoreCase(sElementId.getSId()))
			{	
				removeElementId= elId;
				break;
			}
		}	
		this.elementIds.remove(removeElementId);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}

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
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

public class TestImporter1 extends PepperImporterImpl 
{
	Logger logger= Logger.getLogger(TestExporter1.class);
	public TestImporter1()
	{
		this.name= "TestImporter1";
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
	public void importCorpusStructure(SCorpusGraph corpusGraph)
	{
		for (SElementId sElementId: elementIds)
		{
			//copying element id, because it will be destroyed if creator of elementIds list is destroyed
			SElementId sElementId2= SaltCommonFactory.eINSTANCE.createSElementId();
			sElementId2.setSId(sElementId.getSId());
			
			SDocument document= SaltCommonFactory.eINSTANCE.createSDocument();
			document.setSElementId(sElementId2);
			corpusGraph.addSNode(document);
		}	
		
	} 
	
	@Override
	public void start(SElementId sElementId) 
	{
		logger.info("(TestImporter1) start document: "+ sElementId.getSId());
		SElementId removeSElementId= null;
		for (SElementId sElementId2: elementIds)
			if (sElementId2.getSId().equalsIgnoreCase(sElementId.getSId()))
			{	
				removeSElementId= sElementId2;
				break;
			}
		this.elementIds.remove(removeSElementId);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}

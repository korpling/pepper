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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.JobCases;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;

public class PepperJobDelegatorTest extends PepperJobImpl 
{
	public class ImporterGraphPairDelegator extends ImporterGraphPair
	{
		protected ImporterGraphPair original= null;
		
		public ImporterGraphPairDelegator (ImporterGraphPair original)
		{
			this.original= original;
		}
		public PepperImporter getImporter()
		{ return(original.importer); }
		
		public SCorpusGraph getSCorpusGraph()
		{ return(original.sCorpusGraph); }
	}
	
	public void validateBeforeStart() throws PepperConvertException
	{
		super.validateBeforeStart();
	}
	
	public void createAndWirePepperModuleController(PepperModule module)
	{
		super.createAndWirePepperModuleController(module);
	}
	
	public EList<ImporterGraphPairDelegator> importCorpusStructureTest()
	{
		EList<ImporterGraphPairDelegator> retList= new BasicEList<ImporterGraphPairDelegator>();
		for (ImporterGraphPair pair: super.importCorpusStructure())
		{
			ImporterGraphPairDelegator pair2= new ImporterGraphPairDelegator(pair);
			retList.add(pair2);
		}	
		return(retList);
	}
	 
	public EList<PepperModuleController> getAllModuleControlers()
	{
		return(this.allModuleControlers);
	}
	public EList<PepperFinishableMonitor> getAllM2JMonitors()
	{
		return(this.allM2JMonitors);
	}
	
	public EList<EList<PepperModuleController>> createPhases()
	{
		return(super.createPhases());
	}
	
	public void wireModuleControllers(EList<EList<PepperModuleController>> steps)
	{
		super.wireModuleControllers(steps);
	}
}

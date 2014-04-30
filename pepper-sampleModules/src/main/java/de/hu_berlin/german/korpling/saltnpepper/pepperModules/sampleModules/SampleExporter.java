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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.sampleModules;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * This is a sample {@link PepperExporter}, which can be used for creating individual Exporters for the 
 * Pepper Framework. Therefore you have to take a look to todo's and adapt the code.
 * 
 * <ul>
 *  <li>the salt model to fill, manipulate or export can be accessed via {@link #getSaltProject()}</li>
 * 	<li>special parameters given by Pepper workflow can be accessed via {@link #getSpecialParams()}</li>
 *  <li>a place to store temprorary datas for processing can be accessed via {@link #getTemproraries()}</li>
 *  <li>a place where resources of this bundle are, can be accessed via {@link #getResources()}</li>
 *  <li>a logService can be accessed via {@link #getLogService()}</li>
 * </ul>
 * @author Florian Zipser
 * @version 1.0
 *
 */
//TODO /1/: change the name of the component, for example use the format name and the ending Exporter (FORMATExporterComponent)
@Component(name="SampleExporterComponent", factory="PepperExporterComponentFactory")
public class SampleExporter extends PepperExporterImpl implements PepperExporter
{
	// =================================================== mandatory ===================================================
		/**
		 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
		 * 
		 * A constructor for your module. Set the coordinates, with which your module shall be registered. 
		 * The coordinates (modules name, version and supported formats) are a kind of a fingerprint, 
		 * which should make your module unique.
		 */
		public SampleExporter()
		{
			super();
			//TODO change the name of the module, for example use the format name and the ending Exporter (FORMATExporter)
			this.setName("SampleExporter");
			//TODO change the version of your module, we recommend to synchronize this value with the maven version in your pom.xml
			this.setVersion("1.1.0");
			//TODO change "sample" with format name and 1.0 with format version to support
			this.addSupportedFormat("sample", "1.0", null); 
		}
		
		/**
		 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
		 * 
		 * This method creates a customized {@link PepperMapper} object and returns it. You can here do some additional initialisations. 
		 * Thinks like setting the {@link SElementId} of the {@link SDocument} or {@link SCorpus} object and the {@link URI} resource is done
		 * by the framework (or more in detail in method {@link #start()}).  
		 * The parameter <code>sElementId</code>, if a {@link PepperMapper} object should be created in case of the object to map is either 
		 * an {@link SDocument} object or an {@link SCorpus} object of the mapper should be initialized differently. 
		 * <br/>
		 * 
		 * @param sElementId {@link SElementId} of the {@link SCorpus} or {@link SDocument} to be processed. 
		 * @return {@link PepperMapper} object to do the mapping task for object connected to given {@link SElementId}
		 */
		public PepperMapper createPepperMapper(SElementId sElementId)
		{
			//TODO create an object of a class derived from PepperMapper and return it, if necessary, make some more initializations 
			return(null);
		}
		
	// =================================================== optional ===================================================	
		/**
		 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
		 * 
		 * This method is called by the pepper framework after initializing this object and directly before start processing. 
		 * Initializing means setting properties {@link PepperModuleProperties}, setting temporary files, resources etc. .
		 * returns false or throws an exception in case of {@link PepperModule} instance is not ready for any reason.
		 * @return false, {@link PepperModule} instance is not ready for any reason, true, else.
		 */
		@Override
		public boolean isReadyToStart() throws PepperModuleNotReadyException
		{
			//TODO make some initializations if necessary
			return(super.isReadyToStart());
		}
}

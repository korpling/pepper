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

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperManipulatorImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.sampleModules.SampleImporter.SampleMapper;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * This is a sample {@link PepperManipulator}, which can be used for creating individual manipulators for the 
 * Pepper Framework. Therefore you have to take a look to todo's and adapt the code.
 * 
 * <ul>
 *  <li>the salt model to fill, manipulate or export can be accessed via {@link #getSaltProject()}</li>
 * 	<li>special parameters given by Pepper workflow can be accessed via {@link #getSpecialParams()}s()</li>
 *  <li>a place to store temporary datas for processing can be accessed via {@link #getTemproraries()}</li>
 *  <li>a place where resources of this bundle are, can be accessed via {@link #getResources()}</li>
 *  <li>a logService can be accessed via {@link #getLogService()}</li>
 * </ul>
 * @author Florian Zipser
 * @version 1.0
 *
 */
//TODO /1/: change the name of the component, for example use the format name and the ending manipulator (FORMATManipulatorComponent)
@Component(name="SampleManipulatorComponent", factory="PepperManipulatorComponentFactory")
public class SampleManipulator extends PepperManipulatorImpl 
{
	// =================================================== mandatory ===================================================
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * <br/>
	 * A constructor for your module. Set the coordinates, with which your module shall be registered. 
	 * The coordinates (modules name, version and supported formats) are a kind of a fingerprint, 
	 * which should make your module unique.
	 */
	public SampleManipulator() {
		super();
		//TODO change the name of the module, for example use the format name and the ending Manipulator
		this.setName("SampleManipulator");
		//TODO change the version of your module, we recommend to synchronize this value with the maven version in your pom.xml
		this.setVersion("1.1.0");
	}
	
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * <br/>
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
	public PepperMapper createPepperMapper(SElementId sElementId){
		//TODO create an object of a class derived from PepperMapper and return it, if necessary, make some more initializations 
		return(null);
	}
	
	/**
	 * This class is a dummy implementation for a mapper, to show how it works. Pepper or more specific this dummy
	 * implementation of a Pepper module creates one mapper object per {@link SDocument} object and {@link SCorpus}
	 * object each. This ensures, that each of those objects is run independently from another and runs parallelized.
	 * <br/>
	 * The method {@link #mapSCorpus()} is supposed to handle all {@link SCorpus} object and the method {@link #mapSDocument()}
	 * is supposed to handle all {@link SDocument} objects. 
	 * <br/>
	 * In our dummy implementation, we just print out some information about a corpus to system.out. This is not very 
	 * useful, but might be a good starting point to explain how access the several objects in Salt model. 
	 * 
	 * @author Florian Zipser
	 *
	 */
	public class SampleMapper extends PepperMapperImpl{
		@Override
		public DOCUMENT_STATUS mapSCorpus() {
			// TODO Auto-generated method stub
			return super.mapSCorpus();
		}
		
		@Override
		public DOCUMENT_STATUS mapSDocument() {
			//create a StringBuilder, to be filled with informations (we need to intermediately store the results, because of parallelism of modules)
			StringBuilder out= new StringBuilder();
			out.append("\n");
			out.append("document ");
			//print out the id of the document
			out.append(getSDocument().getSId());
			out.append("\n");
			out.append("-------------------------------\n");
			out.append("nodes: ");
			//print out the general number of nodes
			out.append(getSDocument().getSDocumentGraph().getSNodes().size());
			out.append("\n");
			//print out the general number of relations
			out.append("relations: ");
			out.append(getSDocument().getSDocumentGraph().getSRelations().size());
			out.append("\n");
			//print out the general number of primary texts
			out.append("texts: ");
			out.append(getSDocument().getSDocumentGraph().getSTextualDSs().size());
			out.append("\n");
			//print out the general number of tokens
			out.append("tokens: ");
			out.append(getSDocument().getSDocumentGraph().getSTokens().size());
			out.append("\n");
			//print out the general number of spans
			out.append("spans: ");
			out.append(getSDocument().getSDocumentGraph().getSSpans().size());
			out.append("\n");
			//print out the general number of structures
			out.append("structures: ");
			out.append(getSDocument().getSDocumentGraph().getSStructures().size());
			out.append("\n");
			
			//TODO show the traversion mechanism to print out the number of each annotation name, and the max path length per relation type (dominance and pointing)
			
			System.out.println(out.toString());
			return super.mapSDocument();
		}
	}
	
// =================================================== optional ===================================================	
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * <br/>
	 * This method is called by the pepper framework after initializing this object and directly before start processing. 
	 * Initializing means setting properties {@link PepperModuleProperties}, setting temporary files, resources etc. .
	 * returns false or throws an exception in case of {@link PepperModule} instance is not ready for any reason.
	 * @return false, {@link PepperModule} instance is not ready for any reason, true, else.
	 */
	@Override
	public boolean isReadyToStart() throws PepperModuleNotReadyException{
		//TODO make some initializations if necessary
		return(super.isReadyToStart());
	}
}

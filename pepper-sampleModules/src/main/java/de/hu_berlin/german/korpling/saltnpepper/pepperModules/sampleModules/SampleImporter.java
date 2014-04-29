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

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * This is a sample {@link PepperImporter}, which can be used for creating individual Importers for the 
 * Pepper Framework. Therefore you have to take a look to todo's and adapt the code.
 * 
 * <ul>
 *  <li>the salt model to fill, manipulate or export can be accessed via {@link #getSaltProject()}</li>
 * 	<li>special parameters given by Pepper workflow can be accessed via {@link #getSpecialParams()}</li>
 *  <li>a place to store temprorary datas for processing can be accessed via {@link #getTemproraries()}</li>
 *  <li>a place where resources of this bundle are, can be accessed via {@link #getResources()}</li>
 *  <li>a logService can be accessed via {@link #getLogService()}</li>
 * </ul>
 * If this is the first time, you are implementing a Pepper module, we strongly recommend, to take a look into the
 * 'Developer's Guide for Pepper modules', you will find on <a href="https://korpling.german.hu-berlin.de/saltnpepper/">https://korpling.german.hu-berlin.de/saltnpepper/</a>.
 * @author Florian Zipser
 * @version 1.0
 *
 */
//TODO change the name of the component, for example use the format name and the ending Importer (FORMATImporterComponent)
@Component(name="SampleImporterComponent", factory="PepperImporterComponentFactory")
public class SampleImporter extends PepperImporterImpl implements PepperImporter
{

// =================================================== mandatory ===================================================
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * 
	 * A constructor for your module. Set the coordinates, with which your module shall be registered. 
	 * The coordinates (modules name, version and supported formats) are a kind of a fingerprint, 
	 * which should make your module unique.
	 */
	public SampleImporter()
	{
		super();
		//TODO change the name of the module, for example use the format name and the ending Importer (FORMATImporter)
		this.setName("SampleImporter");
		//TODO change the version of your module, we recommend to synchronize this value with the maven version in your pom.xml
		this.setVersion("1.1.0");
		//TODO change "sample" with format name and 1.0 with format version to support
		this.addSupportedFormat("sample", "1.0", null);
		//TODO change the endings in endings of files you want to import, see also predefined endings beginning with 'ENDING_' 
		this.getSDocumentEndings().add("ENDING OF FILES TO IMPORT");
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
	
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * 
	 * This method is called by the pepper framework and returns if a corpus located at the given {@link URI} is importable
	 * by this importer. If yes, 1 must be returned, if no 0 must be returned. If it is not quite sure, if the given corpus
	 * is importable by this importer any value between 0 and 1 can be returned. If this method is not overridden, 
	 * null is returned.
	 * @return 1 if corpus is importable, 0 if corpus is not importable, 0 < X < 1, if no definitiv answer is possible,  null if method is not overridden 
	 */
	public Double isImportable(URI corpusPath)
	{
		//TODO some code to analyze the given corpus-structure
		return(null);
	}

// =================================================== optional ===================================================	
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * 
	 * This method is called by the pepper framework after initializing this object and directly before start processing. 
	 * Initializing means setting properties {@link PepperModuleProperties}, setting temprorary files, resources etc. .
	 * returns false or throws an exception in case of {@link PepperModule} instance is not ready for any reason.
	 * @return false, {@link PepperModule} instance is not ready for any reason, true, else.
	 */
	@Override
	public boolean isReadyToStart() throws PepperModuleNotReadyException
	{
		//TODO make some initializations if necessary
		return(super.isReadyToStart());
	}
	
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * 
	 * This method is called by the pepper framework to import the corpus-structure. In case that you are creating an importer, 
	 * check if the default behavior of the corpus-structure import mechanism fits for your need. If the default behaviour does not 
	 * fullfill your needs, or you don't know the default, we recommend, to take a look into the
	 * 'Developer's Guide for Pepper modules', you will find on <a href="https://korpling.german.hu-berlin.de/saltnpepper/">https://korpling.german.hu-berlin.de/saltnpepper/</a>. 
	 * @param corpusGraph the CorpusGraph object, which has to be fullfilled.
	 */
	@Override
	public void importCorpusStructure(SCorpusGraph sCorpusGraph) throws PepperModuleException
	{
		//TODO remove the following line of code for adoption
		super.importCorpusStructure(sCorpusGraph);
	}
}

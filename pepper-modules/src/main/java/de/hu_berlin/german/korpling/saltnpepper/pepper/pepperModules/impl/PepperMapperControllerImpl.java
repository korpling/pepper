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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.MAPPING_RESULT;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapperController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.exceptions.NotInitializedException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * The class {@link PepperMapperControllerImpl} is a communicator class between a {@link PepperModule} and a {@link PepperMapper} object. The aim of this
 * class is to provide some fields, whicch can be set by the {@link PepperMapper} and be read by the {@link PepperModule} object. It does not
 * contain any reference to the {@link PepperMapper} object. This mechanism is used, to make sure that in case of a forgotten clean up, the 
 * {@link PepperMapper} object can be removed by the java garbage collector and does not overfill the main memory. 
 * 
 * @author Florian Zipser
 *
 */
public class PepperMapperControllerImpl extends Thread implements PepperMapperController{

	/**
	 * Initializes this object and sets its {@link ThreadGroup} and the name of the thread.
	 * @param threadGroup
	 * @param threadName
	 */
	public PepperMapperControllerImpl(ThreadGroup threadGroup, String threadName)
	{
		super(threadGroup,threadName);
	}
	/** mapper, controlled by this object. **/
	private PepperMapper pepperMapper= null;
	/**
	 * Sets the mapper, controlled by this object.
	 * @param pepperMapper
	 */
	@Override
	public void setPepperMapper(PepperMapper pepperMapper)
	{
		this.pepperMapper= pepperMapper;
	}
	
	/**
	 * Returns the mapper, controlled by this object.
	 * @return
	 */
	@Override
	public PepperMapper getPepperMapper()
	{
		return(pepperMapper);
	}
	/** when {@link #getPepperMapper()} is set to null, in {@link #map()}, the {@link MAPPING_RESULT} value has to be stored here.  **/
	protected volatile MAPPING_RESULT mappingResult= null;
	/** {@inheritDoc PepperMapperConnector#getMappingResult()} **/
	@Override
	public MAPPING_RESULT getMappingResult() {
		if (this.getPepperMapper()!= null)
			return(this.getPepperMapper().getMappingResult());
		else if (mappingResult!= null)
			return(mappingResult);
		else
			throw new PepperModuleException("this.getPepperMapper() is empty and internal mappingResult is null for '"+this.getSElementId()+"', this might be a bug of pepper.");
	}
	/** {@link SElementId} object of the {@link SCorpus} or {@link SDocument} object, which is contained by containing {@link PepperMapper}**/
	protected volatile SElementId sElementId= null;
	/**
	 * {@inheritDoc PepperMapperConnector#getSElementId()}
	 */
	@Override
	public SElementId getSElementId() {
		return sElementId;
	}
	/**
	 * {@inheritDoc PepperMapperConnector#setSElementId(SElementId)}
	 */
	@Override
	public void setSElementId(SElementId sElementId) {
		if (sElementId== null)
			throw new PepperModuleException("Cannot set an empty sElementId.");
		this.sElementId = sElementId;
	}
	
	/** when {@link #getPepperMapper()} is set to null, in {@link #map()}, the progress value has to be stored here.  **/
	protected volatile Double progress= null;
	/**
	 * {@inheritDoc PepperMapperConnector#getProgress()}
	 */
	@Override
	public Double getProgress() 
	{
		if (this.getPepperMapper()!= null)
			return(this.getPepperMapper().getProgress());
		else if (progress!= null)
			return(progress);
		else
			throw new PepperModuleException("Cannot return progress, because no PepperMapper '"+getName()+"' is given. This might be a bug of the Pepper module, please make sure, that method PepperModule.createPepperMapper() is implemented.");
	}
	
	
	/**
	 * This method starts the {@link PepperMapper} object in a thread. If {@link #getSCorpus()} is not null,
	 * {@link #mapSCorpus()} is called, if {@link #getSDocument()} is not null, {@link #mapSDocument()} is called.
	 */
	@Override
	public void run()
	{
		try
		{
			this.map();
		}catch (Exception e)
		{
			//TODO make some exception handling like having an exception list in PepperMapperConnector, to which the exception is added
			e.printStackTrace();
		}
		finally
		{
			//reset mapper object, in case it uses a big amount of main memory
			this.mappingResult= this.getPepperMapper().getMappingResult();
			this.progress= 1d;
			this.setPepperMapper(null);
			if (this.getPepperModule()== null)
				throw new PepperFWException("The containing PepperModule object is not set.");
			this.getPepperModule().done(this);
		}
	}
	
	/**
	 * {@inheritDoc PepperMapper#map()}
	 */
	@Override
	public void map() 
	{
		MAPPING_RESULT mappingResult= null;
	
		if (this.getPepperMapper().getSCorpus()!= null)
			mappingResult= this.getPepperMapper().mapSCorpus();
		else if (this.getPepperMapper().getSDocument()!= null)
			mappingResult= this.getPepperMapper().mapSDocument();
		else
			throw new NotInitializedException("Cannot start mapper, because neither the SDocument nor the SCorpus value is set.");
		
		this.getPepperMapper().setMappingResult(mappingResult);
	}
	
	/** PepperModule containing this object**/
	protected PepperModule pepperModule= null;
	/**
	 * {@inheritDoc PepperMapperController#setPepperModule(PepperModule)}
	 */
	@Override
	public void setPepperModule(PepperModule pepperModule) {
		this.pepperModule= pepperModule;
	}
	
	/**
	 * Returns {@link PepperModule} object containing this object
	 * @return
	 */
	public PepperModule getPepperModule() {
		return(this.pepperModule);
	}
}

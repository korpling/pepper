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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.NotInitializedException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.DocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.MappingSubject;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.ModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapperController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * TODO make docu
 * @author Florian Zipser
 */
public class PepperModuleImpl implements PepperModule, UncaughtExceptionHandler 
{	
	Logger logger= LoggerFactory.getLogger(PepperModuleImpl.class);
	
	/**
	 * TODO make docu
	 */
	protected PepperModuleImpl() {
		getFingerprint();
	}	
	
	/** a kind of a fingerprint of this object **/
	private PepperModuleDesc fingerprint= null;
	
	/**
	 * {@inheritDoc PepperModule#getFingerprint()}
	 */
	public PepperModuleDesc getFingerprint(){
		if (fingerprint== null){
			fingerprint= new PepperModuleDesc();
			
			if (this instanceof PepperManipulator){
				fingerprint.setModuleType(MODULE_TYPE.MANIPULATOR);
			}
			else if (this instanceof PepperImporter){
				fingerprint.setModuleType(MODULE_TYPE.IMPORTER);
			}
			else if (this instanceof PepperExporter){
				fingerprint.setModuleType(MODULE_TYPE.EXPORTER);
			}
		}
		return(fingerprint);
	}
	
	/**
	 * {@inheritDoc PepperModule#getName()}
	 */
	@Override
	public String getName() {
		return getFingerprint().getName();
	}
	/**
	 * Sets the name of this {@link PepperModule}. 
	 * <strong>Note, that a name can only be set once and not be changed again.</strong> 
	 * @param name name of this module.
	 */
	protected void setName(String name){
		if (	(name!= null)&&
				(getName()== null)){
			getFingerprint().setName(name);
		}
	}
	
	/**
	 * {@inheritDoc PepperModule#getModuleType()}
	 * @return
	 */
	@Override
	public MODULE_TYPE getModuleType() {
		return(getFingerprint().getModuleType());
	}
	/**
	 * {@inheritDoc PepperModule#getDesc()}
	 */
	@Override
	public String getDesc() {
		return getFingerprint().getDesc();
	}
	/**
	 * {@inheritDoc PepperModule#setDesc(String)}
	 */
	@Override
	public void setDesc(String desc) {
		getFingerprint().setDesc(desc);	
	}
	/**
	 * {@inheritDoc PepperModule#getSupplierContact()}
	 */
	@Override
	public URI getSupplierContact() {
		return(getFingerprint().getSupplierContact());
	}
	/**
	 * {@inheritDoc PepperModule#setSupplierContact(URI)}
	 */
	@Override
	public void setSupplierContact(URI supplierContact) {
		getFingerprint().setSupplierContact(supplierContact);
	}
	/**
	 * TODO make docu
	 */
	protected SaltProject saltProject = null;
	/**
	 * {@inheritDoc PepperModule#getSaltProject()}
	 */
	@Override
	public SaltProject getSaltProject() {
		return saltProject;
	}
	/**
	 * {@inheritDoc PepperModule#setSaltProject(SaltProject)}
	 */
	@Override
	public synchronized void setSaltProject(SaltProject newSaltProject) {
		saltProject= newSaltProject;
	}

	/**
	 * TODO make docu
	 */
	protected URI resources = null;
	/**
	 * {@inheritDoc PepperModule#getResources()}
	 */
	@Override
	public URI getResources() {
		return resources;
	}

	/**
	 * {@inheritDoc PepperModule#setResources(org.eclipse.emf.common.util.URI)}
	 */
	@Override	
	public void setResources(URI newResources) 
	{
		resources= newResources;
	}
	/**
	 * TODO make docu
	 */
	@Deprecated
	protected URI temproraries = null;

	/**
	 * {@inheritDoc PepperModule#getTemproraries()}
	 */
	@Override
	@Deprecated
	public URI getTemproraries() {
		return temproraries;
	}

	/**
	 * {@inheritDoc PepperModule#setTemproraries(URI)}
	 */
	@Override
	@Deprecated
	public void setTemproraries(URI newTemproraries) 
	{
		temproraries= newTemproraries;
	}
	
	/**
	 * TODO make docu
	 */
	protected String symbolicName = null;
	/**
	 * {@inheritDoc PepperModule#getSymbolicName()}
	 */
	@Override
	public String getSymbolicName() {
		return symbolicName;
	}

	/**
	 * {@inheritDoc PepperModule#setSymbolicName(String)}
	 */
	@Override
	public void setSymbolicName(String newSymbolicName) {
		symbolicName = newSymbolicName;
	}

	/**
	 * TODO make docu
	 */
	protected String version = null;
	/**
	 * {@inheritDoc PepperModule#getVersion()}
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * {@inheritDoc PepperModule#setVersion(String)}
	 */
	@Override
	public void setVersion(String newVersion) {
		version = newVersion;
	}

	/**
	 * A {@link PepperModuleProperties} object containing properties to customize the behaviour of this {@link PepperModule}. 
	 */
	private PepperModuleProperties properties= null;
	
	/**
	 * {@inheritDoc PepperModule#getProperties()}
	 */
	@Override
	public PepperModuleProperties getProperties()
	{
		return(properties);
	}
	
	/**
	 * {@inheritDoc PepperModule#setProperties(PepperModuleProperties)}
	 */
	@Override
	public void setProperties(PepperModuleProperties properties)
	{
		this.properties= properties;
	}
	
	/** The {@link ComponentContext} of the OSGi environment the bundle was started in.**/
	private ComponentContext componentContext=null;
	/**
	 * Returns the {@link ComponentContext} of the OSGi environment the bundle was started in.
	 * @return
	 */
	public ComponentContext getComponentContext()
	{
		return(this.componentContext);
	}
	
	/**
	 * This method is called by OSGi framework and sets the component context, this class is running in. 
	 * This method scans the given {@link ComponentContext} object for symbolic name and version and initializes its
	 * values {@link #symbolicName} and {@link #version} with it. When running this class in OSGi context,
	 * you do not have to set both values by hand. With the given architecture, the symbolic name and the bundle 
	 * version will be given by pom.xml, via MANIFEST.MF and finally read by this method. Also sets the internal
	 * field storing the {@link ComponentContext} which can be retrieved via {@link #getComponentContext()}. 
	 * @param componentContext
	 */
	@Activate
	protected void activate(ComponentContext componentContext) 
	{
		this.componentContext= componentContext;
		if (	(componentContext!= null)&&
				(componentContext.getBundleContext()!= null)&&
				(componentContext.getBundleContext().getBundle()!= null))
		{		
			this.setSymbolicName(componentContext.getBundleContext().getBundle().getSymbolicName());
			this.setVersion(componentContext.getBundleContext().getBundle().getVersion().toString());
		}
	}
	
	/**
	 * {@inheritDoc PepperModule#isReadyToStart()}
	 */
	public boolean isReadyToStart() throws PepperModuleNotReadyException
	{
		Boolean retVal= true;
		if (getResources()== null){
			retVal= false;
		}else{
			File resourceFile= new File(getResources().toFileString());
			if (!resourceFile.exists()){
				retVal= false;
			}
		}
		if (getModuleType()== null){
			retVal= false;
		}
		if (getName()== null){
			retVal= false;
		}
		return(retVal);
	}
	
	/** the controller object, which acts as bridge between Pepper framework and Pepper module.**/
	protected ModuleController moduleController= null;
	/**
	 * {@inheritDoc PepperModule#getPepperModuleController()}
	 */
	@Override
	public ModuleController getModuleController() {
		return(moduleController);
	}

	/**
	 * {@inheritDoc PepperModule#setPepperModuleController(ModuleController)}
	 */
	@Override
	public void setPepperModuleController(ModuleController newModuleController) {
		setPepperModuleController_basic(newModuleController);
		newModuleController.setPepperModule_basic(this);
	}

	/**
	 * {@inheritDoc PepperModule#setPepperModuleController_basic(ModuleController)}
	 */
	@Override
	public void setPepperModuleController_basic(ModuleController newModuleController) {
		moduleController= newModuleController;
	}
	
	/** The {@link SCorpusGraph} object which should be processed by this module.**/
	protected SCorpusGraph sCorpusGraph= null;
	/**
	 * {@inheritDoc PepperModule#getSCorpusGraph()}
	 */
	public SCorpusGraph getSCorpusGraph() {
		return sCorpusGraph;
	}
	/**
	 * {@inheritDoc PepperModule#setSCorpusGraph(SCorpusGraph)}
	 */
	public void setSCorpusGraph(SCorpusGraph newSCorpusGraph) {
		sCorpusGraph= newSCorpusGraph;
	}
	
	/**
	 * A threadsafe map of all {@link PepperMapperController} objects which are connected with a started {@link PepperMapper} corresponding to their
	 * {@link SElementId}.
	 */
	private Map<String, PepperMapperController> mappersControllers= null;
	
	/**
	 * A lock for method {@link #getMappers()} to create a new mappers list.
	 */
	private Lock getMapperConnectorLock= new ReentrantLock(); 
	/**
	 * Returns a threadsafe map of all {@link PepperMapperController} objects which are connected with a started {@link PepperMapper} corresponding to their
	 * @return
	 */
	protected Map<String, PepperMapperController> getMapperControllers()
	{
		if (mappersControllers== null)
		{
			getMapperConnectorLock.lock();
			try{
				if (mappersControllers== null)
					mappersControllers= new Hashtable<String, PepperMapperController>();
			}finally
			{getMapperConnectorLock.unlock();}
		}
		return(mappersControllers);
	}
	
	protected boolean isMultithreaded= true;
	/**
	 * {@inheritDoc PepperModule#setIsMultithreaded(boolean)}
	 */
	public void setIsMultithreaded(boolean isMultithreaded)
	{
		this.isMultithreaded= isMultithreaded;
	}
	
	/**
	 * {@inheritDoc PepperModule#isMultithreaded()}
	 */
	public boolean isMultithreaded()
	{
		return(isMultithreaded);
	}
	
	/** Group of all mapper threads of this module **/
	private ThreadGroup mapperThreadGroup= null;
	/**
	 * Returns a {@link ThreadGroup} where {@link PepperMapper} objects and the corresponding threads are
	 * supposed to run in.
	 * @return
	 */
	protected ThreadGroup getMapperThreadGroup(){
		return(mapperThreadGroup);
	}
	/**
	 * Sets a {@link ThreadGroup} where {@link PepperMapper} objects and the corresponding threads are
	 * supposed to run in.
	 * @param mapperThreadGroup
	 */
	protected void setMapperThreadGroup(ThreadGroup mapperThreadGroup){
		this.mapperThreadGroup= mapperThreadGroup;
	}
	
	// ========================== end: extract corpus-path
	/** an internal flag, to check if {@link #start(SElementId)} has been overridden or not. For downwards compatibility to modules implemented with < pepper 1.1.6**/
	private boolean isStartOverridden= true;
	/** A map relating {@link SElementId} belonging to {@link SDocument} objects to their {@link DocumentController} container.**/
	private Map<String, DocumentController> documentId2DC= null;
	/**
	 * Returns the map relating {@link SElementId} belonging to {@link SDocument} objects to their {@link DocumentController} container.
	 * @return map
	 */
	protected Map<String, DocumentController> getDocumentId2DC(){
		if (documentId2DC== null){
			synchronized (this) {
				if (documentId2DC== null){
					documentId2DC= new Hashtable<String, DocumentController>();
				}
			}
		}
		return(documentId2DC);
	}
	
	/**
	 * {@inheritDoc PepperModule#start()}
	 */
	@Override
	public void start() throws PepperModuleException
	{
		if (getSaltProject()== null)
			throw new PepperFWException("No salt project was set in module '"+getName()+", "+getVersion()+"'.");
		//creating new thread group for mapper threads
		mapperThreadGroup = new ThreadGroup(Thread.currentThread().getThreadGroup(), this.getName()+"_mapperGroup");
		boolean isStart= true;
		SElementId sElementId= null;
		DocumentController documentController= null;
		while ((isStart) || (sElementId!= null)){	
			isStart= false;
			documentController= this.getModuleController().next();
			if (documentController== null){
				break;
			}
			sElementId= documentController.getsDocumentId();
			getDocumentId2DC().put(SaltFactory.eINSTANCE.getGlobalId(sElementId), documentController);
			//call for using push-method
			try{
				this.start(sElementId);
			}catch (Exception e)
			{
				if (this.isStartOverridden)
				{// if start was overridden, for downwards compatibility to modules implemented with < pepper 1.1.6
					this.done(sElementId, DOCUMENT_STATUS.DELETED);
				}// if start was overridden, for downwards compatibility to modules implemented with < pepper 1.1.6
				throw new PepperModuleException(this, "",e);
			}
			if (this.isStartOverridden)
			{// if start was overridden, for downwards compatibility to modules implemented with < pepper 1.1.6
				this.done(sElementId, DOCUMENT_STATUS.COMPLETED);
			}// if start was overridden, for downwards compatibility to modules implemented with < pepper 1.1.6
		}	
		Collection<PepperMapperController> controllers=null;
		HashSet<PepperMapperController> alreadyWaitedFor= new HashSet<PepperMapperController>();
		//wait for all SDocuments to be finished
		controllers= Collections.synchronizedCollection(this.getMapperControllers().values());
		for (PepperMapperController controller: controllers)
		{
			try {
				controller.join();
				alreadyWaitedFor.add(controller);
			} catch (InterruptedException e) {
				throw new PepperFWException("Cannot wait for mapper thread '"+controller+"' in "+this.getName()+" to end. ", e);
			}
			this.done(controller);
		}
		
		this.end();
		//only wait for  controllers which have been added by end()
		for (PepperMapperController controller: this.getMapperControllers().values())
		{
			if (!alreadyWaitedFor.contains(controller)){
				try {
					controller.join();
				} catch (InterruptedException e) {
					throw new PepperFWException("Cannot wait for mapper thread '"+controller+"' in "+this.getName()+" to end. ", e);
				}
				this.done(controller);
			}
		}
	}
	
	/** A list containing all global ids, which have been mapped and for which the pepper fw has been notified, that they are processed. **/
	private HashSet<String> mappedIds= null; 
	/** 
	 * Returns a  list containing all global ids, which have been mapped and for which the pepper fw has been notified, 
	 * that they are processed.
	 * @return list containing all already processed global ids
	 **/
	private synchronized Collection<String> getMappedIds()
	{
		if (mappedIds== null)
			mappedIds= new HashSet<String>();
		return(mappedIds);
	}
	
	/**
	 * {@inheritDoc PepperModule#done(PepperMapperController)}
	 */
	public void done(SElementId sElementId, DOCUMENT_STATUS result)
	{
		if (sElementId.getIdentifiableElement() instanceof SDocument)
		{
			DocumentController docController= getDocumentId2DC().get(SaltFactory.eINSTANCE.getGlobalId(sElementId));
			if (docController== null){
				throw new PepperFWException("Cannot find a "+DocumentController.class.getSimpleName()+" object corresponding to "+SDocument.class.getSimpleName()+" '"+SaltFactory.eINSTANCE.getGlobalId(sElementId)+"'. Controllers are listed for the following SElementId objects: "+getDocumentId2DC()+". ");
			}
			if (DOCUMENT_STATUS.DELETED.equals(result)){
				this.getModuleController().delete(docController);
			}else if (DOCUMENT_STATUS.COMPLETED.equals(result)){
				this.getModuleController().complete(docController);
			}else if (DOCUMENT_STATUS.FAILED.equals(result))
			{
				logger.error("Cannot map '"+sElementId+"' with module '"+this.getName()+"', because of a mapping result was '"+DOCUMENT_STATUS.FAILED+"'.");
				this.getModuleController().delete(docController);
			}
			else throw new PepperModuleException(this, "Cannot notify pepper framework for process of SElementId '"+sElementId.getSId()+"', because the mapping result was '"+result+"', and only '"+DOCUMENT_STATUS.COMPLETED+"', '"+DOCUMENT_STATUS.FAILED+"' and '"+DOCUMENT_STATUS.DELETED+"' is permitted.");
		}
	}
	
	/**
	 * {@inheritDoc PepperModule#done(PepperMapperController)}
	 */
	@Override
	public void done(PepperMapperController controller)
	{
		if (controller== null)
			throw new PepperFWException("This might be a bug of Pepper framework. The given PepperMapperController is null in methode done().");
		if (controller.getMappingSubjects()!= null){
			for (MappingSubject subject: controller.getMappingSubjects()){
				String globalId= SaltFactory.eINSTANCE.getGlobalId(subject.getSElementId());
				DOCUMENT_STATUS result= null; 
				if (!getMappedIds().contains(globalId))
				{//only if framework has not already been notified
					this.getMappedIds().add(SaltFactory.eINSTANCE.getGlobalId(subject.getSElementId()));
					try{	
						result= subject.getMappingResult();
					}catch (Exception e){
						result= DOCUMENT_STATUS.FAILED;
					}
					this.done(subject.getSElementId(), result);
				}//only if framework has not already been notified
			}
		}
	}
	
	/**
	 * This method is called by method {@link #start()}, if the method was not overridden
	 * by the current class. If this is not the case, this method will be called for every document which has
	 * to be processed.
	 * @param sElementId the id value for the current document or corpus to process  
	 */
	@Override
	public void start(SElementId sElementId) throws PepperModuleException {
		//for downwards compatibility to modules implemented with < pepper 1.1.6
		isStartOverridden= false;
		//check if mapper has to be called in multi-threaded or single-threaded mode.
		
		//copy all corpora into finite list
		corporaToEnd= new Vector<SCorpus>();
		for (SCorpusGraph sCorpusGraph: this.getSaltProject().getSCorpusGraphs()){
			if (sCorpusGraph!= null){ 
				for (SCorpus sCorpus: sCorpusGraph.getSCorpora()){
					corporaToEnd.add(sCorpus);
				}
			}
		}
		
		
		if (	(sElementId!= null) &&
				(sElementId.getSIdentifiableElement()!= null) &&
				((sElementId.getSIdentifiableElement() instanceof SDocument) ||
				((sElementId.getSIdentifiableElement() instanceof SCorpus))))
		{//only if given sElementId belongs to an object of type SDocument or SCorpus	
			PepperMapperController controller= new PepperMapperControllerImpl(mapperThreadGroup, this.getName()+"_mapper("+ sElementId.getSId()+")");
			
			String id= sElementId.getSId();
			if (sElementId.getSIdentifiableElement() instanceof SDocument)
				id= SaltFactory.eINSTANCE.getGlobalId(sElementId);
			this.getMapperControllers().put(id, controller);
//			controller.setSElementId(sElementId);
			controller.setUncaughtExceptionHandler(this);
			controller.setPepperModule(this);
			
			PepperMapper mapper= this.createPepperMapper(sElementId);
			mapper.setProperties(this.getProperties());
			
			if (this instanceof PepperImporter){
				URI resource= ((PepperImporter)this).getSElementId2ResourceTable().get(sElementId);
				mapper.setResourceURI(resource);
			}
			
			if 	(	(sElementId.getSIdentifiableElement() instanceof SDocument) &&
					(mapper.getSDocument()== null)){
				mapper.setSDocument((SDocument)sElementId.getSIdentifiableElement());
			} else if (	(sElementId.getSIdentifiableElement() instanceof SCorpus)&&
						(mapper.getSCorpus()== null)){
				mapper.setSCorpus((SCorpus)sElementId.getSIdentifiableElement());
			}
			
			controller.setPepperMapper(mapper);
			
			if (this.isMultithreaded()){
				controller.start();
			}else
				controller.map();
		}//only if given sElementId belongs to an object of type SDocument or SCorpus
	}

	/**
	 * {@inheritDoc PepperModule#createPepperMapper(SElementId)}
	 */
	public PepperMapper createPepperMapper(SElementId sElementId)
	{
		throw new NotInitializedException("Cannot start mapping, because the method createPepperMapper() of module '"+this.getName()+"' has not been overridden. Please check that first.");
	}
	
	/**
	 * A list of all corpora, which should be called in method {@link #end()}. The interim storage
	 * necessary because of {@link ConcurrentModificationException}, 
	 * if any other modules adds or removes a corpus object in the meantime)
	 */
	private Collection<SCorpus> corporaToEnd= null; 
	
	/**
	 * Calls method {@link #start(SElementId)} for every root {@link SCorpus} of {@link SaltProject} object.
	 */
	@Override
	public void end() throws PepperModuleException 
	{
		if (getSaltProject()== null)
			throw new PepperModuleException(this, "Error in method end() salt project was empty.");
		if (getSaltProject().getSCorpusGraphs()== null)
			throw new PepperModuleException(this, "Error in method end() corpus graphs of salt project were empty.");
		
//		Collection<SCorpusGraph> sCorpusGraphs= Collections.synchronizedCollection(this.getSaltProject().getSCorpusGraphs());
//		for (SCorpusGraph sCorpusGraph: sCorpusGraphs)
//		{
//			if (sCorpusGraph!= null)
//			{
//				Collection<SCorpus> sCorpora= Collections.synchronizedCollection(sCorpusGraph.getSCorpora()); 
//				for (SCorpus sCorpus: sCorpora)
//				{
//					this.start(sCorpus.getSElementId());
//				}
//			}
//		}
		if (corporaToEnd!= null){
			for (SCorpus sCorpus: corporaToEnd){
				this.start(sCorpus.getSElementId());
			}
		}
	}

	/**
	 * Method catches Uncaught exceptions thrown by {@link PepperMapperImpl} while running
	 * as Thread. This method just logs the exception. It is assumed, that the {@link PepperMapperImpl}
	 * itself notifies the Pepper framework about the unsuccessful process. 
	 */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		logger.error("An exception was thrown by one of the mapper threads, but the thread having thrown that exception is not of type PepperMapperController. ",e);
		if (logger instanceof NOPLogger){
			e.printStackTrace();
		}	
	}
	
	/**
	 * {@inheritDoc PepperModule#getProgress(String)}
	 */
	@Override
	public Double getProgress(String globalId) 
	{
		if (globalId== null)
			throw new PepperFWException("Cannot return the progress for an empty sDocumentId.");
		
		PepperMapperController controller=  this.getMapperControllers().get(globalId);
		//outcommented for downwards compatibility to modules implemented with < pepper 1.1.6
		//if (controller== null)
		//	throw new PepperFWException("Cannot return the progress for sDocumentId '"+sDocumentId+"', because no mapper controller exists. This might be a bug.");
		if (controller!= null)
			return(controller.getProgress());
		else return(null);
	}
	/**
	 * {@inheritDoc PepperModule#getProgress()}
	 */
	@Override
	public Double getProgress() 
	{
		Collection<PepperMapperController> controllers= Collections.synchronizedCollection(this.getMapperControllers().values());
		Double progress= 0d;
		//walk through all controllers to aggregate progresses
		if (	(controllers!= null )&&
				(controllers.size()> 0))
		{
			for (PepperMapperController controller: controllers)
			{
				if (controller!= null)
				{
					progress= progress+ controller.getProgress();
				}
			}
			if (progress>0d)
				progress= progress/controllers.size();
		}
		return(progress);
	}
	/**
	 * {@inheritDoc PeppeprotectdrModule#proposeImportOrder()}
	 */
	@Override
	public List<SElementId> proposeImportOrder(SCorpusGraph sCorpusGraph) {
		 return(new Vector<SElementId>());
	}
	
	/**
	 * Returns a string representation of this object.
	 * <br/>
	 * <strong>Note: This representation cannot be used for serialization/ deserialization purposes.</strong>
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(getModuleType());
		result.append("(");
		result.append(getName());
		result.append(", ");
		if (getVersion()!= null)
			result.append(getVersion());
		else result.append("NO_VERSION");
		result.append(")");
		return result.toString();
	}
} //PepperModuleImpl

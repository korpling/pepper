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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * An object of this class observes a set of SDocument objects through the conversion process. If the status of an SDocument object changes
 * the PepperDocumentObserver object has to be notified. 
 * An internal table maps the correlation of an SDocument object, the currently corresponding PepperModule (via PepperModuleController) and
 * its current status. To observe a list of SDocument objects the method {@link #observeSDocument(SElementId)}} has to be called for each SDocument object. 
 * To change the status of one of the SDocument objects the method {@link #setSDocumentStatus(SElementId, PepperModuleController, PEPPER_SDOCUMENT_STATUS)} has
 * to be called. 
 * An object has a temprorary and a global status. The temprorary status is related to the PepperModule and can be one of {@link PEPPER_SDOCUMENT_STATUS}}. 
 * Whereas the global status is an aggregation of all temproray status, this means, that it is IN_PROCESS, until all PepperModules has processed one document.
 * If the flag {@link #removE_SDOCUMENT_AFTER_PROCESSING} is set, an SDocument object will be removed after it was processed by all PepperModules.  
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperDocumentControllerImpl#getPepperModuleControllers <em>Pepper Module Controllers</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperDocumentControllerImpl#getPepperJobController <em>Pepper Job Controller</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperDocumentControllerImpl#getREMOVE_SDOCUMENT_AFTER_PROCESSING <em>REMOVE SDOCUMENT AFTER PROCESSING</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperDocumentControllerImpl#getCOMPUTE_PERFORMANCE <em>COMPUTE PERFORMANCE</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperDocumentControllerImpl#getAMOUNT_OF_COMPUTABLE_SDOCUMENTS <em>AMOUNT OF COMPUTABLE SDOCUMENTS</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperDocumentControllerImpl#getCurrentAmountOfSDocuments <em>Current Amount Of SDocuments</em>}</li>
 * </ul>
 * </p>
 * @author Florian Zipser
 * @generated
 */
public class PepperDocumentControllerImpl extends EObjectImpl implements PepperDocumentController {
	/**
	 * The cached value of the '{@link #getPepperModuleControllers() <em>Pepper Module Controllers</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperModuleControllers()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperModuleController> pepperModuleControllers;

	/**
	 * The default value of the '{@link #getREMOVE_SDOCUMENT_AFTER_PROCESSING() <em>REMOVE SDOCUMENT AFTER PROCESSING</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getREMOVE_SDOCUMENT_AFTER_PROCESSING()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean REMOVE_SDOCUMENT_AFTER_PROCESSING_EDEFAULT = Boolean.TRUE;
	/**
	 * The cached value of the '{@link #getREMOVE_SDOCUMENT_AFTER_PROCESSING() <em>REMOVE SDOCUMENT AFTER PROCESSING</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getREMOVE_SDOCUMENT_AFTER_PROCESSING()
	 * @generated
	 * @ordered
	 */
	protected Boolean removE_SDOCUMENT_AFTER_PROCESSING = REMOVE_SDOCUMENT_AFTER_PROCESSING_EDEFAULT;

	/**
	 * The default value of the '{@link #getCOMPUTE_PERFORMANCE() <em>COMPUTE PERFORMANCE</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCOMPUTE_PERFORMANCE()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean COMPUTE_PERFORMANCE_EDEFAULT = Boolean.TRUE;

	/**
	 * The cached value of the '{@link #getCOMPUTE_PERFORMANCE() <em>COMPUTE PERFORMANCE</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCOMPUTE_PERFORMANCE()
	 * @generated
	 * @ordered
	 */
	protected Boolean computE_PERFORMANCE = COMPUTE_PERFORMANCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAMOUNT_OF_COMPUTABLE_SDOCUMENTS() <em>AMOUNT OF COMPUTABLE SDOCUMENTS</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAMOUNT_OF_COMPUTABLE_SDOCUMENTS()
	 * @generated
	 * @ordered
	 */
	protected static final Integer AMOUNT_OF_COMPUTABLE_SDOCUMENTS_EDEFAULT = new Integer(10);

	/**
	 * The default value of the '{@link #getCurrentAmountOfSDocuments() <em>Current Amount Of SDocuments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentAmountOfSDocuments()
	 * @generated
	 * @ordered
	 */
	protected static final Integer CURRENT_AMOUNT_OF_SDOCUMENTS_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected PepperDocumentControllerImpl() {
		super();
		init();
	}
	
	private void init()
	{
		this.sDocumentStatusTable= new Hashtable<SElementId, SDocumentStatus>();
		this.performanceResolver= new PerformanceResolver();
		this.currentlyProcessedSDocuments= new AmountOfSDocumentController();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperFWPackage.Literals.PEPPER_DOCUMENT_CONTROLLER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperModuleController> getPepperModuleControllers() {
		if (pepperModuleControllers == null) {
			pepperModuleControllers = new EObjectWithInverseResolvingEList<PepperModuleController>(PepperModuleController.class, this, PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER);
		}
		return pepperModuleControllers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperJob getPepperJobController() {
		if (eContainerFeatureID() != PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER) return null;
		return (PepperJob)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPepperJobController(PepperJob newPepperJobController, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newPepperJobController, PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperJobController(PepperJob newPepperJobController) {
		if (newPepperJobController != eInternalContainer() || (eContainerFeatureID() != PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER && newPepperJobController != null)) {
			if (EcoreUtil.isAncestor(this, newPepperJobController))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newPepperJobController != null)
				msgs = ((InternalEObject)newPepperJobController).eInverseAdd(this, PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER, PepperJob.class, msgs);
			msgs = basicSetPepperJobController(newPepperJobController, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER, newPepperJobController, newPepperJobController));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getREMOVE_SDOCUMENT_AFTER_PROCESSING() {
		return removE_SDOCUMENT_AFTER_PROCESSING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setREMOVE_SDOCUMENT_AFTER_PROCESSING(Boolean newREMOVE_SDOCUMENT_AFTER_PROCESSING) {
		Boolean oldREMOVE_SDOCUMENT_AFTER_PROCESSING = removE_SDOCUMENT_AFTER_PROCESSING;
		removE_SDOCUMENT_AFTER_PROCESSING = newREMOVE_SDOCUMENT_AFTER_PROCESSING;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__REMOVE_SDOCUMENT_AFTER_PROCESSING, oldREMOVE_SDOCUMENT_AFTER_PROCESSING, removE_SDOCUMENT_AFTER_PROCESSING));
		{//sets REMOVE_SDOCUMENT_AFTER_PROCESSING to false if AMOUNT_OF_COMPUTABLE_SDOCUMENTS is higher or equal zero
			if (this.getAMOUNT_OF_COMPUTABLE_SDOCUMENTS()<= 0)
				this.removE_SDOCUMENT_AFTER_PROCESSING= false;
		}//sets REMOVE_SDOCUMENT_AFTER_PROCESSING to false if AMOUNT_OF_COMPUTABLE_SDOCUMENTS is higher or equal zero
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getCOMPUTE_PERFORMANCE() {
		return computE_PERFORMANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setCOMPUTE_PERFORMANCE(Boolean newCOMPUTE_PERFORMANCE) {
		Boolean oldCOMPUTE_PERFORMANCE = computE_PERFORMANCE;
		computE_PERFORMANCE = newCOMPUTE_PERFORMANCE;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__COMPUTE_PERFORMANCE, oldCOMPUTE_PERFORMANCE, computE_PERFORMANCE));
		if (computE_PERFORMANCE)
		{
			this.performanceResolver= new PerformanceResolver();
		}
		else this.performanceResolver= null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Integer getAMOUNT_OF_COMPUTABLE_SDOCUMENTS() 
	{
		return(this.currentlyProcessedSDocuments.getMaxAmountOfDocuments());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setAMOUNT_OF_COMPUTABLE_SDOCUMENTS(Integer newAMOUNT_OF_COMPUTABLE_SDOCUMENTS) 
	{
		this.currentlyProcessedSDocuments.setMaxAmountOfDocuments(newAMOUNT_OF_COMPUTABLE_SDOCUMENTS);
		{//sets REMOVE_SDOCUMENT_AFTER_PROCESSING to true, if AMOUNT_OF_COMPUTABLE_SDOCUMENTS is equal or higher than zero
			if (newAMOUNT_OF_COMPUTABLE_SDOCUMENTS >= 0)
				this.removE_SDOCUMENT_AFTER_PROCESSING= true;
		}//sets REMOVE_SDOCUMENT_AFTER_PROCESSING to true, if AMOUNT_OF_COMPUTABLE_SDOCUMENTS is equal or higher than zero
	}

	/**
	 * Returns the number of currently processed documents. 
	 */
	public Integer getCurrentAmountOfSDocuments() 
	{
		return(this.currentlyProcessedSDocuments.getCurrentlyProcessedSDocuments());
	}

	// ============================ start: performance computing
	/**
	 * This class gives possibilities to log the processing time for PepperModules and documents.
	 */
	private class PerformanceResolver
	{
		private class PerformanceEntry
		{
			public SElementId sDocumentId= null;
			public PepperModuleController pModuleController= null;
			public Long time= null;
		}
		
		/**
		 * List of all PerformanceEntry objects
		 */
		private volatile EList<PerformanceEntry> performanceEntries= null;
		
		public PerformanceResolver()
		{
			this.performanceEntries= new BasicEList<PerformanceEntry>();
		}
		
		/**
		 * Notifies the PerformanceResolver object, that the processing of the given SDocument object has 
		 * been started by the given PepperModule instance.
		 * Creates a new PerformanceEntry and adds it to the performanceEntry list.
		 * @param sDocumentId
		 * @param pModuleController
		 */
		public synchronized void startDocument(	SElementId sDocumentId, 
												PepperModuleController pModuleController)
		{	
			if (	(sDocumentId!= null) &&
					(pModuleController!= null))
			{	
				PerformanceEntry performanceEntry= new PerformanceEntry();
				performanceEntry.sDocumentId= sDocumentId;
				performanceEntry.pModuleController= pModuleController;
				performanceEntry.time= System.nanoTime();
				
				if (this.performanceEntries== null)
					throw new PepperFWException("An internal exception occurs, while notifiying the Pepper framework, that a document has been finished by a module. Internal message('performanceEntries==null')");

				this.performanceEntries.add(performanceEntry);
			}
			else throw new PepperFWException("Cannot log performance, because sDocumentId object or PepperModuleController object is null.");
		}
		
		/**
		 * Notifies the PerformanceResolver object, that the processing of the given SDocument object has 
		 * been finished by the given PepperModule instance.
		 * @param sDocumentId
		 * @param pModuleController
		 */
		public synchronized void endDocument(	SElementId sDocumentId, 
												PepperModuleController pModuleController)
		{
			if (	(sDocumentId!= null) &&
					(pModuleController!= null))
			{
				PerformanceEntry performanceEntry= null;
				if (this.performanceEntries== null)
					throw new PepperFWException("An internal exception occurs, while notifiying the Pepper framework, that a document has been finished by a module. Internal message('performanceEntries==null')");
				for (int i= 0; i< this.performanceEntries.size(); i++)
				{//search the correct performance entry
					PerformanceEntry searchPerformanceEntry= this.performanceEntries.get(i);
					if (searchPerformanceEntry== null)
						throw new PepperFWException("An internal exception occurs, while notifiying the Pepper framework, that a document has been finished by a module. Internal message('searchPerformanceEntry==null')");
					if (searchPerformanceEntry.pModuleController== null)
						throw new PepperFWException("An internal exception occurs, while notifiying the Pepper framework, that a document has been finished by a module. Internal message('searchPerformanceEntry.pModuleController==null')");
					if (searchPerformanceEntry.sDocumentId== null)
						throw new PepperFWException("An internal exception occurs, while notifiying the Pepper framework, that a document has been finished by a module. Internal message('searchPerformanceEntry.sDocumentId==null')");
					if (	(searchPerformanceEntry.pModuleController.equals(pModuleController))&&
							(searchPerformanceEntry.sDocumentId.equals(sDocumentId)))
					{
						performanceEntry= searchPerformanceEntry;
						break;
					}
				}//search the correct performance entry
				
				if (performanceEntry== null)
					throw new PepperFWException("Cannot not log performance for document '"+sDocumentId.getSId()+"' and module '"+pModuleController.getPepperModule().getName()+"', because no entry in list was found.");
				performanceEntry.time= System.nanoTime() - performanceEntry.time;
			}
			else throw new PepperFWException("Cannot log performance, because sDocumentId or pModuleController is null.");
		}
		
		/**
		 * Returns the processing time to the given document in all modules.
		 * @param sDocumentId the id of the document, to which the total amount of time is requested
		 * @return the total amount of time, which was used for to process the given document 
		 */
		private synchronized Long getTotalProcessingTimeOfSDocument(SElementId sDocumentId)
		{
			Long retVal= null;
			for (int i= 0; i< this.performanceEntries.size(); i++)
			{//search the correct performance entry
				PerformanceEntry performanceEntry= this.performanceEntries.get(i);
				if (performanceEntry.sDocumentId.equals(sDocumentId))
				{
					if (retVal== null)
						retVal= performanceEntry.time;
					else
						retVal= retVal + performanceEntry.time;
				}
			}//search the correct performance entry
			
			return(retVal);
		}
		
		/**
		 * Returns the processing time which was needed by the given PepperModuleController to process
		 * all documents..
		 * @param pModuleController the PepperModuleController, to which the total amount of time is requested
		 * @return the total amount of time, which was by the given PepperDocumentController 
		 */
		private synchronized Long getTotalProcessingTimeOfPepperModule(PepperModuleController pModuleController)
		{
			Long retVal= null;
			for (int i= 0; i< this.performanceEntries.size(); i++)
			{//search the correct perfromance entry
				PerformanceEntry performanceEntry= this.performanceEntries.get(i);
				if (performanceEntry.pModuleController.equals(pModuleController))
				{
					if (retVal== null)
						retVal= performanceEntry.time;
					else
						retVal= retVal + performanceEntry.time;
				}
			}//search the correct perfromance entry
			return(retVal);
		}
	}
	
	/**
	 * An object which stores and resolves the needed time for processing.
	 */
	private volatile PerformanceResolver performanceResolver= null;
// ============================ end: performance computing
	
// ============================ start: AmountOfSDocumentController
	/**
`	 * An object, which contains all SDocument objects which are currently in process.
	 */
	private volatile AmountOfSDocumentController currentlyProcessedSDocuments= null;
		
	/**
	 * This class provides an object to store the queue of current processed {@link SDocument} objects related to 
	 * the {@link PepperModuleController} objects processing it. This class also takes care, that the maximal number
	 * of {@link SDocument} objects which can be processed at the same time will not be exceeded.
	 * <ul>
	 * 	<li>The method {@link #addSDocumentToQueue(SElementId)} adds an {@link SDocument} to the queue.</li>
	 *  <li>The method {@link #removeSDocumentFromQueue(SElementId) removes an {@link SDocument} from the queue.</li>
	 *  <li>The method {@link #waitForRemovingSDocumentFromQueue()} lets the invoking method wait until a new slot is free, in case of the maximal number of {@link SDocument} objects to process at the same time is reached.</li>
	 * </ul> 
	 */
	private class AmountOfSDocumentController
	{
		/**
		 * Lock to lock await and signal methods.
		 */
		protected volatile Lock lock= new ReentrantLock();
		
		/**
		 * If condition is achieved a new SDocument can be created.
		 */
		private volatile Condition spaceForNewSDocument=lock.newCondition();
		
		private volatile Integer maxAmountOfDocuments= AMOUNT_OF_COMPUTABLE_SDOCUMENTS_EDEFAULT;
		
		/**
		 * list of currently processed {@link SDocument}
		 */
		private volatile Collection<SElementId> sDocumentIds= null;
		
		/**
		 * Returns the number of currently processed documents. Means {@link SDocument} in 
		 * state {@link PEPPER_SDOCUMENT_STATUS#IN_PROCESS}.
		 */
		public Integer getCurrentlyProcessedSDocuments() 
		{
			return(this.sDocumentIds.size());
		}
		
		public AmountOfSDocumentController()
		{
			this.sDocumentIds= new Vector<SElementId>();
		}
		/**
		 * @param maxAmountOfDocuments the maxAmountOfDocuments to set
		 */
		public synchronized void setMaxAmountOfDocuments(Integer maxAmountOfDocuments) {
			this.maxAmountOfDocuments = maxAmountOfDocuments;
		}
		
		/**
		 * @return the maximal amount of documents
		 */
		public synchronized Integer getMaxAmountOfDocuments() {
			return maxAmountOfDocuments;
		}
		
		/**
		 * This method adds a new SDocumentId to the queue of current processed documents.
		 * @param sDocumentId
		 */
		public synchronized void addSDocumentToQueue(SElementId sDocumentId)
		{
			if (!this.sDocumentIds.contains(sDocumentId)) 
			{
				if (	(this.maxAmountOfDocuments> 0) &&
						(sDocumentIds.size()>= this.getMaxAmountOfDocuments()))
				{
					throw new PepperFWException("Cannot import or create a new document, because maximal number of simulaneously stored sDocument is reached ("+this.getMaxAmountOfDocuments()+"). SDocument-object that caused the exception is: "+sDocumentId.getSId()+". If this module is not an PepperImporter, this document has not yet been added by an PepperImporter.");
				}
			
				this.sDocumentIds.add(sDocumentId);
			}
		}
		
		/**
		 * This method removes the given sDocumentId from the queue of current processed documents.
		 * @param sDocumentId {@link SElementId} of the {@link SDocument} to remove from queue
		 */
		public synchronized void removeSDocumentFromQueue(SElementId sDocumentId)
		{
			if (sDocumentId== null)
				throw new PepperConvertException("Cannot remove the SDocument object from queue, because the given sDocumentId is null.");
			this.lock.lock();
			try{
				this.sDocumentIds.remove(sDocumentId);
				spaceForNewSDocument.signal();
			}catch (Exception e) {
				throw new PepperConvertException("An internal exception occured, can not unlock the constraint 'spaceForNewSDocument' after sending a signal.");	
			}finally{
				this.lock.unlock();
			}
		}
		
		/**
		 * This method constrain the calling method to wait until any SDocument has been removed
		 * from the queue of current processed SDocument-objects.
		 */
		public void waitForRemovingSDocumentFromQueue()
		{
			lock.lock();
			try {
				if (	(this.getMaxAmountOfDocuments()> 0)&&
						(this.getCurrentlyProcessedSDocuments()>= this.getMaxAmountOfDocuments()))
				{
					if (getLogService()!= null)
						getLogService().log(LogService.LOG_DEBUG, "waiting for a free slot for a new document...");
					boolean wait= true;
					long waitingTime= 0l;
					while (wait)
					{
						waitingTime= spaceForNewSDocument.awaitNanos(10000000000L);
						if (waitingTime> 0l)
							wait= false;
//						if (getLogService()!= null)
//							getLogService().log(LogService.LOG_DEBUG, getStatus4Print());
					}
					if (getLogService()!= null)
						getLogService().log(LogService.LOG_DEBUG, "waiting for a free slot for a new document... OK");
				}
			} catch (InterruptedException e) {
				throw new PepperFWException(e.getMessage());
			}
			finally
			{
				lock.unlock();
			}
		}
	}	
// ============================ end: AmountOfSDocumentController
	/**
	 * Stores the current status for every sElementId.
	 */
	private volatile Hashtable<SElementId, SDocumentStatus> sDocumentStatusTable= null;
	
	/**
	 * Returns the total percentage value of the progress of the Pepper workflow.
	 * @return percentage value between 0 and 1. 
	 */
	private double getTotalPercentage()
	{
		Collection<SDocumentStatus> sDocStatuses= Collections.synchronizedCollection(sDocumentStatusTable.values());
		double p= 0l;
		for (SDocumentStatus sDocStatus: sDocStatuses)
		{
			p= p+ sDocStatus.getPercentage();
		}
		return(p/sDocumentStatusTable.size());
	}
		
	/**
	 * Puts the given sDocumentId into an internal list of SElementIds belonging to SDocument objects which have to be observed. 
	 * Observing in this sense means, that this PepperDocumentController object logs everything what is done with a document. 
	 * If it was started by a PepperModule, if it was processed or removed by such a module.  
	 * @param sDocumentId {@link SElementId} of the {@link SDocument} object to observe
	 */
	public synchronized void observeSDocument(SElementId sDocumentId) 
	{
		if (getLogService()!= null)
			getLogService().log(LogService.LOG_DEBUG, "observing document: "+ sDocumentId.getSId());
		if (sDocumentId== null)
			throw new PepperConvertException("Cannot observe an empty sDocumentId.");
		if (	(sDocumentId.getSId()== null) ||
				(sDocumentId.getSId().isEmpty()))
			throw new PepperConvertException("Cannot observe a sDocumentId with an empty sId value.");
		if (sDocumentId.getSIdentifiableElement()== null)
			throw new PepperConvertException("Cannot observe an sDocumentId with an empty sIdentifiableElement: "+sDocumentId.getSId()+".");
		if (!(sDocumentId.getSIdentifiableElement() instanceof SDocument))
			throw new PepperConvertException("Cannot observe an sDocumentId whos sIdentifiableElement is not of type SDocument: "+sDocumentId.getSId()+".");
		
		if (	(this.getPepperModuleControllers()== null) ||
				(this.getPepperModuleControllers().size()== 0))
			throw new PepperConvertException("Cannot observe the given sDocumentId '"+sDocumentId.getSId()+"', because no PepperModuleControllers where given. Please set them first.");
		
		SDocumentStatus sDocumentStatus= this.sDocumentStatusTable.get(sDocumentId);
		if (sDocumentStatus!= null)
			throw new PepperConvertException("The document corresponding to the given sDocumentId is already observed.");
		
		sDocumentStatus= new SDocumentStatus();
		sDocumentStatus.setsDocumentId(sDocumentId);
		this.sDocumentStatusTable.put(sDocumentId, sDocumentStatus);
		
		for (PepperModuleController pModuleController: Collections.synchronizedCollection(this.getPepperModuleControllers()))
		{//create a PepperModuleStatusTuple for every controller
			sDocumentStatus.addPepperModule(pModuleController);
		}//create a PepperModuleStatusTuple for every controller
	}

	/**
	 * Returns a list of {@link SElementId} objects corresponding to {@link SDocument} objects, which are observed
	 * by this object.
	 * @return list of all {@link SElementId} objects 
	 */
	public EList<SElementId> getObservedSDocuments() 
	{
		EList<SElementId> retVal= null;
		if (	(this.sDocumentStatusTable!= null) &&
				(this.sDocumentStatusTable.size()> 0))
		{	
			retVal= new BasicEList<SElementId>();
			Enumeration<SElementId> sDocumentIds= this.sDocumentStatusTable.keys();
			while (sDocumentIds.hasMoreElements())
			{
				retVal.add(sDocumentIds.nextElement());
			}
		}
		return(retVal);
	}
	
	/**
	 * {@inheritDoc PepperDocumentController#setSDocumentStatus(SElementId, PepperModuleController, PEPPER_SDOCUMENT_STATUS)}
	 */
	public void setSDocumentStatus(	SElementId sDocumentId, 
									PepperModuleController pModuleController, 
									PEPPER_SDOCUMENT_STATUS status) 
	{
		if (sDocumentId== null)
			throw new PepperConvertException("Cannot add sDocument status, because it is null.");
		if (pModuleController== null)
			throw new PepperConvertException("Cannot add sDocument status, because the given moduleController is null.");

		SDocumentStatus sDocumentStatus= this.sDocumentStatusTable.get(sDocumentId);
		if (sDocumentStatus== null)
			throw new PepperConvertException("Cannot add sDocument status, because no entry for the sDocumentId '"+sDocumentId.getSId()+"' exists. Please call observeSDocument() first.");
		if (	(sDocumentStatus.getStepStatuses()== null) ||
				(sDocumentStatus.getStepStatuses().size()== 0))
			throw new PepperConvertException("Cannot add sDocument status, because no PepperModuleController-object is given for the sDocumentId '"+sDocumentId.getSId()+"'. Please call add PeppermoduleControllers first.");
		
		//updates step status and document status to the new given status
		sDocumentStatus.update(pModuleController, status);
		
		
		if (	(PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(sDocumentStatus.getGlobalStatus()))||
				(PEPPER_SDOCUMENT_STATUS.DELETED.equals(sDocumentStatus.getGlobalStatus()))||
				(PEPPER_SDOCUMENT_STATUS.FAILED.equals(sDocumentStatus.getGlobalStatus())))
		{//clean up when document is finished
			if (this.removE_SDOCUMENT_AFTER_PROCESSING)
			{//delete SDocumentGraph if SDocument was fully processed and flag to delete is set
				((SDocument)sDocumentId.getSIdentifiableElement()).setSDocumentGraph(null);
			}//delete SDocumentGraph if SDocument was fully processed and flag to delete is set
			this.currentlyProcessedSDocuments.removeSDocumentFromQueue(sDocumentId);
		}//clean up when document is finished
		
		//start: notify the performance resolver to log event
			if (this.performanceResolver!= null)
			{
				//log start time
				if (status.equals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS))
					this.performanceResolver.startDocument(sDocumentId, pModuleController);
				//log end time
				else if (	(status.equals(PEPPER_SDOCUMENT_STATUS.COMPLETED))||
							(status.equals(PEPPER_SDOCUMENT_STATUS.DELETED))||
							(status.equals(PEPPER_SDOCUMENT_STATUS.FAILED)))
					this.performanceResolver.endDocument(sDocumentId, pModuleController);
			}
		//end: notify the performance resolver to log event
		//start: notify the DocumentAmountController that one document has been removed
			if (status.equals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS))
			{//add the id of the currently processed SDocument object to the list of processed documents 
				this.currentlyProcessedSDocuments.addSDocumentToQueue(sDocumentId);
			}//add the id of the currently processed SDocument object to the list of processed documents
		//end: notify the DocumentAmountController that one document has been removed
	}

	/**
	 * Returns a String containing each observed document, and the status of each Pepper module which has to process the {@link SDocument}
	 * corresponding to the {@link SDocument}.
	 */
	public String getStatus4Print() 
	{
		String retVal= null;
		if (	(this.sDocumentStatusTable!= null) &&
				(this.sDocumentStatusTable.size()> 0))
		{	
			StringBuffer printStatus= new StringBuffer();
			printStatus.append("total progress: "+ Math.round(this.getTotalPercentage()*10000)/100.0+"%\n");
			Enumeration<SElementId> sDocumentIds= this.sDocumentStatusTable.keys();
			SElementId sDocumentId= null;
			while (sDocumentIds.hasMoreElements())
			{
				sDocumentId= sDocumentIds.nextElement();
				SDocumentStatus sDocStatus= this.sDocumentStatusTable.get(sDocumentId);
				printStatus.append(sDocumentId.getSId());
				printStatus.append(" ["+sDocStatus.getGlobalStatus()+"] ... "+Math.round(sDocStatus.getPercentage()*10000)/100.0+"%\n");
				for (StepStatus stepStatus: sDocStatus.getStepStatuses())
				{
					if (stepStatus.getpModuleController().getPepperModule()!= null) 
						printStatus.append("\t"+stepStatus.getpModuleController().getPepperModule().getName());
					else printStatus.append("\t"+stepStatus.getpModuleController());
					printStatus.append(" ["+stepStatus.getModuleStatus());
					if (PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(stepStatus.getModuleStatus()))
						printStatus.append(" "+ stepStatus.getRunTime()/100000+" ms ... "+(stepStatus.getPercentage()*10000)/100.0+"%]\n");
					else if (PEPPER_SDOCUMENT_STATUS.NOT_STARTED.equals(stepStatus.getModuleStatus()))
						printStatus.append("]\n");
					else
						printStatus.append(" "+ stepStatus.getRunTime()/100000+" ms ... "+(stepStatus.getPercentage()*10000)/100.0+"% ]\n");
						
				}
			}
			retVal= printStatus.toString();
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public PEPPER_SDOCUMENT_STATUS getStatus(SElementId sDocumentId) 
	{
		if (sDocumentId== null)
			throw new PepperConvertException("Cannot return sDocument status, because it is null.");
		
		SDocumentStatus sDocStatus= this.sDocumentStatusTable.get(sDocumentId);
		if (sDocStatus== null)
			throw new PepperConvertException("Cannot return sDocument status, the given sDocumentId '"+sDocumentId+"' has not been observed. Please call observeSDocument(sDocumentId) first.");
		return(sDocStatus.getGlobalStatus());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	*/
	public PEPPER_SDOCUMENT_STATUS getStatus(SElementId sDocumentId, PepperModuleController pepperModuleController) 
	{
		PEPPER_SDOCUMENT_STATUS retVal= null;
		
		if (sDocumentId== null)
			throw new PepperConvertException("Cannot return sDocument status, because it is null.");
		if (pepperModuleController== null)
			throw new PepperConvertException("Cannot return sDocument status, because the given moduleController is null.");
		
		SDocumentStatus sDocumentStatus= this.sDocumentStatusTable.get(sDocumentId);
		for (StepStatus stepStatus: sDocumentStatus.getStepStatuses())
		{
			if (stepStatus.getpModuleController().equals(pepperModuleController))
			{
				retVal= stepStatus.getModuleStatus();
				break;
			}
		}
		return(retVal);
	}

	/**
	 * This method must be called if the PepperDocumentController shall be wasted and shall clean up. 
	 */
	public void finish() 
	{
		if (this.getCOMPUTE_PERFORMANCE())
		{
			if (this.getLogService()!= null)
			{	
				StringBuilder logText= new StringBuilder();
				for (int i= 0; i< this.pepperModuleControllers.size(); i++)
				{
					PepperModuleController pModuleController= this.pepperModuleControllers.get(i);
					Long neededTime= this.performanceResolver.getTotalProcessingTimeOfPepperModule(pModuleController);
					if (neededTime!= null)
						neededTime= neededTime /1000000;
					logText.append("total amount of time for '"+pModuleController.getPepperModule().getName()+"':\t"+neededTime+"ms\n");
				}
				Enumeration<SElementId> sDocumentIds= this.sDocumentStatusTable.keys();
				SElementId sDocumentId= null;
				while(sDocumentIds.hasMoreElements())
				{
					sDocumentId= sDocumentIds.nextElement();
					logText.append("total amount of time for processing document '"+sDocumentId.getSId()+"':\t"+this.performanceResolver.getTotalProcessingTimeOfSDocument(sDocumentId)/1000000+"ms\n");
				}
				this.getLogService().log(LogService.LOG_INFO, logText.toString());
			}
		}
	}

// ================================== start: LogService
	/**
	 * OSGi service for logging.
	 */
	private LogService logService= null;
	/**
	 * Sets the OSGi {@link LogService}, for logging in an OSGi environment.
	 * @param logService reference to a {@link LogService}
	 */
	public void setLogService(LogService logService) 
	{
		this.logService= logService;
	}

	/**
	 * Returns the OSGi {@link LogService}, for logging in an OSGi environment.
	 * @return reference to a {@link LogService}
	 */
	public LogService getLogService() 
	{
		return(this.logService);
	}
	
	/**
	 * This method shall be called by PepperModuleController, if it controls a PepperImportModule.
	 * This method regulates the maximal number of current processed SDocument-objects. This function
	 * is necessary if some PepperModules have different processing times, especially if an importer is 
	 * much faster than the other modules. In this case too much memory will be allocated and a memory-exception
	 * occurs. Therefore this methods regulates the maximal amount of documents. 
	 */
	public synchronized void waitForSDocument() 
	{
		this.currentlyProcessedSDocuments.waitForRemovingSDocumentFromQueue();
	}

	// ================================== end: LogService
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPepperModuleControllers()).basicAdd(otherEnd, msgs);
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetPepperJobController((PepperJob)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS:
				return ((InternalEList<?>)getPepperModuleControllers()).basicRemove(otherEnd, msgs);
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER:
				return basicSetPepperJobController(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER:
				return eInternalContainer().eInverseRemove(this, PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER, PepperJob.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS:
				return getPepperModuleControllers();
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER:
				return getPepperJobController();
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__REMOVE_SDOCUMENT_AFTER_PROCESSING:
				return getREMOVE_SDOCUMENT_AFTER_PROCESSING();
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__COMPUTE_PERFORMANCE:
				return getCOMPUTE_PERFORMANCE();
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__AMOUNT_OF_COMPUTABLE_SDOCUMENTS:
				return getAMOUNT_OF_COMPUTABLE_SDOCUMENTS();
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__CURRENT_AMOUNT_OF_SDOCUMENTS:
				return getCurrentAmountOfSDocuments();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS:
				getPepperModuleControllers().clear();
				getPepperModuleControllers().addAll((Collection<? extends PepperModuleController>)newValue);
				return;
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER:
				setPepperJobController((PepperJob)newValue);
				return;
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__REMOVE_SDOCUMENT_AFTER_PROCESSING:
				setREMOVE_SDOCUMENT_AFTER_PROCESSING((Boolean)newValue);
				return;
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__COMPUTE_PERFORMANCE:
				setCOMPUTE_PERFORMANCE((Boolean)newValue);
				return;
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__AMOUNT_OF_COMPUTABLE_SDOCUMENTS:
				setAMOUNT_OF_COMPUTABLE_SDOCUMENTS((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS:
				getPepperModuleControllers().clear();
				return;
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER:
				setPepperJobController((PepperJob)null);
				return;
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__REMOVE_SDOCUMENT_AFTER_PROCESSING:
				setREMOVE_SDOCUMENT_AFTER_PROCESSING(REMOVE_SDOCUMENT_AFTER_PROCESSING_EDEFAULT);
				return;
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__COMPUTE_PERFORMANCE:
				setCOMPUTE_PERFORMANCE(COMPUTE_PERFORMANCE_EDEFAULT);
				return;
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__AMOUNT_OF_COMPUTABLE_SDOCUMENTS:
				setAMOUNT_OF_COMPUTABLE_SDOCUMENTS(AMOUNT_OF_COMPUTABLE_SDOCUMENTS_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS:
				return pepperModuleControllers != null && !pepperModuleControllers.isEmpty();
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER:
				return getPepperJobController() != null;
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__REMOVE_SDOCUMENT_AFTER_PROCESSING:
				return REMOVE_SDOCUMENT_AFTER_PROCESSING_EDEFAULT == null ? removE_SDOCUMENT_AFTER_PROCESSING != null : !REMOVE_SDOCUMENT_AFTER_PROCESSING_EDEFAULT.equals(removE_SDOCUMENT_AFTER_PROCESSING);
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__COMPUTE_PERFORMANCE:
				return COMPUTE_PERFORMANCE_EDEFAULT == null ? computE_PERFORMANCE != null : !COMPUTE_PERFORMANCE_EDEFAULT.equals(computE_PERFORMANCE);
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__AMOUNT_OF_COMPUTABLE_SDOCUMENTS:
				return AMOUNT_OF_COMPUTABLE_SDOCUMENTS_EDEFAULT == null ? getAMOUNT_OF_COMPUTABLE_SDOCUMENTS() != null : !AMOUNT_OF_COMPUTABLE_SDOCUMENTS_EDEFAULT.equals(getAMOUNT_OF_COMPUTABLE_SDOCUMENTS());
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__CURRENT_AMOUNT_OF_SDOCUMENTS:
				return CURRENT_AMOUNT_OF_SDOCUMENTS_EDEFAULT == null ? getCurrentAmountOfSDocuments() != null : !CURRENT_AMOUNT_OF_SDOCUMENTS_EDEFAULT.equals(getCurrentAmountOfSDocuments());
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (REMOVE_SDOCUMENT_AFTER_PROCESSING: ");
		result.append(removE_SDOCUMENT_AFTER_PROCESSING);
		result.append(", COMPUTE_PERFORMANCE: ");
		result.append(computE_PERFORMANCE);
		result.append(')');
		return result.toString();
	}
} //PepperDocumentControllerImpl

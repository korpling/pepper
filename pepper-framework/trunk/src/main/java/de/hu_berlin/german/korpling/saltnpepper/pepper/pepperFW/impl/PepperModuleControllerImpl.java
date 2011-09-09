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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Module Controller</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl#getPepperModule <em>Pepper Module</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl#getPepperJob <em>Pepper Job</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl#getInputPepperModuleMonitors <em>Input Pepper Module Monitors</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl#getOutputPepperModuleMonitors <em>Output Pepper Module Monitors</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl#getPepperM2JMonitor <em>Pepper M2J Monitor</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl#getPepperJobLogger <em>Pepper Job Logger</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl#getPepperDocumentController <em>Pepper Document Controller</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PepperModuleControllerImpl extends EObjectImpl implements PepperModuleController {
	/**
	 * The cached value of the '{@link #getPepperModule() <em>Pepper Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperModule()
	 * @generated
	 * @ordered
	 */
	protected PepperModule pepperModule;

	/**
	 * The cached value of the '{@link #getInputPepperModuleMonitors() <em>Input Pepper Module Monitors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputPepperModuleMonitors()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperQueuedMonitor> inputPepperModuleMonitors;

	/**
	 * The cached value of the '{@link #getOutputPepperModuleMonitors() <em>Output Pepper Module Monitors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputPepperModuleMonitors()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperQueuedMonitor> outputPepperModuleMonitors;

	/**
	 * The cached value of the '{@link #getPepperM2JMonitor() <em>Pepper M2J Monitor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperM2JMonitor()
	 * @generated
	 * @ordered
	 */
	protected PepperFinishableMonitor pepperM2JMonitor;

	/**
	 * The cached value of the '{@link #getPepperJobLogger() <em>Pepper Job Logger</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperJobLogger()
	 * @generated
	 * @ordered
	 */
	protected PepperJobLogger pepperJobLogger;

	/**
	 * The cached value of the '{@link #getPepperDocumentController() <em>Pepper Document Controller</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperDocumentController()
	 * @generated
	 * @ordered
	 */
	protected PepperDocumentController pepperDocumentController;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperModuleControllerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperFWPackage.Literals.PEPPER_MODULE_CONTROLLER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModule getPepperModule() {
		return pepperModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPepperModule(PepperModule newPepperModule, NotificationChain msgs) {
		PepperModule oldPepperModule = pepperModule;
		pepperModule = newPepperModule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE, oldPepperModule, newPepperModule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperModule(PepperModule newPepperModule) {
		if (newPepperModule != pepperModule) {
			NotificationChain msgs = null;
			if (pepperModule != null)
				msgs = ((InternalEObject)pepperModule).eInverseRemove(this, PepperInterfacePackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER, PepperModule.class, msgs);
			if (newPepperModule != null)
				msgs = ((InternalEObject)newPepperModule).eInverseAdd(this, PepperInterfacePackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER, PepperModule.class, msgs);
			msgs = basicSetPepperModule(newPepperModule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE, newPepperModule, newPepperModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperJob getPepperJob() {
		if (eContainerFeatureID() != PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB) return null;
		return (PepperJob)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPepperJob(PepperJob newPepperJob, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newPepperJob, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperJob(PepperJob newPepperJob) {
		if (newPepperJob != eInternalContainer() || (eContainerFeatureID() != PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB && newPepperJob != null)) {
			if (EcoreUtil.isAncestor(this, newPepperJob))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newPepperJob != null)
				msgs = ((InternalEObject)newPepperJob).eInverseAdd(this, PepperFWPackage.PEPPER_JOB__PEPPER_MODULE_CONTROLLERS, PepperJob.class, msgs);
			msgs = basicSetPepperJob(newPepperJob, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB, newPepperJob, newPepperJob));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperQueuedMonitor> getInputPepperModuleMonitors() {
		if (inputPepperModuleMonitors == null) {
			inputPepperModuleMonitors = new EObjectResolvingEList<PepperQueuedMonitor>(PepperQueuedMonitor.class, this, PepperFWPackage.PEPPER_MODULE_CONTROLLER__INPUT_PEPPER_MODULE_MONITORS);
		}
		return inputPepperModuleMonitors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperQueuedMonitor> getOutputPepperModuleMonitors() {
		if (outputPepperModuleMonitors == null) {
			outputPepperModuleMonitors = new EObjectResolvingEList<PepperQueuedMonitor>(PepperQueuedMonitor.class, this, PepperFWPackage.PEPPER_MODULE_CONTROLLER__OUTPUT_PEPPER_MODULE_MONITORS);
		}
		return outputPepperModuleMonitors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperFinishableMonitor getPepperM2JMonitor() {
		if (pepperM2JMonitor != null && pepperM2JMonitor.eIsProxy()) {
			InternalEObject oldPepperM2JMonitor = (InternalEObject)pepperM2JMonitor;
			pepperM2JMonitor = (PepperFinishableMonitor)eResolveProxy(oldPepperM2JMonitor);
			if (pepperM2JMonitor != oldPepperM2JMonitor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_M2J_MONITOR, oldPepperM2JMonitor, pepperM2JMonitor));
			}
		}
		return pepperM2JMonitor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperFinishableMonitor basicGetPepperM2JMonitor() {
		return pepperM2JMonitor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperM2JMonitor(PepperFinishableMonitor newPepperM2JMonitor) {
		PepperFinishableMonitor oldPepperM2JMonitor = pepperM2JMonitor;
		pepperM2JMonitor = newPepperM2JMonitor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_M2J_MONITOR, oldPepperM2JMonitor, pepperM2JMonitor));
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperJobLogger getPepperJobLogger() {
		if (pepperJobLogger != null && pepperJobLogger.eIsProxy()) {
			InternalEObject oldPepperJobLogger = (InternalEObject)pepperJobLogger;
			pepperJobLogger = (PepperJobLogger)eResolveProxy(oldPepperJobLogger);
			if (pepperJobLogger != oldPepperJobLogger) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB_LOGGER, oldPepperJobLogger, pepperJobLogger));
			}
		}
		return pepperJobLogger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperJobLogger basicGetPepperJobLogger() {
		return pepperJobLogger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperJobLogger(PepperJobLogger newPepperJobLogger) {
		PepperJobLogger oldPepperJobLogger = pepperJobLogger;
		pepperJobLogger = newPepperJobLogger;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB_LOGGER, oldPepperJobLogger, pepperJobLogger));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperDocumentController getPepperDocumentController() {
		if (pepperDocumentController != null && pepperDocumentController.eIsProxy()) {
			InternalEObject oldPepperDocumentController = (InternalEObject)pepperDocumentController;
			pepperDocumentController = (PepperDocumentController)eResolveProxy(oldPepperDocumentController);
			if (pepperDocumentController != oldPepperDocumentController) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER, oldPepperDocumentController, pepperDocumentController));
			}
		}
		return pepperDocumentController;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperDocumentController basicGetPepperDocumentController() {
		return pepperDocumentController;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPepperDocumentController(PepperDocumentController newPepperDocumentController, NotificationChain msgs) {
		PepperDocumentController oldPepperDocumentController = pepperDocumentController;
		pepperDocumentController = newPepperDocumentController;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER, oldPepperDocumentController, newPepperDocumentController);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperDocumentController(PepperDocumentController newPepperDocumentController) {
		if (newPepperDocumentController != pepperDocumentController) {
			NotificationChain msgs = null;
			if (pepperDocumentController != null)
				msgs = ((InternalEObject)pepperDocumentController).eInverseRemove(this, PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS, PepperDocumentController.class, msgs);
			if (newPepperDocumentController != null)
				msgs = ((InternalEObject)newPepperDocumentController).eInverseAdd(this, PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS, PepperDocumentController.class, msgs);
			msgs = basicSetPepperDocumentController(newPepperDocumentController, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER, newPepperDocumentController, newPepperDocumentController));
	}

	/**
	 * 
	 * @param exception
	 */
	private void addException(PepperException exception)
	{
		if (this.getPepperM2JMonitor()!= null)
		{	
			this.getPepperM2JMonitor().getExceptions().add(exception);
			this.getPepperM2JMonitor().finish();
		}
		else
			throw new PepperFWException("There is no Module2JobMonitor set, therefore, exceptions can not be handled in normal way. Original Exception is: "+exception.getMessage(), exception);
	}
	
	/**
	 * An enumeration of possible thread run mode. 
	 *
	 */
	private enum THREAD_RUN_MODE {START, IMPORT_CORPUS_STRUCTURE};
	
	/**
	 * The actual thread run mode.
	 */
	private THREAD_RUN_MODE threadRunMode= null;
	@Override
	public void run() 
	{
		if (this.threadRunMode== THREAD_RUN_MODE.START)
			this.realStart();
		else if (this.threadRunMode== THREAD_RUN_MODE.IMPORT_CORPUS_STRUCTURE)
			this.realImportCorpusStructure(this.sCorpusGraph);
		else
			throw new PepperFWException("The thread run mode is not set correctly, the call of start() or importCorpusStructure() was not correct. Mode is: "+ this.threadRunMode);
	}
	
	private boolean isReadyToRun()
	{
		Boolean retVal= true;
		
		//no m2j-monitor
		if (	(this.getPepperM2JMonitor()== null))
		{	
			this.addException(new PepperConvertException("Cannot start() step, because no m2j-monitor is given."));
			retVal= false;
		}
		//no module
		if (this.getPepperModule()== null)
		{	
			this.addException(new PepperConvertException("Cannot start() step, because no module is given."));
			retVal= false;
		}
		return(retVal);
	}
	
	/**
	 * Creates a thread, which calls realStart().
	 */
	public void start() 
	{
		this.threadRunMode= THREAD_RUN_MODE.START;
		String threadName= null;
		if (this.getPepperModule()!= null)
			threadName= this.getPepperModule().getName();
		Thread moduleControllerThread= new Thread(this, "PepperModuleController["+threadName+"]");
		moduleControllerThread.start();
	}
	/**
	 * shows if step was started
	 */
	private boolean started= false;
	
	/**
	 * The real start call, can run in a thread or without.
	 */
	private void realStart() 
	{
		
		if (isReadyToRun())
		{
			//no input-monitor
			if (	(this.getInputPepperModuleMonitors()== null) ||
					(this.getInputPepperModuleMonitors().size()== 0))
			{	
				this.addException(new PepperConvertException("Cannot start() step, because no input-monitor is given."));
				return;
			}
			{//initialize everything, before start
				this.listOfNotPipelinedOrders= new BasicEList<SElementId>();
				//set started as true
				this.started= true;
				
				//initializing cache monitor
				if (cacheMonitor== null)
				{	
					cacheMonitor= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
				}
			}	
				
			//for all input monitors create an importController-thread
			for (PepperQueuedMonitor inputMonitor: this.getInputPepperModuleMonitors())
			{
				InputMonitorController inputMonitorController= new InputMonitorController();
				inputMonitorController.inputMonitor= inputMonitor;
				inputMonitorController.cacheMonitor= this.cacheMonitor;
				Thread cacheThread= new Thread(inputMonitorController, "PepperInputMonitorController");
				cacheThread.start();
			}
			
			//starting module
			try {
				this.getPepperModule().start();	
			} catch (Exception e) {
				throw new PepperException("Cannot convert data. ", e); 
			}
			{//checking, that no order remains in input-monitors or in cache
				for (PepperQueuedMonitor inputMonitor: this.getInputPepperModuleMonitors())
				{
					if ((!inputMonitor.isFinished()) || (!inputMonitor.isEmpty()))
					{
						this.addException(new PepperModuleException("An error occurs, there are document-ids which were not processed by module: "+this.getPepperModule().getName()));
						return;
					}
				}
				if ((!this.cacheMonitor.isFinished()) || (!this.cacheMonitor.isEmpty()))
				{	
					this.addException(new PepperModuleException("An error occurs, there are document-ids which were not processed by module: "+this.getPepperModule().getName()));
					return;
				}	
			}	
			
			//notifiying all output monitors for finished
			for (PepperQueuedMonitor m2mMonitor: this.getOutputPepperModuleMonitors())
			{
				m2mMonitor.finish();
			}
			//notifiying m2j-monitor for finished
			this.getPepperM2JMonitor().finish();
			
			{//controlling if every taken document was processed by PepperModule
				if (this.listOfNotPipelinedOrders.size()> 0)
				{	
					this.addException(new PepperModuleException("An error occurs, there are document-ids ("+this.listOfNotPipelinedOrders+") which were gettet by module, but not returned by calling put(element-id) or finish(element-id): "+this.getPepperModule().getName()));
					return;
				}
			}//controlling if every taken document was processed by PepperModule
		}
	}
	
	/**
	 * Stores the corpusGraph in which realImportCorpusStructure() shall import
	 */
	private SCorpusGraph sCorpusGraph= null; 
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void importCorpusStructure(SCorpusGraph sCorpusGraph) throws PepperConvertException {
		this.threadRunMode= THREAD_RUN_MODE.IMPORT_CORPUS_STRUCTURE;
		this.sCorpusGraph= sCorpusGraph;
		Thread moduleControllerThread= new Thread(this, "PepperModuleController["+this.getPepperModule().getName()+"]");
		moduleControllerThread.start();
	}
	
	/**
	 * The real implementation of importing the corpus structure.
	 */
	public void realImportCorpusStructure(SCorpusGraph sCorpusGraph) throws PepperConvertException 
	{
		if (isReadyToRun())
		{
			if (! (this.getPepperModule() instanceof PepperImporter))
			{
				this.addException(new PepperFWException("The given module is not of type PepperImporter: "+this.getPepperModule().getName()));
				return;
			}	
			((PepperImporter)this.getPepperModule()).importCorpusStructure(sCorpusGraph);
						
			{//potential checks if everything is ok
				
			}//potential checks if everything is ok
			
			//notifiying m2j-monitor for finished
			this.getPepperM2JMonitor().finish();
		}
	}
	

	/**
	 * internal monitor, to return the first arriving order.
	 */
	private PepperQueuedMonitor cacheMonitor= null;
	/**
	 * stores number of cache controllers how are finished
	 */
	protected volatile Integer numberOfFinishedCacheController= 0;
	
	/**
	 * This class is a container for input-monitors and puts all their output into the
	 * cache-monitor.
	 * @author Florian Zipser
	 *
	 */
	private class InputMonitorController implements Runnable
	{
		public PepperQueuedMonitor inputMonitor= null;
		public PepperQueuedMonitor cacheMonitor= null;
		
		@Override
		public void run() 
		{
			if (inputMonitor== null)
				throw new PepperConvertException("Sorry, this seems to be an internal failure. The input-monitor for CacheController is empty.");
			if (cacheMonitor== null)
				throw new PepperConvertException("Sorry, this seems to be an internal failure. The cache-monitor for CacheController is empty.");
			
			while (	(!this.inputMonitor.isFinished()) ||
					(!this.inputMonitor.isEmpty()))
			{	
				SElementId sElementId= this.inputMonitor.get();
				if (sElementId!= null)
				{	
					this.cacheMonitor.put(sElementId);
				}	
			}
			synchronized(numberOfFinishedCacheController)
			{
				numberOfFinishedCacheController++;
				if (numberOfFinishedCacheController.equals(getInputPepperModuleMonitors().size()))
				{	
					this.cacheMonitor.finish();	
				}
			}
		}
		
	}
	
	private EList<SElementId> listOfNotPipelinedOrders= null;
	
	/**
	 * Returns an Order listed in one of the input monitors. It always waits for the first 
	 * arriving order and returns this. Therefore there is an internal cache, which looks
	 * to all the input monitors and puts first return of input-monitor into the cache. 
	 */
	@Override
	public SElementId get() 
	{
		if (!this.started)
			throw new PepperConvertException("Cannot finish the given element-id, because module-controller was not started.");
		SElementId sElementId= null;
		sElementId= cacheMonitor.get();
		
		//puts the current element in list of not pipelined orders
		if (sElementId!= null)
		{
			this.listOfNotPipelinedOrders.add(sElementId);
		}
		
		{//notify, that sElementId is IN_PROCESS
			if (this.getPepperJobLogger()!= null)
				this.getPepperJobLogger().logStatus(sElementId, PEPPER_SDOCUMENT_STATUS.IN_PROCESS, this.getPepperModule().getName());
			if (sElementId!= null)
			{
				if (sElementId.getSIdentifiableElement()== null)
					throw new PepperConvertException("The sElementId '"+sElementId.getSId()+"' which was requested has no sIdentifiableElement.");
				if (sElementId.getSIdentifiableElement() instanceof SDocument)
				{	
					{//wait until a new document can be started in case of import
						if (this.getPepperModule() instanceof PepperImporter)
						{
							this.getPepperDocumentController().waitForSDocument();
						}
					}//wait until a new document can be started in case of import
					{//notify the document controller, that a new document has been started
						if (this.getPepperDocumentController()== null)
							throw new PepperFWException("No PepperDocumentController is given.");
						this.getPepperDocumentController().setSDocumentStatus(sElementId, this, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
					}//notify the document controller, that a new document has been started
				}
			}
		}//notify, that sElementId is IN_PROCESS
		return(sElementId);
	}

	@Override
	public void put(SElementId sElementId) 
	{
		{//notify, that sElementId is COMPLETED
			if (this.getPepperJobLogger()!= null)
				this.getPepperJobLogger().logStatus(sElementId, PEPPER_SDOCUMENT_STATUS.COMPLETED, this.getPepperModule().getName());
			if (sElementId.getSIdentifiableElement() instanceof SDocument)
				this.getPepperDocumentController().setSDocumentStatus(sElementId, this, PEPPER_SDOCUMENT_STATUS.COMPLETED);
		}//notify, that sElementId is COMPLETED
//		if (sElementId== null)
//			throw new PepperConvertException("Cannot put the given element-id, because its null.");
		if (!this.started)
			throw new PepperConvertException("Cannot finish the given element-id, because the module-controller was not started (please call sytart() first).");
		for (PepperQueuedMonitor m2mMonitor: this.getOutputPepperModuleMonitors())
			m2mMonitor.put(sElementId);
		//removes element id from list of not pipelined orders
		this.listOfNotPipelinedOrders.remove(sElementId);
	}
	
	@Override
	public void finish(SElementId sElementId) 
	{
		if (sElementId== null)
			throw new PepperConvertException("Cannot put the given element-id, because its null.");
		if (!this.started)
			throw new PepperConvertException("Cannot finish the given element-id, because module-controller was not started.");
		
		{//notify, that sElementId is DELETED
			if (this.getPepperJobLogger()!= null)
				this.getPepperJobLogger().logStatus(sElementId, PEPPER_SDOCUMENT_STATUS.DELETED, this.getPepperModule().getName());
			if (sElementId.getSIdentifiableElement() instanceof SDocument)
				this.getPepperDocumentController().setSDocumentStatus(sElementId, this, PEPPER_SDOCUMENT_STATUS.DELETED);
		}//notify, that sElementId is DELETED
		
		//removes element id from list of not pipelined orders
		this.listOfNotPipelinedOrders.remove(sElementId);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE:
				if (pepperModule != null)
					msgs = ((InternalEObject)pepperModule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE, null, msgs);
				return basicSetPepperModule((PepperModule)otherEnd, msgs);
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetPepperJob((PepperJob)otherEnd, msgs);
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER:
				if (pepperDocumentController != null)
					msgs = ((InternalEObject)pepperDocumentController).eInverseRemove(this, PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS, PepperDocumentController.class, msgs);
				return basicSetPepperDocumentController((PepperDocumentController)otherEnd, msgs);
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
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE:
				return basicSetPepperModule(null, msgs);
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB:
				return basicSetPepperJob(null, msgs);
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER:
				return basicSetPepperDocumentController(null, msgs);
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
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB:
				return eInternalContainer().eInverseRemove(this, PepperFWPackage.PEPPER_JOB__PEPPER_MODULE_CONTROLLERS, PepperJob.class, msgs);
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
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE:
				return getPepperModule();
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB:
				return getPepperJob();
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__INPUT_PEPPER_MODULE_MONITORS:
				return getInputPepperModuleMonitors();
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__OUTPUT_PEPPER_MODULE_MONITORS:
				return getOutputPepperModuleMonitors();
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_M2J_MONITOR:
				if (resolve) return getPepperM2JMonitor();
				return basicGetPepperM2JMonitor();
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB_LOGGER:
				if (resolve) return getPepperJobLogger();
				return basicGetPepperJobLogger();
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER:
				if (resolve) return getPepperDocumentController();
				return basicGetPepperDocumentController();
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
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE:
				setPepperModule((PepperModule)newValue);
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB:
				setPepperJob((PepperJob)newValue);
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__INPUT_PEPPER_MODULE_MONITORS:
				getInputPepperModuleMonitors().clear();
				getInputPepperModuleMonitors().addAll((Collection<? extends PepperQueuedMonitor>)newValue);
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__OUTPUT_PEPPER_MODULE_MONITORS:
				getOutputPepperModuleMonitors().clear();
				getOutputPepperModuleMonitors().addAll((Collection<? extends PepperQueuedMonitor>)newValue);
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_M2J_MONITOR:
				setPepperM2JMonitor((PepperFinishableMonitor)newValue);
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB_LOGGER:
				setPepperJobLogger((PepperJobLogger)newValue);
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER:
				setPepperDocumentController((PepperDocumentController)newValue);
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
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE:
				setPepperModule((PepperModule)null);
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB:
				setPepperJob((PepperJob)null);
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__INPUT_PEPPER_MODULE_MONITORS:
				getInputPepperModuleMonitors().clear();
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__OUTPUT_PEPPER_MODULE_MONITORS:
				getOutputPepperModuleMonitors().clear();
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_M2J_MONITOR:
				setPepperM2JMonitor((PepperFinishableMonitor)null);
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB_LOGGER:
				setPepperJobLogger((PepperJobLogger)null);
				return;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER:
				setPepperDocumentController((PepperDocumentController)null);
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
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE:
				return pepperModule != null;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB:
				return getPepperJob() != null;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__INPUT_PEPPER_MODULE_MONITORS:
				return inputPepperModuleMonitors != null && !inputPepperModuleMonitors.isEmpty();
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__OUTPUT_PEPPER_MODULE_MONITORS:
				return outputPepperModuleMonitors != null && !outputPepperModuleMonitors.isEmpty();
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_M2J_MONITOR:
				return pepperM2JMonitor != null;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB_LOGGER:
				return pepperJobLogger != null;
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER:
				return pepperDocumentController != null;
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * Returns a String representation of this object.
	 * @return a String representation of this object.
	 */
	public String toString()
	{
		StringBuffer retVal= new StringBuffer();
		retVal.append(this.getClass().getSimpleName());
		retVal.append("(");
		if (this.getPepperModule()== null)
			retVal.append("null");
		else retVal.append(this.getPepperModule().getName());
		retVal.append(")");
		return(retVal.toString());
	}
} //PepperModuleControllerImpl

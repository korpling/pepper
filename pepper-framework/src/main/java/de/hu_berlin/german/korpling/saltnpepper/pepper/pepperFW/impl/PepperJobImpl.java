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

import java.io.File;
import java.util.Collection;
import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusStructureFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Convert Job</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getPepperImporters <em>Pepper Importers</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getPepperModules <em>Pepper Modules</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getPepperExporters <em>Pepper Exporters</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getId <em>Id</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getPepperModuleControllers <em>Pepper Module Controllers</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getPepperM2MMonitors <em>Pepper M2M Monitors</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getPepperM2JMonitors <em>Pepper M2J Monitors</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getSaltProject <em>Salt Project</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getPepperJ2CMonitor <em>Pepper J2C Monitor</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getPepperJobLogger <em>Pepper Job Logger</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getPepperDocumentController <em>Pepper Document Controller</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PepperJobImpl extends EObjectImpl implements PepperJob 
{
	/**
	 * The cached value of the '{@link #getPepperImporters() <em>Pepper Importers</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperImporters()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperImporter> pepperImporters;

	/**
	 * The cached value of the '{@link #getPepperModules() <em>Pepper Modules</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperModules()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperModule> pepperModules;

	/**
	 * The cached value of the '{@link #getPepperExporters() <em>Pepper Exporters</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperExporters()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperExporter> pepperExporters;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final Integer ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected Integer id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPepperModuleControllers() <em>Pepper Module Controllers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperModuleControllers()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperModuleController> pepperModuleControllers;

	/**
	 * The cached value of the '{@link #getPepperM2MMonitors() <em>Pepper M2M Monitors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperM2MMonitors()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperQueuedMonitor> pepperM2MMonitors;

	/**
	 * The cached value of the '{@link #getPepperM2JMonitors() <em>Pepper M2J Monitors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperM2JMonitors()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperFinishableMonitor> pepperM2JMonitors;

	/**
	 * The default value of the '{@link #getSaltProject() <em>Salt Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSaltProject()
	 * @generated
	 * @ordered
	 */
	protected static final SaltProject SALT_PROJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSaltProject() <em>Salt Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSaltProject()
	 * @generated
	 * @ordered
	 */
	protected SaltProject saltProject = SALT_PROJECT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPepperJ2CMonitor() <em>Pepper J2C Monitor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperJ2CMonitor()
	 * @generated
	 * @ordered
	 */
	protected PepperFinishableMonitor pepperJ2CMonitor;

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
	 * The cached value of the '{@link #getPepperDocumentController() <em>Pepper Document Controller</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperDocumentController()
	 * @generated
	 * @ordered
	 */
	protected PepperDocumentController pepperDocumentController;

	/**
	 * The default value of the '{@link #getProperties() <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected static final Properties PROPERTIES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected Properties properties = PROPERTIES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected PepperJobImpl() {
		super();
		this.init();
	}
	
	protected void init()
	{
		//have to be new initialized for every start() 
		this.allModuleControlers= new BasicEList<PepperModuleController>();
		this.allM2JMonitors= new BasicEList<PepperFinishableMonitor>();
		this.setPepperDocumentController(PepperFWFactory.eINSTANCE.createPepperDocumentController());
	}

// ========================================== start: LogService	
	private LogService logService;

	public void setLogService(LogService logService) 
	{
		this.logService = logService;
		this.pepperDocumentController.setLogService(this.logService);
	}
	
	public LogService getLogService() 
	{
		return(this.logService);
	}
	
	public void unsetLogService(LogService logService) {
		this.logService= null;
	}

// ========================================== end: LogService
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperFWPackage.Literals.PEPPER_JOB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperImporter> getPepperImporters() {
		if (pepperImporters == null) {
			pepperImporters = new EDataTypeUniqueEList<PepperImporter>(PepperImporter.class, this, PepperFWPackage.PEPPER_JOB__PEPPER_IMPORTERS);
		}
		return pepperImporters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperModule> getPepperModules() {
		if (pepperModules == null) {
			pepperModules = new EDataTypeUniqueEList<PepperModule>(PepperModule.class, this, PepperFWPackage.PEPPER_JOB__PEPPER_MODULES);
		}
		return pepperModules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperExporter> getPepperExporters() {
		if (pepperExporters == null) {
			pepperExporters = new EDataTypeUniqueEList<PepperExporter>(PepperExporter.class, this, PepperFWPackage.PEPPER_JOB__PEPPER_EXPORTERS);
		}
		return pepperExporters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(Integer newId) {
		Integer oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_JOB__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperModuleController> getPepperModuleControllers() {
		if (pepperModuleControllers == null) {
			pepperModuleControllers = new EObjectContainmentWithInverseEList<PepperModuleController>(PepperModuleController.class, this, PepperFWPackage.PEPPER_JOB__PEPPER_MODULE_CONTROLLERS, PepperFWPackage.PEPPER_MODULE_CONTROLLER__PEPPER_JOB);
		}
		return pepperModuleControllers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperQueuedMonitor> getPepperM2MMonitors() {
		if (pepperM2MMonitors == null) {
			pepperM2MMonitors = new EObjectContainmentEList<PepperQueuedMonitor>(PepperQueuedMonitor.class, this, PepperFWPackage.PEPPER_JOB__PEPPER_M2M_MONITORS);
		}
		return pepperM2MMonitors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperFinishableMonitor> getPepperM2JMonitors() {
		if (pepperM2JMonitors == null) {
			pepperM2JMonitors = new EObjectContainmentEList<PepperFinishableMonitor>(PepperFinishableMonitor.class, this, PepperFWPackage.PEPPER_JOB__PEPPER_M2J_MONITORS);
		}
		return pepperM2JMonitors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SaltProject getSaltProject() {
		return saltProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSaltProject(SaltProject newSaltProject) {
		SaltProject oldSaltProject = saltProject;
		saltProject = newSaltProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_JOB__SALT_PROJECT, oldSaltProject, saltProject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperFinishableMonitor getPepperJ2CMonitor() {
		if (pepperJ2CMonitor != null && pepperJ2CMonitor.eIsProxy()) {
			InternalEObject oldPepperJ2CMonitor = (InternalEObject)pepperJ2CMonitor;
			pepperJ2CMonitor = (PepperFinishableMonitor)eResolveProxy(oldPepperJ2CMonitor);
			if (pepperJ2CMonitor != oldPepperJ2CMonitor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PepperFWPackage.PEPPER_JOB__PEPPER_J2C_MONITOR, oldPepperJ2CMonitor, pepperJ2CMonitor));
			}
		}
		return pepperJ2CMonitor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperFinishableMonitor basicGetPepperJ2CMonitor() {
		return pepperJ2CMonitor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperJ2CMonitor(PepperFinishableMonitor newPepperJ2CMonitor) {
		PepperFinishableMonitor oldPepperJ2CMonitor = pepperJ2CMonitor;
		pepperJ2CMonitor = newPepperJ2CMonitor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_JOB__PEPPER_J2C_MONITOR, oldPepperJ2CMonitor, pepperJ2CMonitor));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER, oldPepperJobLogger, pepperJobLogger));
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
	public NotificationChain basicSetPepperJobLogger(PepperJobLogger newPepperJobLogger, NotificationChain msgs) {
		PepperJobLogger oldPepperJobLogger = pepperJobLogger;
		pepperJobLogger = newPepperJobLogger;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER, oldPepperJobLogger, newPepperJobLogger);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperJobLogger(PepperJobLogger newPepperJobLogger) {
		if (newPepperJobLogger != pepperJobLogger) {
			NotificationChain msgs = null;
			if (pepperJobLogger != null)
				msgs = ((InternalEObject)pepperJobLogger).eInverseRemove(this, PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB, PepperJobLogger.class, msgs);
			if (newPepperJobLogger != null)
				msgs = ((InternalEObject)newPepperJobLogger).eInverseAdd(this, PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB, PepperJobLogger.class, msgs);
			msgs = basicSetPepperJobLogger(newPepperJobLogger, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER, newPepperJobLogger, newPepperJobLogger));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperDocumentController getPepperDocumentController() {
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER, oldPepperDocumentController, newPepperDocumentController);
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
				msgs = ((InternalEObject)pepperDocumentController).eInverseRemove(this, PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER, PepperDocumentController.class, msgs);
			if (newPepperDocumentController != null)
				msgs = ((InternalEObject)newPepperDocumentController).eInverseAdd(this, PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER, PepperDocumentController.class, msgs);
			msgs = basicSetPepperDocumentController(newPepperDocumentController, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER, newPepperDocumentController, newPepperDocumentController));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * Name of property for the flag if perfromance shall be measured and displayed 
	 */
	public static final String PROP_COMPUTE_PERFORMANCE= "pepper.computePerformance";
	/**
	 * Name of property for the maximal number of actually processed SDocument-objects 
	 */
	public static final String PROP_COMPUTE_AMOUNT_OFDOCS= "pepper.maxAmountOfProcessedSDocuments";
	/**
	 * Name of property for the flag if an SDocument-object shall be removed after it was processed by all PepperModules 
	 */
	public static final String PROP_REMOVE_SDOCUMENT_AFTER_PROCESSING= "pepper.removeSDocumentAfterProcessing";
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setProperties(Properties newProperties) {
		Properties oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_JOB__PROPERTIES, oldProperties, properties));
		
		{//extract property-information
			if (newProperties!= null)
			{	
				String performanceVal= properties.getProperty(PROP_COMPUTE_PERFORMANCE);
				
				if (	(performanceVal!= null) &&
						(	(performanceVal.equalsIgnoreCase("true"))||
							(performanceVal.equalsIgnoreCase("false"))))
				{	
					Boolean val= new Boolean(performanceVal);
					this.pepperDocumentController.setCOMPUTE_PERFORMANCE(val);
				}
				
				String amountOfDocumentsVal= properties.getProperty(PROP_COMPUTE_AMOUNT_OFDOCS);
				if (amountOfDocumentsVal!= null)
				{	
					Integer val= null;
					try {
						val= new Integer(amountOfDocumentsVal);
					} catch (NumberFormatException e) {
						throw new PepperConvertException("Cannot start converting, because the property '"+PROP_COMPUTE_AMOUNT_OFDOCS +"' is not a number: "+amountOfDocumentsVal);
					}
					this.pepperDocumentController.setAMOUNT_OF_COMPUTABLE_SDOCUMENTS(val);
				}

				String removeVal= properties.getProperty(PROP_REMOVE_SDOCUMENT_AFTER_PROCESSING);
				if (	(removeVal!= null) &&
						(	(removeVal.equalsIgnoreCase("true"))||
							(removeVal.equalsIgnoreCase("false"))))
				{	
					Boolean val= Boolean.valueOf(performanceVal);
					this.pepperDocumentController.setREMOVE_SDOCUMENT_AFTER_PROCESSING(val);
				}
				
			}	
		}//extract property-information
	}

	@Override
	public void run() 
	{
		this.start();
	}
	
	/**
	 * Checks if the set workflow description is complete and the system is ready to start the workflow. If there is 
	 * something missing, this method will throw an {@link PepperConvertException}
	 */
	protected void validateBeforeStart() throws PepperConvertException
	{
		//if id of job is not set
		if (this.getId()== null)
			throw new PepperConvertException("Job Cannot start with converting, because the id for job is not set.");
		//if no saltProject is given
		if (this.getSaltProject()== null)
			throw new PepperConvertException("Job Cannot start with converting, because the salt-project for job "+this.getId()+" is not set.");
		//checking if importers are given
		if ((this.getPepperImporters()== null) || (this.getPepperImporters().size()< 1))
			throw new PepperConvertException("Job Cannot start with converting, because no importers are given.");
		//checking if exporters are given
		if ((this.getPepperExporters()== null) || (this.pepperExporters.size()< 1))
			throw new PepperConvertException("Cannot start with converting, because no exporters are given.");
	
		//start: checking if all importers are correct instantiated
			for (PepperImporter importer: this.getPepperImporters())
			{
				//check if importer has a corpus definition
				if (importer.getCorpusDefinition()== null)
					throw new PepperConvertException("Cannot start converting, because no corpus definition is set for importer: "+importer.getName());
				//check if importer has an uri
				if (importer.getCorpusDefinition().getCorpusPath()== null)
					throw new PepperConvertException("Cannot start converting, because no corpus path is set for importer: "+importer.getName());
				if (importer.getCorpusDefinition().getCorpusPath().toFileString()== null)
					throw new PepperConvertException("Cannot start converting, because the given corpus path is null for importer: '"+importer.getName()+ "'. Please set a corpus path as uri syntax, for example 'file:\\rootCorpus'.");
				//check if importer has an existing uri
				File corpusPath= new File(importer.getCorpusDefinition().getCorpusPath().toFileString());
				if (!corpusPath.exists())
					throw new PepperConvertException("Cannot start converting, because the given corpus path does not exists for importer: '"+importer.getName()+ "', path: '"+corpusPath.getAbsolutePath()+"'.");
				//setting saltProject to importer
				importer.setSaltProject(this.getSaltProject());
			}
		//end: checking if all importers are correct instantiated
		//start: checking if all manipulators are correct instantiated
			for (PepperModule module: this.getPepperModules())
			{
				//check only the PepperManipulators
				if (module instanceof PepperManipulator)
				{
					PepperManipulator manipulator= (PepperManipulator) module;
					//setting saltProject to manipulator
					manipulator.setSaltProject(this.getSaltProject());
				}
			}
		//end: checking if all manipulators are correct instantiated
		
		//start: checking if all exporters are correct instantiated
			for (PepperExporter exporter: this.getPepperExporters())
			{
				//check if exporter has a corpus definition
				if (exporter.getCorpusDefinition()== null)
					throw new PepperConvertException("Cannot start converting, because no corpus definition is set for exporter: "+exporter.getName());
				//check if exporter has an uri
				if (exporter.getCorpusDefinition().getCorpusPath()== null)
					throw new PepperConvertException("Cannot start converting, because no corpus path is set for exporter: "+exporter.getName());
				if (exporter.getCorpusDefinition().getCorpusPath().toFileString()== null)
					throw new PepperConvertException("Cannot start converting, because the given corpus path is null for exporter: '"+exporter.getName()+ "'.");
				//check if exporter has an existing uri
				File corpusPath= new File(exporter.getCorpusDefinition().getCorpusPath().toFileString());				
				if (!corpusPath.exists())
				{	
					if(corpusPath.isFile())
					{
						corpusPath.getParentFile().mkdirs();
					}
					else 
					{
						corpusPath.mkdirs();
					}
				}
				//setting saltProject to exporter
				exporter.setSaltProject(this.getSaltProject());
			}
		//end: checking if all exporters are correct instantiated
	}
	
	protected EList<PepperModuleController> allModuleControlers= null;
	protected EList<PepperFinishableMonitor> allM2JMonitors= null;
	
	/**
	 * Creates and wires a {@link PepperModuleController} object, which looks on given {@link PepperModule}
	 * @param module
	 */
	protected void createAndWirePepperModuleController(PepperModule module)
	{
		if (module== null)
			throw new PepperFWException("Method createAndWirePepperModuleController(PepperModule) cannot work with empty PepperModule.");
		PepperModuleController pepperModuleController= PepperFWFactory.eINSTANCE.createPepperModuleController();
		//set PepperJob Logger to ModuleController
		pepperModuleController.setPepperJobLogger(this.getPepperJobLogger());
		pepperModuleController.setPepperModule(module);
		allModuleControlers.add(pepperModuleController);
		
		PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
		pepperModuleController.setPepperM2JMonitor(m2jMonitor);
		allM2JMonitors.add(m2jMonitor);
	}
	
	/**
	 * Wires all given PepperModuleControllers with the given PepperDocumentController.
	 * @param pModuleControllers all module controllers to be wired
	 * @param pDocumentController document controller to be wired
	 */
	protected void wirePepperDocumentControllerWithPepperModuleControllers(	EList<PepperModuleController> pModuleControllers,
																			PepperDocumentController pDocumentController)
	{
		if (pepperDocumentController== null)
			throw new PepperFWException("The PepperModuleController-object is empty. This can be caused by resetting it to null (Please remove the resetting).");
		pDocumentController.getPepperModuleControllers().addAll(pModuleControllers);
	}
	
	protected class ImporterGraphPair 
	{
		public  PepperImporter importer= null;
		public  SCorpusGraph sCorpusGraph= null;
	}
	
	
	/**
	 * Creates a corpus-tree in salt project for every importer. 
	 * It also calls the importers to import their structure.  
	 */
	protected EList<ImporterGraphPair> importCorpusStructure()
	{
		if (this.getPepperImporters().size()== 0)
			throw new PepperFWException("Cannot import the corpus-structue, because no importers are given.");
		EList<ImporterGraphPair> importerGraphPairList= new BasicEList<ImporterGraphPair>();
		EList<PepperFinishableMonitor> listOfImportMonitors= new BasicEList<PepperFinishableMonitor>();
		EList<PepperModuleController> listOfImportControllers= new BasicEList<PepperModuleController>();
		
		//create a controller and monitor for import
		for (PepperImporter importer: this.getPepperImporters())
		{
			//create controller
			PepperModuleController importController= PepperFWFactory.eINSTANCE.createPepperModuleController();
			importController.setPepperModule(importer);
			listOfImportControllers.add(importController);
			//set PepperJob Logger to ModuleController
			importController.setPepperJobLogger(this.getPepperJobLogger());
			
			//create monitors
			PepperFinishableMonitor importMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
			importController.setPepperM2JMonitor(importMonitor);
			listOfImportMonitors.add(importMonitor);
		}
		//start importing all
		for (PepperModuleController moduleController: listOfImportControllers)
		{
			//create graphs
			SCorpusGraph corpGraph= SCorpusStructureFactory.eINSTANCE.createSCorpusGraph();
			this.getSaltProject().getSCorpusGraphs().add((SCorpusGraph) corpGraph);
			
			
			//store a paired list
			ImporterGraphPair importerGraphPair= new ImporterGraphPair();
			importerGraphPair.importer= (PepperImporter) moduleController.getPepperModule();
			importerGraphPair.sCorpusGraph= corpGraph;
			importerGraphPairList.add(importerGraphPair);
			//wire importer and corpus graph
			importerGraphPair.importer.setSCorpusGraph(corpGraph);
			
			//start importing structure
			moduleController.importCorpusStructure((SCorpusGraph) corpGraph);
		}	
		
		//waiting until all monitors are finished
		for (PepperFinishableMonitor monitor: listOfImportMonitors)
		{
			monitor.waitUntilFinished();
		}
		return(importerGraphPairList);
	}
	
	/**
	 * Seperates all modules into steps. 
	 * <ul/>
	 * 	<li>every manipulation-module has its own step</li>
	 * 	<li>every manipulation-module has its own step</li>
	 * 	<li>every exporter is in the same step</li>
	 * </ul>
	 * @return a list of steps, one step is a list of module-controllers
	 */
	protected EList<EList<PepperModuleController>> createSteps()
	{
		EList<EList<PepperModuleController>> steps= new BasicEList<EList<PepperModuleController>>();
		//every importer is in same step
		EList<PepperModuleController> importerStep= new BasicEList<PepperModuleController>();
		for (PepperImporter importer: this.getPepperImporters())
			importerStep.add((PepperModuleController)importer.getPepperModuleController());
		steps.add(importerStep);
		
		//every manipulation-module has its own step
		for (PepperModule module: this.getPepperModules())
		{
			EList<PepperModuleController> moduleStep= new BasicEList<PepperModuleController>();
			moduleStep.add((PepperModuleController)module.getPepperModuleController());
			steps.add(moduleStep);
		}	
		
		//every exporter is in the same step
		EList<PepperModuleController> exporterStep= new BasicEList<PepperModuleController>();
		for (PepperExporter exporter: this.getPepperExporters())
			exporterStep.add((PepperModuleController)exporter.getPepperModuleController());
		steps.add(exporterStep);
		
		return(steps);
	}
	
	/**
	 * Wire all module-controllers with each other, by creating a 
	 * PepperModule2ModuleMonitor. This method takes all module-controllers of step n 
	 * and uses them as input for PepperModule2ModuleMonitor, all module-controllers
	 * of step n+1 and uses them for output of PepperModule2ModuleMonitor.
	 * @param steps a list of steps, one step is a list of module-controllers
	 */
	protected void wireModuleControllers(EList<EList<PepperModuleController>> steps)
	{
		int run= 1;
		EList<PepperModuleController> lastStep= null;
		//running throug all steps
		for (EList<PepperModuleController> step: steps)
		{
			//just start at step 2 not before
			if (run!= 1)
			{
				//creating cross-product of modules of last step and current step
				for (PepperModuleController lastController: lastStep)
				{
					for (PepperModuleController currController: step)
					{
						PepperQueuedMonitor m2mMonitor= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
						lastController.getOutputPepperModuleMonitors().add(m2mMonitor);
						currController.getInputPepperModuleMonitors().add(m2mMonitor);
					}	
				}	
			}	
			lastStep= step;
			run++;
		}	
	}
	
	/**
	 * <ul>
	 * 	<li>Checks if everything necessary is set</li>
	 * 	<li>Initialize everything necessary  to start</li>
	 * 	<li>Import the corpus structure</li>
	 *  <li>Create and wire PepperModuleController for all modules</li>
	 *  <li>Wire all modules with each other, by creating a PepperModule2ModuleMonitor</li>
	 *  <li>Putting all element-ids from corpus structure to importers</li>
	 *  <li>Start all moduleControllers</li>
	 *  <li>Wait until all modules are finished</li>
	 * </ul>
	 */
	public void start() throws PepperConvertException
	{
		if (this.logService!= null) 
			this.logService.log(LogService.LOG_INFO,"===== Starting with job("+id+") ==============================");
		
		//checks if everything necessary is set
		this.validateBeforeStart();
		
		EList<ImporterGraphPair> importerGraphPairs= null;
		{//import the corpus structure
			importerGraphPairs= this.importCorpusStructure();
		}
		{//create and wire PepperModuleController for all modules
			//import corpus-structure first, because of the module-controllers 
			//can have only one m2j-monitor, and it will be overridden else
			
			//all importers
			for (PepperImporter importer: this.getPepperImporters())
			{
				this.createAndWirePepperModuleController(importer);
			}
			
			//all modules
			for (PepperModule module: this.getPepperModules())
			{ 
				this.createAndWirePepperModuleController(module); 
			}
			
			//all exporters
			for (PepperExporter exporter: this.getPepperExporters())
			{
				this.createAndWirePepperModuleController(exporter);
			}
		}	
		{//wire all PepperModuleControllers with PepperDocumentController
			this.wirePepperDocumentControllerWithPepperModuleControllers(this.allModuleControlers, this.getPepperDocumentController());
		}//wire all PepperModuleControllers with PepperDocumentController
		{//wire all modules with each other, by creating a PepperModule2ModuleMonitor
			//an ordered list which contain all steps, one step is a list of all modules in each step 
			EList<EList<PepperModuleController>> steps= this.createSteps();
			//wiring all module-controllers by steps
			this.wireModuleControllers(steps);
		}
		{//add all imported documents to PepperDocumentController to observe
			for (ImporterGraphPair importerGraphPair: importerGraphPairs)
			{	
				for (SDocument sDocument: importerGraphPair.sCorpusGraph.getSDocuments())
				{	
					this.getPepperDocumentController().observeSDocument(sDocument.getSElementId());
				}
			}
		}//add all imported documents to PepperDocumentController to observe
		LogService pepperJobLogger= null;
		if (this.getPepperJobLogger()!= null)
			pepperJobLogger= this.getPepperJobLogger().getLogService();
		{	//Putting all element-ids from corpus structure to importers
			if (pepperJobLogger!= null)
			{	
				this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "putting all element ids to importer");
				this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "{");
			}	
			//TODO this block shall be changed into traversing graph, or something (maybe an accessor module can supply this function)
			for (PepperImporter importer: this.getPepperImporters())
			{
				PepperQueuedMonitor importMonitor= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
				//add monitor to importController
				((PepperModuleController) importer.getPepperModuleController()).getInputPepperModuleMonitors().add(importMonitor);
				//search element-ids for current importer
				for (ImporterGraphPair pair: importerGraphPairs)
				{
					if (pepperJobLogger!= null)
					{	
						this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "\timporter "+ pair.importer.getName());
						this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "\t{");
					}
					//correct importer found
					if (pair.importer.equals(importer))
					{
						//searching through all documents of current graph 
						for (SDocument document: pair.sCorpusGraph.getSDocuments())
						{
							if (pepperJobLogger!= null)
								this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "\t\t"+document.getSElementId()+"...");
							//putting element-id into monitors queue
							importMonitor.put(document.getSElementId());
							if (pepperJobLogger!= null)
								this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "OK");
						}	
						//finishing the import monitor after putting all element-ids in its queue
						importMonitor.finish();
					}	
					if (pepperJobLogger!= null)
						this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "\t}");
				}	
			}	
			if (pepperJobLogger!= null)
				this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "}");
		}
		
		{//start all moduleControllers
			if (pepperJobLogger!= null)
			{	
				this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "starting all module controllers["+this.allModuleControlers.size()+"]");
				this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "{");
			}
			for (PepperModuleController moduleController: this.allModuleControlers)
			{	
				if (pepperJobLogger!= null)
					this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "\tcontroller of module '"+ moduleController.getPepperModule().getName()+ "'...");
				moduleController.start();
				if (pepperJobLogger!= null)
					this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "OK");
			}
			if (pepperJobLogger!= null)
				this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "}");
		}
		//wait until all modules are finished
		for (PepperFinishableMonitor m2jMonitor: allM2JMonitors)
		{
			if (pepperJobLogger!= null)
				this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "waiting for monitor: "+ m2jMonitor.getId()+ "...");
			m2jMonitor.waitUntilFinished();
			if (pepperJobLogger!= null)
				this.getPepperJobLogger().getLogService().log(LogService.LOG_DEBUG, "waiting for monitor: "+ m2jMonitor.getId()+ "... OK");
			
			//exception handling for exceptions in modulecontrollers
			if (m2jMonitor.getExceptions().size()> 0)
				this.getPepperJ2CMonitor().getExceptions().addAll(m2jMonitor.getExceptions());
		}
		this.getPepperJ2CMonitor().finish();
		
		this.pepperDocumentController.setLogService(this.getLogService());
		
		if (this.pepperDocumentController!= null)
			this.pepperDocumentController.finish();

		if (this.getLogService()!= null) 
		{	
			this.logService.log(LogService.LOG_INFO, this.getPepperDocumentController().getStatus4Print());
			this.logService.log(LogService.LOG_INFO,"=====Ending with job("+this.getId()+") ================================");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_JOB__PEPPER_MODULE_CONTROLLERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPepperModuleControllers()).basicAdd(otherEnd, msgs);
			case PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER:
				if (pepperJobLogger != null)
					msgs = ((InternalEObject)pepperJobLogger).eInverseRemove(this, PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB, PepperJobLogger.class, msgs);
				return basicSetPepperJobLogger((PepperJobLogger)otherEnd, msgs);
			case PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER:
				if (pepperDocumentController != null)
					msgs = ((InternalEObject)pepperDocumentController).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER, null, msgs);
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
			case PepperFWPackage.PEPPER_JOB__PEPPER_MODULE_CONTROLLERS:
				return ((InternalEList<?>)getPepperModuleControllers()).basicRemove(otherEnd, msgs);
			case PepperFWPackage.PEPPER_JOB__PEPPER_M2M_MONITORS:
				return ((InternalEList<?>)getPepperM2MMonitors()).basicRemove(otherEnd, msgs);
			case PepperFWPackage.PEPPER_JOB__PEPPER_M2J_MONITORS:
				return ((InternalEList<?>)getPepperM2JMonitors()).basicRemove(otherEnd, msgs);
			case PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER:
				return basicSetPepperJobLogger(null, msgs);
			case PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_JOB__PEPPER_IMPORTERS:
				return getPepperImporters();
			case PepperFWPackage.PEPPER_JOB__PEPPER_MODULES:
				return getPepperModules();
			case PepperFWPackage.PEPPER_JOB__PEPPER_EXPORTERS:
				return getPepperExporters();
			case PepperFWPackage.PEPPER_JOB__ID:
				return getId();
			case PepperFWPackage.PEPPER_JOB__PEPPER_MODULE_CONTROLLERS:
				return getPepperModuleControllers();
			case PepperFWPackage.PEPPER_JOB__PEPPER_M2M_MONITORS:
				return getPepperM2MMonitors();
			case PepperFWPackage.PEPPER_JOB__PEPPER_M2J_MONITORS:
				return getPepperM2JMonitors();
			case PepperFWPackage.PEPPER_JOB__SALT_PROJECT:
				return getSaltProject();
			case PepperFWPackage.PEPPER_JOB__PEPPER_J2C_MONITOR:
				if (resolve) return getPepperJ2CMonitor();
				return basicGetPepperJ2CMonitor();
			case PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER:
				if (resolve) return getPepperJobLogger();
				return basicGetPepperJobLogger();
			case PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER:
				return getPepperDocumentController();
			case PepperFWPackage.PEPPER_JOB__PROPERTIES:
				return getProperties();
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
			case PepperFWPackage.PEPPER_JOB__PEPPER_IMPORTERS:
				getPepperImporters().clear();
				getPepperImporters().addAll((Collection<? extends PepperImporter>)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_MODULES:
				getPepperModules().clear();
				getPepperModules().addAll((Collection<? extends PepperModule>)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_EXPORTERS:
				getPepperExporters().clear();
				getPepperExporters().addAll((Collection<? extends PepperExporter>)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__ID:
				setId((Integer)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_MODULE_CONTROLLERS:
				getPepperModuleControllers().clear();
				getPepperModuleControllers().addAll((Collection<? extends PepperModuleController>)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_M2M_MONITORS:
				getPepperM2MMonitors().clear();
				getPepperM2MMonitors().addAll((Collection<? extends PepperQueuedMonitor>)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_M2J_MONITORS:
				getPepperM2JMonitors().clear();
				getPepperM2JMonitors().addAll((Collection<? extends PepperFinishableMonitor>)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__SALT_PROJECT:
				setSaltProject((SaltProject)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_J2C_MONITOR:
				setPepperJ2CMonitor((PepperFinishableMonitor)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER:
				setPepperJobLogger((PepperJobLogger)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER:
				setPepperDocumentController((PepperDocumentController)newValue);
				return;
			case PepperFWPackage.PEPPER_JOB__PROPERTIES:
				setProperties((Properties)newValue);
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
			case PepperFWPackage.PEPPER_JOB__PEPPER_IMPORTERS:
				getPepperImporters().clear();
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_MODULES:
				getPepperModules().clear();
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_EXPORTERS:
				getPepperExporters().clear();
				return;
			case PepperFWPackage.PEPPER_JOB__ID:
				setId(ID_EDEFAULT);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_MODULE_CONTROLLERS:
				getPepperModuleControllers().clear();
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_M2M_MONITORS:
				getPepperM2MMonitors().clear();
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_M2J_MONITORS:
				getPepperM2JMonitors().clear();
				return;
			case PepperFWPackage.PEPPER_JOB__SALT_PROJECT:
				setSaltProject(SALT_PROJECT_EDEFAULT);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_J2C_MONITOR:
				setPepperJ2CMonitor((PepperFinishableMonitor)null);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER:
				setPepperJobLogger((PepperJobLogger)null);
				return;
			case PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER:
				setPepperDocumentController((PepperDocumentController)null);
				return;
			case PepperFWPackage.PEPPER_JOB__PROPERTIES:
				setProperties(PROPERTIES_EDEFAULT);
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
			case PepperFWPackage.PEPPER_JOB__PEPPER_IMPORTERS:
				return pepperImporters != null && !pepperImporters.isEmpty();
			case PepperFWPackage.PEPPER_JOB__PEPPER_MODULES:
				return pepperModules != null && !pepperModules.isEmpty();
			case PepperFWPackage.PEPPER_JOB__PEPPER_EXPORTERS:
				return pepperExporters != null && !pepperExporters.isEmpty();
			case PepperFWPackage.PEPPER_JOB__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case PepperFWPackage.PEPPER_JOB__PEPPER_MODULE_CONTROLLERS:
				return pepperModuleControllers != null && !pepperModuleControllers.isEmpty();
			case PepperFWPackage.PEPPER_JOB__PEPPER_M2M_MONITORS:
				return pepperM2MMonitors != null && !pepperM2MMonitors.isEmpty();
			case PepperFWPackage.PEPPER_JOB__PEPPER_M2J_MONITORS:
				return pepperM2JMonitors != null && !pepperM2JMonitors.isEmpty();
			case PepperFWPackage.PEPPER_JOB__SALT_PROJECT:
				return SALT_PROJECT_EDEFAULT == null ? saltProject != null : !SALT_PROJECT_EDEFAULT.equals(saltProject);
			case PepperFWPackage.PEPPER_JOB__PEPPER_J2C_MONITOR:
				return pepperJ2CMonitor != null;
			case PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER:
				return pepperJobLogger != null;
			case PepperFWPackage.PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER:
				return pepperDocumentController != null;
			case PepperFWPackage.PEPPER_JOB__PROPERTIES:
				return PROPERTIES_EDEFAULT == null ? properties != null : !PROPERTIES_EDEFAULT.equals(properties);
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
		result.append(" (pepperImporters: ");
		result.append(pepperImporters);
		result.append(", pepperModules: ");
		result.append(pepperModules);
		result.append(", pepperExporters: ");
		result.append(pepperExporters);
		result.append(", id: ");
		result.append(id);
		result.append(", saltProject: ");
		result.append(saltProject);
		result.append(", properties: ");
		result.append(properties);
		result.append(')');
		return result.toString();
	}

	

} //PepperConvertJobImpl

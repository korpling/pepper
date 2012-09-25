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

import java.io.File;

import org.apache.felix.scr.annotations.Activate;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PersistenceConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.modules.SCorpusStructureAccessor;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getPepperModuleController <em>Pepper Module Controller</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getSaltProject <em>Salt Project</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getReturningMode <em>Returning Mode</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getSCorpusGraph <em>SCorpus Graph</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getResources <em>Resources</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getTemproraries <em>Temproraries</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getSymbolicName <em>Symbolic Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getPersistenceConnector <em>Persistence Connector</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getSpecialParams <em>Special Params</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class PepperModuleImpl extends EObjectImpl implements PepperModule 
{
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

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
	 * The default value of the '{@link #getReturningMode() <em>Returning Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturningMode()
	 * @generated
	 * @ordered
	 */
	protected static final RETURNING_MODE RETURNING_MODE_EDEFAULT = RETURNING_MODE.PUT;

	/**
	 * The cached value of the '{@link #getReturningMode() <em>Returning Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturningMode()
	 * @generated
	 * @ordered
	 */
	protected RETURNING_MODE returningMode = RETURNING_MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSCorpusGraph() <em>SCorpus Graph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSCorpusGraph()
	 * @generated
	 * @ordered
	 */
	protected static final SCorpusGraph SCORPUS_GRAPH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSCorpusGraph() <em>SCorpus Graph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSCorpusGraph()
	 * @generated
	 * @ordered
	 */
	protected SCorpusGraph sCorpusGraph = SCORPUS_GRAPH_EDEFAULT;

	/**
	 * The default value of the '{@link #getResources() <em>Resources</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResources()
	 * @generated
	 * @ordered
	 */
	protected static final URI RESOURCES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResources() <em>Resources</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResources()
	 * @generated
	 * @ordered
	 */
	protected URI resources = RESOURCES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTemproraries() <em>Temproraries</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemproraries()
	 * @generated
	 * @ordered
	 */
	protected static final URI TEMPRORARIES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTemproraries() <em>Temproraries</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemproraries()
	 * @generated
	 * @ordered
	 */
	protected URI temproraries = TEMPRORARIES_EDEFAULT;

	/**
	 * The default value of the '{@link #getSymbolicName() <em>Symbolic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbolicName()
	 * @generated
	 * @ordered
	 */
	protected static final String SYMBOLIC_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSymbolicName() <em>Symbolic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbolicName()
	 * @generated
	 * @ordered
	 */
	protected String symbolicName = SYMBOLIC_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPersistenceConnector() <em>Persistence Connector</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistenceConnector()
	 * @generated
	 * @ordered
	 */
	protected PersistenceConnector persistenceConnector;

	/**
	 * The default value of the '{@link #getSpecialParams() <em>Special Params</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecialParams()
	 * @generated
	 * @ordered
	 */
	protected static final URI SPECIAL_PARAMS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecialParams() <em>Special Params</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecialParams()
	 * @generated
	 * @ordered
	 */
	protected URI specialParams = SPECIAL_PARAMS_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

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
	
	/**
	 * This method is called by OSGi framework and sets the component context, this class is running in. 
	 * This method scans the given {@link ComponentContext} object for symbolic name and version and initializes its
	 * values {@link #symbolicName} and {@link #version} with it. When running this class in OSGi context,
	 * you do not have to set both values by hand. With the given architecture, the symbolic name and the bundle 
	 * version will be given by pom.xml, via MMANIFEST.MF and finally read by this method. 
	 * @param componentContext
	 */
	@Activate
	protected void activate(ComponentContext componentContext) 
	{
		if (	(componentContext!= null)&&
				(componentContext.getBundleContext()!= null)&&
				(componentContext.getBundleContext().getBundle()!= null))
		{		
			this.setSymbolicName(componentContext.getBundleContext().getBundle().getSymbolicName());
			this.setVersion(componentContext.getBundleContext().getBundle().getVersion().toString());
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected PepperModuleImpl() {
		super();
		init();
	}

	private void init()
	{
		this.setPersistenceConnector(PepperModulesFactory.eINSTANCE.createPersistenceConnector());
	}
	/**
	 * {@inheritDoc PepperModule#isReadyToStart()}
	 */
	public boolean isReadyToStart() throws PepperModuleNotReadyException
	{
		return(true);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperModulesPackage.Literals.PEPPER_MODULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModuleController getPepperModuleController() {
		if (eContainerFeatureID() != PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER) return null;
		return (PepperModuleController)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPepperModuleController(PepperModuleController newPepperModuleController, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newPepperModuleController, PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperModuleController(PepperModuleController newPepperModuleController) {
		if (newPepperModuleController != eInternalContainer() || (eContainerFeatureID() != PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER && newPepperModuleController != null)) {
			if (EcoreUtil.isAncestor(this, newPepperModuleController))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newPepperModuleController != null)
				msgs = ((InternalEObject)newPepperModuleController).eInverseAdd(this, PepperModulesPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE, PepperModuleController.class, msgs);
			msgs = basicSetPepperModuleController(newPepperModuleController, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER, newPepperModuleController, newPepperModuleController));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_MODULE__SALT_PROJECT, oldSaltProject, saltProject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RETURNING_MODE getReturningMode() {
		return returningMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SCorpusGraph getSCorpusGraph() {
		return sCorpusGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSCorpusGraph(SCorpusGraph newSCorpusGraph) {
		SCorpusGraph oldSCorpusGraph = sCorpusGraph;
		sCorpusGraph = newSCorpusGraph;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_MODULE__SCORPUS_GRAPH, oldSCorpusGraph, sCorpusGraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI getResources() {
		return resources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setResources(URI newResources) 
	{
		if (newResources== null)
			throw new PepperModuleException("The given resource path for module '"+this.getName()+"' with uri is empty.");
		File file= new File(newResources.toFileString());
		if (!file.exists())
			throw new PepperModuleException("The given resource path for module '"+this.getName()+"' with uri '"+newResources+"' does not exists.");
		if (!file.isDirectory())
			throw new PepperModuleException("The given resource path for module '"+this.getName()+"' with uri '"+newResources+"' is not a directory.");
		URI oldResources = resources;
		resources = newResources;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_MODULE__RESOURCES, oldResources, resources));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI getTemproraries() {
		return temproraries;
	}

	/**
	 * {@inheritDoc PepperModule#setTemproraries(URI)}
	 */
	public void setTemproraries(URI newTemproraries) 
	{
		if (newTemproraries== null)
			throw new PepperModuleException("The given temprorary path for module '"+this.getName()+"' with uri is empty.");
		if (newTemproraries.toFileString()== null)
			throw new PepperModuleException("Cannot create an uri out of given temprorary path '"+newTemproraries+"' for module '"+this.getName()+"'.");
		File file= new File(newTemproraries.toFileString());
		if (!file.exists())
			throw new PepperModuleException("The given temprorary path for module '"+this.getName()+"' with uri '"+newTemproraries+"' does not exists.");
		if (!file.isDirectory())
			throw new PepperModuleException("The given temprorary path for module '"+this.getName()+"' with uri '"+newTemproraries+"' is not a directory.");
		
		this.removeDirRec(file);
		file.mkdir();
		URI oldTemproraries = temproraries;
		temproraries = newTemproraries;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_MODULE__TEMPRORARIES, oldTemproraries, temproraries));
	}
	
	private void removeDirRec(File dir)
	{
		if (dir != null)
		{
			if (dir.listFiles()!= null && dir.listFiles().length!= 0)
			{	
				for (File subDir: dir.listFiles())
				{
					this.removeDirRec(subDir);
				}
			}
			dir.delete();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSymbolicName() {
		return symbolicName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymbolicName(String newSymbolicName) {
		String oldSymbolicName = symbolicName;
		symbolicName = newSymbolicName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_MODULE__SYMBOLIC_NAME, oldSymbolicName, symbolicName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceConnector getPersistenceConnector() {
		return persistenceConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistenceConnector(PersistenceConnector newPersistenceConnector, NotificationChain msgs) {
		PersistenceConnector oldPersistenceConnector = persistenceConnector;
		persistenceConnector = newPersistenceConnector;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_MODULE__PERSISTENCE_CONNECTOR, oldPersistenceConnector, newPersistenceConnector);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPersistenceConnector(PersistenceConnector newPersistenceConnector) {
		if (newPersistenceConnector != persistenceConnector) {
			NotificationChain msgs = null;
			if (persistenceConnector != null)
				msgs = ((InternalEObject)persistenceConnector).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PepperModulesPackage.PEPPER_MODULE__PERSISTENCE_CONNECTOR, null, msgs);
			if (newPersistenceConnector != null)
				msgs = ((InternalEObject)newPersistenceConnector).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PepperModulesPackage.PEPPER_MODULE__PERSISTENCE_CONNECTOR, null, msgs);
			msgs = basicSetPersistenceConnector(newPersistenceConnector, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_MODULE__PERSISTENCE_CONNECTOR, newPersistenceConnector, newPersistenceConnector));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI getSpecialParams() {
		return specialParams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpecialParams(URI newSpecialParams) {
		URI oldSpecialParams = specialParams;
		specialParams = newSpecialParams;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_MODULE__SPECIAL_PARAMS, oldSpecialParams, specialParams));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_MODULE__VERSION, oldVersion, version));
	}

	/**
	 * Checks if everything is set, so the module can be started.
	 * Checking:
	 * <ul>
	 * 	<li>temproraries</li>
	 * 	<li>resources</li>
	 * </ul>
	 */
	protected void readyToStart()
	{
		if (this.getTemproraries()== null)
			throw new PepperModuleException("Cannot start module '"+this.getName()+"', because the temproraries aren't set.");
		if (this.getResources()== null)
			throw new PepperModuleException("Cannot start module '"+this.getName()+"', because the resource path aren't set.");
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void start() throws PepperModuleException 
	{
//		TODO uncomment this line
//		this.readyToStart();
		boolean isStart= true;
		SElementId sElementId= null;
		while ((isStart) || (sElementId!= null))
		{	
			isStart= false;
			sElementId= this.getPepperModuleController().get();
			if (sElementId== null)
				break;
			//call for using push-method
			try
			{
				this.start(sElementId);
			}
			catch (Exception e) {
				if (this.getLogService()!= null)
				{
					String moduleAction= null;
					if (this instanceof PepperImporter)
						moduleAction= "import";
					else if (this instanceof PepperExporter)
						moduleAction= "export";
					else moduleAction= "manipulate";
					this.getLogService().log(LogService.LOG_WARNING, "Cannot "+moduleAction+" the SDocument '"+sElementId.getSId()+"' with pepper module '"+this.getName()+"', because of a nested exception.", e);
				}
			}
			if (this.returningMode== RETURNING_MODE.PUT)
			{	
				this.getPepperModuleController().put(sElementId);
			}
			else if (this.returningMode== RETURNING_MODE.FINISH)
			{	
				this.getPepperModuleController().finish(sElementId);
			}
			else 
			{	
				throw new PepperModuleException("An error occurs in this module (name: "+this.getName()+"). The returningMode isn't correctly set (it's "+this.getReturningMode()+"). Please contact module supplier.");
			}
		}
		this.end();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void start(SElementId sElementId) throws PepperModuleException 
	{
		throw new PepperModuleException("An error occurs in this module (name: "+this.getName()+"). The method start(SElementId) isn't implemented. Please contact module supplier.");
	}

	/**
	 * Calls method {@link #start(SElementId)} for every root {@link SCorpus} of {@link SaltProject} object.
	 */
	public void end() throws PepperModuleException 
	{
		if (this.getSaltProject().getSCorpusGraphs()!= null)
		{//if salt model contain corpus graphs
			BasicEList<SCorpusGraph> corpGraphs= new BasicEList<SCorpusGraph>();
			//the list has to be copied, because of a possible MultibleModificationException in some PepperModules (e.g. DOTExporter)
			for (SCorpusGraph sCorpusGraph: this.getSaltProject().getSCorpusGraphs())
			{
				corpGraphs.add(sCorpusGraph);
			}
			
			for (SCorpusGraph sCorpusGraph: corpGraphs)
			{//for every corpus graph
				//FIXME why does these lines do not work???
				//--> hildebax 14.08.2012: getSRootCorpus() fixed, should work now <--
//				for (SCorpus sCorpus: sCorpusGraph.getSRootCorpus())
//				{//for every root corpus
//					this.start(sCorpus.getSElementId());
//				}//for every root corpus
				SCorpusStructureAccessor acc= new SCorpusStructureAccessor();
				acc.setSCorpusGraph(sCorpusGraph);
				if (acc.getSRootCorpora()!= null)
				{//if corpus graph contain corpora
					for (SCorpus sCorpus: acc.getSRootCorpora())
					{//for every root corpus
						this.start(sCorpus.getSElementId());
					}//for every root corpus
				}//if corpus graph contain corpora
			}//for every corpus graph
		}//if salt model contain corpus graphs	
	}

// ====================================== start: getting logger ======================================
	private LogService logService;

	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	public void unsetLogService(LogService logService) {
		logService= null;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public LogService getLogService() 
	{
		return(this.logService);
	}

	/**
	 * {@inheritDoc PepperModule#getProgress(SElementId)}
	 */
	public Double getProgress(SElementId sDocumentId) 
	{
		return(null);
	}

	// ====================================== end: getting logger ======================================

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetPepperModuleController((PepperModuleController)otherEnd, msgs);
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
			case PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER:
				return basicSetPepperModuleController(null, msgs);
			case PepperModulesPackage.PEPPER_MODULE__PERSISTENCE_CONNECTOR:
				return basicSetPersistenceConnector(null, msgs);
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
			case PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER:
				return eInternalContainer().eInverseRemove(this, PepperModulesPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE, PepperModuleController.class, msgs);
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
			case PepperModulesPackage.PEPPER_MODULE__NAME:
				return getName();
			case PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER:
				return getPepperModuleController();
			case PepperModulesPackage.PEPPER_MODULE__SALT_PROJECT:
				return getSaltProject();
			case PepperModulesPackage.PEPPER_MODULE__RETURNING_MODE:
				return getReturningMode();
			case PepperModulesPackage.PEPPER_MODULE__SCORPUS_GRAPH:
				return getSCorpusGraph();
			case PepperModulesPackage.PEPPER_MODULE__RESOURCES:
				return getResources();
			case PepperModulesPackage.PEPPER_MODULE__TEMPRORARIES:
				return getTemproraries();
			case PepperModulesPackage.PEPPER_MODULE__SYMBOLIC_NAME:
				return getSymbolicName();
			case PepperModulesPackage.PEPPER_MODULE__PERSISTENCE_CONNECTOR:
				return getPersistenceConnector();
			case PepperModulesPackage.PEPPER_MODULE__SPECIAL_PARAMS:
				return getSpecialParams();
			case PepperModulesPackage.PEPPER_MODULE__VERSION:
				return getVersion();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER:
				setPepperModuleController((PepperModuleController)newValue);
				return;
			case PepperModulesPackage.PEPPER_MODULE__SALT_PROJECT:
				setSaltProject((SaltProject)newValue);
				return;
			case PepperModulesPackage.PEPPER_MODULE__SCORPUS_GRAPH:
				setSCorpusGraph((SCorpusGraph)newValue);
				return;
			case PepperModulesPackage.PEPPER_MODULE__RESOURCES:
				setResources((URI)newValue);
				return;
			case PepperModulesPackage.PEPPER_MODULE__TEMPRORARIES:
				setTemproraries((URI)newValue);
				return;
			case PepperModulesPackage.PEPPER_MODULE__SYMBOLIC_NAME:
				setSymbolicName((String)newValue);
				return;
			case PepperModulesPackage.PEPPER_MODULE__PERSISTENCE_CONNECTOR:
				setPersistenceConnector((PersistenceConnector)newValue);
				return;
			case PepperModulesPackage.PEPPER_MODULE__SPECIAL_PARAMS:
				setSpecialParams((URI)newValue);
				return;
			case PepperModulesPackage.PEPPER_MODULE__VERSION:
				setVersion((String)newValue);
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
			case PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER:
				setPepperModuleController((PepperModuleController)null);
				return;
			case PepperModulesPackage.PEPPER_MODULE__SALT_PROJECT:
				setSaltProject(SALT_PROJECT_EDEFAULT);
				return;
			case PepperModulesPackage.PEPPER_MODULE__SCORPUS_GRAPH:
				setSCorpusGraph(SCORPUS_GRAPH_EDEFAULT);
				return;
			case PepperModulesPackage.PEPPER_MODULE__RESOURCES:
				setResources(RESOURCES_EDEFAULT);
				return;
			case PepperModulesPackage.PEPPER_MODULE__TEMPRORARIES:
				setTemproraries(TEMPRORARIES_EDEFAULT);
				return;
			case PepperModulesPackage.PEPPER_MODULE__SYMBOLIC_NAME:
				setSymbolicName(SYMBOLIC_NAME_EDEFAULT);
				return;
			case PepperModulesPackage.PEPPER_MODULE__PERSISTENCE_CONNECTOR:
				setPersistenceConnector((PersistenceConnector)null);
				return;
			case PepperModulesPackage.PEPPER_MODULE__SPECIAL_PARAMS:
				setSpecialParams(SPECIAL_PARAMS_EDEFAULT);
				return;
			case PepperModulesPackage.PEPPER_MODULE__VERSION:
				setVersion(VERSION_EDEFAULT);
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
			case PepperModulesPackage.PEPPER_MODULE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PepperModulesPackage.PEPPER_MODULE__PEPPER_MODULE_CONTROLLER:
				return getPepperModuleController() != null;
			case PepperModulesPackage.PEPPER_MODULE__SALT_PROJECT:
				return SALT_PROJECT_EDEFAULT == null ? saltProject != null : !SALT_PROJECT_EDEFAULT.equals(saltProject);
			case PepperModulesPackage.PEPPER_MODULE__RETURNING_MODE:
				return returningMode != RETURNING_MODE_EDEFAULT;
			case PepperModulesPackage.PEPPER_MODULE__SCORPUS_GRAPH:
				return SCORPUS_GRAPH_EDEFAULT == null ? sCorpusGraph != null : !SCORPUS_GRAPH_EDEFAULT.equals(sCorpusGraph);
			case PepperModulesPackage.PEPPER_MODULE__RESOURCES:
				return RESOURCES_EDEFAULT == null ? resources != null : !RESOURCES_EDEFAULT.equals(resources);
			case PepperModulesPackage.PEPPER_MODULE__TEMPRORARIES:
				return TEMPRORARIES_EDEFAULT == null ? temproraries != null : !TEMPRORARIES_EDEFAULT.equals(temproraries);
			case PepperModulesPackage.PEPPER_MODULE__SYMBOLIC_NAME:
				return SYMBOLIC_NAME_EDEFAULT == null ? symbolicName != null : !SYMBOLIC_NAME_EDEFAULT.equals(symbolicName);
			case PepperModulesPackage.PEPPER_MODULE__PERSISTENCE_CONNECTOR:
				return persistenceConnector != null;
			case PepperModulesPackage.PEPPER_MODULE__SPECIAL_PARAMS:
				return SPECIAL_PARAMS_EDEFAULT == null ? specialParams != null : !SPECIAL_PARAMS_EDEFAULT.equals(specialParams);
			case PepperModulesPackage.PEPPER_MODULE__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", saltProject: ");
		result.append(saltProject);
		result.append(", returningMode: ");
		result.append(returningMode);
		result.append(", sCorpusGraph: ");
		result.append(sCorpusGraph);
		result.append(", resources: ");
		result.append(resources);
		result.append(", temproraries: ");
		result.append(temproraries);
		result.append(", symbolicName: ");
		result.append(symbolicName);
		result.append(", specialParams: ");
		result.append(specialParams);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //PepperModuleImpl

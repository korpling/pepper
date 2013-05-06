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
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Module Resolver</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleResolverImpl#getPepperImporterComponentFactories <em>Pepper Importer Component Factories</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleResolverImpl#getPepperManipulatorComponentFactories <em>Pepper Manipulator Component Factories</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleResolverImpl#getPepperExporterComponentFactories <em>Pepper Exporter Component Factories</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleResolverImpl#getTemprorariesPropertyName <em>Temproraries Property Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleResolverImpl#getResourcesPropertyName <em>Resources Property Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
@Component(name="PepperModuleResolverComponent", configurationPid="PepperModuleResolverComponent", immediate=true, enabled=true, servicefactory=false)
public class PepperModuleResolverImpl extends EObjectImpl implements PepperModuleResolver {
	
	/**
	 * The default value of the '{@link #getTemprorariesPropertyName() <em>Temproraries Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemprorariesPropertyName()
	 * @generated
	 * @ordered
	 */
	protected static final String TEMPRORARIES_PROPERTY_NAME_EDEFAULT = "PepperModuleResolver.TemprorariesURI";

	/**
	 * The cached value of the '{@link #getTemprorariesPropertyName() <em>Temproraries Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemprorariesPropertyName()
	 * @generated
	 * @ordered
	 */
	protected String temprorariesPropertyName = TEMPRORARIES_PROPERTY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getResourcesPropertyName() <em>Resources Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcesPropertyName()
	 * @generated
	 * @ordered
	 */
	protected static final String RESOURCES_PROPERTY_NAME_EDEFAULT = "PepperModuleResolver.ResourcesURI";

	/**
	 * The cached value of the '{@link #getResourcesPropertyName() <em>Resources Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcesPropertyName()
	 * @generated
	 * @ordered
	 */
	protected String resourcesPropertyName = RESOURCES_PROPERTY_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public PepperModuleResolverImpl() {
		super();
		init();
	}

	/**
	 * Initializes this object. 
	 */
	private void init()
	{
		//creating table for storing number of module instances
		this.numberOfModuleInstances= new Hashtable<String, Integer>();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperFWPackage.Literals.PEPPER_MODULE_RESOLVER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ComponentFactory> getPepperImporterComponentFactories() {
		if (pepperImporterComponentFactories == null) {
			pepperImporterComponentFactories = new EDataTypeUniqueEList<ComponentFactory>(ComponentFactory.class, this, PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_IMPORTER_COMPONENT_FACTORIES);
		}
		return pepperImporterComponentFactories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ComponentFactory> getPepperManipulatorComponentFactories() {
		if (pepperManipulatorComponentFactories == null) {
			pepperManipulatorComponentFactories = new EDataTypeUniqueEList<ComponentFactory>(ComponentFactory.class, this, PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_MANIPULATOR_COMPONENT_FACTORIES);
		}
		return pepperManipulatorComponentFactories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ComponentFactory> getPepperExporterComponentFactories() {
		if (pepperExporterComponentFactories == null) {
			pepperExporterComponentFactories = new EDataTypeUniqueEList<ComponentFactory>(ComponentFactory.class, this, PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_EXPORTER_COMPONENT_FACTORIES);
		}
		return pepperExporterComponentFactories;
	}
	
	@Activate
	public void activate(ComponentContext componentContext)
	{
	}
	
// ====================================== start: getting logger ======================================
	private LogService logService;

	@Reference(unbind="unsetLogService", cardinality=ReferenceCardinality.OPTIONAL, policy=ReferencePolicy.STATIC)
	public void setLogService(LogService logService) 
	{
		this.logService = logService;
	}
	
	public LogService getLogService()
	{
		return(this.logService);
	}
	
	public void unsetLogService(LogService logService) {
		this.logService= null;
	}
// ====================================== end: getting logger ======================================
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTemprorariesPropertyName() {
		return temprorariesPropertyName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemprorariesPropertyName(String newTemprorariesPropertyName) {
		String oldTemprorariesPropertyName = temprorariesPropertyName;
		temprorariesPropertyName = newTemprorariesPropertyName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_MODULE_RESOLVER__TEMPRORARIES_PROPERTY_NAME, oldTemprorariesPropertyName, temprorariesPropertyName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResourcesPropertyName() {
		return resourcesPropertyName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourcesPropertyName(String newResourcesPropertyName) {
		String oldResourcesPropertyName = resourcesPropertyName;
		resourcesPropertyName = newResourcesPropertyName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_MODULE_RESOLVER__RESOURCES_PROPERTY_NAME, oldResourcesPropertyName, resourcesPropertyName));
	}

	
	/**
	 * Returns a status description as {@link String}. The returned {@link String} contains the number of all available
	 * {@link PepperModule} objects in a readable format.
	 * @return {@link String} representation of all {@link PepperModule} objects available by this {@link PepperModuleResolver} object.
	 */
	public String getStatus()
	{
		String infoString= "";
			
		{//print out all importers
			EList<PepperImporter> importers= this.getPepperImporters();
			Integer numOfFactories= 0;
			if (this.getPepperImporterComponentFactories()!= null) numOfFactories= this.getPepperImporterComponentFactories().size();
			Integer numOfImporters= 0;
			if (importers!= null) numOfImporters= importers.size();
			infoString= infoString + "=========================================================== \n";
			infoString= infoString + "registered importer-factories and importers ("+numOfFactories+"/ "+numOfImporters+"): \n";
			if (importers!= null)
			{
				for (PepperImporter importer: importers)
				{	
					infoString= infoString  + "\t"+importer.getName()+"\n";
					if (importer.getSupportedFormats()!= null)
					{	
						for (FormatDefinition formatDef: importer.getSupportedFormats())
							infoString= infoString  + "\t\t"+formatDef.getFormatName()+ ", "+formatDef.getFormatVersion()+ "\n";
					}
				}
			}
			else 
				infoString= infoString  + "\tno importers registered...\n";
		}
		{//print out all manipulators
			EList<PepperManipulator> manipulators= this.getPepperManipulators();
			Integer numOfFactories= 0;
			if (this.getPepperManipulatorComponentFactories()!= null) numOfFactories= this.getPepperManipulatorComponentFactories().size();
			Integer numOfManipulators= 0;
			if (manipulators!= null) numOfManipulators= manipulators.size();
			infoString= infoString + "=========================================================== \n";
			infoString= infoString + "registered manipulator-factories and manipulators ("+numOfFactories+"/ "+numOfManipulators+"): \n";
			if (manipulators!= null)
			{
				for (PepperManipulator manipulator: manipulators)
				{	
					infoString= infoString  + "\t"+manipulator.getName()+"\n";
				}
			}
			else 
				infoString= infoString  + "\tno manipulators registered...\n";
		}	
		{//print out all exporters
			EList<PepperExporter> exporters= this.getPepperExporters();
			Integer numOfFactories= 0;
			if (this.getPepperExporterComponentFactories()!= null) numOfFactories= this.getPepperExporterComponentFactories().size();
			Integer numOfExporters= 0;
			if (exporters!= null) numOfExporters= exporters.size();
			
			infoString= infoString + "=========================================================== \n";
			infoString= infoString + "registered exporter-factories and exporters ("+numOfFactories+"/ "+numOfExporters+"): \n";
			if (exporters!= null)
			{
				for (PepperExporter exporter: exporters)
				{	
					infoString= infoString  + "\t"+exporter.getName()+"\n";
					if (exporter.getSupportedFormats()!= null)
					{	
						for (FormatDefinition formatDef: exporter.getSupportedFormats())
							infoString= infoString  + "\t\t"+formatDef.getFormatName()+ ", "+formatDef.getFormatVersion()+ "\n";
					}
				}
			}
			else 
				infoString= infoString  + "\tno exporters registered...\n";
			infoString= infoString + "=========================================================== \n";
		}
		return(infoString);
	}

// ============================================ start: resolve PepperImporters	
	/**
	 * This unnecessary variable must be initialized, because of restrictions of the maven osgi scr plugin. Here
	 * it is not possible to use a list as osgi-reference. (this is a workaround)
	 */
	protected ComponentFactory pepperImporterComponentFactory= null;
	
	/**
	 * The cached value of the '{@link #getPepperImporterComponentFactories() <em>Pepper Importer Component Factories</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperImporterComponentFactories()
	 * @ordered
	 */
	protected EList<ComponentFactory> pepperImporterComponentFactories;
	
	/**
	 * This method is called by OSGi framework and adds all registered {@link ComponentFactory} objects having the
	 * name PepperImporterComponentFactory to this object. All {@link ComponentFactory} objects are stored in an internal object 
	 * {@link #pepperImporterComponentFactories}
	 */
	@Reference(unbind="removePepperImporterComponentFactory", cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.STATIC ,target="(component.factory=PepperImporterComponentFactory)")
	public void addPepperImporterComponentFactory(ComponentFactory pepperImporterComponentFactory) 
	{
		if (pepperImporterComponentFactory== null)
			throw new PepperException("Cannot add an empty pepperImporterComponentFactory.");
		if (this.pepperImporterComponentFactories== null)
			this.pepperImporterComponentFactories= new BasicEList<ComponentFactory>();
		this.getPepperImporterComponentFactories().add(pepperImporterComponentFactory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void removePepperImporterComponentFactory(ComponentFactory pepperImporterComponentFactory) 
	{
		if (this.pepperImporterComponentFactories== null)
			this.getPepperImporterComponentFactories().remove(pepperImporterComponentFactory);
	}
// ============================================ end: resolve PepperImporters
// ============================================ start: resolve PepperManipulator	
	/**
	 * This unnecessary variable must be initialized, because of restrictions of the maven osgi scr plugin. Here
	 * it is not possible to use a list as osgi-reference. (this is a workaround)
	 */
	protected ComponentFactory pepperManipulatorComponentFactory;
	/**
	 * The cached value of the '{@link #getPepperManipulatorComponentFactories() <em>Pepper Manipulator Component Factories</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperManipulatorComponentFactories()
	 * @ordered
	 */
	protected EList<ComponentFactory> pepperManipulatorComponentFactories;

	/**
	 * This method is called by OSGi framework and adds all registered {@link ComponentFactory} objects having the
	 * name PepperManipulatorComponentFactory to this object. All {@link ComponentFactory} objects are stored in an internal object 
	 * {@link #pepperManipulatorComponentFactories}.
	 */
	@Reference(unbind="removePepperManipulatorComponentFactory", cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.STATIC, target="(component.factory=PepperManipulatorComponentFactory)")
	public void addPepperManipulatorComponentFactory(ComponentFactory pepperManipulatorComponentFactory) 
	{
		if (pepperManipulatorComponentFactory== null)
			throw new PepperException("Cannot add an empty pepperManipulatorComponentFactory.");
		if (this.pepperManipulatorComponentFactories== null)
			this.pepperManipulatorComponentFactories= new BasicEList<ComponentFactory>();
		this.getPepperManipulatorComponentFactories().add(pepperManipulatorComponentFactory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void removePepperManipulatorComponentFactory(ComponentFactory pepperManipulatorComponentFactory) 
	{
		if (this.pepperManipulatorComponentFactories== null)
			this.getPepperManipulatorComponentFactories().remove(pepperManipulatorComponentFactory);
	}
// ============================================ end: resolve PepperManipulators
// ============================================ start: resolve PepperExporters

	/**
	 * This unnecessary variable must be initialized, because of restrictions of the maven osgi scr plugin. Here
	 * it is not possible to use a list as osgi-reference. (this is a workaround)
	 */
	protected ComponentFactory pepperExporterComponentFactory;
	
	/**
	 * The cached value of the '{@link #getPepperExporterComponentFactories() <em>Pepper Exporter Component Factories</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperExporterComponentFactories()
	 * @ordered
	 */
	protected EList<ComponentFactory> pepperExporterComponentFactories;
	/**
	 * {@inheritDoc PepperModuleResolver#addPepperExporterComponentFactory(ComponentFactory)}
	 */
	@Reference(unbind="removePepperExporterComponentFactory", cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.STATIC, target="(component.factory=PepperExporterComponentFactory)")
	public void addPepperExporterComponentFactory(ComponentFactory pepperExporterComponentFactory) 
	{
		if (pepperExporterComponentFactory== null)
			throw new PepperException("Cannot add an empty pepperExporterComponentFactory.");
		if (this.pepperExporterComponentFactories== null)
			this.pepperExporterComponentFactories= new BasicEList<ComponentFactory>();
		this.getPepperExporterComponentFactories().add(pepperExporterComponentFactory);
	}

	/**
	 * {@inheritDoc PepperModuleResolver#removePepperExporterComponentFactory(ComponentFactory)}
	 */
	public void removePepperExporterComponentFactory(ComponentFactory pepperExporterComponentFactory) 
	{
		if (this.pepperExporterComponentFactories== null)
			this.getPepperExporterComponentFactories().remove(pepperExporterComponentFactory);
	}
// ============================================ end: resolve PepperExporters
	
	public static final String RESOURCES=".resources";
	
	/**
	 * Sets resources to a given PepperModule-object.
	 * @param module - the object for setting resources
	 */
	protected void setResources(PepperModule module)
	{
		String resURIStr= System.getProperty(this.resourcesPropertyName);		
		
		if (	(resURIStr== null) ||
				(resURIStr.isEmpty()))
			throw new PepperFWException("Cannot start converting, because the system property '"+this.resourcesPropertyName+"' isn't set. This might be an internal failure.");
		
		if (	(module.getSymbolicName()== null) ||
				(module.getSymbolicName().isEmpty()))
			throw new PepperModuleException("Cannot set resources to module '"+module.getName()+"', because its symbolic name is empty.");
		
		String resourceDirectoryName= System.getProperty(module.getSymbolicName()+RESOURCES);
		File resFile= null;
		URI resURI= null;
		if (resourceDirectoryName!= null)
		{
			resURI= URI.createFileURI(resourceDirectoryName);
			resFile= new File(resURI.toFileString());
		}
		else 
		{
			resURI= URI.createFileURI(resURIStr+"/"+module.getSymbolicName());
			resFile= new File(resURI.toFileString());
		}
		if (!resFile.exists())
		{
			resFile.mkdirs();
			if (this.getLogService()!= null)
				this.getLogService().log(LogService.LOG_WARNING, "Resource folder '"+resFile.getAbsolutePath()+"' for pepper module '"+module.getSymbolicName()+"' does not exist.");
		}
		module.setResources(resURI);
	}
	
	/**
	 * Sets Temproraries to a given PepperModule-object.
	 * @param module - the object for setting temproraries
	 */
	protected void setTemproraries(PepperModule module)
	{
		String tempURIStr= System.getProperty(this.temprorariesPropertyName);
		if (	(tempURIStr== null) ||
				(tempURIStr.isEmpty()))
			throw new PepperFWException("Cannot start converting, because the system property '"+this.temprorariesPropertyName+"' isn't set. This might be an internal failure.");
		
		if (	(module.getSymbolicName()== null) ||
				(module.getSymbolicName().isEmpty()))
			throw new PepperModuleException("Cannot set temproraries to module '"+module.getName()+"', because its symbolic name is empty.");
		URI tempURI= URI.createFileURI(tempURIStr+"/"+module.getSymbolicName()+"/"+this.numberOfModuleInstances.get(module.getSymbolicName()));
		File tempFile= new File(tempURI.toFileString());
		try {
			tempFile.mkdirs();
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw new PepperException("Cannot create temprorary folder for module: "+module.getName());
		}
		module.setTemproraries(tempURI);
	}
	
	/**
	 * Sets Logger to a given PepperModule-object.
	 * @param module - the object for setting temproraries
	 */
	protected void setLogger(PepperModule module)
	{
		//if module not null and doesn't has a LogService
		if (	(module!= null)&&
				(module.getLogService()== null))
		{
			module.setLogService(this.getLogService());
		}
			
	}
	
	/**
	 * Stores to every created kind of module the number of existing instances.
	 */
	private Map<String, Integer> numberOfModuleInstances= null;
	
	/**
	 * increases the number of the instances of the given module.
	 */
	private void increaseNumberOfModules(String moduleSymbolicName)
	{
		if (	(moduleSymbolicName== null) ||
				(moduleSymbolicName.isEmpty()))
			throw new PepperModuleException("Cannot increase number of modules, because the symbolic name of module is empty.");
		if (this.numberOfModuleInstances.containsKey(moduleSymbolicName))
		{
			Integer number= this.numberOfModuleInstances.get(moduleSymbolicName);
			number++;
			this.numberOfModuleInstances.put(moduleSymbolicName, number);
		}	
		else this.numberOfModuleInstances.put(moduleSymbolicName, 1);
	}
	
	/**
	 * {@inheritDoc PepperModuleResolver#getPepperImporters()}
	 */
	public EList<PepperImporter> getPepperImporters() 
	{
		EList<PepperImporter> pepperImporters= null;
		if (this.pepperImporterComponentFactories!= null)
		{	
			//run through all pepperImporterComponentFactories and search for mapping PepperImporter
			for (ComponentFactory componentFactory: this.getPepperImporterComponentFactories())
			{
				Object instance= componentFactory.newInstance(null).getInstance();
				if (instance instanceof PepperImporter)
				{
					if (pepperImporters== null)
						pepperImporters= new BasicEList<PepperImporter>();
					PepperImporter importer= (PepperImporter) instance;
					if (	(importer.getSymbolicName()== null) ||
							(importer.getSymbolicName().isEmpty()))
						throw new PepperModuleException("Cannot register PepperModule, because the symbolic name of module '"+importer.getName()+"' is empty.");
					this.increaseNumberOfModules(importer.getSymbolicName());
					this.setTemproraries(importer);
					this.setResources(importer);
					this.setLogger(importer);
					pepperImporters.add(importer);
				}
			}
		}		
		return(pepperImporters);
	}

	/**
	 * {@inheritDoc PepperModuleResolver#getPepperManipulators()}
	 */
	public EList<PepperManipulator> getPepperManipulators() 
	{
		EList<PepperManipulator> pepperManipulators= null;
		if (this.pepperManipulatorComponentFactories!= null)
		{	
			//run through all pepperManipulatorComponentFactories and search for mapping PepperManipulator
			for (ComponentFactory componentFactory: this.getPepperManipulatorComponentFactories())
			{
				Object instance= componentFactory.newInstance(null).getInstance();
				if (instance instanceof PepperManipulator)
				{
					if (pepperManipulators== null)
						pepperManipulators= new BasicEList<PepperManipulator>();
					PepperManipulator manipulator= (PepperManipulator) instance;
					this.increaseNumberOfModules(manipulator.getSymbolicName());
					this.setTemproraries(manipulator);
					this.setResources(manipulator);
					this.setLogger(manipulator);
					pepperManipulators.add(manipulator);
				}
			}
		}
		return(pepperManipulators);
	}

	/**
	 * {@inheritDoc PepperModuleResolver#getPepperExporters()}
	 */
	public EList<PepperExporter> getPepperExporters() 
	{
		EList<PepperExporter> pepperExporters= null;
		if (this.pepperExporterComponentFactories!= null)
		{	
			//run through all pepperExporterComponentFactories and search for mapping PepperExporter
			for (ComponentFactory componentFactory: this.getPepperExporterComponentFactories())
			{
				Object instance= componentFactory.newInstance(null).getInstance();
				if (instance instanceof PepperExporter)
				{
					if (pepperExporters== null)
						pepperExporters= new BasicEList<PepperExporter>();
					PepperExporter exporter= (PepperExporter) instance;
					this.increaseNumberOfModules(exporter.getSymbolicName());
					this.setTemproraries(exporter);
					this.setResources(exporter);
					this.setLogger(exporter);
					pepperExporters.add(exporter);
				}
			}
		}
		return(pepperExporters);
	}

	/**
	 * Returns a {@link PepperImporter} object matching to the given {@link ImporterParams}. A new instance of
	 * the specific {@link PepperImporter} class is created and returned. No references to the returned object will
	 * be stored in this {@link PepperModuleResolver} object. When calling {@link #getPepperImporter(ImporterParams)}
	 * a new instance of {@link PepperImporter} is created.
	 * @param pepperImporterParams specifies the {@link PepperImporter} object to be found
	 * @return a new instance of {@link PepperImporter} matching the given {@link ImporterParams}
	 */
	public PepperImporter getPepperImporter(ImporterParams pepperImporterParams) 
	{
		PepperImporter pepperImporter= null;
		
		if (this.pepperImporterComponentFactories!= null)
		{	
			if (	(this.getPepperImporters()== null)||
					(this.getPepperImporters().size()== 0))
				throw new PepperConvertException("Cannot convert data, because no importer is registered.");
			//run through all pepperImporterComponentFactories and search for mapping PepperImporter
			for (PepperImporter importer: this.getPepperImporters())
			//emit by name
			if(pepperImporterParams.getModuleName()!= null)
			{
				if (pepperImporterParams.getModuleName().equalsIgnoreCase(importer.getName()))
				{	
					pepperImporter= importer;
					break;
				}
			}
			//emit by format Definition
			else
			{
				if (	(pepperImporterParams.getFormatName()!= null) &&
						(pepperImporterParams.getFormatVersion()!= null))
				{
					for (FormatDefinition format: importer.getSupportedFormats())
					{
						if (	(pepperImporterParams.getFormatName().equalsIgnoreCase(format.getFormatName())) &&
								(pepperImporterParams.getFormatVersion().equalsIgnoreCase(format.getFormatVersion())))
						{	
							pepperImporter= importer;
							break;
						}
					}	
				}	
			}
		}
		return(pepperImporter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public PepperManipulator getPepperManipulator(ModuleParams pepperModuleParams) 
	{
		PepperManipulator pepperManipulator= null;
		
		if (this.pepperManipulatorComponentFactories!= null)
		{	
			EList<PepperManipulator> manipulators=this.getPepperManipulators();
			if (	(manipulators!= null) &&
					(manipulators.size()> 0))
			{
				//run through all pepperManipulatorComponentFactories and search for mapping PepperManipulator
				for (PepperManipulator manipulator: manipulators)
				{
					//emit by name
					if(pepperModuleParams.getModuleName()!= null)
					{
						if (pepperModuleParams.getModuleName().equalsIgnoreCase(manipulator.getName()))
						{	
							pepperManipulator= manipulator;
							break;
						}
					}	
				}
			}
		}
		return(pepperManipulator);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public PepperExporter getPepperExporter(ExporterParams pepperExporterParams) 
	{
		PepperExporter pepperExporter= null;
		
		if (this.pepperExporterComponentFactories!= null)
		{	
			//run through all pepperExporterComponentFactories and search for mapping PepperExporter
			for (PepperExporter exporter: this.getPepperExporters())
			{
				//emit by name
				if(pepperExporterParams.getModuleName()!= null)
				{
					if (pepperExporterParams.getModuleName().equalsIgnoreCase(exporter.getName()))
					{	
						pepperExporter= exporter;
						break;
					}
				}
				//emit by format Definition
				else
				{
					if (	(pepperExporterParams.getFormatName()!= null) &&
							(pepperExporterParams.getFormatVersion()!= null))
					{
						for (FormatDefinition format: exporter.getSupportedFormats())
						{
							if (	(pepperExporterParams.getFormatName().equalsIgnoreCase(format.getFormatName())) &&
									(pepperExporterParams.getFormatVersion().equalsIgnoreCase(format.getFormatVersion())))
							{	
								pepperExporter= exporter;
								break;
							}
						}	
					}	
				}		
			}	
		}
		return(pepperExporter);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_IMPORTER_COMPONENT_FACTORIES:
				return getPepperImporterComponentFactories();
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_MANIPULATOR_COMPONENT_FACTORIES:
				return getPepperManipulatorComponentFactories();
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_EXPORTER_COMPONENT_FACTORIES:
				return getPepperExporterComponentFactories();
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__TEMPRORARIES_PROPERTY_NAME:
				return getTemprorariesPropertyName();
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__RESOURCES_PROPERTY_NAME:
				return getResourcesPropertyName();
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
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_IMPORTER_COMPONENT_FACTORIES:
				getPepperImporterComponentFactories().clear();
				getPepperImporterComponentFactories().addAll((Collection<? extends ComponentFactory>)newValue);
				return;
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_MANIPULATOR_COMPONENT_FACTORIES:
				getPepperManipulatorComponentFactories().clear();
				getPepperManipulatorComponentFactories().addAll((Collection<? extends ComponentFactory>)newValue);
				return;
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_EXPORTER_COMPONENT_FACTORIES:
				getPepperExporterComponentFactories().clear();
				getPepperExporterComponentFactories().addAll((Collection<? extends ComponentFactory>)newValue);
				return;
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__TEMPRORARIES_PROPERTY_NAME:
				setTemprorariesPropertyName((String)newValue);
				return;
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__RESOURCES_PROPERTY_NAME:
				setResourcesPropertyName((String)newValue);
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
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_IMPORTER_COMPONENT_FACTORIES:
				getPepperImporterComponentFactories().clear();
				return;
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_MANIPULATOR_COMPONENT_FACTORIES:
				getPepperManipulatorComponentFactories().clear();
				return;
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_EXPORTER_COMPONENT_FACTORIES:
				getPepperExporterComponentFactories().clear();
				return;
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__TEMPRORARIES_PROPERTY_NAME:
				setTemprorariesPropertyName(TEMPRORARIES_PROPERTY_NAME_EDEFAULT);
				return;
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__RESOURCES_PROPERTY_NAME:
				setResourcesPropertyName(RESOURCES_PROPERTY_NAME_EDEFAULT);
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
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_IMPORTER_COMPONENT_FACTORIES:
				return pepperImporterComponentFactories != null && !pepperImporterComponentFactories.isEmpty();
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_MANIPULATOR_COMPONENT_FACTORIES:
				return pepperManipulatorComponentFactories != null && !pepperManipulatorComponentFactories.isEmpty();
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__PEPPER_EXPORTER_COMPONENT_FACTORIES:
				return pepperExporterComponentFactories != null && !pepperExporterComponentFactories.isEmpty();
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__TEMPRORARIES_PROPERTY_NAME:
				return TEMPRORARIES_PROPERTY_NAME_EDEFAULT == null ? temprorariesPropertyName != null : !TEMPRORARIES_PROPERTY_NAME_EDEFAULT.equals(temprorariesPropertyName);
			case PepperFWPackage.PEPPER_MODULE_RESOLVER__RESOURCES_PROPERTY_NAME:
				return RESOURCES_PROPERTY_NAME_EDEFAULT == null ? resourcesPropertyName != null : !RESOURCES_PROPERTY_NAME_EDEFAULT.equals(resourcesPropertyName);
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
		result.append(" (pepperImporterComponentFactories: ");
		result.append(pepperImporterComponentFactories);
		result.append(", pepperManipulatorComponentFactories: ");
		result.append(pepperManipulatorComponentFactories);
		result.append(", pepperExporterComponentFactories: ");
		result.append(pepperExporterComponentFactories);
		result.append(", temprorariesPropertyName: ");
		result.append(temprorariesPropertyName);
		result.append(", resourcesPropertyName: ");
		result.append(resourcesPropertyName);
		result.append(')');
		return result.toString();
	}

} //PepperModuleResolverImpl

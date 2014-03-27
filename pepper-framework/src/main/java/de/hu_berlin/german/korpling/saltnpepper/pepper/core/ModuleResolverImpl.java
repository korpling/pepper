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
package de.hu_berlin.german.korpling.saltnpepper.pepper.core;

import java.io.File;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.StepDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;

/**
 * The {@link ModuleResolverImpl} realizes a bridge between the Pepper framework and the OSGi environment. Through OSGi declarative services
 * all {@link PepperModule} no matter if {@link PepperImporter}, {@link PepperExporter} or {@link PepperManipulator}, all of them are registered 
 * in the {@link ModuleResolverImpl}. The main task of this class is to get a description of a {@link PepperModule} and to resolve a real instance of
 * that {@link PepperModule}.
 * 
 * @author Florian Zipser
 */
@Component(name="ModuleResolverComponent", configurationPid="ModuleResolverComponent", immediate=true, enabled=true, servicefactory=false)
public class ModuleResolverImpl implements ModuleResolver{
	private static final Logger logger = LoggerFactory.getLogger(ModuleResolverImpl.class);
	
	/**
	 * TODO make docu
	 */
	public ModuleResolverImpl() {
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
	
	/** The {@link ComponentContext} of the OSGi environment the bundle was started in.**/
	private ComponentContext componentContext=null;
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getComponentContext()
	 */
	@Override
	public ComponentContext getComponentContext()
	{
		return(this.componentContext);
	}
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#activate(org.osgi.service.component.ComponentContext)
	 */
	@Override
	@Activate
	public void activate(ComponentContext componentContext)
	{
		this.componentContext= componentContext;
	}
	
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getStatus()
	 */
	@Override
	public String getStatus()
	{
		String infoString= "";
			
		{//print out all importers
			List<PepperImporter> importers= getPepperImporters();
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
						for (FormatDesc formatDef: importer.getSupportedFormats())
							infoString= infoString  + "\t\t"+formatDef.getFormatName()+ ", "+formatDef.getFormatVersion()+ "\n";
					}
				}
			}
			else 
				infoString= infoString  + "\tno importers registered...\n";
		}
		{//print out all manipulators
			List<PepperManipulator> manipulators= this.getPepperManipulators();
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
			List<PepperExporter> exporters= this.getPepperExporters();
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
						for (FormatDesc formatDef: exporter.getSupportedFormats())
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
	 * TODO make docu
	 */
	protected List<ComponentFactory> pepperImporterComponentFactories;
	
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#addPepperImporterComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	@Reference(unbind="removePepperImporterComponentFactory", cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.STATIC ,target="(component.factory=PepperImporterComponentFactory)")
	@Override
	public void addPepperImporterComponentFactory(ComponentFactory pepperImporterComponentFactory) 
	{
		if (pepperImporterComponentFactory== null)
			throw new PepperException("Cannot add an empty pepperImporterComponentFactory.");
		if (this.pepperImporterComponentFactories== null)
			this.pepperImporterComponentFactories= new Vector<ComponentFactory>();
		this.getPepperImporterComponentFactories().add(pepperImporterComponentFactory);
	}

	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#removePepperImporterComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	@Override
	public void removePepperImporterComponentFactory(ComponentFactory pepperImporterComponentFactory) 
	{
		if (this.pepperImporterComponentFactories== null)
			this.getPepperImporterComponentFactories().remove(pepperImporterComponentFactory);
	}
	
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getPepperImporterComponentFactories()
	 */
	@Override
	public List<ComponentFactory> getPepperImporterComponentFactories() {
		if (pepperImporterComponentFactories == null) {
			synchronized (this) {
				if (pepperImporterComponentFactories == null)
				{
					pepperImporterComponentFactories = new Vector<ComponentFactory>();
				}
			}
		}
		return pepperImporterComponentFactories;
	}

// ============================================ end: resolve PepperImporters
// ============================================ start: resolve PepperManipulator	
	/**
	 * This unnecessary variable must be initialized, because of restrictions of the maven osgi scr plugin. Here
	 * it is not possible to use a list as osgi-reference. (this is a workaround)
	 */
	protected ComponentFactory pepperManipulatorComponentFactory;
	/**
	 * TODO make docu
	 */
	protected List<ComponentFactory> pepperManipulatorComponentFactories;

	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#addPepperManipulatorComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	@Reference(unbind="removePepperManipulatorComponentFactory", cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.STATIC, target="(component.factory=PepperManipulatorComponentFactory)")
	@Override
	public void addPepperManipulatorComponentFactory(ComponentFactory pepperManipulatorComponentFactory) 
	{
		if (pepperManipulatorComponentFactory== null)
			throw new PepperException("Cannot add an empty pepperManipulatorComponentFactory.");
		if (this.pepperManipulatorComponentFactories== null)
			this.pepperManipulatorComponentFactories= new Vector<ComponentFactory>();
		this.getPepperManipulatorComponentFactories().add(pepperManipulatorComponentFactory);
	}

	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#removePepperManipulatorComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	@Override
	public void removePepperManipulatorComponentFactory(ComponentFactory pepperManipulatorComponentFactory) 
	{
		if (this.pepperManipulatorComponentFactories== null)
			this.getPepperManipulatorComponentFactories().remove(pepperManipulatorComponentFactory);
	}
	
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getPepperManipulatorComponentFactories()
	 */
	@Override
	public List<ComponentFactory> getPepperManipulatorComponentFactories() {
		if (pepperManipulatorComponentFactories == null) {
			synchronized (this) {
				if (pepperManipulatorComponentFactories == null) {
					pepperManipulatorComponentFactories= new Vector<ComponentFactory>();
				}
			}
		}
		return pepperManipulatorComponentFactories;
	}

// ============================================ end: resolve PepperManipulators
// ============================================ start: resolve PepperExporters

	/**
	 * This unnecessary variable must be initialized, because of restrictions of the maven osgi scr plugin. Here
	 * it is not possible to use a list as osgi-reference. (this is a workaround)
	 */
	protected ComponentFactory pepperExporterComponentFactory;
	
	/**
	 * TODO make docu
	 */
	protected List<ComponentFactory> pepperExporterComponentFactories;
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#addPepperExporterComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	@Reference(unbind="removePepperExporterComponentFactory", cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.STATIC, target="(component.factory=PepperExporterComponentFactory)")
	@Override
	public void addPepperExporterComponentFactory(ComponentFactory pepperExporterComponentFactory) 
	{
		if (pepperExporterComponentFactory== null)
			throw new PepperException("Cannot add an empty pepperExporterComponentFactory.");
		if (this.pepperExporterComponentFactories== null)
			this.pepperExporterComponentFactories= new Vector<ComponentFactory>();
		this.getPepperExporterComponentFactories().add(pepperExporterComponentFactory);
	}

	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#removePepperExporterComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	@Override
	public void removePepperExporterComponentFactory(ComponentFactory pepperExporterComponentFactory) 
	{
		if (this.pepperExporterComponentFactories== null)
			this.getPepperExporterComponentFactories().remove(pepperExporterComponentFactory);
	}
	
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getPepperExporterComponentFactories()
	 */
	@Override
	public List<ComponentFactory> getPepperExporterComponentFactories() {
		if (pepperExporterComponentFactories == null) {
			synchronized (this) {
				if (pepperExporterComponentFactories== null){
					pepperExporterComponentFactories = new Vector<ComponentFactory>();
				}
			}
		}
		return pepperExporterComponentFactories;
	}
	
	/**
	 * Sets resources to a given PepperModule-object. The policy of computing the resource path is the following:
	 * <ol>
	 * 	<li>If a system property named SYMBOLIC_NAME_OF_MODULE+{@link #RESOURCES} is set, the {@link PepperModule#getResources()} would be directed the value contained by this property</li>
	 * 	<li>If environment variable {@value PepperConfiguration#ENV_PEPPER_MODULE_RESOURCES} is set, {@link PepperModule#getResources()} would be directed to value of {@value PepperConfiguration#ENV_PEPPER_MODULE_RESOURCES}/SYMBOLIC_NAME_OF_MODULE</li>
	 *  <li>If system property {@value PepperConfiguration#PROP_PEPPER_MODULE_RESOURCES} is set, {@link PepperModule#getResources()} would be directed to value of {@value PepperConfiguration#PROP_PEPPER_MODULE_RESOURCES}/SYMBOLIC_NAME_OF_MODULE</li>
	 *  <li>If nothing is set, {@link PepperModule#getResources()} would be directed to a subfolder having the name equal to the symbolic name of the bundle, which is located in the same folder as the bundle is located </li>
	 * </ol>
	 * @param module - the object for setting resources
	 */
	protected void setResources(PepperModule module)
	{
		if (	(module.getSymbolicName()== null) ||
				(module.getSymbolicName().isEmpty()))
			throw new PepperModuleException("Cannot set resources to module '"+module.getName()+"', because its symbolic name is empty.");
		
		// check case 1 (specific module resource property)
		String propName= module.getSymbolicName()+RESOURCES;
		String resourcePathStr=	null;
		resourcePathStr=System.getProperty(propName);
		
		// check case 2 (general system environment for module resources)
		if (	(resourcePathStr== null)||
				(resourcePathStr.isEmpty()))
		{
			String env=System.getenv(PepperConfiguration.ENV_PEPPER_MODULE_RESOURCES);
			if (	(env!= null)&&
					(!env.isEmpty()))
			{
				resourcePathStr= env+"/"+module.getSymbolicName();
			}
		}
		
		// check case 3 (general system property for module resources)
		if (	(resourcePathStr== null)||
				(resourcePathStr.isEmpty()))
		{
			String prop=System.getProperty(PepperConfiguration.PROP_PEPPER_MODULE_RESOURCES);
			if (	(prop!= null)&&
					(!prop.isEmpty()))
			{
				resourcePathStr= prop+"/"+module.getSymbolicName();
			}
		}
		
		// check case 4 (retrieve path from location where bundle is)
		if (	(resourcePathStr== null)||
				(resourcePathStr.isEmpty()))
		{
			resourcePathStr= retrieveResourcePathFromBundle(module);
		}
		
		if (resourcePathStr== null)
			logger.warn("Cannot set resource for pepper module '"+module.getName()+"'.");
		else
		{	
			URI resourcePathURI= URI.createFileURI(resourcePathStr);
			File resourcePathFile= new File(resourcePathURI.toFileString());
			
			if (!resourcePathFile.exists())
			{
				logger.warn("Resource folder '"+resourcePathFile.getAbsolutePath()+"' for pepper module '"+module.getSymbolicName()+"' does not exist and will be created. ");
				resourcePathFile.mkdirs();
			}
			module.setResources(resourcePathURI);
		}
	}
	/** name of system property to determine the locations of OSGi bundles**/
	public static final String PROP_OSGI_BUNDLES= "osgi.bundles";
	/**
	 * Retrieves the path, where the bundle is located and extracts the path, where resources are estimated 
	 * @return
	 */
	protected String retrieveResourcePathFromBundle(PepperModule module)
	{
		String resourcePathStr= null;
		
		if (	(module.getComponentContext()!= null)&&
				(module.getComponentContext().getBundleContext()!= null)&&
				(module.getComponentContext().getBundleContext().getBundle()!= null)&&
				(module.getComponentContext().getBundleContext().getBundle().getLocation()!= null))
		{
			if (module.getComponentContext()!= null)
			{
				Collection<String> bundleLocations=  new Vector<String>();
				String[] bundleNames= System.getProperty(PROP_OSGI_BUNDLES).split(",");
				if (bundleNames.length>0)
				{
					for (String bundleName: bundleNames)
					{
						bundleName= bundleName.replace("reference:", "");
						if (bundleName.contains("@")){
							int pos= bundleName.indexOf("@");
							bundleName= bundleName.substring(0, pos);
						}
						bundleLocations.add(bundleName);
					}
				}
				
				String currLocation=module.getComponentContext().getBundleContext().getBundle().getLocation();
				currLocation= currLocation.replace("initial@reference:file:", "");
				currLocation= currLocation.replace("../", "");
				if (currLocation.endsWith("/"))
					currLocation= currLocation.substring(0, currLocation.length()-1);
				String location=null;
				for (String bundleLocation: bundleLocations)
				{
					if (bundleLocation.contains(currLocation))
					{
						location= bundleLocation;
						break;
					}
				}
				if (location== null)
					throw new PepperFWException("Cannot retrieve a location out of OSGi parameters for retrieving resource path for Pepper module '"+ module.getName()+"'. ");
				if (location.endsWith(".jar"))
					location= location.replace(".jar", "/");
				else
				{
					if (!location.endsWith("/"))
						location= location+"/";
					location= location+ PepperConfiguration.SOURCES_RESOURCES;
					
				}
				resourcePathStr= location;
				if (resourcePathStr.startsWith("file:"))
					resourcePathStr= resourcePathStr.replace("file:", "");
			}	
		}
		return(resourcePathStr);
	}
	
	/** Configuration object to configure behaviour of {@link ModuleResolverImpl}**/
	private volatile PepperConfiguration pepperConfiguration= null;
	 /* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#setConfiguration(de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperConfiguration)
	 */
    @Override
	public synchronized void setConfiguration(PepperConfiguration pepperConfiguration)
    {
    	this.pepperConfiguration= pepperConfiguration;
    }
    /* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getConfiguration()
	 */
    @Override
	public PepperConfiguration getConfiguration()
    {
    	return(pepperConfiguration);
    }
	
	/**
	 * Sets a temporary folder for  each {@link PepperModule} to store temporary files
	 * if necessary. The general temporary folder could either be given via the property 
	 * {@link PepperConfiguration#PROP_TEMP_FOLDER} or if this is not set, resolved by via
	 * using the temporary folder provided by the OS.<br/>
	 * The specific temporary folder is given by the general temporary folder + a subfolder
	 * for the modules name and a subfolder for the number of the instance of the current module. 
	 * @param module - the object for setting temporaries
	 * @param number - number of module instance
	 */
	protected void setTemporaries(PepperModule module, int number)
	{
		if (	(module.getSymbolicName()== null) ||
				(module.getSymbolicName().isEmpty()))
			throw new PepperModuleException("Cannot set temporaries to module '"+module.getName()+"', because its symbolic name is empty.");
		
		File genTmpPath= getConfiguration().getTempPath();
		if (genTmpPath== null)
			throw new PepperFWException("Cannot start converting, because the system property '"+PepperConfiguration.PROP_TEMP_FOLDER+"' isn't set. This might be an internal failure.");
		
		File tmpPath= new File(genTmpPath.getAbsolutePath()+"/"+module.getSymbolicName()+"/"+number);
		
		try {
			tmpPath.mkdirs();
		} catch (Exception e) 
		{
			throw new PepperException("Cannot create temporary folder for module: "+module.getName(), e);
		}
		module.setTemproraries(URI.createFileURI(tmpPath.getAbsolutePath()));
	}
	
	/**
	 * Stores to every created kind of module the number of existing instances.
	 */
	private volatile Map<String, Integer> numberOfModuleInstances= null;
	
	/**
	 * increases the number of the instances of the given module.
	 */
	private Integer increaseNumberOfModules(PepperModule module)
	{
		Integer retVal= null;
		if (module== null)
			throw new PepperFWException("Cannot increase number of modules, because module is empty. ");
		String moduleSymbolicName= module.getSymbolicName();
		if (	(moduleSymbolicName== null) ||
				(moduleSymbolicName.isEmpty()))
			throw new PepperFWException("Cannot increase number of module '"+module.getName()+"', because the symbolic name of module is empty.");
		synchronized (this) {
			if (this.numberOfModuleInstances.containsKey(moduleSymbolicName))
			{
				Integer number= this.numberOfModuleInstances.get(moduleSymbolicName);
				retVal= number;
				number++;
				this.numberOfModuleInstances.put(moduleSymbolicName, number);
			}	
			else{
				this.numberOfModuleInstances.put(moduleSymbolicName, 1);
				retVal= 1;
			}
		}
		return(retVal);
	}
	
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getPepperImporters()
	 */
	@Override
	public List<PepperImporter> getPepperImporters() 
	{
		List<PepperImporter> pepperImporters= null;
		if (this.pepperImporterComponentFactories!= null)
		{	
			//run through all pepperImporterComponentFactories and search for mapping PepperImporter
			for (ComponentFactory componentFactory: this.getPepperImporterComponentFactories())
			{
				Object instance= componentFactory.newInstance(null).getInstance();
				if (instance instanceof PepperImporter)
				{
					if (pepperImporters== null)
						pepperImporters= new Vector<PepperImporter>();
					PepperImporter importer= (PepperImporter) instance;
					if (	(importer.getSymbolicName()== null) ||
							(importer.getSymbolicName().isEmpty()))
						throw new PepperModuleException("Cannot register PepperModule, because the symbolic name of module '"+importer.getName()+"' is empty.");
					
					this.setTemporaries(importer, increaseNumberOfModules(importer));
					this.setResources(importer);
					pepperImporters.add(importer);
				}
			}
		}		
		return(pepperImporters);
	}

	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getPepperManipulators()
	 */
	@Override
	public List<PepperManipulator> getPepperManipulators() 
	{
		List<PepperManipulator> pepperManipulators= null;
		if (this.pepperManipulatorComponentFactories!= null)
		{	
			//run through all pepperManipulatorComponentFactories and search for mapping PepperManipulator
			for (ComponentFactory componentFactory: this.getPepperManipulatorComponentFactories())
			{
				Object instance= componentFactory.newInstance(null).getInstance();
				if (instance instanceof PepperManipulator)
				{
					if (pepperManipulators== null)
						pepperManipulators= new Vector<PepperManipulator>();
					PepperManipulator manipulator= (PepperManipulator) instance;
					this.setTemporaries(manipulator, increaseNumberOfModules(manipulator));
					this.setResources(manipulator);
					pepperManipulators.add(manipulator);
				}
			}
		}
		return(pepperManipulators);
	}

	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getPepperExporters()
	 */
	@Override
	public List<PepperExporter> getPepperExporters() 
	{
		List<PepperExporter> pepperExporters= null;
		if (this.pepperExporterComponentFactories!= null)
		{	
			//run through all pepperExporterComponentFactories and search for mapping PepperExporter
			for (ComponentFactory componentFactory: this.getPepperExporterComponentFactories())
			{
				Object instance= componentFactory.newInstance(null).getInstance();
				if (instance instanceof PepperExporter)
				{
					if (pepperExporters== null)
						pepperExporters= new Vector<PepperExporter>();
					PepperExporter exporter= (PepperExporter) instance;
					this.setTemporaries(exporter, increaseNumberOfModules(exporter));
					this.setResources(exporter);
					pepperExporters.add(exporter);
				}
			}
		}
		return(pepperExporters);
	}

	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getPepperModule(de.hu_berlin.german.korpling.saltnpepper.pepper.communication.StepDesc)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PepperModule getPepperModule(StepDesc stepDesc) 
	{
		PepperModule pepperModule= null;
		
		if (this.pepperImporterComponentFactories!= null)
		{	
			if (	(this.getPepperImporters()== null)||
					(this.getPepperImporters().size()== 0))
				throw new PepperConvertException("Cannot convert data, because no Pepper module is registered.");
			//run through all pepperImporterComponentFactories and search for mapping PepperImporter
			List<PepperModule> modules= null;
			if (MODULE_TYPE.IMPORTER.equals(stepDesc.getModuleType()))
				modules= (List<PepperModule>)(List<? extends PepperModule>)getPepperImporters();
			else if (MODULE_TYPE.MANIPULATOR.equals(stepDesc.getModuleType()))
				modules= (List<PepperModule>)(List<? extends PepperModule>)getPepperManipulators();
			else if (MODULE_TYPE.EXPORTER.equals(stepDesc.getModuleType()))
				modules= (List<PepperModule>)(List<? extends PepperModule>)getPepperExporters();
			
			if (modules== null){
				throw new PepperException("Cannot resolve a module for step description '"+stepDesc+"', since no Pepper modules are registered.");
			}
			
			for (PepperModule module: modules){
				//emit by name
				if(stepDesc.getName()!= null)
				{
					if (stepDesc.getName().equalsIgnoreCase(module.getName()))
					{	
						pepperModule= module;
						break;
					}
				}
				//emit by format Definition
				else
				{
					if (	(pepperModule instanceof PepperImporter)||
							(pepperModule instanceof PepperExporter))
					{
						if (	(stepDesc.getCorpusDesc().getFormatDesc().getFormatName()!= null) &&
								(stepDesc.getCorpusDesc().getFormatDesc().getFormatVersion()!= null))
						{
							List<FormatDesc> supportedFormats= null;
							if (pepperModule instanceof PepperImporter)
								supportedFormats= ((PepperImporter)pepperModule).getSupportedFormats();
							else if (pepperModule instanceof PepperExporter)
								supportedFormats= ((PepperExporter)pepperModule).getSupportedFormats();
							for (FormatDesc format: supportedFormats)
							{
								if (	(stepDesc.getCorpusDesc().getFormatDesc().getFormatName().equalsIgnoreCase(format.getFormatName())) &&
										(stepDesc.getCorpusDesc().getFormatDesc().getFormatVersion().equalsIgnoreCase(format.getFormatVersion())))
								{	
									pepperModule= module;
									break;
								}
							}	
						}	
					}
				}
			}
		}
		return(pepperModule);
	}
	
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getPepperImporter(de.hu_berlin.german.korpling.saltnpepper.pepper.communication.StepDesc)
	 */
	@Override
	public PepperImporter getPepperImporter(StepDesc stepDesc) 
	{
		return((PepperImporter)getPepperModule(stepDesc));
	}
	
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getPepperManipulator(de.hu_berlin.german.korpling.saltnpepper.pepper.communication.StepDesc)
	 */
	@Override
	public PepperManipulator getPepperManipulator(StepDesc stepDesc) 
	{
		return((PepperManipulator)getPepperModule(stepDesc));
	}

	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver#getPepperExporter(de.hu_berlin.german.korpling.saltnpepper.pepper.communication.StepDesc)
	 */
	@Override
	public PepperExporter getPepperExporter(StepDesc stepDesc) 
	{
		return((PepperExporter)getPepperModule(stepDesc));
	}
	
	/**
	 * TODO make docu
	 */
	public String toString() {
				StringBuffer result = new StringBuffer(super.toString());
		result.append(" (pepperImporterComponentFactories: ");
		result.append(pepperImporterComponentFactories);
		result.append(", pepperManipulatorComponentFactories: ");
		result.append(pepperManipulatorComponentFactories);
		result.append(", pepperExporterComponentFactories: ");
		result.append(pepperExporterComponentFactories);
		result.append(')');
		return result.toString();
	}

} //PepperModuleResolverImpl
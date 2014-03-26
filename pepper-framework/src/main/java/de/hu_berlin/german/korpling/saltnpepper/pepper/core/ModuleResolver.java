package de.hu_berlin.german.korpling.saltnpepper.pepper.core;

import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.StepDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;

public interface ModuleResolver {

	/**
	 * Returns the {@link ComponentContext} of the OSGi environment the bundle was started in.
	 * @return
	 */
	public abstract ComponentContext getComponentContext();

	/**
	 * Sets the {@link ComponentContext} of the OSGi environment the bundle was started in. 
	 * @param componentContext
	 */
	public abstract void activate(ComponentContext componentContext);

	/**
	 * Returns a status description as {@link String}. The returned {@link String} contains the number of all available
	 * {@link PepperModule} objects in a readable format.
	 * @return {@link String} representation of all {@link PepperModule} objects available by this {@link PepperModuleResolver} object.
	 */
	public abstract String getStatus();

	/**
	 * This method is called by OSGi framework and adds all registered {@link ComponentFactory} objects having the
	 * name PepperImporterComponentFactory to this object. All {@link ComponentFactory} objects are stored in an internal object 
	 * {@link #pepperImporterComponentFactories}
	 */
	//	@Reference(unbind="removePepperImporterComponentFactory", cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.STATIC ,target="(component.factory=PepperImporterComponentFactory)")
	public abstract void addPepperImporterComponentFactory(
			ComponentFactory pepperImporterComponentFactory);

	/**
	 * TODO make docu
	 */
	public abstract void removePepperImporterComponentFactory(
			ComponentFactory pepperImporterComponentFactory);

	/**
	 * TODO make docu
	 */
	public abstract List<ComponentFactory> getPepperImporterComponentFactories();

	/**
	 * This method is called by OSGi framework and adds all registered {@link ComponentFactory} objects having the
	 * name PepperManipulatorComponentFactory to this object. All {@link ComponentFactory} objects are stored in an internal object 
	 * {@link #pepperManipulatorComponentFactories}.
	 */
	//	@Reference(unbind="removePepperManipulatorComponentFactory", cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.STATIC, target="(component.factory=PepperManipulatorComponentFactory)")
	public abstract void addPepperManipulatorComponentFactory(
			ComponentFactory pepperManipulatorComponentFactory);

	/**
	 * TODO make docu
	 */
	public abstract void removePepperManipulatorComponentFactory(
			ComponentFactory pepperManipulatorComponentFactory);

	/**
	 * TODO make docu
	 */
	public abstract List<ComponentFactory> getPepperManipulatorComponentFactories();

	/**
	 * This method is called by OSGi framework and adds all registered {@link ComponentFactory} objects having the
	 * name PepperExporterComponentFactory to this object. All {@link ComponentFactory} objects are stored in the 
	 * internal object list {@link #pepperExporterComponentFactories}.
	 * @param pepperExporterComponentFactory {@link ComponentFactory} object to be stored in internal list 
	 * @model pepperExporterComponentFactoryDataType="de.hub.corpling.pepper.pepperFW.ComponentFactory"
	 * @generated
	 */
	//	@Reference(unbind="removePepperExporterComponentFactory", cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.STATIC, target="(component.factory=PepperExporterComponentFactory)")
	public abstract void addPepperExporterComponentFactory(
			ComponentFactory pepperExporterComponentFactory);

	/**
	 * TODO make some docu
	 */
	public abstract void removePepperExporterComponentFactory(
			ComponentFactory pepperExporterComponentFactory);

	/**
	 * TODO make docu
	 */
	public abstract List<ComponentFactory> getPepperExporterComponentFactories();

	// ============================================ end: resolve PepperExporters
	public static final String RESOURCES = ".resources";

	/**
	 * Sets the configuration object for this object.
	 * @param pepperConfiguration
	 */
	public abstract void setConfiguration(
			PepperConfiguration pepperConfiguration);

	/**
	 * Returns the configuration object for this converter object. If no {@link PepperConfiguration}
	 * object was set, an automatic detection of configuration file will be started. 
	 * @return configuration object
	 */
	public abstract PepperConfiguration getConfiguration();

	/**
	 * Creates an instance of {@link PepperImporter} for each listed {@link ComponentFactory} in list 
	 * {@link #pepperImporterComponentFactories} and returns that list. This {@link PepperModuleResolver} instance
	 * does not store any link to the created object, so it can be used and removed as the caller like. Thus each call
	 * creates a new list containing new objects.
	 * @return a list of {@link PepperImporter} objects.
	 */
	public abstract List<PepperImporter> getPepperImporters();

	/**
	 * Creates an instance of {@link PepperManipulator} for each listed {@link ComponentFactory} in list 
	 * {@link #pepperManipulatorComponentFactories} and returns that list. This {@link PepperModuleResolver} instance
	 * does not store any link to the created object, so it can be used and removed as the caller like. Thus each call
	 * creates a new list containing new objects.
	 * @return a list of {@link PepperManipulator} objects.
	 */
	public abstract List<PepperManipulator> getPepperManipulators();

	/**
	 * Creates an instance of {@link PepperExporter} for each listed {@link ComponentFactory} in list 
	 * {@link #pepperExporterComponentFactories} and returns that list. This {@link PepperModuleResolver} instance
	 * does not store any link to the created object, so it can be used and removed as the caller like. Thus each call
	 * creates a new list containing new objects.
	 * @return a list of {@link PepperExporter} objects.
	 */
	public abstract List<PepperExporter> getPepperExporters();

	/**
	 * Returns a {@link PepperModule} object matching to the given {@link StepDesc}. A new instance of
	 * the specific {@link PepperImporter} class is created and returned. No references to the returned object will
	 * be stored in this {@link PepperModuleResolver} object. When calling {@link #getPepperModule(ImporterParams)}
	 * a new instance of {@link PepperModule} is created.
	 * @param pepperImporterParams specifies the {@link PepperModule} object to be found
	 * @return a new instance of {@link PepperModule} matching the given {@link StepDesc}
	 */
	public abstract PepperModule getPepperModule(StepDesc stepDesc);

	/**
	 * Returns a {@link PepperImporter} object matching to the given {@link ImporterParams}. A new instance of
	 * the specific {@link PepperImporter} class is created and returned. No references to the returned object will
	 * be stored in this {@link PepperModuleResolver} object. When calling {@link #getPepperImporter(ImporterParams)}
	 * a new instance of {@link PepperImporter} is created.
	 * @param pepperImporterParams specifies the {@link PepperImporter} object to be found
	 * @return a new instance of {@link PepperImporter} matching the given {@link ImporterParams}
	 */
	public abstract PepperImporter getPepperImporter(StepDesc stepDesc);

	/**
	 * Creates an instance of {@link PepperManipulator} for each listed {@link ComponentFactory} in list 
	 * {@link #pepperManipulatorComponentFactories} and returns that list. This {@link PepperModuleResolver} instance
	 * does not store any link to the created object, so it can be used and removed as the caller like. Thus each call
	 * creates a new list containing new objects.
	 * @return a list of {@link PepperManipulator} objects.
	 */
	public abstract PepperManipulator getPepperManipulator(StepDesc stepDesc);

	/**
	 * Creates an instance of {@link PepperExporter} for each listed {@link ComponentFactory} in list 
	 * {@link #pepperExporterComponentFactories} and returns that list. This {@link PepperModuleResolver} instance
	 * does not store any link to the created object, so it can be used and removed as the caller like. Thus each call
	 * creates a new list containing new objects.
	 * @return a list of {@link PepperExporter} objects.
	 */
	public abstract PepperExporter getPepperExporter(StepDesc stepDesc);

}
/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.ComponentContext;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperModuleImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * TODO make some docu
 * 
 * @author Florian Zipser
 */
public interface PepperModule{
	/** 
	 * A string specifying a value for a folder as ending. This is useful for {@link #setTypeOfResource(URI)}, to determine, that
	 * even a folder can be mapped to a resource.Can be used by importers to be put in collection {@link #getSDocumentEndings()} or {@link #getSCorpusEndings()}
	 */
	public static final String ENDING_FOLDER="FOLDER";
	/**
	 * A string specifying a value for a leaf folder as ending. This is useful for {@link #setTypeOfResource(URI)}, to determine, that
	 * even a leaf folder can be mapped to a resource. Can be used by importers to be put in collection {@link #getSDocumentEndings()} or {@link #getSCorpusEndings()}
	 */
	public static final String ENDING_LEAF_FOLDER="LEAF_FOLDER";
	/**Ending for an xml file. Can be used by importers to be put in collection {@link #getSDocumentEndings()} or {@link #getSCorpusEndings()}*/
	public static final String ENDING_XML="xml";
	/**Ending for an txt file. Can be used by importers to be put in collection {@link #getSDocumentEndings()} or {@link #getSCorpusEndings()}*/
	public static final String ENDING_TXT="txt";
	/**Ending for an tab file. Can be used by importers to be put in collection {@link #getSDocumentEndings()} or {@link #getSCorpusEndings()}*/
	public static final String ENDING_TAB="tab";
	/** All kinds of file endings **/
	public static final String ENDING_ALL_FILES="ALL_FILES";
	
	/**
	 * Returns a {@link PepperModuleDesc} object, which is a kind of a fingerprint of this {@link PepperModule}. This fingerprint for instance contains information like
	 * the name, the version of this module or information about the supplier.
	 * @return fingerprint to this module
	 */
	public PepperModuleDesc getFingerprint();
	
	/**
	 * Returns the type of this module.
	 * @return type of module
	 */
	public MODULE_TYPE getModuleType();
	
	/**
	 * Returns the {@link ComponentContext} of the OSGi environment the bundle was started in.
	 * @return
	 */
	public ComponentContext getComponentContext();
	
	/**
	 * Returns the name of this module. In most cases, the name somehow describes the task of the module.
	 * @return the value of the '<em>Name</em>' attribute.
	 */
	String getName();
	
	/**
	 * Returns the version of this module.
	 * @return the value of the '<em>Version</em>' attribute.
	 */
	String getVersion();

	/**
	 * Sets the version of this module. The version normally is set internally, this method only exists for 
	 * dependency injection, by the modules project itself. But this method is never called by the pepper framework.
	 * @param value the new value of the '<em>Version</em>' attribute.
	 */
	void setVersion(String value);
	/**
	 * Returns a short description of this module. Please support some information, for the user, of
	 * what task this module does. 
	 * @return a short description of the task of this module
	 */
	public String getDesc();
	/**
	 * Sets a short description of this module. Please support some information, for the user, of
	 * what task this module does. 
	 * @param desc a short description of the task of this module
	 */
	public void setDesc(String desc);
	
	/**
	 * Returns a uri where to find more information about this module and where to find some contact 
	 * information to contact the supplier.
	 * @return contact address like eMail address or homepage address
	 */
	public URI getSupplierContact();
	/**
	 * Sets a uri where to find more information about this module and where to find some contact 
	 * information to contact the supplier.
	 * @param uri contact address like eMail address or homepage address
	 */
	public void setSupplierContact(URI uri);

	/**
	 * Returns a {@link PepperModuleProperties} object containing properties to customize the behavior of this {@link PepperModule}.
	 * @return
	 */
	public PepperModuleProperties getProperties();
	
	/**
	 * Sets the{@link PepperModuleProperties} object containing properties to customize the behavior of this {@link PepperModule}.
	 * Please make sure, that this method is called in constructor of your module. If not, a general {@link PepperModuleProperties} 
	 * object is created by the pepper framework and will be initialized. This means, when calling this method later,
	 * all properties for customizing the module will be overridden.  
	 * @param properties 
	 */
	public void setProperties(PepperModuleProperties properties);
	
	/**
	 * Returns the container and controller object for the current module. The {@link ModuleController} object is a kind
	 * of communicator between a {@link PepperModule} and the pepper framework. 
	 * @return the value of the '<em>Pepper Module Controller</em>' container reference.
	 */
	ModuleController getModuleController();

	/**
	 * Sets the container and controller object for the current module. The {@link ModuleController} object is a kind
	 * of communicator between a {@link PepperModule} and the pepper framework. Also calls the inverse method {@link ModuleController#setPepperModule_basic(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule)}.
	 * Note, this method only should be called by pepper framework.
	 * @param value the new value of the '<em>Pepper Module Controller</em>' container reference.
	 */
	void setPepperModuleController(ModuleController value);

	/**
	 * Sets the container and controller object for the current module. The {@link ModuleController} object is a kind
	 * of communicator between a {@link PepperModule} and the pepper framework. 
	 * Note, this method only should be called by pepper framework.
	 * @param value the new value of the '<em>Pepper Module Controller</em>' container reference.
	 */
	public void setPepperModuleController_basic(ModuleController value);
	
	/**
	 * Returns the {@link SaltProject} object, which is filled, manipulated or exported by the current module.
	 * @return the value of the '<em>Salt Project</em>' attribute.
	 */
	SaltProject getSaltProject();

	/**
	 * Sets the {@link SaltProject} object, which is filled, manipulated or exported by the current module.
	 * Note: This method only should be called by the pepper framework.
	 * @param value the new value of the '<em>Salt Project</em>' attribute.
	 */
	void setSaltProject(SaltProject value);

	/**
	 * Returns the {@link SCorpusGraph} object which is filled, manipulated or exported by the current module. The 
	 * {@link SCorpusGraph} object is contained in the salt project {@link #getSaltProject()}.
	 * @return the value of the '<em>SCorpus Graph</em>' attribute.
	 */
	SCorpusGraph getSCorpusGraph();

	/**
	 * Sets the {@link SCorpusGraph} object which is filled, manipulated or exported by the current module. The 
	 * {@link SCorpusGraph} object is contained in the salt project {@link #getSaltProject()}.
	 * Note: This method only should be called by the pepper framework. 
	 * @param value the new value of the '<em>SCorpus Graph</em>' attribute.
	 */
	void setSCorpusGraph(SCorpusGraph value);

	/**
	 * Returns the path of the folder which might contain resources for a Pepper module. This is the folder,
	 * which is delivered as part of the modules zip. Usually a Pepper module is a zip file containing a jar file
	 * and a folder having the same name as the jar file. In default configuration all files of folder 
	 * "./src/main/resources" are copied to the resource folder.
	 * @return path to resources
	 */
	URI getResources();

	/**
	 * Sets the resource folder used by {@link #getResources()}. This method should only be invoked by the
	 * Pepper framework. The documentation of {@link #getResources()} for more details.
	 * @param value path to resource folder
	 */
	void setResources(URI value);

	/**
	 * TODO make docu 
	 */
	@Deprecated
	URI getTemproraries();

	/**
	 * TODO make docu 
	 */
	@Deprecated
	void setTemproraries(URI value);

	/**
	 * Returns the symbolic name of this OSGi bundle. 
	 * @return the value of the '<em>Symbolic Name</em>' attribute.
	 */
	String getSymbolicName();

	/**
	 * Sets the symbolic name of this OSGi bundle. This value is set automatically inside the activate method, which is 
	 * implemented in {@link PepperModuleImpl} class. If you want to manipulate that method. make sure to set the 
	 * symbolic name and make sure, that it is set to the bundles symbolic name.
	 * @param value the new value of the '<em>Symbolic Name</em>' attribute.
	 */
	void setSymbolicName(String value);
	
	/**
	 * If {@link #isReadyToStart()} has returned false, this method returns a list of reasons
	 * why this module is not ready to start.  
	 * @return a list describing the reasons, or an empty list if there were no problems
	 */
	public Collection<String> getStartProblems();
	
	/**
	 * This method is called by the pepper framework after initializing this object and directly before start processing. 
	 * Initializing means setting properties {@link PepperModuleProperties}, setting temporary files, resources etc. .
	 * returns false or throws an exception in case of {@link PepperModule} instance is not ready for any reason
	 * <br/>
	 * This method is also called, when Pepper is in self-test mode, to check if module is correctly instantiated.
	 * <br/>
	 * The default implementation checks:
	 * <ul>
	 *  <li>if a path to resource folder is given</li>
	 *  <li>if the {@link MODULE_TYPE} is not null</li>
	 *  <li>if the name is not null</li>
	 * </ul>
	 * When overriding this method, please call super.isReadyToStart() first and in case a
	 * problem occured add it to the list {@link #getStartProblems()}.
	 * @return false, {@link PepperModule} instance is not ready for any reason, true, else.
	 */
	public boolean isReadyToStart() throws PepperModuleNotReadyException;
	
	/**
	 * Sets whether this {@link PepperModule} is able to run multithreaded. This method only should be called by the module itself.
	 * @param isThreaded true, if module can run in multithread mode.
	 */
	public void setIsMultithreaded(boolean isMultithreaded);
	/**
	 * Returns whether this {@link PepperModule} is able to run multithreaded. The behavior only should be set by the module itself
	 * via calling {@link #setIsMultithreaded(boolean)}.
	 * @return true, if module can run in multithread mode.
	 */
	public boolean isMultithreaded();
	/**
	 * Starts the conversion process. This method is the main method of a pepper module. I fthis method is not overridden, it will call
	 * {@link #start(SElementId)} for each {@link SDocument} and {@link SCorpus} object being contained in the set {@link SCorpusGraph}.
	 * This is done in a multithreaded way by default. 
	 * <strong>Note: When your module should not run in multithreaded mode, call {@link #setIsMultithreaded(boolean)}.</strong>
	 * @model exceptions="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleException"
	 */
	void start() throws PepperModuleException;

	/**
	 * This method is called by the method {@link #start()}. This is the only call in Pepper. You do not need to override this method,
	 * in case of you are happy with the default behavior. In default, this method invokes a multithreaded process, which creates {@link PepperMapper}
	 * objects for each given {@link SElementId} object, to process the corresponding  {@link SDocument} or {@link SCorpus} object. The 
	 * {@link PepperMapper} objects are not created by the method itself, the creation is delegated to {@link #createPepperMapper(SElementId)}, which
	 * has to be overridden. Default initializations are done there (for more details, please take a look into the doc of that method). Further this 
	 * method links the created {@link PepperMapper} object to a {@link PepperMapperController} object and makes sure, that the process runs
	 * in a by Pepper controlled manner.
	 * <br/>
	 * <strong>Note: When your module should not run in multithreaded mode, call {@link #setIsMultithreaded(boolean)}.</strong>
	 * <br/>
	 * <strong>Note: In case of you override this method, please make sure to also override the following methods:
	 * <ul>
	 * 	<li>{@link #getProgress(SElementId)}</li>
	 * </ul>
	 * </strong>
	 * @model exceptions="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleException" sElementIdDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SElementId"
	 */
	void start(SElementId sElementId) throws PepperModuleException;
	
	/**
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 * 
	 * This method creates a customized {@link PepperMapper} object and returns it. You can here do some additional initialisations. 
	 * Thinks like setting the {@link SElementId} of the {@link SDocument} or {@link SCorpus} object and the {@link URI} resource is done
	 * by the framework (or more in detail in method {@link #start()}).  
	 * The parameter <code>sElementId</code>, if a {@link PepperMapper} object should be created in case of the object to map is either 
	 * an {@link SDocument} object or an {@link SCorpus} object of the mapper should be initialized differently. 
	 * <br/>
	 * <strong>Note: Override this method.</strong>
	 * 
	 * @param sElementId {@link SElementId} of the {@link SCorpus} or {@link SDocument} to be processed. 
	 * @return {@link PepperMapper} object to do the mapping task for object connected to given {@link SElementId}
	 */
	public PepperMapper createPepperMapper(SElementId sElementId);

	/**
	 * This method could be overridden, to make a proposal for the import order of {@link SDocument} objects. 
	 * Overriding this method is useful, in case of the order matters in the specific mapping of this 
	 * {@link PepperModule}. In this case a influencing the import order can decrease the processing time.
	 * If you do not want to influence the order, just return an empty list, or don't override this method. 
	 * <br/>
	 * In case you want to override this method, you can return a value for each passed {@link SCorpusGraph}. 
	 * <br/>
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.</strong>
	 * @param sCorpusGraph the {@link SCorpusGraph} object for which the order could be proposed
	 * @return a list determining the import order of {@link SDocument} objects
	 */
	public List<SElementId> proposeImportOrder(SCorpusGraph sCorpusGraph);
	
	/**
	 * This method is invoked by the Pepper framework, to get the current progress concerning the {@link SDocument} object
	 * corresponding to the given {@link SElementId} in percent. A valid value return must be between 0 and 1. 
	 * <br/>
	 * <strong>Note: In case, you have overridden the method {@link #start(SElementId)} or {@link #start()}, please also override this method,
	 * because it accesses an internal list of all mappers, which initialized in {@link #start(SElementId)}.</strong>
	 * @param globalID identifier of the requested {@link SDocument} object, note, that this is not the {@link SElementId}.
	 */
	Double getProgress(String globalId);
	
	/**
	 * This method is invoked by the Pepper framework, to get the current total progress of all {@link SDocument} objects
	 * being processed by this module. A valid value return must be between 0 and 1. This method can 
	 * be overridden by a derived {@link PepperModule} class. If this method is not overridden, it will return null.
	 * @return the progress of all documents processed by this module 
	 */
	Double getProgress();
	
	/**
	 * This method is called by the pepper framework at the end of a conversion process. Means after all objects (
	 * {@link SDocument} and {@link SCorpus} objects) have been processed. This method can be used to do some clean up
	 * (e.g. to close streams etc.). 
	 */
	void end() throws PepperModuleException;
	
	/**
	 * This method is called by a {@link PepperMapperController} object to notify the {@link PepperModule} object, that the
	 * mapping is done.
	 * @param controller The object which is done with its job
	 */
	public void done(PepperMapperController controller);
	
	/**
	 * Invokes processings, before the mapping was started. This could be
	 * helpful, for instance to make some preparations for the mapping. To
	 * trigger this pre processing for a specific Pepper module a set of
	 * customization properties is available. Customization properties
	 * triggering a pre processing starts with
	 * {@value PepperModuleProperties#PREFIX_PEPPER_BEFORE}. This method is
	 * called by the method {@link #map()}, before
	 * {@link PepperMapper#mapSDocument()} was called.
	 * 
	 * @param sElementId
	 *            id of either {@link SDocument} or {@link SCorpus} object to be
	 *            prepared
	 * @throws PepperModuleException
	 */
	public void before(SElementId sElementId) throws PepperModuleException;

	/**
	 * Invokes processings, after the mapping is done. This could be helpful,
	 * for instance to make some processing after the mapping e.g. adding all
	 * created nodes and relations to a layer. To trigger this post processing
	 * for a specific Pepper module a set of customization properties is
	 * available. Customization properties triggering a post processing starts
	 * with {@value PepperModuleProperties#PREFIX_PEPPER_AFTER}. This method is
	 * called by the method {@link #map()}, after
	 * {@link PepperMapper#mapSDocument()} was called.
	 * 
	 * @param sElementId
	 *            id of either {@link SDocument} or {@link SCorpus} object to be
	 *            post processed
	 * @throws PepperModuleException
	 */
	public void after(SElementId sElementId) throws PepperModuleException;
}

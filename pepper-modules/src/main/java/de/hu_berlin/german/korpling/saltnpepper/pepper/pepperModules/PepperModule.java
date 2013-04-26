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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pepper Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getName <em>Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getPepperModuleController <em>Pepper Module Controller</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSaltProject <em>Salt Project</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getReturningMode <em>Returning Mode</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSCorpusGraph <em>SCorpus Graph</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getResources <em>Resources</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getTemproraries <em>Temproraries</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSymbolicName <em>Symbolic Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getPersistenceConnector <em>Persistence Connector</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSpecialParams <em>Special Params</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule()
 * @model abstract="true"
 * @generated
 */
public interface PepperModule extends EObject {
	/**
	 * Returns the name of this module. In most cases, the name somehow describes the task of the module.
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_Name()
	 * @model required="true" changeable="false"
	 * @generated
	 */
	String getName();
	
	/**
	 * Returns the version of this module.
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the version of this module. The version normally is set internally, this method only exists for 
	 * dependency injection, by the modules project itself. But this method is never called by the pepper framework.
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns a {@link PepperModuleProperties} object containing properties to customize the behaviour of this {@link PepperModule}.
	 * @return
	 */
	public PepperModuleProperties getProperties();
	
	/**
	 * Sets the{@link PepperModuleProperties} object containing properties to customize the behaviour of this {@link PepperModule}.
	 * Please make sure, that this method is called in constructor of your module. If not, a general {@link PepperModuleProperties} 
	 * object is created by the pepper framework and will be initialized. This means, when calling this method later,
	 * all properties for customizing the module will be overridden.  
	 * @param properties 
	 */
	public void setProperties(PepperModuleProperties properties);
	
	/**
	 * Returns the container and controller object for the current module. The {@link PepperModuleController} object is a kind
	 * of communicator between a {@link PepperModule} and the pepper framework. 
	 * @return the value of the '<em>Pepper Module Controller</em>' container reference.
	 * @see #setPepperModuleController(PepperModuleController)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_PepperModuleController()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController#getPepperModule
	 * @model opposite="pepperModule" transient="false"
	 * @generated
	 */
	PepperModuleController getPepperModuleController();

	/**
	 * Sets the container and controller object for the current module. The {@link PepperModuleController} object is a kind
	 * of communicator between a {@link PepperModule} and the pepper framework. 
	 * Note, this method only should be called by pepper framework.
	 * @param value the new value of the '<em>Pepper Module Controller</em>' container reference.
	 * @see #getPepperModuleController()
	 * @generated
	 */
	void setPepperModuleController(PepperModuleController value);

	/**
	 * Returns the {@link SaltProject} object, which is filled, manipulated or exported by the current module.
	 * @return the value of the '<em>Salt Project</em>' attribute.
	 * @see #setSaltProject(SaltProject)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_SaltProject()
	 * @model dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SaltProject" required="true"
	 * @generated
	 */
	SaltProject getSaltProject();

	/**
	 * Sets the {@link SaltProject} object, which is filled, manipulated or exported by the current module.
	 * Note: This method only should be called by the pepper framework.
	 * @param value the new value of the '<em>Salt Project</em>' attribute.
	 * @see #getSaltProject()
	 * @generated
	 */
	void setSaltProject(SaltProject value);

	/**
	 * Returns the {@link SCorpusGraph} object which is filled, manipulated or exported by the current module. The 
	 * {@link SCorpusGraph} object is contained in the salt project {@link #getSaltProject()}.
	 * @return the value of the '<em>SCorpus Graph</em>' attribute.
	 * @see #setSCorpusGraph(SCorpusGraph)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_SCorpusGraph()
	 * @model dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SCorpusGraph" required="true"
	 * @generated
	 */
	SCorpusGraph getSCorpusGraph();

	/**
	 * Sets the {@link SCorpusGraph} object which is filled, manipulated or exported by the current module. The 
	 * {@link SCorpusGraph} object is contained in the salt project {@link #getSaltProject()}.
	 * Note: This method only should be called by the pepper framework. 
	 * @param value the new value of the '<em>SCorpus Graph</em>' attribute.
	 * @see #getSCorpusGraph()
	 * @generated
	 */
	void setSCorpusGraph(SCorpusGraph value);
	
//	/**
//	 * Only for internal use, returns the mode of module corresponding to current processed object like
//	 * {@link SDocument} or {@link SCorpus}. This method must be replaced.
//	 * @return the value of the '<em>Returning Mode</em>' attribute.
//	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE
//	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_ReturningMode()
//	 * @model default="PUT" changeable="false"
//	 * @generated
//	 * @deprecated
//	 */
//	RETURNING_MODE getReturningMode();

	/**
	 * Returns the value of the '<em><b>Resources</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resources</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resources</em>' attribute.
	 * @see #setResources(URI)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_Resources()
	 * @model dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.URI"
	 * @generated
	 */
	URI getResources();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getResources <em>Resources</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resources</em>' attribute.
	 * @see #getResources()
	 * @generated
	 */
	void setResources(URI value);

	/**
	 * Returns the value of the '<em><b>Temproraries</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temproraries</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temproraries</em>' attribute.
	 * @see #setTemproraries(URI)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_Temproraries()
	 * @model dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.URI"
	 * @generated
	 */
	URI getTemproraries();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getTemproraries <em>Temproraries</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temproraries</em>' attribute.
	 * @see #getTemproraries()
	 * @generated
	 */
	void setTemproraries(URI value);

	/**
	 * Returns the symbolic name of this OSGi bundle. 
	 * @return the value of the '<em>Symbolic Name</em>' attribute.
	 * @see #setSymbolicName(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_SymbolicName()
	 * @model required="true"
	 * @generated
	 */
	String getSymbolicName();

	/**
	 * Sets the symbolic name of this OSGi bundle. This value is set automatically inside the activate method, which is 
	 * implemented in {@link PepperModuleImpl} class. If you want to manipulate that method. make sure to set the 
	 * symbolic name and make sure, that it is set to the bundles symbolic name.
	 * 
	 * @param value the new value of the '<em>Symbolic Name</em>' attribute.
	 * @see #getSymbolicName()
	 * @generated
	 */
	void setSymbolicName(String value);

	/**
	 * Returns the value of the '<em><b>Persistence Connector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Connector</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Connector</em>' containment reference.
	 * @see #setPersistenceConnector(PersistenceConnector)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_PersistenceConnector()
	 * @model containment="true"
	 * @generated
	 */
	PersistenceConnector getPersistenceConnector();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getPersistenceConnector <em>Persistence Connector</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Connector</em>' containment reference.
	 * @see #getPersistenceConnector()
	 * @generated
	 */
	void setPersistenceConnector(PersistenceConnector value);

	/**
	 * Returns the value of the '<em><b>Special Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Special Params</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Special Params</em>' attribute.
	 * @see #setSpecialParams(URI)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_SpecialParams()
	 * @model dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.URI"
	 * @generated
	 * @deprecated
	 */
	URI getSpecialParams();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSpecialParams <em>Special Params</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Special Params</em>' attribute.
	 * @see #getSpecialParams()
	 * @generated
	 * @deprecated
	 */
	void setSpecialParams(URI value);
	
	/**
	 * This method is called by the pepper framework after initializing this object and directly before start processing. 
	 * Initializing means setting properties {@link PepperModuleProperties}, setting temprorary files, resources etc. .
	 * returns false or throws an exception in case of {@link PepperModule} instance is not ready for any reason
	 * @return false, {@link PepperModule} instance is not ready for any reason, true, else.
	 */
	public boolean isReadyToStart() throws PepperModuleNotReadyException;
	/**
	 * Starts the conversion process. This method is the main method of a pepper module.
	 * @model exceptions="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleException"
	 * @generated
	 */
	void start() throws PepperModuleException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleException" sElementIdDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SElementId"
	 * @generated
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
	 * 
	 * @param sElementId {@link SElementId} of the {@link SCorpus} or {@link SDocument} to be processed. 
	 * @return {@link PepperMapper} object to do the mapping task for object connected to given {@link SElementId}
	 */
	public PepperMapper createPepperMapper(SElementId sElementId);

	/**
	 * This method is invoked by the Pepper framework, to get the current progress concerning the {@link SDocument} object
	 * corresponding to the given {@link SElementId} in percent. A valid value return must be between 0 and 1. This method can 
	 * be overridden by a derived {@link PepperModule} class. If this method is not overridden, it will return null. 
	 * @param sDocumentId identifier of the requested {@link SDocument} object.
	 * @model sDocumentIdDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SElementId"
	 * @generated
	 */
	Double getProgress(SElementId sDocumentId);
	
	/**
	 * This method is invoked by the Pepper framework, to get the current total progress of all {@link SDocument} objects
	 * being processed by this module. A valid value return must be between 0 and 1. This method can 
	 * be overridden by a derived {@link PepperModule} class. If this method is not overridden, it will return null. 
	 */
	Double getProgress();
	
	/**
	 * This method is called by the pepper framework at the end of a conversion process. Means after all objects (
	 * {@link SDocument} and {@link SCorpus} objects) have been processed. This method can be used to do some clean up
	 * (e.g. to close streams etc.). 
	 * @model exceptions="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleException"
	 * @generated
	 */
	void end() throws PepperModuleException;

	/**
	 * Sets the OSGi logging service.
	 * Note, this method only should be called by pepper framework.
	 * @model logServiceDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.LogService"
	 * @generated
	 */
	void setLogService(LogService logService);

	/**
	 * Unsets the OSGi logging service.
	 * Note, this method only should be called by pepper framework.
	 * @model logServiceDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.LogService"
	 * @generated
	 */
	void unsetLogService(LogService logService);

	/**
	 * Returns an OSGi logging service, to log events during the processing. OSGi provides several log levels like:
	 * {@link LogService#LOG_ERROR}, {@link LogService#LOG_WARNING}, {@link LogService#LOG_INFO} and 
	 * {@link LogService#LOG_DEBUG}.
	 * @model kind="operation" dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.LogService"
	 * @generated
	 */
	LogService getLogService();

} // PepperModule

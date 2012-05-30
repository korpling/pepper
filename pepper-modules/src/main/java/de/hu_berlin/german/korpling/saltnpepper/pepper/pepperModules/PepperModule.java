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
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
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
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_Name()
	 * @model required="true" changeable="false"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Pepper Module Controller</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController#getPepperModule <em>Pepper Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Module Controller</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Module Controller</em>' container reference.
	 * @see #setPepperModuleController(PepperModuleController)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_PepperModuleController()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController#getPepperModule
	 * @model opposite="pepperModule" transient="false"
	 * @generated
	 */
	PepperModuleController getPepperModuleController();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getPepperModuleController <em>Pepper Module Controller</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Module Controller</em>' container reference.
	 * @see #getPepperModuleController()
	 * @generated
	 */
	void setPepperModuleController(PepperModuleController value);

	/**
	 * Returns the value of the '<em><b>Salt Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Salt Project</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Salt Project</em>' attribute.
	 * @see #setSaltProject(SaltProject)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_SaltProject()
	 * @model dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SaltProject" required="true"
	 * @generated
	 */
	SaltProject getSaltProject();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSaltProject <em>Salt Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Salt Project</em>' attribute.
	 * @see #getSaltProject()
	 * @generated
	 */
	void setSaltProject(SaltProject value);

	/**
	 * Returns the value of the '<em><b>Returning Mode</b></em>' attribute.
	 * The default value is <code>"PUT"</code>.
	 * The literals are from the enumeration {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Returning Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Returning Mode</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_ReturningMode()
	 * @model default="PUT" changeable="false"
	 * @generated
	 */
	RETURNING_MODE getReturningMode();

	/**
	 * Returns the value of the '<em><b>SCorpus Graph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>SCorpus Graph</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>SCorpus Graph</em>' attribute.
	 * @see #setSCorpusGraph(SCorpusGraph)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_SCorpusGraph()
	 * @model dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SCorpusGraph" required="true"
	 * @generated
	 */
	SCorpusGraph getSCorpusGraph();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSCorpusGraph <em>SCorpus Graph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>SCorpus Graph</em>' attribute.
	 * @see #getSCorpusGraph()
	 * @generated
	 */
	void setSCorpusGraph(SCorpusGraph value);

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
	 * Returns the value of the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbolic Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbolic Name</em>' attribute.
	 * @see #setSymbolicName(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_SymbolicName()
	 * @model required="true"
	 * @generated
	 */
	String getSymbolicName();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSymbolicName <em>Symbolic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 */
	URI getSpecialParams();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSpecialParams <em>Special Params</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Special Params</em>' attribute.
	 * @see #getSpecialParams()
	 * @generated
	 */
	void setSpecialParams(URI value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModule_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleException"
	 * @generated
	 */
	void end() throws PepperModuleException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model logServiceDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.LogService"
	 * @generated
	 */
	void setLogService(LogService logService);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model logServiceDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.LogService"
	 * @generated
	 */
	void unsetLogService(LogService logService);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.LogService"
	 * @generated
	 */
	LogService getLogService();

	/**
	 * This method is invoked by the Pepper framework, to get the current progress concerning the {@link SDocument} object
	 * corresponding to the given {@link SElementId} in percent. A valid value return must be between 0 and 1. This method can 
	 * be overridden by a derived {@link PepperModule} class. If this method is not overridden, it will return null. 
	 * @param sDocumentId identifier of the requested {@link SDocument} object.
	 * @model sDocumentIdDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SElementId"
	 * @generated
	 */
	Double getProgress(SElementId sDocumentId);

} // PepperModule

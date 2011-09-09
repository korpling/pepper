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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW;

import org.eclipse.emf.common.util.EList;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;

import java.util.Properties;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pepper Convert Job</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperImporters <em>Pepper Importers</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperModules <em>Pepper Modules</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperExporters <em>Pepper Exporters</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getId <em>Id</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperModuleControllers <em>Pepper Module Controllers</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperM2MMonitors <em>Pepper M2M Monitors</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperM2JMonitors <em>Pepper M2J Monitors</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getSaltProject <em>Salt Project</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperJ2CMonitor <em>Pepper J2C Monitor</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperJobLogger <em>Pepper Job Logger</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperDocumentController <em>Pepper Document Controller</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob()
 * @model
 * @generated
 */
public interface PepperJob extends de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable 
{
	
	/**
	 * For logging with OSGi
	 * @param logService
	 */
	public void setLogService(LogService logService);
	
	/**
	 * For logging with OSGi
	 * @param logService
	 */
	public LogService getLogService();
	
	
	/**
	 * Returns the value of the '<em><b>Pepper Importers</b></em>' attribute list.
	 * The list contents are of type {@link de.hub.corpling.pepper.pepperInterface.PepperImporter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Importers</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Importers</em>' attribute list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_PepperImporters()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.PepperImporter" required="true"
	 * @generated
	 */
	EList<PepperImporter> getPepperImporters();

	/**
	 * Returns the value of the '<em><b>Pepper Modules</b></em>' attribute list.
	 * The list contents are of type {@link de.hub.corpling.pepper.pepperInterface.PepperModule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Modules</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Modules</em>' attribute list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_PepperModules()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.PepperModule"
	 * @generated
	 */
	EList<PepperModule> getPepperModules();

	/**
	 * Returns the value of the '<em><b>Pepper Exporters</b></em>' attribute list.
	 * The list contents are of type {@link de.hub.corpling.pepper.pepperInterface.PepperExporter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Exporters</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Exporters</em>' attribute list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_PepperExporters()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.PepperExporter" required="true"
	 * @generated
	 */
	EList<PepperExporter> getPepperExporters();

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(Integer)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_Id()
	 * @model required="true"
	 * @generated
	 */
	Integer getId();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(Integer value);

	/**
	 * Returns the value of the '<em><b>Pepper Module Controllers</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController}.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperJob <em>Pepper Job</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Module Controllers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Module Controllers</em>' containment reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_PepperModuleControllers()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperJob
	 * @model opposite="pepperJob" containment="true"
	 * @generated
	 */
	EList<PepperModuleController> getPepperModuleControllers();

	/**
	 * Returns the value of the '<em><b>Pepper M2M Monitors</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper M2M Monitors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper M2M Monitors</em>' containment reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_PepperM2MMonitors()
	 * @model containment="true"
	 * @generated
	 */
	EList<PepperQueuedMonitor> getPepperM2MMonitors();

	/**
	 * Returns the value of the '<em><b>Pepper M2J Monitors</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper M2J Monitors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper M2J Monitors</em>' containment reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_PepperM2JMonitors()
	 * @model containment="true"
	 * @generated
	 */
	EList<PepperFinishableMonitor> getPepperM2JMonitors();

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
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_SaltProject()
	 * @model dataType="de.hub.corpling.pepper.pepperInterface.SaltProject" required="true"
	 * @generated
	 */
	SaltProject getSaltProject();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getSaltProject <em>Salt Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Salt Project</em>' attribute.
	 * @see #getSaltProject()
	 * @generated
	 */
	void setSaltProject(SaltProject value);

	/**
	 * Returns the value of the '<em><b>Pepper J2C Monitor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper J2C Monitor</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper J2C Monitor</em>' reference.
	 * @see #setPepperJ2CMonitor(PepperFinishableMonitor)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_PepperJ2CMonitor()
	 * @model
	 * @generated
	 */
	PepperFinishableMonitor getPepperJ2CMonitor();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperJ2CMonitor <em>Pepper J2C Monitor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper J2C Monitor</em>' reference.
	 * @see #getPepperJ2CMonitor()
	 * @generated
	 */
	void setPepperJ2CMonitor(PepperFinishableMonitor value);

	/**
	 * Returns the value of the '<em><b>Pepper Job Logger</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger#getPepperJob <em>Pepper Job</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Job Logger</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Job Logger</em>' reference.
	 * @see #setPepperJobLogger(PepperJobLogger)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_PepperJobLogger()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger#getPepperJob
	 * @model opposite="pepperJob"
	 * @generated
	 */
	PepperJobLogger getPepperJobLogger();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperJobLogger <em>Pepper Job Logger</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Job Logger</em>' reference.
	 * @see #getPepperJobLogger()
	 * @generated
	 */
	void setPepperJobLogger(PepperJobLogger value);

	/**
	 * Returns the value of the '<em><b>Pepper Document Controller</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperJobController <em>Pepper Job Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Document Controller</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Document Controller</em>' containment reference.
	 * @see #setPepperDocumentController(PepperDocumentController)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_PepperDocumentController()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperJobController
	 * @model opposite="pepperJobController" containment="true"
	 * @generated
	 */
	PepperDocumentController getPepperDocumentController();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperDocumentController <em>Pepper Document Controller</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Document Controller</em>' containment reference.
	 * @see #getPepperDocumentController()
	 * @generated
	 */
	void setPepperDocumentController(PepperDocumentController value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' attribute.
	 * @see #setProperties(Properties)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJob_Properties()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.Properties"
	 * @generated
	 */
	Properties getProperties();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getProperties <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' attribute.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(Properties value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="de.hub.corpling.pepper.pepperFW.PepperModuleException de.hub.corpling.pepper.pepperFW.PepperConvertException"
	 * @generated
	 */
	void start() throws PepperModuleException, PepperConvertException;

} // PepperConvertJob

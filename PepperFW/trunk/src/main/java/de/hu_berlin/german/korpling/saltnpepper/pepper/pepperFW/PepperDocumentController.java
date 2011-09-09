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

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * The PepperDocumentController observes the lifetime of an SDocumentObject. If the SDocument-object was
 * processed by all modules, its SDocumentGraph-object will be removed. This is done because of
 * main memory size. If you set the Flag REMOVE_SDOCUMENT_AFTER_PROCESSING to false, the SDOcumentGraph-objects 
 * will not be removed. 
 * 
 * Flags:
 * <ul>
 *  <li>REMOVE_SDOCUMENT_AFTER_PROCESSING - removes a document if all PepperModules finished working on it, REMOVE_SDOCUMENT_AFTER_PROCESSING will be setted or remain false, if AMOUNT_OF_COMPUTABLE_SDOCUMENTS is higher or equal zero</li>
 *  <li>COMPUTE_PERFORMANCE - stores performance information while processing and logs them after finish, the agragated performance information will be logged if the last document was processed</li>
 *  <li>AMOUNT_OF_COMPUTABLE_SDOCUMENTS - the maximal number of SDocument-objects which can be stored in a salt project in the same time. A value under zero means an infinite number of documents. If this value is more or equal zero, the flag REMOVE_SDOCUMENT_AFTER_PROCESSING is set to true automatically.</li>
 * </ul>
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperModuleControllers <em>Pepper Module Controllers</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperJobController <em>Pepper Job Controller</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperDocumentController()
 * @model
 * @generated
 */
public interface PepperDocumentController extends EObject {
	/**
	 * Returns the value of the '<em><b>Pepper Module Controllers</b></em>' reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController}.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperDocumentController <em>Pepper Document Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Module Controllers</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Module Controllers</em>' reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperDocumentController_PepperModuleControllers()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperDocumentController
	 * @model opposite="pepperDocumentController"
	 * @generated
	 */
	EList<PepperModuleController> getPepperModuleControllers();

	/**
	 * Returns the value of the '<em><b>Pepper Job Controller</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperDocumentController <em>Pepper Document Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Job Controller</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Job Controller</em>' container reference.
	 * @see #setPepperJobController(PepperJob)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperDocumentController_PepperJobController()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperDocumentController
	 * @model opposite="pepperDocumentController" transient="false"
	 * @generated
	 */
	PepperJob getPepperJobController();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperJobController <em>Pepper Job Controller</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Job Controller</em>' container reference.
	 * @see #getPepperJobController()
	 * @generated
	 */
	void setPepperJobController(PepperJob value);

	/**
	 * Returns the value of the '<em><b>REMOVE SDOCUMENT AFTER PROCESSING</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>REMOVE SDOCUMENT AFTER PROCESSING</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>REMOVE SDOCUMENT AFTER PROCESSING</em>' attribute.
	 * @see #setREMOVE_SDOCUMENT_AFTER_PROCESSING(Boolean)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperDocumentController_REMOVE_SDOCUMENT_AFTER_PROCESSING()
	 * @model default="true"
	 * @generated
	 */
	Boolean getREMOVE_SDOCUMENT_AFTER_PROCESSING();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getREMOVE_SDOCUMENT_AFTER_PROCESSING <em>REMOVE SDOCUMENT AFTER PROCESSING</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>REMOVE SDOCUMENT AFTER PROCESSING</em>' attribute.
	 * @see #getREMOVE_SDOCUMENT_AFTER_PROCESSING()
	 * @generated
	 */
	void setREMOVE_SDOCUMENT_AFTER_PROCESSING(Boolean value);

	/**
	 * Returns the value of the '<em><b>COMPUTE PERFORMANCE</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>COMPUTE PERFORMANCE</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>COMPUTE PERFORMANCE</em>' attribute.
	 * @see #setCOMPUTE_PERFORMANCE(Boolean)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperDocumentController_COMPUTE_PERFORMANCE()
	 * @model default="true"
	 * @generated
	 */
	Boolean getCOMPUTE_PERFORMANCE();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getCOMPUTE_PERFORMANCE <em>COMPUTE PERFORMANCE</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>COMPUTE PERFORMANCE</em>' attribute.
	 * @see #getCOMPUTE_PERFORMANCE()
	 * @generated
	 */
	void setCOMPUTE_PERFORMANCE(Boolean value);

	/**
	 * Returns the value of the '<em><b>AMOUNT OF COMPUTABLE SDOCUMENTS</b></em>' attribute.
	 * The default value is <code>"10"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>AMOUNT OF COMPUTABLE SDOCUMENTS</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>AMOUNT OF COMPUTABLE SDOCUMENTS</em>' attribute.
	 * @see #setAMOUNT_OF_COMPUTABLE_SDOCUMENTS(Integer)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperDocumentController_AMOUNT_OF_COMPUTABLE_SDOCUMENTS()
	 * @model default="10" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	Integer getAMOUNT_OF_COMPUTABLE_SDOCUMENTS();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getAMOUNT_OF_COMPUTABLE_SDOCUMENTS <em>AMOUNT OF COMPUTABLE SDOCUMENTS</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>AMOUNT OF COMPUTABLE SDOCUMENTS</em>' attribute.
	 * @see #getAMOUNT_OF_COMPUTABLE_SDOCUMENTS()
	 * @generated
	 */
	void setAMOUNT_OF_COMPUTABLE_SDOCUMENTS(Integer value);

	/**
	 * Returns the value of the '<em><b>Current Amount Of SDocuments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Amount Of SDocuments</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Amount Of SDocuments</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperDocumentController_CurrentAmountOfSDocuments()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Integer getCurrentAmountOfSDocuments();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model sDocumentIdDataType="de.hub.corpling.pepper.pepperInterface.SElementId"
	 * @generated
	 */
	void observeSDocument(SElementId sDocumentId);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="de.hub.corpling.pepper.pepperInterface.SElementId"
	 * @generated
	 */
	EList<SElementId> getObservedSDocuments();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model sDocumentIdDataType="de.hub.corpling.pepper.pepperInterface.SElementId"
	 * @generated
	 */
	void setSDocumentStatus(SElementId sDocumentId, PepperModuleController moduleController, PEPPER_SDOCUMENT_STATUS status);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getStatus4Print();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model sDocumentIdDataType="de.hub.corpling.pepper.pepperInterface.SElementId"
	 * @generated
	 */
	PEPPER_SDOCUMENT_STATUS getStatus(SElementId sDocumentId);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model sDocumentIdDataType="de.hub.corpling.pepper.pepperInterface.SElementId"
	 * @generated
	 */
	PEPPER_SDOCUMENT_STATUS getStatus(SElementId sDocumentId, PepperModuleController pepperModuleController);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void finish();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model logServiceDataType="de.hub.corpling.pepper.pepperFW.LogService"
	 * @generated
	 */
	void setLogService(LogService logService);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="de.hub.corpling.pepper.pepperFW.LogService"
	 * @generated
	 */
	LogService getLogService();

	/**
	 * This method shall be called by PepperModuleController, if it controls a PepperImportModule.
	 * This method regulates the maximal number of current processed SDocument-objects. This function
	 * is necessary if some PepperModules have different processing times, especially if an importer is 
	 * much faster than the other modules. In this case too much memory will be allocated and a memory-exception
	 * occurs. Therefore this methods regulates the maximal amount of documents. 
	 * @model
	 * @generated
	 */
	void waitForSDocument();

} // PepperDocumentController

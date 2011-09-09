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

import org.eclipse.emf.ecore.EObject;

import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pepper Job Logger</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger#getPepperJob <em>Pepper Job</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJobLogger()
 * @model
 * @generated
 */
public interface PepperJobLogger extends EObject {
	/**
	 * Returns the value of the '<em><b>Pepper Job</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperJobLogger <em>Pepper Job Logger</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Job</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Job</em>' reference.
	 * @see #setPepperJob(PepperJob)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperJobLogger_PepperJob()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperJobLogger
	 * @model opposite="pepperJobLogger"
	 * @generated
	 */
	PepperJob getPepperJob();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger#getPepperJob <em>Pepper Job</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Job</em>' reference.
	 * @see #getPepperJob()
	 * @generated
	 */
	void setPepperJob(PepperJob value);

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model sElementIdDataType="de.hub.corpling.pepper.pepperInterface.SElementId"
	 * @generated
	 */
	void logStatus(SElementId sElementId, PEPPER_SDOCUMENT_STATUS status, String moduleName);

} // PepperJobLogger

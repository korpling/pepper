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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pepper Module Controller</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperJob <em>Pepper Job</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getInputPepperModuleMonitors <em>Input Pepper Module Monitors</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getOutputPepperModuleMonitors <em>Output Pepper Module Monitors</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperM2JMonitor <em>Pepper M2J Monitor</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperJobLogger <em>Pepper Job Logger</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperDocumentController <em>Pepper Document Controller</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleController()
 * @model
 * @generated
 */
public interface PepperModuleController extends de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable {

	/**
	 * Returns the value of the '<em><b>Pepper Job</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperModuleControllers <em>Pepper Module Controllers</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Job</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Job</em>' container reference.
	 * @see #setPepperJob(PepperJob)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleController_PepperJob()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperModuleControllers
	 * @model opposite="pepperModuleControllers" transient="false"
	 * @generated
	 */
	PepperJob getPepperJob();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperJob <em>Pepper Job</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Job</em>' container reference.
	 * @see #getPepperJob()
	 * @generated
	 */
	void setPepperJob(PepperJob value);

	/**
	 * Returns the value of the '<em><b>Input Pepper Module Monitors</b></em>' reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input Pepper Module Monitors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Pepper Module Monitors</em>' reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleController_InputPepperModuleMonitors()
	 * @model
	 * @generated
	 */
	EList<PepperQueuedMonitor> getInputPepperModuleMonitors();

	/**
	 * Returns the value of the '<em><b>Output Pepper Module Monitors</b></em>' reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Pepper Module Monitors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Pepper Module Monitors</em>' reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleController_OutputPepperModuleMonitors()
	 * @model
	 * @generated
	 */
	EList<PepperQueuedMonitor> getOutputPepperModuleMonitors();

	/**
	 * Returns the value of the '<em><b>Pepper M2J Monitor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper M2J Monitor</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper M2J Monitor</em>' reference.
	 * @see #setPepperM2JMonitor(PepperFinishableMonitor)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleController_PepperM2JMonitor()
	 * @model
	 * @generated
	 */
	PepperFinishableMonitor getPepperM2JMonitor();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperM2JMonitor <em>Pepper M2J Monitor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper M2J Monitor</em>' reference.
	 * @see #getPepperM2JMonitor()
	 * @generated
	 */
	void setPepperM2JMonitor(PepperFinishableMonitor value);

	/**
	 * Returns the value of the '<em><b>Pepper Job Logger</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Job Logger</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Job Logger</em>' reference.
	 * @see #setPepperJobLogger(PepperJobLogger)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleController_PepperJobLogger()
	 * @model
	 * @generated
	 */
	PepperJobLogger getPepperJobLogger();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperJobLogger <em>Pepper Job Logger</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Job Logger</em>' reference.
	 * @see #getPepperJobLogger()
	 * @generated
	 */
	void setPepperJobLogger(PepperJobLogger value);

	/**
	 * Returns the value of the '<em><b>Pepper Document Controller</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperModuleControllers <em>Pepper Module Controllers</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Document Controller</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Document Controller</em>' reference.
	 * @see #setPepperDocumentController(PepperDocumentController)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleController_PepperDocumentController()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperModuleControllers
	 * @model opposite="pepperModuleControllers"
	 * @generated
	 */
	PepperDocumentController getPepperDocumentController();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperDocumentController <em>Pepper Document Controller</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Document Controller</em>' reference.
	 * @see #getPepperDocumentController()
	 * @generated
	 */
	void setPepperDocumentController(PepperDocumentController value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="de.hub.corpling.pepper.pepperFW.PepperModuleException"
	 * @generated
	 */
	void start() throws PepperModuleException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="de.hub.corpling.pepper.pepperFW.PepperConvertException" sCorpusGraphDataType="de.hub.corpling.pepper.pepperInterface.SCorpusGraph"
	 * @generated
	 */
	void importCorpusStructure(SCorpusGraph sCorpusGraph) throws PepperConvertException;
	
	/**
	 * This method is invoked by the Pepper framework, to get the current progress concerning the {@link SDocument} object
	 * corresponding to the given {@link SElementId} in percent. A valid value return must be between 0 and 1. This method can 
	 * be overridden by a derived {@link PepperModule} class. If this method is not overridden, it will return null. 
	 * @param sDocumentId identifier of the requested {@link SDocument} object.
	 */
	//TODO this method must be also specified in Pepper EMF model
	public Double getProgress(SElementId sDocumentId);
} // PepperModuleController

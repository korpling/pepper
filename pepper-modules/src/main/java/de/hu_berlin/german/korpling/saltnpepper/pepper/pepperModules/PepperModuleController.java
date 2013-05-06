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

import org.eclipse.emf.ecore.EObject;

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
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController#getPepperModule <em>Pepper Module</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModuleController()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface PepperModuleController extends EObject {
	/**
	 * Returns the value of the '<em><b>Pepper Module</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getPepperModuleController <em>Pepper Module Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Module</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Module</em>' containment reference.
	 * @see #setPepperModule(PepperModule)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperModuleController_PepperModule()
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getPepperModuleController
	 * @model opposite="pepperModuleController" containment="true"
	 * @generated
	 */
	PepperModule getPepperModule();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController#getPepperModule <em>Pepper Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Module</em>' containment reference.
	 * @see #getPepperModule()
	 * @generated
	 */
	void setPepperModule(PepperModule value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model sElementIdDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SElementId"
	 * @generated
	 */
	void put(SElementId sElementId);

	/**
	 * Returns an {@link SElementId} of a {@link SDocument} which can be processed next. If no {@link SDocument} object
	 * processed by a preceding module is available, the {@link #get()} method waits until one gets available and first
	 * returns than. 
	 * TODO This method also checks if the number of current mapper threads is not higher the maximum number of mapper threads for this module. See issue #257.  
	 * @model dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SElementId"
	 * @generated
	 */
	SElementId get();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model sElementIdDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SElementId"
	 * @generated
	 */
	void finish(SElementId sElementId);
} // PepperModuleController

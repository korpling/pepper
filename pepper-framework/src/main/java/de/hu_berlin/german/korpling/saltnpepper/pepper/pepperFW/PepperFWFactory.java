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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfaceFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage
 */
public interface PepperFWFactory extends PepperInterfaceFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PepperFWFactory eINSTANCE = de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Pepper Converter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Converter</em>'.
	 * @generated
	 */
	PepperConverter createPepperConverter();

	/**
	 * Returns a new object of class '<em>Pepper Module Resolver</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Module Resolver</em>'.
	 * @generated
	 */
	PepperModuleResolver createPepperModuleResolver();

	/**
	 * Returns a new object of class '<em>Pepper Job</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Job</em>'.
	 * @generated
	 */
	PepperJob createPepperJob();

	/**
	 * Returns a new object of class '<em>Pepper Module Controller</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Module Controller</em>'.
	 * @generated
	 */
	PepperModuleController createPepperModuleController();

	/**
	 * Returns a new object of class '<em>Pepper Queued Monitor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Queued Monitor</em>'.
	 * @generated
	 */
	PepperQueuedMonitor createPepperQueuedMonitor();

	/**
	 * Returns a new object of class '<em>Pepper Finishable Monitor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Finishable Monitor</em>'.
	 * @generated
	 */
	PepperFinishableMonitor createPepperFinishableMonitor();

	/**
	 * Returns a new object of class '<em>Pepper Monitor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Monitor</em>'.
	 * @generated
	 */
	PepperMonitor createPepperMonitor();

	/**
	 * Returns a new object of class '<em>Pepper Job Logger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Job Logger</em>'.
	 * @generated
	 */
	PepperJobLogger createPepperJobLogger();

	/**
	 * Returns a new object of class '<em>Pepper Document Controller</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Document Controller</em>'.
	 * @generated
	 */
	PepperDocumentController createPepperDocumentController();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PepperFWPackage getPepperFWPackage();

} //PepperFWFactory

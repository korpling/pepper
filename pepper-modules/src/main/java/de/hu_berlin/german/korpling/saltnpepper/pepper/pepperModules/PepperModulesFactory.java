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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage
 * @generated
 */
public interface PepperModulesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PepperModulesFactory eINSTANCE = de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Format Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Format Definition</em>'.
	 * @generated
	 */
	FormatDefinition createFormatDefinition();

	/**
	 * Returns a new object of class '<em>Corpus Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Corpus Definition</em>'.
	 * @generated
	 */
	CorpusDefinition createCorpusDefinition();

	/**
	 * Returns a new object of class '<em>Extension Factory Pair</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extension Factory Pair</em>'.
	 * @generated
	 */
	ExtensionFactoryPair createExtensionFactoryPair();

	/**
	 * Returns a new object of class '<em>Persistence Connector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Persistence Connector</em>'.
	 * @generated
	 */
	PersistenceConnector createPersistenceConnector();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PepperModulesPackage getPepperModulesPackage();

} //PepperModulesFactory

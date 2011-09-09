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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage
 * @generated
 */
public interface PepperParamsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PepperParamsFactory eINSTANCE = de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Module Params</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Module Params</em>'.
	 * @generated
	 */
	ModuleParams createModuleParams();

	/**
	 * Returns a new object of class '<em>Importer Params</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Importer Params</em>'.
	 * @generated
	 */
	ImporterParams createImporterParams();

	/**
	 * Returns a new object of class '<em>Pepper Params</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Params</em>'.
	 * @generated
	 */
	PepperParams createPepperParams();

	/**
	 * Returns a new object of class '<em>Pepper Job Params</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Job Params</em>'.
	 * @generated
	 */
	PepperJobParams createPepperJobParams();

	/**
	 * Returns a new object of class '<em>Exporter Params</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exporter Params</em>'.
	 * @generated
	 */
	ExporterParams createExporterParams();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PepperParamsPackage getPepperParamsPackage();

} //PepperParamsFactory

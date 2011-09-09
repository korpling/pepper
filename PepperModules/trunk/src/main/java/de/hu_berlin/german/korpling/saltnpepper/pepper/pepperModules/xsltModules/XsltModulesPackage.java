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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesFactory
 * @model kind="package"
 * @generated
 */
public interface XsltModulesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "xsltModules";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "xsltModules";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "xsltModules";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	XsltModulesPackage eINSTANCE = de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.XsltModulesPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.PepperXSLTExporterImpl <em>Pepper XSLT Exporter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.PepperXSLTExporterImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.XsltModulesPackageImpl#getPepperXSLTExporter()
	 * @generated
	 */
	int PEPPER_XSLT_EXPORTER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__NAME = PepperInterfacePackage.PEPPER_EXPORTER__NAME;

	/**
	 * The feature id for the '<em><b>Pepper Module Controller</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__PEPPER_MODULE_CONTROLLER = PepperInterfacePackage.PEPPER_EXPORTER__PEPPER_MODULE_CONTROLLER;

	/**
	 * The feature id for the '<em><b>Salt Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__SALT_PROJECT = PepperInterfacePackage.PEPPER_EXPORTER__SALT_PROJECT;

	/**
	 * The feature id for the '<em><b>Returning Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__RETURNING_MODE = PepperInterfacePackage.PEPPER_EXPORTER__RETURNING_MODE;

	/**
	 * The feature id for the '<em><b>SCorpus Graph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__SCORPUS_GRAPH = PepperInterfacePackage.PEPPER_EXPORTER__SCORPUS_GRAPH;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__RESOURCES = PepperInterfacePackage.PEPPER_EXPORTER__RESOURCES;

	/**
	 * The feature id for the '<em><b>Temproraries</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__TEMPRORARIES = PepperInterfacePackage.PEPPER_EXPORTER__TEMPRORARIES;

	/**
	 * The feature id for the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__SYMBOLIC_NAME = PepperInterfacePackage.PEPPER_EXPORTER__SYMBOLIC_NAME;

	/**
	 * The feature id for the '<em><b>Persistence Connector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__PERSISTENCE_CONNECTOR = PepperInterfacePackage.PEPPER_EXPORTER__PERSISTENCE_CONNECTOR;

	/**
	 * The feature id for the '<em><b>Special Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__SPECIAL_PARAMS = PepperInterfacePackage.PEPPER_EXPORTER__SPECIAL_PARAMS;

	/**
	 * The feature id for the '<em><b>Supported Formats</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__SUPPORTED_FORMATS = PepperInterfacePackage.PEPPER_EXPORTER__SUPPORTED_FORMATS;

	/**
	 * The feature id for the '<em><b>Corpus Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__CORPUS_DEFINITION = PepperInterfacePackage.PEPPER_EXPORTER__CORPUS_DEFINITION;

	/**
	 * The feature id for the '<em><b>Xslt Transformer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER__XSLT_TRANSFORMER = PepperInterfacePackage.PEPPER_EXPORTER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Pepper XSLT Exporter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_XSLT_EXPORTER_FEATURE_COUNT = PepperInterfacePackage.PEPPER_EXPORTER_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.XSLTTransformerImpl <em>XSLT Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.XSLTTransformerImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.XsltModulesPackageImpl#getXSLTTransformer()
	 * @generated
	 */
	int XSLT_TRANSFORMER = 1;

	/**
	 * The feature id for the '<em><b>Extension Factory Pairs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_TRANSFORMER__EXTENSION_FACTORY_PAIRS = PepperInterfacePackage.PERSISTENCE_CONNECTOR__EXTENSION_FACTORY_PAIRS;

	/**
	 * The number of structural features of the '<em>XSLT Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_TRANSFORMER_FEATURE_COUNT = PepperInterfacePackage.PERSISTENCE_CONNECTOR_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.PepperXSLTExporter <em>Pepper XSLT Exporter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper XSLT Exporter</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.PepperXSLTExporter
	 * @generated
	 */
	EClass getPepperXSLTExporter();

	/**
	 * Returns the meta object for the containment reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.PepperXSLTExporter#getXsltTransformer <em>Xslt Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xslt Transformer</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.PepperXSLTExporter#getXsltTransformer()
	 * @see #getPepperXSLTExporter()
	 * @generated
	 */
	EReference getPepperXSLTExporter_XsltTransformer();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XSLTTransformer <em>XSLT Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>XSLT Transformer</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XSLTTransformer
	 * @generated
	 */
	EClass getXSLTTransformer();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	XsltModulesFactory getXsltModulesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.PepperXSLTExporterImpl <em>Pepper XSLT Exporter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.PepperXSLTExporterImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.XsltModulesPackageImpl#getPepperXSLTExporter()
		 * @generated
		 */
		EClass PEPPER_XSLT_EXPORTER = eINSTANCE.getPepperXSLTExporter();
		/**
		 * The meta object literal for the '<em><b>Xslt Transformer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_XSLT_EXPORTER__XSLT_TRANSFORMER = eINSTANCE.getPepperXSLTExporter_XsltTransformer();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.XSLTTransformerImpl <em>XSLT Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.XSLTTransformerImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.XsltModulesPackageImpl#getXSLTTransformer()
		 * @generated
		 */
		EClass XSLT_TRANSFORMER = eINSTANCE.getXSLTTransformer();

	}

} //XsltModulesPackage

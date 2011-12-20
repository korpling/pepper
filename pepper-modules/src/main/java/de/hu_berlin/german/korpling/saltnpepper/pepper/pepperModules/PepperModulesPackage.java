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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
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
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesFactory
 * @model kind="package"
 * @generated
 */
public interface PepperModulesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "pepperModules";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "pepperModules";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PepperModulesPackage eINSTANCE = de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl <em>Pepper Module</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperModule()
	 * @generated
	 */
	int PEPPER_MODULE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Pepper Module Controller</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE__PEPPER_MODULE_CONTROLLER = 1;

	/**
	 * The feature id for the '<em><b>Salt Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE__SALT_PROJECT = 2;

	/**
	 * The feature id for the '<em><b>Returning Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE__RETURNING_MODE = 3;

	/**
	 * The feature id for the '<em><b>SCorpus Graph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE__SCORPUS_GRAPH = 4;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE__RESOURCES = 5;

	/**
	 * The feature id for the '<em><b>Temproraries</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE__TEMPRORARIES = 6;

	/**
	 * The feature id for the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE__SYMBOLIC_NAME = 7;

	/**
	 * The feature id for the '<em><b>Persistence Connector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE__PERSISTENCE_CONNECTOR = 8;

	/**
	 * The feature id for the '<em><b>Special Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE__SPECIAL_PARAMS = 9;

	/**
	 * The number of structural features of the '<em>Pepper Module</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl <em>Pepper Importer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperImporter()
	 * @generated
	 */
	int PEPPER_IMPORTER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__NAME = PEPPER_MODULE__NAME;

	/**
	 * The feature id for the '<em><b>Pepper Module Controller</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__PEPPER_MODULE_CONTROLLER = PEPPER_MODULE__PEPPER_MODULE_CONTROLLER;

	/**
	 * The feature id for the '<em><b>Salt Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__SALT_PROJECT = PEPPER_MODULE__SALT_PROJECT;

	/**
	 * The feature id for the '<em><b>Returning Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__RETURNING_MODE = PEPPER_MODULE__RETURNING_MODE;

	/**
	 * The feature id for the '<em><b>SCorpus Graph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__SCORPUS_GRAPH = PEPPER_MODULE__SCORPUS_GRAPH;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__RESOURCES = PEPPER_MODULE__RESOURCES;

	/**
	 * The feature id for the '<em><b>Temproraries</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__TEMPRORARIES = PEPPER_MODULE__TEMPRORARIES;

	/**
	 * The feature id for the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__SYMBOLIC_NAME = PEPPER_MODULE__SYMBOLIC_NAME;

	/**
	 * The feature id for the '<em><b>Persistence Connector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__PERSISTENCE_CONNECTOR = PEPPER_MODULE__PERSISTENCE_CONNECTOR;

	/**
	 * The feature id for the '<em><b>Special Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__SPECIAL_PARAMS = PEPPER_MODULE__SPECIAL_PARAMS;

	/**
	 * The feature id for the '<em><b>Supported Formats</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__SUPPORTED_FORMATS = PEPPER_MODULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Corpus Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER__CORPUS_DEFINITION = PEPPER_MODULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Pepper Importer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_IMPORTER_FEATURE_COUNT = PEPPER_MODULE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl <em>Pepper Exporter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperExporter()
	 * @generated
	 */
	int PEPPER_EXPORTER = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__NAME = PEPPER_MODULE__NAME;

	/**
	 * The feature id for the '<em><b>Pepper Module Controller</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__PEPPER_MODULE_CONTROLLER = PEPPER_MODULE__PEPPER_MODULE_CONTROLLER;

	/**
	 * The feature id for the '<em><b>Salt Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__SALT_PROJECT = PEPPER_MODULE__SALT_PROJECT;

	/**
	 * The feature id for the '<em><b>Returning Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__RETURNING_MODE = PEPPER_MODULE__RETURNING_MODE;

	/**
	 * The feature id for the '<em><b>SCorpus Graph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__SCORPUS_GRAPH = PEPPER_MODULE__SCORPUS_GRAPH;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__RESOURCES = PEPPER_MODULE__RESOURCES;

	/**
	 * The feature id for the '<em><b>Temproraries</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__TEMPRORARIES = PEPPER_MODULE__TEMPRORARIES;

	/**
	 * The feature id for the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__SYMBOLIC_NAME = PEPPER_MODULE__SYMBOLIC_NAME;

	/**
	 * The feature id for the '<em><b>Persistence Connector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__PERSISTENCE_CONNECTOR = PEPPER_MODULE__PERSISTENCE_CONNECTOR;

	/**
	 * The feature id for the '<em><b>Special Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__SPECIAL_PARAMS = PEPPER_MODULE__SPECIAL_PARAMS;

	/**
	 * The feature id for the '<em><b>Supported Formats</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__SUPPORTED_FORMATS = PEPPER_MODULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Corpus Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER__CORPUS_DEFINITION = PEPPER_MODULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Pepper Exporter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_EXPORTER_FEATURE_COUNT = PEPPER_MODULE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.FormatDefinitionImpl <em>Format Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.FormatDefinitionImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getFormatDefinition()
	 * @generated
	 */
	int FORMAT_DEFINITION = 3;

	/**
	 * The feature id for the '<em><b>Format Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_DEFINITION__FORMAT_NAME = 0;

	/**
	 * The feature id for the '<em><b>Format Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_DEFINITION__FORMAT_VERSION = 1;

	/**
	 * The feature id for the '<em><b>Format Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_DEFINITION__FORMAT_REFERENCE = 2;

	/**
	 * The number of structural features of the '<em>Format Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_DEFINITION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.CorpusDefinitionImpl <em>Corpus Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.CorpusDefinitionImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getCorpusDefinition()
	 * @generated
	 */
	int CORPUS_DEFINITION = 4;

	/**
	 * The feature id for the '<em><b>Corpus Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORPUS_DEFINITION__CORPUS_PATH = 0;

	/**
	 * The feature id for the '<em><b>Format Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORPUS_DEFINITION__FORMAT_DEFINITION = 1;

	/**
	 * The number of structural features of the '<em>Corpus Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORPUS_DEFINITION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController <em>Pepper Module Controller</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperModuleController()
	 * @generated
	 */
	int PEPPER_MODULE_CONTROLLER = 5;

	/**
	 * The feature id for the '<em><b>Pepper Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_CONTROLLER__PEPPER_MODULE = 0;

	/**
	 * The number of structural features of the '<em>Pepper Module Controller</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_CONTROLLER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperManipulatorImpl <em>Pepper Manipulator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperManipulatorImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperManipulator()
	 * @generated
	 */
	int PEPPER_MANIPULATOR = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR__NAME = PEPPER_MODULE__NAME;

	/**
	 * The feature id for the '<em><b>Pepper Module Controller</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR__PEPPER_MODULE_CONTROLLER = PEPPER_MODULE__PEPPER_MODULE_CONTROLLER;

	/**
	 * The feature id for the '<em><b>Salt Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR__SALT_PROJECT = PEPPER_MODULE__SALT_PROJECT;

	/**
	 * The feature id for the '<em><b>Returning Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR__RETURNING_MODE = PEPPER_MODULE__RETURNING_MODE;

	/**
	 * The feature id for the '<em><b>SCorpus Graph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR__SCORPUS_GRAPH = PEPPER_MODULE__SCORPUS_GRAPH;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR__RESOURCES = PEPPER_MODULE__RESOURCES;

	/**
	 * The feature id for the '<em><b>Temproraries</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR__TEMPRORARIES = PEPPER_MODULE__TEMPRORARIES;

	/**
	 * The feature id for the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR__SYMBOLIC_NAME = PEPPER_MODULE__SYMBOLIC_NAME;

	/**
	 * The feature id for the '<em><b>Persistence Connector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR__PERSISTENCE_CONNECTOR = PEPPER_MODULE__PERSISTENCE_CONNECTOR;

	/**
	 * The feature id for the '<em><b>Special Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR__SPECIAL_PARAMS = PEPPER_MODULE__SPECIAL_PARAMS;

	/**
	 * The number of structural features of the '<em>Pepper Manipulator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MANIPULATOR_FEATURE_COUNT = PEPPER_MODULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.ExtensionFactoryPairImpl <em>Extension Factory Pair</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.ExtensionFactoryPairImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getExtensionFactoryPair()
	 * @generated
	 */
	int EXTENSION_FACTORY_PAIR = 7;

	/**
	 * The feature id for the '<em><b>File Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_FACTORY_PAIR__FILE_EXTENSION = 0;

	/**
	 * The feature id for the '<em><b>Resource Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_FACTORY_PAIR__RESOURCE_FACTORY = 1;

	/**
	 * The number of structural features of the '<em>Extension Factory Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_FACTORY_PAIR_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PersistenceConnectorImpl <em>Persistence Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PersistenceConnectorImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPersistenceConnector()
	 * @generated
	 */
	int PERSISTENCE_CONNECTOR = 8;

	/**
	 * The feature id for the '<em><b>Extension Factory Pairs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSISTENCE_CONNECTOR__EXTENSION_FACTORY_PAIRS = 0;

	/**
	 * The number of structural features of the '<em>Persistence Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSISTENCE_CONNECTOR_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE <em>RETURNING MODE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getRETURNING_MODE()
	 * @generated
	 */
	int RETURNING_MODE = 9;

	/**
	 * The meta object id for the '<em>URI</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.URI
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getURI()
	 * @generated
	 */
	int URI = 10;

	/**
	 * The meta object id for the '<em>SCorpus Graph</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getSCorpusGraph()
	 * @generated
	 */
	int SCORPUS_GRAPH = 11;

	/**
	 * The meta object id for the '<em>Salt Project</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getSaltProject()
	 * @generated
	 */
	int SALT_PROJECT = 12;

	/**
	 * The meta object id for the '<em>SElement Id</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getSElementId()
	 * @generated
	 */
	int SELEMENT_ID = 13;

	/**
	 * The meta object id for the '<em>Pepper Module Exception</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperModuleException()
	 * @generated
	 */
	int PEPPER_MODULE_EXCEPTION = 14;

	/**
	 * The meta object id for the '<em>EPackage</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getEPackage()
	 * @generated
	 */
	int EPACKAGE = 15;

	/**
	 * The meta object id for the '<em>Resource Factory</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getResourceFactory()
	 * @generated
	 */
	int RESOURCE_FACTORY = 16;

	/**
	 * The meta object id for the '<em>Log Service</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.osgi.service.log.LogService
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getLogService()
	 * @generated
	 */
	int LOG_SERVICE = 17;


	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule <em>Pepper Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Module</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule
	 * @generated
	 */
	EClass getPepperModule();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getName()
	 * @see #getPepperModule()
	 * @generated
	 */
	EAttribute getPepperModule_Name();

	/**
	 * Returns the meta object for the container reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getPepperModuleController <em>Pepper Module Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Pepper Module Controller</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getPepperModuleController()
	 * @see #getPepperModule()
	 * @generated
	 */
	EReference getPepperModule_PepperModuleController();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSaltProject <em>Salt Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Salt Project</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSaltProject()
	 * @see #getPepperModule()
	 * @generated
	 */
	EAttribute getPepperModule_SaltProject();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getReturningMode <em>Returning Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Returning Mode</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getReturningMode()
	 * @see #getPepperModule()
	 * @generated
	 */
	EAttribute getPepperModule_ReturningMode();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSCorpusGraph <em>SCorpus Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>SCorpus Graph</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSCorpusGraph()
	 * @see #getPepperModule()
	 * @generated
	 */
	EAttribute getPepperModule_SCorpusGraph();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getResources <em>Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resources</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getResources()
	 * @see #getPepperModule()
	 * @generated
	 */
	EAttribute getPepperModule_Resources();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getTemproraries <em>Temproraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temproraries</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getTemproraries()
	 * @see #getPepperModule()
	 * @generated
	 */
	EAttribute getPepperModule_Temproraries();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSymbolicName <em>Symbolic Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Symbolic Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSymbolicName()
	 * @see #getPepperModule()
	 * @generated
	 */
	EAttribute getPepperModule_SymbolicName();

	/**
	 * Returns the meta object for the containment reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getPersistenceConnector <em>Persistence Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Connector</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getPersistenceConnector()
	 * @see #getPepperModule()
	 * @generated
	 */
	EReference getPepperModule_PersistenceConnector();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSpecialParams <em>Special Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Special Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule#getSpecialParams()
	 * @see #getPepperModule()
	 * @generated
	 */
	EAttribute getPepperModule_SpecialParams();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter <em>Pepper Importer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Importer</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter
	 * @generated
	 */
	EClass getPepperImporter();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter#getSupportedFormats <em>Supported Formats</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Supported Formats</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter#getSupportedFormats()
	 * @see #getPepperImporter()
	 * @generated
	 */
	EReference getPepperImporter_SupportedFormats();

	/**
	 * Returns the meta object for the containment reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter#getCorpusDefinition <em>Corpus Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Corpus Definition</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter#getCorpusDefinition()
	 * @see #getPepperImporter()
	 * @generated
	 */
	EReference getPepperImporter_CorpusDefinition();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter <em>Pepper Exporter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Exporter</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter
	 * @generated
	 */
	EClass getPepperExporter();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter#getSupportedFormats <em>Supported Formats</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Supported Formats</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter#getSupportedFormats()
	 * @see #getPepperExporter()
	 * @generated
	 */
	EReference getPepperExporter_SupportedFormats();

	/**
	 * Returns the meta object for the containment reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter#getCorpusDefinition <em>Corpus Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Corpus Definition</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter#getCorpusDefinition()
	 * @see #getPepperExporter()
	 * @generated
	 */
	EReference getPepperExporter_CorpusDefinition();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition <em>Format Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Format Definition</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition
	 * @generated
	 */
	EClass getFormatDefinition();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition#getFormatName <em>Format Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition#getFormatName()
	 * @see #getFormatDefinition()
	 * @generated
	 */
	EAttribute getFormatDefinition_FormatName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition#getFormatVersion <em>Format Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format Version</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition#getFormatVersion()
	 * @see #getFormatDefinition()
	 * @generated
	 */
	EAttribute getFormatDefinition_FormatVersion();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition#getFormatReference <em>Format Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format Reference</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition#getFormatReference()
	 * @see #getFormatDefinition()
	 * @generated
	 */
	EAttribute getFormatDefinition_FormatReference();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition <em>Corpus Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Corpus Definition</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition
	 * @generated
	 */
	EClass getCorpusDefinition();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition#getCorpusPath <em>Corpus Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Corpus Path</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition#getCorpusPath()
	 * @see #getCorpusDefinition()
	 * @generated
	 */
	EAttribute getCorpusDefinition_CorpusPath();

	/**
	 * Returns the meta object for the containment reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition#getFormatDefinition <em>Format Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Format Definition</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition#getFormatDefinition()
	 * @see #getCorpusDefinition()
	 * @generated
	 */
	EReference getCorpusDefinition_FormatDefinition();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController <em>Pepper Module Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Module Controller</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController
	 * @generated
	 */
	EClass getPepperModuleController();

	/**
	 * Returns the meta object for the containment reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController#getPepperModule <em>Pepper Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pepper Module</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController#getPepperModule()
	 * @see #getPepperModuleController()
	 * @generated
	 */
	EReference getPepperModuleController_PepperModule();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperManipulator <em>Pepper Manipulator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Manipulator</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperManipulator
	 * @generated
	 */
	EClass getPepperManipulator();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.ExtensionFactoryPair <em>Extension Factory Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extension Factory Pair</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.ExtensionFactoryPair
	 * @generated
	 */
	EClass getExtensionFactoryPair();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.ExtensionFactoryPair#getFileExtension <em>File Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Extension</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.ExtensionFactoryPair#getFileExtension()
	 * @see #getExtensionFactoryPair()
	 * @generated
	 */
	EAttribute getExtensionFactoryPair_FileExtension();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.ExtensionFactoryPair#getResourceFactory <em>Resource Factory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource Factory</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.ExtensionFactoryPair#getResourceFactory()
	 * @see #getExtensionFactoryPair()
	 * @generated
	 */
	EAttribute getExtensionFactoryPair_ResourceFactory();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PersistenceConnector <em>Persistence Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Persistence Connector</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PersistenceConnector
	 * @generated
	 */
	EClass getPersistenceConnector();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PersistenceConnector#getExtensionFactoryPairs <em>Extension Factory Pairs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Extension Factory Pairs</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PersistenceConnector#getExtensionFactoryPairs()
	 * @see #getPersistenceConnector()
	 * @generated
	 */
	EReference getPersistenceConnector_ExtensionFactoryPairs();

	/**
	 * Returns the meta object for enum '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE <em>RETURNING MODE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>RETURNING MODE</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE
	 * @generated
	 */
	EEnum getRETURNING_MODE();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.URI <em>URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>URI</em>'.
	 * @see org.eclipse.emf.common.util.URI
	 * @model instanceClass="org.eclipse.emf.common.util.URI"
	 * @generated
	 */
	EDataType getURI();

	/**
	 * Returns the meta object for data type '{@link de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph <em>SCorpus Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>SCorpus Graph</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph
	 * @model instanceClass="de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph"
	 * @generated
	 */
	EDataType getSCorpusGraph();

	/**
	 * Returns the meta object for data type '{@link de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject <em>Salt Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Salt Project</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject
	 * @model instanceClass="de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject"
	 * @generated
	 */
	EDataType getSaltProject();

	/**
	 * Returns the meta object for data type '{@link de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId <em>SElement Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>SElement Id</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId
	 * @model instanceClass="de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId"
	 * @generated
	 */
	EDataType getSElementId();

	/**
	 * Returns the meta object for data type '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException <em>Pepper Module Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Module Exception</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException
	 * @model instanceClass="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException"
	 * @generated
	 */
	EDataType getPepperModuleException();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.EPackage <em>EPackage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EPackage</em>'.
	 * @see org.eclipse.emf.ecore.EPackage
	 * @model instanceClass="org.eclipse.emf.ecore.EPackage"
	 * @generated
	 */
	EDataType getEPackage();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl <em>Resource Factory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Resource Factory</em>'.
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl
	 * @model instanceClass="org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl"
	 * @generated
	 */
	EDataType getResourceFactory();

	/**
	 * Returns the meta object for data type '{@link org.osgi.service.log.LogService <em>Log Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Log Service</em>'.
	 * @see org.osgi.service.log.LogService
	 * @model instanceClass="org.osgi.service.log.LogService"
	 * @generated
	 */
	EDataType getLogService();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PepperModulesFactory getPepperModulesFactory();

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
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl <em>Pepper Module</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModuleImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperModule()
		 * @generated
		 */
		EClass PEPPER_MODULE = eINSTANCE.getPepperModule();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE__NAME = eINSTANCE.getPepperModule_Name();

		/**
		 * The meta object literal for the '<em><b>Pepper Module Controller</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_MODULE__PEPPER_MODULE_CONTROLLER = eINSTANCE.getPepperModule_PepperModuleController();

		/**
		 * The meta object literal for the '<em><b>Salt Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE__SALT_PROJECT = eINSTANCE.getPepperModule_SaltProject();

		/**
		 * The meta object literal for the '<em><b>Returning Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE__RETURNING_MODE = eINSTANCE.getPepperModule_ReturningMode();

		/**
		 * The meta object literal for the '<em><b>SCorpus Graph</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE__SCORPUS_GRAPH = eINSTANCE.getPepperModule_SCorpusGraph();

		/**
		 * The meta object literal for the '<em><b>Resources</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE__RESOURCES = eINSTANCE.getPepperModule_Resources();

		/**
		 * The meta object literal for the '<em><b>Temproraries</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE__TEMPRORARIES = eINSTANCE.getPepperModule_Temproraries();

		/**
		 * The meta object literal for the '<em><b>Symbolic Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE__SYMBOLIC_NAME = eINSTANCE.getPepperModule_SymbolicName();

		/**
		 * The meta object literal for the '<em><b>Persistence Connector</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_MODULE__PERSISTENCE_CONNECTOR = eINSTANCE.getPepperModule_PersistenceConnector();

		/**
		 * The meta object literal for the '<em><b>Special Params</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE__SPECIAL_PARAMS = eINSTANCE.getPepperModule_SpecialParams();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl <em>Pepper Importer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperImporter()
		 * @generated
		 */
		EClass PEPPER_IMPORTER = eINSTANCE.getPepperImporter();

		/**
		 * The meta object literal for the '<em><b>Supported Formats</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_IMPORTER__SUPPORTED_FORMATS = eINSTANCE.getPepperImporter_SupportedFormats();

		/**
		 * The meta object literal for the '<em><b>Corpus Definition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_IMPORTER__CORPUS_DEFINITION = eINSTANCE.getPepperImporter_CorpusDefinition();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl <em>Pepper Exporter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperExporter()
		 * @generated
		 */
		EClass PEPPER_EXPORTER = eINSTANCE.getPepperExporter();

		/**
		 * The meta object literal for the '<em><b>Supported Formats</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_EXPORTER__SUPPORTED_FORMATS = eINSTANCE.getPepperExporter_SupportedFormats();

		/**
		 * The meta object literal for the '<em><b>Corpus Definition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_EXPORTER__CORPUS_DEFINITION = eINSTANCE.getPepperExporter_CorpusDefinition();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.FormatDefinitionImpl <em>Format Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.FormatDefinitionImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getFormatDefinition()
		 * @generated
		 */
		EClass FORMAT_DEFINITION = eINSTANCE.getFormatDefinition();

		/**
		 * The meta object literal for the '<em><b>Format Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FORMAT_DEFINITION__FORMAT_NAME = eINSTANCE.getFormatDefinition_FormatName();

		/**
		 * The meta object literal for the '<em><b>Format Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FORMAT_DEFINITION__FORMAT_VERSION = eINSTANCE.getFormatDefinition_FormatVersion();

		/**
		 * The meta object literal for the '<em><b>Format Reference</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FORMAT_DEFINITION__FORMAT_REFERENCE = eINSTANCE.getFormatDefinition_FormatReference();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.CorpusDefinitionImpl <em>Corpus Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.CorpusDefinitionImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getCorpusDefinition()
		 * @generated
		 */
		EClass CORPUS_DEFINITION = eINSTANCE.getCorpusDefinition();

		/**
		 * The meta object literal for the '<em><b>Corpus Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORPUS_DEFINITION__CORPUS_PATH = eINSTANCE.getCorpusDefinition_CorpusPath();

		/**
		 * The meta object literal for the '<em><b>Format Definition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORPUS_DEFINITION__FORMAT_DEFINITION = eINSTANCE.getCorpusDefinition_FormatDefinition();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController <em>Pepper Module Controller</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperModuleController()
		 * @generated
		 */
		EClass PEPPER_MODULE_CONTROLLER = eINSTANCE.getPepperModuleController();

		/**
		 * The meta object literal for the '<em><b>Pepper Module</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_MODULE_CONTROLLER__PEPPER_MODULE = eINSTANCE.getPepperModuleController_PepperModule();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperManipulatorImpl <em>Pepper Manipulator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperManipulatorImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperManipulator()
		 * @generated
		 */
		EClass PEPPER_MANIPULATOR = eINSTANCE.getPepperManipulator();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.ExtensionFactoryPairImpl <em>Extension Factory Pair</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.ExtensionFactoryPairImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getExtensionFactoryPair()
		 * @generated
		 */
		EClass EXTENSION_FACTORY_PAIR = eINSTANCE.getExtensionFactoryPair();

		/**
		 * The meta object literal for the '<em><b>File Extension</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_FACTORY_PAIR__FILE_EXTENSION = eINSTANCE.getExtensionFactoryPair_FileExtension();

		/**
		 * The meta object literal for the '<em><b>Resource Factory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_FACTORY_PAIR__RESOURCE_FACTORY = eINSTANCE.getExtensionFactoryPair_ResourceFactory();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PersistenceConnectorImpl <em>Persistence Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PersistenceConnectorImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPersistenceConnector()
		 * @generated
		 */
		EClass PERSISTENCE_CONNECTOR = eINSTANCE.getPersistenceConnector();

		/**
		 * The meta object literal for the '<em><b>Extension Factory Pairs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSISTENCE_CONNECTOR__EXTENSION_FACTORY_PAIRS = eINSTANCE.getPersistenceConnector_ExtensionFactoryPairs();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE <em>RETURNING MODE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getRETURNING_MODE()
		 * @generated
		 */
		EEnum RETURNING_MODE = eINSTANCE.getRETURNING_MODE();

		/**
		 * The meta object literal for the '<em>URI</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.URI
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getURI()
		 * @generated
		 */
		EDataType URI = eINSTANCE.getURI();

		/**
		 * The meta object literal for the '<em>SCorpus Graph</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getSCorpusGraph()
		 * @generated
		 */
		EDataType SCORPUS_GRAPH = eINSTANCE.getSCorpusGraph();

		/**
		 * The meta object literal for the '<em>Salt Project</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getSaltProject()
		 * @generated
		 */
		EDataType SALT_PROJECT = eINSTANCE.getSaltProject();

		/**
		 * The meta object literal for the '<em>SElement Id</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getSElementId()
		 * @generated
		 */
		EDataType SELEMENT_ID = eINSTANCE.getSElementId();

		/**
		 * The meta object literal for the '<em>Pepper Module Exception</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getPepperModuleException()
		 * @generated
		 */
		EDataType PEPPER_MODULE_EXCEPTION = eINSTANCE.getPepperModuleException();

		/**
		 * The meta object literal for the '<em>EPackage</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.EPackage
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getEPackage()
		 * @generated
		 */
		EDataType EPACKAGE = eINSTANCE.getEPackage();

		/**
		 * The meta object literal for the '<em>Resource Factory</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getResourceFactory()
		 * @generated
		 */
		EDataType RESOURCE_FACTORY = eINSTANCE.getResourceFactory();

		/**
		 * The meta object literal for the '<em>Log Service</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.osgi.service.log.LogService
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl#getLogService()
		 * @generated
		 */
		EDataType LOG_SERVICE = eINSTANCE.getLogService();

	}

} //PepperModulesPackage

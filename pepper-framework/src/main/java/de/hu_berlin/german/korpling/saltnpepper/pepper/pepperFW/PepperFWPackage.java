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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage;

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
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory
 * @model kind="package"
 * @generated
 */
public interface PepperFWPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "pepperFW";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "de.hub.corpling.pepper.PepperFW";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "pepperFW";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PepperFWPackage eINSTANCE = de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl <em>Pepper Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperConverter()
	 * @generated
	 */
	int PEPPER_CONVERTER = 0;

	/**
	 * The feature id for the '<em><b>Pepper Module Resolver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER = 0;

	/**
	 * The feature id for the '<em><b>Pepper Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_CONVERTER__PEPPER_PARAMS = 1;

	/**
	 * The feature id for the '<em><b>Pepper Jobs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_CONVERTER__PEPPER_JOBS = 2;

	/**
	 * The feature id for the '<em><b>Pepper J2C Monitors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_CONVERTER__PEPPER_J2C_MONITORS = 3;

	/**
	 * The feature id for the '<em><b>Parallelized</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_CONVERTER__PARALLELIZED = 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_CONVERTER__PROPERTIES = 5;

	/**
	 * The feature id for the '<em><b>Pepper Params URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_CONVERTER__PEPPER_PARAMS_URI = 6;

	/**
	 * The number of structural features of the '<em>Pepper Converter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_CONVERTER_FEATURE_COUNT = 7;


	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleResolverImpl <em>Pepper Module Resolver</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleResolverImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperModuleResolver()
	 * @generated
	 */
	int PEPPER_MODULE_RESOLVER = 1;

	/**
	 * The feature id for the '<em><b>Pepper Importer Component Factories</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_RESOLVER__PEPPER_IMPORTER_COMPONENT_FACTORIES = 0;

	/**
	 * The feature id for the '<em><b>Pepper Manipulator Component Factories</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_RESOLVER__PEPPER_MANIPULATOR_COMPONENT_FACTORIES = 1;

	/**
	 * The feature id for the '<em><b>Pepper Exporter Component Factories</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_RESOLVER__PEPPER_EXPORTER_COMPONENT_FACTORIES = 2;

	/**
	 * The feature id for the '<em><b>Temproraries Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_RESOLVER__TEMPRORARIES_PROPERTY_NAME = 3;

	/**
	 * The feature id for the '<em><b>Resources Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_RESOLVER__RESOURCES_PROPERTY_NAME = 4;

	/**
	 * The number of structural features of the '<em>Pepper Module Resolver</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_RESOLVER_FEATURE_COUNT = 5;


	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl <em>Pepper Job</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperJob()
	 * @generated
	 */
	int PEPPER_JOB = 2;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl <em>Pepper Module Controller</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperModuleController()
	 * @generated
	 */
	int PEPPER_MODULE_CONTROLLER = 3;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable <em>Runnable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getRunnable()
	 * @generated
	 */
	int RUNNABLE = 4;

	/**
	 * The number of structural features of the '<em>Runnable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNNABLE_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Pepper Importers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__PEPPER_IMPORTERS = RUNNABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pepper Modules</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__PEPPER_MODULES = RUNNABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Pepper Exporters</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__PEPPER_EXPORTERS = RUNNABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__ID = RUNNABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Pepper Module Controllers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__PEPPER_MODULE_CONTROLLERS = RUNNABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Pepper M2M Monitors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__PEPPER_M2M_MONITORS = RUNNABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Pepper M2J Monitors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__PEPPER_M2J_MONITORS = RUNNABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Salt Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__SALT_PROJECT = RUNNABLE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Pepper J2C Monitor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__PEPPER_J2C_MONITOR = RUNNABLE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Pepper Job Logger</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__PEPPER_JOB_LOGGER = RUNNABLE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Pepper Document Controller</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER = RUNNABLE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB__PROPERTIES = RUNNABLE_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Pepper Job</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB_FEATURE_COUNT = RUNNABLE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Pepper Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_CONTROLLER__PEPPER_MODULE = PepperModulesPackage.PEPPER_MODULE_CONTROLLER__PEPPER_MODULE;

	/**
	 * The feature id for the '<em><b>Pepper Job</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_CONTROLLER__PEPPER_JOB = PepperModulesPackage.PEPPER_MODULE_CONTROLLER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Input Pepper Module Monitors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_CONTROLLER__INPUT_PEPPER_MODULE_MONITORS = PepperModulesPackage.PEPPER_MODULE_CONTROLLER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Output Pepper Module Monitors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_CONTROLLER__OUTPUT_PEPPER_MODULE_MONITORS = PepperModulesPackage.PEPPER_MODULE_CONTROLLER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Pepper M2J Monitor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_CONTROLLER__PEPPER_M2J_MONITOR = PepperModulesPackage.PEPPER_MODULE_CONTROLLER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Pepper Job Logger</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_CONTROLLER__PEPPER_JOB_LOGGER = PepperModulesPackage.PEPPER_MODULE_CONTROLLER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Pepper Document Controller</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER = PepperModulesPackage.PEPPER_MODULE_CONTROLLER_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Pepper Module Controller</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MODULE_CONTROLLER_FEATURE_COUNT = PepperModulesPackage.PEPPER_MODULE_CONTROLLER_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperMonitorImpl <em>Pepper Monitor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperMonitorImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperMonitor()
	 * @generated
	 */
	int PEPPER_MONITOR = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MONITOR__ID = 0;

	/**
	 * The feature id for the '<em><b>Finished</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MONITOR__FINISHED = 1;

	/**
	 * The feature id for the '<em><b>Exceptions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MONITOR__EXCEPTIONS = 2;

	/**
	 * The number of structural features of the '<em>Pepper Monitor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_MONITOR_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperQueuedMonitorImpl <em>Pepper Queued Monitor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperQueuedMonitorImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperQueuedMonitor()
	 * @generated
	 */
	int PEPPER_QUEUED_MONITOR = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_QUEUED_MONITOR__ID = PEPPER_MONITOR__ID;

	/**
	 * The feature id for the '<em><b>Finished</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_QUEUED_MONITOR__FINISHED = PEPPER_MONITOR__FINISHED;

	/**
	 * The feature id for the '<em><b>Exceptions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_QUEUED_MONITOR__EXCEPTIONS = PEPPER_MONITOR__EXCEPTIONS;

	/**
	 * The feature id for the '<em><b>Order Queue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_QUEUED_MONITOR__ORDER_QUEUE = PEPPER_MONITOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_QUEUED_MONITOR__EMPTY = PEPPER_MONITOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Pepper Queued Monitor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_QUEUED_MONITOR_FEATURE_COUNT = PEPPER_MONITOR_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFinishableMonitorImpl <em>Pepper Finishable Monitor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFinishableMonitorImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperFinishableMonitor()
	 * @generated
	 */
	int PEPPER_FINISHABLE_MONITOR = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_FINISHABLE_MONITOR__ID = PEPPER_MONITOR__ID;

	/**
	 * The feature id for the '<em><b>Finished</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_FINISHABLE_MONITOR__FINISHED = PEPPER_MONITOR__FINISHED;

	/**
	 * The feature id for the '<em><b>Exceptions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_FINISHABLE_MONITOR__EXCEPTIONS = PEPPER_MONITOR__EXCEPTIONS;

	/**
	 * The number of structural features of the '<em>Pepper Finishable Monitor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_FINISHABLE_MONITOR_FEATURE_COUNT = PEPPER_MONITOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobLoggerImpl <em>Pepper Job Logger</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobLoggerImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperJobLogger()
	 * @generated
	 */
	int PEPPER_JOB_LOGGER = 8;

	/**
	 * The feature id for the '<em><b>Pepper Job</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB_LOGGER__PEPPER_JOB = 0;

	/**
	 * The number of structural features of the '<em>Pepper Job Logger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB_LOGGER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperDocumentControllerImpl <em>Pepper Document Controller</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperDocumentControllerImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperDocumentController()
	 * @generated
	 */
	int PEPPER_DOCUMENT_CONTROLLER = 9;

	/**
	 * The feature id for the '<em><b>Pepper Module Controllers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS = 0;

	/**
	 * The feature id for the '<em><b>Pepper Job Controller</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER = 1;

	/**
	 * The feature id for the '<em><b>REMOVE SDOCUMENT AFTER PROCESSING</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_DOCUMENT_CONTROLLER__REMOVE_SDOCUMENT_AFTER_PROCESSING = 2;

	/**
	 * The feature id for the '<em><b>COMPUTE PERFORMANCE</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_DOCUMENT_CONTROLLER__COMPUTE_PERFORMANCE = 3;

	/**
	 * The feature id for the '<em><b>AMOUNT OF COMPUTABLE SDOCUMENTS</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_DOCUMENT_CONTROLLER__AMOUNT_OF_COMPUTABLE_SDOCUMENTS = 4;

	/**
	 * The feature id for the '<em><b>Current Amount Of SDocuments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_DOCUMENT_CONTROLLER__CURRENT_AMOUNT_OF_SDOCUMENTS = 5;

	/**
	 * The number of structural features of the '<em>Pepper Document Controller</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_DOCUMENT_CONTROLLER_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS <em>PEPPER SDOCUMENT STATUS</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPEPPER_SDOCUMENT_STATUS()
	 * @generated
	 */
	int PEPPER_SDOCUMENT_STATUS = 10;

	/**
	 * The meta object id for the '<em>Bundle Context</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.osgi.framework.BundleContext
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getBundleContext()
	 * @generated
	 */
	int BUNDLE_CONTEXT = 11;


	/**
	 * The meta object id for the '<em>Pepper Module</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperInterface.PepperModule
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperModule()
	 * @generated
	 */
	int PEPPER_MODULE = 12;


	/**
	 * The meta object id for the '<em>Pepper Importer</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperInterface.PepperImporter
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperImporter()
	 * @generated
	 */
	int PEPPER_IMPORTER = 13;

	/**
	 * The meta object id for the '<em>Pepper Exporter</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperInterface.PepperExporter
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperExporter()
	 * @generated
	 */
	int PEPPER_EXPORTER = 14;


	/**
	 * The meta object id for the '<em>Pepper Params</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperParams()
	 * @generated
	 */
	int PEPPER_PARAMS = 15;


	/**
	 * The meta object id for the '<em>URI</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.URI
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getURI()
	 * @generated
	 */
	int URI = 16;


	/**
	 * The meta object id for the '<em>Pepper Importer Params</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperImporterParams()
	 * @generated
	 */
	int PEPPER_IMPORTER_PARAMS = 17;

	/**
	 * The meta object id for the '<em>Pepper Module Params</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperModuleParams()
	 * @generated
	 */
	int PEPPER_MODULE_PARAMS = 18;

	/**
	 * The meta object id for the '<em>Pepper Exporter Params</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperExporterParams()
	 * @generated
	 */
	int PEPPER_EXPORTER_PARAMS = 19;


	/**
	 * The meta object id for the '<em>Concurrent Linked Queue</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.concurrent.ConcurrentLinkedQueue
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getConcurrentLinkedQueue()
	 * @generated
	 */
	int CONCURRENT_LINKED_QUEUE = 20;


	/**
	 * The meta object id for the '<em>Pepper Module Exception</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperExceptions.PepperModuleException
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperModuleException()
	 * @generated
	 */
	int PEPPER_MODULE_EXCEPTION = 21;


	/**
	 * The meta object id for the '<em>Pepper Convert Exception</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperExceptions.PepperConvertException
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperConvertException()
	 * @generated
	 */
	int PEPPER_CONVERT_EXCEPTION = 22;


	/**
	 * The meta object id for the '<em>Pepper Exception</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperExceptions.PepperException
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperException()
	 * @generated
	 */
	int PEPPER_EXCEPTION = 23;


	/**
	 * The meta object id for the '<em>Component Factory</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.osgi.service.component.ComponentFactory
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getComponentFactory()
	 * @generated
	 */
	int COMPONENT_FACTORY = 24;


	/**
	 * The meta object id for the '<em>Log Service</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.osgi.service.log.LogService
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getLogService()
	 * @generated
	 */
	int LOG_SERVICE = 25;


	/**
	 * The meta object id for the '<em>Properties</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Properties
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getProperties()
	 * @generated
	 */
	int PROPERTIES = 26;


	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter <em>Pepper Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Converter</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter
	 * @generated
	 */
	EClass getPepperConverter();

	/**
	 * Returns the meta object for the containment reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperModuleResolver <em>Pepper Module Resolver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pepper Module Resolver</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperModuleResolver()
	 * @see #getPepperConverter()
	 * @generated
	 */
	EReference getPepperConverter_PepperModuleResolver();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperParams <em>Pepper Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pepper Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperParams()
	 * @see #getPepperConverter()
	 * @generated
	 */
	EAttribute getPepperConverter_PepperParams();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperJobs <em>Pepper Jobs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pepper Jobs</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperJobs()
	 * @see #getPepperConverter()
	 * @generated
	 */
	EReference getPepperConverter_PepperJobs();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperJ2CMonitors <em>Pepper J2C Monitors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pepper J2C Monitors</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperJ2CMonitors()
	 * @see #getPepperConverter()
	 * @generated
	 */
	EReference getPepperConverter_PepperJ2CMonitors();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#isParallelized <em>Parallelized</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parallelized</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#isParallelized()
	 * @see #getPepperConverter()
	 * @generated
	 */
	EAttribute getPepperConverter_Parallelized();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getProperties()
	 * @see #getPepperConverter()
	 * @generated
	 */
	EAttribute getPepperConverter_Properties();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperParamsURI <em>Pepper Params URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pepper Params URI</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperParamsURI()
	 * @see #getPepperConverter()
	 * @generated
	 */
	EAttribute getPepperConverter_PepperParamsURI();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver <em>Pepper Module Resolver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Module Resolver</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver
	 * @generated
	 */
	EClass getPepperModuleResolver();

	/**
	 * Returns the meta object for the attribute list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperImporterComponentFactories <em>Pepper Importer Component Factories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Pepper Importer Component Factories</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperImporterComponentFactories()
	 * @see #getPepperModuleResolver()
	 * @generated
	 */
	EAttribute getPepperModuleResolver_PepperImporterComponentFactories();

	/**
	 * Returns the meta object for the attribute list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperManipulatorComponentFactories <em>Pepper Manipulator Component Factories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Pepper Manipulator Component Factories</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperManipulatorComponentFactories()
	 * @see #getPepperModuleResolver()
	 * @generated
	 */
	EAttribute getPepperModuleResolver_PepperManipulatorComponentFactories();

	/**
	 * Returns the meta object for the attribute list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperExporterComponentFactories <em>Pepper Exporter Component Factories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Pepper Exporter Component Factories</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperExporterComponentFactories()
	 * @see #getPepperModuleResolver()
	 * @generated
	 */
	EAttribute getPepperModuleResolver_PepperExporterComponentFactories();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getTemprorariesPropertyName <em>Temproraries Property Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temproraries Property Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getTemprorariesPropertyName()
	 * @see #getPepperModuleResolver()
	 * @generated
	 */
	EAttribute getPepperModuleResolver_TemprorariesPropertyName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getResourcesPropertyName <em>Resources Property Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resources Property Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getResourcesPropertyName()
	 * @see #getPepperModuleResolver()
	 * @generated
	 */
	EAttribute getPepperModuleResolver_ResourcesPropertyName();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob <em>Pepper Job</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Job</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob
	 * @generated
	 */
	EClass getPepperJob();

	/**
	 * Returns the meta object for the attribute list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperImporters <em>Pepper Importers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Pepper Importers</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperImporters()
	 * @see #getPepperJob()
	 * @generated
	 */
	EAttribute getPepperJob_PepperImporters();

	/**
	 * Returns the meta object for the attribute list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperModules <em>Pepper Modules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Pepper Modules</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperModules()
	 * @see #getPepperJob()
	 * @generated
	 */
	EAttribute getPepperJob_PepperModules();

	/**
	 * Returns the meta object for the attribute list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperExporters <em>Pepper Exporters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Pepper Exporters</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperExporters()
	 * @see #getPepperJob()
	 * @generated
	 */
	EAttribute getPepperJob_PepperExporters();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getId()
	 * @see #getPepperJob()
	 * @generated
	 */
	EAttribute getPepperJob_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperModuleControllers <em>Pepper Module Controllers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pepper Module Controllers</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperModuleControllers()
	 * @see #getPepperJob()
	 * @generated
	 */
	EReference getPepperJob_PepperModuleControllers();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperM2MMonitors <em>Pepper M2M Monitors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pepper M2M Monitors</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperM2MMonitors()
	 * @see #getPepperJob()
	 * @generated
	 */
	EReference getPepperJob_PepperM2MMonitors();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperM2JMonitors <em>Pepper M2J Monitors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pepper M2J Monitors</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperM2JMonitors()
	 * @see #getPepperJob()
	 * @generated
	 */
	EReference getPepperJob_PepperM2JMonitors();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getSaltProject <em>Salt Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Salt Project</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getSaltProject()
	 * @see #getPepperJob()
	 * @generated
	 */
	EAttribute getPepperJob_SaltProject();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperJ2CMonitor <em>Pepper J2C Monitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pepper J2C Monitor</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperJ2CMonitor()
	 * @see #getPepperJob()
	 * @generated
	 */
	EReference getPepperJob_PepperJ2CMonitor();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperJobLogger <em>Pepper Job Logger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pepper Job Logger</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperJobLogger()
	 * @see #getPepperJob()
	 * @generated
	 */
	EReference getPepperJob_PepperJobLogger();

	/**
	 * Returns the meta object for the containment reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperDocumentController <em>Pepper Document Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pepper Document Controller</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getPepperDocumentController()
	 * @see #getPepperJob()
	 * @generated
	 */
	EReference getPepperJob_PepperDocumentController();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#getProperties()
	 * @see #getPepperJob()
	 * @generated
	 */
	EAttribute getPepperJob_Properties();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController <em>Pepper Module Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Module Controller</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController
	 * @generated
	 */
	EClass getPepperModuleController();

	/**
	 * Returns the meta object for the container reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperJob <em>Pepper Job</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Pepper Job</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperJob()
	 * @see #getPepperModuleController()
	 * @generated
	 */
	EReference getPepperModuleController_PepperJob();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getInputPepperModuleMonitors <em>Input Pepper Module Monitors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Input Pepper Module Monitors</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getInputPepperModuleMonitors()
	 * @see #getPepperModuleController()
	 * @generated
	 */
	EReference getPepperModuleController_InputPepperModuleMonitors();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getOutputPepperModuleMonitors <em>Output Pepper Module Monitors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Output Pepper Module Monitors</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getOutputPepperModuleMonitors()
	 * @see #getPepperModuleController()
	 * @generated
	 */
	EReference getPepperModuleController_OutputPepperModuleMonitors();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperM2JMonitor <em>Pepper M2J Monitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pepper M2J Monitor</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperM2JMonitor()
	 * @see #getPepperModuleController()
	 * @generated
	 */
	EReference getPepperModuleController_PepperM2JMonitor();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperJobLogger <em>Pepper Job Logger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pepper Job Logger</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperJobLogger()
	 * @see #getPepperModuleController()
	 * @generated
	 */
	EReference getPepperModuleController_PepperJobLogger();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperDocumentController <em>Pepper Document Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pepper Document Controller</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController#getPepperDocumentController()
	 * @see #getPepperModuleController()
	 * @generated
	 */
	EReference getPepperModuleController_PepperDocumentController();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable <em>Runnable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Runnable</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable
	 * @generated
	 */
	EClass getRunnable();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor <em>Pepper Queued Monitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Queued Monitor</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor
	 * @generated
	 */
	EClass getPepperQueuedMonitor();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#getOrderQueue <em>Order Queue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Order Queue</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#getOrderQueue()
	 * @see #getPepperQueuedMonitor()
	 * @generated
	 */
	EAttribute getPepperQueuedMonitor_OrderQueue();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#isEmpty <em>Empty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Empty</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#isEmpty()
	 * @see #getPepperQueuedMonitor()
	 * @generated
	 */
	EAttribute getPepperQueuedMonitor_Empty();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor <em>Pepper Finishable Monitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Finishable Monitor</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor
	 * @generated
	 */
	EClass getPepperFinishableMonitor();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor <em>Pepper Monitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Monitor</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor
	 * @generated
	 */
	EClass getPepperMonitor();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#getId()
	 * @see #getPepperMonitor()
	 * @generated
	 */
	EAttribute getPepperMonitor_Id();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#isFinished <em>Finished</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Finished</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#isFinished()
	 * @see #getPepperMonitor()
	 * @generated
	 */
	EAttribute getPepperMonitor_Finished();

	/**
	 * Returns the meta object for the attribute list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#getExceptions <em>Exceptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Exceptions</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor#getExceptions()
	 * @see #getPepperMonitor()
	 * @generated
	 */
	EAttribute getPepperMonitor_Exceptions();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger <em>Pepper Job Logger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Job Logger</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger
	 * @generated
	 */
	EClass getPepperJobLogger();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger#getPepperJob <em>Pepper Job</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pepper Job</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger#getPepperJob()
	 * @see #getPepperJobLogger()
	 * @generated
	 */
	EReference getPepperJobLogger_PepperJob();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController <em>Pepper Document Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Document Controller</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController
	 * @generated
	 */
	EClass getPepperDocumentController();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperModuleControllers <em>Pepper Module Controllers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Pepper Module Controllers</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperModuleControllers()
	 * @see #getPepperDocumentController()
	 * @generated
	 */
	EReference getPepperDocumentController_PepperModuleControllers();

	/**
	 * Returns the meta object for the container reference '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperJobController <em>Pepper Job Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Pepper Job Controller</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getPepperJobController()
	 * @see #getPepperDocumentController()
	 * @generated
	 */
	EReference getPepperDocumentController_PepperJobController();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getREMOVE_SDOCUMENT_AFTER_PROCESSING <em>REMOVE SDOCUMENT AFTER PROCESSING</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>REMOVE SDOCUMENT AFTER PROCESSING</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getREMOVE_SDOCUMENT_AFTER_PROCESSING()
	 * @see #getPepperDocumentController()
	 * @generated
	 */
	EAttribute getPepperDocumentController_REMOVE_SDOCUMENT_AFTER_PROCESSING();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getCOMPUTE_PERFORMANCE <em>COMPUTE PERFORMANCE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>COMPUTE PERFORMANCE</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getCOMPUTE_PERFORMANCE()
	 * @see #getPepperDocumentController()
	 * @generated
	 */
	EAttribute getPepperDocumentController_COMPUTE_PERFORMANCE();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getAMOUNT_OF_COMPUTABLE_SDOCUMENTS <em>AMOUNT OF COMPUTABLE SDOCUMENTS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>AMOUNT OF COMPUTABLE SDOCUMENTS</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getAMOUNT_OF_COMPUTABLE_SDOCUMENTS()
	 * @see #getPepperDocumentController()
	 * @generated
	 */
	EAttribute getPepperDocumentController_AMOUNT_OF_COMPUTABLE_SDOCUMENTS();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getCurrentAmountOfSDocuments <em>Current Amount Of SDocuments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Current Amount Of SDocuments</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController#getCurrentAmountOfSDocuments()
	 * @see #getPepperDocumentController()
	 * @generated
	 */
	EAttribute getPepperDocumentController_CurrentAmountOfSDocuments();

	/**
	 * Returns the meta object for enum '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS <em>PEPPER SDOCUMENT STATUS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>PEPPER SDOCUMENT STATUS</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS
	 * @generated
	 */
	EEnum getPEPPER_SDOCUMENT_STATUS();

	/**
	 * Returns the meta object for data type '{@link org.osgi.framework.BundleContext <em>Bundle Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Bundle Context</em>'.
	 * @see org.osgi.framework.BundleContext
	 * @model instanceClass="org.osgi.framework.BundleContext"
	 * @generated
	 */
	EDataType getBundleContext();

	/**
	 * Returns the meta object for data type '{@link de.hub.corpling.pepper.pepperInterface.PepperModule <em>Pepper Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Module</em>'.
	 * @see de.hub.corpling.pepper.pepperInterface.PepperModule
	 * @model instanceClass="de.hub.corpling.pepper.pepperInterface.PepperModule"
	 * @generated
	 */
	EDataType getPepperModule();

	/**
	 * Returns the meta object for data type '{@link de.hub.corpling.pepper.pepperInterface.PepperImporter <em>Pepper Importer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Importer</em>'.
	 * @see de.hub.corpling.pepper.pepperInterface.PepperImporter
	 * @model instanceClass="de.hub.corpling.pepper.pepperInterface.PepperImporter"
	 * @generated
	 */
	EDataType getPepperImporter();

	/**
	 * Returns the meta object for data type '{@link de.hub.corpling.pepper.pepperInterface.PepperExporter <em>Pepper Exporter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Exporter</em>'.
	 * @see de.hub.corpling.pepper.pepperInterface.PepperExporter
	 * @model instanceClass="de.hub.corpling.pepper.pepperInterface.PepperExporter"
	 * @generated
	 */
	EDataType getPepperExporter();

	/**
	 * Returns the meta object for data type '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams <em>Pepper Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams
	 * @model instanceClass="de.hub.corpling.pepper.pepperParams.PepperParams"
	 * @generated
	 */
	EDataType getPepperParams();

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
	 * Returns the meta object for data type '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams <em>Pepper Importer Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Importer Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams
	 * @model instanceClass="de.hub.corpling.pepper.pepperParams.ImporterParams"
	 * @generated
	 */
	EDataType getPepperImporterParams();

	/**
	 * Returns the meta object for data type '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams <em>Pepper Module Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Module Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams
	 * @model instanceClass="de.hub.corpling.pepper.pepperParams.ModuleParams"
	 * @generated
	 */
	EDataType getPepperModuleParams();

	/**
	 * Returns the meta object for data type '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams <em>Pepper Exporter Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Exporter Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams
	 * @model instanceClass="de.hub.corpling.pepper.pepperParams.ExporterParams"
	 * @generated
	 */
	EDataType getPepperExporterParams();

	/**
	 * Returns the meta object for data type '{@link java.util.concurrent.ConcurrentLinkedQueue <em>Concurrent Linked Queue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Concurrent Linked Queue</em>'.
	 * @see java.util.concurrent.ConcurrentLinkedQueue
	 * @model instanceClass="java.util.concurrent.ConcurrentLinkedQueue"
	 * @generated
	 */
	EDataType getConcurrentLinkedQueue();

	/**
	 * Returns the meta object for data type '{@link de.hub.corpling.pepper.pepperExceptions.PepperModuleException <em>Pepper Module Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Module Exception</em>'.
	 * @see de.hub.corpling.pepper.pepperExceptions.PepperModuleException
	 * @model instanceClass="de.hub.corpling.pepper.pepperExceptions.PepperModuleException"
	 * @generated
	 */
	EDataType getPepperModuleException();

	/**
	 * Returns the meta object for data type '{@link de.hub.corpling.pepper.pepperExceptions.PepperConvertException <em>Pepper Convert Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Convert Exception</em>'.
	 * @see de.hub.corpling.pepper.pepperExceptions.PepperConvertException
	 * @model instanceClass="de.hub.corpling.pepper.pepperExceptions.PepperConvertException"
	 * @generated
	 */
	EDataType getPepperConvertException();

	/**
	 * Returns the meta object for data type '{@link de.hub.corpling.pepper.pepperExceptions.PepperException <em>Pepper Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pepper Exception</em>'.
	 * @see de.hub.corpling.pepper.pepperExceptions.PepperException
	 * @model instanceClass="de.hub.corpling.pepper.pepperExceptions.PepperException"
	 * @generated
	 */
	EDataType getPepperException();

	/**
	 * Returns the meta object for data type '{@link org.osgi.service.component.ComponentFactory <em>Component Factory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Component Factory</em>'.
	 * @see org.osgi.service.component.ComponentFactory
	 * @model instanceClass="org.osgi.service.component.ComponentFactory"
	 * @generated
	 */
	EDataType getComponentFactory();

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
	 * Returns the meta object for data type '{@link java.util.Properties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Properties</em>'.
	 * @see java.util.Properties
	 * @model instanceClass="java.util.Properties"
	 * @generated
	 */
	EDataType getProperties();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PepperFWFactory getPepperFWFactory();

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
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl <em>Pepper Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperConverter()
		 * @generated
		 */
		EClass PEPPER_CONVERTER = eINSTANCE.getPepperConverter();
		/**
		 * The meta object literal for the '<em><b>Pepper Module Resolver</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER = eINSTANCE.getPepperConverter_PepperModuleResolver();
		/**
		 * The meta object literal for the '<em><b>Pepper Params</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_CONVERTER__PEPPER_PARAMS = eINSTANCE.getPepperConverter_PepperParams();
		/**
		 * The meta object literal for the '<em><b>Pepper Jobs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_CONVERTER__PEPPER_JOBS = eINSTANCE.getPepperConverter_PepperJobs();
		/**
		 * The meta object literal for the '<em><b>Pepper J2C Monitors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_CONVERTER__PEPPER_J2C_MONITORS = eINSTANCE.getPepperConverter_PepperJ2CMonitors();
		/**
		 * The meta object literal for the '<em><b>Parallelized</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_CONVERTER__PARALLELIZED = eINSTANCE.getPepperConverter_Parallelized();
		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_CONVERTER__PROPERTIES = eINSTANCE.getPepperConverter_Properties();
		/**
		 * The meta object literal for the '<em><b>Pepper Params URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_CONVERTER__PEPPER_PARAMS_URI = eINSTANCE.getPepperConverter_PepperParamsURI();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleResolverImpl <em>Pepper Module Resolver</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleResolverImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperModuleResolver()
		 * @generated
		 */
		EClass PEPPER_MODULE_RESOLVER = eINSTANCE.getPepperModuleResolver();
		/**
		 * The meta object literal for the '<em><b>Pepper Importer Component Factories</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE_RESOLVER__PEPPER_IMPORTER_COMPONENT_FACTORIES = eINSTANCE.getPepperModuleResolver_PepperImporterComponentFactories();
		/**
		 * The meta object literal for the '<em><b>Pepper Manipulator Component Factories</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE_RESOLVER__PEPPER_MANIPULATOR_COMPONENT_FACTORIES = eINSTANCE.getPepperModuleResolver_PepperManipulatorComponentFactories();
		/**
		 * The meta object literal for the '<em><b>Pepper Exporter Component Factories</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE_RESOLVER__PEPPER_EXPORTER_COMPONENT_FACTORIES = eINSTANCE.getPepperModuleResolver_PepperExporterComponentFactories();
		/**
		 * The meta object literal for the '<em><b>Temproraries Property Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE_RESOLVER__TEMPRORARIES_PROPERTY_NAME = eINSTANCE.getPepperModuleResolver_TemprorariesPropertyName();
		/**
		 * The meta object literal for the '<em><b>Resources Property Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MODULE_RESOLVER__RESOURCES_PROPERTY_NAME = eINSTANCE.getPepperModuleResolver_ResourcesPropertyName();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl <em>Pepper Job</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperJob()
		 * @generated
		 */
		EClass PEPPER_JOB = eINSTANCE.getPepperJob();
		/**
		 * The meta object literal for the '<em><b>Pepper Importers</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_JOB__PEPPER_IMPORTERS = eINSTANCE.getPepperJob_PepperImporters();
		/**
		 * The meta object literal for the '<em><b>Pepper Modules</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_JOB__PEPPER_MODULES = eINSTANCE.getPepperJob_PepperModules();
		/**
		 * The meta object literal for the '<em><b>Pepper Exporters</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_JOB__PEPPER_EXPORTERS = eINSTANCE.getPepperJob_PepperExporters();
		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_JOB__ID = eINSTANCE.getPepperJob_Id();
		/**
		 * The meta object literal for the '<em><b>Pepper Module Controllers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_JOB__PEPPER_MODULE_CONTROLLERS = eINSTANCE.getPepperJob_PepperModuleControllers();
		/**
		 * The meta object literal for the '<em><b>Pepper M2M Monitors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_JOB__PEPPER_M2M_MONITORS = eINSTANCE.getPepperJob_PepperM2MMonitors();
		/**
		 * The meta object literal for the '<em><b>Pepper M2J Monitors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_JOB__PEPPER_M2J_MONITORS = eINSTANCE.getPepperJob_PepperM2JMonitors();
		/**
		 * The meta object literal for the '<em><b>Salt Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_JOB__SALT_PROJECT = eINSTANCE.getPepperJob_SaltProject();
		/**
		 * The meta object literal for the '<em><b>Pepper J2C Monitor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_JOB__PEPPER_J2C_MONITOR = eINSTANCE.getPepperJob_PepperJ2CMonitor();
		/**
		 * The meta object literal for the '<em><b>Pepper Job Logger</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_JOB__PEPPER_JOB_LOGGER = eINSTANCE.getPepperJob_PepperJobLogger();
		/**
		 * The meta object literal for the '<em><b>Pepper Document Controller</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER = eINSTANCE.getPepperJob_PepperDocumentController();
		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_JOB__PROPERTIES = eINSTANCE.getPepperJob_Properties();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl <em>Pepper Module Controller</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperModuleController()
		 * @generated
		 */
		EClass PEPPER_MODULE_CONTROLLER = eINSTANCE.getPepperModuleController();
		/**
		 * The meta object literal for the '<em><b>Pepper Job</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_MODULE_CONTROLLER__PEPPER_JOB = eINSTANCE.getPepperModuleController_PepperJob();
		/**
		 * The meta object literal for the '<em><b>Input Pepper Module Monitors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_MODULE_CONTROLLER__INPUT_PEPPER_MODULE_MONITORS = eINSTANCE.getPepperModuleController_InputPepperModuleMonitors();
		/**
		 * The meta object literal for the '<em><b>Output Pepper Module Monitors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_MODULE_CONTROLLER__OUTPUT_PEPPER_MODULE_MONITORS = eINSTANCE.getPepperModuleController_OutputPepperModuleMonitors();
		/**
		 * The meta object literal for the '<em><b>Pepper M2J Monitor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_MODULE_CONTROLLER__PEPPER_M2J_MONITOR = eINSTANCE.getPepperModuleController_PepperM2JMonitor();
		/**
		 * The meta object literal for the '<em><b>Pepper Job Logger</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_MODULE_CONTROLLER__PEPPER_JOB_LOGGER = eINSTANCE.getPepperModuleController_PepperJobLogger();
		/**
		 * The meta object literal for the '<em><b>Pepper Document Controller</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER = eINSTANCE.getPepperModuleController_PepperDocumentController();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable <em>Runnable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getRunnable()
		 * @generated
		 */
		EClass RUNNABLE = eINSTANCE.getRunnable();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperQueuedMonitorImpl <em>Pepper Queued Monitor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperQueuedMonitorImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperQueuedMonitor()
		 * @generated
		 */
		EClass PEPPER_QUEUED_MONITOR = eINSTANCE.getPepperQueuedMonitor();
		/**
		 * The meta object literal for the '<em><b>Order Queue</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_QUEUED_MONITOR__ORDER_QUEUE = eINSTANCE.getPepperQueuedMonitor_OrderQueue();
		/**
		 * The meta object literal for the '<em><b>Empty</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_QUEUED_MONITOR__EMPTY = eINSTANCE.getPepperQueuedMonitor_Empty();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFinishableMonitorImpl <em>Pepper Finishable Monitor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFinishableMonitorImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperFinishableMonitor()
		 * @generated
		 */
		EClass PEPPER_FINISHABLE_MONITOR = eINSTANCE.getPepperFinishableMonitor();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperMonitorImpl <em>Pepper Monitor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperMonitorImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperMonitor()
		 * @generated
		 */
		EClass PEPPER_MONITOR = eINSTANCE.getPepperMonitor();
		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MONITOR__ID = eINSTANCE.getPepperMonitor_Id();
		/**
		 * The meta object literal for the '<em><b>Finished</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MONITOR__FINISHED = eINSTANCE.getPepperMonitor_Finished();
		/**
		 * The meta object literal for the '<em><b>Exceptions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_MONITOR__EXCEPTIONS = eINSTANCE.getPepperMonitor_Exceptions();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobLoggerImpl <em>Pepper Job Logger</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobLoggerImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperJobLogger()
		 * @generated
		 */
		EClass PEPPER_JOB_LOGGER = eINSTANCE.getPepperJobLogger();
		/**
		 * The meta object literal for the '<em><b>Pepper Job</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_JOB_LOGGER__PEPPER_JOB = eINSTANCE.getPepperJobLogger_PepperJob();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperDocumentControllerImpl <em>Pepper Document Controller</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperDocumentControllerImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperDocumentController()
		 * @generated
		 */
		EClass PEPPER_DOCUMENT_CONTROLLER = eINSTANCE.getPepperDocumentController();
		/**
		 * The meta object literal for the '<em><b>Pepper Module Controllers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS = eINSTANCE.getPepperDocumentController_PepperModuleControllers();
		/**
		 * The meta object literal for the '<em><b>Pepper Job Controller</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER = eINSTANCE.getPepperDocumentController_PepperJobController();
		/**
		 * The meta object literal for the '<em><b>REMOVE SDOCUMENT AFTER PROCESSING</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_DOCUMENT_CONTROLLER__REMOVE_SDOCUMENT_AFTER_PROCESSING = eINSTANCE.getPepperDocumentController_REMOVE_SDOCUMENT_AFTER_PROCESSING();
		/**
		 * The meta object literal for the '<em><b>COMPUTE PERFORMANCE</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_DOCUMENT_CONTROLLER__COMPUTE_PERFORMANCE = eINSTANCE.getPepperDocumentController_COMPUTE_PERFORMANCE();
		/**
		 * The meta object literal for the '<em><b>AMOUNT OF COMPUTABLE SDOCUMENTS</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_DOCUMENT_CONTROLLER__AMOUNT_OF_COMPUTABLE_SDOCUMENTS = eINSTANCE.getPepperDocumentController_AMOUNT_OF_COMPUTABLE_SDOCUMENTS();
		/**
		 * The meta object literal for the '<em><b>Current Amount Of SDocuments</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_DOCUMENT_CONTROLLER__CURRENT_AMOUNT_OF_SDOCUMENTS = eINSTANCE.getPepperDocumentController_CurrentAmountOfSDocuments();
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS <em>PEPPER SDOCUMENT STATUS</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPEPPER_SDOCUMENT_STATUS()
		 * @generated
		 */
		EEnum PEPPER_SDOCUMENT_STATUS = eINSTANCE.getPEPPER_SDOCUMENT_STATUS();
		/**
		 * The meta object literal for the '<em>Bundle Context</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.osgi.framework.BundleContext
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getBundleContext()
		 * @generated
		 */
		EDataType BUNDLE_CONTEXT = eINSTANCE.getBundleContext();
		/**
		 * The meta object literal for the '<em>Pepper Module</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.corpling.pepper.pepperInterface.PepperModule
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperModule()
		 * @generated
		 */
		EDataType PEPPER_MODULE = eINSTANCE.getPepperModule();
		/**
		 * The meta object literal for the '<em>Pepper Importer</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.corpling.pepper.pepperInterface.PepperImporter
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperImporter()
		 * @generated
		 */
		EDataType PEPPER_IMPORTER = eINSTANCE.getPepperImporter();
		/**
		 * The meta object literal for the '<em>Pepper Exporter</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.corpling.pepper.pepperInterface.PepperExporter
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperExporter()
		 * @generated
		 */
		EDataType PEPPER_EXPORTER = eINSTANCE.getPepperExporter();
		/**
		 * The meta object literal for the '<em>Pepper Params</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperParams()
		 * @generated
		 */
		EDataType PEPPER_PARAMS = eINSTANCE.getPepperParams();
		/**
		 * The meta object literal for the '<em>URI</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.URI
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getURI()
		 * @generated
		 */
		EDataType URI = eINSTANCE.getURI();
		/**
		 * The meta object literal for the '<em>Pepper Importer Params</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperImporterParams()
		 * @generated
		 */
		EDataType PEPPER_IMPORTER_PARAMS = eINSTANCE.getPepperImporterParams();
		/**
		 * The meta object literal for the '<em>Pepper Module Params</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperModuleParams()
		 * @generated
		 */
		EDataType PEPPER_MODULE_PARAMS = eINSTANCE.getPepperModuleParams();
		/**
		 * The meta object literal for the '<em>Pepper Exporter Params</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperExporterParams()
		 * @generated
		 */
		EDataType PEPPER_EXPORTER_PARAMS = eINSTANCE.getPepperExporterParams();
		/**
		 * The meta object literal for the '<em>Concurrent Linked Queue</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.concurrent.ConcurrentLinkedQueue
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getConcurrentLinkedQueue()
		 * @generated
		 */
		EDataType CONCURRENT_LINKED_QUEUE = eINSTANCE.getConcurrentLinkedQueue();
		/**
		 * The meta object literal for the '<em>Pepper Module Exception</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.corpling.pepper.pepperExceptions.PepperModuleException
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperModuleException()
		 * @generated
		 */
		EDataType PEPPER_MODULE_EXCEPTION = eINSTANCE.getPepperModuleException();
		/**
		 * The meta object literal for the '<em>Pepper Convert Exception</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.corpling.pepper.pepperExceptions.PepperConvertException
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperConvertException()
		 * @generated
		 */
		EDataType PEPPER_CONVERT_EXCEPTION = eINSTANCE.getPepperConvertException();
		/**
		 * The meta object literal for the '<em>Pepper Exception</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.corpling.pepper.pepperExceptions.PepperException
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getPepperException()
		 * @generated
		 */
		EDataType PEPPER_EXCEPTION = eINSTANCE.getPepperException();
		/**
		 * The meta object literal for the '<em>Component Factory</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.osgi.service.component.ComponentFactory
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getComponentFactory()
		 * @generated
		 */
		EDataType COMPONENT_FACTORY = eINSTANCE.getComponentFactory();
		/**
		 * The meta object literal for the '<em>Log Service</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.osgi.service.log.LogService
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getLogService()
		 * @generated
		 */
		EDataType LOG_SERVICE = eINSTANCE.getLogService();
		/**
		 * The meta object literal for the '<em>Properties</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Properties
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperFWPackageImpl#getProperties()
		 * @generated
		 */
		EDataType PROPERTIES = eINSTANCE.getProperties();

	}

} //PepperFWPackage

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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsFactory
 * @model kind="package"
 * @generated
 */
public interface PepperParamsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "pepperParams";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "pepperParams";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PepperParamsPackage eINSTANCE = de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ModuleParamsImpl <em>Module Params</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ModuleParamsImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getModuleParams()
	 * @generated
	 */
	int MODULE_PARAMS = 0;

	/**
	 * The feature id for the '<em><b>Module Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_PARAMS__MODULE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Special Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_PARAMS__SPECIAL_PARAMS = 1;

	/**
	 * The number of structural features of the '<em>Module Params</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_PARAMS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ImporterParamsImpl <em>Importer Params</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ImporterParamsImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getImporterParams()
	 * @generated
	 */
	int IMPORTER_PARAMS = 1;

	/**
	 * The feature id for the '<em><b>Module Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORTER_PARAMS__MODULE_NAME = MODULE_PARAMS__MODULE_NAME;

	/**
	 * The feature id for the '<em><b>Special Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORTER_PARAMS__SPECIAL_PARAMS = MODULE_PARAMS__SPECIAL_PARAMS;

	/**
	 * The feature id for the '<em><b>Format Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORTER_PARAMS__FORMAT_NAME = MODULE_PARAMS_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Format Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORTER_PARAMS__FORMAT_VERSION = MODULE_PARAMS_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Source Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORTER_PARAMS__SOURCE_PATH = MODULE_PARAMS_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Importer Params</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORTER_PARAMS_FEATURE_COUNT = MODULE_PARAMS_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsImpl <em>Pepper Params</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getPepperParams()
	 * @generated
	 */
	int PEPPER_PARAMS = 2;

	/**
	 * The feature id for the '<em><b>Pepper Job Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_PARAMS__PEPPER_JOB_PARAMS = 0;

	/**
	 * The number of structural features of the '<em>Pepper Params</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_PARAMS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperJobParamsImpl <em>Pepper Job Params</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperJobParamsImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getPepperJobParams()
	 * @generated
	 */
	int PEPPER_JOB_PARAMS = 3;

	/**
	 * The feature id for the '<em><b>Importer Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB_PARAMS__IMPORTER_PARAMS = 0;

	/**
	 * The feature id for the '<em><b>Module Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB_PARAMS__MODULE_PARAMS = 1;

	/**
	 * The feature id for the '<em><b>Exporter Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB_PARAMS__EXPORTER_PARAMS = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB_PARAMS__ID = 3;

	/**
	 * The number of structural features of the '<em>Pepper Job Params</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PEPPER_JOB_PARAMS_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ExporterParamsImpl <em>Exporter Params</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ExporterParamsImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getExporterParams()
	 * @generated
	 */
	int EXPORTER_PARAMS = 4;

	/**
	 * The feature id for the '<em><b>Module Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTER_PARAMS__MODULE_NAME = MODULE_PARAMS__MODULE_NAME;

	/**
	 * The feature id for the '<em><b>Special Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTER_PARAMS__SPECIAL_PARAMS = MODULE_PARAMS__SPECIAL_PARAMS;

	/**
	 * The feature id for the '<em><b>Format Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTER_PARAMS__FORMAT_NAME = MODULE_PARAMS_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Format Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTER_PARAMS__FORMAT_VERSION = MODULE_PARAMS_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Destination Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTER_PARAMS__DESTINATION_PATH = MODULE_PARAMS_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Exporter Params</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTER_PARAMS_FEATURE_COUNT = MODULE_PARAMS_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '<em>URI</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.URI
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getURI()
	 * @generated
	 */
	int URI = 5;


	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams <em>Module Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Module Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams
	 * @generated
	 */
	EClass getModuleParams();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams#getModuleName <em>Module Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams#getModuleName()
	 * @see #getModuleParams()
	 * @generated
	 */
	EAttribute getModuleParams_ModuleName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams#getSpecialParams <em>Special Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Special Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams#getSpecialParams()
	 * @see #getModuleParams()
	 * @generated
	 */
	EAttribute getModuleParams_SpecialParams();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams <em>Importer Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Importer Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams
	 * @generated
	 */
	EClass getImporterParams();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getFormatName <em>Format Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getFormatName()
	 * @see #getImporterParams()
	 * @generated
	 */
	EAttribute getImporterParams_FormatName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getFormatVersion <em>Format Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format Version</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getFormatVersion()
	 * @see #getImporterParams()
	 * @generated
	 */
	EAttribute getImporterParams_FormatVersion();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getSourcePath <em>Source Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Path</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getSourcePath()
	 * @see #getImporterParams()
	 * @generated
	 */
	EAttribute getImporterParams_SourcePath();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams <em>Pepper Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams
	 * @generated
	 */
	EClass getPepperParams();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams#getPepperJobParams <em>Pepper Job Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pepper Job Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams#getPepperJobParams()
	 * @see #getPepperParams()
	 * @generated
	 */
	EReference getPepperParams_PepperJobParams();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams <em>Pepper Job Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pepper Job Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams
	 * @generated
	 */
	EClass getPepperJobParams();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams#getImporterParams <em>Importer Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Importer Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams#getImporterParams()
	 * @see #getPepperJobParams()
	 * @generated
	 */
	EReference getPepperJobParams_ImporterParams();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams#getModuleParams <em>Module Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Module Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams#getModuleParams()
	 * @see #getPepperJobParams()
	 * @generated
	 */
	EReference getPepperJobParams_ModuleParams();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams#getExporterParams <em>Exporter Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Exporter Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams#getExporterParams()
	 * @see #getPepperJobParams()
	 * @generated
	 */
	EReference getPepperJobParams_ExporterParams();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams#getId()
	 * @see #getPepperJobParams()
	 * @generated
	 */
	EAttribute getPepperJobParams_Id();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams <em>Exporter Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exporter Params</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams
	 * @generated
	 */
	EClass getExporterParams();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams#getFormatName <em>Format Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams#getFormatName()
	 * @see #getExporterParams()
	 * @generated
	 */
	EAttribute getExporterParams_FormatName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams#getFormatVersion <em>Format Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format Version</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams#getFormatVersion()
	 * @see #getExporterParams()
	 * @generated
	 */
	EAttribute getExporterParams_FormatVersion();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams#getDestinationPath <em>Destination Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Destination Path</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams#getDestinationPath()
	 * @see #getExporterParams()
	 * @generated
	 */
	EAttribute getExporterParams_DestinationPath();

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
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PepperParamsFactory getPepperParamsFactory();

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
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ModuleParamsImpl <em>Module Params</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ModuleParamsImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getModuleParams()
		 * @generated
		 */
		EClass MODULE_PARAMS = eINSTANCE.getModuleParams();

		/**
		 * The meta object literal for the '<em><b>Module Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODULE_PARAMS__MODULE_NAME = eINSTANCE.getModuleParams_ModuleName();

		/**
		 * The meta object literal for the '<em><b>Special Params</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODULE_PARAMS__SPECIAL_PARAMS = eINSTANCE.getModuleParams_SpecialParams();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ImporterParamsImpl <em>Importer Params</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ImporterParamsImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getImporterParams()
		 * @generated
		 */
		EClass IMPORTER_PARAMS = eINSTANCE.getImporterParams();

		/**
		 * The meta object literal for the '<em><b>Format Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORTER_PARAMS__FORMAT_NAME = eINSTANCE.getImporterParams_FormatName();

		/**
		 * The meta object literal for the '<em><b>Format Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORTER_PARAMS__FORMAT_VERSION = eINSTANCE.getImporterParams_FormatVersion();

		/**
		 * The meta object literal for the '<em><b>Source Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORTER_PARAMS__SOURCE_PATH = eINSTANCE.getImporterParams_SourcePath();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsImpl <em>Pepper Params</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getPepperParams()
		 * @generated
		 */
		EClass PEPPER_PARAMS = eINSTANCE.getPepperParams();

		/**
		 * The meta object literal for the '<em><b>Pepper Job Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_PARAMS__PEPPER_JOB_PARAMS = eINSTANCE.getPepperParams_PepperJobParams();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperJobParamsImpl <em>Pepper Job Params</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperJobParamsImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getPepperJobParams()
		 * @generated
		 */
		EClass PEPPER_JOB_PARAMS = eINSTANCE.getPepperJobParams();

		/**
		 * The meta object literal for the '<em><b>Importer Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_JOB_PARAMS__IMPORTER_PARAMS = eINSTANCE.getPepperJobParams_ImporterParams();

		/**
		 * The meta object literal for the '<em><b>Module Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_JOB_PARAMS__MODULE_PARAMS = eINSTANCE.getPepperJobParams_ModuleParams();

		/**
		 * The meta object literal for the '<em><b>Exporter Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PEPPER_JOB_PARAMS__EXPORTER_PARAMS = eINSTANCE.getPepperJobParams_ExporterParams();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PEPPER_JOB_PARAMS__ID = eINSTANCE.getPepperJobParams_Id();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ExporterParamsImpl <em>Exporter Params</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ExporterParamsImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getExporterParams()
		 * @generated
		 */
		EClass EXPORTER_PARAMS = eINSTANCE.getExporterParams();

		/**
		 * The meta object literal for the '<em><b>Format Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPORTER_PARAMS__FORMAT_NAME = eINSTANCE.getExporterParams_FormatName();

		/**
		 * The meta object literal for the '<em><b>Format Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPORTER_PARAMS__FORMAT_VERSION = eINSTANCE.getExporterParams_FormatVersion();

		/**
		 * The meta object literal for the '<em><b>Destination Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPORTER_PARAMS__DESTINATION_PATH = eINSTANCE.getExporterParams_DestinationPath();

		/**
		 * The meta object literal for the '<em>URI</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.URI
		 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsPackageImpl#getURI()
		 * @generated
		 */
		EDataType URI = eINSTANCE.getURI();

	}

} //PepperParamsPackage

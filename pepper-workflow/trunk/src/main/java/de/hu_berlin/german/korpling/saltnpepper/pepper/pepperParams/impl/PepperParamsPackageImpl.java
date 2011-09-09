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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PepperParamsPackageImpl extends EPackageImpl implements PepperParamsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass moduleParamsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importerParamsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperParamsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperJobParamsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exporterParamsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType uriEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PepperParamsPackageImpl() {
		super(eNS_URI, PepperParamsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link PepperParamsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PepperParamsPackage init() {
		if (isInited) return (PepperParamsPackage)EPackage.Registry.INSTANCE.getEPackage(PepperParamsPackage.eNS_URI);

		// Obtain or create and register package
		PepperParamsPackageImpl thePepperParamsPackage = (PepperParamsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PepperParamsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PepperParamsPackageImpl());

		isInited = true;

		// Create package meta-data objects
		thePepperParamsPackage.createPackageContents();

		// Initialize created meta-data
		thePepperParamsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePepperParamsPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PepperParamsPackage.eNS_URI, thePepperParamsPackage);
		return thePepperParamsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModuleParams() {
		return moduleParamsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModuleParams_ModuleName() {
		return (EAttribute)moduleParamsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModuleParams_SpecialParams() {
		return (EAttribute)moduleParamsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImporterParams() {
		return importerParamsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImporterParams_FormatName() {
		return (EAttribute)importerParamsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImporterParams_FormatVersion() {
		return (EAttribute)importerParamsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImporterParams_SourcePath() {
		return (EAttribute)importerParamsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperParams() {
		return pepperParamsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperParams_PepperJobParams() {
		return (EReference)pepperParamsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperJobParams() {
		return pepperJobParamsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperJobParams_ImporterParams() {
		return (EReference)pepperJobParamsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperJobParams_ModuleParams() {
		return (EReference)pepperJobParamsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperJobParams_ExporterParams() {
		return (EReference)pepperJobParamsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperJobParams_Id() {
		return (EAttribute)pepperJobParamsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExporterParams() {
		return exporterParamsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExporterParams_FormatName() {
		return (EAttribute)exporterParamsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExporterParams_FormatVersion() {
		return (EAttribute)exporterParamsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExporterParams_DestinationPath() {
		return (EAttribute)exporterParamsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getURI() {
		return uriEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperParamsFactory getPepperParamsFactory() {
		return (PepperParamsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		moduleParamsEClass = createEClass(MODULE_PARAMS);
		createEAttribute(moduleParamsEClass, MODULE_PARAMS__MODULE_NAME);
		createEAttribute(moduleParamsEClass, MODULE_PARAMS__SPECIAL_PARAMS);

		importerParamsEClass = createEClass(IMPORTER_PARAMS);
		createEAttribute(importerParamsEClass, IMPORTER_PARAMS__FORMAT_NAME);
		createEAttribute(importerParamsEClass, IMPORTER_PARAMS__FORMAT_VERSION);
		createEAttribute(importerParamsEClass, IMPORTER_PARAMS__SOURCE_PATH);

		pepperParamsEClass = createEClass(PEPPER_PARAMS);
		createEReference(pepperParamsEClass, PEPPER_PARAMS__PEPPER_JOB_PARAMS);

		pepperJobParamsEClass = createEClass(PEPPER_JOB_PARAMS);
		createEReference(pepperJobParamsEClass, PEPPER_JOB_PARAMS__IMPORTER_PARAMS);
		createEReference(pepperJobParamsEClass, PEPPER_JOB_PARAMS__MODULE_PARAMS);
		createEReference(pepperJobParamsEClass, PEPPER_JOB_PARAMS__EXPORTER_PARAMS);
		createEAttribute(pepperJobParamsEClass, PEPPER_JOB_PARAMS__ID);

		exporterParamsEClass = createEClass(EXPORTER_PARAMS);
		createEAttribute(exporterParamsEClass, EXPORTER_PARAMS__FORMAT_NAME);
		createEAttribute(exporterParamsEClass, EXPORTER_PARAMS__FORMAT_VERSION);
		createEAttribute(exporterParamsEClass, EXPORTER_PARAMS__DESTINATION_PATH);

		// Create data types
		uriEDataType = createEDataType(URI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		importerParamsEClass.getESuperTypes().add(this.getModuleParams());
		exporterParamsEClass.getESuperTypes().add(this.getModuleParams());

		// Initialize classes and features; add operations and parameters
		initEClass(moduleParamsEClass, ModuleParams.class, "ModuleParams", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModuleParams_ModuleName(), ecorePackage.getEString(), "moduleName", null, 0, 1, ModuleParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModuleParams_SpecialParams(), this.getURI(), "specialParams", null, 0, 1, ModuleParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(importerParamsEClass, ImporterParams.class, "ImporterParams", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImporterParams_FormatName(), ecorePackage.getEString(), "formatName", null, 0, 1, ImporterParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImporterParams_FormatVersion(), ecorePackage.getEString(), "formatVersion", null, 0, 1, ImporterParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImporterParams_SourcePath(), this.getURI(), "sourcePath", null, 0, 1, ImporterParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pepperParamsEClass, PepperParams.class, "PepperParams", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPepperParams_PepperJobParams(), this.getPepperJobParams(), null, "pepperJobParams", null, 0, -1, PepperParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pepperJobParamsEClass, PepperJobParams.class, "PepperJobParams", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPepperJobParams_ImporterParams(), this.getImporterParams(), null, "importerParams", null, 0, -1, PepperJobParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperJobParams_ModuleParams(), this.getModuleParams(), null, "moduleParams", null, 0, -1, PepperJobParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperJobParams_ExporterParams(), this.getExporterParams(), null, "exporterParams", null, 0, -1, PepperJobParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperJobParams_Id(), ecorePackage.getEIntegerObject(), "id", null, 1, 1, PepperJobParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exporterParamsEClass, ExporterParams.class, "ExporterParams", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExporterParams_FormatName(), ecorePackage.getEString(), "formatName", null, 0, 1, ExporterParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExporterParams_FormatVersion(), ecorePackage.getEString(), "formatVersion", null, 0, 1, ExporterParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExporterParams_DestinationPath(), this.getURI(), "destinationPath", null, 0, 1, ExporterParams.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(uriEDataType, org.eclipse.emf.common.util.URI.class, "URI", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //PepperParamsPackageImpl

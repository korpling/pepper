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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.ExtensionFactoryPair;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfaceFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PersistenceConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.XsltModulesPackageImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.osgi.service.log.LogService;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PepperInterfacePackageImpl extends EPackageImpl implements PepperInterfacePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperModuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperImporterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperExporterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass formatDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass corpusDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperModuleControllerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperManipulatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extensionFactoryPairEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass persistenceConnectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum returninG_MODEEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType uriEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sCorpusGraphEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType saltProjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sElementIdEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pepperModuleExceptionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType ePackageEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType resourceFactoryEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType logServiceEDataType = null;

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
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PepperInterfacePackageImpl() {
		super(eNS_URI, PepperInterfaceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PepperInterfacePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PepperInterfacePackage init() {
		if (isInited) return (PepperInterfacePackage)EPackage.Registry.INSTANCE.getEPackage(PepperInterfacePackage.eNS_URI);

		// Obtain or create and register package
		PepperInterfacePackageImpl thePepperInterfacePackage = (PepperInterfacePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PepperInterfacePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PepperInterfacePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		XsltModulesPackageImpl theXsltModulesPackage = (XsltModulesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(XsltModulesPackage.eNS_URI) instanceof XsltModulesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(XsltModulesPackage.eNS_URI) : XsltModulesPackage.eINSTANCE);

		// Create package meta-data objects
		thePepperInterfacePackage.createPackageContents();
		theXsltModulesPackage.createPackageContents();

		// Initialize created meta-data
		thePepperInterfacePackage.initializePackageContents();
		theXsltModulesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePepperInterfacePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PepperInterfacePackage.eNS_URI, thePepperInterfacePackage);
		return thePepperInterfacePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperModule() {
		return pepperModuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModule_Name() {
		return (EAttribute)pepperModuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperModule_PepperModuleController() {
		return (EReference)pepperModuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModule_SaltProject() {
		return (EAttribute)pepperModuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModule_ReturningMode() {
		return (EAttribute)pepperModuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModule_SCorpusGraph() {
		return (EAttribute)pepperModuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModule_Resources() {
		return (EAttribute)pepperModuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModule_Temproraries() {
		return (EAttribute)pepperModuleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModule_SymbolicName() {
		return (EAttribute)pepperModuleEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperModule_PersistenceConnector() {
		return (EReference)pepperModuleEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModule_SpecialParams() {
		return (EAttribute)pepperModuleEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperImporter() {
		return pepperImporterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperImporter_SupportedFormats() {
		return (EReference)pepperImporterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperImporter_CorpusDefinition() {
		return (EReference)pepperImporterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperExporter() {
		return pepperExporterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperExporter_SupportedFormats() {
		return (EReference)pepperExporterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperExporter_CorpusDefinition() {
		return (EReference)pepperExporterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFormatDefinition() {
		return formatDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFormatDefinition_FormatName() {
		return (EAttribute)formatDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFormatDefinition_FormatVersion() {
		return (EAttribute)formatDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFormatDefinition_FormatReference() {
		return (EAttribute)formatDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCorpusDefinition() {
		return corpusDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCorpusDefinition_CorpusPath() {
		return (EAttribute)corpusDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorpusDefinition_FormatDefinition() {
		return (EReference)corpusDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperModuleController() {
		return pepperModuleControllerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperModuleController_PepperModule() {
		return (EReference)pepperModuleControllerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperManipulator() {
		return pepperManipulatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtensionFactoryPair() {
		return extensionFactoryPairEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionFactoryPair_FileExtension() {
		return (EAttribute)extensionFactoryPairEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionFactoryPair_ResourceFactory() {
		return (EAttribute)extensionFactoryPairEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPersistenceConnector() {
		return persistenceConnectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPersistenceConnector_ExtensionFactoryPairs() {
		return (EReference)persistenceConnectorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getRETURNING_MODE() {
		return returninG_MODEEEnum;
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
	public EDataType getSCorpusGraph() {
		return sCorpusGraphEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSaltProject() {
		return saltProjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSElementId() {
		return sElementIdEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPepperModuleException() {
		return pepperModuleExceptionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getEPackage() {
		return ePackageEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getResourceFactory() {
		return resourceFactoryEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getLogService() {
		return logServiceEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperInterfaceFactory getPepperInterfaceFactory() {
		return (PepperInterfaceFactory)getEFactoryInstance();
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
		pepperModuleEClass = createEClass(PEPPER_MODULE);
		createEAttribute(pepperModuleEClass, PEPPER_MODULE__NAME);
		createEReference(pepperModuleEClass, PEPPER_MODULE__PEPPER_MODULE_CONTROLLER);
		createEAttribute(pepperModuleEClass, PEPPER_MODULE__SALT_PROJECT);
		createEAttribute(pepperModuleEClass, PEPPER_MODULE__RETURNING_MODE);
		createEAttribute(pepperModuleEClass, PEPPER_MODULE__SCORPUS_GRAPH);
		createEAttribute(pepperModuleEClass, PEPPER_MODULE__RESOURCES);
		createEAttribute(pepperModuleEClass, PEPPER_MODULE__TEMPRORARIES);
		createEAttribute(pepperModuleEClass, PEPPER_MODULE__SYMBOLIC_NAME);
		createEReference(pepperModuleEClass, PEPPER_MODULE__PERSISTENCE_CONNECTOR);
		createEAttribute(pepperModuleEClass, PEPPER_MODULE__SPECIAL_PARAMS);

		pepperImporterEClass = createEClass(PEPPER_IMPORTER);
		createEReference(pepperImporterEClass, PEPPER_IMPORTER__SUPPORTED_FORMATS);
		createEReference(pepperImporterEClass, PEPPER_IMPORTER__CORPUS_DEFINITION);

		pepperExporterEClass = createEClass(PEPPER_EXPORTER);
		createEReference(pepperExporterEClass, PEPPER_EXPORTER__SUPPORTED_FORMATS);
		createEReference(pepperExporterEClass, PEPPER_EXPORTER__CORPUS_DEFINITION);

		formatDefinitionEClass = createEClass(FORMAT_DEFINITION);
		createEAttribute(formatDefinitionEClass, FORMAT_DEFINITION__FORMAT_NAME);
		createEAttribute(formatDefinitionEClass, FORMAT_DEFINITION__FORMAT_VERSION);
		createEAttribute(formatDefinitionEClass, FORMAT_DEFINITION__FORMAT_REFERENCE);

		corpusDefinitionEClass = createEClass(CORPUS_DEFINITION);
		createEAttribute(corpusDefinitionEClass, CORPUS_DEFINITION__CORPUS_PATH);
		createEReference(corpusDefinitionEClass, CORPUS_DEFINITION__FORMAT_DEFINITION);

		pepperModuleControllerEClass = createEClass(PEPPER_MODULE_CONTROLLER);
		createEReference(pepperModuleControllerEClass, PEPPER_MODULE_CONTROLLER__PEPPER_MODULE);

		pepperManipulatorEClass = createEClass(PEPPER_MANIPULATOR);

		extensionFactoryPairEClass = createEClass(EXTENSION_FACTORY_PAIR);
		createEAttribute(extensionFactoryPairEClass, EXTENSION_FACTORY_PAIR__FILE_EXTENSION);
		createEAttribute(extensionFactoryPairEClass, EXTENSION_FACTORY_PAIR__RESOURCE_FACTORY);

		persistenceConnectorEClass = createEClass(PERSISTENCE_CONNECTOR);
		createEReference(persistenceConnectorEClass, PERSISTENCE_CONNECTOR__EXTENSION_FACTORY_PAIRS);

		// Create enums
		returninG_MODEEEnum = createEEnum(RETURNING_MODE);

		// Create data types
		uriEDataType = createEDataType(URI);
		sCorpusGraphEDataType = createEDataType(SCORPUS_GRAPH);
		saltProjectEDataType = createEDataType(SALT_PROJECT);
		sElementIdEDataType = createEDataType(SELEMENT_ID);
		pepperModuleExceptionEDataType = createEDataType(PEPPER_MODULE_EXCEPTION);
		ePackageEDataType = createEDataType(EPACKAGE);
		resourceFactoryEDataType = createEDataType(RESOURCE_FACTORY);
		logServiceEDataType = createEDataType(LOG_SERVICE);
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

		// Obtain other dependent packages
		XsltModulesPackage theXsltModulesPackage = (XsltModulesPackage)EPackage.Registry.INSTANCE.getEPackage(XsltModulesPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theXsltModulesPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		pepperImporterEClass.getESuperTypes().add(this.getPepperModule());
		pepperExporterEClass.getESuperTypes().add(this.getPepperModule());
		pepperManipulatorEClass.getESuperTypes().add(this.getPepperModule());

		// Initialize classes and features; add operations and parameters
		initEClass(pepperModuleEClass, PepperModule.class, "PepperModule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPepperModule_Name(), ecorePackage.getEString(), "name", null, 1, 1, PepperModule.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperModule_PepperModuleController(), this.getPepperModuleController(), this.getPepperModuleController_PepperModule(), "pepperModuleController", null, 0, 1, PepperModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModule_SaltProject(), this.getSaltProject(), "saltProject", null, 1, 1, PepperModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModule_ReturningMode(), this.getRETURNING_MODE(), "returningMode", "PUT", 0, 1, PepperModule.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModule_SCorpusGraph(), this.getSCorpusGraph(), "sCorpusGraph", null, 1, 1, PepperModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModule_Resources(), this.getURI(), "resources", null, 0, 1, PepperModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModule_Temproraries(), this.getURI(), "temproraries", null, 0, 1, PepperModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModule_SymbolicName(), ecorePackage.getEString(), "symbolicName", null, 1, 1, PepperModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperModule_PersistenceConnector(), this.getPersistenceConnector(), null, "persistenceConnector", null, 0, 1, PepperModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModule_SpecialParams(), this.getURI(), "specialParams", null, 0, 1, PepperModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(pepperModuleEClass, null, "start", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getPepperModuleException());

		op = addEOperation(pepperModuleEClass, null, "start", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSElementId(), "sElementId", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getPepperModuleException());

		op = addEOperation(pepperModuleEClass, null, "end", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getPepperModuleException());

		op = addEOperation(pepperModuleEClass, null, "setLogService", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLogService(), "logService", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperModuleEClass, null, "unsetLogService", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLogService(), "logService", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperModuleEClass, this.getLogService(), "getLogService", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(pepperImporterEClass, PepperImporter.class, "PepperImporter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPepperImporter_SupportedFormats(), this.getFormatDefinition(), null, "supportedFormats", null, 1, -1, PepperImporter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperImporter_CorpusDefinition(), this.getCorpusDefinition(), null, "corpusDefinition", null, 1, 1, PepperImporter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(pepperImporterEClass, null, "importCorpusStructure", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSCorpusGraph(), "corpusGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getPepperModuleException());

		initEClass(pepperExporterEClass, PepperExporter.class, "PepperExporter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPepperExporter_SupportedFormats(), this.getFormatDefinition(), null, "supportedFormats", null, 1, -1, PepperExporter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperExporter_CorpusDefinition(), this.getCorpusDefinition(), null, "corpusDefinition", null, 1, 1, PepperExporter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(pepperExporterEClass, null, "createFolderStructure", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSElementId(), "sElementId", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(formatDefinitionEClass, FormatDefinition.class, "FormatDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFormatDefinition_FormatName(), ecorePackage.getEString(), "formatName", null, 1, 1, FormatDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFormatDefinition_FormatVersion(), ecorePackage.getEString(), "formatVersion", null, 0, 1, FormatDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFormatDefinition_FormatReference(), this.getURI(), "formatReference", null, 0, 1, FormatDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(corpusDefinitionEClass, CorpusDefinition.class, "CorpusDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCorpusDefinition_CorpusPath(), this.getURI(), "corpusPath", null, 0, 1, CorpusDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCorpusDefinition_FormatDefinition(), this.getFormatDefinition(), null, "formatDefinition", null, 0, 1, CorpusDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pepperModuleControllerEClass, PepperModuleController.class, "PepperModuleController", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPepperModuleController_PepperModule(), this.getPepperModule(), this.getPepperModule_PepperModuleController(), "pepperModule", null, 0, 1, PepperModuleController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(pepperModuleControllerEClass, null, "put", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSElementId(), "sElementId", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperModuleControllerEClass, this.getSElementId(), "get", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperModuleControllerEClass, null, "finish", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSElementId(), "sElementId", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(pepperManipulatorEClass, PepperManipulator.class, "PepperManipulator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(extensionFactoryPairEClass, ExtensionFactoryPair.class, "ExtensionFactoryPair", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtensionFactoryPair_FileExtension(), ecorePackage.getEString(), "fileExtension", null, 0, 1, ExtensionFactoryPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtensionFactoryPair_ResourceFactory(), this.getResourceFactory(), "resourceFactory", null, 0, 1, ExtensionFactoryPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(persistenceConnectorEClass, PersistenceConnector.class, "PersistenceConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPersistenceConnector_ExtensionFactoryPairs(), this.getExtensionFactoryPair(), null, "extensionFactoryPairs", null, 0, -1, PersistenceConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(persistenceConnectorEClass, ecorePackage.getEResource(), "save", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "modelObject", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getURI(), "modelURI", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(persistenceConnectorEClass, ecorePackage.getEResource(), "getResource", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getURI(), "resourceURI", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(returninG_MODEEEnum, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE.class, "RETURNING_MODE");
		addEEnumLiteral(returninG_MODEEEnum, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE.FINISH);
		addEEnumLiteral(returninG_MODEEEnum, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.RETURNING_MODE.PUT);

		// Initialize data types
		initEDataType(uriEDataType, org.eclipse.emf.common.util.URI.class, "URI", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(sCorpusGraphEDataType, SCorpusGraph.class, "SCorpusGraph", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(saltProjectEDataType, SaltProject.class, "SaltProject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(sElementIdEDataType, SElementId.class, "SElementId", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperModuleExceptionEDataType, PepperModuleException.class, "PepperModuleException", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(ePackageEDataType, EPackage.class, "EPackage", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(resourceFactoryEDataType, ResourceFactoryImpl.class, "ResourceFactory", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(logServiceEDataType, LogService.class, "LogService", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //PepperInterfacePackageImpl

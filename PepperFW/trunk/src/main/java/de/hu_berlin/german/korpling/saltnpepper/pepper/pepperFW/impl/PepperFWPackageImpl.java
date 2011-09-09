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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams;
import java.util.Properties;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PepperFWPackageImpl extends EPackageImpl implements PepperFWPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperConverterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperModuleResolverEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperJobEClass = null;

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
	private EClass runnableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperQueuedMonitorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperFinishableMonitorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperMonitorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperJobLoggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperDocumentControllerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum peppeR_SDOCUMENT_STATUSEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType bundleContextEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pepperModuleEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pepperImporterEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pepperExporterEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pepperParamsEDataType = null;

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
	private EDataType pepperImporterParamsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pepperModuleParamsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pepperExporterParamsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType concurrentLinkedQueueEDataType = null;

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
	private EDataType pepperConvertExceptionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pepperExceptionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType componentFactoryEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType logServiceEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType propertiesEDataType = null;

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
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PepperFWPackageImpl() {
		super(eNS_URI, PepperFWFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PepperFWPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PepperFWPackage init() {
		if (isInited) return (PepperFWPackage)EPackage.Registry.INSTANCE.getEPackage(PepperFWPackage.eNS_URI);

		// Obtain or create and register package
		PepperFWPackageImpl thePepperFWPackage = (PepperFWPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PepperFWPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PepperFWPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		PepperInterfacePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		thePepperFWPackage.createPackageContents();

		// Initialize created meta-data
		thePepperFWPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePepperFWPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PepperFWPackage.eNS_URI, thePepperFWPackage);
		return thePepperFWPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperConverter() {
		return pepperConverterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperConverter_PepperModuleResolver() {
		return (EReference)pepperConverterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperConverter_PepperParams() {
		return (EAttribute)pepperConverterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperConverter_PepperJobs() {
		return (EReference)pepperConverterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperConverter_PepperJ2CMonitors() {
		return (EReference)pepperConverterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperConverter_Parallelized() {
		return (EAttribute)pepperConverterEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperConverter_Properties() {
		return (EAttribute)pepperConverterEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperConverter_PepperParamsURI() {
		return (EAttribute)pepperConverterEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperModuleResolver() {
		return pepperModuleResolverEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModuleResolver_PepperImporterComponentFactories() {
		return (EAttribute)pepperModuleResolverEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModuleResolver_PepperManipulatorComponentFactories() {
		return (EAttribute)pepperModuleResolverEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModuleResolver_PepperExporterComponentFactories() {
		return (EAttribute)pepperModuleResolverEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModuleResolver_TemprorariesPropertyName() {
		return (EAttribute)pepperModuleResolverEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperModuleResolver_ResourcesPropertyName() {
		return (EAttribute)pepperModuleResolverEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperJob() {
		return pepperJobEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperJob_PepperImporters() {
		return (EAttribute)pepperJobEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperJob_PepperModules() {
		return (EAttribute)pepperJobEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperJob_PepperExporters() {
		return (EAttribute)pepperJobEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperJob_Id() {
		return (EAttribute)pepperJobEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperJob_PepperModuleControllers() {
		return (EReference)pepperJobEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperJob_PepperM2MMonitors() {
		return (EReference)pepperJobEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperJob_PepperM2JMonitors() {
		return (EReference)pepperJobEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperJob_SaltProject() {
		return (EAttribute)pepperJobEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperJob_PepperJ2CMonitor() {
		return (EReference)pepperJobEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperJob_PepperJobLogger() {
		return (EReference)pepperJobEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperJob_PepperDocumentController() {
		return (EReference)pepperJobEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperJob_Properties() {
		return (EAttribute)pepperJobEClass.getEStructuralFeatures().get(11);
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
	public EReference getPepperModuleController_PepperJob() {
		return (EReference)pepperModuleControllerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperModuleController_InputPepperModuleMonitors() {
		return (EReference)pepperModuleControllerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperModuleController_OutputPepperModuleMonitors() {
		return (EReference)pepperModuleControllerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperModuleController_PepperM2JMonitor() {
		return (EReference)pepperModuleControllerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperModuleController_PepperJobLogger() {
		return (EReference)pepperModuleControllerEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperModuleController_PepperDocumentController() {
		return (EReference)pepperModuleControllerEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRunnable() {
		return runnableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperQueuedMonitor() {
		return pepperQueuedMonitorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperQueuedMonitor_OrderQueue() {
		return (EAttribute)pepperQueuedMonitorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperQueuedMonitor_Empty() {
		return (EAttribute)pepperQueuedMonitorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperFinishableMonitor() {
		return pepperFinishableMonitorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperMonitor() {
		return pepperMonitorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperMonitor_Id() {
		return (EAttribute)pepperMonitorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperMonitor_Finished() {
		return (EAttribute)pepperMonitorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperMonitor_Exceptions() {
		return (EAttribute)pepperMonitorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperJobLogger() {
		return pepperJobLoggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperJobLogger_PepperJob() {
		return (EReference)pepperJobLoggerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperDocumentController() {
		return pepperDocumentControllerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperDocumentController_PepperModuleControllers() {
		return (EReference)pepperDocumentControllerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperDocumentController_PepperJobController() {
		return (EReference)pepperDocumentControllerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperDocumentController_REMOVE_SDOCUMENT_AFTER_PROCESSING() {
		return (EAttribute)pepperDocumentControllerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperDocumentController_COMPUTE_PERFORMANCE() {
		return (EAttribute)pepperDocumentControllerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperDocumentController_AMOUNT_OF_COMPUTABLE_SDOCUMENTS() {
		return (EAttribute)pepperDocumentControllerEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPepperDocumentController_CurrentAmountOfSDocuments() {
		return (EAttribute)pepperDocumentControllerEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPEPPER_SDOCUMENT_STATUS() {
		return peppeR_SDOCUMENT_STATUSEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getBundleContext() {
		return bundleContextEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPepperModule() {
		return pepperModuleEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPepperImporter() {
		return pepperImporterEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPepperExporter() {
		return pepperExporterEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPepperParams() {
		return pepperParamsEDataType;
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
	public EDataType getPepperImporterParams() {
		return pepperImporterParamsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPepperModuleParams() {
		return pepperModuleParamsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPepperExporterParams() {
		return pepperExporterParamsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getConcurrentLinkedQueue() {
		return concurrentLinkedQueueEDataType;
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
	public EDataType getPepperConvertException() {
		return pepperConvertExceptionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPepperException() {
		return pepperExceptionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getComponentFactory() {
		return componentFactoryEDataType;
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
	public EDataType getProperties() {
		return propertiesEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperFWFactory getPepperFWFactory() {
		return (PepperFWFactory)getEFactoryInstance();
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
		pepperConverterEClass = createEClass(PEPPER_CONVERTER);
		createEReference(pepperConverterEClass, PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER);
		createEAttribute(pepperConverterEClass, PEPPER_CONVERTER__PEPPER_PARAMS);
		createEReference(pepperConverterEClass, PEPPER_CONVERTER__PEPPER_JOBS);
		createEReference(pepperConverterEClass, PEPPER_CONVERTER__PEPPER_J2C_MONITORS);
		createEAttribute(pepperConverterEClass, PEPPER_CONVERTER__PARALLELIZED);
		createEAttribute(pepperConverterEClass, PEPPER_CONVERTER__PROPERTIES);
		createEAttribute(pepperConverterEClass, PEPPER_CONVERTER__PEPPER_PARAMS_URI);

		pepperModuleResolverEClass = createEClass(PEPPER_MODULE_RESOLVER);
		createEAttribute(pepperModuleResolverEClass, PEPPER_MODULE_RESOLVER__PEPPER_IMPORTER_COMPONENT_FACTORIES);
		createEAttribute(pepperModuleResolverEClass, PEPPER_MODULE_RESOLVER__PEPPER_MANIPULATOR_COMPONENT_FACTORIES);
		createEAttribute(pepperModuleResolverEClass, PEPPER_MODULE_RESOLVER__PEPPER_EXPORTER_COMPONENT_FACTORIES);
		createEAttribute(pepperModuleResolverEClass, PEPPER_MODULE_RESOLVER__TEMPRORARIES_PROPERTY_NAME);
		createEAttribute(pepperModuleResolverEClass, PEPPER_MODULE_RESOLVER__RESOURCES_PROPERTY_NAME);

		pepperJobEClass = createEClass(PEPPER_JOB);
		createEAttribute(pepperJobEClass, PEPPER_JOB__PEPPER_IMPORTERS);
		createEAttribute(pepperJobEClass, PEPPER_JOB__PEPPER_MODULES);
		createEAttribute(pepperJobEClass, PEPPER_JOB__PEPPER_EXPORTERS);
		createEAttribute(pepperJobEClass, PEPPER_JOB__ID);
		createEReference(pepperJobEClass, PEPPER_JOB__PEPPER_MODULE_CONTROLLERS);
		createEReference(pepperJobEClass, PEPPER_JOB__PEPPER_M2M_MONITORS);
		createEReference(pepperJobEClass, PEPPER_JOB__PEPPER_M2J_MONITORS);
		createEAttribute(pepperJobEClass, PEPPER_JOB__SALT_PROJECT);
		createEReference(pepperJobEClass, PEPPER_JOB__PEPPER_J2C_MONITOR);
		createEReference(pepperJobEClass, PEPPER_JOB__PEPPER_JOB_LOGGER);
		createEReference(pepperJobEClass, PEPPER_JOB__PEPPER_DOCUMENT_CONTROLLER);
		createEAttribute(pepperJobEClass, PEPPER_JOB__PROPERTIES);

		pepperModuleControllerEClass = createEClass(PEPPER_MODULE_CONTROLLER);
		createEReference(pepperModuleControllerEClass, PEPPER_MODULE_CONTROLLER__PEPPER_JOB);
		createEReference(pepperModuleControllerEClass, PEPPER_MODULE_CONTROLLER__INPUT_PEPPER_MODULE_MONITORS);
		createEReference(pepperModuleControllerEClass, PEPPER_MODULE_CONTROLLER__OUTPUT_PEPPER_MODULE_MONITORS);
		createEReference(pepperModuleControllerEClass, PEPPER_MODULE_CONTROLLER__PEPPER_M2J_MONITOR);
		createEReference(pepperModuleControllerEClass, PEPPER_MODULE_CONTROLLER__PEPPER_JOB_LOGGER);
		createEReference(pepperModuleControllerEClass, PEPPER_MODULE_CONTROLLER__PEPPER_DOCUMENT_CONTROLLER);

		runnableEClass = createEClass(RUNNABLE);

		pepperQueuedMonitorEClass = createEClass(PEPPER_QUEUED_MONITOR);
		createEAttribute(pepperQueuedMonitorEClass, PEPPER_QUEUED_MONITOR__ORDER_QUEUE);
		createEAttribute(pepperQueuedMonitorEClass, PEPPER_QUEUED_MONITOR__EMPTY);

		pepperFinishableMonitorEClass = createEClass(PEPPER_FINISHABLE_MONITOR);

		pepperMonitorEClass = createEClass(PEPPER_MONITOR);
		createEAttribute(pepperMonitorEClass, PEPPER_MONITOR__ID);
		createEAttribute(pepperMonitorEClass, PEPPER_MONITOR__FINISHED);
		createEAttribute(pepperMonitorEClass, PEPPER_MONITOR__EXCEPTIONS);

		pepperJobLoggerEClass = createEClass(PEPPER_JOB_LOGGER);
		createEReference(pepperJobLoggerEClass, PEPPER_JOB_LOGGER__PEPPER_JOB);

		pepperDocumentControllerEClass = createEClass(PEPPER_DOCUMENT_CONTROLLER);
		createEReference(pepperDocumentControllerEClass, PEPPER_DOCUMENT_CONTROLLER__PEPPER_MODULE_CONTROLLERS);
		createEReference(pepperDocumentControllerEClass, PEPPER_DOCUMENT_CONTROLLER__PEPPER_JOB_CONTROLLER);
		createEAttribute(pepperDocumentControllerEClass, PEPPER_DOCUMENT_CONTROLLER__REMOVE_SDOCUMENT_AFTER_PROCESSING);
		createEAttribute(pepperDocumentControllerEClass, PEPPER_DOCUMENT_CONTROLLER__COMPUTE_PERFORMANCE);
		createEAttribute(pepperDocumentControllerEClass, PEPPER_DOCUMENT_CONTROLLER__AMOUNT_OF_COMPUTABLE_SDOCUMENTS);
		createEAttribute(pepperDocumentControllerEClass, PEPPER_DOCUMENT_CONTROLLER__CURRENT_AMOUNT_OF_SDOCUMENTS);

		// Create enums
		peppeR_SDOCUMENT_STATUSEEnum = createEEnum(PEPPER_SDOCUMENT_STATUS);

		// Create data types
		bundleContextEDataType = createEDataType(BUNDLE_CONTEXT);
		pepperModuleEDataType = createEDataType(PEPPER_MODULE);
		pepperImporterEDataType = createEDataType(PEPPER_IMPORTER);
		pepperExporterEDataType = createEDataType(PEPPER_EXPORTER);
		pepperParamsEDataType = createEDataType(PEPPER_PARAMS);
		uriEDataType = createEDataType(URI);
		pepperImporterParamsEDataType = createEDataType(PEPPER_IMPORTER_PARAMS);
		pepperModuleParamsEDataType = createEDataType(PEPPER_MODULE_PARAMS);
		pepperExporterParamsEDataType = createEDataType(PEPPER_EXPORTER_PARAMS);
		concurrentLinkedQueueEDataType = createEDataType(CONCURRENT_LINKED_QUEUE);
		pepperModuleExceptionEDataType = createEDataType(PEPPER_MODULE_EXCEPTION);
		pepperConvertExceptionEDataType = createEDataType(PEPPER_CONVERT_EXCEPTION);
		pepperExceptionEDataType = createEDataType(PEPPER_EXCEPTION);
		componentFactoryEDataType = createEDataType(COMPONENT_FACTORY);
		logServiceEDataType = createEDataType(LOG_SERVICE);
		propertiesEDataType = createEDataType(PROPERTIES);
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
		PepperInterfacePackage thePepperInterfacePackage = (PepperInterfacePackage)EPackage.Registry.INSTANCE.getEPackage(PepperInterfacePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		pepperJobEClass.getESuperTypes().add(this.getRunnable());
		pepperModuleControllerEClass.getESuperTypes().add(thePepperInterfacePackage.getPepperModuleController());
		pepperModuleControllerEClass.getESuperTypes().add(this.getRunnable());
		pepperQueuedMonitorEClass.getESuperTypes().add(this.getPepperMonitor());
		pepperFinishableMonitorEClass.getESuperTypes().add(this.getPepperMonitor());

		// Initialize classes and features; add operations and parameters
		initEClass(pepperConverterEClass, PepperConverter.class, "PepperConverter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPepperConverter_PepperModuleResolver(), this.getPepperModuleResolver(), null, "pepperModuleResolver", null, 1, 1, PepperConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperConverter_PepperParams(), this.getPepperParams(), "pepperParams", null, 0, 1, PepperConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperConverter_PepperJobs(), this.getPepperJob(), null, "pepperJobs", null, 0, -1, PepperConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperConverter_PepperJ2CMonitors(), this.getPepperFinishableMonitor(), null, "pepperJ2CMonitors", null, 0, -1, PepperConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperConverter_Parallelized(), ecorePackage.getEBoolean(), "parallelized", "false", 0, 1, PepperConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperConverter_Properties(), this.getProperties(), "properties", null, 0, 1, PepperConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperConverter_PepperParamsURI(), this.getURI(), "pepperParamsURI", null, 0, 1, PepperConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(pepperConverterEClass, null, "setPepperParams", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getURI(), "pepperParamUri", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperConverterEClass, null, "start", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getPepperException());

		op = addEOperation(pepperConverterEClass, null, "startPepperConvertJob", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEIntegerObject(), "id", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getPepperException());

		initEClass(pepperModuleResolverEClass, PepperModuleResolver.class, "PepperModuleResolver", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPepperModuleResolver_PepperImporterComponentFactories(), this.getComponentFactory(), "pepperImporterComponentFactories", null, 0, -1, PepperModuleResolver.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModuleResolver_PepperManipulatorComponentFactories(), this.getComponentFactory(), "pepperManipulatorComponentFactories", null, 0, -1, PepperModuleResolver.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModuleResolver_PepperExporterComponentFactories(), this.getComponentFactory(), "pepperExporterComponentFactories", null, 0, -1, PepperModuleResolver.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModuleResolver_TemprorariesPropertyName(), ecorePackage.getEString(), "temprorariesPropertyName", "PepperModuleResolver.TemprorariesURI", 0, 1, PepperModuleResolver.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperModuleResolver_ResourcesPropertyName(), ecorePackage.getEString(), "resourcesPropertyName", "PepperModuleResolver.ResourcesURI", 0, 1, PepperModuleResolver.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(pepperModuleResolverEClass, thePepperInterfacePackage.getPepperImporter(), "getPepperImporter", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPepperImporterParams(), "pepperImporterParams", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperModuleResolverEClass, thePepperInterfacePackage.getPepperManipulator(), "getPepperManipulator", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPepperModuleParams(), "pepperModuleParams", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperModuleResolverEClass, thePepperInterfacePackage.getPepperExporter(), "getPepperExporter", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPepperExporterParams(), "pepperExporterParams", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperModuleResolverEClass, null, "addPepperImporterComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getComponentFactory(), "pepperImporterComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperModuleResolverEClass, null, "removePepperImporterComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getComponentFactory(), "pepperImporterComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperModuleResolverEClass, null, "addPepperManipulatorComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getComponentFactory(), "pepperManipulatorComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperModuleResolverEClass, null, "removePepperManipulatorComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getComponentFactory(), "pepperManipulatorComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperModuleResolverEClass, null, "addPepperExporterComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getComponentFactory(), "pepperExporterComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperModuleResolverEClass, null, "removePepperExporterComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getComponentFactory(), "pepperExporterComponentFactory", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperModuleResolverEClass, this.getPepperImporter(), "getPepperImporters", 0, -1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperModuleResolverEClass, thePepperInterfacePackage.getPepperManipulator(), "getPepperManipulators", 0, -1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperModuleResolverEClass, this.getPepperExporter(), "getPepperExporters", 0, -1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperModuleResolverEClass, ecorePackage.getEString(), "getStatus", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(pepperJobEClass, PepperJob.class, "PepperJob", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPepperJob_PepperImporters(), this.getPepperImporter(), "pepperImporters", null, 1, -1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperJob_PepperModules(), this.getPepperModule(), "pepperModules", null, 0, -1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperJob_PepperExporters(), this.getPepperExporter(), "pepperExporters", null, 1, -1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperJob_Id(), ecorePackage.getEIntegerObject(), "id", null, 1, 1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperJob_PepperModuleControllers(), this.getPepperModuleController(), this.getPepperModuleController_PepperJob(), "pepperModuleControllers", null, 0, -1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperJob_PepperM2MMonitors(), this.getPepperQueuedMonitor(), null, "pepperM2MMonitors", null, 0, -1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperJob_PepperM2JMonitors(), this.getPepperFinishableMonitor(), null, "pepperM2JMonitors", null, 0, -1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperJob_SaltProject(), thePepperInterfacePackage.getSaltProject(), "saltProject", null, 1, 1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperJob_PepperJ2CMonitor(), this.getPepperFinishableMonitor(), null, "pepperJ2CMonitor", null, 0, 1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperJob_PepperJobLogger(), this.getPepperJobLogger(), this.getPepperJobLogger_PepperJob(), "pepperJobLogger", null, 0, 1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperJob_PepperDocumentController(), this.getPepperDocumentController(), this.getPepperDocumentController_PepperJobController(), "pepperDocumentController", null, 0, 1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperJob_Properties(), this.getProperties(), "properties", null, 0, 1, PepperJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(pepperJobEClass, null, "start", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getPepperModuleException());
		addEException(op, this.getPepperConvertException());

		initEClass(pepperModuleControllerEClass, PepperModuleController.class, "PepperModuleController", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPepperModuleController_PepperJob(), this.getPepperJob(), this.getPepperJob_PepperModuleControllers(), "pepperJob", null, 0, 1, PepperModuleController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperModuleController_InputPepperModuleMonitors(), this.getPepperQueuedMonitor(), null, "inputPepperModuleMonitors", null, 0, -1, PepperModuleController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperModuleController_OutputPepperModuleMonitors(), this.getPepperQueuedMonitor(), null, "outputPepperModuleMonitors", null, 0, -1, PepperModuleController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperModuleController_PepperM2JMonitor(), this.getPepperFinishableMonitor(), null, "pepperM2JMonitor", null, 0, 1, PepperModuleController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperModuleController_PepperJobLogger(), this.getPepperJobLogger(), null, "PepperJobLogger", null, 0, 1, PepperModuleController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperModuleController_PepperDocumentController(), this.getPepperDocumentController(), this.getPepperDocumentController_PepperModuleControllers(), "pepperDocumentController", null, 0, 1, PepperModuleController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(pepperModuleControllerEClass, null, "start", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getPepperModuleException());

		op = addEOperation(pepperModuleControllerEClass, null, "importCorpusStructure", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePepperInterfacePackage.getSCorpusGraph(), "sCorpusGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getPepperConvertException());

		initEClass(runnableEClass, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable.class, "Runnable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(pepperQueuedMonitorEClass, PepperQueuedMonitor.class, "PepperQueuedMonitor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPepperQueuedMonitor_OrderQueue(), this.getConcurrentLinkedQueue(), "orderQueue", null, 0, 1, PepperQueuedMonitor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperQueuedMonitor_Empty(), ecorePackage.getEBoolean(), "empty", null, 0, 1, PepperQueuedMonitor.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(pepperQueuedMonitorEClass, null, "put", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePepperInterfacePackage.getSElementId(), "sElementId", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperQueuedMonitorEClass, thePepperInterfacePackage.getSElementId(), "get", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(pepperFinishableMonitorEClass, PepperFinishableMonitor.class, "PepperFinishableMonitor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(pepperMonitorEClass, PepperMonitor.class, "PepperMonitor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPepperMonitor_Id(), ecorePackage.getEString(), "id", null, 0, 1, PepperMonitor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperMonitor_Finished(), ecorePackage.getEBoolean(), "finished", null, 0, 1, PepperMonitor.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperMonitor_Exceptions(), this.getPepperException(), "exceptions", null, 0, -1, PepperMonitor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(pepperMonitorEClass, null, "finish", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperMonitorEClass, null, "waitUntilFinished", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(pepperJobLoggerEClass, PepperJobLogger.class, "PepperJobLogger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPepperJobLogger_PepperJob(), this.getPepperJob(), this.getPepperJob_PepperJobLogger(), "pepperJob", null, 0, 1, PepperJobLogger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(pepperJobLoggerEClass, null, "setLogService", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLogService(), "logService", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperJobLoggerEClass, this.getLogService(), "getLogService", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperJobLoggerEClass, null, "logStatus", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePepperInterfacePackage.getSElementId(), "sElementId", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPEPPER_SDOCUMENT_STATUS(), "status", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "moduleName", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(pepperDocumentControllerEClass, PepperDocumentController.class, "PepperDocumentController", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPepperDocumentController_PepperModuleControllers(), this.getPepperModuleController(), this.getPepperModuleController_PepperDocumentController(), "pepperModuleControllers", null, 0, -1, PepperDocumentController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPepperDocumentController_PepperJobController(), this.getPepperJob(), this.getPepperJob_PepperDocumentController(), "pepperJobController", null, 0, 1, PepperDocumentController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperDocumentController_REMOVE_SDOCUMENT_AFTER_PROCESSING(), ecorePackage.getEBooleanObject(), "REMOVE_SDOCUMENT_AFTER_PROCESSING", "true", 0, 1, PepperDocumentController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperDocumentController_COMPUTE_PERFORMANCE(), ecorePackage.getEBooleanObject(), "COMPUTE_PERFORMANCE", "true", 0, 1, PepperDocumentController.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperDocumentController_AMOUNT_OF_COMPUTABLE_SDOCUMENTS(), ecorePackage.getEIntegerObject(), "AMOUNT_OF_COMPUTABLE_SDOCUMENTS", "10", 0, 1, PepperDocumentController.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getPepperDocumentController_CurrentAmountOfSDocuments(), ecorePackage.getEIntegerObject(), "currentAmountOfSDocuments", null, 0, 1, PepperDocumentController.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		op = addEOperation(pepperDocumentControllerEClass, null, "observeSDocument", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePepperInterfacePackage.getSElementId(), "sDocumentId", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperDocumentControllerEClass, thePepperInterfacePackage.getSElementId(), "getObservedSDocuments", 0, -1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperDocumentControllerEClass, null, "addSDocumentStatus", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePepperInterfacePackage.getSElementId(), "sDocumentId", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPepperModuleController(), "moduleController", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPEPPER_SDOCUMENT_STATUS(), "status", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperDocumentControllerEClass, ecorePackage.getEString(), "getStatus4Print", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperDocumentControllerEClass, this.getPEPPER_SDOCUMENT_STATUS(), "getStatus", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePepperInterfacePackage.getSElementId(), "sDocumentId", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperDocumentControllerEClass, this.getPEPPER_SDOCUMENT_STATUS(), "getStatus", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePepperInterfacePackage.getSElementId(), "sDocumentId", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPepperModuleController(), "pepperModuleController", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperDocumentControllerEClass, null, "finish", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(pepperDocumentControllerEClass, null, "setLogService", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLogService(), "logService", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperDocumentControllerEClass, this.getLogService(), "getLogService", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(pepperDocumentControllerEClass, null, "waitForSDocument", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(peppeR_SDOCUMENT_STATUSEEnum, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS.class, "PEPPER_SDOCUMENT_STATUS");
		addEEnumLiteral(peppeR_SDOCUMENT_STATUSEEnum, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS.NOT_STARTED);
		addEEnumLiteral(peppeR_SDOCUMENT_STATUSEEnum, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
		addEEnumLiteral(peppeR_SDOCUMENT_STATUSEEnum, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS.COMPLETED);
		addEEnumLiteral(peppeR_SDOCUMENT_STATUSEEnum, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS.DELETED);
		addEEnumLiteral(peppeR_SDOCUMENT_STATUSEEnum, de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS.FAILED);

		// Initialize data types
		initEDataType(bundleContextEDataType, BundleContext.class, "BundleContext", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperModuleEDataType, PepperModule.class, "PepperModule", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperImporterEDataType, PepperImporter.class, "PepperImporter", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperExporterEDataType, PepperExporter.class, "PepperExporter", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperParamsEDataType, PepperParams.class, "PepperParams", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(uriEDataType, org.eclipse.emf.common.util.URI.class, "URI", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperImporterParamsEDataType, ImporterParams.class, "PepperImporterParams", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperModuleParamsEDataType, ModuleParams.class, "PepperModuleParams", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperExporterParamsEDataType, ExporterParams.class, "PepperExporterParams", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(concurrentLinkedQueueEDataType, ConcurrentLinkedQueue.class, "ConcurrentLinkedQueue", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperModuleExceptionEDataType, PepperModuleException.class, "PepperModuleException", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperConvertExceptionEDataType, PepperConvertException.class, "PepperConvertException", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pepperExceptionEDataType, PepperException.class, "PepperException", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(componentFactoryEDataType, ComponentFactory.class, "ComponentFactory", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(logServiceEDataType, LogService.class, "LogService", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(propertiesEDataType, Properties.class, "Properties", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //PepperFWPackageImpl

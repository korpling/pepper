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

import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS;
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
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperInterfaceFactoryImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 */
public class PepperFWFactoryImpl extends PepperInterfaceFactoryImpl implements PepperFWFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PepperFWFactory init() {
		try {
			PepperFWFactory thePepperFWFactory = (PepperFWFactory)EPackage.Registry.INSTANCE.getEFactory("de.hub.corpling.pepper.PepperFW"); 
			if (thePepperFWFactory != null) {
				return thePepperFWFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PepperFWFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperFWFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PepperFWPackage.PEPPER_CONVERTER: return createPepperConverter();
			case PepperFWPackage.PEPPER_MODULE_RESOLVER: return createPepperModuleResolver();
			case PepperFWPackage.PEPPER_JOB: return createPepperJob();
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER: return createPepperModuleController();
			case PepperFWPackage.PEPPER_QUEUED_MONITOR: return createPepperQueuedMonitor();
			case PepperFWPackage.PEPPER_FINISHABLE_MONITOR: return createPepperFinishableMonitor();
			case PepperFWPackage.PEPPER_MONITOR: return createPepperMonitor();
			case PepperFWPackage.PEPPER_JOB_LOGGER: return createPepperJobLogger();
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER: return createPepperDocumentController();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case PepperFWPackage.PEPPER_SDOCUMENT_STATUS:
				return createPEPPER_SDOCUMENT_STATUSFromString(eDataType, initialValue);
			case PepperFWPackage.BUNDLE_CONTEXT:
				return createBundleContextFromString(eDataType, initialValue);
			case PepperFWPackage.PEPPER_MODULE:
				return createPepperModuleFromString(eDataType, initialValue);
			case PepperFWPackage.PEPPER_IMPORTER:
				return createPepperImporterFromString(eDataType, initialValue);
			case PepperFWPackage.PEPPER_EXPORTER:
				return createPepperExporterFromString(eDataType, initialValue);
			case PepperFWPackage.PEPPER_PARAMS:
				return createPepperParamsFromString(eDataType, initialValue);
			case PepperFWPackage.URI:
				return createURIFromString(eDataType, initialValue);
			case PepperFWPackage.PEPPER_IMPORTER_PARAMS:
				return createPepperImporterParamsFromString(eDataType, initialValue);
			case PepperFWPackage.PEPPER_MODULE_PARAMS:
				return createPepperModuleParamsFromString(eDataType, initialValue);
			case PepperFWPackage.PEPPER_EXPORTER_PARAMS:
				return createPepperExporterParamsFromString(eDataType, initialValue);
			case PepperFWPackage.CONCURRENT_LINKED_QUEUE:
				return createConcurrentLinkedQueueFromString(eDataType, initialValue);
			case PepperFWPackage.PEPPER_MODULE_EXCEPTION:
				return createPepperModuleExceptionFromString(eDataType, initialValue);
			case PepperFWPackage.PEPPER_CONVERT_EXCEPTION:
				return createPepperConvertExceptionFromString(eDataType, initialValue);
			case PepperFWPackage.PEPPER_EXCEPTION:
				return createPepperExceptionFromString(eDataType, initialValue);
			case PepperFWPackage.COMPONENT_FACTORY:
				return createComponentFactoryFromString(eDataType, initialValue);
			case PepperFWPackage.LOG_SERVICE:
				return createLogServiceFromString(eDataType, initialValue);
			case PepperFWPackage.PROPERTIES:
				return createPropertiesFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case PepperFWPackage.PEPPER_SDOCUMENT_STATUS:
				return convertPEPPER_SDOCUMENT_STATUSToString(eDataType, instanceValue);
			case PepperFWPackage.BUNDLE_CONTEXT:
				return convertBundleContextToString(eDataType, instanceValue);
			case PepperFWPackage.PEPPER_MODULE:
				return convertPepperModuleToString(eDataType, instanceValue);
			case PepperFWPackage.PEPPER_IMPORTER:
				return convertPepperImporterToString(eDataType, instanceValue);
			case PepperFWPackage.PEPPER_EXPORTER:
				return convertPepperExporterToString(eDataType, instanceValue);
			case PepperFWPackage.PEPPER_PARAMS:
				return convertPepperParamsToString(eDataType, instanceValue);
			case PepperFWPackage.URI:
				return convertURIToString(eDataType, instanceValue);
			case PepperFWPackage.PEPPER_IMPORTER_PARAMS:
				return convertPepperImporterParamsToString(eDataType, instanceValue);
			case PepperFWPackage.PEPPER_MODULE_PARAMS:
				return convertPepperModuleParamsToString(eDataType, instanceValue);
			case PepperFWPackage.PEPPER_EXPORTER_PARAMS:
				return convertPepperExporterParamsToString(eDataType, instanceValue);
			case PepperFWPackage.CONCURRENT_LINKED_QUEUE:
				return convertConcurrentLinkedQueueToString(eDataType, instanceValue);
			case PepperFWPackage.PEPPER_MODULE_EXCEPTION:
				return convertPepperModuleExceptionToString(eDataType, instanceValue);
			case PepperFWPackage.PEPPER_CONVERT_EXCEPTION:
				return convertPepperConvertExceptionToString(eDataType, instanceValue);
			case PepperFWPackage.PEPPER_EXCEPTION:
				return convertPepperExceptionToString(eDataType, instanceValue);
			case PepperFWPackage.COMPONENT_FACTORY:
				return convertComponentFactoryToString(eDataType, instanceValue);
			case PepperFWPackage.LOG_SERVICE:
				return convertLogServiceToString(eDataType, instanceValue);
			case PepperFWPackage.PROPERTIES:
				return convertPropertiesToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperConverter createPepperConverter() {
		PepperConverterImpl pepperConverter = new PepperConverterImpl();
		return pepperConverter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModuleResolver createPepperModuleResolver() {
		PepperModuleResolverImpl pepperModuleResolver = new PepperModuleResolverImpl();
		return pepperModuleResolver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperJob createPepperJob() {
		PepperJobImpl pepperJob = new PepperJobImpl();
		return pepperJob;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModuleController createPepperModuleController() {
		PepperModuleControllerImpl pepperModuleController = new PepperModuleControllerImpl();
		return pepperModuleController;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperQueuedMonitor createPepperQueuedMonitor() {
		PepperQueuedMonitorImpl pepperQueuedMonitor = new PepperQueuedMonitorImpl();
		return pepperQueuedMonitor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperFinishableMonitor createPepperFinishableMonitor() {
		PepperFinishableMonitorImpl pepperFinishableMonitor = new PepperFinishableMonitorImpl();
		return pepperFinishableMonitor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public PepperMonitor createPepperMonitor() 
	{
		PepperMonitorImpl pepperMonitor = new PepperMonitorImpl();
		return pepperMonitor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperJobLogger createPepperJobLogger() {
		PepperJobLoggerImpl pepperJobLogger = new PepperJobLoggerImpl();
		return pepperJobLogger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperDocumentController createPepperDocumentController() {
		PepperDocumentControllerImpl pepperDocumentController = new PepperDocumentControllerImpl();
		return pepperDocumentController;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PEPPER_SDOCUMENT_STATUS createPEPPER_SDOCUMENT_STATUSFromString(EDataType eDataType, String initialValue) {
		PEPPER_SDOCUMENT_STATUS result = PEPPER_SDOCUMENT_STATUS.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPEPPER_SDOCUMENT_STATUSToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BundleContext createBundleContextFromString(EDataType eDataType, String initialValue) {
		return (BundleContext)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBundleContextToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModule createPepperModuleFromString(EDataType eDataType, String initialValue) {
		return (PepperModule)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPepperModuleToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperImporter createPepperImporterFromString(EDataType eDataType, String initialValue) {
		return (PepperImporter)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPepperImporterToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperExporter createPepperExporterFromString(EDataType eDataType, String initialValue) {
		return (PepperExporter)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPepperExporterToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperParams createPepperParamsFromString(EDataType eDataType, String initialValue) {
		return (PepperParams)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPepperParamsToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI createURIFromString(EDataType eDataType, String initialValue) {
		return (URI)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertURIToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImporterParams createPepperImporterParamsFromString(EDataType eDataType, String initialValue) {
		return (ImporterParams)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPepperImporterParamsToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleParams createPepperModuleParamsFromString(EDataType eDataType, String initialValue) {
		return (ModuleParams)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPepperModuleParamsToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExporterParams createPepperExporterParamsFromString(EDataType eDataType, String initialValue) {
		return (ExporterParams)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPepperExporterParamsToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ConcurrentLinkedQueue createConcurrentLinkedQueueFromString(EDataType eDataType, String initialValue) {
		return (ConcurrentLinkedQueue)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConcurrentLinkedQueueToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModuleException createPepperModuleExceptionFromString(EDataType eDataType, String initialValue) {
		return (PepperModuleException)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPepperModuleExceptionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperConvertException createPepperConvertExceptionFromString(EDataType eDataType, String initialValue) {
		return (PepperConvertException)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPepperConvertExceptionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperException createPepperExceptionFromString(EDataType eDataType, String initialValue) {
		return (PepperException)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPepperExceptionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentFactory createComponentFactoryFromString(EDataType eDataType, String initialValue) {
		return (ComponentFactory)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertComponentFactoryToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogService createLogServiceFromString(EDataType eDataType, String initialValue) {
		return (LogService)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLogServiceToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Properties createPropertiesFromString(EDataType eDataType, String initialValue) {
		return (Properties)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPropertiesToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperFWPackage getPepperFWPackage() {
		return (PepperFWPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 */
	@Deprecated
	public static PepperInterfacePackage getPackage() {
		return PepperInterfacePackage.eINSTANCE;
	}
} //PepperFWFactoryImpl

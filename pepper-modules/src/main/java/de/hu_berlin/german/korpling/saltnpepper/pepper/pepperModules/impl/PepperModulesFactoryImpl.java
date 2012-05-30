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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.*;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

import org.osgi.service.log.LogService;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PepperModulesFactoryImpl extends EFactoryImpl implements PepperModulesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PepperModulesFactory init() {
		try {
			PepperModulesFactory thePepperModulesFactory = (PepperModulesFactory)EPackage.Registry.INSTANCE.getEFactory("de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules"); 
			if (thePepperModulesFactory != null) {
				return thePepperModulesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PepperModulesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModulesFactoryImpl() {
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
			case PepperModulesPackage.FORMAT_DEFINITION: return createFormatDefinition();
			case PepperModulesPackage.CORPUS_DEFINITION: return createCorpusDefinition();
			case PepperModulesPackage.EXTENSION_FACTORY_PAIR: return createExtensionFactoryPair();
			case PepperModulesPackage.PERSISTENCE_CONNECTOR: return createPersistenceConnector();
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
			case PepperModulesPackage.RETURNING_MODE:
				return createRETURNING_MODEFromString(eDataType, initialValue);
			case PepperModulesPackage.URI:
				return createURIFromString(eDataType, initialValue);
			case PepperModulesPackage.SCORPUS_GRAPH:
				return createSCorpusGraphFromString(eDataType, initialValue);
			case PepperModulesPackage.SALT_PROJECT:
				return createSaltProjectFromString(eDataType, initialValue);
			case PepperModulesPackage.SELEMENT_ID:
				return createSElementIdFromString(eDataType, initialValue);
			case PepperModulesPackage.PEPPER_MODULE_EXCEPTION:
				return createPepperModuleExceptionFromString(eDataType, initialValue);
			case PepperModulesPackage.EPACKAGE:
				return createEPackageFromString(eDataType, initialValue);
			case PepperModulesPackage.RESOURCE_FACTORY:
				return createResourceFactoryFromString(eDataType, initialValue);
			case PepperModulesPackage.LOG_SERVICE:
				return createLogServiceFromString(eDataType, initialValue);
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
			case PepperModulesPackage.RETURNING_MODE:
				return convertRETURNING_MODEToString(eDataType, instanceValue);
			case PepperModulesPackage.URI:
				return convertURIToString(eDataType, instanceValue);
			case PepperModulesPackage.SCORPUS_GRAPH:
				return convertSCorpusGraphToString(eDataType, instanceValue);
			case PepperModulesPackage.SALT_PROJECT:
				return convertSaltProjectToString(eDataType, instanceValue);
			case PepperModulesPackage.SELEMENT_ID:
				return convertSElementIdToString(eDataType, instanceValue);
			case PepperModulesPackage.PEPPER_MODULE_EXCEPTION:
				return convertPepperModuleExceptionToString(eDataType, instanceValue);
			case PepperModulesPackage.EPACKAGE:
				return convertEPackageToString(eDataType, instanceValue);
			case PepperModulesPackage.RESOURCE_FACTORY:
				return convertResourceFactoryToString(eDataType, instanceValue);
			case PepperModulesPackage.LOG_SERVICE:
				return convertLogServiceToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FormatDefinition createFormatDefinition() {
		FormatDefinitionImpl formatDefinition = new FormatDefinitionImpl();
		return formatDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorpusDefinition createCorpusDefinition() {
		CorpusDefinitionImpl corpusDefinition = new CorpusDefinitionImpl();
		return corpusDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionFactoryPair createExtensionFactoryPair() {
		ExtensionFactoryPairImpl extensionFactoryPair = new ExtensionFactoryPairImpl();
		return extensionFactoryPair;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceConnector createPersistenceConnector() {
		PersistenceConnectorImpl persistenceConnector = new PersistenceConnectorImpl();
		return persistenceConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RETURNING_MODE createRETURNING_MODEFromString(EDataType eDataType, String initialValue) {
		RETURNING_MODE result = RETURNING_MODE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRETURNING_MODEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
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
	public SCorpusGraph createSCorpusGraphFromString(EDataType eDataType, String initialValue) {
		return (SCorpusGraph)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSCorpusGraphToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SaltProject createSaltProjectFromString(EDataType eDataType, String initialValue) {
		return (SaltProject)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSaltProjectToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SElementId createSElementIdFromString(EDataType eDataType, String initialValue) {
		return (SElementId)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSElementIdToString(EDataType eDataType, Object instanceValue) {
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
	public EPackage createEPackageFromString(EDataType eDataType, String initialValue) {
		return (EPackage)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEPackageToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceFactoryImpl createResourceFactoryFromString(EDataType eDataType, String initialValue) {
		return (ResourceFactoryImpl)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertResourceFactoryToString(EDataType eDataType, Object instanceValue) {
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
	public PepperModulesPackage getPepperModulesPackage() {
		return (PepperModulesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PepperModulesPackage getPackage() {
		return PepperModulesPackage.eINSTANCE;
	}

} //PepperModulesFactoryImpl

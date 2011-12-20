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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.util;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage
 * @generated
 */
public class PepperModulesSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PepperModulesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModulesSwitch() {
		if (modelPackage == null) {
			modelPackage = PepperModulesPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case PepperModulesPackage.PEPPER_MODULE: {
				PepperModule pepperModule = (PepperModule)theEObject;
				T result = casePepperModule(pepperModule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperModulesPackage.PEPPER_IMPORTER: {
				PepperImporter pepperImporter = (PepperImporter)theEObject;
				T result = casePepperImporter(pepperImporter);
				if (result == null) result = casePepperModule(pepperImporter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperModulesPackage.PEPPER_EXPORTER: {
				PepperExporter pepperExporter = (PepperExporter)theEObject;
				T result = casePepperExporter(pepperExporter);
				if (result == null) result = casePepperModule(pepperExporter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperModulesPackage.FORMAT_DEFINITION: {
				FormatDefinition formatDefinition = (FormatDefinition)theEObject;
				T result = caseFormatDefinition(formatDefinition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperModulesPackage.CORPUS_DEFINITION: {
				CorpusDefinition corpusDefinition = (CorpusDefinition)theEObject;
				T result = caseCorpusDefinition(corpusDefinition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperModulesPackage.PEPPER_MODULE_CONTROLLER: {
				PepperModuleController pepperModuleController = (PepperModuleController)theEObject;
				T result = casePepperModuleController(pepperModuleController);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperModulesPackage.PEPPER_MANIPULATOR: {
				PepperManipulator pepperManipulator = (PepperManipulator)theEObject;
				T result = casePepperManipulator(pepperManipulator);
				if (result == null) result = casePepperModule(pepperManipulator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperModulesPackage.EXTENSION_FACTORY_PAIR: {
				ExtensionFactoryPair extensionFactoryPair = (ExtensionFactoryPair)theEObject;
				T result = caseExtensionFactoryPair(extensionFactoryPair);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperModulesPackage.PERSISTENCE_CONNECTOR: {
				PersistenceConnector persistenceConnector = (PersistenceConnector)theEObject;
				T result = casePersistenceConnector(persistenceConnector);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Module</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Module</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperModule(PepperModule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Importer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Importer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperImporter(PepperImporter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Exporter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Exporter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperExporter(PepperExporter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Format Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Format Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFormatDefinition(FormatDefinition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Corpus Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Corpus Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCorpusDefinition(CorpusDefinition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Module Controller</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Module Controller</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperModuleController(PepperModuleController object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Manipulator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Manipulator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperManipulator(PepperManipulator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Extension Factory Pair</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Extension Factory Pair</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtensionFactoryPair(ExtensionFactoryPair object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Persistence Connector</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Persistence Connector</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersistenceConnector(PersistenceConnector object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //PepperModulesSwitch

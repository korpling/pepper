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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor;

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
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage
 * @generated
 */
public class PepperFWSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PepperFWPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperFWSwitch() {
		if (modelPackage == null) {
			modelPackage = PepperFWPackage.eINSTANCE;
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
			case PepperFWPackage.PEPPER_CONVERTER: {
				PepperConverter pepperConverter = (PepperConverter)theEObject;
				T result = casePepperConverter(pepperConverter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperFWPackage.PEPPER_MODULE_RESOLVER: {
				PepperModuleResolver pepperModuleResolver = (PepperModuleResolver)theEObject;
				T result = casePepperModuleResolver(pepperModuleResolver);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperFWPackage.PEPPER_JOB: {
				PepperJob pepperJob = (PepperJob)theEObject;
				T result = casePepperJob(pepperJob);
				if (result == null) result = caseRunnable(pepperJob);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperFWPackage.PEPPER_MODULE_CONTROLLER: {
				PepperModuleController pepperModuleController = (PepperModuleController)theEObject;
				T result = casePepperModuleController(pepperModuleController);
				if (result == null) result = casePepperInterface_PepperModuleController(pepperModuleController);
				if (result == null) result = caseRunnable(pepperModuleController);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperFWPackage.RUNNABLE: {
				de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable runnable = (de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable)theEObject;
				T result = caseRunnable(runnable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperFWPackage.PEPPER_QUEUED_MONITOR: {
				PepperQueuedMonitor pepperQueuedMonitor = (PepperQueuedMonitor)theEObject;
				T result = casePepperQueuedMonitor(pepperQueuedMonitor);
				if (result == null) result = casePepperMonitor(pepperQueuedMonitor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperFWPackage.PEPPER_FINISHABLE_MONITOR: {
				PepperFinishableMonitor pepperFinishableMonitor = (PepperFinishableMonitor)theEObject;
				T result = casePepperFinishableMonitor(pepperFinishableMonitor);
				if (result == null) result = casePepperMonitor(pepperFinishableMonitor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperFWPackage.PEPPER_MONITOR: {
				PepperMonitor pepperMonitor = (PepperMonitor)theEObject;
				T result = casePepperMonitor(pepperMonitor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperFWPackage.PEPPER_JOB_LOGGER: {
				PepperJobLogger pepperJobLogger = (PepperJobLogger)theEObject;
				T result = casePepperJobLogger(pepperJobLogger);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PepperFWPackage.PEPPER_DOCUMENT_CONTROLLER: {
				PepperDocumentController pepperDocumentController = (PepperDocumentController)theEObject;
				T result = casePepperDocumentController(pepperDocumentController);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Converter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Converter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperConverter(PepperConverter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Module Resolver</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Module Resolver</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperModuleResolver(PepperModuleResolver object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Job</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Job</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperJob(PepperJob object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Runnable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Runnable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRunnable(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.Runnable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Queued Monitor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Queued Monitor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperQueuedMonitor(PepperQueuedMonitor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Finishable Monitor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Finishable Monitor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperFinishableMonitor(PepperFinishableMonitor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Monitor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Monitor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperMonitor(PepperMonitor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Job Logger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Job Logger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperJobLogger(PepperJobLogger object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pepper Document Controller</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pepper Document Controller</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePepperDocumentController(PepperDocumentController object) {
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
	public T casePepperInterface_PepperModuleController(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController object) {
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

} //PepperFWSwitch

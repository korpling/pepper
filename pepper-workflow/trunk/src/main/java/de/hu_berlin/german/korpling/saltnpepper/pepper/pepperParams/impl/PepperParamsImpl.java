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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Params</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperParamsImpl#getPepperJobParams <em>Pepper Job Params</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PepperParamsImpl extends EObjectImpl implements PepperParams {
	/**
	 * The cached value of the '{@link #getPepperJobParams() <em>Pepper Job Params</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperJobParams()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperJobParams> pepperJobParams;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperParamsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperParamsPackage.Literals.PEPPER_PARAMS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperJobParams> getPepperJobParams() {
		if (pepperJobParams == null) {
			pepperJobParams = new EObjectContainmentEList<PepperJobParams>(PepperJobParams.class, this, PepperParamsPackage.PEPPER_PARAMS__PEPPER_JOB_PARAMS);
		}
		return pepperJobParams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperParamsPackage.PEPPER_PARAMS__PEPPER_JOB_PARAMS:
				return ((InternalEList<?>)getPepperJobParams()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperParamsPackage.PEPPER_PARAMS__PEPPER_JOB_PARAMS:
				return getPepperJobParams();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PepperParamsPackage.PEPPER_PARAMS__PEPPER_JOB_PARAMS:
				getPepperJobParams().clear();
				getPepperJobParams().addAll((Collection<? extends PepperJobParams>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PepperParamsPackage.PEPPER_PARAMS__PEPPER_JOB_PARAMS:
				getPepperJobParams().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PepperParamsPackage.PEPPER_PARAMS__PEPPER_JOB_PARAMS:
				return pepperJobParams != null && !pepperJobParams.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PepperParamsImpl

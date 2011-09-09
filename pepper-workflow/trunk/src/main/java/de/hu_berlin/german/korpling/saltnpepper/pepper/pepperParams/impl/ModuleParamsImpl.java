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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Module Params</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ModuleParamsImpl#getModuleName <em>Module Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ModuleParamsImpl#getSpecialParams <em>Special Params</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModuleParamsImpl extends EObjectImpl implements ModuleParams {
	/**
	 * The default value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleName()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleName()
	 * @generated
	 * @ordered
	 */
	protected String moduleName = MODULE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpecialParams() <em>Special Params</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecialParams()
	 * @generated
	 * @ordered
	 */
	protected static final URI SPECIAL_PARAMS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecialParams() <em>Special Params</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecialParams()
	 * @generated
	 * @ordered
	 */
	protected URI specialParams = SPECIAL_PARAMS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModuleParamsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperParamsPackage.Literals.MODULE_PARAMS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModuleName(String newModuleName) {
		String oldModuleName = moduleName;
		moduleName = newModuleName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperParamsPackage.MODULE_PARAMS__MODULE_NAME, oldModuleName, moduleName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI getSpecialParams() {
		return specialParams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpecialParams(URI newSpecialParams) {
		URI oldSpecialParams = specialParams;
		specialParams = newSpecialParams;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperParamsPackage.MODULE_PARAMS__SPECIAL_PARAMS, oldSpecialParams, specialParams));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperParamsPackage.MODULE_PARAMS__MODULE_NAME:
				return getModuleName();
			case PepperParamsPackage.MODULE_PARAMS__SPECIAL_PARAMS:
				return getSpecialParams();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PepperParamsPackage.MODULE_PARAMS__MODULE_NAME:
				setModuleName((String)newValue);
				return;
			case PepperParamsPackage.MODULE_PARAMS__SPECIAL_PARAMS:
				setSpecialParams((URI)newValue);
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
			case PepperParamsPackage.MODULE_PARAMS__MODULE_NAME:
				setModuleName(MODULE_NAME_EDEFAULT);
				return;
			case PepperParamsPackage.MODULE_PARAMS__SPECIAL_PARAMS:
				setSpecialParams(SPECIAL_PARAMS_EDEFAULT);
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
			case PepperParamsPackage.MODULE_PARAMS__MODULE_NAME:
				return MODULE_NAME_EDEFAULT == null ? moduleName != null : !MODULE_NAME_EDEFAULT.equals(moduleName);
			case PepperParamsPackage.MODULE_PARAMS__SPECIAL_PARAMS:
				return SPECIAL_PARAMS_EDEFAULT == null ? specialParams != null : !SPECIAL_PARAMS_EDEFAULT.equals(specialParams);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (moduleName: ");
		result.append(moduleName);
		result.append(", specialParams: ");
		result.append(specialParams);
		result.append(')');
		return result.toString();
	}

} //ModuleParamsImpl

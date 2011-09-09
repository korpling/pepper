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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Importer Params</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ImporterParamsImpl#getFormatName <em>Format Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ImporterParamsImpl#getFormatVersion <em>Format Version</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.ImporterParamsImpl#getSourcePath <em>Source Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ImporterParamsImpl extends ModuleParamsImpl implements ImporterParams {
	/**
	 * The default value of the '{@link #getFormatName() <em>Format Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormatName()
	 * @generated
	 * @ordered
	 */
	protected static final String FORMAT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFormatName() <em>Format Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormatName()
	 * @generated
	 * @ordered
	 */
	protected String formatName = FORMAT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFormatVersion() <em>Format Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormatVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String FORMAT_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFormatVersion() <em>Format Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormatVersion()
	 * @generated
	 * @ordered
	 */
	protected String formatVersion = FORMAT_VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourcePath() <em>Source Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourcePath()
	 * @generated
	 * @ordered
	 */
	protected static final URI SOURCE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourcePath() <em>Source Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourcePath()
	 * @generated
	 * @ordered
	 */
	protected URI sourcePath = SOURCE_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImporterParamsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperParamsPackage.Literals.IMPORTER_PARAMS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFormatName() {
		return formatName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormatName(String newFormatName) {
		String oldFormatName = formatName;
		formatName = newFormatName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperParamsPackage.IMPORTER_PARAMS__FORMAT_NAME, oldFormatName, formatName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFormatVersion() {
		return formatVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormatVersion(String newFormatVersion) {
		String oldFormatVersion = formatVersion;
		formatVersion = newFormatVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperParamsPackage.IMPORTER_PARAMS__FORMAT_VERSION, oldFormatVersion, formatVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI getSourcePath() {
		return sourcePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourcePath(URI newSourcePath) {
		URI oldSourcePath = sourcePath;
		sourcePath = newSourcePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperParamsPackage.IMPORTER_PARAMS__SOURCE_PATH, oldSourcePath, sourcePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperParamsPackage.IMPORTER_PARAMS__FORMAT_NAME:
				return getFormatName();
			case PepperParamsPackage.IMPORTER_PARAMS__FORMAT_VERSION:
				return getFormatVersion();
			case PepperParamsPackage.IMPORTER_PARAMS__SOURCE_PATH:
				return getSourcePath();
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
			case PepperParamsPackage.IMPORTER_PARAMS__FORMAT_NAME:
				setFormatName((String)newValue);
				return;
			case PepperParamsPackage.IMPORTER_PARAMS__FORMAT_VERSION:
				setFormatVersion((String)newValue);
				return;
			case PepperParamsPackage.IMPORTER_PARAMS__SOURCE_PATH:
				setSourcePath((URI)newValue);
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
			case PepperParamsPackage.IMPORTER_PARAMS__FORMAT_NAME:
				setFormatName(FORMAT_NAME_EDEFAULT);
				return;
			case PepperParamsPackage.IMPORTER_PARAMS__FORMAT_VERSION:
				setFormatVersion(FORMAT_VERSION_EDEFAULT);
				return;
			case PepperParamsPackage.IMPORTER_PARAMS__SOURCE_PATH:
				setSourcePath(SOURCE_PATH_EDEFAULT);
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
			case PepperParamsPackage.IMPORTER_PARAMS__FORMAT_NAME:
				return FORMAT_NAME_EDEFAULT == null ? formatName != null : !FORMAT_NAME_EDEFAULT.equals(formatName);
			case PepperParamsPackage.IMPORTER_PARAMS__FORMAT_VERSION:
				return FORMAT_VERSION_EDEFAULT == null ? formatVersion != null : !FORMAT_VERSION_EDEFAULT.equals(formatVersion);
			case PepperParamsPackage.IMPORTER_PARAMS__SOURCE_PATH:
				return SOURCE_PATH_EDEFAULT == null ? sourcePath != null : !SOURCE_PATH_EDEFAULT.equals(sourcePath);
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
		result.append(" (formatName: ");
		result.append(formatName);
		result.append(", formatVersion: ");
		result.append(formatVersion);
		result.append(", sourcePath: ");
		result.append(sourcePath);
		result.append(')');
		return result.toString();
	}

} //ImporterParamsImpl

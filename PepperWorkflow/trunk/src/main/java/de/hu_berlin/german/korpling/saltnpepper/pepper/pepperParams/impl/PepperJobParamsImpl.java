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
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Job Params</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperJobParamsImpl#getImporterParams <em>Importer Params</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperJobParamsImpl#getModuleParams <em>Module Params</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperJobParamsImpl#getExporterParams <em>Exporter Params</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.impl.PepperJobParamsImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PepperJobParamsImpl extends EObjectImpl implements PepperJobParams {
	/**
	 * The cached value of the '{@link #getImporterParams() <em>Importer Params</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImporterParams()
	 * @generated
	 * @ordered
	 */
	protected EList<ImporterParams> importerParams;

	/**
	 * The cached value of the '{@link #getModuleParams() <em>Module Params</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleParams()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleParams> moduleParams;

	/**
	 * The cached value of the '{@link #getExporterParams() <em>Exporter Params</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExporterParams()
	 * @generated
	 * @ordered
	 */
	protected EList<ExporterParams> exporterParams;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final Integer ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected Integer id = ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperJobParamsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperParamsPackage.Literals.PEPPER_JOB_PARAMS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ImporterParams> getImporterParams() {
		if (importerParams == null) {
			importerParams = new EObjectContainmentEList<ImporterParams>(ImporterParams.class, this, PepperParamsPackage.PEPPER_JOB_PARAMS__IMPORTER_PARAMS);
		}
		return importerParams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ModuleParams> getModuleParams() {
		if (moduleParams == null) {
			moduleParams = new EObjectContainmentEList<ModuleParams>(ModuleParams.class, this, PepperParamsPackage.PEPPER_JOB_PARAMS__MODULE_PARAMS);
		}
		return moduleParams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExporterParams> getExporterParams() {
		if (exporterParams == null) {
			exporterParams = new EObjectContainmentEList<ExporterParams>(ExporterParams.class, this, PepperParamsPackage.PEPPER_JOB_PARAMS__EXPORTER_PARAMS);
		}
		return exporterParams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(Integer newId) {
		Integer oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperParamsPackage.PEPPER_JOB_PARAMS__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperParamsPackage.PEPPER_JOB_PARAMS__IMPORTER_PARAMS:
				return ((InternalEList<?>)getImporterParams()).basicRemove(otherEnd, msgs);
			case PepperParamsPackage.PEPPER_JOB_PARAMS__MODULE_PARAMS:
				return ((InternalEList<?>)getModuleParams()).basicRemove(otherEnd, msgs);
			case PepperParamsPackage.PEPPER_JOB_PARAMS__EXPORTER_PARAMS:
				return ((InternalEList<?>)getExporterParams()).basicRemove(otherEnd, msgs);
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
			case PepperParamsPackage.PEPPER_JOB_PARAMS__IMPORTER_PARAMS:
				return getImporterParams();
			case PepperParamsPackage.PEPPER_JOB_PARAMS__MODULE_PARAMS:
				return getModuleParams();
			case PepperParamsPackage.PEPPER_JOB_PARAMS__EXPORTER_PARAMS:
				return getExporterParams();
			case PepperParamsPackage.PEPPER_JOB_PARAMS__ID:
				return getId();
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
			case PepperParamsPackage.PEPPER_JOB_PARAMS__IMPORTER_PARAMS:
				getImporterParams().clear();
				getImporterParams().addAll((Collection<? extends ImporterParams>)newValue);
				return;
			case PepperParamsPackage.PEPPER_JOB_PARAMS__MODULE_PARAMS:
				getModuleParams().clear();
				getModuleParams().addAll((Collection<? extends ModuleParams>)newValue);
				return;
			case PepperParamsPackage.PEPPER_JOB_PARAMS__EXPORTER_PARAMS:
				getExporterParams().clear();
				getExporterParams().addAll((Collection<? extends ExporterParams>)newValue);
				return;
			case PepperParamsPackage.PEPPER_JOB_PARAMS__ID:
				setId((Integer)newValue);
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
			case PepperParamsPackage.PEPPER_JOB_PARAMS__IMPORTER_PARAMS:
				getImporterParams().clear();
				return;
			case PepperParamsPackage.PEPPER_JOB_PARAMS__MODULE_PARAMS:
				getModuleParams().clear();
				return;
			case PepperParamsPackage.PEPPER_JOB_PARAMS__EXPORTER_PARAMS:
				getExporterParams().clear();
				return;
			case PepperParamsPackage.PEPPER_JOB_PARAMS__ID:
				setId(ID_EDEFAULT);
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
			case PepperParamsPackage.PEPPER_JOB_PARAMS__IMPORTER_PARAMS:
				return importerParams != null && !importerParams.isEmpty();
			case PepperParamsPackage.PEPPER_JOB_PARAMS__MODULE_PARAMS:
				return moduleParams != null && !moduleParams.isEmpty();
			case PepperParamsPackage.PEPPER_JOB_PARAMS__EXPORTER_PARAMS:
				return exporterParams != null && !exporterParams.isEmpty();
			case PepperParamsPackage.PEPPER_JOB_PARAMS__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
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
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //PepperJobParamsImpl

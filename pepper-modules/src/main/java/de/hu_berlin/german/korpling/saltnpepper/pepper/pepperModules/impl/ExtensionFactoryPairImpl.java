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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.ExtensionFactoryPair;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extension Factory Pair</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.ExtensionFactoryPairImpl#getFileExtension <em>File Extension</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.ExtensionFactoryPairImpl#getResourceFactory <em>Resource Factory</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExtensionFactoryPairImpl extends EObjectImpl implements ExtensionFactoryPair {
	/**
	 * The default value of the '{@link #getFileExtension() <em>File Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileExtension()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_EXTENSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileExtension() <em>File Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileExtension()
	 * @generated
	 * @ordered
	 */
	protected String fileExtension = FILE_EXTENSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getResourceFactory() <em>Resource Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceFactory()
	 * @generated
	 * @ordered
	 */
	protected static final ResourceFactoryImpl RESOURCE_FACTORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResourceFactory() <em>Resource Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceFactory()
	 * @generated
	 * @ordered
	 */
	protected ResourceFactoryImpl resourceFactory = RESOURCE_FACTORY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtensionFactoryPairImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperModulesPackage.Literals.EXTENSION_FACTORY_PAIR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileExtension(String newFileExtension) {
		String oldFileExtension = fileExtension;
		fileExtension = newFileExtension;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.EXTENSION_FACTORY_PAIR__FILE_EXTENSION, oldFileExtension, fileExtension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceFactoryImpl getResourceFactory() {
		return resourceFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceFactory(ResourceFactoryImpl newResourceFactory) {
		ResourceFactoryImpl oldResourceFactory = resourceFactory;
		resourceFactory = newResourceFactory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.EXTENSION_FACTORY_PAIR__RESOURCE_FACTORY, oldResourceFactory, resourceFactory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperModulesPackage.EXTENSION_FACTORY_PAIR__FILE_EXTENSION:
				return getFileExtension();
			case PepperModulesPackage.EXTENSION_FACTORY_PAIR__RESOURCE_FACTORY:
				return getResourceFactory();
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
			case PepperModulesPackage.EXTENSION_FACTORY_PAIR__FILE_EXTENSION:
				setFileExtension((String)newValue);
				return;
			case PepperModulesPackage.EXTENSION_FACTORY_PAIR__RESOURCE_FACTORY:
				setResourceFactory((ResourceFactoryImpl)newValue);
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
			case PepperModulesPackage.EXTENSION_FACTORY_PAIR__FILE_EXTENSION:
				setFileExtension(FILE_EXTENSION_EDEFAULT);
				return;
			case PepperModulesPackage.EXTENSION_FACTORY_PAIR__RESOURCE_FACTORY:
				setResourceFactory(RESOURCE_FACTORY_EDEFAULT);
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
			case PepperModulesPackage.EXTENSION_FACTORY_PAIR__FILE_EXTENSION:
				return FILE_EXTENSION_EDEFAULT == null ? fileExtension != null : !FILE_EXTENSION_EDEFAULT.equals(fileExtension);
			case PepperModulesPackage.EXTENSION_FACTORY_PAIR__RESOURCE_FACTORY:
				return RESOURCE_FACTORY_EDEFAULT == null ? resourceFactory != null : !RESOURCE_FACTORY_EDEFAULT.equals(resourceFactory);
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
		result.append(" (fileExtension: ");
		result.append(fileExtension);
		result.append(", resourceFactory: ");
		result.append(resourceFactory);
		result.append(')');
		return result.toString();
	}

} //ExtensionFactoryPairImpl

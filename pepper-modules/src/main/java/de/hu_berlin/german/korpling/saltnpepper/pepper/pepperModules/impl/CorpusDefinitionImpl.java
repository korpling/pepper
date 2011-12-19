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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Corpus Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.CorpusDefinitionImpl#getCorpusPath <em>Corpus Path</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.CorpusDefinitionImpl#getFormatDefinition <em>Format Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CorpusDefinitionImpl extends EObjectImpl implements CorpusDefinition {
	/**
	 * The default value of the '{@link #getCorpusPath() <em>Corpus Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorpusPath()
	 * @generated
	 * @ordered
	 */
	protected static final URI CORPUS_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCorpusPath() <em>Corpus Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorpusPath()
	 * @generated
	 * @ordered
	 */
	protected URI corpusPath = CORPUS_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFormatDefinition() <em>Format Definition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormatDefinition()
	 * @generated
	 * @ordered
	 */
	protected FormatDefinition formatDefinition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CorpusDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperModulesPackage.Literals.CORPUS_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI getCorpusPath() {
		return corpusPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorpusPath(URI newCorpusPath) {
		URI oldCorpusPath = corpusPath;
		corpusPath = newCorpusPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.CORPUS_DEFINITION__CORPUS_PATH, oldCorpusPath, corpusPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FormatDefinition getFormatDefinition() {
		return formatDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFormatDefinition(FormatDefinition newFormatDefinition, NotificationChain msgs) {
		FormatDefinition oldFormatDefinition = formatDefinition;
		formatDefinition = newFormatDefinition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperModulesPackage.CORPUS_DEFINITION__FORMAT_DEFINITION, oldFormatDefinition, newFormatDefinition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormatDefinition(FormatDefinition newFormatDefinition) {
		if (newFormatDefinition != formatDefinition) {
			NotificationChain msgs = null;
			if (formatDefinition != null)
				msgs = ((InternalEObject)formatDefinition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PepperModulesPackage.CORPUS_DEFINITION__FORMAT_DEFINITION, null, msgs);
			if (newFormatDefinition != null)
				msgs = ((InternalEObject)newFormatDefinition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PepperModulesPackage.CORPUS_DEFINITION__FORMAT_DEFINITION, null, msgs);
			msgs = basicSetFormatDefinition(newFormatDefinition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.CORPUS_DEFINITION__FORMAT_DEFINITION, newFormatDefinition, newFormatDefinition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperModulesPackage.CORPUS_DEFINITION__FORMAT_DEFINITION:
				return basicSetFormatDefinition(null, msgs);
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
			case PepperModulesPackage.CORPUS_DEFINITION__CORPUS_PATH:
				return getCorpusPath();
			case PepperModulesPackage.CORPUS_DEFINITION__FORMAT_DEFINITION:
				return getFormatDefinition();
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
			case PepperModulesPackage.CORPUS_DEFINITION__CORPUS_PATH:
				setCorpusPath((URI)newValue);
				return;
			case PepperModulesPackage.CORPUS_DEFINITION__FORMAT_DEFINITION:
				setFormatDefinition((FormatDefinition)newValue);
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
			case PepperModulesPackage.CORPUS_DEFINITION__CORPUS_PATH:
				setCorpusPath(CORPUS_PATH_EDEFAULT);
				return;
			case PepperModulesPackage.CORPUS_DEFINITION__FORMAT_DEFINITION:
				setFormatDefinition((FormatDefinition)null);
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
			case PepperModulesPackage.CORPUS_DEFINITION__CORPUS_PATH:
				return CORPUS_PATH_EDEFAULT == null ? corpusPath != null : !CORPUS_PATH_EDEFAULT.equals(corpusPath);
			case PepperModulesPackage.CORPUS_DEFINITION__FORMAT_DEFINITION:
				return formatDefinition != null;
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
		result.append(" (corpusPath: ");
		result.append(corpusPath);
		result.append(')');
		return result.toString();
	}

} //CorpusDefinitionImpl

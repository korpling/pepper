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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.PepperXSLTExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XSLTTransformer;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesPackage;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper XSLT Exporter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl.PepperXSLTExporterImpl#getXsltTransformer <em>Xslt Transformer</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PepperXSLTExporterImpl extends PepperExporterImpl implements PepperXSLTExporter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected PepperXSLTExporterImpl() {
		super();
		init();
	}
	
	protected void init()
	{
		this.setXsltTransformer(XsltModulesFactory.eINSTANCE.createXSLTTransformer());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return XsltModulesPackage.Literals.PEPPER_XSLT_EXPORTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public XSLTTransformer getXsltTransformer() 
	{
		if (super.getPersistenceConnector()== null)
			return(null);
		return((XSLTTransformer)super.getPersistenceConnector());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXsltTransformer(XSLTTransformer newXsltTransformer, NotificationChain msgs) {
		// TODO: implement this method to set the contained 'Xslt Transformer' containment reference
		// -> this method is automatically invoked to keep the containment relationship in synch
		// -> do not modify other features
		// -> return msgs, after adding any generated Notification to it (if it is null, a NotificationChain object must be created first)
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setXsltTransformer(XSLTTransformer newXsltTransformer) 
	{
		super.setPersistenceConnector(newXsltTransformer);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case XsltModulesPackage.PEPPER_XSLT_EXPORTER__XSLT_TRANSFORMER:
				return basicSetXsltTransformer(null, msgs);
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
			case XsltModulesPackage.PEPPER_XSLT_EXPORTER__XSLT_TRANSFORMER:
				return getXsltTransformer();
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
			case XsltModulesPackage.PEPPER_XSLT_EXPORTER__XSLT_TRANSFORMER:
				setXsltTransformer((XSLTTransformer)newValue);
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
			case XsltModulesPackage.PEPPER_XSLT_EXPORTER__XSLT_TRANSFORMER:
				setXsltTransformer((XSLTTransformer)null);
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
			case XsltModulesPackage.PEPPER_XSLT_EXPORTER__XSLT_TRANSFORMER:
				return getXsltTransformer() != null;
		}
		return super.eIsSet(featureID);
	}

} //PepperXSLTExporterImpl

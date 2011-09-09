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
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.ExtensionFactoryPair;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PersistenceConnector;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Persistence Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PersistenceConnectorImpl#getExtensionFactoryPairs <em>Extension Factory Pairs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PersistenceConnectorImpl extends EObjectImpl implements PersistenceConnector {
	/**
	 * The cached value of the '{@link #getExtensionFactoryPairs() <em>Extension Factory Pairs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensionFactoryPairs()
	 * @generated
	 * @ordered
	 */
	protected EList<ExtensionFactoryPair> extensionFactoryPairs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PersistenceConnectorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperInterfacePackage.Literals.PERSISTENCE_CONNECTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExtensionFactoryPair> getExtensionFactoryPairs() {
		if (extensionFactoryPairs == null) {
			extensionFactoryPairs = new EObjectResolvingEList<ExtensionFactoryPair>(ExtensionFactoryPair.class, this, PepperInterfacePackage.PERSISTENCE_CONNECTOR__EXTENSION_FACTORY_PAIRS);
		}
		return extensionFactoryPairs;
	}
	
	/**
	 * Returns a resource object, by searching through given ExtensionResourcePairs for
	 * the given resource URI.
	 */
	public Resource getResource(URI resourceURI) 
	{
		if(	(this.getExtensionFactoryPairs()== null)||
			(this.getExtensionFactoryPairs().size()== 0))
			throw new PepperModuleException("Cannot return a resource, because no fileExtension-factory-pairs are given.");
		
		ResourceSet resourceSet = new ResourceSetImpl();
		for (ExtensionFactoryPair pair: this.getExtensionFactoryPairs())
		{
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(pair.getFileExtension(), pair.getResourceFactory());
		}	
		Resource resource = null;
		try {
			resource = resourceSet.getResource(resourceURI, true);
		} catch (Throwable fileNotFoundException) {
			resource = resourceSet.createResource(resourceURI);
		}
		return resource;
	}

	/**
	 * Stores the given model object as resource into the given modelURI and
	 * returns the created resource object.
	 * @param modelObject object which shall be stored
	 * @param modelURI place to where the model shall be stored
	 * @return the created resource
	 */
	public Resource save(EObject modelObject, URI modelURI)
	{
		Resource source= null;
		if (modelObject== null)
			throw new PepperModuleException("Cannot save the given model object into uri, because model object is empty.");
		if (modelURI== null)
			throw new PepperModuleException("Cannot save the given model object into uri, because model uri is empty.");
		//source model
		source = getResource(modelURI);
		System.out.println("source: "+ source);
		source.getContents().add(modelObject);
		try {
			source.save(null);
		} catch (IOException e) 
		{
			throw new PepperModuleException("Cannot save the given model object '"+modelObject+"' to given uri '"+modelURI+"' ", e);
		}
		return(source);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperInterfacePackage.PERSISTENCE_CONNECTOR__EXTENSION_FACTORY_PAIRS:
				return getExtensionFactoryPairs();
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
			case PepperInterfacePackage.PERSISTENCE_CONNECTOR__EXTENSION_FACTORY_PAIRS:
				getExtensionFactoryPairs().clear();
				getExtensionFactoryPairs().addAll((Collection<? extends ExtensionFactoryPair>)newValue);
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
			case PepperInterfacePackage.PERSISTENCE_CONNECTOR__EXTENSION_FACTORY_PAIRS:
				getExtensionFactoryPairs().clear();
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
			case PepperInterfacePackage.PERSISTENCE_CONNECTOR__EXTENSION_FACTORY_PAIRS:
				return extensionFactoryPairs != null && !extensionFactoryPairs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PersistenceConnectorImpl

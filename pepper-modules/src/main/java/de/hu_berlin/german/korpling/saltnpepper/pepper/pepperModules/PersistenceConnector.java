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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PersistenceConnector#getExtensionFactoryPairs <em>Extension Factory Pairs</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage#getPersistenceConnector()
 * @model
 * @generated
 */
public interface PersistenceConnector extends EObject {
	/**
	 * Returns the value of the '<em><b>Extension Factory Pairs</b></em>' reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.ExtensionFactoryPair}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension Factory Pairs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension Factory Pairs</em>' reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage#getPersistenceConnector_ExtensionFactoryPairs()
	 * @model
	 * @generated
	 */
	EList<ExtensionFactoryPair> getExtensionFactoryPairs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model modelURIDataType="de.hub.corpling.pepper.pepperInterface.URI"
	 * @generated
	 */
	Resource save(EObject modelObject, URI modelURI);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model resourceURIDataType="de.hub.corpling.pepper.pepperInterface.URI"
	 * @generated
	 */
	Resource getResource(URI resourceURI);

} // PersistenceConnector

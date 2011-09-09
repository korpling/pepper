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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.ComponentFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pepper Module Resolver</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperImporterComponentFactories <em>Pepper Importer Component Factories</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperManipulatorComponentFactories <em>Pepper Manipulator Component Factories</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperExporterComponentFactories <em>Pepper Exporter Component Factories</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getTemprorariesPropertyName <em>Temproraries Property Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getResourcesPropertyName <em>Resources Property Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleResolver()
 * @model
 * @generated
 */
public interface PepperModuleResolver extends EObject {
	/**
	 * Returns the value of the '<em><b>Pepper Importer Component Factories</b></em>' attribute list.
	 * The list contents are of type {@link org.osgi.service.component.ComponentFactory}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Importer Component Factories</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Importer Component Factories</em>' attribute list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleResolver_PepperImporterComponentFactories()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.ComponentFactory"
	 * @generated
	 */
	EList<ComponentFactory> getPepperImporterComponentFactories();

	/**
	 * Returns the value of the '<em><b>Pepper Manipulator Component Factories</b></em>' attribute list.
	 * The list contents are of type {@link org.osgi.service.component.ComponentFactory}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Manipulator Component Factories</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Manipulator Component Factories</em>' attribute list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleResolver_PepperManipulatorComponentFactories()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.ComponentFactory"
	 * @generated
	 */
	EList<ComponentFactory> getPepperManipulatorComponentFactories();

	/**
	 * Returns the value of the '<em><b>Pepper Exporter Component Factories</b></em>' attribute list.
	 * The list contents are of type {@link org.osgi.service.component.ComponentFactory}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Exporter Component Factories</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Exporter Component Factories</em>' attribute list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleResolver_PepperExporterComponentFactories()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.ComponentFactory"
	 * @generated
	 */
	EList<ComponentFactory> getPepperExporterComponentFactories();

	/**
	 * Returns the value of the '<em><b>Temproraries Property Name</b></em>' attribute.
	 * The default value is <code>"PepperModuleResolver.TemprorariesURI"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temproraries Property Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temproraries Property Name</em>' attribute.
	 * @see #setTemprorariesPropertyName(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleResolver_TemprorariesPropertyName()
	 * @model default="PepperModuleResolver.TemprorariesURI"
	 * @generated
	 */
	String getTemprorariesPropertyName();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getTemprorariesPropertyName <em>Temproraries Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temproraries Property Name</em>' attribute.
	 * @see #getTemprorariesPropertyName()
	 * @generated
	 */
	void setTemprorariesPropertyName(String value);

	/**
	 * Returns the value of the '<em><b>Resources Property Name</b></em>' attribute.
	 * The default value is <code>"PepperModuleResolver.ResourcesURI"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resources Property Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resources Property Name</em>' attribute.
	 * @see #setResourcesPropertyName(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModuleResolver_ResourcesPropertyName()
	 * @model default="PepperModuleResolver.ResourcesURI"
	 * @generated
	 */
	String getResourcesPropertyName();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getResourcesPropertyName <em>Resources Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resources Property Name</em>' attribute.
	 * @see #getResourcesPropertyName()
	 * @generated
	 */
	void setResourcesPropertyName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pepperImporterParamsDataType="de.hub.corpling.pepper.pepperFW.PepperImporterParams"
	 * @generated
	 */
	PepperImporter getPepperImporter(ImporterParams pepperImporterParams);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pepperModuleParamsDataType="de.hub.corpling.pepper.pepperFW.PepperModuleParams"
	 * @generated
	 */
	PepperManipulator getPepperManipulator(ModuleParams pepperModuleParams);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pepperExporterParamsDataType="de.hub.corpling.pepper.pepperFW.PepperExporterParams"
	 * @generated
	 */
	PepperExporter getPepperExporter(ExporterParams pepperExporterParams);

	/**
	 * Adds a new pepperImporterComponentFactory to internal list, which shall create new PepperImporter-objects.
	 * @param pepperImporterComponentFactory factory which shall create new PepperImporter-objects
	 * @model pepperImporterComponentFactoryDataType="de.hub.corpling.pepper.pepperFW.ComponentFactory"
	 * @generated
	 */
	void addPepperImporterComponentFactory(ComponentFactory pepperImporterComponentFactory);

	/**
	 * Removes the given factory from internal list.
	 * @param pepperImporterComponentFactory factory which shall create new PepperImporter-objects
	 * @model pepperImporterComponentFactoryDataType="de.hub.corpling.pepper.pepperFW.ComponentFactory"
	 * @generated
	 */
	void removePepperImporterComponentFactory(ComponentFactory pepperImporterComponentFactory);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pepperManipulatorComponentFactoryDataType="de.hub.corpling.pepper.pepperFW.ComponentFactory"
	 * @generated
	 */
	void addPepperManipulatorComponentFactory(ComponentFactory pepperManipulatorComponentFactory);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pepperManipulatorComponentFactoryDataType="de.hub.corpling.pepper.pepperFW.ComponentFactory"
	 * @generated
	 */
	void removePepperManipulatorComponentFactory(ComponentFactory pepperManipulatorComponentFactory);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pepperExporterComponentFactoryDataType="de.hub.corpling.pepper.pepperFW.ComponentFactory"
	 * @generated
	 */
	void addPepperExporterComponentFactory(ComponentFactory pepperExporterComponentFactory);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pepperExporterComponentFactoryDataType="de.hub.corpling.pepper.pepperFW.ComponentFactory"
	 * @generated
	 */
	void removePepperExporterComponentFactory(ComponentFactory pepperExporterComponentFactory);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="de.hub.corpling.pepper.pepperFW.PepperImporter"
	 * @generated
	 */
	EList<PepperImporter> getPepperImporters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<PepperManipulator> getPepperManipulators();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="de.hub.corpling.pepper.pepperFW.PepperExporter"
	 * @generated
	 */
	EList<PepperExporter> getPepperExporters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getStatus();

} // PepperModuleResolver
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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams;

import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Importer Params</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getFormatName <em>Format Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getFormatVersion <em>Format Version</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getSourcePath <em>Source Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage#getImporterParams()
 * @model
 * @generated
 */
public interface ImporterParams extends ModuleParams {
	/**
	 * Returns the value of the '<em><b>Format Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Format Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format Name</em>' attribute.
	 * @see #setFormatName(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage#getImporterParams_FormatName()
	 * @model
	 * @generated
	 */
	String getFormatName();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getFormatName <em>Format Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format Name</em>' attribute.
	 * @see #getFormatName()
	 * @generated
	 */
	void setFormatName(String value);

	/**
	 * Returns the value of the '<em><b>Format Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Format Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format Version</em>' attribute.
	 * @see #setFormatVersion(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage#getImporterParams_FormatVersion()
	 * @model
	 * @generated
	 */
	String getFormatVersion();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getFormatVersion <em>Format Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format Version</em>' attribute.
	 * @see #getFormatVersion()
	 * @generated
	 */
	void setFormatVersion(String value);

	/**
	 * Returns the value of the '<em><b>Source Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Path</em>' attribute.
	 * @see #setSourcePath(URI)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage#getImporterParams_SourcePath()
	 * @model dataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.URI"
	 * @generated
	 */
	URI getSourcePath();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams#getSourcePath <em>Source Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Path</em>' attribute.
	 * @see #getSourcePath()
	 * @generated
	 */
	void setSourcePath(URI value);

} // ImporterParams

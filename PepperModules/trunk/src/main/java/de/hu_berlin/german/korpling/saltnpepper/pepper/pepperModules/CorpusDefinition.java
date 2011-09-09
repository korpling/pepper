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

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Corpus Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition#getCorpusPath <em>Corpus Path</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition#getFormatDefinition <em>Format Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage#getCorpusDefinition()
 * @model
 * @generated
 */
public interface CorpusDefinition extends EObject {
	/**
	 * Returns the value of the '<em><b>Corpus Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Corpus Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Corpus Path</em>' attribute.
	 * @see #setCorpusPath(URI)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage#getCorpusDefinition_CorpusPath()
	 * @model dataType="de.hub.corpling.pepper.pepperInterface.URI"
	 * @generated
	 */
	URI getCorpusPath();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition#getCorpusPath <em>Corpus Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corpus Path</em>' attribute.
	 * @see #getCorpusPath()
	 * @generated
	 */
	void setCorpusPath(URI value);

	/**
	 * Returns the value of the '<em><b>Format Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Format Definition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format Definition</em>' containment reference.
	 * @see #setFormatDefinition(FormatDefinition)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage#getCorpusDefinition_FormatDefinition()
	 * @model containment="true"
	 * @generated
	 */
	FormatDefinition getFormatDefinition();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition#getFormatDefinition <em>Format Definition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format Definition</em>' containment reference.
	 * @see #getFormatDefinition()
	 * @generated
	 */
	void setFormatDefinition(FormatDefinition value);

} // CorpusDefinition

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
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pepper Importer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter#getSupportedFormats <em>Supported Formats</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter#getCorpusDefinition <em>Corpus Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperImporter()
 * @model abstract="true"
 * @generated
 */
public interface PepperImporter extends PepperModule {

	/**
	 * A character or character sequence to mark a file extension as not to be one of the imported ones.
	 */
	public static final String NEGATIVE_FILE_EXTENSION_MARKER="-";
	/**
	 * Returns the value of the '<em><b>Supported Formats</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Supported Formats</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Supported Formats</em>' containment reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperImporter_SupportedFormats()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<FormatDefinition> getSupportedFormats();

	/**
	 * Returns the value of the '<em><b>Corpus Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Corpus Definition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Corpus Definition</em>' containment reference.
	 * @see #setCorpusDefinition(CorpusDefinition)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperImporter_CorpusDefinition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CorpusDefinition getCorpusDefinition();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter#getCorpusDefinition <em>Corpus Definition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corpus Definition</em>' containment reference.
	 * @see #getCorpusDefinition()
	 * @generated
	 */
	void setCorpusDefinition(CorpusDefinition value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleException" corpusGraphDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SCorpusGraph"
	 * @generated
	 */
	void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model formatReferenceDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.URI"
	 * @generated
	 */
	FormatDefinition addSupportedFormat(String formatName, String formatVersion, URI formatReference);
} // PepperImporter

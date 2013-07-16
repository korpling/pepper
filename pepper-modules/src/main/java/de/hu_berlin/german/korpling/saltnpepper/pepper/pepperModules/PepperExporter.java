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

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pepper Exporter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter#getSupportedFormats <em>Supported Formats</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter#getCorpusDefinition <em>Corpus Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperExporter()
 * @model abstract="true"
 * @generated
 */
public interface PepperExporter extends PepperModule {

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
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperExporter_SupportedFormats()
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
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage#getPepperExporter_CorpusDefinition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CorpusDefinition getCorpusDefinition();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter#getCorpusDefinition <em>Corpus Definition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corpus Definition</em>' containment reference.
	 * @see #getCorpusDefinition()
	 * @generated
	 */
	void setCorpusDefinition(CorpusDefinition value);

	/**
	 * Returns table correspondence between {@link SElementId} and a resource.
	 * Stores {@link SElementId} objects corresponding to either a {@link SDocument} or a {@link SCorpus} object, which has
	 * been created during the run of {@link #importCorpusStructure(SCorpusGraph)}. Corresponding to the {@link SElementId} object
	 * this table stores the resource from where the element shall be imported.<br/>
	 * For instance:
	 * <table>
	 * 	<tr><td>corpus_1</td><td>/home/me/corpora/myCorpus</td></tr>
	 *  <tr><td>corpus_2</td><td>/home/me/corpora/myCorpus/subcorpus</td></tr>
	 *  <tr><td>doc_1</td><td>/home/me/corpora/myCorpus/subcorpus/document1.xml</td></tr>
	 *  <tr><td>doc_2</td><td>/home/me/corpora/myCorpus/subcorpus/document2.xml</td></tr>
	 * </table>
	 * @return table correspondence between {@link SElementId} and a resource.
	 */
	public Map<SElementId, URI> getSElementId2ResourceTable();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model sElementIdDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.SElementId"
	 * @generated
	 */
	void createFolderStructure(SElementId sElementId);

	/**
	 * This method is called by {@link #start()} to export the corpus-structure. This method than can create the 
	 * folder-structure to store the document-structure into it, if necessary. 
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZATION 
	 * @param corpusGraph
	 */
	void exportCorpusStructure(SCorpusGraph corpusGraph);
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model formatReferenceDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.URI"
	 * @generated
	 */
	FormatDefinition addSupportedFormat(String formatName, String formatVersion, URI formatReference);
} // PepperExporter

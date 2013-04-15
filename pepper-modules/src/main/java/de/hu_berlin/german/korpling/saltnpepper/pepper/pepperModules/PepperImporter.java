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


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.util.URI;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusDocumentRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;


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
	 * A string specifying a value for a folder as ending. This is useful for {@link #setTypeOfResource(URI)}, to determine, that
	 * even a folder can be mapped to a resource.Can be used by importers to be put in collection {@link #getSDocumentEndings()} or {@link #getSCorpusEndings()}
	 */
	public static final String ENDING_FOLDER="FOLDER";
	/**
	 * A string specifying a value for a leaf folder as ending. This is useful for {@link #setTypeOfResource(URI)}, to determine, that
	 * even a leaf folder can be mapped to a resource. Can be used by importers to be put in collection {@link #getSDocumentEndings()} or {@link #getSCorpusEndings()}
	 */
	public static final String ENDING_LEAF_FOLDER="LEAF_FOLDER";
	/**Ending for an xml file. Can be used by importers to be put in collection {@link #getSDocumentEndings()} or {@link #getSCorpusEndings()}*/
	public static final String ENDING_XML="xml";
	/**Ending for an txt file. Can be used by importers to be put in collection {@link #getSDocumentEndings()} or {@link #getSCorpusEndings()}*/
	public static final String ENDING_TXT="txt";
	/**Ending for an tab file. Can be used by importers to be put in collection {@link #getSDocumentEndings()} or {@link #getSCorpusEndings()}*/
	public static final String ENDING_tab="tab";
	
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
	 * This method is called by Pepper at the start of conversion process. 
	 * It shall create the corpus-structure of the corpus to import. That means creating all necessary {@link SCorpus}, 
	 * {@link SDocument} and all Relation-objects between them. The path to the corpus to import is given by
	 * this.getCorpusDefinition().getCorpusPath().
	 * @param corpusGraph an empty graph given by Pepper, which shall contains the corpus structure
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
	
//	/**
//	 * This method returns if a given {@link URI} object shall be imported during import phase. 
//	 * This decision depends on the kind and the content of the given fileExtension list. The file
//	 * extension list can contain a set of file extensions (Strings without '.') to be imported or
//	 * marked as to be not imported (via the prefix {@link PepperImporter#NEGATIVE_FILE_EXTENSION_MARKER}).
//	 * The following list shows the condition of computation for returned value:
//	 * <ul>
//	 * <li>This method returns false, in case of the given {@link URI} obejct is null</li>
//	 * <li>This method returns true for every {@link URI} object in case of the list is null or empty.</li>
//	 * <li>This method returns true if the given list is a positive list (does not contain the negative marker '{@value PepperImporter#NEGATIVE_FILE_EXTENSION_MARKER}' at all) and the file extension of the uri is contained in the list.</li>
//	 * <li>This method returns true if the given list is a negative list (any item is prefixed with the negative marker '{@value PepperImporter#NEGATIVE_FILE_EXTENSION_MARKER}') and the file extension of the uri is <b>not</b>contained in the list.</li>
//	 * </ul> 
//	 * Note: When a list contains items prefixed with the negative marker and items which are not, the list is interpreted
//	 * as a negative list.
//	 * 
//	 * @param checkUri
//	 * @param fileExtensions
//	 * @return
//	 */
//	public boolean isFileToImport(URI checkUri, List<String> fileExtensions);
//	/**
//	 * Computes a corpus-structure given by the file-structure located by the address of the given {@link URI} object. For the root folder 
//	 * (the direct URI location) a root-corpus ({@link SCorpus} object) is created. For each sub-folder a sub-corpus ({@link SCorpus} object) is created.
//	 * For each file for which the method {@link #isFileToImport(URI, List)} returns <code>true</code>, a {@link SDocument} object is created and added
//	 * to the current {@link SCorpusGraph} object. The objects are connected via {@link SCorpusRelation}
//	 * or {@link SCorpusDocumentRelation} objects. While traversing the file-structure, an {@link SElementId} object is created representing the 
//	 * corpus-hierarchie and added to the created {@link SCorpus} or {@link SDocument} objects. A map of these {@link SElementId} objects corresponding
//	 * to the {@link URI} objects is returned, so that in method {@link #start(SElementId)} this map can be used to identify the {@link URI} location
//	 * of the {@link SDocument} objects. 
//	 * Note: For a description of how to add entries to the file extension list, see {@link #isFileToImport(URI, List)}.
//	 * 
//	 * @param currURI the parent folder of folder structure.
//	 * @param parentsID the elementid, reffering to the element, to which the corpus structure has to be added
//	 * @param endings a list of endings which identifiy a document in watched format. Please attend, that endings are without ".", e.g. (xml, dot, txt...). If endings is null, all files will be used.
//	 * @return a map of elementid corresponing to a directory
//	 * @throws IOException
//	 */
//	public Map<SElementId, URI> createCorpusStructure(	URI currURI, 
//														SElementId parentsID, 
//														EList<String> endings) throws IOException;
	/**
	 * This method is called by the pepper framework and returns if a corpus located at the given {@link URI} is importable
	 * by this importer. If yes, 1 must be returned, if no 0 must be returned. If it is not quite sure, if the given corpus
	 * is importable by this importer any value between 0 and 1 can be returned. If this method is not overridden, 
	 * null is returned.
	 *     
	 * @return 1 if corpus is importable, 0 if corpus is not importable, 0 < X < 1, if no definitiv answer is possible,  null if method is not overridden 
	 */
	public Double isImportable(URI corpusPath);
} // PepperImporter

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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;


/**
 * TODO make docu
 * @author Florian Zipser
 */
public interface PepperExporter extends PepperModule {

	/**
	 * TODO docu
	 * @return
	 */	
	List<FormatDesc> getSupportedFormats();

	/**
	 * TODO docu
	 * @return
	 */	
	CorpusDesc getCorpusDesc();

	/**
	 * Returns the format ending for files to be exported and related to {@link SDocument} objects. 
	 * @return file ending for {@link SDocument} objects to be exported.
	 */
	public String getSDocumentEnding();
	/**
	 * Sets the format ending for files to be exported and related to {@link SDocument} objects. 
	 * @param file ending for {@link SDocument} objects to be exported.
	 */
	public void setSDocumentEnding(String sDocumentEnding);
	
	/**
	 * TODO docu
	 * @return
	 */	
	void setCorpusDesc(CorpusDesc corpusDesc);

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
	 * Creates a folder structure basing on the passed corpus path in ({@link CorpusDesc#getCorpusPath()}).
	 * For each segment in {@link SElementId} a folder is created. 
	 * @return the entire path of {@link SElementId} as file path, which was created on disk 
	 */	
	URI createFolderStructure(SElementId sElementId);

	/**
	 * Determines how the corpus-structure should be exported.
	 * @author Florian Zipser
	 *
	 */
	public enum EXPORT_MODE{
		/** corpus-structure should not be exported**/
		NO_EXPORT,
		/** {@link SCorpus} objects are exported into a folder structure, but {@link SDocument} objects are not exported **/
		CORPORA_ONLY,
		/** {@link SCorpus} objects are exported into a folder structure and {@link SDocument} objects are stored in files having the ending determined by PepperExporter#getSDocumentEnding() **/
		DOCUMENTS_IN_FILES
	}
	/**
	 * Returns how corpus-structure is exported.
	 * @return
	 */
	public EXPORT_MODE getExportMode();
	/**
	 * Determines how the corpus-structure should be exported.  
	 * <ul>
	 * 	<li>EXPORT_MODE#NO_EXPORT - corpus-structure should not be exported</li>
	 *  <li>EXPORT_MODE#CORPORA_ONLY {@link SCorpus} objects are exported into a folder structure, but {@link SDocument} objects are not exported</li>
	 *  <li>EXPORT_MODE#DOCUMENTS_IN_FILES - {@link SCorpus} objects are exported into a folder structure and {@link SDocument} objects are stored in files having the ending determined by PepperExporter#getSDocumentEnding()</li>
	 * </ul>
	 * @param exportMode
	 */
	public void setExportMode(EXPORT_MODE exportMode);
	
	/**
	 * This method is called by {@link #start()} to export the corpus-structure into a folder-structure. 
	 * That means, each {@link SElementId} belonging to a {@link SDocument} or {@link SCorpus} object is stored
	 * {@link #getSElementId2ResourceTable()} together with thze corresponding file-structure object 
	 * (file or folder) located by a {@link URI}. The {@link URI} object corresponding to files will get
	 * the file ending determined by {@link #getSDocumentEnding(String)}, which could be set by {@link #setSDocumentEnding(String)}.
	 * <br/>
	 * To adapt the creation of {@link URI}s set the export mode via {@link #setExportMode(EXPORT_MODE)}.
	 */
	public void exportCorpusStructure();
	/**
	 * {@inheritDoc PepperModuleDesc#addSupportedFormat(String, String, URI)}
	 */	
	FormatDesc addSupportedFormat(String formatName, String formatVersion, URI formatReference);
} // PepperExporter

/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
 * <p>
 * A mapping task in the Pepper workflow is not a monolithic block. It consists
 * of several smaller steps.
 * <ul>
 * <li>Declare the fingerprint of the module. This is part of the constructor.</li>
 * <li>Check readyness of the module.</li>
 * <li>Export the corpus structure.</li>
 * <li>Export the document structure and create a mapper for each corpus and
 * document.</li>
 * <li>clean-up</li>
 * </ul>
 * The following describes the single steps in short. To get a more detailed
 * explanation, take a look to the documentations found at <a
 * href="http://u.hu-berlin.de/saltnpepper"
 * >http://u.hu-berlin.de/saltnpepper</a>.
 * </p>
 * <p>
 * <h3>Declare the fingerprint</h3>
 * Initialize the module and set the modules name, its description and the
 * format description of data which are importable. This is part of the
 * constructor:
 * 
 * <pre>
 * public MyModule() {
 * 	super(&quot;Name of the module&quot;);
 * 	setSupplierContact(URI.createURI(&quot;Contact address of the module's supplier&quot;));
 * 	setSupplierHomepage(URI.createURI(&quot;homepage of the module&quot;));
 * 	setDesc(&quot;A short description of what is the intention of this module, for instance which formats are importable. &quot;);
 * 	this.addSupportedFormat(&quot;The name of a format which is importable e.g. txt&quot;, &quot;The version corresponding to the format name&quot;, null);
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * <h3>Check readyness of the module</h3>
 * This method is invoked by the Pepper framework before the mapping process is
 * started. This method must return true, otherwise, this Pepper module could
 * not be used in a Pepper workflow. At this point problems which prevent the
 * module from being used you can report all problems to the user, for instance
 * a database connection could not be established.
 * 
 * <pre>
 * public boolean isReadyToStart() {
 * 	return (true);
 * }
 * </pre>
 * 
 * </p>
 * 
 * </p> <h3>Export corpus structure</h3>
 * The corpus-structure export is handled in the method {@link #exportCorpusStructure()}.
 * It is invoked on top of the method ' start() ' of the PepperExporter . For
 * totally changing the default behavior just override this method. The aim of
 * the method {@link #exportCorpusStructure()} is to fill the map of corresponding corpus-structure and file
 * structure. The file structure is automatically created, there are
 * just URI s pointing to the virtual file or folder. The creation of the file
 * or folder has to be done by the Pepper module itself in method {@link PepperMapper#mapSCorpus()}
 * or {@link PepperMapper#mapSDocument()}. To adapt the creation of this 'virtual' file structure,
 * you first have to choose the mode of export. You can do this for instance in
 * method 'readyToStart()', as shown in the following snippet. But even in the
 * constructor as well. 
 * <pre>
 * public boolean isReadyToStart(){ 
 * 	... //option 1
 * 	setExportMode(EXPORT_MODE.NO_EXPORT); 
 * 	//option 2
 * 	setExportMode(EXPORT_MODE.CORPORA_ONLY); 
 * 	//option 3
 * 	setExportMode(EXPORT_MODE.DOCUMENTS_IN_FILES);
 *  //sets the ending, which should be added to the documents name
 * 	setSDocumentEnding(ENDING_TAB); 
 * 	.. 
 * }
 * </pre>
 * In this snippet, option 1 means that
 * nothing will be mapped. Option 2 means that only {@link SCorpus} objects are mapped
 * to a folder and {@link SDocument} objects will be ignored. And option 3 means that
 * {@link SCorpus} objects are mapped to a folder and {@link SDocument} objects are mapped to a
 * file. The ending of that file can be determined by passing the ending with
 * method {@link #setSDocumentEnding(String)}. In the given snippet a {@link URI} having the
 * ending 'tab' is created for each {@link SDocument}.
 * <p>
 * <h3>Export the document structure</h3>
 * In the method {@link #createPepperMapper(SElementId)} a {@link PepperMapper}
 * object needs to be initialized and returned. The {@link PepperMapper} is the
 * major part major part doing the mapping. It provides the methods
 * {@link PepperMapper#mapSCorpus()} to handle the mapping of a single
 * {@link SCorpus} object and {@link PepperMapper#mapSDocument()} to handle a
 * single {@link SDocument} object. Both methods are invoked by the Pepper
 * framework. To set the {@link PepperMapper#getResourceURI()}, which offers the
 * mapper the file or folder of the current {@link SCorpus} or {@link SDocument}
 * object, this filed needs to be set in the
 * {@link #createPepperMapper(SElementId)} method. The following snippet shows a
 * dummy of that method:
 * 
 * <pre>
 * public PepperMapper createPepperMapper(SElementId sElementId) {
 * 	PepperMapper mapper = new PepperMapperImpl() {
 * 		&#064;Override
 * 		public DOCUMENT_STATUS mapSCorpus() {
 * 			// handling the mapping of a single corpus
 * 
 * 			// accessing the current file or folder
 * 			getResourceURI();
 * 
 * 			// returning, that the corpus was mapped successfully
 * 			return (DOCUMENT_STATUS.COMPLETED);
 * 		}
 * 
 * 		&#064;Override
 * 		public DOCUMENT_STATUS mapSDocument() {
 * 			// handling the mapping of a single document
 * 
 * 			// accessing the current file or folder
 * 			getResourceURI();
 * 
 * 			// returning, that the document was mapped successfully
 * 			return (DOCUMENT_STATUS.COMPLETED);
 * 		}
 * 	};
 * 	// pass current file or folder to mapper. When using
 * 	// PepperImporter.importCorpusStructure or
 * 	// PepperExporter.exportCorpusStructure, the mapping between file or folder
 * 	// and SCorpus or SDocument was stored here
 * 	mapper.setResourceURI(getSElementId2ResourceTable().get(sElementId));
 * 	return (mapper);
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * <h3>clean-up</h3>
 * Sometimes it might be necessary to clean up after the module did the job. For
 * instance when writing an im- or an exporter it might be necessary to close
 * file streams, a db connection etc. Therefore, after the processing is done,
 * the Pepper framework calls the method described in the following snippet:
 * 
 * <pre>
 * public void end() {
 * 	super.end();
 * 	// do some clean up like closing of streams etc.
 * }
 * </pre>
 * 
 * </p>
 * 
 * @author Florian Zipser
 */
public interface PepperExporter extends PepperModule {

	/**
	 * TODO docu
	 * 
	 * @return
	 */
	List<FormatDesc> getSupportedFormats();

	/**
	 * TODO docu
	 * 
	 * @return
	 */
	CorpusDesc getCorpusDesc();

	/**
	 * Returns the format ending for files to be exported and related to
	 * {@link SDocument} objects.
	 * 
	 * @return file ending for {@link SDocument} objects to be exported.
	 */
	public String getSDocumentEnding();

	/**
	 * Sets the format ending for files to be exported and related to
	 * {@link SDocument} objects.
	 * 
	 * @param file
	 *            ending for {@link SDocument} objects to be exported.
	 */
	public void setSDocumentEnding(String sDocumentEnding);

	/**
	 * TODO docu
	 * 
	 * @return
	 */
	void setCorpusDesc(CorpusDesc corpusDesc);

	/**
	 * Returns table correspondence between {@link SElementId} and a resource.
	 * Stores {@link SElementId} objects corresponding to either a
	 * {@link SDocument} or a {@link SCorpus} object, which has been created
	 * during the run of {@link #importCorpusStructure(SCorpusGraph)}.
	 * Corresponding to the {@link SElementId} object this table stores the
	 * resource from where the element shall be imported.<br/>
	 * For instance:
	 * <table>
	 * <tr>
	 * <td>corpus_1</td>
	 * <td>/home/me/corpora/myCorpus</td>
	 * </tr>
	 * <tr>
	 * <td>corpus_2</td>
	 * <td>/home/me/corpora/myCorpus/subcorpus</td>
	 * </tr>
	 * <tr>
	 * <td>doc_1</td>
	 * <td>/home/me/corpora/myCorpus/subcorpus/document1.xml</td>
	 * </tr>
	 * <tr>
	 * <td>doc_2</td>
	 * <td>/home/me/corpora/myCorpus/subcorpus/document2.xml</td>
	 * </tr>
	 * </table>
	 * 
	 * @return table correspondence between {@link SElementId} and a resource.
	 */
	public Map<SElementId, URI> getSElementId2ResourceTable();

	/**
	 * Creates a folder structure basing on the passed corpus path in (
	 * {@link CorpusDesc#getCorpusPath()}). For each segment in
	 * {@link SElementId} a folder is created.
	 * 
	 * @return the entire path of {@link SElementId} as file path, which was
	 *         created on disk
	 */
	URI createFolderStructure(SElementId sElementId);

	/**
	 * Determines how the corpus-structure should be exported.
	 * 
	 * @author Florian Zipser
	 *
	 */
	public enum EXPORT_MODE {
		/** corpus-structure should not be exported **/
		NO_EXPORT,
		/**
		 * {@link SCorpus} objects are exported into a folder structure, but
		 * {@link SDocument} objects are not exported
		 **/
		CORPORA_ONLY,
		/**
		 * {@link SCorpus} objects are exported into a folder structure and
		 * {@link SDocument} objects are stored in files having the ending
		 * determined by PepperExporter#getSDocumentEnding()
		 **/
		DOCUMENTS_IN_FILES
	}

	/**
	 * Returns how corpus-structure is exported.
	 * 
	 * @return
	 */
	public EXPORT_MODE getExportMode();

	/**
	 * Determines how the corpus-structure should be exported.
	 * <ul>
	 * <li>EXPORT_MODE#NO_EXPORT - corpus-structure should not be exported</li>
	 * <li>EXPORT_MODE#CORPORA_ONLY {@link SCorpus} objects are exported into a
	 * folder structure, but {@link SDocument} objects are not exported</li>
	 * <li>EXPORT_MODE#DOCUMENTS_IN_FILES - {@link SCorpus} objects are exported
	 * into a folder structure and {@link SDocument} objects are stored in files
	 * having the ending determined by PepperExporter#getSDocumentEnding()</li>
	 * </ul>
	 * 
	 * @param exportMode
	 */
	public void setExportMode(EXPORT_MODE exportMode);

	/**
	 * This method is called by {@link #start()} to export the corpus-structure
	 * into a folder-structure. That means, each {@link SElementId} belonging to
	 * a {@link SDocument} or {@link SCorpus} object is stored
	 * {@link #getSElementId2ResourceTable()} together with thze corresponding
	 * file-structure object (file or folder) located by a {@link URI}. The
	 * {@link URI} object corresponding to files will get the file ending
	 * determined by {@link #getSDocumentEnding(String)}, which could be set by
	 * {@link #setSDocumentEnding(String)}. <br/>
	 * To adapt the creation of {@link URI}s set the export mode via
	 * {@link #setExportMode(EXPORT_MODE)}.
	 */
	public void exportCorpusStructure();

	/**
	 * {@inheritDoc PepperModuleDesc#addSupportedFormat(String, String, URI)}
	 */
	FormatDesc addSupportedFormat(String formatName, String formatVersion, URI formatReference);
} // PepperExporter

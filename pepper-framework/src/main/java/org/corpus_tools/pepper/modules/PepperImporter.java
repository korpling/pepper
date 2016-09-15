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
package org.corpus_tools.pepper.modules;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.impl.PepperImporterImpl;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.SALT_TYPE;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SCorpusDocumentRelation;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SCorpusRelation;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;

/**
 * <p>
 * A mapping task in the Pepper workflow is not a monolithic block. It consists
 * of several smaller steps.
 * <ul>
 * <li>Declare the fingerprint of the module. This is part of the constructor.
 * </li>
 * <li>Check readyness of the module.</li>
 * <li>Analyze whether the files in the passed corpus path is importable by this
 * importer.</li>
 * <li>Import the corpus structure.</li>
 * <li>Import the document structure and create a mapper for each corpus and
 * document.</li>
 * <li>clean-up</li>
 * </ul>
 * The following describes the single steps in short. To get a more detailed
 * explanation, take a look to the documentations found at
 * <a href="http://u.hu-berlin.de/saltnpepper" >http://u.hu-berlin.de/
 * saltnpepper</a>.
 * </p>
 * <p>
 * <h3>Declare the fingerprint</h3> Initialize the module and set the modules
 * name, its description and the format description of data which are
 * importable. This is part of the constructor:
 * 
 * <pre>
 * public MyModule() {
 * 	super(&quot;Name of the module&quot;);
 * 	setSupplierContact(URI.createURI(&quot;Contact address of the module's supplier&quot;));
 * 	setSupplierHomepage(URI.createURI(&quot;homepage of the module&quot;));
 * 	setDesc(&quot;A short description of what is the intention of this module, for instance which formats are importable. &quot;);
 * 	this.addSupportedFormat(&quot;The name of a format which is importable e.g. txt&quot;,
 * 			&quot;The version corresponding to the format name&quot;, null);
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * <h3>Check readyness of the module</h3> This method is invoked by the Pepper
 * framework before the mapping process is started. This method must return
 * true, otherwise, this Pepper module could not be used in a Pepper workflow.
 * At this point problems which prevent the module from being used you can
 * report all problems to the user, for instance a database connection could not
 * be established.
 * 
 * <pre>
 * public boolean isReadyToStart() {
 * 	return (true);
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * <h3>Analyze data</h3> Depending on the formats you want to support with your
 * importer the detection can be very different. In the simplest case, it only
 * is necessary, to search through the files at the given location (or to
 * recursively traverse through directories, in case the location points to a
 * directory), and to read their header section. For instance some formats like
 * the xml formats PAULA (see: http:// www.sfb632.uni-potsdam.de/en/paula.html )
 * or TEI (see: http://www.tei-c.org/Guidelines/P5/). The method should return a
 * value between 0 and 1, where 0 means not importable and 1 means definitely
 * importable. If null is returned, Pepper interprets this as unknown and will
 * never suggest this module to the user.
 * 
 * <pre>
 * public Double isImportable(URI corpusPath) {
 * 	return null;
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * <h3>Import corpus structure</h3> The classes {@link PepperImporterImpl} and
 * {@link PepperExporterImpl} provide an automatic mechanism to im- or export
 * the corpus-structure. This mechanism is adaptable step by step, according to
 * your specific purpose. Since many formats do not care about the
 * corpus-structure and they only encode the document-structure, the
 * corpus-structure is simultaneous to the file structure of a corpus. Pepper's
 * default mapping maps the root-folder to a root-corpus ({@link SCorpus}
 * object). A sub-folder then corresponds to a sub-corpus ({@link SCorpus}
 * object). The relation between super- and sub-corpus, is represented as a
 * {@link SCorpusRelation} object. Following the assumption, that files contain
 * the document-structure, there is one {@link SDocument} corresponding to each
 * file in a sub-folder. The {@link SCorpus} and the {@link SDocument} objects
 * are linked with a {@link SCorpusDocumentRelation}.<br/>
 * For keeping the correspondance between the corpus-structure and the file
 * structure, both the im- and the exporter make use of a map, which can be
 * accessed via {@link #getIdentifier2ResourceTable()}. <br/>
 * To adapt the behavior, you can set the file endings in the constructor as
 * follows:
 * 
 * <pre>
 * this.getDocumentEndings().add(&quot;file ending&quot;);
 * </pre>
 * 
 * You can also add the value {@link PepperModule#ENDING_LEAF_FOLDER} to import
 * not files but leaf folders as {@link SDocument} objects. Another possibility
 * is to add the value {@link PepperModule#ENDING_ALL_FILES} to import all files
 * no matter their ending.
 * </p>
 * <p>
 * <h3>Import the document structure</h3> In the method
 * {@link #createPepperMapper(Identifier)} a {@link PepperMapper} object needs
 * to be initialized and returned. The {@link PepperMapper} is the major part
 * major part doing the mapping. It provides the methods
 * {@link PepperMapper#mapSCorpus()} to handle the mapping of a single
 * {@link SCorpus} object and {@link PepperMapper#mapSDocument()} to handle a
 * single {@link SDocument} object. Both methods are invoked by the Pepper
 * framework. To set the {@link PepperMapper#getResourceURI()}, which offers the
 * mapper the file or folder of the current {@link SCorpus} or {@link SDocument}
 * object, this filed needs to be set in the
 * {@link #createPepperMapper(Identifier)} method. The following snippet shows a
 * dummy of that method:
 * 
 * <pre>
 * public PepperMapper createPepperMapper(Identifier sElementId) {
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
 * 	// PepperExporter.exportCorpusStructure, the mapping between file or
 * 	// folder
 * 	// and SCorpus or SDocument was stored here
 * 	mapper.setResourceURI(getIdentifier2ResourceTable().get(sElementId));
 * 	return (mapper);
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * <h3>clean-up</h3> Sometimes it might be necessary to clean up after the
 * module did the job. For instance when writing an im- or an exporter it might
 * be necessary to close file streams, a db connection etc. Therefore, after the
 * processing is done, the Pepper framework calls the method described in the
 * following snippet:
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
public interface PepperImporter extends PepperModule {
	/**
	 * A character or character sequence to mark a file extension as not to be
	 * one of the imported ones.
	 */
	static final String NEGATIVE_FILE_EXTENSION_MARKER = "-";

	/**
	 * Returns a list of formats, which are importable by this
	 * {@link PepperImporter} object.
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
	 * TODO docu
	 * 
	 * @return
	 */
	void setCorpusDesc(CorpusDesc corpusDesc);

	/**
	 * Stores {@link Identifier} objects corresponding to either a
	 * {@link SDocument} or a {@link SCorpus} object, which has been created
	 * during the run of {@link #importCorpusStructure(SCorpusGraph)}.
	 * Corresponding to the {@link Identifier} object this table stores the
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
	 */
	Map<Identifier, URI> getIdentifier2ResourceTable();

	/**
	 * Returns list containing all format endings for files, which are
	 * importable and could be mapped to {@link SDocument} or
	 * {@link SDocumentGraph} objects by this Pepper module.
	 * 
	 * @return a collection of endings
	 */
	Collection<String> getDocumentEndings();

	/**
	 * Returns a collection of all file endings for a {@link SCorpus} object.
	 * See {@inheritDoc #sCorpusEndings}. This list contains per default value
	 * {@value #ENDING_FOLDER}. To remove the default value, call
	 * {@link Collection#remove(Object)} on {@link #getCorpusEndings()}. To add
	 * endings to the collection, call {@link Collection#add(Ending)} and to
	 * remove endings from the collection, call
	 * {@link Collection#remove(Ending)}.
	 * 
	 * @return a collection of endings
	 */
	Collection<String> getCorpusEndings();

	/**
	 * Returns a collection of filenames, not to be imported.
	 * {@inheritDoc #importIgnoreList} . To add endings to the collection, call
	 * {@link Collection#add(Ending)} and to remove endings from the collection,
	 * call {@link Collection#remove(Ending)}.
	 * 
	 * @return a collection of endings to be ignored
	 */
	Collection<String> getIgnoreEndings();

	/**
	 * This method is a callback and can be overridden by derived importers.
	 * This method is called via the import of the corpus-structure (
	 * {@link #importCorpusStructure(SCorpusGraph)}). During the traversal of
	 * the file-structure the method
	 * {@link #importCorpusStructure(SCorpusGraph)} calls this method for each
	 * resource, to determine if the resource either represents a
	 * {@link SCorpus}, a {@link SDocument} object or shall be ignored. <br/>
	 * If this method is not overridden, the default behavior is:
	 * <ul>
	 * <li>For each file having an ending, which is contained in
	 * {@link #getDocumentEndings()} {@link SALT_TYPE#SDOCUMENT} is returned
	 * </li>
	 * <li>For each file having an ending, which is contained in
	 * {@link #getCorpusEndings()} {@link SALT_TYPE#SCorpus} is returned</li>
	 * <li>If {@link #getDocumentEndings()} contains {@link #ENDING_ALL_FILES},
	 * for each file (which is not a folder) {@link SALT_TYPE#SDOCUMENT} is
	 * returned</li>
	 * <li>If {@link #getDocumentEndings()} contains {@link #ENDING_LEAF_FOLDER}
	 * , for each leaf folder {@link SALT_TYPE#SDOCUMENT} is returned</li>
	 * <li>If {@link #getCorpusEndings()} contains {@link #ENDING_FOLDER}, for
	 * each folder {@link SALT_TYPE#SCORPUS} is returned</li>
	 * <li>null otherwise</li>
	 * </ul>
	 * 
	 * @param resource
	 *            {@link URI} resource to be specified
	 * @return {@link SALT_TYPE#SCORPUS} if resource represents a
	 *         {@link SCorpus} object, {@link SALT_TYPE#SDOCUMENT} if resource
	 *         represents a {@link SDocument} object or null, if it shall be
	 *         igrnored.
	 */
	SALT_TYPE setTypeOfResource(URI resource);

	/**
	 * This method is called by Pepper at the start of a conversion process to
	 * create the corpus-structure. A corpus-structure consists of corpora
	 * (represented via the Salt element {@link SCorpus}), documents
	 * (represented represented via the Salt element {@link SDocument}) and a
	 * linking between corpora and a corpus and a document (represented via the
	 * Salt element {@link SCorpusRelation} and {@link SCorpusDocumentRelation}
	 * ). Each corpus corpus can contain 0..* subcorpus and 0..* documents, but
	 * a corpus cannot contain both document and corpus. <br/>
	 * For many cases the creation of the corpus-struccture can be done
	 * automatically, therefore, just adopt the two lists #gets <br/>
	 * This method creates the corpus-structure via a top down traversal in file
	 * structure. For each found file (real file and folder), the method
	 * {@link #setTypeOfResource(URI)} is called to set the type of the
	 * resource. If the type is a {@link SALT_TYPE#SDOCUMENT} a
	 * {@link SDocument} object is created for the resource, if the type is a
	 * {@link SALT_TYPE#SCORPUS} a {@link SCorpus} object is created, if the
	 * type is null, the resource is ignored.
	 * 
	 * @param corpusGraph
	 *            an empty graph given by Pepper, which shall contains the
	 *            corpus structure
	 */
	void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException;

	/**
	 * {@inheritDoc PepperModuleDesc#addSupportedFormat(String, String, URI)}
	 */
	FormatDesc addSupportedFormat(String formatName, String formatVersion, URI formatReference);

	/**
	 * This method is called by Pepper and returns if a corpus located at the
	 * given {@link URI} is importable by this importer. If yes, 1 must be
	 * returned, if no 0 must be returned. If it is not quite sure, if the given
	 * corpus is importable by this importer any value between 0 and 1 can be
	 * returned. If this method is not overridden, null is returned.
	 * 
	 * @return 1 if corpus is importable, 0 if corpus is not importable, 0 < X <
	 *         1, if no definitiv answer is possible, null if method is not
	 *         overridden
	 */
	Double isImportable(URI corpusPath);
}

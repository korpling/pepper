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
	 * TODO docu
	 * @return
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
	 * {@inheritDoc PepperModuleDesc#addSupportedFormat(String, String, URI)}
	 */	
	FormatDesc addSupportedFormat(String formatName, String formatVersion, URI formatReference);
} // PepperExporter

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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 *TODO make docu
 *
 *@author Florian Zipser
 */
public abstract class PepperExporterImpl extends PepperModuleImpl implements PepperExporter {
	
	/**
	 * TODO make docu
	 */
	protected PepperExporterImpl() {
		super();
	}
	
	/**
	 * {@inheritDoc PepperExporter#getSupportedFormats()}
	 */
	public List<FormatDesc> getSupportedFormats() {
		return(getFingerprint().getSupportedFormats());
	}
	/**
	 * {@inheritDoc PepperExporter#addSupportedFormat(String, String, URI)}
	 */
	@Override
	public FormatDesc addSupportedFormat(String formatName, String formatVersion, URI formatReference) {
		return(getFingerprint().addSupportedFormat(formatName, formatVersion, formatReference));
	}	
	
	/**
	 * TODO make docu
	 */
	protected CorpusDesc corpusDefinition;

	/**
	 * {@inheritDoc PepperExporter#getCorpusDefinition()}
	 */
	@Override
	public CorpusDesc getCorpusDesc() {
		return corpusDefinition;
	}

	/**
	 * {@inheritDoc PepperExporter#setCorpusDefinition(CorpusDefinition)}
	 */
	@Override
	public void setCorpusDesc(CorpusDesc newCorpusDefinition) {
		corpusDefinition= newCorpusDefinition;
	}

	/**
	 * Adds the call of method {@link #exportCorpusStructure(SCorpusGraph)}, than calls {@link PepperModule#start()}.
	 */
	@Override
	public void start() throws PepperModuleException
	{
		if (this.getSaltProject()!= null)
		{
			Collection<SCorpusGraph> corpGraphs= Collections.synchronizedList(this.getSaltProject().getSCorpusGraphs());
			for (SCorpusGraph sCorpusGraph: corpGraphs)
			{
				if (sCorpusGraph== null)
					throw new PepperFWException("An empty SDocumentGraph is in list of SaltProject. This might be a bug of pepper framework.");
				exportCorpusStructure(sCorpusGraph);
			}
		}
		super.start();
	}
	
	/**
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
	 * 
	 */
	private Map<SElementId, URI> sElementId2ResourceTable= null;
	
	/**
	 * {@inheritDoc PepperImporter#getSElementId2ResourceTable()}
	 */
	@Override
	public synchronized Map<SElementId, URI> getSElementId2ResourceTable() {
		if (sElementId2ResourceTable== null)
			sElementId2ResourceTable= new Hashtable<SElementId, URI>();
		return sElementId2ResourceTable;
	}
	
	/**
	 * {@inheritDoc PepperExporter#exportCorpusStructure(SCorpusGraph)}
	 */
	@Override
	public void exportCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException
	{
		
	}
	
	/**
	 * {@inheritDoc PepperExporter#createFolderStructure(SElementId)}
	 */
	@Override
	public void createFolderStructure(SElementId sElementId)
	{
		if (sElementId== null)
			throw new PepperConvertException("Cannot export the given sElementID, because given SElementId-object is null.");
		if (sElementId.getSIdentifiableElement()== null)
			throw new PepperConvertException("Cannot export the given sElementID, because the SIdentifiableElement-object of given SElementId-object is null.");
		if (	(!(sElementId.getSIdentifiableElement() instanceof SDocument)) &&
				((!(sElementId.getSIdentifiableElement() instanceof SCorpus))))
			throw new PepperConvertException("Cannot export the given sElementID, because the element corresponding to it, isn't of type SDocument or SCorpus.");
		if (getCorpusDesc()== null)
			throw new PepperFWException("Cannot export the corpus structure to file structure, because no corpus definition was given. ");
		if (getCorpusDesc().getCorpusPath()== null)
			throw new PepperFWException("Cannot export the corpus structure to file structure, because the corpus path for module '"+getName()+"' is empty. ");
		
		try {
			File folder= new File(getCorpusDesc().getCorpusPath().toFileString());
			File newFolder= new File(folder.getCanonicalPath() + "/"+ sElementId.getSElementPath().toString());
			newFolder.mkdirs();
		} catch (IOException e) {
			throw new PepperConvertException("Cannot create corpus path as folders.", e);
		}
	}
}

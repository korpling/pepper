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
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
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
 * This is an abstract implementation of {@link PepperExporter}. This class
 * cannot be instantiated directly. To provide an exporter, just inherit this
 * class.
 * 
 * @see PepperExporter
 *
 * @author Florian Zipser
 */
public abstract class PepperExporterImpl extends PepperModuleImpl implements PepperExporter {
	/**
	 * Creates a {@link PepperModule} of type {@link MODULE_TYPE#EXPORTER}. The
	 * name of this module is set to "MyExporter". <br/>
	 * We recommend to use the constructor
	 * {@link PepperExporterImpl#PepperExporterImpl(String)} and pass a proper
	 * name.
	 */
	protected PepperExporterImpl() {
		super("MyExporter");
	}

	/**
	 * Creates a {@link PepperModule} of type {@link MODULE_TYPE#EXPORTER} and
	 * sets is name to the passed one.
	 */
	protected PepperExporterImpl(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc PepperExporter#getSupportedFormats()}
	 */
	public List<FormatDesc> getSupportedFormats() {
		return (getFingerprint().getSupportedFormats());
	}

	/**
	 * {@inheritDoc PepperExporter#addSupportedFormat(String, String, URI)}
	 */
	@Override
	public FormatDesc addSupportedFormat(String formatName, String formatVersion, URI formatReference) {
		return (getFingerprint().addSupportedFormat(formatName, formatVersion, formatReference));
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
		corpusDefinition = newCorpusDefinition;
	}

	/**
	 * Adds the call of method {@link #exportCorpusStructure(SCorpusGraph)},
	 * than calls {@link PepperModule#start()}.
	 */
	@Override
	public void start() throws PepperModuleException {
		exportCorpusStructure();
		super.start();
	}

	private String sDocumentEnding = null;

	/**
	 * {@inheritDoc PepperExporter#getSDocumentEnding()}
	 */
	@Override
	public String getSDocumentEnding() {
		return (sDocumentEnding);
	}

	/**
	 * {@inheritDoc PepperExporter#setSDocumentEnding(String)}
	 */
	@Override
	public void setSDocumentEnding(String sDocumentEnding) {
		this.sDocumentEnding = sDocumentEnding;
	}

	/** Determines how the corpus-structure should be exported. **/
	private EXPORT_MODE exportMode = EXPORT_MODE.NO_EXPORT;

	/** {@inheritDoc PepperExporter#getExportMode()} */
	public EXPORT_MODE getExportMode() {
		return exportMode;
	}

	/**
	 * {@inheritDoc
	 * PepperExporter#setExportMode(de.hu_berlin.german.korpling.saltnpepper.
	 * pepper.modules.PepperExporter.EXPORT_MODE)}
	 */
	public void setExportMode(EXPORT_MODE exportMode) {
		this.exportMode = exportMode;
	}

	/**
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
	 */
	private Map<SElementId, URI> sElementId2ResourceTable = null;

	/**
	 * {@inheritDoc PepperImporter#getSElementId2ResourceTable()}
	 */
	@Override
	public synchronized Map<SElementId, URI> getSElementId2ResourceTable() {
		if (sElementId2ResourceTable == null)
			sElementId2ResourceTable = new Hashtable<SElementId, URI>();
		return sElementId2ResourceTable;
	}

	/** {@inheritDoc PepperExporter#exportCorpusStructure()} **/
	public void exportCorpusStructure() {
		if ((getExportMode() != null) && (!getExportMode().equals(EXPORT_MODE.NO_EXPORT))) {
			if (this.getSaltProject() != null) {
				Collection<SCorpusGraph> corpGraphs = Collections.synchronizedList(this.getSaltProject().getSCorpusGraphs());
				for (SCorpusGraph sCorpusGraph : corpGraphs) {
					if (sCorpusGraph == null) {
						logger.warn("An empty SDocumentGraph is in list of SaltProject. This might be a bug of pepper framework.");
					} else {
						if (getCorpusDesc() == null)
							throw new PepperFWException("Cannot export the corpus-structure to file structure, because no corpus description was given. ");
						if (getCorpusDesc().getCorpusPath() == null)
							throw new PepperFWException("Cannot export the corpus-structure to file structure, because the corpus path for module '" + getName() + "' is empty. ");
						for (SCorpus sCorpus : sCorpusGraph.getSCorpora()) {
							URI resourceURI = getCorpusDesc().getCorpusPath();
							for (String segment : sCorpus.getSElementPath().segments()) {
								resourceURI = resourceURI.appendSegment(segment);
							}
							getSElementId2ResourceTable().put(sCorpus.getSElementId(), resourceURI);
						}
						if (getExportMode().equals(EXPORT_MODE.DOCUMENTS_IN_FILES)) {
							String ending = getSDocumentEnding();
							if (ending == null) {
								ending = "";
							}
							for (SDocument sDocument : sCorpusGraph.getSDocuments()) {
								URI resourceURI = getCorpusDesc().getCorpusPath();
								for (String segment : sDocument.getSElementPath().segments()) {
									resourceURI = resourceURI.appendSegment(segment);
								}
								resourceURI = resourceURI.appendFileExtension(ending);
								getSElementId2ResourceTable().put(sDocument.getSElementId(), resourceURI);

								// in case of folders in hierarchie does not
								// exist, create them
								String fileName = resourceURI.toFileString();
								if (fileName == null) {
									resourceURI.toString();
								}
								File outFile = new File(fileName);
								if (!outFile.getParentFile().exists()) {
									outFile.getParentFile().mkdirs();
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc PepperExporter#createFolderStructure(SElementId)}
	 */
	@Override
	@Deprecated
	public URI createFolderStructure(SElementId sElementId) {
		if (sElementId == null)
			throw new PepperConvertException("Cannot export the given sElementID, because given SElementId-object is null.");
		if (sElementId.getSIdentifiableElement() == null)
			throw new PepperConvertException("Cannot export the given sElementID, because the SIdentifiableElement-object of given SElementId-object is null.");
		if ((!(sElementId.getSIdentifiableElement() instanceof SDocument)) && ((!(sElementId.getSIdentifiableElement() instanceof SCorpus))))
			throw new PepperConvertException("Cannot export the given sElementID, because the element corresponding to it, isn't of type SDocument or SCorpus.");
		if (getCorpusDesc() == null)
			throw new PepperFWException("Cannot export the corpus-structure to file structure, because no corpus description was given. ");
		if (getCorpusDesc().getCorpusPath() == null)
			throw new PepperFWException("Cannot export the corpus-structure to file structure, because the corpus path for module '" + getName() + "' is empty. ");

		try {
			File folder = new File(getCorpusDesc().getCorpusPath().toFileString());
			File newFolder = new File(folder.getCanonicalPath() + "/" + sElementId.getSElementPath().toString());
			newFolder.mkdirs();
			return (URI.createFileURI(newFolder.getAbsolutePath()));
		} catch (IOException e) {
			throw new PepperConvertException("Cannot create corpus path as folders for '" + sElementId + "'.", e);
		}
	}
}

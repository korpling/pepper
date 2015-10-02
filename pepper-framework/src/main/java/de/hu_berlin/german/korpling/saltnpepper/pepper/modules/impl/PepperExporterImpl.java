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
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.u.saltnpepper.graph.Identifier;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpus;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpusGraph;
import de.hu_berlin.u.saltnpepper.salt.common.SDocument;

/**
 * <p>
 * This class is an abstract implementation of {@link PepperExporter} and cannot
 * be instantiated directly. To implement an exporter for Pepper, the easiest
 * way is to derive this class. For further information, read the javadoc of
 * {@link PepperManipulator} and the documentation of <a
 * href="http://u.hu-berlin.de/saltnpepper">u.hu-berlin.de/saltnpepper</a>.
 * </p>
 * 
 * @see PepperManipulator
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
	 * {@inheritDoc PepperExporter#getDocumentEnding()}
	 */
	@Override
	public String getDocumentEnding() {
		return (sDocumentEnding);
	}

	/**
	 * {@inheritDoc PepperExporter#setDocumentEnding(String)}
	 */
	@Override
	public void setDocumentEnding(String sDocumentEnding) {
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
	private Map<Identifier, URI> sElementId2ResourceTable = null;

	/**
	 * {@inheritDoc PepperImporter#getIdentifier2ResourceTable()}
	 */
	@Override
	public synchronized Map<Identifier, URI> getIdentifier2ResourceTable() {
		if (sElementId2ResourceTable == null) {
			sElementId2ResourceTable = new Hashtable<Identifier, URI>();
		}
		return sElementId2ResourceTable;
	}

	/** {@inheritDoc PepperExporter#exportCorpusStructure()} **/
	public void exportCorpusStructure() {
		if ((getExportMode() != null) && (!getExportMode().equals(EXPORT_MODE.NO_EXPORT))) {
			if (this.getSaltProject() != null) {
				Collection<SCorpusGraph> corpGraphs = new LinkedList<>(this.getSaltProject().getCorpusGraphs());
				for (SCorpusGraph sCorpusGraph : corpGraphs) {
					if (sCorpusGraph == null) {
						logger.warn("An empty SDocumentGraph is in list of SaltProject. This might be a bug of pepper framework.");
					} else {
						if (getCorpusDesc() == null)
							throw new PepperFWException("Cannot export the corpus-structure to file structure, because no corpus description was given. ");
						if (getCorpusDesc().getCorpusPath() == null)
							throw new PepperFWException("Cannot export the corpus-structure to file structure, because the corpus path for module '" + getName() + "' is empty. ");
						for (SCorpus sCorpus : sCorpusGraph.getCorpora()) {
							URI resourceURI = getCorpusDesc().getCorpusPath();
							for (String segment : sCorpus.getPath().segments()) {
								resourceURI = resourceURI.appendSegment(segment);
							}
							getIdentifier2ResourceTable().put(sCorpus.getIdentifier(), resourceURI);
						}
						if (getExportMode().equals(EXPORT_MODE.DOCUMENTS_IN_FILES)) {
							String ending = getDocumentEnding();
							if (ending == null) {
								ending = "";
							}
							for (SDocument sDocument : sCorpusGraph.getDocuments()) {
								URI resourceURI = getCorpusDesc().getCorpusPath();
								for (String segment : sDocument.getPath().segments()) {
									resourceURI = resourceURI.appendSegment(segment);
								}
								resourceURI = resourceURI.appendFileExtension(ending);
								getIdentifier2ResourceTable().put(sDocument.getIdentifier(), resourceURI);

								// in case of folders in hierarchy does not
								// exist, create them
								String fileName = resourceURI.toFileString();
								if (fileName == null) {
									fileName = resourceURI.toString();
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
	 * {@inheritDoc PepperExporter#createFolderStructure(Identifier)}
	 */
	@Override
	@Deprecated
	public URI createFolderStructure(Identifier sElementId) {
		if (sElementId == null)
			throw new PepperConvertException("Cannot export the given sElementID, because given Identifier-object is null.");
		if (sElementId.getIdentifiableElement() == null)
			throw new PepperConvertException("Cannot export the given sElementID, because the SIdentifiableElement-object of given Identifier-object is null.");
		if ((!(sElementId.getIdentifiableElement() instanceof SDocument)) && ((!(sElementId.getIdentifiableElement() instanceof SCorpus))))
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

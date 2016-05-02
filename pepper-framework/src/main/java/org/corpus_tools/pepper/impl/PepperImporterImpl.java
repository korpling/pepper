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
package org.corpus_tools.pepper.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.exceptions.WorkflowException;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperManipulator;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.SALT_TYPE;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.xml.sax.ext.DefaultHandler2;

/**
 * <p>
 * This class is an abstract implementation of {@link PepperImporter} and cannot
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
public abstract class PepperImporterImpl extends PepperModuleImpl implements PepperImporter {
	/**
	 * Creates a {@link PepperModule} of type {@link MODULE_TYPE#IMPORTER}. The
	 * name is set to "MyImporter".
	 * 
	 * <br/>
	 * We recommend to use the constructor
	 * {@link PepperImporterImpl#PepperImporterImpl(String)} and pass a proper
	 * name.
	 */
	protected PepperImporterImpl() {
		super("MyImporter");
	}

	/**
	 * Creates a {@link PepperModule} of type {@link MODULE_TYPE#IMPORTER} and
	 * sets is name to the passed one.
	 */
	protected PepperImporterImpl(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc PepperImporter#getSupportedFormats()}
	 */
	public List<FormatDesc> getSupportedFormats() {
		return (getFingerprint().getSupportedFormats());
	}

	/**
	 * {@inheritDoc PepperImporter#addSupportedFormat(String, String, URI)}
	 */
	@Override
	public FormatDesc addSupportedFormat(String formatName, String formatVersion, URI formatReference) {
		return (getFingerprint().addSupportedFormat(formatName, formatVersion, formatReference));
	}

	/**
	 * TODO make docu
	 */
	protected CorpusDesc corpusDesc;

	/**
	 * {@inheritDoc PepperImporter#getCorpusDefinition()}
	 */
	@Override
	public CorpusDesc getCorpusDesc() {
		if (corpusDesc == null) {
			corpusDesc = new CorpusDesc();
		}
		return corpusDesc;
	}

	/**
	 * {@inheritDoc PepperImporter#setCorpusDefinition(CorpusDefinition)}
	 */
	@Override
	public void setCorpusDesc(CorpusDesc newCorpusDefinition) {
		this.corpusDesc = newCorpusDefinition;
	}

	/**
	 * {@inheritDoc PepperImporter#readFirstLines(URI, int)}
	 */
	@Override
	public String readFirstLines(final URI corpusPath, final int lines) {
		String retVal = null;
		if (corpusPath != null) {
			File importPath = new File(corpusPath.toFileString());
			try (BufferedReader br = new BufferedReader(new FileReader(importPath))) {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				int i = 0;
				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
					i++;
					if (i >= lines) {
						break;
					}
				}
				retVal = sb.toString();
			} catch (IOException e) {
				return (null);
			}
		}
		return (retVal);
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
		if (sElementId2ResourceTable == null)
			sElementId2ResourceTable = new Hashtable<Identifier, URI>();
		return sElementId2ResourceTable;
	}

	/**
	 * {@inheritDoc PepperImporter#importCorpusStructure(SCorpusGraph)}
	 */
	@Override
	public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException {
		this.setCorpusGraph(corpusGraph);
		if (this.getCorpusGraph() == null) {
			throw new PepperModuleException(this, "Cannot start with importing corpus, because salt project isn't set.");
		}
		if (this.getCorpusDesc() == null) {
			throw new PepperModuleException(this, "Cannot start with importing corpus, because no corpus definition to import is given.");
		}
		if (this.getCorpusDesc().getCorpusPath() == null) {
			throw new PepperModuleException(this, "Cannot start with importing corpus, because the path of given corpus definition is null.");
		}
		if (!this.getCorpusDesc().getCorpusPath().isFile()) {
			throw new PepperModuleException(this, "Cannot start with importing corpus, because the given corpus path does not locate a file.");
		}
		// clean uri in corpus path (if it is a folder and ends with/, / has to
		// be removed)
		if ((this.getCorpusDesc().getCorpusPath().toFileString().endsWith("/")) || (this.getCorpusDesc().getCorpusPath().toFileString().endsWith("\\"))) {
			this.getCorpusDesc().setCorpusPath(this.getCorpusDesc().getCorpusPath().trimSegments(1));
		}
		Boolean containsDocuments = importCorpusStructureRec(this.getCorpusDesc().getCorpusPath(), null);
		if (logger.isDebugEnabled()) {
			if (getIdentifier2ResourceTable().size() > 0) {
				StringBuilder str = new StringBuilder();
				str.append("[");
				str.append(getName());
				str.append("]");
				str.append(" import corpora and documents: \n");
				for (URI uri : getIdentifier2ResourceTable().values()) {
					str.append("\t");
					str.append(uri);
					str.append("\n");
				}
				logger.debug(str.toString());
			}
		}
		if (getIdentifier2ResourceTable().isEmpty()) {
			logger.warn("[{}] No corpora and documents fount to import in '{}'. ", getName(), this.getCorpusDesc().getCorpusPath());
		}
		if (!containsDocuments) {
			logger.warn("[{}] No documents fount to import in '{}'. ", getName(), this.getCorpusDesc().getCorpusPath());
		}
	}

	/**
	 * Top down traversal in file given structure. This method is called by
	 * {@link #importCorpusStructure(SCorpusGraph)} and creates the
	 * corpus-structure via a top down traversal in file structure. For each
	 * found file (real file and folder), the method
	 * {@link #setTypeOfResource(URI)} is called to set the type of the
	 * resource. If the type is a {@link SALT_TYPE#SDOCUMENT} a
	 * {@link SDocument} object is created for the resource, if the type is a
	 * {@link SALT_TYPE#SCORPUS} a {@link SCorpus} object is created, if the
	 * type is null, the resource is ignored.
	 * 
	 * @param currURI
	 * @param parentsID
	 * @param endings
	 * @return retrns true, if path contains documents, flase otherwise
	 * @throws IOException
	 */
	protected Boolean importCorpusStructureRec(URI currURI, SCorpus parent) {
		Boolean retVal = false;

		// set name for corpus graph
		if ((this.getCorpusGraph().getName() == null) || (this.getCorpusGraph().getName().isEmpty())) {
			this.getCorpusGraph().setName(currURI.lastSegment());
		}

		if ((currURI.lastSegment() != null) && (!this.getIgnoreEndings().contains(currURI.lastSegment()))) {// if
			SALT_TYPE type = this.setTypeOfResource(currURI);
			if (type != null) {
				// do not ignore resource create new id
				File currFile = new File(currURI.toFileString());

				if (SALT_TYPE.SCORPUS.equals(type)) {
					// resource is a SCorpus create corpus
					SCorpus sCorpus = getCorpusGraph().createCorpus(parent, currURI.lastSegment());
					this.getIdentifier2ResourceTable().put(sCorpus.getIdentifier(), currURI);
					if (currFile.isDirectory()) {
						File[] files = currFile.listFiles();
						if (files != null) {
							for (File file : files) {
								try {
									// if retval is true or returned value is
									// true
									// set retVal to true
									Boolean containsDocuments = importCorpusStructureRec(URI.createFileURI(file.getCanonicalPath()), sCorpus);
									retVal = (retVal || containsDocuments);
								} catch (IOException e) {
									throw new PepperModuleException("Cannot import corpus structure, because cannot create a URI out of file '" + file + "'. ", e);
								}
							}
						}
					}
				}// resource is a SCorpus
				else if (SALT_TYPE.SDOCUMENT.equals(type)) {
					retVal = true;
					// resource is a SDocument
					if (parent == null) {
						// if there is no corpus given, create one with name of
						// document
						parent = getCorpusGraph().createCorpus(null, currURI.lastSegment().replace("." + currURI.fileExtension(), ""));

						this.getIdentifier2ResourceTable().put(parent.getIdentifier(), currURI);
					}
					File docFile = new File(currURI.toFileString());
					SDocument sDocument = null;
					if (docFile.isDirectory()) {
						sDocument = getCorpusGraph().createDocument(parent, currURI.lastSegment());
					} else {
						// if uri is a file, cut off file ending
						sDocument = getCorpusGraph().createDocument(parent, currURI.lastSegment().replace("." + currURI.fileExtension(), ""));
					}
					// link documentId with resource
					this.getIdentifier2ResourceTable().put(sDocument.getIdentifier(), currURI);
				}// resource is a SDocument
			}// do not ignore resource
		}// if file is not part of ignore list
		return (retVal);
	}

	/**
	 * Overrides the method {@link PepperModuleImpl#start()} to add the
	 * following, before {@link PepperModuleImpl#start()} is called.
	 * <ol>
	 * <li>a check if corpus path exists</li>
	 * </ol>
	 */
	@Override
	public void start() throws PepperModuleException {
		if (getCorpusDesc().getCorpusPath() == null) {
			throw new WorkflowException("[" + getName() + "] Cannot import corpus-structure, because no corpus path was given. ");
		}
		File corpusFile = new File(getCorpusDesc().getCorpusPath().toFileString());
		if (!corpusFile.exists()) {
			throw new WorkflowException("[" + getName() + "] Cannot import corpus-structure, because the given corpus path '" + corpusFile.getAbsolutePath() + "' does not exist. ");
		}
		super.start();
	}

	/**
	 * Contains all endings, which determine a resource to be a resource for a
	 * {@link SDocument} object.
	 */
	private Collection<String> sDocumentEndings = null;

	/**
	 * {@inheritDoc PepperImporter#getDocumentEndings()}
	 */
	@Override
	public synchronized Collection<String> getDocumentEndings() {
		if (sDocumentEndings == null) {
			sDocumentEndings = new HashSet<String>();
		}
		return (sDocumentEndings);
	}

	/**
	 * Contains all endings, which determine a resource to be a resource for a
	 * {@link SCorpus} object.
	 */
	private Collection<String> sCorpusEndings = null;

	/**
	 * {@inheritDoc PepperImporter#getCorpusEndings()}
	 */
	@Override
	public synchronized Collection<String> getCorpusEndings() {
		if (sCorpusEndings == null) {
			sCorpusEndings = new HashSet<String>();
			sCorpusEndings.add(ENDING_FOLDER);
		}
		return (sCorpusEndings);
	}

	/**
	 * {@inheritDoc PepperImporter#setTypeOfResource(URI)}
	 */
	@Override
	public SALT_TYPE setTypeOfResource(URI resource) {
		File file = new File(resource.toFileString());
		if (file.isDirectory()) {// resource is a folder
			File folder = new File(resource.toFileString());
			if (isLeafFolder(folder)) {// resource is leaf folder
				if (this.getDocumentEndings().contains(ENDING_LEAF_FOLDER)) {
					return (SALT_TYPE.SDOCUMENT);
				} else if ((this.getCorpusEndings().contains(ENDING_FOLDER)) || (this.getCorpusEndings().contains(ENDING_LEAF_FOLDER))) {
					return (SALT_TYPE.SCORPUS);
				} else
					return (null);
			}// resource is leaf folder
			else {// resource is no leaf folder
				if (this.getCorpusEndings().contains(ENDING_FOLDER))
					return (SALT_TYPE.SCORPUS);
				else
					return (null);
			}// resource is no leaf folder

		}// resource is a folder
		else {// resource is not a folder
			String ending = resource.fileExtension();
			File resourceAsFile = new File(resource.toFileString());
			
			if(resourceAsFile.isHidden()) {
				// explicitly ignore hidden files as document
				return (null);
			} else {
				
				if (this.getDocumentEndings().contains(ENDING_ALL_FILES)) {
					return (SALT_TYPE.SDOCUMENT);
				} else if (this.getDocumentEndings().contains(ending)) {
					return (SALT_TYPE.SDOCUMENT);
				} else if (this.getCorpusEndings().contains(ending)) {
					return (SALT_TYPE.SCORPUS);
				} else {
					return (null);
				}
			}
		}// resource is not a folder
	}

	/**
	 * Computes if a folder is a leaf folder or not.
	 * 
	 * @return true, if folder is a leaf folder, false otherwise
	 **/
	private boolean isLeafFolder(File folder) {
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			if (files != null) {
				for (File file : files) {
					if ((file.isDirectory()) && (!getIgnoreEndings().contains(file.getName()))) {
						return (false);
					}
				}
			}
			return (true);
		} else {
			return (false);
		}
	}

	/**
	 * Collection of filenames, not to be imported, like '.svn'
	 */
	private Collection<String> importIgnoreList = null;

	/**
	 * Returns a collection of filenames, not to be imported. {@inheritDoc
	 * #importIgnoreList} .
	 * 
	 * @return
	 */
	@Override
	public synchronized Collection<String> getIgnoreEndings() {
		if (importIgnoreList == null) {
			importIgnoreList = new HashSet<String>();
			importIgnoreList.add(".svn");
		}
		return (importIgnoreList);
	}

	/**
	 * Helper method to read an xml file with a {@link DefaultHandler2}
	 * implementation given as <em>contentHandler</em>. It is assumed, that the
	 * file encoding is set to UTF-8.
	 * 
	 * @param contentHandler
	 *            {@link DefaultHandler2} implementation
	 * @param documentLocation
	 *            location of the xml-file
	 */
	protected void readXMLResource(DefaultHandler2 contentHandler, URI documentLocation) {
		PepperUtil.readXMLResource(contentHandler, documentLocation);
	}

	/**
	 * {@inheritDoc PepperImporter#isImportable(URI)}
	 */
	@Override
	public Double isImportable(URI corpusPath) {
		return null;
	}
}

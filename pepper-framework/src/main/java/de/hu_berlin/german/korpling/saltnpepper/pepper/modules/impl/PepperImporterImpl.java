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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.common.util.URI;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.WorkflowException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleXMLResourceException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * TODO make docu
 * 
 * @author Florian Zipser
 */
public abstract class PepperImporterImpl extends PepperModuleImpl implements PepperImporter {
	/**
	 * TODO make docu
	 */
	protected PepperImporterImpl() {
		super();
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
		if (corpusDesc== null){
			corpusDesc= new CorpusDesc();
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

	/**
	 * {@inheritDoc PepperImporter#importCorpusStructure(SCorpusGraph)}
	 */
	@Override
	public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException {
		this.setSCorpusGraph(corpusGraph);
		if (this.getSCorpusGraph() == null){
			throw new PepperModuleException(this, "Cannot start with importing corpus, because salt project isn't set.");
		}
		if (this.getCorpusDesc() == null){
			throw new PepperModuleException(this, "Cannot start with importing corpus, because no corpus definition to import is given.");
		}
		if (this.getCorpusDesc().getCorpusPath() == null){
			throw new PepperModuleException(this, "Cannot start with importing corpus, because the path of given corpus definition is null.");
		}
		if (!this.getCorpusDesc().getCorpusPath().isFile()){
			throw new PepperModuleException(this, "Cannot start with importing corpus, because the given corpus path does not locate a file.");
		}
		// clean uri in corpus path (if it is a folder and ends with/, / has to
		// be removed)
		if ((this.getCorpusDesc().getCorpusPath().toFileString().endsWith("/")) || (this.getCorpusDesc().getCorpusPath().toFileString().endsWith("\\"))) {
			this.getCorpusDesc().setCorpusPath(this.getCorpusDesc().getCorpusPath().trimSegments(1));
		}
		importCorpusStructureRec(this.getCorpusDesc().getCorpusPath(), null);
	}

	/**
	 * Top down traversal in file given structure. This method is called by
	 * {@link #importCorpusStructure(SCorpusGraph)} and creates the
	 * corpus-structure via a top down traversal in file structure. For each
	 * found file (real file and folder), the method
	 * {@link #setTypeOfResource(URI)} is called to set the type of the
	 * resource. If the type is a {@link STYPE_NAME#SDOCUMENT} a
	 * {@link SDocument} object is created for the resource, if the type is a
	 * {@link STYPE_NAME#SCORPUS} a {@link SCorpus} object is created, if the
	 * type is null, the resource is ignored.
	 * 
	 * @param currURI
	 * @param parentsID
	 * @param endings
	 * @throws IOException
	 */
	protected void importCorpusStructureRec(URI currURI, SCorpus parent) {
		// set name for corpus graph
		if ((this.getSCorpusGraph().getSName() == null) || (this.getSCorpusGraph().getSName().isEmpty())) {
			this.getSCorpusGraph().setSName(currURI.lastSegment());
		}

		if ((currURI.lastSegment() != null) && (!this.getIgnoreEndings().contains(currURI.lastSegment()))) {// if
			STYPE_NAME type = this.setTypeOfResource(currURI);
			if (type != null) {
				// do not ignore resource create new id
				File currFile = new File(currURI.toFileString());

				if (STYPE_NAME.SCORPUS.equals(type)) {
					// resource is a SCorpus create corpus
					SCorpus sCorpus = getSCorpusGraph().createSCorpus(parent, currURI.lastSegment());
					this.getSElementId2ResourceTable().put(sCorpus.getSElementId(), currURI);
					if (currFile.isDirectory()) {
						for (File file : currFile.listFiles()) {
							try {
								this.importCorpusStructureRec(URI.createFileURI(file.getCanonicalPath()), sCorpus);
							} catch (IOException e) {
								throw new PepperModuleException("Cannot import corpus structure, because cannot create a URI out of file '" + file + "'. ", e);
							}
						}
					}
				}// resource is a SCorpus
				else if (STYPE_NAME.SDOCUMENT.equals(type)) {
					// resource is a SDocument
					if (parent == null) {
						// if there is no corpus given, create one with name of document
						parent = getSCorpusGraph().createSCorpus(null, currURI.lastSegment().replace("." + currURI.fileExtension(), ""));
						
						this.getSElementId2ResourceTable().put(parent.getSElementId(), currURI);
					}
					File docFile= new File(currURI.toFileString());
					SDocument sDocument= null;
					if (docFile.isDirectory()){
						sDocument = getSCorpusGraph().createSDocument(parent, currURI.lastSegment());
					}else{
						//if uri is a file, cut off file ending
						sDocument = getSCorpusGraph().createSDocument(parent, currURI.lastSegment().replace("." + currURI.fileExtension(), ""));
					}
					this.getSElementId2ResourceTable().put(sDocument.getSElementId(), currURI);
				}// resource is a SDocument
					// link documentId with resource
			}// do not ignore resource
		}// if file is not part of ignore list
	}
	
	/**
	 * Overrides the method {@link PepperModuleImpl#start()} to add the following, 
	 * before {@link PepperModuleImpl#start()} is called.
	 * <ol>
	 *  <li>a check if corpus path exists</li>
	 * </ol>
	 */
	@Override
	public void start() throws PepperModuleException {
		if (getCorpusDesc()== null){
			throw new WorkflowException("Cannot import corpus-structure, because no corpus description was given. ");
		}
		if (getCorpusDesc().getCorpusPath()== null){
			throw new WorkflowException("Cannot import corpus-structure, because no corpus path was given. ");
		}
		File corpusFile= new File(getCorpusDesc().getCorpusPath().toFileString());
		if (!corpusFile.exists()){
			throw new WorkflowException("Cannot import corpus-structure, because the given corpus path '"+corpusFile.getAbsolutePath()+"' does not exist. ");
		}
		super.start();
	}

	/**
	 * Contains all endings, which determine a resource to be a resource for a
	 * {@link SDocument} object.
	 */
	private Collection<String> sDocumentEndings = null;

	/**
	 * {@inheritDoc PepperImporter#getSDocumentEndings()}
	 */
	@Override
	public synchronized Collection<String> getSDocumentEndings() {
		if (sDocumentEndings == null)
			sDocumentEndings = new HashSet<String>();
		return (sDocumentEndings);
	}

	/**
	 * Contains all endings, which determine a resource to be a resource for a
	 * {@link SCorpus} object.
	 */
	private Collection<String> sCorpusEndings = null;

	/**
	 * {@inheritDoc PepperImporter#getSCorpusEndings()}
	 */
	@Override
	public synchronized Collection<String> getSCorpusEndings() {
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
	public STYPE_NAME setTypeOfResource(URI resource) {
		File file = new File(resource.toFileString());
		if (file.isDirectory()) {// resource is a folder
			File folder = new File(resource.toFileString());
			if (isLeafFolder(folder)) {// resource is leaf folder
				if (this.getSDocumentEndings().contains(ENDING_LEAF_FOLDER)) {
					return (STYPE_NAME.SDOCUMENT);
				} else if ((this.getSCorpusEndings().contains(ENDING_FOLDER)) || (this.getSCorpusEndings().contains(ENDING_LEAF_FOLDER))) {
					return (STYPE_NAME.SCORPUS);
				} else
					return (null);
			}// resource is leaf folder
			else {// resource is no leaf folder
				if (this.getSCorpusEndings().contains(ENDING_FOLDER))
					return (STYPE_NAME.SCORPUS);
				else
					return (null);
			}// resource is no leaf folder

		}// resource is a folder
		else {// resource is not a folder
			String ending = resource.fileExtension();
			if (this.getSDocumentEndings().contains(ENDING_ALL_FILES)) {
				return (STYPE_NAME.SDOCUMENT);
			} else if (this.getSDocumentEndings().contains(ending)) {
				return (STYPE_NAME.SDOCUMENT);
			} else if (this.getSCorpusEndings().contains(ending)) {
				return (STYPE_NAME.SCORPUS);
			} else {
				return (null);
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
			for (File file : folder.listFiles()) {
				if ((file.isDirectory()) && (!getIgnoreEndings().contains(file.getName())))
					return (false);
			}
			return (true);
		} else
			return (false);
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
	// TODO moved to PepperMapperIMpl
	protected void readXMLResource(DefaultHandler2 contentHandler, URI documentLocation) {
		if (documentLocation == null)
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the given uri to locate file is null.");

		File exmaraldaFile = new File(documentLocation.toFileString());
		if (!exmaraldaFile.exists())
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the file does not exist: " + exmaraldaFile);

		if (!exmaraldaFile.canRead())
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the file can not be read: " + exmaraldaFile);

		SAXParser parser;
		XMLReader xmlReader;

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			parser = factory.newSAXParser();
			xmlReader = parser.getXMLReader();
			xmlReader.setContentHandler(contentHandler);
		} catch (ParserConfigurationException e) {
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource '" + exmaraldaFile.getAbsolutePath() + "'.", e);
		} catch (Exception e) {
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource '" + exmaraldaFile.getAbsolutePath() + "'.", e);
		}
		try {
			InputStream inputStream = new FileInputStream(exmaraldaFile);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			xmlReader.parse(is);
		} catch (SAXException e) {

			try {
				parser = factory.newSAXParser();
				xmlReader = parser.getXMLReader();
				xmlReader.setContentHandler(contentHandler);
				xmlReader.parse(exmaraldaFile.getAbsolutePath());
			} catch (Exception e1) {
				throw new PepperModuleXMLResourceException("Cannot load a xml-resource '" + exmaraldaFile.getAbsolutePath() + "'.", e1);
			}
		} catch (Exception e) {
			if (e instanceof PepperModuleException)
				throw (PepperModuleException) e;
			else
				throw new PepperModuleXMLResourceException("Cannot read xml-file'" + documentLocation + "', because of a nested exception. ", e);
		}
	}

	/**
	 * {@inheritDoc PepperImporter#isImportable(URI)}
	 */
	@Override
	public Double isImportable(URI corpusPath) {
		return null;
	}
}

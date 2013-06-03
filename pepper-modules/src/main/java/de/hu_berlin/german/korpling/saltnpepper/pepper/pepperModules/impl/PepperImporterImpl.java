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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperImporterException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleXMLResourceException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusDocumentRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Importer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl#getSupportedFormats <em>Supported Formats</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl#getCorpusDefinition <em>Corpus Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class PepperImporterImpl extends PepperModuleImpl implements PepperImporter {
	/**
	 * The cached value of the '{@link #getSupportedFormats() <em>Supported Formats</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupportedFormats()
	 * @generated
	 * @ordered
	 */
	protected EList<FormatDefinition> supportedFormats;

	/**
	 * The cached value of the '{@link #getCorpusDefinition() <em>Corpus Definition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorpusDefinition()
	 * @generated
	 * @ordered
	 */
	protected CorpusDefinition corpusDefinition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperImporterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperModulesPackage.Literals.PEPPER_IMPORTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FormatDefinition> getSupportedFormats() {
		if (supportedFormats == null) {
			supportedFormats = new EObjectContainmentEList<FormatDefinition>(FormatDefinition.class, this, PepperModulesPackage.PEPPER_IMPORTER__SUPPORTED_FORMATS);
		}
		return supportedFormats;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorpusDefinition getCorpusDefinition() {
		return corpusDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCorpusDefinition(CorpusDefinition newCorpusDefinition, NotificationChain msgs) {
		CorpusDefinition oldCorpusDefinition = corpusDefinition;
		corpusDefinition = newCorpusDefinition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_IMPORTER__CORPUS_DEFINITION, oldCorpusDefinition, newCorpusDefinition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorpusDefinition(CorpusDefinition newCorpusDefinition) {
		if (newCorpusDefinition != corpusDefinition) {
			NotificationChain msgs = null;
			if (corpusDefinition != null)
				msgs = ((InternalEObject)corpusDefinition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PepperModulesPackage.PEPPER_IMPORTER__CORPUS_DEFINITION, null, msgs);
			if (newCorpusDefinition != null)
				msgs = ((InternalEObject)newCorpusDefinition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PepperModulesPackage.PEPPER_IMPORTER__CORPUS_DEFINITION, null, msgs);
			msgs = basicSetCorpusDefinition(newCorpusDefinition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_IMPORTER__CORPUS_DEFINITION, newCorpusDefinition, newCorpusDefinition));
	}

	/**
	 * Creates a new {@link FormatDefinitionImpl} objects, sets its values 
	 * {@link FormatDefinition#setFormatName(String)}, {@link FormatDefinition#setFormatVersion(String)} and
	 * {@link FormatDefinition#setFormatReference(URI)} to the given ones, adds the created object to
	 * list of supported formats {@link #getSupportedFormats()} and returns it.
	 * @param pepperModule the importer or exporter, to whichs supported formats list the format shall be added
	 * @param formatName name of the supported format to add
	 * @param formatVersion version of the supported format to add
	 * @param formatReference uri reference where the supported format to add is described or defined
	 */
	public FormatDefinition addSupportedFormat(String formatName, String formatVersion, URI formatReference) {
		return(PepperExporterImpl.addSupportedFormat(this, formatName, formatVersion, formatReference));
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
	 * {@inheritDoc PepperImporter#importCorpusStructure(SCorpusGraph)}
	 */
	@Override
	public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException 
	{
		this.setSCorpusGraph(corpusGraph);
		if (this.getSCorpusGraph()== null)
			throw new PepperImporterException(this.name+": Cannot start with importing corpus, because salt project isn�t set.");
		
		if (this.getCorpusDefinition()== null)
			throw new PepperImporterException(this.name+": Cannot start with importing corpus, because no corpus definition to import is given.");
		if (this.getCorpusDefinition().getCorpusPath()== null)
			throw new PepperImporterException(this.name+": Cannot start with importing corpus, because the path of given corpus definition is null.");
		
		
		if (!this.getCorpusDefinition().getCorpusPath().isFile())
			throw new PepperImporterException(this.name+": Cannot start with importing corpus, because the given corpus path does not locate a file.");
		
		
		//clean uri in corpus path (if it is a folder and ends with/, / has to be removed)
		if (	(this.getCorpusDefinition().getCorpusPath().toFileString().endsWith("/")) || 
				(this.getCorpusDefinition().getCorpusPath().toFileString().endsWith("\\")))
		{
			this.getCorpusDefinition().setCorpusPath(this.getCorpusDefinition().getCorpusPath().trimSegments(1));
		}
		importCorpusStructureRec(this.getCorpusDefinition().getCorpusPath(), null);
	}
	
	/**
	 * Top down traversal in file given structure. This method is called by {@link #importCorpusStructure(SCorpusGraph)} and creates
	 * the corpus-structure via a top down traversal in file structure. For each found file (real file and folder), the method 
	 * {@link #setTypeOfResource(URI)} is called to set the type of the resource. If the type is a {@link STYPE_NAME#SDOCUMENT} a {@link SDocument}
	 * object is created for the resource, if the type is a {@link STYPE_NAME#SCORPUS} a {@link SCorpus} object is created, if the type
	 * is null, the resource is ignored.  
	 * @param currURI
	 * @param parentsID
	 * @param endings
	 * @throws IOException
	 */
	protected void importCorpusStructureRec(URI currURI, SElementId parentsID)
	{
		//set name for corpus graph
		if (	(this.getSCorpusGraph().getSName()== null) || 
				(this.getSCorpusGraph().getSName().isEmpty()))
		{
			this.getSCorpusGraph().setSName(currURI.lastSegment());
		}
		
		if (	(currURI.lastSegment()!= null)&&
				(!this.getIgnoreEndings().contains(currURI.lastSegment())))
		{//if file is not part of ignore list	
			STYPE_NAME type= this.setTypeOfResource(currURI);
			if (type!= null)
			{//do not ignore resource
				//create new id
				SElementId currId= SaltCommonFactory.eINSTANCE.createSElementId();
				
				File currFile= new File(currURI.toFileString());
				
				if (STYPE_NAME.SCORPUS.equals(type))
				{//resource is a SCorpus
					if (parentsID== null)
						currId.setSId("/"+currURI.lastSegment());
					else currId.setSId(parentsID.getSId()+"/"+currURI.lastSegment());
					//create corpus
					SCorpus sCorpus= SaltCommonFactory.eINSTANCE.createSCorpus();
					sCorpus.setSElementId(currId);
					sCorpus.setSName(currURI.lastSegment());
					this.getSCorpusGraph().addSNode(sCorpus);
					//if corpus has a parent
					if (parentsID!= null)
					{
						SCorpus parentCorpus= this.getSCorpusGraph().getSCorpus(parentsID);
						SCorpusRelation sCorpRel= SaltCommonFactory.eINSTANCE.createSCorpusRelation();
						sCorpRel.setSSuperCorpus(parentCorpus);
						sCorpRel.setSSubCorpus(sCorpus);
						this.getSCorpusGraph().addSRelation(sCorpRel);
					}	
					
					if (currFile.isDirectory())
					{
						for (File file: currFile.listFiles())
						{
							try {
								this.importCorpusStructureRec(URI.createFileURI(file.getCanonicalPath()), currId);
							} catch (IOException e) {
								throw new PepperImporterException("Cannot import corpus structure, because cannot create a URI out of file '"+file+"'. ", e);
							}
						}
					}
				}//resource is a SCorpus
				else if (STYPE_NAME.SDOCUMENT.equals(type))
				{//resource is a SDocument
					if (parentsID== null)
					{//if there is no corpus given, create one with name of document
						parentsID = SaltCommonFactory.eINSTANCE.createSElementId();
						parentsID.setSId(currURI.lastSegment().replace("."+currURI.fileExtension(), ""));
						//create corpus
						SCorpus sCorpus= SaltCommonFactory.eINSTANCE.createSCorpus();
						sCorpus.setSElementId(parentsID);	
						this.getSCorpusGraph().addSNode(sCorpus);
						sCorpus.setSName(parentsID.getSId());
					}
					currId.setSId(parentsID.getSId()+"/"+currURI.lastSegment().replace("."+currURI.fileExtension(), ""));			
					
					//start: create a new document 
					SDocument sDocument= SaltCommonFactory.eINSTANCE.createSDocument();
					sDocument.setSElementId(currId);
					sDocument.setSName(currURI.lastSegment().replace("."+currURI.fileExtension(), ""));
					this.getSCorpusGraph().addSNode(sDocument);
					SCorpusDocumentRelation sCorpDocRel= SaltCommonFactory.eINSTANCE.createSCorpusDocumentRelation();
					sCorpDocRel.setSCorpus(this.getSCorpusGraph().getSCorpus(parentsID));
					sCorpDocRel.setSDocument(sDocument);
					this.getSCorpusGraph().addSRelation(sCorpDocRel);
				}//resource is a SDocument
				//link documentId with resource
				this.getSElementId2ResourceTable().put(currId, currURI);
			}//do not ignore resource
		}//if file is not part of ignore list
	}
	
	/**
	 * Contains all endings, which determine a resource to be a resource for a {@link SDocument} object. 
	 */
	private Collection<String> sDocumentEndings= null;
	/**
	 * {@inheritDoc PepperImporter#getSDocumentEndings()}
	 */
	@Override
	public synchronized Collection<String> getSDocumentEndings()
	{
		if (sDocumentEndings== null)
			sDocumentEndings= new HashSet<String>();
		return(sDocumentEndings);
	}
	
	/**
	 * Contains all endings, which determine a resource to be a resource for a {@link SCorpus} object. 
	 */
	private Collection<String> sCorpusEndings= null;
	/**
	 * {@inheritDoc PepperImporter#getSCorpusEndings()}
	 */
	@Override
	public synchronized Collection<String> getSCorpusEndings()
	{
		if (sCorpusEndings== null)
		{
			sCorpusEndings= new HashSet<String>();
			sCorpusEndings.add(ENDING_FOLDER);
		}
		return(sCorpusEndings);
	}
	
	/**
	 * {@inheritDoc PepperImporter#setTypeOfResource(URI)}
	 */
	@Override
	public STYPE_NAME setTypeOfResource(URI resource)
	{
		File file= new File(resource.toFileString());
		if (file.isDirectory())
		{//resource is a folder 
			File folder= new File(resource.toFileString());
			if (isLeafFolder(folder))
			{//resource is leaf folder
				if (this.getSDocumentEndings().contains(ENDING_LEAF_FOLDER))
				{
					return(STYPE_NAME.SDOCUMENT);
				}
				else if (	(this.getSCorpusEndings().contains(ENDING_FOLDER))||
							(this.getSCorpusEndings().contains(ENDING_LEAF_FOLDER)))
				{
					return(STYPE_NAME.SCORPUS);
				}
				else return(null);
			}//resource is leaf folder
			else
			{//resource is no leaf folder
				if (this.getSCorpusEndings().contains(ENDING_FOLDER))
					return(STYPE_NAME.SCORPUS);
				else return(null);
			}//resource is no leaf folder
			
		}//resource is a folder
		else
		{// resource is not a folder
			String ending= resource.fileExtension();
			if (this.getSDocumentEndings().contains(ENDING_ALL_FILES))
			{
				return(STYPE_NAME.SDOCUMENT);
			}
			else if (this.getSDocumentEndings().contains(ending))
			{
				return(STYPE_NAME.SDOCUMENT);
			}
			else if (this.getSCorpusEndings().contains(ending))
			{
				return(STYPE_NAME.SCORPUS);
			}
			else 
			{
				return(null);
			}
		}// resource is not a folder
	}
	
	/** 
	 * Computes if a folder is a leaf folder or not.
	 * @return true, if folder is a leaf folder, false otherwise
	 **/
	private boolean isLeafFolder(File folder)
	{
		if (folder.isDirectory())
		{
			for (File file: folder.listFiles())
			{
				if (	(file.isDirectory())&&
						(!getIgnoreEndings().contains(file.getName())))
					return(false);
			}
			return(true);
		}
		else return(false);
	}
	
	/**
	 * Collection of filenames, not to be imported, like '.svn'
	 */
	private Collection<String> importIgnoreList= null;
	/**
	 * Returns a collection of filenames, not to be imported. {@inheritDoc #importIgnoreList}}.
	 * @return
	 */
	@Override
	public synchronized Collection<String> getIgnoreEndings()
	{
		if (importIgnoreList== null)
		{
			importIgnoreList= new HashSet<String>();
			importIgnoreList.add(".svn");
		}
		return(importIgnoreList);
	}
	
	/**
	 * Helper method to read an xml file with a {@link DefaultHandler2} implementation given as <em>contentHandler</em>. It is assumed,
	 * that the file encoding is set to UTF-8. 
	 * @param contentHandler {@link DefaultHandler2} implementation
	 * @param documentLocation location of the xml-file
	 */
	//TODO moved to PepperMapperIMpl
	protected void readXMLResource(	DefaultHandler2 contentHandler, 
									URI documentLocation)
	{
		if (documentLocation== null)
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the given uri to locate file is null.");
		
		File exmaraldaFile= new File(documentLocation.toFileString());
		if (!exmaraldaFile.exists()) 
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the file does not exist: " + exmaraldaFile);
		
		if (!exmaraldaFile.canRead())
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the file can not be read: " + exmaraldaFile);
		
		SAXParser parser;
        XMLReader xmlReader;
        
        SAXParserFactory factory= SAXParserFactory.newInstance();
        
        try
        {
			parser= factory.newSAXParser();
	        xmlReader= parser.getXMLReader();
	        xmlReader.setContentHandler(contentHandler);
        } catch (ParserConfigurationException e) {
        	throw new PepperModuleXMLResourceException("Cannot load a xml-resource '"+exmaraldaFile.getAbsolutePath()+"'.", e);
        }catch (Exception e) {
	    	throw new PepperModuleXMLResourceException("Cannot load a xml-resource '"+exmaraldaFile.getAbsolutePath()+"'.", e);
		}
        try {
	        InputStream inputStream= new FileInputStream(exmaraldaFile);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			xmlReader.parse(is);
        } catch (SAXException e) 
        {
        	
            try
            {
				parser= factory.newSAXParser();
		        xmlReader= parser.getXMLReader();
		        xmlReader.setContentHandler(contentHandler);
				xmlReader.parse(exmaraldaFile.getAbsolutePath());
            }catch (Exception e1) {
            	throw new PepperModuleXMLResourceException("Cannot load a xml-resource '"+exmaraldaFile.getAbsolutePath()+"'.", e1);
			}
		}
        catch (Exception e) {
			if (e instanceof PepperModuleException)
				throw (PepperModuleException)e;
			else throw new PepperModuleXMLResourceException("Cannot read xml-file'"+documentLocation+"', because of a nested exception. ",e);
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperModulesPackage.PEPPER_IMPORTER__SUPPORTED_FORMATS:
				return ((InternalEList<?>)getSupportedFormats()).basicRemove(otherEnd, msgs);
			case PepperModulesPackage.PEPPER_IMPORTER__CORPUS_DEFINITION:
				return basicSetCorpusDefinition(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperModulesPackage.PEPPER_IMPORTER__SUPPORTED_FORMATS:
				return getSupportedFormats();
			case PepperModulesPackage.PEPPER_IMPORTER__CORPUS_DEFINITION:
				return getCorpusDefinition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PepperModulesPackage.PEPPER_IMPORTER__SUPPORTED_FORMATS:
				getSupportedFormats().clear();
				getSupportedFormats().addAll((Collection<? extends FormatDefinition>)newValue);
				return;
			case PepperModulesPackage.PEPPER_IMPORTER__CORPUS_DEFINITION:
				setCorpusDefinition((CorpusDefinition)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PepperModulesPackage.PEPPER_IMPORTER__SUPPORTED_FORMATS:
				getSupportedFormats().clear();
				return;
			case PepperModulesPackage.PEPPER_IMPORTER__CORPUS_DEFINITION:
				setCorpusDefinition((CorpusDefinition)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PepperModulesPackage.PEPPER_IMPORTER__SUPPORTED_FORMATS:
				return supportedFormats != null && !supportedFormats.isEmpty();
			case PepperModulesPackage.PEPPER_IMPORTER__CORPUS_DEFINITION:
				return corpusDefinition != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 *  {@inheritDoc PepperImporter#isImportable(URI)}}
	 */
	@Override
	public Double isImportable(URI corpusPath) {
		return null;
	}
} //PepperImporterImpl

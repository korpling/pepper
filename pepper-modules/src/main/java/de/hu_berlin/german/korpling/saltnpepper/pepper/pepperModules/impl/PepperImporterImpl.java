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
import java.util.Hashtable;
import java.util.List;
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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleXMLResourceException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusDocumentRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
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
	 * {@inheritDoc}
	 * 
	 */
	public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException {
		throw new PepperModuleException("An error occurs in this importer-module (name: "+this.getName()+"). The method importCorpusStructure() isn�t implemented.");
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
	 * Stores relation between documents and their resource 
	 */
	private Map<SElementId, URI> documentResourceTable= null;
	
	/**
	 * This method can be overridden by derived classes. This method is called by the method {@link #createCorpusStructureRec(URI, SElementId, EList)},
	 * to check if a file shall be imported into a {@link SDocument} object. For instance, if the file type is not precise enough
	 * to check that, a derived class of the given class can override that method to read the content of the file and to make a decision
	 * corresponding to its content. 
	 * @param checkUri the {@link URI} locating the file to check 
	 * @return true, if and only if the file corresponding to the given {@link URI} shall be mapped to a {@link SDocument} object 
	 */
	protected boolean isFileToImport(URI checkUri)
	{ 
		if (checkUri== null)
			throw new PepperModuleException("Cannot check if the given uri can be mapped to a SDocument object, because the uri is null.");
		return(true); 
	}
	
	/**
	 * {@inheritDoc PepperImporter#isFileToImport(URI, List)}
	 */
	public boolean isFileToImport(URI checkUri, List<String> fileExtensions)
	{
		boolean retVal= true;
		if (checkUri== null)
			retVal= false;
		if (	(fileExtensions!= null)&&
				(fileExtensions.size()> 0))
		{
			boolean isNegativeList=false;
			boolean isNegativeOccurance= false;
			boolean isPositiveOccurance= false;
			for (String fileExtension: fileExtensions)
			{
				
				if (fileExtension.contains(NEGATIVE_FILE_EXTENSION_MARKER))
				{
					if (fileExtension.equalsIgnoreCase(NEGATIVE_FILE_EXTENSION_MARKER+checkUri.fileExtension()))
						isNegativeOccurance= true;
					isNegativeList= true;
				}
				else if (fileExtension.equalsIgnoreCase(checkUri.fileExtension()))
					isPositiveOccurance= true;
			}
			if (isNegativeList)
			{
				if (isNegativeOccurance)
					retVal= false;
				else retVal=true;
			}
			else 
			{
				if (isPositiveOccurance)
					retVal= true;
				else retVal= false;
			}
		}
		return(retVal);
	}
	/**
	 * {@inheritDoc PepperImporter#createCorpusStructure(URI, SElementId, EList)}
	 */
	public Map<SElementId, URI> createCorpusStructure(	URI currURI, 
														SElementId parentsID, 
														EList<String> endings) throws IOException
	{
		if (this.getSCorpusGraph()== null)
			this.setSCorpusGraph(SaltFactory.eINSTANCE.createSCorpusGraph());
		documentResourceTable= new Hashtable<SElementId, URI>();
		this.createCorpusStructureRec(currURI, parentsID, endings);
		
		return(this.documentResourceTable);
	}
	/**
	 * Traverses recursively the folder structure to create a corpus-structure from it and creates a {@link SDocument} object,
	 * in case of the file type given in <code>endings</code> is correct and the method {@link #isFileASDocument()} returns true.
	 * @param currURI
	 * @param parentsID
	 * @param endings
	 * @throws IOException
	 */
	private void createCorpusStructureRec(URI currURI, SElementId parentsID, EList<String> endings) throws IOException
	{
		String corpGraphName= null;
		if (!".svn".equalsIgnoreCase(currURI.lastSegment()))
		{	
			File currFile= new File(currURI.toFileString());
			//if uri is a directory, create a corpus
			if (currFile.isDirectory())
			{
				//create new id
				SElementId currId= SaltCommonFactory.eINSTANCE.createSElementId();
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
				for (File file: currFile.listFiles())
				{
					this.createCorpusStructureRec(URI.createFileURI(file.getCanonicalPath()), currId, endings);
				}
			}	
			//if uri is a file create document and possibly a corpus 
			else 
			{
				SElementId currId= SaltCommonFactory.eINSTANCE.createSElementId();
				
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
					if (isFileToImport(currURI, endings))
					{//the file has the correct ending
						SDocument sDocument= SaltCommonFactory.eINSTANCE.createSDocument();
						sDocument.setSElementId(currId);
						sDocument.setSName(currURI.lastSegment().replace("."+currURI.fileExtension(), ""));
						this.getSCorpusGraph().addSNode(sDocument);
						SCorpusDocumentRelation sCorpDocRel= SaltCommonFactory.eINSTANCE.createSCorpusDocumentRelation();
						sCorpDocRel.setSCorpus(this.getSCorpusGraph().getSCorpus(parentsID));
						sCorpDocRel.setSDocument(sDocument);
						this.getSCorpusGraph().addSRelation(sCorpDocRel);
						//link documentId with resource
						this.documentResourceTable.put(currId, currURI);
					}
					
				//end: create a new document
				corpGraphName=parentsID.getSId();
				
				//setting name for corpus graph
				if (	(this.getSCorpusGraph().getSName()== null) || 
						(this.getSCorpusGraph().getSName().isEmpty()))
					this.getSCorpusGraph().setSName(corpGraphName);
			}
		}
	}
	
	/**
	 * Helper method to read an xml file with a {@link DefaultHandler2} implementation given as <em>contentHandler</em>. It is assumed,
	 * that the file encoding is set to UTF-8. 
	 * @param contentHandler {@link DefaultHandler2} implementation
	 * @param documentLocation location of the xml-file
	 */
	protected void readXMLResource(	DefaultHandler2 contentHandler, 
									URI documentLocation)
	{
		if (documentLocation== null)
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the given uri to locate file is null.");
		
		File exmaraldaFile= new File(documentLocation.toFileString());
		if (!exmaraldaFile.exists()) 
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the file does not exists: " + exmaraldaFile);
		
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

} //PepperImporterImpl

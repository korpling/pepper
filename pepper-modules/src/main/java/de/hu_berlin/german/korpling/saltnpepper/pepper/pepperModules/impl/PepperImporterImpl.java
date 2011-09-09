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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusDocumentRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

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
public class PepperImporterImpl extends PepperModuleImpl implements PepperImporter {
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
		return PepperInterfacePackage.Literals.PEPPER_IMPORTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FormatDefinition> getSupportedFormats() {
		if (supportedFormats == null) {
			supportedFormats = new EObjectContainmentEList<FormatDefinition>(FormatDefinition.class, this, PepperInterfacePackage.PEPPER_IMPORTER__SUPPORTED_FORMATS);
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperInterfacePackage.PEPPER_IMPORTER__CORPUS_DEFINITION, oldCorpusDefinition, newCorpusDefinition);
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
				msgs = ((InternalEObject)corpusDefinition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PepperInterfacePackage.PEPPER_IMPORTER__CORPUS_DEFINITION, null, msgs);
			if (newCorpusDefinition != null)
				msgs = ((InternalEObject)newCorpusDefinition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PepperInterfacePackage.PEPPER_IMPORTER__CORPUS_DEFINITION, null, msgs);
			msgs = basicSetCorpusDefinition(newCorpusDefinition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperInterfacePackage.PEPPER_IMPORTER__CORPUS_DEFINITION, newCorpusDefinition, newCorpusDefinition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void importCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException {
		throw new PepperModuleException("An error occurs in this importer-module (name: "+this.getName()+"). The method importCorpusStructure() isn�t implemented.");
	}

	/**
	 * Stores relation between documents and their resource 
	 */
	private Map<SElementId, URI> documentResourceTable= null;
	
	/**
	 * Generates a corpus structure out of the folder structure, that is given by uri.
	 * 
	 * @param currURI the parent folder of folder structure.
	 * @param parentsID the elementid, reffering to the element, to which the corpus structure has to be added
	 * @param endings a list of endings which identifiy a document in watched format. Please attend, that endings are without ".", e.g. (xml, dot, txt...). If endings is null, all files will be used.
	 * @return a map of elementid corresponing to a directory
	 * @throws IOException
	 */
	protected Map<SElementId, URI> createCorpusStructure(	URI currURI, 
															SElementId parentsID, 
															EList<String> endings) throws IOException
	{
		documentResourceTable= new Hashtable<SElementId, URI>();
		this.createCorpusStructureRec(currURI, parentsID, endings);
		
		return(this.documentResourceTable);
	}
		
	private void createCorpusStructureRec(URI currURI, SElementId parentsID, EList<String> endings) throws IOException
	{
		String corpGraphName= null;
		if (!currURI.lastSegment().equalsIgnoreCase(".svn"))
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
				
				{//create a new document 
					if (	(endings== null) ||
							(endings.contains(currURI.fileExtension())))
					{//the file has corrct ending
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
				}
				corpGraphName=parentsID.getSId();
				
				//setting name for corpus graph
				if (	(this.getSCorpusGraph().getSName()== null) || 
						(this.getSCorpusGraph().getSName().equalsIgnoreCase("")))
					this.getSCorpusGraph().setSName(corpGraphName);
			}
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
			case PepperInterfacePackage.PEPPER_IMPORTER__SUPPORTED_FORMATS:
				return ((InternalEList<?>)getSupportedFormats()).basicRemove(otherEnd, msgs);
			case PepperInterfacePackage.PEPPER_IMPORTER__CORPUS_DEFINITION:
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
			case PepperInterfacePackage.PEPPER_IMPORTER__SUPPORTED_FORMATS:
				return getSupportedFormats();
			case PepperInterfacePackage.PEPPER_IMPORTER__CORPUS_DEFINITION:
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
			case PepperInterfacePackage.PEPPER_IMPORTER__SUPPORTED_FORMATS:
				getSupportedFormats().clear();
				getSupportedFormats().addAll((Collection<? extends FormatDefinition>)newValue);
				return;
			case PepperInterfacePackage.PEPPER_IMPORTER__CORPUS_DEFINITION:
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
			case PepperInterfacePackage.PEPPER_IMPORTER__SUPPORTED_FORMATS:
				getSupportedFormats().clear();
				return;
			case PepperInterfacePackage.PEPPER_IMPORTER__CORPUS_DEFINITION:
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
			case PepperInterfacePackage.PEPPER_IMPORTER__SUPPORTED_FORMATS:
				return supportedFormats != null && !supportedFormats.isEmpty();
			case PepperInterfacePackage.PEPPER_IMPORTER__CORPUS_DEFINITION:
				return corpusDefinition != null;
		}
		return super.eIsSet(featureID);
	}

} //PepperImporterImpl
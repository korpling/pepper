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
import java.io.IOException;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Exporter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl#getSupportedFormats <em>Supported Formats</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl#getCorpusDefinition <em>Corpus Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class PepperExporterImpl extends PepperModuleImpl implements PepperExporter {
	
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
	public static FormatDefinition addSupportedFormat(PepperModule pepperModule,
					String formatName, String formatVersion, URI formatReference) 
	{
		FormatDefinition retVal= null;
		if (	(pepperModule instanceof PepperImporter)||
				(pepperModule instanceof PepperExporter))
		{
			retVal= PepperModulesFactory.eINSTANCE.createFormatDefinition();
			retVal.setFormatName(formatName);
			retVal.setFormatVersion(formatVersion);
			retVal.setFormatReference(formatReference);
			if (pepperModule instanceof PepperImporter)
				((PepperImporter)pepperModule).getSupportedFormats().add(retVal);
			if (pepperModule instanceof PepperExporter)
				((PepperExporter)pepperModule).getSupportedFormats().add(retVal);
		}
		return(retVal);
	}
	
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
	protected PepperExporterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperModulesPackage.Literals.PEPPER_EXPORTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FormatDefinition> getSupportedFormats() {
		if (supportedFormats == null) {
			supportedFormats = new EObjectContainmentEList<FormatDefinition>(FormatDefinition.class, this, PepperModulesPackage.PEPPER_EXPORTER__SUPPORTED_FORMATS);
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_EXPORTER__CORPUS_DEFINITION, oldCorpusDefinition, newCorpusDefinition);
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
				msgs = ((InternalEObject)corpusDefinition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PepperModulesPackage.PEPPER_EXPORTER__CORPUS_DEFINITION, null, msgs);
			if (newCorpusDefinition != null)
				msgs = ((InternalEObject)newCorpusDefinition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PepperModulesPackage.PEPPER_EXPORTER__CORPUS_DEFINITION, null, msgs);
			msgs = basicSetCorpusDefinition(newCorpusDefinition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperModulesPackage.PEPPER_EXPORTER__CORPUS_DEFINITION, newCorpusDefinition, newCorpusDefinition));
	}

	/**
	 * Adds the call of method {@link #exportCorpusStructure(SCorpusGraph)}, than calls {@link PepperModule#start()}.
	 */
	@Override
	public void start() throws PepperModuleException
	{
		if (this.getSaltProject()!= null)
		{
			for (SCorpusGraph sCorpusGraph: this.getSaltProject().getSCorpusGraphs())
			{
				if (sCorpusGraph== null)
					throw new PepperFWException("An empty SDocumentGraph is in list of SaltProject. This might be a bug of pepper framework.");
				exportCorpusStructure(sCorpusGraph);
			}
		}
		super.start();
	}
	
	/**
	 * {@inheritDoc PepperExporter#exportCorpusStructure(SCorpusGraph)}
	 */
	public void exportCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException
	{
		
	}
	
	/**
	 * {@inheritDoc PepperExporter#createFolderStructure(SElementId)}
	 */
	public void createFolderStructure(SElementId sElementId)
	{
		if (sElementId== null)
			throw new PepperConvertException("Cannot export the given sElementID, because given SElementId-object is null.");
		if (sElementId.getSIdentifiableElement()== null)
			throw new PepperConvertException("Cannot export the given sElementID, because the SIdentifiableElement-object of given SElementId-object is null.");
		if (	(!(sElementId.getSIdentifiableElement() instanceof SDocument)) &&
				((!(sElementId.getSIdentifiableElement() instanceof SCorpus))))
			throw new PepperConvertException("Cannot export the given sElementID, because the element corresponding to it, isn�t of type SDocument or SCorpus.");
		
		try {
			File folder= new File(this.getCorpusDefinition().getCorpusPath().toFileString());
			File newFolder= new File(folder.getCanonicalPath() + "/"+ sElementId.getSElementPath().toString());
			newFolder.mkdirs();
		} catch (IOException e) {
			throw new PepperConvertException("Cannot create corpus path as folders.", e);
		}
	}

	/**
	 * Creates a new {@link FormatDefinitionImpl} objects, sets its values 
	 * {@link FormatDefinition#setFormatName(String)}, {@link FormatDefinition#setFormatVersion(String)} and
	 * {@link FormatDefinition#setFormatReference(URI)} to the given ones, adds the created object to
	 * list of supported formats {@link #getSupportedFormats()} and returns it.
	 * @param formatName name of the supported format to add
	 * @param formatVersion version of the supported format to add
	 * @param formatReference uri reference where the supported format to add is described or defined
	 */
	public FormatDefinition addSupportedFormat(String formatName, String formatVersion, URI formatReference) 
	{
		return(addSupportedFormat(this, formatName, formatVersion, formatReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperModulesPackage.PEPPER_EXPORTER__SUPPORTED_FORMATS:
				return ((InternalEList<?>)getSupportedFormats()).basicRemove(otherEnd, msgs);
			case PepperModulesPackage.PEPPER_EXPORTER__CORPUS_DEFINITION:
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
			case PepperModulesPackage.PEPPER_EXPORTER__SUPPORTED_FORMATS:
				return getSupportedFormats();
			case PepperModulesPackage.PEPPER_EXPORTER__CORPUS_DEFINITION:
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
			case PepperModulesPackage.PEPPER_EXPORTER__SUPPORTED_FORMATS:
				getSupportedFormats().clear();
				getSupportedFormats().addAll((Collection<? extends FormatDefinition>)newValue);
				return;
			case PepperModulesPackage.PEPPER_EXPORTER__CORPUS_DEFINITION:
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
			case PepperModulesPackage.PEPPER_EXPORTER__SUPPORTED_FORMATS:
				getSupportedFormats().clear();
				return;
			case PepperModulesPackage.PEPPER_EXPORTER__CORPUS_DEFINITION:
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
			case PepperModulesPackage.PEPPER_EXPORTER__SUPPORTED_FORMATS:
				return supportedFormats != null && !supportedFormats.isEmpty();
			case PepperModulesPackage.PEPPER_EXPORTER__CORPUS_DEFINITION:
				return corpusDefinition != null;
		}
		return super.eIsSet(featureID);
	}

} //PepperExporterImpl

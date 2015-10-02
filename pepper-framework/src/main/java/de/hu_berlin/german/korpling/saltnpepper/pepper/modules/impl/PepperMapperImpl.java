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

import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.xml.sax.ext.DefaultHandler2;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.MappingSubject;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapperController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.u.saltnpepper.salt.SaltFactory;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpus;
import de.hu_berlin.u.saltnpepper.salt.common.SDocument;

/**
 * An abstract implementation of {@link PepperMapper} to be used for further
 * derivations for specific mapping purposes.
 * 
 * @author Florian Zipser
 *
 */
public class PepperMapperImpl implements PepperMapper {

	public PepperMapperImpl() {
		this.initialize();
	}

	/**
	 * a list of all subjects ({@link SDocument} or {@link SCorpus}) to be
	 * merged
	 */
	private List<MappingSubject> mappingSubjects = null;

	/**
	 * Returns a list of all subjects ({@link SDocument} or {@link SCorpus}) to
	 * be merged
	 * 
	 * @return a list of {@link MappingSubject}
	 */
	public List<MappingSubject> getMappingSubjects() {
		if (mappingSubjects == null) {
			mappingSubjects = new Vector<MappingSubject>();
		}
		return mappingSubjects;
	}

	/**
	 * {@inheritDoc PepperMapper#getResourceURI()}
	 */
	public URI getResourceURI() {
		URI retVal = null;
		if (getMappingSubjects().size() > 0) {
			retVal = getMappingSubjects().get(0).getResourceURI();
		}
		return (retVal);
	}

	/**
	 * {@inheritDoc PepperMapper#setResourceURI(URI)}
	 */
	public void setResourceURI(URI resourceURI) {
		MappingSubject subj = null;
		if (getMappingSubjects().size() < 1) {
			subj = new MappingSubject();
			getMappingSubjects().add(subj);
		} else {
			subj = getMappingSubjects().get(0);
		}
		subj.setResourceURI(resourceURI);
	}

	/**
	 * {@inheritDoc PepperMapper#getDocument()}
	 */
	@Override
	public SDocument getDocument() {
		SDocument retVal = null;
		if (getMappingSubjects().size() > 0) {
			if ((getMappingSubjects().get(0).getIdentifier() != null) && (getMappingSubjects().get(0).getIdentifier().getIdentifiableElement() != null) && (getMappingSubjects().get(0).getIdentifier().getIdentifiableElement() instanceof SDocument)) {
				retVal = (SDocument) getMappingSubjects().get(0).getIdentifier().getIdentifiableElement();
			}
		}
		return (retVal);
	}

	/**
	 * {@inheritDoc PepperMapper#setDocument(SDocument)}
	 */
	@Override
	public void setDocument(SDocument sDocument) {

		MappingSubject subj = null;
		if (getMappingSubjects().size() < 1) {
			subj = new MappingSubject();
			getMappingSubjects().add(subj);
		} else {
			subj = getMappingSubjects().get(0);
		}
		if (sDocument.getIdentifier() == null) {
			throw new PepperFWException("This should not happen, please fix me. ");
			//sDocument.setIdentifier(SaltFactory.createIdentifier());
		}
		subj.setIdentifier(sDocument.getIdentifier());
	}

	/**
	 * {@inheritDoc PepperMapper#getCorpus()}
	 */
	@Override
	public SCorpus getCorpus() {
		SCorpus retVal = null;
		if (getMappingSubjects().size() > 0) {
			if ((getMappingSubjects().get(0).getIdentifier() != null) && (getMappingSubjects().get(0).getIdentifier().getIdentifiableElement() != null) && (getMappingSubjects().get(0).getIdentifier().getIdentifiableElement() instanceof SCorpus)) {
				retVal = (SCorpus) getMappingSubjects().get(0).getIdentifier().getIdentifiableElement();
			}
		}
		return (retVal);
	}

	/**
	 * {@inheritDoc PepperMapper#setCorpus(SCorpus)}
	 */
	public void setCorpus(SCorpus sCorpus) {
		MappingSubject subj = null;
		if (getMappingSubjects().size() < 1) {
			subj = new MappingSubject();
			getMappingSubjects().add(subj);
		} else {
			subj = getMappingSubjects().get(0);
		}
		if (sCorpus.getIdentifier() == null)
			throw new PepperModuleException(this, "Cannot set 'SCorpus'.");
		subj.setIdentifier(sCorpus.getIdentifier());
	}

	/**
	 * {@link PepperModuleProperties} object containing user customizations to
	 * be observed during the mapping.
	 */
	protected PepperModuleProperties props = null;

	/**
	 * {@inheritDoc PepperMapper#getProperties()}
	 */
	public PepperModuleProperties getProperties() {
		return props;
	}

	/**
	 * {@inheritDoc PepperMapper#setProperties(PepperModuleProperties)}
	 */
	public void setProperties(PepperModuleProperties props) {
		this.props = props;
	}

	/** {@inheritDoc PepperMapperConnector#setMappingResult(DOCUMENT_STATUS)} **/
	@Override
	public synchronized void setMappingResult(DOCUMENT_STATUS mappingResult) {
		MappingSubject subj = null;
		if (getMappingSubjects().size() < 1) {
			subj = new MappingSubject();
			getMappingSubjects().add(subj);
		} else {
			subj = getMappingSubjects().get(0);
		}
		subj.setMappingResult(mappingResult);
	}

	/** {@inheritDoc PepperMapperConnector#getMappingResult()} **/
	@Override
	public DOCUMENT_STATUS getMappingResult() {
		DOCUMENT_STATUS retVal = null;
		if (getMappingSubjects().size() > 0) {
			retVal = getMappingSubjects().get(0).getMappingResult();
		}
		return (retVal);
	}

	/**
	 * This method initializes this object and is called by the constructor.
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	protected void initialize() {

	}

	/**
	 * {@inheritDoc PepperMapper#setDocument(SDocument)}
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public DOCUMENT_STATUS mapSDocument() {
		throw new UnsupportedOperationException("OVERRIDE THE METHOD 'public DOCUMENT_STATUS mapSDocument()' IN '" + getClass().getName() + "' FOR CUSTOMIZED MAPPING.");
	}

	/**
	 * {@inheritDoc PepperMapper#setCorpus(SCorpus)}
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public DOCUMENT_STATUS mapSCorpus() {
		return (DOCUMENT_STATUS.COMPLETED);
	}

	/** Stores the current progress (between 0 and 1) **/
	protected volatile Double progress = 0d;

	/**
	 * {@inheritDoc PepperMapper#getProgress()}
	 */
	@Override
	public Double getProgress() {
		return (progress);
	}

	/**
	 * {@inheritDoc PepperMapper#addProgress(Double)}
	 */
	@Override
	public void addProgress(Double progress) {
		this.setProgress(getProgress() + progress);
	}

	/**
	 * {@inheritDoc PepperMapper#setProgress(Double)} OVERRIDE THIS METHOD FOR
	 * CUSTOMIZED MAPPING.
	 */
	@Override
	public void setProgress(Double progress) {
		this.progress = progress;
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

	protected PepperMapperController controller= null;
	/** {@inheritDoc} **/
	@Override
	public void setPepperMapperController(PepperMapperController controller) {
		this.controller= controller;
	}
	/** {@inheritDoc} **/
	@Override
	public PepperMapperController getPepperMapperController() {
		return controller;
	}
}

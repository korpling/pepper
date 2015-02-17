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
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.common.util.URI;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.MappingSubject;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleXMLResourceException;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * An abstract implementation of {@link PepperMapper} to be used for further derivations for specific mapping
 * purposes.
 * 
 * @author Florian Zipser
 *
 */
public class PepperMapperImpl implements PepperMapper {
	
	public PepperMapperImpl()
	{
		this.initialize();
	}
	/** a list of all subjects ({@link SDocument} or {@link SCorpus}) to be merged*/
	private List<MappingSubject> mappingSubjects= null;
	/**
	 * Returns a list of all subjects ({@link SDocument} or {@link SCorpus}) to be merged
	 * @return a list of {@link MappingSubject}
	 */
	public List<MappingSubject> getMappingSubjects() {
		if (mappingSubjects== null){
			mappingSubjects= new Vector<MappingSubject>();
		}
		return mappingSubjects;
	}
	/**
	 * {@inheritDoc PepperMapper#getResourceURI()}
	 */
	public URI getResourceURI() {
		URI retVal= null;
		if (getMappingSubjects().size()> 0){
			retVal= getMappingSubjects().get(0).getResourceURI();
		}
		return(retVal);
	}
	/**
	 * {@inheritDoc PepperMapper#setResourceURI(URI)}
	 */
	public void setResourceURI(URI resourceURI) {
		MappingSubject subj= null;
		if (getMappingSubjects().size()< 1){
			subj= new MappingSubject();
			getMappingSubjects().add(subj);
		}else{
			subj= getMappingSubjects().get(0);
		}
		subj.setResourceURI(resourceURI);
	}
	
	/**
	 * {@inheritDoc PepperMapper#getSDocument()}
	 */
	@Override
	public SDocument getSDocument() {
		SDocument retVal= null;
		if (getMappingSubjects().size()> 0){
			if (	(getMappingSubjects().get(0).getSElementId()!= null)&&
					(getMappingSubjects().get(0).getSElementId().getSIdentifiableElement()!= null)&&
					(getMappingSubjects().get(0).getSElementId().getSIdentifiableElement() instanceof SDocument)){
				retVal= (SDocument)getMappingSubjects().get(0).getSElementId().getSIdentifiableElement();
			}
		}
		return(retVal);
	}
	/**
	 * {@inheritDoc PepperMapper#setSDocument(SDocument)}
	 */
	@Override
	public void setSDocument(SDocument sDocument) {
		
		MappingSubject subj= null;
		if (getMappingSubjects().size()< 1){
			subj= new MappingSubject();
			getMappingSubjects().add(subj);
		}else{
			subj= getMappingSubjects().get(0);
		}
		if (sDocument.getSElementId()== null){
			sDocument.setSElementId(SaltFactory.eINSTANCE.createSElementId());
		}
		subj.setSElementId(sDocument.getSElementId());
	}
	/**
	 * {@inheritDoc PepperMapper#getSCorpus()}
	 */
	@Override
	public SCorpus getSCorpus() {
		SCorpus retVal= null;
		if (getMappingSubjects().size()> 0){
			if (	(getMappingSubjects().get(0).getSElementId()!= null)&&
					(getMappingSubjects().get(0).getSElementId().getSIdentifiableElement()!= null)&&
					(getMappingSubjects().get(0).getSElementId().getSIdentifiableElement() instanceof SCorpus)){
				retVal= (SCorpus)getMappingSubjects().get(0).getSElementId().getSIdentifiableElement();
			}
		}
		return(retVal);
	}

	/**
	 * {@inheritDoc PepperMapper#setSCorpus(SCorpus)} 
	 */
	public void setSCorpus(SCorpus sCorpus) {
		MappingSubject subj= null;
		if (getMappingSubjects().size()< 1){
			subj= new MappingSubject();
			getMappingSubjects().add(subj);
		}else{
			subj= getMappingSubjects().get(0);
		}
		if (sCorpus.getSElementId()== null)
			throw new PepperModuleException(this, "Cannot set 'SCorpus'.");
		subj.setSElementId(sCorpus.getSElementId());
	}
	/**
	 * {@link PepperModuleProperties} object containing user customizations to be observed during the mapping.
	 */
	protected PepperModuleProperties props= null;
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
		MappingSubject subj= null;
		if (getMappingSubjects().size()< 1){
			subj= new MappingSubject();
			getMappingSubjects().add(subj);
		}else{
			subj= getMappingSubjects().get(0);
		}
		subj.setMappingResult(mappingResult);
	}
	/** {@inheritDoc PepperMapperConnector#getMappingResult()} **/
	@Override
	public DOCUMENT_STATUS getMappingResult() {
		DOCUMENT_STATUS retVal= null;
		if (getMappingSubjects().size()> 0){
			retVal= getMappingSubjects().get(0).getMappingResult();
		}
		return(retVal);
	}
	
	/**
	 * This method initializes this object and is called by the constructor.
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	protected void initialize()
	{
		
	}
	
	/**
	 * {@inheritDoc PepperMapper#setSDocument(SDocument)}
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public DOCUMENT_STATUS mapSDocument() {
		throw new UnsupportedOperationException("OVERRIDE THE METHOD 'public DOCUMENT_STATUS mapSDocument()' IN '"+getClass().getName()+"' FOR CUSTOMIZED MAPPING.");
	}
	/**
	 * {@inheritDoc PepperMapper#setSCorpus(SCorpus)}
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public DOCUMENT_STATUS mapSCorpus() {
		return(DOCUMENT_STATUS.COMPLETED);
	}
	/** Stores the current progress (between 0 and 1)**/
	protected volatile Double progress= 0d;
	/**
	 * {@inheritDoc PepperMapper#getProgress()}
	 */
	@Override
	public Double getProgress() 
	{
		return(progress);
	}
	/**
	 * {@inheritDoc PepperMapper#addProgress(Double)}
	 */
	@Override
	public void addProgress(Double progress)
	{
		this.setProgress(getProgress()+ progress);
	}
	
	/**
	 * {@inheritDoc PepperMapper#setProgress(Double)}
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public void setProgress(Double progress) 
	{
		this.progress= progress;
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
		
		File resourceFile= new File(documentLocation.toFileString());
		if (!resourceFile.exists()) 
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the file does not exist: " + resourceFile);
		
		if (!resourceFile.canRead())
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the file can not be read: " + resourceFile);
		
		SAXParser parser;
        XMLReader xmlReader;
        
        SAXParserFactory factory= SAXParserFactory.newInstance();
        
        try{
			parser= factory.newSAXParser();
	        xmlReader= parser.getXMLReader();
	        xmlReader.setContentHandler(contentHandler);
        } catch (ParserConfigurationException e) {
        	throw new PepperModuleXMLResourceException("Cannot load a xml-resource '"+resourceFile.getAbsolutePath()+"'.", e);
        }catch (Exception e) {
	    	throw new PepperModuleXMLResourceException("Cannot load a xml-resource '"+resourceFile.getAbsolutePath()+"'.", e);
		}
        try {
	        InputStream inputStream= new FileInputStream(resourceFile);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			xmlReader.parse(is);
        } catch (SAXException e) 
        {
            try{
				parser= factory.newSAXParser();
		        xmlReader= parser.getXMLReader();
		        xmlReader.setContentHandler(contentHandler);
				xmlReader.parse(resourceFile.getAbsolutePath());
            }catch (Exception e1) {
            	throw new PepperModuleXMLResourceException("Cannot load a xml-resource '"+resourceFile.getAbsolutePath()+"'.", e1);
			}
		}
        catch (Exception e) {
			if (e instanceof PepperModuleException)
				throw (PepperModuleException)e;
			else throw new PepperModuleXMLResourceException("Cannot read xml-file'"+documentLocation+"', because of a nested exception. ",e);
		}
	}
}

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.log.LogService;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleXMLResourceException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.MAPPING_RESULT;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperties;
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
	
	/**
	 * OSGi logger for this mapper. To be removed, when abstract logging via slf4j is used.
	 * @deprecated
	 */
	private LogService logService;

	public void setLogService(LogService logService) 
	{
		this.logService = logService;
	}
	
	public LogService getLogService() 
	{
		return(this.logService);
	}

	/**
	 * {@link URI} of resource. The URI could refer a directory or a file, which can be a corpus or a document.
	 */
	protected URI resourceURI= null;
	/**
	 * {@inheritDoc PepperMapper#getResourceURI()}
	 */
	public URI getResourceURI() {
		return(resourceURI);
	}
	/**
	 * {@inheritDoc PepperMapper#setResourceURI(URI)}
	 */
	public void setResourceURI(URI resourceURI) {
		this.resourceURI= resourceURI;
	}
	/**
	 * {@link SDocument} object to be created/ fullfilled during the mapping.
	 */
	protected SDocument sDocument= null;
	/**
	 * {@inheritDoc PepperMapper#getSDocument()}
	 */
	@Override
	public SDocument getSDocument() {
		return(sDocument);
	}
	/**
	 * {@inheritDoc PepperMapper#setSDocument(SDocument)}
	 */
	@Override
	public void setSDocument(SDocument sDocument) {
		this.sDocument= sDocument;
	}
	/**
	 * {@link SCorpus} object to be created/ fullfilled during the mapping.
	 */
	protected SCorpus sCorpus= null;
	
	public SCorpus getSCorpus() {
		return sCorpus;
	}

	/**
	 * {@inheritDoc PepperMapper#setSCorpus(SCorpus)} 
	 */
	public void setSCorpus(SCorpus sCorpus) {
		this.sCorpus = sCorpus;
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

	protected volatile MAPPING_RESULT mappingResult= null;
	/** {@inheritDoc PepperMapperConnector#setMappingResult(MAPPING_RESULT)} **/
	@Override
	public synchronized void setMappingResult(MAPPING_RESULT mappingResult) {
		this.mappingResult= mappingResult;
		
	}
	/** {@inheritDoc PepperMapperConnector#getMappingResult()} **/
	@Override
	public MAPPING_RESULT getMappingResult() {
		return(this.mappingResult);
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
	public MAPPING_RESULT mapSDocument() {
		throw new UnsupportedOperationException("OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.");
	}
	/**
	 * {@inheritDoc PepperMapper#setSCorpus(SCorpus)}
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public MAPPING_RESULT mapSCorpus() {
		return(MAPPING_RESULT.FINISHED);
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
        
        try
        {
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
        	
            try
            {
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

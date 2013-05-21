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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * This interface can be used in a threaded environment, where the {@link PepperModule} is used for delegating thread jobs,
 * and a set of PepperMappers are mapping one {@link SDocument} or {@link SCorpus} object.
 * 
 * @author Florian Zipser
 *
 */
public interface PepperMapper {
	/**
	 * Sets the OSGi logger for this mapper. To be removed, when abstract logging via slf4j is used.
	 * @deprecated
	 * @param logService
	 */
	public void setLogService(LogService logService);
	/**
	 * Returns the OSGi logger for this mapper. To be removed, when abstract logging via slf4j is used.
	 * @deprecated
	 * @return
	 */
	public LogService getLogService();
	/**
	 * Sets {@link URI} of resource. The URI could refer a directory or a file, which can be a corpus or a document.
	 * @param resourceURI uri of resource
	 */
	public void setResourceURI(URI resourceURI);
	/**
	 * Returns {@link URI} of resource. The URI could refer a directory or a file, which can be a corpus or a document.
	 * @return uri of resource
	 */
	public URI getResourceURI();
	/**
	 * Returns the {@link SDocument} object to be mapped by this mapper. 
	 * @return {@link SDocument} object to be mapped
	 */
	public SDocument getSDocument();
	
	/**
	 * Sets the {@link SDocument} object to be mapped by this mapper. 
	 * @param sDocument {@link SDocument} object to be mapped
	 */
	public void setSDocument(SDocument sDocument);	
	/**
	 * Returns the {@link SCorpus} object to be mapped by this mapper.
	 * @return {@link SCorpus} object to be mapped
	 */
	public SCorpus getSCorpus();
	/**
	 * Sets the {@link SCorpus} object to be mapped by this mapper.
	 * @param sCorpus {@link SCorpus} object to be mapped
	 */
	public void setSCorpus(SCorpus sCorpus);
	/**
	 * Returns the {@link PepperModuleProperties} object containing user customizations to be observed during the mapping.
	 * @return {@link PepperModuleProperties} object to be used
	 */
	public PepperModuleProperties getProperties();
	/**
	 * Sets the {@link PepperModuleProperties} object containing user customizations to be observed during the mapping.
	 * @param props {@link PepperModuleProperties} object to be used
	 */
	public void setProperties(PepperModuleProperties props);
	/**
	 * Sets the result of the current mapping, when it is finished. 
	 * @param status of mapping
	 */
	public void setMappingResult(MAPPING_RESULT mappingResult);
	/**
	 * Returns the result of the mapping, when finished.
	 * @return mapping result
	 */
	public MAPPING_RESULT getMappingResult();
	/**
	 * Starts to map a given {@link SDocument} object, if one is given.
	 */
	public MAPPING_RESULT mapSDocument();
	/**
	 * Starts to map a given {@link SCorpus} object, if one is given.
	 */
	public MAPPING_RESULT mapSCorpus();
	
	/**
	 * This method is invoked by the containing {@link PepperModule} object, to get the current progress concerning the {@link SDocument} or 
	 * {@link SCorpus} object handled by this object. A valid value return must be between 0 and 1 or null if method the {@link PepperModule} does
	 * not call the method {@link #setProgress(Double)}.   
	 * @param sDocumentId identifier of the requested {@link SDocument} object.
	 */
	public Double getProgress();
	
	/**
	 * Adds the given progress to the already existing progress of the current handled {@link SDocument} or {@link SCorpus} object.
	 * This method should be called by the {@link PepperMapper}
	 * containing this connector object.
	 * @param progress the progress must be a value between 0 for 0% and 1 for 100% 
	 */
	public void addProgress(Double progress);
	
	/**
	 * Sets the progress of the current handled {@link SDocument} or {@link SCorpus} object. This method should be called by the {@link PepperMapper}
	 * containing this connector object.
	 * @param progress the progress must be a value between 0 for 0% and 1 for 100%
	 */
	public void setProgress(Double progress);
}

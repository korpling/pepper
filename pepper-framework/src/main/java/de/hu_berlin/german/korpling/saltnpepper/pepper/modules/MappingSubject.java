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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * A subject is a container for {@link SDocument} or {@link SCorpus} object to be merged. Further this 
 * container contains the {@link DOCUMENT_STATUS} and the {@link URI} where the resource is located   
 * @author Florian Zipser
 *
 */
public class MappingSubject{
	/** {@link URI} of resource. The URI could refer a directory or a file, which can be a corpus or a document.*/
	protected URI resourceURI= null;
	/**{@inheritDoc PepperMapper#getResourceURI()}*/
	public URI getResourceURI() {
		return(resourceURI);
	}
	/** {@inheritDoc PepperMapper#setResourceURI(URI)} */
	public void setResourceURI(URI resourceURI) {
		this.resourceURI= resourceURI;
	}
	/** {@link SElementId} object corresponding to either {@link SDocument} or {@link SCorpus} which should be mapped**/
	protected SElementId sElementId= null;
	/**
	 * Returns {@link SElementId} object corresponding to either 
	 * {@link SDocument} or {@link SCorpus} which should be mapped
	 * @return
	 */
	public SElementId getSElementId() {
		return sElementId;
	}
	/**
	 * Sets {@link SElementId} object corresponding to either {@link SDocument} or {@link SCorpus} which should be mapped
	 * @param sElementId
	 */
	public void setSElementId(SElementId sElementId) {
		this.sElementId = sElementId;
	}
	protected volatile DOCUMENT_STATUS mappingResult= null;
	/** {@inheritDoc PepperMapperConnector#setMappingResult(DOCUMENT_STATUS)} **/
	public synchronized void setMappingResult(DOCUMENT_STATUS mappingResult) {
		this.mappingResult= mappingResult;
		
	}
	/** {@inheritDoc PepperMapperConnector#getMappingResult()} **/
	public DOCUMENT_STATUS getMappingResult() {
		return(this.mappingResult);
	}
	/**
	 * Returns a String representation of this object. This representation should not be used for
	 * serialization/deserialization purposes.
	 */
	public String toString(){
		StringBuilder builder= new StringBuilder();
		String globalId= SaltFactory.eINSTANCE.getGlobalId(getSElementId());
		builder.append("(");
		if (globalId!= null){
			builder.append(globalId);	
		}else builder.append(getSElementId().toString());
		builder.append(", ");
		builder.append(getResourceURI());
		builder.append(", ");
		builder.append(getMappingResult());
		builder.append(")");
		return(builder.toString());
	}
}

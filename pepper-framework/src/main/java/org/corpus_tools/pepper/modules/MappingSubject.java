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
package org.corpus_tools.pepper.modules;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.graph.Identifier;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;

/**
 * A subject is a container for {@link SDocument} or {@link SCorpus} object to
 * be merged. Further this container contains the {@link DOCUMENT_STATUS} and
 * the {@link URI} where the resource is located
 */
public class MappingSubject {
	/**
	 * {@link URI} of resource. The URI could refer a directory or a file, which
	 * can be a corpus or a document.
	 */
	protected URI resourceURI = null;

	/** {@inheritDoc PepperMapper#getResourceURI()} */
	public URI getResourceURI() {
		return (resourceURI);
	}

	/** {@inheritDoc PepperMapper#setResourceURI(URI)} */
	public void setResourceURI(URI resourceURI) {
		this.resourceURI = resourceURI;
	}

	/**
	 * {@link Identifier} object corresponding to either {@link SDocument} or
	 * {@link SCorpus} which should be mapped
	 **/
	protected Identifier sElementId = null;

	/**
	 * Returns {@link Identifier} object corresponding to either
	 * {@link SDocument} or {@link SCorpus} which should be mapped
	 * 
	 * @return
	 */
	public Identifier getIdentifier() {
		return sElementId;
	}

	/**
	 * Sets {@link Identifier} object corresponding to either {@link SDocument}
	 * or {@link SCorpus} which should be mapped
	 * 
	 * @param sElementId
	 */
	public void setIdentifier(Identifier sElementId) {
		this.sElementId = sElementId;
	}

	protected volatile DOCUMENT_STATUS mappingResult = null;

	/** {@inheritDoc PepperMapperConnector#setMappingResult(DOCUMENT_STATUS)} **/
	public synchronized void setMappingResult(DOCUMENT_STATUS mappingResult) {
		this.mappingResult = mappingResult;

	}

	/** {@inheritDoc PepperMapperConnector#getMappingResult()} **/
	public DOCUMENT_STATUS getMappingResult() {
		return (this.mappingResult);
	}

	/**
	 * If the set {@link Identifier} belongs to a {@link SDocument}, this is
	 * it's controller.
	 **/
	private DocumentController documentController = null;

	/**
	 * Returns the document controller, if one was set. This is useful in case
	 * the set {@link Identifier} belongs to a {@link SDocument}.
	 * 
	 * @return the controller for the {@link SDocument}
	 */
	public DocumentController getDocumentController() {
		return documentController;
	}

	/**
	 * If the set {@link Identifier} belongs to a {@link SDocument}, it's
	 * controller can be passed with this method.
	 * 
	 * @param documentController
	 *            the controller for the {@link SDocument}
	 */
	public void setDocumentController(DocumentController documentController) {
		this.documentController = documentController;
	}

	/**
	 * Returns a String representation of this object. This representation
	 * should not be used for serialization/deserialization purposes.
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String globalId = SaltUtil.getGlobalId(getIdentifier());
		builder.append("(");
		if (globalId != null) {
			builder.append(globalId);
		} else
			builder.append(getIdentifier().toString());
		builder.append(", ");
		builder.append(getResourceURI());
		builder.append(", ");
		builder.append(getMappingResult());
		builder.append(")");
		return (builder.toString());
	}
}

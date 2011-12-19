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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PersistenceConnector;

import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>XSLT Transformer</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesPackage#getXSLTTransformer()
 * @model
 * @generated
 */
public interface XSLTTransformer extends PersistenceConnector {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model sourceURIDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.URI" targetURIDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.URI" xsltURIDataType="de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.URI"
	 * @generated
	 */
	void transform(URI sourceURI, URI targetURI, URI xsltURI);

} // XSLTTransformer

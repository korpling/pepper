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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pepper XSLT Exporter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.PepperXSLTExporter#getXsltTransformer <em>Xslt Transformer</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesPackage#getPepperXSLTExporter()
 * @model
 * @generated
 */
public interface PepperXSLTExporter extends PepperExporter {

	/**
	 * Returns the value of the '<em><b>Xslt Transformer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xslt Transformer</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xslt Transformer</em>' containment reference.
	 * @see #setXsltTransformer(XSLTTransformer)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesPackage#getPepperXSLTExporter_XsltTransformer()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	XSLTTransformer getXsltTransformer();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.PepperXSLTExporter#getXsltTransformer <em>Xslt Transformer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xslt Transformer</em>' containment reference.
	 * @see #getXsltTransformer()
	 * @generated
	 */
	void setXsltTransformer(XSLTTransformer value);
} // PepperXSLTExporter

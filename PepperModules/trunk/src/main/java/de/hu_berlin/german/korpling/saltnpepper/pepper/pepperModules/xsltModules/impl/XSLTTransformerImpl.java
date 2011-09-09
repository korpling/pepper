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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PersistenceConnectorImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XSLTTransformer;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>XSLT Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class XSLTTransformerImpl extends PersistenceConnectorImpl implements XSLTTransformer {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XSLTTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return XsltModulesPackage.Literals.XSLT_TRANSFORMER;
	}

	/**
	 * Transforms a source file into a target file using an xslt-script. Both, target
	 * and source file has to encoded in UTF-8. 
	 * @param sourceURI source of transformation
	 * @param targetURI target of transformation
	 * @param xsltURI the used xslt script
	 */
	public void transform(URI sourceURI, URI targetURI, URI xsltURI)
	{
		if (sourceURI== null)
			throw new PepperModuleException("Cannot transform, because the source uri is empty.");
		if (targetURI== null)
			throw new PepperModuleException("Cannot transform, because the target uri is empty.");
		if (xsltURI== null)
			throw new PepperModuleException("Cannot transform, because the uri of transformation file is empty.");
		File sourceFile = new File(sourceURI.toFileString());
		if (!sourceFile.exists())
			throw new PepperModuleException("Cannot transform, because the source uri does not exists: "+ sourceURI);
        File xsltFile = new File(xsltURI.toFileString());
        if (!xsltFile.exists())
			throw new PepperModuleException("Cannot transform, because the uri of transformation file does not exists: "+ xsltURI);
        File targetFile= new File(targetURI.toFileString());
        targetFile.getParentFile().mkdirs();
        
        Source xmlSource= null;
        Source xsltSource = null;
        Result result = null;
        
        InputStreamReader sourceStream= null;
        InputStreamReader xmlSourceStream= null;
        OutputStreamWriter resultStream= null;
        
        
        try {
        	sourceStream= new InputStreamReader(new FileInputStream(sourceFile), "UTF8");
        	xmlSource = new StreamSource(sourceStream);
        	xmlSourceStream= new InputStreamReader(new FileInputStream(xsltFile), "UTF8");
        	xsltSource = new StreamSource(xmlSourceStream);
        	resultStream= new OutputStreamWriter(new FileOutputStream(targetFile), "UTF8");
			result = new StreamResult(resultStream);
	
			// create an instance of TransformerFactory
			TransformerFactory transFact = javax.xml.transform.TransformerFactory
					.newInstance();
	
			Transformer trans = transFact.newTransformer(xsltSource);
	
			trans.transform(xmlSource, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				sourceStream.close();
			} catch (IOException e) {
				throw new PepperModuleException("Cannot close streams for writing or reading file. ", e);
			}
			try {
				xmlSourceStream.close();
			} catch (IOException e) {
				throw new PepperModuleException("Cannot close streams for writing or reading file. ", e);
			}
			try {
				resultStream.close();
			} catch (IOException e) {
				throw new PepperModuleException("Cannot close streams for writing or reading file. ", e);
			}
		}
	}

} //XSLTTransformerImpl

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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.ParamTests;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Pepper Converter</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SetParameterByFileTest extends TestCase {

	/**
	 * The fixture for this Pepper Converter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperConverter fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SetParameterByFileTest.class);
	}

	/**
	 * Constructs a new Pepper Converter test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SetParameterByFileTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Pepper Converter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PepperConverter fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Pepper Converter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperConverter getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(PepperFWFactory.eINSTANCE.createPepperConverter());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}
	
	public void testParams0() throws Exception
	{
		URI uri= null;
		boolean isCorrect= false;
		String description= "no file given";
		
		if (isCorrect)
			this.getFixture().setPepperParams(uri);
		else
		{
			try {
				this.getFixture().setPepperParams(uri);
				fail(description);
			} catch (Exception e) {
			}
		}	
	}
	
	private String resourcesFolderName= "./src/test/resources/pepperParams/";
	
	public void testParams1() throws Exception
	{
//		URI uri= new URI("./data/pepperParams/testParam/PepperParams1.pepperparams");
		URI uri= URI.createFileURI(resourcesFolderName+ "testParam/PepperParams1.pepperparams");
		boolean isCorrect= true;
		String description= "everything shall be ok";
		
		if (isCorrect)
			this.getFixture().setPepperParams(uri);
		else
		{
			try {
				this.getFixture().setPepperParams(uri);
				fail(description);
			} catch (Exception e) {
			}
		}	
	}
	public void testParams2() throws Exception
	{
//		URI uri= new URI("./data/pepperParams/testParam/NoExporter.pepperparams");
		URI uri= URI.createFileURI(resourcesFolderName+ "testParam/NoExporter.pepperparams");
		boolean isCorrect= false;
		String description= "no exporter is given";
		
		if (isCorrect)
			this.getFixture().setPepperParams(uri);
		else
		{
			try {
				this.getFixture().setPepperParams(uri);
				fail(description);
			} catch (Exception e) {
			}
		}	
	}
	public void testParams3() throws Exception
	{
//		URI uri= new URI("./data/pepperParams/testParam/NoImporter.pepperparams");
		URI uri= URI.createFileURI(resourcesFolderName+ "testParam/NoImporter.pepperparams");
		boolean isCorrect= false;
		String description= "no importer is given";
		
		if (isCorrect)
			this.getFixture().setPepperParams(uri);
		else
		{
			try {
				this.getFixture().setPepperParams(uri);
				fail(description);
			} catch (Exception e) {
			}
		}	
	}
	public void testParams4() throws Exception
	{
//		URI uri= new URI("./data/pepperParams/testParam/NoModuleName.pepperparams");
		URI uri= URI.createFileURI(resourcesFolderName+ "testParam/NoModuleName.pepperparams");
		boolean isCorrect= false;
		String description= "a given module has no name";
		
		if (isCorrect)
			this.getFixture().setPepperParams(uri);
		else
		{
			try {
				this.getFixture().setPepperParams(uri);
				fail(description);
			} catch (Exception e) {
			}
		}	
	}
	public void testParams5() throws Exception
	{
//		URI uri= new URI("./data/pepperParams/testParam/NoNameNoDefinitionExporter.pepperparams");
		URI uri= URI.createFileURI(resourcesFolderName+ "testParam/NoNameNoDefinitionExporter.pepperparams");
		boolean isCorrect= false;
		String description= "no name given in exporter";
		
		if (isCorrect)
			this.getFixture().setPepperParams(uri);
		else
		{
			try {
				this.getFixture().setPepperParams(uri);
				fail(description);
			} catch (Exception e) {
			}
		}	
	}
	public void testParams6() throws Exception
	{
//		URI uri= new URI("./data/pepperParams/testParam/NoNameNoDefinitionImporter.pepperparams");
		URI uri= URI.createFileURI(resourcesFolderName+ "testParam/NoNameNoDefinitionImporter.pepperparams");
		boolean isCorrect= false;
		String description= "no name given in importer";
		
		if (isCorrect)
			this.getFixture().setPepperParams(uri);
		else
		{
			try {
				this.getFixture().setPepperParams(uri);
				fail(description);
			} catch (Exception e) {
			}
		}	
	}
	
	public void testParams7() throws Exception
	{
//		URI uri= new URI("./data/pepperParams/testParam/NoPepperJob.pepperparams");
		URI uri= URI.createFileURI(resourcesFolderName+ "testParam/NoPepperJob.pepperparams");
		boolean isCorrect= false;
		String description= "no PepperJob-object";
		
		if (isCorrect)
			this.getFixture().setPepperParams(uri);
		else
		{
			try {
				this.getFixture().setPepperParams(uri);
				fail(description);
			} catch (Exception e) {
			}
		}	
	}
	
	public void testParams8() throws Exception
	{
//		URI uri= new URI("./data/pepperParams/testParam/NoJobId.pepperparams");
		URI uri= URI.createFileURI(resourcesFolderName+ "testParam/NoJobId.pepperparams");
		boolean isCorrect= false;
		String description= "the job has no id";
		
		if (isCorrect)
			this.getFixture().setPepperParams(uri);
		else
		{
			try {
				this.getFixture().setPepperParams(uri);
				fail(description);
			} catch (Exception e) {
			}
		}	
	}
	
	
} //PepperConverterTest

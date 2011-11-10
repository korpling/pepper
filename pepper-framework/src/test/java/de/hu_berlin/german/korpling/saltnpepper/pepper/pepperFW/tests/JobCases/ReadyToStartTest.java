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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.JobCases;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.TestModules.TestExporter1;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.TestModules.TestImporter1;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Pepper Convert Job</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start() <em>Start</em>}</li>
 * </ul>
 * </p>
 */
public class ReadyToStartTest extends TestCase 
{
	/**
	 * The fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	protected PepperJobDelegatorTest fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	public static void main(String[] args) {
		TestRunner.run(ReadyToStartTest.class);
	}

	/**
	 * Constructs a new Pepper Job test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	public ReadyToStartTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	protected void setFixture(PepperJobDelegatorTest fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	protected PepperJobDelegatorTest getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(new PepperJobDelegatorTest());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 *
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start() <em>Start</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	public void testStartNoId() throws Exception
	{
		TestImporter1 importer1= new TestImporter1();
		CorpusDefinition corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//		corpDef.setCorpusPath(new URI("./data/pepperJob/testCorpus1"));
		corpDef.setCorpusPath(URI.createFileURI("./data/pepperJob/testCorpus1"));
		importer1.setCorpusDefinition(corpDef);
		
		TestExporter1 exporter1= new TestExporter1();
		exporter1.setCorpusDefinition(corpDef);
		
		
		this.getFixture().getPepperExporters().add(exporter1);
		this.getFixture().getPepperImporters().add(importer1);
		try {
			this.getFixture().validateBeforeStart();
			fail();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start() <em>Start</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	public void testStartWithoutSaltProject() throws Exception
	{
		CorpusDefinition corpDef= null;
		corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//		corpDef.setCorpusPath(new URI("./data/pepperJob/testCorpus1"));
		corpDef.setCorpusPath(URI.createFileURI("./data/pepperJob/testCorpus1"));
		TestImporter1 importer1= new TestImporter1();
		importer1.setCorpusDefinition(corpDef);
		
		
		TestExporter1 exporter1= new TestExporter1();
		corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//		corpDef.setCorpusPath(new URI("./data/pepperJob/testCorpus1"));
		corpDef.setCorpusPath(URI.createFileURI("./data/pepperJob/testCorpus1"));
		exporter1.setCorpusDefinition(corpDef);
		
		
		this.getFixture().getPepperExporters().add(exporter1);
		this.getFixture().getPepperImporters().add(importer1);
		this.getFixture().setId(0);
		
		
		try {
			this.getFixture().validateBeforeStart();
			fail();
		} catch (PepperConvertException e) {
		}
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start() <em>Start</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	public void testStartWithoutImporters() 
	{
		this.getFixture().getPepperExporters().add(new TestExporter1());
		this.getFixture().setId(0);
		try {
			this.getFixture().validateBeforeStart();
			fail();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start() <em>Start</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	public void testStartNoURIForImporter() throws Exception
	{
		
		CorpusDefinition corpDef= null;
		corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
		TestImporter1 importer1= new TestImporter1();
		importer1.setCorpusDefinition(corpDef);
		
		
		TestExporter1 exporter1= new TestExporter1();
		corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//		corpDef.setCorpusPath(new URI("./data/pepperJob/testCorpus1"));
		corpDef.setCorpusPath(URI.createFileURI("./data/pepperJob/testCorpus1"));
		exporter1.setCorpusDefinition(corpDef);
		
		
		this.getFixture().getPepperExporters().add(exporter1);
		this.getFixture().getPepperImporters().add(importer1);
		this.getFixture().setId(0);
		try {
			this.getFixture().validateBeforeStart();
			fail();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start() <em>Start</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	public void testStartWithoutExporters() 
	{
		this.getFixture().getPepperImporters().add(new TestImporter1());
		this.getFixture().setId(0);
		try {
			this.getFixture().validateBeforeStart();
			fail();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start() <em>Start</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob#start()
	 */
	public void testStartNoURIForExporter() throws Exception
	{
		CorpusDefinition corpDef= null;
		corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
		TestExporter1 exporter1= new TestExporter1();
		exporter1.setCorpusDefinition(corpDef);
		
		TestImporter1 importer1= new TestImporter1();
		corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
//		corpDef.setCorpusPath(new URI("./data/pepperJob/testCorpus1"));
		corpDef.setCorpusPath(URI.createFileURI("./data/pepperJob/testCorpus1"));
		importer1.setCorpusDefinition(corpDef);
		
		
		this.getFixture().getPepperExporters().add(exporter1);
		this.getFixture().getPepperImporters().add(importer1);
		this.getFixture().setId(0);
		try {
			this.getFixture().validateBeforeStart();
			fail();
		} catch (Exception e) {
		}
	}
	
} //PepperConvertJobTest

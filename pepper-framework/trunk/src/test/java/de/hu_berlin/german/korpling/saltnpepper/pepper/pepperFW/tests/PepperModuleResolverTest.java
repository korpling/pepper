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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests;


import java.io.File;

import junit.framework.TestCase;
import junit.textui.TestRunner;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.Resolver.TestComponentFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.Resolver.TestExporter1;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.Resolver.TestImporter1;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.Resolver.TestManipulator1;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Pepper Module Resolver</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperImporter(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams) <em>Get Pepper Importer</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperManipulator(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams) <em>Get Pepper Manipulator</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperExporter(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams) <em>Get Pepper Exporter</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#addPepperImporterComponentFactory(org.osgi.service.component.ComponentFactory) <em>Add Pepper Importer Component Factory</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#removePepperImporterComponentFactory(org.osgi.service.component.ComponentFactory) <em>Remove Pepper Importer Component Factory</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#addPepperManipulatorComponentFactory(org.osgi.service.component.ComponentFactory) <em>Add Pepper Manipulator Component Factory</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#removePepperManipulatorComponentFactory(org.osgi.service.component.ComponentFactory) <em>Remove Pepper Manipulator Component Factory</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#addPepperExporterComponentFactory(org.osgi.service.component.ComponentFactory) <em>Add Pepper Exporter Component Factory</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#removePepperExporterComponentFactory(org.osgi.service.component.ComponentFactory) <em>Remove Pepper Exporter Component Factory</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperImporters() <em>Get Pepper Importers</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperManipulators() <em>Get Pepper Manipulators</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperExporters() <em>Get Pepper Exporters</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getStatus() <em>Get Status</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class PepperModuleResolverTest extends TestCase {

	/**
	 * The fixture for this Pepper Module Resolver test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperModuleResolver fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PepperModuleResolverTest.class);
	}

	/**
	 * Constructs a new Pepper Module Resolver test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModuleResolverTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Pepper Module Resolver test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PepperModuleResolver fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Pepper Module Resolver test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperModuleResolver getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(PepperFWFactory.eINSTANCE.createPepperModuleResolver());
		System.setProperty(this.getFixture().getTemprorariesPropertyName(), "./data/TMP");
		System.setProperty(this.getFixture().getResourcesPropertyName(), "./data/moduleResources");
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

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperImporter(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams) <em>Get Pepper Importer</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperImporter(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams)
	 */
	public void testGetPepperImporter__ImporterParams() 
	{
		//see other testImporter-methods
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#addPepperImporterComponentFactory(org.osgi.service.component.ComponentFactory) <em>Add Pepper Importer Component Factory</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#addPepperImporterComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	public void testAddPepperImporterComponentFactory__ComponentFactory() 
	{
		//see other testImporter-methods
	}

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#removePepperImporterComponentFactory(org.osgi.service.component.ComponentFactory) <em>Remove Pepper Importer Component Factory</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#removePepperImporterComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	public void testRemovePepperImporterComponentFactory__ComponentFactory() 
	{
		//see other testImporter-methods
	}
	
	/**
	 * This test tests adding an importer factory.
	 */
	public void testAddImporterFactory()
	{
		TestComponentFactory importerFactory= new TestComponentFactory();
		importerFactory.setClass(TestImporter1.class);
		this.getFixture().addPepperImporterComponentFactory(importerFactory);
		assertEquals(1, this.getFixture().getPepperImporterComponentFactories().size());
	}
	
	/**
	 * This test tests adding an importer factory and checks, that an object will
	 * return.
	 */
	public void testGetOneImporter()
	{
		TestComponentFactory importerFactory= new TestComponentFactory();
		importerFactory.setClass(TestImporter1.class);
		this.getFixture().addPepperImporterComponentFactory(importerFactory);
		ImporterParams importerParams1= PepperParamsFactory.eINSTANCE.createImporterParams();
		importerParams1.setModuleName(TestImporter1.getModuleName());
		PepperImporter importer1= this.getFixture().getPepperImporter(importerParams1);
		assertNotNull(importer1);
	}
	
	/**
	 * This test tests adding an importer factory and checks, that not the same
	 * objects will returned by calling getImporter.
	 */
	public void testGetTwoImporter()
	{
		TestComponentFactory importerFactory= new TestComponentFactory();
		importerFactory.setClass(TestImporter1.class);
		this.getFixture().addPepperImporterComponentFactory(importerFactory);
		ImporterParams importerParams1= PepperParamsFactory.eINSTANCE.createImporterParams();
		importerParams1.setModuleName(TestImporter1.getModuleName());
		PepperImporter importer1= this.getFixture().getPepperImporter(importerParams1);
		assertNotNull(importer1);
		ImporterParams importerParams2= PepperParamsFactory.eINSTANCE.createImporterParams();
		importerParams2.setModuleName(TestImporter1.getModuleName());
		PepperImporter importer2= this.getFixture().getPepperImporter(importerParams2);
		assertNotNull(importer2);
		assertNotSame(importer1, importer2);
	}
	
	/**
	 * This test tests adding an importer factory and checks, that an object will
	 * return.
	 */
	public void testGetOneImporterByFormatDefinition()
	{
		TestComponentFactory importerFactory= new TestComponentFactory();
		importerFactory.setClass(TestImporter1.class);
		this.getFixture().addPepperImporterComponentFactory(importerFactory);
		ImporterParams importerParams1= PepperParamsFactory.eINSTANCE.createImporterParams();
		importerParams1.setFormatName(TestImporter1.getFormats().get(0).getFormatName());
		importerParams1.setFormatVersion(TestImporter1.getFormats().get(0).getFormatVersion());

		PepperImporter importer1= this.getFixture().getPepperImporter(importerParams1);
		assertNotNull(importer1);
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperImporters() <em>Get Pepper Importers</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperImporters()
	 */
	public void testGetPepperImporters() 
	{
		assertNull(this.getFixture().getPepperImporters());
		
		//create one importer
		TestComponentFactory importerFactory= new TestComponentFactory();
		importerFactory.setClass(TestImporter1.class);
		this.getFixture().addPepperImporterComponentFactory(importerFactory);
		ImporterParams importerParams1= PepperParamsFactory.eINSTANCE.createImporterParams();
		importerParams1.setModuleName(TestImporter1.getModuleName());
		
		assertEquals(1, this.getFixture().getPepperImporters().size());
		assertEquals(TestImporter1.getModuleName(), this.getFixture().getPepperImporters().get(0).getName());
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperManipulator(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams) <em>Get Pepper Manipulator</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperManipulator(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams)
	 */
	public void testGetPepperManipulator__ModuleParams() 
	{
		//see other testExporter-methods
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#addPepperManipulatorComponentFactory(org.osgi.service.component.ComponentFactory) <em>Add Pepper Manipulator Component Factory</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#addPepperManipulatorComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	public void testAddPepperManipulatorComponentFactory__ComponentFactory() 
	{
		//see other testManipulator-methods
	}

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#removePepperManipulatorComponentFactory(org.osgi.service.component.ComponentFactory) <em>Remove Pepper Manipulator Component Factory</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#removePepperManipulatorComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	public void testRemovePepperManipulatorComponentFactory__ComponentFactory() 
	{
		//see other testManipulator-methods
	}

	/**
	 * This test tests adding an manipulator factory.
	 */
	public void testAddManipulatorFactory()
	{
		TestComponentFactory manipulatorFactory= new TestComponentFactory();
		manipulatorFactory.setClass(TestManipulator1.class);
		this.getFixture().addPepperManipulatorComponentFactory(manipulatorFactory);
		assertEquals(1, this.getFixture().getPepperManipulatorComponentFactories().size());
	}
	
	/**
	 * This test tests adding an manipulator factory and checks, that an object will
	 * return.
	 */
	public void testGetOneManipulator()
	{
		TestComponentFactory manipulatorFactory= new TestComponentFactory();
		manipulatorFactory.setClass(TestManipulator1.class);
		this.getFixture().addPepperManipulatorComponentFactory(manipulatorFactory);
		ModuleParams manipulatorParams1= PepperParamsFactory.eINSTANCE.createModuleParams();
		manipulatorParams1.setModuleName(TestManipulator1.getModuleName());
		PepperManipulator manipulator1= this.getFixture().getPepperManipulator(manipulatorParams1);
		assertNotNull(manipulator1);
	}
	
	/**
	 * This test tests adding an manipulator factory and checks, that not the same
	 * objects will returned by calling getManipulator.
	 */
	public void testGetTwoManipulator()
	{
		TestComponentFactory manipulatorFactory= new TestComponentFactory();
		manipulatorFactory.setClass(TestManipulator1.class);
		this.getFixture().addPepperManipulatorComponentFactory(manipulatorFactory);
		ModuleParams manipulatorParams1= PepperParamsFactory.eINSTANCE.createModuleParams();
		manipulatorParams1.setModuleName(TestManipulator1.getModuleName());
		PepperManipulator manipulator1= this.getFixture().getPepperManipulator(manipulatorParams1);
		assertNotNull(manipulator1);
		ModuleParams manipulatorParams2= PepperParamsFactory.eINSTANCE.createModuleParams();
		manipulatorParams2.setModuleName(TestManipulator1.getModuleName());
		PepperManipulator manipulator2= this.getFixture().getPepperManipulator(manipulatorParams2);
		assertNotNull(manipulator2);
		assertNotSame(manipulator1, manipulator2);
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperManipulators() <em>Get Pepper Manipulators</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperManipulators()
	 */
	public void testGetPepperManipulators() 
	{
		assertNull(this.getFixture().getPepperManipulators());
		
		//create one manipulator
		TestComponentFactory manipulatorFactory= new TestComponentFactory();
		manipulatorFactory.setClass(TestManipulator1.class);
		this.getFixture().addPepperManipulatorComponentFactory(manipulatorFactory);
		ModuleParams manipulatorParams1= PepperParamsFactory.eINSTANCE.createModuleParams();
		manipulatorParams1.setModuleName(TestManipulator1.getModuleName());
		
		assertEquals(1, this.getFixture().getPepperManipulators().size());
		assertEquals(TestManipulator1.getModuleName(), this.getFixture().getPepperManipulators().get(0).getName());
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperExporter(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams) <em>Get Pepper Exporter</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperExporter(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams)
	 */
	public void testGetPepperExporter__ExporterParams() 
	{
		//see other testExporter-methods
	}
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#addPepperExporterComponentFactory(org.osgi.service.component.ComponentFactory) <em>Add Pepper Exporter Component Factory</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#addPepperExporterComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	public void testAddPepperExporterComponentFactory__ComponentFactory() 
	{
		//see other testExporter-methods
	}

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#removePepperExporterComponentFactory(org.osgi.service.component.ComponentFactory) <em>Remove Pepper Exporter Component Factory</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#removePepperExporterComponentFactory(org.osgi.service.component.ComponentFactory)
	 */
	public void testRemovePepperExporterComponentFactory__ComponentFactory() 
	{
		//see other testExporter-methods
	}
	
	/**
	 * This test tests adding an exporter factory.
	 */
	public void testAddExporterFactory()
	{
		TestComponentFactory exporterFactory= new TestComponentFactory();
		exporterFactory.setClass(TestExporter1.class);
		this.getFixture().addPepperExporterComponentFactory(exporterFactory);
		assertEquals(1, this.getFixture().getPepperExporterComponentFactories().size());
	}
	
	/**
	 * This test tests adding an exporter factory and checks, that an object will
	 * return.
	 */
	public void testGetOneExporter()
	{
		TestComponentFactory exporterFactory= new TestComponentFactory();
		exporterFactory.setClass(TestExporter1.class);
		this.getFixture().addPepperExporterComponentFactory(exporterFactory);
		ExporterParams exporterParams1= PepperParamsFactory.eINSTANCE.createExporterParams();
		exporterParams1.setModuleName(TestExporter1.getModuleName());
		PepperExporter exporter1= this.getFixture().getPepperExporter(exporterParams1);
		assertNotNull(exporter1);
	}
	
	/**
	 * This test tests adding an exporter factory and checks, that not the same
	 * objects will returned by calling getExporter.
	 */
	public void testGetTwoExporter()
	{
		TestComponentFactory exporterFactory= new TestComponentFactory();
		exporterFactory.setClass(TestExporter1.class);
		this.getFixture().addPepperExporterComponentFactory(exporterFactory);
		ExporterParams exporterParams1= PepperParamsFactory.eINSTANCE.createExporterParams();
		exporterParams1.setModuleName(TestExporter1.getModuleName());
		PepperExporter exporter1= this.getFixture().getPepperExporter(exporterParams1);
		assertNotNull(exporter1);
		ExporterParams exporterParams2= PepperParamsFactory.eINSTANCE.createExporterParams();
		exporterParams2.setModuleName(TestExporter1.getModuleName());
		PepperExporter exporter2= this.getFixture().getPepperExporter(exporterParams2);
		assertNotNull(exporter2);
		assertNotSame(exporter1, exporter2);
	}
	
	/**
	 * This test tests adding an exporter factory and checks, that an object will
	 * return.
	 */
	public void testGetOneExporterByFormatDefinition()
	{
		TestComponentFactory exporterFactory= new TestComponentFactory();
		exporterFactory.setClass(TestExporter1.class);
		this.getFixture().addPepperExporterComponentFactory(exporterFactory);
		ExporterParams exporterParams1= PepperParamsFactory.eINSTANCE.createExporterParams();
		exporterParams1.setFormatName(TestExporter1.getFormats().get(0).getFormatName());
		exporterParams1.setFormatVersion(TestExporter1.getFormats().get(0).getFormatVersion());

		PepperExporter exporter1= this.getFixture().getPepperExporter(exporterParams1);
		assertNotNull(exporter1);
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperExporters() <em>Get Pepper Exporters</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getPepperExporters()
	 */
	public void testGetPepperExporters() 
	{
		assertNull(this.getFixture().getPepperExporters());
		
		//create one exporter
		TestComponentFactory exporterFactory= new TestComponentFactory();
		exporterFactory.setClass(TestExporter1.class);
		this.getFixture().addPepperExporterComponentFactory(exporterFactory);
		ExporterParams exporterParams1= PepperParamsFactory.eINSTANCE.createExporterParams();
		exporterParams1.setModuleName(TestExporter1.getModuleName());
		
		assertEquals(1, this.getFixture().getPepperExporters().size());
		assertEquals(TestExporter1.getModuleName(), this.getFixture().getPepperExporters().get(0).getName());
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getStatus() <em>Get Status</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver#getStatus()
	 */
	public void testGetStatus() 
	{
		assertNotNull(this.getFixture().getStatus());
	}

	/**
	 * Tests if temprorary folder is set.
	 */
	public void testImporterTempURI()
	{
		assertNull(this.getFixture().getPepperImporters());
		
		//create one Importer
		TestComponentFactory ImporterFactory= new TestComponentFactory();
		ImporterFactory.setClass(TestImporter1.class);
		this.getFixture().addPepperImporterComponentFactory(ImporterFactory);
		ImporterParams ImporterParams1= PepperParamsFactory.eINSTANCE.createImporterParams();
		ImporterParams1.setModuleName(TestImporter1.getModuleName());
		
		assertEquals(1, this.getFixture().getPepperImporters().size());
		PepperImporter Importer= this.getFixture().getPepperImporters().get(0);
		assertEquals(TestImporter1.getModuleName(), Importer.getName());
		assertNotNull("There is no temprorary folder for Importer.", Importer.getTemproraries());
		File tempFile= new File(Importer.getTemproraries().toFileString());
		assertTrue("The file shall Imists.", tempFile.exists());
		assertTrue("The file shall be a folder.", tempFile.isDirectory());
	}
	
	/**
	 * Tests if temprorary folder is set.
	 */
	public void testManipulatorTempURI()
	{
		assertNull(this.getFixture().getPepperManipulators());
		
		//create one Manipulator
		TestComponentFactory ManipulatorFactory= new TestComponentFactory();
		ManipulatorFactory.setClass(TestManipulator1.class);
		this.getFixture().addPepperManipulatorComponentFactory(ManipulatorFactory);
		ModuleParams ManipulatorParams1= PepperParamsFactory.eINSTANCE.createModuleParams();
		ManipulatorParams1.setModuleName(TestManipulator1.getModuleName());
		
		assertEquals(1, this.getFixture().getPepperManipulators().size());
		PepperManipulator Manipulator= this.getFixture().getPepperManipulators().get(0);
		assertEquals(TestManipulator1.getModuleName(), Manipulator.getName());
		assertNotNull("There is no temprorary folder for Manipulator.", Manipulator.getTemproraries());
		File tempFile= new File(Manipulator.getTemproraries().toFileString());
		assertTrue("The file shall exists.", tempFile.exists());
		assertTrue("The file shall be a folder.", tempFile.isDirectory());
	}
	
	/**
	 * Tests if temprorary folder is set.
	 */
	public void testExporterTempURI()
	{
		assertNull(this.getFixture().getPepperExporters());
		
		//create one exporter
		TestComponentFactory exporterFactory= new TestComponentFactory();
		exporterFactory.setClass(TestExporter1.class);
		this.getFixture().addPepperExporterComponentFactory(exporterFactory);
		ExporterParams exporterParams1= PepperParamsFactory.eINSTANCE.createExporterParams();
		exporterParams1.setModuleName(TestExporter1.getModuleName());
		
		assertEquals(1, this.getFixture().getPepperExporters().size());
		PepperExporter exporter= this.getFixture().getPepperExporters().get(0);
		assertEquals(TestExporter1.getModuleName(), exporter.getName());
		assertNotNull("There is no temprorary folder for exporter.", exporter.getTemproraries());
		File tempFile= new File(exporter.getTemproraries().toFileString());
		assertTrue("The file shall exists.", tempFile.exists());
		assertTrue("The file shall be a folder.", tempFile.isDirectory());
	}

	/**
	 * Tests if resource folder is set.
	 */
	public void testImporterResourceURI()
	{
		assertNull(this.getFixture().getPepperImporters());
		
		//create one Importer
		TestComponentFactory ImporterFactory= new TestComponentFactory();
		ImporterFactory.setClass(TestImporter1.class);
		this.getFixture().addPepperImporterComponentFactory(ImporterFactory);
		ImporterParams ImporterParams1= PepperParamsFactory.eINSTANCE.createImporterParams();
		ImporterParams1.setModuleName(TestImporter1.getModuleName());
		
		assertEquals(1, this.getFixture().getPepperImporters().size());
		PepperImporter Importer= this.getFixture().getPepperImporters().get(0);
		assertEquals(TestImporter1.getModuleName(), Importer.getName());
		assertNotNull("There is no resource folder for Importer.", Importer.getResources());
		File resFile= new File(Importer.getResources().toFileString());
		assertTrue("The file shall exists.", resFile.exists());
		assertTrue("The file shall be a folder.", resFile.isDirectory());
	}
	/**
	 * Tests if resource folder is set.
	 */
	public void testManipulatorResourceURI()
	{
		assertNull(this.getFixture().getPepperManipulators());
		
		//create one Manipulator
		TestComponentFactory ManipulatorFactory= new TestComponentFactory();
		ManipulatorFactory.setClass(TestManipulator1.class);
		this.getFixture().addPepperManipulatorComponentFactory(ManipulatorFactory);
		ModuleParams ManipulatorParams1= PepperParamsFactory.eINSTANCE.createModuleParams();
		ManipulatorParams1.setModuleName(TestManipulator1.getModuleName());
		
		assertEquals(1, this.getFixture().getPepperManipulators().size());
		PepperManipulator Manipulator= this.getFixture().getPepperManipulators().get(0);
		assertEquals(TestManipulator1.getModuleName(), Manipulator.getName());
		assertNotNull("There is no resource folder for Manipulator.", Manipulator.getResources());
		File resFile= new File(Manipulator.getResources().toFileString());
		assertTrue("The file shall exists.", resFile.exists());
		assertTrue("The file shall be a folder.", resFile.isDirectory());
	}
	
	/**
	 * Tests if resource folder is set.
	 */
	public void testExporterResourceURI()
	{
		assertNull(this.getFixture().getPepperExporters());
		
		//create one exporter
		TestComponentFactory exporterFactory= new TestComponentFactory();
		exporterFactory.setClass(TestExporter1.class);
		this.getFixture().addPepperExporterComponentFactory(exporterFactory);
		ExporterParams exporterParams1= PepperParamsFactory.eINSTANCE.createExporterParams();
		exporterParams1.setModuleName(TestExporter1.getModuleName());
		
		assertEquals(1, this.getFixture().getPepperExporters().size());
		PepperExporter exporter= this.getFixture().getPepperExporters().get(0);
		assertEquals(TestExporter1.getModuleName(), exporter.getName());
		assertNotNull("There is no resource folder for exporter.", exporter.getResources());
		File resFile= new File(exporter.getResources().toFileString());
		assertTrue("The file shall exists.", resFile.exists());
		assertTrue("The file shall be a folder.", resFile.isDirectory());
	}
	
	

} //PepperModuleResolverTest

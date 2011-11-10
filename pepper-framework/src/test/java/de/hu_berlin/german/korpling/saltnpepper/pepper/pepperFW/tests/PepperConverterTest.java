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

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleResolverImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.Resolver.TestComponentFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.TestModules.TestExporter1;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.TestModules.TestImporter1;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.TestModules.TestModule1;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Pepper Converter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#setPepperParams(org.eclipse.emf.common.util.URI) <em>Set Pepper Params</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#start() <em>Start</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#startPepperConvertJob(java.lang.Integer) <em>Start Pepper Convert Job</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class PepperConverterTest extends TestCase {

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
		TestRunner.run(PepperConverterTest.class);
	}

	/**
	 * Constructs a new Pepper Converter test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperConverterTest(String name) {
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
	 */
	@Override
	protected void setUp() throws Exception 
	{
		setFixture(PepperFWFactory.eINSTANCE.createPepperConverter());
		PropertyConfigurator.configureAndWatch("settings/log4j.properties", 60*1000 );
		
		PepperModuleResolver resolver= PepperFWFactory.eINSTANCE.createPepperModuleResolver();
		((PepperConverter)this.getFixture()).setPepperModuleResolver(resolver);
		
		//add Importer
		TestComponentFactory importerFactory= new TestComponentFactory();
		importerFactory.setClass(TestImporter1.class);
		resolver.addPepperImporterComponentFactory(importerFactory);
		
		//add Manipulator
		TestComponentFactory manipulatorFactory= new TestComponentFactory();
		manipulatorFactory.setClass(TestModule1.class);
		resolver.addPepperExporterComponentFactory(manipulatorFactory);
		
		//add Exporter
		TestComponentFactory exporterFactory= new TestComponentFactory();
		exporterFactory.setClass(TestExporter1.class);
		resolver.addPepperExporterComponentFactory(exporterFactory);
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
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#setPepperParams(java.net.URI) <em>Set Pepper Params</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#setPepperParams(java.net.URI)
	 */
	public void testSetPepperParams__URI() 
	{
		//this is tested in: SetParameterByFileTest
	}

	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#start() <em>Start</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#start()
	 */
	public void testStart() throws Exception
	{
//		this.getFixture().getPepperModuleResolver().addPepperImporter(new TestImporter1());
//		this.getFixture().getPepperModuleResolver().addPepperModule(new TestModule1());
//		this.getFixture().getPepperModuleResolver().addPepperExporter(new TestExporter1());
//		
//		URI uri= new URI("./data/pepperParams/testParam/OKMoreJobs.pepperparams");
//		this.getFixture().setPepperParams(uri);
//		this.getFixture().start();
		//TODO further tests with using a parameter file
	}

	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#startPepperConvertJob(java.lang.Integer) <em>Start Pepper Convert Job</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#startPepperConvertJob(java.lang.Integer)
	 */
	public void testStartPepperConvertJob__Integer() throws Exception
	{
		//see other start... tests
	}
	
	/**
	 * tests jobs, which does not exist
	 * @throws Exception
	 */
	public void testStartNonExistingJobs() throws Exception
	{
		class Runner extends FinishableRunner 
		{			
			@Override
			public void myRunning()
			{				
				//generate a list of element-ids, all module has to see
				EList<SElementId> elementIds= new BasicEList<SElementId>();
				for (int i= 0; i < 10; i++)
				{
					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
					sElementId.setSId("id"+i);
					elementIds.add(sElementId);
				}	
				
//				URI uri= null;
//				try {
//					uri = new URI("./data/pepperParams/testParam/OKMoreJobs.pepperparams");
//					((PepperConverter)this.getFixture()).setPepperParams(uri);
//				} catch (URISyntaxException e1) {
//					throw new RuntimeException(e1);
//				}
//				uri = URI.createFileURI("./data/pepperParams/testParam/OKMoreJobs.pepperparams");
				for (int i= 0; i< 10; i++)
				{	
					((PepperConverter)this.getFixture()).startPepperConvertJob(i);
				}	
			}
		}
		try {
			FinishableRunner.startRunner(3000l, new Runner(), this.getFixture());
			fail("the jobs does not exists");
		} catch (PepperException e) {
		}
	}
	
	class TestModuleResolver extends PepperModuleResolverImpl
	{
		PepperImporter importer= null;
		public PepperImporter getPepperImporter(ImporterParams pepperImporterParams)
		{
			return(importer);
		}
		
		PepperManipulator manipulator= null;
		public PepperManipulator getPepperManipulator(ModuleParams pepperModuleParams)
		{
			return(manipulator);
		}
		
		PepperExporter exporter= null;
		public PepperExporter getPepperExporter(ExporterParams pepperExporterParams)
		{
			return(exporter);
		}
	}
	
	class Notifier
	{
		EList<PepperModule> modules= new BasicEList<PepperModule>();
	}
	
	class TestModuleResolver2 extends PepperModuleResolverImpl
	{
		Notifier notifier= null;
		
		EList<SElementId> importerElementIds= null;
		public PepperImporter getPepperImporter(ImporterParams pepperImporterParams)
		{
			TestImporter1 importer= new TestImporter1();;
			importer.setSElementIds(importerElementIds);
			notifier.modules.add(importer);
			return(importer);
		}
		
		EList<SElementId> manipulatorElementIds= null;
		public PepperManipulator getPepperManipulator(ModuleParams pepperModuleParams)
		{
			TestModule1 manipulator= new TestModule1();
			manipulator.setSElementIds(manipulatorElementIds);
			notifier.modules.add(manipulator);
			return(manipulator);
		}
		
		EList<SElementId> exporterElementIds= null;
		public PepperExporter getPepperExporter(ExporterParams pepperExporterParams)
		{
			TestExporter1 exporter= new TestExporter1();
			exporter.setSElementIds(exporterElementIds);
			notifier.modules.add(exporter);
			return(exporter);
		}
	}
	
	/**
	 * Tests one job with importer and exporter.
	 * @throws Exception
	 */
	//TODO fixme
//	public void testSettingSpecialParams() throws Exception
//	{
//		class Runner extends FinishableRunner 
//		{			
//			TestImporter1 importer1= null;
//			TestExporter1 exporter1= null;
//			@Override
//			public void myRunning()
//			{
//				//generate a list of element-ids, all module has to see
//				EList<SElementId> elementIds= new BasicEList<SElementId>();
//				for (int i= 0; i < 10; i++)
//				{
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					elementIds.add(sElementId);
//				}	
//				TestModuleResolver resolver= new TestModuleResolver();
//				((PepperConverter)this.getFixture()).setPepperModuleResolver(resolver);
//				//init importer
//				importer1= new TestImporter1();
//				importer1.elementIds= elementIds;
//				resolver.importer= importer1;
//				
//				//init exporter
//				exporter1= new TestExporter1();
//				exporter1.elementIds= elementIds;
//				resolver.exporter= exporter1;
//				
//				
//				URI uri;
//				uri = URI.createFileURI("./data/pepperParams/testParam/OKOneJobSpecialParams.pepperparams");
//				
//				((PepperConverter)this.getFixture()).setPepperParams(uri);
//				((PepperConverter)this.getFixture()).start();
//				
//				this.finished= true;
//			}
//		}
//		Runner runner= new Runner();
//		FinishableRunner.startRunner(5000l, runner, this.getFixture());
////		System.out.println("importer1.getSpecialParams(): "+ runner.importer1.getSpecialParams());
////		System.out.println("exporter1.getSpecialParams(): "+ runner.exporter1.getSpecialParams());
//		
//	}
	
	/**
	 * Tests one job with importer and exporter.
	 * @throws Exception
	 */
	//TODO fixme
//	public void testStartOneJob() throws Exception
//	{
//		class Runner extends FinishableRunner 
//		{			
//			@Override
//			public void myRunning()
//			{
//				//generate a list of element-ids, all module has to see
//				EList<SElementId> elementIds= new BasicEList<SElementId>();
//				for (int i= 0; i < 10; i++)
//				{
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					elementIds.add(sElementId);
//				}	
//				TestModuleResolver resolver= new TestModuleResolver();
//				((PepperConverter)this.getFixture()).setPepperModuleResolver(resolver);
//				//init importer
//				TestImporter1 importer1= new TestImporter1();
//				importer1.elementIds= elementIds;
//				resolver.importer= importer1;
//				
//				//init exporter
//				TestExporter1 exporter1= new TestExporter1();
//				exporter1.elementIds= elementIds;
//				resolver.exporter= exporter1;
//				
//				
//				URI uri;
////				try {
////					uri = new URI("./data/pepperParams/testParam/OKOneJobs.pepperparams");
////				} catch (URISyntaxException e1) {
////					throw new RuntimeException(e1);
////				}
//				uri = URI.createFileURI("./data/pepperParams/testParam/OKOneJobs.pepperparams");
//				((PepperConverter)this.getFixture()).setPepperParams(uri);
//				((PepperConverter)this.getFixture()).startPepperConvertJob(1);
//				this.finished= true;
//			}
//		}
//		FinishableRunner.startRunner(3000l, new Runner(), this.getFixture());
//		
//	}
	
	/**
	 * Tests one job with importer, module and exporter.
	 * @throws Exception
	 */
	//TODO fixme
//	public void testStartOneJob2() throws Exception
//	{
//		
//		class Runner extends FinishableRunner 
//		{			
//			@Override
//			public void myRunning()
//			{
//				//generate a list of element-ids, all module has to see
//				EList<SElementId> elementIds= new BasicEList<SElementId>();
//				for (int i= 0; i < 10; i++)
//				{
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					elementIds.add(sElementId);
//				}	
//				TestModuleResolver resolver= new TestModuleResolver();
//				((PepperConverter)this.getFixture()).setPepperModuleResolver(resolver);
//				//init importer
//				TestImporter1 importer1= new TestImporter1();
//				importer1.elementIds= elementIds;
//				resolver.importer= importer1;
//				
//				//init manipulator
//				TestModule1 module1= new TestModule1();
//				module1.elementIds= elementIds;
//				resolver.manipulator= module1;
////				((PepperConverter)this.getFixture()).getPepperModuleResolver().addPepperModule(module1);
//				
//				//init exporter
//				TestExporter1 exporter1= new TestExporter1();
//				exporter1.elementIds= elementIds;
//				resolver.exporter= exporter1;				
//				
//				URI uri;
////				try {
////					uri = new URI("./data/pepperParams/testParam/OKOneJobs2.pepperparams");
////				} catch (URISyntaxException e1) {
////					throw new RuntimeException(e1);
////				}
//				uri = URI.createFileURI("./data/pepperParams/testParam/OKOneJobs2.pepperparams");
//				((PepperConverter)this.getFixture()).setPepperParams(uri);
//				((PepperConverter)this.getFixture()).startPepperConvertJob(1);
//				
//				assertEquals(0, importer1.elementIds.size());
//				assertEquals(0, module1.elementIds.size());
//				assertEquals(0, exporter1.elementIds.size());
//				this.finished= true;
//			}
//		}
//		FinishableRunner.startRunner(3000l, new Runner(), this.getFixture());	
//	}
	
	/**
	 * Tests two identical jobs. Both jobs doesn�t run parallel.
	 * @throws Exception
	 */
	//TODO fixme
//	public void testStartTwoJobsNotParallel() throws Exception
//	{
//		
//		class Runner extends FinishableRunner 
//		{			
//			@Override
//			public void myRunning()
//			{
//				//generate a list of element-ids, all module has to see
//				EList<SElementId> elementIds= new BasicEList<SElementId>();
//				for (int i= 0; i < 10; i++)
//				{
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					elementIds.add(sElementId);
//				}	
//				TestModuleResolver2 resolver= new TestModuleResolver2();
//				resolver.notifier= new Notifier();
//				((PepperConverter)this.getFixture()).setPepperModuleResolver(resolver);
//				//init importer
//				resolver.importerElementIds= elementIds;
//				
//				//init manipulator
//				resolver.manipulatorElementIds= elementIds;
//				
//				//init exporter
//				resolver.exporterElementIds= elementIds;
//				
//				URI uri;
////				try {
////					uri = new URI("./data/pepperParams/testParam/OKTwoJobs1.pepperparams");
////				} catch (URISyntaxException e1) {
////					throw new RuntimeException(e1);
////				}
//				uri = URI.createFileURI("./data/pepperParams/testParam/OKTwoJobs1.pepperparams");
//				((PepperConverter)this.getFixture()).setParallelized(false);
//				((PepperConverter)this.getFixture()).setPepperParams(uri);
//				((PepperConverter)this.getFixture()).start();
//				
//				for (PepperModule pepperModule: resolver.notifier.modules)
//				{
//					if (pepperModule instanceof TestImporter1)
//						assertEquals(0, ((TestImporter1)pepperModule).elementIds.size());
//					else if (pepperModule instanceof TestModule1)
//					{
//						assertEquals(0, ((TestModule1)pepperModule).elementIds.size());
//					}
//					else if (pepperModule instanceof TestExporter1)
//					{
//						assertEquals(0, ((TestExporter1)pepperModule).elementIds.size());
//					}
//				}	
//				this.finished= true;
//			}
//		}
//		FinishableRunner.startRunner(8000l, new Runner(), this.getFixture());	
//	}
	
	/**
	 * Tests two identical jobs. Both jobs run parallel.
	 * @throws Exception
	 */
	//TODO fixme
//	public void testStartTwoJobsParallel() throws Exception
//	{
//		
//		class Runner extends FinishableRunner 
//		{			
//			@Override
//			public void myRunning()
//			{
//				//generate a list of element-ids, all module has to see
//				EList<SElementId> elementIds= new BasicEList<SElementId>();
//				for (int i= 0; i < 10; i++)
//				{
//					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
//					sElementId.setSId("id"+i);
//					elementIds.add(sElementId);
//				}	
//				TestModuleResolver2 resolver= new TestModuleResolver2();
//				resolver.notifier= new Notifier();
//				((PepperConverter)this.getFixture()).setPepperModuleResolver(resolver);
//				//init importer
//				resolver.importerElementIds= elementIds;
//				
//				//init manipulator
//				resolver.manipulatorElementIds= elementIds;
//				
//				//init exporter
//				resolver.exporterElementIds= elementIds;
//				
//				URI uri;
////				try {
////					uri = new URI("./data/pepperParams/testParam/OKTwoJobs1.pepperparams");
////				} catch (URISyntaxException e1) {
////					throw new RuntimeException(e1);
////				}
//				uri = URI.createFileURI("./data/pepperParams/testParam/OKTwoJobs1.pepperparams");
//				((PepperConverter)this.getFixture()).setParallelized(true);
//				((PepperConverter)this.getFixture()).setPepperParams(uri);
//				((PepperConverter)this.getFixture()).start();
//				
//				for (PepperModule pepperModule: resolver.notifier.modules)
//				{
//					if (pepperModule instanceof TestImporter1)
//						assertEquals(0, ((TestImporter1)pepperModule).elementIds.size());
//					else if (pepperModule instanceof TestModule1)
//						assertEquals(0, ((TestModule1)pepperModule).elementIds.size());
//					else if (pepperModule instanceof TestExporter1)
//					{	
//						assertEquals(0, ((TestExporter1)pepperModule).elementIds.size());
//					}
//				}	
//				this.finished= true;
//			}
//		}
//		FinishableRunner.startRunner(8000l, new Runner(), this.getFixture());	
//	}
	
	/**
	 * Checks the private method checkAndResolveURI(URI baseURI, URI uri), if the only supported uris can be passed and if 
	 * the resolved uri is correct.
	 */
	public void testCheckAndResolveURI_URI_URI()
	{
		class TestPepperConverterImpl extends PepperConverterImpl
		{
			public URI checkAndResolveURITest(URI baseURI, URI uri)
			{
				return(super.checkAndResolveURI(baseURI, uri));
			}
		}
		TestPepperConverterImpl fixture= new TestPepperConverterImpl();
		URI baseUri= null;
		URI uri= null;
		
		assertNull(fixture.checkAndResolveURITest(null, null));
		
		baseUri= URI.createURI("file:/d:/TestFolder");
		uri= null;
		assertNull(fixture.checkAndResolveURITest(baseUri, uri));
		
		baseUri= null;
		uri= URI.createURI("file:/d:/TestFolder");
		assertEquals(uri, fixture.checkAndResolveURITest(baseUri, uri));
		
		//start: check windows absolute pathes
			try {
				baseUri= null;
				uri= URI.createURI("d:/TestFolder");
				fixture.checkAndResolveURITest(baseUri, uri);
				fail("Wrong scheme");
			} catch (PepperConvertException e) {
			}
			
			try {
				baseUri= URI.createURI("d:/TestFolder");
				uri= URI.createURI("./TestFolder");
				fixture.checkAndResolveURITest(baseUri, uri);
				fail("Wrong scheme");
			} catch (PepperConvertException e) {
			}
		//end: check windows absolute pathes
			
		baseUri= URI.createURI("file:/d:/a/b/c");
		uri= URI.createURI("/d/e/f/");
		assertEquals(URI.createURI("/d/e/f/"),fixture.checkAndResolveURITest(baseUri, uri));
		
		baseUri= URI.createURI("file:/d:/a/b/c");
		uri= URI.createURI("./d/e/f/");
		assertEquals(URI.createURI("file:/d:/a/b/d/e/f/"),fixture.checkAndResolveURITest(baseUri, uri));
		
		baseUri= URI.createURI("file:/d:/a/b/c/");
		uri= URI.createURI("./d/e/f/");
		assertEquals(URI.createURI("file:/d:/a/b/c/d/e/f/"),fixture.checkAndResolveURITest(baseUri, uri));
	}
} //PepperConverterTest

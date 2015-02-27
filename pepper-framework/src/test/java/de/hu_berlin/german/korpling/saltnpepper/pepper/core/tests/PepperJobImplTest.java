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
package de.hu_berlin.german.korpling.saltnpepper.pepper.core.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.JOB_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MEMORY_POLICY;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.StepDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolverImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperParamsReader;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.Step;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperOSGiRunnerException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.WorkflowException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperManipulatorImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperModuleTest;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;

public class PepperJobImplTest extends PepperJobImpl implements UncaughtExceptionHandler{

	public PepperJobImplTest() {
		super("myJob");
	}
	private PepperJobImpl fixture= null;
	
	public PepperJobImpl getFixture() {
		return fixture;
	}

	public void setFixture(PepperJobImpl fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp(){
		setFixture(this);
		isLoadTest= false;
	}
	
	@Test
	public void testSetGetSaltProject() {
		SaltProject saltProject= SaltFactory.eINSTANCE.createSaltProject();
		getFixture().setSaltProject(saltProject);
		assertEquals(saltProject, getFixture().getSaltProject());
	}
	/**
	 * Tests if id is set correctly
	 */
	@Test
	public void testGetId() {
		assertEquals("myJob", getFixture().getId());
		try{
			new PepperJobImpl(null);
		}catch (PepperFWException e){}
		try{
			new PepperJobImpl("");
		}catch (PepperFWException e){}
	}
	/**
	 * tests if {@link PepperJobImpl#toString()} always returns a correct value
	 */
	@Test
	public void testToString() {
		assertNotNull(getFixture().toString());
	}
	/**
	 * tests if {@link PepperJobImpl#setConfiguration(de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperProperties)} and
	 * {@link PepperJobImpl#getConfiguration()} works correctly
	 */
	@Test
	public void testGetSetConfiguration() {
		assertNotNull(getFixture().getConfiguration());
		PepperConfiguration conf= new PepperConfiguration();
		getFixture().setConfiguration(conf);
		assertEquals(conf, getFixture().getConfiguration());
	}
	/**
	 * Tests if {@link PepperJobImpl#setConfiguration(de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration)} 
	 * initializes values for {@link PepperJobImpl#getMemPolicy()} and {@link PepperJobImpl#getMaxNumberOfDocuments()}. 
	 */
	@Test
	public void testAfterSetConfiguration(){
		PepperConfiguration conf= new PepperConfiguration();
		getFixture().setConfiguration(conf);
		
		assertEquals(MEMORY_POLICY.MODERATE, getFixture().getMemPolicy());
		assertEquals(10, getFixture().getMaxNumberOfDocuments());
		
		conf.put(PepperConfiguration.PROP_MEMORY_POLICY, MEMORY_POLICY.GREEDY.toString());
		conf.put(PepperConfiguration.PROP_MAX_AMOUNT_OF_SDOCUMENTS, "2");
		getFixture().setConfiguration(conf);
		
		assertEquals(MEMORY_POLICY.GREEDY, getFixture().getMemPolicy());
		assertEquals(2, getFixture().getMaxNumberOfDocuments());
	}
	
	/**
	 * Tests if setting and getting of {@link ModuleResolverImpl} object works correctly
	 */
	@Test
	public void testSetGetModuleResolver(){
		assertNull(getFixture().getModuleResolver());
		ModuleResolver moduleResolver= new ModuleResolverImpl();
		getFixture().setModuleResolver(moduleResolver);
		assertEquals(moduleResolver, getFixture().getModuleResolver());
	}
	/**
	 * Tests if the adding of different steps like import, manipulation and export steps and checks if
	 * they are contained in all respecting lists  {@link #getImportSteps()}, {@link #getManipulationSteps()}, 
	 * {@link #getExportSteps()} and {@link #getAllSteps()}. Implicitly also checks {@link #addStep(Step)}, 
	 * but not in detail.
	 */
	@Test
	public void testGetAllStep(){
		assertNotNull(getFixture().getImportSteps());
		assertEquals(0, getFixture().getImportSteps().size());
		
		assertNotNull(getFixture().getManipulationSteps());
		assertEquals(0, getFixture().getManipulationSteps().size());
		
		assertNotNull(getFixture().getExportSteps());
		assertEquals(0, getFixture().getExportSteps().size());
		
		assertNotNull(getFixture().getAllSteps());
		assertEquals(0, getFixture().getAllSteps().size());
		
		PepperManipulator manipulator= new PepperManipulatorImpl() {
		};
		
		PepperImporter importer= new PepperImporterImpl() {
		};
		
		PepperExporter exporter= new PepperExporterImpl() {
		};
		
		Step imStep= new Step("anyId");
		imStep.setPepperModule(importer);
		getFixture().addStep(imStep);
		assertEquals(1, getFixture().getImportSteps().size());
		assertTrue(getFixture().getImportSteps().contains(imStep));
		assertEquals(1, getFixture().getAllSteps().size());
		assertTrue(getFixture().getAllSteps().contains(imStep));
		
		Step maStep= new Step("maStep");
		maStep.setPepperModule(manipulator);
		getFixture().addStep(maStep);
		assertEquals(1, getFixture().getManipulationSteps().size());
		assertTrue(getFixture().getManipulationSteps().contains(maStep));
		assertEquals(2, getFixture().getAllSteps().size());
		assertTrue(getFixture().getAllSteps().contains(imStep));
		assertTrue(getFixture().getAllSteps().contains(maStep));
		
		
		Step exStep= new Step("exStep");
		exStep.setPepperModule(exporter);
		getFixture().addStep(exStep);
		assertEquals(1, getFixture().getExportSteps().size());
		assertTrue(getFixture().getExportSteps().contains(exStep));
		assertEquals(3, getFixture().getAllSteps().size());
		assertTrue(getFixture().getAllSteps().contains(imStep));
		assertTrue(getFixture().getAllSteps().contains(maStep));
		assertTrue(getFixture().getAllSteps().contains(exStep));
	}
	
	/**
	 * Tests if the adding of a {@link StepDesc} works correctly.
	 * Indirectly method {@link #addStep(Step)} is tested.
	 */
	@Test
	public void testAddStep_STEPDESC(){
		StepDesc stepDesc= null;
		try {
			getFixture().addStep(stepDesc);
			fail();
		} catch (WorkflowException e) {}
		
		stepDesc= new StepDesc();
		try {
			getFixture().addStep(stepDesc);
			fail("Should not work, because no module resolver set.");
		} catch (PepperFWException e) {}
		
		ModuleResolver moduleResolver= new ModuleResolverImpl();
		getFixture().setModuleResolver(moduleResolver);
		
		try {
			getFixture().addStep(stepDesc);
			fail("Should not work, because module type is not set.");
		} catch (WorkflowException e) {}
		
		stepDesc.setModuleType(MODULE_TYPE.MANIPULATOR);
		
		getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
		
		try {
			getFixture().addStep(stepDesc);
			fail("Should not work, no matching Pepper module exist in module resolver.");
		} catch (WorkflowException e) {}
		
		moduleResolver= new ModuleResolverImpl(){
			@Override
			public PepperModule getPepperModule(StepDesc stepDesc){
				PepperModule manipulator= new PepperManipulatorImpl() {
					
				};
				return(manipulator);
			}
		};
		getFixture().setModuleResolver(moduleResolver);
		
		assertEquals(0, getFixture().getAllSteps().size());
		Step step= getFixture().addStep(stepDesc);
		assertEquals(1, getFixture().getAllSteps().size());
		assertTrue(getFixture().getAllSteps().contains(step));
	}
	
	class SampleModule extends PepperManipulatorImpl{
		List<SElementId> orders= null;
		@Override
		public List<SElementId> proposeImportOrder(SCorpusGraph sCorpusGraph){
			return(orders);
		}
	}
	/**
	 * Tests if the adding of a {@link Step} works correctly.Just a small test, since the most
	 * stuff is already tested in {@link #testAddStep_STEPDESC()}
	 */
	@Test
	public void testAddStep_STEP(){
		Step step= null;
		try {
			getFixture().addStep(step);
			fail();
		} catch (WorkflowException e) {}
	}
	
	/**
	 * Tests if the method {@link #wire()} checks its prerequisites correctly.
	 */
	@Test
	public void testWire_Prerequisites(){
		try {
			this.wire();
			fail("Should fail, since no import steps are given.");
		} catch (Exception e) {}
		Step imStep= new Step("imStep");
		imStep.setPepperModule(new PepperImporterImpl() {
		});
		getFixture().addStep(imStep);
		try {
			this.wire();
			fail("Should fail, since no export steps are given.");
		} catch (Exception e) {}
		
		Step exStep= new Step("exStep");
		exStep.setPepperModule(new PepperExporterImpl() {
		});
		getFixture().addStep(exStep);
		
		this.wire();
	}
	/**
	 * Tests if the method {@link #wire()} works correctly.
	 * Checked is:
	 * <ul>
	 * <li>if {@link #getDocumentBuses()} is correct</li>
	 * </ul>
	 */
	@Test
	public void testWire_small(){
		Step imStep= new Step("imStep");
		imStep.setPepperModule(new PepperImporterImpl() {
		});
		getFixture().addStep(imStep);
		
		Step exStep= new Step("exStep");
		exStep.setPepperModule(new PepperExporterImpl() {
		});
		getFixture().addStep(exStep);
		
		this.wire();
		
		assertEquals(1, this.initialDocumentBuses.size());
		assertEquals(3, this.getDocumentBuses().size());
	}
	/**
	 * Tests if the method {@link #wire()} works correctly.
	 * Checked is:
	 * <ul>
	 * <li>if {@link #getDocumentBuses()} is correct</li>
	 * </ul>
	 */
	@Test
	public void testWire_large(){
		Step imStep= new Step("imStep");
		imStep.setPepperModule(new PepperImporterImpl() {});
		getFixture().addStep(imStep);
		
		Step ma1Step= new Step("ma1Step");
		ma1Step.setPepperModule(new PepperManipulatorImpl() {});
		getFixture().addStep(ma1Step);
		
		Step ma2Step= new Step("ma2Step");
		ma2Step.setPepperModule(new PepperManipulatorImpl() {});
		getFixture().addStep(ma2Step);
		
		Step ex1Step= new Step("ex1Step");
		ex1Step.setPepperModule(new PepperExporterImpl() {});
		getFixture().addStep(ex1Step);
		
		Step ex2Step= new Step("ex2Step");
		ex2Step.setPepperModule(new PepperExporterImpl() {});
		getFixture().addStep(ex2Step);
		
		this.wire();
		
		assertEquals(1, this.initialDocumentBuses.size());
		assertEquals(5, this.getDocumentBuses().size());
	}
	
	
	/**
	 * Tests the method {@link #unifyProposedImportOrders(java.util.List)} and checks, if the correct 
	 * unified order is returned.
	 */
	@Test
	public void testUnifyProposedImportOrders(){
		try {
			this.unifyProposedImportOrders(null);
			fail();
		} catch (PepperFWException e) {
		}
		
		getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
		//create three corpus graphs
		getFixture().getSaltProject().getSCorpusGraphs().add(SampleGenerator.createCorpusStructure());
		
		Vector<SElementId> primitiveOrder= new Vector<SElementId>();
		for (SDocument sDocument: getFixture().getSaltProject().getSCorpusGraphs().get(0).getSDocuments()){
			primitiveOrder.add(sDocument.getSElementId());
		}
		List<SElementId> testOrders= null;
		
		SampleModule sampleModule1= new SampleModule();
		sampleModule1.orders= new Vector<SElementId>();
		sampleModule1.setSaltProject(getSaltProject());
		Step step1= new Step("step1");
		step1.setPepperModule(sampleModule1);
		getFixture().addStep(step1);
		
		testOrders= this.unifyProposedImportOrders(getFixture().getSaltProject().getSCorpusGraphs().get(0));
		
		assertEquals(primitiveOrder.size(), testOrders.size());
		assertTrue(primitiveOrder.containsAll(testOrders));
		assertTrue(testOrders.containsAll(primitiveOrder));
		assertEquals(primitiveOrder, testOrders);
		
		Random random= new Random();
		for (int y=0; y<10; y++){
			int i= random.nextInt(primitiveOrder.size()-1);
			SElementId documentId= primitiveOrder.get(i);
			primitiveOrder.remove(i);
			primitiveOrder.add(documentId);
		}
		sampleModule1.orders= primitiveOrder;
		testOrders= this.unifyProposedImportOrders(getFixture().getSaltProject().getSCorpusGraphs().get(0));
		
		assertEquals(primitiveOrder.size(), testOrders.size());
		assertTrue(primitiveOrder.containsAll(testOrders));
		assertTrue(testOrders.containsAll(primitiveOrder));
		assertEquals(primitiveOrder, testOrders);
		
		sampleModule1.orders= new Vector<SElementId>();
		sampleModule1.orders.add(primitiveOrder.get(0));
		testOrders= this.unifyProposedImportOrders(getFixture().getSaltProject().getSCorpusGraphs().get(0));
		
		assertEquals(primitiveOrder.size(), testOrders.size());
		assertTrue(primitiveOrder.containsAll(testOrders));
		assertTrue(testOrders.containsAll(primitiveOrder));
	}
	
	public static class MyImporter extends PepperImporterImpl{
		protected List<SDocument> expectedSDocuments= null; 
		protected List<SDocument> givenSDocuments= null; 
		/** idle time for waiting with mapping of document-structure**/
		public long idleTime=0;
		
		public MyImporter(String name){
			setName(name);
			givenSDocuments= new Vector<SDocument>();
		}
		@Override
		public void importCorpusStructure(SCorpusGraph corpusGraph) 
		{
			SCorpus sCorpus= SaltFactory.eINSTANCE.createSCorpus();
			if (corpusGraph== null)
				throw new PepperOSGiRunnerException("CorpusGraph was null.");
			corpusGraph.addSNode(sCorpus);
			for (SDocument sDoc: expectedSDocuments){
				corpusGraph.addSDocument(sCorpus, sDoc);
			}
		}
		public PepperMapper createPepperMapper(SElementId sElementId){
			if (sElementId== null)
				throw new PepperOSGiRunnerException("Passed sElementId cannot be null.");
			if (sElementId.getSIdentifiableElement()== null)
				throw new PepperOSGiRunnerException("SIdentifiableElement corresponding to passed sElementId cannot be null.");
			
			if (sElementId.getSIdentifiableElement() instanceof SDocument)
				givenSDocuments.add((SDocument) sElementId.getSIdentifiableElement());
			return(new PepperMapperImpl(){
				public DOCUMENT_STATUS mapSDocument(){
					try {
						getSDocument().setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
						Thread.sleep(idleTime);
					} catch (InterruptedException e) {
						throw new PepperException("Cannot send thread to sleep");
					}
					return(DOCUMENT_STATUS.COMPLETED);
				}
			});
		}
	}
	public static class MyManipulator extends PepperManipulatorImpl{
		protected List<SDocument> givenSDocuments= null;
		protected List<SDocument> deletedSDocuments= null;
		/** idle time for waiting with mapping of document-structure**/
		public long idleTime=0;
		public MyManipulator(String name){
			setName(name);
			givenSDocuments= new Vector<SDocument>();
		}
		public PepperMapper createPepperMapper(SElementId sElementId){
			if (sElementId.getSIdentifiableElement() instanceof SDocument)
				givenSDocuments.add((SDocument) sElementId.getSIdentifiableElement());
			return(new PepperMapperImpl(){
				public DOCUMENT_STATUS mapSDocument(){
					DOCUMENT_STATUS status= DOCUMENT_STATUS.COMPLETED;
					 
					if (deletedSDocuments!= null){
						int idxDeleteDoc= deletedSDocuments.indexOf(getSDocument());
						if (	(idxDeleteDoc!= -1)&&
								(deletedSDocuments.get(idxDeleteDoc).getSCorpusGraph().equals(getSDocument().getSCorpusGraph()))){
							status= DOCUMENT_STATUS.DELETED;
						}
					}
					try {
						Thread.sleep(idleTime);
					} catch (InterruptedException e) {
						throw new PepperException("Cannot send thread to sleep");
					}
					return(status);
				}
			});
		}
	}
	public static class MyExporter extends PepperExporterImpl{
		protected List<SDocument> givenSDocuments= null;
		/** idle time for waiting with mapping of document-structure**/
		public long idleTime=0;
		public MyExporter(String name){
			setName(name);
			givenSDocuments= new Vector<SDocument>();
		}
		public PepperMapper createPepperMapper(SElementId sElementId){
			if (sElementId.getSIdentifiableElement() instanceof SDocument)
				givenSDocuments.add((SDocument) sElementId.getSIdentifiableElement());
			return(new PepperMapperImpl(){
				public DOCUMENT_STATUS mapSDocument(){
					try {
						Thread.sleep(idleTime);
					} catch (InterruptedException e) {
						throw new PepperException("Cannot send thread to sleep");
					}
					return(DOCUMENT_STATUS.COMPLETED);
				}
			});
		}
	}
	/**
	 * Tests the entire conversion process using a very small workflow description containing:
	 * <ul>
	 * <li> one importer</li>
	 * <li> one exporter</li>
	 * </ul>
	 */
	@Test
	public void testConvert_1IM_1EX(){
		List<SDocument> expectedSDocuments= new Vector<SDocument>();
		for (int i= 0; i< 10; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			expectedSDocuments.add(sDoc);
		}
		
		Step importStep= new Step("im1");
		importStep.setCorpusDesc(new CorpusDesc());
		importStep.getCorpusDesc().setCorpusPath(URI.createFileURI(System.getProperty("java.io.tmpdir")));			
		MyImporter myImporter= new MyImporter("myImporter");
		myImporter.setResources(dummyResourceURI);
		myImporter.expectedSDocuments= expectedSDocuments;
		importStep.setPepperModule(myImporter);
		getFixture().addStep(importStep);
		
		Step exportStep= new Step("ex1");
		MyExporter myExporter= new MyExporter("myExporter");
		myExporter.setResources(dummyResourceURI);
		exportStep.setPepperModule(myExporter);
		getFixture().addStep(exportStep);
		
		getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
		getFixture().convert();
		
		assertTrue(expectedSDocuments.containsAll(myImporter.givenSDocuments));
		assertTrue(myImporter.givenSDocuments.containsAll(expectedSDocuments));
		assertTrue(expectedSDocuments.containsAll(myExporter.givenSDocuments));
		assertTrue(myExporter.givenSDocuments.containsAll(expectedSDocuments));
	}
	/**
	 * Tests the entire conversion process using a very small workflow description containing:
	 * <ul>
	 * <li> one importer - 10 documents</li>
	 * <li> one importer - 5 documents</li>
	 * <li> one exporter - 15 documents</li>
	 * </ul>
	 */
	@Test
	public void testConvert_2IM_1EX(){
		List<SDocument> expectedSDocuments1= new Vector<SDocument>();
		for (int i= 0; i< 10; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			expectedSDocuments1.add(sDoc);
		}
		List<SDocument> expectedSDocuments2= new Vector<SDocument>();
		for (int i= 0; i< 5; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			expectedSDocuments2.add(sDoc);
		}
		
		Step importStep1= new Step("im1");
		importStep1.setCorpusDesc(new CorpusDesc());
		importStep1.getCorpusDesc().setCorpusPath(URI.createFileURI(System.getProperty("java.io.tmpdir")));			
		MyImporter myImporter1= new MyImporter("myImporter1");
		myImporter1.setResources(dummyResourceURI);
		myImporter1.expectedSDocuments= expectedSDocuments1;
		importStep1.setPepperModule(myImporter1);
		getFixture().addStep(importStep1);
		
		Step importStep2= new Step("im2");
		importStep2.setCorpusDesc(new CorpusDesc());
		importStep2.getCorpusDesc().setCorpusPath(URI.createFileURI(System.getProperty("java.io.tmpdir")));			
		MyImporter myImporter2= new MyImporter("myImporter2");
		myImporter2.setResources(dummyResourceURI);
		myImporter2.expectedSDocuments= expectedSDocuments2;
		importStep2.setPepperModule(myImporter2);
		getFixture().addStep(importStep2);
		
		Step exportStep= new Step("ex1");
		MyExporter myExporter= new MyExporter("myExporter");
		myExporter.setResources(dummyResourceURI);
		exportStep.setPepperModule(myExporter);
		getFixture().addStep(exportStep);
		
		getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
		getFixture().convert();
		
		assertEquals(expectedSDocuments1.size(), myImporter1.givenSDocuments.size());
		assertTrue(expectedSDocuments1.containsAll(myImporter1.givenSDocuments));
		assertTrue(myImporter1.givenSDocuments.containsAll(expectedSDocuments1));
		assertEquals(expectedSDocuments2.size(), myImporter2.givenSDocuments.size());
		assertTrue(expectedSDocuments2.containsAll(myImporter2.givenSDocuments));
		assertTrue(myImporter2.givenSDocuments.containsAll(expectedSDocuments2));
		
		List<SDocument> expectedSDocuments= new Vector<SDocument>();
		expectedSDocuments.addAll(expectedSDocuments1);
		expectedSDocuments.addAll(expectedSDocuments2);
		assertEquals(expectedSDocuments.size(), myExporter.givenSDocuments.size());
		assertTrue(expectedSDocuments.containsAll(myExporter.givenSDocuments));
		assertTrue(myExporter.givenSDocuments.containsAll(expectedSDocuments));
	}
	
	/**
	 * Tests the entire conversion process using a very small workflow description containing:
	 * <ul>
	 * <li> one importer - 10 documents</li>
	 * <li> one importer - 5 documents</li>
	 * <li> one exporter - 15 documents</li>
	 * <li> one exporter - 15 documents</li>
	 * </ul>
	 */
	@Test
	public void testConvert_2IM_2EX(){
		List<SDocument> expectedSDocuments1= new Vector<SDocument>();
		for (int i= 0; i< 10; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			expectedSDocuments1.add(sDoc);
		}
		List<SDocument> expectedSDocuments2= new Vector<SDocument>();
		for (int i= 0; i< 5; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			expectedSDocuments2.add(sDoc);
		}
		
		Step importStep1= new Step("im1");
		importStep1.setCorpusDesc(new CorpusDesc());
		importStep1.getCorpusDesc().setCorpusPath(URI.createFileURI(System.getProperty("java.io.tmpdir")));			
		MyImporter myImporter1= new MyImporter("myImporter1");
		myImporter1.setResources(dummyResourceURI);
		myImporter1.expectedSDocuments= expectedSDocuments1;
		importStep1.setPepperModule(myImporter1);
		getFixture().addStep(importStep1);
		
		Step importStep2= new Step("im2");
		importStep2.setCorpusDesc(new CorpusDesc());
		importStep2.getCorpusDesc().setCorpusPath(URI.createFileURI(System.getProperty("java.io.tmpdir")));			
		MyImporter myImporter2= new MyImporter("myImporter2");
		myImporter2.setResources(dummyResourceURI);
		myImporter2.expectedSDocuments= expectedSDocuments2;
		importStep2.setPepperModule(myImporter2);
		getFixture().addStep(importStep2);
		
		Step exportStep1= new Step("ex1");
		MyExporter myExporter1= new MyExporter("myExporter1");
		myExporter1.setResources(dummyResourceURI);
		exportStep1.setPepperModule(myExporter1);
		getFixture().addStep(exportStep1);
		
		Step exportStep2= new Step("ex2");
		MyExporter myExporter2= new MyExporter("myExporter2");
		myExporter2.setResources(dummyResourceURI);
		exportStep2.setPepperModule(myExporter2);
		getFixture().addStep(exportStep2);
		
		getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
		getFixture().convert();
		
		assertEquals(expectedSDocuments1.size(), myImporter1.givenSDocuments.size());
		assertTrue(expectedSDocuments1.containsAll(myImporter1.givenSDocuments));
		assertTrue(myImporter1.givenSDocuments.containsAll(expectedSDocuments1));
		assertEquals(expectedSDocuments2.size(), myImporter2.givenSDocuments.size());
		assertTrue(expectedSDocuments2.containsAll(myImporter2.givenSDocuments));
		assertTrue(myImporter2.givenSDocuments.containsAll(expectedSDocuments2));
		
		List<SDocument> expectedSDocuments= new Vector<SDocument>();
		expectedSDocuments.addAll(expectedSDocuments1);
		expectedSDocuments.addAll(expectedSDocuments2);
		assertEquals(expectedSDocuments.size(), myExporter1.givenSDocuments.size());
		assertTrue(expectedSDocuments.containsAll(myExporter1.givenSDocuments));
		assertTrue(myExporter1.givenSDocuments.containsAll(expectedSDocuments));
		assertEquals(expectedSDocuments.size(), myExporter2.givenSDocuments.size());
		assertTrue(expectedSDocuments.containsAll(myExporter2.givenSDocuments));
		assertTrue(myExporter2.givenSDocuments.containsAll(expectedSDocuments));
	}

	private URI dummyResourceURI= URI.createFileURI(new File(System.getProperty("java.io.tmpdir")).getAbsolutePath());
	/**
	 * Tests the entire conversion process using a very small workflow description containing:
	 * <ul>
	 * <li> one importer - 10 documents</li>
	 * <li> one importer - 5 documents</li>
	 * <li> one manipulator - 15 documents</li>
	 * <li> one manipulator - 10 documents</li>
	 * <li> one exporter - 10 documents</li>
	 * <li> one exporter - 10 documents</li>
	 * </ul>
	 */
	@Test
	public void testConvert_2IM_1MA_1EX(){
		List<SDocument> expectedSDocuments1= new Vector<SDocument>();
		for (int i= 0; i< 10; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			expectedSDocuments1.add(sDoc);
		}
		List<SDocument> expectedSDocuments2= new Vector<SDocument>();
		for (int i= 0; i< 5; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			expectedSDocuments2.add(sDoc);
		}
		
		List<SDocument> expectedSDocuments= new Vector<SDocument>();
		expectedSDocuments.addAll(expectedSDocuments1);
		expectedSDocuments.addAll(expectedSDocuments2);
		
		Step importStep1= new Step("im1");
		importStep1.setCorpusDesc(new CorpusDesc());
		importStep1.getCorpusDesc().setCorpusPath(URI.createFileURI(System.getProperty("java.io.tmpdir")));			
		MyImporter myImporter1= new MyImporter("myImporter1");
		myImporter1.setResources(dummyResourceURI);
		myImporter1.expectedSDocuments= expectedSDocuments1;
		importStep1.setPepperModule(myImporter1);
		getFixture().addStep(importStep1);
		
		Step importStep2= new Step("im2");
		importStep2.setCorpusDesc(new CorpusDesc());
		importStep2.getCorpusDesc().setCorpusPath(URI.createFileURI(System.getProperty("java.io.tmpdir")));			
		MyImporter myImporter2= new MyImporter("myImporter2");
		myImporter2.expectedSDocuments= expectedSDocuments2;
		myImporter2.setResources(dummyResourceURI);
		importStep2.setPepperModule(myImporter2);
		getFixture().addStep(importStep2);
		
		Step manipulationStep1= new Step("ma1");
		MyManipulator myManipulator1= new MyManipulator("myManipulator1");
		myManipulator1.setResources(dummyResourceURI);
		manipulationStep1.setPepperModule(myManipulator1);
		getFixture().addStep(manipulationStep1);
		
		Step exportStep1= new Step("ex1");
		MyExporter myExporter1= new MyExporter("myExporter1");
		myExporter1.setResources(dummyResourceURI);
		exportStep1.setPepperModule(myExporter1);
		getFixture().addStep(exportStep1);
				
		getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
		getFixture().convert();
		
		//import phase
		assertTrue(expectedSDocuments1.containsAll(myImporter1.givenSDocuments));
		assertTrue(myImporter1.givenSDocuments.containsAll(expectedSDocuments1));
		assertTrue(expectedSDocuments2.containsAll(myImporter2.givenSDocuments));
		assertTrue(myImporter2.givenSDocuments.containsAll(expectedSDocuments2));
		
		//manipulation step 1
		assertEquals(expectedSDocuments.size(), myManipulator1.givenSDocuments.size());
		assertTrue(expectedSDocuments.containsAll(myManipulator1.givenSDocuments));
		assertTrue(myManipulator1.givenSDocuments.containsAll(expectedSDocuments));
		
		//export phase
		assertEquals(expectedSDocuments.size(), myExporter1.givenSDocuments.size());
		assertTrue(expectedSDocuments.containsAll(myExporter1.givenSDocuments));
		assertTrue("expected: "+expectedSDocuments+", but was "+myExporter1.givenSDocuments, myExporter1.givenSDocuments.containsAll(expectedSDocuments));
	}
	
	
	/**
	 * Tests the entire conversion process using a very small workflow description containing:
	 * <ul>
	 * <li> one importer - 10 documents</li>
	 * <li> one importer - 5 documents</li>
	 * <li> one manipulator - 15 documents</li>
	 * <li> one manipulator - 10 documents</li>
	 * <li> one exporter - 10 documents</li>
	 * </ul>
	 */
	@Test
	public void testConvert_2IM_2MA_1EX(){
		List<SDocument> expectedSDocuments1= new Vector<SDocument>();
		for (int i= 0; i< 10; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			expectedSDocuments1.add(sDoc);
		}
		List<SDocument> expectedSDocuments2= new Vector<SDocument>();
		for (int i= 0; i< 5; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			expectedSDocuments2.add(sDoc);
		}
		
		List<SDocument> expectedSDocuments= new Vector<SDocument>();
		expectedSDocuments.addAll(expectedSDocuments1);
		expectedSDocuments.addAll(expectedSDocuments2);
		
		Step importStep1= new Step("im1");
		importStep1.setCorpusDesc(new CorpusDesc());
		importStep1.getCorpusDesc().setCorpusPath(URI.createFileURI(System.getProperty("java.io.tmpdir")));			
		MyImporter myImporter1= new MyImporter("myImporter1");
		myImporter1.setResources(dummyResourceURI);
		myImporter1.expectedSDocuments= expectedSDocuments1;
		importStep1.setPepperModule(myImporter1);
		getFixture().addStep(importStep1);
		
		Step importStep2= new Step("im2");
		importStep2.setCorpusDesc(new CorpusDesc());
		importStep2.getCorpusDesc().setCorpusPath(URI.createFileURI(System.getProperty("java.io.tmpdir")));			
		MyImporter myImporter2= new MyImporter("myImporter2");
		myImporter2.setResources(dummyResourceURI);
		myImporter2.expectedSDocuments= expectedSDocuments2;
		importStep2.setPepperModule(myImporter2);
		getFixture().addStep(importStep2);
		
		Step manipulationStep1= new Step("ma1");
		MyManipulator myManipulator1= new MyManipulator("myManipulator1");
		myManipulator1.setResources(dummyResourceURI);
		manipulationStep1.setPepperModule(myManipulator1);
		getFixture().addStep(manipulationStep1);
		
		Step manipulationStep2= new Step("ma2");
		MyManipulator myManipulator2= new MyManipulator("myManipulator2");
		myManipulator2.setResources(dummyResourceURI);
		myManipulator2.deletedSDocuments= expectedSDocuments2;
		manipulationStep2.setPepperModule(myManipulator2);
		getFixture().addStep(manipulationStep2);
		
		Step exportStep1= new Step("ex1");
		MyExporter myExporter1= new MyExporter("myExporter1");
		myExporter1.setResources(dummyResourceURI);
		exportStep1.setPepperModule(myExporter1);
		getFixture().addStep(exportStep1);
		
		getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
		getFixture().convert();
		
		//import phase
		assertEquals(expectedSDocuments1.size(), myImporter1.givenSDocuments.size());
		assertTrue(expectedSDocuments1.containsAll(myImporter1.givenSDocuments));
		assertTrue(myImporter1.givenSDocuments.containsAll(expectedSDocuments1));
		assertEquals(expectedSDocuments2.size(), myImporter2.givenSDocuments.size());
		assertTrue(expectedSDocuments2.containsAll(myImporter2.givenSDocuments));
		assertTrue(myImporter2.givenSDocuments.containsAll(expectedSDocuments2));
		
		//manipulation step 1
		assertEquals(expectedSDocuments.size(), myManipulator1.givenSDocuments.size());
		assertTrue(expectedSDocuments.containsAll(myManipulator1.givenSDocuments));
		assertTrue(myManipulator1.givenSDocuments.containsAll(expectedSDocuments));
		//manipulation step 2
		assertEquals(expectedSDocuments.size(), myManipulator2.givenSDocuments.size());
		assertTrue(expectedSDocuments.containsAll(myManipulator2.givenSDocuments));
		assertTrue(myManipulator2.givenSDocuments.containsAll(expectedSDocuments1));
		
		//export phase
		assertEquals(expectedSDocuments1.size(), myExporter1.givenSDocuments.size());
		assertTrue(expectedSDocuments1.containsAll(myExporter1.givenSDocuments));
		assertTrue("expected: "+expectedSDocuments1+", but was "+myExporter1.givenSDocuments, myExporter1.givenSDocuments.containsAll(expectedSDocuments1));
	}
	
	/**
	 * Tests the entire conversion process using a very small workflow description and 
	 * checks, that only 2 documents are processed at the same time.
	 */
	@Test
	public void testConvert_CurrentNumberOfDocuments(){
		PepperConfiguration conf= new PepperConfiguration();
		conf.put(PepperConfiguration.PROP_MAX_AMOUNT_OF_SDOCUMENTS, "2");
		getFixture().setConfiguration(conf);
		
		Step importStep1= new Step("im1");

		List<SDocument> expectedDocuments= new Vector<SDocument>();
		for (int i=0; i< 20; i++){
			expectedDocuments.add(SaltFactory.eINSTANCE.createSDocument());
		}
		MyImporter myImporter= new MyImporter("myImporter");
		myImporter.setResources(dummyResourceURI);
		myImporter.idleTime= 20;
		myImporter.expectedSDocuments= expectedDocuments;
		importStep1.setPepperModule(myImporter);
		importStep1.setCorpusDesc(new CorpusDesc());
		importStep1.getCorpusDesc().setCorpusPath(URI.createFileURI(System.getProperty("java.io.tmpdir")));			
		
		
		getFixture().addStep(importStep1);
		
		
		Step manipulationStep1= new Step("ma1");

		MyManipulator myManipulator= new MyManipulator("myManipulator");
		myManipulator.setResources(dummyResourceURI);
		myManipulator.idleTime= 25;
		manipulationStep1.setPepperModule(myManipulator);
		getFixture().addStep(manipulationStep1);
		
		Step exportStep1= new Step("ex1");

		MyExporter myExporter= new MyExporter("myExporter");
		myExporter.setResources(dummyResourceURI);
		myExporter.idleTime= 30;
		exportStep1.setPepperModule(myExporter);
		getFixture().addStep(exportStep1);
		
		getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
		
		Thread watchDog= new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!JOB_STATUS.ENDED.equals(getFixture().getStatus())){
					assertTrue("Current number of documents is not correct. Expected: less than '2', but was '"+getFixture().getNumOfActiveDocuments()+"'. ", getFixture().getNumOfActiveDocuments()<= 2);
					try {
						Thread.sleep(10l);
					} catch (InterruptedException e) {
						throw new PepperException("Error in test: ", e);
					}
				}
			}
		});
		watchDog.setUncaughtExceptionHandler(this);
		watchDog.start();
		
		getFixture().convert();
		assertTrue(!exception);
		
		assertEquals(expectedDocuments.size(), myImporter.givenSDocuments.size());
		assertTrue(expectedDocuments.containsAll(myImporter.givenSDocuments));
		assertTrue(myImporter.givenSDocuments.containsAll(expectedDocuments));
		
		assertEquals(expectedDocuments.size(), myManipulator.givenSDocuments.size());
		assertTrue(expectedDocuments.containsAll(myManipulator.givenSDocuments));
		assertTrue(myManipulator.givenSDocuments.containsAll(expectedDocuments));
		
		assertEquals(expectedDocuments.size(), myExporter.givenSDocuments.size());
		assertTrue(expectedDocuments.containsAll(myExporter.givenSDocuments));
		assertTrue(myExporter.givenSDocuments.containsAll(expectedDocuments));
	}

	private boolean exception= false;
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		exception= true;
		e.printStackTrace();
		throw new PepperException("An exception occured in some tests '"+t.getName()+"'. ", e);
	}
	
	@Test
	public void testLoad_PepperParams() throws XMLStreamException, IOException{
		File tmpFolder= PepperUtil.getTempTestFile("pepperJobTest");
		tmpFolder.mkdirs();
		File propFile= new File(tmpFolder.getAbsolutePath()+"/test.properties"); 
		OutputStream propStream = new FileOutputStream(propFile);
		
		Properties props= new Properties();
		props.put("prop1", "val1");
		props.put("prop2", "val2");
		props.store(propStream, "");
		
		URI corpPath= URI.createFileURI(new File("/anyPath/").getAbsolutePath());
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		XMLOutputFactory o= XMLOutputFactory.newFactory();
		XMLStreamWriter xmlWriter= o.createXMLStreamWriter(outStream);
		xmlWriter.writeStartDocument();
		xmlWriter.writeStartElement(PepperParamsReader.PREFIX_PEPPERPARAMS, PepperParamsReader.ELEMENT_PEPPERPARAMS, PepperParamsReader.NS_PEPPERPARAMS);
			xmlWriter.writeAttribute(PepperParamsReader.PREFIX_XMI, PepperParamsReader.NS_XMI, "version", "2.0");
			xmlWriter.writeStartElement(PepperParamsReader.ELEMENT_PEPPER_JOB_PARAMS);
				xmlWriter.writeAttribute(PepperParamsReader.ATT_ID, "anyId");
				xmlWriter.writeStartElement(PepperParamsReader.ELEMENT_IMPORTER_PARAMS);
					xmlWriter.writeAttribute(PepperParamsReader.ATT_MODULE_NAME, "importer1");
					xmlWriter.writeAttribute(PepperParamsReader.ATT_SOURCE_PATH, corpPath.toString());
					xmlWriter.writeAttribute(PepperParamsReader.ATT_SPECIAL_PARAMS, URI.createFileURI(propFile.getAbsolutePath()).toString());
				xmlWriter.writeEndElement();
				xmlWriter.writeStartElement(PepperParamsReader.ELEMENT_IMPORTER_PARAMS);
					xmlWriter.writeAttribute(PepperParamsReader.ATT_FORMAT_NAME, "anyFormat");
					xmlWriter.writeAttribute(PepperParamsReader.ATT_FORMAT_VERSION, "anyFormatVersion");
					xmlWriter.writeAttribute(PepperParamsReader.ATT_SOURCE_PATH, corpPath.toString());
					xmlWriter.writeAttribute(PepperParamsReader.ATT_SPECIAL_PARAMS, URI.createFileURI(propFile.getAbsolutePath()).toString());
				xmlWriter.writeEndElement();
				xmlWriter.writeStartElement(PepperParamsReader.ELEMENT_MODULE_PARAMS);
					xmlWriter.writeAttribute(PepperParamsReader.ATT_MODULE_NAME, "manipulator1");
					xmlWriter.writeAttribute(PepperParamsReader.ATT_SPECIAL_PARAMS, URI.createFileURI(propFile.getAbsolutePath()).toString());
				xmlWriter.writeEndElement();
				xmlWriter.writeStartElement(PepperParamsReader.ELEMENT_EXPORTER_PARAMS);
					xmlWriter.writeAttribute(PepperParamsReader.ATT_MODULE_NAME, "exporter1");
					xmlWriter.writeAttribute(PepperParamsReader.ATT_DEST_PATH, corpPath.toString());
				xmlWriter.writeAttribute(PepperParamsReader.ATT_SPECIAL_PARAMS, URI.createFileURI(propFile.getAbsolutePath()).toString());
			xmlWriter.writeEndElement();
			xmlWriter.writeStartElement(PepperParamsReader.ELEMENT_EXPORTER_PARAMS);
				xmlWriter.writeAttribute(PepperParamsReader.ATT_FORMAT_NAME, "anyFormat");
				xmlWriter.writeAttribute(PepperParamsReader.ATT_FORMAT_VERSION, "anyFormatVersion");
				xmlWriter.writeAttribute(PepperParamsReader.ATT_DEST_PATH, corpPath.toString());
				xmlWriter.writeAttribute(PepperParamsReader.ATT_SPECIAL_PARAMS, URI.createFileURI(propFile.getAbsolutePath()).toString());
			xmlWriter.writeEndElement();
		xmlWriter.writeEndElement();
		xmlWriter.writeEndDocument();
		
		File outFile= new File(tmpFolder.getAbsolutePath()+"/test.pepperParams"); 
		OutputStream outputStream = new FileOutputStream(outFile);
		outStream.writeTo(outputStream);
		//to shut off, checking if module exists in module resolver
		isLoadTest= true;
		getFixture().setModuleResolver(new ModuleResolverImpl());
		
		getFixture().load(URI.createFileURI(outFile.getAbsolutePath()));
		
		assertEquals(5, getFixture().getStepDescs().size());
		assertNotNull(getFixture().getStepDescs().get(0));
		
		assertEquals("importer1", getFixture().getStepDescs().get(0).getName());
		assertEquals(MODULE_TYPE.IMPORTER, getFixture().getStepDescs().get(0).getModuleType());
		assertEquals(corpPath, getFixture().getStepDescs().get(0).getCorpusDesc().getCorpusPath());
		assertNotNull(getFixture().getStepDescs().get(0).getProps());
		assertEquals(props.size(), getFixture().getStepDescs().get(0).getProps().size());
		assertEquals(props, getFixture().getStepDescs().get(0).getProps());
		
		assertEquals("anyFormat", getFixture().getStepDescs().get(1).getCorpusDesc().getFormatDesc().getFormatName());
		assertEquals("anyFormatVersion", getFixture().getStepDescs().get(1).getCorpusDesc().getFormatDesc().getFormatVersion());
		assertEquals(corpPath, getFixture().getStepDescs().get(1).getCorpusDesc().getCorpusPath());
		assertEquals(MODULE_TYPE.IMPORTER, getFixture().getStepDescs().get(1).getModuleType());
		assertNotNull(getFixture().getStepDescs().get(1).getProps());
		assertEquals(props.size(), getFixture().getStepDescs().get(1).getProps().size());
		assertEquals(props, getFixture().getStepDescs().get(1).getProps());
		
		assertEquals("manipulator1", getFixture().getStepDescs().get(2).getName());
		assertEquals(MODULE_TYPE.MANIPULATOR, getFixture().getStepDescs().get(2).getModuleType());
		assertNotNull(getFixture().getStepDescs().get(2).getProps());
		assertEquals(props.size(), getFixture().getStepDescs().get(2).getProps().size());
		assertEquals(props, getFixture().getStepDescs().get(2).getProps());
		
		assertEquals("exporter1", getFixture().getStepDescs().get(3).getName());
		assertEquals(MODULE_TYPE.EXPORTER, getFixture().getStepDescs().get(3).getModuleType());
		assertEquals(corpPath, getFixture().getStepDescs().get(3).getCorpusDesc().getCorpusPath());
		assertNotNull(getFixture().getStepDescs().get(3).getProps());
		assertEquals(props.size(), getFixture().getStepDescs().get(3).getProps().size());
		assertEquals(props, getFixture().getStepDescs().get(3).getProps());
		
		assertEquals("anyFormat", getFixture().getStepDescs().get(4).getCorpusDesc().getFormatDesc().getFormatName());
		assertEquals("anyFormatVersion", getFixture().getStepDescs().get(4).getCorpusDesc().getFormatDesc().getFormatVersion());
		assertEquals(corpPath, getFixture().getStepDescs().get(4).getCorpusDesc().getCorpusPath());
		assertEquals(MODULE_TYPE.EXPORTER, getFixture().getStepDescs().get(4).getModuleType());
		assertNotNull(getFixture().getStepDescs().get(4).getProps());
		assertEquals(props.size(), getFixture().getStepDescs().get(4).getProps().size());
		assertEquals(props, getFixture().getStepDescs().get(4).getProps());
	}
	/** switch to shut off adding of step, when loading PepperParams**/
	private boolean isLoadTest= false;
	public void addStep(Step step){
		if (!isLoadTest){
			super.addStep(step);
		}
	}
	/**
	 * Checks if storing a job to xml file works correctly.
	 * Stores a job containing an importer, manipulator and exporter. Each having some customization properties.
	 * @throws IOException 
	 * @throws SAXException 
	 */
	@Test
	public void save_test() throws SAXException, IOException{
		ModuleResolver resolver= new ModuleResolverImpl(){
			public PepperModule getPepperModule(StepDesc stepDesc){
				PepperModule manipulator= new PepperManipulatorImpl() {};
				return(manipulator);
			}
		};
		getFixture().setModuleResolver(resolver);
		
		//importer declared by formatName and formatVersion
		StepDesc step1= new StepDesc();
		step1.setModuleType(MODULE_TYPE.IMPORTER);
		step1.getCorpusDesc().setCorpusPath(URI.createFileURI("/somewhere/"));
		step1.getCorpusDesc().getFormatDesc().setFormatName("anyFormat");
		step1.getCorpusDesc().getFormatDesc().setFormatVersion("1.0");
		step1.setProps(new Properties());
		step1.getProps().put("prop1", "val1");
		step1.getProps().put("prop2", "5");
		getFixture().addStepDesc(step1);
		
		//manipulator declared by name
		StepDesc step2= new StepDesc();
		step2.setModuleType(MODULE_TYPE.MANIPULATOR);
		step2.setName("anyManipulator");
		step2.setProps(new Properties());
		step2.getProps().put("prop1", "val1");
		step2.getProps().put("prop2", "5");
		getFixture().addStepDesc(step2);
		
		//exporter declared by name
		StepDesc step3= new StepDesc();
		step3.setModuleType(MODULE_TYPE.EXPORTER);
		step3.getCorpusDesc().setCorpusPath(URI.createFileURI("/somewhere/"));
		step3.setName("anyExporter");
		step3.setProps(new Properties());
		step3.getProps().put("prop1", "val1");
		step3.getProps().put("prop2", "5");
		getFixture().addStepDesc(step3);
		
		File file = new File(PepperUtil.getTempTestFile("pepperJobImplTest").getAbsolutePath()+"/test_save."+PepperUtil.FILE_ENDING_PEPPER);
		
		getFixture().save(URI.createFileURI(file.getAbsolutePath()));
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setValidating(false);
		try {
			dbf.setFeature("http://xml.org/sax/features/namespaces", false);
			dbf.setFeature("http://xml.org/sax/features/validation", false);
			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		XMLUnit.setTestDocumentBuilderFactory(dbf);
		XMLUnit.setControlDocumentBuilderFactory(dbf);

		File goldFile= new File(PepperModuleTest.getTestResources()+"/workflowDescription/save_test.pepper");
		Reader goldReader = new InputStreamReader(new FileInputStream(goldFile.getAbsolutePath()), "UTF-8");
		Reader fixtureReader = new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF-8");
		Diff diff = XMLUnit.compareXML(goldReader, fixtureReader);
		if (!diff.identical()) {
			System.out.println(goldFile.getAbsolutePath() + " <> " + file.getAbsolutePath());
			System.out.println(diff);
			fail();
		}
	}
}

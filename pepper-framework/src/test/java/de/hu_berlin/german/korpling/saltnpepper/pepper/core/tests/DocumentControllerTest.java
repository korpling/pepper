package de.hu_berlin.german.korpling.saltnpepper.pepper.core.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.DocumentControllerImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleControllerImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.DocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.*;

@RunWith(JUnit4.class)
public class DocumentControllerTest extends DocumentControllerImpl{

	protected DocumentController fixture = null;

	protected void setFixture(DocumentController fixture) {
		this.fixture = fixture;
	}

	protected DocumentController getFixture() {
		return fixture;
	}

	@Before
	public void setUp() throws Exception {
		setFixture(new DocumentControllerImpl());
	}

	@After
	public void tearDown() throws Exception {
		setFixture(null);
	}
	
	/** Tests if to string method resturns a value**/
	@Test
	public void testToString(){
		assertNotNull(getFixture().toString());
	}
	
	/** 
	 * Tests the adding of several module controllers and checks if they are present than.
	 */
	@Test
	public void testAddGetModuleControllers(){
		List<ModuleControllerImpl> moduleControllers= new Vector<ModuleControllerImpl>();
		for (int i= 0; i< 10;i++){
			ModuleControllerImpl moduleController= new ModuleControllerImpl("c_"+i);
			getFixture().addModuleControllers(moduleController);
			moduleControllers.add(moduleController);
		}
		assertTrue(moduleControllers.containsAll(getFixture().getModuleControllers()));
	}
	/**
	 * Tests if set {@link SDocument} is returned again.
	 */
	@Test
	public void testSetGetSDocument(){
		SDocument sDocument= SaltFactory.eINSTANCE.createSDocument();
		getFixture().setSDocument(sDocument);
		assertEquals(sDocument, getFixture().getSDocument());
	}
//	/**
//	 * Tests if a global id was created correctly
//	 */
//	@Test
//	public void testGlobalId(){
//		getFixture().setSDocument(SaltFactory.eINSTANCE.createSDocument());
//		assertEquals("", getFixture().getGlobalId());
//		SCorpusGraph sCorpusGraph= SaltFactory.eINSTANCE.createSCorpusGraph();
//		sCorpusGraph.addSNode(getFixture().getSDocument());
//		assertEquals("salt:doc1", getFixture().getGlobalId());
//	}
	
	/**
	 * Tests the inner class {@link DetailedStatus} of {@link DocumentControllerImpl}. 
	 * @throws InterruptedException 
	 */
	@Test
	public void testDetailedStatus() throws InterruptedException{
		DetailedStatus detailedStatus= new DetailedStatus();
		assertEquals(DOCUMENT_STATUS.NOT_STARTED, detailedStatus.getStatus());
		assertNull(detailedStatus.getProcessingTime());
		detailedStatus.setStatus(DOCUMENT_STATUS.IN_PROGRESS);
		Long pTime= detailedStatus.getProcessingTime();
		Thread.sleep(10);
		assertNotNull(pTime);
		detailedStatus.setStatus(DOCUMENT_STATUS.COMPLETED);
		assertNotEquals(pTime, detailedStatus.getProcessingTime());
		assertTrue(detailedStatus.getProcessingTime()> pTime);
		pTime= detailedStatus.getProcessingTime();
		assertEquals(pTime, detailedStatus.getProcessingTime());
	}
	
	/**
	 * Tests the method {@link DocumentControllerImpl#updateStatus(String, DOCUMENT_STATUS)} for a list of 5 {@link ModuleControllerImpl}
	 * Also tests method {@link DocumentControllerImpl#getProgress()}.
	 * objects.
	 */
	@Test
	public void testUpdateStatus(){
		List<ModuleControllerImpl> moduleControllers= new Vector<ModuleControllerImpl>();
		for (int i= 0; i< 5;i++){
			ModuleControllerImpl moduleController= new ModuleControllerImpl("c_"+i);
			getFixture().addModuleControllers(moduleController);
			moduleControllers.add(moduleController);
		}
		getFixture().setSDocument(SaltFactory.eINSTANCE.createSDocument());
		
		assertTrue(new Double(0).equals(getFixture().getProgress()));
		getFixture().updateStatus(moduleControllers.get(0).getId(), DOCUMENT_STATUS.IN_PROGRESS);
		assertTrue(new Double(0).equals(getFixture().getProgress()));
		assertEquals(DOCUMENT_STATUS.IN_PROGRESS, getFixture().getGlobalStatus());
		try {
			getFixture().addModuleControllers(new ModuleControllerImpl("noOne"));
			fail();
		} catch (PepperFWException e){}
		getFixture().updateStatus(moduleControllers.get(0).getId(), DOCUMENT_STATUS.COMPLETED);
		assertTrue(new Double(0.2).equals(getFixture().getProgress()));
		assertEquals(DOCUMENT_STATUS.IN_PROGRESS, getFixture().getGlobalStatus());
		try {
			getFixture().updateStatus(moduleControllers.get(0).getId(), DOCUMENT_STATUS.IN_PROGRESS);
			fail();
		} catch (Exception e) {}
		
		getFixture().updateStatus(moduleControllers.get(1).getId(), DOCUMENT_STATUS.IN_PROGRESS);
		getFixture().updateStatus(moduleControllers.get(2).getId(), DOCUMENT_STATUS.IN_PROGRESS);
		getFixture().updateStatus(moduleControllers.get(3).getId(), DOCUMENT_STATUS.IN_PROGRESS);
		getFixture().updateStatus(moduleControllers.get(4).getId(), DOCUMENT_STATUS.IN_PROGRESS);
		assertEquals(DOCUMENT_STATUS.IN_PROGRESS, getFixture().getGlobalStatus());
		assertTrue(new Double(0.2).equals(getFixture().getProgress()));
		getFixture().updateStatus(moduleControllers.get(1).getId(), DOCUMENT_STATUS.COMPLETED);
		assertTrue(new Double(0.4).equals(getFixture().getProgress()));
		getFixture().updateStatus(moduleControllers.get(2).getId(), DOCUMENT_STATUS.COMPLETED);
		//exception because of division problem
		assertTrue(new Double(0.6)<getFixture().getProgress() && (new Double(0.61)>getFixture().getProgress()));
		getFixture().updateStatus(moduleControllers.get(3).getId(), DOCUMENT_STATUS.COMPLETED);
		assertTrue(new Double(0.8).equals(getFixture().getProgress()));
		getFixture().updateStatus(moduleControllers.get(4).getId(), DOCUMENT_STATUS.COMPLETED);
		assertEquals(DOCUMENT_STATUS.COMPLETED, getFixture().getGlobalStatus());
		assertTrue(new Double(1).equals(getFixture().getProgress()));
	}
	
	/**
	 * Tests the method {@link DocumentControllerImpl#updateStatus(String, DOCUMENT_STATUS)}, if exception
	 * throwing does work for corrupt parameters.
	 * objects.
	 */
	@Test
	public void testUpdateStatus_PARAMS(){
		try{
			getFixture().updateStatus(null, DOCUMENT_STATUS.IN_PROGRESS);
			fail();
		}catch (PepperFWException e){}
		try{
			getFixture().updateStatus("anyId", null);
			fail();
		}catch (PepperFWException e){}
	}
	
	/**
	 * Tests whether the processing of the number of {@link PepperModule}s currently processing
	 * this {@link DocumentControllerImpl} is correct. 
	 */
	@Test
	public void testGetNumOfProcessingModules(){
		assertEquals(0, getFixture().getNumOfProcessingModules());
		
		getFixture().addModuleControllers(new ModuleControllerImpl("module0"));
		getFixture().addModuleControllers(new ModuleControllerImpl("module1"));
		getFixture().addModuleControllers(new ModuleControllerImpl("module2"));
		getFixture().addModuleControllers(new ModuleControllerImpl("module3"));
		
		getFixture().updateStatus("module0", DOCUMENT_STATUS.IN_PROGRESS);
		assertEquals(1, getFixture().getNumOfProcessingModules());
		getFixture().updateStatus("module0", DOCUMENT_STATUS.COMPLETED);
		assertEquals(0, getFixture().getNumOfProcessingModules());
		
		
		getFixture().updateStatus("module1", DOCUMENT_STATUS.IN_PROGRESS);
		assertEquals(1, getFixture().getNumOfProcessingModules());
		getFixture().updateStatus("module2", DOCUMENT_STATUS.IN_PROGRESS);
		assertEquals(2, getFixture().getNumOfProcessingModules());
		getFixture().updateStatus("module3", DOCUMENT_STATUS.IN_PROGRESS);
		assertEquals(3, getFixture().getNumOfProcessingModules());
		
		getFixture().updateStatus("module2", DOCUMENT_STATUS.COMPLETED);
		assertEquals(2, getFixture().getNumOfProcessingModules());
		getFixture().updateStatus("module3", DOCUMENT_STATUS.COMPLETED);
		assertEquals(1, getFixture().getNumOfProcessingModules());
		getFixture().updateStatus("module1", DOCUMENT_STATUS.FAILED);
		assertEquals(0, getFixture().getNumOfProcessingModules());
	}
	
	/**
	 * Tests the sleep and awake mechanism, even if modules are currntly processing the document.
	 * @throws IOException 
	 */
	@Test
	public void testSleepAwake() throws IOException{
		try {
			getFixture().sendToSleep();
			fail();
		} catch (PepperFWException e) {
		}
		SDocument sDocument= SaltFactory.eINSTANCE.createSDocument();
		sDocument.setSName("myDocument");
		SampleGenerator.createSDocumentStructure(sDocument);
		getFixture().setSDocument(sDocument);
		getFixture().setLocation(URI.createFileURI(File.createTempFile(sDocument.getSName(), "."+SaltFactory.FILE_ENDING_SALT).getAbsolutePath()));
		
		getFixture().sendToSleep();
		assertTrue(getFixture().isAsleep());
		assertNotNull(getFixture().getSDocument().getSDocumentGraphLocation());
		assertNull(getFixture().getSDocument().getSDocumentGraph());
		
		getFixture().awake();
		assertTrue(!getFixture().isAsleep());
		assertNotNull(getFixture().getSDocument().getSDocumentGraph());
		
		getFixture().addModuleControllers(new ModuleControllerImpl("module0"));
		getFixture().updateStatus("module0", DOCUMENT_STATUS.IN_PROGRESS);
		getFixture().sendToSleep();
		assertTrue(!getFixture().isAsleep());
		getFixture().updateStatus("module0", DOCUMENT_STATUS.COMPLETED);
		getFixture().sendToSleep();
		assertTrue(getFixture().isAsleep());
	}
}
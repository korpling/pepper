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
package org.corpus_tools.pepper.core.tests;

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

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.core.DocumentControllerImpl;
import org.corpus_tools.pepper.core.ModuleControllerImpl;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.modules.DocumentController;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DocumentControllerTest extends DocumentControllerImpl {

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

	/** Tests if to string method resturns a value **/
	@Test
	public void testToString() {
		assertNotNull(getFixture().toString());
	}

	/**
	 * Tests the adding of several module controllers and checks if they are
	 * present than.
	 */
	@Test
	public void testAddGetModuleControllers() {
		List<ModuleControllerImpl> moduleControllers = new Vector<ModuleControllerImpl>();
		for (int i = 0; i < 10; i++) {
			ModuleControllerImpl moduleController = new ModuleControllerImpl("c_" + i);
			getFixture().addModuleControllers(moduleController);
			moduleControllers.add(moduleController);
		}
		assertTrue(moduleControllers.containsAll(getFixture().getModuleControllers()));
	}

	/**
	 * Tests if set {@link SDocument} is returned again.
	 */
	@Test
	public void testSetGetSDocument() {
		SDocument sDocument = SaltFactory.createSDocument();
		getFixture().setDocument(sDocument);
		assertEquals(sDocument, getFixture().getDocument());
	}

	// /**
	// * Tests if a global id was created correctly
	// */
	// @Test
	// public void testGlobalId(){
	// getFixture().setDocument(SaltFactory.createSDocument());
	// assertEquals("", getFixture().getGlobalId());
	// SCorpusGraph sCorpusGraph= SaltFactory.createSCorpusGraph();
	// sCorpusGraph.addNode(getFixture().getDocument());
	// assertEquals("salt:doc1", getFixture().getGlobalId());
	// }

	/**
	 * Tests the inner class {@link DetailedStatus} of
	 * {@link DocumentControllerImpl}.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testDetailedStatus() throws InterruptedException {
		DetailedStatus detailedStatus = new DetailedStatus();
		assertEquals(DOCUMENT_STATUS.NOT_STARTED, detailedStatus.getStatus());
		assertNull(detailedStatus.getProcessingTime());
		detailedStatus.setStatus(DOCUMENT_STATUS.IN_PROGRESS);
		Long pTime = detailedStatus.getProcessingTime();
		Thread.sleep(10);
		assertNotNull(pTime);
		detailedStatus.setStatus(DOCUMENT_STATUS.COMPLETED);
		assertNotEquals(pTime, detailedStatus.getProcessingTime());
		assertTrue(detailedStatus.getProcessingTime() > pTime);
		pTime = detailedStatus.getProcessingTime();
		assertEquals(pTime, detailedStatus.getProcessingTime());
	}

	/**
	 * Tests the method
	 * {@link DocumentControllerImpl#updateStatus(String, DOCUMENT_STATUS)} for
	 * a list of 5 {@link ModuleControllerImpl} Also tests method
	 * {@link DocumentControllerImpl#getProgress()}. objects.
	 */
	@Test
	public void testUpdateStatus() {
		List<ModuleControllerImpl> moduleControllers = new Vector<ModuleControllerImpl>();
		for (int i = 0; i < 5; i++) {
			ModuleControllerImpl moduleController = new ModuleControllerImpl("c_" + i);
			getFixture().addModuleControllers(moduleController);
			moduleControllers.add(moduleController);
		}
		getFixture().setDocument(SaltFactory.createSDocument());

		assertTrue(new Double(0).equals(getFixture().getProgress()));
		getFixture().updateStatus(moduleControllers.get(0), DOCUMENT_STATUS.IN_PROGRESS);
		assertTrue(new Double(0).equals(getFixture().getProgress()));
		assertEquals(DOCUMENT_STATUS.IN_PROGRESS, getFixture().getGlobalStatus());
		try {
			getFixture().addModuleControllers(new ModuleControllerImpl("noOne"));
			fail();
		} catch (PepperFWException e) {
		}
		getFixture().updateStatus(moduleControllers.get(0), DOCUMENT_STATUS.COMPLETED);
		assertTrue(new Double(0.2).equals(getFixture().getProgress()));
		assertEquals(DOCUMENT_STATUS.IN_PROGRESS, getFixture().getGlobalStatus());
		try {
			getFixture().updateStatus(moduleControllers.get(0), DOCUMENT_STATUS.IN_PROGRESS);
			fail();
		} catch (Exception e) {
		}

		getFixture().updateStatus(moduleControllers.get(1), DOCUMENT_STATUS.IN_PROGRESS);
		getFixture().updateStatus(moduleControllers.get(2), DOCUMENT_STATUS.IN_PROGRESS);
		getFixture().updateStatus(moduleControllers.get(3), DOCUMENT_STATUS.IN_PROGRESS);
		getFixture().updateStatus(moduleControllers.get(4), DOCUMENT_STATUS.IN_PROGRESS);
		assertEquals(DOCUMENT_STATUS.IN_PROGRESS, getFixture().getGlobalStatus());
		assertTrue(new Double(0.2).equals(getFixture().getProgress()));
		getFixture().updateStatus(moduleControllers.get(1), DOCUMENT_STATUS.COMPLETED);
		assertTrue(new Double(0.4).equals(getFixture().getProgress()));
		getFixture().updateStatus(moduleControllers.get(2), DOCUMENT_STATUS.COMPLETED);
		// exception because of division problem
		assertTrue(new Double(0.6) < getFixture().getProgress() && (new Double(0.61) > getFixture().getProgress()));
		getFixture().updateStatus(moduleControllers.get(3), DOCUMENT_STATUS.COMPLETED);
		assertTrue(new Double(0.8).equals(getFixture().getProgress()));
		getFixture().updateStatus(moduleControllers.get(4), DOCUMENT_STATUS.COMPLETED);
		assertEquals(DOCUMENT_STATUS.COMPLETED, getFixture().getGlobalStatus());
		assertTrue(new Double(1).equals(getFixture().getProgress()));
	}

	/**
	 * Tests the method
	 * {@link DocumentControllerImpl#updateStatus(String, DOCUMENT_STATUS)}, if
	 * exception throwing does work for corrupt parameters. objects.
	 */
	@Test
	public void testUpdateStatus_PARAMS() {
		try {
			getFixture().updateStatus(null, DOCUMENT_STATUS.IN_PROGRESS);
			fail();
		} catch (PepperFWException e) {
		}
		try {
			getFixture().updateStatus(new ModuleControllerImpl("anyId"), null);
			fail();
		} catch (PepperFWException e) {
		}
	}

	/**
	 * Tests whether the processing of the number of {@link PepperModule}s
	 * currently processing this {@link DocumentControllerImpl} is correct.
	 */
	@Test
	public void testGetNumOfProcessingModules() {
		assertEquals(0, getFixture().getNumOfProcessingModules());

		getFixture().addModuleControllers(new ModuleControllerImpl("module0"));
		getFixture().addModuleControllers(new ModuleControllerImpl("module1"));
		getFixture().addModuleControllers(new ModuleControllerImpl("module2"));
		getFixture().addModuleControllers(new ModuleControllerImpl("module3"));

		getFixture().updateStatus(getFixture().getModuleControllers().get(0), DOCUMENT_STATUS.IN_PROGRESS);
		assertEquals(1, getFixture().getNumOfProcessingModules());
		getFixture().updateStatus(getFixture().getModuleControllers().get(0), DOCUMENT_STATUS.COMPLETED);
		assertEquals(0, getFixture().getNumOfProcessingModules());

		getFixture().updateStatus(getFixture().getModuleControllers().get(1), DOCUMENT_STATUS.IN_PROGRESS);
		assertEquals(1, getFixture().getNumOfProcessingModules());
		getFixture().updateStatus(getFixture().getModuleControllers().get(2), DOCUMENT_STATUS.IN_PROGRESS);
		assertEquals(2, getFixture().getNumOfProcessingModules());
		getFixture().updateStatus(getFixture().getModuleControllers().get(3), DOCUMENT_STATUS.IN_PROGRESS);
		assertEquals(3, getFixture().getNumOfProcessingModules());

		getFixture().updateStatus(getFixture().getModuleControllers().get(2), DOCUMENT_STATUS.COMPLETED);
		assertEquals(2, getFixture().getNumOfProcessingModules());
		getFixture().updateStatus(getFixture().getModuleControllers().get(3), DOCUMENT_STATUS.COMPLETED);
		assertEquals(1, getFixture().getNumOfProcessingModules());
		getFixture().updateStatus(getFixture().getModuleControllers().get(1), DOCUMENT_STATUS.FAILED);
		assertEquals(0, getFixture().getNumOfProcessingModules());
	}

	/**
	 * Tests the sleep and awake mechanism, even if modules are currntly
	 * processing the document.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSleepAwake() throws IOException {
		try {
			getFixture().sendToSleep();
			fail();
		} catch (PepperFWException e) {
		}
		SDocument sDocument = SaltFactory.createSDocument();
		sDocument.setName("myDocument");
		SampleGenerator.createSDocumentStructure(sDocument);
		getFixture().setDocument(sDocument);
		getFixture().setLocation(URI.createFileURI(File.createTempFile(sDocument.getName(), "." + SaltUtil.FILE_ENDING_SALT_XML).getAbsolutePath()));

		getFixture().sendToSleep();
		assertTrue(getFixture().isAsleep());
		assertNotNull(getFixture().getDocument().getDocumentGraphLocation());
		assertNull(getFixture().getDocument().getDocumentGraph());

		getFixture().awake();
		assertTrue(!getFixture().isAsleep());
		assertNotNull(getFixture().getDocument().getDocumentGraph());

		getFixture().addModuleControllers(new ModuleControllerImpl("module0"));
		getFixture().updateStatus(getFixture().getModuleControllers().get(0), DOCUMENT_STATUS.IN_PROGRESS);
		getFixture().sendToSleep();
		assertTrue(!getFixture().isAsleep());
		getFixture().updateStatus(getFixture().getModuleControllers().get(0), DOCUMENT_STATUS.COMPLETED);
		getFixture().sendToSleep();
		assertTrue(getFixture().isAsleep());
	}
}
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
package org.corpus_tools.pepper.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Vector;

import org.corpus_tools.pepper.core.DocumentBus;
import org.corpus_tools.pepper.core.DocumentControllerImpl;
import org.corpus_tools.pepper.core.ModuleControllerImpl;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.impl.PepperModuleImpl;
import org.corpus_tools.pepper.modules.DocumentController;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.testFramework.old.PepperTestUtil;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class ModuleControllerTest {

	private ModuleControllerImpl fixture = null;

	public ModuleControllerImpl getFixture() {
		return fixture;
	}

	public void setFixture(ModuleControllerImpl fixture) {
		this.fixture = fixture;
	}

	private String id = "moduleController1";

	@Before
	public void setUp() {
		this.setFixture(new ModuleControllerImpl(id));
	}

	/** Tests if id is set correctly **/
	@Test
	public void testGetId() {
		assertEquals(id, getFixture().getId());
	}

	private static class PepperModuleSample extends PepperModuleImpl {
		public PepperModuleSample() {
			super("SampleModule");
		}
	}

	@Test
	public void testConstructor() {
		try {
			new ModuleControllerImpl(null);
			fail();
		} catch (PepperFWException e) {
		}
		try {
			new ModuleControllerImpl("");
			fail();
		} catch (PepperFWException e) {
		}
	}

	@Test
	public void testSetGetPepperModule() {
		PepperModule module = new PepperModuleSample();
		assertNull(getFixture().getPepperModule());
		getFixture().setPepperModule(module);
		assertEquals(module, getFixture().getPepperModule());
	}

	@Test
	public void testSetPepperModule_Basic() {
		try {
			getFixture().setPepperModule_basic(null);
		} catch (PepperFWException e) {
		}
		PepperModule module = new PepperModuleSample();
		assertNull(getFixture().getPepperModule());
		getFixture().setPepperModule_basic(module);
		assertEquals(module, getFixture().getPepperModule());
	}

	@Test
	public void testSetGetInputBus() {
		Vector<String> outIds = new Vector<String>();
		outIds.add(getFixture().getId());
		Vector<String> inIds = new Vector<String>();
		inIds.add("nonExistingId");
		DocumentBus bus = new DocumentBus(outIds, inIds);
		getFixture().setInputDocumentBus(bus);
		assertEquals(bus, getFixture().getInputDocumentBus());
	}

	@Test
	public void testSetGetOutputBus() {
		Vector<String> outIds = new Vector<String>();
		outIds.add(getFixture().getId());
		Vector<String> inIds = new Vector<String>();
		inIds.add("nonExistingId");
		DocumentBus bus = new DocumentBus(outIds, inIds);

		getFixture().setOutputDocumentBus(bus);
		assertEquals(bus, getFixture().getOutputDocumentBus());
	}

	private static class SampleModule extends PepperModuleImpl {
		public SampleModule() {
			super("SampleModule");
		}
	}

	/**
	 * Tests if the bidirectional wiring of {@link PepperModule} and
	 * {@link ModuleControllerImpl} works.
	 */
	@Test
	public void testAutomaticWirering_PepperModule() {
		SampleModule module = new SampleModule();
		getFixture().setPepperModule(module);
		assertEquals(module, getFixture().getPepperModule());
		assertEquals(getFixture(), getFixture().getPepperModule().getModuleController());

		SampleModule module2 = new SampleModule();
		getFixture().setPepperModule(module2);
		assertEquals(module2, getFixture().getPepperModule());
		assertEquals(getFixture(), module2.getModuleController());
	}

	/**
	 * Tests if method {@link ModuleControllerImpl#toString()} returns a non
	 * empty string
	 */
	@Test
	public void testToString() {
		assertNotNull(getFixture().toString());
		assertNotEquals("", getFixture().toString());
	}

	/**
	 * Tests the method {@link ModuleControllerImpl#next()}
	 */
	@Test
	public void testNext() {
		DocumentBus input = new DocumentBus("firstModule", getFixture().getId());
		DocumentBus output = new DocumentBus(getFixture().getId(), "lastModule");
		getFixture().setInputDocumentBus(input);
		getFixture().setOutputDocumentBus(output);
		SaltProject saltProject = SampleGenerator.createSaltProject();
		List<DocumentController> expectedDocumentControllers = new Vector<DocumentController>();
		for (SDocument sDocument : saltProject.getCorpusGraphs().get(0).getDocuments()) {
			DocumentController controller = new DocumentControllerImpl();
			controller.setDocument(sDocument);
			controller.addModuleControllers(getFixture());
			expectedDocumentControllers.add(controller);
			input.put(controller);
		}
		input.finish("firstModule");
		List<DocumentController> documentControllers = new Vector<DocumentController>();
		DocumentController controller = null;
		while ((controller = getFixture().next()) != null) {
			documentControllers.add(controller);
		}
		assertTrue(expectedDocumentControllers.containsAll(documentControllers));
		assertTrue(documentControllers.containsAll(expectedDocumentControllers));
	}

	/**
	 * Tests the method
	 * {@link ModuleControllerImpl#complete(DocumentController)}
	 */
	@Test
	public void testComplete() {
		try {
			getFixture().complete(null);
			fail();
		} catch (PepperFWException e) {
		}

		DocumentBus input = new DocumentBus("firstModule", getFixture().getId());
		DocumentBus output = new DocumentBus(getFixture().getId(), "lastModule");
		getFixture().setInputDocumentBus(input);
		getFixture().setOutputDocumentBus(output);
		try {
			getFixture().complete(new DocumentControllerImpl());
			fail();
		} catch (PepperFWException e) {
		}

		SaltProject saltProject = SampleGenerator.createSaltProject();
		List<DocumentController> expectedDocumentControllers = new Vector<DocumentController>();
		for (SDocument sDocument : saltProject.getCorpusGraphs().get(0).getDocuments()) {
			DocumentController controller = new DocumentControllerImpl();
			controller.setDocument(sDocument);
			controller.addModuleControllers(getFixture());
			expectedDocumentControllers.add(controller);
			input.put(controller);
		}
		try {
			getFixture().complete(expectedDocumentControllers.get(0));
			fail();
		} catch (PepperFWException e) {
		}

		input.finish("firstModule");
		DocumentController controller = null;
		while ((controller = getFixture().next()) != null) {
			getFixture().complete(controller);
		}
		assertNotNull(output.getDocumentBus().get("lastModule"));
		assertEquals(expectedDocumentControllers.size(), output.getDocumentBus().get("lastModule").size());
	}

	/**
	 * Tests the method {@link ModuleControllerImpl#delete(DocumentController)}
	 */
	@Test
	public void testDelete() {
		try {
			getFixture().delete(null);
			fail();
		} catch (PepperFWException e) {
		}

		DocumentBus input = new DocumentBus("firstModule", getFixture().getId());
		DocumentBus output = new DocumentBus(getFixture().getId(), "lastModule");
		getFixture().setInputDocumentBus(input);
		getFixture().setOutputDocumentBus(output);
		try {
			getFixture().delete(new DocumentControllerImpl());
			fail();
		} catch (PepperFWException e) {
		}

		SaltProject saltProject = SampleGenerator.createSaltProject();
		List<DocumentController> expectedDocumentControllers = new Vector<DocumentController>();
		for (SDocument sDocument : saltProject.getCorpusGraphs().get(0).getDocuments()) {
			DocumentController controller = new DocumentControllerImpl();
			controller.setLocation(URI.createFileURI(
					PepperTestUtil.getTempPath_static("moduleController").toString() + "/document.salt"));
			controller.setDocument(sDocument);
			controller.addModuleControllers(getFixture());
			expectedDocumentControllers.add(controller);
			input.put(controller);
		}
		try {
			getFixture().delete(expectedDocumentControllers.get(0));
			fail();
		} catch (PepperFWException e) {
		}

		input.finish("firstModule");
		DocumentController controller = null;
		while ((controller = getFixture().next()) != null) {
			getFixture().delete(controller);
		}
		assertNotNull(output.getDocumentBus().get("lastModule"));
		assertEquals(0, output.getDocumentBus().get("lastModule").size());
	}

	/**
	 * Tests the method {@link ModuleControllerImpl#getProgress(String)}
	 */
	@Test
	public void testgetProgress() {
		try {
			getFixture().getProgress(null);
			fail();
		} catch (PepperFWException e) {
		}

		SaltProject saltProject = SampleGenerator.createSaltProject();
		List<DocumentController> expectedDocumentControllers = new Vector<DocumentController>();
		for (SDocument sDocument : saltProject.getCorpusGraphs().get(0).getDocuments()) {
			DocumentController controller = new DocumentControllerImpl();
			controller.setDocument(sDocument);
			controller.addModuleControllers(getFixture());
			expectedDocumentControllers.add(controller);
		}

		getFixture().setPepperModule(new PepperModuleImpl() {
			@Override
			public Double getProgress(String globalId) {
				return (0D);
			}
		});

		for (DocumentController controller : expectedDocumentControllers) {
			assertEquals(new Double(0), getFixture().getProgress(controller.getGlobalId()));
		}
	}
}

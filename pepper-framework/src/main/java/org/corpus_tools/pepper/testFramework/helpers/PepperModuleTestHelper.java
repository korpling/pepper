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
package org.corpus_tools.pepper.testFramework.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.corpus_tools.pepper.core.ModuleControllerImpl;
import org.corpus_tools.pepper.exceptions.PepperTestException;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.pepper.util.FileComparator;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PepperModuleTestHelper {
	protected static final Logger logger = LoggerFactory.getLogger("Pepper");
	protected URI resourceURI = null;
	protected PepperModule fixture = null;
	/**
	 * {@inheritDoc PepperTestUtil#TMP_TEST_DIR}
	 */
	public static String TMP_TEST_DIR = PepperTestUtil.TMP_TEST_DIR;

	/**
	 * Creates an object of type {@link PepperModuleTest}. To initialize it, the
	 * overridable method {@link #initialize()} is called.
	 */
	public PepperModuleTestHelper() {
		initialize();
	}

	/**
	 * Sets the current fixture to test.
	 * 
	 * @param fixture
	 *            object to test, derived from {@link PepperModule}
	 */
	protected void setFixture(PepperModule fixture) {
		this.fixture = fixture;
		if (resourceURI != null) {
			getFixture().setResources(resourceURI);
		}
		getFixture().setSaltProject(SaltFactory.createSaltProject());
		getFixture().getSaltProject().addCorpusGraph(SaltFactory.createSCorpusGraph());

	}

	/**
	 * Returns the current fixture to test.
	 * 
	 * @return object to test, derived from {@link PepperModule}
	 */
	protected PepperModule getFixture() {
		return fixture;
	}

	/**
	 * Initializes this object. This means:
	 * <ul>
	 * <li>the resource folder {@link #setResourcesURI(URI)} is set to mavens
	 * default 'src/main/resources'</li>
	 * </ul>
	 */
	public void initialize() {
		setResourcesURI(URI.createFileURI("src/main/resources"));
	}

	/**
	 * Returns a {@link File} object pointing to a temporary path, where the
	 * caller can store temporary files. The temporary path is located in the
	 * temporary directory provided by the underlying os. The resulting
	 * directory is located in TEMP_PATH_BY_OS/{@value #TMP_TEST_DIR}/
	 * <code>testDirectory</code>.
	 * 
	 * @param testDirectory
	 *            last part of the temporary path
	 * @return a file object locating to a temporary folder, where files can be
	 *         stored temporarily
	 */
	public URI getTempURI(String testDirectory) {
		return (URI.createFileURI(getTempPath_static(testDirectory).getAbsolutePath()));
	}

	/**
	 * Returns a {@link File} object pointing to a temporary path, where the
	 * caller can store temporary files. The temporary path is located in the
	 * temporary directory provided by the underlying os. The resulting
	 * directory is located in TEMP_PATH_BY_OS/{@value #TMP_TEST_DIR}/
	 * <code>testDirectory</code>.
	 * 
	 * @param testDirectory
	 *            last part of the temporary path
	 * @return a file object locating to a temporary folder, where files can be
	 *         stored temporarily
	 */
	public File getTempPath(String testDirectory) {
		return (getTempPath_static(testDirectory));
	}

	/**
	 * {@inheritDoc PepperTestUtil#getTempPath_static(String)}
	 */
	public static File getTempPath_static(String testDirectory) {
		return (PepperTestUtil.getTempPath_static(testDirectory));
	}

	/**
	 * {@inheritDoc PepperTestUtil#getTestResources()}
	 */
	public static String getTestResources() {
		return (PepperTestUtil.getTestResources());
	}

	/**
	 * {@inheritDoc PepperTestUtil#getSrcResources()}
	 */
	public static String getSrcResources() {
		return (PepperTestUtil.getSrcResources());
	}

	/**
	 * This methods starts the processing of Pepper in the development
	 * environment. In case of the fixture is {@link PepperImporter}, first the
	 * method {@link PepperImporter#importCorpusStructure(SCorpusGraph)} is
	 * called. For all kinds of fixture, the method
	 * {@link PepperModule#start(org.corpus_tools.salt.graph.Identifier)} is
	 * called for each {@link SDocument} object contained in the variable
	 * {@link PepperModule#getSaltProject()}. This method will wait, until each
	 * {@link ModuleControllerImpl} return having finished the process. <br/>
	 * To create a test using this method do the following:<br/>
	 * <ul>
	 * <li>Create {@link CorpusDefinition} and add it to this object and set its
	 * {@link FormatDefinition} and corpus path</li>
	 * <li>Create a {@link SCorpusGraph} object as the one to be filled and add
	 * it with
	 * 
	 * <pre>
	 * getFixture().getSaltProject().getCorpusGraphs().add(importedCorpusGraph);
	 * getFixture().importCorpusStructure(importedCorpusGraph);
	 * </pre>
	 * 
	 * </li>
	 * </ul>
	 */
	public void start() {
		Collection<PepperModule> fixtures = new ArrayList<PepperModule>();
		fixtures.add(getFixture());
		PepperTestUtil.start(fixtures);
	}

	/**
	 * Sets the path to resources folder for module.
	 * 
	 * @param resourceURI
	 *            URI with path
	 */
	public void setResourcesURI(URI resourceURI) {
		if (resourceURI != null) {
			File resourceDir = new File(resourceURI.toFileString());
			if (!resourceDir.exists()) {
				if (!resourceDir.mkdirs()) {
					logger.warn("Cannot create folder {}. ", resourceDir);
				}
			}
			this.resourceURI = resourceURI;
			if (getFixture() != null) {
				getFixture().setResources(resourceURI);
			}
		} else
			throw new PepperTestException("A resource uri must be set.");
	}

	/**
	 * Compares the content of two files. Iff they are exactly the same, than
	 * true will be returned. False otherwise.
	 * 
	 * @param uri1
	 *            first file to compare
	 * @param uri2
	 *            second file to compare
	 * @return true, iff files are exactly the same
	 * @throws IOException
	 */
	public boolean compareFiles(URI uri1, URI uri2) throws IOException {
		return (this.compareFiles(new File(uri1.toFileString()), new File(uri2.toFileString())));
	}

	/**
	 * Compares the content of two files. Iff they are exactly the same, than
	 * true will be returned. False otherwise.
	 * 
	 * @param file1
	 *            first file to compare
	 * @param file2
	 *            second file to compare
	 * @return true, iff files are exactly the same
	 * @throws IOException
	 * @throws IOException
	 */
	public boolean compareFiles(File file1, File file2) throws IOException {
		FileComparator comparator = new FileComparator();
		return (comparator.compareFiles(file1, file2));
	}
}

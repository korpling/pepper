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
package de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MEMORY_POLICY;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleControllerImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.Step;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperTestException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.DocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.doNothing.DoNothingExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.doNothing.DoNothingImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleTestException;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * The method start simulates the Pepper framework as in normal runtime. The
 * difference is, that here Pepper is started in an development environment and
 * enables for developers to get an access directly to the OSGi environment.
 * 
 * @author Florian Zipser
 * 
 */
public abstract class PepperModuleTest {
	private URI resourceURI = null;

	protected PepperModule fixture = null;

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
	 * Creates an object of type {@link PepperModuleTest}. To initialize it, the
	 * overridable method {@link #initilaize()} is called.
	 */
	public PepperModuleTest() {
		initilaize();
	}

	/**
	 * Initializes this object. This means:
	 * <ul>
	 * <li>the resource folder {@link #setResourcesURI(URI)} is set to mavens
	 * default 'src/main/resources'</li>
	 * </ul>
	 */
	public void initilaize() {
		setResourcesURI(URI.createFileURI("src/main/resources"));
	}

	/**
	 * Name of the directory where tests for Pepper and Pepper modules can be
	 * stored.
	 */
	public static String TMP_TEST_DIR = "pepper-test";

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
		return(PepperModuleTest.getTempPath_static(testDirectory));
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
	public static File getTempPath_static(String testDirectory) {
		if ((testDirectory == null) || (testDirectory.isEmpty())) {
			throw new PepperModuleTestException("Cannot return a temporary directory, since the given last part is empty.");
		}
		File retVal = null;
		retVal = new File(System.getProperty("java.io.tmpdir") + "/" + TMP_TEST_DIR + "/" + testDirectory + "/");
		retVal.mkdirs();
		return (retVal);
	}

	/**
	 * Returns a default test folder, where to find resources for tests. When
	 * using the default maven structure, this folder is located at
	 * 'src/test/resources/'.
	 * 
	 * @return a folder where to find test resources
	 */
	public static String getTestResources() {
		return ("src/test/resources/");
	}

	/**
	 * This methods starts the processing of Pepper in the development
	 * environment. In case of the fixture is {@link PepperImporter}, first the
	 * method {@link PepperImporter#importCorpusStructure(SCorpusGraph)} is
	 * called. For all kinds of fixture, the method
	 * {@link PepperModule#start(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId)}
	 * is called for each {@link SDocument} object contained in the variable
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
	 * this.getFixture().getSaltProject().getSCorpusGraphs().add(importedCorpusGraph);
	 * this.getFixture().importCorpusStructure(importedCorpusGraph);
	 * </pre>
	 * 
	 * </li>
	 * </ul>
	 */
	public void start() {
		if (this.getFixture() == null)
			throw new PepperModuleTestException("Cannot start Pepper module, because the fixture is not set.");

		if (this.getFixture().getSaltProject() == null)
			this.getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());

		File tmpFolder = new File(System.getProperty("java.io.tmpdir") + "/pepperModuleTest/");

		PepperImpl pepper = new PepperImpl();
		PepperConfiguration conf = new PepperConfiguration();
		conf.setProperty(PepperConfiguration.PROP_MEMORY_POLICY, MEMORY_POLICY.MODERATE.toString());
		pepper.setConfiguration(conf);
		PepperJob job = pepper.getJob(pepper.createJob());

		if (!(job instanceof PepperJobImpl))
			throw new PepperModuleTestException("Cannot start Pepper module test, because '" + PepperJob.class + "' is not of type '" + PepperJobImpl.class + "'. ");

		((PepperJobImpl) job).setSaltProject(getFixture().getSaltProject());

		CorpusDesc corpusDesc;
		FormatDesc formatDesc;

		Step fixtureStep = null;
		fixtureStep = new Step("fixture_step");
		fixtureStep.setModuleType(getFixture().getModuleType());
		fixtureStep.setName(getFixture().getName());
		fixtureStep.setVersion(getFixture().getVersion());
		if (getFixture() instanceof PepperImporter) {
			fixtureStep.setCorpusDesc(((PepperImporter) getFixture()).getCorpusDesc());
		} else if (getFixture() instanceof PepperExporter) {
			fixtureStep.setCorpusDesc(((PepperExporter) getFixture()).getCorpusDesc());
		}
		fixtureStep.setPepperModule(getFixture());
		((PepperJobImpl) job).addStep(fixtureStep);

		URI dummyResourceURI = URI.createFileURI(new File(System.getProperty("java.io.tmpdir")).getAbsolutePath());

		// define export step
		Step exportStep = new Step("doNothing_export_step");
		exportStep.setModuleType(MODULE_TYPE.EXPORTER);
		exportStep.setName(DoNothingImporter.MODULE_NAME);
		PepperExporter exporter = new DoNothingExporter();
		exporter.setResources(dummyResourceURI);
		exportStep.setPepperModule(exporter);

		corpusDesc = new CorpusDesc();
		corpusDesc.setCorpusPath(URI.createFileURI(tmpFolder.getAbsolutePath()));
		formatDesc = new FormatDesc();
		formatDesc.setFormatName(DoNothingImporter.FORMAT_NAME);
		formatDesc.setFormatVersion(DoNothingImporter.FORMAT_VERSION);
		corpusDesc.setFormatDesc(formatDesc);
		exportStep.setCorpusDesc(corpusDesc);
		exporter.setCorpusDesc(corpusDesc);
		// define export step

		// define import step
		List<Step> importSteps = new Vector<Step>();
		for (SCorpusGraph sCorpusgraph : getFixture().getSaltProject().getSCorpusGraphs()) {
			Step importStep = new Step("doNothing_import_step");
			importStep.setModuleType(MODULE_TYPE.IMPORTER);
			importStep.setName(DoNothingImporter.MODULE_NAME);
			PepperImporter importer = new DoNothingImporter();
			importer.setResources(dummyResourceURI);
			importStep.setPepperModule(importer);

			corpusDesc = new CorpusDesc();
			corpusDesc.setCorpusPath(URI.createFileURI(tmpFolder.getAbsolutePath()));
			formatDesc = new FormatDesc();
			formatDesc.setFormatName(DoNothingImporter.FORMAT_NAME);
			formatDesc.setFormatVersion(DoNothingImporter.FORMAT_VERSION);
			corpusDesc.setFormatDesc(formatDesc);
			importer.setCorpusDesc(corpusDesc);
			importSteps.add(importStep);
		}
		// define import step
		if (getFixture() instanceof PepperImporter) {
			((PepperJobImpl) job).addStep(exportStep);
		} else if (getFixture() instanceof PepperManipulator) {
			if ((importSteps.size() == 0) && (getFixture().getSaltProject().getSCorpusGraphs().size() == 0)) {
				throw new PepperModuleTestException(getFixture(), "Please add either an importer to workflow or create a filled SaltProject to be manipulated. ");
			}
			for (Step importStep : importSteps) {
				((PepperJobImpl) job).addStep(importStep);
			}
			((PepperJobImpl) job).addStep(exportStep);
		} else if (getFixture() instanceof PepperExporter) {
			if ((importSteps.size() == 0) && (getFixture().getSaltProject() == null)) {
				throw new PepperModuleTestException(getFixture(), "Please add either an importer to workflow or create a filled SaltProject to be exported. ");
			}
			for (Step importStep : importSteps) {
				((PepperJobImpl) job).addStep(importStep);
			}
		} else {
			throw new PepperModuleTestException(getFixture(), "Cannot run test, because given fixture '" + getFixture() + "' was neither of type '" + PepperImporter.class + "', '" + PepperManipulator.class + "' nor '" + PepperExporter.class + "'. ");
		}

		job.convert();

		for (DocumentController controller : ((PepperJobImpl) job).getDocumentControllers()) {
			controller.awake();
		}
	}

	@Test
	public void testSetGetCorpusGraph() {
		SCorpusGraph corpGraph = SaltCommonFactory.eINSTANCE.createSCorpusGraph();
		this.getFixture().setSCorpusGraph(corpGraph);
		assertEquals(corpGraph, this.getFixture().getSCorpusGraph());
	}

	@Test
	public void testGetName() {
		assertNotNull("The importer has to have a name.", this.getFixture().getName());
		assertFalse("The name of the importer cannot be empty.", this.getFixture().getName().equals(""));
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
				resourceDir.mkdirs();
			}
			this.resourceURI = resourceURI;
			if (getFixture() != null) {
				getFixture().setResources(resourceURI);
			}
		} else
			throw new PepperTestException("A resource uri must be set.");
	}

	/**
	 * Checks if the resource path is set.
	 */
	@Test
	public void testSetGetResources() {
		assertNotNull("Cannot run test, because resources arent set. Please call setResourcesURI(URI resourceURI) before start testing.", resourceURI);
		this.getFixture().setResources(resourceURI);
		assertEquals(resourceURI, this.getFixture().getResources());
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
		return(PepperModuleTest.compareFiles_static(file1, file2));
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
	public static boolean compareFiles_static(File file1, File file2) throws IOException {
		boolean retVal = false;

		if ((file1 == null) || (file2 == null))
			throw new PepperModuleTestException("One of the files to compare are null.");

		if (!file1.exists())
			throw new PepperModuleTestException("The file '" + file1 + "' does not exist.");
		if (!file2.exists())
			throw new PepperModuleTestException("The file '" + file2 + "' does not exist.");
		String contentFile1 = null;
		String contentFile2 = null;
		BufferedReader brFile1 = null;
		BufferedReader brFile2 = null;
		try {
			brFile1 = new BufferedReader(new FileReader(file1));
			String line = null;
			while ((line = brFile1.readLine()) != null) {
				contentFile1 = contentFile1 + line;
			}
			brFile2 = new BufferedReader(new FileReader(file2));
			line = null;
			while ((line = brFile2.readLine()) != null) {
				contentFile2 = contentFile2 + line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			brFile1.close();
			brFile2.close();
		}

		if (contentFile1 == null) {
			if (contentFile2 == null)
				retVal = true;
			else
				retVal = false;
		} else if (contentFile1.equals(contentFile2))
			retVal = true;
		return (retVal);
	}
}

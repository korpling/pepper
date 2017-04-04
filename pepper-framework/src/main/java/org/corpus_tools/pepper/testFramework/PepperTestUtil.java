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
package org.corpus_tools.pepper.testFramework;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.MEMORY_POLICY;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperConfiguration;
import org.corpus_tools.pepper.common.PepperJob;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.core.ModuleControllerImpl;
import org.corpus_tools.pepper.core.PepperImpl;
import org.corpus_tools.pepper.core.PepperJobImpl;
import org.corpus_tools.pepper.core.Step;
import org.corpus_tools.pepper.modules.DocumentController;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.coreModules.DoNothingExporter;
import org.corpus_tools.pepper.modules.coreModules.DoNothingImporter;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleTestException;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SaltProject;
import org.eclipse.emf.common.util.URI;

/**
 * This class offers some static fields and methods for testing
 * {@link PepperModule} objects.
 *
 */
public class PepperTestUtil {
	/**
	 * Name of the directory where tests for Pepper and Pepper modules can be
	 * stored.
	 */
	public static String TMP_TEST_DIR = "pepper-test";

	/**
	 * Returns a {@link File} object pointing to a temporary path, to store
	 * temporary files. The path is located in the temporary directory provided
	 * by the underlying os. The resulting directory is located in
	 * TEMP_PATH_BY_OS/{@value #TMP_TEST_DIR}/ <code>testDirectory</code>.
	 */
	public static File createTestTempPath(String testDirectory) {
		if ((testDirectory == null) || (testDirectory.isEmpty())) {
			PepperUtil.getTempFile();
		}
		File retVal = null;
		retVal = PepperUtil.getTempTestFile(TMP_TEST_DIR + "/" + testDirectory);
		return (retVal);
	}

	/**
	 * Returns a {@link URI} object pointing to a temporary path, to store
	 * temporary files. The path is located in the temporary directory provided
	 * by the underlying os. The resulting directory is located in
	 * TEMP_PATH_BY_OS/{@value #TMP_TEST_DIR}/ <code>testDirectory</code>.
	 */
	public static URI createTestTempPathAsUri(String testDirectory) {
		return (URI.createFileURI(createTestTempPath(testDirectory).getAbsolutePath()));
	}

	/**
	 * Returns a default test folder, where to find resources for tests. When
	 * using the default maven structure, this folder is located at
	 * 'src/test/resources/'.
	 * 
	 * @return a folder where to find test resources
	 */
	public static String getSrcResources() {
		return ("src/main/resources/");
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
	 * Creates an alibi {@link Step} for the method {@link #start(Collection)}.
	 * 
	 * @param createAlibiImporter
	 * @return
	 */
	private static Step createAlibiStep(boolean createAlibiImporter) {
		URI dummyResourceURI = URI.createFileURI(new File(System.getProperty("java.io.tmpdir")).getAbsolutePath());
		FormatDesc formatDesc;

		// set tmp folder
		File tmpFolder = PepperTestUtil.createTestTempPath("pepperModuleTest");

		Step step = null;
		CorpusDesc corpusDesc = new CorpusDesc();
		corpusDesc.setCorpusPath(URI.createFileURI(tmpFolder.getAbsolutePath()));
		formatDesc = new FormatDesc();
		formatDesc.setFormatName(DoNothingImporter.FORMAT_NAME);
		formatDesc.setFormatVersion(DoNothingImporter.FORMAT_VERSION);
		corpusDesc.setFormatDesc(formatDesc);

		if (createAlibiImporter) {
			step = new Step("doNothing_import_step");
			PepperImporter importer = new DoNothingImporter();
			importer.setResources(dummyResourceURI);
			importer.setCorpusDesc(corpusDesc);
			step.setPepperModule(importer);
			step.setModuleType(MODULE_TYPE.IMPORTER);
		} else {
			step = new Step("doNothing_export_step");
			PepperExporter exporter = new DoNothingExporter();
			exporter.setCorpusDesc(corpusDesc);
			exporter.setResources(dummyResourceURI);
			step.setPepperModule(exporter);
			step.setModuleType(MODULE_TYPE.EXPORTER);
		}
		step.setCorpusDesc(corpusDesc);
		step.setName(DoNothingImporter.MODULE_NAME);

		return (step);
	}

	public static Pepper createDefaultPepper() {
		// Create a Pepper object
		final PepperImpl pepper = new PepperImpl();
		final PepperConfiguration conf = new PepperConfiguration();
		conf.setProperty(PepperConfiguration.PROP_MEMORY_POLICY, MEMORY_POLICY.MODERATE.toString());
		return pepper;
	}

	/**
	 * This methods starts the processing of Pepper in the development
	 * environment for a set of Pepper modules. This enables the test of
	 * multiple modules at once, for instance the import and export of data to
	 * check no data is lost. In case of the fixture is {@link PepperImporter},
	 * first the method
	 * {@link PepperImporter#importCorpusStructure(SCorpusGraph)} is called. For
	 * all kinds of fixture, the method
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
	 * 
	 * @param fixtures
	 *            the fixture modules
	 */
	public static void start(final Collection<? extends PepperModule> fixtures) {
		start(createDefaultPepper(), fixtures);
	}

	/**
	 * @see #start(Collection)
	 */
	@SafeVarargs
	public static <T extends PepperModule> void runPepperForTest(T... fixtures) {
		start(createDefaultPepper(), Arrays.asList(fixtures));
	}

	public static PepperJob prepareFixturesAndCreateJob(final Pepper pepper,
			final Collection<? extends PepperModule> fixtures) {
		if (fixtures == null) {
			throw new PepperModuleTestException(
					"Cannot start Pepper modules, because the list of fixtures is not set.");
		}
		final Collection<PepperImporter> importers = new ArrayList<PepperImporter>();
		final Collection<PepperExporter> exporters = new ArrayList<PepperExporter>();

		/**
		 * set the salt project for all modules, if it is already set, check
		 * that it is equal in all modules. Extract all importers, manipulators
		 * and exporters
		 */
		SaltProject saltProject = null;
		boolean hasOnlyOneFixture = true;
		for (PepperModule fixture : fixtures) {
			if (hasOnlyOneFixture) {
				saltProject = fixture.getSaltProject();
			} else if (saltProject != fixture.getSaltProject()) {
				throw new PepperModuleTestException(
						"Cannot run test because the SaltProject objects are not equal for all Pepper modules. ");
			}
			hasOnlyOneFixture = false;

			// if module does not have a resource, set a default one
			if (fixture.getResources() == null) {
				fixture.setResources(URI.createFileURI("src/main/resources"));
			}

			// fill importers manipulators and exporters collection
			if (fixture instanceof PepperImporter) {
				importers.add((PepperImporter) fixture);
			} else if (fixture instanceof PepperExporter) {
				exporters.add((PepperExporter) fixture);
			}
		}
		if (saltProject == null) {
			saltProject = SaltFactory.createSaltProject();
			for (PepperModule fixture : fixtures) {
				fixture.setSaltProject(saltProject);
			}
		}

		// create a Pepper job object
		final PepperJob job = pepper.getJob(pepper.createJob());
		if (!(job instanceof PepperJobImpl)) {
			throw new PepperModuleTestException("Cannot start Pepper module test, because '" + PepperJob.class
					+ "' is not of type '" + PepperJobImpl.class + "'. ");
		} else {
			((PepperJobImpl) job).setSaltProject(saltProject);
		}

		createStepForFixtures(fixtures, job);

		/** Create and add alibi steps **/
		if ((importers.isEmpty()) || (importers.size() != saltProject.getCorpusGraphs().size())) {
			for (SCorpusGraph cGraph : saltProject.getCorpusGraphs()) {
				boolean isAssociated = false;
				for (PepperModule fixture : fixtures) {
					if (fixture.getCorpusGraph() == cGraph) {
						isAssociated = true;
						break;
					}
				}
				if (!isAssociated) {
					Step alibiStep = createAlibiStep(true);
					((PepperJobImpl) job).addStep(alibiStep);
				}
			}
		}
		if (exporters.isEmpty()) {
			Step alibiStep = createAlibiStep(false);
			((PepperJobImpl) job).addStep(alibiStep);
		}
		return job;
	}

	/**
	 * @see #start(Collection)
	 * 
	 * @param pepper
	 *            a pepper instance to be used
	 */
	public static void start(final Pepper pepper, final Collection<? extends PepperModule> fixtures) {
		final PepperJob job = prepareFixturesAndCreateJob(pepper, fixtures);
		job.convert();
		for (DocumentController controller : ((PepperJobImpl) job).getDocumentControllers()) {
			controller.awake();
		}
	}

	private static void createStepForFixtures(final Collection<? extends PepperModule> fixtures, PepperJob job) {
		/** Create a step for each fixture. **/
		for (PepperModule fixture : fixtures) {
			final Step fixtureStep = new Step("fixture_step");
			fixtureStep.setModuleType(fixture.getModuleType());
			fixtureStep.setName(fixture.getName());
			fixtureStep.setVersion(fixture.getVersion());
			if (fixture instanceof PepperImporter) {
				fixtureStep.setCorpusDesc(((PepperImporter) fixture).getCorpusDesc());
			} else if (fixture instanceof PepperExporter) {
				fixtureStep.setCorpusDesc(((PepperExporter) fixture).getCorpusDesc());
			}
			fixtureStep.setPepperModule(fixture);
			((PepperJobImpl) job).addStep(fixtureStep);
		}
	}
}

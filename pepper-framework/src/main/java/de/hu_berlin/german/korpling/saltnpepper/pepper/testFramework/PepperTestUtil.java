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
package de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MEMORY_POLICY;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleControllerImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.Step;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.DocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules.DoNothingExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules.DoNothingImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleTestException;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * This class offers some static fields and methods for testing
 * {@link PepperModule} objects.
 * 
 * @author florian
 *
 */
public class PepperTestUtil {
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
	public static File getTempPath_static(String testDirectory) {
		if ((testDirectory == null) || (testDirectory.isEmpty())) {
			throw new PepperModuleTestException("Cannot return a temporary directory, since the given last part is empty.");
		}
		File retVal = null;
		retVal = PepperUtil.getTempTestFile(TMP_TEST_DIR + "/" + testDirectory);
		return (retVal);
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
		File tmpFolder = PepperModuleTest.getTempPath_static("pepperModuleTest");

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

	/**
	 * This methods starts the processing of Pepper in the development
	 * environment for a set of Pepper modules. This enables the test of
	 * multiple modules at once, for instance the import and export of data to
	 * check no data is lost. In case of the fixture is {@link PepperImporter},
	 * first the method
	 * {@link PepperImporter#importCorpusStructure(SCorpusGraph)} is called. For
	 * all kinds of fixture, the method
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
	public static void start(Collection<PepperModule> fixtures) {
		if (fixtures == null) {
			throw new PepperModuleTestException("Cannot start Pepper modules, because the list of fixtures is not set.");
		}
		//
		Collection<PepperImporter> importers = new ArrayList<PepperImporter>();
		Collection<PepperManipulator> manipulators = new ArrayList<PepperManipulator>();
		Collection<PepperExporter> exporters = new ArrayList<PepperExporter>();

		/**
		 * set the salt project for all modules, if it is already set, check
		 * that it is equal in all modules. Extract all importers, manipulators
		 * and exporters
		 */
		SaltProject saltProject = null;
		int i = 1;
		for (PepperModule fixture : fixtures) {
			if (i == 1) {
				saltProject = fixture.getSaltProject();
			} else if (saltProject != fixture.getSaltProject()) {
				throw new PepperModuleTestException("Cannot run test because the SaltProject objects are not equal for all Pepper modules. ");
			}
			i++;

			// if module does not have a resource, set a default one
			fixture.setResources(URI.createFileURI("src/main/resources"));

			// fill importers manipulators and exporters collection
			if (fixture instanceof PepperImporter) {
				importers.add((PepperImporter) fixture);
			} else if (fixture instanceof PepperManipulator) {
				manipulators.add((PepperManipulator) fixture);
			} else if (fixture instanceof PepperExporter) {
				exporters.add((PepperExporter) fixture);
			}
		}
		if (saltProject == null) {
			saltProject = SaltFactory.eINSTANCE.createSaltProject();
			for (PepperModule fixture : fixtures) {
				fixture.setSaltProject(saltProject);
			}
		}

		// Create a Pepper object
		PepperImpl pepper = new PepperImpl();
		PepperConfiguration conf = new PepperConfiguration();
		conf.setProperty(PepperConfiguration.PROP_MEMORY_POLICY, MEMORY_POLICY.MODERATE.toString());
		pepper.setConfiguration(conf);

		// create a Pepper job object
		PepperJob job = pepper.getJob(pepper.createJob());
		if (!(job instanceof PepperJobImpl)) {
			throw new PepperModuleTestException("Cannot start Pepper module test, because '" + PepperJob.class + "' is not of type '" + PepperJobImpl.class + "'. ");
		} else {
			((PepperJobImpl) job).setSaltProject(saltProject);
		}

		/** Create a step for each fixture. **/
		for (PepperModule fixture : fixtures) {
			Step fixtureStep = null;
			fixtureStep = new Step("fixture_step");
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

		/** Create and add alibi steps **/
		if ((importers.size() == 0) || (importers.size() != saltProject.getSCorpusGraphs().size())) {
			for (SCorpusGraph cGraph : saltProject.getSCorpusGraphs()) {
				boolean isAssociated = false;
				for (PepperModule fixture : fixtures) {
					if (fixture.getSCorpusGraph() == cGraph) {
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
		if (exporters.size() == 0) {
			Step alibiStep = createAlibiStep(false);
			((PepperJobImpl) job).addStep(alibiStep);
		}

		// start the conversion
		job.convert();

		for (DocumentController controller : ((PepperJobImpl) job).getDocumentControllers()) {
			controller.awake();
		}
	}
}

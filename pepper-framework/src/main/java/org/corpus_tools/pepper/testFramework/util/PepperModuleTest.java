package org.corpus_tools.pepper.testFramework.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.HAS_PASSED_SELFTEST;
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.util.Set;

import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.core.ModuleFitnessChecker;
import org.corpus_tools.pepper.exceptions.PepperTestException;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.pepper.testFramework.RunFitnessCheck;
import org.corpus_tools.pepper.testFramework.old.PepperExporterTest;
import org.corpus_tools.pepper.testFramework.old.PepperImporterTest;
import org.corpus_tools.pepper.testFramework.old.PepperManipulatorTest;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.exceptions.SaltResourceException;
import org.corpus_tools.salt.util.Difference;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;

/**
 * This is the parent class for module test classes {@link PepperImporterTest},
 * {@link PepperManipulatorTest} and {@link PepperExporterTest}. The class
 * provides a set of helping functions, which are used in each derived sub
 * classes. For instance the module to be tested can be set and returned via
 * {@link #setFixture(PepperModule)} and {@link #getFixture()}.
 */
public abstract class PepperModuleTest extends PepperModuleTestCoreFunctionality {
	private static ModuleFitness fitness = null;

	@Test
	public void checkThatModuleHasName() {
		preTest();
		assertThat(fitness.getFitness(FitnessFeature.HAS_NAME)).as("A module's name must not be empty. ").isTrue();
	}

	@Test
	public void checkThatModuleIsReadyToRun() {
		preTest();
		assertThat(fitness.getFitness(FitnessFeature.IS_READY_TO_RUN))
				.as("The module is not ready to run, please check the method ''PepperModule.isReadyToRun()'. ")
				.isTrue();
	}

	@Test
	public void checkThatModuleHasSupplierContact() {
		preTest();
		assertThat(fitness.getFitness(FitnessFeature.HAS_SUPPLIER_CONTACT))
				.as("The module does not provide an email address of the module's supplier. ").isTrue();
	}

	@Test
	public void checkThatModuleHasSupplierHomepage() {
		preTest();
		assertThat(fitness.getFitness(FitnessFeature.HAS_SUPPLIER_HP))
				.as("The module does not provide a link to the modules supplier's homepage. ").isTrue();
	}

	@Test
	public void checkThatModuleHasDescription() {
		preTest();
		assertThat(fitness.getFitness(FitnessFeature.HAS_DESCRIPTION))
				.as("The module does not provide a proper description. ").isTrue();
	}

	@Test
	public void checkThatModuleHasSupportedFormats() {
		preTest();
		assertThat(fitness.getFitness(FitnessFeature.HAS_DESCRIPTION))
				.as("The module does not provide a list of formats it supports. ").isTrue();
	}

	@Test
	public void checkThatMethodIsImportableIsImplemented() {
		preTest();
		fitness.getFitness(FitnessFeature.IS_IMPORTABLE);
		assumeTrue(
				"The module does not provide implement method isImportable(). This is not required in current Pepper version, but strongly recommanded. ",
				fitness.getFitness(FitnessFeature.IS_IMPORTABLE));
	}

	@Test
	public void checkThatModuleHasPassedSelfTest() {
		preTest();
		assumeTrue(fitness.getFitness(FitnessFeature.HAS_SELFTEST));
		boolean hasPassedSelfTest = fitness.getFitness(HAS_PASSED_SELFTEST);
		whenHasNotPassedSelfTestThenSaveSaltProject(hasPassedSelfTest);
		assertThat(hasPassedSelfTest)
				.as("The module has not passed the provided self-test. " + diffsBetweenActualAndExpected()).isTrue();
	}

	protected String diffsBetweenActualAndExpected() {
		SCorpusGraph expectedCorpusGraph = null;
		try {
			expectedCorpusGraph = SaltUtil.loadSaltProject(fixture.getSelfTestDesc().getExpectedCorpusPath())
					.getCorpusGraphs().get(0);
		} catch (SaltResourceException e) {
			throw new PepperTestException("Cannot load expected corpus graph from '"
					+ fixture.getSelfTestDesc().getExpectedCorpusPath() + "', because of a nested exception. ", e);
		}
		final Set<Difference> diffs = SaltUtil.compare(expectedCorpusGraph)
				.with(fixture.getSaltProject().getCorpusGraphs().get(0)).andFindDiffs();
		if (!diffs.isEmpty()) {
			return "There are differences between actual and expected Salt model: " + diffs;
		}
		return diffs.toString();
	}

	protected void whenHasNotPassedSelfTestThenSaveSaltProject(boolean hasPassedSelfTest) {
		if (!hasPassedSelfTest) {
			final File saltProjectLoaction = getTempPath("actualSaltProject");
			getFixture().getSaltProject().saveSaltProject(URI.createFileURI(saltProjectLoaction.getAbsolutePath()));
			logger.error("Test did not passed has self-test, the actual Salt project was stored to '"
					+ saltProjectLoaction.getAbsolutePath() + "'. ");
		}
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
	protected File getTempPath(String testDirectory) {
		return (PepperTestUtil.createTempPath(testDirectory));
	}

	@Test
	public void checkThatModuleCanImportSelfTestData() {
		preTest();
		assumeTrue(fitness.getFitness(FitnessFeature.HAS_SELFTEST));
		assertThat(fitness.getFitness(IS_IMPORTABLE_SEFTEST_DATA))
				.as("The imported file was not detected as being importable by this importer. ").isTrue();
	}

	@Test
	public void checkThatSelfTestResultIsValid() {
		preTest();
		assumeTrue(fitness.getFitness(FitnessFeature.HAS_SELFTEST));
		assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA))
				.as("The self-test does not produce a valid salt model. ").isTrue();
	}

	private void preTest() {
		assumeTrue(
				"The fitness check for " + this.getClass().getSimpleName()
						+ " is turned off. To turn it on, set variable 'RUN_FITNESS_CHECK' to 'true'. ",
				this instanceof RunFitnessCheck);
		whenFitnessCheckWasntStartetdThenRun();
		whenFixtureIsNullThenFail();
	}

	private synchronized void whenFitnessCheckWasntStartetdThenRun() {
		if (fitness != null) {
			return;
		}
		ModuleFitnessChecker fitnessChecker = new ModuleFitnessChecker();
		fitness = fitnessChecker.checkFitness(fixture);
	}

	private void whenFixtureIsNullThenFail() {
		if (getFixture() == null) {
			fail("Cannot run tests when no fixture is set. Please call setFixture(PepperModule) before running test. ");
		}
	}
}

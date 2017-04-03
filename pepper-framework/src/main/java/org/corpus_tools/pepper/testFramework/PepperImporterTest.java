package org.corpus_tools.pepper.testFramework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA;
import static org.junit.Assume.assumeTrue;

import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.testFramework.util.PepperImExporterTest;
import org.junit.Test;

public abstract class PepperImporterTest<M extends PepperImporter> extends PepperImExporterTest<M> {
	@Test
	public void checkThatMethodIsImportableIsImplemented() {
		preTest();
		getFitness().getFitness(FitnessFeature.IS_IMPORTABLE);
		assumeTrue(
				"The module does not provide implement method isImportable(). This is not required in current Pepper version, but strongly recommanded. ",
				getFitness().getFitness(FitnessFeature.IS_IMPORTABLE));
	}

	@Test
	public void checkThatModuleCanImportSelfTestData() {
		preTest();
		assumeTrue(getFitness().getFitness(FitnessFeature.HAS_SELFTEST));
		assertThat(getFitness().getFitness(IS_IMPORTABLE_SEFTEST_DATA))
				.as("The imported file was not detected as being importable by this importer. ").isTrue();
	}

	@Test
	public void checkThatSelfTestResultIsValid() {
		preTest();
		assumeTrue(getFitness().getFitness(FitnessFeature.HAS_SELFTEST));
		assertThat(getFitness().getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA))
				.as("The self-test does not produce a valid salt model. ").isTrue();
	}
}

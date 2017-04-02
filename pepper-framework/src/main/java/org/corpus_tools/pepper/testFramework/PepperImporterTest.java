package org.corpus_tools.pepper.testFramework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA;
import static org.junit.Assume.assumeTrue;

import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.testFramework.util.PepperImExporterTest;
import org.junit.Test;

public abstract class PepperImporterTest extends PepperImExporterTest {
	@Test
	public void checkThatMethodIsImportableIsImplemented() {
		preTest();
		fitness.getFitness(FitnessFeature.IS_IMPORTABLE);
		assumeTrue(
				"The module does not provide implement method isImportable(). This is not required in current Pepper version, but strongly recommanded. ",
				fitness.getFitness(FitnessFeature.IS_IMPORTABLE));
	}

	@Test
	public void checkThatModuleCanImportSelfTestData() {
		preTest();
		assumeTrue(fitness.getFitness(FitnessFeature.HAS_SELFTEST));
		assertThat(fitness.getFitness(IS_IMPORTABLE_SEFTEST_DATA))
				.as("The imported file was not detected as being importable by this importer. ").isTrue();
	}
}

package org.corpus_tools.pepper.testFramework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.HAS_PASSED_SELFTEST;
import static org.junit.Assume.assumeTrue;

import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.testFramework.util.PepperImExporterTest;
import org.junit.Test;

public abstract class PepperExporterTest<M extends PepperExporter> extends PepperImExporterTest<M> {
	@Override
	@Test
	public void checkThatModuleHasPassedSelfTest() {
		preTest();
		assumeTrue(getFitness(FitnessFeature.HAS_SELFTEST));
		assertThat(getFitness(HAS_PASSED_SELFTEST)).as("The module has not passed the provided self-test. ").isTrue();
	}
}

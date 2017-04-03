package org.corpus_tools.pepper.testFramework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.modules.PepperManipulator;
import org.corpus_tools.pepper.testFramework.util.PepperModuleTest;
import org.junit.Test;

public abstract class PepperManipulatorTest<M extends PepperManipulator> extends PepperModuleTest<M> {
	@Test
	public void checkThatSelfTestResultIsValid() {
		preTest();
		assumeTrue(getFitness().getFitness(FitnessFeature.HAS_SELFTEST));
		assertThat(getFitness().getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA))
				.as("The self-test does not produce a valid salt model. ").isTrue();
	}
}

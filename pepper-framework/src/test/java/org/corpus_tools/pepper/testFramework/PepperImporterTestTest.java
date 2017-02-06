package org.corpus_tools.pepper.testFramework;

import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.HAS_PASSED_SELFTEST;
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.HAS_SELFTEST;
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA;
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.IS_VALID_SELFTEST_DATA;
import static org.mockito.Mockito.when;

import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.core.SelfTestDesc;
import org.corpus_tools.pepper.impl.PepperImporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PepperImporterTestTest {
	private PepperImporterTest fixture;

	private ModuleFitness fitness;

	@Before
	public void beforeEach() {
		fitness = new ModuleFitness("myImporter");
		fixture = new MyPepperImporterTest();
	}

	class MyPepperImporterTest extends PepperImporterTest {
		public MyPepperImporterTest() {
			super();
			setFixture(Mockito.spy(PepperImporterImpl.class));
			when(getFixture().getSelfTestDesc()).thenReturn(new SelfTestDesc(null, null));
		}

		@Override
		protected String diffsBetweenActualAndExpected() {
			return "";
		}

		@Override
		protected ModuleFitness runSelfTest() {
			return fitness;
		}
	}

	@Test
	public void whenCheckThatWhenSimulatingFitnessCheckModulePassesSelfTestAndAllFeaturesPassed_thenSuccess() {
		// GIVEN
		fitness.setFeature(HAS_SELFTEST, true);
		fitness.setFeature(HAS_PASSED_SELFTEST, true);
		fitness.setFeature(IS_IMPORTABLE_SEFTEST_DATA, true);
		fitness.setFeature(IS_VALID_SELFTEST_DATA, true);

		// WHEN
		fixture.checkThatWhenSimulatingFitnessCheckModulePassesSelfTest();
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatWhenSimulatingFitnessCheckModulePassesSelfTestAndNoSelfTestAvailable_thenFail() {
		// GIVEN
		fitness.setFeature(HAS_SELFTEST, false);
		fitness.setFeature(HAS_PASSED_SELFTEST, true);
		fitness.setFeature(IS_IMPORTABLE_SEFTEST_DATA, true);
		fitness.setFeature(IS_VALID_SELFTEST_DATA, true);

		// WHEN
		fixture.checkThatWhenSimulatingFitnessCheckModulePassesSelfTest();
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatWhenSimulatingFitnessCheckModulePassesSelfTestAndSelfTestNotPassed_thenFail() {
		// GIVEN
		fitness.setFeature(HAS_SELFTEST, true);
		fitness.setFeature(HAS_PASSED_SELFTEST, false);
		fitness.setFeature(IS_IMPORTABLE_SEFTEST_DATA, true);
		fitness.setFeature(IS_VALID_SELFTEST_DATA, true);

		// WHEN
		fixture.checkThatWhenSimulatingFitnessCheckModulePassesSelfTest();
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatWhenSimulatingFitnessCheckModulePassesSelfTestAndSelfTestNotImportable_thenFail() {
		// GIVEN
		fitness.setFeature(HAS_SELFTEST, true);
		fitness.setFeature(HAS_PASSED_SELFTEST, true);
		fitness.setFeature(IS_IMPORTABLE_SEFTEST_DATA, false);
		fitness.setFeature(IS_VALID_SELFTEST_DATA, true);

		// WHEN
		fixture.checkThatWhenSimulatingFitnessCheckModulePassesSelfTest();
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatWhenSimulatingFitnessCheckModulePassesSelfTestAndSelfTestNotValid_thenFail() {
		// GIVEN
		fitness.setFeature(HAS_SELFTEST, true);
		fitness.setFeature(HAS_PASSED_SELFTEST, true);
		fitness.setFeature(IS_IMPORTABLE_SEFTEST_DATA, true);
		fitness.setFeature(IS_VALID_SELFTEST_DATA, false);

		// WHEN
		fixture.checkThatWhenSimulatingFitnessCheckModulePassesSelfTest();
	}
}

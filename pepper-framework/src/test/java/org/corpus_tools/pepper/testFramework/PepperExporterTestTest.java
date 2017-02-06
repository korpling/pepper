package org.corpus_tools.pepper.testFramework;

import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.HAS_PASSED_SELFTEST;
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.HAS_SELFTEST;
import static org.mockito.Mockito.when;

import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.core.SelfTestDesc;
import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PepperExporterTestTest {
	private PepperExporterTest fixture;

	private ModuleFitness fitness;

	@Before
	public void beforeEach() {
		fitness = new ModuleFitness("myExporter");
		fixture = new MyPepperExporterTest();
	}

	class MyPepperExporterTest extends PepperExporterTest {
		public MyPepperExporterTest() {
			super();
			setFixture(Mockito.spy(PepperExporterImpl.class));
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

		// WHEN
		fixture.checkThatWhenSimulatingFitnessCheckModulePassesSelfTest();
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatWhenSimulatingFitnessCheckModulePassesSelfTestAndNoSelfTestAvailable_thenFail() {
		// GIVEN
		fitness.setFeature(HAS_SELFTEST, false);
		fitness.setFeature(HAS_PASSED_SELFTEST, true);

		// WHEN
		fixture.checkThatWhenSimulatingFitnessCheckModulePassesSelfTest();
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatWhenSimulatingFitnessCheckModulePassesSelfTestAndSelfTestNotPassed_thenFail() {
		// GIVEN
		fitness.setFeature(HAS_SELFTEST, true);
		fitness.setFeature(HAS_PASSED_SELFTEST, false);

		// WHEN
		fixture.checkThatWhenSimulatingFitnessCheckModulePassesSelfTest();
	}
}

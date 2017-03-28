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
			when(getFixture().getSelfTestDesc()).thenReturn(SelfTestDesc.create().build());
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

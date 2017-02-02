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

import static org.assertj.core.api.Assertions.assertThat;

import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.core.ModuleFitnessChecker;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperManipulator;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.util.SaltUtil;
import org.junit.Test;

/**
 * This class is an abstract helper class to create own test classes for any
 * {@link PepperModule}. The method start simulates the Pepper framework as in
 * normal runtime. The difference is, that here Pepper is started in an
 * development environment and enables for developers to get an access directly
 * to the OSGi environment.
 * 
 * @author Florian Zipser
 *
 */
public abstract class PepperModuleTest extends PepperModuleTestHelper {

	@Test
	public void checkThatFixtureIsSet() {
		assertThat(getFixture()).as("Please inject your module implementation with setFixture(PepperModule). ")
				.isNotNull();
	}

	@Test
	public void checkThatCorpusGraphIsSettable() {
		final SCorpusGraph corpGraph = SaltFactory.createSCorpusGraph();
		getFixture().setCorpusGraph(corpGraph);

		assertThat(getFixture().getCorpusGraph()).as("When setting and getting the corpus graph, both should be same. ")
				.isEqualTo(corpGraph);
	}

	@Test
	public void checkThatModuleHasName() {
		assertThat(getFixture().getName()).as("A module's name must not be empty. ").isNotEmpty();
	}

	@Test
	public void checkThatResourcePathIsSet() {
		getFixture().setResources(resourceURI);
		final String msg = "Cannot run test, because resources arent set. Please call setResourcesURI(URI resourceURI) before start testing. ";

		assertThat(resourceURI).as(msg).isNotNull();
		assertThat(getFixture().getResources()).as(msg).isEqualTo(resourceURI);
	}

	@Test
	public void checkThatWhenSimulatingFitnessCheckModulePassesSelfTest() {
		if (getFixture().getSelfTestDesc() == null) {
			// no self test given --> nothing to be tested
			return;
		}

		// WHEN
		final ModuleFitness fitness = new ModuleFitnessChecker(PepperTestUtil.createDefaultPepper()).selfTest(fixture);

		// THEN
		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		if (fixture instanceof PepperImporter && fixture instanceof PepperManipulator) {
			assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).as("" + SaltUtil
					.compare(SaltUtil.loadSaltProject(fixture.getSelfTestDesc().getExpectedCorpusPath())
							.getCorpusGraphs().get(0))
					.with(fixture.getSaltProject().getCorpusGraphs().get(0)).andFindDiffs()).isTrue();
		} else {
			assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isTrue();
		}
		if (fixture instanceof PepperImporter) {
			assertThat(fitness.getFitness(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA)).isTrue();
		}
		if (fixture instanceof PepperImporter && fixture instanceof PepperManipulator) {
			assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
		}
	}
}

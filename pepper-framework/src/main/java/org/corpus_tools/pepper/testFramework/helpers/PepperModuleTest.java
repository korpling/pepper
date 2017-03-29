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
package org.corpus_tools.pepper.testFramework.helpers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.File;
import java.util.Set;

import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.core.SelfTestRunner;
import org.corpus_tools.pepper.exceptions.PepperTestException;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.exceptions.SaltResourceException;
import org.corpus_tools.salt.util.Difference;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
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
		whenFixtureIsNullThenFail();
		final SCorpusGraph corpGraph = SaltFactory.createSCorpusGraph();
		getFixture().setCorpusGraph(corpGraph);

		assertThat(getFixture().getCorpusGraph()).as("When setting and getting the corpus graph, both should be same. ")
				.isEqualTo(corpGraph);
	}

	private void whenFixtureIsNullThenFail() {
		if (getFixture() == null) {
			fail("Cannot run tests when no fixture is set. Please call setFixture(PepperModule) before running test. ");
		}
	}

	@Test
	public void checkThatModuleHasName() {
		whenFixtureIsNullThenFail();

		assertThat(getFixture().getName()).as("A module's name must not be empty. ").isNotEmpty();
	}

	@Test
	public void checkThatResourcePathIsSet() {
		whenFixtureIsNullThenFail();
		getFixture().setResources(resourceURI);
		final String msg = "Cannot run test, because resources arent set. Please call setResourcesURI(URI resourceURI) before start testing. ";

		assertThat(resourceURI).as(msg).isNotNull();
		assertThat(getFixture().getResources()).as(msg).isEqualTo(resourceURI);
	}

	@Test
	public void checkThatWhenSimulatingFitnessCheckModulePassesSelfTest() {
		whenFixtureIsNullThenFail();
		if (getFixture().getSelfTestDesc() == null) {
			// no self test given --> nothing to be tested
			return;
		}
		// WHEN
		final ModuleFitness fitness = runSelfTest();
		// THEN
		checkThatWhenSimulatingFitnessCheckModulePassesSelfTest(fitness);
	}

	protected ModuleFitness runSelfTest() {
		return new SelfTestRunner(PepperTestUtil.createDefaultPepper(), null, fixture).selfTest();
	}

	protected abstract void checkThatWhenSimulatingFitnessCheckModulePassesSelfTest(ModuleFitness fitness);

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
			System.out.println("Test did not passed has self-test, the actual Salt project was stored to '"
					+ saltProjectLoaction.getAbsolutePath() + "'. ");
		}
	}
}

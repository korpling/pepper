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
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.HAS_PASSED_SELFTEST;
import static org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature.HAS_SELFTEST;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.testFramework.helpers.PepperImExporterTest;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SaltProject;

/**
 * <p>
 * This class is a helper class for creating tests for {@link PepperExporter}s.
 * This class provides a fixture declaration which could be called via
 * {@link #setFixture(PepperExporter)}. The fixture which is returned via
 * {@link #getFixture()} is of type {@link PepperExporter}. To create an easier
 * access, we recommend to overwrite the method {@link #getFixture()} as
 * follows:
 * 
 * <pre>
 * &#064;Override
 * public MY_EXPORTER_CLASS getFixture() {
 * 	return (MY_EXPORTER_CLASS) fixture;
 * }
 * </pre>
 * 
 * The method {@link #setFixture(PepperExporter)} sets the {@link SaltProject}
 * and creates a single {@link SCorpusGraph} object, which is added to the list
 * of corpus structures in the salt project. To access the salt project or the
 * corpus structure use the following code:
 * 
 * <pre>
 * 	getFixture().getSaltProject();
 *  getFixture().getSaltProject().getCorpusGraphs()
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * This class predefines a test to check that the format of the exporter is set
 * correctly. Therefore you need to call {@link #addSupportedFormat(FormatDesc)}
 * and pass the format your exporter should support. Otherwise this test will
 * fail. You can do this as follows:
 * 
 * <pre>
 * addSupportedFormat(new FormatDesc().setFormatName(FORMAT_NAME).setFormatVersion(FROMAT_VERSION));
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * To run the test call {@link #start()} in your test method. This will start
 * the test environment, which simulates a Pepper conversion process.
 * </p>
 * 
 * @author florian
 *
 */
public abstract class PepperExporterTest extends PepperImExporterTest {
	@Override
	protected PepperExporter getFixture() {
		return ((PepperExporter) super.getFixture());
	}

	@Override
	public void checkThatWhenSimulatingFitnessCheckModulePassesSelfTest(final ModuleFitness fitness) {
		assertThat(fitness.getFitness(HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(HAS_PASSED_SELFTEST)).isTrue();
	}
}

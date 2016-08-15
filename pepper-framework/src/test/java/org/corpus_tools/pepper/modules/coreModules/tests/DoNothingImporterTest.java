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
package org.corpus_tools.pepper.modules.coreModules.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.core.ModuleFitnessChecker;
import org.corpus_tools.pepper.modules.coreModules.DoNothingImporter;
import org.corpus_tools.pepper.testFramework.PepperImporterTest;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class DoNothingImporterTest extends PepperImporterTest {

	@Before
	public void setUp() throws Exception {
		setFixture(new DoNothingImporter());
		// set formats to support
		FormatDesc formatDef = new FormatDesc();
		formatDef.setFormatName(DoNothingImporter.FORMAT_NAME);
		formatDef.setFormatVersion(DoNothingImporter.FORMAT_VERSION);
		this.supportedFormatsCheck.add(formatDef);
	}

	@Test
	public void whenSelfTestingModule_thenResultShouldBeTrue() {
//		SaltProject project = SaltFactory.createSaltProject();
//		project.addCorpusGraph(SaltFactory.createSCorpusGraph());
//		SaltUtil.saveSaltProject(project, URI.createFileURI("/home/florian/work/snp/workspace/pepper/pepper-framework/src/main/resources/modules/selfTests/doNothingImporter/expected/"));
		
		final ModuleFitness fitness = new ModuleFitnessChecker(PepperTestUtil.createDefaultPepper()).selfTest(fixture);
//		SaltUtil.saveSaltProject(fixture.getSaltProject(), URI.createFileURI("/home/florian/test/bla/"));
//		System.out.println(SaltUtil.compare(fixture.getSaltProject()).with(SaltUtil.loadCompleteSaltProject(URI.createFileURI("/home/florian/test/bla/saltProject.salt"))).andFindDiffs());	
		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
	}
}

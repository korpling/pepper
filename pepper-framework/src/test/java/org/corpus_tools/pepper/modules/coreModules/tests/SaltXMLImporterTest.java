package org.corpus_tools.pepper.modules.coreModules.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.core.ModuleFitnessChecker;
import org.corpus_tools.pepper.modules.coreModules.SaltXMLImporter;
import org.corpus_tools.pepper.testFramework.PepperImporterTest;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class SaltXMLImporterTest extends PepperImporterTest {
	@Before
	public void setUp() throws Exception {
		setFixture(new SaltXMLImporter());
		// set formats to support
		FormatDesc formatDef = new FormatDesc();
		formatDef.setFormatName(SaltXMLImporter.FORMAT_NAME);
		formatDef.setFormatVersion(SaltXMLImporter.FORMAT_VERSION);
		this.supportedFormatsCheck.add(formatDef);
	}

	@Test
	public void whenSelfTestingModule_thenResultShouldBeTrue() {
		final ModuleFitness fitness = new ModuleFitnessChecker(PepperTestUtil.createDefaultPepper()).selfTest(fixture);

		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(
				fitness.getFitness(
						FitnessFeature.HAS_PASSED_SELFTEST))
								.describedAs(""
										+ SaltUtil
												.compare(SaltUtil
														.loadSaltProject(
																fixture.getSelfTestDesc().getExpectedCorpusPath())
														.getCorpusGraphs().get(0))
												.with(fixture.getSaltProject().getCorpusGraphs().get(0)).andFindDiffs())
								.isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
	}
}

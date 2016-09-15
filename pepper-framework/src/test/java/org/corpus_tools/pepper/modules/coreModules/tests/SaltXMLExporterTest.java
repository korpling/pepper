package org.corpus_tools.pepper.modules.coreModules.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.core.ModuleFitnessChecker;
import org.corpus_tools.pepper.modules.coreModules.SaltXMLExporter;
import org.corpus_tools.pepper.testFramework.PepperExporterTest;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.util.SaltUtil;
import org.junit.Before;
import org.junit.Test;

public class SaltXMLExporterTest extends PepperExporterTest {
	@Before
	public void setUp() throws Exception {
		setFixture(new SaltXMLExporter());
		// set formats to support
		FormatDesc formatDef = new FormatDesc();
		formatDef.setFormatName(SaltXMLExporter.FORMAT_NAME);
		formatDef.setFormatVersion(SaltXMLExporter.FORMAT_VERSION);
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
	}
}
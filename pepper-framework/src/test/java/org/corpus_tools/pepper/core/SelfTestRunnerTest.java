package org.corpus_tools.pepper.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperManipulator;
import org.corpus_tools.pepper.modules.coreModules.DoNothingExporter;
import org.corpus_tools.pepper.modules.coreModules.DoNothingImporter;
import org.corpus_tools.pepper.modules.coreModules.DoNothingManipulator;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.mockito.Matchers;

public class SelfTestRunnerTest {
	private static final URI SAMPLE_INPUT_PATH = URI
			.createFileURI(PepperTestUtil.getTestResources() + "selfTest/sampleCorpus/in/");
	private static final URI SAMPLE_EXPECTED_PATH = URI
			.createFileURI(PepperTestUtil.getTestResources() + "selfTest/sampleCorpus/out/");

	private static final Pepper PEPPER = PepperTestUtil.createDefaultPepper();

	@Test(expected = PepperFWException.class)
	public void whenSelfTestAndNoPepperWasSpecified_thenFail() {
		final PepperImporter importer = mock(PepperImporter.class);
		new SelfTestRunner(null, null, importer).selfTest();
	}

	@Test
	public void whenSelfTestAndNoPepperModuleWasSpecified_thenHasSelfTestIsFalse() {
		final Pepper pepper = mock(Pepper.class);
		final ModuleFitness fitness = new SelfTestRunner(pepper, null, null).selfTest();

		assertThat(fitness).isNull();
	}

	@Test
	public void whenSelfTestAndPepperModuleReturnsEmptyTestDesc_thenHasSelfTestIsFalse() {
		final Pepper pepper = mock(Pepper.class);
		final PepperImporter importer = mock(PepperImporter.class);
		when(importer.getSelfTestDesc()).thenReturn(null);
		final ModuleFitness fitness = new SelfTestRunner(pepper, null, importer).selfTest();

		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isFalse();
	}

	@Test
	public void whenSelfTestAndTestDescIsNotValid_thenHasSelfTestIsTrue() {
		final Pepper pepper = mock(Pepper.class);
		final PepperImporter importer = mock(PepperImporter.class);
		final SelfTestDesc desc = mock(SelfTestDesc.class);
		when(importer.getSelfTestDesc()).thenReturn(desc);
		final ModuleFitness fitness = new SelfTestRunner(pepper, null, importer).selfTest();

		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
	}

	@Test
	public void whenSelfTestAndModuleIsImporterAndEverythingIsOk_thenAllFeaturesShouldBeTrue() {
		// GIVEN
		final SelfTestDesc desc = mock(SelfTestDesc.class);
		when(desc.getInputCorpusPath()).thenReturn(SAMPLE_INPUT_PATH);
		when(desc.getExpectedCorpusPath()).thenReturn(SAMPLE_EXPECTED_PATH);
		when(desc.isValid(Matchers.anyListOf(String.class))).thenReturn(true);
		when(desc.compare(any(SaltProject.class), any(SaltProject.class))).thenReturn(true);
		final PepperImporter importer = spy(DoNothingImporter.class);

		doReturn(desc).when(importer).getSelfTestDesc();
		when(importer.isImportable(any(URI.class))).thenReturn(1.0);
		when(importer.getSaltProject()).thenReturn(SampleGenerator.createSaltProject());
		// WHEN
		final ModuleFitness fitness = new SelfTestRunner(PEPPER, null, importer).selfTest();
		// THEN
		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
	}

	@Test
	public void whenSelfTestAndModuleIsImporterAndImportedCorpusIsNotEqualToExpectedOne_thenPassedSelfTestShouldBeFalse() {
		// GIVEN
		final SelfTestDesc desc = mock(SelfTestDesc.class);
		when(desc.getInputCorpusPath()).thenReturn(SAMPLE_INPUT_PATH);
		when(desc.getExpectedCorpusPath()).thenReturn(SAMPLE_EXPECTED_PATH);
		when(desc.isValid(Matchers.anyListOf(String.class))).thenReturn(true);
		when(desc.compare(any(SaltProject.class), any(SaltProject.class))).thenReturn(false);
		final PepperImporter importer = spy(DoNothingImporter.class);
		doReturn(desc).when(importer).getSelfTestDesc();
		when(importer.isImportable(any(URI.class))).thenReturn(1.0);
		when(importer.getSaltProject()).thenReturn(SampleGenerator.createSaltProject());
		// WHEN
		final ModuleFitness fitness = new SelfTestRunner(PEPPER, null, importer).selfTest();
		// THEN
		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isFalse();
		assertThat(fitness.getFitness(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
	}

	@Test
	public void whenSelfTestAndModuleIsManipulatorAndEverythingIsOk_thenFeaturesShouldBeTrue() {
		// GIVEN
		final SelfTestDesc desc = mock(SelfTestDesc.class);
		when(desc.getInputCorpusPath()).thenReturn(SAMPLE_INPUT_PATH);
		when(desc.getExpectedCorpusPath()).thenReturn(SAMPLE_EXPECTED_PATH);
		when(desc.isValid(Matchers.anyListOf(String.class))).thenReturn(true);
		when(desc.compare(any(SaltProject.class), any(SaltProject.class))).thenReturn(false);
		final PepperManipulator manipulator = spy(DoNothingManipulator.class);
		doReturn(desc).when(manipulator).getSelfTestDesc();
		when(manipulator.getSaltProject()).thenReturn(SampleGenerator.createSaltProject());
		// WHEN
		final ModuleFitness fitness = new SelfTestRunner(PEPPER, null, manipulator).selfTest();
		// THEN
		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isFalse();
		assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
	}

	@Test
	public void whenSelfTestAndModuleIsExporterAndEverythingIsOk_thenFeaturesShouldBeTrue() {
		// GIVEN
		final SelfTestDesc desc = mock(SelfTestDesc.class);
		when(desc.getInputCorpusPath()).thenReturn(SAMPLE_INPUT_PATH);
		when(desc.getExpectedCorpusPath()).thenReturn(SAMPLE_EXPECTED_PATH);
		when(desc.isValid(Matchers.anyListOf(String.class))).thenReturn(true);
		when(desc.compare(any(SaltProject.class), any(SaltProject.class))).thenReturn(false);
		final PepperExporter exporter = spy(DoNothingExporter.class);
		doReturn(desc).when(exporter).getSelfTestDesc();
		when(exporter.getSaltProject()).thenReturn(SampleGenerator.createSaltProject());
		// WHEN
		final ModuleFitness fitness = new SelfTestRunner(PEPPER, null, exporter).selfTest();
		// THEN
		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isFalse();
	}
}

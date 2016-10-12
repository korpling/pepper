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
package org.corpus_tools.pepper.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.Fitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperManipulator;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.coreModules.DoNothingExporter;
import org.corpus_tools.pepper.modules.coreModules.DoNothingImporter;
import org.corpus_tools.pepper.modules.coreModules.DoNothingManipulator;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ModuleFitnessCheckerTest {

	private PepperModule createHealthyModule() {
		PepperModule healthyModule = mock(PepperImporter.class);
		when(healthyModule.getName()).thenReturn("MyModule");
		when(healthyModule.getDesc()).thenReturn("any description");
		when(healthyModule.getSupplierHomepage()).thenReturn(null);
		when(healthyModule.getSupplierContact()).thenReturn(URI.createURI("me@mail.com"));
		when(healthyModule.isReadyToStart()).thenReturn(true);
		return healthyModule;
	}

	private PepperModule createCriticalModule() {
		PepperModule criticalModule = mock(PepperImporter.class);
		when(criticalModule.getSupplierHomepage()).thenReturn(URI.createURI("http://me.com"));
		when(criticalModule.getSupplierContact()).thenReturn(URI.createURI("me@mail.com"));
		when(criticalModule.isReadyToStart()).thenReturn(false);
		return criticalModule;
	}

	@Test
	public void whenCheckingHealthForNull_thenReturnNull() {
		PepperModule module = null;
		assertThat(new ModuleFitnessChecker().checkHealth(module)).isNull();
	}

	@Test
	public void whenModuleIsReadyToRun_thenCorrespondingHealthFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.isReadyToStart()).thenReturn(true);
		assertThat(new ModuleFitnessChecker().checkHealth(module).getFitness(FitnessFeature.IS_READY_TO_RUN))
				.isEqualTo(true);
	}

	@Test
	public void whenModuleIsNotReadyToRun_thenCorrespondingHealthFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.isReadyToStart()).thenReturn(false);
		assertThat(new ModuleFitnessChecker().checkHealth(module).getFitness(FitnessFeature.IS_READY_TO_RUN))
				.isEqualTo(false);
	}

	@Test
	public void whenCheckingHealthForNullSet_thenReturnEmptyList() {
		Collection<PepperModule> modules = null;
		new ModuleFitnessChecker().checkHealth(modules).isEmpty();
	}

	@Test
	public void whenCheckingHealthForMultipleModules_thenReturnListOfFitnessValues() {
		PepperModule healthyModule = createHealthyModule();
		PepperModule criticalModule = createCriticalModule();

		List<ModuleFitness> fitnesses = new ModuleFitnessChecker()
				.checkHealth(Arrays.asList(healthyModule, criticalModule));
		assertThat(fitnesses.get(0).getOverallFitness()).isEqualTo(Fitness.HEALTHY);
		assertThat(fitnesses.get(1).getOverallFitness()).isEqualTo(Fitness.CRITICAL);
	}

	@Test
	public void whenCheckinHealthForModuleListWithNullEntries_thenIgnoreNullEntries() {
		List<ModuleFitness> fitnesses = new ModuleFitnessChecker()
				.checkHealth(Arrays.asList(mock(PepperModule.class), null, mock(PepperModule.class)));
		assertThat(fitnesses.size()).isEqualTo(2);
	}

	@Test
	public void whenCheckingFitnessForNull_thenReturnNull() {
		PepperModule module = null;
		assertThat(new ModuleFitnessChecker().checkFitness(module)).isNull();
	}

	@Test
	public void whenCheckingFitnessFeatureThrowsException_thenCorrespondingFitnessFeatureShouldBeFalse() {
		PepperModule module = mock(PepperModule.class);
		when(module.getDesc()).thenThrow(new RuntimeException());
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.HAS_NAME))
				.isEqualTo(false);
	}

	@Test
	public void whenModuleHasName_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getName()).thenReturn("MyModule");
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.HAS_NAME)).isEqualTo(true);
	}

	@Test
	public void whenModuleHasNoName_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getName()).thenReturn(null);
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.HAS_NAME))
				.isEqualTo(false);
	}

	@Test
	public void whenModuleHasDescription_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getDesc()).thenReturn("any description");
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.HAS_DESCRIPTION))
				.isEqualTo(true);
	}

	@Test
	public void whenModuleHasNoDescription_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getDesc()).thenReturn(null);
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.HAS_DESCRIPTION))
				.isEqualTo(false);
	}

	@Test
	public void whenModuleHasSupplierContact_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierContact()).thenReturn(URI.createURI("me@mail.com"));
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.HAS_SUPPLIER_CONTACT))
				.isEqualTo(true);
	}

	@Test
	public void whenModuleHasNoSupplierContact_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierContact()).thenReturn(null);
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.HAS_SUPPLIER_CONTACT))
				.isEqualTo(false);
	}

	@Test
	public void whenModuleHasSupplierHomepage_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierHomepage()).thenReturn(URI.createURI("http://me.com"));
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.HAS_SUPPLIER_HP))
				.isEqualTo(true);
	}

	@Test
	public void whenModuleHasNoSupplierHomepage_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierHomepage()).thenReturn(null);
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.HAS_SUPPLIER_HP))
				.isEqualTo(false);
	}

	@Test
	public void whenImporterSupportsIsImportable_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperImporter module = mock(PepperImporter.class);
		when(module.isImportable(any(URI.class))).thenReturn(1.0);
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.IS_IMPORTABLE))
				.isEqualTo(true);
	}

	@Test
	public void whenImporterDoesNotSupportIsImportable_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperImporter importer = mock(PepperImporter.class);
		when(importer.isImportable(any(URI.class))).thenReturn(null);
		assertThat(new ModuleFitnessChecker().checkFitness(importer).getFitness(FitnessFeature.IS_IMPORTABLE))
				.isEqualTo(false);
	}

	@Test
	public void whenImportereHasFormats_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperImporter importer = mock(PepperImporter.class);
		when(importer.getSupportedFormats())
				.thenReturn(Arrays.asList(new FormatDesc().setFormatName("anyFormat").setFormatVersion("any Version")));
		assertThat(new ModuleFitnessChecker().checkFitness(importer).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS))
				.isEqualTo(true);
	}

	@Test
	public void whenImporterHasNoFormats_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperImporter exporter = mock(PepperImporter.class);
		when(exporter.getSupportedFormats()).thenReturn(null);
		assertThat(new ModuleFitnessChecker().checkFitness(exporter).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS))
				.isEqualTo(false);
		when(exporter.getSupportedFormats()).thenReturn(Arrays.asList(new FormatDesc()));
		assertThat(new ModuleFitnessChecker().checkFitness(exporter).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS))
				.isEqualTo(false);
	}

	@Test
	public void whenExportereHasFormats_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperExporter module = mock(PepperExporter.class);
		when(module.getSupportedFormats())
				.thenReturn(Arrays.asList(new FormatDesc().setFormatName("anyFormat").setFormatVersion("any Version")));
		assertThat(new ModuleFitnessChecker().checkFitness(module).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS))
				.isEqualTo(true);
	}

	@Test
	public void whenExporterHasNoFormats_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperExporter exporter = mock(PepperExporter.class);
		when(exporter.getSupportedFormats()).thenReturn(null);
		assertThat(new ModuleFitnessChecker().checkFitness(exporter).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS))
				.isEqualTo(false);
		when(exporter.getSupportedFormats()).thenReturn(Arrays.asList(new FormatDesc()));
		assertThat(new ModuleFitnessChecker().checkFitness(exporter).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS))
				.isEqualTo(false);
	}

	@Test
	public void whenCheckingFitnessForNullSet_thenReturnEmptyList() {
		final Collection<PepperModule> modules = null;
		new ModuleFitnessChecker().checkFitness(modules).isEmpty();
	}

	@Test
	public void whenCheckingFitnessForFitImporter_thenReturnFit() {
		assertThat(new ModuleFitnessChecker().checkFitness(new DoNothingImporter()).getOverallFitness())
				.isEqualTo(Fitness.FIT);
	}

	@Test
	public void whenCheckingFitnessForMultipleModules_thenReturnListOfFitnessValues() {
		final PepperModule fitModule = new DoNothingImporter();
		final PepperModule healthyModule = createHealthyModule();
		final PepperModule criticalModule = createCriticalModule();

		final List<ModuleFitness> fitnesses = new ModuleFitnessChecker()
				.checkFitness(Arrays.asList(fitModule, healthyModule, criticalModule));
		assertThat(fitnesses.get(0).getOverallFitness()).isEqualTo(Fitness.FIT);
		assertThat(fitnesses.get(1).getOverallFitness()).isEqualTo(Fitness.HEALTHY);
		assertThat(fitnesses.get(2).getOverallFitness()).isEqualTo(Fitness.CRITICAL);
	}

	@Test
	public void whenCheckinFitnessForModuleListWithNullEntries_thenIgnoreNullEntries() {
		final List<ModuleFitness> fitnesses = new ModuleFitnessChecker()
				.checkFitness(Arrays.asList(mock(PepperModule.class), null, mock(PepperModule.class)));
		assertThat(fitnesses.size()).isEqualTo(2);
	}

	@Test(expected = PepperFWException.class)
	public void whenSelfTestAndNoPepperWasSpecified_thenFail() {
		final PepperImporter importer = mock(PepperImporter.class);
		new ModuleFitnessChecker(null).selfTest(importer, null);
	}

	@Test
	public void whenSelfTestAndNoPepperModuleWasSpecified_thenHasSelfTestIsFalse() {
		final Pepper pepper = mock(Pepper.class);
		final ModuleFitness fitness = new ModuleFitnessChecker(pepper).selfTest(null, null);

		assertThat(fitness).isNull();
	}

	@Test
	public void whenSelfTestAndPepperModuleReturnsEmptyTestDesc_thenHasSelfTestIsFalse() {
		final Pepper pepper = mock(Pepper.class);
		final PepperImporter importer = mock(PepperImporter.class);
		when(importer.getSelfTestDesc()).thenReturn(null);
		final ModuleFitness fitness = new ModuleFitnessChecker(pepper).selfTest(importer, null);

		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isFalse();
	}

	@Test
	public void whenSelfTestAndTestDescIsNotValid_thenHasSelfTestIsTrue() {
		final Pepper pepper = mock(Pepper.class);
		final PepperImporter importer = mock(PepperImporter.class);
		final SelfTestDesc desc = mock(SelfTestDesc.class);
		when(importer.getSelfTestDesc()).thenReturn(desc);
		final ModuleFitness fitness = new ModuleFitnessChecker(pepper).selfTest(importer, null);

		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
	}

	@Test
	public void whenSelfTestAndModuleIsImporterAndEverythingIsOk_thenAllFeaturesShouldBeTrue() {
		final Pepper pepper = PepperTestUtil.createDefaultPepper();
		final SelfTestDesc desc = mock(SelfTestDesc.class);
		when(desc.getInputCorpusPath())
				.thenReturn(URI.createFileURI(PepperTestUtil.getTestResources() + "selfTest/sampleCorpus/in/"));
		when(desc.getExpectedCorpusPath())
				.thenReturn(URI.createFileURI(PepperTestUtil.getTestResources() + "selfTest/sampleCorpus/out/"));
		when(desc.isValid(Matchers.anyListOf(String.class))).thenReturn(true);
		when(desc.compare(any(SaltProject.class), any(SaltProject.class))).thenReturn(true);
		final PepperImporter importer = spy(DoNothingImporter.class);

		doReturn(desc).when(importer).getSelfTestDesc();
		when(importer.isImportable(any(URI.class))).thenReturn(1.0);
		when(importer.getSaltProject()).thenReturn(SampleGenerator.createSaltProject());

		final ModuleFitness fitness = new ModuleFitnessChecker(pepper).selfTest(importer, null);

		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
	}

	@Test
	public void whenSelfTestAndModuleIsImporterAndImportedCorpusIsNotEqualToExpectedOne_thenPassedSelfTestShouldBeFalse() {
		final Pepper pepper = PepperTestUtil.createDefaultPepper();
		final SelfTestDesc desc = mock(SelfTestDesc.class);
		when(desc.getInputCorpusPath())
				.thenReturn(URI.createFileURI(PepperTestUtil.getTestResources() + "selfTest/sampleCorpus/in/"));
		when(desc.getExpectedCorpusPath())
				.thenReturn(URI.createFileURI(PepperTestUtil.getTestResources() + "selfTest/sampleCorpus/out/"));
		when(desc.isValid(Matchers.anyListOf(String.class))).thenReturn(true);
		when(desc.compare(any(SaltProject.class), any(SaltProject.class))).thenReturn(false);
		final PepperImporter importer = spy(DoNothingImporter.class);
		doReturn(desc).when(importer).getSelfTestDesc();
		when(importer.isImportable(any(URI.class))).thenReturn(1.0);
		when(importer.getSaltProject()).thenReturn(SampleGenerator.createSaltProject());

		final ModuleFitness fitness = new ModuleFitnessChecker(pepper).selfTest(importer, null);

		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isFalse();
		assertThat(fitness.getFitness(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
	}

	@Test
	public void whenSelfTestAndModuleIsManipulatorAndEverythingIsOk_thenFeaturesShouldBeTrue() {
		final Pepper pepper = PepperTestUtil.createDefaultPepper();
		final SelfTestDesc desc = mock(SelfTestDesc.class);
		when(desc.getInputCorpusPath())
				.thenReturn(URI.createFileURI(PepperTestUtil.getTestResources() + "selfTest/sampleCorpus/in/"));
		when(desc.getExpectedCorpusPath())
				.thenReturn(URI.createFileURI(PepperTestUtil.getTestResources() + "selfTest/sampleCorpus/out/"));
		when(desc.isValid(Matchers.anyListOf(String.class))).thenReturn(true);
		when(desc.compare(any(SaltProject.class), any(SaltProject.class))).thenReturn(false);
		final PepperManipulator manipulator = spy(DoNothingManipulator.class);
		doReturn(desc).when(manipulator).getSelfTestDesc();
		when(manipulator.getSaltProject()).thenReturn(SampleGenerator.createSaltProject());

		final ModuleFitness fitness = new ModuleFitnessChecker(pepper).selfTest(manipulator, null);

		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isFalse();
		assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
	}

	@Test
	public void whenSelfTestAndModuleIsExporterAndEverythingIsOk_thenFeaturesShouldBeTrue() {
		final Pepper pepper = PepperTestUtil.createDefaultPepper();
		final SelfTestDesc desc = mock(SelfTestDesc.class);
		when(desc.getInputCorpusPath())
				.thenReturn(URI.createFileURI(PepperTestUtil.getTestResources() + "selfTest/sampleCorpus/in/"));
		when(desc.getExpectedCorpusPath())
				.thenReturn(URI.createFileURI(PepperTestUtil.getTestResources() + "selfTest/sampleCorpus/out/"));
		when(desc.isValid(Matchers.anyListOf(String.class))).thenReturn(true);
		when(desc.compare(any(SaltProject.class), any(SaltProject.class))).thenReturn(false);
		final PepperExporter exporter = spy(DoNothingExporter.class);
		doReturn(desc).when(exporter).getSelfTestDesc();
		when(exporter.getSaltProject()).thenReturn(SampleGenerator.createSaltProject());

		final ModuleFitness fitness = new ModuleFitnessChecker(pepper).selfTest(exporter, null);

		assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
		assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isFalse();
	}
}

package org.corpus_tools.pepper.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.Fitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ModuleFitnessCheckerTest {

	@Test
	public void whenCheckingHealthForNull_thenReturnNull() {
		PepperModule module = null;
		assertThat(ModuleFitnessChecker.checkHealth(module)).isNull();
	}

	@Test
	public void whenModuleIsReadyToRun_thenCorrespondingHealthFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.isReadyToStart()).thenReturn(true);
		assertThat(ModuleFitnessChecker.checkHealth(module).getFitness(FitnessFeature.IS_READY_TO_RUN)).isEqualTo(true);
	}

	@Test
	public void whenModuleIsNotReadyToRun_thenCorrespondingHealthFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.isReadyToStart()).thenReturn(false);
		assertThat(ModuleFitnessChecker.checkHealth(module).getFitness(FitnessFeature.IS_READY_TO_RUN)).isEqualTo(false);
	}

	@Test
	public void whenCheckingHealthForNullSet_thenReturnEmptyList() {
		Collection<PepperModule> modules = null;
		ModuleFitnessChecker.checkHealth(modules).isEmpty();
	}

	@Test
	public void whenCheckingHealthForMultipleModules_thenReturnListOfFitnessValues() {
		PepperModule healthyModule = mock(PepperImporter.class);
		when(healthyModule.getSupplierHomepage()).thenReturn(null);
		when(healthyModule.getSupplierContact()).thenReturn(URI.createURI(""));
		when(healthyModule.isReadyToStart()).thenReturn(true);

		PepperModule criticalModule = mock(PepperImporter.class);
		when(criticalModule.getSupplierHomepage()).thenReturn(URI.createURI(""));
		when(criticalModule.getSupplierContact()).thenReturn(URI.createURI(""));
		when(criticalModule.isReadyToStart()).thenReturn(false);

		List<ModuleFitness> fitnesses = ModuleFitnessChecker.checkHealth(Arrays.asList(healthyModule, criticalModule));
		assertThat(fitnesses.get(0).getOverallFitness()).isEqualTo(Fitness.HEALTHY);
		assertThat(fitnesses.get(1).getOverallFitness()).isEqualTo(Fitness.CRITICAL);
	}

	@Test
	public void whenCheckinHealthForModuleListWithNullEntries_thenIgnoreNullEntries() {
		List<ModuleFitness> fitnesses = ModuleFitnessChecker.checkHealth(Arrays.asList(mock(PepperModule.class), null, mock(PepperModule.class)));
		assertThat(fitnesses.size()).isEqualTo(2);
	}

	@Test
	public void whenCheckingFitnessForNull_thenReturnNull() {
		PepperModule module = null;
		assertThat(ModuleFitnessChecker.checkFitness(module)).isNull();
	}

	@Test
	public void whenModuleHasSupplierContact_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierContact()).thenReturn(URI.createURI(""));
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.SUPPORTS_SUPPLIER_CONTACT)).isEqualTo(true);
	}

	@Test
	public void whenModuleHasNoSupplierContact_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierContact()).thenReturn(null);
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.SUPPORTS_SUPPLIER_CONTACT)).isEqualTo(false);
	}

	@Test
	public void whenModuleHasSupplierHomepage_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierHomepage()).thenReturn(URI.createURI(""));
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.SUPPORTS_SUPPLIER_HP)).isEqualTo(true);
	}

	@Test
	public void whenModuleHasNoSupplierHomepage_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierHomepage()).thenReturn(null);
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.SUPPORTS_SUPPLIER_HP)).isEqualTo(false);
	}

	@Test
	public void whenImporterSupportsIsImportable_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperImporter module = mock(PepperImporter.class);
		when(module.isImportable(any(URI.class))).thenReturn(1.0);
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.IS_IMPORTABLE)).isEqualTo(true);
	}

	@Test
	public void whenImporterDoesNotSupportIsImportable_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperImporter module = mock(PepperImporter.class);
		when(module.isImportable(any(URI.class))).thenReturn(null);
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.IS_IMPORTABLE)).isEqualTo(false);
	}

	@Test
	public void whenCheckingFitnessForNullSet_thenReturnEmptyList() {
		Collection<PepperModule> modules = null;
		ModuleFitnessChecker.checkFitness(modules).isEmpty();
	}

	@Test
	public void whenCheckingFitnessForFitModule_thenReturnFit() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierContact()).thenReturn(URI.createURI(""));
		when(module.getSupplierHomepage()).thenReturn(URI.createURI(""));
		when(module.isReadyToStart()).thenReturn(true);
		assertThat(ModuleFitnessChecker.checkFitness(module).getOverallFitness()).isEqualTo(Fitness.FIT);
	}

	@Test
	public void whenCheckingFitnessForFitImporter_thenReturnFit() {
		PepperImporter module = mock(PepperImporter.class);
		when(module.getSupplierContact()).thenReturn(URI.createURI(""));
		when(module.getSupplierHomepage()).thenReturn(URI.createURI(""));
		when(module.isReadyToStart()).thenReturn(true);
		when(module.isImportable(any(URI.class))).thenReturn(1.0);
		assertThat(ModuleFitnessChecker.checkFitness(module).getOverallFitness()).isEqualTo(Fitness.FIT);
	}

	@Test
	public void whenCheckingFitnessForMultipleModules_thenReturnListOfFitnessValues() {
		PepperModule fitModule = mock(PepperImporter.class);
		when(fitModule.getSupplierContact()).thenReturn(URI.createURI(""));
		when(fitModule.getSupplierHomepage()).thenReturn(URI.createURI(""));
		when(fitModule.isReadyToStart()).thenReturn(true);

		PepperModule healthyModule = mock(PepperImporter.class);
		when(healthyModule.getSupplierHomepage()).thenReturn(null);
		when(healthyModule.getSupplierContact()).thenReturn(URI.createURI(""));
		when(healthyModule.isReadyToStart()).thenReturn(true);

		PepperModule criticalModule = mock(PepperImporter.class);
		when(criticalModule.getSupplierHomepage()).thenReturn(URI.createURI(""));
		when(criticalModule.getSupplierContact()).thenReturn(URI.createURI(""));
		when(criticalModule.isReadyToStart()).thenReturn(false);

		List<ModuleFitness> fitnesses = ModuleFitnessChecker.checkFitness(Arrays.asList(fitModule, healthyModule, criticalModule));
		assertThat(fitnesses.get(0).getOverallFitness()).isEqualTo(Fitness.FIT);
		assertThat(fitnesses.get(1).getOverallFitness()).isEqualTo(Fitness.HEALTHY);
		assertThat(fitnesses.get(2).getOverallFitness()).isEqualTo(Fitness.CRITICAL);
	}

	@Test
	public void whenCheckinFitnessForModuleListWithNullEntries_thenIgnoreNullEntries() {
		List<ModuleFitness> fitnesses = ModuleFitnessChecker.checkFitness(Arrays.asList(mock(PepperModule.class), null, mock(PepperModule.class)));
		assertThat(fitnesses.size()).isEqualTo(2);
	}
}

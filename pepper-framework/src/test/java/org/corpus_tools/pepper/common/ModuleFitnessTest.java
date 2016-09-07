package org.corpus_tools.pepper.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.corpus_tools.pepper.common.ModuleFitness.Fitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.common.ModuleFitness.ModuleFitnessBuilder;
import org.junit.Before;
import org.junit.Test;

public class ModuleFitnessTest {

	private ModuleFitness fixture;

	@Before
	public void beforeEach() {
		fixture = new ModuleFitness("myModule");
	}

	@Test
	public void moduleFitnessAlwaysHasAName() {
		assertThat(fixture.getModuleName()).isNotEmpty();
	}

	@Test
	public void whenSetingEmptyFeature_thenNothingShouldHappen() {
		fixture.setFeature(null, true);
	}

	@Test
	public void whenSettingFitnessFeatureToTrue_thenFeatureShouldBeTrue() {
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, true);
		assertThat(fixture.getFitness(FitnessFeature.IS_IMPORTABLE)).isEqualTo(true);
	}

	@Test
	public void whenSetingFitnessFeatureToFalse_thenFeatureShouldBeFalse() {
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, false);
		assertThat(fixture.getFitness(FitnessFeature.IS_IMPORTABLE)).isEqualTo(false);
	}

	@Test
	public void whenSetingAFeatureTwice_thenTheFeatureMustHaveLastValue() {
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, true);
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, false);
		assertThat(fixture.getFitness(FitnessFeature.IS_IMPORTABLE)).isEqualTo(false);
	}

	@Test
	public void whenNoFeatureFitnessExists_thenOverallFitnessIsFIT() {
		assertThat(fixture.getOverallFitness()).isEqualTo(Fitness.FIT);
	}

	@Test
	public void whenHealthFeatureFitnessIsFalse_thenOverallFitnessIsCritical() {
		fixture.setFeature(FitnessFeature.HAS_SUPPLIER_CONTACT, true);
		fixture.setFeature(FitnessFeature.IS_READY_TO_RUN, false);
		fixture.setFeature(FitnessFeature.HAS_SUPPLIER_HP, false);
		assertThat(fixture.getOverallFitness()).isEqualTo(Fitness.CRITICAL);
	}

	@Test
	public void whenAtLeastOneFitnessFeatureIsFalse_thenOverallFitnessIsHealthy() {
		fixture.setFeature(FitnessFeature.HAS_SUPPLIER_CONTACT, true);
		fixture.setFeature(FitnessFeature.HAS_SUPPLIER_HP, false);
		assertThat(fixture.getOverallFitness()).isEqualTo(Fitness.HEALTHY);
	}

	@Test
	public void whenOnlyHealthFeaturesExistAndTheyAreTrue_thenReturnHealthy() {
		fixture.setFeature(FitnessFeature.IS_READY_TO_RUN, true);
		assertThat(fixture.getOverallFitness()).isEqualTo(Fitness.HEALTHY);
	}

	@Test
	public void whenFitnessFeatureIsSetToTrue_thenOverallFitnessIsFIT() {
		fixture.setFeature(FitnessFeature.HAS_SUPPLIER_CONTACT, true);
		assertThat(fixture.getOverallFitness()).isEqualTo(Fitness.FIT);
	}

	@Test
	public void whenTwoModuleFitnessObjectsAreEmpty_thenTheyShouldBeEqual() {
		ModuleFitness template = new ModuleFitnessBuilder("myModule").build();
		assertThat(fixture).isEqualTo(template);
		assertThat(fixture.hashCode()).isEqualTo(template.hashCode());
	}

	@Test
	public void whenTwoModuleFitnessObjectsHaveDifferentModuleNames_thenTheyShouldBeUnEqual() {
		ModuleFitness template = new ModuleFitnessBuilder("notMyModule").build();
		assertThat(fixture).isNotEqualTo(template);
		assertThat(fixture.hashCode()).isNotEqualTo(template.hashCode());
	}

	@Test
	public void whenTwoModuleFitnessObjectsDifferInFeatures_thenTheyShouldBeUnEqual() {
		ModuleFitness template = new ModuleFitnessBuilder("myModule")
				.addFitnessFeature(FitnessFeature.IS_IMPORTABLE, true).build();
		assertThat(fixture).isNotEqualTo(template);
		assertThat(fixture.hashCode()).isNotEqualTo(template.hashCode());
	}

	@Test
	public void whenTwoModuleFitnessObjectsDifferInFeatureFitness_thenTheyShouldBeUnEqual() {
		ModuleFitness template = new ModuleFitnessBuilder("myModule")
				.addFitnessFeature(FitnessFeature.IS_IMPORTABLE, true).build();
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, false);
		assertThat(fixture).isNotEqualTo(template);
		assertThat(fixture.hashCode()).isNotEqualTo(template.hashCode());
	}

	@Test
	public void whenGettingHealthFeatures_thenReturnHeatlthFeatures() {
		assertThat(FitnessFeature.getHealthFeatures()).containsExactlyInAnyOrder(FitnessFeature.HAS_NAME,
				FitnessFeature.IS_READY_TO_RUN, FitnessFeature.HAS_PASSED_SELFTEST,
				FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA, FitnessFeature.IS_VALID_SELFTEST_DATA);
	}

	@Test
	public void whenGettingFitnessFeatures_thenReturnFitnessFeatures() {
		assertThat(FitnessFeature.getFitnessFeatures()).containsExactlyInAnyOrder(FitnessFeature.IS_IMPORTABLE,
				FitnessFeature.HAS_SUPPLIER_CONTACT, FitnessFeature.HAS_SUPPLIER_HP, FitnessFeature.HAS_DESCRIPTION,
				FitnessFeature.HAS_SUPPORTED_FORMATS, FitnessFeature.HAS_SELFTEST);
	}

	@Test
	public void whenToString_thenReturnNonEmptyString() {
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, true);
		fixture.setFeature(FitnessFeature.IS_READY_TO_RUN, true);
		assertThat(fixture.toString()).isNotEmpty();
	}
}

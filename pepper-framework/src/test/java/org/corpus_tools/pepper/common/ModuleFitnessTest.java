package org.corpus_tools.pepper.common;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import org.corpus_tools.pepper.common.ModuleFitness.Fitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.common.ModuleFitness.ModuleFitnessBuilder;

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
	public void whenSetingAFeature_thenTheFeatureMustExist() {
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, Fitness.FIT);
		assertThat(fixture.getFitness(FitnessFeature.IS_IMPORTABLE)).isEqualTo(Fitness.FIT);
	}

	@Test
	public void whenSetingNullFeature_thenNothingShouldHappen() {
		fixture.setFeature(null, Fitness.FIT);
	}

	@Test
	public void whenSetingAnOptionalFeatureWithCritical_thenFeatureShouldBeUnfit() {
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, Fitness.CRITICAL);
		assertThat(fixture.getFitness(FitnessFeature.IS_IMPORTABLE)).isEqualTo(Fitness.HEALTHY);
	}

	@Test
	public void whenSetingAFeatureTwice_thenTheFeatureMustHaveLastFitnessValue() {
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, Fitness.FIT);
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, Fitness.HEALTHY);
		assertThat(fixture.getFitness(FitnessFeature.IS_IMPORTABLE)).isEqualTo(Fitness.HEALTHY);
	}
	
	@Test
	public void whenSettingFitnessFeatureToTrue_thenItMustBeFit(){
		fixture.setFeature(FitnessFeature.SUPPORTS_SUPPLIER_CONTACT, true);
		assertThat(fixture.getFitness(FitnessFeature.SUPPORTS_SUPPLIER_CONTACT)).isEqualTo(Fitness.FIT);
	}
	@Test
	public void whenSettingFitnessFeatureToFalse_thenItMustBeHealthy(){
		fixture.setFeature(FitnessFeature.SUPPORTS_SUPPLIER_CONTACT, false);
		assertThat(fixture.getFitness(FitnessFeature.SUPPORTS_SUPPLIER_CONTACT)).isEqualTo(Fitness.HEALTHY);
	}
	@Test
	public void whenSettingHealthFeatureTotrue_thenItMustBeHealthy(){
		fixture.setFeature(FitnessFeature.IS_READY_TO_RUN, true);
		assertThat(fixture.getFitness(FitnessFeature.IS_READY_TO_RUN)).isEqualTo(Fitness.HEALTHY);
	}
	
	@Test
	public void whenSettingHealthFeatureToFalse_thenItMustBeCritical(){
		fixture.setFeature(FitnessFeature.IS_READY_TO_RUN, false);
		assertThat(fixture.getFitness(FitnessFeature.IS_READY_TO_RUN)).isEqualTo(Fitness.CRITICAL);
	}

	@Test
	public void whenNoFeatureFitnessExists_thenOverallFitnessIsFIT() {
		assertThat(fixture.getOverallFitness()).isEqualTo(Fitness.FIT);
	}

	@Test
	public void whenImportantFeatureFitnessIsCritical_thenOverallFitnessIsCritical() {
		fixture.setFeature(FitnessFeature.SUPPORTS_SUPPLIER_CONTACT, Fitness.FIT);
		fixture.setFeature(FitnessFeature.IS_READY_TO_RUN, Fitness.CRITICAL);
		fixture.setFeature(FitnessFeature.SUPPORTS_SUPPLIER_HP, Fitness.HEALTHY);
		assertThat(fixture.getOverallFitness()).isEqualTo(Fitness.CRITICAL);
	}

	@Test
	public void whenLowestOptionalFeatureFitnessIsUnfit_thenOverallFitnessIsUnfit() {
		fixture.setFeature(FitnessFeature.SUPPORTS_SUPPLIER_CONTACT, Fitness.FIT);
		fixture.setFeature(FitnessFeature.SUPPORTS_SUPPLIER_HP, Fitness.HEALTHY);
		assertThat(fixture.getOverallFitness()).isEqualTo(Fitness.HEALTHY);
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
		ModuleFitness template = new ModuleFitnessBuilder("myModule").addFitnessFeature(FitnessFeature.IS_IMPORTABLE, Fitness.FIT).build();
		assertThat(fixture).isNotEqualTo(template);
		assertThat(fixture.hashCode()).isNotEqualTo(template.hashCode());
	}

	@Test
	public void whenTwoModuleFitnessObjectsDifferInFeatureFitness_thenTheyShouldBeUnEqual() {
		ModuleFitness template = new ModuleFitnessBuilder("myModule").addFitnessFeature(FitnessFeature.IS_IMPORTABLE, Fitness.FIT).build();
		fixture.setFeature(FitnessFeature.IS_IMPORTABLE, Fitness.HEALTHY);
		assertThat(fixture).isNotEqualTo(template);
		assertThat(fixture.hashCode()).isNotEqualTo(template.hashCode());
	}

	@Test
	public void whenGettingImportantFeatures_thenReturnImportantFeatures() {
		assertThat(FitnessFeature.getHealthFeatures()).containsExactlyInAnyOrder(FitnessFeature.IS_READY_TO_RUN);
	}

	@Test
	public void whenGettingOptionalFeatures_thenReturnOptionalFeatures() {
		assertThat(FitnessFeature.getFitnessFeatures()).containsExactlyInAnyOrder(FitnessFeature.IS_IMPORTABLE, FitnessFeature.SUPPORTS_SUPPLIER_CONTACT, FitnessFeature.SUPPORTS_SUPPLIER_HP);
	}
}

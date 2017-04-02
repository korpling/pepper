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
package org.corpus_tools.pepper.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;

/**
 * An object determining the fitness of a single Pepper module. Multiple single
 * fitness values forms one combined value determining the overall fitness.
 */
public class ModuleFitness {
	public static enum Fitness {
		/**
		 * An entire module or single feature is absolutely fit, so no problems
		 * are detected
		 **/
		FIT,
		/**
		 * Only used for overall fitness of a module rather than for a single
		 * value and means, the health features are ok, but fitness features
		 * aren't
		 **/
		HEALTHY,
		/**
		 * An entire module or single feature is absolutely unfit, so some
		 * problems are detected, when a module is critical, than it is not
		 * healthy.
		 **/
		CRITICAL
	}

	public static enum FitnessFeature {
		/**
		 * Name of health feature determining whether a module's name is set.
		 */
		HAS_NAME,
		/**
		 * Name of health feature determining whether the module is ready to be
		 * ran in Pepper.
		 */
		IS_READY_TO_RUN,
		/**
		 * Name of health feature determining whether the module passes the self
		 * test, if a self test is implemented.
		 */
		HAS_PASSED_SELFTEST,
		/**
		 * Name of health feature determining whether the module is able to
		 * import the self test data, if a self test is implemented and module
		 * is an importer.
		 */
		IS_IMPORTABLE_SEFTEST_DATA,
		/**
		 * Name of health feature determining whether the module the self test
		 * data are valid, if a self test is implemented and module is an
		 * importer or a manipulator.
		 */
		IS_VALID_SELFTEST_DATA,
		/**
		 * Name of fitness feature determining whether
		 * {@link PepperModule#getSupplierContact()} is supported.
		 */
		HAS_SUPPLIER_CONTACT,
		/**
		 * Name of fitness feature determining whether
		 * {@link PepperModule#getSupplierHomepage()} is supported.
		 */
		HAS_SUPPLIER_HP,
		/**
		 * Name of fitness feature determining whether
		 * {@link PepperModule#getDesc()} is supported
		 */
		HAS_DESCRIPTION,
		/**
		 * Name of fitness feature determining whether
		 * {@link PepperImporter#getSupportedFormats()}
		 * {@link PepperExporter#getSupportedFormats()} is supported. <br/>
		 * Only for {@link PepperImporter} and {@link PepperExporter}.
		 */
		HAS_SUPPORTED_FORMATS,
		/**
		 * Name of fitness feature determining whether the module provides a
		 * self test.
		 **/
		HAS_SELFTEST,
		/**
		 * Name of fitness feature determining whether
		 * {@link PepperImporter#isImportable(org.eclipse.emf.common.util.URI)}
		 * is implemented. <br/>
		 * Only for {@link PepperImporter}.
		 **/
		IS_IMPORTABLE;

		private static final Set<FitnessFeature> FITNESS_FEATURES = new HashSet<>();
		private static final Set<FitnessFeature> HEALTH_FEATURES = new HashSet<>();

		/**
		 * Returns the names of the fitness features taking part at health test.
		 * These are the features which makes a module able to be ran.
		 */
		public static Collection<FitnessFeature> getHealthFeatures() {
			if (HEALTH_FEATURES.size() < 1) {
				HEALTH_FEATURES.add(IS_READY_TO_RUN);
				HEALTH_FEATURES.add(HAS_NAME);
				HEALTH_FEATURES.add(HAS_PASSED_SELFTEST);
				HEALTH_FEATURES.add(IS_IMPORTABLE_SEFTEST_DATA);
				HEALTH_FEATURES.add(IS_VALID_SELFTEST_DATA);
			}
			return HEALTH_FEATURES;
		}

		/**
		 * Returns the names of the fitness feature.
		 */
		public static Collection<FitnessFeature> getFitnessFeatures() {
			if (FITNESS_FEATURES.size() < 1) {

				FITNESS_FEATURES.add(HAS_SUPPLIER_CONTACT);
				FITNESS_FEATURES.add(HAS_SUPPLIER_HP);
				FITNESS_FEATURES.add(HAS_DESCRIPTION);
				FITNESS_FEATURES.add(HAS_SUPPORTED_FORMATS);
				FITNESS_FEATURES.add(IS_IMPORTABLE);
				FITNESS_FEATURES.add(HAS_SELFTEST);
			}
			return FITNESS_FEATURES;
		}
	}

	public ModuleFitness(final String moduleName) {
		this.moduleName = moduleName;
	}

	private String moduleName;

	public String getModuleName() {
		return moduleName;
	}

	private final Map<FitnessFeature, Boolean> fitnessMap = new HashMap<>();

	/**
	 * Sets the value to the specified feature. If the {@link FitnessFeature} is
	 * a health feature, true means {@link Fitness#FIT} and false means
	 * {@link Fitness#HEALTHY}. If the {@link FitnessFeature} is a fitness
	 * feature, true means {@link Fitness#HEALTHY} and false means
	 * {@link Fitness#CRITICAL}.
	 */
	public void setFeature(final FitnessFeature feature, boolean value) {
		if (feature != null) {
			fitnessMap.put(feature, Boolean.valueOf(value));
		}
	}

	/**
	 * Returns value for specified fitness feature. When the fitness feature is
	 * not contained in list, null is returned.
	 */
	public Boolean getFitness(final FitnessFeature feature) {
		return fitnessMap.get(feature);
	}

	/**
	 * Returns a combined fitness value representing all single values. A module
	 * is: {@link Fitness#FIT}
	 * <ul>
	 * <li>{@link Fitness#FIT}, when all single fitness feature are fit</li>
	 * <li>{@link Fitness#HEALTHY}, when all health feature are fit, but at
	 * least one of the fitness ones isn't</li>
	 * <li>{@link Fitness#CRITICAL}, at least one health feature is not fit</li>
	 * </ul>
	 * 
	 * @return the overall fitness value
	 */
	public Fitness getOverallFitness() {
		if (fitnessMap.size() == 0) {
			return Fitness.FIT;
		}
		Fitness overallFitness = Fitness.CRITICAL;
		for (FitnessFeature healthFeature : FitnessFeature.getHealthFeatures()) {
			Boolean fitness = fitnessMap.get(healthFeature);
			if (Boolean.FALSE.equals(fitness)) {
				return overallFitness;
			}
		}
		overallFitness = Fitness.HEALTHY;
		boolean hasFitnessValue = false;
		for (FitnessFeature fitnessFeature : FitnessFeature.getFitnessFeatures()) {
			Boolean fitness = fitnessMap.get(fitnessFeature);
			if (Boolean.FALSE.equals(fitness)) {
				return overallFitness;
			} else if (Boolean.TRUE.equals(fitness)) {
				hasFitnessValue = true;
			}
		}
		if (hasFitnessValue) {
			overallFitness = Fitness.FIT;
		}
		return (overallFitness);
	}

	@Override
	public String toString() {
		final StringBuilder retVal = new StringBuilder();
		retVal.append(moduleName);
		retVal.append(":");
		for (FitnessFeature healthFeature : FitnessFeature.getHealthFeatures()) {
			retVal.append(healthFeature);
			retVal.append("=");
			retVal.append(getFitness(healthFeature));
			retVal.append(", ");
		}
		for (FitnessFeature fitnessFeature : FitnessFeature.getFitnessFeatures()) {
			retVal.append(fitnessFeature);
			retVal.append("=");
			retVal.append(getFitness(fitnessFeature));
			retVal.append(", ");
		}
		return retVal.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fitnessMap == null) ? 0 : fitnessMap.hashCode());
		result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModuleFitness other = (ModuleFitness) obj;
		if (fitnessMap == null) {
			if (other.fitnessMap != null)
				return false;
		} else if (!fitnessMap.equals(other.fitnessMap))
			return false;
		if (moduleName == null) {
			if (other.moduleName != null)
				return false;
		} else if (!moduleName.equals(other.moduleName))
			return false;
		return true;
	}

	/**
	 * A simple builder to create {@link ModuleFitness} objects.
	 */
	public static class ModuleFitnessBuilder {
		private ModuleFitness moduleFitness;

		public ModuleFitnessBuilder(String moduleName) {
			moduleFitness = new ModuleFitness(moduleName);
		}

		public ModuleFitnessBuilder addFitnessFeature(final FitnessFeature feature, final boolean fitness) {
			moduleFitness.setFeature(feature, fitness);
			return this;
		}

		public ModuleFitness build() {
			return moduleFitness;
		}
	}
}

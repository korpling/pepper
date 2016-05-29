package org.corpus_tools.pepper.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;

/**
 * An object determining the fitness of a single Pepper module. Multiple single
 * fitness values forms one combined value determining the overall fitness.
 * 
 * @author florian
 *
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
		 * Name of fitness feature determining whether
		 * {@link PepperImporter#isImportable(org.eclipse.emf.common.util.URI)}
		 * is implemented.
		 **/
		IS_IMPORTABLE,
		/**
		 * Name of fitness feature determining whether
		 * {@link PepperModule#getSupplierContact()} is supported.
		 */
		SUPPORTS_SUPPLIER_CONTACT,
		/**
		 * Name of fitness feature determining whether
		 * {@link PepperModule#getSupplierHomepage()} is supported.
		 */
		SUPPORTS_SUPPLIER_HP,
		/**
		 * Name of fitness feature determining whether the module is ready to be
		 * ran in Pepper.
		 */
		IS_READY_TO_RUN;

		private static final Set<FitnessFeature> FITNESS_FEATURES = new HashSet<>();
		private static final Set<FitnessFeature> HEALTH_FEATURES = new HashSet<>();

		/**
		 * Returns the names of the fitness features taking part at health test.
		 * These are the features which makes a module able to be ran.
		 * 
		 * @return
		 */
		public static Collection<FitnessFeature> getHealthFeatures() {
			if (HEALTH_FEATURES.size() < 1) {
				HEALTH_FEATURES.add(IS_READY_TO_RUN);
			}
			return HEALTH_FEATURES;
		}

		/**
		 * Returns the names of the fitness feature.
		 * 
		 * @return
		 */
		public static Collection<FitnessFeature> getFitnessFeatures() {
			if (FITNESS_FEATURES.size() < 1) {
				FITNESS_FEATURES.add(IS_IMPORTABLE);
				FITNESS_FEATURES.add(SUPPORTS_SUPPLIER_CONTACT);
				FITNESS_FEATURES.add(SUPPORTS_SUPPLIER_HP);
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

	private final Map<FitnessFeature, Fitness> fitnessMap = new HashMap<>();

	/**
	 * Adds a {@link FitnessFeature} value. A health {@link FitnessFeature} can
	 * not be set to {@link Fitness#CRITICAL}. If it was set to
	 * {@link Fitness#CRITICAL}, its value is changed to {@link Fitness#HEALTHY}
	 * . Parallel a fitness feature cannot be set to {@link Fitness#CRITICAL}.
	 * If it was set to {@link Fitness#CRITICAL}, its value is changed to
	 * {@link Fitness#HEALTHY}.
	 * 
	 * @param feature
	 * @param fitness
	 */
	public void setFeature(final FitnessFeature feature, Fitness fitness) {
		if (feature != null) {
			if (FitnessFeature.getFitnessFeatures().contains(feature) && Fitness.CRITICAL.equals(fitness)) {
				fitness = Fitness.HEALTHY;
			}
			this.fitnessMap.put(feature, fitness);
		}
	}

	/**
	 * Sets the value to the specified feature. If the {@link FitnessFeature} is
	 * a health feature, true means {@link Fitness#FIT} and false means
	 * {@link Fitness#HEALTHY}. If the {@link FitnessFeature} is a fitness
	 * feature, true means {@link Fitness#HEALTHY} and false means
	 * {@link Fitness#CRITICAL}.
	 * 
	 * @param feature
	 * @param value
	 */
	public void setFeature(final FitnessFeature feature, boolean value) {
		if (feature != null) {
			Fitness fitness= null;
			if (FitnessFeature.getHealthFeatures().contains(feature)) {
				if(value){
					fitness = Fitness.HEALTHY;
				}else{
					fitness = Fitness.CRITICAL;
				}
			}else{
				if(value){
					fitness = Fitness.FIT;
				}else{
					fitness = Fitness.HEALTHY;
				}
			}
			setFeature(feature, fitness);
		}
	}

	public Fitness getFitness(final FitnessFeature feature) {
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
		for (FitnessFeature feature : FitnessFeature.getHealthFeatures()) {
			if (Fitness.CRITICAL.equals(fitnessMap.get(feature))) {
				return Fitness.CRITICAL;
			}
		}
		Fitness lowestFitness = Fitness.FIT;
		for (FitnessFeature feature : FitnessFeature.getFitnessFeatures()) {
			Fitness currentFitness = fitnessMap.get(feature);
			if (currentFitness != null && lowestFitness.compareTo(currentFitness) < 0) {
				lowestFitness = fitnessMap.get(feature);
			}
		}
		return lowestFitness;
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
	 * Asimple builder to create {@link ModuleFitness} objects.
	 * 
	 * @author florian
	 *
	 */
	public static class ModuleFitnessBuilder {
		private ModuleFitness moduleFitness;

		public ModuleFitnessBuilder(String moduleName) {
			moduleFitness = new ModuleFitness(moduleName);
		}

		public ModuleFitnessBuilder addFitnessFeature(final FitnessFeature feature, final Fitness fitness) {
			moduleFitness.setFeature(feature, fitness);
			return this;
		}

		public ModuleFitness build() {
			return moduleFitness;
		}
	}
}

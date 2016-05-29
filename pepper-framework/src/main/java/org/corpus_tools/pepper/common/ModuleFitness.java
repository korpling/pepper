package org.corpus_tools.pepper.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
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
		 * value and means, the important features are fit, but optional ones
		 * aren't
		 **/
		UNFIT,
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
		/**
		 * Returns the names of the important fitness feature. These are the
		 * features which makes a module able to be ran.
		 * 
		 * @return
		 */
		public static Collection<FitnessFeature> getImportantFitnessFeatures() {
			return Arrays.asList(IS_READY_TO_RUN);
		}

		/**
		 * Returns the names of the optional fitness feature.
		 * 
		 * @return
		 */
		public static Collection<FitnessFeature> getOptionalFitnessFeatures() {
			return Arrays.asList(IS_IMPORTABLE, SUPPORTS_SUPPLIER_CONTACT, SUPPORTS_SUPPLIER_HP);
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
	 * Adds a {@link FitnessFeature} value.
	 * 
	 * @param feature
	 * @param fitness
	 */
	public void addFitnessFeature(final FitnessFeature feature, final Fitness fitness) {
		if (feature != null) {
			this.fitnessMap.put(feature, fitness);
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
	 * <li>{@link Fitness#UNFIT}, when all important fitness feature are fit,
	 * but at least one of the optional ones isn't</li>
	 * <li>{@link Fitness#CRITICAL}, at least one important fitness feature is
	 * not fit</li>
	 * </ul>
	 * 
	 * @return the overall fitness value
	 */
	public Fitness getOverallFitness() {
		for (FitnessFeature feature : FitnessFeature.getImportantFitnessFeatures()) {
			if (Fitness.CRITICAL.equals(fitnessMap.get(feature))) {
				return Fitness.CRITICAL;
			}
		}
		Fitness lowestFitness = Fitness.FIT;
		for (FitnessFeature feature : FitnessFeature.getOptionalFitnessFeatures()) {
			Fitness currentFitness = fitnessMap.get(feature);
			if (currentFitness!= null && lowestFitness.compareTo(currentFitness) > 0) {
				lowestFitness = fitnessMap.get(feature);
			}
		}
		return lowestFitness;
	}

	@Override
	public String toString(){
		final StringBuilder retVal= new StringBuilder();
		retVal.append(moduleName);
		retVal.append(":");
		for (FitnessFeature importantFeature:  FitnessFeature.getImportantFitnessFeatures()){
			retVal.append(importantFeature);
			retVal.append("=");
			retVal.append(getFitness(importantFeature));
			retVal.append(", ");
		}
		for (FitnessFeature optionalFeature:  FitnessFeature.getOptionalFitnessFeatures()){
			retVal.append(optionalFeature);
			retVal.append("=");
			retVal.append(getFitness(optionalFeature));
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
}

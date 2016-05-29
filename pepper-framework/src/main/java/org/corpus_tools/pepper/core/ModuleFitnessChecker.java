package org.corpus_tools.pepper.core;

import java.util.ArrayList;
import java.util.Collection;

import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.Fitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.eclipse.emf.common.util.URI;

/**
 * A helper class for checking health and fitness of a module or a set of
 * modules.
 * 
 * @author florian
 *
 */
public class ModuleFitnessChecker {
	private static URI corpusPath = URI.createFileURI(PepperUtil.getTempFile("fitnessCheck").getAbsolutePath());

	/**
	 * Returns a {@link ModuleFitness} value for each {@link PepperModule} in
	 * specified list.
	 * 
	 * @param modules
	 * @return
	 */
	public static Collection<ModuleFitness> checkFitness(final Collection<PepperModule> modules) {
		final Collection<ModuleFitness> moduleFitness = new ArrayList<>();
		for (PepperModule module : modules) {
			if (module != null) {
				moduleFitness.add(checkFitness(module));
			}
		}
		return moduleFitness;
	}

	/**
	 * Returns a {@link ModuleFitness} value for specified {@link PepperModule}.
	 * 
	 * @param modules
	 * @return
	 */
	public static ModuleFitness checkFitness(final PepperModule module) {
		if (module == null) {
			return null;
		}
		final ModuleFitness fitness = checkHealth(module);

		fitness.setFeature(FitnessFeature.SUPPORTS_SUPPLIER_HP, module.getSupplierHomepage() != null ? Fitness.FIT : Fitness.HEALTHY);
		fitness.setFeature(FitnessFeature.SUPPORTS_SUPPLIER_CONTACT, module.getSupplierContact() != null ? Fitness.FIT : Fitness.HEALTHY);

		if (module instanceof PepperImporter) {
			Double isImportableRate = ((PepperImporter) module).isImportable(corpusPath);
			fitness.setFeature(FitnessFeature.IS_IMPORTABLE, isImportableRate != null ? Fitness.FIT : Fitness.HEALTHY);
		}
		return fitness;
	}

	/**
	 * Returns a {@link ModuleFitness} value for each {@link PepperModule} in
	 * specified list. Only checks {@link FitnessFeature#getHealthFeatures()}.
	 * 
	 * @param modules
	 * @return
	 */
	public static Collection<ModuleFitness> checkHealth(final Collection<PepperModule> modules) {
		final Collection<ModuleFitness> moduleFitness = new ArrayList<>();
		for (PepperModule module : modules) {
			if (module != null) {
				moduleFitness.add(checkHealth(module));
			}
		}
		return moduleFitness;
	}

	/**
	 * Returns a {@link ModuleFitness} value for specified {@link PepperModule}.
	 * Only checks {@link FitnessFeature#getHealthFeatures()}.
	 * 
	 * @param modules
	 * @return
	 */
	public static ModuleFitness checkHealth(final PepperModule module) {
		if (module == null) {
			return null;
		}
		final ModuleFitness fitness = new ModuleFitness(module.getName());
		fitness.setFeature(FitnessFeature.IS_READY_TO_RUN, module.isReadyToStart() ? Fitness.FIT : Fitness.CRITICAL);

		return fitness;
	}
}

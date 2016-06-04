package org.corpus_tools.pepper.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.eclipse.emf.common.util.URI;

import com.google.common.base.Strings;

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
	public static List<ModuleFitness> checkFitness(final Collection<PepperModule> modules) {
		final List<ModuleFitness> moduleFitness = new ArrayList<>();
		if (modules == null) {
			return moduleFitness;
		}
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

		fitness.setFeature(FitnessFeature.HAS_DESCRIPTION, module.getDesc() != null ? true : false);
		fitness.setFeature(FitnessFeature.HAS_SUPPLIER_HP, module.getSupplierHomepage() != null ? true : false);
		fitness.setFeature(FitnessFeature.HAS_SUPPLIER_CONTACT, module.getSupplierContact() != null ? true : false);

		if (module instanceof PepperImporter) {
			PepperImporter importer = (PepperImporter) module;
			Double isImportableRate = importer.isImportable(corpusPath);
			fitness.setFeature(FitnessFeature.IS_IMPORTABLE, isImportableRate != null ? true : false);
			fitness.setFeature(FitnessFeature.HAS_SUPPORTED_FORMATS, hasSupportedFormats(importer.getSupportedFormats()));
		}

		if (module instanceof PepperExporter) {
			PepperExporter exporter = (PepperExporter) module;
			fitness.setFeature(FitnessFeature.HAS_SUPPORTED_FORMATS, hasSupportedFormats(exporter.getSupportedFormats()));
		}
		return fitness;
	}

	private static boolean hasSupportedFormats(List<FormatDesc> formatDescs) {
		boolean hasFormats = false;
		if (formatDescs != null && formatDescs.size() > 0) {
			hasFormats = true;
			for (FormatDesc formatDesc : formatDescs) {
				if (Strings.isNullOrEmpty(formatDesc.getFormatName())) {
					hasFormats = false;
				}
			}
		}
		return hasFormats;
	}

	/**
	 * Returns a {@link ModuleFitness} value for each {@link PepperModule} in
	 * specified list. Only checks {@link FitnessFeature#getHealthFeatures()}.
	 * 
	 * @param modules
	 * @return
	 */
	public static List<ModuleFitness> checkHealth(final Collection<PepperModule> modules) {
		final List<ModuleFitness> moduleFitness = new ArrayList<>();
		if (modules == null) {
			return moduleFitness;
		}
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
		fitness.setFeature(FitnessFeature.HAS_NAME, Strings.isNullOrEmpty(module.getName()) ? false : true);
		fitness.setFeature(FitnessFeature.IS_READY_TO_RUN, module.isReadyToStart() ? true : false);
		return fitness;
	}
}

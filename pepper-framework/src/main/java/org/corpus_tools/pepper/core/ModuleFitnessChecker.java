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

		new AddFeature(fitness, FitnessFeature.HAS_DESCRIPTION) {
			@Override
			public boolean condition() {
				return Strings.isNullOrEmpty(module.getDesc()) ? false : true;
			}
		};

		new AddFeature(fitness, FitnessFeature.HAS_SUPPLIER_HP) {
			@Override
			public boolean condition() {
				return module.getSupplierHomepage() == null ? false : true;
			}
		};

		new AddFeature(fitness, FitnessFeature.HAS_SUPPLIER_CONTACT) {
			@Override
			public boolean condition() {
				return module.getSupplierContact() == null ? false : true;
			}
		};

		if (module instanceof PepperImporter) {
			final PepperImporter importer = (PepperImporter) module;

			new AddFeature(fitness, FitnessFeature.IS_IMPORTABLE) {
				@Override
				public boolean condition() {
					return importer.isImportable(corpusPath) != null ? true : false;
				}
			};

			new AddFeature(fitness, FitnessFeature.HAS_SUPPORTED_FORMATS) {
				@Override
				public boolean condition() {
					return hasSupportedFormats(importer.getSupportedFormats());
				}
			};
		}

		if (module instanceof PepperExporter) {
			final PepperExporter exporter = (PepperExporter) module;

			new AddFeature(fitness, FitnessFeature.HAS_SUPPORTED_FORMATS) {
				@Override
				public boolean condition() {
					return hasSupportedFormats(exporter.getSupportedFormats());
				}
			};
		}
		return fitness;
	}

	private static boolean hasSupportedFormats(final List<FormatDesc> formatDescs) {
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

		new AddFeature(fitness, FitnessFeature.HAS_NAME) {
			@Override
			public boolean condition() {
				return Strings.isNullOrEmpty(module.getName()) ? false : true;
			}
		};

		new AddFeature(fitness, FitnessFeature.IS_READY_TO_RUN) {
			@Override
			public boolean condition() {
				return module.isReadyToStart() ? true : false;
			}
		};
		return fitness;
	}

	/**
	 * helper class to check the feature's condition in an error prone manner.
	 * When an exception occurs, the feature's fitness is automatically set to
	 * false.
	 * 
	 * @author florian
	 *
	 */
	public static abstract class AddFeature {
		public AddFeature(final ModuleFitness fitness, final FitnessFeature feature) {
			boolean val;
			try {
				val = condition();
			} catch (RuntimeException e) {
				val = false;
			}
			fitness.setFeature(feature, val);
		}

		public abstract boolean condition();
	}

}

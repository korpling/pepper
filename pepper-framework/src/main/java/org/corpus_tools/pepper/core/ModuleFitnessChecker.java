package org.corpus_tools.pepper.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.impl.SelfTestDesc;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperManipulator;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * A helper class for checking health and fitness of a module or a set of
 * modules.
 * 
 * @author florian
 *
 */
public class ModuleFitnessChecker {
	private static final Logger logger = LoggerFactory.getLogger("Pepper");
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
	 * When the specified module provides a self test, the fitness feature
	 * {@link FitnessFeature#HAS_SELFTEST} is set to true and self test is ran.
	 * Depending on success, the following health features are set:
	 * <ul>
	 * <li>{@link FitnessFeature#HAS_PASSED_SELFTEST}</li>
	 * <li>{@link FitnessFeature#IS_IMPORTABLE_SEFTEST_DATA}</li>
	 * <li>{@link FitnessFeature#IS_VALID_SELFTEST_DATA}</li>
	 * </ul>
	 * 
	 * @param pepperModule
	 *            module to test
	 * @param pepper
	 *            Pepper environment to test
	 * @param moduleFitness
	 *            the {@link ModuleFitness} to be filled.
	 */
	protected static ModuleFitness selfTest(final PepperModule pepperModule, final Pepper pepper, ModuleFitness moduleFitness) {
		if (pepperModule == null) {
			return moduleFitness;
		}
		if (moduleFitness == null) {
			moduleFitness = new ModuleFitness(pepperModule.getName());
		}
		if (pepper == null) {
			throw new PepperFWException("Cannot run integration test for module '" + pepperModule + "', because Pepper framework is not specified. ");
		}

		final SelfTestDesc testDesc = pepperModule.getSelfTestDesc();
		if (testDesc == null) {
			moduleFitness.setFeature(FitnessFeature.HAS_SELFTEST, false);
			return moduleFitness;
		} else {
			moduleFitness.setFeature(FitnessFeature.HAS_SELFTEST, true);
		}

		boolean isImportable = false;
		boolean hasPassed = false;
		boolean isValid = false;
		moduleFitness.setFeature(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA, isImportable);
		moduleFitness.setFeature(FitnessFeature.HAS_PASSED_SELFTEST, hasPassed);
		moduleFitness.setFeature(FitnessFeature.IS_VALID_SELFTEST_DATA, isValid);

		final List<String> problems = new ArrayList<>();
		if (!testDesc.isValid(problems)) {
			if (PepperUtil.isNotNullOrEmpty(problems)) {
				for (String problem : problems) {
					logger.warn(warn(pepperModule, problem));
				}
			}
			return moduleFitness;
		}

		if (pepperModule instanceof PepperImporter) {
			final PepperImporter importer = (PepperImporter) pepperModule;
			final CorpusDesc corpusDesc = PepperUtil.createCorpusDesc().withCorpusPath(testDesc.getInputCorpusPath()).build();
			importer.setCorpusDesc(corpusDesc);
			final Double importRate = importer.isImportable(corpusDesc.getCorpusPath());
			isImportable = (importRate == null || importRate < 1.0) ? false : true;
		} else if (pepperModule instanceof PepperManipulator || pepperModule instanceof PepperExporter) {
			// load Salt from in corpus path
			SaltProject saltProject = null;
			try {
				SaltUtil.loadSaltProject(testDesc.getInputCorpusPath());
			} catch (RuntimeException e) {
				logger.warn(warn(pepperModule, "The input salt project was could bot have been loaded from path '" + testDesc.getInputCorpusPath() + "'. May be the path does not contain a salt project. "));
				return moduleFitness;
			}
			pepperModule.setSaltProject(saltProject);
		}

		PepperTestUtil.start(pepper, Arrays.asList(pepperModule));

		if (pepperModule instanceof PepperImporter || pepperModule instanceof PepperManipulator) {
			hasPassed = whenModuleIsImpoterOrManipualtorThenCallSelftestDescCompare(pepperModule);
			isValid = SaltUtil.validate(pepperModule.getSaltProject()).andGetResult();
		} else if (pepperModule instanceof PepperExporter) {
			hasPassed = whenModuleIsExpoterThenCallSelftestDescCompare(pepperModule);
		}

		moduleFitness.setFeature(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA, isImportable);
		moduleFitness.setFeature(FitnessFeature.HAS_PASSED_SELFTEST, hasPassed);
		moduleFitness.setFeature(FitnessFeature.IS_VALID_SELFTEST_DATA, isValid);
		return moduleFitness;
	}

	private static boolean whenModuleIsImpoterOrManipualtorThenCallSelftestDescCompare(PepperModule pepperModule) {
		final SaltProject outputProject = pepperModule.getSaltProject();
		if (SaltUtil.isNullOrEmpty(outputProject.getCorpusGraphs()) || outputProject.getCorpusGraphs().size() != 1) {
			logger.warn(warn(pepperModule, "The salt project contained no corpus structures or it contains more than one corpus structure. "));
			return false;
		}
		final URI out = pepperModule.getSelfTestDesc().getInputCorpusPath();
		final SaltProject expectedProject = SaltUtil.loadSaltProject(out);
		try {
			return pepperModule.getSelfTestDesc().compare(pepperModule.getSaltProject(), expectedProject);
		} catch (RuntimeException e) {
			logger.warn(warn(pepperModule, "An error occured while comparing actual salt project with expected salt project. "));
			return false;
		}
	}

	private static boolean whenModuleIsExpoterThenCallSelftestDescCompare(PepperModule pepperModule) {
		try {
			final PepperExporter exporter = (PepperExporter) pepperModule;
			return pepperModule.getSelfTestDesc().compare(exporter.getCorpusDesc().getCorpusPath(), pepperModule.getSelfTestDesc().getOutputCorpusPath());
		} catch (RuntimeException e) {
			logger.warn(warn(pepperModule, "An error occured while comparing actual salt project with expected salt project. "));
			return false;
		}
	}

	private static String warn(PepperModule pepperModule, String msg) {
		return "Failure in test of module '" + pepperModule.getName() + "': " + msg;
	}

	/**
	 * helper class to check the feature's condition in an error prone manner.
	 * When an exception occurs, the feature's fitness is automatically set to
	 * false.
	 * 
	 * @author florian
	 *
	 */
	private static abstract class AddFeature {
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

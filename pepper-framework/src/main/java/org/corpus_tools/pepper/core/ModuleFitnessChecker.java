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
	final private Pepper pepper;

	public ModuleFitnessChecker(Pepper pepper) {
		this.pepper = pepper;
	}

	public ModuleFitnessChecker() {
		this.pepper = PepperTestUtil.createDefaultPepper();
	}

	/**
	 * Returns a {@link ModuleFitness} value for each {@link PepperModule} in
	 * specified list.
	 * 
	 * @param modules
	 * @return
	 */
	public List<ModuleFitness> checkFitness(final Collection<PepperModule> modules) {
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
	public ModuleFitness checkFitness(final PepperModule module) {
		if (module == null) {
			return null;
		}
		// call this function first to make sure all required properties are set for the self-test
		final SelfTestDesc selfTestDesc = module.getSelfTestDesc();

		
		ModuleFitness fitness = checkHealth(module);

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
		if (pepper != null) {
			fitness = selfTest(module, fitness, selfTestDesc);
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
	public List<ModuleFitness> checkHealth(final Collection<PepperModule> modules) {
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
	public ModuleFitness checkHealth(final PepperModule module) {
		if (module == null) {
			return null;
		}
		final ModuleFitness fitness = new ModuleFitness(module.getName());

		new AddFeature(fitness, FitnessFeature.HAS_NAME) {
			@Override
			public boolean condition() {
				return !Strings.isNullOrEmpty(module.getName());
			}
		};

		new AddFeature(fitness, FitnessFeature.IS_READY_TO_RUN) {
			@Override
			public boolean condition() {
				PepperTestUtil.prepareFixturesAndCreateJob(pepper, Arrays.asList(module));
				return module.isReadyToStart();
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
	 */
	public ModuleFitness selfTest(final PepperModule pepperModule) {
		return selfTest(pepperModule, null);
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
	protected ModuleFitness selfTest(final PepperModule pepperModule, 
			ModuleFitness moduleFitness) {
		return selfTest(pepperModule, moduleFitness, pepperModule == null ? null : pepperModule.getSelfTestDesc());
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
	 * @param selfTestDesc The self-test description as provided by {@link PepperModule#getSelfTestDesc()}
	 */
	protected ModuleFitness selfTest(final PepperModule pepperModule, 
			ModuleFitness moduleFitness, SelfTestDesc selfTestDesc) {
		if (pepperModule == null) {
			return moduleFitness;
		}
		if (moduleFitness == null) {
			moduleFitness = new ModuleFitness(pepperModule.getName());
		}
		if (pepper == null) {
			throw new PepperFWException("Cannot run integration test for module '" + pepperModule
					+ "', because Pepper framework is not specified. ");
		}

		if (selfTestDesc == null) {
			moduleFitness.setFeature(FitnessFeature.HAS_SELFTEST, false);
			return moduleFitness;
		} else {
			moduleFitness.setFeature(FitnessFeature.HAS_SELFTEST, true);
		}

		boolean hasPassed = false;
		moduleFitness.setFeature(FitnessFeature.HAS_PASSED_SELFTEST, hasPassed);
		if (selfTestIsNotValid(selfTestDesc, pepperModule)) {
			return moduleFitness;
		}

		if (pepperModule instanceof PepperImporter) {
			final PepperImporter importer = (PepperImporter) pepperModule;
			final CorpusDesc corpusDesc = PepperUtil.createCorpusDesc()
					.withCorpusPath(cleanURI(selfTestDesc.getInputCorpusPath())).build();
			importer.setCorpusDesc(corpusDesc);
			final Double importRate = importer.isImportable(corpusDesc.getCorpusPath());
			final boolean isImportable = (importRate != null && importRate > 0.0) ? true : false;
			moduleFitness.setFeature(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA, isImportable);
		} else if (pepperModule instanceof PepperManipulator || pepperModule instanceof PepperExporter) {
			// load Salt from in corpus path
			SaltProject saltProject = null;
			try {
				saltProject = SaltUtil.loadCompleteSaltProject(cleanURI(selfTestDesc.getInputCorpusPath()));
			} catch (RuntimeException e) {
				logger.warn(warn(pepperModule, "The input salt project was could not have been loaded from path '"
						+ selfTestDesc.getInputCorpusPath() + "'. The path might not contain a salt project. "));
				return moduleFitness;
			}
			if (pepperModule instanceof PepperExporter) {
				final PepperExporter exporter = (PepperExporter) pepperModule;
				exporter.setCorpusDesc(new CorpusDesc.Builder()
						.withCorpusPath(URI.createFileURI(PepperUtil.getTempFile("self-test").getAbsolutePath())
								.appendSegment("" + System.currentTimeMillis()))
						.build());
			}

			pepperModule.setSaltProject(saltProject);
		}

		try {
			PepperTestUtil.start(pepper, Arrays.asList(pepperModule));
		
			if (pepperModule instanceof PepperImporter || pepperModule instanceof PepperManipulator) {
				hasPassed = whenModuleIsImpoterOrManipualtorThenCallSelftestDescCompare(pepperModule, selfTestDesc);
				final boolean isValid = SaltUtil.validate(pepperModule.getSaltProject()).andFindInvalidities().isValid();
				moduleFitness.setFeature(FitnessFeature.IS_VALID_SELFTEST_DATA, isValid);
			} else if (pepperModule instanceof PepperExporter) {
				hasPassed = whenModuleIsExpoterThenCallSelftestDescCompare(pepperModule, selfTestDesc);
			}

		} catch (Exception ex) {
			logger.error("Selftest throw exception: {}", ex.getMessage());
			hasPassed = false;
		}
		moduleFitness.setFeature(FitnessFeature.HAS_PASSED_SELFTEST, hasPassed);
		return moduleFitness;
	}

	private boolean selfTestIsNotValid(final SelfTestDesc selfTestDesc, final PepperModule pepperModule) {
		final List<String> problems = new ArrayList<>();
		if (!selfTestDesc.isValid(problems)) {
			if (PepperUtil.isNotNullOrEmpty(problems)) {
				for (String problem : problems) {
					logger.warn(warn(pepperModule, problem));
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Creating a uri from file with appending of further fragments can cause
	 * invalid uris. For instance
	 * <code> URI.createFileURI("/home/me/a/").append("b");</code> will lead to
	 * a uri <code>/home/me/a//b</code>.
	 * 
	 * @param input
	 *            a potentially invalid uri
	 * @return cleaned uri
	 */
	private URI cleanURI(URI input) {
		URI cleanedURI = input;
		if (cleanedURI.toFileString().contains("//")) {
			cleanedURI = URI.createFileURI(cleanedURI.toFileString().replace("//", "/"));
		}
		return cleanedURI;
	}

	private static boolean whenModuleIsImpoterOrManipualtorThenCallSelftestDescCompare(PepperModule pepperModule,
			SelfTestDesc selfTestDesc) {
		final SaltProject outputProject = pepperModule.getSaltProject();
		if (SaltUtil.isNullOrEmpty(outputProject.getCorpusGraphs()) || outputProject.getCorpusGraphs().size() != 1) {
			logger.warn(warn(pepperModule,
					"The salt project contains no corpus structures or it contains more than one corpus structure. "));
			return false;
		}
		final URI expected = selfTestDesc.getExpectedCorpusPath();
		final SaltProject expectedProject = SaltUtil.loadSaltProject(expected);
		try {
			return selfTestDesc.compare(pepperModule.getSaltProject(), expectedProject);
		} catch (RuntimeException e) {
			logger.warn(warn(pepperModule,
					"An error occured while comparing actual salt project with expected salt project. "));
			return false;
		}
	}

	private static boolean whenModuleIsExpoterThenCallSelftestDescCompare(PepperModule pepperModule,
			SelfTestDesc selfTestDesc) {
		final PepperExporter exporter = (PepperExporter) pepperModule;
		try {
			return selfTestDesc.compare(exporter.getCorpusDesc().getCorpusPath(), selfTestDesc.getExpectedCorpusPath());
		} catch (RuntimeException e) {
			logger.warn(warn(pepperModule,
					"An error occured while comparing actual salt project with expected salt project. "));
			return false;
		}
	}

	private static String warn(PepperModule pepperModule, String msg) {
		return "Failure in self-test of module '" + pepperModule.getName() + "': " + msg;
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
			} catch (Throwable e) {
				val = false;
				logger.warn("Cannot check fitness feature '" + feature + "', because a nested excpetion occured. ", e);
			}
			fitness.setFeature(feature, val);
		}

		public abstract boolean condition();
	}
}

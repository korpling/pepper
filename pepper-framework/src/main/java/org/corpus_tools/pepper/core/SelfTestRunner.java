package org.corpus_tools.pepper.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.corpus_tools.pepper.common.CorpusDesc;
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

public class SelfTestRunner {
	private static final Logger logger = LoggerFactory.getLogger("Pepper");
	private final Pepper pepper;
	private final PepperModule pepperModule;
	private ModuleFitness moduleFitness;

	public SelfTestRunner(Pepper pepper, ModuleFitness moduleFitness, PepperModule pepperModule) {
		this.pepper = pepper;
		this.moduleFitness = moduleFitness;
		this.pepperModule = pepperModule;
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
	public ModuleFitness selfTest() {
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

		final SelfTestDesc selfTestDesc = pepperModule.getSelfTestDesc();
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

		if (isImporter(pepperModule)) {
			final PepperImporter importer = (PepperImporter) pepperModule;
			final CorpusDesc corpusDesc = PepperUtil.createCorpusDesc()
					.withCorpusPath(cleanURI(selfTestDesc.getInputCorpusPath())).build();
			importer.setCorpusDesc(corpusDesc);
			final Double importRate = importer.isImportable(corpusDesc.getCorpusPath());
			final boolean isImportable = (importRate != null && importRate > 0.0) ? true : false;
			moduleFitness.setFeature(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA, isImportable);
		} else if (isManipulatorOrExporter(pepperModule)) {
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

		PepperTestUtil.start(pepper, Arrays.asList(pepperModule));

		if (pepperModule instanceof PepperImporter || pepperModule instanceof PepperManipulator) {
			hasPassed = whenModuleIsImpoterOrManipualtorThenCallSelftestDescCompare(pepperModule, selfTestDesc);
			final boolean isValid = SaltUtil.validate(pepperModule.getSaltProject()).andFindInvalidities().isValid();
			moduleFitness.setFeature(FitnessFeature.IS_VALID_SELFTEST_DATA, isValid);
		} else if (pepperModule instanceof PepperExporter) {
			hasPassed = whenModuleIsExpoterThenCallSelftestDescCompare(pepperModule, selfTestDesc);
		}

		moduleFitness.setFeature(FitnessFeature.HAS_PASSED_SELFTEST, hasPassed);
		return moduleFitness;
	}

	private boolean isImporter(PepperModule pepperModule) {
		return pepperModule instanceof PepperImporter;
	}

	private boolean isManipulatorOrExporter(PepperModule pepperModule) {
		return pepperModule instanceof PepperManipulator || pepperModule instanceof PepperExporter;
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
}

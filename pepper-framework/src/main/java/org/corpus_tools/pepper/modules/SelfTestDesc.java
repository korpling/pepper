package org.corpus_tools.pepper.modules;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;

/**
 * This class is a container for a module selftest. A Pepper module provides an
 * object of this type, and the Pepper framework consumes its methods to check
 * the the results of the module's mapping process. Mainly this class provides
 * an input path, which should contain all files acting as the input for the
 * tested module. And an expected path, which contains the expected output. The
 * method {@link #compare(SaltProject, SaltProject)} or
 * {@link #compare(URI, URI)} is called later to compare the result produced by
 * the module with the expected model or files.
 * 
 * @author florian
 *
 */
public class SelfTestDesc {
	private URI inputCorpusPath = null;
	private URI expectedCorpusPath = null;

	/**
	 * Creates an object with input corpus path and expected path.
	 * 
	 * @param inputCorpusPath
	 *            the path where the corpus used as input is located
	 * @param expectedCorpusPath
	 *            the path where the expected corpus is located
	 */
	public SelfTestDesc(URI inputCorpusPath, URI expectedCorpusPath) {
		this.inputCorpusPath = inputCorpusPath;
		this.expectedCorpusPath = expectedCorpusPath;
	}

	/**
	 * @return the path where the corpus used as input is located
	 */
	public URI getInputCorpusPath() {
		return inputCorpusPath;
	}

	/**
	 * @return the path where the expected corpus is located
	 */
	public URI getExpectedCorpusPath() {
		return expectedCorpusPath;
	}

	/**
	 * This method is called by the Pepper framework, when making a selftest of
	 * the module. This method is called when tested module is an importers or
	 * manipulators.
	 * 
	 * <strong> You are free to overwrite this method with your own comparison.
	 * </strong>
	 * 
	 * @param actualProject
	 *            is the project produced by the module itself (which was
	 *            generated from the files from the input corpus path)
	 * @param expectedProject
	 *            is the expected Salt model, which is created from the output
	 *            corpus path.
	 * @return true, when both models are equal, false otherwise
	 */
	public boolean compare(SaltProject actualProject, SaltProject expectedProject) {
		if (actualProject == null || expectedProject == null) {
			return false;
		}
		if (actualProject.getName() != null) {
			if (!actualProject.getName().equals(expectedProject.getName())) {
				return false;
			}
		} else if (expectedProject.getName() != null) {
			return false;
		}
		if (expectedProject.getCorpusGraphs().size() != actualProject.getCorpusGraphs().size()) {
			return false;
		}
		boolean retVal = true;
		for (int i = 0; i < expectedProject.getCorpusGraphs().size(); i++) {
			retVal = retVal && SaltUtil.compare(actualProject.getCorpusGraphs().get(0))
					.with(expectedProject.getCorpusGraphs().get(0)).andCheckIsomorphie();
		}
		return retVal;
	}

	/**
	 * This method is called by the Pepper framework, when making a selftest of
	 * the module. This method is called when tested module is an exporter.
	 * 
	 * <strong> You are free to overwrite this method with your own comparison.
	 * </strong>
	 * 
	 * @param actualProject
	 *            is the corpus produced by the module itself (which was
	 *            generated from the files from the input corpus path)
	 * @param expectedProject
	 *            is the expected corpus, which is created from the output
	 *            corpus path.
	 * @return true, when both models are equal, false otherwise
	 */
	public boolean compare(URI actualCorpusPath, URI expectedCorpusPath) {
		if (actualCorpusPath == null || expectedCorpusPath == null) {
			return false;
		}
		final File actualDir = new File(actualCorpusPath.toFileString());
		final File expectedDir = new File(expectedCorpusPath.toFileString());

		return new HashSet<File>(FileUtils.listFiles(actualDir, null, true))
				.containsAll(FileUtils.listFiles(expectedDir, null, true));
	}

	/**
	 * Returns whether this object was correctly instantiated by module.
	 * 
	 * @param problems
	 *            when object is not valid, the reason(s) are written here.
	 * @return true if object is valid, false otherwise
	 */
	public boolean isValid(final Collection<String> problems) {
		final URI in = this.getInputCorpusPath();
		boolean retVal = true;
		if (in == null) {
			if (problems != null) {
				problems.add("The input corpus path was null. ");
			}
			retVal = false;
		} else {
			final File inFile = new File(in.toFileString());
			if (!inFile.exists()) {
				if (problems != null) {
					problems.add("The input corpus path was null. ");
				}
				retVal = false;
			}
		}
		final URI out = this.getInputCorpusPath();
		if (out == null) {
			if (problems != null) {
				problems.add("The output corpus path was null. ");
			}
			retVal = false;
		} else {
			final File inFile = new File(in.toFileString());
			if (!inFile.exists()) {
				if (problems != null) {
					problems.add("The input corpus path was null. ");
				}
				retVal = false;
			}
		}
		return retVal;
	}
}

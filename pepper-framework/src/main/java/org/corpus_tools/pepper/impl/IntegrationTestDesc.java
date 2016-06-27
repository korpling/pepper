package org.corpus_tools.pepper.impl;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;

public class IntegrationTestDesc {
	private URI inputCorpusPath = null;
	private URI outputCorpusPath = null;

	public IntegrationTestDesc(URI inputCorpusPath, URI outputCorpusPath) {
		this.inputCorpusPath = inputCorpusPath;
		this.outputCorpusPath = outputCorpusPath;
	}

	public URI getInputCorpusPath() {
		return inputCorpusPath;
	}

	public URI getOutputCorpusPath() {
		return outputCorpusPath;
	}

	// this method only works for importers and manipulators
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
			retVal = retVal && SaltUtil.compare(actualProject.getCorpusGraphs().get(0)).with(expectedProject.getCorpusGraphs().get(0)).andCheckIsomorphie();
		}
		return retVal;
	}

	// this method only works for exporters
	public boolean compare(URI actualCorpusPath, URI expectedCorpusPath) {
		if (actualCorpusPath == null || expectedCorpusPath == null) {
			return false;
		}
		final File actualDir = new File(actualCorpusPath.toFileString());
		final File expectedDir = new File(expectedCorpusPath.toFileString());

		return new HashSet<File>(FileUtils.listFiles(actualDir, null, true)).containsAll(FileUtils.listFiles(expectedDir, null, true));
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

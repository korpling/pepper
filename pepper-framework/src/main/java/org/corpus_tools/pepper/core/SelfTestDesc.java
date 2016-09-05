package org.corpus_tools.pepper.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.util.SaltUtil;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.XMLUnit;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.google.common.base.Strings;

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
	private static final Logger logger = LoggerFactory.getLogger("Pepper");
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
	public boolean compare(final SaltProject actualProject, final SaltProject expectedProject) {
		return SaltUtil.compare(actualProject).with(expectedProject).andCheckIsomorphie();
	}

	/**
	 * This method is called by the Pepper framework, when making a selftest of
	 * the module. This method is called when tested module is an exporter.
	 * 
	 * <strong> You are free to overwrite this method with your own comparison.
	 * </strong>
	 * 
	 * @param actualCorpusPath
	 *            is the corpus path produced by the module itself (which was
	 *            generated from the files from the input corpus path)
	 * @param expectedCorpusPath
	 *            is the expected corpus path
	 * @return true, when the content of both paths is equal, false otherwise
	 */
	public boolean compare(final URI actualCorpusPath, final URI expectedCorpusPath) {
		if (actualCorpusPath == null || expectedCorpusPath == null) {
			return false;
		}
		final File actualDir = new File(actualCorpusPath.toFileString());
		final File expectedDir = new File(expectedCorpusPath.toFileString());
		if (!actualDir.exists() || !expectedDir.exists()) {
			return false;
		}

		final Collection<File> actualFiles = FileUtils.listFiles(actualDir, null, true);
		final Collection<File> expectedFiles = FileUtils.listFiles(expectedDir, null, true);
		if (actualFiles == null || expectedFiles == null) {
			return false;
		}
		if (actualFiles.size() != expectedFiles.size()) {
			return false;
		}

		final String expectedFilePrefix = getCannonicalPathWithoutException(expectedDir);
		final String actualFilePrefix = getCannonicalPathWithoutException(actualDir);

		final Map<String, File> expectedFileMap = new Hashtable<>();
		for (File expectedFile : expectedFiles) {
			expectedFileMap.put(getCannonicalPathWithoutException(expectedFile).replace(expectedFilePrefix, ""),
					expectedFile);
		}
		for (File actualFile : actualFiles) {
			final File expectedFile = expectedFileMap
					.get(getCannonicalPathWithoutException(actualFile).replace(actualFilePrefix, ""));
			if (!compare(actualFile, expectedFile)) {
				return false;
			}
		}
		return true;
	}

	private String getCannonicalPathWithoutException(File input) {
		try {
			return input.getCanonicalPath();
		} catch (IOException e) {
			logger.warn("Cannot create cannonical path for '" + input + "'.", e);
		}
		return "";
	}

	/**
	 * This method is called by {@link #compare(URI, URI)} to compare two files
	 * with each other.
	 * 
	 * <strong> You are free to overwrite this method with your own comparison.
	 * </strong>
	 * 
	 * @param actualFile
	 *            the file produced by the module itself (which was generated
	 *            from the files from the input corpus path)
	 * @param expectedFile
	 *            the file, which is contained in expected corpus path.
	 * @return true, when both files are equal, false otherwise
	 */
	protected boolean compare(final File actualFile, final File expectedFile) {
		if (!filesDoExist(actualFile, expectedFile)) {
			return false;
		}
		if (isXmlFile(expectedFile)) {
			return compareXML(actualFile, expectedFile);
		}
		return filesAreEqual(actualFile, expectedFile);
	}

	private boolean isXmlFile(File pobablyXMlFile) {
		try (BufferedReader brTest = new BufferedReader(new FileReader(pobablyXMlFile))) {
			final String firstLine = brTest.readLine();
			if (!Strings.isNullOrEmpty(firstLine)) {
				if (firstLine.contains("<?xml")) {
					return true;
				}
			}
		} catch (IOException e1) {
			return false;
		}
		return false;
	}

	private boolean filesAreEqual(final File actualFile, final File expectedFile) {
		try {
			return FileUtils.contentEquals(actualFile, expectedFile);
		} catch (IOException e) {
			return false;
		}
	}

	private boolean filesDoExist(final File actualFile, final File expectedFile) {
		if (actualFile == null || !actualFile.exists() || expectedFile == null || !expectedFile.exists()) {
			return false;
		}
		return true;
	}

	protected final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

	/**
	 * This method is called by {@link #compare(URI, URI)} to compare two
	 * xmlfiles with each other.
	 * 
	 * <strong> You are free to overwrite this method with your own comparison.
	 * </strong>
	 * 
	 * @param actualXmlFile
	 *            the xml file produced by the module itself (which was
	 *            generated from the files from the input corpus path)
	 * @param expectedXmlFile
	 *            the xml file, which is contained in expected corpus path.
	 * @return true, when both files are equal, false otherwise
	 */
	protected boolean compareXML(final File actualXmlFile, final File expectedXmlFile) {
		if (!filesDoExist(actualXmlFile, expectedXmlFile)) {
			return false;
		}
		if (filesAreEqual(actualXmlFile, expectedXmlFile)) {
			return true;
		}
		final DocumentBuilder docBuilder;
		final Diff diff;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			XMLUnit.setIgnoreWhitespace(true);
			XMLUnit.setIgnoreComments(true);
			XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
			diff = XMLUnit.compareXML(docBuilder.parse(expectedXmlFile), docBuilder.parse(actualXmlFile));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			return false;
		}
		diff.overrideElementQualifier(new ElementNameQualifier());
		return diff.identical();
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
					problems.add("The input corpus path '" + inFile.getAbsolutePath() + "' does not exist. ");
				}
				retVal = false;
			}
		}
		final URI expected = this.getExpectedCorpusPath();
		if (expected == null) {
			if (problems != null) {
				problems.add("The expected corpus path was null. ");
			}
			retVal = false;
		} else {
			final File expectedFile = new File(expected.toFileString());
			if (!expectedFile.exists()) {
				if (problems != null) {
					problems.add("The expected corpus path '" + expectedFile.getAbsolutePath() + "' does not exist. ");
				}
				retVal = false;
			}
		}
		return retVal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expectedCorpusPath == null) ? 0 : expectedCorpusPath.hashCode());
		result = prime * result + ((inputCorpusPath == null) ? 0 : inputCorpusPath.hashCode());
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
		SelfTestDesc other = (SelfTestDesc) obj;
		if (expectedCorpusPath == null) {
			if (other.expectedCorpusPath != null)
				return false;
		} else if (!expectedCorpusPath.equals(other.expectedCorpusPath))
			return false;
		if (inputCorpusPath == null) {
			if (other.inputCorpusPath != null)
				return false;
		} else if (!inputCorpusPath.equals(other.inputCorpusPath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SelfTestDesc [inputCorpusPath=" + inputCorpusPath + ", expectedCorpusPath=" + expectedCorpusPath + "]";
	}
}

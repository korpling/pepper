package org.corpus_tools.pepper.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.eclipse.emf.common.util.URI;

/**
 * This class contains helper method to sample the content of a folder. These
 * methods are used for computing whether a corpus is importable by an importer
 * or not. The helper methods samples files or read the first X lines of a file.
 * 
 * @author florian
 *
 */
public class IsImportableUtil {
	/**
	 * The number of files which are read for sampling when invoking
	 * {@link #isImportable(URI)}.
	 **/
	public static final int NUMBER_OF_SAMPLED_FILES = 20;
	/**
	 * The number of lines in a file which are read for sampling when invoking
	 * {@link #isImportable(URI)}.
	 **/
	public static final int NUMBER_OF_SAMPLED_LINES = 10;

	/**
	 * Creates a sampled set of <code>numberOfSampledFiles</code> files
	 * recursively from directory <code>dir</code> with specified endings.
	 * 
	 * @param dir
	 *            the directory to be traversed recursively
	 * @param numberOfSampledFiles
	 *            number of files to be sampled
	 * @param fileEndings
	 *            endings of files to be sampled
	 * @return a collection of files having on of the endings in
	 *         <code>endings</code> in directory <code>dir</code>
	 */
	public static Collection<File> sampleFiles(File dir, int numberOfSampledFiles, String... fileEndings) {
		if (dir == null || !dir.exists()) {
			throw new PepperModuleException("Cannot sample files in folder, since folder '" + dir + "' is empty or does not exist. ");
		}
		if (numberOfSampledFiles == 0) {
			return new ArrayList<>();
		}
		if (fileEndings.length == 0) {
			fileEndings = null;
		}
		Collection<File> files = FileUtils.listFiles(dir, fileEndings, true);
		File[] allFiles = new File[files.size()];
		allFiles = files.toArray(allFiles);
		Collection<File> sampledFiles = new HashSet<>(numberOfSampledFiles);
		Random randomGenerator = new Random();
		int maxFiles = (numberOfSampledFiles > allFiles.length) ? allFiles.length : numberOfSampledFiles;
		while (sampledFiles.size() < maxFiles) {
			sampledFiles.add(allFiles[randomGenerator.nextInt(allFiles.length)]);
		}
		return sampledFiles;
	}

	/**
	 * Reads the first X lines of the passed file and returns them as a String
	 * 
	 * @param corpusPath
	 *            path to file
	 * @param lines
	 *            number of lines
	 * @return first X lines
	 */
	public static String readFirstLines(final URI corpusPath, final int lines) {
		return (readFirstLines(new File(corpusPath.toFileString()), lines));
	}

	/**
	 * Reads the first X lines of the passed file and returns them as a String
	 * 
	 * @param corpusPath
	 *            path to file
	 * @param lines
	 *            number of lines
	 * @return first X lines
	 */
	public static String readFirstLines(final File file, final int numOfLinesToRead) {
		if (file == null || !file.exists()) {
			throw new PepperModuleException("Cannot read first '" + numOfLinesToRead + "' of specified file '" + file.getAbsolutePath() + "', because it was null or does not exist. ");
		}
		if (numOfLinesToRead < 1) {
			return null;
		}
		StringBuilder fileContent = new StringBuilder();
		try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
			String line;
			boolean isFirstLine = true;
			while (((line = reader.readLine()) != null) && reader.getLineNumber() <= numOfLinesToRead) {
				if (!isFirstLine) {
					fileContent.append(System.lineSeparator());
				}
				isFirstLine = false;
				fileContent.append(line);

			}
		} catch (IOException e) {

		}

		return fileContent.toString();
	}

	/**
	 * Returns <code>numberOfLines</code> lines of a sampled set of
	 * <code>numberOfSampledFiles</code> files having the ending specified by
	 * <code>fileEndings</code> recursively from specified corpus path.
	 * 
	 * @param corpusPath
	 *            directory to be searched in
	 * @param numberOfSampledFiles
	 *            number of files to be read
	 * @param numberOfLines
	 *            number of lines to be read the numberOfSampledFiles files
	 * @param fileEndings
	 *            endings to be considered. If no endings specified, all files
	 *            are considered
	 * @return <code>numberOfLines</code> lines of
	 *         <code>numberOfSampledFiles</code> files
	 */
	public static Collection<String> sampleFileContent(URI corpusPath, int numberOfSampledFiles, int numberOfLines, String... fileEndings) {
		if (corpusPath == null) {
			return new ArrayList<String>();
		}
		final File dir = new File(corpusPath.toFileString());
		final Collection<File> sampledFiles = sampleFiles(dir, numberOfSampledFiles, fileEndings);
		final Collection<String> fileContents = new ArrayList<>(sampledFiles.size());
		for (File sampleFile : sampledFiles) {
			fileContents.add(readFirstLines(sampleFile, numberOfLines));
		}
		return fileContents;
	}
}

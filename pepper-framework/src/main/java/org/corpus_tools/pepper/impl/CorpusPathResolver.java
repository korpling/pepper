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
import org.apache.commons.io.FilenameUtils;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.eclipse.emf.common.util.URI;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class CorpusPathResolver {
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

	private Multimap<String, File> unreadFilesGroupedByExtension;
	private Multimap<String, FileContent> readFilesGroupedByExtension;

	public CorpusPathResolver(URI corpusPath) {
		unreadFilesGroupedByExtension = groupFilesByEnding(corpusPath);
	}

	/**
	 * Returns {@value #NUMBER_OF_SAMPLED_LINES} lines of a sampled set of
	 * {@value #NUMBER_OF_SAMPLED_FILES} files having the ending specified by
	 * <code>fileEndings</code> recursively from specified corpus path.
	 * 
	 * @param fileEnding
	 *            ending to be considered. If no endings specified, all files
	 *            are considered
	 * @return the first {@value #NUMBER_OF_SAMPLED_LINES} lines of
	 *         {@value #NUMBER_OF_SAMPLED_FILES} files
	 */
	public Collection<String> sampleFileContent(String fileEnding) {
		return sampleFileContent(NUMBER_OF_SAMPLED_FILES, NUMBER_OF_SAMPLED_LINES, fileEnding);
	}

	/**
	 * Returns <code>fileEndings</code> lines of a sampled set of
	 * <code>numberOfSampledLines</code> files having the ending specified by
	 * <code>fileEndings</code> recursively from specified corpus path.
	 * 
	 * @param numberOfSampledFiles
	 *            number of files to be read
	 * @param numberOfSampledLines
	 *            number of lines to be read
	 * @param fileEnding
	 *            ending to be considered. If no endings specified, all files
	 *            are considered
	 * @return the first {@value #NUMBER_OF_SAMPLED_LINES} lines of
	 *         <code>numberOfSampledLines</code> files
	 */
	public Collection<String> sampleFileContent(int numberOfSampledFiles, int numberOfSampledLines, String fileEnding) {
		Collection<FileContent> fileContents = getXFilesWithExtension(numberOfSampledFiles, numberOfSampledLines, fileEnding);
		if (fileContents == null) {
			return new ArrayList<>();
		}
		Collection<String> contents = new ArrayList<>();
		for (FileContent content : fileContents) {
			if (content.numberOfLines < numberOfSampledLines) {
				content.firstXlines = readFirstLines(content.file, numberOfSampledLines);
			}
			contents.add(content.firstXlines);
		}
		return contents;
	}

	/**
	 * Helper class to group a file, its content and the number of lines which
	 * was read for content.
	 * 
	 * @author florian
	 *
	 */
	private class FileContent {
		File file;
		String firstXlines;
		int numberOfLines;

		public FileContent(File file, String firstXlines, int numberOfLines) {
			this.file = file;
			this.firstXlines = firstXlines;
			this.numberOfLines = numberOfLines;
		}
	}

	/**
	 * Groups files for their file ending into a multimap. The key is the ending. 
	 * @param corpusPath
	 * @return
	 */
	private Multimap<String, File> groupFilesByEnding(final URI corpusPath) {
		Multimap<String, File> files = HashMultimap.create();
		if (corpusPath == null) {
			return files;
		}
		final File dir = new File(corpusPath.toFileString());
		if (dir == null || !dir.exists()) {
			throw new PepperModuleException("Cannot sample files in folder, since folder '" + dir + "' is empty or does not exist. ");
		}
		Collection<File> allFiles = FileUtils.listFiles(dir, null, true);
		for (File file : allFiles) {
			String ext = FilenameUtils.getExtension(file.getName());
			if (!Strings.isNullOrEmpty(ext)) {
				files.put(ext, file);
			}
		}
		return files;
	}

	private Collection<FileContent> getXFilesWithExtension(int numOfFiles, int numOfLinesToRead, String extension) {
		Collection<FileContent> readFiles = readFilesGroupedByExtension.get(extension);
		if (readFiles != null && readFiles.size() >= numOfFiles) {
			return readFiles;
		}
		int numOfReadFiles = 0;
		if (readFiles != null) {
			numOfReadFiles = readFiles.size();
		}

		Collection<File> unreadFiles = unreadFilesGroupedByExtension.get(extension);
		// read files as long as there are files to be read
		if (unreadFiles != null) {
			Collection<File> newFiles = sampleFiles(unreadFiles, numOfFiles - numOfReadFiles);
			for (File newFile : newFiles) {
				unreadFilesGroupedByExtension.remove(extension, newFile);
				String firstLines = readFirstLines(newFile, numOfLinesToRead);
				FileContent content = new FileContent(newFile, firstLines, numOfLinesToRead);
				readFilesGroupedByExtension.put(extension, content);
			}
			readFiles = readFilesGroupedByExtension.get(extension);
		}
		return readFiles;
	}

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
	private Collection<File> sampleFiles(Collection<File> files, int numberOfSampledFiles) {
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
	private String readFirstLines(final File file, final int numOfLinesToRead) {
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
}

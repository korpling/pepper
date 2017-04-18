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
package org.corpus_tools.pepper.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileComparator {
	private static final Logger LOG = LoggerFactory.getLogger("Pepper");
	protected final File file1;
	protected final File file2;

	public FileComparator(File file1, File file2) {
		whenFileIsNullThrowException(file1);
		whenFileIsNullThrowException(file2);
		this.file1 = file1;
		this.file2 = file2;
	}

	private void whenFileIsNullThrowException(File file) {
		if (file == null) {
			throw new IllegalArgumentException("Cannot compare files, because specified file was null. ");
		}
		if (!file.exists()) {
			throw new IllegalArgumentException(
					"Cannot compare files, because specified file '" + file + "' does not exist. ");
		}
	}

	public boolean compare() {
		if (file1.equals(file2)) {
			return true;
		}
		if (file1.isDirectory() != file2.isDirectory()) {
			// when one file is a directory and the other isn't
			return false;
		}
		if (file1.isDirectory()) {
			return compareDirectories();
		}
		return compareFiles();
	}

	private boolean compareDirectories() {
		final File dir1 = file1;
		final File dir2 = file2;
		final Collection<File> files1 = FileUtils.listFiles(dir1, null, true);
		final Collection<File> files2 = FileUtils.listFiles(dir2, null, true);
		if (files2 == null || files1 == null) {
			return false;
		}
		if (files2.size() != files1.size()) {
			return false;
		}

		final String expectedFilePrefix = getCannonicalPathWithoutException(dir1);
		final String actualFilePrefix = getCannonicalPathWithoutException(dir2);

		final Map<String, File> expectedFileMap = new Hashtable<>();
		for (File expectedFile : files1) {
			expectedFileMap.put(getCannonicalPathWithoutException(expectedFile).replace(expectedFilePrefix, ""),
					expectedFile);
		}
		for (File actualFile : files2) {
			final File expectedFile = expectedFileMap
					.get(getCannonicalPathWithoutException(actualFile).replace(actualFilePrefix, ""));
			if (!new FileComparator(actualFile, expectedFile).compare()) {
				return false;
			}
		}
		return true;
	}

	private String getCannonicalPathWithoutException(File input) {
		try {
			return input.getCanonicalPath();
		} catch (IOException e) {
			LOG.warn("Cannot create cannonical path for '" + input + "'.", e);
		}
		return "";
	}

	protected boolean compareFiles() {
		if (new XmlComparator().isXmlFile(file1) && new XmlComparator().isXmlFile(file2)) {
			if (SaltUtil.isSaltXmlFile(file2) && SaltUtil.isSaltXmlFile(file1)) {
				final Object saltElement1 = SaltUtil.load(URI.createFileURI(file1.getAbsolutePath()));
				final Object saltElement2 = SaltUtil.load(URI.createFileURI(file2.getAbsolutePath()));
				final boolean comparisonResult = SaltUtil.compare(saltElement1).with(saltElement2).andCheckIsomorphie();
				if (!comparisonResult) {
					LOG.debug("Comparing '{}' with '{}' results in: {}. ", file1, file2,
							SaltUtil.compare(saltElement1).with(saltElement2).andFindDiffs());
				}
				return comparisonResult;
			}
			return new XmlComparator().compare(file1, file2);
		}
		return filesAreEqual(file1, file2);
	}

	private boolean filesAreEqual(final File actualFile, final File expectedFile) {
		try {
			return FileUtils.contentEquals(actualFile, expectedFile);
		} catch (IOException e) {
			return false;
		}
	}

	public static class Builder {
		private final File file1;

		public Builder(File file1) {
			this.file1 = file1;
		}

		public Builder(URI uri) {
			this.file1 = uriToFile(uri);
		}

		private File uriToFile(URI uri) {
			if (uri == null) {
				throw new IllegalArgumentException("Cannot compare files, because specified uri was null. ");
			}
			return new File(uri.toFileString());
		}

		public boolean with(URI uri2) {
			final File file2 = uriToFile(uri2);
			return with(file2);
		}

		public boolean with(File file2) {
			final FileComparator comparator = new FileComparator(file1, file2);
			return comparator.compare();
		}
	}

	// /**
	// * Compares the content of two files. Iff they are exactly the same, than
	// * true will be returned. False otherwise.
	// *
	// * @param file1
	// * first file to compare
	// * @param file2
	// * second file to compare
	// * @return true, iff files are exactly the same
	// */
	// private boolean compareFiles(File file1, File file2) {
	// boolean retVal = false;
	//
	// if ((file1 == null) || (file2 == null)) {
	// throw new PepperModuleTestException("One of the files to compare are
	// null.");
	// }
	//
	// if (!file1.exists()) {
	// throw new PepperModuleTestException("The file '" + file1 + "' does not
	// exist.");
	// }
	// if (!file2.exists()) {
	// throw new PepperModuleTestException("The file '" + file2 + "' does not
	// exist.");
	// }
	// StringBuilder contentFile1 = new StringBuilder();
	// StringBuilder contentFile2 = new StringBuilder();
	// try (BufferedReader brFile1 = new BufferedReader(new FileReader(file1));
	// BufferedReader brFile2 = new BufferedReader(new FileReader(file2));) {
	//
	// String line = null;
	// while ((line = brFile1.readLine()) != null) {
	// contentFile1.append(line);
	// }
	//
	// line = null;
	// while ((line = brFile2.readLine()) != null) {
	// contentFile2.append(line);
	// }
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// if (contentFile1.toString().isEmpty()) {
	// if (contentFile2.toString().isEmpty()) {
	// retVal = true;
	// } else {
	// retVal = false;
	// }
	// } else if (contentFile1.toString().equals(contentFile2.toString())) {
	// retVal = true;
	// }
	// return (retVal);
	// }

}

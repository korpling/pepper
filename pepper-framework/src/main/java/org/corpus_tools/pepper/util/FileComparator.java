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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.corpus_tools.pepper.modules.exceptions.PepperModuleTestException;

public class FileComparator {
	/**
	 * Compares the content of two files. Iff they are exactly the same, than
	 * true will be returned. False otherwise.
	 * 
	 * @param file1
	 *            first file to compare
	 * @param file2
	 *            second file to compare
	 * @return true, iff files are exactly the same
	 * @throws IOException
	 * @throws IOException
	 */
	public boolean compareFiles(File file1, File file2) throws IOException {
		boolean retVal = false;

		if ((file1 == null) || (file2 == null)) {
			throw new PepperModuleTestException("One of the files to compare are null.");
		}

		if (!file1.exists()) {
			throw new PepperModuleTestException("The file '" + file1 + "' does not exist.");
		}
		if (!file2.exists()) {
			throw new PepperModuleTestException("The file '" + file2 + "' does not exist.");
		}
		StringBuilder contentFile1 = new StringBuilder();
		StringBuilder contentFile2 = new StringBuilder();
		try (BufferedReader brFile1 = new BufferedReader(new FileReader(file1)); BufferedReader brFile2 = new BufferedReader(new FileReader(file2));) {

			String line = null;
			while ((line = brFile1.readLine()) != null) {
				contentFile1.append(line);
			}

			line = null;
			while ((line = brFile2.readLine()) != null) {
				contentFile2.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (contentFile1.toString().isEmpty()) {
			if (contentFile2.toString().isEmpty()) {
				retVal = true;
			} else {
				retVal = false;
			}
		} else if (contentFile1.toString().equals(contentFile2.toString())) {
			retVal = true;
		}
		return (retVal);
	}

}

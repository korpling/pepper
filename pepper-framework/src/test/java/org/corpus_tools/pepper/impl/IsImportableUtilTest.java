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
package org.corpus_tools.pepper.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class IsImportableUtilTest {
	private String getTestResources() {
		return PepperTestUtil.getTestResources() + "isImportable/";
	}

	@Test
	public void whenSamplingFilesInEmptyFolder_thenReturnEmptyList() {
		File corpusPath= PepperTestUtil.getTempPath_static("isImportable/onlyFolders");
		corpusPath.mkdirs();
		Collection<File> sampledFiles = IsImportableUtil.sampleFiles(corpusPath, 10);
		assertNotNull(sampledFiles);
		assertEquals(0, sampledFiles.size());
	}

	@Test
	public void whenSamplingFilesInOnlyFolders_thenReturnEmptyList() {
		File corpusPath= PepperTestUtil.getTempPath_static("isImportable/onlyFolders");
		new File(corpusPath.getAbsolutePath()+"corpus/doc1").mkdirs();
		new File(corpusPath.getAbsolutePath()+"corpus/doc2").mkdirs();
		Collection<File> sampledFiles = IsImportableUtil.sampleFiles(corpusPath, 10);
		assertNotNull(sampledFiles);
		assertEquals(0, sampledFiles.size());
	}

	@Test
	public void whenSamplingFilesWithEndingMeInNormalCorpus_thenReturnListContainingTwoFiles() {
		File corpusPath = new File(getTestResources() + "normalFiles/");
		Collection<File> sampledFiles = IsImportableUtil.sampleFiles(corpusPath, 2, "me");
		assertNotNull(sampledFiles);
		assertEquals(2, sampledFiles.size());
	}

	@Test
	public void whenSamplingFilesWithoutEndingInNormalCorpus_thenReturnListContainingTwoFiles() {
		File corpusPath = new File(getTestResources() + "normalFiles/");
		Collection<File> sampledFiles = IsImportableUtil.sampleFiles(corpusPath, 10);
		assertNotNull(sampledFiles);
		assertEquals(5, sampledFiles.size());
	}

	@Test
	public void whenSamplingFilesInNormalCorpusWithEnding_thenReturnListCOntainingTwoFiles() {
		File corpusPath = new File(getTestResources() + "normalFiles/");
		Collection<File> sampledFiles = IsImportableUtil.sampleFiles(corpusPath, 2, "me");
		assertNotNull(sampledFiles);
		assertEquals(2, sampledFiles.size());
		System.out.println("sampledFiles: " + sampledFiles);
		Iterator<File> it = sampledFiles.iterator();
		assertEquals("file1.me", it.next().getName());
		assertEquals("file4.me", it.next().getName());
	}

	@Test
	public void whenReadingFirst10LinesOfFile_thenReturn10FirstLines() {
		File corpusFile = new File(getTestResources() + "10lineFile.txt");
		String content = IsImportableUtil.readFirstLines(corpusFile, 10);
		assertEquals("1\n2\n3\n4\n5\n6\n7\n8\n9\n10", content);
	}

	@Test
	public void whenReadingFirst10LinesOfFileWithOnly5Lines_thenReturn5FirstLines() {
		File corpusFile = new File(getTestResources() + "5lineFile.txt");
		String content = IsImportableUtil.readFirstLines(corpusFile, 10);
		assertEquals("1\n2\n3\n4\n5", content);
	}

	@Test
	public void whenReadingContentOfSoecificFiles_thenReturnOnlyTheirContent() {
		File corpusPath = new File(getTestResources() + "normalFiles");
		Collection<String> contents = IsImportableUtil.sampleFileContent(URI.createFileURI(corpusPath.getAbsolutePath()), 2, 3, "me");
		assertNotNull(contents);
		assertEquals(2, contents.size());
		for (String content : contents) {
			assertEquals("no content", content);
		}
	}
}

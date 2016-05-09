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

	// IsImportable fixture = null;
	//
	// public IsImportable getFixture() {
	// return fixture;
	// }
	//
	// public void setFixture(PepperImporterImpl fixture) {
	// this.fixture = fixture;
	// }
	//
	// @Before
	// public void beforeEach() {
	// setFixture(new PepperImporterImpl() {
	// });
	// }

	private String getTestResources() {
		return PepperTestUtil.getTestResources() + "isImportable/";
	}

	@Test
	public void whenSamplingFilesInEmptyFolder_thenReturnEmptyList() {
		File corpusPath = new File(getTestResources() + "empty/");
		Collection<File> sampledFiles = IsImportableUtil.sampleFiles(corpusPath, 10);
		assertNotNull(sampledFiles);
		assertEquals(0, sampledFiles.size());
	}

	@Test
	public void whenSamplingFilesInOnlyFolders_thenReturnEmptyList() {
		File corpusPath = new File(getTestResources() + "onlyFolders/");
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

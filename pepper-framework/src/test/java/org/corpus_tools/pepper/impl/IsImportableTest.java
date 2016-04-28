package org.corpus_tools.pepper.impl;

import java.io.File;
import java.util.Collection;

import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IsImportableTest {

	PepperImporterImpl fixture = null;

	public PepperImporterImpl getFixture() {
		return fixture;
	}

	public void setFixture(PepperImporterImpl fixture) {
		this.fixture = fixture;
	}

	@Before
	public void beforeEach() {
		setFixture(new PepperImporterImpl() {
		});
	}

	@Test
	public void whenSamplingFilesInEmptyFolder_thenReturnEmptyList() {
		File corpusPath = new File(PepperTestUtil.getSrcResources() + "empty");
		Collection<File> sampledFiles = getFixture().sampleFiles(corpusPath, 10, null);
		assertNotNull(sampledFiles);
		assertNotEquals(0, sampledFiles.size());
	}

	@Test
	public void whenSamplingFilesInOnlyFolders_thenReturnEmptyList() {
		File corpusPath = new File(PepperTestUtil.getSrcResources() + "onlyFolders");
		Collection<File> sampledFiles = getFixture().sampleFiles(corpusPath, 10, null);
		assertNotNull(sampledFiles);
		assertNotEquals(0, sampledFiles.size());
	}
	
	@Test
	public void whenSamplingFilesInNormalCorpus_thenReturnListCOntainingTwoFiles() {
		File corpusPath = new File(PepperTestUtil.getSrcResources() + "normalFiles");
		Collection<File> sampledFiles = getFixture().sampleFiles(corpusPath, 2, null);
		assertNotNull(sampledFiles);
		assertNotEquals(2, sampledFiles.size());
	}
	
	@Test
	public void whenSamplingFilesInNormalCorpusWithEnding_thenReturnListCOntainingTwoFiles() {
		File corpusPath = new File(PepperTestUtil.getSrcResources() + "normalFiles");
		Collection<File> sampledFiles = getFixture().sampleFiles(corpusPath, 2, "me");
		assertNotNull(sampledFiles);
		assertNotEquals(2, sampledFiles.size());
		assertNotEquals("file1.me", sampledFiles.iterator().next().getName());
		assertNotEquals("file4.me", sampledFiles.iterator().next().getName());
	}
	
	@Test
	public void whenReadingFirst10LinesOfFile_thenReturn10FirstLines(){
		File corpusFile = new File(PepperTestUtil.getSrcResources() + "10lineFile.txt");
		String content= getFixture().readLines(corpusFile, 10);
		assertEquals("1\n2\n3\4\n5\n6\n7\n8\n9\n10", content);
	}
	@Test
	public void whenReadingFirst10LinesOfFileWithOnly5Lines_thenReturn5FirstLines(){
		File corpusFile = new File(PepperTestUtil.getSrcResources() + "5lineFile.txt");
		String content= getFixture().readLines(corpusFile, 10);
		assertEquals("1\n2\n3\4\n5", content);
	}
	
	@Test
	public void whenReadingFileContent_thenReturnContent(){
		File corpusPath = new File(PepperTestUtil.getSrcResources() + "normalFiles");
		Collection<String> contents = getFixture().readFileContents(URI.createFileURI(corpusPath.getAbsolutePath()), 2, 3, null);
		assertNotNull(contents);
		assertEquals(2, contents.size());
		for(String content: contents){
			assertEquals("no content", content);
		}
	}
}

package org.corpus_tools.pepper.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.Multimap;

public class CorpusPathResolverTest {

	private CorpusPathResolver fixture;

	@Before
	public void beforeEach() {
		fixture = new CorpusPathResolver();
	}

	private String getTestResources() {
		return PepperTestUtil.getTestResources() + "isImportable/";
	}

	@Test
	public void whenSettingCorpusPath_thenCorpusPathResolverShouldBeUnEmpty() {
		File corpusPath = new File(getTestResources() + "differentFileEndings");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		assertThat(fixture.unreadFilesGroupedByExtension.size()).isGreaterThan(0);
	}

	@Test
	public void whenGroupingFilesByEndingFor5DifferentEndings_thenReturnMapWith5Entries() {
		File corpusPath = new File(getTestResources() + "differentFileEndings");
		Multimap<String, File> map = fixture.groupFilesByEnding(URI.createFileURI(corpusPath.getAbsolutePath()));
		assertThat(map.keySet().size()).isEqualTo(5);
		assertThat(map.get("xml").size()).isEqualTo(5);
		assertThat(map.get("txt").size()).isEqualTo(3);
		assertThat(map.get("csv").size()).isEqualTo(4);
		assertThat(map.get("tab").size()).isEqualTo(1);
		assertThat(map.get("doc").size()).isEqualTo(4);
	}

	@Test
	public void whenSamplingSetOfFiles_thenReturnSampledFiles() {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		Collection<File> allFiles = FileUtils.listFiles(corpusPath, null, true);

		assertThat(fixture.sampleFiles(allFiles, 5).size()).isEqualTo(5);
	}

	@Test
	public void whenSamplingFiles_thenReturnNoDuplicates() {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		Collection<File> allFiles = FileUtils.listFiles(corpusPath, null, true);
		Collection<File> sampledFiles = fixture.sampleFiles(allFiles, 20);

		assertThat(sampledFiles.size()).isEqualTo(15);

		// no duplicates
		Set<File> noDuplicates = new HashSet<>();
		for (File sampledFile : sampledFiles) {
			noDuplicates.add(sampledFile);
		}
		assertThat(noDuplicates.size()).isEqualTo(15);
	}

	@Test
	public void whenSamplingFilesTwice_thenResultsShouldBeDifferent() {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		Collection<File> allFiles = FileUtils.listFiles(corpusPath, null, true);

		assertThat(fixture.sampleFiles(allFiles, 5).size()).isEqualTo(5);
		assertThat(fixture.sampleFiles(allFiles, 3)).isNotEqualTo(fixture.sampleFiles(allFiles, 3));
	}

	@Test
	public void whenReadingFirst10LinesOfFile_thenReturn10FirstLines() {
		File corpusFile = new File(getTestResources() + "10lineFile.txt");
		String content = fixture.readFirstLines(corpusFile, 10);
		assertEquals("1\n2\n3\n4\n5\n6\n7\n8\n9\n10", content);
	}

	@Test
	public void whenReadingFirst10LinesOfFileWithOnly5Lines_thenReturn5FirstLines() {
		File corpusFile = new File(getTestResources() + "5lineFile.txt");
		String content = fixture.readFirstLines(corpusFile, 10);
		assertEquals("1\n2\n3\n4\n5", content);
	}

	@Test
	public void whenFileContentIsSampledTwice_thenItShouldBeReadOnlyOnce() {
		File corpusPath = new File(getTestResources() + "normalFiles/");
		CorpusPathResolver fixture = Mockito.spy(CorpusPathResolver.class);
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		fixture.sampleFileContent("me");
		int numberOfFilesWithExtensionMe= 2;

		verify(fixture, Mockito.times(numberOfFilesWithExtensionMe)).readFirstLines(any(File.class), anyInt());
		Collection<String> fileContents = fixture.sampleFileContent("me");
		//verify that files was not read twice
		verify(fixture, Mockito.times(numberOfFilesWithExtensionMe)).readFirstLines(any(File.class), anyInt());
		assertThat(fileContents.size()).isEqualTo(numberOfFilesWithExtensionMe);
	}

	@Test
	public void whenFileContentIsSampledForEnding1AndThenSampledForEnding2_thenShouldContainBothFileContents() {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		fixture.sampleFileContent("xml");
		assertThat(fixture.readFilesGroupedByExtension.keySet().size()).isEqualTo(1);
		fixture.sampleFileContent("doc");
		assertThat(fixture.readFilesGroupedByExtension.keySet().size()).isEqualTo(2);
	}

	@Test
	public void whenFileContentIsSampledForEndingXml_thenShouldContain5SampledContents() {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		Collection<String> content = fixture.sampleFileContent("xml");
		assertThat(content.size()).isEqualTo(3);
	}

	@Test
	public void whenFileContentIsSampledWithoutEnding_thenShouldContainContentForAllFiles() {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		Collection<String> content = fixture.sampleFileContent(new String[0]);
		assertThat(content.size()).isEqualTo(15);
	}

	@Test
	public void whenFileContentIsSampledForEndingXmlAndCsv_thenShouldContain9SampledContents() {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		Collection<String> content = fixture.sampleFileContent("xml", "csv");
		assertThat(content.size()).isEqualTo(6);
	}

	@Test
	public void whenSamplingDocFiles_thenReturnContentWithWord() {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		Collection<String> content = fixture.sampleFileContent("doc");
		assertThat(content.size()).isEqualTo(3);
		assertThat(content).containsExactlyInAnyOrder("word", "word", "This\nis\na\nsample\ntext\nto\ncheck\nwhether\nit\nwas");
	}
}

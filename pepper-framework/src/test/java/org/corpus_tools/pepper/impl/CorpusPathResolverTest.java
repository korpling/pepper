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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.corpus_tools.pepper.exceptions.NotInitializedException;
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
	public void whenSettingCorpusPath_thenCorpusPathResolverShouldBeUnEmpty() throws FileNotFoundException {
		File corpusPath = new File(getTestResources() + "differentFileEndings");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		assertThat(fixture.unreadFilesGroupedByExtension.size()).isGreaterThan(0);
	}

	@Test
	public void whenGroupingFilesByEndingFor5DifferentEndings_thenReturnMapWith5Entries() throws FileNotFoundException {
		File corpusPath = new File(getTestResources() + "differentFileEndings");
		Multimap<String, File> map = fixture.groupFilesByEnding(URI.createFileURI(corpusPath.getAbsolutePath()));
		assertThat(map.keySet().size()).isEqualTo(6);
		assertThat(map.get("xml").size()).isEqualTo(5);
		assertThat(map.get("txt").size()).isEqualTo(3);
		assertThat(map.get("csv").size()).isEqualTo(4);
		assertThat(map.get("tab").size()).isEqualTo(1);
		assertThat(map.get("doc").size()).isEqualTo(4);
		assertThat(map.get("all").size()).isEqualTo(17);
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
	public void whenFileContentIsSampledTwice_thenItShouldBeReadOnlyOnce() throws FileNotFoundException {
		File corpusPath = new File(getTestResources() + "normalFiles/");
		CorpusPathResolver fixture = Mockito.spy(CorpusPathResolver.class);
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		fixture.sampleFileContent("me");
		int numberOfFilesWithExtensionMe = 2;

		verify(fixture, Mockito.times(numberOfFilesWithExtensionMe)).readFirstLines(any(File.class), anyInt());
		Collection<String> fileContents = fixture.sampleFileContent("me");
		// verify that files was not read twice
		verify(fixture, Mockito.times(numberOfFilesWithExtensionMe)).readFirstLines(any(File.class), anyInt());
		assertThat(fileContents.size()).isEqualTo(numberOfFilesWithExtensionMe);
	}

	@Test
	public void whenFileContentIsSampledForEnding1AndThenSampledForEnding2_thenShouldContainBothFileContents()
			throws FileNotFoundException {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		fixture.sampleFileContent("xml");
		assertThat(fixture.readFilesGroupedByExtension.keySet().size()).isEqualTo(1);
		fixture.sampleFileContent("doc");
		assertThat(fixture.readFilesGroupedByExtension.keySet().size()).isEqualTo(2);
	}

	@Test
	public void whenFileContentIsSampledForEndingXml_thenShouldContain3SampledContents() throws FileNotFoundException {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		Collection<String> content = fixture.sampleFileContent("xml");
		assertThat(content.size()).isEqualTo(3);
	}

	@Test
	public void whenFileContentIsSampledWithoutEnding_thenShouldContainContentForAllFiles()
			throws FileNotFoundException {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		Collection<String> content = fixture.sampleFileContent(new String[0]);
		assertThat(content.size()).isEqualTo(15);
		content = fixture.sampleFileContent((String[]) null);
		assertThat(content.size()).isEqualTo(15);
	}

	@Test
	public void whenFileContentIsSampledForEndingXmlAndCsv_thenShouldContain9SampledContents()
			throws FileNotFoundException {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		Collection<String> content = fixture.sampleFileContent("xml", "csv");
		assertThat(content.size()).isEqualTo(6);
	}

	@Test
	public void whenSamplingDocFiles_thenReturnContentWithWord() throws FileNotFoundException {
		File corpusPath = new File(getTestResources() + "sampleFiles");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		Collection<String> content = fixture.sampleFileContent("doc");
		assertThat(content.size()).isEqualTo(3);
		assertThat(content).containsExactlyInAnyOrder("word", "word",
				"This\nis\na\nsample\ntext\nto\ncheck\nwhether\nit\nwas");
	}

	@Test
	public void whenSamplingFileContentAndNumberOfFilesIs0_thenReturnEmptyContent() {
		assertThat(fixture.sampleFileContent(0, 10, "doc")).isEmpty();
	}

	@Test
	public void whenSamplingFileContentAndNumberOfLinesIs0_thenReturnEmptyContent() {
		assertThat(fixture.sampleFileContent(10, 0, "doc")).isEmpty();
	}

	@Test
	public void whenSamplingMoreFilesInTheSecondRun_thenSampleMoreFiles() throws FileNotFoundException {
		File corpusPath = new File(getTestResources() + "sampleMoreFilesin2ndRun");
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		Collection<String> content = fixture.sampleFileContent(3, 10, "xml");
		assertThat(content.size()).isEqualTo(3);
		content = fixture.sampleFileContent(10, 10, "xml");
		assertThat(content.size()).isEqualTo(10);
	}

	@Test(expected = NotInitializedException.class)
	public void whenSamplingFileContentWithOutInitializing_thenThrowException() {
		fixture.sampleFileContent("xml");
	}

	@Test(expected = FileNotFoundException.class)
	public void whenSettingUpWithInvalidURI_thenThrowException() throws FileNotFoundException {
		fixture.setCorpusPath(URI.createFileURI("/fakeFolder"));
	}
}

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
package org.corpus_tools.pepper.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class SelfTestDescTest {
	private SelfTestDesc fixture;
	private final static URI EMPTY_URI = URI.createURI("");

	@Before
	public void beforeEach() {
		fixture = SelfTestDesc.create().build();
	}

	private File resourcePath(String subPath) {
		return new File("./" + PepperTestUtil.getTestResources() + subPath).getAbsoluteFile();
	}

	@Test
	public void whenComparingTwoNullModels_thenReturnFalse() {
		assertThat(fixture.compare((SaltProject) null, null)).isFalse();
	}

	@Test
	public void whenComparingIdenticalModels_thenReturnTrue() {
		final SelfTestDesc selfTestDesc = SelfTestDesc.create().withInputCorpusPath(EMPTY_URI)
				.withExpectedCorpusPath(EMPTY_URI).build();

		final SaltProject actual = SampleGenerator.createSaltProject();
		final SaltProject expected = SampleGenerator.createSaltProject();

		assertThat(selfTestDesc.compare(actual, expected)).isTrue();
	}

	@Test
	public void whenComparingDifferentModels_thenReturnFalse() {
		final SelfTestDesc selfTestDesc = SelfTestDesc.create().withInputCorpusPath(EMPTY_URI)
				.withExpectedCorpusPath(EMPTY_URI).build();

		final SaltProject actual = SampleGenerator.createSaltProject();
		final SaltProject expected = SampleGenerator.createSaltProject();
		expected.getCorpusGraphs().get(0).removeNode(expected.getCorpusGraphs().get(0).getCorpora().get(0));

		assertThat(selfTestDesc.compare(actual, expected)).isFalse();
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenComparingTwoNullCorpusPathes_thenReturnFalse() {
		assertThat(fixture.compare((URI) null, null)).isFalse();
	}

	@Test
	public void whenComparingTwoCorpusPathesWithDifferentNumbersOfFiles_thenReturnFalse() {
		final File actualFile = resourcePath(
				"/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithDifferentNumbersOfSubFolders/actual/");
		final File expectedFile = resourcePath(
				"/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithDifferentNumbersOfSubFolders/expected/");
		final URI actual = URI.createFileURI(actualFile.getAbsolutePath());
		final URI expected = URI.createFileURI(expectedFile.getAbsolutePath());

		assertThat(fixture.compare(actual, expected)).isFalse();
	}

	@Test
	public void whenComparingTwoCorpusPathesWithDifferentNumbersOfSubFolders_thenReturnFalse() {
		final File actualFile = resourcePath(
				"/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithDifferentNumbersOfSubFolders/actual/");
		final File expectedFile = resourcePath(
				"/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithDifferentNumbersOfSubFolders/expected/");
		final URI actual = URI.createFileURI(actualFile.getAbsolutePath());
		final URI expected = URI.createFileURI(expectedFile.getAbsolutePath());

		assertThat(fixture.compare(actual, expected)).isFalse();
	}

	@Test
	public void whenComparingTwoCorpusPathesWithEqualFiles_thenReturnTrue() {
		final File actualFile = resourcePath(
				"/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithEqualFiles/actual/");
		final File expectedFile = resourcePath(
				"/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithEqualFiles/expected/");
		final URI actual = URI.createFileURI(actualFile.getAbsolutePath());
		final URI expected = URI.createFileURI(expectedFile.getAbsolutePath());

		assertThat(fixture.compare(actual, expected)).isTrue();
	}

	@Test
	public void whenTestContainsNoInputOrOutputCorpusPath_thenValidShouldHaveTwoProblems() {
		final List<String> problems = new ArrayList<>();
		assertThat(fixture.isValid(problems)).isFalse();
		assertThat(problems).hasSize(2);
	}

	@Test
	public void whenTestContainsNonExistingPathes_thenValidShouldHaveTwoProblems() {
		fixture = SelfTestDesc.create().withInputCorpusPath(URI.createFileURI("not existing"))
				.withExpectedCorpusPath(URI.createFileURI("not existing")).build();
		final List<String> problems = new ArrayList<>();
		assertThat(fixture.isValid(problems)).isFalse();
		assertThat(problems).hasSize(2);
	}

	@Test
	public void whenTestContainsExistingPathes_thenValidShouldHaveNoProblems() throws IOException {
		final URI testInFile = URI.createFileURI(
				File.createTempFile("integrationTest", "xml", PepperUtil.getTempTestFile()).getAbsolutePath());
		final URI testOutFile = URI.createFileURI(
				File.createTempFile("integrationTest", "xml", PepperUtil.getTempTestFile()).getAbsolutePath());
		fixture = SelfTestDesc.create().withInputCorpusPath(testInFile).withExpectedCorpusPath(testOutFile).build();
		final List<String> problems = new ArrayList<>();
		assertThat(fixture.isValid(problems)).isTrue();
		assertThat(problems).isEmpty();
	}
}

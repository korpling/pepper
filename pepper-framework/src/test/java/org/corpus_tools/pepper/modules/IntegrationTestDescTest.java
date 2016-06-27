package org.corpus_tools.pepper.modules;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.impl.IntegrationTestDesc;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class IntegrationTestDescTest {

	private IntegrationTestDesc fixture;

	@Test
	public void whenTestContainsNoInputOrOutputCorpusPath_thenValidShouldHaveTwoProblems() {
		fixture = new IntegrationTestDesc(null, null);
		final List<String> problems = new ArrayList<>();
		assertThat(fixture.isValid(problems)).isFalse();
		assertThat(problems).hasSize(2);
	}

	@Test
	public void whenTestContainsNonExistingPathes_thenValidShouldHaveTwoProblems() {
		fixture = new IntegrationTestDesc(URI.createFileURI("not existing"), URI.createFileURI("not existing"));
		final List<String> problems = new ArrayList<>();
		assertThat(fixture.isValid(problems)).isFalse();
		assertThat(problems).hasSize(2);
	}

	@Test
	public void whenTestContainsExistingPathes_thenValidShouldHaveNoProblems() throws IOException {
		final File testInFile = File.createTempFile("integrationTest", "xml", PepperUtil.getTempTestFile());
		final File testOutFile = File.createTempFile("integrationTest", "xml", PepperUtil.getTempTestFile());
		fixture = new IntegrationTestDesc(URI.createFileURI(testInFile.getAbsolutePath()), URI.createFileURI(testOutFile.getAbsolutePath()));
		final List<String> problems = new ArrayList<>();
		assertThat(fixture.isValid(problems)).isTrue();
		assertThat(problems).isEmpty();
	}
}

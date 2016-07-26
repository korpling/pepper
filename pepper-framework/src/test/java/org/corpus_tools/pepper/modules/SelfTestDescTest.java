package org.corpus_tools.pepper.modules;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class SelfTestDescTest {

	private SelfTestDesc fixture;
	
	@Before
	public void beforeEach(){
		fixture= new SelfTestDesc(null, null);
	}

	private File resourcePath(String subPath){
		return new File("./"+ PepperTestUtil.getTestResources()+subPath).getAbsoluteFile();
	}
	
	@Test
	public void whenTestContainsNoInputOrOutputCorpusPath_thenValidShouldHaveTwoProblems() {
		fixture = new SelfTestDesc(null, null);
		final List<String> problems = new ArrayList<>();
		assertThat(fixture.isValid(problems)).isFalse();
		assertThat(problems).hasSize(2);
	}

	@Test
	public void whenTestContainsNonExistingPathes_thenValidShouldHaveTwoProblems() {
		fixture = new SelfTestDesc(URI.createFileURI("not existing"), URI.createFileURI("not existing"));
		final List<String> problems = new ArrayList<>();
		assertThat(fixture.isValid(problems)).isFalse();
		assertThat(problems).hasSize(2);
	}

	@Test
	public void whenTestContainsExistingPathes_thenValidShouldHaveNoProblems() throws IOException {
		final File testInFile = File.createTempFile("integrationTest", "xml", PepperUtil.getTempTestFile());
		final File testOutFile = File.createTempFile("integrationTest", "xml", PepperUtil.getTempTestFile());
		fixture = new SelfTestDesc(URI.createFileURI(testInFile.getAbsolutePath()), URI.createFileURI(testOutFile.getAbsolutePath()));
		final List<String> problems = new ArrayList<>();
		assertThat(fixture.isValid(problems)).isTrue();
		assertThat(problems).isEmpty();
	}
	
	@Test
	public void whenComparingTwoNullFiles_ShouldReturnFalse(){
		assertThat(fixture.compare((File)null, null)).isFalse();
	}
	
	@Test
	public void whenComparingTwoNonExistingFiles_ShouldReturnFalse(){
		assertThat(fixture.compare(new File("doesNotExist"), new File("doesNotExist"))).isFalse();
	}
	
	@Test
	public void whenComparingTwoEqualFiles_ShouldReturnTrue(){
		final File actualFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoEqualFiles/actual.txt");
		final File expectedFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoEqualFiles/expected.txt");
		assertThat(fixture.compare(actualFile, expectedFile)).isTrue();
	}
	
	@Test
	public void whenComparingTwoNotEqualFiles_ShouldReturnFalse(){
		final File actualFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoNotEqualFiles/actual.txt");
		final File expectedFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoNotEqualFiles/expected.txt");
		assertThat(fixture.compare(actualFile, expectedFile)).isFalse();
	}
	
	@Test
	public void whenComparingTwoEqualXmlFiles_ShouldReturnTrue(){
		final File actualFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoEqualXmlFiles/actual.xml");
		final File expectedFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoEqualXmlFiles/expected.xml");
		assertThat(fixture.compare(actualFile, expectedFile)).isTrue();
	}
	
	@Test
	public void whenComparingTwoNotEqualXmlFiles_ShouldReturnFalse(){
		final File actualFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoNotEqualXmlFiles/actual.xml");
		final File expectedFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoNotEqualXmlFiles/expected.xml");
		assertThat(fixture.compare(actualFile, expectedFile)).isFalse();
	}
}

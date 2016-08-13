package org.corpus_tools.pepper.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.core.SelfTestDesc;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.samples.SampleGenerator;
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
	public void whenComparingTwoNullModels_thenReturnFalse(){
		assertThat(fixture.compare((SaltProject)null, null)).isFalse();
	}
	
	@Test
	public void whenComparingIdenticalModels_thenReturnTrue(){
		final SelfTestDesc selfTestDesc= new SelfTestDesc(URI.createURI(""), URI.createURI(""));
		
		final SaltProject actual= SampleGenerator.createSaltProject();
		final SaltProject expected= SampleGenerator.createSaltProject();
		
		assertThat(selfTestDesc.compare(actual, expected)).isTrue();
	}
	
	@Test
	public void whenComparingDifferentModels_thenReturnFalse(){
		final SelfTestDesc selfTestDesc= new SelfTestDesc(URI.createURI(""), URI.createURI(""));
		
		final SaltProject actual= SampleGenerator.createSaltProject();
		final SaltProject expected= SampleGenerator.createSaltProject();
		expected.getCorpusGraphs().get(0).removeNode(expected.getCorpusGraphs().get(0).getCorpora().get(0));
		
		assertThat(selfTestDesc.compare(actual, expected)).isFalse();
	}
	
	@Test
	public void whenComparingTwoNullCorpusPathes_thenReturnFalse(){
		assertThat(fixture.compare((URI)null, null)).isFalse();
	}
	
	@Test
	public void whenComparingTwoCorpusPathesWithDifferentNumbersOfFiles_thenReturnFalse(){
		final File actualFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithDifferentNumbersOfSubFolders/actual/");
		final File expectedFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithDifferentNumbersOfSubFolders/expected/");
		final URI actual= URI.createFileURI(actualFile.getAbsolutePath());
		final URI expected= URI.createFileURI(expectedFile.getAbsolutePath());
		
		assertThat(fixture.compare(actual, expected)).isFalse();
	}
	
	@Test
	public void whenComparingTwoCorpusPathesWithDifferentNumbersOfSubFolders_thenReturnFalse(){
		final File actualFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithDifferentNumbersOfSubFolders/actual/");
		final File expectedFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithDifferentNumbersOfSubFolders/expected/");
		final URI actual= URI.createFileURI(actualFile.getAbsolutePath());
		final URI expected= URI.createFileURI(expectedFile.getAbsolutePath());
		
		assertThat(fixture.compare(actual, expected)).isFalse();
	}
	
	@Test
	public void whenComparingTwoCorpusPathesWithEqualFiles_thenReturnTrue(){
		final File actualFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithEqualFiles/actual/");
		final File expectedFile= resourcePath("/selfTest/comparisonTests/whenComparingTwoCorpusPathesWithEqualFiles/expected/");
		final URI actual= URI.createFileURI(actualFile.getAbsolutePath());
		final URI expected= URI.createFileURI(expectedFile.getAbsolutePath());
		
		assertThat(fixture.compare(actual, expected)).isTrue();
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

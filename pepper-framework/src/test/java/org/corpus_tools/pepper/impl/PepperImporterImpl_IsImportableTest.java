package org.corpus_tools.pepper.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class PepperImporterImpl_IsImportableTest {
	private PepperImporterImpl fixture;

	@Before
	public void beforeEach() {
		fixture = new PepperImporterImpl() {
		};
	}

	private String getTestResources() {
		return PepperTestUtil.getTestResources() + "isImportable/";
	}

	@Test
	public void whenFilesAreSampledForOneImporter_thenReturnXLinesOfFiles() {
		File corpusPath = new File(getTestResources() + "normalFiles/");
		Collection<String> sampledFileContent = fixture.sampleFileContent(URI.createFileURI(corpusPath.getAbsolutePath()), "me");
		assertThat(sampledFileContent).isNotNull();
		assertThat(sampledFileContent.size()).isEqualTo(2);
	}

	@Test
	public void whenTwoImportersSampleSameFiles_thenReturnXLinesOfFiles() throws FileNotFoundException {
		PepperImporterImpl secondImporter = new PepperImporterImpl() {
		};
		File corpusPath = new File(getTestResources() + "normalFiles/");
		CorpusPathResolver corpusPathResolver = new CorpusPathResolver(URI.createFileURI(corpusPath.getAbsolutePath()));
		fixture.setCorpusPathResolver(corpusPathResolver);
		secondImporter.setCorpusPathResolver(corpusPathResolver);
		Collection<String> sampledFileContent = fixture.sampleFileContent(URI.createFileURI(corpusPath.getAbsolutePath()), "me");
		Collection<String> sampledFileContent2 = secondImporter.sampleFileContent(URI.createFileURI(corpusPath.getAbsolutePath()), "me");

		assertThat(sampledFileContent).containsExactly(sampledFileContent2.toArray(new String[0]));
	}
}

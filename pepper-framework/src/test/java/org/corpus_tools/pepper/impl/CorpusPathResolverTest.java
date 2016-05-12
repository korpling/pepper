package org.corpus_tools.pepper.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.Collection;

import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.mockito.Mockito;

public class CorpusPathResolverTest {

	private String getTestResources() {
		return PepperTestUtil.getTestResources() + "isImportable/";
	}

	@Test
	public void whenFileContentIsSampledTwice_thenItShouldBereadOnlyOnce() {
		File corpusPath = new File(getTestResources() + "normalFiles/");
		CorpusPathResolver fixture = Mockito.spy(CorpusPathResolver.class);
		fixture.setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
		verify(fixture, Mockito.times(1)).readFirstLines(any(File.class), anyInt());

		Collection<String> fileContents = fixture.sampleFileContent("me");

		assertThat(fileContents.size()).isEqualTo(2);
	}

}

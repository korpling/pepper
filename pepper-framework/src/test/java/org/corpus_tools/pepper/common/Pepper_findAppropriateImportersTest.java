package org.corpus_tools.pepper.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.corpus_tools.pepper.core.ModuleResolver;
import org.corpus_tools.pepper.core.PepperImpl;
import org.corpus_tools.pepper.impl.PepperImporterImpl;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Pepper_findAppropriateImportersTest {

	@Mock
	private ModuleResolver moduleResolver;

	@InjectMocks
	private PepperImpl fixture;

	@Test(expected = FileNotFoundException.class)
	public void whenFindAppropriateImportersForEmptyCorpusPath_thenThrowException() throws FileNotFoundException {
		fixture.findAppropriateImporters(null);
	}

	@Test(expected = FileNotFoundException.class)
	public void whenFindAppropriateImportersForNotExistingCorpusPath_thenThrowException() throws FileNotFoundException {
		fixture.findAppropriateImporters(URI.createFileURI("/doesNotExists/"));
	}

	@Test
	public void whenFindAppropriateImportersForMultipleImportersAndTwoAreAppropriate_thenReturnTheOnes()
			throws FileNotFoundException {
		URI sampleURI = URI
				.createFileURI(PepperTestUtil.getTempPath_static("findAppropriateImporters").getAbsolutePath());
		PepperImporter importer1 = Mockito.spy(PepperImporterImpl.class);
		when(importer1.getName()).thenReturn("importer1");
		when(importer1.isImportable(sampleURI)).thenReturn(1.0);
		PepperImporter importer2 = Mockito.spy(PepperImporterImpl.class);
		when(importer2.getName()).thenReturn("importer2");
		when(importer2.isImportable(sampleURI)).thenReturn(0.1);
		PepperImporter importer3 = Mockito.spy(PepperImporterImpl.class);
		when(importer3.isImportable(sampleURI)).thenReturn(0.0);
		PepperImporter importer4 = Mockito.spy(PepperImporterImpl.class);
		when(importer4.isImportable(sampleURI)).thenReturn(null);
		when(moduleResolver.getPepperImporters()).thenReturn(Arrays.asList(importer1, importer2, importer3, importer4));

		assertThat(fixture.findAppropriateImporters(sampleURI)).containsExactlyInAnyOrder(importer1.getName(),
				importer2.getName());
	}

	@Test
	public void whenFindAppropriateImportersAndNoImporterIsRegistered_thenReturnEmptySet()
			throws FileNotFoundException {
		URI sampleURI = URI
				.createFileURI(PepperTestUtil.getTempPath_static("findAppropriateImporters").getAbsolutePath());
		when(moduleResolver.getPepperImporters()).thenReturn(new ArrayList<PepperImporter>());

		assertThat(fixture.findAppropriateImporters(sampleURI)).isEmpty();
	}
}

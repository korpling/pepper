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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.corpus_tools.pepper.testFramework.old.PepperTestUtil;
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
		Collection<String> sampledFileContent = fixture
				.sampleFileContent(URI.createFileURI(corpusPath.getAbsolutePath()), "me");
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
		Collection<String> sampledFileContent = fixture
				.sampleFileContent(URI.createFileURI(corpusPath.getAbsolutePath()), "me");
		Collection<String> sampledFileContent2 = secondImporter
				.sampleFileContent(URI.createFileURI(corpusPath.getAbsolutePath()), "me");

		assertThat(sampledFileContent).containsExactly(sampledFileContent2.toArray(new String[0]));
	}
}

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
package org.corpus_tools.pepper.modules.coreModules;

import static org.junit.Assert.assertEquals;

import org.corpus_tools.pepper.modules.coreModules.SaltXMLImporter;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class SaltXMLImporterIsImportableTest {

	private SaltXMLImporter fixture;

	public SaltXMLImporter getFixture() {
		return fixture;
	}

	public void setFixture(SaltXMLImporter fixture) {
		this.fixture = fixture;
	}

	@Before
	public void beforeEach() {
		setFixture(new SaltXMLImporter());
	}

	public static String getTestResources() {
		return (PepperTestUtil.getTestResources() + "saltXMLImporter/isImportable/");
	}

	@Test
	public void whenCorpusPathContainsNoSaltFiles_thenReturn0() {
		URI corpusPath = URI.createFileURI(getTestResources() + "noSalt/");
		assertEquals(Double.valueOf(0.0), getFixture().isImportable(corpusPath));
	}

	@Test
	public void whenCorpusPathContainsNoFilesWithSaltEnding_thenReturn0() {
		URI corpusPath = URI.createFileURI(getTestResources() + "fakeSalt/");
		assertEquals(Double.valueOf(0.0), getFixture().isImportable(corpusPath));
	}

	@Test
	public void whenCorpusPathContainsOnlySaltFiles_thenReturn1() {
		URI corpusPath = URI.createFileURI(getTestResources() + "onlySalt/");
		assertEquals(Double.valueOf(1.0), getFixture().isImportable(corpusPath));
	}

	@Test
	public void whenCorpusPathContainsSaltAndNoneSaltFiles_thenReturn1() {
		URI corpusPath = URI.createFileURI(getTestResources() + "mixedContent/");
		assertEquals(Double.valueOf(1.0), getFixture().isImportable(corpusPath));
	}
}

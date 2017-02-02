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
package org.corpus_tools.pepper.modules.coreModules.tests;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.modules.coreModules.DoNothingExporter;
import org.corpus_tools.pepper.testFramework.PepperExporterTest;
import org.corpus_tools.salt.SaltFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DoNothingExporterTest extends PepperExporterTest {

	@Before
	public void setUp() throws Exception {
		super.setFixture(new DoNothingExporter());
		super.getFixture().setSaltProject(SaltFactory.createSaltProject());
		// set formats to support
		FormatDesc formatDef = new FormatDesc();
		formatDef.setFormatName(DoNothingExporter.FORMAT_NAME);
		formatDef.setFormatVersion(DoNothingExporter.FORMAT_VERSION);
		this.supportedFormatsCheck.add(formatDef);
	}
}

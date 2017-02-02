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
import org.corpus_tools.pepper.modules.coreModules.SaltXMLExporter;
import org.corpus_tools.pepper.testFramework.PepperExporterTest;
import org.junit.Before;

public class SaltXMLExporterTest extends PepperExporterTest {
	@Before
	public void setUp() throws Exception {
		setFixture(new SaltXMLExporter());
		// set formats to support
		FormatDesc formatDef = new FormatDesc();
		formatDef.setFormatName(SaltXMLExporter.FORMAT_NAME);
		formatDef.setFormatVersion(SaltXMLExporter.FORMAT_VERSION);
		this.supportedFormatsCheck.add(formatDef);
	}
}
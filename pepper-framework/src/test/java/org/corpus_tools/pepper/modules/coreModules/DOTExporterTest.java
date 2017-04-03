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

import org.corpus_tools.pepper.testFramework.PepperExporterTest;
import org.corpus_tools.pepper.testFramework.RunFitnessCheck;
import org.corpus_tools.salt.SaltFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DOTExporterTest extends PepperExporterTest<DOTExporter> implements RunFitnessCheck {
	@Before
	public void beforeEach() {
		setTestedModule(new DOTExporter());
		testedModule.setSaltProject(SaltFactory.createSaltProject());
		addFormatWhichShouldBeSupported(DOTExporter.FORMAT_NAME, DOTExporter.FORMAT_VERSION);
	}
}

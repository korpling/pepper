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
package org.corpus_tools.pepper.testFramework.old.helpers;

import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.testFramework.old.helpers.PepperImExporterTest;
import org.junit.Before;
import org.junit.Test;

public class PepperImExporterTestTest {
	private PepperImExporterTest fixture;

	@Before
	public void beforeEach() {
		fixture = new PepperImExporterTest() {
			@Override
			protected void checkThatWhenSimulatingFitnessCheckModulePassesSelfTest(ModuleFitness fitness) {
				// do nothing

			}
		};
	}

	@Test
	public void whenCheckThatCorrectFormatsAreSupportedAndFormatsAreCorrect_thenSuccess() {
		// GIVEN
		final PepperExporter exporterToTest = new PepperExporterImpl() {
		};
		exporterToTest.addSupportedFormat("format1", "1.0", null);
		exporterToTest.addSupportedFormat("format1", "2.0", null);
		exporterToTest.addSupportedFormat("format2", "1.0", null);
		fixture.setFixture(exporterToTest);
		fixture.addFormatWhichShouldBeSupported("format1", "1.0");
		fixture.addFormatWhichShouldBeSupported("format1", "2.0");
		fixture.addFormatWhichShouldBeSupported("format2", "1.0");

		// WHEN THEN
		fixture.checkThatCorrectFormatsAreSupported();
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatCorrectFormatsAreSupportedAndFormatsAreCorrect_thenFail() {
		// GIVEN
		final PepperExporter exporterToTest = new PepperExporterImpl() {
		};
		exporterToTest.addSupportedFormat("format1", "1.0", null);
		exporterToTest.addSupportedFormat("format2", "1.0", null);
		fixture.addFormatWhichShouldBeSupported("format1", "1.0");
		fixture.addFormatWhichShouldBeSupported("format1", "2.0");
		fixture.addFormatWhichShouldBeSupported("format2", "1.0");

		// WHEN THEN
		fixture.checkThatCorrectFormatsAreSupported();
	}
}

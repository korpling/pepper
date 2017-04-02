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
package org.corpus_tools.pepper.testFramework.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.FormatDesc.FormatDescBuilder;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleTestException;
import org.junit.Test;

public abstract class PepperImExporterTest extends PepperModuleTest {

	/**
	 * A list of formats, which shall be supported
	 */
	private List<FormatDesc> supportedFormatsCheck = null;

	@Override
	protected void setFixture(PepperModule fixture) {
		if (fixture == null) {
			throw new PepperModuleTestException(
					"Cannot start pepper test, because no pepper module was set for current test. ");
		}
		if (!(fixture instanceof PepperImporter || fixture instanceof PepperExporter)) {
			throw new PepperModuleTestException(fixture,
					"Cannot start pepper test, because the set pepper module neither an im- nor an exporter ");
		}
		super.setFixture(fixture);
		initializeFormatsToBeSupportedWhenNecessary();
	}

	private void initializeFormatsToBeSupportedWhenNecessary() {
		if (supportedFormatsCheck == null) {
			supportedFormatsCheck = new ArrayList<>();
		}
	}

	/**
	 * Adds a format description to the list of formats which should be
	 * supported by the module to be tested.
	 */
	protected void addFormatWhichShouldBeSupported(FormatDesc formatDesc) {
		initializeFormatsToBeSupportedWhenNecessary();
		if (formatDesc == null) {
			fail("Cannot add an empty format description.");
		}
		supportedFormatsCheck.add(formatDesc);
	}

	/**
	 * Adds a format description to the list of formats which should be
	 * supported by the module to be tested.
	 */
	protected void addFormatWhichShouldBeSupported(String formatName, String formatVersion) {
		addFormatWhichShouldBeSupported(
				new FormatDescBuilder().withName(formatName).withVersion(formatVersion).build());
	}

	@Test
	public void checkThatCorrectFormatsAreSupported() {
		assertThat(getSupportedFormatsFromFixture()).containsAll(supportedFormatsCheck);
	}

	private List<FormatDesc> getSupportedFormatsFromFixture() {
		if (testedModule == null) {
			return Collections.emptyList();
		}
		if (testedModule instanceof PepperImporter) {
			return ((PepperImporter) testedModule).getSupportedFormats();
		} else if (testedModule instanceof PepperExporter) {
			return ((PepperExporter) testedModule).getSupportedFormats();
		}
		return Collections.emptyList();
	}
}

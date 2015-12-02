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
package org.corpus_tools.pepper.testFramework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Vector;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SaltProject;
import org.junit.Test;

/**
 * <p>
 * This class is a helper class for creating tests for {@link PepperImporter}s.
 * This class provides a fixture declaration which could be called via
 * {@link #setFixture(PepperImporter)}. The fixture which is returned via
 * {@link #getFixture()} is of type {@link PepperImporter}. To create an easier
 * access, we recommend to overwrite the method {@link #getFixture()} as
 * follows:
 * 
 * <pre>
 * &#064;Override
 * public MY_IMPORTER_CLASS getFixture() {
 * 	return (MY_IMPORTER_CLASS) fixture;
 * }
 * </pre>
 * 
 * The method {@link #setFixture(PepperIMporter)} sets the {@link SaltProject}
 * and creates a single {@link SCorpusGraph} object, which is added to the list
 * of corpus structures in the salt project. To access the salt project or the
 * corpus structure use the following code:
 * 
 * <pre>
 * 	getFixture().getSaltProject();
 *  getFixture().getSaltProject().getCorpusGraphs()
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * This class predefines a test to check that the format of the importer is set
 * correctly. Therefore you need to call {@link #addSupportedFormat(FormatDesc)}
 * and pass the format your importer should support. Otherwise this test will
 * fail. You can do this as follows:
 * 
 * <pre>
 * addSupportedFormat(new FormatDesc().setFormatName(FORMAT_NAME).setFormatVersion(FROMAT_VERSION));
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * To run the test call {@link #start()} in your test method. This will start
 * the test environment, which simulates a Pepper conversion process.
 * </p>
 * 
 * @author florian
 *
 */
public abstract class PepperImporterTest extends PepperModuleTest {
	/**
	 * A list of formats, which shall be supported
	 */
	protected List<FormatDesc> supportedFormatsCheck = null;

	/**
	 * Adds a format description to the list of formats which are supported by
	 * the module to be tested.
	 */
	public void addSupportedFormat(FormatDesc formatDesc) {
		if (formatDesc == null) {
			fail("Cannot add an empty format description.");
		}
		supportedFormatsCheck.add(formatDesc);
	}

	protected void setFixture(PepperImporter fixture) {
		super.setFixture(fixture);
		this.supportedFormatsCheck = new Vector<FormatDesc>();
	}

	protected PepperImporter getFixture() {
		return ((PepperImporter) super.getFixture());
	}

	@Test
	public void testGetSupportedFormats() {
		assertNotNull("Cannot run test, please set fixture first.", getFixture());
		assertNotNull("There have to be some supported formats", getFixture().getSupportedFormats());
		List<FormatDesc> formatDefs = getFixture().getSupportedFormats();
		assertNotSame("Number of supported formats have to be more than 0", 0, formatDefs);
		for (FormatDesc formatDef : formatDefs) {
			assertNotNull("The name of supported formats has to be set.", formatDef.getFormatName());
			assertFalse("The name of the supported formats can't be empty.", formatDef.getFormatName().equals(""));

			assertNotNull("The version of supported formats has to be set.", formatDef.getFormatVersion());
			assertFalse("The version of the supported formats can't be empty.", formatDef.getFormatVersion().equals(""));
		}
		assertTrue("Cannot test the supported formats please set variable 'supportedFormatsCheck'.", this.supportedFormatsCheck.size() > 0);
		assertEquals("There is a different between the number formats which are supported by module, and the number of formats which shall be supported.", this.supportedFormatsCheck.size(), getFixture().getSupportedFormats().size());
		for (FormatDesc formatCheck : this.supportedFormatsCheck) {
			Boolean hasOpponend = false;
			for (FormatDesc formatDef : getFixture().getSupportedFormats()) {
				if ((formatDef.getFormatName().equalsIgnoreCase(formatCheck.getFormatName())) && (formatDef.getFormatVersion().equalsIgnoreCase(formatCheck.getFormatVersion())))
					hasOpponend = true;
			}
			assertTrue("The format '" + formatCheck.getFormatName() + " " + formatCheck.getFormatVersion() + "' has to be supported, but does not exist in list of suppoted formats.", hasOpponend);
		}
	}
}

package org.corpus_tools.pepper.testFramework.helpers;

import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.testFramework.helpers.PepperImExporterTest;
import org.junit.Before;
import org.junit.Test;

public class PepperImExporterTestTest {
	private PepperImExporterTest fixture;

	@Before
	public void beforeEach() {
		fixture = new PepperImExporterTest() {
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

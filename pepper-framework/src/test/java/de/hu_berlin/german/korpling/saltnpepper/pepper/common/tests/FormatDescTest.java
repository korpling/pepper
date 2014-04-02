package de.hu_berlin.german.korpling.saltnpepper.pepper.common.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;

public class FormatDescTest {

	private FormatDesc fixture= null;
	
	public FormatDesc getFixture() {
		return fixture;
	}

	public void setFixture(FormatDesc fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp() throws Exception {
		setFixture(new FormatDesc());
	}

	@Test
	public void testEquals() {
		String formatName= null;
		String formatVersion= null;
		FormatDesc template= new FormatDesc();
		
		assertTrue(getFixture().equals(getFixture()));
		
		formatName= "";
		formatVersion= "";
		
		//name is empty but not null
		getFixture().setFormatName(formatName);
		assertFalse(getFixture().equals(template));
		template.setFormatName(formatName);
		assertFalse(getFixture().equals(template));
		//version is empty but not null
		getFixture().setFormatVersion(formatVersion);
		assertFalse(getFixture().equals(template));
		template.setFormatVersion(formatVersion);
		assertTrue(getFixture().equals(template));
		
		// names and versions are equal
		formatName= "name";
		formatVersion= "version";
		getFixture().setFormatName(formatName);
		template.setFormatName(formatName);
		getFixture().setFormatVersion(formatVersion);
		template.setFormatVersion(formatVersion);
		assertTrue(getFixture().equals(template));
		
		// names are different
		template.setFormatName("otherName");
		assertFalse(getFixture().equals(template));
		
		template.setFormatName(formatName);
		
		// versions are different
		template.setFormatVersion("otherVersion");
		assertFalse(getFixture().equals(template));
	}

}

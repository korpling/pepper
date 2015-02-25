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

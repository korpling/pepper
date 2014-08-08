/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;

public class PepperUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBreakString(){
		assertEquals("do nothing", PepperUtil.breakString("do nothing"));
		assertEquals("do no\nthing", PepperUtil.breakString("do nothing", 5));
		assertEquals(">do n\n>othi\n>ng", PepperUtil.breakString(">","do nothing", 5));
	}
	
	@Test
	public void testGetTempFile(){
		File file= PepperUtil.getTempFile();
		assertNotNull(file);
		assertTrue(file.toString().contains("pepper"));
		assertTrue(file.exists());
	}
	
	@Test
	public void testGetTempFile_String1(){
		File file= PepperUtil.getTempFile("test");
		assertNotNull(file);
		assertTrue(file.toString().contains("pepper"));
		assertTrue(file.toString().contains("test"));
		assertTrue(file.exists());
	}
	
	@Test
	public void testGetTempFile_String2(){
		File file= PepperUtil.getTempFile("test/sub/");
		assertNotNull(file);
		assertTrue(file.toString().contains("pepper"));
		assertTrue(file.toString().contains("test/sub"));
		assertTrue(file.exists());
	}

	@Test
	public void testGetTempTestFile(){
		File file= PepperUtil.getTempTestFile("test/sub/");
		assertNotNull(file);
		assertTrue(file.toString().contains("pepper-test"));
		assertTrue(file.toString().contains("test/sub"));
		assertTrue(file.exists());
	}
}

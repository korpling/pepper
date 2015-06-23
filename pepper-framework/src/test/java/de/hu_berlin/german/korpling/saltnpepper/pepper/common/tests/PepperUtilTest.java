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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;

/**
 * This class is a test class to tes {@link PepperUtil}.
 * @author florian
 *
 */
public class PepperUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBreakString2(){
		String theString= "In linguistics, human language is a system of sounds.";
		StringBuilder out= new StringBuilder();
		int length= 20;
		assertEquals("human language is a system of sounds.", theString= PepperUtil.breakString2(out, theString, length));
		assertEquals("In linguistics, ", out.toString());
		out= new StringBuilder();
		assertEquals("system of sounds.", theString= PepperUtil.breakString2(out, theString, length));
		assertEquals("human language is a ", out.toString());
		out= new StringBuilder();
		assertEquals(null, theString= PepperUtil.breakString2(out, theString, length));
		assertEquals("system of sounds.", out.toString());
	}
	
	@Test
	public void testBreakString2_Unbrekable(){
		String theString= "InLinguisticsHumanLanguageIsASystemOfSounds.";
		StringBuilder out= new StringBuilder();
		int length= 20;
		assertEquals("nguageIsASystemOfSounds.", theString= PepperUtil.breakString2(out, theString, length));
		assertEquals("InLinguisticsHumanLa", out.toString());
		out= new StringBuilder();
		assertEquals("nds.", theString= PepperUtil.breakString2(out, theString, length));
		assertEquals("nguageIsASystemOfSou", out.toString());
		out= new StringBuilder();
		assertEquals(null, theString= PepperUtil.breakString2(out, theString, length));
		assertEquals("nds.", out.toString());
	}
	
	@Test
	public void testBreakStringWithPrefix(){
		String theString= "In linguistics, human language is a system of sounds.";
		StringBuilder out= new StringBuilder();
		out.append("   ");
		int length= 20;
		assertEquals("human language is a system of sounds.", theString= PepperUtil.breakString2(out, theString, length));
		assertEquals("   In linguistics, ", out.toString());
		out= new StringBuilder();
		out.append("   ");
		assertEquals("is a system of sounds.", theString= PepperUtil.breakString2(out, theString, length));
		assertEquals("   human language ", out.toString());
		out= new StringBuilder();
		out.append("   ");
		assertEquals("sounds.", theString= PepperUtil.breakString2(out, theString, length));
		assertEquals("   is a system of ", out.toString());
		out= new StringBuilder();
		out.append("   ");
		assertEquals(null, theString= PepperUtil.breakString2(out, theString, length));
		assertEquals("   sounds.", out.toString());
	}
	
	@Test
	public void testPrintTable(){
		Integer[] length= {3, 20, 4};
		String[][] content= new String[3][3];
		
		content[0][0]= "No";
		content[0][1]= "description";
		content[0][2]= "value";
		
		content[1][0]= "1";
		content[1][1]= "In linguistics, human language is a system of sounds.";
		content[1][2]= "whatAValue";
		
		content[2][0]= "2";
		content[2][1]= "Phonetics is the study of acoustic, visual, and articulatory properties in the production and perception of speech and non-speech sounds.";
		content[2][2]= "AnotherValue";

		assertEquals("+----+---------------------+------+\n| No | description         | valu |\n|    |                     | e    |\n+----+---------------------+------+\n| 1  | In linguistics,     | what |\n|    | human language is a | AVal |\n|    | system of sounds.   | ue   |\n| 2  | Phonetics is the    | Anot |\n|    | study of acoustic,  | herV |\n|    | visual, and         | alue |\n|    | articulatory        |      |\n|    | properties in the   |      |\n|    | production and      |      |\n|    | perception of       |      |\n|    | speech and non-     |      |\n|    | speech sounds.      |      |\n+----+---------------------+------+\n", PepperUtil.createTable(length, content, true, true, true));
		assertEquals("+---+--------------------+----+\n|No |description         |valu|\n|   |                    |e   |\n+---+--------------------+----+\n|1  |In linguistics,     |what|\n|   |human language is a |AVal|\n|   |system of sounds.   |ue  |\n|2  |Phonetics is the    |Anot|\n|   |study of acoustic,  |herV|\n|   |visual, and         |alue|\n|   |articulatory        |    |\n|   |properties in the   |    |\n|   |production and      |    |\n|   |perception of       |    |\n|   |speech and non-     |    |\n|   |speech sounds.      |    |\n+---+--------------------+----+\n", PepperUtil.createTable(length, content, true, false, true));
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

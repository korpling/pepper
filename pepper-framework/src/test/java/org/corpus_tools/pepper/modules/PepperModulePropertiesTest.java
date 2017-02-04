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
package org.corpus_tools.pepper.modules;

import java.util.Properties;

import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.PepperModuleProperty;
import org.corpus_tools.pepper.modules.exceptions.PepperModulePropertyException;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class PepperModulePropertiesTest extends TestCase {
	private PepperModuleProperties fixture = null;

	public void setFixture(PepperModuleProperties fixture) {
		this.fixture = fixture;
	}

	public PepperModuleProperties getFixture() {
		return fixture;
	}

	@Before
	public void setUp() {
		this.setFixture(new PepperModuleProperties());
	}

	@Test
	public void testAddProp() {
		String propName = "prop1";
		PepperModuleProperty<Integer> prop = new PepperModuleProperty<Integer>(propName, Integer.class, "some desc");
		prop.setValue(123);
		getFixture().addProperty(prop);
		assertEquals(prop, getFixture().getProperty(propName));
	}

	/**
	 * Checks adding a bunch of properties via {@link Properties} object. All
	 * properties are already described.
	 */
	@Test
	public void testAdd_Properties() {
		String propName1 = "prop1";
		String propName2 = "prop2";
		String propName3 = "prop3";

		PepperModuleProperty<Integer> prop1 = new PepperModuleProperty<Integer>(propName1, Integer.class, "some desc");
		getFixture().addProperty(prop1);
		PepperModuleProperty<Integer> prop2 = new PepperModuleProperty<Integer>(propName2, Integer.class, "some desc");
		getFixture().addProperty(prop2);
		PepperModuleProperty<Integer> prop3 = new PepperModuleProperty<Integer>(propName3, Integer.class, "some desc");
		getFixture().addProperty(prop3);

		Properties properties = new Properties();
		properties.put(propName1, 123);
		properties.put(propName2, 123);
		properties.put(propName3, 123);

		getFixture().setPropertyValues(properties);
		assertEquals(getFixture().getProperty(propName1), prop1);
		assertEquals(getFixture().getProperty(propName2), prop2);
		assertEquals(getFixture().getProperty(propName3), prop3);
	}

	/**
	 * Checks adding a bunch of properties via {@link Properties} object. Some
	 * of the properties are not desribed.
	 */
	@Test
	public void testAdd_Properties2() {
		String propName1 = "prop1";
		String propName2 = "prop2";
		String propName3 = "prop3";
		String propName4 = "prop4";
		String propName5 = "prop5";

		PepperModuleProperty<Integer> prop1 = new PepperModuleProperty<Integer>(propName1, Integer.class, "some desc");
		getFixture().addProperty(prop1);
		PepperModuleProperty<Integer> prop2 = new PepperModuleProperty<Integer>(propName2, Integer.class, "some desc");
		getFixture().addProperty(prop2);
		PepperModuleProperty<Integer> prop3 = new PepperModuleProperty<Integer>(propName3, Integer.class, "some desc");
		getFixture().addProperty(prop3);

		Properties properties = new Properties();
		properties.put(propName1, 12);
		properties.put(propName2, 34);
		properties.put(propName3, 56);
		properties.put(propName4, 78);
		properties.put(propName5, 90);

		getFixture().setPropertyValues(properties);
		assertEquals(getFixture().getProperty(propName1), prop1);
		assertEquals(getFixture().getProperty(propName2), prop2);
		assertEquals(getFixture().getProperty(propName3), prop3);
		assertEquals(getFixture().getProperty(propName4).getValue(), "78");
		assertFalse(getFixture().getProperty(propName4).getValue().equals(78));
		assertEquals(getFixture().getProperty(propName5).getValue(), "90");
		assertFalse(getFixture().getProperty(propName5).getValue().equals(90));
	}

	/**
	 * Checks adding a bunch of properties via {@link Properties} object. Some
	 * of the properties are not desribed.
	 */
	@Test
	public void testCheckProperties() {
		String propName1 = "prop1";

		PepperModuleProperty<Integer> prop1 = new PepperModuleProperty<Integer>(propName1, Integer.class, "some desc",
				true);
		getFixture().addProperty(prop1);

		try {
			getFixture().checkProperties();
			fail("a property, whichs value is marked as required is not given");
		} catch (PepperModulePropertyException e) {
		}

		getFixture().setPropertyValue(propName1, 12);
		getFixture().checkProperties();
	}

	/**
	 * Checks if check works correctly. Sets all required values, than
	 */
	@Test
	public void testAdd_CheckProperties() {
		String propName1 = "prop1";
		String propName2 = "prop2";
		String propName3 = "prop3";
		String propName4 = "prop4";
		String propName5 = "prop5";

		PepperModuleProperty<Integer> prop1 = new PepperModuleProperty<Integer>(propName1, Integer.class, "some desc",
				true);
		getFixture().addProperty(prop1);
		PepperModuleProperty<Integer> prop2 = new PepperModuleProperty<Integer>(propName2, Integer.class, "some desc",
				true);
		getFixture().addProperty(prop2);
		PepperModuleProperty<Integer> prop3 = new PepperModuleProperty<Integer>(propName3, Integer.class, "some desc",
				true);
		getFixture().addProperty(prop3);
		PepperModuleProperty<Integer> prop4 = new PepperModuleProperty<Integer>(propName4, Integer.class, "some desc",
				false);
		getFixture().addProperty(prop4);
		PepperModuleProperty<Integer> prop5 = new PepperModuleProperty<Integer>(propName5, Integer.class, "some desc",
				false);
		getFixture().addProperty(prop5);

		Properties properties = new Properties();
		properties.put(propName1, 12);
		properties.put(propName2, 34);
		properties.put(propName3, 56);

		getFixture().setPropertyValues(properties);
		assertTrue(getFixture().checkProperties());
	}

	/**
	 * Checks if check works correctly. Does not set all required values, than
	 */
	@Test
	public void testAdd_CheckProperties2() {
		String propName1 = "prop1";
		String propName2 = "prop2";
		String propName3 = "prop3";

		PepperModuleProperty<Integer> prop1 = new PepperModuleProperty<Integer>(propName1, Integer.class, "some desc",
				true);
		getFixture().addProperty(prop1);
		PepperModuleProperty<Integer> prop2 = new PepperModuleProperty<Integer>(propName2, Integer.class, "some desc",
				true);
		getFixture().addProperty(prop2);
		PepperModuleProperty<Integer> prop3 = new PepperModuleProperty<Integer>(propName3, Integer.class, "some desc",
				true);
		getFixture().addProperty(prop3);

		Properties properties = new Properties();
		properties.put(propName1, 12);
		properties.put(propName2, 34);

		getFixture().setPropertyValues(properties);
		try {
			getFixture().checkProperties();
			fail("Check should not return true");
		} catch (PepperModulePropertyException e) {
		}
	}

	@Test
	public void testRemoveProperty() {
		String propName1 = "prop1";
		PepperModuleProperty<Integer> prop1 = new PepperModuleProperty<Integer>(propName1, Integer.class, "some desc",
				true);
		getFixture().addProperty(prop1);
		Properties properties = new Properties();
		properties.put(propName1, 12);
		getFixture().setPropertyValues(properties);

		assertNotNull(getFixture().getProperty(propName1).getValue());
		getFixture().removePropertyValue(propName1);
		assertNull(getFixture().getProperty(propName1).getValue());
	}

	@Test
	public void test_PROP_SIMPLE_TOKENIZE_3() {
		String sep = "' ', '\\'', ',', '\\\\'";

		assertEquals(4, getFixture().stringToCharList(sep).size());
		assertEquals(Character.valueOf(' '), getFixture().stringToCharList(sep).get(0));
		assertEquals(Character.valueOf('\''), getFixture().stringToCharList(sep).get(1));
		assertEquals(Character.valueOf(','), getFixture().stringToCharList(sep).get(2));
		assertEquals(Character.valueOf('\\'), getFixture().stringToCharList(sep).get(3));
	}
}

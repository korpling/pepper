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

import static org.corpus_tools.pepper.modules.PepperModuleProperty.create;

import java.util.Properties;

import org.corpus_tools.pepper.modules.exceptions.PepperModulePropertyException;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class PepperModulePropertiesTest extends TestCase {
	private static final String propName1 = "prop1";
	private static final String propName2 = "prop2";
	private static final String propName3 = "prop3";
	private static final String propName4 = "prop4";
	private static final String propName5 = "prop5";
	private final PepperModuleProperty<Integer> prop1 = createSimpleProperty(propName1);
	private final PepperModuleProperty<Integer> prop2 = createSimpleProperty(propName2);
	private final PepperModuleProperty<Integer> prop3 = createSimpleProperty(propName3);
	private final PepperModuleProperty<Integer> prop4 = createSimpleProperty(propName4);
	private final PepperModuleProperty<Integer> prop5 = createSimpleProperty(propName5);
	private final PepperModuleProperty<Integer> requiredProp1 = create().withName(propName1).withType(Integer.class)
			.withDescription("some desc").isRequired(true).build();
	private final PepperModuleProperty<Integer> requiredProp2 = create().withName(propName2).withType(Integer.class)
			.withDescription("some desc").isRequired(true).build();
	private final PepperModuleProperty<Integer> requiredProp3 = create().withName(propName3).withType(Integer.class)
			.withDescription("some desc").isRequired(true).build();

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

	private static PepperModuleProperty<Integer> createSimpleProperty(String propName) {
		return create().withName(propName).withType(Integer.class).withDescription("some desc").build();
	}

	@Test
	public void testAddProp() {
		prop1.setValue(123);
		getFixture().addProperty(prop1);
		assertEquals(prop1, getFixture().getProperty(propName1));
	}

	/**
	 * Checks adding a bunch of properties via {@link Properties} object. All
	 * properties are already described.
	 */
	@Test
	public void testAdd_Properties() {
		getFixture().addProperty(prop1);
		getFixture().addProperty(prop2);
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
		getFixture().addProperty(prop1);
		getFixture().addProperty(prop2);
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
		getFixture().addProperty(requiredProp1);
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
		getFixture().addProperty(prop1);
		getFixture().addProperty(prop2);
		getFixture().addProperty(prop3);
		getFixture().addProperty(prop4);
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
		getFixture().addProperty(requiredProp1);
		getFixture().addProperty(requiredProp2);
		getFixture().addProperty(requiredProp3);
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
		getFixture().addProperty(requiredProp1);
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

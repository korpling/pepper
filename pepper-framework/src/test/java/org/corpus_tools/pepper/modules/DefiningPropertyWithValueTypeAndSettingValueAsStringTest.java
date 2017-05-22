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

import static org.assertj.core.api.Assertions.assertThat;
import static org.corpus_tools.pepper.modules.PepperModuleProperty.create;

import java.io.File;

import org.junit.Test;

public class DefiningPropertyWithValueTypeAndSettingValueAsStringTest {
	private PepperModuleProperty<?> property;
	private Object value;

	private <T> void given(Class<T> clazz) {
		property = create().withName("prop1").withType(clazz).withDescription("desc").build();
	}

	private void when(Object value) {
		this.value = value;
		property.setValueString(value.toString());
	}

	private void then() {
		assertThat(property.getValue()).isEqualTo(value);
	}

	@Test
	public void whenDefiningPropertyWithIntegerValueAndSettingValueAsString_ValueMustBeReadAsInteger() {
		given(Integer.class);
		when(123);
		then();
	}

	@Test
	public void whenDefiningPropertyWithBooleanValueAndSettingValueAsString_ValueMustBeReadAsBoolean() {
		given(Boolean.class);
		when(true);
		then();
	}

	@Test
	public void whenDefiningPropertyWithFileValueAndSettingValueAsString_ValueMustBeReadAsFile() {
		given(File.class);
		when(new File("./me/"));
		then();
	}

	@Test
	public void whenDefiningPropertyWithStringValueAndSettingValueAsString_ValueMustBeReadAsString() {
		given(String.class);
		when("hello world");
		then();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenDefiningPropertyWithStringArrayValueAndSettingValueAsString_ValueMustBeReadAsStringArray() {
		given(String[].class);
		when("red, green,blue");
		String[] expectedValue = new String[] { "red", "green", "blue" };
		assertThat(((PepperModuleProperty<String[]>) property).getValue()).containsExactly(expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenSettingValueAsStringWithoutCommasAndPropertyIsArray_ValuemustContainOnlyOneEntry() {
		given(String[].class);
		when("red greenblue");
		String[] expectedValue = new String[] { "red greenblue" };
		assertThat(((PepperModuleProperty<String[]>) property).getValue()).containsExactly(expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenDefiningPropertyWithCharacterArrayValueAndSettingValueAsString_ValueMustBeReadAsCharacterArray() {
		given(Character[].class);
		when("a, b,c");
		Character[] expectedValue = new Character[] { 'a', 'b', 'c' };
		assertThat(((PepperModuleProperty<Character[]>) property).getValue()).containsExactly(expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenDefiningPropertyWithBooleanArrayValueAndSettingValueAsString_ValueMustBeReadAsBooleanArray() {
		given(Boolean[].class);
		when("true, false,true");
		Boolean[] expectedValue = new Boolean[] { true, false, true };
		assertThat(((PepperModuleProperty<Boolean[]>) property).getValue()).containsExactly(expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenDefiningPropertyWithIntegerArrayValueAndSettingValueAsString_ValueMustBeReadAsIntegerArray() {
		given(Integer[].class);
		when("1, 2,3");
		Integer[] expectedValue = new Integer[] { 1, 2, 3 };
		assertThat(((PepperModuleProperty<Integer[]>) property).getValue()).containsExactly(expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenDefiningPropertyWithLongArrayValueAndSettingValueAsString_ValueMustBeReadAsLongArray() {
		given(Long[].class);
		when("1, 2,3");
		Long[] expectedValue = new Long[] { 1l, 2l, 3l };
		assertThat(((PepperModuleProperty<Long[]>) property).getValue()).containsExactly(expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenDefiningPropertyWithFloatArrayValueAndSettingValueAsString_ValueMustBeReadAsFloatArray() {
		given(Float[].class);
		when("1.5, 2.5,3.5");
		Float[] expectedValue = new Float[] { 1.5f, 2.5f, 3.5f };
		assertThat(((PepperModuleProperty<Float[]>) property).getValue()).containsExactly(expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenDefiningPropertyWithDoubleArrayValueAndSettingValueAsString_ValueMustBeReadAsDoubleArray() {
		given(Double[].class);
		when("1, 2,3");
		Double[] expectedValue = new Double[] { 1d, 2d, 3d };
		assertThat(((PepperModuleProperty<Double[]>) property).getValue()).containsExactly(expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenDefiningPropertyWithByteArrayValueAndSettingValueAsString_ValueMustBeReadAsByteArray() {
		given(Byte[].class);
		when("1, 2,3");
		Byte[] expectedValue = new Byte[] { Byte.valueOf("1"), Byte.valueOf("2"), Byte.valueOf("3") };
		assertThat(((PepperModuleProperty<Byte[]>) property).getValue()).containsExactly(expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenDefiningPropertyWithShortArrayValueAndSettingValueAsString_ValueMustBeReadAsShortArray() {
		given(Short[].class);
		when("1, 2,3");
		Short[] expectedValue = new Short[] { 1, 2, 3 };
		assertThat(((PepperModuleProperty<Short[]>) property).getValue()).containsExactly(expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenDefiningPropertyWithFileArrayValueAndSettingValueAsString_ValueMustBeReadAsFileArray() {
		given(File[].class);
		when("1, 2,3");
		File[] expectedValue = new File[] { new File("1"), new File("2"), new File("3") };
		assertThat(((PepperModuleProperty<File[]>) property).getValue()).containsExactly(expectedValue);
	}
}

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

public class PepperModulePropertyTest {
	private PepperModuleProperty<?> property = null;

	private void when() {
		property = create().withName("MyProp").withType(Boolean.class).withDescription("Any description.")
				.withDefaultValue(true).isRequired(true).build();
	}

	@Test
	public void whenBuildingPropertyWithName_PropertyMustContainName() {
		when();
		assertThat(property.getName()).isEqualTo("MyProp");
	}

	@Test
	public void whenBuildingPropertyWithType_PropertyMustContainType() {
		when();
		assertThat(property.getType()).isEqualTo(Boolean.class);
	}

	@Test
	public void whenBuildingPropertyWithDescription_PropertyMustContainDescription() {
		when();
		assertThat(property.getDescription()).isEqualTo("Any description.");
	}

	@Test
	public void whenBuildingPropertyWithDefaultValue_PropertyMustContainDefaultValue() {
		when();
		assertThat(property.getDefaultValue()).isEqualTo(true);
	}

	@Test
	public void whenBuildingPropertyWithReuired_PropertyMustBerequired() {
		when();
		assertThat(property.isRequired()).isEqualTo(true);
	}

	@Test
	public void whenSettingValue_valueMustBeSetOne() {
		Integer value = 123;
		PepperModuleProperty<Integer> prop = create().withName("prop1").withType(Integer.class).withDescription("desc")
				.build();
		prop.setValue(value);
		assertThat(prop.getValue()).isEqualTo(value);
	}

	@Test
	public void whenSettingDefaultValueAndNoValue_valueMustBeDefault() {
		String defaultValue = "hello world";
		PepperModuleProperty<String> prop = create().withName("MyProp").withType(String.class).withDescription("desc")
				.withDefaultValue(defaultValue).build();
		assertThat(prop.getValue()).isEqualTo(defaultValue);
	}

	@Test
	public void whenOverwritingDefaultValueWithValue_valueMustBeValue() {
		String defaultValue = "hello world";
		PepperModuleProperty<String> prop = create().withName("MyProp").withType(String.class).withDescription("desc")
				.withDefaultValue(defaultValue).build();
		prop.setValue("goodbye world");
		assertThat(prop.getValue()).isEqualTo("goodbye world");
	}

	@Test
	public void whenOverwritingDefaultValueWithValue_defaultValueMustBeDefaultValue() {
		String defaultValue = "hello world";
		PepperModuleProperty<String> prop = create().withName("MyProp").withType(String.class).withDescription("desc")
				.withDefaultValue(defaultValue).build();
		prop.setValue("goodbye world");
		assertThat(prop.getDefaultValue()).isEqualTo("hello world");
	}

	@Test
	public void whenHavingDefaultValueForAnArryProperty_ValueMustBeDefaultValue() {
		PepperModuleProperty<String[]> prop = create().withName("MyProp").withType(String[].class)
				.withDescription("desc").withDefaultValue(new String[] { "yellow", "black" }).build();
		assertThat(prop.getValue()).containsExactly("yellow", "black");
	}

	@Test
	public void whenSettingValueToArray_valueMustBeTheArrayOverwritingDefaultValues() {
		String[] values = new String[] { "red", "green", "blue" };
		PepperModuleProperty<String[]> prop = create().withName("MyProp").withType(String[].class)
				.withDescription("desc").withDefaultValue(new String[] { "yellow", "black" }).build();
		prop.setValue(values);
		assertThat(prop.getValue()).containsExactly(values);
	}

	@Test
	public void whenSettingValueToBooleanArray_valueMustBeTheBooleanArray() {
		Boolean[] values = new Boolean[] { true, false, true };
		PepperModuleProperty<Boolean[]> prop = create().withName("MyProp").withType(Boolean[].class)
				.withDescription("desc").build();
		prop.setValue(values);
		assertThat(prop.getValue()).containsExactly(values);
	}

	@Test
	public void whenSettingValueToCharacterArray_valueMustBeTheCharacterArray() {
		Character[] values = new Character[] { 'a', 'b', 'c' };
		PepperModuleProperty<Character[]> prop = create().withName("MyProp").withType(Character[].class)
				.withDescription("desc").build();
		prop.setValue(values);
		assertThat(prop.getValue()).containsExactly(values);
	}

	@Test
	public void whenSettingValueToIntegerArray_valueMustBeTheIntegerArray() {
		Integer[] values = new Integer[] { 1, 2, 3 };
		PepperModuleProperty<Integer[]> prop = create().withName("MyProp").withType(Integer[].class)
				.withDescription("desc").build();
		prop.setValue(values);
		assertThat(prop.getValue()).containsExactly(values);
	}

	@Test
	public void whenSettingValueToLongArray_valueMustBeTheLongArray() {
		Long[] values = new Long[] { 1l, 2l, 3l };
		PepperModuleProperty<Long[]> prop = create().withName("MyProp").withType(Long[].class).withDescription("desc")
				.build();
		prop.setValue(values);
		assertThat(prop.getValue()).containsExactly(values);
	}

	@Test
	public void whenSettingValueToFloatArray_valueMustBeTheFloatArray() {
		Float[] values = new Float[] { 1.5f, 2.5f, 3.5f };
		PepperModuleProperty<Float[]> prop = create().withName("MyProp").withType(Float[].class).withDescription("desc")
				.build();
		prop.setValue(values);
		assertThat(prop.getValue()).containsExactly(values);
	}

	@Test
	public void whenSettingValueToDoubleArray_valueMustBeTheDoubleArray() {
		Double[] values = new Double[] { 1d, 2d, 3d };
		PepperModuleProperty<Double[]> prop = create().withName("MyProp").withType(Double[].class)
				.withDescription("desc").build();
		prop.setValue(values);
		assertThat(prop.getValue()).containsExactly(values);
	}

	@Test
	public void whenSettingValueToByteArray_valueMustBeTheByteArray() {
		Byte[] values = new Byte[] { 1, 2, 3 };
		PepperModuleProperty<Byte[]> prop = create().withName("MyProp").withType(Byte[].class).withDescription("desc")
				.build();
		prop.setValue(values);
		assertThat(prop.getValue()).containsExactly(values);
	}

	@Test
	public void whenSettingValueToShortArray_valueMustBeTheShortArray() {
		Short[] values = new Short[] { 1, 2, 3 };
		PepperModuleProperty<Short[]> prop = create().withName("MyProp").withType(Short[].class).withDescription("desc")
				.build();
		prop.setValue(values);
		assertThat(prop.getValue()).containsExactly(values);
	}

	@Test
	public void whenSettingValueToFileArray_valueMustBeTheFileArray() {
		File[] values = new File[] { new File("1"), new File("2"), new File("3") };
		PepperModuleProperty<File[]> prop = create().withName("MyProp").withType(File[].class).withDescription("desc")
				.build();
		prop.setValue(values);
		assertThat(prop.getValue()).containsExactly(values);
	}
}

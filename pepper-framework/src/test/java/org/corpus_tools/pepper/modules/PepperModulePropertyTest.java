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
}

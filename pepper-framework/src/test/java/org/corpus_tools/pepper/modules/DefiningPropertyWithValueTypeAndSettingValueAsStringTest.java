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

}

package org.corpus_tools.pepper.modules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.corpus_tools.pepper.modules.PepperModuleProperty.create;

import java.io.File;

import org.junit.Test;

public class DefiningPropertyWithValueTypeAndSettingValueAsString {
	private PepperModuleProperty<?> property = create().withName("prop1").withType(Integer.class)
			.withDescription("desc").build();
	private Object value;

	private void when() {
		property.setValueString(value.toString());
	}

	private void then() {
		assertThat(property.getValue()).isEqualTo(value);
	}

	@Test
	public void whenDefiningPropertyWithIntegerValueAndSettingValueAsString_ValueMustBeReadAsInteger() {
		value = 123;
		when();
		then();
	}

	/**
	 * Checks setting and returning an int value.
	 */
	@Test
	public void testSetProperty_Boolean() {
		value = true;
		when();
		then();
	}

	/**
	 * Checks setting and returning an int value.
	 */
	@Test
	public void testSetProperty_File() {
		value = new File("./me/");
		when();
		then();
	}

	/**
	 * Checks setting and returning an int value.
	 */
	@Test
	public void testSetProperty_String() {
		value = "hello world";
		when();
		then();
	}
}

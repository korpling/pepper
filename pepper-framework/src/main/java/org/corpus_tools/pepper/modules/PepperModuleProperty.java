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

import java.io.File;
import java.io.Serializable;

/**
 * With properties, the conversion process done by a Pepper module can be
 * customized. A property in general is an attribute value pair, with a unique
 * name and a value influencing the module's behavior. It is possible to add a
 * default value in case the value was not initialized by the Pepper workflow
 * file. A further flag can be used to set the property as required, which
 * means, the property must be set to run the container module. Each property
 * should contain a description, to help the user understanding how to use this
 * property.
 * 
 * A property is created in a fluent way. See the following sample:
 * 
 * <pre>
 * PepperModuleProperty.create().withName("MyProp").withClass(Boolean.class).withDefaultValue(true)
 * 		.withDescription("Please describe the property for the user. ").isRequired(false).build();
 * </pre>
 */
public class PepperModuleProperty<T> implements Comparable<PepperModuleProperty<?>>, Serializable {
	private static final long serialVersionUID = -1577480488804525468L;
	private final String name;
	private T value = null;
	private final T defaultValue;
	private final boolean required;
	private final String description;
	private final Class<T> type;

	/**
	 * @deprecated will be removed with Pepper 4.0
	 */
	@Deprecated
	public PepperModuleProperty(final String name, Class<T> clazz, final String description) {
		this(name, clazz, description, null, false);
	}

	/**
	 * @deprecated will be removed with Pepper 4.0
	 */
	@Deprecated
	public PepperModuleProperty(final String name, Class<T> clazz, final String description, T defaultValue) {
		this(name, clazz, description, defaultValue, false);
	}

	/**
	 * @deprecated will be removed with Pepper 4.0
	 */
	@Deprecated
	public PepperModuleProperty(final String name, Class<T> clazz, final String description, final boolean required) {
		this(name, clazz, description, null, required);
	}

	/**
	 * Creates a {@link PepperModuleProperty} instance and sets its values to
	 * the given ones.
	 * 
	 * @param name
	 *            name of the property
	 * @param clazz
	 *            the propertys class
	 * @param description
	 *            a description to the property
	 * @param defaultValue
	 *            sets a default value if no one is set
	 * @param required
	 *            determines if the property is required (true means property is
	 *            required)
	 * @deprecated the visibility of this method will be set to private
	 */
	@Deprecated
	public PepperModuleProperty(final String name, Class<T> clazz, final String description, T defaultValue,
			final boolean required) {
		this.name = name;
		this.type = clazz;
		this.description = description;
		this.required = required;
		this.value = defaultValue;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public Class<T> getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public boolean isRequired() {
		return (required);
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T getValue() {
		return (value);
	}

	public final T getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the given value to the internal one. In case of the type of this
	 * property is not a String, this method will try to cast the given value.
	 * Please note, that only supported types can be casted. The following types
	 * are provide by a type cast:
	 * <ul>
	 * <li>{@link Character}</li>
	 * <li>{@link Boolean}</li>
	 * <li>{@link Integer}</li>
	 * <li>{@link Long}</li>
	 * <li>{@link Float}</li>
	 * <li>{@link Double}</li>
	 * <li>{@link Byte}</li>
	 * <li>{@link Short}</li>
	 * <li>{@link File}</li>
	 * </ul>
	 * 
	 * @param value
	 *            consumes the given String parameter and transforms it
	 */
	@SuppressWarnings("unchecked")
	public void setValueString(String value) {
		if (value == null)
			this.value = null;
		else {
			value = value.trim();
			if (String.class.isAssignableFrom(type)) {
				this.value = (T) value;
			} else if (Character.class.isAssignableFrom(type)) {
				this.value = (T) Character.valueOf(value.charAt(0));
			} else if (Boolean.class.isAssignableFrom(type)) {
				this.value = (T) Boolean.valueOf(value);
			} else if (Integer.class.isAssignableFrom(type)) {
				this.value = (T) Integer.valueOf(value);
			} else if (Long.class.isAssignableFrom(type)) {
				this.value = (T) Long.valueOf(value);
			} else if (Float.class.isAssignableFrom(type)) {
				this.value = (T) Float.valueOf(value);
			} else if (Double.class.isAssignableFrom(type)) {
				this.value = (T) Double.valueOf(value);
			} else if (Byte.class.isAssignableFrom(type)) {
				this.value = (T) Byte.valueOf(value);
			} else if (Short.class.isAssignableFrom(type)) {
				this.value = (T) Short.valueOf(value);
			} else if (File.class.isAssignableFrom(type)) {
				this.value = (T) new File(value);
			}
		}
	}

	public String toString() {
		return (this.getName() + "=" + this.getType() + "::" + this.getValue());
	}

	@Override
	public int compareTo(PepperModuleProperty<?> o) {
		return (getName().compareTo(o.getName()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (required ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PepperModuleProperty<?> other = (PepperModuleProperty<?>) obj;
		if (defaultValue == null) {
			if (other.defaultValue != null)
				return false;
		} else if (!defaultValue.equals(other.defaultValue))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (required != other.required)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public static NameBuilder create() {
		return new NameBuilder();
	}

	public static class NameBuilder {
		public TypeBuilder withName(String name) {
			return new TypeBuilder(name);
		}

		public static class TypeBuilder {
			private final String name;

			public TypeBuilder(String name) {
				this.name = name;
			}

			public <T> DescriptionBuilder<T> withType(Class<T> type) {
				return new DescriptionBuilder<T>(name, type);
			}

			public static class DescriptionBuilder<T> {
				private final String name;
				private final Class<T> type;

				public DescriptionBuilder(String name, Class<T> type) {
					this.name = name;
					this.type = type;
				}

				public Builder<T> withDescription(String description) {
					return new Builder<T>(name, type, description);
				}

				public static class Builder<T> {
					private final String name;
					private final Class<T> type;
					private String description;
					private T defaultValue;
					private boolean isRequired;

					public Builder(String name, Class<T> type, String description) {
						this.name = name;
						this.type = type;
						this.description = description;
					}

					public Builder<T> withDefaultValue(T defaultValue) {
						this.defaultValue = defaultValue;
						return this;
					}

					public Builder<T> isRequired(boolean isRequired) {
						this.isRequired = isRequired;
						return this;
					}

					public PepperModuleProperty<T> build() {
						return new PepperModuleProperty<T>(name, type, description, defaultValue, isRequired);
					}
				}
			}
		}
	}
}

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
package org.corpus_tools.pepper.modules.coreModules;

import static org.corpus_tools.pepper.modules.PepperModuleProperty.create;

import org.corpus_tools.pepper.modules.PepperModuleProperties;

public class SaltValidatorProperties extends PepperModuleProperties {
	private static final long serialVersionUID = 2045401827779201706L;
	public static final String PROP_CLEAN = "clean";
	public static final String PROP_SELF_RELATION = "selfRelation";

	public SaltValidatorProperties() {
		addProperty(create().withName(PROP_CLEAN).withType(Boolean.class)
				.withDescription(
						"This property determines, if the validator should try to repair the salt model in case it is invalid. Attention: Handle this property with care, since  repairing could mean to remove invalid objects. ")
				.build());
		addProperty(create().withName(PROP_SELF_RELATION).withType(Boolean.class)
				.withDescription(
						"This property determines, if relations which have the same node as source and target should be removed. ")
				.build());
	}

	public Boolean isClean() {
		return ((Boolean) getProperty(PROP_CLEAN).getValue());
	}

	public Boolean isSelfRelation() {
		return ((Boolean) getProperty(PROP_SELF_RELATION).getValue());
	}
}

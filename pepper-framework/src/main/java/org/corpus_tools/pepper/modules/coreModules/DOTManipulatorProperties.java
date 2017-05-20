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

import java.io.File;

import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.PepperModuleProperty;
import org.corpus_tools.salt.util.SaltUtil;

public class DOTManipulatorProperties extends PepperModuleProperties {
	private static final long serialVersionUID = 3660642467438248413L;
	public static final String PREFIX = "dotManipulator.";
	public static final String PROP_OUTPUTFILE = "outputDir";
	public static final String PROP_FILE_ENDING = "fileEnding";

	public DOTManipulatorProperties() {
		addProperty(PepperModuleProperty.create().withName(PROP_OUTPUTFILE).withType(String.class)
				.withDescription("The location to where the output shall be written to as File object.")
				.isRequired(true).build());
		addProperty(PepperModuleProperty.create().withName(PROP_FILE_ENDING).withType(String.class)
				.withDescription("The file ending of dot files.").isRequired(true)
				.withDefaultValue(SaltUtil.FILE_ENDING_DOT).build());
	}

	public File getOutputFile() {
		final String fileName = (String) getProperty(PROP_OUTPUTFILE).getValue();
		if (fileName != null && !fileName.isEmpty()) {
			return (new File(fileName));
		}
		return null;
	}

	public String getFileEnding() {
		return ((String) getProperty(PROP_FILE_ENDING).getValue());
	}
}

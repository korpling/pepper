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
	public static final String PREFIX = "dotManipulator.";
	public static final String PROP_OUTPUTFILE = "outputDir";
	public static final String PROP_FILE_ENDING = "fileEnding";

	public DOTManipulatorProperties() {
		this.addProperty(new PepperModuleProperty<String>(PROP_OUTPUTFILE, String.class,
				"The location to where the output shall be written to as File object.", true));
		this.addProperty(new PepperModuleProperty<String>(PROP_FILE_ENDING, String.class,
				"The file ending of dot files.", SaltUtil.FILE_ENDING_DOT, false));
	}

	/**
	 * Returns the location to where the output shall be written to as File
	 * object.
	 * 
	 * @return
	 */
	public File getOutputFile() {
		final PepperModuleProperty<String> prop = (PepperModuleProperty<String>) this.getProperty(PROP_OUTPUTFILE);
		final String fileName = prop.getValue();
		if (fileName != null && !fileName.isEmpty()) {
			return (new File(fileName));
		}
		return null;
	}

	/**
	 * Returns the file ending of dot files.
	 * 
	 * @return
	 */
	public String getFileEnding() {
		PepperModuleProperty<String> prop = (PepperModuleProperty<String>) this.getProperty(PROP_FILE_ENDING);
		return (prop.getValue());
	}
}

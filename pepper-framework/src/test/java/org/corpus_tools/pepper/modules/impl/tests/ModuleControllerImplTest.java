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
package org.corpus_tools.pepper.modules.impl.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.core.ModuleControllerImpl;
import org.corpus_tools.pepper.core.PepperJobImpl;
import org.corpus_tools.pepper.impl.PepperManipulatorImpl;
import org.corpus_tools.pepper.testFramework.PepperModuleTest;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class ModuleControllerImplTest extends ModuleControllerImpl {

	public ModuleControllerImplTest() {
		super("1");
	}

	@Before
	public void setUp() throws Exception {
		// setFixture(this);
		this.setJob_basic(new PepperJobImpl("1"));
		this.setPepperModule_basic(new PepperManipulatorImpl() {
		});
	}

	@Test
	public void testCopyRes() throws IOException {
		File fromPath = new File(PepperModuleTest.getTestResources() + "/copyRes/");
		File toPath = PepperUtil.getTempTestFile("to");

		FileUtils.deleteDirectory(toPath);

		this.getJob().setBaseDir(URI.createFileURI(fromPath.getCanonicalPath()));
		String prop = "file1.txt    ->   " + toPath + ";  file2.txt-> " + toPath.getAbsolutePath() + "; ./folder-> " + toPath;
		this.copyResources(prop);

		File file1 = new File(toPath.getCanonicalPath() + "/file1.txt");
		File file2 = new File(toPath.getCanonicalPath() + "/file2.txt");
		File file3 = new File(toPath.getCanonicalPath() + "/folder/file3.txt");

		assertTrue(file1.getCanonicalPath() + " does not exist", file1.exists());
		assertTrue(file2.getCanonicalPath() + " does not exist", file2.exists());
		assertTrue(file3.getCanonicalPath() + " does not exist", file3.exists());
	}

}

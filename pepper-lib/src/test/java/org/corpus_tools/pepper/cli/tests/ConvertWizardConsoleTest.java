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
package org.corpus_tools.pepper.cli.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.corpus_tools.pepper.cli.ConvertWizardConsole;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.core.ModuleResolverImpl;
import org.corpus_tools.pepper.core.PepperJobImpl;
import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.impl.PepperImporterImpl;
import org.corpus_tools.pepper.modules.PepperModule;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class ConvertWizardConsoleTest {

	private static class SimpleModuleResolver extends ModuleResolverImpl {

		@Override
		public PepperModule getPepperModule(StepDesc stepDesc) {
			if (MODULE_TYPE.IMPORTER.equals(stepDesc.getModuleType())) {
				return new PepperImporterImpl() {
				};
			} else if (MODULE_TYPE.EXPORTER.equals(stepDesc.getModuleType())) {
				return new PepperExporterImpl() {
				};
			}
			return (null);
		}
	}

	/**
	 * Checks whether the deresolving of pathes works for pathes: import path:
	 * HOME/corpus/importFolder export path: HOME/corpus/exportFolder workflow
	 * file: HOME/corpus/test.pepper should be deresolved in workflow file to
	 * import path: ./importFolder export path: ./exportFolder
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDeresolveURIs() throws IOException {
		File folder = PepperUtil.getTempTestFile("convertwizard/");
		File pepperFile = new File(folder.getCanonicalPath() + "/corpus/test.pepper");
		PepperJobImpl pepperJob = new PepperJobImpl("test");
		pepperJob.setModuleResolver(new SimpleModuleResolver());

		StepDesc importStep = new StepDesc();
		importStep.setName("TestImporter");
		importStep.setModuleType(MODULE_TYPE.IMPORTER);
		importStep.getCorpusDesc().setCorpusPath(URI.createFileURI(
				new File(folder.getAbsolutePath() + "/./corpus/importFolder/").getCanonicalPath() + "/"));
		pepperJob.addStepDesc(importStep);

		StepDesc exportStep = new StepDesc();
		exportStep.setName("TestImporter");
		exportStep.setModuleType(MODULE_TYPE.IMPORTER);
		exportStep.getCorpusDesc().setCorpusPath(URI
				.createFileURI(new File(folder.getAbsolutePath() + "/./corpus/exportFolder").getCanonicalPath() + "/"));
		pepperJob.addStepDesc(exportStep);

		ConvertWizardConsole.deresolveURIs(pepperFile, pepperJob);

		assertEquals(URI.createFileURI("./importFolder/"), importStep.getCorpusDesc().getCorpusPath());
		assertEquals(URI.createFileURI("./exportFolder/"), exportStep.getCorpusDesc().getCorpusPath());
	}
}

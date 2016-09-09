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
import java.io.IOException;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.PepperConfiguration;
import org.corpus_tools.pepper.impl.PepperManipulatorImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.graph.Identifier;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(name = "DOTManipulatorComponent", factory = "PepperManipulatorComponentFactory")
public class DOTManipulator extends PepperManipulatorImpl {
	public DOTManipulator() {
		super("DOTManipulator");
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		setDesc("This manipulator exports a Salt model to the dot syntax. This can be used to create a graphical representation of the Salt model. ");
		this.setProperties(new DOTManipulatorProperties());
	}

	@Activate
	public void activate(ComponentContext componentContext) {
		super.activate(componentContext);
	}

	@Override
	public PepperMapper createPepperMapper(Identifier sElementId) {
		PepperMapper mapper = new PepperMapperImpl() {
			@Override
			public DOCUMENT_STATUS mapSDocument() {
				// TODO fixme
				// Salt2DOT salt2Dot = new Salt2DOT();
				// salt2Dot.salt2Dot(getDocument().getIdentifier(),
				// getResourceURI());

				SaltUtil.saveDocumentGraph(getDocument().getDocumentGraph(), getResourceURI());
				addProgress(1.0);
				return (DOCUMENT_STATUS.COMPLETED);
			}
		};

		String outputStr = ((DOTManipulatorProperties) this.getProperties()).getOutputFile().getAbsolutePath();
		File outputFile = new File(outputStr + "/" + ((SNode) sElementId.getIdentifiableElement()).getPath() + "."
				+ ((DOTManipulatorProperties) this.getProperties()).getFileEnding());
		if (!outputFile.exists()) {
			try {
				if (!outputFile.getParentFile().exists()) {
					if (!outputFile.getParentFile().mkdirs()) {
						logger.warn("Cannot create folder {}. ", outputFile.getParentFile());
					}
				}
				if (!outputFile.getParentFile().exists()) {
					throw new PepperModuleException(
							"Cannot create folder for output file for dot: " + outputFile.getParentFile());
				}
				if (!outputFile.createNewFile()) {
					logger.warn("Cannot create file {}. ", outputFile);
				}
			} catch (IOException e) {
				throw new PepperModuleException("Cannot create output file for dot: " + outputStr);
			}
		}

		URI outputURI = URI.createFileURI(outputFile.getAbsolutePath());
		mapper.setResourceURI(outputURI);

		return (mapper);
	}
}

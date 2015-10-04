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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperManipulatorImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.u.saltnpepper.graph.Identifier;
import de.hu_berlin.u.saltnpepper.salt.core.SNode;
import de.hu_berlin.u.saltnpepper.salt.util.SaltUtil;

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
				//TODO fixme
//				Salt2DOT salt2Dot = new Salt2DOT();
//				salt2Dot.salt2Dot(getDocument().getIdentifier(), getResourceURI());
				
				SaltUtil.saveDocumentGraph(getDocument().getDocumentGraph(), getResourceURI());
				addProgress(1.0);
				return (DOCUMENT_STATUS.COMPLETED);
			}
		};

		String outputStr = ((DOTManipulatorProperties) this.getProperties()).getOutputFile().getAbsolutePath();
		File outputFile = new File(outputStr + "/" + ((SNode)sElementId.getIdentifiableElement()).getPath() + "." + ((DOTManipulatorProperties) this.getProperties()).getFileEnding());
		if (!outputFile.exists()) {
			try {
				if (!outputFile.getParentFile().exists())
					outputFile.getParentFile().mkdirs();
				if (!outputFile.getParentFile().exists())
					throw new PepperModuleException("Cannot create folder for output file for dot: " + outputFile.getParentFile());
				outputFile.createNewFile();
			} catch (IOException e) {
				throw new PepperModuleException("Cannot create output file for dot: " + outputStr);
			}
		}

		URI outputURI = URI.createFileURI(outputFile.getAbsolutePath());
		mapper.setResourceURI(outputURI);

		return (mapper);
	}
}

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

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.u.saltnpepper.graph.Identifier;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpusGraph;
import de.hu_berlin.u.saltnpepper.salt.core.SNode;
import de.hu_berlin.u.saltnpepper.salt.util.SaltUtil;

@Component(name = "DOTExporterComponent", factory = "PepperExporterComponentFactory", enabled = true)
public class DOTExporter extends PepperExporterImpl {
	public DOTExporter() {
		super("DOTExporter");
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		setDesc("This exporter exports a Salt model to the dot syntax. This can be used to create a graphical representation of the Salt model. ");
		// set list of formats supported by this module
		this.addSupportedFormat("dot", "1.0", null);
	}

	@Override
	public PepperMapper createPepperMapper(Identifier sElementId) {
		PepperMapper mapper = new PepperMapperImpl() {
			@Override
			public DOCUMENT_STATUS mapSDocument() {
				// workaround to deal with a bug in salt
				SCorpusGraph sCorpusGraph = getDocument().getGraph();

				SaltUtil.save_DOT(getDocument(), getResourceURI());

				// workaround to deal with a bug in salt
				if (getDocument().getGraph() == null) {
					getDocument().setGraph(sCorpusGraph);
				}

				addProgress(1.0);
				return (DOCUMENT_STATUS.COMPLETED);
			}

			public DOCUMENT_STATUS mapSCorpus() {
				// this is a workaround, it would be easier to use
				// exportCorpusStructure(SCorpusGraph corpusGraph), but because
				// of a strange bug, this doesn't work
				List<SNode> roots = getCorpus().getGraph().getRoots();
				if ((roots != null) && (!roots.isEmpty())) {
					if (getCorpus().equals(roots.get(0))) {
						SaltUtil.save_DOT(getCorpus().getGraph(), getCorpusDesc().getCorpusPath());
					}
				}

				return (DOCUMENT_STATUS.COMPLETED);
			}
		};

		String segments = "";
		URI outputURI = null;

		for (String segment : ((SNode)sElementId.getIdentifiableElement()).getPath().segmentsList())
			segments = segments + "/" + segment;
		outputURI = URI.createFileURI(this.getCorpusDesc().getCorpusPath().toFileString() + segments + "." + SaltUtil.FILE_ENDING_SALT_XML);

		mapper.setResourceURI(outputURI);
		return (mapper);
	}
}

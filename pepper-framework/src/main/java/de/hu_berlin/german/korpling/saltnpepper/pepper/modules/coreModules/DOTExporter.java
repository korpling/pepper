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
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.u.saltnpepper.salt.SaltFactory;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpusGraph;

@Component(name = "DOTExporterComponent", factory = "PepperExporterComponentFactory", enabled = true)
public class DOTExporter extends PepperExporterImpl {
	public DOTExporter() {
		super("DOTExporter");
		setSupplierContact(URI.createURI("saltnpepper@lists.hu-berlin.de"));
		setSupplierHomepage(URI.createURI("https://github.com/korpling/pepper"));
		setDesc("This exporter exports a Salt model to the dot syntax. This can be used to create a graphical representation of the Salt model. ");
		// set list of formats supported by this module
		this.addSupportedFormat("dot", "1.0", null);
	}

	@Override
	public PepperMapper createPepperMapper(SElementId sElementId) {
		PepperMapper mapper = new PepperMapperImpl() {
			@Override
			public DOCUMENT_STATUS mapSDocument() {
				// workaround to deal with a bug in salt
				SCorpusGraph sCorpusGraph = getDocument().getCorpusGraph();

				SaltFactory.save_DOT(getDocument(), getResourceURI());

				// workaround to deal with a bug in salt
				if (getDocument().getCorpusGraph() == null) {
					getDocument().setCorpusGraph(sCorpusGraph);
				}

				addProgress(1.0);
				return (DOCUMENT_STATUS.COMPLETED);
			}

			public DOCUMENT_STATUS mapSCorpus() {
				// this is a workaround, it would be easier to use
				// exportCorpusStructure(SCorpusGraph corpusGraph), but because
				// of a strange bug, this doesn't work
				List<Node> roots = getCorpus().getCorpusGraph().getRoots();
				if ((roots != null) && (!roots.isEmpty())) {
					if (getCorpus().equals(roots.get(0))) {
						SaltFactory.save_DOT(getCorpus().getCorpusGraph(), getCorpusDesc().getCorpusPath());
					}
				}

				return (DOCUMENT_STATUS.COMPLETED);
			}
		};

		String segments = "";
		URI outputURI = null;

		for (String segment : sElementId.getSElementPath().segmentsList())
			segments = segments + "/" + segment;
		outputURI = URI.createFileURI(this.getCorpusDesc().getCorpusPath().toFileString() + segments + "." + SaltFactory.FILE_ENDING_DOT);

		mapper.setResourceURI(outputURI);
		return (mapper);
	}
}

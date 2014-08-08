/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.tests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;

public class PepperExporterImplTest extends PepperExporterImpl{

	
	private PepperExporterImpl fixture= null;

	public void setFixture(PepperExporterImpl fixture) {
		this.fixture = fixture;
	}

	public PepperExporterImpl getFixture() {
		return fixture;
	}
	
	@Before
	public void setUp()
	{
		this.setFixture(this);
	}
	
	/**
	 * Test the correct export of corpus-structure.
	 */
	@Test
	public void testExportCoprusStructure(){
		getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
		SampleGenerator.createCorpusStructure(getFixture().getSaltProject());
		getFixture().setSDocumentEnding(".txt");
		getFixture().setExportMode(EXPORT_MODE.DOCUMENTS_IN_FILES);
		CorpusDesc corpusDesc= new CorpusDesc();
		corpusDesc.setCorpusPath(URI.createFileURI(PepperUtil.getTempTestFile().getAbsolutePath()));
		getFixture().setCorpusDesc(corpusDesc);
		
		getFixture().exportCorpusStructure();
		assertNotNull(getFixture().getSElementId2ResourceTable());
		for (SCorpusGraph sCorpusGraph: getFixture().getSaltProject().getSCorpusGraphs()){
			for (SCorpus sCorpus: sCorpusGraph.getSCorpora()){
				assertNotNull(getFixture().getSElementId2ResourceTable().get(sCorpus.getSElementId()));
			}
			for (SDocument sDocument: sCorpusGraph.getSDocuments()){
				assertNotNull(getFixture().getSElementId2ResourceTable().get(sDocument.getSElementId()));
			}
		}
	}
}

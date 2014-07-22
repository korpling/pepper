package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperModuleImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;

public class PepperModuleImplTest {

	private PepperModuleImpl fixture= null; 
	
	public PepperModuleImpl getFixture() {
		return fixture;
	}

	public void setFixture(PepperModuleImpl fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp() throws Exception {
		setFixture(new PepperModuleImpl(){
			@Override
			public PepperMapper createPepperMapper(SElementId sElementId) {
				return super.createPepperMapper(sElementId);
			}
		});
		getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
	}

	@Test
	public void test_PropAddSLayer() {
		getFixture().setSaltProject(SampleGenerator.createCompleteSaltproject2());
		getFixture().getProperties().setPropertyValue(PepperModuleProperties.PROP_AFTER_ADD_SLAYER, "layer1; layer2");
		
		for (SCorpusGraph corpGraph: getFixture().getSaltProject().getSCorpusGraphs()){
			for (SDocument sDoc: corpGraph.getSDocuments()){
				getFixture().after(sDoc.getSElementId());
				
				assertEquals(2, sDoc.getSDocumentGraph().getSLayers().size());
				SLayer layer1= sDoc.getSDocumentGraph().getSLayers().get(0);
				SLayer layer2= sDoc.getSDocumentGraph().getSLayers().get(1);
				for (SNode sNode: sDoc.getSDocumentGraph().getSNodes()){
					assertTrue(sNode.getSLayers().contains(layer1));
					assertTrue(sNode.getSLayers().contains(layer2));
				}
				for (SRelation sRel: sDoc.getSDocumentGraph().getSRelations()){
					assertTrue(sRel.getSLayers().contains(layer1));
					assertTrue(sRel.getSLayers().contains(layer2));
				}
			}
		}
	}

}

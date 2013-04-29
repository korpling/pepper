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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.PepperConverter;

import java.io.File;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.FinishableRunner;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.JobCases.PepperJobDelegatorTest;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.TestModules.TestExporter1;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.TestModules.TestImporter1;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * This TestCase tests if a document will be removed from SaltProject, 
 * after it was processed by all modules.
 * 
 * @author Florian Zipser
 */
public class MaxAmountOfDocumentTest extends TestCase 
{
	/**
	 * The fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	protected PepperJobDelegatorTest fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	public static void main(String[] args) {
		TestRunner.run(MaxAmountOfDocumentTest.class);
	}

	/**
	 * Constructs a new Pepper Job test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	public MaxAmountOfDocumentTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	protected void setFixture(PepperJobDelegatorTest fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	protected PepperJobDelegatorTest getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(new PepperJobDelegatorTest());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 *
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}
	/**
	 * Tests if a document graph will be removed from a document, after it was processed by all modules.
	 * This test contains one importer and one exporter. 
	 */
	public void test1() throws Exception
	{
		final Integer maxNumOfSDocuments= 2;
		class TestImporter1 extends PepperImporterImpl
		{
			{ this.name= "TestImporter1"; }
			public EList<SElementId> elementIds= null;
			
			@Override
			public void importCorpusStructure(SCorpusGraph corpusGraph)
			{
				for (SElementId sElementId: elementIds)
				{
					//copying element id, because it will be destroyed if creator of elementIds list is destroyed
					SElementId sElementId2= SaltCommonFactory.eINSTANCE.createSElementId();
					sElementId2.setSId(sElementId.getSId());
					
					SDocument document= SaltCommonFactory.eINSTANCE.createSDocument();
					document.setSElementId(sElementId2);
					corpusGraph.addSNode(document);
				}	
				
			} 
			
			@Override
			public void start(SElementId sElementId) 
			{
				System.out.println("importing document "+ sElementId.getSId());
				System.out.println("number of current processed documents: "+((PepperModuleControllerImpl)this.getPepperModuleController()).getPepperDocumentController().getCurrentAmountOfSDocuments());
				//check if there are more than maxNumOfDocuments in process
				assertTrue(maxNumOfSDocuments >= ((PepperModuleControllerImpl)this.getPepperModuleController()).getPepperDocumentController().getCurrentAmountOfSDocuments());
				
				SElementId removeSElementId= null;
				
				if (sElementId.getSIdentifiableElement() instanceof SDocument)
				{
					((SDocument) sElementId.getSIdentifiableElement()).setSDocumentGraph(SaltCommonFactory.eINSTANCE.createSDocumentGraph());
				}
				
				for (SElementId sElementId2: elementIds)
					if (sElementId2.getSId().equalsIgnoreCase(sElementId.getSId()))
					{	
						removeSElementId= sElementId2;
						break;
					}
				this.elementIds.remove(removeSElementId);
				System.out.println("---> importing document ENDE "+ sElementId.getSId());
			}
		}
		

		class TestExporter1 extends PepperExporterImpl
		{
			{ this.name= "TestExporter1"; }
			EList<SElementId> elementIds= null;
			
			@Override
			public void start(SElementId sElementId) 
			{
				System.out.println("exporting document "+ sElementId.getSId());
				String alibiStr= "";
				{//Exporter has to be slower than importer
					for (int i= 0; i< 2000; i++)
					{
						alibiStr= alibiStr+ i;
					}
				}//Exporter has to be slower than importer
				this.elementIds.remove(sElementId);
			}
		}
		
		class Runner extends FinishableRunner 
		{	
			public SaltProject saltProject= null;
			@Override
			public void myRunning()
			{
				//generate a list of element-ids, all module has to see
				EList<SElementId> elementIds= new BasicEList<SElementId>();
				for (int i= 0; i < 10; i++)
				{
					SElementId sElementId= SaltCommonFactory.eINSTANCE.createSElementId();
					sElementId.setSId("id"+i);
					elementIds.add(sElementId);
				}	
				
				URI uri= null;
				uri= URI.createFileURI("./data/DocumentGraphRemoving/testCorpus1");
				
				File file = new File(uri.toFileString());
				file.mkdirs();

				CorpusDefinition corpDef= null;
				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
				corpDef.setCorpusPath(uri);
				
				//init job
				((PepperJob)this.getFixture()).setSaltProject(saltProject);
				((PepperJob)this.getFixture()).setId(0);
				
				//init importer
				TestImporter1 importer1= new TestImporter1();
				importer1.elementIds= elementIds;
				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
				corpDef.setCorpusPath(uri);
				importer1.setCorpusDefinition(corpDef);
				((PepperJob)this.getFixture()).getPepperImporters().add(importer1);
								
				//init exporter
				TestExporter1 exporter1= new TestExporter1();
				exporter1.elementIds= elementIds;
				corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
				corpDef.setCorpusPath(uri);
				exporter1.setCorpusDefinition(corpDef);
				((PepperJob)this.getFixture()).getPepperExporters().add(exporter1);
				
				PepperFinishableMonitor jobMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
				((PepperJob)this.getFixture()).setPepperJ2CMonitor(jobMonitor);
				((PepperJob)this.getFixture()).start();
				
				//checking if all element-ids were seen
				assertEquals(0, importer1.elementIds.size());
				assertEquals(0, exporter1.elementIds.size());
				
				this.finished= true;
			}
		}
		
		SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
		Runner runner= new Runner();
		runner.saltProject= saltProject;
		this.getFixture().getPepperDocumentController().setAMOUNT_OF_COMPUTABLE_SDOCUMENTS(maxNumOfSDocuments);
//		FinishableRunner.startRunner(10000l, runner, this.getFixture());
		FinishableRunner.startRunner(100000l, runner, this.getFixture());

//		System.out.println(this.getFixture().getPepperDocumentController().getStatus4Print());
		for (SCorpusGraph sCorpGraph: saltProject.getSCorpusGraphs())
		{
			for (SDocument sDocument: sCorpGraph.getSDocuments())
			{
				assertEquals(PEPPER_SDOCUMENT_STATUS.COMPLETED, this.getFixture().getPepperDocumentController().getStatus(sDocument.getSElementId()));
				assertNull("A document shall not contain a document graph any more.", sDocument.getSDocumentGraph());
			}
		}
	}
} //PepperConvertJobTest

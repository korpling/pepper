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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests;

import java.util.Hashtable;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperDocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperModuleControllerImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

public class PepperDocumentControllerTest extends TestCase{

	/**
	 * The fixture for this Pepper Document Controller test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperDocumentController fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PepperDocumentControllerTest.class);
	}

	/**
	 * Constructs a new Pepper Document Controller test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperDocumentControllerTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Pepper Document Controller test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PepperDocumentController fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Pepper Document Controller test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PepperDocumentController getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(PepperFWFactory.eINSTANCE.createPepperDocumentController());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the '{@link de.hub.corpling.pepper.pepperFW.PepperDocumentController#observeSDocument(de.hub.corpling.salt.saltCore.SElementId) <em>Observe SDocument</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperFW.PepperDocumentController#observeSDocument(de.hub.corpling.salt.saltCore.SElementId)
	 */
	public void testObserveSDocument__SElementId() 
	{
		SElementId sElementId1= null;
		
		try {
			this.getFixture().observeSDocument(sElementId1);
			fail("shall not observe an empty SElemenstId");
		} catch (PepperConvertException e) {	}
		
		sElementId1= SaltFactory.eINSTANCE.createSElementId();
		
		try {
			this.getFixture().observeSDocument(sElementId1);
			fail("shall not observe an SElemenstId with an empty SId value");
		} catch (PepperConvertException e) {	}
		sElementId1.setSId("id1");
		
		try {
			this.getFixture().observeSDocument(sElementId1);
			fail("shall not observe an SElemenstId which has no SIdentifiableElement");
		} catch (PepperConvertException e) {	}
		SCorpus sCorpus= SaltFactory.eINSTANCE.createSCorpus();
		sElementId1.setSIdentifiableElement(sCorpus);
		try {
			this.getFixture().observeSDocument(sElementId1);
			fail("shall not observe an SElemenstId whos SIdentifiableElement is not of type SDocument");
		} catch (PepperConvertException e) {	}
		
		SDocument sDocument= SaltFactory.eINSTANCE.createSDocument();
		sElementId1.setSIdentifiableElement(sDocument);
		
		//setting PepperModuleController
		PepperModuleController pModuleController= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(pModuleController);
		
		this.getFixture().observeSDocument(sElementId1);
		try {
			this.getFixture().observeSDocument(sElementId1);
			fail("shall not observe an SElemenstId two times");
		} catch (PepperConvertException e) {	}
		
		SElementId sElementId2= SaltFactory.eINSTANCE.createSElementId();
		sElementId2.setSId("id1");
		sElementId2.setSIdentifiableElement(sDocument);
		
//		try {
//			this.getFixture().observeSDocument(sElementId2);
//			fail("shall not observe two SElemenstIds with same SId-value");
//		} catch (PepperConvertException e) {	}
	}

	/**
	 * Tests the '{@link de.hub.corpling.pepper.pepperFW.PepperDocumentController#getObservedSDocuments() <em>Get Observed SDocuments</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperFW.PepperDocumentController#getObservedSDocuments()
	 */
	public void testGetObservedSDocuments() 
	{
		//setting PepperModuleController
		PepperModuleController pModuleController= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(pModuleController);
		
//		EList<SElementId> sElementIds= new BasicEList<SElementId>();
//		SElementId sElementId= null;
//		for (int i= 1; i < 10; i++)
//		{
//			sElementId= SaltFactory.eINSTANCE.createSElementId();
//			sElementId.setSId("id"+i);
//			sElementId.setSIdentifiableElement(SaltFactory.eINSTANCE.createSDocument());
//			this.getFixture().observeSDocument(sElementId);
//			sElementIds.add(sElementId);
//		}
		String[] ids= {"doc1", "doc2","doc3","doc4","doc5","doc6","doc7","doc8","doc9", "doc10"};
		EList<SElementId> sElementIds= createSDocumentList(ids);
		
		assertTrue(this.getFixture().getObservedSDocuments().containsAll(sElementIds));
		assertTrue(sElementIds.containsAll(this.getFixture().getObservedSDocuments()));
	}
	
	private EList<SElementId> createSDocumentList(String[] ids)
	{
		EList<SElementId> sDocumentIds= null;
		if (	(ids!= null)&&
				(ids.length> 0))
		{
			sDocumentIds= new BasicEList<SElementId>();
			SElementId sElementId= null;
			for (String id: ids)
			{
				sElementId= SaltFactory.eINSTANCE.createSElementId();
				sElementId.setSId(id);
				sElementId.setSIdentifiableElement(SaltFactory.eINSTANCE.createSDocument());
				this.getFixture().observeSDocument(sElementId);
				sDocumentIds.add(sElementId);
			}
		}
		return(sDocumentIds);
	}

	
	public void testStatusChanging_10()
	{
		this.getFixture().setAMOUNT_OF_COMPUTABLE_SDOCUMENTS(10);
		this.statusChanging();
	}
	
	public void statusChanging()
	{
		PepperModuleController importerController= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(importerController);
		
		PepperModuleController exporterController= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(exporterController);
		
		String[] ids= {"doc1", "doc2","doc3","doc4","doc5","doc6","doc7","doc8","doc9", "doc10"};
		EList<SElementId> sElementIds= createSDocumentList(ids);
		
		for (SElementId sDocumentId: sElementIds)
			this.getFixture().setSDocumentStatus(sDocumentId, importerController, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
		assertTrue(this.getFixture().getObservedSDocuments().containsAll(sElementIds));
		
		for (SElementId sDocumentId: sElementIds)
			this.getFixture().setSDocumentStatus(sDocumentId, importerController, PEPPER_SDOCUMENT_STATUS.COMPLETED);
		assertTrue(this.getFixture().getObservedSDocuments().containsAll(sElementIds));
		
		for (SElementId sDocumentId: sElementIds)
			this.getFixture().setSDocumentStatus(sDocumentId, exporterController, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
		assertTrue(this.getFixture().getObservedSDocuments().containsAll(sElementIds));
		
		for (SElementId sDocumentId: sElementIds)
			this.getFixture().setSDocumentStatus(sDocumentId, exporterController, PEPPER_SDOCUMENT_STATUS.COMPLETED);
	}
	
	/**
	 * Tests the '{@link de.hub.corpling.pepper.pepperFW.PepperDocumentController#setSDocumentStatus(de.hub.corpling.salt.saltCore.SElementId, de.hub.corpling.pepper.pepperFW.PepperModuleController, de.hub.corpling.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS) <em>Add SDocument Status</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperFW.PepperDocumentController#setSDocumentStatus(de.hub.corpling.salt.saltCore.SElementId, de.hub.corpling.pepper.pepperFW.PepperModuleController, de.hub.corpling.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS)
	 */
	public void testsetSDocumentStatus__SElementId_PepperModuleController_PEPPER_SDOCUMENT_STATUS() 
	{
		SElementId sDocumentId1= null;
		
		sDocumentId1= SaltFactory.eINSTANCE.createSElementId();
		sDocumentId1.setSId("id1");
		SDocument sDocument= SaltFactory.eINSTANCE.createSDocument();
		sDocumentId1.setSIdentifiableElement(sDocument);
		
		//setting PepperModuleController
		PepperModuleController pModuleController= null;
		
		try {
			this.getFixture().setSDocumentStatus(sDocumentId1, pModuleController, PEPPER_SDOCUMENT_STATUS.COMPLETED);
			fail("shall not add documentId to empty moduleController");
		} catch (Exception e) {}
		
		pModuleController= PepperFWFactory.eINSTANCE.createPepperModuleController();
		
		try {
			this.getFixture().setSDocumentStatus(sDocumentId1, pModuleController, PEPPER_SDOCUMENT_STATUS.COMPLETED);
			fail("shall not add documentId to a moduleController, which does not exist in PepperDocumentController");
		} catch (Exception e) {}
		
		this.getFixture().getPepperModuleControllers().add(pModuleController);
		this.getFixture().observeSDocument(sDocumentId1);
		this.getFixture().setSDocumentStatus(sDocumentId1, pModuleController, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
		this.getFixture().setSDocumentStatus(sDocumentId1, pModuleController, PEPPER_SDOCUMENT_STATUS.COMPLETED);
		
		try {
			this.getFixture().setSDocumentStatus(sDocumentId1, pModuleController, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
			fail("shall not add documentId to a moduleController, with setting status to lower type");
		} catch (Exception e) {}
	}

	/**
	 * Tests the '{@link de.hub.corpling.pepper.pepperFW.PepperDocumentController#getStatus4Print() <em>Get Status4 Print</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperFW.PepperDocumentController#getStatus4Print()
	 */
	public void testGetStatus4Print() 
	{
		SElementId sDocumentId1= null;
		
		sDocumentId1= SaltFactory.eINSTANCE.createSElementId();
		sDocumentId1.setSId("id1");
		SDocument sDocument= SaltFactory.eINSTANCE.createSDocument();
		sDocumentId1.setSIdentifiableElement(sDocument);
		
		//setting PepperModuleController
		PepperModuleController pModuleController= null;
		pModuleController= PepperFWFactory.eINSTANCE.createPepperModuleController();
		
		this.getFixture().getPepperModuleControllers().add(pModuleController);
		this.getFixture().observeSDocument(sDocumentId1);
		this.getFixture().setSDocumentStatus(sDocumentId1, pModuleController, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
		this.getFixture().setSDocumentStatus(sDocumentId1, pModuleController, PEPPER_SDOCUMENT_STATUS.COMPLETED);
		
		assertNotNull(this.getFixture().getStatus4Print());
	}

	/**
	 * Tests setting the lifecycle of one document. From NOT_STARTED, over IN_PROCESS to COMPLETED.
	 * @see de.hub.corpling.pepper.pepperFW.PepperDocumentController#getStatus(de.hub.corpling.salt.saltCore.SElementId)
	 */
	public void testGetStatus__SElementId() 
	{
		PepperModuleController pModController1= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(pModController1);
		PepperModuleController pModController2= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(pModController2);
		
		SElementId sDocumentId1= SaltFactory.eINSTANCE.createSElementId();
		sDocumentId1.setSIdentifiableElement(SaltFactory.eINSTANCE.createSDocument());
		sDocumentId1.setSId("id1");
		((SDocument)sDocumentId1.getSIdentifiableElement()).setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		assertNotNull(((SDocument)sDocumentId1.getSIdentifiableElement()).getSDocumentGraph());
		this.getFixture().observeSDocument(sDocumentId1);
		
		//checking status of sDocument1
		assertEquals(PEPPER_SDOCUMENT_STATUS.NOT_STARTED, this.getFixture().getStatus(sDocumentId1));
		
		{//setting to IN_PROCESS
			//adding document1 to be processesd with moduleController1
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController1, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS, this.getFixture().getStatus(sDocumentId1));
		}//setting to IN_PROCESS
		
		this.getFixture().setSDocumentStatus(sDocumentId1, pModController2, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);

		{//setting only one module controller to COMPLETED
			//adding document1 to be processesd with moduleController1
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController1, PEPPER_SDOCUMENT_STATUS.COMPLETED);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS, this.getFixture().getStatus(sDocumentId1));
		}//setting only one module controller to COMPLETED
		
		{//setting second module controller to COMPLETED
			//adding document1 to be processesd with moduleController2
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController2, PEPPER_SDOCUMENT_STATUS.COMPLETED);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.COMPLETED, this.getFixture().getStatus(sDocumentId1));
		}//setting second module controller to COMPLETED
		assertNull(((SDocument)sDocumentId1.getSIdentifiableElement()).getSDocumentGraph());
	}

	/**
	 * Tests setting the lifecycle of one document. From NOT_STARTED, over IN_PROCESS to FAILED.
	 * @see de.hub.corpling.pepper.pepperFW.PepperDocumentController#getStatus(de.hub.corpling.salt.saltCore.SElementId)
	 */
	public void testGetStatus__SElementId2() 
	{
		PepperModuleController pModController1= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(pModController1);
		PepperModuleController pModController2= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(pModController2);
		
		SElementId sDocumentId1= SaltFactory.eINSTANCE.createSElementId();
		sDocumentId1.setSIdentifiableElement(SaltFactory.eINSTANCE.createSDocument());
		sDocumentId1.setSId("id1");
		((SDocument)sDocumentId1.getSIdentifiableElement()).setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		assertNotNull(((SDocument)sDocumentId1.getSIdentifiableElement()).getSDocumentGraph());
		this.getFixture().observeSDocument(sDocumentId1);
		
		//checking status of sDocument1
		assertEquals(PEPPER_SDOCUMENT_STATUS.NOT_STARTED, this.getFixture().getStatus(sDocumentId1));
		
		{//setting to IN_PROCESS
			//adding document1 to be processesd with moduleController1
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController1, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS, this.getFixture().getStatus(sDocumentId1));
		}//setting to IN_PROCESS
		
		this.getFixture().setSDocumentStatus(sDocumentId1, pModController2, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);

		{//setting only one module controller to COMPLETED
			//adding document1 to be processesd with moduleController1
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController1, PEPPER_SDOCUMENT_STATUS.COMPLETED);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS, this.getFixture().getStatus(sDocumentId1));
		}//setting only one module controller to COMPLETED
		
		{//setting second module controller to COMPLETED
			//adding document1 to be processesd with moduleController2
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController2, PEPPER_SDOCUMENT_STATUS.FAILED);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.FAILED, this.getFixture().getStatus(sDocumentId1));
		}//setting second module controller to COMPLETED
		assertNull(((SDocument)sDocumentId1.getSIdentifiableElement()).getSDocumentGraph());
	}
	
	/**
	 * Tests setting the lifecycle of on document. Form NOT_STARTED, over IN_PROCESS to DELETED.
	 * @see de.hub.corpling.pepper.pepperFW.PepperDocumentController#getStatus(de.hub.corpling.salt.saltCore.SElementId)
	 */
	public void testGetStatus__SElementId3() 
	{
		PepperModuleController pModController1= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(pModController1);
		PepperModuleController pModController2= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(pModController2);
		
		SElementId sDocumentId1= SaltFactory.eINSTANCE.createSElementId();
		sDocumentId1.setSIdentifiableElement(SaltFactory.eINSTANCE.createSDocument());
		sDocumentId1.setSId("id1");
		((SDocument)sDocumentId1.getSIdentifiableElement()).setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		assertNotNull(((SDocument)sDocumentId1.getSIdentifiableElement()).getSDocumentGraph());
		this.getFixture().observeSDocument(sDocumentId1);
		
		//checking status of sDocument1
		assertEquals(PEPPER_SDOCUMENT_STATUS.NOT_STARTED, this.getFixture().getStatus(sDocumentId1));
		
		{//setting to IN_PROCESS
			//adding document1 to be processesd with moduleController1
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController1, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS, this.getFixture().getStatus(sDocumentId1));
		}//setting to IN_PROCESS
		
		this.getFixture().setSDocumentStatus(sDocumentId1, pModController2, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);

		{//setting only one module controller to COMPLETED
			//adding document1 to be processesd with moduleController1
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController1, PEPPER_SDOCUMENT_STATUS.COMPLETED);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS, this.getFixture().getStatus(sDocumentId1));
		}//setting only one module controller to COMPLETED
		
		{//setting second module controller to COMPLETED
			//adding document1 to be processesd with moduleController2
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController2, PEPPER_SDOCUMENT_STATUS.DELETED);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.DELETED, this.getFixture().getStatus(sDocumentId1));
		}//setting second module controller to COMPLETED
		assertNull(((SDocument)sDocumentId1.getSIdentifiableElement()).getSDocumentGraph());
	}
	/**
	 * Tests the '{@link de.hub.corpling.pepper.pepperFW.PepperDocumentController#getStatus(de.hub.corpling.salt.saltCore.SElementId, de.hub.corpling.pepper.pepperFW.PepperModuleController) <em>Get Status</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.pepper.pepperFW.PepperDocumentController#getStatus(de.hub.corpling.salt.saltCore.SElementId, de.hub.corpling.pepper.pepperFW.PepperModuleController)
	 */
	public void testGetStatus__SElementId_PepperModuleController() 
	{
		PepperModuleController pModController1= PepperFWFactory.eINSTANCE.createPepperModuleController();
		this.getFixture().getPepperModuleControllers().add(pModController1);
		
		SElementId sDocumentId1= SaltFactory.eINSTANCE.createSElementId();
		sDocumentId1.setSIdentifiableElement(SaltFactory.eINSTANCE.createSDocument());
		sDocumentId1.setSId("id1");
		this.getFixture().observeSDocument(sDocumentId1);
		
		//checking status of sDocument1
		assertEquals(PEPPER_SDOCUMENT_STATUS.NOT_STARTED, this.getFixture().getStatus(sDocumentId1));
		
		{//setting to IN_PROCESS
			//adding document1 to be processesd with moduleController1
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController1, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS, this.getFixture().getStatus(sDocumentId1, pModController1));
		}//setting to IN_PROCESS
				
		{//setting only one module controller to COMPLETED
			//adding document1 to be processesd with moduleController1
			this.getFixture().setSDocumentStatus(sDocumentId1, pModController1, PEPPER_SDOCUMENT_STATUS.COMPLETED);
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.COMPLETED, this.getFixture().getStatus(sDocumentId1, pModController1));
		}//setting only one module controller to COMPLETED
		
		{//setting to IN_PROCESS
			try {
				//adding document1 to be processesd with moduleController1
				this.getFixture().setSDocumentStatus(sDocumentId1, pModController1, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
				fail("Shall not set the status to a lower type.");
			} catch (PepperConvertException e) {}
			//checking status of sDocument1
			assertEquals(PEPPER_SDOCUMENT_STATUS.COMPLETED, this.getFixture().getStatus(sDocumentId1, pModController1));
		}//setting to IN_PROCESS
	}
	
	/**
	 * Checks if a deadlock occurs in the following situation
	 * modules: 2 (1 importer)
	 * documents: 3
	 * max number of documents: 2
	 * 1 step: module1 starts doc1 and doc2
	 * 2 step: module1 starts doc3 (must wait)
	 * 3 step: module1 finishes doc1 and doc2
	 * 4 step: module2 starts doc1 and doc2
	 * 5 step: module2 finishes doc1 and doc2
	 * 6 step: module1 (really) starts doc3 (automatically)
	 * 7 step: module1 finishes doc3
	 * 8 step: module2 starts doc3
	 * 9 step: module2 finishes doc3 
	 * @throws InterruptedException 
	 */
	//TODO use this method and especially the nested class as framework to create more deadlock tests, ask Andreas to do that --> good training, for learning about deadlocks
	public void testCheckDeadlock_MaxDocuments2() throws InterruptedException
	{
		class PepperModController extends PepperModuleControllerImpl{
			public String name= null;
			boolean isImporter= false;
			public PepperModController(String name, boolean isImporter)
			{
				this.name= name;
				this.isImporter= isImporter;
			}
			
			public SElementId get(SElementId sDocId)
			{
				if (isImporter)
				{
					this.getPepperDocumentController().waitForSDocument();
				}
				try
				{
					this.getPepperDocumentController().setSDocumentStatus(sDocId, this, PEPPER_SDOCUMENT_STATUS.IN_PROCESS);
				}
				catch (PepperFWException e) {
					throw new PepperFWException("Cannot start module document '"+sDocId.getSId()+"' with '"+this.name+"', because of nested exception. ",e);
				}
				return(null);
			}
			
			@Override
			public void put(SElementId sElementId) 
			{
				this.getPepperDocumentController().setSDocumentStatus(sElementId, this, PEPPER_SDOCUMENT_STATUS.COMPLETED);
			}
			
			@Override
			public void finish(SElementId sElementId) 
			{
				this.getPepperDocumentController().setSDocumentStatus(sElementId, this, PEPPER_SDOCUMENT_STATUS.DELETED);
			}
			
			private SElementId sDocId= null;
			private  PEPPER_SDOCUMENT_STATUS status= null;
			public synchronized void  start(SElementId sDocId, long delayTime, PEPPER_SDOCUMENT_STATUS status) throws InterruptedException
			{
				this.readyWith.put(sDocId, false);
				this.sDocId= sDocId;
				this.status= status;
				Thread moduleThread= new Thread(this);
				moduleThread.start();
				//wait because of global variables has to be set in run(), before next call of this method is invoked 
				Thread.sleep(300);
			}
			private volatile Hashtable<SElementId, Boolean> readyWith= new Hashtable<SElementId, Boolean>(); 
			
			public synchronized void setReadyWith(SElementId sDocId) throws InterruptedException
			{
				this.readyWith.put(sDocId, true);
				Thread.sleep(200);
			}
			
			@Override
			public void run() 
			{
				SElementId sDocId= this.sDocId;
				PEPPER_SDOCUMENT_STATUS status= this.status;
				this.get(sDocId);
				while(!readyWith.get(sDocId))
				{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						throw new PepperConvertException("Couldn't wait.", e);
					}
				}
				if (PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(status))
					put(sDocId);
				else if(PEPPER_SDOCUMENT_STATUS.DELETED.equals(status))
					finish(sDocId);
			}
		}
		
		this.getFixture().setAMOUNT_OF_COMPUTABLE_SDOCUMENTS(2);
		
		SDocument sDocument1= SaltFactory.eINSTANCE.createSDocument();
		sDocument1.setSElementId(SaltFactory.eINSTANCE.createSElementId());
		sDocument1.getSElementId().setSId("doc01");
		
		SDocument sDocument2= SaltFactory.eINSTANCE.createSDocument();
		sDocument2.setSElementId(SaltFactory.eINSTANCE.createSElementId());
		sDocument2.getSElementId().setSId("doc02");
		
		SDocument sDocument3= SaltFactory.eINSTANCE.createSDocument();
		sDocument3.setSElementId(SaltFactory.eINSTANCE.createSElementId());
		sDocument3.getSElementId().setSId("doc03");
		
		SDocument[] sDocuments= {sDocument1,sDocument2, sDocument3};
		
		PepperModController module1= new PepperModController("module1", true);
		PepperModController module2= new PepperModController("module2", false);
		
		{//creating modules (moduleController)
			this.getFixture().getPepperModuleControllers().add(module1);
			this.getFixture().getPepperModuleControllers().add(module2);
		}//creating modules (moduleController)
		
		{//adding documents to observed ones by PepperDocumentController
			this.getFixture().observeSDocument(sDocument1.getSElementId());
			this.getFixture().observeSDocument(sDocument2.getSElementId());
			this.getFixture().observeSDocument(sDocument3.getSElementId());
		}//adding documents to observed ones by PepperDocumentController
		Long delayTime= 2000l;
		{//starting doc1 and doc2 with module1
			module1.start(sDocument1.getSElementId(), delayTime, PEPPER_SDOCUMENT_STATUS.COMPLETED);
			assertEquals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS, this.getFixture().getStatus(sDocument1.getSElementId(), module1));
			
			module1.start(sDocument2.getSElementId(), delayTime, PEPPER_SDOCUMENT_STATUS.COMPLETED);
			assertEquals(PEPPER_SDOCUMENT_STATUS.IN_PROCESS, this.getFixture().getStatus(sDocument2.getSElementId(), module1));
		}//starting doc1 and doc2 with module1
		//starting doc3 module1, must wait until doc1 and doc2 are ready
		module1.start(sDocument3.getSElementId(), delayTime, PEPPER_SDOCUMENT_STATUS.COMPLETED);
		Thread.sleep(100);
		assertEquals(PEPPER_SDOCUMENT_STATUS.NOT_STARTED, this.getFixture().getStatus(sDocument3.getSElementId(), module1));
		{//waiting and checking that doc1 and doc2 are finished by module1
			module1.setReadyWith(sDocument1.getSElementId());
			module1.setReadyWith(sDocument2.getSElementId());
			assertEquals(PEPPER_SDOCUMENT_STATUS.COMPLETED, this.getFixture().getStatus(sDocument1.getSElementId(), module1));
			assertEquals(PEPPER_SDOCUMENT_STATUS.COMPLETED, this.getFixture().getStatus(sDocument2.getSElementId(), module1));
		}//waiting and checking that doc1 and doc2 are finished by module1
		assertEquals(PEPPER_SDOCUMENT_STATUS.NOT_STARTED, this.getFixture().getStatus(sDocument3.getSElementId(), module1));
		{//wait until doc1 is processed by module1
			while (!PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(this.getFixture().getStatus(sDocument1.getSElementId(), module1)))
				Thread.sleep(100);
		}//wait until doc1 is processed by module1
		module2.start(sDocument1.getSElementId(), delayTime, PEPPER_SDOCUMENT_STATUS.COMPLETED);
		{//wait until doc2 is processed by module1
			while (!PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(this.getFixture().getStatus(sDocument2.getSElementId(), module1)))
				Thread.sleep(100);
		}//wait until doc2 is processed by module1
		module2.start(sDocument2.getSElementId(), delayTime, PEPPER_SDOCUMENT_STATUS.COMPLETED);
		module2.setReadyWith(sDocument1.getSElementId());
		module2.setReadyWith(sDocument2.getSElementId());
		{//wait until doc1 is processed by module1
			while (	(!PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(this.getFixture().getStatus(sDocument1.getSElementId(), module2))||
					(!PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(this.getFixture().getStatus(sDocument2.getSElementId(), module2)))))
				Thread.sleep(100);
		}//wait until doc1 is processed by module1
		
		module1.setReadyWith(sDocument3.getSElementId());
		{//wait until doc3 is processed by module1
		while (!PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(this.getFixture().getStatus(sDocument3.getSElementId(), module1)))
			Thread.sleep(100);
		}//wait until doc3 is processed by module1
		module2.start(sDocument3.getSElementId(), delayTime, PEPPER_SDOCUMENT_STATUS.COMPLETED);
		module2.setReadyWith(sDocument3.getSElementId());
		

		assertEquals(PEPPER_SDOCUMENT_STATUS.COMPLETED, this.getFixture().getStatus(sDocument1.getSElementId(), module2));
		assertEquals(PEPPER_SDOCUMENT_STATUS.COMPLETED, this.getFixture().getStatus(sDocument2.getSElementId(), module2));
		assertEquals(PEPPER_SDOCUMENT_STATUS.COMPLETED, this.getFixture().getStatus(sDocument3.getSElementId(), module2));
		{//waiting until all documents are processed by all modules
			for (SDocument sDoc: sDocuments)
			{
				while(!PEPPER_SDOCUMENT_STATUS.COMPLETED.equals(this.getFixture().getStatus(sDoc.getSElementId())))
				{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						throw new PepperConvertException("Couldn't wait.", e);
					}
				}
			}
		}//waiting until all documents are processed by all modules
	}
}

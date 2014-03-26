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
package de.hu_berlin.german.korpling.saltnpepper.pepper.core.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.german.korpling.saltnpepper.pepper.core.DocumentBus;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.DocumentControllerImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.DocumentController;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

@RunWith(JUnit4.class)
public class DocumentBusTest{

	protected DocumentBus fixture= null;
	
	public DocumentBus getFixture() {
		return fixture;
	}

	public void setFixture(DocumentBus fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp() throws Exception {
		inputs= new Vector<String>();
		for (int i=  0; i< 5;i++){
			inputs.add("in_controller_"+i);
		}
		
		outputs= new Vector<String>();
		for (int i=  0; i< 5;i++){
			outputs.add("out_controller_"+i);
		}
		
		setFixture(new DocumentBus(inputs, outputs));
	}
	
	private Vector<String> outputs= null;
	private Vector<String> inputs= null;
	
	/**
	 * Tests if the method {@link DocumentBus#getId()} returns a string 
	 */
	@Test
	public void testGetId(){
		assertNotNull(getFixture().getId());
		assertFalse("".equals(getFixture().getId()));
	}
	
	/**
	 * Tests if all input and output controllers are stored in lists.
	 */
	@Test
	public void testInOutControllerIds(){
		assertEquals(inputs.size(), getFixture().getInputControllerIds().size());
		assertTrue(inputs.containsAll(getFixture().getInputControllerIds()));
		
		assertEquals(outputs.size(), getFixture().getOutputControllerIds().size());
		assertTrue(outputs.containsAll(getFixture().getOutputControllerIds()));
	}
	/**
	 * Tests if {@link DocumentBus#isFinished()} returns true, when all input controllers have called 
	 * {@link DocumentBus#finish(String)}. 
	 */
	@Test
	public void testIsFinished()
	{
		setFixture(new DocumentBus(inputs, outputs));
		for (String id: inputs){
			assertFalse(getFixture().isFinished());
			getFixture().finish(id);
		}
		assertTrue(getFixture().isFinished());
	}
	
	/**
	 * Tests if the put of document Controllers does work correctly.
	 */
	@Test
	public void testPut()
	{
		Vector<DocumentController> docControllers= new Vector<DocumentController>(); 
		for (int i= 0; i < 20; i++){
			DocumentController docController= new DocumentControllerImpl();
			docControllers.add(docController);
			getFixture().put(docController);
		}
		for (ConcurrentLinkedQueue<DocumentController> queue: getFixture().getDocumentBus().values()){
			assertTrue("Expected: "+ docControllers+", but given: "+queue, queue.containsAll(docControllers));
		}
	}
	
	/**
	 * Tests if the put of a not contained document controller throws an exception.
	 */
	@Test
	public void testPut_Empty()
	{
		try{
			getFixture().put(null);
			fail("Should have thrown an exception");
		}catch(PepperFWException e){
			
		}
	}

	/**
	 * Tests if the put and pop of document Controllers.
	 */
	@Test
	public void testPutAndPop()
	{
		Vector<DocumentController> docControllers= new Vector<DocumentController>();
		SCorpusGraph sCorpGraph= SaltFactory.eINSTANCE.createSCorpusGraph();
		SCorpus sCorpus= SaltFactory.eINSTANCE.createSCorpus();
		sCorpGraph.addSNode(sCorpus);
		for (int i= 0; i < 20; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			sCorpGraph.addSDocument(sCorpus, sDoc);
			
			DocumentController docController= new DocumentControllerImpl(sDoc);
			docControllers.add(docController);
			getFixture().put(docController);
		}
		for (ConcurrentLinkedQueue<DocumentController> queue: getFixture().getDocumentBus().values()){
			assertTrue("Expected: "+ docControllers+", but given: "+queue, queue.containsAll(docControllers));
		}
		
		for (int i=1; i<= docControllers.size(); i++){
			for (String outputId: outputs){
				getFixture().pop(outputId);
			}
			for (ConcurrentLinkedQueue<DocumentController> queue: getFixture().getDocumentBus().values()){
				assertEquals(docControllers.size()-i, queue.size());
			}
		}
		for (ConcurrentLinkedQueue<DocumentController> queue: getFixture().getDocumentBus().values()){
			assertFalse("Expected: "+ docControllers+", but given: "+queue, queue.containsAll(docControllers));
		}
	}
	
	/**
	 * Creates 1 module controller producing data and 5 module controllers consuming data.
	 * 20 documents or {@link DocumentController} are produced and passed via the queue. All
	 * module controllers are threaded.
	 * @throws InterruptedException 
	 */
	@Test
	public void testPop_Threaded() throws InterruptedException
	{
		Vector<String> importModuleControllers= new Vector<String>();
		SimplePepperModuleController importerController= new SimplePepperModuleController();
		importerController.waitTime= new Long(25);
		importerController.controllerId="producer";
		importModuleControllers.add(importerController.controllerId);
		
		Vector<DocumentController> docControllers= new Vector<DocumentController>();
		SCorpusGraph sCorpGraph= SaltFactory.eINSTANCE.createSCorpusGraph();
		SCorpus sCorpus= SaltFactory.eINSTANCE.createSCorpus();
		sCorpGraph.addSNode(sCorpus);
		for (int i= 0; i < 20; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			sCorpGraph.addSDocument(sCorpus, sDoc);
			
			DocumentController docController= new DocumentControllerImpl(sDoc);
			docControllers.add(docController);
			importerController.getDocumentControllers().add(docController);
		}
		
		Vector<String> exportModuleControllers= new Vector<String>();
		Vector<SimplePepperModuleController> moduleControllers= new Vector<DocumentBusTest.SimplePepperModuleController>();
		for (int i=0; i < 5; i++){
			SimplePepperModuleController controller= new SimplePepperModuleController();
			controller.waitTime= new Long(10+ i*5);
			controller.controllerId="consumer_"+i;
			moduleControllers.add(controller);
			exportModuleControllers.add(controller.controllerId);
		}
		setFixture(new DocumentBus(importModuleControllers, exportModuleControllers));
		
		importerController.outputQueue= getFixture();
		Thread thread= new Thread(importerController);
		thread.start();
		
		for (SimplePepperModuleController outController: moduleControllers){
			outController.inputQueue= getFixture();
			thread= new Thread(outController);
			thread.start();
		}
		
		Long time= System.nanoTime();
		for (SimplePepperModuleController outController: moduleControllers){
			while(!outController.done){
				Thread.sleep(100);
				Long neededTime= (System.nanoTime()-time)/1000000;
				if (neededTime> 5000){
					System.out.println("abort test, since it took too long for consumer '"+outController+"'.");
					break;
				}
			}
		}
		for (SimplePepperModuleController outController: moduleControllers){
			assertEquals(outController.controllerId+" has not the expected number of elements", docControllers.size(), outController.getDocumentControllers().size());
			assertTrue(outController.getDocumentControllers().containsAll(docControllers));
			
			assertTrue(outController+" is not done", outController.done);
		}
	}
	
	private class SimplePepperModuleController implements Runnable{

		public Long waitTime= null;
		public String controllerId= null;
		private List<DocumentController> documentControllers= null;
		public List<DocumentController> getDocumentControllers(){
			if (documentControllers== null){
				this.documentControllers= new Vector<DocumentController>();
			}
			return(documentControllers);
		}
		public DocumentBus inputQueue= null;
		public DocumentBus outputQueue= null;
		public boolean done= false;
		
		@Override
		public void run() {
			Long time= System.nanoTime();
			if (inputQueue!= null)
			{
				DocumentController docController= null;
				while ((docController= inputQueue.pop(controllerId)) != null)
				{
					getDocumentControllers().add(docController);
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			else if (	(outputQueue!= null)&&
						(!getDocumentControllers().isEmpty()))
			{
				for (DocumentController docController: getDocumentControllers())
				{
					outputQueue.put(docController);
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				outputQueue.finish(controllerId);
			}
			time= (System.nanoTime()- time)/1000000;
			done= true;
		}
		public String toString(){
			return(controllerId+"(wait: "+waitTime+")"+": consumed/produced: "+getDocumentControllers()+ " ==> still on bus: " +getFixture().getDocumentBus());
		}
	}
}

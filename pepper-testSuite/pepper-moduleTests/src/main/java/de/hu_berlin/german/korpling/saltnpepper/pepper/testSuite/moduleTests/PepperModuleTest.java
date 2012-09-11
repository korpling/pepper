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
package de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.logReader.LogReader;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests.util.FileComparator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests.util.PepperModuleTestLogService;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * The method start simulates the Pepper framework as in normal runtime. The difference is, that here Pepper is started
 * in an developement environment and enables for developers to get an access directly to the OSGi environment.
 * @author Florian Zipser
 *
 */
public abstract class PepperModuleTest extends TestCase 
{
	private URI resourceURI= null;
	private URI temproraryURI= null;
	private URI specialParamsURI= null;
	
	protected PepperModule fixture= null;
	
	/**
	 * Sets the current fixture to test.
	 * @param fixture object to test, derived from {@link PepperModule}
	 */
	protected void setFixture(PepperModule fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the current fixture to test.
	 * @return object to test, derived from {@link PepperModule}
	 */
	protected PepperModule getFixture() {
		return fixture;
	}
	
	/**
	 * This methods starts the processing of Pepper in the development environment. In case of the fixture is 
	 * {@link PepperImporter}, first the method {@link PepperImporter#importCorpusStructure(SCorpusGraph)} is called.
	 * For all kinds of fixture, the method {@link PepperModule#start(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId)}
	 * is called for each {@link SDocument} object contained in the variable {@link PepperModule#getSaltProject()}.
	 * This method will wait, until each {@link PepperModuleController} return having finished the process.
	 * <br/>
	 * To create a test using this method do the following:<br/>
	 * <ul>
	 * <li>Create {@link CorpusDefinition} and add it to this object and set its {@link FormatDefinition} and corpus path </li>
	 * <li>Create a {@link SCorpusGraph} object as the one to be filled and add it with  
	 * 	<pre>
	 *  this.getFixture().getSaltProject().getSCorpusGraphs().add(importedCorpusGraph);
	 *	this.getFixture().importCorpusStructure(importedCorpusGraph);
	 * </pre>
	 * </li>
	 * </ul>
	 */
	public void start()
	{
		if (this.getFixture()== null)
			throw new PepperModuleTestException("Cannot start pepper-module, because the fixture is not set.");

		PepperModuleController moduleController= PepperFWFactory.eINSTANCE.createPepperModuleController();
		moduleController.setPepperModule(this.getFixture());
		
		PepperModuleTestLogService logService= new PepperModuleTestLogService();
		LogReader logReader= new LogReader();
		logService.setLogListener(logReader);
		this.getFixture().setLogService(logService);
		//setting the document controller, to delete documents
		moduleController.setPepperDocumentController(PepperFWFactory.eINSTANCE.createPepperDocumentController());
		moduleController.getPepperDocumentController().setREMOVE_SDOCUMENT_AFTER_PROCESSING(false);
		
		PepperQueuedMonitor queuedMonitor= PepperFWFactory.eINSTANCE.createPepperQueuedMonitor();
		moduleController.getInputPepperModuleMonitors().add(queuedMonitor);
		
		PepperFinishableMonitor m2jMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
		moduleController.setPepperM2JMonitor(m2jMonitor);
		
		
		if (this.getFixture() instanceof PepperImporter)
		{//if fixture is an importer, import corpus structure first
			if (this.getFixture().getSaltProject()== null)
				this.getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
			if (	(this.getFixture().getSaltProject().getSCorpusGraphs()== null) ||
					(this.getFixture().getSaltProject().getSCorpusGraphs().size()==0))
				this.getFixture().getSaltProject().getSCorpusGraphs().add(SaltFactory.eINSTANCE.createSCorpusGraph());
			if (this.getFixture().getSaltProject().getSCorpusGraphs().get(0)== null)
				throw new PepperModuleTestException("Cannot start pepper-module, because the corpus graph list of the SaltProject of the fixture has SDocument objects.");
			
			((PepperImporter) this.getFixture()).importCorpusStructure(this.getFixture().getSaltProject().getSCorpusGraphs().get(0));
		}//if fixture is an importer, import corpus structure first
		
		if (this.getFixture().getSaltProject().getSCorpusGraphs().get(0).getSDocuments()== null)
			throw new PepperModuleTestException("Cannot start pepper-module, because the first corpus graph of the SaltProject of the fixture has SDocument objects.");
		for (SDocument sDocument: this.getFixture().getSaltProject().getSCorpusGraphs().get(0).getSDocuments())
		{
			moduleController.getPepperDocumentController().observeSDocument(sDocument.getSElementId());
			queuedMonitor.put(sDocument.getSElementId());
		}
		queuedMonitor.finish();
		
		moduleController.start();
		//wait until monitor is finished
		m2jMonitor.waitUntilFinished();
		for (PepperException ex: m2jMonitor.getExceptions())
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public void testSetGetCorpusGraph()
	{
		SCorpusGraph corpGraph= SaltCommonFactory.eINSTANCE.createSCorpusGraph();
		this.getFixture().setSCorpusGraph(corpGraph);
		assertEquals(corpGraph, this.getFixture().getSCorpusGraph());
	}
	
	public void testGetName()
	{
		assertNotNull("The importer has to have a name.",this.getFixture().getName());
		assertFalse("The name of the importer cannot be empty.", this.getFixture().getName().equals(""));
	}
	
	/**
	 * Sets the path to temprorary folder for module.
	 * @param tempURI URI with path 
	 */
	public void setTemprorariesURI(URI tempURI)
	{
		if (tempURI== null)
			throw new PepperModuleTestException("The given temprorary uri is empty.");
		if (tempURI.toString()== null)
			throw new PepperModuleTestException("Cannot create file for uri '"+tempURI+"'.");
		File tmpDir= new File(tempURI.toString());
		if (!tmpDir.exists())
			tmpDir.mkdirs();
		this.temproraryURI= tempURI;
		this.getFixture().setTemproraries(temproraryURI);
		
	}
	public void testSetGetTemproraries()
	{
		assertNotNull("Cannot run test, because temproraries aren't set. Please call setTemprorariesURI(URI tempURI) before start testing.", temproraryURI);
		this.getFixture().setTemproraries(temproraryURI);
		assertEquals(temproraryURI, this.getFixture().getTemproraries());
	}
	public void setSpecialParamsURI(URI specialParamsURI) 
	{
		this.specialParamsURI = specialParamsURI;
	}

	public URI getSpecialParamsURI() {
		return specialParamsURI;
	}
	
	/**
	 * Sets the path to resources folder for module.
	 * @param resourceURI URI with path 
	 */
	public void setResourcesURI(URI resourceURI)
	{
		if (resourceURI!= null)
		{	
			File resourceDir= new File(resourceURI.toFileString());
			if (!resourceDir.exists())
				resourceDir.mkdirs();
			this.getFixture().setResources(resourceURI);
			this.resourceURI= resourceURI;
		}
		else throw new RuntimeException("A resource uri must be set.");
	}
	
	/**
	 * Checks if the resource path is set.
	 */
	public void testSetGetResources()
	{
		assertNotNull("Cannot run test, because resources arent set. Please call setResourcesURI(URI resourceURI) before start testing.", resourceURI);
		this.getFixture().setResources(resourceURI);
		assertEquals(resourceURI, this.getFixture().getResources());
	}
	
	/**
	 * Checks if symbolic name is given.
	 * FIXME does not work any more without an OSGi environment.  
	 */
	public void testGetSymbolicName()
	{
//		assertNotNull("The symbolic name of module shall not be null.", this.getFixture().getSymbolicName());
	}
	
	/**
	 * Compares the content of two files. Iff they are exactly the same, 
	 * than true will be returned. False otherwise.
	 * @param uri1 first file to compare
	 * @param uri2 second file to compare
	 * @return true, iff files are exactly the same
	 * @throws IOException
	 */
	public boolean compareFiles(URI uri1, URI uri2) throws IOException
	{
		return(this.compareFiles(new File(uri1.toFileString()), new File(uri2.toFileString())));
	}
	
	/**
	 * Compares the content of two files. Iff they are exactly the same, 
	 * than true will be returned. False otherwise.
	 * @param file1 first file to compare
	 * @param file2 second file to compare
	 * @return true, iff files are exactly the same
	 * @throws IOException 
	 * @throws IOException
	 */
	public boolean compareFiles(File file1, File file2) throws IOException
	{
		FileComparator comparator= new FileComparator();
		return(comparator.compareFiles(file1, file2));
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.PepperConverter;

import java.io.File;

import org.eclipse.emf.common.util.URI;

import junit.framework.TestCase;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.JobCases.PepperJobDelegatorTest;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.MAPPING_RESULT;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapperController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

public class PepperModuleProgressTest extends TestCase {

	/**
	 * The fixture for this Pepper Job test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	protected PepperJobDelegatorTest fixture = null;
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
	
	class MyPepperMapper extends PepperMapperImpl
	{
		@Override
		public MAPPING_RESULT mapSDocument() 
		{
			while (clock < 10)
			{
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					throw new PepperModuleException("", e);
				}
				this.setProgress(myProgress);
			}
			return(MAPPING_RESULT.FINISHED);
		}
	}
	
	class MyImporter extends PepperImporterImpl
	{
		@Override
		public void importCorpusStructure(SCorpusGraph sCorpusGraph)
		{
			
			SCorpus corpus= SaltCommonFactory.eINSTANCE.createSCorpus();
			sCorpusGraph.addSNode(corpus);
			for (int i=0; i< 5; i++)
			{
				sCorpusGraph.addSDocument(corpus, SaltCommonFactory.eINSTANCE.createSDocument());
			}
		}
		
		@Override
		public PepperMapper createPepperMapper(SElementId sElementId)
		{
			return(new MyPepperMapper());
		}
	}
	/** just an alibi exporter **/
	class MyExporter extends PepperExporterImpl
	{
		@Override
		public PepperMapper createPepperMapper(SElementId sElementId)
		{
			return(new MyPepperMapper());
		}
	}
	
	/** dummy progress of all mappers**/
	private volatile Double myProgress= 0d;
	/** clock to increase progress **/
	private volatile Integer clock=0;
	
	/**
	 * Checks, that progress of process is computed correctly by {@link PepperModule}, {@link PepperMapperController} and {@link PepperMapper}
	 * Checks for a number of 5 documents, that progress is computed correctly.
	 * objects.
	 * @throws InterruptedException 
	 */
	public void testGetProgress() throws InterruptedException
	{
		File file= new File(System.getProperty("java.io.tmpdir")+"/PepperModuleTest_progress/");
		file.mkdirs();
		
		//create sample corpus graph with 5 SDocuments
		SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
		
		URI uri = URI.createFileURI(file.getAbsolutePath());
		CorpusDefinition corpDef= null;
		corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setCorpusPath(uri);
		
		//init job
		((PepperJob)this.getFixture()).setSaltProject(saltProject);
		((PepperJob)this.getFixture()).setId(0);
		
		//init importer
		MyImporter myImporter= new MyImporter();
		corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setCorpusPath(uri);
		myImporter.setCorpusDefinition(corpDef);
		((PepperJob)this.getFixture()).getPepperImporters().add(myImporter);
		
		//init importer
		MyExporter myExporter= new MyExporter();
		corpDef= PepperFWFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setCorpusPath(uri);
		myExporter.setCorpusDefinition(corpDef);
		((PepperJob)this.getFixture()).getPepperExporters().add(myExporter);
						
		PepperFinishableMonitor jobMonitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
		((PepperJob)this.getFixture()).setPepperJ2CMonitor(jobMonitor);
		Runnable runner= new Runnable() {
			@Override
			public void run() {
				((PepperJob)getFixture()).start();
			}
		};
		new Thread(runner).start();
		
		Thread.sleep(1000);
		 for (int i=0; i<= 10;i++)
		 {
			 assertEquals(myProgress, myImporter.getProgress());
			 for (SDocument sDoc: saltProject.getSCorpusGraphs().get(0).getSDocuments())
			 {
				 assertEquals(myProgress, myImporter.getProgress(sDoc.getSElementId()));
			 }
			 if (myProgress==0.7d)
			 {
				 myProgress= 0.8d;
			 }
			 else myProgress= myProgress +0.1d;
			 clock++;
			 Thread.sleep(100);
		 }
	}
}

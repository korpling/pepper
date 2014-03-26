package de.hu_berlin.german.korpling.saltnpepper.pepper.core.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.Step;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.tests.PepperJobImplTest.MyImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

public class PepperJobImplTest_removeDocumentFromMM extends PepperJobImpl{
	public PepperJobImplTest_removeDocumentFromMM() {
		super("myJob");
	}
	private PepperJobImpl fixture= null;
	
	public PepperJobImpl getFixture() {
		return fixture;
	}

	public void setFixture(PepperJobImpl fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp(){
		setFixture(this);
	}
	private URI dummyResourceURI= URI.createFileURI(new File(System.getProperty("java.io.tmpdir")).getAbsolutePath());
	/**
	 * Checks if a {@link SDocumentGraph} is not in memory any more, after all {@link PepperModule}'s have processed it.
	 * Remove means, that the connection between {@link SDocument} and {@link SDocumentGraph} is removed. 
	 */
	@Test
	public void testDocumentRemoveFromMM(){
		List<SDocument> expectedSDocuments= new Vector<SDocument>();
		for (int i= 0; i< 30; i++){
			SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
			sDoc.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
			expectedSDocuments.add(sDoc);
		}
		
		Step importStep1= new Step("im1");
		MyImporter myImporter1= new PepperJobImplTest.MyImporter("myImporter1");
		myImporter1.setResources(dummyResourceURI);
		myImporter1.expectedSDocuments= expectedSDocuments;
		importStep1.setPepperModule(myImporter1);
		getFixture().addStep(importStep1);
		
		Step exportStep1= new Step("ex1");
		MyExporter myExporter1= new MyExporter("ex1");
		myExporter1.setResources(dummyResourceURI);
		exportStep1.setPepperModule(myExporter1);
		getFixture().addStep(exportStep1);
		
		for (SDocument sDoc: expectedSDocuments){
			assertNotNull(sDoc.getSDocumentGraph());
		}
		PepperConfiguration conf= new PepperConfiguration();
		conf.setProperty(PepperConfiguration.PROP_MAX_AMOUNT_OF_SDOCUMENTS, "2");
			
		getFixture().convert();
		
		for (SDocument sDoc: expectedSDocuments){
			assertNull(sDoc.getSDocumentGraph());
		}
	}
	
	class MyExporter extends PepperExporterImpl{
		public MyExporter(String name){
			setName(name);
		}
		@Override
		public PepperMapper createPepperMapper(SElementId sElementId) {
			if (sElementId.getSIdentifiableElement() instanceof SDocument){
				if (((SDocument)sElementId.getSIdentifiableElement()).getSDocumentGraph()== null){
					throw new PepperModuleException(this, "An error in test occured, because the SDocumentGraph was null.");
				}
			}
			return(new PepperMapperImpl(){
				@Override
				public DOCUMENT_STATUS mapSDocument() {
					try {
						Thread.sleep(1000);
						System.out.println("done with: "+ getSDocument());
					} catch (InterruptedException e) {
						throw new PepperModuleException(this,"", e);
					}
					return DOCUMENT_STATUS.COMPLETED;
				}
			});
		}
	}
}

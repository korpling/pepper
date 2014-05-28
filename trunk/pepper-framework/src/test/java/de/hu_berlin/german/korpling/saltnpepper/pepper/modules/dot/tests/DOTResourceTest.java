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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.dot.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.resources.dot.DOTResource;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpanningRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STimeline;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STimelineRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SaltCorePackage;

public class DOTResourceTest extends TestCase 
{
	private SaltProject fixture= null;
	
	/**
	 * Sets the fixture for this SText Overlapping Relation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected void setFixture(SaltProject fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this SText Overlapping Relation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected SaltProject getFixture() {
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
		setFixture(SaltCommonFactory.eINSTANCE.createSaltProject());
	}
	
//	/**
//	 * 
//	 * 		span1
//	 * 		/		\
//	 * 	tok1		tok2
//	 * 	|			|
//	 * 	This		is
//	 * @throws IOException 
//	 */
//	public void testDOTResourceWriting1() throws IOException
//	{
//		String tmpFileName= "_TMP/resourceWriting1.dot";
//		String expectedFileName= "./src/test/resources/expected/resourceWriting1.dot";
//		
//		SDocumentGraph sDocGraph= SaltCommonFactory.eINSTANCE.createSDocumentGraph();
//		{//creating the graph
//			//text
//			STextualDS sText= SaltCommonFactory.eINSTANCE.createSTextualDS();
//			sText.setSText("This is a sample text.");
//			sDocGraph.addSNode(sText);
//			
//			//tok1
//			SToken tok1= SaltCommonFactory.eINSTANCE.createSToken();
//			sDocGraph.addSNode(tok1);
//			//tok2
//			SToken tok2= SaltCommonFactory.eINSTANCE.createSToken();
//			sDocGraph.addSNode(tok2);
//			//textlRel1
//			STextualRelation textRel1= SaltCommonFactory.eINSTANCE.createSTextualRelation();
//			textRel1.setSTextualDS(sText);
//			textRel1.setSToken(tok1);
//			textRel1.setSStart(0);
//			textRel1.setSEnd(4);
//			sDocGraph.addSRelation(textRel1);
//			
//			//textlRel1
//			STextualRelation textRel2= SaltCommonFactory.eINSTANCE.createSTextualRelation();
//			textRel2.setSTextualDS(sText);
//			textRel2.setSToken(tok2);
//			textRel2.setSStart(5);
//			textRel2.setSEnd(7);
//			sDocGraph.addSRelation(textRel2);
//			
//			//span1 
//			SSpan span= SaltCommonFactory.eINSTANCE.createSSpan();
//			sDocGraph.addSNode(span);
//			
//			//spanRel1
//			SSpanningRelation spanRel1= SaltCommonFactory.eINSTANCE.createSSpanningRelation();
//			spanRel1.setSSpan(span);
//			spanRel1.setSToken(tok1);
//			sDocGraph.addSRelation(spanRel1);
//			
//			//spanRel2
//			SSpanningRelation spanRel2= SaltCommonFactory.eINSTANCE.createSSpanningRelation();
//			spanRel2.setSSpan(span);
//			spanRel2.setSToken(tok2);
//			sDocGraph.addSRelation(spanRel2);
//		}
//		
//		
//		{//save and reload
//			// create resource set and resource 
//			ResourceSet resourceSet = new ResourceSetImpl();
//
//			// Register XML resource factory
//			resourceSet.getPackageRegistry().put(SaltCorePackage.eINSTANCE.getNsURI(), SaltCorePackage.eINSTANCE);
//			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("dot", new DOTResource());
//			
//			URI outURI= URI.createFileURI(tmpFileName);
//			
//			//save resources
//			Resource resourceOut = resourceSet.createResource(outURI);
//			resourceOut.getContents().add(sDocGraph);
//			resourceOut.save(null);
//		}
//		assertTrue(this.compareFiles(new File(expectedFileName), new File(tmpFileName)));
//	}
//	
//	/**
//	 * 
//	 * 		span1
//	 * 		/		\
//	 * 	tok1		tok2
//	 *  time(1-1)	(3-4)
//	 * 	|			|
//	 * 	This		is
//	 * @throws IOException 
//	 */
//	public void testDOTResourceWriting2() throws IOException
//	{
//		String tmpFileName= "_TMP/resourceWriting2.dot";
//		String expectedFileName= "./src/test/resources/expected/resourceWriting2.dot";
//		
//		SDocumentGraph sDocGraph= SaltCommonFactory.eINSTANCE.createSDocumentGraph();
//		sDocGraph.setSName("graph1");
//		sDocGraph.setSId("graph1");
//		{//creating the graph
//			//text
//			STextualDS sText= SaltCommonFactory.eINSTANCE.createSTextualDS();
//			sText.setSText("This is a sample text.");
//			sDocGraph.addSNode(sText);
//			
//			//tok1
//			SToken tok1= SaltCommonFactory.eINSTANCE.createSToken();
//			sDocGraph.addSNode(tok1);
//			//tok2
//			SToken tok2= SaltCommonFactory.eINSTANCE.createSToken();
//			sDocGraph.addSNode(tok2);
//			//textlRel1
//			STextualRelation textRel1= SaltCommonFactory.eINSTANCE.createSTextualRelation();
//			textRel1.setSTextualDS(sText);
//			textRel1.setSToken(tok1);
//			textRel1.setSStart(0);
//			textRel1.setSEnd(4);
//			sDocGraph.addSRelation(textRel1);
//			
//			//textlRel1
//			STextualRelation textRel2= SaltCommonFactory.eINSTANCE.createSTextualRelation();
//			textRel2.setSTextualDS(sText);
//			textRel2.setSToken(tok2);
//			textRel2.setSStart(5);
//			textRel2.setSEnd(7);
//			sDocGraph.addSRelation(textRel2);
//			
//			//span1 
//			SSpan span= SaltCommonFactory.eINSTANCE.createSSpan();
//			sDocGraph.addSNode(span);
//			
//			//spanRel1
//			SSpanningRelation spanRel1= SaltCommonFactory.eINSTANCE.createSSpanningRelation();
//			spanRel1.setSSpan(span);
//			spanRel1.setSToken(tok1);
//			sDocGraph.addSRelation(spanRel1);
//			
//			//spanRel2
//			SSpanningRelation spanRel2= SaltCommonFactory.eINSTANCE.createSSpanningRelation();
//			spanRel2.setSSpan(span);
//			spanRel2.setSToken(tok2);
//			sDocGraph.addSRelation(spanRel2);
//			
//			//STimeline
//			STimeline time= SaltCommonFactory.eINSTANCE.createSTimeline();
//			sDocGraph.addSNode(time);
//			time.addSPointOfTime("1");
//			time.addSPointOfTime("2");
//			time.addSPointOfTime("3");
//			time.addSPointOfTime("4");
//			
//			//spanRel1
//			STimelineRelation timeRel1= SaltCommonFactory.eINSTANCE.createSTimelineRelation();
//			timeRel1.setSTimeline(time);
//			timeRel1.setSToken(tok1);
//			timeRel1.setSStart(1);
//			timeRel1.setSStart(1);
//			sDocGraph.addSRelation(timeRel1);
//			
//			//spanRel1
//			STimelineRelation timeRel2= SaltCommonFactory.eINSTANCE.createSTimelineRelation();
//			timeRel2.setSTimeline(time);
//			timeRel2.setSToken(tok2);
//			timeRel2.setSStart(3);
//			timeRel2.setSStart(4);
//			sDocGraph.addSRelation(timeRel2);
//		}
//		
//		{//save and reload
//			// create resource set and resource 
//			ResourceSet resourceSet = new ResourceSetImpl();
//
//			// Register XML resource factory
//			resourceSet.getPackageRegistry().put(SaltCorePackage.eINSTANCE.getNsURI(), SaltCorePackage.eINSTANCE);
//			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("dot", new DOTResource());
//			
//			URI outURI= URI.createFileURI(tmpFileName);
//			
//			//save resources
//			Resource resourceOut = resourceSet.createResource(outURI);
//			resourceOut.getContents().add(sDocGraph);
//			resourceOut.save(null);
//		}
//		assertTrue(this.compareFiles(new File(expectedFileName), new File(tmpFileName)));
//	}
	
	public void testAlibiTest()
	{}
	
	protected boolean compareFiles(File file1, File file2) throws IOException
	{
		boolean retVal= false;
		
		if ((file1== null) || (file2== null))
			throw new NullPointerException("One of the files to compare are null.");
		
		String contentFile1= null;
		String contentFile2= null;
		BufferedReader brFile1= null;
		BufferedReader brFile2= null;
		try 
		{
			brFile1=  new BufferedReader(new FileReader(file1));
			String line= null;
			while (( line = brFile1.readLine()) != null)
			{
		          contentFile1= contentFile1+  line;
		    }
			brFile2=  new BufferedReader(new FileReader(file2));
			line= null;
			while (( line = brFile2.readLine()) != null)
			{
		          contentFile2= contentFile2+  line;
		    }
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally 
		{
			brFile1.close();
			brFile2.close();
		} 
		
		if (contentFile1== null)
		{
			if (contentFile2== null)
				retVal= true;
			else retVal= false;
		}	
		else if (contentFile1.equals(contentFile2))
			retVal= true;
		return(retVal);
	}
}

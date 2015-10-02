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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;
import de.hu_berlin.u.saltnpepper.salt.SaltFactory;
import de.hu_berlin.u.saltnpepper.salt.common.SaltProject;

public class DOTResourceTest extends TestCase {
	private SaltProject fixture = null;

	/**
	 * Sets the fixture for this SText Overlapping Relation test case. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 */
	protected void setFixture(SaltProject fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this SText Overlapping Relation test case. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 */
	protected SaltProject getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SaltFactory.createSaltProject());
	}

	// /**
	// *
	// * span1
	// * / \
	// * tok1 tok2
	// * | |
	// * This is
	// * @throws IOException
	// */
	// public void testDOTResourceWriting1() throws IOException
	// {
	// String tmpFileName= "_TMP/resourceWriting1.dot";
	// String expectedFileName=
	// "./src/test/resources/expected/resourceWriting1.dot";
	//
	// SDocumentGraph sDocGraph=
	// SaltCommonFactory.eINSTANCE.createSDocumentGraph();
	// {//creating the graph
	// //text
	// STextualDS sText= SaltCommonFactory.eINSTANCE.createSTextualDS();
	// sText.setText("This is a sample text.");
	// sDocGraph.addNode(sText);
	//
	// //tok1
	// SToken tok1= SaltCommonFactory.eINSTANCE.createSToken();
	// sDocGraph.addNode(tok1);
	// //tok2
	// SToken tok2= SaltCommonFactory.eINSTANCE.createSToken();
	// sDocGraph.addNode(tok2);
	// //textlRel1
	// STextualRelation textRel1=
	// SaltCommonFactory.eINSTANCE.createSTextualRelation();
	// textRel1.setTarget(sText);
	// textRel1.setSToken(tok1);
	// textRel1.setStart(0);
	// textRel1.setEnd(4);
	// sDocGraph.addRelation(textRel1);
	//
	// //textlRel1
	// STextualRelation textRel2=
	// SaltCommonFactory.eINSTANCE.createSTextualRelation();
	// textRel2.setTarget(sText);
	// textRel2.setSToken(tok2);
	// textRel2.setStart(5);
	// textRel2.setEnd(7);
	// sDocGraph.addRelation(textRel2);
	//
	// //span1
	// SSpan span= SaltCommonFactory.eINSTANCE.createSSpan();
	// sDocGraph.addNode(span);
	//
	// //spanRel1
	// SSpanningRelation spanRel1=
	// SaltCommonFactory.eINSTANCE.createSSpanningRelation();
	// spanRel1.setSource(span);
	// spanRel1.setSToken(tok1);
	// sDocGraph.addRelation(spanRel1);
	//
	// //spanRel2
	// SSpanningRelation spanRel2=
	// SaltCommonFactory.eINSTANCE.createSSpanningRelation();
	// spanRel2.setSource(span);
	// spanRel2.setSToken(tok2);
	// sDocGraph.addRelation(spanRel2);
	// }
	//
	//
	// {//save and reload
	// // create resource set and resource
	// ResourceSet resourceSet = new ResourceSetImpl();
	//
	// // Register XML resource factory
	// resourceSet.getPackageRegistry().put(SaltCorePackage.eINSTANCE.getNsURI(),
	// SaltCorePackage.eINSTANCE);
	// resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("dot",
	// new DOTResource());
	//
	// URI outURI= URI.createFileURI(tmpFileName);
	//
	// //save resources
	// Resource resourceOut = resourceSet.createResource(outURI);
	// resourceOut.getContents().add(sDocGraph);
	// resourceOut.save(null);
	// }
	// assertTrue(this.compareFiles(new File(expectedFileName), new
	// File(tmpFileName)));
	// }
	//
	// /**
	// *
	// * span1
	// * / \
	// * tok1 tok2
	// * time(1-1) (3-4)
	// * | |
	// * This is
	// * @throws IOException
	// */
	// public void testDOTResourceWriting2() throws IOException
	// {
	// String tmpFileName= "_TMP/resourceWriting2.dot";
	// String expectedFileName=
	// "./src/test/resources/expected/resourceWriting2.dot";
	//
	// SDocumentGraph sDocGraph=
	// SaltCommonFactory.eINSTANCE.createSDocumentGraph();
	// sDocGraph.setName("graph1");
	// sDocGraph.setId("graph1");
	// {//creating the graph
	// //text
	// STextualDS sText= SaltCommonFactory.eINSTANCE.createSTextualDS();
	// sText.setText("This is a sample text.");
	// sDocGraph.addNode(sText);
	//
	// //tok1
	// SToken tok1= SaltCommonFactory.eINSTANCE.createSToken();
	// sDocGraph.addNode(tok1);
	// //tok2
	// SToken tok2= SaltCommonFactory.eINSTANCE.createSToken();
	// sDocGraph.addNode(tok2);
	// //textlRel1
	// STextualRelation textRel1=
	// SaltCommonFactory.eINSTANCE.createSTextualRelation();
	// textRel1.setTarget(sText);
	// textRel1.setSToken(tok1);
	// textRel1.setStart(0);
	// textRel1.setEnd(4);
	// sDocGraph.addRelation(textRel1);
	//
	// //textlRel1
	// STextualRelation textRel2=
	// SaltCommonFactory.eINSTANCE.createSTextualRelation();
	// textRel2.setTarget(sText);
	// textRel2.setSToken(tok2);
	// textRel2.setStart(5);
	// textRel2.setEnd(7);
	// sDocGraph.addRelation(textRel2);
	//
	// //span1
	// SSpan span= SaltCommonFactory.eINSTANCE.createSSpan();
	// sDocGraph.addNode(span);
	//
	// //spanRel1
	// SSpanningRelation spanRel1=
	// SaltCommonFactory.eINSTANCE.createSSpanningRelation();
	// spanRel1.setSource(span);
	// spanRel1.setSToken(tok1);
	// sDocGraph.addRelation(spanRel1);
	//
	// //spanRel2
	// SSpanningRelation spanRel2=
	// SaltCommonFactory.eINSTANCE.createSSpanningRelation();
	// spanRel2.setSource(span);
	// spanRel2.setSToken(tok2);
	// sDocGraph.addRelation(spanRel2);
	//
	// //STimeline
	// STimeline time= SaltCommonFactory.eINSTANCE.createSTimeline();
	// sDocGraph.addNode(time);
	// time.addSPointOfTime("1");
	// time.addSPointOfTime("2");
	// time.addSPointOfTime("3");
	// time.addSPointOfTime("4");
	//
	// //spanRel1
	// STimelineRelation timeRel1=
	// SaltCommonFactory.eINSTANCE.createSTimelineRelation();
	// timeRel1.setTimeline(time);
	// timeRel1.setSToken(tok1);
	// timeRel1.setStart(1);
	// timeRel1.setStart(1);
	// sDocGraph.addRelation(timeRel1);
	//
	// //spanRel1
	// STimelineRelation timeRel2=
	// SaltCommonFactory.eINSTANCE.createSTimelineRelation();
	// timeRel2.setTimeline(time);
	// timeRel2.setSToken(tok2);
	// timeRel2.setStart(3);
	// timeRel2.setStart(4);
	// sDocGraph.addRelation(timeRel2);
	// }
	//
	// {//save and reload
	// // create resource set and resource
	// ResourceSet resourceSet = new ResourceSetImpl();
	//
	// // Register XML resource factory
	// resourceSet.getPackageRegistry().put(SaltCorePackage.eINSTANCE.getNsURI(),
	// SaltCorePackage.eINSTANCE);
	// resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("dot",
	// new DOTResource());
	//
	// URI outURI= URI.createFileURI(tmpFileName);
	//
	// //save resources
	// Resource resourceOut = resourceSet.createResource(outURI);
	// resourceOut.getContents().add(sDocGraph);
	// resourceOut.save(null);
	// }
	// assertTrue(this.compareFiles(new File(expectedFileName), new
	// File(tmpFileName)));
	// }

	public void testAlibiTest() {
	}

	protected boolean compareFiles(File file1, File file2) throws IOException {
		boolean retVal = false;

		if ((file1 == null) || (file2 == null))
			throw new NullPointerException("One of the files to compare are null.");

		String contentFile1 = null;
		String contentFile2 = null;
		BufferedReader brFile1 = null;
		BufferedReader brFile2 = null;
		try {
			brFile1 = new BufferedReader(new FileReader(file1));
			String line = null;
			while ((line = brFile1.readLine()) != null) {
				contentFile1 = contentFile1 + line;
			}
			brFile2 = new BufferedReader(new FileReader(file2));
			line = null;
			while ((line = brFile2.readLine()) != null) {
				contentFile2 = contentFile2 + line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			brFile1.close();
			brFile2.close();
		}

		if (contentFile1 == null) {
			if (contentFile2 == null)
				retVal = true;
			else
				retVal = false;
		} else if (contentFile1.equals(contentFile2))
			retVal = true;
		return (retVal);
	}
}

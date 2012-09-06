package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.tests;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import junit.framework.TestCase;

public class PepperImporterImplTest extends TestCase{

	class FixtureImporter extends PepperImporterImpl
	{
		
	}
	
	private FixtureImporter fixture= null;

	public void setFixture(FixtureImporter fixture) {
		this.fixture = fixture;
	}

	public FixtureImporter getFixture() {
		return fixture;
	}
	
	public void setUp()
	{
		this.setFixture(new FixtureImporter());
	}
	
	/**
	 * Tests method {@link GenericXMLImporter#isFileToImport(org.eclipse.emf.common.util.URI, java.util.List)}, if the computation works
	 * correctly for varying sets of file extension lists and {@link URI} objects.
	 * 
	 * <table>
	 * <tr><td colspan="2">Set 1</td></tr>
	 * <tr><td>List</td><td>empty</td></tr>
	 * <tr><td>file extension</td><td>abc</td></tr>
	 * <tr><td>expected result</td><td>true</td></tr>
	 * <tr><td colspan="2">Set 2</td></tr>
	 * <tr><td>List</td><td>efg</td></tr>
	 * <tr><td>file extension</td><td>abc</td></tr>
	 * <tr><td>expected result</td><td>false</td></tr>
	 * <tr><td colspan="2">Set 3</td></tr>
	 * <tr><td>List</td><td>-efg</td></tr>
	 * <tr><td>file extension</td><td>abc</td></tr>
	 * <tr><td>expected result</td><td>true</td></tr>
	 * <tr><td colspan="2">Set 4</td></tr>
	 * <tr><td>List</td><td>-abc</td></tr>
	 * <tr><td>file extension</td><td>abc</td></tr>
	 * <tr><td>expected result</td><td>false</td></tr>
	 * <tr><td colspan="2">Set 5</td></tr>
	 * <tr><td>List</td><td>-abc, efg</td></tr>
	 * <tr><td>file extension</td><td>abc</td></tr>
	 * <tr><td>expected result</td><td>false</td></tr>
	 * <tr><td colspan="2">Set 6</td></tr>
	 * <tr><td>List</td><td>-abc, efg</td></tr>
	 * <tr><td>file extension</td><td>efg</td></tr>
	 * <tr><td>expected result</td><td>true</td></tr>
	 * </table>
	 * 
	 */
	public void testIsFileToImport()
	{
		EList<String> fileExtensionDescs= null;
		URI testFile= null;
		String endingABC= "abc";
		String endingEFG= "efg";
		
		//set 1
		fileExtensionDescs= new BasicEList<String>();
		testFile= URI.createFileURI("test."+ endingABC);
		assertTrue(this.getFixture().isFileToImport(testFile, fileExtensionDescs));
		
		//set 2
		fileExtensionDescs= new BasicEList<String>();
		fileExtensionDescs.add(endingEFG);
		testFile= URI.createFileURI("test."+ endingABC);
		assertFalse(this.getFixture().isFileToImport(testFile, fileExtensionDescs));
		
		//set 3
		fileExtensionDescs= new BasicEList<String>();
		fileExtensionDescs.add(PepperImporter.NEGATIVE_FILE_EXTENSION_MARKER+endingEFG);
		testFile= URI.createFileURI("test."+ endingABC);
		assertTrue(this.getFixture().isFileToImport(testFile, fileExtensionDescs));
		
		//set 4
		fileExtensionDescs= new BasicEList<String>();
		fileExtensionDescs.add(PepperImporter.NEGATIVE_FILE_EXTENSION_MARKER+endingABC);
		testFile= URI.createFileURI("test."+ endingABC);
		assertFalse(this.getFixture().isFileToImport(testFile, fileExtensionDescs));
		
		//set 5
		fileExtensionDescs= new BasicEList<String>();
		fileExtensionDescs.add(PepperImporter.NEGATIVE_FILE_EXTENSION_MARKER+endingABC);
		fileExtensionDescs.add(endingEFG);
		testFile= URI.createFileURI("test."+ endingABC);
		assertFalse(this.getFixture().isFileToImport(testFile, fileExtensionDescs));
		
		//set 6
		fileExtensionDescs= new BasicEList<String>();
		fileExtensionDescs.add(PepperImporter.NEGATIVE_FILE_EXTENSION_MARKER+endingABC);
		fileExtensionDescs.add(endingEFG);
		testFile= URI.createFileURI("test."+ endingEFG);
		assertTrue(this.getFixture().isFileToImport(testFile, fileExtensionDescs));	
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.tests;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
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
		this.getFixture().setSCorpusGraph(SaltFactory.eINSTANCE.createSCorpusGraph());
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
	
	/**
	 * Tests if several folder structures are mapped correctly, with changing list of file extensions.
	 * <pre>
	 * |-root
	 *   |-superCorpus1
	 *     |-file1.abc
	 *     |-file2.abc
	 *     |-file3.def
	 *   |-superCorpus2
	 *     |-file4.abc
	 *     |-file5.def
	 * </pre>
	 * 
	 * set1:
	 * list: empty
	 * 
	 * set2:
	 * list: abc
	 * 
	 * set3:
	 * list:-abc
	 * @throws IOException 
	 */
	public void testCreateCorpusStructure() throws IOException
	{
		File tmpFolder= new File(System.getProperty("java.io.tmpdir"));
		
		File root= new File(tmpFolder+"/root");
		root.mkdirs();
		File superCorpus1=new File(root+"/superCorpus1");
		superCorpus1.mkdirs();
		File superCorpus2=new File(root+"/superCorpus2");
		superCorpus2.mkdirs();
		File file1= new File(superCorpus1+"/file1.abc");
		file1.createNewFile();
		File file2= new File(superCorpus1+"/file2.abc");
		file2.createNewFile();
		File file3= new File(superCorpus1+"/file3.def");
		file3.createNewFile();
		File file4= new File(superCorpus2+"/file4.abc");
		file4.createNewFile();
		File file5= new File(superCorpus2+"/file5.def");
		file5.createNewFile();
		EList<String> fileExtensions=null;
		
		Map<SElementId, URI>  table= null;
		//set 1
		fileExtensions= new BasicEList<String>();
		table= this.getFixture().createCorpusStructure(URI.createFileURI(root.getAbsolutePath()), null, fileExtensions);
		assertEquals(5, table.size());
		
		//set 2
		fileExtensions= new BasicEList<String>();
		fileExtensions.add("-abc");
		table= this.getFixture().createCorpusStructure(URI.createFileURI(root.getAbsolutePath()), null, fileExtensions);
		assertEquals(2, table.size());
		
		
		//set 3
		fileExtensions= new BasicEList<String>();
		fileExtensions.add("abc");
		table= this.getFixture().createCorpusStructure(URI.createFileURI(root.getAbsolutePath()), null, fileExtensions);
		assertEquals(3, table.size());
	}
}

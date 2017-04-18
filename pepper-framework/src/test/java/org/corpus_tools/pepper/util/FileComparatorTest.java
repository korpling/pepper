package org.corpus_tools.pepper.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class FileComparatorTest {
	private static final String TEST_BASE = PepperTestUtil.getTestResources() + "/util/fileComparatorTest/";
	private static final String equalFiles = new File(TEST_BASE + "equalFiles/").getAbsolutePath();
	private static final String unequalFiles = new File(TEST_BASE + "unequalFiles/").getAbsolutePath();
	private static final String equalFolders = new File(TEST_BASE + "equalFolders/").getAbsolutePath();
	private static final String unequalFolders = new File(TEST_BASE + "unequalFolders/").getAbsolutePath();
	private static final String equalXml = new File(TEST_BASE + "equalXml/").getAbsolutePath();
	private static final String unequalXml = new File(TEST_BASE + "unequalXml/").getAbsolutePath();
	private static final String equalSaltXml = new File(TEST_BASE + "equalSaltXml/").getAbsolutePath();
	private static final String unequalSaltXml = new File(TEST_BASE + "unequalSaltXml/").getAbsolutePath();
	private static final String identicalPath = new File(TEST_BASE + "identicalPathes/dummyFile.txt").getAbsolutePath();
	private static final String folderWithFile = new File(TEST_BASE + "fileWithFolder/").getAbsolutePath();
	private static final String first = "/1/";
	private static final String second = "/2/";

	@Test
	public void whenComparingIdenticalPath_thenReturnTrue() {
		final URI file1 = URI.createFileURI(identicalPath);
		assertThat(new FileComparator.Builder(file1).with(file1)).isTrue();
	}

	@Test
	public void whenComparingFileWithFolder_thenReturnFalse() {
		final URI folder = URI.createFileURI(folderWithFile + "/a");
		final URI file = URI.createFileURI(folderWithFile + "/dummyFile.txt");
		assertThat(new FileComparator.Builder(file).with(folder)).isFalse();
		assertThat(new FileComparator.Builder(folder).with(file)).isFalse();
	}

	@Test
	public void whenFoldersAreEqual_thenReturnTrue() {
		final URI file1 = URI.createFileURI(equalFolders + first);
		final URI file2 = URI.createFileURI(equalFolders + second);
		assertThat(new FileComparator.Builder(file1).with(file2)).isTrue();
	}

	@Test
	public void whenFoldersAreUnEqual_thenReturnFalse() {
		final URI file1 = URI.createFileURI(unequalFolders + first);
		final URI file2 = URI.createFileURI(unequalFolders + second);
		assertThat(new FileComparator.Builder(file1).with(file2)).isFalse();
	}

	@Test
	public void whenFilesAreEqual_thenReturnTrue() {
		final URI file1 = URI.createFileURI(equalFiles + first);
		final URI file2 = URI.createFileURI(equalFiles + second);
		assertThat(new FileComparator.Builder(file1).with(file2)).isTrue();
	}

	@Test
	public void whenFilesAreUnEqual_thenReturnFalse() {
		final URI file1 = URI.createFileURI(unequalFiles + first);
		final URI file2 = URI.createFileURI(unequalFiles + second);
		assertThat(new FileComparator.Builder(file1).with(file2)).isFalse();
	}

	@Test
	public void whenXmlFilesAreEqual_thenReturnTrue() {
		final URI file1 = URI.createFileURI(equalXml + first);
		final URI file2 = URI.createFileURI(equalXml + second);
		assertThat(new FileComparator.Builder(file1).with(file2)).isTrue();
	}

	@Test
	public void whenXmlFilesAreUnEqual_thenReturnFalse() {
		final URI file1 = URI.createFileURI(unequalXml + first);
		final URI file2 = URI.createFileURI(unequalXml + second);
		assertThat(new FileComparator.Builder(file1).with(file2)).isFalse();
	}

	@Test
	public void whenSaltXmlAreEqual_thenReturnTrue() {
		final URI file1 = URI.createFileURI(equalSaltXml + first);
		final URI file2 = URI.createFileURI(equalSaltXml + second);
		assertThat(new FileComparator.Builder(file1).with(file2)).isTrue();
	}

	@Test
	public void whenSaltXmlAreUnEqual_thenReturnFalse() {
		final URI file1 = URI.createFileURI(unequalSaltXml + first);
		final URI file2 = URI.createFileURI(unequalSaltXml + second);
		assertThat(new FileComparator.Builder(file1).with(file2)).isFalse();
	}
}

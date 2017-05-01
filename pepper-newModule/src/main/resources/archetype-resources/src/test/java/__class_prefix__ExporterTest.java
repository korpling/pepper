package ${package};

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.testFramework.PepperExporterTest;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;


/**
 * This is a dummy implementation of a JUnit test for testing the
 * {@link ${class_prefix}Exporter} class. Feel free to adapt and enhance this test class
 * for real tests to check the work of your importer. If you are not confirm
 * with JUnit, please have a look at <a
 * href="http://www.vogella.com/tutorials/JUnit/article.html">
 * http://www.vogella.com/tutorials/JUnit/article.html</a>. <br/>
 * Please note, that the test class is derived from {@link PepperExporterTest}.
 * The usage of this class should simplfy your work and allows you to test only
 * your single importer in the Pepper environment.
 */
public class ${class_prefix}ExporterTest extends PepperExporterTest<${class_prefix}Exporter> {
	/**
	 * This method is called by the JUnit environment each time before a test
	 * case starts. So each time a method annotated with @Test is called. This
	 * enables, that each method could run in its own environment being not
	 * influenced by before or after running test cases.
	 */
	@Before
	public void beforeEach() {
		setTestedModule(new ${class_prefix}Exporter());
		addFormatWhichShouldBeSupported("dot", "1.0");
	}

	/**
	 * This is a test to check the correct work of our dummy implementation.
	 * This test is supposed to show the usage of JUnit and to give some
	 * impressions how to check simple things of the created salt model. <br/>
	 * You can create as many test cases as you like, just create further
	 * methods having the annotation "@Test". Note that it is very helpful, to
	 * give them self explaining names and a short JavaDoc explaining their
	 * purpose. This could help very much, when searching for bugs or extending
	 * the tests. <br/>
	 * In our case, we just test, if correct number of corpora and documents was
	 * created, if all corpora have got a meta-annotation and if each
	 * document-structure contains the right number of nodes and relations.
	 */
	@Test
	public void whenExportingSampleToDot_allDotFilesMustExist() {
		// create a sample corpus, the class SampleGenerator provides a bunch of
		// helpful methods to create sample documents and corpora
		getTestedModule().setSaltProject(SampleGenerator.createSaltProject());

		// determine location, to where the corpus should be exported
		getTestedModule().setCorpusDesc(new CorpusDesc().setCorpusPath(URI.createFileURI(PepperTestUtil.createTestTempPath("${class_prefix}Exporter").getAbsolutePath())));

		// starts the Pepper framework and the conversion process
		start();

		URI superCorpus = PepperTestUtil.createTestTempPathAsUri("${class_prefix}Exporter").appendSegment("rootCorpus");
		URI subCorpus1 = superCorpus.appendSegment("subCorpus1");
		URI subCorpus2 = superCorpus.appendSegment("subCorpus2");

		assertThat(createFile(superCorpus).exists()).isTrue();
		assertThat(createFile(subCorpus1).exists()).isTrue();
		assertThat(createFile(subCorpus1.appendSegment("doc1.dot")).exists()).isTrue();
		assertThat(createFile(subCorpus1.appendSegment("doc2.dot")).exists()).isTrue();
		assertThat(createFile(subCorpus2).exists()).isTrue();
		assertThat(createFile(subCorpus2.appendSegment("doc3.dot")).exists()).isTrue();
		assertThat(createFile(subCorpus2.appendSegment("doc4.dot")).exists()).isTrue();
	}

	private File createFile(URI uri) {
		return new File(uri.toFileString());
	}
}

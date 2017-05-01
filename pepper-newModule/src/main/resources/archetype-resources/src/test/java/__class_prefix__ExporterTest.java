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
	public void test_DummyImplementation() {
		// create a sample corpus, the class SampleGenerator provides a bunch of
		// helpful methods to create sample documents and corpora
		getTestedModule().setSaltProject(SampleGenerator.createSaltProject());

		// determine location, to where the corpus should be exported
		getTestedModule().setCorpusDesc(new CorpusDesc().setCorpusPath(URI.createFileURI(PepperTestUtil.createTestTempPath("${class_prefix}Exporter").getAbsolutePath())));

		// starts the Pepper framework and the conversion process
		start();

		File superCorpus = new File(PepperTestUtil.createTestTempPath("${class_prefix}Exporter").getAbsolutePath() + "/rootCorpus");
		assertThat(superCorpus.exists()).isTrue();
		File subCorpus1 = new File(superCorpus.getAbsolutePath() + "/subCorpus1");
		assertThat(subCorpus1.exists()).isTrue();
		File document1 = new File(subCorpus1.getAbsolutePath() + "/doc1.dot");
		assertThat(document1.exists()).isTrue();
		File document2 = new File(subCorpus1.getAbsolutePath() + "/doc2.dot");
		assertThat(document2.exists()).isTrue();
		File subCorpus2 = new File(superCorpus.getAbsolutePath() + "/subCorpus2");
		assertThat(subCorpus1.exists()).isTrue();
		File document3 = new File(subCorpus2.getAbsolutePath() + "/doc3.dot");
		assertThat(document3.exists()).isTrue();
		File document4 = new File(subCorpus2.getAbsolutePath() + "/doc4.dot");
		assertThat(document4.exists()).isTrue();
	}
}

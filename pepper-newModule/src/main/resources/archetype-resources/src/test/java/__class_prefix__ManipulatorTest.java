package ${package};

import static org.assertj.core.api.Assertions.assertThat;

import org.corpus_tools.pepper.testFramework.PepperManipulatorTest;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a dummy implementation of a JUnit test for testing the
 * {@link ${class_prefix}Manipulator} class. Feel free to adapt and enhance this test
 * class for real tests to check the work of your manipulator. If you are not
 * confirm with JUnit, please have a look at <a
 * href="http://www.vogella.com/tutorials/JUnit/article.html">
 * http://www.vogella.com/tutorials/JUnit/article.html</a>. <br/>
 * Please note, that the test class is derived from
 * {@link PepperManipulatorTest}. The usage of this class should simplfy your
 * work and allows you to test only your single manipulator in the Pepper
 * environment.
 */
public class ${class_prefix}ManipulatorTest extends PepperManipulatorTest<${class_prefix}Manipulator> {
	/**
	 * This method is called by the JUnit environment each time before a test
	 * case starts. So each time a method annotated with @Test is called. This
	 * enables, that each method could run in its own environment being not
	 * influenced by before or after running test cases.
	 */
	@Before
	public void beforeEach() {
		setTestedModule(new ${class_prefix}Manipulator());
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
	public void whenManipulatingSampleCorpus_DateMetaAnnotationMustMatch1989_12_17() {
		// create a sample corpus, the class SampleGenerator provides a bunch of
		// helpful methods to create sample documents and corpora
		getTestedModule().setSaltProject(SampleGenerator.createSaltProject());
		// starts the Pepper framework and the conversion process
		start();

		// checks that each corpus contains a date annotation and that its value
		// is 1989-12-17 just to show how tests work, for more tests, please
		// take a look
		// into ${class_prefix}Manipulator
		for (SCorpus corpus : getTestedModule().getSaltProject().getCorpusGraphs().get(0).getCorpora()) {
			assertThat(corpus.getMetaAnnotation("date")).isNotNull();
			assertThat(corpus.getMetaAnnotation("date").getValue()).isEqualTo("1989-12-17");
		}
	}
}

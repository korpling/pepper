package ${package};

import static org.assertj.core.api.Assertions.assertThat;

import org.corpus_tools.pepper.testFramework.PepperImporterTest;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.SaltProject;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a dummy implementation of a JUnit test for testing the
 * {@link ${class_prefix}Importer} class. Feel free to adapt and enhance this test class
 * for real tests to check the work of your importer. If you are not confirm
 * with JUnit, please have a look at <a
 * href="http://www.vogella.com/tutorials/JUnit/article.html">
 * http://www.vogella.com/tutorials/JUnit/article.html</a>. <br/>
 * Please note, that the test class is derived from {@link PepperImporterTest}.
 * The usage of this class should simplify your work and allows you to test only
 * your single importer in the Pepper environment.
 */
public class ${class_prefix}ImporterTest extends PepperImporterTest<${class_prefix}Importer> {
	/**
	 * This method is called by the JUnit environment each time before a test
	 * case starts. So each time a method annotated with @Test is called. This
	 * enables, that each method could run in its own environment being not
	 * influenced by before or after running test cases.
	 */
	@Before
	public void beforeEach() {
		setTestedModule(new ${class_prefix}Importer());
		addFormatWhichShouldBeSupported("sample", "1.0");
	}
	
	/**
	 * This is a test to check the correct work of our dummy implementation.
	 * This test is supposed to show the usage of JUnit and to give some
	 * impressions how to check simple things of the created salt model. <br/>
	 * You can create as many test cases as you like, just create further
	 * methods having the annotation
	 * 
	 * @Test. Note that it is very helpful, to give them self explaining names
	 *        and a short JavaDoc explaining their purpose. This could help very
	 *        much, when searching for bugs or extending the tests. <br/>
	 *        In our case, we just test, if correct number of corpora and
	 *        documents was created, if all corpora have got a meta-annotation
	 *        and if each document-structure contains the right number of nodes
	 *        and relations.
	 */
	@Test
	public void whenDummyCorpusIsImported_allNodesAndRelationsMustExist() {
		// set the path, from where to import the corpus, in our dummy
		// implementation, the location is also just a dummy
		getTestedModule().getCorpusDesc().setCorpusPath(PepperTestUtil.createTestTempPathAsUri("MyImporter"));
		// starts the Pepper framework and the conversion process
		start();

		SaltProject project = getTestedModule().getSaltProject();

		// checks wether the salt project, which is a container for the created
		// salt model exists.
		assertThat(project).isNotNull();
		// checks wether really one corpus-structure was created in the target
		// salt model
		assertThat(project.getCorpusGraphs()).hasSize(1);
		// checks that the corpus-structure contains 3 corpora
		assertThat(project.getCorpusGraphs().get(0).getCorpora()).hasSize(3);
		// checks that the corpus-structure contains 4 documents
		assertThat(project.getCorpusGraphs().get(0).getDocuments()).hasSize(4);

		// checks that each corpus contains a date annotation and that its value
		// is 1989-12-17
		for (SCorpus corpus : project.getCorpusGraphs().get(0).getCorpora()) {
			assertThat(corpus.getMetaAnnotation("date")).isNotNull();
			assertThat(corpus.getMetaAnnotation("date").getValue()).isEqualTo("1989-12-17");
		}

		// checks for each document-structure, that all kinds of nodes and
		// relations are contained
		for (SDocument document : project.getCorpusGraphs().get(0).getDocuments()) {
			SDocumentGraph documentStructure = document.getDocumentGraph();
			// checks that all nodes are contained
			assertThat(documentStructure.getNodes()).hasSize(27);
			// checks that all relations are contained
			assertThat(documentStructure.getRelations()).hasSize(46);
			// checks that all tokens (subclass of nodes) are contained
			assertThat(documentStructure.getTokens()).hasSize(11);
			// checks that all spans (subclass of nodes) are contained
			assertThat(documentStructure.getSpans()).hasSize(3);
			// checks that all structures (subclass of nodes) are contained
			assertThat(documentStructure.getStructures()).hasSize(12);
			// checks that all pointing relations (subclass of relations) are
			// contained
			assertThat(documentStructure.getPointingRelations()).hasSize(1);
		}
	}
}

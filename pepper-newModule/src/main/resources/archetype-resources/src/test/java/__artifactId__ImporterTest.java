package ${package};

import ${package}.${artifactId}Importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.testFramework.PepperImporterTest;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a dummy implementation of a JUnit test for testing the
 * {@link ${artifactId}Importer} class. Feel free to adapt and enhance this test class
 * for real tests to check the work of your importer. If you are not confirm
 * with JUnit, please have a look at <a
 * href="http://www.vogella.com/tutorials/JUnit/article.html">
 * http://www.vogella.com/tutorials/JUnit/article.html</a>. <br/>
 * Please note, that the test class is derived from {@link PepperImporterTest}.
 * The usage of this class should simplfy your work and allows you to test only
 * your single importer in the Pepper environment.
 */
public class ${artifactId}ImporterTest extends PepperImporterTest {
	/**
	 * This method is called by the JUnit environment each time before a test
	 * case starts. So each time a method annotated with @Test is called. This
	 * enables, that each method could run in its own environment being not
	 * influenced by before or after running test cases.
	 */
	@Before
	public void setUp() {
		setFixture(new ${artifactId}Importer());

		// TODO set the formats to be supported by your importer, so that they
		// can be checked
		FormatDesc formatDef = new FormatDesc();
		formatDef.setFormatName("sample");
		formatDef.setFormatVersion("1.0");
		this.supportedFormatsCheck.add(formatDef);
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
	public void test_DummyImplementation() {
		// set the path, from where to import the corpus, in our dummy
		// implementation, the location is also just a dummy
		getFixture().getCorpusDesc().setCorpusPath(getTempURI("${artifactId}Importer"));
		// starts the Pepper framework and the conversion process
		start();

		// checks if the salt project, which is a container for the created salt
		// model exists.
		assertNotNull(getFixture().getSaltProject());
		// checks if really one corpus-structure was created in the target salt
		// model
		assertEquals(1, getFixture().getSaltProject().getCorpusGraphs().size());
		// checks that the corpus-structure contains 3 corpora
		assertEquals(3, getFixture().getSaltProject().getCorpusGraphs().get(0).getCorpora().size());
		// checks that the corpus-structure contains 4 documents
		assertEquals(4, getFixture().getSaltProject().getCorpusGraphs().get(0).getDocuments().size());

		// checks that each corpus contains a date annotation and that its value
		// is 1989-12-17
		for (SCorpus sCorpus : getFixture().getSaltProject().getCorpusGraphs().get(0).getCorpora()) {
			assertNotNull(sCorpus.getMetaAnnotation("date"));
			assertEquals("1989-12-17", sCorpus.getMetaAnnotation("date").getValue());
		}

		// checks for each document-structure, that all kinds of nodes and
		// relations are contained
		for (SDocument sDocument : getFixture().getSaltProject().getCorpusGraphs().get(0).getDocuments()) {
			// checks that all nodes are contained
			assertEquals(27, sDocument.getDocumentGraph().getNodes().size());
			// checks that all relations are contained
			assertEquals(46, sDocument.getDocumentGraph().getRelations().size());
			// checks that all tokens (subclass of nodes) are contained
			assertEquals(11, sDocument.getDocumentGraph().getTokens().size());
			// checks that all spans (subclass of nodes) are contained
			assertEquals(3, sDocument.getDocumentGraph().getSpans().size());
			// checks that all structures (subclass of nodes) are contained
			assertEquals(12, sDocument.getDocumentGraph().getStructures().size());
			// checks that all pointing relations (subclass of relations) are
			// contained
			assertEquals(1, sDocument.getDocumentGraph().getPointingRelations().size());
		}
	}
}

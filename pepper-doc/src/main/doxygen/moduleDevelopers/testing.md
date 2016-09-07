Testing {#testing}
========

Pepper helps you to write test code faster by providing a test suite skeleton (the three classes @ref org.corpus_tools.pepper.testFramework.PepperManipulatorTest, @ref org.corpus_tools.pepper.testFramework.PepperImporterTest and @ref org.corpus_tools.pepper.testFramework.PepperExporterTest). These classes use the JUnit test framework (see: [junit.org](junit.org)) and implement some very basic tests for checking the consistency of a Pepper module. Just benefit from these classes by creating an own test class derived from one of them and your tests will be ran during the Maven build cycle. For getting an immediate feedback, you can also run them directly in your development environment by running a JUnit task. On the one hand the test classes provide tests which can be adapted to your needs and check if your module can be plugged into the Pepper framework (by checking if necessary values are set like the supported format for an im- or exporter). And on the other hand, they provide some helper classes and functions, which can be used when adding further test methods for checking the functionality of your module.

> **Note**
>
> We strongly recommend to add some module specific test methods, to increase the quality of your code and even to make it easier changing things and still having a correctly running module.

To set up the tests for the JUnit framework override the method setUp() as shown in the following snippet:

\code
    @Before
    protected void setUp() throws Exception {
        //1: setting and initializing of class to test
        this.setFixture(new MyImporter());
        //2: adding format information
        this.supportedFormatsCheck.add(new FormatDesc()
            .setFormatName("myFormat")
            .setFormatVersion("1.0"));
    }                
\endcode

Since the JUnit framework can not know about the classes to test, you need to set and initialize it as shown on position 1. In case you are implementing an im- or exporter, you need to set the supported formats and versions in your test case. Pepper will check them automatically, but the test environment needs to know the correct pairs of format name and version. Replace FORMAT\_NAME and FORMAT\_VERSION with your specific ones. You can even add more than one @ref org.corpus_tools.pepper.common.FormatDesc object.

The provided test classes emulate the Pepper environment, so that you can run your entire module and just check the in- or output.

In case that you write an importer, you can create an input file containing a corpus in the format you want to support, run your importer and check its output against a predefined template. The test will return a processed Salt model which can be checked for its nodes, relations and so on. The following snippet shows how to read a document and how to check the returned Salt model:

\code
	public void someTest(){
        getFixture().setCorpusDesc(new CorpusDesc().
            setCorpusPath(URI
                .createFileURI(PATH_TO_SAMPLE_CORPUS)));
        getFixture().getCorpusDesc().getFormatDesc()
            .setFormatName(FORMAT_NAME)
            .setFormatVersion(FORMAT_VERSION);
        
        //...
        
        //create an empty corpus graph, which is filled by your module 
        SCorpusGraph importedSCorpusGraph= SaltFactory.createSCorpusGraph();
        // add the corpus graph to your module
        getFixture().getSaltProject().addCorpusGraph(importedSCorpusGraph);
        
        //run your Pepper module
        start();
        
        //checks that the corpus graph object 
        //is not empty any more
        assertNotNull(getFixture().getSaltProject()
            .getCorpusGraphs());
        //checks that the corpus graph contains 
        //X SCorpus objects
        assertEquals(X, getFixture().getSaltProject()
            .getCorpusGraphs().get(0).getCorpora().size());
    }
\endcode

More samples for testing can be found in the automatically generated test classes when using Pepper's archetype for Maven, see @ref tutorial.

For testing an exporter, you may want to use Salt's sample generator (org.corpus_tools.salt.samples.SampleGenerator). Here you will find a lot of methods creating a sample Salt model with the possibility to just create the layers, you want to use. For more information, take a look into [Salt's api](https://korpling.github.io/salt/doc/classorg_1_1corpus__tools_1_1salt_1_1samples_1_1_sample_generator.html).

The class @ref org.corpus_tools.pepper.testFramework.PepperTestUtil provides some very helpful methods like @ref org.corpus_tools.pepper.testFramework.PepperTestUtil.getSrcResources() to get a reference to the resources folder (./src/main/resources) or the method getTestResources to get a reference to the test resource folder (./src/test/resources).

When you are implementing an importer and an exporter you may want to make sure that no data is lost, you can orchestrate the im- and the exporter and compare the input files with the output files. In that case implementing the classes @ref org.corpus_tools.pepper.testFramework.PepperImporterTest and @ref org.corpus_tools.pepper.testFramework.PepperExporterTest won't do the job, because they assume that you are testing a single module. To load multiple modules in the Pepper test environment, use the method @ref org.corpus_tools.pepper.testFramework.PepperTestUtil.start(Collection\<PepperModule\> fixtures) instead and pass your im- and your exporter.

## Testing self-tests

To test whether the self-test is working or not, you can run it as a JUnit test as follows:

\code
@Test
public void whenSelfTestingModule_thenResultShouldBeTrue() {
	final ModuleFitness fitness = new ModuleFitnessChecker(PepperTestUtil.createDefaultPepper())
			.selfTest(getFixture());
	assertThat(fitness.getFitness(FitnessFeature.HAS_SELFTEST)).isTrue();
	assertThat(fitness.getFitness(FitnessFeature.HAS_PASSED_SELFTEST)).isTrue();
	//when module is an importer, you can also test the next feature
	assertThat(fitness.getFitness(FitnessFeature.IS_IMPORTABLE_SEFTEST_DATA)).isTrue();
	//when module is an importer or a manipulator, you can also test the next feature
	assertThat(fitness.getFitness(FitnessFeature.IS_VALID_SELFTEST_DATA)).isTrue();
}
\endcode
To learn more about the self-test please check \ref selftest
Testing 
========

In every good book about computer programming it is written that testing the software you are developing is a very important issue. Since testing increases the quality of software enormously, we agree to that. Spending time on developing tests may seem wasted, but it will decrease development time in the long run. Therefore we help you to write test code faster by providing a test suite skeleton (the three classes PepperManipulatorTest, PepperImporterTest and PepperExporterTest). These classes use the JUnit test framework (see: [junit.org](junit.org)) and implement some very basic tests for checking the consistency of a Pepper module. Just benefit from these classes by creating an own test class derived from one of the provided ones and your tests will be ran during the Maven build cycle. For getting an immediate feedback, you can also run them directly in your development environment by running a JUnit task. On the one hand the test classes provide tests which can be adopted to your need and check if your module can be plugged into the Pepper framework (by checking if necessary values are set like the supported format for an im- or exporter). And on the other hand, they provide some helper classes and functions, which can be used when adding further test methods for checking the functionality of your module.

> **Note**
>
> We strongly recommend to add some module specific test methods, to increase the quality of your code and even to make it easier changing things and still having a correctly running module.

To set up the tests for the JUnit framework override the method setUp() as shown in the following snippet:

    @Before
    protected void setUp() throws Exception {
        //1: setting and initializing of class to test
        this.setFixture(new SampleImporterTest);
        //2: adding format information
        this.supportedFormatsCheck.add(new FormatDesc()
            .setFormatName("sample")
            .setFormatVersion("1.0"));
    }                

Since the JUnit framework can not know about the classes to test, you need to set and initialize it as shown on position 1. In case you are implementing an im- or exporter, you need to set the supported formats and versions in your test case. Pepper will check them automatically, but the test environment needs to know the correct pairs of format name and version. Replace FORMAT\_NAME and FORMAT\_VERSION with your specific ones. You can even add more than one `FormatDefinition` object.

The provided test classes emulate the Pepper environment, so that you can run your entire module and just check the in- or output.

In case that you write an importer, you can create an input file containing a corpus in the format you want to support, run your importer and check its output against a defined template. The test will return a processed Salt model which can be checked for its nodes, edges and so on. The following snippet shows how to read a document and how to check the returned Salt model:

    public void testSomeTest(){
        getFixture().setCorpusDesc(new CorpusDesc().
            setCorpusPath(URI
                .createFileURI(PATH_TO_SAMPLE_CORPUS)));
        getFixture().getCorpusDesc().getFormatDesc()
            .setFormatName(FORMAT_NAME)
            .setFormatVersion(FORMAT_VERSION);
        
        //...
        
        //create an empty corpus graph, which is filled by your module 
        SCorpusGraph importedSCorpusGraph= SaltFactory
                .eINSTANCE.createSCorpusGraph();
        // add the corpus graph to your module
        this.getFixture().getSaltProject().getSCorpusGraphs()
                .add(importedSCorpusGraph);
        
        //run your Pepper module
        this.start();
        
        //checks that the corpus graph object 
        //is not empty any more
        assertNotNull(getFixture().getSaltProject()
            .getSCorpusGraphs());
        //checks that the corpus graph contains 
        //X SCorpus objects
        assertEquals(X, getFixture().getSaltProject()
            .getSCorpusGraphs().getSCorpora().size());
    }

More samples for tests can be found in the sample project in the classes `SampleImporterTest`, `SampleManipulatorTest` and `SampleExporterTest`.

For testing an exporter, you may want to use Salt's sample generator (de.hu\_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator). Here you will find a lot of methods creating a sample Salt model with the possibility to just create the layers, you want to use. For more information, take a look into the Salt quick user's guide (see [u.hu-berlin.de/saltnpepper](u.hu-berlin.de/saltnpepper)).

The class de.hu\_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperTestUtil provides some very helpful methods like getSrcResources() to get a reference to the resources folder (.../src/main/resources) or the method getTestResources to get a reference to the test resource folder (.../src/test/resources).

When you are implementing an importer and an exporter you may want to make sure that no data is lost, you can orchestrate the im- and the exporter and compare the input files with the output files. In that case implementing the classes PepperImporterTest and PepperExporterTest want do the job, because they assume that you are testing a single module. To load multiple modules in the Pepper test environment, use the method PepperUtil.start(Collection\<PepperModule\> fixtures) instead and pass your im- and your exporter.

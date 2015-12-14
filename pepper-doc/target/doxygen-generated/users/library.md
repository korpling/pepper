Pepper as a library {#library}
======

With the Pepper library, we provide a programmatic access to the Pepper framework, including the configuration of a conversion workflow, the start of a conversion and getting information about the registered Pepper modules. Since Pepper is based on a plugin structure called OSGi (see: http://www.osgi.org/), each Pepper module is plugged into the framework separately, whether running Pepper as CLI, or running it as a library. The Pepper framework consists of two necessary components: a jar file, which can be included in your software project via maven, and a plugin folder containing all Pepper modules. The following excerpt shows the necessary maven coordinates.

\code
<dependency>
   <artifactId>pepper-lib</artifactId>
   <groupId>org.corpus-tools</groupId>
   <version>VERSION</version>
</dependency>
\endcode 

Please replace the placeholder VERSION with the version you want to use. When Pepper is included in your project, you need to get all necessary plugins and modules, therefore:
<ol>
<li>Download a Pepper release of your choice from http://corpus-tools.org/pepper/</li>
<li>Unzip the downloaded zip file</li>
<li>Copy the contained folder plugins to a folder of your choice, let's call it PLUGINS_HOME.</li>
</ol>
Now you can start coding. Here is a template, showing how to initialize a Pepper object.

\code
    	PepperStarterConfiguration pepperConf = new PepperStarterConfiguration();
		pepperConf.setProperty(PepperStarterConfiguration.PROP_PLUGIN_PATH, PATH_TO_PLUGINS);
		PepperConnector pepper = new PepperOSGiConnector();
		pepper.setConfiguration(pepperConf);
		pepper.init();
\endcode  

Let's have some code explanations with that:

In line 1, we initialize a configuration object to configure the Pepper framework before starting it. Line 2 sets the plugin folder. Please replace the placeholder "PLUGIN_HOME" with the real location. The configuration provides some more adaptation possibilities, just have a look at the JavaDoc or the class itself.

In line 3, we initialize the proper Pepper object symbolising the Pepper framework. In the future, there may be several connectors to access the framework, e.g. to access a Pepper instance running on a remote server. But currently there is just an OSGi connector, which starts a separate OSGi environment (Pepper uses the OSGi environment Equinox, see http://www.eclipse.org/equinox/).

The last line of code passes the configuration to the Pepper object. Pepper is configured now and we are ready to use it. Before we start a conversion workflow, we show how to query the registered modules. The following snippet prints all information about a module to standard out.

\code
    for (PepperModuleDesc moduleDesc: pepper.getRegisteredModules()){
        System.out.println(moduleDesc.getName());
        System.out.println(moduleDesc.getVersion());
        System.out.println(moduleDesc.getDesc());
        System.out.println(moduleDesc.getModuleType());
        System.out.println(moduleDesc.getSupplierContact());
        System.out.println(moduleDesc.getSupportedFormats());
    }
\endcode  

Next, we show how to create a single workflow in Pepper and how to run it. In Pepper, a workflow is called a job and is represented by the class PepperJob. A job consists of several steps. A step can handle an importer, a manipulator or an exporter. A job can contain 1 to n importers, 0 to n manipulators and 1 to n exporters. When using an importer or an exporter, we need to describe the corpus to be im- and exported. The following snippet shows the creation of a corpus description, containing the location of the corpus and a description of the format of the corpus.

\code
    CorpusDesc corpusExport= new CorpusDesc();
    corpusExport.setCorpusPath(URI.createFileURI("CORPUS_PATH"));
    corpusExport.getFormatDesc().setFormatName("NAME_OF_FORMAT");
    corpusExport.setFormatVersion("VERSION_OF_FORMAT");
    StepDesc stepImport= new StepDesc();
    stepImport.setProps(new Properties());
    stepImport.setCorpusDesc(corpusExport);
    CorpusDesc corpusImport= new CorpusDesc();
    corpusImport.setCorpusPath(URI.createFileURI("CORPUS_PATH"));
    corpusImport.getFormatDesc().setFormatName("NAME_OF_FORMAT")
    corpusImport.setFormatVersion("VERSION_OF_FORMAT");
    StepDesc stepExport= new StepDesc();
    stepExport.setProps(new Properties());
    stepExport.setCorpusDesc(corpusImport);
    String jobId= pepper.createJob();
    PepperJob job= pepper.getJob(jobId);
    job.addStepDesc(stepImport);
    job.addStepDesc(stepExport);
    job.convert(); 
\endcode

Here we create two corpora (line 1-4 and line 10-13) and two steps (line 6-8 and 15-17), one for the import and one for the export. When creating a step, you can also pass some properties for customization. For detailed description of which properties are available corresponding to a specific module, please take a look at the documentation of the Pepper module. After creating the steps, we need to add them to the job (line 21-22). So the last thing to do is to start the job with invoking the method 'convert()' (line 24). Another way of converting a job is converting a predefined workflow file. The following snippet shows how to do this.

\code
    String jobId= pepper.createJob();
    PepperJob pepperJob= pepper.getJob(jobId);
    pepperJob.load("URI_OF_WORKFLOW_FILE");
    pepperJob.convert();
\endcode  

That's it! Now you know how to use the basic functionalities of the Pepper library. We hope you will be happy with it.
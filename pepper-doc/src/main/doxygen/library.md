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
	    CorpusDesc corpusExport= new CorpusDesc().setCorpusPath(URI.createFileURI("CORPUS_PATH"));
	    corpusExport.getFormatDesc().setFormatName("NAME_OF_FORMAT");
	    corpusExport.getFormatDesc().setFormatVersion("VERSION_OF_FORMAT");
	    
	    CorpusDesc corpusImport= new CorpusDesc().setCorpusPath(URI.createFileURI("CORPUS_PATH"));
	    corpusImport.getFormatDesc().setFormatName("NAME_OF_FORMAT");
	    corpusImport.getFormatDesc().setFormatVersion("VERSION_OF_FORMAT");

	    String jobId= pepper.createJob();
	    PepperJob job= pepper.getJob(jobId);
	    job.addStepDesc(new StepDesc().setProps(new Properties()).setCorpusDesc(corpusImport));
	    job.addStepDesc(new StepDesc().setProps(new Properties()).setCorpusDesc(corpusExport));
	    
	    //start conversion
	    job.convert(); 
\endcode

Here we create two corpora (line 1-4 and line 10-13) and two steps (line 6-8 and 15-17), one for the import and one for the export. When creating a step, you can also pass some properties for customization. For detailed description of which properties are available corresponding to a specific module, please take a look at the documentation of the Pepper module. After creating the steps, we need to add them to the job (line 21-22). So the last thing to do is to start the job with invoking the method 'convert()' (line 24). Another way of converting a job is converting a predefined workflow file. The following snippet shows how to do this.

\code
    String jobId= pepper.createJob();
    PepperJob pepperJob= pepper.getJob(jobId);
    pepperJob.load("URI_OF_WORKFLOW_FILE");
    pepperJob.convert();
\endcode  


Import data
===

With Pepper you can just import data into a Salt model for further processing in your application. Therefore you need to define an import step and start the conversion via <b><i>convertFrom()</i></b> instead of <i>convert()</i>. 

\code
	job.addStepDesc(new StepDesc().setName(IMPORTER_NAME).setCorpusDesc(new CorpusDesc().setCorpusPath(URI.createFileURI(PATH_TO_CORPUS))).setModuleType(MODULE_TYPE.IMPORTER));

	job.convertFrom();
\endcode

Afterwards, you can access the Salt model and work with it. 
\code
	job.getSaltProject();
\endcode

<b> Attention: If your main application is not part of the OSGi environment, you can run into class loader exceptions when accessing Pepper. To avoid such exceptions, read @ref bridgeOSGi. </b>


Export data
===

With Pepper you can create a Salt model in your main application and export it to any supported format. Therefore you need to define an export step and start the conversion via <b><i>convertTo()</i></b> instead of <i>convert()</i>. Define your model via:
\code
	job.getSaltProject();
\endcode

Afterwards, you can export it with:

\code
	job.addStepDesc(new StepDesc().setName(EXPORTER_NAME).setCorpusDesc(new CorpusDesc().setCorpusPath(URI.createFileURI(PATH_TO_CORPUS))).setModuleType(MODULE_TYPE.EXPORTER));

	job.convertFrom();
\endcode

<b>Attention: If your main application is not part of the OSGi environment, you can run into class loader exceptions when accessing Pepper. To avoid such exceptions, read @ref bridgeOSGi. </b>

Bridging OSGi {#bridgeOSGi}
===

Since Pepper is running in an OSGi container, it uses a different class loader than your main application. When you now try to access Salt model elements, it can happen that you get a class loader exception. To avoid that, Pepper offers you a mechanism to bridge objects from your main application to OSGi and back. Therefore it is necessary to register all concerning packages before Pepper is initialized. The following snippet shows this mechanism:

\code
		PepperOSGiConnector pepper = new PepperOSGiConnector();
		pepper.addSharedPackage("org.corpus_tools.salt", "3");
		pepper.addSharedPackage("org.corpus_tools.salt.common", "3");
		pepper.addSharedPackage("org.corpus_tools.salt.core", "3");
		pepper.addSharedPackage("org.corpus_tools.salt.graph", "3");
		pepper.addSharedPackage("org.corpus_tools.salt.util", "3");		
		pepper.init();
\endcode
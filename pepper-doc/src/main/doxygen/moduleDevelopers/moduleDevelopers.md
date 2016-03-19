Pepper module developers {#moduleDevelopers}
============================

This tutorial explains how to create a module for the linguistic conversion platform Pepper (http://corpus-tools.org/pepper). Pepper bases on Salt a meta model for linguistic data. Both technologies are under Apache License 2.0 and are written in Java. The tutorial is along a sample project, which contains simple implementations of an importer, an exporter and a manipulator. You can use them as a template and a  kind of an inspiration for your own project.

Prerequisits
===
Before proceeding with this tutorial, you should have an understanding of Java (http://openjdk.java.net/ or http://www.oracle.com/technetwork/java), a basic knowledge about Maven (https://maven.apache.org/), an impression of what Salt (http://corpus-tools.org/salt) is and how it works and last but not least a basic understanding of how Pepper processess linguistic data (http://corpus-tools.org/pepper/userGuide.html). 

Make sure to have a running instance of Java's JDK at least version 1.7  and a running Maven instance of version 3.x. 

For testing your Java instance open a command line and type in:

\code
java -version
\endcode 

For testing your Maven instance open a command line and type in:
\code
mvn -version
\endcode 

Set up
===

**Step 1**  Create your module via maven
\code
mvn archetype:generate \
    -DarchetypeGroupId=org.corpus-tools \
    -DarchetypeArtifactId=pepper-newModule \
    -DarchetypeVersion=${project.version} 
\endcode
**Step 2**  Follow the instructions and enter 
- the **groupId** which will identify your project uniquely across all projects, 
- the **artifactId** an identifier for your project (it is also used for class names), 
- the **version** the module's version, 
- the **package name**, which is by default a combination of groupId and artifactId
- **your name** which is used as developers name in Javadoc and pom.xml and your 
- **companies name** which is the license owner and set to the organisation in pom.xml   

**Step 3** Go to MY_MODULE_HOME and run 
\code
mvn clean install
\endcode

**Step 4** Download the latest stable version of Pepper plattform from http://corpus-tools.org/pepper/#download

**Step 5** Unzip Pepper to PEPPER_HOME

**Step 6** Open Pepper's configuration file under PEPPER_HOME/conf/pepper.properties

**Step 7** Add the target folder of your module to Pepper's dropin pathes. Search for 'pepper.dropin.paths' and replace it with:
\code
pepper.dropin.paths= MY_MODULE_HOME/target
\endcode

Now the module is ready to be started in Pepper. To check whether your module is registered in Pepper check if it is listed there.

**Step 8** Go to PEPPER_HOME and run 
\code
	pepperStart.bat	  (for Windows)
	./pepperStart.sh  (for Linux)
\endcode
**Step 9** Type in 'list'
\code	
	pepper>list
\endcode
  Pepper will print a list of all registered modules, search for your importer, manipulator and exporter.
**Step 10** To get more information on one of the modules type in 'list' followed by the module's name
\code
pepper> list MyImporter
\endcode 

Now you can run the three modules in Pepper. You can use them as part of your conversion workflow.

To develop a Pepper module you only need a text editor and a command line, but it probably is more comfortable to use an IDE like Eclipse (https://eclipse.org/) or Netbeans (https://netbeans.org/) etc. You can easily develop a Pepper module with Eclipse \subpage useEclipse.


Functionality of your project
===
Your new project now contains three modules: an importer, a manipulator and an exporter. 

The importer just imports a static corpus containing one super-corpus, two sub-corpora and four documents. Here you can see how to create a corpus-structure and a document-structure. Salt differentiates between the corpus-structure and the document-structure. The document-structure contains the primary data (data sources) and the linguistic annotations. A bunch of such information is grouped to a document (`SDocument` in Salt). The corpus-structure now is a grouping mechanism to group several documents to a corpus or sub-corpus (`SCorpus` in Salt). 

The manipulator, traverses over the document-structure and prints out some information about it, like the frequencies of annotations, the number of nodes and edges and so on. What you can see here is how to access data and traverse the graph structure in the Salt framework. 

The exporter exports the corpus into a format named DOT, which can be used for visualization of the corpus. The main logic of that mapping is not contained in the exporter itself, since such a component is already part of the Salt framework. The exporter should just give an impression of how to deal with the creation of the specific output files.


Start coding
============

There is a specific interface for each type of module in Pepper. An importer must implement the interface @ref org.corpus_tools.pepper.modules.PepperImporter, a manipulator must implement @ref org.corpus_tools.pepper.modules.PepperManipulator and an exporter must implement @ref org.corpus_tools.pepper.modules.PepperExporter. There is also a specific class to each module type implementing the corresponding interface:
* @ref org.corpus_tools.pepper.impl.PepperImporterImpl,
* @ref org.corpus_tools.pepper.impl.PepperManipulatorImpl and
* @ref org.corpus_tools.pepper.impl.PepperExporterImpl.

Each module in your project implements one of the interfaces and extends one of these classes.
The importer, manipulator and exporter classes implement the supertype @ref org.corpus_tools.pepper.PepperModule and extend the class @ref org.corpus_tools.pepper.impl.PepperModuleImpl. ![class diagram showing the inheritance of Pepper module types](./moduleDevelopers/images/pepperModule_classDiagram.png "Image title")

A mapping process can be relatively time consuming, to increase the speed of mapping an entire corpus, if we are able to process mapping tasks simultaneously. Therefore we added mechanisms to run the process multi-threaded. Unfortunately in Java multi-threading is not that trivial and the easiest way to do it is to separate each thread in an own class. Therefore there is another class, the @ref org.corpus_tools.pepper.modules.PepperMapper. This class does the main mapping task. As you will find in your project, each module contains a nested class extending @ref org.corpus_tools.pepper.impl.PepperMapperImpl. For each corpus and document an instance of that class is initialized which handles the mapping. Therefore we have 1:n relationship between @ref org.corpus_tools.pepper.modules.PepperModule and @ref org.corpus_tools.pepper.modules.PepperMapper.

The class @ref org.corpus_tools.pepper.modules.ModuleController is a mediator between the concrete Pepper module and the Pepper framework. It initializes, starts and ends the modules processing.  

![sequence diagram of communication between Pepper and Pepper module](./moduleDevelopers/images/pepper_workflow.png)

A mapping can be divided into several acts. Some of these acts correspond to the module's type, for instance a manipulator does not need to im- or export the corpus structure. Some of the acts are mandatory, some are recommended and some are optional, depending on your usecase. The following sections will explain the acts in detail.

* \subpage mapping [mandatory]
* \subpage analyze [recommended, if module is an importer]
* \subpage corpusStructure  [mandatory, if module is an im- or exporter]
* \subpage customization [recommended]
* \subpage feedback  [recommended]
* \subpage testing [recommended]
* \subpage cleanUp [optional]


Bundle the module
====

Pepper modules use to have a fixed structure containing the Java code and some resources, which may be needed for running the module. Such resources could be some XSLT files in case the conversion process makes use of an XSLT conversion. A module normally is packaed in a zip file having the following structure:

\code
company.myModule.zip
 |--company.myModule/
 |  |--LICENSE
 |  |--NOTICE
 |  |--...
 |--company.myModule.jar
\endcode

To create this structure, you can run Maven assembly:

\code
mvn clean install assembly:single
\endcode
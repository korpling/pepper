Pepper module developers {#moduleDevelopers}
============================

This guide aims to help you to develop your own Pepper module.
The following tutorial helps you to create the skeleton of your module and to generate a sample implementation in round about 10 minutes. 

* \subpage tutorial

Pepper module's architecture 
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

* \subpage init [mandatory]
* \subpage mapping [mandatory]
* \subpage analyze [recommended, if module is an importer]
* \subpage corpusStructure  [mandatory, if module is an im- or exporter]
* \subpage customization [recommended]
* \subpage feedback  [recommended]
* \subpage testing [recommended]
* \subpage cleanUp [optional]

A helper to extract the tags and attributes from an xml file is described here.

* \subpage helper

Bundle the module
====

Pepper modules use to have a fixed structure containing the Java code and some resources, which may be needed for running the module. Such resources could be some XSLT files in case the conversion process makes use of an XSLT conversion. A module normally is packed into a zip file having the following structure:

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
Run your first Pepper module
============================

This tutorial explains how to create a module for the linguistic conversion platform Pepper (http://corpus-tools.org/pepper). Pepper bases on Salt a meta model for linguistic data. Both technologies are under Apache License 2.0 and are written in Java. The tutorial is along a sample project, which contains simple implementations of an importer, an exporter and a manipulator. You can use them as a template and a  kind of an inspiration for your own project.

Prerequisits
===
Before proceeding with this tutorial, you should have an understanding of Java (http://openjdk.java.net/ or http://www.oracle.com/technetwork/java), a basic knowledge about Maven (https://maven.apache.org/) and last but not least an impression of what Salt (http://corpus-tools.org/salt) is and how it works. 

Make sure to have a running instance of Java's JDK at least version 1.7  and a running Maven instance of version 3.x . At least you need to have an SVN client to download the sample.

For testing your Java instance open a command line and type in:

\code
java -version
\endcode 

For testing your Maven instance open a command line and type in:
\code
mvn -version
\endcode 

For testing your SVN instance open a command line and type in:
\code
svn --version
\endcode 

Set up
===

-# download the Pepper sample module via svn to SAMPLE_HOME
\code
svn export https://github.com/korpling/pepper/trunk/pepper-sampleModules 
\endcode
-# go to SAMPLE_HOME and run 
\code
mvn clean install
\endcode
-# download the latest stable version of Pepper plattform from http://corpus-tools.org/pepper/#download
-# unzip Pepper to PEPPER_HOME
-# Open Pepper's configuration file under PEPPER_HOME/conf/pepper.properties
-# add the target folder of the sample module to Pepper's dropin pathes. Search for 'pepper.dropin.paths' and replace it with:
\code
pepper.dropin.paths= SAMPLE_HOME/target
\endcode

Now the module is ready to be started in Pepper. To check whether your module is registered in Pepper check if it is listed there.

-# Go to PEPPER_HOME and run 
\code
	pepperStart.bat	  (for Windows)
	./pepperStart.sh  (for Linux)
\endcode
-# type in 'list'
\code	
	pepper>list
\endcode
  Pepper will print a list of all registered modules, search for 'SampleImporter', 'SampleManipulator' and 'SampleExporter'.
-# to get more information on one of the modules type in 'list' followed by the module's name
\code
pepper> list SampleImporter
\endcode 

Now you can run the three modules in Pepper. You can use them as part of your conversion workflow.

To develop a Pepper module you only need a text editor and a command line, but it probably is more comfortable to use an IDE like Eclipse (https://eclipse.org/) or Netbeans (https://netbeans.org/) etc. You can easily develop a Pepper module with Eclipse @ref .


The sample
===
Your new Pepper module project now contains three modules, one of each kind: an importer, a manipulator and an exporter. 

The importer just imports a static corpus containing one super-corpus, two sub-corpora and four documents. Here you can see how to create a corpus-structure and a document-structure. Salt differentiates between the corpus-structure and the document-structure. The document-structure contains the primary data (data sources) and the linguistic annotations. A bunch of such information is grouped to a document (`SDocument` in Salt). The corpus-structure now is a grouping mechanism to group several documents to a corpus or sub-corpus (`SCorpus` in Salt). 

The manipulator, traverses over the document-structure and prints out some information about it, like the frequencies of annotations, the number of nodes and edges and so on. What you can see here is how to access data and traverse the graph structure in the Salt framework. 

The exporter exports the corpus into a format named DOT, which can be used for visualization of the corpus. The main logic of that mapping is not contained in the exporter itself, since such a component is already part of the Salt framework. The exporter should just give an impression of how to deal with the creation of the specific output files.


Just code it
============

With Pepper we tried to avoid as much complexity as possible without reducing the functionality. We want to enable you to concentrate on the main issues, which are the mapping of objects. But still there are some things you need to know about the framework. Therefore we here introduce some aspects of the Pepper framework and its interaction with a Pepper module. Reducing the complexity is not always possible, but we tried. To manage this trade-off, we followed the approach convention over configuration. That means, we made some assumptions, which apply to many mapping tasks. This makes implementing very simple if the default case matches. But if not, you always have the possibility to adapt the module to your needs. The adaptable default behavior mostly is realized by class derivation and call-back methods, which always can be overridden.

Pepper differentiates three sorts of modules: the importer, the manipulator and the exporter. An importer maps a corpus given in format *X* to a Salt model. A manipulator maps a Salt model to another Salt model, in terms of changing it or just retrievs some information. An exporter maps a Salt model to a corpus in format *Y*. All three modules `PepperImporter`, `PepperManipulator` and `PepperExporter` inherit the super type `PepperModule`. No matter of what kind of module you are going to implement, it must inherit one of the three named types. ?shows this relation. Now you might ask, what are the classes `ModuleController` and `PepperMapper` good for. The class `ModuleController` acts as a mediator between the concrete Pepper module and the Pepper framework. It initializes, starts and ends the modules processing. To explain the `PepperMapper` class, we want to give a short motivation: Since a mapping process can be relatively time consuming, we could increase the speed of mapping an entire corpus, if we are able to process mapping tasks simultaneously. Therefore we added mechanisms to run the process multi-threaded[1]. Unfortunately in Java multi-threading is not that trivial and the easiest way to do it is to separate each thread in an own class. This is where `PepperMapper` comes into game. A `PepperModule` object is a singleton instance for one step in the Pepper workflow and divides and distributes the tasks of mapping corpora and documents to several `PepperModule` objects.

A mapping task in the Pepper workflow is not a monolithic blog. It consists of several smaller conceptual aspects. Not each of the named aspects is essential, and some are belonging on the type of module you are implementing. Some are optional, some are recommended and some are mandatory to implement. For a better understanding, the rest of this section is structured according these aspects instead of the order in the code.

-   mapping document-structure and corpus-structure [mandatory]

-   analyzing an unknown corpus [recommended, if module is an importer]

-   im- and export corpus-structure [mandatory, if module is an im- or exporter]

-   customizing the mapping [recommended]

-   monitoring the progress, logging and error handling [recommended]

-   prepare and clean-up [optional]

The main aspect surely is the mapping of the document-structure and corpus-structure. This aspect deals with the creation, manipulation and export of Salt models. In this sense, the others are more sideaspects and not essential for the mapping itself, but important for the workflow.

Some of the aspects are spread over several classes (PepperModule and PepperMapper) and methods. The single paragraphs mention which methods are involved. To get an overview of the entire method stack, figure ? illustrates the communication between the framework, the `PepperModule` and `PepperMapper` class.
 

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



old
=====
In this tutorial we show how to create your own Pepper module along a sample project in a few simple steps. The sample project contains
sample implementations of an importer, an exporter and a manipulator, which could be used as  a  template  and  a  kind  of  an  inspiration for your own project. To run this tutorial you need to install an SVN client (for Windows see https://tortoisesvn.net/) and Maven (see https://maven.apache.org/). 
Follow the next X steps to get the module started.

-# download the Pepper sample module via svn to SAMPLE_HOME
\code
svn export https://github.com/korpling/pepper/trunk/pepper-sampleModules 
\endcode
-# go to SAMPLE_HOME and run 
\code
mvn clean install
\endcode
-# download the latest stable version of Pepper plattform from http://corpus-tools.org/pepper/#download
-# unzip Pepper to PEPPER_HOME
-# Open Pepper's configuration file under PEPPER_HOME/conf/pepper.properties
-# add the target folder of the sample module to Pepper's dropin pathes. Search for 'pepper.dropin.paths' and replace it with:
\code
pepper.dropin.paths= SAMPLE_HOME/target
\endcode

Now the module is ready to be started in Pepper. To check whether your module is registered in Pepper check if it is listed there.

-# Go to PEPPER_HOME and run 
\code
	pepperStart.bat	  (for Windows)
	./pepperStart.sh  (for Linux)
\endcode
-# type in 'list'
\code	
	pepper>list
\endcode
  Pepper will print a list of all registered modules, search for 'SampleImporter', 'SampleManipulator' and 'SampleExporter'.
-# to get more information on one of the modules type in 'list' followed by the module's name
\code
pepper> list SampleImporter
\endcode 

Adaptieren

2.open pom.xml
2.1 change the groupId
2.2 change the artifactId to the name of your module


Eclipse
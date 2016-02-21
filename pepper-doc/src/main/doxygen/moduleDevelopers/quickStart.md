Run your first Pepper module
============================

This tutorial show how o create your own Pepper module along a sample project. The sample project contains
sample implementations of an importer, an exporter and a manipulator, which could be used as  a  template  and  a  kind  of  an  inspiration  for  your own project. The importer imports a static corpus containing one super-corpus, two sub-corpora and four documents. Each document contains a primary text, a tokenization, part-of-speech annotations,information structure annotations, syntactic annotations and anaphoric relations. The manipulator,
traverses over the document-structure and prints out some information about it, like the frequencies
of annotations, the number of nodes and edges and so on. The exporter exports the corpus into a format
named DOT (see: http://www.graphviz.org/), which can be used to visualize the corpus. 
The sample module gives you a base to start from to create your own module. Follow the next X steps to get it started.


1. download the Pepper sample module via svn 
```
svn export https://github.com/korpling/pepper/trunk/pepper-sampleModules 
```
(see Tortoise for Windows, package manager for linux) to SAMPLE_HOME
	1.1 go to SAMPLE_HOME and run mvn clean install
	Pepper and its modules makeuse of the lifecycle management and build system Maven (see: http://maven.apache.org). Maven allows to easily build a Pepper module and to resolve it's dependencies to other libraries. This enables to build the project from a project description file (see pom.xml) and allows to rebuild the project on other systems.

2. download the latest stable version of Pepper plattform from http://corpus-tools.org/pepper/#download
	2.1 unzip Pepper to PEPPER_HOME
* Open Pepper's configuration file under PEPEPR_HOME/conf/pepper.properties
* add the target folder of the sample module to Pepper's dropin pathes. Search for 'pepper.dropin.paths' and replace it with:
	pepper.dropin.paths= SAMPLE_HOME/target

Now the module is ready to be started in Pepper. To check whether your module is registered in Pepper check if it is listed there.

* Go to PEPPER_HOME and run 
	pepperStart.bat	  (for Windows)
	./pepperStart.sh  (for Linux)

* type in 'list'
	pepper>list
  Pepper will print a list of all registered modules, search for 'SampleImporter', 'SampleManipulator' and 'SampleExporter'.
* to get more information one one of the modules type in 'list' followed by the module's name
	pepper> list SampleImporter

Adaptieren

2.open pom.xml
2.1 change the groupId
2.2 change the artifactId to the name of your module


Eclipse
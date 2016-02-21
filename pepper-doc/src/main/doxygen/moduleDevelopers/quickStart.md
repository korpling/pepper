Run your first Pepper module
============================

In this tutorial we show how o create your own Pepper module along a sample project in a few simple steps. The sample project contains
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
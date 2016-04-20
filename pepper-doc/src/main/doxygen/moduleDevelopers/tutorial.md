Tutorial: Getting started in 10 minutes {#tutorial}
==

This tutorial explains how to set up a skeleton for your own module for the linguistic conversion platform Pepper (http://corpus-tools.org/pepper). Pepper bases on Salt (http://corpus-tools.org/salt) a meta model for linguistic data. Both technologies are under Apache License 2.0 and are written in Java. The tutorial shows how to create a Maven project which contains the basic classes for the three types of Pepper modules: importer, manipulator and exporter. Furthermore each module comes with a dummy implementation which can be used as a template for implementing your mapping logic. Feel free to use the templates as a lookup or an inspiration for your own module. 

## Prerequisites

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

Finally, you have to have the current version of Pepper checked out from the [git repository at GitHub](https://github.com/korpling/pepper) and installed locally. To do this, you can either `clone` it directly, as follows below, or create a fork (you need an account on [github.com](http://github.com)) so that you work on your own Pepper repository and can also contribute changes to the original Pepper by issuing [pull requests](https://help.github.com/articles/using-pull-requests/). If you decide to fork you need to replace the git URL in the below example with that of your own for repository of course.

Go to the directory you want to clone the Pepper source code into -- the cloning process will create a new subdirectory called *pepper*. From the command line, run
\code
git clone https://github.com/korpling/pepper.git
cd pepper
mvn clean install
\endcode

## Set up


**Step 1** Go to the folder in which you want to create a new folder containing your module (let's call the latter _MY_MODULE_HOME_).

**Step 2**  Create your module with Maven. *Note* that the below is a one-line command (`\`s signify a line break, used for readability).
\code
mvn archetype:generate \
    -DarchetypeGroupId=org.corpus-tools \
    -DarchetypeArtifactId=pepper-newModule \
    -DarchetypeVersion=${project.version} 
\endcode
**Step 3**  Follow the instructions and enter 
- the **groupId** which will identify your project uniquely across all projects, 
- the **artifactId** an identifier for your project, 
- the **package**, which is by default a combination of groupId and artifactId. ***Note:*** If your *groupId* or *artifactId* contain slashes (`-`), you need to remove them or replace them with, e.g., underscores (`_`), otherwise the build will break.
- the **class prefix**, which is used as a prefix and name for your modules for instance the prefix 'My' will result in an importer named 'MyImporter' 
- **organisation** which is the license owner and set to the organisation in pom.xml 
- **your_name** which is used as developers name in Javadoc and pom.xml and your 
- **Y**: Confirm the above settings by entering `y`, alternatively enter `n` to re-start the process, or press `CTRL+C` to abort the generation altogether.
  

**Step 4** Go to _MY_MODULE_HOME_ and run 
\code
mvn clean install
\endcode

**Step 5** Download the latest stable version of Pepper plattform from http://corpus-tools.org/pepper/#download

**Step 6** Unzip Pepper to _PEPPER_HOME_

**Step 7** Open Pepper's configuration file under PEPPER_HOME/conf/pepper.properties

**Step 8** Add the target folder of your module to Pepper's dropin pathes. Search for 'pepper.dropin.paths' and replace it with:
\code
pepper.dropin.paths= MY_MODULE_HOME/target
\endcode

Now the module is ready to be started in Pepper. To check whether your module is registered in Pepper check if it is listed there.

**Step 9** Go to _PEPPER_HOME_ and run 
\code
	pepper.bat	  (for Windows)
	./pepper.sh  (for Linux)
\endcode

**Step 10** Type in 'list'
\code	
	pepper>list
\endcode
  Pepper will print a list of all registered modules, search for your importer, manipulator and exporter.
**Step 10** To get more information on one of the modules type in 'list' followed by the module's name
\code
pepper> list MyImporter
\endcode 

Now you can run the three modules in Pepper. You can use them as part of your conversion workflow.

To develop a Pepper module you only need a text editor and a command line, but it probably is more comfortable to use an IDE like Eclipse (https://eclipse.org/) or Netbeans (https://netbeans.org/) etc. You can easily develop a Pepper module with Eclipse @ref useEclipse.


## What does my own module do?

Your new project now contains three modules: an importer, a manipulator and an exporter. 

The **importer** just imports a static corpus containing one super-corpus, two sub-corpora and four documents. Here you can see how to create a corpus structure and a document structure. Salt differentiates between the corpus structure and the document structure. The document structure contains the primary data (data sources) and the linguistic annotations. A bunch of such information is grouped to a document (`SDocument` in Salt). The corpus structure now is a grouping mechanism to group several documents to a corpus or sub corpus (`SCorpus` in Salt). The dummy importer creates token annotations (lemma and pos), span annotations (information structure), hierarchical annotations (syntax tree) and a pointing relation (anaphoric annotation). What you can see here is how to create the several model elements of Salt and how does the import process in Pepper works.

The **manipulator**, traverses over the document structure and prints out some information about it, like the frequencies of annotations, the number of nodes and relations and so on. What you can see here is how to access data and traverse the graph structure in Salt and how does the manipulation process in Pepper works. 

The **exporter** exports the corpus into a format named DOT (http://graphviz.org/), which can be used for visualization of the corpus. The main logic of that mapping is not contained in the exporter itself, since such a component is already part of the Salt framework. The exporter should just give an impression of how to deal with the creation of the specific output files.

We recommend to take a look into the generated modules for getting some impressions how to implement a Pepper module. 

For more detailed descriptions concerning the Pepper module's architecture and the communication between Pepper and a Pepper module go on reading with @ref moduleDevelopers. 

![](http://korpling.github.io/pepper/images/SaltNPepper_logo2010.png)

#Pepper
Pepper is a pluggable, Java-based, open source converter framework for linguistic data. It was developed to convert data from a linguistic data format _X_ to another linguistic data format _Y_. With Pepper, you can convert data from and into all supported formats. For instance from <a href="http://www.exmaralda.org/">EXMARaLDA</a> format to <a href="http://www.ims.uni-stuttgart.de/forschung/ressourcen/werkzeuge/TIGERSearch/doc/html/TigerXML.html">Tiger XML</a>, or <a href="http://mmax2.sourceforge.net/">MMAX</a>
to <a href="http://www.sfb632.uni-potsdam.de/annis/">ANNIS</a> format or <a href="http://www.wagsoft.com/RSTTool/">Treetagger</a> output and <a href="">RST</a> to <a href="https://www.sfb632.uni-potsdam.de/en/paula.html">PAULA</a> and many many more.

To decrease the number of conceptual mappings, Pepper follows the intermediate model approach, which means that a conversion consists of two mappings. First, the data coming from format _X_ will be mapped to the intermediate model <a href="http://u.hu-berlin.de/saltnpepper">Salt</a> and second, the data will be mapped from Salt to format _Y_. If you imagine a set of n source and target formats, then this approach will decrease the number of mappings from _n²-n_ mappings in case of the direct mapping approach to _2n_ mappings.

The Pepper framework itself is just a container controlling the workflow of a conversion process. The mapping itself is done by a set of Pepper modules. Pepper is a highly pluggable framework which offers the possibility to plug-in new modules in order to incorporate further formats. The flexibel architecture of Pepper allows to combine all existing modules and to easily plug-in new ones. The Pepper workflow is separated into three different phases:
<ul>
  <li>the import phase (mapping data from a given fromat to Salt),</li>
  <li>the optional manipulation phase (manipulating or enhancing data in Salt) and the</li>
  <li>export phase (mapping data from Salt to a given format).</li> 
</ul>
The three phase process makes it feasible to influence and manipulate data during conversion, for example by adding additional information or linguistic annotations, or by merging data from different sources.

The import phase handles the mapping from a format _X_ to Salt, the export phase handles the mapping from Salt to a format _Y_. During the manipulation phase, the data in Salt can be enhanced, reduced or manipulated. A phase is divided into several steps: the import and export phase must contain at least one step each. Whereas the manipulation phase can contain any number of steps. Each Pepper module realizes exactly one step. For instance the import of data in the EXMARaLDA format is done by one module, the EXMARaLDAImporter. The orchestration of Pepper modules is determined by the Pepper workflow description file (.pepperparams). A Pepper module can be identified by specifying it name or the format's name and version of the corpus to be converted.

##Download and Install

Here you will find the current stable release, snapshots and even older versions of Pepper including a set of Pepper modules.
* <a href="https://korpling.german.hu-berlin.de/saltnpepper/pepper/download/stable/">Stable Releases</a>
* <a href="https://korpling.german.hu-berlin.de/saltnpepper/pepper/download/snapshot/">Snapshot Releases</a>

Pepper is system independent and comes as a ready to run zip archive, so you do not need any
installation. But since Pepper is Java based, you need to have Java installed on your system. On most
systems, Java is installed by default, but in case it is not please download it from
www.oracle.com/technetwork/java/javase/ or http://openjdk.java.net/. To check if Java (or more precisly a Java
Runtime Environment) is running, open a command line and run:
```bash
java -version
```
You need at least version 1.6.

## Running Pepper

To run Pepper open a command line and enter (for windows):

```
pepperStart.bat ARGUMENTS
```

or (for linux, unix and Mac OS):

```
bash pepperStart.sh ARGUMENTS
```
To get a list of all available arguments, enter 'help' as argument:
```
pepperStart.bat help
```
If no argument are given, Pepper starts the interactive command line and displays the Pepper prompt:
```
pepper>
```
To get a list of all available commands, enter 'help'. 
To start a conversion by a given workflow description file enter the command 'convert' followed by the file.
```
pepper>convert mySampleWorkflow.pepper
```
If you do not already have a workflow description file, enter just 'convert' and Pepper starts the conversion wizzard.

### Conversion Wizzard

1. Enter the path of the corpus to import e.g. 'C:\myCorpus\':
     
     ```
     pepper/wizzard/importer>C:\myCorpus\
     ```
1. Now Pepper displays a list of all available importers and asks you to choose one of them. 
     
     ```
     +------+-------------------------------+------------------------------------------+
     | no   | module name                   | format                                   |
     +------+-------------------------------+------------------------------------------+
     | * 1  | TextImporter                  | (txt, 0.0)                               |
     |   2  | SaltXMLImporter               | (SaltXML, 1.0)                           |
     |   3  | DoNothingImporter             | (doNothing, 0.0)                         |
     ```
     A '*' next to the number of the importer shows, that this importer is recommended. Recommended means that the passed path contains files in a format, the importer is able to process. 
     To choose an importer, just enter its number or its name.
     ```
     pepper/wizzard/importer>1
     ```

1. Some importers provide a list of properties to customize the import. If this is the case, Pepper displays a list of all available customization properties.
      ```
     +----+-------------------------------+------------------------------------------+
     | no | property name                 | description                              |
     +----+-------------------------------+------------------------------------------+
     | 1  | pepper.before.addSLayer       | Consumes a semicolon separated list of   |
     |    |                               | names for {@link SLayer} objects. For    |
     ...
     ```
     To use a property, enter its number or name, followed by '=' and the value of the property.
     ```
     pepper/wizzard/importer>1=anyValue
     ```
     Pepper keeps asking you to enter further customization properties until you enter an empty line.
1. Since it is possible to use more than one importers for one workflow, Pepper asks you to enter a further corpus path. To skip that press just 'enter'. 
1. In Pepper you have the possibility to manipulate the data between the im- and the export phase. Therefore Pepper displays a list of all available manipulators and asks you to enter the  number or name of a manipulator. To skip adding a manipulator press just 'enter'. 
1. Also manipulators can be customized and when the choosen manipulator provides properties for customization Pepper displays them and asks you for entering such a property.
1. To skip the adding of further manipulators press just 'enter'.
1. The last thing remains is to choose an exporter. Pepper shows a list of all available exporters. To choose one of them enter its number or its name.
1. Again you have the chance to customize the export by adding some customization properties, if provided. To do so, enter the number or name of a property followed by '=' and the value.
1. Along with importers and manipulators, you can add more than one exporter. To stop adding exporters, enter an empty line.
1. To save the workflow enter 'save' followed by a path  where to store the file (.pepper).
     ```
     pepper/wizzard>C:\myCorpus\myWorkflow.pepper
     ```
1. To start the conversion enter 'start'.

     ```
     pepper/wizzard>start
     ```
1. To exit and abort the conversion enter 'exit'.

### Workflow File

In Pepper you have the chance to store a workflow in a workflow file (.pepper). This offers you to redo and reuse a congigured workflow. You can also add this file to a version control system, to keep the details how a corpus was processed.
A workflow is stored in an xml file following the [Pepper scheme](https://korpling.german.hu-berlin.de/saltnpepper/pepper/schema/10/pepper.rnc).
 A workflow consists of three phases: import pahse, manipulation phase and export phase. The notation of the workflow file follows this structure. To identify a Pepper module realizing a step, you have to describe that module by its name or the formats name and version, the corpus is in. The following sample consists of three steps, one importer, one manipulator and one exporter:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-model href="https://korpling.german.hu-berlin.de/saltnpepper/pepper/schema/10/pepper.rnc" type="application/relax-ng-compact-syntax"?>
<pepper-job version="1.0">
    <importer formatName="FORMAT_NAME" formatVersion="FORMAT_VERSION" path="SOURCE_PATH">
        <customization>
            <property key="PROPERTY_NAME">PROPERTY_VALUE</property>
        </customization>
    </importer>
    <manipulator name="MANIPULATOR_NAME">
        <customization>
            <property key="PROPERTY_NAME_1">PROPERTY_VALUE</property>
            <property key="PROPERTY_NAME_2">PROPERTY_VALUE</property>
        </customization>
    </manipulator>
    <exporter name="EXPORTER_NAME" path="TARGET_PATH"/>
</pepper-job>
```

The importer in contrast to the exporter is identified by the formats name (FORMAT_NAME) and the formats version (FORMAT_VERSION). The exporter and the manipulator are identified by the module names (MANIPULATOR_NAME and EXPORTER_NAME). To customize the workflow some modules provide a set of properties. A property consists of a name-value pair. A description of properties can be found on the modules site or when entering the command 'list' followed by the modules name in the pepper console, for instance:

```
pepper>list MANIPULATOR_NAME
```

An importer and an exporter need a source or a target path to address where to find the corpus or where to store it. Such a path can be addressed relative or absolute. A relative path can start with './' for current directory or '../' for the parent directory for instance:

```
./corpus
```

or

```
../corpus
```

An absolute path could start with the file scheme like:
```
file:/C:/format1/corpus1/
```
or
```
file:///C:/format1/corpus1/
```
or without any scheme:
```
C:/format1/corpus1/
```

##Modules

Pepper is just a container to control the conversion workflow. The real power comes from the single Pepper modules. A lot of modules for Pepper are already pluged in when downloading Pepper. A list of available Pepper modules can be found [here](./gh-site/modules.md).

To install a further module into an existing Pepper instance open the Pepper console and use command 'is':
```
pepper>is PATH_TO_PLUGIN
```

To update a module, open the Pepper console and use command 'update'
```
update GROUP_ID::ARTIFACT_ID::REPOSITORY
```

You can also install or update a module manually, by copying it. In most cases a Pepper module is provided as a zip file containing the module as a .jar file, and a folder having the same name as the jar file. This folder contains the license files, documentations and other resources the Pepper module needs. Unzip the file and copy its content to the plugin folder of Pepper (PEPEPR_HOME/plugins). When you want to update a module, make sure to delete the existing module by removing the .jar file and the folder.

## Create a new Module

In case, the available modules does not solve your problem, you can also implement an own module to support another format or to manipulate the data during the conversion process. Please read the <a href="http://korpling.github.io/pepper/docs/pepper_modulesDevelopersGuide.pdf">Module Developer's Guide</a> to get a detailed documentation of how to implement a Pepper module. A list of needed OSGi modules, for testing Pepper can be found [here](./gh-site/osgi.md).

## Pepper as Library
With the Pepper library, we provide a programmatic access to the Pepper framework including, the configuration of a conversion workflow, the start of a conversion and getting information about the registered Pepper modules. Since Pepper bases on a plugin structure named OSGi (see: http://www.osgi.org/), each Pepper module is plugged into the framework separatly. This goes for running Pepper as CLI and for running Pepper as a library as well. So the Pepper framework consists of two necesary components, first a jar file, which could be included in your software project via maven and second a plugin folder containing all Pepper modules. The following excerpt shows the necessary maven coordinates.
```xml
<dependency>
  <artifactId>pepper-lib</artifactId>
  <groupId>de.hu_berlin.german.korpling.saltnpepper</groupId>
  <version>VERSION</version>
</dependency>
```
Please replace the placeholder *VERSION* with the version you want to use. Unfortunatly, Pepper is
not included in the maven central repository, therefore you need to include our maven repository into
your projects pom:
```xml
<repositories>
  <!-- ... -->
  <repository>
    <id>korpling</id>
    <name>korpling maven repo</name>
    <url>http://korpling.german.hu-berlin.de/maven2</url>
  </repository>
</repositories>
```
When Pepper is included in your project, you need to get all necessary plugins and modules, therefore:
1. Download a Pepper release of your choice from http://u-hu-berlin.de/saltnpepper/
1. Unzip the downloaded zip file
1. Copy the contained folder plugins to a folder of your choice lets call it PLUGINS_HOME.

Now you can start coding, we here give you a template how to initialize a Pepper object.
```java
PepperStarterConfiguration pepperConf= new
PepperStarterConfiguration();
pepperConf.setProperty(PepperStarterConfiguration.
PROP_PLUGIN_PATH, "PLUGIN_HOME");
PepperConnector pepper= new PepperOSGiConnector();
pepper.setProperties(pepperConf);
```
Too much? Ok lets give some explanaitions to the code:

*In line 1, we initialize a configuration object to configure the Pepper framework before starting it. Line 2 for instance sets the plugin folder. Please replace the placeholder *PLUGIN_HOME* with the real location. The configuration provides some more adaption possibilities, just take a look into the JavaDoc or the class itself.
* In line 3, we initialize the proper Pepper object symbolising the Pepper framework. In future there might be several connectors to access the framework. For instance to access a Pepper instance running on a remote server. But currently there is just an OSGi connector, which starts a separate OSGi environment (the used OSGi environment is equinox, see http://www.eclipse.org/equinox/).
* The last line of code passes the configuration to the Pepper object. Pepper is configured now and we are ready to use it. Before we start a conversion workflow, we show how to query the registered modules. The following snippets prints all information of a module to standard out.

```java
for (PepperModuleDesc moduleDesc: pepper.getRegisteredModules()){
System.out.println(moduleDesc.getName());
System.out.println(moduleDesc.getVersion());
System.out.println(moduleDesc.getDesc());
System.out.println(moduleDesc.getModuleType());
System.out.println(moduleDesc.getSupplierContact());
System.out.println(moduleDesc.getSupportedFormats());
}
```
Next we show how to create a single workflow in Pepper and how to run it. In Pepper a workflow is called a job and is represented by the class PepperJob. A job consists of several steps. A step could handle an importer, a manipulator or an exporter. A job can contain *1* to *n* importers, *0* to *n* manipulators and *1* to *n* exporters. When using an importer or an exporter, we need to describe the corpus to be im- and exported. The following snippet shows the creation of a corpus description, containing the location of the corpus and a description of the format of the corpus.
```java
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
```

We here create two corpora (line 1-4 and line 10-13) and two steps (line 6-8 and 15-17), one for the import and one for the export. When creating a step, you can also pass some properties for customization. For detailed description of which properties are available corresponding to a specific module, please take a look into the documentation of the Pepper module. After creating the steps, we need to add them to the job (line 21-22). So the last thing to do is to start the job with invoking the method 'convert()' (line 24).
Another way of converting a job is converting a predefined workflow file. The following snippet shows how to do this.
```java
String jobId= pepper.createJob();
PepperJob pepperJob= pepper.getJob(jobId);
pepperJob.load("URI_OF_WORKFLOW_FILE");
pepperJob.convert();
```

Thats it. Now you know how to use the basic functionalities of the Pepper library. We hope you will
be happy with it.


##Maven

You can also plug-in this api into your code via maven. 

```xml
<groupId>de.hu_berlin.german.korpling.saltnpepper</groupId>
<artifactId>pepper</artifactId>
<version>VERSION</version>
```

To access the maven coordinates, you need to include our maven repository:

```xml
<repositories>
  <repository>
    <id>korpling</id>
    <name>korpling maven repo</name>
    <url>http://korpling.german.hu-berlin.de/maven2</url>
  </repository>
</repositories>
```

##Contribute

Please help us, to make the api better and give us feedback and send a bug report or a feature request. You can also write a mail to saltnpepper@lists.hu-berlin.de


##Funders

This project has been funded by several institutions:

<table>
  <tr>
    <td><a href="https://www.linguistik.hu-berlin.de/institut/professuren/korpuslinguistik/standardseite-en?set_language=en&cl=en"><img width="100" src="https://www.linguistik.hu-berlin.de/institut/professuren/korpuslinguistik/forschung/whig/Inhalte/609px-Huberlin-logo.svg.jpg"/></a></td> 
    <td><a href="https://www.linguistik.hu-berlin.de/institut/professuren/korpuslinguistik/standardseite-en?set_language=en&cl=en">Humboldt-Universität zu Berlin, department of corpus linguistics and morphology</a></td>
  </tr>
  <tr>
    <td><a href="http://www.inria.fr/en"><img width="150" src="http://www.inria.fr/extension/site_inria/design/site_inria/images/logos/logo_INRIA_en.png"/></a></td> 
    <td><a href="http://www.inria.fr/en">Institut national de recherche en informatique et en automatique (INRIA)</a></td>
  </tr>
  <tr>
    <td><a href="https://www.sfb632.uni-potsdam.de/en/"><img width="100" src="https://www.sfb632.uni-potsdam.de/images/SFB-Bilder/bridge_big.jpg"/></a></td> 
    <td><a href="https://www.sfb632.uni-potsdam.de/en/">Sonderforschungsbereich 632, Information structure: The linguistic means for structuring utterances, sentences and texts </a></td>
  </tr>
  <tr>
    <td><a href="http://www.dfg.de/en/"><img src="http://www.dfg.de/includes/images/dfg_logo.gif"/></a></td> 
    <td><a href="http://www.dfg.de/en/">Deutsche Forschungsgemeinschaft</a></td>
  </tr>
  <tr>
    <td><a href="http://de.clarin.eu/en/"><img width="100" src="http://www.bbaw.de/forschung/clarin/uebersicht/bild"/></a></td> 
    <td><a href="http://de.clarin.eu/en/">CLARIN-D</a></td>
  </tr>
</table>


##License

  Copyright 2009 Humboldt-Universität zu Berlin, INRIA.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.


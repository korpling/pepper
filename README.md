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

Pepper is system independent and comes as a ready to run zip archive, so you do not need any
installation. But since Pepper is Java based, you need to have Java installed on your system. On most
systems, Java is installed by default, but in case it is not please download it from
www.oracle.com/technetwork/java/javase/ or http://openjdk.java.net/. To check if Java (or more precisly a Java
Runtime Environment) is running, open a command line and run:
```bash
java -version
```
You need at least version 1.6.

<a href="https://korpling.german.hu-berlin.de/saltnpepper/repository/saltNpepper_full/">Here</a> you will find the current stable release, snapshots and even older versions of Pepper including a set of Pepper modules.


##Documentation
* <a href="http://korpling.github.io/pepper/docs/pepper_usersGuide.pdf">User's Guide</a> - a documentation for Pepper users
* <a href="http://korpling.github.io/pepper/docs/pepper_modulesDevelopersGuide.pdf">Module Developer's Guide</a> - a documentation of how to implement an own Pepper module

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

  Copyright 2009 Humboldt University of Berlin, INRIA.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.


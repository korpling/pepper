# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Fixed

- Update some dependencies for better compatibility with newer Java versions

## [3.6.0] - 2021-03-01

### Added

- Add new modules to be included in the default pepper distribution

## [3.5.3] - 2020-11-23

### Changed

- Use version tag prefix "v" since its inherited to all Pepper modules and should not be "pepper-"

## [3.5.2]

* Re-release 3.5.1 as 3.5.2 because of aborted release process

## [3.5.1]

* Avoid busy loop to check for cancelation with the new API. 
  This should also help to detect and forward some of the internal errors to the calling process.

## [3.5.0]

* Allow to cancel a running Pepper job via the API

## [3.4.1]

* Fixed startup problems introduced in 3.4.1

## [3.4.1]

* Fix export version of commons-io in manifest
* Update to Salt version 3.4.2

## [3.4.0]

* Set Maven Central URL to https://repo1.maven.org/maven2/ to make the internal updater work again.
* Update to Salt version 3.4.1

## [3.0.2]
* fix Salt dependency
* enhance module tutorial
* correct pepper parent module version in newModule archetype

## [3.0.1]
* make sure Pepper is using the latest bugfix version of Salt (currently 3.0.6)
* update Maven Aether dependency to enhance the update process
* fix some Eclipse build warnings

## [3.0.0]
* updated Salt to version 3.0.3
* allowed to define a maven snapshot repository separate from stable repository 
* changed all package names to org.corpus_tools.pepper
* changed group-id org.corpus_tools
* updated use of commons.lang3 to version 3.4
* added more tolerancy in wizard for backslaches and relative pathes
* Fix memory leak when documents are deleted

## [2.1.2]
* added an integrated TextExporter
* removed memeory settings in bat and sh

## [2.1.1]
* a TextExporter is added to Pepper for exporting just primary texts 
* Pepper's version is displayed when starting Pepper
* When Pepper cannot retrieve the resource folder for Pepper modules a warning is printed instead of throwing an exception
* added a new general property to print out the corpus graph as an ascii
* new command: version or v prints pepper version
* new command: "repeat" or short "r" command to repeat the last command
* added a possibility to add a suppliers homepage in Pepper modules
* fixed #60: Properties in workflow description are not passed to Pepper, when there are characters like linebraks in the value.
* fixed #59: sort Pepper modules in list view
* fixed #57: Catch exceptions throwed by SAX (org.xml.sax.SAXParseException) and create a user friendly output, informing the user, that there are problems like encoding issues in the source files.
* fixed #55: Problems with path deresolving in Pepper wizard at least in Windows
* fixed #56: problems with file encoding in Windows

## [2.1.1]
* this is a bugfix version containing fixes for the plugin mechanism and the logging, which caused LinkageExceptions because of a bug in the current logback implementation 


## [2.1.0]
* a detailed log can be found on github under: https://github.com/korpling/pepper/issues?utf8=%E2%9C%93&q=is%3Aclosed+milestone%3A%22Pepper+2.1+release%22+

* added a wizard to create pepper workflow descriptions (open console and enter 'convert')
* added a new update mechanism to automatically download Pepper modules (use update command)
* removed all 3rd party libs from source code, they are added now by maven, but note to run mvn install assembly:single before running Pepper
* added the support of a new workflow description file (see: https://korpling.german.hu-berlin.de/saltnpepper/pepper/schema/10/pepper.rnc)
* added possibility to not only load bundles from plugin folder, even user defined folders can be set via configuration 'pepper.dropin.paths' in conf/pepper.properties
* added a mechanism for customization properties available in each Pepper module
* added a mechanism to display output for displays with width of 120 and 80 
* added a property to module SaltValidator to determine if an invalid module should be tried to be repaired.
* updated Pepper to use Java 1.7
* added default, that all intermediate Salt documents are deleted when Pepper terminates 
* added a naming the users name as extension for temporary folders followed by a randomized sequence ...
* updated Salt version to 2.1.0

## [2.0.0]
	* removed EMF from Pepper (only used to access Salt)
	* simplified project structure, main projects are
	** pepper-framework (core of Pepper)
	** pepper-lib (containing CLI and access to bridge OSGi)
	** pepper-doc (documentation, reorganized documentation, fulfilled parts and adapted documentation to new structure)
	** pepper-parentModule (a collection of pom plugins for Pepper modules)
	** pepper-sampleModule (containing sample importer, manipulator and exporter, could be used as template for own modules)
	* replaced OSGi logging with slf4j
	* documents are sent to sleep when not needed, this decreases memory amount, since a single document does not to be held in main memory, when it is in waiting mode for a module, documents are stored in systems temporary folder (could be parameterized)
	* improved test environment for modules, see: PepperImporterTest, PepperExporterTest, PepperManipulatorTest
	* new interactive console to access Pepper and even the underlying OSGi (in parts) 
	* updated JUnit to version 4
## [1.1.7]
	* reorganized detection of Pepper module resources, now PEPPER_TEST environment variable is necessary any more, resources now can be directly loaded from same path, where the bundle is located 
## [1.1.6]
	* added a multi threadable handling for all Pepper modules
	* a new mechanism for importing the corpus-structure for importers, with which most importers can automatically import the corpus-structure
	* updated to Salt 1.1.7 
	
## [1.1.5]
	* adopted entire project to eclipse juno
	* set notice plugin-in to a global accessable license-mappings.xml on http://korpling.german.hu-berlin.de/saltnpepper/misc/license-mappings.xml
	* removed bundle as packaging of dependencies in pom 

## [1.1.4]
	pepper-modules
  ---------------
  	*+ added method addSupportedFormat() for simpler creation of a supported format
  	*+ added automatically method to create the symbolic name of a PepperModule
  	
  	
## [1.1.3]

  pepper-framework
  ---------------
	*=	repaired dealing with uris in pepper workflow description, a relative path now can be used with './a/b/c' and an absolute path can be used with 
	    'file:/c:/a/b/c' or 'file:///c:/a/b/c' (for windows) and 'file:/a/b/c' or 'file:///a/b/c' (for linux and mac)
	*+	status message of progress each 20 seconds, with total progress and progress of each document in each pepper module
	
  pepper-moduleTests
  ---------------
    *+ added a better dealing with pepper-module tests when calling testStart(), now the method importCorpusStructure() in case of the fixture
       is an importer will be called
    *+ class PepperModuleTestException was added to mark, that an exception occured in the user defined tests
    
  pepepr-modules
  ---------------
  	*+ added method isFileToImport(URI) to PepperImporter, this method is a callback, to check if a file shall be imported, in case of the file extension is not enough

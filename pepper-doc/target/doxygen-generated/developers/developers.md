Pepper developers {#developers}
======

Unfortunately this documentation is on a very low level and needs to be enhanced. 

The Pepper project is not a monolithic architecture. It is separated into the following sub projects which are Maven sub projects:
\code
pepper
|-pepper-doc
|-pepper-framework
|-pepper-lib
|-pepper-newModule
|-pepper-parentModule
\endcode


Sub project  | Description
------------- 		| -------------
pepper-doc  		| Contains the documentation for the entire project. 
pepper-framework	| Contains the workflow control inside the OSGi environment. This subproject is the core of Pepper.
pepper-lib			| This project is the entry point running Pepper stand-alone or using it as a library in other tools. This project initializes the OSGi environment and starts the pepper-framework. This project further contains the Pepper console and the import wizard.
pepper-newModule	| Contains a Maven archetype to generate new Pepper modules.
pepper-parentModule | Is the parent project for all Pepper modules and contains the most necessary dependencies and Maven settings.

To run Pepper only the projects pepper-lib and pepper-framework are necessary. When there is already an OSGi environment only the project pepper-framework is necessary.


Items to be described in future
===

* Pepper uses OSGi to realize its plugIn structure
* Pepper's build process is based on Maven 
* Pepper is a service infrastructure, therefore more parallel jobs are possible, so one conversion is a job, job is identified by unique id 
* a job consists of steps (identifying module, carrying customization of step and im-or export path if neccessary) 
* DocumentController (abstraction for SDocument, knows the status global and of each module, can send SDcoument to sleep or awake) 
* ModuleController and DocumentBus between controllers 
* some (very simple) modules are part of pepper distribution like SaltXML importer and DoNothing to make sure, something is there
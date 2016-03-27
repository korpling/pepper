Pepper module developers {#moduleDevelopers}
============================

This guide aims to help you to develop your own Pepper module.

The easiest way to start developing a module is along an example, therefore the following tutorial provides the easy generation of a Pepper module's skeleton. The skeleton contains dummy implementations for an importer, a manipulator and an exporter.  

* \subpage tutorial

After setting up the skeleton for your own module it might be helpful to take a closer look into the architecture of a Pepper module project and how does the data- and communication flow work between Pepper and it's modules. If you do not have the time to read all the following articles, just pick the ones you are interested in. But we recommend to read them all in the given order to get a comprehensive overview. 

* \subpage moduleArchitecture
* \subpage init
* \subpage analyze
* \subpage corpusStructure
* \subpage mapping
* \subpage feedback
* \subpage cleanUp
* \subpage customization

A helper to extract tags and attributes from an xml file is described here. This tool can help you improve the implementation speed when creating an importer for a  xml format. 

* \subpage helper

For testing, Pepper uses the JUnit framework (http://junit.org) and provides a test environment which makes it rather easy to test your module without setting up the Pepper platform.

* \subpage testing

When you are done with the implementation work of your module you should deliver it in a way which Pepper can easily plug in into the platform. Therefore Pepper provides a Maven assembly goal to do so.  

* \subpage bundle

Last but not least, when you have done all the great work to implement a module for Pepper, let the world benefit from your work. Publish your module that people can use it. But don't forget to think about licensing. Since Pepper is an open-source software, we plead to think about open-source too. Pepper is under the Apache 2.0 license, which does not force you to go open-source, but there are a lot of good reasons to go open source, see https://opensource.org.
 
You are convinced of open-source? Then don't forget to publish your sources on a portal like github (https://github.com/), source forge (https://sourceforge.net/), bitbucket (https://bitbucket.org/) and so on.

If you would be so kind to let us know about your great module, we will publish it on Pepper's homepage (http://corpus-tools.org/pepper/knownModules.html) and can include it into the official Pepper releases if you want.

Pepper module developers {#moduleDevelopers}
============================

This guide aims to help you to develop your own Pepper module.

The easiest way to start developing an module is along an example, therefore the following tutorial provides the easy generation of a Pepper module's skeleton. The skeleton contains dummy implementations for an importer, a manipulator and an exporter.  

* \subpage tutorial

After setting up the skeleton for your own module it might be helpful to take a closer look into the architecture of a Pepper module project and how the data- and communication flow between Pepper and it's modules work. Read the following sections in the given order to get a comprehensive overview.

* \subpage moduleArchitecture
* \subpage init
* \subpage analyze
* \subpage corpusStructure
* \subpage mapping
* \subpage feedback
* \subpage cleanUp
* \subpage customization

A helper to extract the tags and attributes from an xml file is described here.
* \subpage helper

For testing Pepper makes use of the JUnit framework (http://junit.org) and provides a test environment which makes it rather easy to test your module without running the Pepper platform on your own.

* \subpage testing

When you are done with the implementation work of your module you should deliver it in a way which Pepper can easily plug in into the platform. Therefore Pepper provides a Maven assembly goal to do so.  

* \subpage bundle

Last but not least, when you have done all the great work to implement a module for Pepper, let the world benefit from your work. Publish your module that people can use it. But don't forget to think about licensing your module. Since Pepper is an open-source software, we plead to think about open-source too. Pepper is under the Apache 2.0 license, which does not force you to go open-source, but there are a lot good reasons to go open source (https://opensource.org).
 
You are convinced of open-source? Then don't forget to publish your sources on a portal like github (https://github.com/), source forge (https://sourceforge.net/), bitbucket (https://bitbucket.org/) and so on.

If you would be so kind to let us know about your great module, we will publish it on Pepper's homepage (http://corpus-tools.org/pepper/knownModules.html) and can include it into the official Pepper releases if you want.

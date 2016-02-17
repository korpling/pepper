Foreword
========

The aim of this document is to provide a helpful guide to create your own Pepper module, that can be plugged into the Pepper framework.

We are trying to make things as easy to use as possible, but we are a non-profit project and we need your help. So please tell us if things are too difficult and help us improving the framework.

You are even very welcome to help us improving this documentation by reporting bugs, requests for more information or by writing sections. Please write an email to <saltnpepper@lists.hu-berlin.de>.

Have fun developing with SaltNPepper!

Introduction
============

With SaltNPepper we provide two powerful frameworks for dealing with linguistically annotated data. SaltNPepper is an Open Source project developed at the Humboldt University of Berlin (see: <http://www.hu-berlin.de/>) and INRIA (Institut national de recherche en informatique et automatique, see: <http://www.inria.fr/>). In linguistic research a variety of formats exists, but no unified way of processing them. To fill that gap, we developed a meta model called Salt which abstracts over linguistic data. Based on this model, we also developed the pluggable universal converter framework Pepper to convert linguistic data between various formats.

Pepper is a container controlling the workflow of a conversion process, the mapping itself is done by a set of modules called Pepper modules mapping the linguistic data between a given format and Salt and vice versa. Pepper is a highly pluggable framework which offers the possibility to plug-in new modules in order to incorporate further formats. The architecture of Pepper is flexible and makes it possible to benefit from all already existing modules. This means that when adding a new or previously unknown format *Z* to Pepper, it is automatically possible to map data between *Z* and all already supported formats *A*, *B*, *C*, … . A Pepper workflow consists of three phases:

1.  the import phase (mapping data from a given format to Salt),

2.  the optional manipulation phase (manipulating or enhancing data in Salt) and the

3.  export phase (mapping data from Salt to a given format).

The three phase process makes it feasible to influence and manipulate data during conversion, for example by adding additional information or linguistic annotations, or by merging data from different sources.

To make Pepper a pluggable framework, we used an underlying plug-in technology called OSGi (see: <http://www.osgi.org/>). OSGi is a mighty framework and has a lot of impact on programming things in Java. Because we do not want to force you to learn OSGi, when you just want to create a new module for Pepper, we tried to hide the OSGi layer as good as possible. Therefore, and for the lifecycle management of such projects, we used another framework named Maven (see: <http://maven.apache.org/>). Maven is configured via an xml file called *pom.xml*, you will find it in all SaltNPepper projects and also in the root of the sample project which can be used as a template. Maven makes things easier for use especially in dealing with dependencies.

In the following, ? explains how to set up your environment to start developing your Pepper module and explains how to download and adopt a template module to your own needs. This module then is the base for your own module. In ? we dig into the source code and explain the mechanism how a Pepper module works and interacts with the Pepper framework. In ? we explain how to add properties to your module, so that the user can dynamically customize the behavior of the mapping. Since testing of software often is a pain in the back, the `pepper-testSuite` already comes with some predefined tests. This test bed is based on JUnit (see: [junit.org](junit.org)) and can easily be extended for a specific module test. They should save you some time.

The sample
----------

Before we start, we want to introduce our sample implementation of a Pepper module. This guide will show the power of Pepper and in parts even of Salt along that sample project. Therefore it contains sample implementations, which could be used as a template and a kind of an inspiration for your own project. So don't hesitate to override the specific parts. The project contains three modules, one of each kind: an importer, a manipulator and en exporter. The importer just imports a static corpus containing one super-corpus, two sub-corpora and four documents. Here you can see how to create a corpus-structure and a document-structure[1], as a shortcut to the Salt framework. The manipulator, traverses over the document-structure and prints out some information about it, like the frequencies of annotations, the number of nodes and edges and so on. What you can see here is how to access data and traverse the graph structure in the Salt framework. The exporter exports the corpus into a format named DOT, which can be used for visualization of the corpus. The main logic of that mapping is not contained in the exporter itself, since such a component is already part of the Salt framework. The exporter should just give an impression of how to deal with the creation of the specific output files. Please note, that these modules are just samples and do not show the entire power of Salt or Pepper. For learning more about Salt, please read the Salt model guide or the Salt quick User's guide. Both are available on [u.hu-berlin.de/saltnpepper](u.hu-berlin.de/saltnpepper).

[1] Salt differentiates between the corpus-structure and the document-structure. The document-structure contains the primary data (data sources) and the linguistic annotations. A bunch of such information is grouped to a document (`SDocument` in Salt). The corpus-structure now is a grouping mechanism to group several documents to a corpus or sub-corpus (`SCorpus` in Salt).

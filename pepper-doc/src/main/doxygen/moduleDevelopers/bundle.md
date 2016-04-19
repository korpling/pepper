Bundle and deliver {#bundle}
====

Pepper modules use to have a fixed structure containing the Java code and some resources, which may be needed for running the module. Such resources could be some XSLT files in case the conversion process makes use of an XSLT conversion. A module normally is packed into a zip file having the following structure:

\code
company.myModule.zip
 |--company.myModule/
 |  |--LICENSE
 |  |--NOTICE
 |  |--...
 |--company.myModule.jar
\endcode

To create this structure, you can run Maven assembly:

\code
mvn clean install assembly:single
\endcode
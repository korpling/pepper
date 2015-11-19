Configuring Pepper {#config}
======

The Pepper home folder contains a folder named 'conf/', here you find two files to adapt the behavior of Pepper. First 
you can change the logging behavior by adapting the file logback.xml. The default configuration is configured using the 
INFO log level:

\code
<root level="info">
   ...
</root>
\endcode

A log level determines which types of log messages are printed. The hierarchy is the following (ascending): trace, 
debug, info, warn, error. That means, when setting the log level to info, warn and error messages are printed, but no 
trace and debug messages.

Secondly, by adapting the file 'pepper.properties' you can change the processing behavior of Pepper. This file has a 
key-value notation, which mean an entry has a key, followed by the equal sign and the corresponding value. Here is an 
excerpt of that file:

<pre>
##########
# Determines where to find the plugins for Pepper $PEPPER_HOME points to the pepper home folder (this is not an 
# environment variable)
##########
pepper.plugin.path=./plugins/

##########
# Determines if Pepper shall measure and display the performance of the used PepperModules
##########
pepper.computePerformance=true

##########
# The maximal number of currently processed SDocument-objects
########## 
pepper.maxAmountOfProcessedSDocuments=4

##########
# Determines if an SDocument-object shall be removed after it was processed by all PepperModules
########## 
pepper.removeSDocumentAfterProcessing=true
</pre>
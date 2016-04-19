Monitoring the progress {#feedback}
=======================

What could be more annoying than a not responding program and you do not know if it is still working or not? A conversion job could take some time, which is already frustrating enough for the user. Therefore we want to keep the frustration of users as small as possible and give them a precise response about the progress of the conversion job.

When you are using the default mapping mechanism overwriting @ref org.corpus_tools.pepper.impl.PepperMapperImpl, this class provides the methods @ref org.corpus_tools.pepper.impl.PepperMapperImpl.addProgress() and @ref org.corpus_tools.pepper.impl.PepperMapperImpl.setProgress() for this purpose. Both methods have a different semantic. addProgress(Double progress) will add the passed value to the current progress, whereas setProgress(Double progress) will overwrite the entire progress. The passed value for progress must be between 0 for 0% and 1 for 100%. It is up to you to call one of the methods in your code and to estimate the remaining work. Often it is easier not to estimate the time needed for the process, than the complexity. For instance to divide the total process costs in several steps and to return a progress for each step. For instance the following snippet separates the entire mapping process into five steps and assigns the same cost for each step.

\code
    //...
    //map all STextualDS objects (one fifth if total process is done)
    addProgress(0.2);
    //map all SToken objects (two fifth if total process is done)
    addProgress(0.2);
    //map all SSpan objects (three fifth if total process is done)
    addProgress(0.2);
    //map all SStruct objects (four fifth if total process is done)
    addProgress(0.2);
    //map all SPointingRelation objects 
    //(process done, should be one of the last lines)
    addProgress(0.2);
    //...
\endcode

> **Note**
>
> When using @ref org.corpus_tools.pepper.modules.PepperMapper, you only have to take care about the progress of the current `SDocument` or `SCorpus` object. The aggregation of all currently processed objects (`SDocument` and `SCorpus`) will be done automatically.

In case you do not want to use the default mechanism, you need to overwrite the methods @ref org.corpus_tools.pepper.modules.PepperModule.getProgress(String globalId) and @ref org.corpus_tools.pepper.modules.PepperModule.getProgress(). The method getProgress(String globalId) shall return the progress of the `SDocument` or `SCorpus` object corresponding to the passed id. Whereas the method getProgress() shall return the aggregated progress of all `SDocument` and `SCorpus` objects currently processed by your module.

Logging
-------

Another form of monitoring is the logging, which could be used for passing messages to the user or passing messages to a file for debugging. The logging in Pepper is handled by the SLF4J framework (see: <http://www.slf4j.org/>). SLF4J is a logging framework, which provides an abstraction for several other logging frameworks like log4j (see: <http://logging.apache.org/log4j/2.x/>) or java.util.logging. Via creating a static logger object you can log several debug levels: trace, debug, info and error. The following snippet shows the instantiation of the static logger and its usage.
\code
    private static final Logger logger= LoggerFactory
            .getLogger(MyImporter.class);
    logger.trace("messages for the implementor");
    logger.debug("message for the implementor and user");
    logger.info("messages for the user");
    logger.error("messages in case of an exception");
\endcode

Error handling
--------------

Even when the module crashes and the conversion could not be ended successfully, the user needs a feedback of what has happened. The main question would be: Is it a bug in code or a problem in the data? In both cases, the user needs a precise description. Either to notify you, the module developer, or to find the problem, in the data. Passing just a `NullPointerException` is not very useful to the user and is very frustrating.

Pepper provides a hierarchy of exception classes to be used for different purposes. For instance there are classes to describe problems in customization properties, problems in the data or bugs in the module. 

![corpus-structure represented in file structure](./moduleDevelopers/images/exceptionClassDiagram.png)

The main and general class @ref org.corpus_tools.pepper.modules.exceptions.PepperModuleException can be used in case the exception does not match to one of the more specific types. When initializing a @ref org.corpus_tools.pepper.modules.exceptions.PepperModuleException or one of its subclasses, you can pass a @ref org.corpus_tools.pepper.modules.PepperModule or a @ref org.corpus_tools.pepper.modules.PepperMapper object. The exception itself will expand the error message with the modules name etc. A more detailed description of the exception classes could be found at @ref org.corpus_tools.pepper.exceptions.

When an exception was thrown for a single document, Pepper will not abort the entire conversion process. Pepper will set the status of this document to FAILED in  @ref org.corpus_tools.pepper.common.DOCUMENT_STATUS  and will remove it from the rest of the workflow, so that following modules will not process the corrupted document. Pepper will give a feedback to the user containing the error message provided by the module.
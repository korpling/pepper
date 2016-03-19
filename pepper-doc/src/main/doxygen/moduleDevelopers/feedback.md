Monitoring the progress {#feedback}
=======================

What could be more annoying than a not responding program and you do not know if it is still working or not? A conversion job could take some time, which is already frustrating enough for the user. Therefore we want to keep the frustration of users as small as possible and give them a precise response about the progress of the conversion job.

Although Pepper is providing a mechanism to make the monitoring of the progress as simple as possible, a rest work for you remains to do. But don't get afraid, monitoring the progress just means the call of a single method.

When you are using the default mapping mechanism by implementing the class `PepperMapper`, this class provides the methods addProgress(Double progress) and setProgress(Double progress) for this purpose. Both methods have a different semantic. addProgress(Double progress) will add the passed value to the current progress, whereas setProgress(Double progress) will override the entire progress. The passed value for progress must be a value between 0 for 0% and 1 for 100%. It is up to you to call one of the methods in your code and to estimate the progress. Often it is easier not to estimate the time needed for the process, than to divide the total process costs in several steps and to return a progress for each step. For instance the following sample separates the entire mapping process into five steps, which get the same costs of process.

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

> **Note**
>
> When using `PepperMapper`, you only have to take care about the progress of the current `SDocument` or `SCorpus` object you are processing. The aggregation of all currently processed objects (`SDocument` and `SCorpus`) will be done automatically.

In case you do not want to use the default mechanism, you need to override the methods getProgress(SElementId sDocumentId) and getProgress() of `PepperModule`. The method getProgress(SElementId sDocumentId) shall return the progress of the `SDocument` or `SCorpus` object corresponding to the passed `SElementId`. Whereas the method getProgress() shall return the aggregated progress of all `SDocument` and `SCorpus` objects currently processed by your module.

Logging
-------

Another form of Monitoring is the logging, which could be used for passing messages to the user or passing messages to a file for debugging. The logging task in Pepper is handled by the SLF4J framework (see: <http://www.slf4j.org/>). SLF4J is a logging framework, which provides an abstraction for several other logging frameworks like log4j (see: <http://logging.apache.org/log4j/2.x/>) or java.util.logging. Via creating a static logger object you can log several debug levels: trace, debug, info and error. The following snippet shows the instantiation of the static logger[4] and its usage.

    private static final Logger logger= LoggerFactory
            .getLogger(SampleImporter.class);
    logger.trace("messages for the implementor");
    logger.debug("message for the implementor and user");
    logger.info("messages for the user");
    logger.error("messages in case of an exception");

Error handling
--------------

Another important aspect of monitoring, is the monitoring when an error occurred. Even when the module crashes and the conversion could not be ended successfully, the user needs a feedback of what has happened. The main question would be is it a bug in code or a bug in the data. In both cases, the user needs a precise description. Either to notify you, the module developer, or to find the bug in the data on her or his own. And unfortunately just a `NullPointerException` is not very useful to the user and is very frustrating.

Pepper provides a hierarchy of Exception classes to be used for different purposes, for instance to describe problems in customization properties, the data or general problems in module. Figure ? gives an overview over the Exception classes for modules. The main and general class `PepperModuleException` can be used in case the exception does not match to one of the more specific types. When initializing a `PepperModuleException` or one of its subclasses, you can pass a `PepperModule` or a `PepperMapper` object. The exception itself will expand the error message with the modules name etc. . A more detailed description of the exception classes could be found in the JavaDoc.

When an exception was thrown for a single document, Pepper will not abort the entire conversion process. Pepper will set the status of this document to `DOCUMENT_STATUS.FAILED` and will remove it from the rest of the workflow, so that modules coming afterwards will not process the corrupted document. Pepper will provide a feedback to the user containing the error message provided by the module.
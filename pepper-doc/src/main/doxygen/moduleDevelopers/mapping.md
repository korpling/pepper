Map documents and corpora {#mapping}
====================================

Salt differentiates between the corpus structure and the document structure. The document structure contains the primary data (data sources) and the linguistic annotations. A bunch of such information is grouped to a document (`SDocument` in Salt). The corpus structure now is a grouping mechanism to group several documents to a corpus or sub corpus (`SCorpus` in Salt). Therefore, mapping the document structure and corpus structure is the main task of a Pepper module. Normally the conceptual mapping of elements between a model or format *X* and Salt is the most tricky part. Not necessarily in a technical sense, but in a semantical. For getting a clue how the mapping can technically be realized, we strongly recommend, to read the Salt model guide and the quick user guide on http://corpus-tools.org/salt/#documentation.

There are two aspects having a big impact on the inner architecture of a Pepper module. First we have the convention over configuration aspect and second we have the aspect of parallelizing a mapping job. This results in a relatively long stack of function calls to give you an intervention option on several points. We come to this later. But if you are happy with the defaults, it is rather simple to implement your module. Again, the @ref org.corpus_tools.pepper.modules.PepperModule is a singleton instance for each Pepper step, whereas there is one instance of @ref org.corpus_tools.pepper.modules.PepperMapper per `SDocument` and `SCorpus` object in the workflow.

Have a look at the following snippet, which is part of each @ref org.corpus_tools.pepper.modules.PepperModule:
\code
    public PepperMapper createPepperMapper(Identifier identifier){
        MyMapper mapper= new MyMapper();
        //1: module is an im-or exporter? 
        // passing the physical location to mapper
        mapper.setResourceURI(getIdentifier2ResourceTable()
              .get(identifier));
        //2: differentiate between documents and corpora
        if (identifier.getIdentifiableElement() 
            instanceof SDocument){
            //do some specific stuff for documents
        }else if (identifier.getIdentifiableElement() 
                  instanceof SCorpus){
            //do some specific stuff for corpora
        }
        return(mapper);
    }
\endcode

This method is supposed to create a new instance of a specialized @ref org.corpus_tools.pepper.modules.PepperMapper. Although the main initializations, necessary for the workflow (e.g. passing the customization properties, see @ref customization) are done by Pepper in the back. The method is the place to make some specific configurations depending on your implementation. If your module is an im- or exporter, it might be necessary to pass the physical location of the file or folder where the Salt model is supposed to be imported from or exported to (see position 1 in code). Sometimes it might be necessary to differentiate the type of object which is supposed to be mapped (either an `SCorpus` or `SDocument` object). This is shown in the snippet under position 2.

That's all we have to do in @ref org.corpus_tools.pepper.impl.PepperModuleImpl for the mapping task, now we come to @ref org.corpus_tools.pepper.impl.PepperMapperImpl. Here you find three methods, supposed to be overwritten, as shown in the following snippet.
\code
    public class MyMapper implements PepperMapperImpl {

        @Override
        protected void initialize(){
            //do some initializations
        }
        
        @Override
        public DOCUMENT_STATUS mapSCorpus() {
            //1: returns the resource in case that a module is 
            // an importer or exporter
           getResourceURI();
           //2: getSCorpus() returns the SCorpus object, 
           // which for instance can be annotated
           getSCorpus().createSMetaAnnotation(null, "author",
                       "Bart Simpson");
           //3: returns that the process was successful
           return(DOCUMENT_STATUS.COMPLETED);
        }
        
        @Override
        public DOCUMENT_STATUS mapSDocument() {
           //4: returns the resource in case that the module is 
           // an importer or exporter
           getResourceURI();
           //5: getSDocument() returns the SDocument 
           getSDocument().setSDocumentGraph(SaltFactory.eINSTANCE
                         .createSDocumentGraph());
           //6: create a primary text "Is this example..."
           STextualDS primaryText= getSDocument().getSDocumentGraph()
                    .createSTextualDS("Is this example more complicated "
                        + "than it appears to be?");
           //7: create a meta-annotation
           getSDocument().createSMetaAnnotation(null, "author",
                       "Bart Simpson");
           //8: returns that the process was successful
           return(DOCUMENT_STATUS.COMPLETED);
        }
    }
\endcode
Not very surprising, the method 'initialize()' is invoked by the constructor and should do some initialization stuff if necessary. The methods @ref org.corpus_tools.pepper.modules.PepperMapper.mapSCorpus() and @ref org.corpus_tools.pepper.modules.PepperMapper.mapSDocument() are the more interesting ones. Here is the place to implement the mapping of the corpus structure or the document structure. Note, that one instance of the mapper always processes exactly one object, so either a `SCorpus` or a `SDocument` object. If you set the physical location at position 1 in method @ref org.corpus_tools.pepper.modules.PepperModule.createPepperMapper(), you can now get that location via calling @ref org.corpus_tools.pepper.modules.PepperMapper.getResourceURI() as shown at position 1 and 4. This method returns a `URI` pointing to the physical location.

> **Note**
>
> If your module is an exporter, that location does not physically exist and has to be created on your own.

Position 2 shows, how to access the current `SCorpus` object and how to annotate it for instance with a meta-annotation (in the snippet, the meta-annotation is about an author having the name 'Bart Simpson', the `null`-value means, that no namespace is used). In method @ref org.corpus_tools.pepper.modules.PepperMapper.mapSDocument(), at position 5, you can access the current object (here it is of type `SDocument`) with 'getSDocument()'. If your module is an importer, you need to create a container for the document structure, a `SDocumentGraph` object. The snippet further shows the creation of a primary text at position 6. In Salt each object can be annotated or meta-annotated, so do the `SDocument` objects, as shown at position 7. Last but not least, both methods have to return a value describing whether the mapping was successful or not (see position 3 and 8). The possible results are described in @ref org.corpus_tools.pepper.common.DOCUMENT_STATUS. Usually you only need to return the @ref org.corpus_tools.pepper.common.DOCUMENT_STATUS.COMPLETED when everything was ok. In case of an error, Pepper will set the status @ref org.corpus_tools.pepper.common.DOCUMENT_STATUS.FAILED automatically, as long, as the exception is thrown, which marks the document or corpus to be not processed any further.

During the mapping it is very helpful for the user, to give some progress status from time to time. Especially when a mapping takes a longer term, it will keep the user from a frustrating experience to have a not responding tool. More information on that can be found in @ref feedback.

## More configuration options

In a few cases, a format does not allow or only hardly allow a parallel processing. In that case you can switch-off the parallelization in your constructor with @ref org.corpus_tools.pepper.modules.PepperModule.setIsMultithreaded(). The Pepper framework does not directly call the method @ref org.corpus_tools.pepper.modules.PepperModule.createPepperMapper(). If you need to intervene at an earlier point, you can do this at any point in the stack as shown in the following excerpt.
\code
    /** Directly called by Pepper framework, 
        waits until a further document or corpus 
        can be processed and delegates it **/
    @Override
    public void start(){
        ...
        Identifier identifier= getModuleController().next()
                     .getsDocumentId();
        start(identifier);
        ...
    }

    /** Only takes control of passed document 
        or corpus and creates a mapper object per each**/
    @Override
    public void start(Identifier identifier){
        ...
        PepperMapper mapper= createPepperMapper(identifier);
        ...
    }

    /** Creates and initializes a PepperMapper instance **/
    @Override
    public PepperMapper createPepperMapper(Identifier identifier){
        ...
    }
\endcode
Even the first two methods could be overwritten by your module, to adapt their functionality on different levels.

> **Note**
>
> Take care when overriding one of them, since they handle some more functionality than explained here in this guide. To get a clue of what happens there, please take a look into the source code at https://github.com/korpling/pepper. It might be well documented and hopefully understandable. But if questions occur, please send a mail to [saltnpepper@lists.hu-berlin.de](saltnpepper@lists.hu-berlin.de).
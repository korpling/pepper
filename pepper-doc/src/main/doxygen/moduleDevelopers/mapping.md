Map documents and corpora {#mapping}
====================================

Remember Salt differentiates between the corpus-structure and the document-structure. The document-structure contains the primary data (data sources) and the linguistic annotations. A bunch of such information is grouped to a document (`SDocument` in Salt). The corpus-structure now is a grouping mechanism to group several documents to a corpus or sub-corpus (`SCorpus` in Salt). Therefore, mapping the document-structure and corpus-structure is the main task of a Pepper module. Normally the conceptual mapping of elements between a model or format *X* and Salt is the most tricky part. Not necessarily in a technical sense, but in a semantical. For getting a clue how the mapping can technically be realized, we strongly recommend, to read the Salt model guide and the quick user guide on [u.hu-berlin.de/saltnpepper/](u.hu-berlin.de/saltnpepper/). We here primarily focus on the technical part of the Pepper workflow and especially on the Pepper modules. But in our Sample module, a lot of templates exist of how to deal with a Salt model. Especially the `SampleImporter` is full of instructions to create a Salt model.

There are two aspects having a big impact on the inner architecture of a Pepper module. First we have the convention over configuration aspect and second we have the aspect of parallelizing a mapping job. This results in a relatively long stack of function calls to give you an intervention option on several points. We come to this later. But if you are happy with the defaults, it is rather simple to implement your module. Again, the `PepperModule` is a singleton instance for each Pepper step, whereas there is one instance of `PepperMapper` per `SDocument` and `SCorpus` object in the workflow.

Enough with words, let's dig into the code. Have a look at the following snippet, which is part of each `PepperModule`:

    public PepperMapper createPepperMapper(SElementId sElementId){
        SampleMapper mapper= new SampleMapper();
        //1: module is an im-or exporter? 
        // passing the physical location to mapper
        mapper.setResourceURI(getSElementId2ResourceTable()
              .get(sElementId));
        //2: differentiate between documents and corpora
        if (sElementId.getSIdentifiableElement() 
            instanceof SDocument){
            //do some specific stuff for documents
        }else if (sElementId.getSIdentifiableElement() 
                  instanceof SCorpus){
            //do some specific stuff for corpora
        }
        return(mapper);
    }

This method is supposed to provide a new instance of a specialized `PepperMapper`. Although the main initializations, necessary for the workflow (e.g. passing the customization properties, see ?) are done by Pepper in the back, this is the place to make some specific configurations depending on your implementation. If your module is an im- or exporter, it might be necessary to pass the physical location of that file or folder where the Salt model is supposed to be imported from or exported to (see position 1 in the code). Sometimes it might be necessary to differentiate the type of object which is supposed to be mapped (either an `SCorpus` or `SDocument` object). This is shown in the snippet under position 2.

That's all we have to do in class `PepperModule` for the mapping task, now we come to the class `PepperMapper`. Here you find three methods, supposed to be overridden, as shown in the following snippet.

    public class SampleMapper implements PepperMapperImpl {

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

Not very surprising, the method 'initialize()' is invoked by the constructor and should do some initialization stuff if necessary. The methods 'mapSCorpus()' and 'mapSDocument()' are the more interesting ones. Here is the place to implement the mapping of the corpus-structure or the document-structure. Note, that one instance of the mapper always processes just one object, so either a `SCorpus` or a `SDocument` object. If you set the physical location at position 1 in method 'createPepperMapper()', you can now get that location via calling 'getResourceURI()' as shown at position 1 and 4 (of the current snippet). This method returns a URI pointing to the physical location.

> **Note**
>
> If your module is an exporter, that location does not physically exist and has to be created on your own.

Position 2 shows, how to access the current `SCorpus` object and how to annotate it for instance with a meta-annotation (in this sample, the meta-annotation is about an author having the name 'Bart Simpson', the null-value means, that no namespace is used). In method 'mapSDocument()', at position 5, you can access the current object (here it is of type `SDocument`) with 'getSDocument()'. If your module is an importer, you need to create a container for the document-structure, a `SDocumentGraph` object. The snippet further shows the creation of a primary text at position 6. In Salt each object can be annotated or meta-annotated, so do the `SDocument` objects, as shown at position 7. Last but not least, both methods have to return a value describing whether the mapping was successful or not (see position 3 and 8). The returned value can be one of the following three:

-   `DOCUMENT_STATUS.COMPLETED` - means, that a document or corpus has been processed successfully.

-   `DOCUMENT_STATUS.FAILED` - means, that the corpus or document could not be processed because of any kind of error.

-   `DOCUMENT_STATUS.DELETED` - means, that the document or corpus was deleted and shall not be processed any further (by following modules).

Usually you only need to return the `DOCUMENT_STATUS.COMPLETED` when everything was ok. In case of an error, Pepper will set the status `DOCUMENT_STATUS.FAILED` automatically, as long, as the exception is thrown[2], which marks it to be not processed any further.

During the mapping it is very helpful for the user, if you give some progress status from time to time. Especially when a mapping takes a longer term, it will keep the user from a frustrating experience to have a not responding tool. More information on that can be found in ?.

That's it... that's it with the mapping of the document-structure and corpus-structure. The rest of this section just handles ways to not using the default mechanisms and making more adaptions.

In a few cases, a format does not allow or difficulty allow to process it in parallel. In that case you can switch-off the parallelization in your constructor with

    setIsMultithreaded(false);

If you wondered what we meant, when we said there is a 'long stack of function calls', here is the answer. The Pepper framework does not directly call the method 'createMapper(SElementId)'. The following excerpt illustrates the stack.

    /** Directly called by Pepper framework, 
        waits until a further document or corpus 
        can be processed and delegates it **/
    @Override
    public void start(){
        ...
        SElementId sElementId= getModuleController().next()
                     .getsDocumentId();
        start(sElementId);
        ...
    }

    /** Only takes control of passed document 
        or corpus and creates a mapper object per each**/
    @Override
    public void start(SElementId sElementId){
        ...
        PepperMapper mapper= createPepperMapper(sElementId);
        ...
    }

    /** Creates and initializes a PepperMapper instance **/
    @Override
    public PepperMapper createPepperMapper(SElementId sElementId){
        ...
    }

Even the first two methods could be overridden by your module, to adapt their functionality on different levels.

> **Note**
>
> Take care when overriding one of them, since they handle some more functionality than explained here in this guide. To get a clue of what happens there, please take a look into the source code. It might be well documented and hopefully understandable. But if questions occur, please send a mail to [saltnpepper@lists.hu-berlin.de](saltnpepper@lists.hu-berlin.de).
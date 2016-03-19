Prepare and clean-up {#cleanUp}
====================

The aspect of initialization is spread over two methods in a Pepper module. First the constructor of a module and second the method 'isReadyToStart()'. Both methods have been touched already in other sections above, but here we want to give a bundled overview about things concerning the initialization. Let us start with an explanation why there are two methods. Sometimes, it might be necessary, to read some configuration files for the initialization, but their location is passed by the Pepper framework. Such locations can be accessed with the method 'getResources()'. Unfortunately, this information can only be set after a Pepper module was created, so after the constructor was called. Therefore we need a possibility for initialization at a later point. The method 'isReadyToStart()' fulfills two tasks, first the initialization task and second, it returns a boolean value to determine, if the module can be started or if things went wrong. If you now wonder where the best location should be, to do your initialization, we recommend an early-as-possible approach. The following snippet shows a sample initialization:

    public SampleImporter(){
        super();
        setName("SampleImporter");    //setting name of module
        setVersion("1.1.0");          //setting version of module
        //supported formats
        addSupportedFormat("sample", "1.0", null);
        //using an own properties object
        setProperties(new SampleProperties()); 
    }

    @Override
    public boolean isReadyToStart(){
        //access the passed resource folder
        File resourceFolder= new File(getResources().toFileString());
        ...
        getSDocumentEndings()
           .add(some value retrieved from resource folder);
    }

In this sample, the file ending is set in method 'isReadyToRun()', since it depends on some files in the resource folder[5]. Other reasons could be dependencies on the passed customizations, which are also set after the constructor was called.

Sometimes it might be necessary to clean up after the module did the job. For instance when writing an im- or an exporter it might be necessary to close file streams, a db connection etc. Therefore, after the processing is done, the Pepper framework calls the method described in the following snippet:

    @Override
    public void end(){
        super.end();
        //do some clean up like closing of streams etc.
    }

To run your clean up, just override it and you're done.

[1] This mechanism enables to map several document-structures and corpus-structures at once.

[2] If not and your module is catching the exception, you have to think about what to do in error case. If you want the document to be processed further by other modules than return `DOCUMENT_STATUS.COMPLETED`. If the document now might be corrupt, than return the `DOCUMENT_STATUS.FAILED`

[3] In case you are wondering, yes this sounds a bit strange, since each file ending which is not contained in the second list won't be imported by default. But there is an option to set this to import each file, no matter what's the ending.

[4] Normally one logger is instantiated for exactly one class.

[5] All files in the folder 'SAMPLE\_HOME/src/main/resources' will be copied to the resource location in a modules distribution.

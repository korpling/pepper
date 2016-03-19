Clean up {#cleanUp}
===
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

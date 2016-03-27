Clean up {#cleanUp}
===
Sometimes it might be necessary to clean up after the module did the job. For instance when writing an im- or an exporter it might be necessary to close file streams, a db connection etc. Therefore, after the processing is done, the Pepper framework calls the method described in the following snippet:

    @Override
    public void end(){
        super.end();
        //do some clean up like closing of streams etc.
    }

To run your clean up, just override it and you're done.

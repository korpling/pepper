Module initialization {#init}
====================

The initialization is spread over two methods in a Pepper module. First the constructor of a module and second the method @ref org.corpus_tools.pepper.modules.PepperModule.isReadyToStart(). The separation is caused for reasons concerning the point of time of passing information to the module. For instance, sometimes it might be necessary, to read some configuration files from disk for the initialization. But the location of the configuration files is passed by the Pepper framework. Unfortunately, this information can only be set after a Pepper module was created, so after the constructor was called. Therefore we need a possibility for initialization at a later point. The method @ref org.corpus_tools.pepper.modules.PepperModule.isReadyToStart() fulfills two tasks, first the initialization task and second, it returns a boolean value to determine, if the module can be started or if things went wrong. If you now wonder where the best location should be, to do your initialization, we recommend an early-as-possible approach. The following snippet shows a sample initialization:
\code
    public MyImporter() {
		super();
		setName("MyImporter");
		// suppliers e-mail address
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		// suppliers homepage
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		//description of what your module is supposed to do
		setDesc("...");
		// the format name and and version to support to im- or export
		addSupportedFormat("myFormat", "1.0", null);
		// the file ending of files to import in case your module is an importer
		getDocumentEndings().add("ENDING OF FILES TO IMPORT");
	}

	@Override
	public boolean isReadyToStart() throws PepperModuleNotReadyException {
		// TODO make some initializations if necessary
		return (super.isReadyToStart());
	}
\endcode

Initialization {#init}
====================

The aspect of initialization is spread over two methods in a Pepper module. First the constructor of a module and second the method @ref org.corpus_tools.pepper.modules.PepperModule.isReadyToStart(). Both methods have been touched already in other sections above, but here we want to give a bundled overview about things concerning the initialization. Let us start with an explanation why there are two methods. Sometimes, it might be necessary, to read some configuration files for the initialization, but their location is passed by the Pepper framework. Such locations can be accessed with the method @ref org.corpus_tools.pepper.modules.PepperModule.getResources(). Unfortunately, this information can only be set after a Pepper module was created, so after the constructor was called. Therefore we need a possibility for initialization at a later point. The method @ref org.corpus_tools.pepper.modules.PepperModule.isReadyToStart() fulfills two tasks, first the initialization task and second, it returns a boolean value to determine, if the module can be started or if things went wrong. If you now wonder where the best location should be, to do your initialization, we recommend an early-as-possible approach. The following snippet shows a sample initialization:
\code
    public MyImporter() {
		super();
		setName("MyImporter");
		// TODO change suppliers e-mail address
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		// TODO change suppliers homepage
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		//TODO add a description of what your module is supposed to do
		setDesc("...");
		// TODO change "sample" with format name and 1.0 with format version to
		// support
		addSupportedFormat("sample", "1.0", null);
		// TODO change the endings in endings of files you want to import, see
		// also predefined endings beginning with 'ENDING_'
		getDocumentEndings().add("ENDING OF FILES TO IMPORT");
	}

	@Override
	public boolean isReadyToStart() throws PepperModuleNotReadyException {
		// TODO make some initializations if necessary
		return (super.isReadyToStart());
	}
\endcode

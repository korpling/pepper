# Fitness Check {#fitness}

The fitness check ensures, that a module fits into the Pepper platform. It checks a set of features describing whether a module implements all fields and methods which should be implemented. Some of the features are required (health features) and some are optional (fitness features). When the module does not pass at least one of the health features, the overall module fitness is set to *critical*. When all health features are passed and at least one fitness is missed, the overall module fitness is *healthy*. When the module passes all features, the overall status is *fit*.

To run the fitness check for an installed module run:
\endcode
pepper>fitness
\endcode 

## Health Features

The health features are required features and ensure that a module is runnable inside Pepper.

### HAS_NAME

This health feature indicates whether a module's name is set. 
When your module fails this check, call the module's super constructor and pass the module's name:

\code
public MyModule(){
	super(MODULE_NAME);
	...
}

\endcode

### IS_READY_TO_RUN

This health feature indicates whether a module is runnable on the Pepper platform. 
To check this, the function @ref org.corpus_tools.pepper.modules.PepperModule.isReadyToStart() is called.
When your module fails this check, it means that this method returned false.

### HAS_PASSED_SELFTEST

When the module provides a self test, this health feature indicates whether the self test was passed. A self test is a test setup to check whether the module is runnable within a Pepper instance (similar to a system integration test). Therefore the module needs to provide a corpus as an input and an expected corpus. The output corpus then is compared to the expected corpus.
When your module fails this check, it means that the self test has not been passed.
For more information on self tests check section HAS_SELFTEST.  
		
### IS_IMPORTABLE_SEFTEST_DATA		
		
When the module is an importer, provides a self test and overrides the isImportable() method, this health feature indicates whether the method isImportable() returned 1.0 for the provided input corpus. A self test is a test setup to check whether the module is runnable within a Pepper instance (similar to a system integration test). Therefore the module needs to provide a corpus as an input and an expected corpus. The output corpus then is compared to the expected corpus.
When your module fails this check, it means that the self test has not been passed.
For more information on self tests check section HAS_SELFTEST.
		
### IS_VALID_SELFTEST_DATA		
		
When the module provides a self test, this health feature indicates whether the provided input and expected corpus is valid. A self test is a test setup to check whether the module is runnable within a Pepper instance (similar to a system integration test). Therefore the module needs to provide a corpus as an input and an expected corpus. The output corpus then is compared to the expected corpus.
When your module fails this check, it means that the self test has not been passed.
For more information on self tests check section HAS_SELFTEST.		

## Fitness features

The fitness features are optional and must not necessarily be passed to run a module in Pepper. But they ensure, that the module implements a kind of a standard and supports information which are helpful to the user.

### HAS_SUPPLIER_CONTACT

This health feature indicates whether a  supplier contact is set. 
When your module fails this check, call the following method in the mpodule's constructor:

\code
public MyModule(){
	...
	setSupplierContact(URI.createURI(...));
	...
}

\endcode

### HAS_SUPPLIER_HP

This health feature indicates whether a supplier homepage is set. 
When your module fails this check, call the following method in the mpodule's constructor:

\code
public MyModule(){
	...
	setSupplierHomepage(URI.createURI(...));
	...
}

\endcode

### HAS_DESCRIPTION

This health feature indicates whether a description for the module is set. 
When your module fails this check, call the following method in the mpodule's constructor:

\code
public MyModule(){
	...
	setDesc("A DESCRIPTION DESCRIBUNG THE PURPOSE OF THE MODULE");
	...
}

\endcode

### HAS_SUPPORTED_FORMATS

When the module is an importer or an exporter, this fitness feature indicates whether the module provides a list containing the format(s) it supports. 
When your module fails this check, call the following method in the mpodule's constructor:

\code
public MyModule(){
	...
	this.addSupportedFormat(FORMAT_NAME, FORMAT_VERSION, FORMAT_DESCRIPTION);
	...
}

\endcode
 
### HAS_SELFTEST {#selftest}

This fitness feature indicates whether the module provides a self test. A self test is a test setup to check whether the module is runnable within a Pepper instance (similar to a system integration test). Therefore the module needs to provide a corpus as an input and an expected corpus. The output corpus then is compared to the expected corpus.
When your module fails thus check, provide an input corpus, an expected corpus and override the following method:

\code
@Override
public SelfTestDesc getSelfTestDesc() {
	return new SelfTestDesc(
			getResources().appendSegment("PATH_TO_INPUT_CORPUS"),
			getResources().appendSegment("PATH_TO_EXPECTED_CORPUS"));
}
\endcode

When your module is an importer, the input corpus path should contain a corpus in the format you want to import and the expected corpus path should contain a corpus in Salt format.
When your module is a manipulator, the input and the export corpus path corpus should contain a corpus in Salt format.
When your module is an exporter, the input corpus path should contain a corpus in the Salt format and the expected corpus path should contain a corpus in the format you want to export.

When the expected and the output corpora are in Salt format, Pepper will use the comparison of Salt modules to check wether they are isomorph. 
When the expected and the output corpora ar in any other format, Pepper will compare them by file comparison. For XML files the XMLUnit library is used.

To have more control on the comparison check the documentation in @ref org.corpus_tools.pepper.core.SelfTestDesc and override one of the following methods (which depends on your particular case):

* @ref org.corpus_tools.pepper.core.SelfTestDesc.compare(final SaltProject actualProject, final SaltProject expectedProject)
* @ref org.corpus_tools.pepper.core.SelfTestDesc.compare(final URI actualCorpusPath, final URI expectedCorpusPath)
* @ref org.corpus_tools.pepper.core.SelfTestDesc.compare(final File actualFile, final File expectedFile)
* @ref org.corpus_tools.pepper.core.SelfTestDesc.compareXML(final File actualXmlFile, final File expectedXmlFile) 		

### IS_IMPORTABLE

When the module is an importer, this fitness feature indicates whether the module implements the method @ref org.corpus_tools.pepper.modules.PepperImporter#isImportable(URI corpusPath). This method is called by Pepper and returns if a corpus is located at the given location is importable by this importer.
When your module fails this check, override the isImportable() method. The following is a snippet from the SaltXMLImporter, which determine that corpora which contain Salt files are analyzed as importable. 

\code
public Double isImportable(URI corpusPath) {
	Double retValue = 0.0;
	for (String content : sampleFileContent(corpusPath, "salt", "xml")) {
		if ((content.contains("<?xml")) && (content.contains("xmi:version=\"2.0\""))
				&& (content.contains("salt"))) {
			retValue = 1.0;
			break;
		}
	}
	return retValue;
}
\endcode

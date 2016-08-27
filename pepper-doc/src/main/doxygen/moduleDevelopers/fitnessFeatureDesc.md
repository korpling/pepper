# Fitness Check {#fitness}

The fitness check ensures, that a module fits into the Pepper platform. It checks a set of features describing whether a module implements all fields and methods which should be implemented. Some of the features are required (health features) and some are optional (fitness features). When the module does not pass at least one of the health features, the overall module fitness is set to *critical*. When all health features are passed and at least one fitness is missed, the overall module fitness is *healthy*. When the module passes all features, the overall status is *fit*.

To run the fitness check for an installed module run:
```
pepper>fitness
``` 

## Health Features

The health features are required features and ensure that a module is runnable inside Pepper.

### HAS_NAME

This health feature indicates whether a module's name is set. 
When you module fails this check, call the module's super constructor and pass the module's name:

```java
public MyModule(){
	super(MODULE_NAME);
	...
}

```

### IS_READY_TO_RUN

This health feature indicates whether a module is runnable on the Pepper platform. 
To check this, the function @ref org.corpus_tools.pepper.modules.PepperModule.isReadyToStart() is called.
When you module fails this check, it means that this method returned false.

### HAS_PASSED_SELFTEST

		/**
		 * Name of health feature determining whether the module passes the self
		 * test, if a self test is implemented.
		 */
		
### IS_IMPORTABLE_SEFTEST_DATA		
		
		/**
		 * Name of health feature determining whether the module is able to
		 * import the self test data, if a self test is implemented and module
		 * is an importer.
		 */
		
### IS_VALID_SELFTEST_DATA		
		
		/**
		 * Name of health feature determining whether the module the self test
		 * data are valid, if a self test is implemented and module is an
		 * importer or a manipulator.
		 */		

## Fitness features

The fitness features are optional and must not necessarily be passed to run a module in Pepper. But they ensure, that the module implements a kind of a standard and supports information which are helpful to the user.

### HAS_SUPPLIER_CONTACT

		/**
		 * Name of fitness feature determining whether
		 * {@link PepperModule#getSupplierContact()} is supported.
		 */

### HAS_SUPPLIER_HP

		/**
		 * Name of fitness feature determining whether
		 * {@link PepperModule#getSupplierHomepage()} is supported.
		 */

### HAS_DESCRIPTION

		/**
		 * Name of fitness feature determining whether
		 * {@link PepperModule#getDesc()} is supported
		 */

### HAS_SUPPORTED_FORMATS

		/**
		 * Name of fitness feature determining whether
		 * {@link PepperImporter#getSupportedFormats()}
		 * {@link PepperExporter#getSupportedFormats()} is supported. <br/>
		 * Only for {@link PepperImporter} and {@link PepperExporter}.
		 */

### HAS_SELFTEST

		/**
		 * Name of fitness feature determining whether the module provides a
		 * self test.
		 **/

### IS_IMPORTABLE

		/**
		 * Name of fitness feature determining whether
		 * {@link PepperImporter#isImportable(org.eclipse.emf.common.util.URI)}
		 * is implemented. <br/>
		 * Only for {@link PepperImporter}.
		 **/

package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW;

public interface PepperProperties {
	/**
	 * flag if Pepper shall measure and display the performance of the used PepperModules
	 */
	public static final String PROP_PREFIX= "pepper";
	
	/**
	 * flag if Pepper shall measure and display the performance of the used PepperModules
	 */
	public static final String PROP_COMPUTE_PERFORMANCE= PROP_PREFIX+".computePerformance";
	/**
	 * Name of the property, which determines a temprorary folder, where the framework and
	 * all modules can store temprorary data.
	 */
	public static final String PROP_TEMP_FOLDER= PROP_PREFIX+".temproraries";
	/**
	 * the maximal number of currently processed SDocument-objects
	 */
	public static final String PROP_MAX_AMOUNT_OF_SDOCUMENTS= PROP_PREFIX+".maxAmountOfProcessedSDocuments";
	/**
	 * the flag if an SDocument-object shall be removed after it was processed by all PepperModules
	 */
	public static final String PROP_REMOVE_SDOCUMENTS_AFTER_PROCESSING= PROP_PREFIX+".removeSDocumentAfterProcessing";
	
	/**
	 * This array contains all properties, which with the Pepper framework can be configured. 
	 */
	public static final String[] ALL_PROP_NAMES= {PROP_COMPUTE_PERFORMANCE, PROP_MAX_AMOUNT_OF_SDOCUMENTS, PROP_REMOVE_SDOCUMENTS_AFTER_PROCESSING};
	public static final String ENV_PEPPER_MODULE_RESOURCES="pepper.modules.resources";
	public static final String PROP_PEPPER_MODULE_RESOURCES="pepper.modules.resources";
	
}

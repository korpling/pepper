package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * Determines possible return modes of a mapping (see {@link PepperMapper#map()} and {@link PepperMapper#start()}).
 * {@link MAPPING_RESULT#FINISHED} means, mapping was successful
 * {@link MAPPING_RESULT#FAILED} means, mapping was not successful and ended with an error
 * {@link MAPPING_RESULT#FINISHED} means, mapping was successful and Mapped object {@link SDocument} or {@link SCorpus} was deleted (for instance when merging objects, and no further processing of mapped object is necessary) 
 * @author Florian Zipser
 *
 */
public enum MAPPING_RESULT {FINISHED, FAILED, DELETED}

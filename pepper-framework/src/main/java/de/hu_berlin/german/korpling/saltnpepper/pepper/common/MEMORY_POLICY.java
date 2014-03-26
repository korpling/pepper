package de.hu_berlin.german.korpling.saltnpepper.pepper.common;

import de.hu_berlin.german.korpling.saltnpepper.pepper.core.DocumentBus;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;

/**
 * Describes the main memory policy of {@link PepperJob}s. More specific, it determines the way of loading and
 * storing {@link SDocumentGraph} objects during the processing. The behavior of the {@link DocumentBus} is
 * described as follows: 
 * <ul>
 * 	<li>{@link MEMORY_POLICY#THRIFTY} - each time, a {@link PepperModule} is setting a {@link SDocument} to {@link DOCUMENT_STATUS#COMPLETED} and no other {@link PepperModule} is currently working on it, it will be send to sleep.</li>
 *  <li>{@link MEMORY_POLICY#MODERATE} - each time, a {@link PepperModule} is setting a {@link SDocument} to {@link DOCUMENT_STATUS#COMPLETED}, no other {@link PepperModule} is currently working on it and no other {@link PepperModule} is waiting on it, it will be send to sleep.</li>
 *  <li>{@link MEMORY_POLICY#GREEDY} - A {@link SDocumentGraph} will never be send to sleep. In case of the number of maximal processed documents is limited, and was reached, no further {@link SDocumentGraph}s will be imported until one was finished somehow.</li>
 * </ul>
 *   
 * @author Florian Zipser
 *
 */
public enum MEMORY_POLICY{
	THRIFTY, 
	MODERATE, 
	GREEDY
}

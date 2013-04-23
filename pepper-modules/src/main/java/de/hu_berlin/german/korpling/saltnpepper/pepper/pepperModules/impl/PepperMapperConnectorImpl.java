package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.MAPPING_RESULT;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapperConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * The class {@link PepperMapperConnectorImpl} is a communicator class between a {@link PepperModule} and a {@link PepperMapper} object. The aim of this
 * class is to provide some fields, whicch can be set by the {@link PepperMapper} and be read by the {@link PepperModule} object. It does not
 * contain any reference to the {@link PepperMapper} object. This mechanism is used, to make sure that in case of a forgotten clean up, the 
 * {@link PepperMapper} object can be removed by the java garbage collector and does not overfill the main memory. 
 * 
 * @author Florian Zipser
 *
 */
public class PepperMapperConnectorImpl implements PepperMapperConnector{

	protected volatile MAPPING_RESULT mappingResult= MAPPING_RESULT.DELETED;
	/** {@inheritDoc PepperMapperConnector#setMappingResult(MAPPING_RESULT)} **/
	@Override
	public synchronized void setMappingResult(MAPPING_RESULT mappingResult) {
		this.mappingResult= mappingResult;
		
	}
	/** {@inheritDoc PepperMapperConnector#getMappingResult()} **/
	@Override
	public MAPPING_RESULT getMappingResult() {
		return(this.mappingResult);
	}
	/** {@link SElementId} object of the {@link SCorpus} or {@link SDocument} object, which is contained by containing {@link PepperMapper}**/
	protected volatile SElementId sElementId= null;
	/**
	 * {@inheritDoc PepperMapperConnector#getSElementId()}
	 */
	public SElementId getSElementId() {
		return sElementId;
	}
	/**
	 * {@inheritDoc PepperMapperConnector#setSElementId(SElementId)}
	 */
	public void setSElementId(SElementId sElementId) {
		this.sElementId = sElementId;
	}
	

}

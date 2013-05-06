package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.MAPPING_RESULT;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * An abstract implementation of {@link PepperMapper} to be used for further derivations for specific mapping
 * purposes.
 * 
 * @author Florian Zipser
 *
 */
public class PepperMapperImpl implements PepperMapper {
	
	public PepperMapperImpl()
	{
		this.initialize();
	}
	
	/**
	 * OSGi logger for this mapper. To be removed, when abstract logging via slf4j is used.
	 * @deprecated
	 */
	private LogService logService;

	public void setLogService(LogService logService) 
	{
		this.logService = logService;
	}
	
	public LogService getLogService() 
	{
		return(this.logService);
	}

	/**
	 * {@link URI} of resource. The URI could refer a directory or a file, which can be a corpus or a document.
	 */
	protected URI resourceURI= null;
	/**
	 * {@inheritDoc PepperMapper#getResourceURI()}
	 */
	public URI getResourceURI() {
		return(resourceURI);
	}
	/**
	 * {@inheritDoc PepperMapper#setResourceURI(URI)}
	 */
	public void setResourceURI(URI resourceURI) {
		this.resourceURI= resourceURI;
	}
	/**
	 * {@link SDocument} object to be created/ fullfilled during the mapping.
	 */
	protected SDocument sDocument= null;
	/**
	 * {@inheritDoc PepperMapper#getSDocument()}
	 */
	@Override
	public SDocument getSDocument() {
		return(sDocument);
	}
	/**
	 * {@inheritDoc PepperMapper#setSDocument(SDocument)}
	 */
	@Override
	public void setSDocument(SDocument sDocument) {
		this.sDocument= sDocument;
	}
	/**
	 * {@link SCorpus} object to be created/ fullfilled during the mapping.
	 */
	protected SCorpus sCorpus= null;
	
	public SCorpus getSCorpus() {
		return sCorpus;
	}

	/**
	 * {@inheritDoc PepperMapper#setSCorpus(SCorpus)} 
	 */
	public void setSCorpus(SCorpus sCorpus) {
		this.sCorpus = sCorpus;
	}
	/**
	 * {@link PepperModuleProperties} object containing user customizations to be observed during the mapping.
	 */
	protected PepperModuleProperties props= null;
	/**
	 * {@inheritDoc PepperMapper#getProperties()} 
	 */
	public PepperModuleProperties getProperties() {
		return props;
	}
	/**
	 * {@inheritDoc PepperMapper#setProperties(PepperModuleProperties)} 
	 */
	public void setProperties(PepperModuleProperties props) {
		this.props = props;
	}

	protected volatile MAPPING_RESULT mappingResult= null;
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
	
	/**
	 * This method initializes this object and is called by the constructor.
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	protected void initialize()
	{
		
	}
	
	/**
	 * {@inheritDoc PepperMapper#setSDocument(SDocument)}
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public MAPPING_RESULT mapSDocument() {
		throw new UnsupportedOperationException("OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.");
	}
	/**
	 * {@inheritDoc PepperMapper#setSCorpus(SCorpus)}
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public MAPPING_RESULT mapSCorpus() {
		return(MAPPING_RESULT.FINISHED);
	}
	/** Stores the current progress (between 0 and 1)**/
	protected volatile Double progress= 0d;
	/**
	 * {@inheritDoc PepperMapper#getProgress()}
	 */
	@Override
	public Double getProgress() 
	{
		return(progress);
	}
	/**
	 * {@inheritDoc PepperMapper#addProgress(Double)}
	 */
	@Override
	public void addProgress(Double progress)
	{
		this.setProgress(getProgress()+ progress);
	}
	
	/**
	 * {@inheritDoc PepperMapper#setProgress(Double)}
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public void setProgress(Double progress) 
	{
		this.progress= progress;
	}
}

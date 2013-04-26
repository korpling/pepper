package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.MAPPING_RESULT;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapperController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * The class {@link PepperMapperControllerImpl} is a communicator class between a {@link PepperModule} and a {@link PepperMapper} object. The aim of this
 * class is to provide some fields, whicch can be set by the {@link PepperMapper} and be read by the {@link PepperModule} object. It does not
 * contain any reference to the {@link PepperMapper} object. This mechanism is used, to make sure that in case of a forgotten clean up, the 
 * {@link PepperMapper} object can be removed by the java garbage collector and does not overfill the main memory. 
 * 
 * @author Florian Zipser
 *
 */
public class PepperMapperControllerImpl extends Thread implements PepperMapperController{

	/**
	 * Initializes this object and sets its {@link ThreadGroup} and the name of the thread.
	 * @param threadGroup
	 * @param threadName
	 */
	public PepperMapperControllerImpl(ThreadGroup threadGroup, String threadName)
	{
		super(threadGroup,threadName);
	}
	/** mapper, controlled by this object. **/
	private PepperMapper pepperMapper= null;
	/**
	 * Sets the mapper, controlled by this object.
	 * @param pepperMapper
	 */
	@Override
	public void setPepperMapper(PepperMapper pepperMapper)
	{
		System.out.println("Set peppermapper");
		this.pepperMapper= pepperMapper;
	}
	
	/**
	 * Returns the mapper, controlled by this object.
	 * @return
	 */
	@Override
	public PepperMapper getPepperMapper()
	{
		return(pepperMapper);
	}
	
	protected volatile MAPPING_RESULT mappingResult= null;
//	/** {@inheritDoc PepperMapperConnector#setMappingResult(MAPPING_RESULT)} **/
//	@Override
//	public synchronized void setMappingResult(MAPPING_RESULT mappingResult) {
//		this.mappingResult= mappingResult;
//		
//	}
	/** {@inheritDoc PepperMapperConnector#getMappingResult()} **/
	@Override
	public MAPPING_RESULT getMappingResult() {
		if (this.getPepperMapper()== null)
			throw new PepperFWException("this.getPepperMapper() is empty, this might be a bug of pepper.");
		else return(this.getPepperMapper().getMappingResult());
	}
	/** {@link SElementId} object of the {@link SCorpus} or {@link SDocument} object, which is contained by containing {@link PepperMapper}**/
	protected volatile SElementId sElementId= null;
	/**
	 * {@inheritDoc PepperMapperConnector#getSElementId()}
	 */
	@Override
	public SElementId getSElementId() {
		return sElementId;
	}
	/**
	 * {@inheritDoc PepperMapperConnector#setSElementId(SElementId)}
	 */
	@Override
	public void setSElementId(SElementId sElementId) {
		this.sElementId = sElementId;
	}
	
//	/**
//	 * {@inheritDoc PepperMapper#getResourceURI()}
//	 */
//	public URI getResourceURI() {
//		if (this.getPepperMapper()== null)
//			throw new PepperFWException("this.getResourceURI() is empty, this might be a bug of pepper.");
//		else return(this.getPepperMapper().getResourceURI());
//	}
//	/**
//	 * {@inheritDoc PepperMapper#setResourceURI(URI)}
//	 */
//	public void setResourceURI(URI resourceURI) {
//		if (this.getPepperMapper()== null)
//			throw new PepperFWException("this.setResourceURI() is empty, this might be a bug of pepper.");
//		else this.getPepperMapper().setResourceURI(resourceURI);
//	}
	
	protected volatile Double progress= null;
	/**
	 * {@inheritDoc PepperMapperConnector#getProgress()}
	 */
	@Override
	public Double getProgress() 
	{
		return(progress);
	}
	
	/**
	 * {@inheritDoc PepperMapperConnector#setProgress(Double)}
	 */
	@Override
	public synchronized void setProgress(Double progress) 
	{
		this.progress= progress;
	}
	
	
	/**
	 * This method starts the {@link PepperMapper} object as a thread. If {@link #getSCorpus()} is not null,
	 * {@link #mapSCorpus()} is called, if {@link #getSDocument()} is not null, {@link #mapSDocument()} is called.
	 */
	@Override
	public void start()
	{
		try
		{
			this.getPepperMapper().map();
		}catch (Exception e)
		{
			//TODO make some exception handling like having an exception list in PepperMapperConnector, to which the exception is added
			e.printStackTrace();
		}
		finally
		{
			System.out.println("----------------> Set PepperMapper to null");
			//reset mapper object, in case it uses a big amount of main memory
			this.setPepperMapper(null);
		}
	}
}

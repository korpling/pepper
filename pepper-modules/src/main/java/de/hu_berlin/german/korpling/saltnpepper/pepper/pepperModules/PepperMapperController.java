package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * The interface {@link PepperMapperController} is a communicator class between a {@link PepperModule} and a {@link PepperMapper} object. The aim of this
 * class is to provide some fields, which can be set by the {@link PepperMapper} and be read by the {@link PepperModule} object. It does not
 * contain any reference to the {@link PepperMapper} object. This mechanism is used, to make sure that in case of a forgotten clean up, the 
 * {@link PepperMapper} object can be removed by the java garbage collector and does not overfill the main memory. 
 * 
 * @author Florian Zipser
 *
 */
public interface PepperMapperController extends Runnable{
	
	/**
	 * {@link Thread#join()}
	 * Waits for this thread to die. An invocation of this method behaves in exactly the same way as the invocation
	 * join(0) 
	 * @throws InterruptedException - if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when this exception is thrown.
	 */
	public void join() throws InterruptedException;
	
	/**
	 * Calls method map.
	 * Delegation of {@link Thread#start()}.
	 * 
	 * Causes this thread to begin execution; the Java Virtual Machine calls the run method of this thread.
	 * The result is that two threads are running concurrently: the current thread (which returns from the call to the start method) and the other thread (which executes its run method).
	 * It is never legal to start a thread more than once. In particular, a thread may not be restarted once it has completed execution.
	 * @throws IllegalThreadStateException - if the thread was already started.
	 */
	public void start();
	/**
	 * Returns the result of the mapping, when finished.
	 * @return mapping result
	 */
	public MAPPING_RESULT getMappingResult();
	/**
	 * Returns {@link SElementId} object of the {@link SCorpus} or {@link SDocument} object, which is contained by containing {@link PepperMapper}.
	 * @return
	 */
	public SElementId getSElementId();
	/**
	 * Set {@link SElementId} object of the {@link SCorpus} or {@link SDocument} object, which is contained by containing {@link PepperMapper}.
	 * @param sElementId
	 */
	public void setSElementId(SElementId sElementId);
	/**
	 * This method is invoked by the containing {@link PepperModule} object, to get the current progress concerning the {@link SDocument} or 
	 * {@link SCorpus} object handled by this object. A valid value return must be between 0 and 1 or null if method the {@link PepperModule} does
	 * not call the method {@link #setProgress(Double)}.<br/>
	 * The call is just delegated to {@link PepperMapper#getProgress()}
	 * @param sDocumentId identifier of the requested {@link SDocument} object.
	 */
	public Double getProgress();
	/**
	 * This method starts the {@link PepperMapper} object. If {@link #getSCorpus()} is not null,
	 * {@link #mapSCorpus()} is called, if {@link #getSDocument()} is not null, {@link #mapSDocument()} is called.
	 */
	public void map();
	/**
	 * Sets the mapper, controlled by this object.
	 * @param pepperMapper
	 */
	public void setPepperMapper(PepperMapper pepperMapper);
	
	/**
	 * Returns the mapper, controlled by this object.
	 * @return
	 */
	public PepperMapper getPepperMapper();
	
}

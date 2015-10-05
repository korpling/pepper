/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules;

import java.util.List;

import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.graph.Identifier;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;

/**
 * The interface {@link PepperMapperController} is a communicator class between
 * a {@link PepperModule} and a {@link PepperMapper} object. The aim of this
 * class is to provide some fields, which can be set by the {@link PepperMapper}
 * and be read by the {@link PepperModule} object. It does not contain any
 * reference to the {@link PepperMapper} object. This mechanism is used, to make
 * sure that in case of a forgotten clean up, the {@link PepperMapper} object
 * can be removed by the java garbage collector and does not overfill the main
 * memory.
 * 
 * @author Florian Zipser
 *
 */
public interface PepperMapperController extends Runnable {

	/**
	 * {@link Thread#join()} Waits for this thread to die. An invocation of this
	 * method behaves in exactly the same way as the invocation join(0)
	 * 
	 * @throws InterruptedException
	 *             - if any thread has interrupted the current thread. The
	 *             interrupted status of the current thread is cleared when this
	 *             exception is thrown.
	 */
	public void join() throws InterruptedException;

	/**
	 * Calls method map. Delegation of {@link Thread#start()}.
	 * 
	 * Causes this thread to begin execution; the Java Virtual Machine calls the
	 * run method of this thread. The result is that two threads are running
	 * concurrently: the current thread (which returns from the call to the
	 * start method) and the other thread (which executes its run method). It is
	 * never legal to start a thread more than once. In particular, a thread may
	 * not be restarted once it has completed execution.
	 * 
	 * @throws IllegalThreadStateException
	 *             - if the thread was already started.
	 */
	public void start();

	/**
	 * Calls method map. Delegation of {@link Thread#start()}.
	 * 
	 * {@inheritDoc Thread#setUncaughtExceptionHandler(java.lang.Thread.
	 * UncaughtExceptionHandler)}
	 */
	public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh);

	/**
	 * Returns the result of the mapping, when finished.
	 * 
	 * @return mapping result
	 */
	public DOCUMENT_STATUS getMappingResult();

	/**
	 * Returns a list of all subjects ({@link SDocument} or {@link SCorpus}) to
	 * be merged
	 * 
	 * @return a list of {@link MappingSubject}
	 */
	public List<MappingSubject> getMappingSubjects();

	/**
	 * Returns {@link Identifier} object of the {@link SCorpus} or
	 * {@link SDocument} object, which is contained by containing
	 * {@link PepperMapper}.
	 * 
	 * @return
	 */
	public Identifier getIdentifier();

	/**
	 * Set {@link Identifier} object of the {@link SCorpus} or {@link SDocument}
	 * object, which is contained by containing {@link PepperMapper}.
	 * 
	 * @param sElementId
	 */
	public void setIdentifier(Identifier sElementId);

	/**
	 * This method is invoked by the containing {@link PepperModule} object, to
	 * get the current progress concerning the {@link SDocument} or
	 * {@link SCorpus} object handled by this object. A valid value return must
	 * be between 0 and 1 or null if method the {@link PepperModule} does not
	 * call the method {@link #setProgress(Double)}.<br/>
	 * The call is just delegated to {@link PepperMapper#getProgress()}
	 * 
	 * @param sDocumentId
	 *            identifier of the requested {@link SDocument} object.
	 */
	public Double getProgress();

	/**
	 * This method starts the {@link PepperMapper} object. If
	 * {@link #getCorpus()} is not null, {@link #mapSCorpus()} is called, if
	 * {@link #getDocument()} is not null, {@link #mapSDocument()} is called.
	 */
	public void map();

	/**
	 * Sets the {@link PepperMapper}, controlled by this object.
	 * 
	 * @param pepperMapper
	 */
	public void setPepperMapper(PepperMapper pepperMapper);

	/**
	 * Returns the {@link PepperMapper}, controlled by this object.
	 * 
	 * @return
	 */
	public PepperMapper getPepperMapper();

	/**
	 * Sets the {@link PepperModule} object, which contains the
	 * {@link PepperMapperController} as a callback reference. This is
	 * necessary, that the {@link PepperMapperController} can notify the
	 * containing object that its job is done via calling
	 * {@link PepperModule#done(PepperMapperController)}
	 * 
	 * @param pepperModule
	 *            containing {@link PepperModule} object
	 */
	public void setPepperModule(PepperModule pepperModule);
	
	/** {@inheritDoc PepperJobImpl#getPermissionForProcessDoument(DocumentController)} **/
	public boolean getPermissionForProcessDoument(DocumentController controller);
}

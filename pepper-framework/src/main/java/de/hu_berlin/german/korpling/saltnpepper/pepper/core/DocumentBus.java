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
package de.hu_berlin.german.korpling.saltnpepper.pepper.core;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MEMORY_POLICY;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.DocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.ModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * This object is an implementation of a threadsafe queue to store
 * {@link DocumentController} objects. Such an object is used to connect
 * multiple {@link ModuleController} objects. An {@link DocumentController} can
 * only be removed from queue, when all output {@link ModuleController} objects
 * requested the {@link DocumentController} object. <br/>
 * 
 * This object has a list of Ids belonging to {@link ModuleController} objects,
 * which are on the output side of this queue. All these objects have to request
 * a document, before this queue can get the status finished. The same goes for
 * {@link ModuleController} objects on the input side. All these controllers
 * have to notify this queue to be finished, before the queue can notify the
 * controllers on the output side, that this queue is finished.
 * 
 * @author Florian Zipser
 * 
 */
public class DocumentBus {
	private static final Logger logger = LoggerFactory.getLogger(DocumentBus.class);

	/**
	 * Creates an object of this type and sets the passes the given input
	 * controller and output controller.
	 * 
	 * @param inputModuleControllerId
	 *            identifier of controller, which produces documents for this
	 *            bus
	 * @param outputModuleControllerId
	 *            identifier of controller, which consumes documents for this
	 *            bus
	 */
	public DocumentBus(String inputModuleControllerId, String outputModuleControllerId) {
		List<String> inputModuleControllerIds = new Vector<String>();
		inputModuleControllerIds.add(inputModuleControllerId);
		List<String> outputModuleControllerIds = new Vector<String>();
		outputModuleControllerIds.add(outputModuleControllerId);
		init(inputModuleControllerIds, outputModuleControllerIds);
	}

	/**
	 * Creates an object of this type and sets the passes the given input
	 * controller and output controller.
	 * 
	 * @param inputModuleControllerId
	 *            identifier of controller, which produces documents for this
	 *            bus
	 * @param outputModuleControllerIds
	 *            list of identifier of controller, which consumes documents for
	 *            this bus
	 */
	public DocumentBus(String inputModuleControllerId, List<String> outputModuleControllerIds) {
		List<String> inputModuleControllerIds = new Vector<String>();
		inputModuleControllerIds.add(inputModuleControllerId);
		init(inputModuleControllerIds, outputModuleControllerIds);
	}

	/**
	 * Creates an object of this type and sets the passes the given input
	 * controller and output controller.
	 * 
	 * @param inputModuleControllerIds
	 *            list of identifier of controller, which produces documents for
	 *            this bus
	 * @param outputModuleControllerId
	 *            identifier of controller, which consumes documents for this
	 *            bus
	 */
	public DocumentBus(List<String> inputModuleControllerIds, String outputModuleControllerId) {
		List<String> outputModuleControllerIds = new Vector<String>();
		outputModuleControllerIds.add(outputModuleControllerId);
		init(inputModuleControllerIds, outputModuleControllerIds);
	}

	/**
	 * Creates an object of this type and sets all passed in- and output
	 * {@link ModuleController} objects. This is important, because an
	 * {@link DocumentController} can only be removed from queue, when all
	 * output {@link ModuleController} objects requested the
	 * {@link DocumentController} object.
	 * 
	 * @param inputModuleControllerIds
	 *            list of identifier of controller, which produces documents for
	 *            this bus
	 * @param outputModuleControllerIds
	 *            list of identifier of controller, which consumes documents for
	 *            this bus
	 */
	public DocumentBus(List<String> inputModuleControllerIds, List<String> outputModuleControllerIds) {
		init(inputModuleControllerIds, outputModuleControllerIds);
	}

	/**
	 * Creates an object of this type and passes all output
	 * {@link ModuleController} objects. This is important, because an
	 * {@link DocumentController} can only be removed from queue, when all
	 * output {@link ModuleController} objects requested the
	 * {@link DocumentController} object.
	 * 
	 * @param outputs
	 */
	protected void init(List<String> inputModuleControllerIds, List<String> outputModuleControllerIds) {
		if ((inputModuleControllerIds == null) || (inputModuleControllerIds.isEmpty()))
			throw new PepperFWException("Cannot create a document bus with an empty list of input module controllers.");
		if ((outputModuleControllerIds == null) || (outputModuleControllerIds.isEmpty()))
			throw new PepperFWException("Cannot create a document bus with an empty list of output module controllers.");

		this.inputModuleControllerIds = new Vector<String>();
		for (String inputControllerId : inputModuleControllerIds) {
			this.inputModuleControllerIds.add(inputControllerId);
		}
		this.outputModuleControllerIds = new Vector<String>();

		for (String outputControllerId : outputModuleControllerIds) {
			this.outputModuleControllerIds.add(outputControllerId);
		}
		this.initDocumentBus(outputModuleControllerIds);

		StringBuilder str = new StringBuilder();
		if ((getInputControllerIds() != null) && (getInputControllerIds().size() != 0)) {
			int i = 0;
			for (String id : getInputControllerIds()) {
				i++;
				str.append(id);
				if (i < getInputControllerIds().size())
					str.append(", ");
			}
		} else {
			str.append("EMPTY");
		}

		str.append(" ==> ");
		if ((getOutputControllerIds() != null) && (getOutputControllerIds().size() != 0)) {
			int i = 0;
			for (String id : getOutputControllerIds()) {
				str.append(id);
				i++;
				if (i < getOutputControllerIds().size())
					str.append(", ");
			}
		} else
			str.append("EMPTY");
		id = str.toString();
	}

	/**
	 * A list of Ids belonging to {@link ModuleController} objects, which are on
	 * the output side of this queue. All these objects have to request a
	 * document, before this queue can get the status finished.
	 **/
	protected List<String> outputModuleControllerIds = null;

	/**
	 * Returns a list of Ids belonging to {@link ModuleController} objects,
	 * which are on the output side of this queue. All these objects have to
	 * request a document, before this queue can get the status finished.
	 * 
	 * @return list of all ids
	 */
	public List<String> getOutputControllerIds() {
		return (outputModuleControllerIds);
	}

	/**
	 * A list of Ids belonging to {@link ModuleController} objects on the input
	 * side. All these controllers have to notify this queue to be finished,
	 * before the queue can notify the controllers on the output side, that this
	 * queue is finished.
	 */
	protected List<String> inputModuleControllerIds = null;

	/**
	 * Returns a list of Ids belonging to {@link ModuleController} objects on
	 * the input side. All these controllers have to notify this queue to be
	 * finished, before the queue can notify the controllers on the output side,
	 * that this queue is finished.
	 * 
	 * @return list of all ids
	 */
	public List<String> getInputControllerIds() {
		return (inputModuleControllerIds);
	}

	/**
	 * Id of this object.
	 */
	protected String id = null;

	/**
	 * Creates an id for this object. the id consists of the in- and output
	 * {@link DocumentController} objects.
	 * 
	 * @return
	 */
	public String getId() {
		return (id);
	}

	/** The {@link PepperJobImpl} object containing this object **/
	private PepperJobImpl pepperJob = null;

	/**
	 * Returns the {@link PepperJob} object containing this object
	 * 
	 * @return {@link PepperJob} object containing this
	 */
	public PepperJobImpl getPepperJob() {
		return pepperJob;
	}

	/**
	 * Sets the {@link PepperJob} object containing this object
	 * 
	 * @param pepperJob
	 *            {@link PepperJob} object containing this
	 */
	public void setPepperJob(PepperJobImpl pepperJob) {
		this.pepperJob = pepperJob;
	}

	/**
	 * This table is the central management object of the {@link DocumentBus}.
	 * This table relates a queue containing document controllers to a
	 * identifier of a {@link ModuleController} object. Key= identifier, value=
	 * queue
	 */
	protected volatile Hashtable<String, ConcurrentLinkedQueue<DocumentController>> documentBus = null;

	/**
	 * Returns the table, which is the central management object of the
	 * {@link DocumentBus}. This table relates a queue containing document
	 * controllers to a identifier of a {@link ModuleController} object. Key=
	 * identifier, value= queue
	 * 
	 * @return
	 */
	public Hashtable<String, ConcurrentLinkedQueue<DocumentController>> getDocumentBus() {
		return (documentBus);
	}

	/**
	 * Initializes the table, which is the central management object of the
	 * {@link DocumentBus}. This table relates a queue containing document
	 * controllers to a identifier of a {@link ModuleController} object. Key=
	 * identifier, value= queue. <br/>
	 * Please take care not to call this method, when this object is already
	 * started, since this will confuse the started process.
	 * 
	 * @param outputModuleControllerIds
	 *            a list of ids of the {@link ModuleController} objects on the
	 *            output side.
	 */
	protected void initDocumentBus(List<String> outputModuleControllerIds) {
		documentBus = new Hashtable<String, ConcurrentLinkedQueue<DocumentController>>();
		for (String controllerId : outputModuleControllerIds) {
			ConcurrentLinkedQueue<DocumentController> queue = new ConcurrentLinkedQueue<DocumentController>();
			documentBus.put(controllerId, queue);
		}
	}

	protected volatile MEMORY_POLICY memPolicy = MEMORY_POLICY.MODERATE;

	/**
	 * Returns the set memory policy.
	 * 
	 * @return
	 */
	public MEMORY_POLICY getMemPolicy() {
		return memPolicy;
	}

	/**
	 * Sets the memory policy. Don't change the memory policy, when job was
	 * started.
	 * 
	 * @param memPolicy
	 */
	public void setMemPolicy(MEMORY_POLICY memPolicy) {
		this.memPolicy = memPolicy;
	}

	/**
	 * This lock is used for creating the condition
	 * {@link #waitUntilAllDocumentsArePut} to let the {@link #pop(String)}
	 * method wait until all documents have been put.
	 */
	protected ReentrantLock lock = new ReentrantLock(true);
	/**
	 * Used to let the {@link #pop(String)} method wait until all documents have
	 * been put.
	 */
	protected Condition waitUntilAllDocumentsArePut = lock.newCondition();

	/**
	 * Sets the entry corresponding to given id to status finished, which means,
	 * that when all listed {@link ModuleController} objects on input side, are
	 * set to status finish, this queue is finished and no further documents are
	 * passed to {@link ModuleController} objects on output side.
	 * 
	 * @param inputControllerId
	 *            id of controller to be set to finish.
	 */
	public void finish(String inputControllerId) {
		if ((inputControllerId == null) || (inputControllerId.isEmpty()))
			throw new PepperFWException("Cannot finish PepperModuleController for document queue, because given id is null.");
		if (!inputModuleControllerIds.contains(inputControllerId))
			throw new PepperFWException("Cannot finish PepperModuleController for document queue, because  to document queue, because the given id '" + inputControllerId + "' is not contained in input list of document queue.");

		lock.lock();
		try {
			inputModuleControllerIds.remove(inputControllerId);
			if (inputModuleControllerIds.size() == 0) {
				// notifies condition, that no further documents will come
				waitUntilAllDocumentsArePut.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Returns true, whether all input {@link ModuleController} objects called
	 * the {@link #finish(String)} method and set this object to status finish.
	 * 
	 * @return if all input controllers called finished
	 */
	public boolean isFinished() {
		if (inputModuleControllerIds.isEmpty())
			return (true);
		else
			return false;
	}

	/**
	 * Adds the given {@link DocumentController} object to the queue.
	 * 
	 * @param documentController
	 *            the {@link DocumentController} object to be added to the
	 *            queue.
	 */
	public void put(DocumentController documentController) {
		if (documentController == null)
			throw new PepperFWException("Cannot add a null value as DocumentController into documentBus.");
		if (logger.isDebugEnabled()) {
			logger.debug("[Pepper] new document '{}' added to document bus. Following documents are waiting in document bus '{}': '{}'... ", documentController.getGlobalId(), getId(), getDocumentBus().values());
		}

		// TODO if possible send documents to sleep, before waiting for lock,
		// otherwise a deadlock could occur
		if (MEMORY_POLICY.THRIFTY.equals(getMemPolicy())) {
			if (getPepperJob() != null) {
				// next line is inside of block, for not causing failing test
				// cases
				documentController.sendToSleep();
				getPepperJob().releaseDocument(documentController);
			}
		} else if (MEMORY_POLICY.MODERATE.equals(getMemPolicy())) {
			// if (!lock.hasWaiters(waitUntilAllDocumentsArePut))
			{// check if some module controllers are waiting, if no, send new
				// document to sleep
				if (getPepperJob() != null) {
					// next line is inside of block, for not causing failing
					// test cases
					documentController.sendToSleep();
					getPepperJob().releaseDocument(documentController);
				}
			}
		}

		lock.lock();
		if (logger.isTraceEnabled()) {
			logger.trace("[Pepper] blocking lock for new document '{}' in document bus {}. ", documentController.getGlobalId(), getId());
		}
		try {
			// add new document controller to all queues
			Collection<ConcurrentLinkedQueue<DocumentController>> queues = getDocumentBus().values();
			for (ConcurrentLinkedQueue<DocumentController> queue : queues) {
				queue.add(documentController);
			}

			// if (MEMORY_POLICY.THRIFTY.equals(getMemPolicy())){
			// if (getPepperJob()!= null){
			// //next line is inside of block, for not causing failing test
			// cases
			// documentController.sendToSleep();
			// getPepperJob().releaseDocument();
			// }
			// } else if (MEMORY_POLICY.MODERATE.equals(getMemPolicy())){
			// if (!lock.hasWaiters(waitUntilAllDocumentsArePut))
			// {//check if some module controllers are waiting, if no, send new
			// document to sleep
			// if (getPepperJob()!= null){
			// //next line is inside of block, for not causing failing test
			// cases
			// documentController.sendToSleep();
			// getPepperJob().releaseDocument();
			// }
			// }
			// }
			// notifies condition, that new document is ready to be processed
			waitUntilAllDocumentsArePut.signalAll();
		} finally {
			lock.unlock();
			if (logger.isTraceEnabled()) {
				logger.trace("[Pepper] unlocked lock for new document '{}' in document bus '{}'. ", documentController.getGlobalId(), getId());
			}
		}
	}

	/**
	 * Returns a {@link DocumentController} object, which is on first place of
	 * the internal queue (regarding the FIFO principle). When all registered
	 * {@link ModuleController} objects popped the {@link DocumentController}
	 * object, it will be removed from internal queue. <br/>
	 * If the document, which is the one to be taken in queue is in sleep mode,
	 * it will be woken up, if the {@link PepperJobImpl} gives the permission.
	 * Otherwise, this method waits for the permission. <br/>
	 * Caution, call this method can take some time, since it waits for two
	 * conditions:
	 * <ol>
	 * <li>a document is waiting in the queue, and the preceding
	 * {@link PepperModule} has not finished all documents</li>
	 * <li>the Pepper framework (more precisely the {@link PepperJobImpl}) gives
	 * the permission to wake up the document, if it was in sleep mode
	 * <li>
	 * </ol>
	 * 
	 * @param moduleController
	 *            object requesting the {@link DocumentController} object
	 * @return head of the queue.
	 */
	public DocumentController pop(String outputControllerId) {
		return (pop(outputControllerId, false));
	}

	/**
	 * Returns a {@link DocumentController} object, which is on first place of
	 * the internal queue (regarding the FIFO principle). When all registered
	 * {@link ModuleController} objects popped the {@link DocumentController}
	 * object, it will be removed from internal queue. <br/>
	 * If the document, which is the one to be taken in queue is in sleep mode,
	 * it will be woken up, if the {@link PepperJobImpl} gives the permission.
	 * Otherwise, this method waits for the permission. <br/>
	 * Caution, call this method can take some time, since it waits for two
	 * conditions:
	 * <ol>
	 * <li>a document is waiting in the queue, and the preceding
	 * {@link PepperModule} has not finished all documents</li>
	 * <li>the Pepper framework (more precisely the {@link PepperJobImpl}) gives
	 * the permission to wake up the document, if it was in sleep mode
	 * <li>
	 * </ol>
	 * In contrast to {@link #pop(String)}, if
	 * <code>ignorePermissionForDocument</code> is set to true this method
	 * returns a {@link DocumentController} object even if the {@link PepperJob}
	 * permission does not allow to process a further document. This mechanism
	 * can be used, if a {@link PepperModule} has an own control mechanism of
	 * sending {@link SDocument}s to sleep.
	 * 
	 * @param outputControllerId
	 *            object requesting the {@link DocumentController} object
	 * @param ignorePermissionForDocument
	 *            if set, a document will be returned even if the Pepper job
	 *            gives no permission
	 * @return head of the queue.
	 */
	public DocumentController pop(String outputControllerId, boolean ignorePermissionForDocument) {
		DocumentController documentController = null;
		if (logger.isDebugEnabled()) {
			logger.debug("[Pepper] remove document for controller {} from document bus. Following documents are still waiting in bus: '{}'... ", outputControllerId, getDocumentBus().values());
		}
		lock.lock();
		logger.trace("[Pepper] blocking lock for remove document for controller {} in document bus {}. ", outputControllerId, getId());
		ConcurrentLinkedQueue<DocumentController> queue = getDocumentBus().get(outputControllerId);
		if (queue == null) {
			throw new PepperFWException("Document bus '" + getId() + "' cannot pop a document controller, because there is no entry for module controller '" + outputControllerId + "'.");
		}
		try {
			if ((queue.size() == 0) && (!this.isFinished())) {
				if ((queue.size() == 0) && (!this.isFinished())) {
					logger.trace("[Pepper] start waiting for condition 'waitUntilAllDocumentsArePut' in DocumentBus {} in pop({}). ", getId(), outputControllerId);
					waitUntilAllDocumentsArePut.await();
					logger.trace("[Pepper] ended waiting for condition 'waitUntilAllDocumentsArePut' in DocumentBus {} in pop({}). ", getId(), outputControllerId);
				}
			}

			documentController = queue.poll();

			if (!ignorePermissionForDocument) {
				if (documentController != null)
					if ((documentController != null) && (documentController.isAsleep())) {
						if (getPepperJob() != null) {
							logger.debug("[Pepper] waiting for permission to wake up document '{}' for module '{}' in document bus '{}'... ", (documentController.getGlobalId() != null) ? documentController.getGlobalId() : "NULL", outputControllerId, getId());
							getPepperJob().getPermissionForProcessDoument(documentController);
						}
						documentController.awake();
					}
			}
		} catch (InterruptedException e) {
			throw new PepperFWException("Something went wrong, when waiting for lock 'waitUntilAllDocumentsArePut'.", e);
		} finally {
			lock.unlock();
			if (logger.isTraceEnabled()) {
				logger.trace("[Pepper] unlocked lock for remove document for controller {} in document bus '{}'and return document '{}'. ", outputControllerId, getId(), (documentController != null) ? documentController.getGlobalId() : "NULL");
			}
		}
		return (documentController);
	}

	/**
	 * returns a String representation of this object. <strong>Note, that this
	 * String cannot be used for serialization/deserialization
	 * purposes.</strong>
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("{");
		str.append(getId());
		str.append("}");
		return (str.toString());
	}
}

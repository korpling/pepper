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
package org.corpus_tools.pepper.core;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.modules.DocumentController;
import org.corpus_tools.pepper.modules.ModuleController;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.graph.Identifier;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stores the global status of a {@link SDocument}. And a list of tuples for all
 * Modules and their current status. Can send {@link SDocumentGraph}s contained
 * in given {@link SDocument} to sleep and wake them.
 * 
 * @author Florian Zipser
 */
public class DocumentControllerImpl implements DocumentController {
	private static final Logger logger = LoggerFactory.getLogger(DocumentControllerImpl.class);

	/**
	 * Initializes an object of this class. The default value of
	 * {@link DOCUMENT_STATUS} will be set to
	 * {@link DOCUMENT_STATUS#NOT_STARTED}.
	 */
	public DocumentControllerImpl() {
		this(null);
	}

	/**
	 * Initializes an object of this class. The default value of
	 * {@link DOCUMENT_STATUS} will be set to
	 * {@link DOCUMENT_STATUS#NOT_STARTED}.
	 * 
	 * @param sDocument
	 *            {@link SDocument} object which is controlled by this object
	 */
	public DocumentControllerImpl(SDocument sDocument) {
		setDocument(sDocument);
		globalStatus = DOCUMENT_STATUS.NOT_STARTED;
	}

	/**
	 * The {@link SDocument} object, to which this object belongs to
	 */
	private volatile SDocument sDocument = null;

	/** {@inheritDoc} **/
	@Override
	public SDocument getDocument() {
		return sDocument;
	}

	/** {@inheritDoc} **/
	@Override
	public void setDocument(SDocument sDocument) {
		this.sDocument = sDocument;
	}

	/** {@inheritDoc} **/
	@Override
	public Identifier getsDocumentId() {
		if (getDocument() == null)
			return (null);
		return getDocument().getIdentifier();
	}

	/** {@inheritDoc} **/
	@Override
	public String getGlobalId() {
		String globalId = SaltUtil.getGlobalId(getsDocumentId());
		return (globalId);
	}

	/**
	 * If set to true, the garbage collector is called after a {@link SDocument}
	 * was send to sleep.
	 */
	private boolean callGC = false;

	/**
	 * Sets whether the garbage collector should be called after sending a
	 * {@link SDocument} object to sleep.
	 * 
	 * @param callGC
	 */
	public void setCallGC(boolean callGC) {
		this.callGC = callGC;
	}

	// ========================================== start: sleep mechanism
	/**
	 * Location, where to store {@link SDocumentGraph} when {@link #sleep()} was
	 * called or load when {@link #awake()} was called.
	 **/
	private URI location = null;

	/** {@inheritDoc} **/
	@Override
	public URI getLocation() {
		return location;
	}

	/** {@inheritDoc} **/
	@Override
	public void setLocation(URI location) {
		this.location = location;
	}

	/**
	 * Determines whether the contained {@link SDocumentGraph} was send to
	 * sleep.
	 **/
	private boolean aSleep = false;

	/** {@inheritDoc} **/
	@Override
	public boolean isAsleep() {
		return (aSleep);
	}

	/**
	 * A lock object which secures, that {@link SDocumentGraph} can not be send
	 * to sleep and wake up at the same time.
	 */
	private Lock sleepLock = new ReentrantLock();

	/**
	 * Sends the contained {@link SDocument} to sleep, which means, it will be
	 * stored to local disk and removed from main memory, by calling
	 * {@link SDocument#saveSDocumentGraph(org.eclipse.emf.common.util.URI)}.
	 * The counterpart to this method is {@link #awake()}. Both methods are
	 * synchronized.
	 */
	protected void sleep() {
		if (getDocument() == null) {
			throw new PepperFWException("Cannot send SDocument to sleep, since no " + SDocument.class.getSimpleName() + " is set.");
		}
		if (getLocation() == null) {
			throw new PepperFWException("Cannot send SDocument to sleep, since no location to store document '" + getsDocumentId() + "' is set.");
		}
		sleepLock.lock();
		try {
			aSleep = true;
			if (getDocument().getDocumentGraph() != null) {
				numOfNodes = getDocument().getDocumentGraph().getNodes().size();
				numOfRelations = getDocument().getDocumentGraph().getRelations().size();
				getDocument().saveDocumentGraph(getLocation());
				logger.debug("[Pepper] Sent document '{}' to sleep. ", SaltUtil.getGlobalId(getsDocumentId()));
			}
		} finally {
			sleepLock.unlock();
		}

		if (callGC) {
			System.gc();
		}
	}

	/** {@inheritDoc} **/
	@Override
	public void sendToSleep() {
		if (getNumOfProcessingModules() == 0) {
			sleep();
		}
	}

	/** {@inheritDoc} **/
	@Override
	public void sendToSleep_FORCE() {
		sleep();
	}

	/** {@inheritDoc} **/
	@Override
	public void awake() {
		if (getDocument() == null) {
			throw new PepperFWException("Cannot send SDocument to sleep, since no " + SDocument.class.getSimpleName() + " is set.");
		}
		sleepLock.lock();
		try {
			getDocument().loadDocumentGraph(getLocation());
			aSleep = false;
			logger.debug("[Pepper] woke up document '{}'. ", SaltUtil.getGlobalId(getsDocumentId()));
		} catch (Exception e) {
			throw new PepperFWException("Cannot awake the document '" + getsDocumentId().getId() + "', because an exception occured, loading it from location '" + getLocation() + "'. ", e);
		} finally {
			sleepLock.unlock();
		}
	}

	// ========================================== end: sleep mechanism

	/**
	 * Returns a textual representation of this object. <strong>Note: This
	 * representation could not be used for serialization/deserialization
	 * purposes.</strong>
	 * 
	 * @return textual representation
	 */
	public String toString() {
		return (getGlobalId() + ": " + getGlobalStatus().toString());
	}

	/**
	 * A list of all {@link ModuleControllerImpl} objects, the here contained
	 * {@link SDocument} object has to pass.
	 **/
	protected volatile List<ModuleControllerImpl> moduleControllers = null;

	/** {@inheritDoc} **/
	@Override
	public List<ModuleControllerImpl> getModuleControllers() {
		if (moduleControllers == null) {
			synchronized (this) {
				if (moduleControllers == null) {
					moduleControllers = new Vector<ModuleControllerImpl>();
				}
			}
		}
		return (moduleControllers);
	}

	/**
	 * Determines, if this object is already 'started', which means if
	 * {@link #updateStatus(String, DOCUMENT_STATUS)} has been called.
	 **/
	protected volatile boolean isStarted = false;

	/** {@inheritDoc} **/
	@Override
	public synchronized void addModuleControllers(ModuleControllerImpl moduleController) {
		if (isStarted)
			throw new PepperFWException("Cannot add any further module controllers, since the processing of document '" + getGlobalId() + "' has already been started.");
		getModuleControllers().add(moduleController);
		getDetailedStatuses().put(moduleController.getId(), new DetailedStatus());
	}

	/**
	 * A small helper class, to store the status and the processing a specific
	 * {@link PepperModule} needed for the {@link SDocument} object contained by
	 * the container {@link DocumentControllerImpl} object.
	 * 
	 * @author Florian Zipser
	 *
	 */
	protected static class DetailedStatus {
		public DetailedStatus() {
		}

		private DOCUMENT_STATUS status = DOCUMENT_STATUS.NOT_STARTED;

		public DOCUMENT_STATUS getStatus() {
			return status;
		}

		public synchronized void setStatus(DOCUMENT_STATUS status) {
			if (DOCUMENT_STATUS.IN_PROGRESS.equals(status)) {
				startTime = System.nanoTime();
			} else if ((DOCUMENT_STATUS.COMPLETED.equals(status)) || (DOCUMENT_STATUS.FAILED.equals(status)) || (DOCUMENT_STATUS.DELETED.equals(status))) {
				processingTime = (System.nanoTime() - startTime) / 1000000;
			}
			this.status = status;
		}

		private Long startTime = null;
		private Long processingTime = null;

		public Long getProcessingTime() {
			Long time = null;
			if ((processingTime == null) && (startTime != null))
				time = (System.nanoTime() - startTime) / 1000000;
			else
				return processingTime;
			return (time);
		}
	}

	/**
	 * A map relating all statuses of the {@link SDocument} contained in this
	 * {@link DocumentControllerImpl} corresponding to to
	 * {@link ModuleControllerImpl} objects to be processed and details to their
	 * statuses.
	 **/
	private volatile Map<String, DetailedStatus> detailedStatuses = null;

	/**
	 * A map relating all statuses of the {@link SDocument} contained in this
	 * {@link DocumentControllerImpl} corresponding to to
	 * {@link ModuleControllerImpl} objects to be processed and details to their
	 * statuses.
	 * 
	 * @return map
	 */
	private Map<String, DetailedStatus> getDetailedStatuses() {
		if (detailedStatuses == null) {
			synchronized (this) {
				if (detailedStatuses == null)
					detailedStatuses = new Hashtable<String, DocumentControllerImpl.DetailedStatus>();
			}
		}
		return (detailedStatuses);
	}

	/**
	 * Stores the number of {@link PepperModule}, currently processing this
	 * {@link DocumentControllerImpl}. Number is determined by method
	 * {@link #updateStatus(String, DOCUMENT_STATUS)}
	 **/
	protected volatile int numberOfProcessingModules = 0;

	/** {@inheritDoc} **/
	@Override
	public int getNumOfProcessingModules() {
		return (numberOfProcessingModules);
	}

	/** stores the currently active document controller **/
	protected volatile ModuleController currentModuleController = null;

	/** {@inheritDoc} */
	@Override
	public ModuleController getCurrentModuleController() {
		return currentModuleController;
	}

	/** {@inheritDoc} **/
	@Override
	public void updateStatus(ModuleController moduleController, DOCUMENT_STATUS status) {
		if (moduleController == null) {
			throw new PepperFWException("Can not update status for document '" + getGlobalId() + "', because the given identifier for module conttroller is empty.");
		}
		if (status == null) {
			throw new PepperFWException("Can not update status for document '" + getGlobalId() + "', because the passed status is null.");
		}
		synchronized (moduleController) {
			DetailedStatus detailedStatus = getDetailedStatuses().get(moduleController.getId());
			if (detailedStatus == null)
				throw new PepperFWException("Can not update status for document '" + getGlobalId() + "', because the passed identifier for module controller '" + moduleController.getId() + "' is not registered.");
			isStarted = true;
			if ((DOCUMENT_STATUS.NOT_STARTED.equals(detailedStatus.getStatus())) && (DOCUMENT_STATUS.IN_PROGRESS.equals(status))) {
				numberOfProcessingModules++;
				detailedStatus.setStatus(status);
				currentModuleController = moduleController;
			} else if ((DOCUMENT_STATUS.IN_PROGRESS.equals(detailedStatus.getStatus())) && ((DOCUMENT_STATUS.COMPLETED.equals(status)) || (DOCUMENT_STATUS.FAILED.equals(status)) || (DOCUMENT_STATUS.DELETED.equals(status)))) {
				numberOfProcessingModules--;
				if (getNumOfProcessingModules() < 0) {
					throw new PepperFWException("The number of " + PepperModule.class.getSimpleName() + " for this " + DocumentControllerImpl.class.getSimpleName() + " object '" + getGlobalId() + "' was set to a value less than 0.");
				}
				currentModuleController = null;
				detailedStatus.setStatus(status);
			} else {
				throw new PepperFWException("Cannot update status of sDocument '" + getGlobalId() + "' for module controller '" + moduleController + "', because the level of current status '" + detailedStatus.getStatus() + "' is higher or equal to the given status '" + status + "'.");
			}
			this.updateGlobalStatus();
		}
	}

	/**
	 * Represents the global status of a single document computed out of all
	 * Pepper modules, which has to process that {@link SDocument}. The global
	 * status represents the minimal status of all Pepper modules. The global
	 * status is set to {@link DOCUMENT_STATUS#COMPLETED} when each module has
	 * set the status to PEPPER_SDOCUMENT_STATUS#COMPLETED.
	 */
	private volatile DOCUMENT_STATUS globalStatus = null;

	/** {@inheritDoc} **/
	@Override
	public DOCUMENT_STATUS getGlobalStatus() {
		return globalStatus;
	}

	/**
	 * Updates the global status of this object. This will be done, by checking
	 * all the status of the contained {@link DetailedStatus} objects. The logic
	 * is:
	 * <ul>
	 * <li>{@link DOCUMENT_STATUS#DELETED} - when one document has status
	 * {@link DOCUMENT_STATUS#DELETED}
	 * <li>
	 * <li>{@link DOCUMENT_STATUS#IN_PROGRESS} - when one document has status
	 * {@link DOCUMENT_STATUS#IN_PROGRESS}
	 * <li>
	 * <li>{@link DOCUMENT_STATUS#FAILED} - when one document has status
	 * {@link DOCUMENT_STATUS#FAILED}
	 * <li>
	 * <li>{@link DOCUMENT_STATUS#COMPLETED} - when one document has status
	 * {@link DOCUMENT_STATUS#DELETED}
	 * <li>
	 * </ul>
	 */
	private synchronized void updateGlobalStatus() {
		DOCUMENT_STATUS newGlobalStatus = null;
		boolean completedExists = false;
		boolean notStartedExists = false;
		for (DetailedStatus detailedStatus : getDetailedStatuses().values()) {
			if (DOCUMENT_STATUS.DELETED.equals(detailedStatus.getStatus())) {
				// if one PepperModuleController says deleted, status is deleted
				newGlobalStatus = DOCUMENT_STATUS.DELETED;
				break;
			}// if one PepperMOduleController says deleted, status is deleted
			else if (DOCUMENT_STATUS.IN_PROGRESS.equals(detailedStatus.getStatus())) {
				// if one PepperModuleController says IN_PROCESS, status is
				// IN_PROCESS
				newGlobalStatus = DOCUMENT_STATUS.IN_PROGRESS;
				break;
			}// if one PepperModuleController says IN_PROCESS, status is
				// IN_PROCESS
			else if (DOCUMENT_STATUS.FAILED.equals(detailedStatus.getStatus())) {
				// if one PepperModuleController says FAILED, status is FAILED
				newGlobalStatus = DOCUMENT_STATUS.FAILED;
				break;
			}// if one PepperModuleController says FAILED, status is FAILED
			else if (DOCUMENT_STATUS.COMPLETED.equals(detailedStatus.getStatus())) {
				completedExists = true;
			} else if (DOCUMENT_STATUS.NOT_STARTED.equals(detailedStatus.getStatus())) {
				notStartedExists = true;
			}
		}
		if (newGlobalStatus == null) {
			if ((notStartedExists) && (completedExists)) {
				globalStatus = DOCUMENT_STATUS.IN_PROGRESS;
			} else if (completedExists) {
				globalStatus = DOCUMENT_STATUS.COMPLETED;
			}
		} else
			globalStatus = newGlobalStatus;
	}

	/** {@inheritDoc} **/
	@Override
	public double getProgress() {
		if (DOCUMENT_STATUS.DELETED.equals(globalStatus))
			return (1.0);
		else if (DOCUMENT_STATUS.FAILED.equals(globalStatus))
			return (1.0);
		else if (DOCUMENT_STATUS.COMPLETED.equals(globalStatus))
			return (1.0);
		else {
			double p_total = 0;
			synchronized (this) {
				for (ModuleControllerImpl moduleController : getModuleControllers()) {
					Double newVal = moduleController.getProgress(getGlobalId());
					if (newVal == null) {
						DetailedStatus detailedStatus = getDetailedStatuses().get(moduleController.getId());
						if (DOCUMENT_STATUS.IN_PROGRESS.equals(detailedStatus.getStatus())) {
							newVal = 0d;
						} else if (DOCUMENT_STATUS.NOT_STARTED.equals(detailedStatus.getStatus())) {
							newVal = 0d;
						} else if (DOCUMENT_STATUS.COMPLETED.equals(detailedStatus.getStatus())) {
							newVal = 1d;
						}
					}
					if (newVal != null && 0d != newVal) {
						p_total = p_total + (newVal / Double.valueOf(moduleControllers.size()));
					}
				}
			}
			return (p_total);
		}
	}

	/** {@inheritDoc} **/
	@Override
	public Long getProcessingTime() {
		Long time = 0L;
		synchronized (this) {
			for (DetailedStatus detailedStatus : detailedStatuses.values()) {
				time = time + detailedStatus.getProcessingTime();
			}
		}
		return (time);
	}

	/**
	 * Returns a formated string containing a report of the progress status of
	 * the contained document.
	 * 
	 * @return a report of the progress status
	 */
	public String getStatusReport() {
		StringBuilder retVal = new StringBuilder();
		retVal.append(getsDocumentId());
		retVal.append("..........");
		retVal.append("(");
		retVal.append(getProcessingTime());
		retVal.append(")");
		for (ModuleController moduleController : getModuleControllers()) {
			retVal.append("\t");
			retVal.append(moduleController.getPepperModule().getName());
			retVal.append("\t\t");
			retVal.append(moduleController.getProgress(getGlobalId()));
		}
		return (retVal.toString());
	}

	int numOfNodes = 0;
	int numOfRelations = 0;

	/** {@inheritDoc} **/
	@Override
	public int getSize_nodes() {
		return numOfNodes;
	}

	/** {@inheritDoc} **/
	@Override
	public int getSize_relations() {
		return numOfRelations;
	}
}

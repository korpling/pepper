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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.exceptions.PepperInActionException;
import org.corpus_tools.pepper.impl.BeforeAfterAction;
import org.corpus_tools.pepper.modules.DocumentController;
import org.corpus_tools.pepper.modules.ModuleController;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An object of this types contains a {@link PepperModule} and handles as a
 * connector between such an object and the Pepper framework.
 * 
 * @author Florian Zipser
 */
public class ModuleControllerImpl implements ModuleController {
	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);
	/**
	 * a logger instance for all messages belonging to the module. This enables,
	 * to control this logger in conf file with the modules name. The logger is
	 * overwritten in {@link #setPepperModule(PepperModule)}.
	 **/
	private Logger mLogger = LoggerFactory.getLogger(ModuleController.class);

	/**
	 * Creates an instance of {@link ModuleControllerImpl}. Sets the internal id
	 * to the passed one. <strong>Note: the id is unchangeable.</strong>
	 * 
	 * @param id
	 *            identifier of this object. Id can neither be null nor empty.
	 */
	public ModuleControllerImpl(String id) {
		if (id == null)
			throw new PepperFWException(
					"Cannot create an instance of PepperModuleController, because the passed identifier is null.");
		if (id.isEmpty())
			throw new PepperFWException(
					"Cannot create an instance of PepperModuleController, because the passed identifier is empty.");
		this.id = id;
	}

	/** id of this object **/
	protected String id = null;

	/** {@inheritDoc} **/
	@Override
	public String getId() {
		return (id);
	}

	/**
	 * The {@link PepperModule} object, this controller object is observing.
	 **/
	protected PepperModule pepperModule;

	/** {@inheritDoc} **/
	@Override
	public PepperModule getPepperModule() {
		return pepperModule;
	}

	/**
	 * Sets the {@link PepperModule} object, this controller object is
	 * observing. Also sets the inverse method
	 * {@link PepperModule#setPepperModuleController_basic(ModuleControllerImpl)}
	 * 
	 * @param newPepperModule
	 *            new object to observe
	 **/
	@Override
	public void setPepperModule(PepperModule newPepperModule) {
		setPepperModule_basic(newPepperModule);
		newPepperModule.setPepperModuleController_basic(this);
		// overwrites the logger, to listen to settings for the modules logger
		mLogger = LoggerFactory.getLogger(getPepperModule().getName());
	}

	/**
	 * Sets the {@link PepperModule} object, this controller object is
	 * observing.
	 * 
	 * @param newPepperModule
	 *            new object to observe
	 **/
	@Override
	public void setPepperModule_basic(PepperModule newPepperModule) {
		if (newPepperModule == null)
			throw new PepperFWException("Cannot set an empty PepperModule object to module controller.");
		pepperModule = newPepperModule;
	}

	/** The {@link PepperJobImpl} object, which is container to this object. */
	private PepperJobImpl job = null;

	/** {@inheritDoc} **/
	@Override
	public PepperJobImpl getJob() {
		return job;
	}

	/** {@inheritDoc} **/
	@Override
	public void setJob(PepperJobImpl job) {
		setJob_basic(job);
	}

	/** {@inheritDoc} **/
	@Override
	public void setJob_basic(PepperJobImpl job) {
		this.job = job;
	}

	/**
	 * The {@link DocumentBus} object working as input for this
	 * {@link ModuleControllerImpl}. All documents on bus will be processed and
	 * set to {@link #outputDocumentBus}
	 **/
	private DocumentBus inputDocumentBus = null;

	/** {@inheritDoc} **/
	@Override
	public DocumentBus getInputDocumentBus() {
		return inputDocumentBus;
	}

	/** {@inheritDoc} **/
	@Override
	public void setInputDocumentBus(DocumentBus inputDocumentBus) {
		this.inputDocumentBus = inputDocumentBus;
	}

	/**
	 * All documents which were consumed by the {@link PepperModule} contained
	 * in this object are set to this output bus, regarding the status, the
	 * {@link PepperModule} returned.
	 **/
	private DocumentBus outputDocumentBus = null;

	/** {@inheritDoc} **/
	@Override
	public DocumentBus getOutputDocumentBus() {
		return outputDocumentBus;
	}

	/** {@inheritDoc} **/
	@Override
	public void setOutputDocumentBus(DocumentBus outputDocumentBus) {
		this.outputDocumentBus = outputDocumentBus;
	}

	/** The {@link SCorpusGraph} object to be filled. **/
	protected volatile SCorpusGraph sCorpusGraph = null;

	/** {@inheritDoc} **/
	@Override
	public SCorpusGraph getCorpusGraph() {
		return (sCorpusGraph);
	}

	/**
	 * Executor, to create and manage threads for import of corpus structure and
	 * import of document structure
	 **/
	protected ExecutorService executor = null;

	/**
	 * Returns an executor service for this object. Executor, to create and
	 * manage threads for import of corpus structure and import of document
	 * structure.
	 * 
	 * @return
	 */
	protected ExecutorService getExecutor() {
		if (executor == null) {
			synchronized (this) {
				if (executor == null) {
					executor = Executors.newSingleThreadExecutor();
				}
			}
		}
		return (executor);
	}

	/** {@inheritDoc} **/
	@Override
	public synchronized Future<?> importCorpusStructure(SCorpusGraph sCorpusGraph) {
		if (sCorpusGraph == null) {
			throw new PepperFWException(
					"Cannot import corpus structure, because the passed SCorpusGraph object was null.");
		}
		if (getPepperModule() == null) {
			throw new PepperFWException(
					"Cannot start import of corpus structure, because the contained Pepper module is null.");
		}
		if (!(getPepperModule() instanceof PepperImporter)) {
			throw new PepperFWException("Cannot start import of corpus structure, because the contained Pepper module '"
					+ getId() + "' is not of type '" + MODULE_TYPE.IMPORTER + "'.");
		}
		if (((PepperImporter) getPepperModule()).getCorpusDesc() == null) {
			throw new PepperFWException(
					"Cannot start import of corpus structure, because the corpus description of Pepper module '"
							+ getId() + "' is not set. ");
		}
		if (!getBusyLock().tryLock()) {
			throw new PepperInActionException(
					"Cannot start importing corpus structure, since this module controller currently imports a corpus structure.");
		} else {
			this.sCorpusGraph = sCorpusGraph;
			ExecutorService executor = Executors.newSingleThreadExecutor();
			Runnable task = new Runnable() {
				public void run() {
					((PepperImporter) getPepperModule()).importCorpusStructure(getCorpusGraph());
					mLogger.debug("[{}] corpus structure imported. ",
							((getPepperModule() != null) ? getPepperModule().getName() : " EMPTY "));
				}
			};

			if (!getBusyLock().tryLock()) {
				throw new PepperInActionException("cannot import corpus structure, because module controller '"
						+ getId() + "' currently is busy with another process.");
			}
			getBusyLock().lock();
			Future<?> future = null;
			try {
				future = executor.submit(task);
			} finally {
				getBusyLock().unlock();
			}
			return (future);
		}
	}

	/**
	 * Starts the import of document-structure. When calling this method, the
	 * {@link ModuleControllerImpl} object will request all
	 * {@link DocumentController} object waiting in the incoming
	 * {@link DocumentBus}.
	 */
	public synchronized Future<?> processDocumentStructures() {
		if (getPepperModule() == null) {
			throw new PepperFWException(
					"Cannot start imort corpus structure, because the contained Pepper module is null.");
		}
		if (!getBusyLock().tryLock()) {
			throw new PepperInActionException(
					"Cannot start importing corpus structure, since this module controller currently imports a corpus structure.");
		} else {

			ExecutorService executor = Executors.newSingleThreadExecutor();
			Runnable task = new Runnable() {
				public void run() {
					// calls before() to do some work before everything is
					// processed when set in customization property
					BeforeAfterAction action = new BeforeAfterAction(getPepperModule());
					action.before(getCorpusGraph());
					getPepperModule().start();
					if (getControllList().size() != 0) {
						throw new PepperModuleException(getPepperModule(),
								"Some documents are still in the processing queue '" + getId() +  "' by module '"
										+ getPepperModule().getName() + "' and neither set to '"
										+ DOCUMENT_STATUS.COMPLETED + "', '" + DOCUMENT_STATUS.DELETED + "' or '"
										+ DOCUMENT_STATUS.FAILED + "'. Remaining documents are: " + getControllList());
					}
					getOutputDocumentBus().finish(getPepperModule().getModuleController().getId());
					mLogger.debug("[{}] completed processing of documents and corpora. ",
							((getPepperModule() != null) ? getPepperModule().getName() : " EMPTY "));
					// calls after() to do some work after everything was
					// processed when set in customization property
					action.after(getCorpusGraph());
				}
			};

			if (!getBusyLock().tryLock()) {
				throw new PepperInActionException("cannot import document structure, because module controller '"
						+ getId() + "' currently is busy with another process.");
			}
			getBusyLock().lock();
			Future<?> future = null;
			try {
				future = executor.submit(task);
			} finally {
				getBusyLock().unlock();
			}
			return (future);
		}
	}

	/**
	 * A lock determining, whether this object currently is busy with importing
	 * corpus structure or importing document structure.
	 **/
	protected ReentrantLock busyLock = null;

	/**
	 * A lock determining, whether this object currently is busy with importing
	 * corpus structure or importing document structure.
	 * 
	 * @return
	 */
	protected ReentrantLock getBusyLock() {
		if (busyLock == null) {
			synchronized (this) {
				if (busyLock == null)
					busyLock = new ReentrantLock();
			}
		}
		return (busyLock);
	}

	/**
	 * This set contains all {@link DocumentController} objects which have been
	 * requested by this {@link ModuleControllerImpl} object or its
	 * {@link PepperModule} but have not been add to output {@link DocumentBus}
	 * object.<br/>
	 * This list is used to control, if this {@link ModuleControllerImpl} object
	 * and its {@link PepperModule} work correctly.
	 **/
	private final Set<DocumentController> controllList = Collections.newSetFromMap(new ConcurrentHashMap<DocumentController, Boolean>());

	/**
	 * This set contains all {@link DocumentController} objects which have been
	 * requested by this {@link ModuleControllerImpl} object or its
	 * {@link PepperModule} but have not been add to output {@link DocumentBus}
	 * object.<br/>
	 * This list is used to control, if this {@link ModuleControllerImpl} object
	 * and its {@link PepperModule} work correctly.
	 * 
	 * @return set of document controllers in progress
	 */
	private Set<DocumentController> getControllList() {
		return controllList;
	}

	/** {@inheritDoc ModuleController#next(boolean)} */
	@Override
	public DocumentController next(boolean ignorePermissionForDocument) {
		if (this.getInputDocumentBus() == null) {
			throw new PepperFWException("The input document bus is not set for module controller '" + getId() + "'.");
		}
		DocumentController documentController = getInputDocumentBus().pop(getId(), ignorePermissionForDocument);
		if (documentController != null) {
			logger.debug("[{}] started processing of document '{}'. ",
					((getPepperModule() != null) ? getPepperModule().getName() : " EMPTY "),
					documentController.getGlobalId());
			// notify documentController, that SDocument now is in progress
			documentController.updateStatus(this, DOCUMENT_STATUS.IN_PROGRESS);
			// puts the current element in list of not pipelined orders
			getControllList().add(documentController);
			if (documentController.getDocument() == null)
				throw new PepperFWException("The current documentController to '" + documentController.getGlobalId()
						+ "' contains no document.");
		}
		return (documentController);
	}

	/** {@inheritDoc} **/
	@Override
	public DocumentController next() {
		logger.debug("[{}] is waiting for further documents in pipeline.",
				(getPepperModule() != null) ? getPepperModule().getName() : "NO_NAME");
		return (next(false));
	}

	/** {@inheritDoc} **/
	@Override
	public void complete(DocumentController documentController) {
		if (documentController == null) {
			throw new PepperFWException(
					"Cannot add the passed document controller to following Pepper modules, because it is null.");
		}
		if (!getControllList().contains(documentController)) {
			throw new PepperFWException(
					"Cannot add the passed document controller to following Pepper modules, because the passed document controller '"
							+ documentController.getGlobalId() + "' has never been add to internal controll list.");
		}
		if (documentController.getDocument() == null) {
			throw new PepperFWException(
					"Cannot complete the passed document controller to following Pepper modules, because there is no SDocument contained in passed document controller '"
							+ documentController.getGlobalId() + "' has never been add to internal controll list.");
		}
		documentController.updateStatus(this, DOCUMENT_STATUS.COMPLETED);

		// if (!this.started)
		// throw new
		// PepperConvertException("Cannot finish the given element-id, because
		// the module-controller was not started (please call sytart()
		// first).");
		getOutputDocumentBus().put(documentController);
		// removes document controller of list of to be processed document
		// controllers
		getControllList().remove(documentController);
		mLogger.debug("[{}] completed document '{}'",
				((getPepperModule() != null) ? getPepperModule().getName() : " EMPTY "),
				documentController.getGlobalId());
	}

	/** {@inheritDoc} **/
	@Override
	public void delete(DocumentController documentController) {
		if (documentController == null) {
			throw new PepperFWException(
					"Cannot notify Pepper, that the passed document controller shall not be processed any further, because it is null.");
		}
		if (!getControllList().contains(documentController)) {
			throw new PepperFWException("Cannot notify Pepper, that the passed document controller '"
					+ documentController.getGlobalId() + "' shall not be processed any further by Pepper module '"
					+ getId() + "', because it is not part of internal controll list '" + getControllList()
					+ "'. The reason could be, that it never has been added or it was already removed. ");
		}

		documentController.updateStatus(this, DOCUMENT_STATUS.DELETED);
		mLogger.debug("[{}] deleted document '{}'",
				((getPepperModule() != null) ? getPepperModule().getName() : " EMPTY "),
				documentController.getGlobalId());

		// make sure the document graph is not held in memory any longer
		documentController.sendToSleep();

		// if document is not processed any further, release slot
		if (getJob() != null) {
			getJob().releaseDocument(documentController);
		}

		// removes document controller of list of to be processed document
		// controllers
		getControllList().remove(documentController);
	}

	/** {@inheritDoc} **/
	@Override
	public Double getProgress(String globalId) {
		if (globalId == null)
			throw new PepperFWException("Cannot notify Pepper framework about progress for '" + getId()
					+ "', because given sDocumentId was null.");
		Double retVal = null;
		if (getPepperModule() != null) {
			retVal = getPepperModule().getProgress(globalId);
			if (retVal != null) {
				if ((retVal < 0) || (retVal > 1))
					throw new PepperFWException("Cannot notify Pepper framework about progress for '" + getId()
							+ "', because the percentage of progress is out of range (0..1). It is '" + retVal + "'.");
			}
		}
		return (retVal);
	}

	/**
	 * Returns a String representation of this object.
	 * 
	 * @return a String representation of this object.
	 */
	public String toString() {
		StringBuffer retVal = new StringBuffer();
		retVal.append(getId());
		retVal.append("(");
		if (getPepperModule() != null)
			retVal.append(getPepperModule().getName());
		else
			retVal.append("EMPTY");
		retVal.append(", ");
		if (getPepperModule() != null)
			retVal.append(getPepperModule().getProgress());
		retVal.append(")");
		return (retVal.toString());
	}
}

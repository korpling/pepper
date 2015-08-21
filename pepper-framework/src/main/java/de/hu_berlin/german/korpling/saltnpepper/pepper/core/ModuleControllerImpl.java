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

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperInActionException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.DocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.ModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;

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
			throw new PepperFWException("Cannot create an instance of PepperModuleController, because the passed identifier is null.");
		if (id.isEmpty())
			throw new PepperFWException("Cannot create an instance of PepperModuleController, because the passed identifier is empty.");
		this.id = id;
	}

	/** id of this object **/
	protected String id = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #getId()
	 */
	@Override
	public String getId() {
		return (id);
	}

	/** The {@link PepperModule} object, this controller object is observing. **/
	protected PepperModule pepperModule;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #getPepperModule()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #getJob()
	 */
	@Override
	public PepperJobImpl getJob() {
		return job;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #setJob
	 * (de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl)
	 */
	@Override
	public void setJob(PepperJobImpl job) {
		setJob_basic(job);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #setJob_basic
	 * (de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #getInputDocumentBus()
	 */
	@Override
	public DocumentBus getInputDocumentBus() {
		return inputDocumentBus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #setInputDocumentBus
	 * (de.hu_berlin.german.korpling.saltnpepper.pepper.core.DocumentBus)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #getOutputDocumentBus()
	 */
	@Override
	public DocumentBus getOutputDocumentBus() {
		return outputDocumentBus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #setOutputDocumentBus
	 * (de.hu_berlin.german.korpling.saltnpepper.pepper.core.DocumentBus)
	 */
	@Override
	public void setOutputDocumentBus(DocumentBus outputDocumentBus) {
		this.outputDocumentBus = outputDocumentBus;
	}

	/** The {@link SCorpusGraph} object to be filled. **/
	protected volatile SCorpusGraph sCorpusGraph = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #getSCorpusGraph()
	 */
	@Override
	public SCorpusGraph getSCorpusGraph() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #importCorpusStructure
	 * (de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon
	 * .sCorpusStructure.SCorpusGraph)
	 */
	@Override
	public synchronized Future<?> importCorpusStructure(SCorpusGraph sCorpusGraph) {
		if (sCorpusGraph == null) {
			throw new PepperFWException("Cannot import corpus structure, because the passed SCorpusGraph object was null.");
		}
		if (getPepperModule() == null) {
			throw new PepperFWException("Cannot start import of corpus structure, because the contained Pepper module is null.");
		}
		if (!(getPepperModule() instanceof PepperImporter)) {
			throw new PepperFWException("Cannot start import of corpus structure, because the contained Pepper module '" + getId() + "' is not of type '" + MODULE_TYPE.IMPORTER + "'.");
		}
		if (((PepperImporter) getPepperModule()).getCorpusDesc() == null) {
			throw new PepperFWException("Cannot start import of corpus structure, because the corpus description of Pepper module '" + getId() + "' is not set. ");
		}
		if (!getBusyLock().tryLock()) {
			throw new PepperInActionException("Cannot start importing corpus structure, since this module controller currently imports a corpus structure.");
		} else {
			this.sCorpusGraph = sCorpusGraph;
			ExecutorService executor = Executors.newSingleThreadExecutor();
			Runnable task = new Runnable() {
				public void run() {
					((PepperImporter) getPepperModule()).importCorpusStructure(getSCorpusGraph());
					mLogger.debug("[{}] corpus structure imported. ", ((getPepperModule() != null) ? getPepperModule().getName() : " EMPTY "));
				}
			};

			if (!getBusyLock().tryLock()) {
				throw new PepperInActionException("cannot import corpus structure, because module controller '" + getId() + "' currently is busy with another process.");
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
			throw new PepperFWException("Cannot start imort corpus structure, because the contained Pepper module is null.");
		}
		if (!getBusyLock().tryLock()) {
			throw new PepperInActionException("Cannot start importing corpus structure, since this module controller currently imports a corpus structure.");
		} else {

			ExecutorService executor = Executors.newSingleThreadExecutor();
			Runnable task = new Runnable() {
				public void run() {
					// calls before() to do some work before everything is
					// processed when set in customization property
					before();
					getPepperModule().start();
					if (getControllList().size() != 0) {
						throw new PepperModuleException(getPepperModule(), "Some documents are still in the processing queue by module '" + getPepperModule().getName() + "' and neither set to '" + DOCUMENT_STATUS.COMPLETED + "', '" + DOCUMENT_STATUS.DELETED + "' or '" + DOCUMENT_STATUS.FAILED + "'. Remaining documents are: " + getControllList());
					}
					getOutputDocumentBus().finish(getPepperModule().getModuleController().getId());
					mLogger.debug("[{}] completed processing of documents and corpora. ", ((getPepperModule() != null) ? getPepperModule().getName() : " EMPTY "));
					// calls after() to do some work after everything was
					// processed when set in customization property
					after();
				}
			};

			if (!getBusyLock().tryLock()) {
				throw new PepperInActionException("cannot import document structure, because module controller '" + getId() + "' currently is busy with another process.");
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

	/** {@inheritDoc PepperModule#before(SElementId)} */
	private void before() throws PepperModuleException {
		if (getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_REPORT_CORPUSGRAPH) != null) {
			boolean isReport = Boolean.parseBoolean(getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_REPORT_CORPUSGRAPH).getValue().toString());
			if (isReport && getSCorpusGraph() != null) {
				List<SNode> roots = getSCorpusGraph().getSRoots();
				if (roots != null) {
					StringBuilder str = new StringBuilder();
					str.append("corpus structure imported by ");
					str.append(getPepperModule().getName());
					for (SNode root : roots) {
						str.append(":\n");
						str.append(getPepperModule().getSaltProject().getSCorpusGraphs().indexOf(((SCorpus) root).getSCorpusGraph()));
						str.append("\n");
						str.append(reportCorpusStructure(root, "", true));
					}
					logger.info(str.toString());
				}
			}
		}
	}

	/** {@inheritDoc PepperModule#after(SElementId)} */
	private void after() throws PepperModuleException {
		if (getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_COPY_RES) != null) {
			// copies resources as files from source to target

			String resString = (String) getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_COPY_RES).getValue();
			copyResources(resString);
		}
	}

	protected String reportCorpusStructure(SNode node, String prefix, boolean isTail) {
		StringBuilder retStr = new StringBuilder();
		retStr.append(prefix);
		retStr.append(((isTail ? "└── " : "├── ") + node.getSName()));
		retStr.append("\n");
		List<Edge> outEdges = getSCorpusGraph().getOutEdges(node.getSId());
		int i = 0;
		for (Edge out : outEdges) {
			if (i < outEdges.size() - 1) {
				retStr.append(prefix);
				retStr.append(reportCorpusStructure((SNode) out.getTarget(), prefix + (isTail ? "    " : "│   "), false));
			} else {
				retStr.append(reportCorpusStructure((SNode) out.getTarget(), prefix + (isTail ? "    " : "│   "), true));
			}
			i++;
		}
		return (retStr.toString());
	}

	/**
	 * Reads customization property
	 * {@link PepperModuleProperties#PROP_AFTER_COPY_RES} and copies the listed
	 * resources to the named target folder.
	 */
	protected void copyResources(String resString) {
		if ((resString != null) && (!resString.isEmpty())) {
			String[] resources = resString.split(";");
			if (resources.length > 0) {
				for (String resource : resources) {
					resource = resource.trim();
					String[] parts = resource.split("->");
					if (parts.length == 2) {
						String sourceStr = parts[0];
						String targetStr = parts[1];
						sourceStr = sourceStr.trim();
						targetStr = targetStr.trim();

						// check if source and target is given
						boolean copyOk = true;
						if ((sourceStr == null) || (sourceStr.isEmpty())) {
							logger.warn("Cannot copy resources for '" + getPepperModule().getName() + "' because no source file was given in property value '" + resource + "'. ");
							copyOk = false;
						}
						if ((targetStr == null) || (targetStr.isEmpty())) {
							logger.warn("Cannot copy resources for '" + getPepperModule().getName() + "' because no target file was given in property value '" + resource + "'. ");
							copyOk = false;
						}
						if (copyOk) {
							File source = new File(sourceStr);
							File target = new File(targetStr);

							// in case of source or target aren't absolute
							// resolve them against current Job's base directory
							String baseDir = getJob().getBaseDir().toFileString();
							if (!baseDir.endsWith("/")) {
								baseDir = baseDir + "/";
							}
							if (!source.isAbsolute()) {
								source = new File(baseDir + sourceStr);
							}
							if (!source.exists()) {
								logger.warn("Cannot copy resources for '" + getPepperModule().getName() + "' because source does not exist '" + source.getAbsolutePath() + "'. Check the property value '" + resource + "'. ");
							} else {
								// only copy if source exists

								if (!target.isAbsolute()) {
									target = new File(baseDir + targetStr);
								}
								if (!target.exists()) {
									target.mkdirs();
								}
								try {
									if (source.isDirectory()) {
										targetStr = target.getAbsolutePath();
										if (!targetStr.endsWith("/")) {
											targetStr = targetStr + "/";
										}
										target = new File(targetStr + source.getName());
										FileUtils.copyDirectory(source, target);
										logger.trace("Copied resource from '" + source.getAbsolutePath() + "' to '" + target.getAbsolutePath() + "'.");
									} else {
										targetStr = target.getCanonicalPath();
										if (!targetStr.endsWith("/")) {
											targetStr = targetStr + "/";
										}
										target = new File(targetStr + source.getName());
										FileUtils.copyFile(source, target);
										logger.trace("Copied resource from '" + source.getAbsolutePath() + "' to '" + target.getAbsolutePath() + "'.");
									}
								} catch (IOException e) {
									logger.warn("Cannot copy resources for '" + getPepperModule().getName() + "' because of '" + e.getMessage() + "'. Check the property value '" + resource + "'. ");
								}
							}
						}
					}
				}
			}
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
	private HashSet<DocumentController> controllList = null;

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
	private HashSet<DocumentController> getControllList() {
		if (controllList == null) {
			synchronized (this) {
				controllList = new HashSet<DocumentController>();
			}
		}
		return (controllList);
	}

	/** {@inheritDoc ModuleController#next(boolean)} */
	@Override
	public DocumentController next(boolean ignorePermissionForDocument) {
		if (this.getInputDocumentBus() == null)
			throw new PepperFWException("The input document bus is not set for module controller '" + getId() + "'.");
		DocumentController documentController = getInputDocumentBus().pop(getId(), ignorePermissionForDocument);
		if (documentController != null) {
			logger.debug("[{}] started processing of document '{}'. ", ((getPepperModule() != null) ? getPepperModule().getName() : " EMPTY "), ((documentController != null) ? documentController.getGlobalId() : "UNKNOWN") + "'");
			// notify documentController, that SDocument now is in progress
			documentController.updateStatus(this, DOCUMENT_STATUS.IN_PROGRESS);
			// puts the current element in list of not pipelined orders
			getControllList().add(documentController);
			if (documentController.getSDocument() == null)
				throw new PepperFWException("The current documentController to '" + documentController.getGlobalId() + "' contains no document.");
		}
		return (documentController);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #next()
	 */
	@Override
	public DocumentController next() {
		logger.debug("[{}] is waiting for further documents in pipeline.", (getPepperModule() != null) ? getPepperModule().getName() : "NO_NAME");
		return (next(false));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #complete
	 * (de.hu_berlin.german.korpling.saltnpepper.pepper.core.DocumentController)
	 */
	@Override
	public void complete(DocumentController documentController) {
		if (documentController == null){
			throw new PepperFWException("Cannot add the passed document controller to following Pepper modules, because it is null.");
		}
		if (!getControllList().contains(documentController)){
			throw new PepperFWException("Cannot add the passed document controller to following Pepper modules, because the passed document controller '" + documentController.getGlobalId() + "' has never been add to internal controll list.");
		}
		if (documentController.getSDocument() == null){
			throw new PepperFWException("Cannot complete the passed document controller to following Pepper modules, because there is no SDocument contained in passed document controller '" + documentController.getGlobalId() + "' has never been add to internal controll list.");
		}
		documentController.updateStatus(this, DOCUMENT_STATUS.COMPLETED);

		// if (!this.started)
		// throw new
		// PepperConvertException("Cannot finish the given element-id, because the module-controller was not started (please call sytart() first).");
		getOutputDocumentBus().put(documentController);
		// removes document controller of list of to be processed document
		// controllers
		getControllList().remove(documentController);
		mLogger.debug("[{}] completed document '{}'", ((getPepperModule() != null) ? getPepperModule().getName() : " EMPTY "), ((documentController != null) ? documentController.getGlobalId() : "UNKNOWN"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #delete
	 * (de.hu_berlin.german.korpling.saltnpepper.pepper.core.DocumentController)
	 */
	@Override
	public void delete(DocumentController documentController) {
		if (documentController == null){
			throw new PepperFWException("Cannot notify Pepper, that the passed document controller shall not be proessed any further, because it is null.");
		}
		if (!getControllList().contains(documentController)){
			throw new PepperFWException("Cannot notify Pepper, that the passed document controller '" + documentController.getGlobalId() + "' shall not be proessed any further by Pepper module '" + getId() + "', because it has never been add to internal controll list '" + getControllList() + "'.");
		}
		// if (!this.started)
		// throw new
		// PepperConvertException("Cannot finish the given element-id, because module-controller was not started.");

		documentController.updateStatus(this, DOCUMENT_STATUS.DELETED);
		mLogger.debug("[{}] deleted document '{}'", ((getPepperModule() != null) ? getPepperModule().getName() : " EMPTY "), ((documentController != null) ? documentController.getGlobalId() : "UNKNOWN"));
		// if document is not processed any further, release slot
		if (getJob() != null) {
			getJob().releaseDocument(documentController);
		}
		// removes document controller of list of to be processed document
		// controllers
		getControllList().remove(documentController);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleController
	 * #getProgress(java.lang.String)
	 */
	@Override
	public Double getProgress(String globalId) {
		if (globalId == null)
			throw new PepperFWException("Cannot notify Pepper framework about progress for '" + getId() + "', because given sDocumentId was null.");
		Double retVal = null;
		if (getPepperModule() != null) {
			retVal = getPepperModule().getProgress(globalId);
			if (retVal != null) {
				if ((retVal < 0) || (retVal > 1))
					throw new PepperFWException("Cannot notify Pepper framework about progress for '" + getId() + "', because the percentage of progress is out of range (0..1). It is '" + retVal + "'.");
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

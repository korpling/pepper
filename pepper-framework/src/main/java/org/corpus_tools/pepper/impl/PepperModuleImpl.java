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
package org.corpus_tools.pepper.impl;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.exceptions.NotInitializedException;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.modules.DocumentController;
import org.corpus_tools.pepper.modules.MappingSubject;
import org.corpus_tools.pepper.modules.ModuleController;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperManipulator;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.PepperMapperController;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleNotReadyException;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.graph.Identifier;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

/**
 * TODO make docu
 * 
 * @author Florian Zipser
 */
public class PepperModuleImpl implements PepperModule, UncaughtExceptionHandler {
	protected Logger logger = LoggerFactory.getLogger("Pepper");

	/**
	 * Creates a {@link PepperModule} object, which is either a
	 * {@link MODULE_TYPE#IMPORTER}, a {@link MODULE_TYPE#MANIPULATOR} or a
	 * {@link MODULE_TYPE#EXPORTER}. The name of this module is set to
	 * "MyModule". <br/>
	 * We recommend to use the constructor
	 * {@link PepperModuleImpl#PepperModuleImpl(String)} and pass a proper name.
	 */
	protected PepperModuleImpl() {
		this("MyModule");
	}

	/**
	 * Creates a {@link PepperModule} object, which is either a
	 * {@link MODULE_TYPE#IMPORTER}, a {@link MODULE_TYPE#MANIPULATOR} or a
	 * {@link MODULE_TYPE#EXPORTER}. The passed name is set as the modules name.
	 */
	protected PepperModuleImpl(String name) {
		setName(name);
		logger = LoggerFactory.getLogger(name);
		getFingerprint();
		if (getProperties() == null) {
			setProperties(new PepperModuleProperties());
		}
	}

	/** a kind of a fingerprint of this object **/
	private PepperModuleDesc fingerprint = null;

	/**
	 * {@inheritDoc}
	 */
	public PepperModuleDesc getFingerprint() {
		if (fingerprint == null) {
			fingerprint = new PepperModuleDesc();

			if (this instanceof PepperManipulator) {
				fingerprint.setModuleType(MODULE_TYPE.MANIPULATOR);
			} else if (this instanceof PepperImporter) {
				fingerprint.setModuleType(MODULE_TYPE.IMPORTER);
			} else if (this instanceof PepperExporter) {
				fingerprint.setModuleType(MODULE_TYPE.EXPORTER);
			}
		}
		return (fingerprint);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return getFingerprint().getName();
	}

	/**
	 * Sets the name of this {@link PepperModule}. <strong>Note, that a name can
	 * only be set once and not be changed again.</strong>
	 * 
	 * @param name
	 *            name of this module.
	 */
	protected void setName(String name) {
		if (name != null) {
			getFingerprint().setName(name);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getVersion() {
		return getFingerprint().getVersion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVersion(String newVersion) {
		if ((newVersion != null) && (getVersion() == null)) {
			getFingerprint().setVersion(newVersion);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public MODULE_TYPE getModuleType() {
		return (getFingerprint().getModuleType());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDesc() {
		return getFingerprint().getDesc();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDesc(String desc) {
		getFingerprint().setDesc(desc);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public URI getSupplierContact() {
		return (getFingerprint().getSupplierContact());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSupplierContact(URI supplierContact) {
		getFingerprint().setSupplierContact(supplierContact);
	}

	@Override
	public URI getSupplierHomepage() {
		return (getFingerprint().getSupplierHomepage());
	}

	@Override
	public void setSupplierHomepage(URI hp) {
		getFingerprint().setSupplierHomepage(hp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PepperModuleProperties getProperties() {
		return (getFingerprint().getProperties());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setProperties(PepperModuleProperties properties) {
		getFingerprint().setProperties(properties);
	}

	/**
	 * Salt project which is processed by module.
	 */
	protected SaltProject saltProject = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaltProject getSaltProject() {
		return saltProject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void setSaltProject(SaltProject newSaltProject) {
		saltProject = newSaltProject;
	}

	/**
	 * TODO make docu
	 */
	protected URI resources = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public URI getResources() {
		return resources;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResources(URI newResources) {
		resources = newResources;
	}

	/**
	 * TODO make docu
	 */
	@Deprecated
	protected URI temproraries = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Deprecated
	public URI getTemproraries() {
		return temproraries;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Deprecated
	public void setTemproraries(URI newTemproraries) {
		temproraries = newTemproraries;
	}

	/**
	 * TODO make docu
	 */
	protected String symbolicName = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSymbolicName() {
		return symbolicName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSymbolicName(String newSymbolicName) {
		symbolicName = newSymbolicName;
	}

	/**
	 * The {@link ComponentContext} of the OSGi environment the bundle was
	 * started in.
	 **/
	private ComponentContext componentContext = null;

	/**
	 * Returns the {@link ComponentContext} of the OSGi environment the bundle
	 * was started in.
	 * 
	 * @return
	 */
	public ComponentContext getComponentContext() {
		return (this.componentContext);
	}

	/**
	 * This method is called by OSGi framework and sets the component context,
	 * this class is running in. This method scans the given
	 * {@link ComponentContext} object for symbolic name and version and
	 * initializes its values {@link #symbolicName} and {@link #version} with
	 * it. When running this class in OSGi context, you do not have to set both
	 * values by hand. With the given architecture, the symbolic name and the
	 * bundle version will be given by pom.xml, via MANIFEST.MF and finally read
	 * by this method. Also sets the internal field storing the
	 * {@link ComponentContext} which can be retrieved via
	 * {@link #getComponentContext()}.
	 * 
	 * @param componentContext
	 */
	@Activate
	protected void activate(ComponentContext componentContext) {
		this.componentContext = componentContext;
		if ((componentContext != null) && (componentContext.getBundleContext() != null) && (componentContext.getBundleContext().getBundle() != null)) {
			this.setSymbolicName(componentContext.getBundleContext().getBundle().getSymbolicName());
			this.setVersion(componentContext.getBundleContext().getBundle().getVersion().toString());
		}
	}

	/** a list containing reasons why this module is not ready to start **/
	private Collection<String> startProblems = new ArrayList<String>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getStartProblems() {
		return (startProblems);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadyToStart() throws PepperModuleNotReadyException {
		Boolean retVal = true;
		if (getResources() == null) {
			startProblems.add("No resource is given for module.");
			retVal = false;
		} else {
			File resourceFile = new File(getResources().toFileString());
			if (!resourceFile.exists()) {
				startProblems.add("Given resource file '" + resourceFile.getAbsolutePath() + "' does not exist.");
				retVal = false;
			}
		}
		if (getModuleType() == null) {
			startProblems.add("No module-type is set for module.");
			retVal = false;
		}
		if (getName() == null) {
			startProblems.add("No name is set for module.");
			retVal = false;
		}
		return (retVal);
	}

	/**
	 * the controller object, which acts as bridge between Pepper framework and
	 * Pepper module.
	 **/
	protected ModuleController moduleController = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModuleController getModuleController() {
		return (moduleController);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPepperModuleController(ModuleController newModuleController) {
		setPepperModuleController_basic(newModuleController);
		newModuleController.setPepperModule_basic(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPepperModuleController_basic(ModuleController newModuleController) {
		moduleController = newModuleController;
	}

	/**
	 * The {@link SCorpusGraph} object which should be processed by this module.
	 **/
	protected SCorpusGraph sCorpusGraph = null;

	/**
	 * {@inheritDoc}
	 */
	public SCorpusGraph getCorpusGraph() {
		return sCorpusGraph;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCorpusGraph(SCorpusGraph newSCorpusGraph) {
		sCorpusGraph = newSCorpusGraph;
	}

	/**
	 * A threadsafe map of all {@link PepperMapperController} objects which are
	 * connected with a started {@link PepperMapper} corresponding to their
	 * {@link Identifier}.
	 */
	private Map<String, PepperMapperController> mappersControllers = null;

	/**
	 * A lock for method {@link #getMappers()} to create a new mappers list.
	 */
	private Lock getMapperConnectorLock = new ReentrantLock();

	/**
	 * Returns a threadsafe map of all {@link PepperMapperController} objects
	 * which are connected with a started {@link PepperMapper} corresponding to
	 * their
	 * 
	 * @return
	 */
	protected Map<String, PepperMapperController> getMapperControllers() {
		if (mappersControllers == null) {
			getMapperConnectorLock.lock();
			try {
				if (mappersControllers == null)
					mappersControllers = new Hashtable<String, PepperMapperController>();
			} finally {
				getMapperConnectorLock.unlock();
			}
		}
		return (mappersControllers);
	}

	protected boolean isMultithreaded = true;

	/**
	 * {@inheritDoc}
	 */
	public void setIsMultithreaded(boolean isMultithreaded) {
		this.isMultithreaded = isMultithreaded;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isMultithreaded() {
		return (isMultithreaded);
	}

	/** Group of all mapper threads of this module **/
	private ThreadGroup mapperThreadGroup = null;

	/**
	 * Returns a {@link ThreadGroup} where {@link PepperMapper} objects and the
	 * corresponding threads are supposed to run in.
	 * 
	 * @return
	 */
	protected ThreadGroup getMapperThreadGroup() {
		return (mapperThreadGroup);
	}

	/**
	 * Sets a {@link ThreadGroup} where {@link PepperMapper} objects and the
	 * corresponding threads are supposed to run in.
	 * 
	 * @param mapperThreadGroup
	 */
	protected void setMapperThreadGroup(ThreadGroup mapperThreadGroup) {
		this.mapperThreadGroup = mapperThreadGroup;
	}

	// ========================== end: extract corpus-path
	/**
	 * an internal flag, to check if {@link #start(Identifier)} has been
	 * overridden or not. For downwards compatibility to modules implemented
	 * with < pepper 1.1.6
	 **/
	private boolean isStartOverridden = true;
	/**
	 * A map relating {@link Identifier} belonging to {@link SDocument} objects
	 * to their {@link DocumentController} container.
	 **/
	private Map<String, DocumentController> documentId2DC = null;

	/**
	 * Returns the map relating {@link Identifier} belonging to
	 * {@link SDocument} objects to their {@link DocumentController} container.
	 * 
	 * @return map
	 */
	protected Map<String, DocumentController> getDocumentId2DC() {
		if (documentId2DC == null) {
			synchronized (this) {
				if (documentId2DC == null) {
					documentId2DC = new Hashtable<String, DocumentController>();
				}
			}
		}
		return (documentId2DC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() throws PepperModuleException {
		if (getSaltProject() == null) {
			throw new PepperFWException("No salt project was set in module '" + getName() + ", " + getVersion() + "'.");
		}
		// creating new thread group for mapper threads
		mapperThreadGroup = new ThreadGroup(Thread.currentThread().getThreadGroup(), this.getName() + "_mapperGroup");
		boolean isStart = true;
		Identifier sElementId = null;
		DocumentController documentController = null;
		while ((isStart) || (sElementId != null)) {
			isStart = false;
			documentController = this.getModuleController().next();
			if (documentController == null) {
				break;
			}
			sElementId = documentController.getDocumentId();
			getDocumentId2DC().put(SaltUtil.getGlobalId(sElementId), documentController);
			// call for using push-method
			try {
				// start mapping
				start(sElementId);
			} catch (Exception e) {
				if (this.isStartOverridden) {
					// if start was overridden, for downwards compatibility to
					// modules implemented with < pepper 1.1.6
					this.done(sElementId, DOCUMENT_STATUS.DELETED);
				}
				if (e instanceof PepperModuleException) {
					throw (PepperModuleException) e;
				} else
					throw new PepperModuleException(this, "", e);
			}
			if (this.isStartOverridden) {
				// if start was overridden, for downwards compatibility to
				// modules implemented with < pepper 1.1.6
				this.done(sElementId, DOCUMENT_STATUS.COMPLETED);
			}
		}
		Collection<PepperMapperController> controllers = null;
		HashSet<PepperMapperController> alreadyWaitedFor = new HashSet<PepperMapperController>();
		// wait for all SDocuments to be finished
		controllers = Collections.synchronizedCollection(this.getMapperControllers().values());
		for (PepperMapperController controller : controllers) {
			try {
				controller.join();
				alreadyWaitedFor.add(controller);
			} catch (InterruptedException e) {
				throw new PepperFWException("Cannot wait for mapper thread '" + controller + "' in " + this.getName() + " to end. ", e);
			}
		}
		this.end();
		// only wait for controllers which have been added by end()
		for (PepperMapperController controller : this.getMapperControllers().values()) {
			if (!alreadyWaitedFor.contains(controller)) {
				try {
					controller.join();
				} catch (InterruptedException e) {
					throw new PepperFWException("Cannot wait for mapper thread '" + controller + "' in " + this.getName() + " to end. ", e);
				}
				this.done(controller);
			}
		}
	}

	/**
	 * A list containing all global ids, which have been mapped and for which
	 * the pepper fw has been notified, that they are processed.
	 **/
	private HashSet<String> mappedIds = null;

	/**
	 * Returns a list containing all global ids, which have been mapped and for
	 * which the pepper fw has been notified, that they are processed.
	 * 
	 * @return list containing all already processed global ids
	 **/
	private synchronized Collection<String> getMappedIds() {
		if (mappedIds == null)
			mappedIds = new HashSet<String>();
		return (mappedIds);
	}

	/**
	 * {@inheritDoc}
	 */
	public void done(Identifier id, DOCUMENT_STATUS result) {
		if (id.getIdentifiableElement() instanceof SDocument) {
			DocumentController docController = getDocumentId2DC().get(SaltUtil.getGlobalId(id));
			if (docController == null) {
				throw new PepperFWException("Error in '" + getName() + "'. Cannot find a " + DocumentController.class.getSimpleName() + " object corresponding to " + SDocument.class.getSimpleName() + " '" + SaltUtil.getGlobalId(id) + "' to pass status '" + result + "'. Controllers are listed for the following Identifier objects: " + getDocumentId2DC() + ". ");
			}
			if (DOCUMENT_STATUS.DELETED.equals(result)) {
				this.getModuleController().delete(docController);
			} else if (DOCUMENT_STATUS.COMPLETED.equals(result)) {
				this.getModuleController().complete(docController);
			} else if (DOCUMENT_STATUS.FAILED.equals(result)) {
				logger.error("Cannot map '" + SaltUtil.getGlobalId(id) + "' with module '" + this.getName() + "', because of a mapping result was '" + DOCUMENT_STATUS.FAILED + "'.");
				this.getModuleController().delete(docController);
			} else
				throw new PepperModuleException(this, "Cannot notify pepper framework for process of Identifier '" + id.getId() + "', because the mapping result was '" + result + "', and only '" + DOCUMENT_STATUS.COMPLETED + "', '" + DOCUMENT_STATUS.FAILED + "' and '" + DOCUMENT_STATUS.DELETED + "' is permitted.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void done(PepperMapperController controller) {
		if (controller == null)
			throw new PepperFWException("This might be a bug of Pepper framework. The given PepperMapperController is null in methode done().");
		if (controller.getMappingSubjects() != null) {
			for (MappingSubject subject : controller.getMappingSubjects()) {
				String globalId = SaltUtil.getGlobalId(subject.getIdentifier());
				DOCUMENT_STATUS result = null;
				if (!getMappedIds().contains(globalId)) {
					// only if framework has not already been notified
					this.getMappedIds().add(SaltUtil.getGlobalId(subject.getIdentifier()));
					try {
						result = subject.getMappingResult();
					} catch (Exception e) {
						result = DOCUMENT_STATUS.FAILED;
					}
					this.done(subject.getIdentifier(), result);
				}
			}
		}
	}

	/**
	 * This method is called by method {@link #start()}, if the method was not
	 * overridden by the current class. If this is not the case, this method
	 * will be called for every document which has to be processed.
	 * 
	 * @param sElementId
	 *            the id value for the current document or corpus to process
	 */
	@Override
	public void start(Identifier sElementId) throws PepperModuleException {
		// for downwards compatibility to modules implemented with < pepper
		// 1.1.6
		isStartOverridden = false;
		// check if mapper has to be called in multi-threaded or single-threaded
		// mode.

		// copy all corpora into finite list
		corporaToEnd = new Vector<SCorpus>();
		// copy the list before iterating over it (the original one might get
		// modified)
		List<SCorpusGraph> corpGraphs = new LinkedList<>(this.getSaltProject().getCorpusGraphs());
		for (SCorpusGraph sCorpusGraph : corpGraphs) {
			if (sCorpusGraph != null) {
				if (MODULE_TYPE.IMPORTER.equals(getModuleType())) {
					boolean belongsToSetCorpusGraph = false;
					if ((sCorpusGraph.getIdentifier() != null) && (((PepperImporter) this).getCorpusGraph() != null) && (((PepperImporter) this).getCorpusGraph().getIdentifier() != null)) {
						if (sCorpusGraph.getIdentifier().equals(((PepperImporter) this).getCorpusGraph().getIdentifier())) {
							belongsToSetCorpusGraph = true;
						}
					} else {
						if (sCorpusGraph.equals(((PepperImporter) this).getCorpusGraph())) {
							belongsToSetCorpusGraph = true;
						}
					}
					// in case of module is an importer, only import corpora
					// from set corpus graph
					if (belongsToSetCorpusGraph) {
						for (SCorpus sCorpus : sCorpusGraph.getCorpora()) {
							corporaToEnd.add(sCorpus);
						}
					}
				} else {
					// if module is not an importer, process all corpora

					for (SCorpus sCorpus : sCorpusGraph.getCorpora()) {
						corporaToEnd.add(sCorpus);
					}
				}
			}
		}

		if ((sElementId != null) && (sElementId.getIdentifiableElement() != null) && ((sElementId.getIdentifiableElement() instanceof SDocument) || ((sElementId.getIdentifiableElement() instanceof SCorpus)))) {
			// only if given sElementId belongs to an object of type SDocument
			// or SCorpus
			PepperMapperController controller = new PepperMapperControllerImpl(mapperThreadGroup, this.getName() + "_mapper(" + sElementId.getId() + ")");

			String id = sElementId.getId();
			if (sElementId.getIdentifiableElement() instanceof SDocument) {
				id = SaltUtil.getGlobalId(sElementId);
			}
			this.getMapperControllers().put(id, controller);
			controller.setUncaughtExceptionHandler(this);
			controller.setPepperModule(this);

			PepperMapper mapper = this.createPepperMapper(sElementId);
			mapper.setProperties(this.getProperties());

			if (this instanceof PepperImporter) {
				if (mapper.getResourceURI() == null) {
					URI resource = ((PepperImporter) this).getIdentifier2ResourceTable().get(sElementId);
					mapper.setResourceURI(resource);
				}
			}

			if ((sElementId.getIdentifiableElement() instanceof SDocument) && (mapper.getDocument() == null)) {
				mapper.setDocument((SDocument) sElementId.getIdentifiableElement());
			} else if ((sElementId.getIdentifiableElement() instanceof SCorpus) && (mapper.getCorpus() == null)) {
				mapper.setCorpus((SCorpus) sElementId.getIdentifiableElement());
			}

			controller.setPepperMapper(mapper);

			if (this.isMultithreaded()) {
				controller.start();
			} else
				controller.run();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public PepperMapper createPepperMapper(Identifier sElementId) {
		throw new NotInitializedException("Cannot start mapping, because the method createPepperMapper() of module '" + this.getName() + "' has not been overridden. Please check that first.");
	}

	/**
	 * A list of all corpora, which should be called in method {@link #end()}.
	 * The interim storage necessary because of
	 * {@link ConcurrentModificationException}, if any other modules adds or
	 * removes a corpus object in the meantime)
	 */
	private Collection<SCorpus> corporaToEnd = null;

	/**
	 * Calls method {@link #start(Identifier)} for every root {@link SCorpus} of
	 * {@link SaltProject} object.
	 */
	@Override
	public void end() throws PepperModuleException {
		logger.trace("[{}] start processing corpus structure (manipulating or exporting). ", getName());
		if (getSaltProject() == null) {
			throw new PepperModuleException(this, "Error in method end() salt project was empty.");
		}
		if (getSaltProject().getCorpusGraphs() == null) {
			throw new PepperModuleException(this, "Error in method end() corpus graphs of salt project were empty.");
		}
		if (corporaToEnd != null) {
			for (SCorpus sCorpus : corporaToEnd) {
				this.start(sCorpus.getIdentifier());
			}
		}
	}

	/**
	 * Method catches Uncaught exceptions thrown by {@link PepperMapperImpl}
	 * while running as Thread. This method just logs the exception. It is
	 * assumed, that the {@link PepperMapperImpl} itself notifies the Pepper
	 * framework about the unsuccessful process.
	 */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO this is a workaround because of a bug in slf4j. Currently errors
		// are not passable to slf4j. Therefore just the error message is
		// passed, and because this is quite unuseful, the stacktrace is also
		// printed.
		logger.error("An exception was thrown by the mapper threads '" + t + "'. ", e.getMessage());
		e.printStackTrace();
		if (logger instanceof NOPLogger) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getProgress(String globalId) {
		if (globalId == null) {
			throw new PepperFWException("Cannot return the progress for an empty sDocumentId.");
		}
		PepperMapperController controller = this.getMapperControllers().get(globalId);
		// outcommented for downwards compatibility to modules implemented with
		// < pepper 1.1.6
		// if (controller== null)
		// throw new
		// PepperFWException("Cannot return the progress for sDocumentId
		// '"+sDocumentId+"', because no mapper controller exists. This might be
		// a bug.");
		if (controller != null) {
			return (controller.getProgress());
		} else {
			return (null);
		}
	}
	
	/**
	 * {@inheritDoc PepperModule#getProgress()}
	 */
	@Override
	public Double getProgress() {
		Collection<PepperMapperController> controllers = Collections.synchronizedCollection(this.getMapperControllers().values());
		Double progress = 0d;
		// walk through all controllers to aggregate progresses
		if ((controllers != null) && (controllers.size() > 0)) {
			for (PepperMapperController controller : controllers) {
				if (controller != null) {
					progress = progress + controller.getProgress();
				}
			}
			if (progress > 0d)
				progress = progress / controllers.size();
		}
		return (progress);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Identifier> proposeImportOrder(SCorpusGraph sCorpusGraph) {
		return (new Vector<Identifier>());
	}

	/**
	 * Returns a string representation of this object. <br/>
	 * <strong>Note: This representation cannot be used for serialization/
	 * deserialization purposes.</strong>
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(getModuleType());
		result.append("(");
		result.append(getName());
		result.append(", ");
		if (getVersion() != null)
			result.append(getVersion());
		else
			result.append("NO_VERSION");
		result.append(")");
		return result.toString();
	}

	@Override
	public IntegrationTestDesc getIntegrationTestDesc() {
		return null;
	}
} // PepperModuleImpl

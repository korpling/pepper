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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.JOB_STATUS;
import org.corpus_tools.pepper.common.MEMORY_POLICY;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.PepperConfiguration;
import org.corpus_tools.pepper.common.PepperJob;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.exceptions.NotInitializedException;
import org.corpus_tools.pepper.exceptions.PepperException;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.exceptions.PepperInActionException;
import org.corpus_tools.pepper.exceptions.WorkflowException;
import org.corpus_tools.pepper.modules.DocumentController;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperty;
import org.corpus_tools.pepper.modules.coreModules.DoNothingExporter;
import org.corpus_tools.pepper.modules.coreModules.DoNothingImporter;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleXMLResourceException;
import org.corpus_tools.pepper.util.XMLStreamWriter;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.graph.Identifier;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * This class represents a single, but entire conversion process in Pepper,
 * containing the entire workflow like import, manipulation and export. <br/>
 * This object contains a list of single steps. Each of such steps represent one
 * task in processing by a specific module. Such a step is represented by the
 * {@link Step} class. To get the list of all steps, see {@link #getSteps()}.
 * <strong>Note: Do not use this list to add a further step. Use
 * {@link #addStep(PepperModule)} instead.</strong>
 * 
 * <h2>Order of tasks to be done for start of processing</h2> If you do not
 * follow this order in calling, and for instance call task 3 fist, than task 1
 * and task 2 will be done automatically.
 * <ul>
 * <li>wiring of {@link ModuleControllerImpl}, {@link DocumentBus} and
 * {@link PepperJobImpl}</li>
 * <li>import of corpus structure</li>
 * <li>start</li>
 * </ul>
 */
public class PepperJobImpl extends PepperJob {
	private static final Logger logger = LoggerFactory.getLogger(PepperJobImpl.class);

	/**
	 * Initializes a {@link PepperJobImpl} and sets its unique identifier.
	 * 
	 * @param jobId
	 *            unique identifier for this job. The id is not changeable
	 */
	public PepperJobImpl(String jobId) {
		if ((jobId == null) || (jobId.isEmpty())) {
			throw new PepperFWException("Cannot initialize a PepperJob with an empty id.");
		}
		id = jobId;
		setSaltProject(SaltFactory.createSaltProject());
	}

	/** The {@link SaltProject} which is converted by this job. **/
	protected SaltProject saltProject = null;

	/** {@inheritDoc} **/
	@Override
	public SaltProject getSaltProject() {
		return saltProject;
	}

	/**
	 * Sets the {@link SaltProject} which is converted by this job.
	 * 
	 * @param saltProject
	 *            new {@link SaltProject}
	 */
	public void setSaltProject(SaltProject saltProject) {
		if (inProgress.isLocked()) {
			throw new PepperInActionException(
					"Cannot set a new salt project to job '" + getId() + "', since this job was already started.");
		}
		this.saltProject = saltProject;
	}

	/**
	 * properties to customize the behavior of conversion for this single job
	 **/
	private PepperConfiguration props = null;

	/**
	 * returns the properties to customize the behavior of conversion for this
	 * single job.
	 * 
	 * @return given customization properties
	 */
	public PepperConfiguration getConfiguration() {
		if (props == null) {
			props = new PepperConfiguration();
		}
		return props;
	}

	/**
	 * Sets the properties to customize the behavior of conversion for this
	 * single job
	 * 
	 * @param conf
	 *            for customization
	 */
	public void setConfiguration(PepperConfiguration conf) {
		if (inProgress.isLocked()) {
			throw new PepperInActionException(
					"Cannot set a new configuration to job '" + getId() + "', since this job was already started.");
		}
		this.props = conf;

		setMemPolicy(getConfiguration().getMemPolicy());
		setMaxNumerOfDocuments(getConfiguration().getMaxAmountOfDocuments());
	}

	/**
	 * A reference to the OSGi module resolver, to find modules matching to the
	 * step description
	 **/
	protected ModuleResolver moduleResolver = null;

	/**
	 * Returns a reference to the OSGi module resolver, to find modules matching
	 * to the step description.
	 * 
	 * @return reference to resolver to resolve {@link PepperModule} objects
	 */
	public ModuleResolver getModuleResolver() {
		return moduleResolver;
	}

	/**
	 * Sets a reference to the OSGi module resolver, to find modules matching to
	 * the step description.
	 * 
	 * @param moduleResolver
	 *            reference to resolver to resolve {@link PepperModule} objects
	 */
	public void setModuleResolver(ModuleResolver moduleResolver) {
		if (inProgress.isLocked()) {
			throw new PepperInActionException(
					"Cannot set a new module resolver to job '" + getId() + "', since this job was already started.");
		}
		this.moduleResolver = moduleResolver;
	}

	/** Internal list of all steps belonging to the manipulation phase. **/
	private List<Step> manipulationSteps = null;

	/**
	 * Returns the list of all steps belonging to the manipulation phase.
	 * 
	 * @return list of {@link Step} objects.
	 **/
	public List<Step> getManipulationSteps() {
		if (manipulationSteps == null) {
			synchronized (this) {
				if (manipulationSteps == null) {
					manipulationSteps = new Vector<Step>();
				}
			}
		}
		return (manipulationSteps);
	}

	/** Internal list of all steps belonging to the import phase. **/
	private List<Step> importSteps = null;

	/**
	 * Returns the list of all steps belonging to the import phase.
	 * 
	 * @return list of {@link Step} objects.
	 **/
	public List<Step> getImportSteps() {
		if (importSteps == null) {
			synchronized (this) {
				if (importSteps == null) {
					importSteps = new Vector<Step>();
				}
			}
		}
		return (importSteps);
	}

	/** Internal list of all steps belonging to the export phase. **/
	private List<Step> exportSteps = null;

	/**
	 * Returns the list of all steps belonging to the export phase.
	 * 
	 * @return list of {@link Step} objects.
	 **/
	public List<Step> getExportSteps() {
		if (exportSteps == null) {
			synchronized (this) {
				if (exportSteps == null) {
					exportSteps = new Vector<Step>();
				}
			}
		}
		return (exportSteps);
	}

	/**
	 * Returns a of all steps belonging no matter, to which phase they belong.
	 * <br/>
	 * <strong>This computation could be expensive, when working more than once
	 * with the list, make a local copy and don't call this method
	 * twice.</strong>
	 * 
	 * @return list of {@link Step} objects.
	 **/
	public List<Step> getAllSteps() {
		List<Step> allSteps = new Vector<Step>();
		if (getImportSteps() != null) {
			allSteps.addAll(getImportSteps());
		}
		if (getManipulationSteps() != null) {
			allSteps.addAll(getManipulationSteps());
		}
		if (getExportSteps() != null) {
			allSteps.addAll(getExportSteps());
		}
		return (allSteps);
	}

	/**
	 * Overrides method {@link PepperJob#addStepDesc(StepDesc)}, but calls it
	 * via super and than calls {@link #addStep(Step)}.<br/>
	 * {@inheritDoc PepperJob#addStepDesc(StepDesc)}
	 */
	@Override
	public synchronized void addStepDesc(StepDesc stepDesc) {
		super.addStepDesc(stepDesc);
		addStep(stepDesc);
	}

	/**
	 * Creates a new {@link Step} object containing all values of the passed
	 * {@link StepDesc} object and adds it to the workflow covered by this
	 * {@link PepperJobImpl}. Further tries to resolve the described
	 * {@link PepperModule}.
	 * 
	 * @param stepDesc
	 *            {@link StepDesc} object to be added to internal list
	 * @return the created {@link Step} object
	 */
	public synchronized Step addStep(StepDesc stepDesc) {
		if (inProgress.isLocked()) {
			throw new PepperInActionException(
					"Cannot add a new step description to job '" + getId() + "', since this job was already started.");
		}
		if (stepDesc == null) {
			throw new WorkflowException("Cannot deal with an empty StepDesc object for job '" + getId() + "'.");
		}
		if (getModuleResolver() == null) {
			throw new PepperFWException("Cannot add step '" + stepDesc + "', because no module resolver is set.");
		}

		Step step = null;
		if (MODULE_TYPE.MANIPULATOR.equals(stepDesc.getModuleType())) {
			step = new Step("ma" + (getManipulationSteps().size() + 1), stepDesc);
		} else if (MODULE_TYPE.IMPORTER.equals(stepDesc.getModuleType())) {
			step = new Step("im" + (getImportSteps().size() + 1), stepDesc);
		} else if (MODULE_TYPE.EXPORTER.equals(stepDesc.getModuleType())) {
			step = new Step("ex" + (getExportSteps().size() + 1), stepDesc);
		} else {
			throw new WorkflowException("Cannot add step description, because the 'MODULE_TYPE' is not set.");
		}
		addStep(step);

		return (step);
	}

	/**
	 * Adds the passed {@link Step} object to the workflow covered by this
	 * {@link PepperJobImpl} object and tries to resolve the described
	 * {@link PepperModule}.
	 * <h2>Prerequisite</h2>
	 * <ul>
	 * <li>{@link #getModuleResolver()} must be set</li>
	 * <li>{@link #getSaltProject()} must be set</li>
	 * </ul>
	 * 
	 * @param step
	 *            {@link Step} object to be added to internal list
	 */
	public synchronized void addStep(Step step) {
		if (inProgress.isLocked()) {
			throw new PepperInActionException(
					"Cannot add a new step to job '" + getId() + "', since this job was already started.");
		}

		if (step == null) {
			throw new WorkflowException("Cannot deal with an empty step object for job '" + getId() + "'.");
		}
		if ((step.getModuleController() == null) || (step.getModuleController().getPepperModule() == null)) {
			if (getModuleResolver() == null) {
				throw new PepperFWException("Cannot add the given step '" + step.getId()
						+ "', because it does not contain a module controller and the module resolver for this job '"
						+ getId() + "' is not set. So the Pepper module can not be estimated.");
			}
			if (getSaltProject() == null) {
				throw new PepperFWException("Cannot add a step '" + step.getId() + "', since no '"
						+ SaltProject.class.getSimpleName() + "' is set for job '" + getId() + "'.");
			}
			PepperModule pepperModule = getModuleResolver().getPepperModule(step);
			if (pepperModule == null) {
				throw new WorkflowException(
						"No Pepper module matching to step '" + step.getId() + "' was found: " + step);
			}
			pepperModule.setSaltProject(getSaltProject());
			step.setPepperModule(pepperModule);
		}

		step.getModuleController().setJob(this);

		if (MODULE_TYPE.MANIPULATOR.equals(step.getModuleType())) {
			getManipulationSteps().add(step);
		} else if (MODULE_TYPE.IMPORTER.equals(step.getModuleType())) {
			getImportSteps().add(step);
		} else if (MODULE_TYPE.EXPORTER.equals(step.getModuleType())) {
			getExportSteps().add(step);
		}
	}

	/** A list of all buses between the {@link ModuleControllerImpl} objects **/
	protected List<DocumentBus> documentBuses = null;

	protected List<DocumentBus> getDocumentBuses() {
		if (documentBuses == null) {
			synchronized (this) {
				if (documentBuses == null)
					documentBuses = new Vector<DocumentBus>();
			}
		}
		return (documentBuses);
	}

	/**
	 * all documentBusses which are connected with {@link PepperImporter}
	 * modules
	 **/
	protected List<DocumentBus> initialDocumentBuses = null;
	/**
	 * Determines, if steps are already wired. This is necessary for
	 * {@link #start()}.
	 **/
	protected boolean isWired = false;

	/**
	 * Wires all {@link Step} objects being contained by this object to be ready
	 * for {@link #start()}. Which means, that:
	 * <ol>
	 * <li>each step of {@link #getImportSteps()} is wired via a initial
	 * document bus with the framework (no wiring, but listening on
	 * {@link #ID_INTITIAL})</li>
	 * <li>each step of {@link #getManipulationSteps()} is wired with following
	 * step in list with a {@link DocumentBus}.
	 * <li/>
	 * <li>each step of {@link #getExportSteps()} is wired via a terminal
	 * document bus with the framework (no wiring, but listening on
	 * {@link #ID_TERMINAL})</li>
	 * </ol>
	 */
	protected synchronized void wire() {

		if (getImportSteps().size() == 0) {
			throw new NotInitializedException("Cannot wire job '" + this + "', since no import steps were given.");
		}
		if (getExportSteps().size() == 0) {
			throw new NotInitializedException("Cannot wire job '" + this + "', since no export steps were given.");
		}

		// compute all ids of import steps
		List<String> importStepIds = new Vector<String>();
		for (Step importStep : getImportSteps()) {
			importStepIds.add(importStep.getModuleController().getId());
		}
		// compute all ids of export steps
		List<String> exportStepIds = new Vector<String>();
		for (Step exportStep : getExportSteps()) {
			exportStepIds.add(exportStep.getModuleController().getId());
		}

		initialDocumentBuses = new Vector<DocumentBus>();
		for (Step importStep : getImportSteps()) {
			DocumentBus initialDocumentBus = new InitialDocumentBus(importStep.getModuleController().getId());
			initialDocumentBus.setPepperJob(this);
			initialDocumentBus.setMemPolicy(getMemPolicy());
			importStep.getModuleController().setInputDocumentBus(initialDocumentBus);
			initialDocumentBuses.add(initialDocumentBus);
			getDocumentBuses().add(initialDocumentBus);
		}

		// DocumentBus terminalDocumentBus= new
		// TerminalDocumentBus(exportStepIds, ID_TERMINAL);
		DocumentBus terminalDocumentBus = new TerminalDocumentBus(exportStepIds);
		terminalDocumentBus.setPepperJob(this);
		terminalDocumentBus.setMemPolicy(getMemPolicy());
		getDocumentBuses().add(terminalDocumentBus);
		for (Step exportStep : getExportSteps()) {
			exportStep.getModuleController().setOutputDocumentBus(terminalDocumentBus);
		}

		// if there are manipulation steps to be done
		if (0 < getManipulationSteps().size()) {

			// connect all import steps with first manipulation step
			Step firstManipulationStep = getManipulationSteps().get(0);
			if (firstManipulationStep == null) {
				throw new PepperFWException("The first step in list of manipulation steps is null.");
			}
			// compute all ids of first manipulation step
			List<String> firstManipulationStepIds = new Vector<String>();
			firstManipulationStepIds.add(firstManipulationStep.getModuleController().getId());

			DocumentBus firstDocumentBus = new DocumentBus(importStepIds, firstManipulationStepIds);
			firstDocumentBus.setPepperJob(this);
			firstDocumentBus.setMemPolicy(getMemPolicy());
			getDocumentBuses().add(firstDocumentBus);

			for (Step importStep : getImportSteps()) {
				importStep.getModuleController().setOutputDocumentBus(firstDocumentBus);
			}
			firstManipulationStep.getModuleController().setInputDocumentBus(firstDocumentBus);

			// connect all manipulation steps
			if (getManipulationSteps().size() > 1) {
				Step lastStep = null;
				for (Step manipulationStep : getManipulationSteps()) {
					if (manipulationStep == null)
						throw new PepperFWException("A manipulation step is null.");

					if (lastStep != null) {
						DocumentBus documentBus = new DocumentBus(lastStep.getModuleController().getId(),
								manipulationStep.getModuleController().getId());
						documentBus.setPepperJob(this);
						documentBus.setMemPolicy(getMemPolicy());
						getDocumentBuses().add(documentBus);
						lastStep.getModuleController().setOutputDocumentBus(documentBus);
						manipulationStep.getModuleController().setInputDocumentBus(documentBus);
					}
					lastStep = manipulationStep;
				}
			}

			Step lastManipulationStep = getManipulationSteps().get(getManipulationSteps().size() - 1);
			if (lastManipulationStep == null) {
				throw new PepperFWException("The last step in list of manipulation steps is null.");
			}
			// compute all ids of first manipulation step
			List<String> lastManipulationStepIds = new Vector<String>();
			lastManipulationStepIds.add(lastManipulationStep.getModuleController().getId());

			DocumentBus lastDocumentBus = new DocumentBus(lastManipulationStepIds, exportStepIds);
			lastDocumentBus.setPepperJob(this);
			lastDocumentBus.setMemPolicy(getMemPolicy());
			getDocumentBuses().add(lastDocumentBus);
			lastManipulationStep.getModuleController().setOutputDocumentBus(lastDocumentBus);
			for (Step exportStep : getExportSteps()) {
				exportStep.getModuleController().setInputDocumentBus(lastDocumentBus);
			}
		} else {// direct connect importers and exporters with one central bus
			DocumentBus centralBus = new DocumentBus(importStepIds, exportStepIds);
			centralBus.setPepperJob(this);
			centralBus.setMemPolicy(getMemPolicy());
			getDocumentBuses().add(centralBus);
			for (Step importStep : getImportSteps()) {
				importStep.getModuleController().setOutputDocumentBus(centralBus);
			}
			for (Step exportStep : getExportSteps()) {
				exportStep.getModuleController().setInputDocumentBus(centralBus);
			}
		}
		isWired = true;
	}

	/** flag to determine, if corpus structure has already been imported **/
	protected volatile boolean isImportedCorpusStructure = false;

	/**
	 * Imports corpus structures of all registered
	 * {@link ImportCorpusStructureTest} steps. After calling
	 * {@link PepperImporter#importCorpusStructure(SCorpusGraph)} , all
	 * following modules will be asked, if they want to influence the order of
	 * importing documents. If this is the case, an order is created and put to
	 * all {@link PepperImporter} objects. <br/>
	 * This method produces as much as {@link SCorpusGraph} objects as
	 * {@link Step} given in import step list {@link #getImportSteps()}. The
	 * position of {@link SCorpusGraph} corresponding to {@link PepperImporter}
	 * (importing that graph) in {@link SaltProject#getCorpusGraphs()} is
	 * equivalent to position of {@link Step} in list {@link #getImportSteps()}.
	 */
	protected synchronized void importCorpusStructures() {
		try {
			if (!isWired) {
				wire();
			}
			List<Future<?>> futures = new Vector<Future<?>>();
			int numOfImportStep = 0;
			for (Step importStep : getImportSteps()) {
				if (getSaltProject() == null) {
					throw new PepperFWException("Cannot import corpus structure, because no salt project is set.");
				}
				SCorpusGraph sCorpusGraph = null;

				if ((getSaltProject().getCorpusGraphs().size() > numOfImportStep)
						&& (getSaltProject().getCorpusGraphs().get(numOfImportStep) != null)) {
					sCorpusGraph = getSaltProject().getCorpusGraphs().get(numOfImportStep);
				} else {
					sCorpusGraph = SaltFactory.createSCorpusGraph();
					getSaltProject().addCorpusGraph(sCorpusGraph);
				}

				futures.add(importStep.getModuleController().importCorpusStructure(sCorpusGraph));
				numOfImportStep++;
			}
			for (Future<?> future : futures) {
				// wait until all corpus structures have been imported
				try {
					future.get();
				} catch (ExecutionException e) {
					throw new PepperModuleException("Failed to import corpus by module. Nested exception was: ",
							e.getCause());
				} catch (InterruptedException e) {
					throw new PepperFWException("Failed to import corpus by module. Nested exception was: ",
							e.getCause());
				} catch (CancellationException e) {
					throw new PepperFWException("Failed to import corpus by module. Nested exception was: ",
							e.getCause());
				}
			}
			int i = 0;
			for (Step step : getImportSteps()) {
				if (getSaltProject().getCorpusGraphs().get(i) == null) {
					throw new PepperModuleException("The importer '" + step.getModuleController().getPepperModule()
							+ "' did not import a corpus structure.");
				}

				// handle proposed import order
				List<Identifier> importOrder = unifyProposedImportOrders(getSaltProject().getCorpusGraphs().get(i));
				for (Identifier sDocumentId : importOrder) {
					DocumentControllerImpl documentController = new DocumentControllerImpl();
					SDocument sDoc = (SDocument) sDocumentId.getIdentifiableElement();
					if (sDoc.getDocumentGraph() == null) {
						sDoc.setDocumentGraph(SaltFactory.createSDocumentGraph());
					}
					documentController.setDocument(sDoc);
					// sets flag to determine whether garbage collector should
					// be called after document was send to sleep
					if (getConfiguration() != null) {
						documentController.setCallGC(getConfiguration().getGcAfterDocumentSleep());
					}
					getDocumentControllers().add(documentController);
					File docFile = null;
					String prefix = sDoc.getName();
					File tmpPath = new File(getConfiguration().getWorkspace().getAbsolutePath() + "/" + getId());
					if (!tmpPath.exists()) {
						if (!tmpPath.mkdirs()) {
							logger.warn("Cannot create folder {}. ", tmpPath);
						}
					}
					try {
						if (prefix.length() < 3) {
							prefix = prefix + "artificial";
						}
						docFile = File.createTempFile(prefix, "." + SaltUtil.FILE_ENDING_SALT_XML, tmpPath);
					} catch (IOException e) {
						throw new PepperFWException("Cannot store document '" + sDoc.getName() + "' to file '" + docFile
								+ "' in folder for temporary files '" + tmpPath + "'. " + e.getMessage(), e);
					}
					documentController.setLocation(URI.createFileURI(docFile.getAbsolutePath()));
					if (!getConfiguration().getKeepDocuments()) {
						docFile.deleteOnExit();
					}

					initialDocumentBuses.get(i).put(documentController);
					// notify document controller about all modules in workflow
					documentController.addModuleControllers(step.getModuleController());
					for (Step manipulationStep : getManipulationSteps()) {
						documentController.addModuleControllers(manipulationStep.getModuleController());
					}
					for (Step exportStep : getExportSteps()) {
						documentController.addModuleControllers(exportStep.getModuleController());
					}
				}
				initialDocumentBuses.get(i).finish(InitialDocumentBus.ID_INTITIAL);
				i++;
			}
			isImportedCorpusStructure = true;
		} catch (RuntimeException e) {
			if (e instanceof PepperException) {
				throw (PepperException) e;
			} else {
				throw new PepperFWException("An exception occured in job '" + getId()
						+ "' while importing the corpus-structure. See nested exception: ", e);
			}
		}
	}

	/**
	 * Returns a list of {@link Identifier}s corresponding to the
	 * {@link SDocument} objects contained in the passed {@link SCorpusGraph}
	 * object. If all registered modules, do not make a proposal, the natural
	 * one (the one given by the order of {@link SDocument}s in
	 * {@link SCorpusGraph}) is taken. <strong>Note: Currently, this method does
	 * not a real unification, if more than one proposals are given, the first
	 * one is taken.</strong>
	 * 
	 * @param sCorpusGraph
	 *            the {@link SCorpusGraph} for which the list has to be unified
	 * @return unified list
	 */
	protected List<Identifier> unifyProposedImportOrders(SCorpusGraph sCorpusGraph) {
		List<Identifier> retVal = new Vector<Identifier>();

		if (sCorpusGraph == null) {
			throw new PepperFWException("Cannot unify the import order, for an empty SCorpusGraph object.");
		}
		Vector<List<Identifier>> listOfOrders = new Vector<List<Identifier>>();
		for (Step step : getAllSteps()) {
			if (step.getModuleController() == null) {
				throw new PepperFWException("Cannot unify proposed import orders, since step '" + step.getId()
						+ "' does not contain a module controller.");
			}
			if (step.getModuleController().getPepperModule() == null) {
				throw new PepperFWException("Cannot unify proposed import orders, since module controller '"
						+ step.getModuleController().getId() + "' does not contain a Pepper module.");
			}

			List<Identifier> importOrder = step.getModuleController().getPepperModule()
					.proposeImportOrder(sCorpusGraph);
			if ((importOrder != null) && (importOrder.size() > 0)) {
				if (importOrder.size() < sCorpusGraph.getDocuments().size()) {
					for (SDocument sDoc : sCorpusGraph.getDocuments()) {
						if (!importOrder.contains(sDoc.getIdentifier()))
							importOrder.add(sDoc.getIdentifier());
					}
				}
				listOfOrders.add(importOrder);
			}
		}
		if (listOfOrders.size() == 0) {
			// if no proposals have been made, make the natural one
			for (SDocument sDocument : sCorpusGraph.getDocuments()) {
				retVal.add(sDocument.getIdentifier());
			}
		} else if (listOfOrders.size() == 1) {
			retVal = listOfOrders.get(0);
		} else {
			retVal = listOfOrders.get(0);
			logger.warn(
					"Sorry the feature of unifying more than one list of proposed import orders is not yet implemented. ");
			// TODO do some fancy stuff for list unification
		}
		return (retVal);
	}

	/**
	 * A list of all {@link DocumentControllerImpl} objects corresponding to
	 * each {@link SDocument} belonging to this job.
	 **/
	protected List<DocumentController> documentControllers = null;

	/**
	 * Returns a list of all {@link DocumentControllerImpl} objects
	 * corresponding to each {@link SDocument} belonging to this job.
	 * 
	 * @return a list of all {@link DocumentControllerImpl}s
	 */
	public List<DocumentController> getDocumentControllers() {
		if (documentControllers == null) {
			synchronized (this) {
				if (documentControllers == null) {
					documentControllers = new Vector<DocumentController>();
				}
			}
		}
		return (documentControllers);
	}

	/**
	 * {@inheritDoc PepperJob#getStatusReport()}
	 */
	@Override
	public String getStatusReport() {
		StringBuilder retVal = new StringBuilder();

		retVal.append("--------------------------- pepper job status ---------------------------");
		retVal.append("\n");

		retVal.append("id:\t\t\t'");
		retVal.append(getId());
		retVal.append("\n");

		retVal.append("active documents:\t");
		retVal.append(getNumOfActiveDocuments());
		retVal.append(" of ");
		retVal.append(getMaxNumberOfDocuments());

		retVal.append("\n");

		retVal.append("status:\t\t\t");
		retVal.append(getStatus());
		retVal.append("\n");

		StringBuilder detailedStr = new StringBuilder();
		double progressOverAll = 0;
		int numOfDocuments = 0;

		if (getDocumentControllers().isEmpty()) {
			retVal.append("- no documents found to display progress -\n");
		} else {

			String sleep = " (sleep)";
			int distance = 0;
			for (DocumentController docController : getDocumentControllers()) {
				String globalId = docController.getGlobalId();
				if (distance < globalId.length()) {
					distance = globalId.length();
				}
			}
			// distance is distance plus 4??? plus length of string 'sleep'
			distance = distance + 4 + sleep.length() + DOCUMENT_STATUS.IN_PROGRESS.toString().length();
			StringBuilder docInfo = null;
			for (DocumentController docController : getDocumentControllers()) {
				docInfo = new StringBuilder();
				numOfDocuments++;
				double progress = docController.getProgress();
				progressOverAll = progressOverAll + progress;
				String progressStr = new DecimalFormat("###.##").format(progress * 100) + "%";
				docInfo.append(docController.getGlobalId());
				docInfo.append("(");
				docInfo.append(docController.getGlobalStatus());
				if (docController.isAsleep()) {
					docInfo.append("/sleep");
				} else {
					docInfo.append("/");
					if (!DOCUMENT_STATUS.COMPLETED.equals(docController.getGlobalStatus())
							&& !DOCUMENT_STATUS.DELETED.equals(docController.getGlobalStatus())
							&& !DOCUMENT_STATUS.FAILED.equals(docController.getGlobalStatus())) {
						if (docController.getCurrentModuleController() == null
								|| docController.getCurrentModuleController().getPepperModule() == null) {
							docInfo.append("???");
						} else {
							docInfo.append(docController.getCurrentModuleController().getPepperModule().getName());
						}
					}
				}
				docInfo.append(")");
				detailedStr.append(String.format("%-" + distance + "s%8s", docInfo.toString(), progressStr));
				detailedStr.append("\n");
			}
			retVal.append("total progress:\t\t");
			if (numOfDocuments != 0) {
				retVal.append(new DecimalFormat("###.##").format(progressOverAll / numOfDocuments * 100) + "%");
			}
			retVal.append("\n");
			retVal.append("processing time:\t");
			retVal.append(DurationFormatUtils.formatDurationHMS(getProcessingTime()));
			retVal.append("\n");
			if (getConfiguration().getDetaialedStatReport()) {
				retVal.append(detailedStr.toString());
			}
		}
		retVal.append("-------------------------------------------------------------------------");
		retVal.append("\n");
		return (retVal.toString());
	}

	/** Determines if {@link #checkReadyToStart()} was already called **/
	protected boolean isReadyToStart = false;

	/**
	 * Checks for each {@link PepperModule} in all steps, if it is ready to
	 * start, via calling {@link PepperModule#isReadyToStart()}.
	 * 
	 * @return a list of steps whose modules are not ready to start
	 */
	protected synchronized Collection<Pair<Step, Collection<String>>> checkReadyToStart() {
		ArrayList<Pair<Step, Collection<String>>> retVal = new ArrayList<>();
		for (Step step : getAllSteps()) {
			if (!step.getModuleController().getPepperModule().isReadyToStart()) {
				Pair<Step, Collection<String>> stepReason = new ImmutablePair<Step, Collection<String>>(step,
						step.getModuleController().getPepperModule().getStartProblems());
				retVal.add(stepReason);
				logger.error("Cannot run pepper job '" + getId() + "', because one of the involved modules '"
						+ step.getModuleController().getPepperModule().getFingerprint() + "' is not ready to run.");
			}
		}
		return (retVal);
	}

	/** Stores the time when this job was started **/
	private Long startTime = 0l;

	/**
	 * Returns the amount of time the job already took.
	 * 
	 * @return time in milli seconds
	 */
	public Long getProcessingTime() {
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * Specifies if this job currently runs a conversion. If this is the case,
	 * some other operations, like adding {@link Step}s cannot be done
	 * simultaneously.
	 **/
	protected volatile ReentrantLock inProgress = new ReentrantLock();

	/**
	 * Starts the conversion of this job.
	 * <ul>
	 * <li>If the single steps of the job has not already been wired, they will
	 * be wired.
	 * <li>
	 * <li>If {@link PepperImporter#importCorpusStructure(SCorpusGraph)} has not
	 * already been called, it will be done.
	 * <li>
	 * </ul>
	 */
	public void convert() {
		if (!inProgress.tryLock()) {
			throw new PepperInActionException(
					"Cannot run convert() of job '" + getId() + "', since this job was already started.");
		}

		inProgress.lock();
		try {
			startTime = System.currentTimeMillis();
			status = JOB_STATUS.INITIALIZING;
			if (!isWired) {
				wire();
			}
			if (!isReadyToStart) {
				Collection<Pair<Step, Collection<String>>> notReadyModules = checkReadyToStart();
				if (notReadyModules.size() != 0) {
					StringBuilder str = new StringBuilder();
					for (Pair<Step, Collection<String>> problems : notReadyModules) {
						str.append("[");
						str.append(problems.getLeft());
						str.append(": ");
						str.append(problems.getRight());
						str.append("], ");
					}
					throw new PepperException("Cannot run Pepper job '" + getId()
							+ "', because at least one of the involved jobs is not ready to run: '" + str.toString()
							+ "'. ");
				}
			}
			status = JOB_STATUS.IMPORTING_CORPUS_STRUCTURE;
			if (!isImportedCorpusStructure) {
				importCorpusStructures();
			}
			status = JOB_STATUS.IMPORTING_DOCUMENT_STRUCTURE;
			List<Pair<ModuleControllerImpl, Future<?>>> futures = new Vector<Pair<ModuleControllerImpl, Future<?>>>();
			// create a future for each step
			for (Step step : getAllSteps()) {
				if (step.getModuleController().getPepperModule().getSaltProject() == null)
					step.getModuleController().getPepperModule().setSaltProject(getSaltProject());
				{
					futures.add(new ImmutablePair<ModuleControllerImpl, Future<?>>(step.getModuleController(),
							step.getModuleController().processDocumentStructures()));
				}
			}

			// log workflow information
			int stepNum = 0; // current number of step
			StringBuilder str = new StringBuilder();
			for (Step step : getAllSteps()) {
				stepNum++;
				str.append("+----------------------------------- step ");
				str.append(stepNum);
				str.append(" -----------------------------------+\n");

				String format = "|%-15s%-63s|\n";
				str.append(String.format(format, step.getModuleType().toString().toLowerCase() + ":", step.getName()));
				str.append(String.format(format, "path:", step.getCorpusDesc().getCorpusPath()));
				if (MODULE_TYPE.IMPORTER.equals(step.getModuleType())) {
					int idxCorpusGraph = getSaltProject().getCorpusGraphs()
							.indexOf(((PepperImporter) step.getModuleController().getPepperModule()).getCorpusGraph());
					str.append(String.format(format, "corpus index:", idxCorpusGraph));
				}

				boolean hasProperties = false;
				StringBuilder propStr = new StringBuilder();
				if (step.getModuleController().getPepperModule().getProperties().getPropertyDesctriptions() != null) {
					// log all properties of all modules and their values

					format = "|               %-25s%-38s|\n";
					for (PepperModuleProperty<?> prop : step.getModuleController().getPepperModule().getProperties()
							.getPropertyDesctriptions()) {
						if (prop.getValue() != null) {
							hasProperties = true;
							propStr.append(String.format(format, prop.getName() + ":", prop.getValue()));
						}
					}
				}
				format = "|%-15s%-63s|\n";
				if (hasProperties) {
					str.append(String.format(format, "properties:", ""));
					str.append(propStr.toString());
				} else {
					str.append(String.format(format, "properties:", "- none -"));
				}
				str.append("|                                                                              |\n");
			}
			str.append("+------------------------------------------------------------------------------+\n");
			logger.info(str.toString());

			for (Pair<ModuleControllerImpl, Future<?>> future : futures) {
				// wait until all document-structures have been imported
				try {
					future.getRight().get();
				} catch (ExecutionException e) {
					if ((e.getCause() != null) && (e.getCause() instanceof PepperException)) {
						throw (PepperException) e.getCause();
					}
					throw new PepperModuleException(
							"Failed to process document by module '" + future.getLeft() + "'. Nested exception was: ",
							e.getCause());
				} catch (InterruptedException e) {
					if ((e.getCause() != null) && (e.getCause() instanceof PepperException)) {
						throw (PepperException) e.getCause();
					}
					throw new PepperFWException(
							"Failed to process document by module '" + future.getLeft() + "'. Nested exception was: ",
							e.getCause());
				} catch (CancellationException e) {
					if ((e.getCause() != null) && (e.getCause() instanceof PepperException)) {
						throw (PepperException) e.getCause();
					}
					throw new PepperFWException(
							"Failed to process document by module '" + future.getLeft() + "'. Nested exception was: ",
							e.getCause());
				}
			}
			status = JOB_STATUS.ENDED;
		} catch (RuntimeException e) {
			status = JOB_STATUS.ENDED_WITH_ERRORS;
			if (e instanceof PepperException) {
				throw (PepperException) e;
			} else {
				throw new PepperFWException("An exception occured in job '" + getId()
						+ "' while importing the corpus-structure. See nested exception: " + e.getMessage(), e);
			}
		} finally {
			inProgress.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void convertFrom() {
		if (getExportSteps().size() > 0) {
			logger.warn(
					"Cannot consider given export steps, any export step is ignored when invoking 'convertFrom()'. To create a conversion process with export steps use 'convert()' instead. ");
			exportSteps.clear();
		}
		addStepDesc(
				new StepDesc().setName(DoNothingExporter.MODULE_NAME).setModuleType(MODULE_TYPE.EXPORTER).setCorpusDesc(
						new CorpusDesc().setCorpusPath(URI.createFileURI(PepperUtil.getTempFile().getAbsolutePath()))));
		convert();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void convertTo() {
		if (getImportSteps().size() > 0) {
			logger.warn(
					"Cannot consider given import steps, any import step is ignored when invoking 'convertTo()'. To create a conversion process with import steps use 'convert()' instead. ");
			importSteps.clear();
		}
		addStepDesc(
				new StepDesc().setName(DoNothingImporter.MODULE_NAME).setModuleType(MODULE_TYPE.IMPORTER).setCorpusDesc(
						new CorpusDesc().setCorpusPath(URI.createFileURI(PepperUtil.getTempFile().getAbsolutePath()))));
		convert();
	}

	// ======================================= start: managing number of active
	// documents
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
	protected void setMemPolicy(MEMORY_POLICY memPolicy) {
		this.memPolicy = memPolicy;
	}

	/**
	 * Determines the maximal number of {@link SDocument} objects which could be
	 * processed at the same time
	 **/
	private volatile int maxNumOfDocuments = 10;

	protected void setMaxNumerOfDocuments(int maxNumOfDocuments) {
		this.maxNumOfDocuments = maxNumOfDocuments;
	}

	/**
	 * Returns the maximal number of {@link SDocument} objects which could be
	 * processed at the same time
	 * 
	 * @return number of documents
	 */
	public int getMaxNumberOfDocuments() {
		return (maxNumOfDocuments);
	}

	/**
	 * Returns the current number of {@link SDocument} objects which could be
	 * processed at the same time
	 * 
	 * @return number of documents
	 */
	public int getNumOfActiveDocuments() {
		return (getActiveDocuments().size());
	}

	/** lock for correct change of {@link #currNumOfDocuments} **/
	private volatile Lock numOfDocsLock = new ReentrantLock();
	/**
	 * condition for notifying {@link #getPermissionForProcessDoument()} when a
	 * document was released via {@link #releaseDocument()}
	 **/
	private volatile Condition numOfDocsCondition = numOfDocsLock.newCondition();
	/** A set of all currently active documents. **/
	private Set<DocumentController> activeDocuments = null;

	/** Returns a set of all currently active documents. **/
	public Set<DocumentController> getActiveDocuments() {
		if (activeDocuments == null) {
			activeDocuments = new HashSet<>();
		}
		return (activeDocuments);
	}

	/**
	 * Returns true, if a {@link SDocument} or more precisely spoken a
	 * {@link SDocumentGraph} could be woken up or imported. This is the case,
	 * as long as: <br/>
	 * {@link #getNumOfActiveDocuments()} < {@link #getMaxNumberOfDocuments()}.
	 * <br/>
	 * Must be synchronized,
	 * 
	 * @return true, when #getCurrNumberOfDocuments()} <
	 *         {@link #getMaxNumberOfDocuments(), false otherwise
	 */
	public boolean getPermissionForProcessDoument(DocumentController controller) {
		if (!MEMORY_POLICY.GREEDY.equals(getMemPolicy())) {
			numOfDocsLock.lock();
			try {
				while (getNumOfActiveDocuments() >= getMaxNumberOfDocuments()) {
					numOfDocsCondition.await();
				}
				getActiveDocuments().add(controller);
			} catch (InterruptedException e) {
				throw new PepperFWException("Something went wrong, when waiting for lock 'numOfDocsCondition'.", e);
			} finally {
				numOfDocsLock.unlock();
			}
		}
		return (true);
	}

	/**
	 * Releases a document and reduces the internal counter for the number of
	 * currently processed documents ({@link #getNumOfActiveDocuments()}).
	 */
	public void releaseDocument(DocumentController controller) {
		numOfDocsLock.lock();
		try {
			getActiveDocuments().remove(controller);
			// not sure, if signal() is correct, or if signalAll() should
			// be used, but I would think, that only one waiter has to be
			// notified --> seems to work correctly
			numOfDocsCondition.signal();
		} finally {
			numOfDocsLock.unlock();
		}
	}

	// ======================================= end: managing number of active
	// documents

	/**
	 * {@inheritDoc PepperJob#save(URI)}
	 */
	@Override
	public URI save(URI uri) {
		if (uri == null) {
			throw new PepperException("Cannot save Pepper job '" + getId() + "', because the passed uri is empty. ");
		}
		File file = null;
		if (PepperUtil.FILE_ENDING_PEPPER.equals(uri.fileExtension())) {
			// passed uri already points to a Pepper file
			file = new File(uri.toFileString());
		} else {
			// uri points to a directory
			String directory = uri.toFileString();
			if (!directory.endsWith("/")) {
				directory = directory + "/";
			}
			file = new File(directory + getId() + "." + PepperUtil.FILE_ENDING_PEPPER);
		}
		// create parent directory of file
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				if (!file.getParentFile().canWrite()) {
					throw new PepperModuleXMLResourceException(
							"Cannot create folder '" + file.getParentFile().getAbsolutePath()
									+ "' to store Pepper workflow file, because of an access permission. ");
				} else {
					throw new PepperModuleXMLResourceException("Cannot create folder '"
							+ file.getParentFile().getAbsolutePath() + "' to store Pepper workflow file. ");
				}
			}
			;
		}

		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter xml;

		try {
			xml = new XMLStreamWriter(xof.createXMLStreamWriter(new FileWriter(file.getAbsolutePath())));
			xml.setPrettyPrint(true);
			xml.writeStartDocument();
			// <pepper>
			xml.writeStartElement(WorkflowDescriptionReader.TAG_PEPEPR_JOB);
			if (getId() != null) {
				xml.writeAttribute(WorkflowDescriptionReader.ATT_ID, getId());
			}
			xml.writeAttribute(WorkflowDescriptionReader.ATT_VERSION, "1.0");
			// <customization> ???
			List<StepDesc> importers = new ArrayList<>();
			List<StepDesc> manipulators = new ArrayList<>();
			List<StepDesc> exporters = new ArrayList<>();
			for (StepDesc step : getStepDescs()) {
				if (MODULE_TYPE.IMPORTER.equals(step.getModuleType())) {
					importers.add(step);
				} else if (MODULE_TYPE.MANIPULATOR.equals(step.getModuleType())) {
					manipulators.add(step);
				} else if (MODULE_TYPE.EXPORTER.equals(step.getModuleType())) {
					exporters.add(step);
				}
			}

			// <importer>
			for (StepDesc step : importers) {
				xml.writeStartElement(WorkflowDescriptionReader.TAG_IMPORTER);
				save_module(xml, step);
				xml.writeEndElement();
			}
			// <manipulator>
			for (StepDesc step : manipulators) {
				xml.writeStartElement(WorkflowDescriptionReader.TAG_MANIPULATOR);
				save_module(xml, step);
				xml.writeEndElement();
			}
			// <exporter>
			for (StepDesc step : exporters) {
				xml.writeStartElement(WorkflowDescriptionReader.TAG_EXPORTER);
				save_module(xml, step);
				xml.writeEndElement();
			}
			xml.writeEndElement();
			xml.writeEndDocument();
			xml.flush();
		} catch (XMLStreamException | IOException e) {
			throw new PepperException("Cannot store Pepper job '" + getId() + "' because of a nested exception. ", e);
		}
		return (URI.createFileURI(file.getAbsolutePath()));
	}

	/**
	 * This method is just a helper method for method {@link #save(URI)} to
	 * avoid boilerplate code
	 * 
	 * @throws XMLStreamException
	 **/
	private static void save_module(XMLStreamWriter xml, StepDesc step) throws XMLStreamException {
		if (step.getName() != null) {
			xml.writeAttribute(WorkflowDescriptionReader.ATT_NAME, step.getName());
		}
		if (step.getCorpusDesc().getFormatDesc().getFormatName() != null) {
			xml.writeAttribute(WorkflowDescriptionReader.ATT_FORMAT_NAME,
					step.getCorpusDesc().getFormatDesc().getFormatName());
		}
		if (step.getCorpusDesc().getFormatDesc().getFormatVersion() != null) {
			xml.writeAttribute(WorkflowDescriptionReader.ATT_FORMAT_VERSION,
					step.getCorpusDesc().getFormatDesc().getFormatVersion());
		}
		if (step.getVersion() != null) {
			xml.writeAttribute(WorkflowDescriptionReader.ATT_NAME, step.getName());
		}
		if ((step.getCorpusDesc() != null) && (step.getCorpusDesc().getCorpusPath() != null)) {
			xml.writeAttribute(WorkflowDescriptionReader.ATT_PATH, step.getCorpusDesc().getCorpusPath().toFileString());
		}
		if ((step.getProps() != null) && (step.getProps().size() > 0)) {
			xml.writeStartElement(WorkflowDescriptionReader.TAG_CUSTOMIZATION);
			for (Object key : step.getProps().keySet()) {
				xml.writeStartElement(WorkflowDescriptionReader.TAG_PROP);
				xml.writeAttribute(WorkflowDescriptionReader.ATT_KEY, key.toString());
				if (step.getProps().get(key) != null) {
					xml.writeCharacters(step.getProps().get(key).toString());
				}
				xml.writeEndElement();
			}
			xml.writeEndElement();
		}
	}

	/**
	 * {@inheritDoc PepperJob#clear()}
	 */
	@Override
	public void clear() {
		// remove all existing steps
		if (stepDescs != null) {
			stepDescs.clear();
		}
		if (importSteps != null) {
			importSteps.clear();
		}
		if (manipulationSteps != null) {
			manipulationSteps.clear();
		}
		if (exportSteps != null) {
			exportSteps.clear();
		}
	}

	/**
	 * {@inheritDoc PepperJob#load(URI)}
	 */
	@Override
	public void load(URI uri) {
		if (uri.isFile()) {
			File wdFile = new File(uri.toFileString());
			// set folder containing workflow description as base dir
			setBaseDir(uri.trimSegments(1));

			SAXParser parser;
			XMLReader xmlReader;
			SAXParserFactory factory = SAXParserFactory.newInstance();

			WorkflowDescriptionReader contentHandler = new WorkflowDescriptionReader();
			contentHandler.setPepperJob(this);
			contentHandler.setLocation(uri);

			// remove all existing steps
			clear();

			try {
				parser = factory.newSAXParser();
				xmlReader = parser.getXMLReader();
				xmlReader.setContentHandler(contentHandler);
			} catch (ParserConfigurationException e) {
				throw new PepperModuleXMLResourceException("Cannot load Pepper workflow description file '"
						+ wdFile.getAbsolutePath() + "': " + e.getMessage() + ". ", e);
			} catch (Exception e) {
				throw new PepperModuleXMLResourceException("Cannot load Pepper workflow description file '"
						+ wdFile.getAbsolutePath() + "': " + e.getMessage() + ". ", e);
			}
			try {
				InputStream inputStream = new FileInputStream(wdFile);
				Reader reader = new InputStreamReader(inputStream, "UTF-8");
				InputSource is = new InputSource(reader);
				is.setEncoding("UTF-8");
				xmlReader.parse(is);
			} catch (SAXException e) {
				try {
					parser = factory.newSAXParser();
					xmlReader = parser.getXMLReader();
					xmlReader.setContentHandler(contentHandler);
					xmlReader.parse(wdFile.getAbsolutePath());
				} catch (Exception e1) {
					throw new PepperModuleXMLResourceException("Cannot load Pepper workflow description file '"
							+ wdFile.getAbsolutePath() + "': " + e1.getMessage() + ". ", e1);
				}
			} catch (Exception e) {
				if (e instanceof PepperModuleException) {
					throw (PepperModuleException) e;
				} else {
					throw new PepperModuleXMLResourceException("Cannot load Pepper workflow description file'" + wdFile
							+ "', because of a nested exception: " + e.getMessage() + ". ", e);
				}
			}
		} else {
			throw new UnsupportedOperationException(
					"Currently Pepper can only load workflow description from local files.");
		}
	}

	/**
	 * Returns a textual representation of this Pepper job. <strong>Note: This
	 * representation could not be used for serialization/deserialization
	 * purposes.</strong>
	 * 
	 * @return textual representation
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(getId());

		if (!getStepDescs().isEmpty()) {
			str.append("{");
			for (StepDesc stepDesc : getStepDescs()) {
				str.append(stepDesc.getName());
				str.append(", ");
			}
			str.append("}");
		} else {
			str.append("{");
			if (getImportSteps() != null) {
				for (Step step : getImportSteps()) {
					str.append(step.getName());
					str.append(", ");
				}
			}
			if (getManipulationSteps() != null) {
				for (Step step : getManipulationSteps()) {
					str.append(step.getName());
					str.append(", ");
				}
			}
			if (getExportSteps() != null) {
				for (Step step : getExportSteps()) {
					str.append(step.getName());
					str.append(", ");
				}
			}
			str.append("}");
		}
		return (str.toString());
	}
}
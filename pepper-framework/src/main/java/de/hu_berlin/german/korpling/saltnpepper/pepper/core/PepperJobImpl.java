/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.List;
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

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.JOB_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MEMORY_POLICY;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.StepDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.NotInitializedException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperInActionException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.WorkflowException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.DocumentController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleXMLResourceException;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

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
 * 
 * @author Florian Zipser
 * 
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
		if ((jobId == null) || (jobId.isEmpty()))
			throw new PepperFWException("Cannot initialize a PepperJob with an empty id.");
		id = jobId;
		setSaltProject(SaltFactory.eINSTANCE.createSaltProject());

	}

	/** The {@link SaltProject} which is converted by this job. **/
	protected SaltProject saltProject = null;

	/**
	 * Returns the {@link SaltProject} which is converted by this job.
	 * 
	 * @return {@link SaltProject}
	 */
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
		if (inProgress.isLocked())
			throw new PepperInActionException("Cannot set a new salt project to job '" + getId() + "', since this job was already started.");
		this.saltProject = saltProject;
	}

	/** properties to customize the behavior of conversion for this single job **/
	private PepperConfiguration props = null;

	/**
	 * returns the properties to customize the behavior of conversion for this
	 * single job.
	 * 
	 * @return given customization properties
	 */
	public PepperConfiguration getConfiguration() {
		if (props== null){
			props= new PepperConfiguration();
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
		if (inProgress.isLocked())
			throw new PepperInActionException("Cannot set a new configuration to job '" + getId() + "', since this job was already started.");
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
		if (inProgress.isLocked())
			throw new PepperInActionException("Cannot set a new module resolver to job '" + getId() + "', since this job was already started.");
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
		if (manipulationSteps == null)
			synchronized (this) {
				if (manipulationSteps == null) {
					manipulationSteps = new Vector<Step>();
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
		if (importSteps == null)
			synchronized (this) {
				if (importSteps == null) {
					importSteps = new Vector<Step>();
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
		if (exportSteps == null)
			synchronized (this) {
				if (exportSteps == null) {
					exportSteps = new Vector<Step>();
				}
			}
		return (exportSteps);
	}

	/**
	 * Returns a of all steps belonging no matter, to which phase they belong. <br/>
	 * <strong>This computation could be expensive, when working more than once
	 * with the list, make a local copy and don't call this method
	 * twice.</strong>
	 * 
	 * @return list of {@link Step} objects.
	 **/
	public List<Step> getAllSteps() {
		List<Step> allSteps = new Vector<Step>();
		if (getImportSteps() != null)
			allSteps.addAll(getImportSteps());
		if (getManipulationSteps() != null)
			allSteps.addAll(getManipulationSteps());
		if (getExportSteps() != null)
			allSteps.addAll(getExportSteps());
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
		if (inProgress.isLocked())
			throw new PepperInActionException("Cannot add a new step description to job '" + getId() + "', since this job was already started.");
		if (stepDesc == null)
			throw new WorkflowException("Cannot deal with an empty StepDesc object for job '" + getId() + "'.");
		if (getModuleResolver() == null)
			throw new PepperFWException("Cannot add step '" + stepDesc + "', because no module resolver is set.");

		Step step = null;
		if (MODULE_TYPE.MANIPULATOR.equals(stepDesc.getModuleType()))
			step = new Step("ma" + (getManipulationSteps().size() + 1), stepDesc);
		else if (MODULE_TYPE.IMPORTER.equals(stepDesc.getModuleType()))
			step = new Step("im" + (getImportSteps().size() + 1), stepDesc);
		else if (MODULE_TYPE.EXPORTER.equals(stepDesc.getModuleType()))
			step = new Step("ex" + (getExportSteps().size() + 1), stepDesc);
		else
			throw new WorkflowException("Cannot add step description, because the 'MODULE_TYPE' is not set.");

		addStep(step);

		return (step);
	}

	/**
	 * Adds the passed {@link Step} object to the workflow covered by this
	 * {@link PepperJobImpl} object and tries to resolve the described
	 * {@link PepperModule}. <h2>Prerequisite</h2>
	 * <ul>
	 * <li>{@link #getModuleResolver()} must be set</li>
	 * <li>{@link #getSaltProject()} must be set</li>
	 * </ul>
	 * 
	 * @param step
	 *            {@link Step} object to be added to internal list
	 */
	public synchronized void addStep(Step step) {
		if (inProgress.isLocked())
			throw new PepperInActionException("Cannot add a new step to job '" + getId() + "', since this job was already started.");

		if (step == null)
			throw new WorkflowException("Cannot deal with an empty step object for job '" + getId() + "'.");

		if ((step.getModuleController() == null) || (step.getModuleController().getPepperModule() == null)) {
			if (getModuleResolver() == null) {
				throw new PepperFWException("Cannot add the given step '" + step.getId() + "', because it does not contain a module controller and the module resolver for this job '" + getId() + "' is not set. So the Pepper module can not be estimated.");
			}
			if (getSaltProject() == null) {
				throw new PepperFWException("Cannot add a step '" + step.getId() + "', since no '" + SaltProject.class.getSimpleName() + "' is set for job '" + getId() + "'.");
			}
			PepperModule pepperModule = getModuleResolver().getPepperModule(step);
			if (pepperModule == null) {
				throw new WorkflowException("No Pepper module matching to step '" + step.getId() + "' was found: " + step);
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

		if (getImportSteps().size() == 0)
			throw new NotInitializedException("Cannot wire job '" + this + "', since no import steps were given.");
		if (getExportSteps().size() == 0)
			throw new NotInitializedException("Cannot wire job '" + this + "', since no export steps were given.");

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
			if (firstManipulationStep == null)
				throw new PepperFWException("The first step in list of manipulation steps is null.");

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
						DocumentBus documentBus = new DocumentBus(lastStep.getModuleController().getId(), manipulationStep.getModuleController().getId());
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
			if (lastManipulationStep == null)
				throw new PepperFWException("The last step in list of manipulation steps is null.");

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
	 * {@link PepperImporter#importCorpusStructure(de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph)}
	 * , all following modules will be asked, if they want to influence the
	 * order of importing documents. If this is the case, an order is created
	 * and put to all {@link PepperImporter} objects. <br/>
	 * This method produces as much as {@link SCorpusGraph} objects as
	 * {@link Step} given in import step list {@link #getImportSteps()}. The
	 * position of {@link SCorpusGraph} corresponding to {@link PepperImporter}
	 * (importing that graph) in {@link SaltProject#getSCorpusGraphs()} is
	 * equivalent to position of {@link Step} in list {@link #getImportSteps()}.
	 */
	protected synchronized void importCorpusStructures() {
		try {
			if (!isWired)
				wire();

			List<Future<?>> futures = new Vector<Future<?>>();
			int numOfImportStep = 0;
			for (Step importStep : getImportSteps()) {
				if (getSaltProject() == null)
					throw new PepperFWException("Cannot import corpus structure, because no salt project is set.");

				SCorpusGraph sCorpusGraph = null;

				if ((getSaltProject().getSCorpusGraphs().size() > numOfImportStep) && (getSaltProject().getSCorpusGraphs().get(numOfImportStep) != null)) {
					sCorpusGraph = getSaltProject().getSCorpusGraphs().get(numOfImportStep);
				} else {
					sCorpusGraph = SaltFactory.eINSTANCE.createSCorpusGraph();
					getSaltProject().getSCorpusGraphs().add(sCorpusGraph);
				}

				futures.add(importStep.getModuleController().importCorpusStructure(sCorpusGraph));
				numOfImportStep++;
			}

			for (Future<?> future : futures) {
				// wait until all corpus structures have been imported
				try {
					future.get();
				} catch (ExecutionException e) {
					throw new PepperModuleException("Failed to import corpus by module. Nested exception was: ", e.getCause());
				} catch (InterruptedException e) {
					throw new PepperFWException("Failed to import corpus by module. Nested exception was: ", e.getCause());
				} catch (CancellationException e) {
					throw new PepperFWException("Failed to import corpus by module. Nested exception was: ", e.getCause());
				}
			}

			int i = 0;
			for (Step step : getImportSteps()) {
				if (getSaltProject().getSCorpusGraphs().get(i) == null) {
					throw new PepperModuleException("The importer '" + step.getModuleController().getPepperModule() + "' did not import a corpus structure.");
				}

				// handle proposed import order
				List<SElementId> importOrder = unifyProposedImportOrders(getSaltProject().getSCorpusGraphs().get(i));
				for (SElementId sDocumentId : importOrder) {
					DocumentControllerImpl documentController = new DocumentControllerImpl();
					SDocument sDoc = (SDocument) sDocumentId.getSIdentifiableElement();
					if (sDoc.getSDocumentGraph() == null) {
						sDoc.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
					}
					documentController.setSDocument(sDoc);
					// sets flag to determine whether garbage collector should
					// be called after document was send to sleep
					if (getConfiguration() != null) {
						documentController.setCallGC(getConfiguration().getGcAfterDocumentSleep());
					}
					getDocumentControllers().add(documentController);

					File docFile = null;
					String prefix = sDoc.getSName();
					File tmpPath= new File(getConfiguration().getWorkspace().getAbsolutePath()+"/"+getId());
					if (!tmpPath.exists()){
						tmpPath.mkdirs();
					}
					try {
						if (prefix.length() < 3) {
							prefix = prefix + "artificial";
						}
						docFile = File.createTempFile(prefix, "." + SaltFactory.FILE_ENDING_SALT, tmpPath);
					} catch (IOException e) {
						throw new PepperFWException("Cannot store document '" + sDoc.getSName() + "' to file '"+((docFile== null)?docFile:docFile.getAbsolutePath())+"' in folder for temporary files '"+tmpPath+"'. "+e.getMessage(), e);
					}
					documentController.setLocation(URI.createFileURI(docFile.getAbsolutePath()));
					if (!getConfiguration().getKeepDocuments()){
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
		} catch (Exception e) {
			if (e instanceof PepperException)
				throw (PepperException) e;
			else
				throw new PepperFWException("An exception occured in job '" + getId() + "' while importing the corpus-structure. See nested exception: ", e);
		}
	}

	/**
	 * Returns a list of {@link SElementId}s corresponding to the
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
	protected List<SElementId> unifyProposedImportOrders(SCorpusGraph sCorpusGraph) {
		List<SElementId> retVal = new Vector<SElementId>();

		if (sCorpusGraph == null)
			throw new PepperFWException("Cannot unify the import order, for an empty SCorpusGraph object.");

		Vector<List<SElementId>> listOfOrders = new Vector<List<SElementId>>();
		for (Step step : getAllSteps()) {
			if (step.getModuleController() == null)
				throw new PepperFWException("Cannot unify proposed import orders, since step '" + step.getId() + "' does not contain a module controller.");
			if (step.getModuleController().getPepperModule() == null)
				throw new PepperFWException("Cannot unify proposed import orders, since module controller '" + step.getModuleController().getId() + "' does not contain a Pepper module.");

			List<SElementId> importOrder = step.getModuleController().getPepperModule().proposeImportOrder(sCorpusGraph);
			if ((importOrder != null) && (importOrder.size() > 0)) {
				if (importOrder.size() < sCorpusGraph.getSDocuments().size()) {
					for (SDocument sDoc : sCorpusGraph.getSDocuments()) {
						if (!importOrder.contains(sDoc.getSElementId()))
							importOrder.add(sDoc.getSElementId());
					}
				}
				listOfOrders.add(importOrder);
			}
		}
		if (listOfOrders.size() == 0) {// if no proposals have been made, make
										// the natural one
			for (SDocument sDocument : sCorpusGraph.getSDocuments()) {
				retVal.add(sDocument.getSElementId());
			}
		} else if (listOfOrders.size() == 1) {
			retVal = listOfOrders.get(0);
		} else {
			retVal = listOfOrders.get(0);
			logger.warn("Sorry the feature of unifying more than one list of proposed import orders is not yet implemented. ");
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
		retVal.append(getCurrNumberOfDocuments());
		retVal.append(" of ");
		retVal.append(getMaxNumberOfDocuments());
		retVal.append("\n");

		retVal.append("status:\t\t\t");
		if (JOB_STATUS.NOT_STARTED.equals(getStatus())) {
			retVal.append(JOB_STATUS.NOT_STARTED);
		} else if (JOB_STATUS.ENDED.equals(getStatus())) {
			retVal.append(JOB_STATUS.ENDED);
		} else if (JOB_STATUS.ENDED_WITH_ERRORS.equals(getStatus())) {
			retVal.append(JOB_STATUS.ENDED_WITH_ERRORS);
		} else if (JOB_STATUS.IN_PROGRESS.equals(getStatus())) {
			retVal.append(JOB_STATUS.IN_PROGRESS);
		} else {
			retVal.append("UNDEFINED STATUS");
		}
		retVal.append("\n");

		StringBuilder detailedStr = new StringBuilder();
		double progressOverAll = 0;
		int numOfDocuments = 0;

		if (getDocumentControllers().size() == 0) {
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
			// distance is distance plus 4??? plus length of string sleep
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
			retVal.append(detailedStr.toString());
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
	 * @return false, if one of the {@link PepperModule} objects returned false.
	 */
	protected synchronized Boolean checkReadyToStart() {
		Boolean retVal = true;
		for (Step step : getAllSteps()) {
			if (!step.getModuleController().getPepperModule().isReadyToStart()) {
				retVal = false;
				logger.error("Cannot run pepper job '" + getId() + "', because one of the involved modules '" + step.getModuleController().getPepperModule().getFingerprint() + "' is not ready to run.");
			}
		}
		return (retVal);
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
	 * <li>If
	 * {@link PepperImporter#importCorpusStructure(de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph)}
	 * has not already been called, it will be done.
	 * <li>
	 * </ul>
	 */
	public void convert() {
		if (!inProgress.tryLock())
			throw new PepperInActionException("Cannot run convert() of job '" + getId() + "', since this job was already started.");
		inProgress.lock();
		try {
			status = JOB_STATUS.IN_PROGRESS;
			if (!isWired) {
				wire();
			}
			if (!isReadyToStart) {
				if (!checkReadyToStart()) {
					throw new PepperException("Cannot run Pepper job '" + getId() + "', because one of the involved job is not ready to run.");
				}
			}
			if (!isImportedCorpusStructure)
				importCorpusStructures();
			List<Pair<ModuleControllerImpl, Future<?>>> futures = new Vector<Pair<ModuleControllerImpl, Future<?>>>();
			for (Step step : getAllSteps()) {
				if (step.getModuleController().getPepperModule().getSaltProject() == null)
					step.getModuleController().getPepperModule().setSaltProject(getSaltProject());
				futures.add(new ImmutablePair<ModuleControllerImpl, Future<?>>(step.getModuleController(), step.getModuleController().importDocumentStructures()));
			}
			for (Pair<ModuleControllerImpl, Future<?>> future : futures) {
				// wait until all document-structures have been imported
				try {
					future.getRight().get();
				} catch (ExecutionException e) {
					if (	(e.getCause()!= null)&&
							(e.getCause() instanceof PepperException)){
							throw (PepperException)e.getCause();
					}
					throw new PepperModuleException("Failed to process document by module '" + future.getLeft() + "'. Nested exception was: ", e.getCause());
				} catch (InterruptedException e) {
					if (	(e.getCause()!= null)&&
							(e.getCause() instanceof PepperException)){
							throw (PepperException)e.getCause();
					}
					throw new PepperFWException("Failed to process document by module '" + future.getLeft() + "'. Nested exception was: ", e.getCause());
				} catch (CancellationException e) {
					if (	(e.getCause()!= null)&&
							(e.getCause() instanceof PepperException)){
							throw (PepperException)e.getCause();
					}
					throw new PepperFWException("Failed to process document by module '" + future.getLeft() + "'. Nested exception was: ", e.getCause());
				}
			}
			status = JOB_STATUS.ENDED;
		} catch (Exception e) {
			status = JOB_STATUS.ENDED_WITH_ERRORS;
			if (e instanceof PepperException)
				throw (PepperException) e;
			else
				throw new PepperFWException("An exception occured in job '" + getId() + "' while importing the corpus-structure. See nested exception: ", e);
		} finally {
			inProgress.unlock();
		}
	}

	/**
	 * Imports a {@link SaltProject} from any format. For conversion a process
	 * can be modeled, similar to {@link #convert()} with the difference, that
	 * no {@link PepperExporter} could be defined. Instead, the processed
	 * {@link SaltProject} is the result.
	 */
	public void convertFrom() {
		if (!inProgress.tryLock())
			throw new PepperInActionException("Cannot run convert() of job '" + getId() + "', since this job was already started.");
		inProgress.lock();
		try {
			// TODO implement this
		} finally {
			inProgress.unlock();
		}
	}

	/**
	 * Exports the SaltProject into any format. For conversion, a normal process
	 * could be created, except the use of an importer. Here the do-nothing
	 * importer is used, and it is expected, that the {@link SaltProject} is
	 * already 'filled'.
	 */
	public void convertTo() {
		if (!inProgress.tryLock())
			throw new PepperInActionException("Cannot run convert() of job '" + getId() + "', since this job was already started.");
		inProgress.lock();
		try {
			// TODO implement this
		} finally {
			inProgress.unlock();
		}
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
	 * Determines the current number of {@link SDocument} objects which could be
	 * processed at the same time
	 **/
	private volatile int currNumOfDocuments = 0;

	/**
	 * Returns the current number of {@link SDocument} objects which could be
	 * processed at the same time
	 * 
	 * @return number of documents
	 */
	public int getCurrNumberOfDocuments() {
		return (currNumOfDocuments);
	}

	/** lock for correct change of {@link #currNumOfDocuments} **/
	private volatile Lock numOfDocsLock = new ReentrantLock();
	/**
	 * condition for notifying {@link #getPermissionForProcessDoument()} when a
	 * document was released via {@link #releaseDocument()}
	 **/
	private volatile Condition numOfDocsCondition = numOfDocsLock.newCondition();

	/**
	 * Returns true, if a {@link SDocument} or more precisely spoken a
	 * {@link SDocumentGraph} could be woken up or imported. This is the case,
	 * as long as: <br/>
	 * {@link #getCurrNumberOfDocuments()} < {@link #getMaxNumberOfDocuments()}. <br/>
	 * Must be synchronized,
	 * 
	 * @return true, when #getCurrNumberOfDocuments()} <
	 *         {@link #getMaxNumberOfDocuments(), false otherwise
	 */
	public boolean getPermissionForProcessDoument(DocumentController controller) {
		numOfDocsLock.lock();
		if (!MEMORY_POLICY.GREEDY.equals(getMemPolicy())) {
			try {
				while (getCurrNumberOfDocuments() >= getMaxNumberOfDocuments()) {
					numOfDocsCondition.await();
				}
				currNumOfDocuments++;
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
	 * currently processed documents ({@link #getCurrNumberOfDocuments()}).
	 */
	public void releaseDocument(DocumentController controller) {
		numOfDocsLock.lock();
		try {
			currNumOfDocuments--;
			// TODO not sure, if signal() is correct, or if signalAll() should
			// be used, but I would think, that only one waiter has to be
			// notified
			numOfDocsCondition.signal();
		} finally {
			numOfDocsLock.unlock();
		}
	}

	// ======================================= end: managing number of active
	// documents

	/**
	 * {@inheritDoc PepperJob#load(URI)}
	 */
	@Override
	public void load(URI uri) {
		if (uri.isFile()) {
			File wdFile = new File(uri.toFileString());

			SAXParser parser;
			XMLReader xmlReader;
			SAXParserFactory factory = SAXParserFactory.newInstance();
			
			WorkflowDescriptionReader contentHandler = new WorkflowDescriptionReader();
			contentHandler.setPepperJob(this);
			contentHandler.setLocation(uri);
			try {
				parser = factory.newSAXParser();
				xmlReader = parser.getXMLReader();
				xmlReader.setContentHandler(contentHandler);
			} catch (ParserConfigurationException e) {
				throw new PepperModuleXMLResourceException("Cannot load Pepper workflow description file '" + wdFile.getAbsolutePath() + "'.", e);
			} catch (Exception e) {
				throw new PepperModuleXMLResourceException("Cannot load Pepper workflow description file '" + wdFile.getAbsolutePath() + "'.", e);
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
					throw new PepperModuleXMLResourceException("Cannot load Pepper workflow description file '" + wdFile.getAbsolutePath() + "'.", e1);
				}
			} catch (Exception e) {
				if (e instanceof PepperModuleException)
					throw (PepperModuleException) e;
				else
					throw new PepperModuleXMLResourceException("Cannot load Pepper workflow description file'" + wdFile + "', because of a nested exception. ", e);
			}
		} else
			throw new UnsupportedOperationException("Currently Pepper can only load workflow description from local files.");
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
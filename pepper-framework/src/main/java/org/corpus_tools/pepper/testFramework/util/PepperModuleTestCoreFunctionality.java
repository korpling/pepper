package org.corpus_tools.pepper.testFramework.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.corpus_tools.pepper.core.ModuleControllerImpl;
import org.corpus_tools.pepper.exceptions.PepperTestException;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleTestException;
import org.corpus_tools.pepper.testFramework.PepperTestUtil;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PepperModuleTestCoreFunctionality<M extends PepperModule> {
	protected static final Logger logger = LoggerFactory.getLogger("Pepper");
	protected M testedModule = null;
	protected static final URI DEFAULT_RESOURCE_PATH = URI.createFileURI("src/main/resources");
	protected URI resourceURI = DEFAULT_RESOURCE_PATH;

	/**
	 * Sets the module to be tested. The module can be accessed via
	 * {@link #testedModule}.
	 */
	protected void setTestedModule(M testedModule) {
		if (testedModule == null) {
			throw new PepperModuleTestException(
					"Cannot start pepper test, because no pepper module was set for current test. ");
		}
		this.testedModule = testedModule;
		if (resourceURI != null) {
			testedModule.setResources(resourceURI);
		}
		testedModule.setSaltProject(SaltFactory.createSaltProject());
		testedModule.getSaltProject().add(SaltFactory.createSCorpusGraph());

	}

	protected M getTestedModule() {
		return testedModule;
	}

	/**
	 * Sets the path to resources folder for the module to be tested. The
	 * default path is set to {@value #DEFAULT_RESOURCE_PATH}.
	 */
	protected void setResourcesURI(URI resourceURI) {
		if (resourceURI == null) {
			throw new PepperTestException(
					"The specified resource path was null. Ülease set a proper value or use the default path '"
							+ DEFAULT_RESOURCE_PATH + "'. ");
		}
		File resourceDir = new File(resourceURI.toFileString());
		if (!resourceDir.exists()) {
			if (!resourceDir.mkdirs()) {
				logger.warn("Cannot create folder {}. ", resourceDir);
			}
		}
		this.resourceURI = resourceURI;
		if (testedModule != null) {
			testedModule.setResources(resourceURI);
		}
	}

	/**
	 * This methods starts the processing of Pepper in the development
	 * environment. In case of the fixture is {@link PepperImporter}, first the
	 * method {@link PepperImporter#importCorpusStructure(SCorpusGraph)} is
	 * called. For all kinds of fixture, the method
	 * {@link PepperModule#start(org.corpus_tools.salt.graph.Identifier)} is
	 * called for each {@link SDocument} object contained in the variable
	 * {@link PepperModule#getSaltProject()}. This method will wait, until each
	 * {@link ModuleControllerImpl} return having finished the process. <br/>
	 * To create a test using this method do the following:<br/>
	 * <ul>
	 * <li>Create {@link CorpusDefinition} and add it to this object and set its
	 * {@link FormatDefinition} and corpus path</li>
	 * <li>Create a {@link SCorpusGraph} object as the one to be filled and add
	 * it with
	 * 
	 * <pre>
	 * getFixture().getSaltProject().getCorpusGraphs().add(importedCorpusGraph);
	 * getFixture().importCorpusStructure(importedCorpusGraph);
	 * </pre>
	 * 
	 * </li>
	 * </ul>
	 */
	public void start() {
		Collection<M> fixtures = new ArrayList<M>();
		fixtures.add(testedModule);
		PepperTestUtil.start(fixtures);
	}
}

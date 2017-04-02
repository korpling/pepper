package org.corpus_tools.pepper.testFramework.util;

import java.io.File;

import org.corpus_tools.pepper.exceptions.PepperTestException;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.salt.SaltFactory;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PepperModuleTestCoreFunctionality {
	protected static final Logger logger = LoggerFactory.getLogger("Pepper");
	protected PepperModule testedModule = null;
	protected static final URI DEFAULT_RESOURCE_PATH = URI.createFileURI("src/main/resources");
	protected URI resourceURI = DEFAULT_RESOURCE_PATH;

	// /**
	// * Returns the module to be tested.
	// */
	// protected PepperModule testedModule {
	// return testedModule;
	// }

	/**
	 * Sets the module to be tested. The module can be accessed via
	 * {@link #testedModule}.
	 */
	protected void setFixture(PepperModule fixture) {
		this.testedModule = fixture;
		if (resourceURI != null) {
			testedModule.setResources(resourceURI);
		}
		testedModule.setSaltProject(SaltFactory.createSaltProject());
		testedModule.getSaltProject().addCorpusGraph(SaltFactory.createSCorpusGraph());

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
}

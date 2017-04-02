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
	protected PepperModule fixture = null;
	protected static final URI DEFAULT_RESOURCE_PATH = URI.createFileURI("src/main/resources");
	protected URI resourceURI = DEFAULT_RESOURCE_PATH;

	/**
	 * Returns the module to be tested.
	 */
	protected PepperModule getFixture() {
		return fixture;
	}

	/**
	 * Sets the module to be tested. The module can be accessed via
	 * {@link #getFixture()}.
	 */
	protected void setFixture(PepperModule fixture) {
		this.fixture = fixture;
		if (resourceURI != null) {
			getFixture().setResources(resourceURI);
		}
		getFixture().setSaltProject(SaltFactory.createSaltProject());
		getFixture().getSaltProject().addCorpusGraph(SaltFactory.createSCorpusGraph());

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
		if (getFixture() != null) {
			getFixture().setResources(resourceURI);
		}
	}
}

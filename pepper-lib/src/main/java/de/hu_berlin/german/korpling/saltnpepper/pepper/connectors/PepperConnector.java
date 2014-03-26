package de.hu_berlin.german.korpling.saltnpepper.pepper.connectors;

import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.PepperStarterConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;

public interface PepperConnector extends Pepper{

	/**
	 * Starts the OSGi framework and initializes {@link Pepper} inside the framework.
	 */
	public abstract void init();

	/**
	 * Sets the {@link PepperStarterConfiguration} to be used by this {@link PepperConnector}. The {@link PepperStarterConfiguration} can only
	 * be set once. A second call of this method would be ignored. 
	 * @param properties the properties to set
	 */
	public abstract void setProperties(PepperStarterConfiguration props);

	/**
	 * Returns the {@link PepperStarterConfiguration} which are used by this {@link PepperConnector}.
	 * @return the properties
	 */
	public abstract PepperStarterConfiguration getProperties();

}
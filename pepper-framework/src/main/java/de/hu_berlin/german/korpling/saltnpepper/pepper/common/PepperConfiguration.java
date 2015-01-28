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
package de.hu_berlin.german.korpling.saltnpepper.pepper.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Property;

import de.hu_berlin.german.korpling.saltnpepper.pepper.core.DocumentBus;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperConfigurationException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * This class contains all possible configurations, to take influence on the
 * Pepper process. A configuration is realized as a {@link Property}, therefore
 * this class is derived from the general {@link Properties} class. It is
 * enhanced for methods to have an easier access to fields to configure the
 * Pepper framework, but you can also find anything necessary when treating this
 * object as a normal {@link Properties} object. <h1>Loading and resolving
 * configuration</h1> Next to the the mechanisms of loading a prop file or an
 * xml file, this object can also resolve the configuration file by checking the
 * location of the Pepper bundle. It is assumed, that the configuration file is
 * contained in one of two possible locations.
 * <ol>
 * <li>development phase: it is assumed, that the configuration file is stored
 * in PEPPER_SOURCE_HOME/src/main/resoruces</li>
 * <li>testing phase: it is assumed, that the configuration file is stored in
 * BUNDLE_LOCATION/../BUNDLE_NAME</li>
 * </ol>
 * 
 * @author Florian Zipser
 * 
 */
public class PepperConfiguration extends Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7702415220939613420L;
	/** A sub folder in Pepper directory to be used as workspace (to store jobs etc.)**/
	public static final String DEFAULT_WORKSPACE="workspace";
	/**
	 * flag if Pepper shall measure and display the performance of the used
	 * PepperModules
	 */
	public static final String PROP_PREFIX = "pepper";

	/**
	 * flag if Pepper shall measure and display the performance of the used
	 * PepperModules
	 */
	public static final String PROP_COMPUTE_PERFORMANCE = PROP_PREFIX + ".computePerformance";
	/**
	 * Name of the property, which determines a temporary folder, where the
	 * framework and all modules can store temporary data.
	 */
	public static final String PROP_TEMP_FOLDER = PROP_PREFIX + ".temporaries";
	/**
	 * the maximal number of currently processed SDocument-objects
	 */
	public static final String PROP_MAX_AMOUNT_OF_SDOCUMENTS = PROP_PREFIX + ".maxAmountOfProcessedSDocuments";
	/**
	 * Name of property to set the memory policy, for instance to configure the
	 * behavior of loading documents. See {@link MEMORY_POLICY} for more
	 * details.
	 **/
	public static final String PROP_MEMORY_POLICY = PROP_PREFIX + ".memPolicy";
	/**
	 * Name of property, to determine, if the garbage collector should run after
	 * a {@link SDocument} has been send to sleep when it waits in
	 * {@link DocumentBus} between to steps.
	 */
	public static final String PROP_CALL_GC_AFTER_DOCUMENT = PROP_PREFIX + ".gcAfterDocumentSleep";
	/**
	 * the flag if an SDocument-object shall be removed after it was processed
	 * by all PepperModules
	 */
	public static final String PROP_REMOVE_SDOCUMENTS_AFTER_PROCESSING = PROP_PREFIX + ".removeSDocumentAfterProcessing";
	/**
	 * name of the flag to determine whether the temporary created document-graph files should be
	 * preserved or deleted after Pepper terminates.
	 */
	public static final String PROP_KEEP_TEMP_DOCS = PROP_PREFIX + ".keepTempDocs";
	/**
	 * name of the flag to determine a workspace for pepper, where all jobs are
	 * stored by default.
	 */
	public static final String PROP_WORKSPACE = PROP_PREFIX + ".workspace";

	/**
	 * This array contains all properties, which with the Pepper framework can
	 * be configured.
	 */
	public static final String[] ALL_PROP_NAMES = { PROP_COMPUTE_PERFORMANCE, PROP_MAX_AMOUNT_OF_SDOCUMENTS, PROP_REMOVE_SDOCUMENTS_AFTER_PROCESSING };
	public static final String ENV_PEPPER_MODULE_RESOURCES = "pepper.modules.resources";
	public static final String PROP_PEPPER_MODULE_RESOURCES = "pepper.modules.resources";

	/**
	 * Folder where the resources of a bundle is expected to be. This follows
	 * the maven structure of a project
	 **/
	public static final String SOURCES_RESOURCES = "src/main/resources/";
	/** Name of the folder containing configuration files. **/
	public static final String FILE_CONF_FOLDER = "conf";
	/** Name of the folder configuration file. **/
	public static final String FILE_CONF_FILE = "userDefined.properties";

	/**
	 * standard constructor
	 */
	public PepperConfiguration() {
		put(PROP_CALL_GC_AFTER_DOCUMENT, Boolean.TRUE);
		put(PROP_MEMORY_POLICY, MEMORY_POLICY.MODERATE);
	}

	/**
	 * Initializes a new object and copies all properties of the given
	 * {@link Properties} object.
	 * 
	 * @param properties
	 */
	public PepperConfiguration(Properties properties) {
		Enumeration<Object> propNames = properties.keys();
		while (propNames.hasMoreElements()) {
			String propName = propNames.nextElement().toString();
			if (propName != null) {
				put(propName, properties.getProperty(propName));
			}
		}

	}

	/**
	 * Loads the configuration from the passed file location
	 * 
	 * @param configuration
	 */
	public void load(File configurationFile) {
		confFolder = configurationFile.getParentFile();
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(configurationFile.getAbsolutePath()));
			load(in);
		} catch (FileNotFoundException e2) {
			throw new PepperConfigurationException("Cannot load configuration file for Pepper at location '" + configurationFile.getAbsolutePath() + "', because of nested exception: ", e2);
		} catch (IOException e) {
			throw new PepperConfigurationException("Cannot load configuration file for Pepper at location '" + configurationFile.getAbsolutePath() + "', because of nested exception: ", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				throw new PepperConfigurationException("Cannot close stream to configuration file for Pepper at location '" + configurationFile.getAbsolutePath() + "', because of nested exception: ", e);
			}
		}
	}

	/**
	 * Resolves the location of the bundle by the given {@link ComponentContext}
	 * and loads the configuration file. Expects the configuration file to be in
	 * one of the two locations:
	 * <ol>
	 * <li>development phase: it is assumed, that the configuration file is
	 * stored in PEPPER_SOURCE_HOME/src/main/resoruces</li>
	 * <li>testing phase: it is assumed, that the configuration file is stored
	 * in BUNDLE_LOCATION/../BUNDLE_NAME</li>
	 * </ol>
	 * 
	 * @param componentContext
	 */
	public void load(ComponentContext componentContext) {
		if (componentContext == null)
			throw new PepperConfigurationException("Cannot resolve configuration file for Pepper, because the given component context is null.");

		String configFileStr = null;

		if ((componentContext != null) && (componentContext.getBundleContext() != null) && (componentContext.getBundleContext().getBundle() != null) && (componentContext.getBundleContext().getBundle().getLocation() != null)) {
			if (componentContext != null) {
				String[] bundleNames = System.getProperty("osgi.bundles").split(",");
				if (bundleNames.length > 0) {
					String currLocation = componentContext.getBundleContext().getBundle().getLocation();
					currLocation = currLocation.replace("initial@reference:file:", "");
					currLocation = currLocation.replace("../", "");
					if (currLocation.endsWith("/"))
						currLocation = currLocation.substring(0, currLocation.length() - 1);

					String location = null;
					for (String bundleName : bundleNames) {
						bundleName = bundleName.replace("reference:", "");
						bundleName = bundleName.replaceAll("@([0-9]+:)?start", "");

						if (bundleName.endsWith(currLocation)) {
							location = bundleName;
							break;
						}
					}
					if (location.endsWith(".jar"))
						location = location.replace(".jar", "/");
					else {
						if (!location.endsWith("/"))
							location = location + "/";
						location = location + SOURCES_RESOURCES;

					}
					configFileStr = location;
					if (configFileStr.startsWith("file:"))
						configFileStr = configFileStr.replace("file:", "");
					configFileStr = configFileStr + FILE_CONF_FOLDER + "/" + FILE_CONF_FILE;
				}
			}
		}
		load(new File(configFileStr));
	}
	/** folder containing the configuration file(s) **/
	private File confFolder = null;
	/**
	 * Sets the folder containing the configuration file(s).
	 * @param confFolder
	 */
	protected void setConfFolder(File confFolder){
		this.confFolder= confFolder;
	}

	/**
	 * Returns the folder containing the configuration file(s). If no
	 * configuration file is used this method returns null.
	 * 
	 * @return
	 */
	public File getConfFolder() {
		return (confFolder);
	}

	/**
	 * Returns a temporary path, where the entire system and all modules can
	 * store temp files. If no temp folder is given by configuration file, the
	 * default temporary folder given by the operating system is used.
	 * 
	 * @return path, where to store temporary files
	 */
	public File getTempPath() {
		String tmpFolderStr = getProperty(PROP_TEMP_FOLDER);
		File tmpFolder= null;
		if (tmpFolderStr != null) {
			tmpFolderStr = tmpFolderStr + "/pepper/";
			tmpFolder = new File(tmpFolderStr);
			if (!tmpFolder.exists()) {
				tmpFolder.mkdirs();
			}
		}else{
			tmpFolder= PepperUtil.getTempFile();
		}
		return (tmpFolder);
	}

	/**
	 * Returns the default workspace for {@link PepperJobImpl} objects, if one
	 * is given in configuration. If no one is given, the temporary folder is returned.
	 * 
	 * @return workspace to be used to store {@link PepperJobImpl}
	 */
	public File getWorkspace() {
		File workspace = null;
		String workspaceStr = getProperty(PROP_WORKSPACE);
		if ((workspaceStr != null) && (!workspaceStr.isEmpty())) {
			workspace = new File(workspaceStr);
		}
		if (workspace== null){
			workspace= new File(getTempPath().getAbsolutePath()+"/"+ DEFAULT_WORKSPACE);
		}
		return (workspace);
	}

	/**
	 * Returns the memory policy for instance to configure the behavior of
	 * loading documents. See {@link MEMORY_POLICY} for more details.
	 * 
	 * @return
	 */
	public MEMORY_POLICY getMemPolicy() {
		String memPolicyStr = getProperty(PROP_MEMORY_POLICY, MEMORY_POLICY.MODERATE.toString());
		return (MEMORY_POLICY.valueOf(memPolicyStr));
	}

	/**
	 * Returns the the maximal number of currently processed SDocument-objects
	 * 
	 * @return
	 */
	public Integer getMaxAmountOfDocuments() {
		String amountOfDocs = getProperty(PROP_MAX_AMOUNT_OF_SDOCUMENTS, new Integer(10).toString());
		return (new Integer(amountOfDocs));
	}

	public Boolean getGcAfterDocumentSleep() {
		String callGC = getProperty(PROP_CALL_GC_AFTER_DOCUMENT, Boolean.TRUE.toString());
		return (Boolean.valueOf(callGC));
	}
	
	/**
	 * name of the flag to determine whether the temporary created document-graph files should be
	 * preserved or deleted after Pepper terminates.
	 * Default value is false.
	 */
	public Boolean getKeepDocuments() {
		String isToKeep = getProperty(PROP_KEEP_TEMP_DOCS, Boolean.FALSE.toString());
		return (Boolean.valueOf(isToKeep));
	}
}

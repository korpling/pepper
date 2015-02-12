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
package de.hu_berlin.german.korpling.saltnpepper.pepper.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.osgi.framework.Constants;

import com.google.common.base.Splitter;

import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.exceptions.PepperPropertyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;

/**
 * This class represents all properties to customize Pepper. This class is
 * derived from the general class {@link Properties} and provides some shortcuts
 * to access specific pepper properties. By default the properties are loaded
 * from folder {@link #FILE_PEPPER_CONF}. If the file
 * {@link #FILE_PEPPER_TEST_PROP} exists, this one will be taken, otherwise the
 * system tries to load file {@link #FILE_PEPPER_PROP}. This behavior can be
 * changed by explicitly passing a properties file. <br/>
 * To initialize the configuration, if no {@link File} or {@link Properties} are
 * passed via the constructor, please call method {@link #load()} or one of the
 * other load methods.
 * <ol>
 * <li>You can call the simple constructor {@link #PepperProperties()} having no
 * parameters. This constructor will make use of the pepper home path mechanism
 * and assumes a file named {@value #FILE_PEPPER_PROP} in the pepper home path</li>
 * 
 * </ol>
 * This class uses a location called pepper home path, where it assumes all
 * resources necessary to run Pepper. If no customization for these resources is
 * given, default values are assumed. The pepper home path if not explicitly
 * given is detected in the following order:
 * <ol>
 * <li>check environment variable {@value ENV_PEPPER_HOME}</li>
 * <li>check system property variable {@value ENV_PEPPER_HOME}</li>
 * <li>set user.dir as pepper home</li>
 * </ol>
 * 
 * @author Florian Zipser
 * 
 */
public class PepperStarterConfiguration extends PepperConfiguration {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3427728837658732050L;

	/** prefix of all properties */
	public static final String PROP_PREFIX = "pepper.";

	/** Name of the environment variable specifying the pepper home path. */
	public static final String ENV_PEPPER_HOME = "PEPPER_HOME";

	/** Name of the system property specifying the pepper home path. */
	public static final String PROP_PEPPER_HOME = PROP_PREFIX + "home";
	/** Name of property to determine the width of outout console. The width could be either 120 or 80. **/
	public static final String PROP_CONSOLE_WIDTH = PROP_PREFIX + "console.width";
	/**
	 * A key for using the a variable for the pepper home path inside of
	 * property files.
	 */
	public static final String KEY_PEPPER_HOME = "$" + ENV_PEPPER_HOME;
	/** Name of the file containing all pepper properties. */
	public static final String FILE_PEPPER_PROP = "pepper.properties";
	/** Name of the file containing all pepper properties. */
	public static final String FILE_PEPPER_TEST_PROP = "pepper-test.properties";
	/** Folder, where to find all configuration files for Pepper */
	public static final String FILE_PEPPER_CONF = "conf";
	/** name of the property of where to find the plugin path */
	public static final String PROP_PLUGIN_PATH = PROP_PREFIX + "plugin.path";
	/** name of the property of where to find the dropin paths (comma seperated) */
	public static final String PROP_DROPIN_PATHS = PROP_PREFIX + "dropin.paths";
	/** name of the property of the location of the osgi profile */
	public static final String PROP_OSGI_PROFILE = PROP_PREFIX + "osgi.profile";
	/** name of the property of the location of the osgi profile */
	public static final String PROP_OSGI_SHAREDPACKAGES = PROP_PREFIX + Constants.FRAMEWORK_SYSTEMPACKAGES;
	/** pepper-eMail address */
	public static final String EMAIL = "saltnpepper@lists.hu-berlin.de";
	/** pepper-homepage */
	public static final String HOMEPAGE = "http://u.hu-berlin.de/saltnpepper";

	/**
	 * Extracts the home path of pepper and returns it. The home path is
	 * searched in following order:
	 * <ol>
	 * <li>check environment variable {@value ENV_PEPPER_HOME}</li>
	 * <li>check system property variable {@value ENV_PEPPER_HOME}</li>
	 * <li>set user.dir as pepper home</li>
	 * </ol>
	 * 
	 * @return path of pepper home
	 */
	public static File findPepperHome() {
		String pepperHome = null;

		// check environment variable
		pepperHome = System.getenv().get(ENV_PEPPER_HOME);
		// check system properties
		if (pepperHome == null)
			pepperHome = System.getProperty(PROP_PEPPER_HOME);
		// check current working directory
		if (pepperHome == null)
			pepperHome = System.getProperty("user.dir") + "/";

		if ((!pepperHome.endsWith("/")) && (!pepperHome.endsWith("\\")))
			pepperHome = pepperHome + "/";
		pepperHome = pepperHome.replace("\\", "/");

		File pepperHomeFile = new File(pepperHome);
		return (pepperHomeFile);
	}

	/**
	 * Creates a {@link PepperStarterConfiguration} object, without setting any
	 * property.
	 */
	public PepperStarterConfiguration() {
	}

	/**
	 * Creates a {@link PepperStarterConfiguration} object, using the given file
	 * to read the properties to be load into the new created object.
	 * 
	 * @param propertiesFile
	 *            file containing the pepper properties
	 */
	public PepperStarterConfiguration(File propertiesFile) {
		load(propertiesFile);
	}

	/**
	 * For test purposes, properties are load from the file
	 * {@link #FILE_PEPPER_TEST_PROP}, which is assumed to be in the
	 * {@link #FILE_PEPPER_CONF} folder in the pepper home path. If this file
	 * does not exists, the properties are tried to load from the file
	 * {@link #FILE_PEPPER_PROP} in the same folder.
	 */
	public void load() {
		File pepperHome = findPepperHome();
		File propFile = new File(pepperHome.getAbsolutePath() + "/" + FILE_PEPPER_CONF + "/" + FILE_PEPPER_TEST_PROP + "/");
		if (!propFile.exists()) {
			propFile = new File(pepperHome.getAbsolutePath() + "/" + FILE_PEPPER_CONF + "/" + FILE_PEPPER_PROP + "/");
		}
		load(propFile);
	}

	/**
	 * Loads pepper properties from given file.
	 * 
	 * @param propertiesFile
	 */
	public void load(File propertiesFile) {
		if (!propertiesFile.exists())
			throw new PepperPropertyException("Cannot read pepper property file, because it does not exist '" + propertiesFile.getAbsolutePath() + "'.");
		try {
			this.setConfFolder(propertiesFile.getParentFile());
			this.load(new FileInputStream(propertiesFile));
			this.cleanUp_PepperPath();
		} catch (FileNotFoundException e) {
			throw new PepperPropertyException("Cannot read pepper property file, because of a nested exception.", e);
		} catch (IOException e) {
			throw new PepperPropertyException("Cannot read pepper property file, because of a nested exception.", e);
		}
	}

	/**
	 * Replaces {@value #KEY_PEPPER_HOME} with path in all properties.
	 */
	private void cleanUp_PepperPath() {
		String replacePath = findPepperHome().getAbsolutePath();
		for (Object key : Collections.synchronizedSet(keySet())) {
			put(key, get(key).toString().replace(KEY_PEPPER_HOME, replacePath));
		}
	}

	/**
	 * Returns the plugIn path, where to find the OSGi bundles.
	 * 
	 * @return plugIn path
	 */
	public String getPlugInPath() {
		return (this.getProperty(PepperStarterConfiguration.PROP_PLUGIN_PATH));
	}

	/**
	 * Returns the dropin paths, where to find the OSGi bundles.
	 *
	 * @return plugIn path
	 */
	public List<String> getDropInPaths() {
		String rawList = this.getProperty(PepperStarterConfiguration.PROP_DROPIN_PATHS);
		if (rawList != null) {
			Iterator<String> it
				= Splitter.on(',').trimResults().omitEmptyStrings().split(rawList).iterator();
			List<String> result = new ArrayList<>();
			while (it.hasNext()) {
				result.add(it.next());
			}
			return result;
		}
		return null;
	}

	/**
	 * Returns the eMail address of SaltNPepper.
	 * 
	 * @return plugIn path
	 */
	public String getPepperEMail() {
		return (EMAIL);
	}

	/**
	 * Returns the homepage address of SaltNPepper.
	 * 
	 * @return plugIn path
	 */
	public String getPepperHomepage() {
		return (HOMEPAGE);
	}

	/**
	 * Returns the path of the OSGi profile file
	 * 
	 * @return plugIn path
	 */
	public File getOSGiProfileFile() {
		String osgiProfile = this.getProperty(PROP_OSGI_PROFILE);
		if ((osgiProfile == null) || (osgiProfile.isEmpty()))
			return (null);
		else {
			File osgiProfileFile = new File(osgiProfile);
			return (osgiProfileFile);
		}
	}

	/**
	 * Returns the content of property {@link #PROP_OSGI_SHAREDPACKAGES}.
	 * 
	 * @return plugIn path
	 */
	public String getSharedPackages() {
		return (this.getProperty(PROP_OSGI_SHAREDPACKAGES));
	}
	
	/** The width of the output console of Pepper. */
	public final static int CONSOLE_WIDTH_120 = 120;
	
	/** The width of the output console of Pepper, when os is windows. */
	public final static int CONSOLE_WIDTH_80 = 80;
	
	/** 
	 * Returns the width of the output console. The width could be either 120 or 80. 
	**/
	public int getConsoleWidth(){
		Integer width= null;
		String widthProp= getProperty(PROP_CONSOLE_WIDTH);
		if (	(widthProp!= null)&&
				(!widthProp.isEmpty())){
			try{
				width= Integer.valueOf(widthProp.trim());
			}catch (NumberFormatException e){
			}
		}
		if (width== null){
			String os= System.getProperty("os.name");
			if (os.startsWith("Windows")){
				width= CONSOLE_WIDTH_80;
			}else{
				width= CONSOLE_WIDTH_120;
			}
		}
		if (width< CONSOLE_WIDTH_120){
			width= CONSOLE_WIDTH_80;
		}else{
			width= CONSOLE_WIDTH_120;
		}
		return(width);
	}
}
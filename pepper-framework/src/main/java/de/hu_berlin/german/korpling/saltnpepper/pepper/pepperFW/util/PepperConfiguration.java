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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Property;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConfigurationException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperProperties;

/**
 * This class contains all possible configurations, to take influence on the Pepper process.
 * A configuration is realized as a {@link Property}, therefore this class is derived from
 * the general {@link Properties} class. It is enhanced for methods to have an easier access 
 * to fields to configure the Pepper framework, but you can also find anything necessary when 
 * treating this object as a normal {@link Properties} object.
 * <h1>Loading and resolving configuration</h1>
 * Next to the the mechanisms of loading a prop file or an xml file, this object can also 
 * resolve the configuration file by checking the location of the Pepper bundle. It is assumed, 
 * that the configuration file is contained in one of two possible locations. 
 * <ol>
 * <li>developement phase: it is assumed, that the configuration file is stored in PEPPER_SOURCE_HOME/src/main/resoruces</li> 
 * <li>testing phase: it is assumed, that the configuration file is stored in BUNDLE_LOCATION/../BUNDLE_NAME</li>
 * </ol>
 * @author Florian Zipser
 *
 */
public class PepperConfiguration extends Properties implements PepperProperties{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7702415220939613420L;
	/** Folder where the resources of a bundle is expected to be. This follows the maven structure of a project **/
	public static final String  SOURCES_RESOURCES="src/main/resources/";
	/** Name of the folder containing configuration files. **/
	public static final String FILE_CONF_FOLDER="conf";
	/** Name of the folder configuration file. **/
	public static final String FILE_CONF_FILE="userDefined.properties";
	
	/**
	 * standard constructor
	 */
	public PepperConfiguration()
	{
		
	}
	
	/**
	 * Initializes a new object and copies all properties of the given {@link Properties}
	 * object.
	 * @param properties
	 */
	public PepperConfiguration(Properties properties)
	{
		Enumeration<Object> propNames= properties.keys();
		while(propNames.hasMoreElements())
		{
			String propName= propNames.nextElement().toString();
			put(propName, properties.getProperty(propName));
		}
			
	}
	
	/**
	 * Loads the configuration from the passed file location
	 * @param configuration
	 */
	public void load(File configurationFile)
	{
		confFolder= configurationFile.getParentFile();
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(configurationFile.getAbsolutePath()));
			load(in);
		} catch (FileNotFoundException e2) {
			throw new PepperConfigurationException("Cannot load configuration file for Pepper at location '"+configurationFile.getAbsolutePath()+"', because of nested exception: ", e2);
		} catch (IOException e) {
			throw new PepperConfigurationException("Cannot load configuration file for Pepper at location '"+configurationFile.getAbsolutePath()+"', because of nested exception: ", e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				throw new PepperConfigurationException("Cannot close stream to configuration file for Pepper at location '"+configurationFile.getAbsolutePath()+"', because of nested exception: ", e);
			}
		}
	}
	
	/**
	 * Resolves the location of the bundle by the given {@link ComponentContext} and loads the configuration file.
	 * Expects the configuration file to be in one of the two locations:
	 * <ol>
	 * <li>developement phase: it is assumed, that the configuration file is stored in PEPPER_SOURCE_HOME/src/main/resoruces</li> 
	 * <li>testing phase: it is assumed, that the configuration file is stored in BUNDLE_LOCATION/../BUNDLE_NAME</li>
	 * </ol>
	 * @param componentContext
	 */
	public void load(ComponentContext componentContext)
	{
		if (componentContext== null)
			throw new PepperConfigurationException("Cannot resolve configuration file for Pepper, because the given component context is null.");
		
		String configFileStr= null;
		
		if (	(componentContext!= null)&&
				(componentContext.getBundleContext()!= null)&&
				(componentContext.getBundleContext().getBundle()!= null)&&
				(componentContext.getBundleContext().getBundle().getLocation()!= null))
		{
			if (componentContext!= null)
			{
				String[] bundleNames= System.getProperty("osgi.bundles").split(",");
				if (bundleNames.length>0)
				{
					String currLocation=componentContext.getBundleContext().getBundle().getLocation();
					currLocation= currLocation.replace("initial@reference:file:", "");
					currLocation= currLocation.replace("../", "");
					if (currLocation.endsWith("/"))
						currLocation= currLocation.substring(0, currLocation.length()-1);
					
					String location= null;
					for (String bundleName: bundleNames)
					{
						bundleName= bundleName.replace("reference:", "");
						bundleName= bundleName.replaceAll("@([0-9]+:)?start", "");
						
						if (bundleName.endsWith(currLocation))
						{
							location= bundleName;
							break;
						}
					}
					if (location.endsWith(".jar"))
						location= location.replace(".jar", "/");
					else
					{
						if (!location.endsWith("/"))
							location= location+"/";
						location= location+ SOURCES_RESOURCES;
						
					}
					configFileStr= location;
					if (configFileStr.startsWith("file:"))
						configFileStr= configFileStr.replace("file:", "");
					configFileStr= configFileStr+ FILE_CONF_FOLDER+"/"+FILE_CONF_FILE;
				}
			}	
		}
		load(new File(configFileStr));
	}
	
	private File confFolder= null; 
	/**
	 * Returns the folder containing the configuration file(s). If no configuration file is used
	 * this method returns null.
	 * @return
	 */
	public File getConfFolder()
	{
		return(confFolder);
	}
	
	/**
	 * Returns a temprorary path, where the entire system and all modules can store temp files. 
	 * If no temp folder is given by configuration file, the default temprorary folder given by
	 * the operating system is used.
	 * @return path, where to store temprorary files
	 */
	public File getTempPath()
	{
		String tmpFolderStr= getProperty(PROP_TEMP_FOLDER);
		if (tmpFolderStr == null)
		{
			tmpFolderStr= System.getProperty("java.io.tmpdir");
		}
		tmpFolderStr= tmpFolderStr+"/pepper/";
		File tmpFolder= new File(tmpFolderStr);
		if (!tmpFolder.exists())
			tmpFolder.mkdirs();
		return(tmpFolder);
	}
}

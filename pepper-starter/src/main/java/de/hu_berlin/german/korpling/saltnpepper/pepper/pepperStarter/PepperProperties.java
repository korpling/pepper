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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperStarter;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.Properties;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperStarter.exceptions.PepperPropertyException;


public class PepperProperties 
{
	public static final String ENV_PEPPER_HOME= "PEPPER_HOME";
	public static final String KW_ENV_PEPPER_HOME="$"+ ENV_PEPPER_HOME;
	public static final String FILE_PEPPER_PROP=KW_ENV_PEPPER_HOME+"/conf/pepperStarter.properties";
	
	public static final String PROP_UD_PROPERTIES="UDproperties";
	
	/**
	 * property of file path to log4j file
	 */
	public static final String PROP_LOG4J= "log4j";
	/**
	 * property of the pepper-eMail address
	 */
	public static final String PROP_EMAIL= "email";
	/**
	 * property of the pepper-homepage
	 */
	public static final String PROP_HOMEPAGE= "homepage";
	
	/**
	 * name of the property of where to find the plugin path 
	 */
	public static final String PROP_PLUGIN_PATH= "plugin.path";
	
	/**
	 * name of the property of where to store the temprorary files and folders 
	 */
	public static final String PROP_TMP_PATH= "temproraries";
	
	/**
	 * name of the property of where to store the temprorary files and folders 
	 */
	public static final String PROP_RESOURCE_PATH= "plugins.modules.resources";
	
	/**
	 * name of the property containing the pattern to identify the pepper-framework 
	 */
	public static final String PROP_PATTERN_PEPPERFW="plugin.pepperFrameworkPattern";
	
	
	
	public static File getPepperHome()
	{
		String workingDir = null; 
		if (System.getenv().get(ENV_PEPPER_HOME)== null)
		{
			if (workingDir== null)
				workingDir= System.getProperty("user.dir")+ "/";
		}
		else
			workingDir= System.getenv().get(ENV_PEPPER_HOME);
		
		
		if (	(!workingDir.endsWith("/"))&&
				(!workingDir.endsWith("\\")))
			workingDir= workingDir + "/";
		workingDir= workingDir.replace("\\", "/");
		
		File pepperStarterHome= new File(workingDir);
		return(pepperStarterHome);
	}
	
	
	
	/**
	 * Loads all PepperStarter.properties and the user-defined properties.
	 * @return
	 */
	public static Properties loadProperties(File udPropFile)
	{
		File pepperStarterHome= getPepperHome();
		
		if (!pepperStarterHome.exists())
			throw new PepperPropertyException("Cannot load any properties, because the home path is not locatable. Please set environment variable '"+ENV_PEPPER_HOME+"'");
		
		File propFile= new File (FILE_PEPPER_PROP.replace(KW_ENV_PEPPER_HOME, pepperStarterHome.getAbsolutePath()));
		if (!propFile.exists())
			throw new PepperPropertyException("Cannot load any properties, because the property file '"+propFile+"' does not exist.");
		
		Properties pepperProps= new Properties();
		try
		{
			pepperProps.load(new FileInputStream(propFile));
		}catch (Exception e) {
			throw new PepperPropertyException("Cannot load any properties, because the property file '"+propFile+"' because of nested exception.", e);
		}
		{//load user-defined properties
			String udPropsName= pepperProps.getProperty(PROP_UD_PROPERTIES);
			if (	(udPropsName== null)||
					(udPropsName.isEmpty()))
				throw new PepperPropertyException("Cannot load user-defined properties, because the property '"+PROP_UD_PROPERTIES+"' in in pepperStarter-properties ('"+propFile.getAbsolutePath()+"'), is not given or empty.");
			
			if (udPropFile== null)
				udPropFile= new File (udPropsName.replace(KW_ENV_PEPPER_HOME, pepperStarterHome.getAbsolutePath()));
			
			Properties udProps= loadUDProperties(udPropFile);
			{//copy all user-defined properties to pepperStarter-properties
				for (Object key: udProps.keySet())
				{
					pepperProps.put(key, udProps.get(key));
				}
			}//copy all user-defined properties to pepperStarter-properties
		}//load user-defined properties
		{//replace $PEPPER_HOME with path
			for (Object key: Collections.synchronizedSet(pepperProps.keySet()))
			{
				pepperProps.put(key, pepperProps.get(key).toString().replace(KW_ENV_PEPPER_HOME, pepperStarterHome.getAbsolutePath()));
			}
		}//replace $PEPPER_HOME with path
		
		return(pepperProps);
	}
	
	/**
	 * Loads user-defined properties from given property fileand returns read properties.
	 * @param udPropFile
	 * @return
	 */
	public static Properties loadUDProperties(File udPropFile)
	{//load user-defined properties
		if (!udPropFile.exists())
			throw new PepperPropertyException("Cannot load user-defined properties, because the property file '"+udPropFile.getAbsolutePath()+"' does not exist.");
		Properties udProps= new Properties();
		try
		{
			udProps.load(new FileInputStream(udPropFile));
		}catch (Exception e) {
			throw new PepperPropertyException("Cannot load user-defined properties, because the property file '"+udPropFile.getAbsolutePath()+"' because of nested exception.", e);
		}
		return(udProps);
	}//load user-defined properties
	
}

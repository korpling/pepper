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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperStarter.exceptions.PepperPropertyException;

public class PepperStarter 
{
	
	private final static String KW_EXTRA_PACKAGES= "de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW";
//	private final static String KW_EXTRA_PACKAGES= "de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW, org.eclipse.emf.ecore, org.eclipse.emf.common";
	/**
	 * The qualified name of the PepperConverter. 
	 */
	public static final String KW_QNAME_PEPPER_CONVERTER= KW_EXTRA_PACKAGES+".PepperConverter";
	
	//TODO remove this
	private static final Boolean PROFILE_TEST= false;
	
	/**
	 * Keyword to which the LogService listens, to get the log4j file
	 */
	private final static String KW_LOGGER_PROPERTY="de.hu_berlin.german.korpling.saltnpepper.logger";
	
	private Logger logger= Logger.getLogger(PepperStarter.class);

	public BundleContext runEquinox() throws Exception
	{
		 {//for debug messages
//			 System.setProperty("equinox.ds.debug", "true");
//			 System.setProperty("equinox.ds.print", "true");
		 }
		 
		 Properties frameworkProperties = new Properties();
		 
		 if (PROFILE_TEST)
		 {//TODO remove this
			 System.out.println("org.osgi.framework.system.packages: "+System.getProperty("org.osgi.framework.system.packages"));
			 
//			 String extraPackages= System.getProperty("org.osgi.framework.system.packages.extra", "");
			 String PROP_EXTRA_PACKAGES= "org.osgi.framework.system.packages.extra";
//			 String PROP_EXTRA_PACKAGES= "org.osgi.framework.system.packages";
			 String extraPackages= System.getProperty(PROP_EXTRA_PACKAGES, "");
			 if (extraPackages.equals(""))
				 extraPackages= extraPackages +KW_EXTRA_PACKAGES;
			 else
				 extraPackages= extraPackages +", "+KW_EXTRA_PACKAGES;
//			 System.setProperty(PROP_EXTRA_PACKAGES, extraPackages);
			 frameworkProperties.setProperty(PROP_EXTRA_PACKAGES, extraPackages);
			 
			 System.out.println("org.osgi.framework.system.packages.extra: "+System.getProperty("org.osgi.framework.system.packages.extra"));
			 System.out.println("org.osgi.framework.system.packages: "+System.getProperty("org.osgi.framework.system.packages"));
		 }//TODO remove this
		 
		 
		 BundleContext bc = null;
		
		 
		 frameworkProperties.setProperty(EclipseStarter.PROP_CLEAN, "true");
		 frameworkProperties.setProperty(EclipseStarter.PROP_CONSOLE, "true");
		 frameworkProperties.setProperty(EclipseStarter.PROP_NOSHUTDOWN, "false");
		 frameworkProperties.setProperty(EclipseStarter.PROP_INSTALL_AREA, "./_TMP/");

		 EclipseStarter.setInitialProperties(frameworkProperties);
		 
		 //it seems, that program will not terminate beacuse of flag 
//		 bc = EclipseStarter.startup(new String[]{"-console"}, null);
		 bc = EclipseStarter.startup(new String[] { "-console", "-dev", "bin" }, null);
//		 bc = EclipseStarter.startup(new String[]{""}, null);
		 
		 if (PROFILE_TEST)
		 {//TODO remove this
			 System.out.println("org.osgi.framework.system.packages.extra: "+System.getProperty("org.osgi.framework.system.packages.extra"));
			 System.out.println("org.osgi.framework.system.packages: "+System.getProperty("org.osgi.framework.system.packages"));
		 }//TODO remove this
		 return bc;
	}
	
//	private static Properties loadProperties(URI uri) throws IOException
//	{
//		if (uri== null)
//			throw new PepperException("Cannot read properties, because no ressource is given.");
//		Properties properties = new Properties();
//		FileInputStream stream = null;
//		try {
//			File propFile= new File(uri.getPath());
//			if (staticLogger!= null)
//				staticLogger.info("using property file: "+ propFile.getAbsolutePath());
//			stream = new FileInputStream(propFile);
//			properties.load(stream);
//		} catch (IOException e) {
//			throw e;
//		}
//		finally
//		{
//			if (stream!= null)
//				stream.close();
//		}
//		
//		return(properties);
//	} 

//========================== start: Property handling	
	private Properties properties= null;
	
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
//========================== end: Property handling
	
//	/**
//	 * stores the uri of the path to common-dependencies-plugins
//	 */
//	private URI commonDependenciesUri= null;
//	/**
//	 * stores the uri of the path to core-plugins
//	 */
//	private URI coreUri= null;
//	/**
//	 * stores the uri of the path to pepper-plugins
//	 */
//	private URI pepperUri= null;
//	/**
//	 * stores the uri of the path to module-plugins
//	 */
//	private URI modulesUri= null;
	
	/**
	 * The encoding for the use of the PEPPER_HOME environment variable inside the property file
	 * to locate the plugin folder.
	 */
//	private static final String KW_ENV_PEPPER_HOME= "$PEPPER_HOME"; 
	
	private void loadProperties(Properties props) throws URISyntaxException
	{
		if (props== null)
			throw new PepperPropertyException("Cannot read properties, because no properties are given.");
		
		if (this.logger!= null)
		{
			this.logger.debug("-------------------------- Pepper Properties --------------------------");
			for (Object key: Collections.synchronizedSet(props.keySet()))
			 this.logger.debug(key+":\t\t"+ props.get(key));
			this.logger.debug("-----------------------------------------------------------------------");
		}
		
		{//setting system properties for temprorary and resource folder for modules
//			String tmpUriStr= props.getProperty("temproraries");
			String tmpUriStr= props.getProperty(PepperProperties.PROP_TMP_PATH);
			
			//checking if property for core module is set
			if (	(tmpUriStr== null) ||
					(tmpUriStr.equals("")))
				throw new PepperException("Cannot set properties, because no path for temproraries folder is given.");
			//replace KW_ENV_PEPPER_HOME if occurs
//			tmpUriStr= tmpUriStr.replace(KW_ENV_PEPPER_HOME, this.getPepperHomeDir().getAbsolutePath()).replace("\\", "/");
			File tmpFile= new File(tmpUriStr);
			try {
				System.setProperty("PepperModuleResolver.TemprorariesURI", tmpFile.getCanonicalPath());
			} catch (Exception e) 
			{
				throw new PepperException("Cannot identify temprorary or resource folder. Nested Exception: "+ e);
			}
			String resUriStr= props.getProperty(PepperProperties.PROP_RESOURCE_PATH);
//			String resUriStr= props.getProperty("plugins.modules.resources");
			//checking if property for core module is set
			if (	(resUriStr== null) ||
					(resUriStr.equals("")))
				throw new PepperException("Cannot set properties, because no path for resources folder for modules is given.");
			//replace KW_ENV_PEPPER_HOME if occurs
//			resUriStr= resUriStr.replace(KW_ENV_PEPPER_HOME, this.getPepperHomeDir().getAbsolutePath()).replace("\\", "/");
			File resFile= new File(resUriStr);
			try {
				System.setProperty("PepperModuleResolver.ResourcesURI", resFile.getCanonicalPath());
			} catch (Exception e) 
			{
				throw new PepperException("Cannot identify temprorary or resource folder. Nested Exception: "+ e);
			}
		}//setting system properties for temprorary and resource folder for modules
	}
	
	/**
	 * The home directory of pepper.
	 */
	private File pepperHomeDir= null;
	
	/**
	 * Returns the set home directory of pepper.
	 * @return set home directory of pepper.
	 */
	public File getPepperHomeDir() {
		return pepperHomeDir;
	}

	/**
	 * Sets the home directory of pepper.
	 * @param pepperHomeDir home directory of pepper.
	 */
	public void setPepperHomeDir(File pepperHomeDir) {
		this.pepperHomeDir = pepperHomeDir;
	}

	/**
	 * The bundle context produces by starting equinox framework
	 */
	private BundleContext bundleContext = null;
	
	private void installingAllBundles() throws BundleException, URISyntaxException, IOException
	{
		this.logger.info("installing OSGI-bundles...");
		this.logger.debug("-------------------- installing bundles --------------------");
		Collection<Bundle> bundles= null;
		{//installing module-bundles
			this.logger.debug("\tinstalling module bundles:");
			bundles= this.installBundles(new File(properties.getProperty(PepperProperties.PROP_PLUGIN_PATH)).toURI());
		}//installing module-bundles
		this.logger.debug("----------------------------------------------------------");
		this.logger.info("installing OSGI-bundles...FINISHED");
		this.logger.info("starting OSGI-bundles...FINISHED");
		this.logger.debug("-------------------- starting bundles --------------------");
		this.startBundles(bundles);
		this.logger.debug("----------------------------------------------------------");
		this.logger.info("starting OSGI-bundles...FINISHED");
	}
	
	/**
	 * Installs all jar-files, of the given pluginPath.
	 * @param pluginPath path ere the bundles are
	 * @param bundleAction a flag, which shows if bundle has to be started or just installed
	 * @throws BundleException
	 * @throws URISyntaxException
	 * @throws IOException 
	 */
	private Collection<Bundle> installBundles(URI pluginPath) throws BundleException, URISyntaxException, IOException
	{
		Collection<Bundle> bundles= new Vector<Bundle>();
		for (File bundleJar: new File(pluginPath.getPath()).listFiles())
		{
			//check if file is file-object
			if (bundleJar.isFile())
			{	
				//check if file is file jar
				if (bundleJar.getName().endsWith(".jar"))
				{
					URI bundleURI= bundleJar.toURI();
					Bundle persistenceBundle = this.bundleContext.installBundle(bundleURI.toString());
					bundles.add(persistenceBundle);
					this.logger.debug("\t\tinstalling bundle: "+persistenceBundle.getSymbolicName()+ persistenceBundle.getVersion());
					{//set system property for ressource path for plugins
//						String resourceUri= pluginPath+"/"+persistenceBundle.getSymbolicName();
						String resourceUri= pluginPath+"/"+bundleJar.getName().replace(".jar", "");
						File resourceFile= new File(resourceUri);
						System.setProperty(persistenceBundle.getSymbolicName()+".resources", resourceFile.getAbsolutePath());
					}//set system property for ressource path for plugins
				}	
			}	
		}
		return(bundles);
	}
	
	/**
	 * Starts all bundle being contained in the given list of bundles.
	 * @param bundles a list of bundles to start
	 * @throws BundleException 
	 */
	private void startBundles(Collection<Bundle> bundles) throws BundleException
	{
		if (bundles!= null)
		{
			Bundle pepperFWBundle= null;
			for (Bundle bundle: bundles)
			{
				if (bundle.getSymbolicName().contains(this.getProperties().getProperty(PepperProperties.PROP_PATTERN_PEPPERFW)))
				{
					pepperFWBundle= bundle;
				}
				else
				{
					this.logger.debug("\t\tstarting bundle: "+bundle.getSymbolicName()+ bundle.getVersion());
					bundle.start();
					if (bundle.getState()!= Bundle.ACTIVE)
						this.logger.error("The bundle '"+bundle.getSymbolicName()+"-" +bundle.getVersion()+"' wasn't started correctly.");
				}
			}
			if (pepperFWBundle!= null)
			{
				this.logger.debug("\t\tstarting bundle: "+pepperFWBundle.getSymbolicName()+ pepperFWBundle.getVersion());
				pepperFWBundle.start();
				if (pepperFWBundle.getState()!= Bundle.ACTIVE)
					this.logger.error("The bundle '"+pepperFWBundle.getSymbolicName()+ "-" +pepperFWBundle.getVersion()+"' wasn't started correctly.");
			}
		}
	}
	
	
	public void startPepperConverter(URI pepperParams) throws Exception
	{
		
		if (this.bundleContext== null)
			throw new RuntimeException("No context is given.");

		if (pepperParams== null)
			throw new RuntimeException("Cannot start, because no parameters are given.");
		
//		 if (PROFILE_TEST)
//		 {//TODO remove this
//			ServiceReference serviceReference = bundleContext.getServiceReference(IPepperConverter.class.getName());
//			if (serviceReference== null) 
//				throw new PepperException("----------> Cannot find an PepperConverter-object with name '"+KW_QNAME_PEPPER_CONVERTER+"'.");
//			else
//			{
//				IPepperConverter pepperConverter= (IPepperConverter) bundleContext.getService(serviceReference);
//				System.out.println("----------> scheint zu gehen");
//				pepperConverter.start();
//				System.out.println("----------> ach nee doch nicht");
//
//			}
//		 }//TODO remove this
		
		ServiceReference[] serviceRefs= bundleContext.getServiceReferences(KW_QNAME_PEPPER_CONVERTER, null);
		if ((serviceRefs== null) || (serviceRefs.length== 0))
			throw new PepperException("Cannot find an PepperConverter-object with name '"+KW_QNAME_PEPPER_CONVERTER+"'.");
		else if (serviceRefs.length > 1)
			throw new PepperException("Cannot deal with more than one service references of type PepperConverter.");
		else
		{
			ServiceReference serviceRef= serviceRefs[0];
			try {
				Method m = null;
				if (bundleContext.getService(serviceRef)== null)
					throw new RuntimeException("No Service with name 'PepperConverter' found in bundle context.");
				{//setting properties
					m = bundleContext.getService(serviceRef).getClass().getMethod("setProperties", Properties.class);
					m.invoke(bundleContext.getService(serviceRef), this.getProperties());
				}//setting properties
				{//setting workflow description
					m = bundleContext.getService(serviceRef).getClass().getMethod("setPepperParams", URI.class);
					m.invoke(bundleContext.getService(serviceRef), pepperParams);
				}//setting workflow description
				{//invoke PepperConverter.start(URI)
					m = bundleContext.getService(serviceRef).getClass().getMethod("start");
					m.invoke(bundleContext.getService(serviceRef));
				}//invoke PepperConverter.start(URI)
			} catch (InvocationTargetException e) {
				try 
				{
					throw e.getTargetException();
				} 
				catch (Exception e1) {
					throw e1;
				}
				catch (Throwable e2) {
					e2.printStackTrace();
				}
			}
		}	
	}
	
	/**
	 * Starts the conversion with setting a property file for PepperStarter and a 
	 * parameter file for PepperConverter. 
	 * 
	 * @param pepperParams - parameter file for PepperConverter
	 * @throws IOException
	 * @throws Exception
	 */
	public void start(URI pepperParams) throws IOException, Exception
	{
		if (this.getProperties()== null)
			throw new RuntimeException("Cannot start Pepper conversion, because no user-defined properties are given.");
	
		if (pepperParams== null)
			throw new RuntimeException("Cannot start Pepper conversion, because no workflow-description is given.");
		
		this.loadProperties(this.getProperties());
		this.bundleContext= this.runEquinox(); 
		this.installingAllBundles();
		this.startPepperConverter(pepperParams);
	}
	
	/**
	 * Returns synopsis for program call
	 * @return
	 */
	public static String getSynopsis()
	{
		StringBuilder retStr= new StringBuilder();
//		retStr= retStr + "Synopsis:\tPepperStarter (-p PEPPER_PARAM | -s IMPORT_SETS -t EXPORT_SETS) \n";
//		retStr= retStr + "\n";
//		retStr= retStr + "\t\tIMPORT_SETS\t\t(IMPORT_SET;)*\n";
//		retStr= retStr + "\t\tEXPORT_SETS\t\t(EXPORT_SET;)*\n";
		
		retStr.append("Synopsis:\tPepperStarter -p PEPPER_PARAM \n");
		retStr.append("\n");
//		retStr.append("\t\tPEPPER_PARAM\t\tthe file of pepper workflow definition in syntax of pepper param file.\n");
		
		return(retStr.toString());
	}
	
	private static String getHello(String eMail, String hp)
	{
		StringBuilder retVal= new StringBuilder();
		
		retVal.append("************************************************************************\n");
		retVal.append("***                         Pepper Converter                         ***\n");
		retVal.append("************************************************************************\n");
		retVal.append("* Pepper converter is a salt model based converter for a variety of    *\n");
		retVal.append("* linguistic formats.                                                  *\n");
		retVal.append("************************************************************************\n");
		retVal.append("further information:\t"+hp+"\n");
		retVal.append("contact:\t\t"+eMail+"\n");
		retVal.append("\n");
		
		return(retVal.toString());
	}
	
	private static String getGoodBye()
	{
		StringBuilder retVal= new StringBuilder();
		retVal.append("************************************************************************\n");
		return(retVal.toString());
	}
	
	private static String getHelp()
	{
		StringBuilder retVal= new StringBuilder();
		retVal.append("short description of how to use Pepper:\n");
		retVal.append(getSynopsis());
		retVal.append("-h\t\t\t prints this help message, does not convert\n");
		retVal.append("-p[WORKFLOW]\t\t the file containing the workflow description (.pepperparams)\n");
		retVal.append("-udProp[PROP_FILE]\t absolute filename, whose file contains user-defined property definition (.properties)\n");
		return(retVal.toString());
	}
	
//	private static final String ENV_PEPPER_HOME="PEPPER_HOME";
	
	protected static Logger staticLogger= Logger.getLogger(PepperStarter.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{	
		Properties pepperParamsProp= PepperProperties.loadProperties(null);
		
		String eMail= pepperParamsProp.getProperty("email");
		String hp= pepperParamsProp.getProperty("homepage");
		final String log4jProperties=pepperParamsProp.getProperty(PepperProperties.PROP_LOG4J);
		
		PropertyConfigurator.configureAndWatch(log4jProperties, 60*1000 );
		//set path of log property file to global properties, so that LogService can reach it
		System.setProperty(KW_LOGGER_PROPERTY, log4jProperties);
		staticLogger.info(getHello(eMail, hp));
		
		//setting timer to stop needed time
		Long timestamp= System.currentTimeMillis();
		
		//path of property file
		String udPropFile= null;
		
		//path of pepper parameters
		String pepperWorkflowDescriptionFile= null;
		
		//if help shall be printed
		boolean isHelp= false;
		
		//check parameters
		for (int i=0; i < args.length; i++)
		{
			// user-defined properties
			if (args[i].equalsIgnoreCase("-udProp"))
			{
				if (args.length> i+1)
					udPropFile= args[i+1];
				i++;
			}	
			
			//workflow file
			else if (args[i].equalsIgnoreCase("-p"))
			{
				if (args.length> i+1)
					pepperWorkflowDescriptionFile= args[i+1];
				i++;
			}	
			//workflow file
			else if (args[i].equalsIgnoreCase("-w"))
			{
				if (args.length> i+1)
					pepperWorkflowDescriptionFile= args[i+1];
				i++;
			}	
			// print help
			else if (args[i].equalsIgnoreCase("-h"))
			{
				isHelp= true;
			}	
		}
		boolean endedWithErrors= false;
		PepperStarter pepperStarter = new PepperStarter();
		//marks if parameter for program call are ok
		boolean paramsOk= false;
//		URI udPropUri= null;
		URI paramUri= null;
		try
		{
			if (isHelp)
				staticLogger.info(getHelp());
			else
			{//no flag for help	
				try 
				{
					if (	(pepperWorkflowDescriptionFile== null) ||
							(pepperWorkflowDescriptionFile.equals("")))
						throw new PepperException("No parameters for pepper converter are given.");	
					//program call
					else
					{
						pepperWorkflowDescriptionFile= pepperWorkflowDescriptionFile.replace("\\", "/");
						paramUri= new URI(pepperWorkflowDescriptionFile);
						paramsOk= true;
					}
					
					{//if user-defined properties file is given are given
						if (	(udPropFile!= null) &&
								(udPropFile.equals("")))
							pepperParamsProp= PepperProperties.loadProperties(new File(udPropFile));
						
					}//if user-defined properties file is given are given
				} catch (Exception e) 
				{
	//				e.printStackTrace();
					StringBuilder errorStr= new StringBuilder();
					errorStr.append("Error in program call:\n");
					errorStr.append("\t"+e+"\n");
					errorStr.append("\n");
					errorStr.append(getHelp());
					staticLogger.error(errorStr.toString());
				}
				
				//starts converting
				if (paramsOk)
				{
					//set home firectory of pepper
					pepperStarter.setProperties(pepperParamsProp);
					pepperStarter.start(paramUri);
				}
			}//no flag for help
		}
		catch (Exception e) 
		{
			staticLogger.error(e);
			//TODO remove at delivery time
			e.printStackTrace();
			
		}
		finally
		{
			
			EclipseStarter.shutdown();
		}
		timestamp= System.currentTimeMillis() - timestamp;
		
		if (endedWithErrors)
			staticLogger.info("CONVERSION ENDED WITH ERRORS, REQUIRED TIME: "+ timestamp +" milliseconds");
		else
			staticLogger.info("CONVERSION ENDED SUCCESSFULLY, REQUIRED TIME: "+timestamp +" milliseconds");
		staticLogger.info(getGoodBye());
		
		if (PROFILE_TEST)
		{//TODO remove this
			
		}//TODO remove this
		else
			System.exit(0);
	}

}

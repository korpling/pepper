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
package de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Properties;
import java.util.Vector;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.PepperStarterConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.exceptions.PepperPropertyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.PepperConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperOSGiRunner;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.JobNotFoundException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;
/**
 * This class is an implementation of {@link Pepper}. It acts as a bridge between the pure java environment and the
 * Pepper universe inside the OSGi environment. This class should help not dealing with OSGi issues when using Pepper and
 * therefore enables it to use Pepper as an embedded library.
 * 
 * @author Florian Zipser
 *
 */
public class PepperOSGiConnector implements Pepper, PepperConnector{

	private static final Logger logger= LoggerFactory.getLogger(PepperOSGiConnector.class);
	
	/**
	 * Starts Equinox by calling {@link #startEquinox()}, installs all pepper bundles and starts them.
	 * <br/>
	 * Sets property {@link PepperOSGiRunner#PROP_TEST_DISABLED} to true.
	 */
	@Override
	public void init(){
		if (getProperties().getPlugInPath()== null){
			throw new PepperPropertyException("Cannot start pepper, because no plugin path is given for Pepper modules.");
		}
		try {
			//disable PepperOSGiRunner
			System.setProperty(PepperOSGiRunner.PROP_TEST_DISABLED, Boolean.TRUE.toString());
			
			if (bundleContext== null)
				bundleContext= this.startEquinox();
			
			logger.debug("plugin path:\t\t"+getProperties().getPlugInPath());			
			
			logger.info("installing OSGI-bundles...");
			logger.debug("-------------------- installing bundles --------------------");
			Collection<Bundle> bundles= null;
			{//installing module-bundles
				logger.debug("\tinstalling OSGI-bundles:");
				bundles= this.installBundles(new File(getProperties().getPlugInPath()).toURI());
			}//installing module-bundles
			logger.debug("----------------------------------------------------------");
			logger.info("installing OSGI-bundles...FINISHED");
			logger.info("starting OSGI-bundles...");
			logger.debug("-------------------- starting bundles --------------------");
			this.startBundles(bundles);
			logger.debug("----------------------------------------------------------");
			logger.info("starting OSGI-bundles...FINISHED");
		} catch (Exception e) {
			throw new PepperException("An exception occured setting up the OSGi environment. ", e);
		}
	}
	/** {@link PepperStarterConfiguration} of this object. **/
	private PepperStarterConfiguration properties= null;
	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperStarter.PepperConnector#setProperties(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperStarter.PepperProperties)
	 */
	@Override
	public void setProperties(PepperStarterConfiguration props) {
		if (properties== null){
			this.properties = props;
		}
	}

	/* (non-Javadoc)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperStarter.PepperConnector#getProperties()
	 */
	@Override
	public PepperStarterConfiguration getProperties() {
		return properties;
	}
	
// ========================================== start: initializing OSGi
	/** a singleton instance of {@link Pepper}**/
	private Pepper pepper= null;
	/**
	 * Returns an instance of {@link Pepper}, which is running inside OSGi. This class will be resolved via
	 * the {@link BundleContext}. If it was resolved once, a singleton instance of this object is returned.
	 * @return {@link Pepper} from inside the OSGi environment.
	 */
	protected Pepper getPepper()
	{
		if (pepper == null){
			ServiceReference serviceReference = bundleContext.getServiceReference(Pepper.class.getName());
			Pepper pepperOSGi=null;
			if (serviceReference != null) {
				try{
					pepperOSGi = (Pepper) bundleContext.getService(serviceReference);
				}catch(ClassCastException e){
					pepperOSGi = (Pepper) bundleContext.getService(serviceReference);
				}
			}else{
				throw new PepperException("The pepper-framework was not found in OSGi environment for '"+Pepper.class.getName()+"'.");
			}
			pepper= pepperOSGi;
		}
		return(pepper);
	}
	
	/**
	 * The context of all pepper bundles.
	 */
	private BundleContext bundleContext= null;	
	
	/**
	 * This method loads a profile via the current classloader and returns its Properties.
	 * This is necessary for passing the OSGi bridge. This method also contains a hack to load
	 * the folder containing the profile file into the classpath.  
	 * @param profileFile
	 * @return
	 */
	protected Properties readCustomProfile(final File profileFile) {
		if (profileFile== null)
			throw new PepperException("Cannot initialize OSGi framework, because given OSGi profile file is null.");
		if (!profileFile.exists())
			throw new PepperException("Cannot initialize OSGi framework, because given OSGi profile file '"+profileFile.getAbsolutePath()+"' does not exist.");
		
		URL u;
		try {
			u = profileFile.getParentFile().toURI().toURL();
			URLClassLoader urlClassLoader = (URLClassLoader) this.getClass().getClassLoader();
			Class<URLClassLoader> urlClass = URLClassLoader.class;
			Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
			method.setAccessible(true);
			method.invoke(urlClassLoader, new Object[]{u});
		} catch (Exception e) {
			throw new PepperException("Cannot initialize OSGi environment, because cannot load OSGi profile, because cannot add profile folder '"+profileFile.getAbsolutePath()+"' to classpath.", e);
		}
		
		final ClassLoader classLoader = this.getClass().getClassLoader();
		
		final InputStream inputStream = classLoader.getResourceAsStream(profileFile.getName());

		if (inputStream == null) {
			throw new PepperException("Cannot load osgi profile from "+ profileFile.getAbsolutePath());
		}

		final Properties profileProperties = new Properties();

		try {
			profileProperties.load(inputStream);
			inputStream.close();
			return profileProperties;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Returns a String, conatining a formatted list of packages to be shared between current classloader
	 * and OSGi classloaders. The list is formatted as it could be taken of the property {@link Constants#FRAMEWORK_SYSTEMPACKAGES_EXTRA}. 
	 */
	protected String getSharedPackages(){
		StringBuilder retVal= new StringBuilder();
		
		String sharedPackages= getProperties().getSharedPackages();
		if (	(sharedPackages!= null)&&
				(!sharedPackages.isEmpty())){
			retVal.append(sharedPackages);
		}else{
			
			//TODO is it possible, to retrieve this information automatically?
			String pepperVersion= "2.0.0";
			
			// pepper.common package 
			retVal.append(Pepper.class.getPackage().getName());
			retVal.append(";version=\""+pepperVersion+"\"");
			
			retVal.append(", ");
			
			//pepper.exceptions package
			retVal.append(PepperException.class.getPackage().getName());
			retVal.append(";version=\""+pepperVersion+"\"");
			
			retVal.append(", ");
			
			//emf-util
			retVal.append(org.eclipse.emf.common.util.URI.class.getPackage().getName());
		}
		return(retVal.toString());
	}
	
	/**
	 * Starts the OSGi Equinox environment.
	 * @return
	 * @throws Exception
	 */
	protected BundleContext startEquinox() throws Exception
	{
		BundleContext bc = null;
		
		Properties frameworkProperties= new Properties();
				 
		frameworkProperties.setProperty(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, getSharedPackages());
		
		frameworkProperties.setProperty(EclipseStarter.PROP_CLEAN, "true");
		frameworkProperties.setProperty(EclipseStarter.PROP_CONSOLE, "true");
		frameworkProperties.setProperty(EclipseStarter.PROP_NOSHUTDOWN, "true");
		frameworkProperties.setProperty(EclipseStarter.PROP_INSTALL_AREA, "./_TMP/");
		
		EclipseStarter.setInitialProperties(frameworkProperties);
		 
		//it seems, that program will not terminate because of flag 
		//bc = EclipseStarter.startup(new String[]{"-console"}, null);
		bc = EclipseStarter.startup(new String[] { "-console", "-dev", "bin", "-noExit", "osgi.noShutdown"}, null);
		 
		return bc;
	}
	/** name of system property to determine the locations of OSGi bundles**/
	public static final String PROP_OSGI_BUNDLES= "osgi.bundles";
	
	/**
	 * Tries to install all jar-files, of the given pluginPath.
	 * <br/>
	 * Each installed jar will be added to system property {@value #PROP_OSGI_BUNDLES} as reference:file:JAR_FILE.
	 * @param pluginPath path ere the bundles are
	 * @param bundleAction a flag, which shows if bundle has to be started or just installed
	 * @throws BundleException
	 * @throws URISyntaxException
	 * @throws IOException 
	 */
	protected Collection<Bundle> installBundles(URI pluginPath) throws BundleException, URISyntaxException, IOException
	{
		Collection<Bundle> bundles= new Vector<Bundle>();
		StringBuilder osgiBundlesProp= null;
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
					logger.debug("\t\tinstalling bundle: "+persistenceBundle.getSymbolicName()+"-"+ persistenceBundle.getVersion());
					
					//set system property for bundle pathes
					if (osgiBundlesProp== null){
						osgiBundlesProp= new StringBuilder();
					}
					osgiBundlesProp.append("reference:");
					osgiBundlesProp.append(bundleURI);
					osgiBundlesProp.append(",");
				}	
			}	
		}
		if (	(System.getProperty(PROP_OSGI_BUNDLES)== null)||
				(System.getProperty(PROP_OSGI_BUNDLES).isEmpty())){
			System.setProperty(PROP_OSGI_BUNDLES, osgiBundlesProp.toString());
		}
		return(bundles);
	}
	
	/**
	 * Starts all bundle being contained in the given list of bundles.
	 * @param bundles a list of bundles to start
	 * @throws BundleException 
	 */
	protected void startBundles(Collection<Bundle> bundles) throws BundleException
	{
		if (bundles!= null)
		{
			for (Bundle bundle: bundles)
			{
				logger.debug("\t\tstarting bundle: "+bundle.getSymbolicName()+ "-"+ bundle.getVersion());
				if (bundle.getState()!= Bundle.ACTIVE){
					try{
						bundle.start();
					}catch (BundleException e){
						logger.warn("The bundle '"+bundle.getSymbolicName()+"-" +bundle.getVersion()+"' wasn't started correctly. This could cause other problems. ");
						logger.debug("The reason was: ",e);
					}
				}
				if (bundle.getState()!= Bundle.ACTIVE){
					logger.error("The bundle '"+bundle.getSymbolicName()+"-" +bundle.getVersion()+"' wasn't started correctly.");
				}
			}
		}
	}

// ========================================== end: initializing OSGi	
	
	/**
	 * {@inheritDoc Pepper#createJob()}
	 */
	@Override
	public String createJob() {
		if (getPepper()== null)
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");
		
		return(getPepper().createJob());
	}
	/**
	 * {@inheritDoc Pepper#getJob(String)}
	 */
	@Override
	public PepperJob getJob(String id) throws JobNotFoundException {
		if (getPepper()== null)
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");
		
		return(getPepper().getJob(id));
	}
	/**
	 * {@inheritDoc Pepper#removeJob(String)}
	 */
	@Override
	public boolean removeJob(String id) throws JobNotFoundException {
		if (getPepper()== null)
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");
		
		return(getPepper().removeJob(id));
	}
	/**
	 * {@inheritDoc Pepper#getRegisteredModules()}
	 */
	@Override
	public Collection<PepperModuleDesc> getRegisteredModules() {
		if (getPepper()== null)
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");
		
		return(getPepper().getRegisteredModules());
	}

	@Override
	public String getRegisteredModulesAsString() {
		if (getPepper()== null)
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");
		
		return(getPepper().getRegisteredModulesAsString());
	}
}

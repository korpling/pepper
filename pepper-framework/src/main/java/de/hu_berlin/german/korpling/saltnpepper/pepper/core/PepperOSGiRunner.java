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
package de.hu_berlin.german.korpling.saltnpepper.pepper.core;

import java.io.File;
import java.util.Collection;
import java.util.Enumeration;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil.PepperJobReporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperOSGiRunnerException;
/**
 * Only starts pepper-osgi-runner, if {@value #PROP_TEST_DISABLED} is not set or is set to false.  
 * @author Florian Zipser
 *
 */
@Component(name="PepperTestComponent", immediate=true, enabled=true)
//TODO replace environment variables and parameters with a param file, which can be passed via OSGi to the test-environmet   
public class PepperOSGiRunner implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(PepperOSGiRunner.class);
	
	public final static String PROP_TEST_DISABLED= "de.hu_berlin.german.korpling.saltnpepper.pepper.disableTest";
	/**
	 * name of environment variable, which is supposed to contain the workflow description file
	 */
	public static final String ENV_PEPPER_WORKFLOW_FILE= "PEPPER_TEST_WORKFLOW_FILE";
	/**
	 * extension of where to find plugins and resources
	 */
	public static final String DIR_PLUGINS= "/plugins";
	/**
	 * extension of where to find plugins and resources
	 */
	public static final String DIR_CONF= "/conf";
	
	public PepperOSGiRunner()
	{}
	
// ========================================== start: PepperConverter		
	/** {@link Pepper} object to do the conversion task**/ 
	private PepperImpl pepper= null;
	/**
	 * Unsets the {@link Pepper} object to do the conversion task. Which means, it sets This method can be called
	 * automatically by the OSGi declarative services framework or manually.
	 * @param pepperConverter
	 */
	public void unsetPepper(Pepper pepperConverter)
	{
		if (!this.isDisabled)
		{	
			this.pepper= null;
		}
	}
	/**
	 * Sets the {@link Pepper} object to do the conversion task. This method can be called
	 * automatically by the OSGi declarative services framework or manually.
	 * @param pepperConverter
	 */
	@Reference(unbind="unsetPepper", cardinality=ReferenceCardinality.MANDATORY, policy=ReferencePolicy.STATIC)
	public void setPepper(Pepper pepperConverter)
	{
		if (!this.isDisabled)
		{	
			this.pepper= (PepperImpl)pepperConverter;
		}
	}
	/**
	 * Returns Pepper registered {@link Pepper} object. Registered either automatically 
	 * by OSGi or manually, both by calling {@link #setPepper(Pepper)}.
	 * @return {@link Pepper} object.
	 */
	public Pepper getPepper(){
		return(pepper);
	}
// ========================================== end: PepperConverter
	
	private static File getWorkflowDescriptionFile()
	{
		if (System.getenv(ENV_PEPPER_WORKFLOW_FILE)== null)
			throw new PepperOSGiRunnerException("Cannot start pepper-osgi-runner, please set environment variable '"+ENV_PEPPER_WORKFLOW_FILE+"' to workflow description file which is supposed to be used for conversion.");
		if (System.getenv(ENV_PEPPER_WORKFLOW_FILE).isEmpty())
			throw new PepperOSGiRunnerException("Cannot start pepper-osgi-runner, please set environment variable '"+ENV_PEPPER_WORKFLOW_FILE+"' to workflow description file which is supposed to be used for conversion. Currently it is empty.");
		File workflowDescFile= new File(System.getenv(ENV_PEPPER_WORKFLOW_FILE));
		if (!workflowDescFile.exists())
			throw new PepperOSGiRunnerException("Cannot start pepper-osgi-runner, because environment variable '"+ENV_PEPPER_WORKFLOW_FILE+"' points to a non  existing file '"+workflowDescFile.getAbsolutePath()+"'.");
		return(workflowDescFile);
	}
	
	/** params passed to this application**/
	public static final String ARG_COMMAND_LINE_PARAMS="sun.java.command";
	/** argument on command line call to determine, that pepper should make a self test**/
	public static final String ARG_SELFTEST="-selfTest";
	
	/**
	 * Returns if Pepper framework should make a self test.
	 * @return
	 */
	private boolean isSelfTest()
	{
	    String args= System.getProperties().getProperty(ARG_COMMAND_LINE_PARAMS);
	    if (args.contains(ARG_SELFTEST))
		return(true);
	    else return(false);	    
	}
	
	public void start() throws PepperException 
	{
		try{
			logger.info(PepperUtil.getHello());
			
			logger.info("PepperModuleResolver.TemprorariesURI:\t"+ System.getProperty("PepperModuleResolver.TemprorariesURI"));
			logger.info(PepperConfiguration.PROP_PEPPER_MODULE_RESOURCES+":\t"+ System.getProperty(PepperConfiguration.PROP_PEPPER_MODULE_RESOURCES));
			logger.info("service registered(PepperConverter): "+this.pepper);
			
			if (pepper== null)
				throw new PepperException("No PepperConverter-object is given for pepper-osgi-runner.");
			
			if (pepper.getModuleResolver()== null)
				throw new PepperException("No '"+ModuleResolverImpl.class+"' is given for passed '"+Pepper.class+"' object.");
			//print registered pepper modules 
			logger.info(pepper.getModuleResolver().getStatus());
			
			if (isSelfTest())
			{
				logger.info("Pepper is running in self test mode...");
			    Collection<String> problems= pepper.selfTest();
			    if (problems.size()==0)
			    	logger.info("- no problems detected -");
			    else
			    {
			    	logger.info("following problems have been found:");
					for (String problem: problems)
						logger.info("\t"+ problem);
			    }
			}
			else
			{
	        		URI workflowDescURI= null;
	        		try {
	        			workflowDescURI= URI.createFileURI(getWorkflowDescriptionFile().getAbsolutePath());
	        		} catch (PepperOSGiRunnerException e) {
	        			logger.error(e.getMessage());
	        		}
	        		
	        		if (workflowDescURI!= null)
	        		{// pepper can be started
	        			PepperJob job= pepper.getJob(pepper.createJob());
	        			job.load(workflowDescURI);
	        			
	        			PepperJobReporter observer= new PepperJobReporter(job);
	        			observer.start();
	        			
	        			try {
	        				job.convert();
	        			} catch (PepperException e) 
	        			{
	        				System.err.println(e);
	        				throw e;
	        			}
	        			catch (Exception e)
	        			{
	        				System.err.println(e);
	        				throw e;
	        			}finally{
	        				observer.setStop(true);
	        			}
	        		}// pepper can be started
			}
		}catch (Exception e) {
			logger.info("Launching of pepper-osgi-runner ended with errors.");
			logger.info("================================== Good bye ==================================");
			throw new PepperOSGiRunnerException("An error occured while running pepper-osgi-runner, because of nested exception.", e);
		}
		
	}
	
	private void printBye(long millis)
	{
		logger.info("time to compute all comparisons: ");		
		
		logger.info("Conversion ended, and needed (milli seconds): "+ millis);
		logger.info("************************************************************************");
	}
	
	private Boolean isDisabled= false;
	
	/**
	 * Method is called by OSGi framework, when bundle is activated. 
	 * <br/>
	 * This method is the entry point, for starting pepper inside an OSGi environment.
	 * @param componentContext reference to the OSGi environment 
	 */
	@Activate
	protected void activate(ComponentContext componentContext)
	{
		Enumeration<Object> keys= componentContext.getProperties().keys();
		
		while (keys.hasMoreElements())
		{
			Object key= keys.nextElement();
		}		
		
		if (	(System.getProperty(PROP_TEST_DISABLED)== null) ||
				(!Boolean.valueOf(System.getProperty(PROP_TEST_DISABLED))))
		{
			this.isDisabled= false;
		}
		else 
			this.isDisabled= true;
		if (!this.isDisabled)
		{	
			Thread pepperTestThread= new Thread(this, "PepperTest-Thread");
			pepperTestThread.start();
		}
	}
	
	/**
	 * Method is called by OSGi framework, when bundle is deactivated. This could happen, if the
	 * user deactivates the bundle manually or, the entire OSGi environment stopped.
	 * <br/>
	 * This method currently does nothing.
	 * @param componentContext reference to the OSGi environment
	 */
	@Deactivate
	protected void deactivate(ComponentContext componentContext)
	{}

	
	@Override
	public void run() 
	{
		Long millis= null;
		try {
			millis= System.currentTimeMillis();
			this.start();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Any exception occurs while running pepper-osgi-runner.", e);
			throw new PepperOSGiRunnerException("Any exception occurs while running pepper-osgi-runner.", e);
		}
		finally
		{
			millis= System.currentTimeMillis() - millis;
			printBye(millis);
		}
	}
}

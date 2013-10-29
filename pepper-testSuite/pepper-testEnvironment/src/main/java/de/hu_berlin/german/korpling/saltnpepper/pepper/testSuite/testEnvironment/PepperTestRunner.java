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
package de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.testEnvironment;

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
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.testEnvironment.exceptions.PepperTestException;
/**
 * Only starts test framework, if {@value #PROP_TEST_DISABLED} is not set or is set to false.  
 * @author Florian Zipser
 *
 */
@Component(name="PepperTestComponent", immediate=true, enabled=true)
//TODO replace environment variables and parameters with a param file, which can be passed via OSGi to the test-environmet   
public class PepperTestRunner implements Runnable
{
	public final static String PROP_TEST_DISABLED= "de.hu_berlin.german.korpling.saltnpepper.pepper.disableTest";
	/**
	 * name of environment variable, which is supposed to contain the workflow description file
	 */
	public static final String ENV_PEPPER_TEST_WORKFLOW_FILE= "PEPPER_TEST_WORKFLOW_FILE";
	/**
	 * extension of where to find plugins and resources
	 */
	public static final String DIR_PLUGINS= "/plugins";
	/**
	 * extension of where to find plugins and resources
	 */
	public static final String DIR_CONF= "/conf";
	
	public PepperTestRunner()
	{}

// ========================================== start: LogService	
	private LogService logService;

	@Reference(unbind="unsetLogService", cardinality=ReferenceCardinality.MANDATORY, policy=ReferencePolicy.STATIC)
	public void setLogService(LogService logService) 
	{
		if (!this.isDisabled)
		{	
			this.logService = logService;
		}
	}
	
	public LogService getLogService() 
	{
		return(this.logService);
	}
	
	public void unsetLogService(LogService logService) {
		if (!this.isDisabled)
		{	
			logService= null;
		}
	}

// ========================================== end: LogService
	
// ========================================== start: PepperConverter		
	private PepperConverter converter= null;
	public void unsetPepperConverter(PepperConverter pepperConverter)
	{
		if (!this.isDisabled)
		{	
			this.converter= null;
		}
	}
	@Reference(unbind="unsetPepperConverter", cardinality=ReferenceCardinality.MANDATORY, policy=ReferencePolicy.STATIC)
	public void setPepperConverter(PepperConverter pepperConverter)
	{
		if (!this.isDisabled)
		{	
			this.converter= pepperConverter;
		}
	}
// ========================================== end: PepperConverter
	
	private static File getWorkflowDescriptionFile()
	{
		if (System.getenv(ENV_PEPPER_TEST_WORKFLOW_FILE)== null)
			throw new PepperTestException("Cannot start PepperTest, please set environment variable '"+ENV_PEPPER_TEST_WORKFLOW_FILE+"' to workflow description file which is supposed to be used for conversion.");
		if (System.getenv(ENV_PEPPER_TEST_WORKFLOW_FILE).isEmpty())
			throw new PepperTestException("Cannot start PepperTest, please set environment variable '"+ENV_PEPPER_TEST_WORKFLOW_FILE+"' to workflow description file which is supposed to be used for conversion. Currently it is empty.");
		File workflowDescFile= new File(System.getenv(ENV_PEPPER_TEST_WORKFLOW_FILE));
		if (!workflowDescFile.exists())
			throw new PepperTestException("Cannot start PepperTest, because environment variable '"+ENV_PEPPER_TEST_WORKFLOW_FILE+"' points to a non  existing file '"+workflowDescFile.getAbsolutePath()+"'.");
		return(workflowDescFile);
	}
	
	public static final String logReaderName= "de.hu_berlin.german.korpling.saltnpepper.pepper-logReader";
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
	
	public void start() throws Exception 
	{
		//setting system properties for several bundles (resource respectively tmp-folders)
			//for LogService
				String logReaderResource= converter.getConfiguration().getConfFolder().getAbsolutePath();
				if (!new File(logReaderResource).exists())
					throw new Exception("Cannot start PepperTest, because no log-file is given at resource '"+logReaderResource+"'.");
				System.setProperty(logReaderName+".resources", logReaderResource);
			//for LogService
		//setting system properties for several bundles (resource respectively tmp-folders)
		
		if (this.logService!= null)
		{
			this.logService.log(LogService.LOG_INFO,"PepperModuleResolver.TemprorariesURI:\t"+ System.getProperty("PepperModuleResolver.TemprorariesURI"));
			this.logService.log(LogService.LOG_INFO,PepperProperties.PROP_PEPPER_MODULE_RESOURCES+":\t"+ System.getProperty(PepperProperties.PROP_PEPPER_MODULE_RESOURCES));
			this.logService.log(LogService.LOG_INFO,logReaderName+".resources:\t"+ System.getProperty(logReaderName+".resources"));
		}	
		if (this.logService!= null)
			this.logService.log(LogService.LOG_DEBUG,"service registered(PepperConverter): "+this.converter);
		if (converter== null)
			throw new PepperException("No PepperConverter-object is given for PepperTest.");
		//print registered pepper modules 
		if (this.logService!= null)
		{
			this.logService.log(LogService.LOG_INFO, converter.getPepperModuleResolver().getStatus());
		}
		
		if (isSelfTest())
		{
		    this.logService.log(LogService.LOG_INFO, "Run pepper in self test mode");
		    Collection<String> problems= converter.selfTest();
		    if (problems.size()==0)
			this.logService.log(LogService.LOG_INFO, "- no problems detected -");
		    else
		    {
			this.logService.log(LogService.LOG_INFO, "following problems have been found:");
			for (String problem: problems)
			    this.logService.log(LogService.LOG_INFO, "\t"+ problem);
		    }
		}
		else
		{
        		URI workflowDescURI= null;
        		try {
        			workflowDescURI= URI.createFileURI(getWorkflowDescriptionFile().getAbsolutePath());
        		} catch (PepperTestException e) {
        			if (this.logService!= null)
        				this.logService.log(LogService.LOG_ERROR, e.getMessage());
        			else 
        				System.err.println(e.getMessage());
        		}
        		
        		if (workflowDescURI!= null)
        		{// pepper can be started
        			converter.setPepperParams(workflowDescURI);
        			
        			try {
        				converter.setParallelized(true);
        				converter.start();
        			} catch (PepperException e) 
        			{
        				System.err.println(e);
        				throw e;
        			}
        			catch (Exception e)
        			{
        				System.err.println(e);
        				throw e;
        			}
        		}// pepper can be started
		}
	}
	
	private void printHello()
	{
		if (this.logService== null)
			throw new PepperTestException("Pepper testEnvironment is running in stealth mode, because no LogService is set. Please check your configuration.");
		
		this.logService.log(LogService.LOG_INFO,"************************************************************************");
		this.logService.log(LogService.LOG_INFO,"***                      Test Pepper Converter                       ***");
		this.logService.log(LogService.LOG_INFO,"************************************************************************");
		this.logService.log(LogService.LOG_INFO,"* Pepper converter is a salt model based converter for a lot of        *");
		this.logService.log(LogService.LOG_INFO,"* linguistical formats.                                                *");
		this.logService.log(LogService.LOG_INFO,"* for contact write an eMail to: saltnpepper@lists.hu-berlin.de        *");
		this.logService.log(LogService.LOG_INFO,"************************************************************************");
		this.logService.log(LogService.LOG_INFO,"\n");
//		this.logService.log(LogService.LOG_INFO,"given workflow description file:\t"+ getWorkflowDescripptionFile());
	}
	
	private void printBye(long millis)
	{
		this.logService.log(LogService.LOG_INFO,"time to compute all comparisons: ");		
		
		this.logService.log(LogService.LOG_INFO,"Conversion ended, and needed (milli seconds): "+ millis);
		this.logService.log(LogService.LOG_INFO,"************************************************************************");
	}
	
	private Boolean isDisabled= false;
	
	/**
	 * Only starts test framework, if {@value #PROP_TEST_DISABLED} is not set or is set to false. 
	 */
	@Activate
	protected void activate(ComponentContext componentContext)
	{
		System.out.println("================================== Welcome to Pepper ==================================");
		
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
			if (this.getLogService()!= null) 
				this.logService.log(LogService.LOG_INFO,"----------------------- bundle pepper-testEnvironment is deactivated -----------------------");
			else System.out.println("----------------------- bundle pepper-testEnvironment is deactivated -----------------------");
			Thread pepperTestThread= new Thread(this, "PepperTest-Thread");
			pepperTestThread.start();
		}
	}
	
	@Deactivate
	protected void deactivate(ComponentContext componentContext)
	{
		if (!this.isDisabled)
		{	
			if (this.getLogService()!= null)
				this.logService.log(LogService.LOG_INFO,"----------------------- bundle pepper-testEnvironment is deactivated -----------------------");
			else System.out.println("----------------------- bundle pepper-testEnvironment is deactivated -----------------------");
		}
		System.out.println("================================== Good bye ==================================");
	}

	
	@Override
	public void run() 
	{
		Long millis= null;
		try {
			millis= System.currentTimeMillis();
			this.printHello();
			this.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PepperTestException("Any exception occurs while running PepperTest.", e);
		}
		finally
		{
			millis= System.currentTimeMillis() - millis;
			System.out.println();
			printBye(millis);
		}
	}
}

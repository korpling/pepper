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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModulesTest;

import java.io.File;
import java.util.Properties;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.util.*;

@Component(name="PepperTestComponent", immediate=true)
@Service
public class PepperTest implements Runnable
{
	/**
	 * name of environment variable, which is supposed to contain the workflow description file
	 */
	public static final String ENV_PEPPER_TEST_WORKFLOW_FILE= "PEPPER_TEST_WORKFLOW_FILE";
	/**
	 * name of environment variable, which is supposed to contain configurations for pepper modules
	 */
	public static final String ENV_PEPPER_TEST= "PEPPER_TEST";
	
	public PepperTest()
	{}

// ========================================== start: LogService	
	@Reference(bind="setLogService", unbind="unsetLogService", cardinality=ReferenceCardinality.MANDATORY_UNARY, policy=ReferencePolicy.STATIC)
	private LogService logService;

	public void setLogService(LogService logService) 
	{
		this.logService = logService;
	}
	
	public LogService getLogService() 
	{
		return(this.logService);
	}
	
	public void unsetLogService(LogService logService) {
		logService= null;
	}

// ========================================== end: LogService
	
// ========================================== start: PepperConverter		
	@Reference(bind="setPepperConverter", unbind="unsetPepperConverter", cardinality=ReferenceCardinality.MANDATORY_UNARY, policy=ReferencePolicy.STATIC)
	private PepperConverter converter= null;
	public void unsetPepperConverter(PepperConverter pepperConverter)
	{
		this.converter= null;
	}
	
	public void setPepperConverter(PepperConverter pepperConverter)
	{
		this.converter= pepperConverter;
	}
// ========================================== end: PepperConverter
	
	private static File getWorkflowDescripptionFile()
	{
		if (System.getenv(ENV_PEPPER_TEST_WORKFLOW_FILE)== null)
			throw new RuntimeException("Cannot start PepperTest, please set environment variable '"+ENV_PEPPER_TEST_WORKFLOW_FILE+"' to workflow description file which is supposed to be used for confersion.");
		if (System.getenv(ENV_PEPPER_TEST_WORKFLOW_FILE).equals(""))
			throw new RuntimeException("Cannot start PepperTest, please set environment variable '"+ENV_PEPPER_TEST_WORKFLOW_FILE+"' to workflow description file which is supposed to be used for confersion. Currently it is empty.");
		File workflowDescFile= new File(System.getenv(ENV_PEPPER_TEST_WORKFLOW_FILE));
		if (!workflowDescFile.exists())
			throw new RuntimeException("Cannot start PepperTest, because environment variable '"+ENV_PEPPER_TEST_WORKFLOW_FILE+"' points to a non  existing file '"+workflowDescFile.getAbsolutePath()+"'.");
		return(workflowDescFile);
	}
	
	public static final String logReaderName= "de.hu_berlin.german.korpling.saltnpepper.misc.LogReader";
	
	public void start() throws Exception 
	{
		{//setting system properties for several bundles (resource respectively tmp-folders)
			File pepperTestPath= null;
			{//checking environment variable PEPPER_TEST
				String pepperTestPathStr= System.getenv(ENV_PEPPER_TEST);
				if (	(pepperTestPathStr== null) ||
						(pepperTestPathStr.equals("")))
					throw new NullPointerException("Cannot start PepperTest, because the environment variable '"+ENV_PEPPER_TEST+"' is not set. Please set this variable and try again.");
				pepperTestPath= new File(pepperTestPathStr);
				if (!pepperTestPath.exists())
					throw new NullPointerException("Cannot start PepperTest, because the path '"+pepperTestPathStr+"' to which the environment variable '"+ENV_PEPPER_TEST+"' points to does not exists.");
			}//checking environment variable PEPPER_TEST
			
			//for module resolver
			System.setProperty("PepperModuleResolver.TemprorariesURI", pepperTestPath.getAbsolutePath()+"/TMP");
			System.setProperty("PepperModuleResolver.ResourcesURI", pepperTestPath.getAbsolutePath()+"/resources");
			{//for LogService
				String logReaderResource= pepperTestPath.getAbsolutePath()+ "/resources/"+logReaderName+"/";
				
				if (!new File(logReaderResource).exists())
					throw new Exception("Cannot start PepperTest, because no log-file is given at resource '"+logReaderResource+"'.");
				System.setProperty(logReaderName+".resources", logReaderResource);
			}//for LogService
		}//setting system properties for several bundles (resource respectively tmp-folders)
		
		if (this.logService!= null)
		{
			this.logService.log(LogService.LOG_INFO,"PepperModuleResolver.TemprorariesURI:\t"+ System.getProperty("PepperModuleResolver.TemprorariesURI"));
			this.logService.log(LogService.LOG_INFO,"PepperModuleResolver.ResourcesURI:\t"+ System.getProperty("PepperModuleResolver.ResourcesURI"));
			this.logService.log(LogService.LOG_INFO,logReaderName+".resources:\t"+ System.getProperty(logReaderName+".resources"));
		}	
		URI workflowDescURI= URI.createFileURI(getWorkflowDescripptionFile().getAbsolutePath());
		if (this.logService!= null)
			this.logService.log(LogService.LOG_DEBUG,"service registered(PepperConverter): "+this.converter);
		if (converter== null)
			throw new PepperException("No PepperConverter-object is given for PepperTest.");
		converter.setPepperParams(workflowDescURI);
		
		{//creating user-defined properties
			Properties props= new Properties();
			props.setProperty(PepperFWProperties.PROP_COMPUTE_PERFORMANCE, "true");
			props.setProperty(PepperFWProperties.PROP_MAX_AMOUNT_OF_SDOCUMENTS, "10");
			props.setProperty(PepperFWProperties.PROP_REMOVE_SDOCUMENTS_AFTER_PROCESSING, "true");
			converter.setProperties(props);
		}//creating user-defined properties
		
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
	}
	
	private void printHello()
	{
		if (this.logService== null)
			throw new NullPointerException("Cannot go on, because no LogService is set. It shall not be possible to start a PepperTest-object without LogService-object.");
		
		this.logService.log(LogService.LOG_INFO,"************************************************************************");
		this.logService.log(LogService.LOG_INFO,"***                      Test Pepper Converter                       ***");
		this.logService.log(LogService.LOG_INFO,"************************************************************************");
		this.logService.log(LogService.LOG_INFO,"* Pepper converter is a salt model based converter for a lot of        *");
		this.logService.log(LogService.LOG_INFO,"* linguistical formats.                                                *");
		this.logService.log(LogService.LOG_INFO,"* for contact write an eMail to: saltnpepper@lists.hu-berlin.de        *");
		this.logService.log(LogService.LOG_INFO,"************************************************************************");
		this.logService.log(LogService.LOG_INFO,"\n");
		this.logService.log(LogService.LOG_INFO,"given workflow description file:\t"+ getWorkflowDescripptionFile());
	}
	
	private void printBye(long millis)
	{
		this.logService.log(LogService.LOG_INFO,"time to compute all comparisons: ");
//		EqualsCounter.count();
		
		
		this.logService.log(LogService.LOG_INFO,"Conversion ended, and needed (milli seconds): "+ millis);
		this.logService.log(LogService.LOG_INFO,"************************************************************************");
	}
	
	protected void activate(ComponentContext componentContext)
	{
			System.out.println("PepperTest Komponente wird aktiviert");
			Thread pepperTestThread= new Thread(this, "PepperTest-Thread");
			pepperTestThread.start();
	}
	
	protected void deactivate(ComponentContext componentContext)
	{
		System.out.println("PepperTest Komponente wird deaktiviert");
		this.logService.log(LogService.LOG_INFO,"PepperTest Komponente wird deaktiviert");
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
			throw new PepperException("Any exception occurs while running PepperTest.", e);
		}
		finally
		{
			millis= System.currentTimeMillis() - millis;
			System.out.println();
			printBye(millis);
		}
	}
}

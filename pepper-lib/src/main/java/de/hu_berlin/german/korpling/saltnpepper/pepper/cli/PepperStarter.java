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
import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil.PepperJobReporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.PepperConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.impl.PepperOSGiConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;

public class PepperStarter 
{
	/**
	 * A logger for logging messages.
	 */
	private static final Logger logger= LoggerFactory.getLogger(PepperStarter.class);

	/**
	 * Returns synopsis for program call
	 * @return
	 */
	public static String getSynopsis()
	{
		StringBuilder retStr= new StringBuilder();
		retStr.append("Synopsis:\tPepperStarter -p PEPPER_PARAM \n");
		retStr.append("\n");
		
		return(retStr.toString());
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{	
		PepperStarterConfiguration pepperProps= new PepperStarterConfiguration();
		pepperProps.load();
		
		String eMail= pepperProps.getPepperEMail();
		String hp= pepperProps.getPepperHomepage();
		
		logger.info(PepperUtil.getHello(eMail, hp));
		//setting timer to stop needed time
		Long timestamp= System.currentTimeMillis();
		
		//path of property file
		String udPropFile= null;
		
		//path of pepper parameters
		String pepperWorkflowDescriptionFile= null;
		
		//if help shall be printed
		boolean isHelp= false;
		// determines if a status should be displayed
		boolean isStatus= false;
		// determines if pepper should run in debug mode
		boolean isDebug= false;
		
		//check parameters
		for (int i=0; i < args.length; i++){
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
			}else if (args[i].equalsIgnoreCase("status")){
				isStatus= true;
			}else if (args[i].equalsIgnoreCase("debug")){
				isDebug= true;
			}
			
		}
		boolean endedWithErrors= false;
		//marks if parameter for program call are ok
		boolean paramsOk= false;
		URI paramUri= null;
		if (isHelp)
			logger.info(getHelp());
		else if(isStatus){
			PepperConnector pepper= new PepperOSGiConnector();
			pepper.setProperties(pepperProps);
			pepper.init();
			Collection<PepperModuleDesc> moduleDescs= pepper.getRegisteredModules();
			if (	(moduleDescs== null)||
					(moduleDescs.size()== 0)){
				logger.info("- no modules registered -");
			}else{
				String format = "| %1$-30s | %2$-15s | %3$-15s | %4$-40s | %5$-20s |";
				logger.info("+--------------------------------+-----------------+-----------------+------------------------------------------+----------------------+");
				logger.info(String.format(format, "module-name", "module-version", "module-type", "formats", "supplier-contact"));
				format = "| %1$-30s | %2$-15s | %3$-15s | %4$-40s | %5$-20s |";
				logger.info("+--------------------------------+-----------------+-----------------+------------------------------------------+----------------------+");
				for (PepperModuleDesc desc: moduleDescs){
					String formatString= "";
							
					if (	(desc.getSupportedFormats()!= null)&&
							(desc.getSupportedFormats().size()> 0)){
						int i= 0;
						for (FormatDesc formatDesc: desc.getSupportedFormats()){
							if (i!= 0){
								formatString= formatString +"; ";
							}
							formatString= formatString+ formatDesc.getFormatName() + ", "+ formatDesc.getFormatVersion();
							i++;
						}
					}
					
					if (desc!= null){
						logger.info(String.format(format, desc.getName(), desc.getVersion(), desc.getModuleType(), formatString, desc.getSupplierContact()));
					}
				}
				logger.info("+--------------------------------+-----------------+-----------------+------------------------------------------+----------------------+");
			}
			
		}else if (isDebug){
			PepperConnector pepper= new PepperOSGiConnector();
			pepper.setProperties(pepperProps);
			pepper.init();
			while(true){
				
			}
		}else{//no flag for help	
			try {
				if (	(pepperWorkflowDescriptionFile== null) ||
						(pepperWorkflowDescriptionFile.isEmpty())){
					throw new PepperException("No parameters for pepper converter are given.");	
				//program call
				}else{
					pepperWorkflowDescriptionFile= pepperWorkflowDescriptionFile.replace("\\", "/");
					paramUri= URI.createFileURI(pepperWorkflowDescriptionFile);
					paramsOk= true;
				}
				if (	(udPropFile!= null) &&
						(udPropFile.isEmpty())){
					pepperProps.load(new File(udPropFile));
				}
			} catch (Exception e){
				StringBuilder errorStr= new StringBuilder();
				errorStr.append("Error in program call:\n");
				errorStr.append("\t"+e+"\n");
				errorStr.append("\n");
				errorStr.append(getHelp());
				logger.error(errorStr.toString());
			}
			
			//starts converting
			if (paramsOk){
				try{
					if(logger.isDebugEnabled()){
						for (Object key: pepperProps.keySet()){
							logger.debug(String.format("%-40s%-16s", key+":", pepperProps.get(key)));
						}
					}
					
					
					PepperConnector pepper= new PepperOSGiConnector();
					pepper.setProperties(pepperProps);
					pepper.init();
					
					logger.debug(pepper.getRegisteredModulesAsString());
					
					String jobId= pepper.createJob();
					
					PepperJob pepperJob= pepper.getJob(jobId);
					pepperJob.load(paramUri);
					PepperJobReporter observer= new PepperJobReporter(pepperJob);
        			observer.start();
					try{
						pepperJob.convert();
					}finally{
						observer.setStop(true);
					}
					
					pepper.removeJob(jobId);
				}catch (Exception e) {
					endedWithErrors= true;
					logger.error("", e);
				}	
				timestamp= System.currentTimeMillis() - timestamp;
				if (endedWithErrors){
					logger.info("CONVERSION ENDED WITH ERRORS, REQUIRED TIME: "+ timestamp +" milliseconds");
				}else{
					logger.info("CONVERSION ENDED SUCCESSFULLY, REQUIRED TIME: "+timestamp +" milliseconds");
				}
			}
		}//no flag for help
		logger.info(getGoodBye());
		if (endedWithErrors){
			System.exit(-1);
		}else{
			System.exit(0);
		}
	}

}

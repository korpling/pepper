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
import java.lang.reflect.Method;
import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asg.cliche.Command;
import asg.cliche.CommandTable;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil.PepperJobReporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.PepperConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.impl.PepperOSGiConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;

public class PepperStarter 
{
	/**
	 * A logger for logging messages.
	 */
	private static final Logger logger= LoggerFactory.getLogger(PepperStarter.class);

	/**
	 * Initializes an instance of PepperStarter.
	 * @param pepperConnector a {@link PepperConnector} object via which the {@link PepperStarter} communicates with a {@link Pepper} instance 
	 */
	public PepperStarter(PepperConnector pepperConnector){
		setPepper(pepperConnector);
		pepper.init();
	}
	
	/**
	 * A reference to Pepper via a {@link PepperConnector}
	 */
	private PepperConnector pepper= null;
	public PepperConnector getPepper() {
		return pepper;
	}

	public void setPepper(PepperConnector pepper) {
		this.pepper = pepper;
	}	
	
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
	
	public static final String COMMAND_INFO="info";
	public static final String COMMAND_HELP="help";
	public static final String COMMAND_SELFTEST="selfTest";
	
	/**
	 * Help to all commands for Pepper
	 * @return
	 */
	@Command(description="Shows all available commands.")
	public String help(){
		StringBuilder retVal= new StringBuilder();
		String format = "| %1$-20s | %2$-15s | %3$-70s |\n";
		retVal.append("+----------------------+-----------------+------------------------------------------------------------------------+\n");
		retVal.append(String.format(format, "command", "parameters", "description"));
		retVal.append("+----------------------+-----------------+------------------------------------------------------------------------+\n");
		format = "| %1$20s | %2$15s | %3$70s |\n";
		retVal.append(String.format(format, COMMAND_INFO, "--", "A table with information about all available Pepper modules."));
		retVal.append(String.format(format, COMMAND_INFO, "module-name", "A table with information about the passed Pepper module."));
		retVal.append(String.format(format, COMMAND_HELP, "--", "Prints this help."));
		retVal.append(String.format(format, COMMAND_SELFTEST, "--", "Tests if the Pepper framework is in runnable mode or if any problems are detected, either in Pepper itself or in any registered Pepper module."));
		retVal.append("+----------------------+-----------------+------------------------------------------------------------------------+\n");
		return(retVal.toString());
	}

	/**
	 * Returns a String containing a table with information about all available Pepper modules.
	 * @return
	 */
    @Command(description="A table with information about all available Pepper modules.") 
    public String info() {
    	StringBuilder retVal= new StringBuilder();

		Collection<PepperModuleDesc> moduleDescs= getPepper().getRegisteredModules();
		if (	(moduleDescs== null)||
				(moduleDescs.size()== 0)){
			retVal.append("- no modules registered -\n");
		}else{
			String format = "| %1$-30s | %2$-15s | %3$-15s | %4$-40s | %5$-20s |\n";
			retVal.append("+--------------------------------+-----------------+-----------------+------------------------------------------+----------------------+\n");
			retVal.append(String.format(format, "module-name", "module-version", "module-type", "formats", "supplier-contact"));
			format = "| %1$-30s | %2$-15s | %3$-15s | %4$-40s | %5$-20s |\n";
			retVal.append("+--------------------------------+-----------------+-----------------+------------------------------------------+----------------------+\n");
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
					retVal.append(String.format(format, desc.getName(), desc.getVersion(), desc.getModuleType(), formatString, desc.getSupplierContact()));
				}
			}
			retVal.append("+--------------------------------+-----------------+-----------------+------------------------------------------+----------------------+\n");
		}
		return(retVal.toString());
    }
    
    /**
     * Calls {@link PepperModule#isReadyToStart()} for all Pepper modules.
     * @return
     */
    @Command(description="Tests if the Pepper framework is in runnable mode or if any problems are detected, either in Pepper itself or in any registered Pepper module.")
    public String selfTest(){
    	StringBuilder retVal= new StringBuilder();
    	Collection<String> problems= getPepper().selfTest();
	    if (problems.size()==0)
	    	retVal.append("- no problems detected -");
	    else
	    {
	    	retVal.append("following problems have been found:");
			for (String problem: problems){
				retVal.append("\t"+ problem);
			}
	    }
    	return(retVal.toString());
    }
    
    /**
	 * Returns a String containing a table with information about the passed Pepper module.
	 * @return
	 */
    @Command(description="A table with information about the passed Pepper module.") 
    public String info(String moduleName) {
    	StringBuilder retVal= new StringBuilder();
    	
    	PepperModuleDesc moduleDesc= null;
    	Collection<PepperModuleDesc> moduleDescs= getPepper().getRegisteredModules();
		if (	(moduleName!= null)&&
				(moduleDescs!= null)&&
				(moduleDescs.size()> 0)){
			for (PepperModuleDesc desc: moduleDescs){
				if (moduleName.equals(desc.getName())){
					moduleDesc= desc;
					break;
				}
			}
		}
		if (moduleDesc!= null){
			retVal.append(moduleDesc.getName());
			retVal.append(", ");
			retVal.append(moduleDesc.getVersion());
			retVal.append("\n");
			retVal.append("supplier:");
			retVal.append(moduleDesc.getSupplierContact());
			retVal.append("\n");
			retVal.append("-------------------------------------------------------------------------\n");
			retVal.append((moduleDesc.getDesc()== null)? "- no description available -":moduleDesc.getDesc());
			retVal.append("\n");
		}else{
			retVal.append("- no Pepper module was found for given name '"+moduleName+"' -");
		}
    	return(retVal.toString());
    }
	
    public String hello(){
    	return("UNBELIEVABLE IT WORKS!!!!!");
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
		boolean isInfo= false;
		boolean isSelfTest= false;
		
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
			}else if (args[i].equalsIgnoreCase(COMMAND_INFO)){
				isInfo= true;
			}else if (args[i].equalsIgnoreCase(COMMAND_SELFTEST)){
				isInfo= true;
			}
		}
		boolean endedWithErrors= false;
		//marks if parameter for program call are ok
		boolean paramsOk= false;
		URI paramUri= null;
		
		PepperConnector pepper= new PepperOSGiConnector();
		pepper.setProperties(pepperProps);
		PepperStarter starter= new PepperStarter(pepper);
		
		if (isHelp)
			logger.info(getHelp());
		else if(isInfo){
			logger.info(starter.info()); 
			
		}else if (isSelfTest){
			logger.info(starter.selfTest());
		}else{//no flag for help	
			try {
				if (	(pepperWorkflowDescriptionFile== null) ||
						(pepperWorkflowDescriptionFile.isEmpty())){
					Shell shell= ShellFactory.createConsoleShell("pepper", "Welcome to the interactive console of Pepper. Type in 'h' or 'help' for help. ", starter);
					CommandTable commands= shell.getCommandTable();
					System.out.println("commands: "+ commands);
					Method method= PepperStarter.class.getMethod("hello");
					commands.addMethod(method, starter, "pepper");
					
					shell.commandLoop();
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

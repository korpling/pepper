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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import asg.cliche.Command;
//import asg.cliche.CommandTable;
//import asg.cliche.Shell;
//import asg.cliche.ShellFactory;
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
	
	private static String getGoodBye()
	{
		StringBuilder retVal= new StringBuilder();
		retVal.append("************************************************************************\n");
		return(retVal.toString());
	}
	
	public enum COMMAND{
		LIST_ALL("list", "l", null, "A table with information about all available Pepper modules."),
		LIST("list", "l", "module-name", "A table with information about the passed Pepper module."),
		HELP("help", "h", null, "Prints this help."),
		SELFTEST("self-test", "st", null, "Tests if the Pepper framework is in runnable mode or if any problems are detected, either in Pepper itself or in any registered Pepper module."),
		EXIT("exit", "e", null, "exits Pepper"),
		CONVERT("convert", "c", "workflow-file", "Loads the passed 'workflow-file' and starts the conversion."),
		OSGI("osgi", "o", null, "opens a console to access the underlying OSGi environment, if OSGi is used."),
		LAUNCH("launch", "la", "module-path", "installs the Pepper module located at 'module path' and starts it."),
		UPDATE("update", "up", "module-path", "updates a Pepper module with the module located at 'module path' and starts it."),
		REMOVE("remove", "re", "module-path", "removes the Pepper module, which was installed from 'module path'.");
		
		private String name= null;
		private String abbreviation= null;
		private String description= null;
		private String parameters= null;
		
		private COMMAND(String name, String abbreviation, String parameters, String description){
			this.name= name;
			this.abbreviation= abbreviation;
			this.parameters= parameters;
			this.description= description;
		}
		
		public String getName(){
			return(name);
		}
		public String getAbbreviations(){
			return(abbreviation);
		}
		public String getParameters(){
			return(parameters);
		}
		public String getDescription(){
			return(description);
		}
	}
	
	/**
	 * Help to all commands for Pepper
	 * @return
	 */
//	@Command(description="Shows all available commands.")
	public String help(){
		StringBuilder retVal= new StringBuilder();
		String format = "| %1$-20s | %2$-15s | %3$-70s |\n";
		retVal.append("+----------------------+-----------------+------------------------------------------------------------------------+\n");
		retVal.append(String.format(format, "command", "parameters", "description"));
		retVal.append("+----------------------+-----------------+------------------------------------------------------------------------+\n");
		format = "| %1$-20s | %2$-15s | %3$-70s |\n";
		for (COMMAND command: COMMAND.values()){
			retVal.append(String.format(format, command.getName(), (command.getParameters()== null)?" -- ":command.getParameters(), command.getDescription()));
		}
		retVal.append("+----------------------+-----------------+------------------------------------------------------------------------+\n");
		return(retVal.toString());
	}

	/**
	 * Returns a String containing a table with information about all available Pepper modules.
	 * @return
	 */
//    @Command(description="A table with information about all available Pepper modules.") 
    public String list() {
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
	 * Returns a String containing a table with information about the passed Pepper module.
	 * @return
	 */
//    @Command(description="A table with information about the passed Pepper module.") 
    public String list(String moduleName) {
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
    /**
     * Opens the OSGi console via an {@link OSGiConsole} object and delegates to it.
     */
    public String osgi(){
    	if (getPepper() instanceof PepperOSGiConnector){
    		OSGiConsole console= new OSGiConsole((PepperOSGiConnector)getPepper(), PROMPT);
    		console.start(input, output);
    		return("exit OSGi");
    	}else{
    		return("No OSGi console availablem, since Pepper is not running in OSGi mode. ");
    	}
    }
    
    /**
     * Loads the passed workflow description file and starts the conversion.
     * @param workFlowFile
     */
    public void convert(String workFlowFile){
    	URI workFlowUri= URI.createFileURI(workFlowFile);	
    	String jobId= pepper.createJob();
			
		PepperJob pepperJob= pepper.getJob(jobId);
		pepperJob.load(workFlowUri);
		PepperJobReporter observer= new PepperJobReporter(pepperJob);
		observer.start();
		try{
			pepperJob.convert();
		}finally{
			observer.setStop(true);
		}
		
		pepper.removeJob(jobId);	
    }
    /**
     * Installs and starts a new Pepper module(s).  
     */
    public String launch(List<String> params){
    	if (getPepper() instanceof PepperOSGiConnector){
    		OSGiConsole console= new OSGiConsole((PepperOSGiConnector)getPepper(), PROMPT);
    		console.launch(params, output);
    		return("launched Pepper module");
    	}else{
    		return("No OSGi console availablem, since Pepper is not running in OSGi mode. ");
    	}
    }
    /**
     * Updates a Pepper module(s).  
     */
    public String update(List<String> params){
    	if (getPepper() instanceof PepperOSGiConnector){
    		OSGiConsole console= new OSGiConsole((PepperOSGiConnector)getPepper(), PROMPT);
    		console.launch(params, output);
    		return("updated Pepper module");
    	}else{
    		return("No OSGi console availablem, since Pepper is not running in OSGi mode. ");
    	}
    }
    /**
     * Removes a new Pepper module(s).  
     */
    public String remove(List<String> params){
    	if (getPepper() instanceof PepperOSGiConnector){
    		OSGiConsole console= new OSGiConsole((PepperOSGiConnector)getPepper(), PROMPT);
    		console.uninstall(params, output);
    		return("removed Pepper module");
    	}else{
    		return("No OSGi console availablem, since Pepper is not running in OSGi mode. ");
    	}
    }
    
    /**
     * Calls {@link PepperModule#isReadyToStart()} for all Pepper modules.
     * @return
     */
//    @Command(description="Tests if the Pepper framework is in runnable mode or if any problems are detected, either in Pepper itself or in any registered Pepper module.")
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

    public static final String PROMPT= "pepper";
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private PrintStream output= System.out;
    /**
     * Starts the interactive console of Pepper.
     */
    public void runInteractive(){
		boolean exit= false;
		String userInput= null;
		output.println("Welcome to Pepper, type 'help' for help.");
		while (!exit){
			try {
				output.print(PROMPT+">");
				userInput = input.readLine();
			} catch (IOException ioe) {				
		         output.println("Cannot read command, type in 'help' for help.");
		    }
			userInput= userInput.trim();
			String[] parts=userInput.split(" ");
			String command= parts[0];
			List<String> params= new Vector<String>();
			int i= 0;
			for (String part: parts){
				if (i> 0){
					params.add(part);
				}
				i++;
			}
			if (	(COMMAND.HELP.getName().equalsIgnoreCase(command))||
					(COMMAND.HELP.getAbbreviations().equalsIgnoreCase(command))){
				output.println(help());
			}else if (	(params.size()== 0)&&
						(	(COMMAND.LIST.getName().equalsIgnoreCase(command))||
								(COMMAND.LIST.getAbbreviations().equalsIgnoreCase(command)))){
				output.println(list());
			}else if (	((params.size()> 0))&&
						(	(COMMAND.LIST.getName().equalsIgnoreCase(command))||
							(COMMAND.LIST.getAbbreviations().equalsIgnoreCase(command)))){
				if (params.size()== 1){
					output.println(list(params.get(0)));
				}else{
					output.println("Please pass exactly one module name.");
				}
			}else if (	(COMMAND.SELFTEST.getName().equalsIgnoreCase(command))||
						(COMMAND.SELFTEST.getAbbreviations().equalsIgnoreCase(command))){
				output.println(selfTest());
			}else if (	(COMMAND.EXIT.getName().equalsIgnoreCase(command))||
						(COMMAND.EXIT.getAbbreviations().equalsIgnoreCase(command))){
				break;
			}else if (	((params.size()> 0))&&
						(	(COMMAND.CONVERT.getName().equalsIgnoreCase(command))||
							(COMMAND.CONVERT.getAbbreviations().equalsIgnoreCase(command)))){
				if (params.size()== 1){
					Long timestamp= System.currentTimeMillis();
					try{
						convert(params.get(0));
						timestamp= System.currentTimeMillis() - timestamp;
						output.println("CONVERSION ENDED SUCCESSFULLY, REQUIRED TIME: "+timestamp +" milliseconds");
					}catch (Exception e){
						e.printStackTrace();
						timestamp= System.currentTimeMillis() - timestamp;
						output.println("CONVERSION ENDED WITH ERRORS, REQUIRED TIME: "+ timestamp +" milliseconds");
					}
				}else{
					output.println("Please pass exactly one workflow file.");
				}
			}else if(	(COMMAND.OSGI.getName().equalsIgnoreCase(command))||
						(COMMAND.OSGI.getAbbreviations().equalsIgnoreCase(command))){
				output.println(osgi());
			}else if(	(COMMAND.LAUNCH.getName().equalsIgnoreCase(command))||
						(COMMAND.LAUNCH.getAbbreviations().equalsIgnoreCase(command))){
				output.println(launch(params));
			}else if(	(COMMAND.UPDATE.getName().equalsIgnoreCase(command))||
						(COMMAND.UPDATE.getAbbreviations().equalsIgnoreCase(command))){
				output.println(update(params));
			}else if(	(COMMAND.REMOVE.getName().equalsIgnoreCase(command))||
						(COMMAND.REMOVE.getAbbreviations().equalsIgnoreCase(command))){
				output.println(remove(params));
			}else{
				output.println("Type 'help' for help.");
			}
		}
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
		boolean endedWithErrors= false;
		
		PepperConnector pepper= new PepperOSGiConnector();
		pepper.setProperties(pepperProps);
		PepperStarter starter= new PepperStarter(pepper);
		
		if (args.length== 0){
			//run interactive console
			try{
				starter.runInteractive();
				}catch (Exception e){
					e.printStackTrace();
				}
		}else if (	(COMMAND.HELP.getName().equalsIgnoreCase(args[0])||
					(COMMAND.HELP.getAbbreviations().equalsIgnoreCase(args[0])))){
			//print help
			logger.info(starter.help());
 
		}else if (	(COMMAND.LIST.getName().equalsIgnoreCase(args[0])||
					(COMMAND.LIST.getAbbreviations().equalsIgnoreCase(args[0])))){
			logger.info(starter.list());
		}else if (	(COMMAND.SELFTEST.getName().equalsIgnoreCase(args[0])||
					(COMMAND.SELFTEST.getAbbreviations().equalsIgnoreCase(args[0])))){
			logger.info(starter.selfTest());
		} else if (	("-p".equalsIgnoreCase(args[0]))||
					("-w".equalsIgnoreCase(args[0]))||
					(args[0]!= null)){
			String workFlowFile=null;
			if(	("-p".equalsIgnoreCase(args[0]))||
				("-w".equalsIgnoreCase(args[0]))){
				if (args[1]== null){
					logger.error("Cannot start conversion, since no workflow description file is given.");
					endedWithErrors= true;
				}else{
					workFlowFile= args[1];
				}
			}else {
				workFlowFile= args[0];
			}
			Long timestamp= System.currentTimeMillis();
			try{
				if(logger.isDebugEnabled()){
					for (Object key: pepperProps.keySet()){
						logger.debug(String.format("%-40s%-16s", key+":", pepperProps.get(key)));
					}
				}
				logger.debug(pepper.getRegisteredModulesAsString());
				workFlowFile= workFlowFile.replace("\\", "/");
				
				starter.convert(workFlowFile);
			}catch (Exception e) {
				endedWithErrors= true;
				logger.error("", e);
			}finally{
				timestamp= System.currentTimeMillis() - timestamp;
			}
			if (endedWithErrors){
				logger.info("CONVERSION ENDED WITH ERRORS, REQUIRED TIME: "+ timestamp +" milliseconds");
			}else{
				logger.info("CONVERSION ENDED SUCCESSFULLY, REQUIRED TIME: "+timestamp +" milliseconds");

			}
		}
		
		logger.info(getGoodBye());
		if (endedWithErrors){
			System.exit(-1);
		}else{
			System.exit(0);
		}	
	}
}

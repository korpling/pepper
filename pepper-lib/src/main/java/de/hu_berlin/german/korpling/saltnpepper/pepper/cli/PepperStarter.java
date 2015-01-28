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

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil.PepperJobReporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.PepperConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.impl.PepperOSGiConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;

public class PepperStarter {
	/**
	 * A logger for logging messages.
	 */
	private static final Logger logger = LoggerFactory.getLogger(PepperStarter.class);

	/**
	 * Initializes an instance of PepperStarter without a
	 * {@link PepperConnector}. Make sure to call method
	 * {@link #setPepper(PepperConnector)} right after calling the constructor.
	 */
	public PepperStarter() {
	}

	/**
	 * Initializes an instance of PepperStarter.
	 * 
	 * @param pepperConnector
	 *            a {@link PepperConnector} object via which the
	 *            {@link PepperStarter} communicates with a {@link Pepper}
	 *            instance
	 */
	public PepperStarter(PepperConnector pepperConnector) {
		setPepper(pepperConnector);
	}

	/**
	 * A reference to Pepper via a {@link PepperConnector}
	 */
	private PepperConnector pepper = null;

	/**
	 * @return a reference to Pepper via a {@link PepperConnector}
	 */
	public PepperConnector getPepper() {
		return pepper;
	}

	/**
	 * Sets a reference to Pepper via a {@link PepperConnector}
	 */
	public void setPepper(PepperConnector pepper) {
		this.pepper = pepper;
		if (!getPepper().isInitialized()){
			getPepper().init();
		}
	}

	public enum COMMAND {
		LIST_ALL("list", "l", null, "A table with information about all available Pepper modules."),
		//
		LIST("list", "l", "module name", "A table with information about the passed Pepper module."),
		//
		CONF("conf", "co", null, "Shows the configuration for current Pepper instance."),
		//
		HELP("help", "h", null, "Prints this help."),
		//
		SELFTEST("self-test", "st", null, "Tests if the Pepper framework is in runnable mode or if any problems are detected, either in Pepper itself or in any registered Pepper module."),
		//
		EXIT("exit", "e", null, "Exits Pepper."),
		//
		CONVERT("convert", "c", "workflow file", "Loads the passed 'workflow-file' and starts the conversion."),
		//
		OSGI("osgi", "o", null, "Opens a console to access the underlying OSGi environment, if OSGi is used."), INSTALL_START("install_start", "is", "module path", "Installs the Pepper module located at 'module path' and starts it."),
		// UPDATE("update", "up", "module path",
		// "updates a Pepper module with the module located at 'module path' and starts it."),
		REMOVE("remove", "re", "bundle name", "Removes all Pepper modules, being contained in the budnle with name 'bundle name'. To find out the bundle name open the osgi console and list all bundles. "),
		//
		START_OSGI("start-osgi", "start", null, "Starts the OSGi environment (the plugin system of Pepper)."),
		//
		STOP_OSGI("stop-osgi", "stop", null, "Stops the OSGi environment (the plugin system of Pepper)."), CLEAN("clean", "cl", null, "Cleans the current Pepper instance and especially removes the OSGi workspace."),
		//
		DEBUG("debug", "d", null, "Switches on/off the debug output.");

		private String name = null;
		private String abbreviation = null;
		private String description = null;
		private String parameters = null;

		private COMMAND(String name, String abbreviation, String parameters, String description) {
			this.name = name;
			this.abbreviation = abbreviation;
			this.parameters = parameters;
			this.description = description;
		}

		public String getName() {
			return (name);
		}

		public String getAbbreviation() {
			return (abbreviation);
		}

		public String getParameters() {
			return (parameters);
		}

		public String getDescription() {
			return (description);
		}
	}

	/**
	 * Help to all commands for Pepper
	 * 
	 * @return
	 */
	public String help() {
		StringBuilder retVal = new StringBuilder();
		String format = "| %1$-20s | %2$-5s | %3$-15s | %4$-70s |\n";
		String line = "+----------------------+-------+-----------------+------------------------------------------------------------------------+\n";
		retVal.append(line);
		retVal.append(String.format(format, "command", "short", "parameters", "description"));
		retVal.append(line);
		for (COMMAND command : COMMAND.values()) {
			retVal.append(String.format(format, command.getName(), command.getAbbreviation(), (command.getParameters() == null) ? " -- " : command.getParameters(), command.getDescription()));
		}
		retVal.append(line);
		return (retVal.toString());
	}

	/**
	 * Returns a String containing a table with information about all available
	 * Pepper modules.
	 * 
	 * @return
	 */
	// @Command(description="A table with information about all available Pepper modules.")
	public String list() {
		StringBuilder retVal = new StringBuilder();

		Collection<PepperModuleDesc> moduleDescs = null;
		try {
			moduleDescs = getPepper().getRegisteredModules();
		} catch (Exception e) {
			if (isDebug) {
				e.printStackTrace();
			}
			retVal.append("Cannot not display any Pepper module. Calling " + COMMAND.START_OSGI.getName() + " might solve the problem. ");
			return (retVal.toString());
		}
		retVal.append(PepperUtil.reportModuleList(moduleDescs));
		return (retVal.toString());
	}

	/**
	 * Returns a String containing a table with information about the passed
	 * Pepper module.
	 * 
	 * @return
	 */
	// @Command(description="A table with information about the passed Pepper module.")
	public String list(String moduleName) {
		StringBuilder retVal = new StringBuilder();
		PepperModuleDesc moduleDesc = null;
		Collection<PepperModuleDesc> moduleDescs = null;
		try {
			moduleDescs = getPepper().getRegisteredModules();
		} catch (Exception e) {
			if (isDebug) {
				e.printStackTrace();
			}
			retVal.append("Cannot not display any Pepper module.");
			return (retVal.toString());
		}
		if ((moduleName != null) && (moduleDescs != null) && (moduleDescs.size() > 0)) {
			for (PepperModuleDesc desc : moduleDescs) {
				if (moduleName.equals(desc.getName())) {
					moduleDesc = desc;
					break;
				}
			}
		}
		if (moduleDesc != null) {
			retVal.append(moduleDesc.getName());
			retVal.append(", ");
			retVal.append(moduleDesc.getVersion());
			retVal.append("\n");
			retVal.append("supplier:");
			retVal.append(moduleDesc.getSupplierContact());
			retVal.append("\n");
			retVal.append("-------------------------------------------------------------------------\n");
			retVal.append((moduleDesc.getDesc() == null) ? "- no description available -" : moduleDesc.getDesc());
			retVal.append("\n");
		} else {
			retVal.append("- no Pepper module was found for given name '" + moduleName + "' -");
		}
		return (retVal.toString());
	}

	/**
	 * Starts the OSGi environment.
	 */
	public String start_osgi() {
		if (getPepper() instanceof PepperOSGiConnector) {
			try {
				getPepper().init();
			} catch (Exception e) {
				if (isDebug) {
					e.printStackTrace();
				}
				return ("Cannot start OSGi, because of a nested exception: " + e.getMessage());
			}
			return ("started OSGi");
		} else {
			return ("Cannot start OSGi, since Pepper is not running in OSGi mode. ");
		}
	}

	/**
	 * Stops the OSGi environment.
	 */
	public String stop_osgi() {
		if (getPepper() instanceof PepperOSGiConnector) {
			try {
				((PepperOSGiConnector) getPepper()).stopOSGi();
			} catch (Exception e) {
				if (isDebug) {
					e.printStackTrace();
				}
				return ("Cannot stop OSGi, because of a nested exception: " + e.getMessage());
			}
			return ("stoped OSGi");
		} else {
			return ("Cannot stop OSGi, since Pepper is not running in OSGi mode. ");
		}
	}

	/**
	 * Cleans the current Pepper instance and especially removes the OSGi
	 * workspace, to set up a new one.
	 * 
	 * @return
	 */
	public String clean() {
		stop_osgi();
		String retVal = "";
		try {
			FileUtils.deleteDirectory(getPepper().getConfiguration().getTempPath());
			retVal = "Cleaned up Pepper instance, please call " + COMMAND.START_OSGI.getName() + " to make Pepper ready to run again.";
		} catch (IOException e) {
			retVal = "Cannot clean Pepper instance, because of " + e.getMessage();
			if (isDebug) {
				e.printStackTrace();
			}
		}

		return (retVal);
	}

	/** Determines if debug mode is on or off **/
	private Boolean isDebug = false;

	/**
	 * Switches on/off the debug mode.
	 * 
	 * @return
	 */
	public String debug() {
		isDebug = !isDebug;
		if (isDebug) {
			// enableDebug();
			return ("Debug mode is on.");
		} else {
			return ("Debug mode is off.");
		}
	}

	/**
	 * Opens the OSGi console via an {@link OSGiConsole} object and delegates to
	 * it.
	 */
	public String osgi() {
		if (getPepper() instanceof PepperOSGiConnector) {
			OSGiConsole console = new OSGiConsole((PepperOSGiConnector) getPepper(), PROMPT);
			console.start(input, output);
			return ("exit OSGi");
		} else {
			return ("No OSGi console available, since Pepper is not running in OSGi mode. ");
		}
	}

	/**
	 * Loads the passed workflow description file and starts the conversion.
	 * 
	 * @param workFlowFile
	 */
	public void convert(String workFlowFile) {
		if (	(workFlowFile== null)||
				(workFlowFile.isEmpty())){
			//if no parameter is given open convert wizzard
			ConvertWizzardConsole console = new ConvertWizzardConsole(PROMPT);
			console.setPepper(getPepper());
			console.start(input, output);
		}
		else{
			URI workFlowUri = URI.createFileURI(workFlowFile);
			String jobId = pepper.createJob();
	
			PepperJob pepperJob = pepper.getJob(jobId);
			pepperJob.load(workFlowUri);
			PepperJobReporter observer = new PepperJobReporter(pepperJob);
			observer.start();
			try {
				pepperJob.convert();
			} finally {
				observer.setStop(true);
			}
	
			pepper.removeJob(jobId);
		}
	}

	/**
	 * Installs and starts a new Pepper module(s).
	 */
	public String installAndStart(List<String> params) {
		if (getPepper() instanceof PepperOSGiConnector) {
			OSGiConsole console = new OSGiConsole((PepperOSGiConnector) getPepper(), PROMPT);
			console.installAndStart(params, output);
			return ("launched Pepper module");
		} else {
			return ("No OSGi console availablem, since Pepper is not running in OSGi mode. ");
		}
	}

	/**
	 * Updates a Pepper module(s).
	 */
	public String update(List<String> params) {
		if (getPepper() instanceof PepperOSGiConnector) {
			OSGiConsole console = new OSGiConsole((PepperOSGiConnector) getPepper(), PROMPT);
			console.installAndStart(params, output);
			return ("Updated Pepper module.");
		} else {
			return ("No OSGi console availablem, since Pepper is not running in OSGi mode. ");
		}
	}

	/**
	 * Removes an existing Pepper module(s).
	 */
	public String remove(List<String> params) {
		if (getPepper() instanceof PepperOSGiConnector) {
			OSGiConsole console = new OSGiConsole((PepperOSGiConnector) getPepper(), PROMPT);
			console.remove(params, output);
			return ("");
		} else {
			return ("No OSGi console availablem, since Pepper is not running in OSGi mode. ");
		}
	}

	/**
	 * Calls {@link PepperModule#isReadyToStart()} for all Pepper modules.
	 * 
	 * @return
	 */
	// @Command(description="Tests if the Pepper framework is in runnable mode or if any problems are detected, either in Pepper itself or in any registered Pepper module.")
	public String selfTest() {
		StringBuilder retVal = new StringBuilder();
		Collection<String> problems = getPepper().selfTest();
		if (problems.size() == 0)
			retVal.append("- no problems detected -");
		else {
			retVal.append("following problems have been found:");
			for (String problem : problems) {
				retVal.append("\t" + problem);
			}
		}
		return (retVal.toString());
	}

	/**
	 * @return a String containing all Pepper configurations formatted as table
	 */
	public String conf() {
		StringBuilder retVal = new StringBuilder();
		String format = "| %1$-40s | %2$-50s |\n";
		String line = "+------------------------------------------+----------------------------------------------------+\n";
		retVal.append(line);
		retVal.append(String.format(format, "name", "value"));
		retVal.append(line);
		for (Object propName : getPepper().getConfiguration().keySet()) {
			retVal.append(String.format(format, propName, getPepper().getConfiguration().getProperty(propName.toString())));
		}
		retVal.append(line);
		return (retVal.toString());
	}

	public static final String PROMPT = "pepper";
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private PrintStream output = System.out;

	/**
	 * Starts the interactive console of Pepper.
	 */
	public void runInteractive() {
		boolean exit = false;
		String userInput = null;

		output.println("Welcome to Pepper, type 'help' for help.");
		while (!exit) {
			try {
				output.print(PROMPT + ">");
				userInput = input.readLine();
			} catch (IOException ioe) {
				output.println("Cannot read command, type in 'help' for help.");
			}
			userInput = userInput.trim();
			String[] parts = userInput.split(" ");
			String command = parts[0];
			List<String> params = new Vector<String>();
			int i = 0;
			for (String part : parts) {
				if (i > 0) {
					params.add(part);
				}
				i++;
			}
			if ((COMMAND.HELP.getName().equalsIgnoreCase(command)) || (COMMAND.HELP.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(help());
			} else if ((params.size() == 0) && ((COMMAND.LIST.getName().equalsIgnoreCase(command)) || (COMMAND.LIST.getAbbreviation().equalsIgnoreCase(command)))) {
				output.println(list());
			} else if (((params.size() > 0)) && ((COMMAND.LIST.getName().equalsIgnoreCase(command)) || (COMMAND.LIST.getAbbreviation().equalsIgnoreCase(command)))) {
				if (params.size() == 1) {
					output.println(list(params.get(0)));
				} else {
					output.println("Please pass exactly one module name.");
				}
			} else if ((COMMAND.CONF.getName().equalsIgnoreCase(command)) || (COMMAND.CONF.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(conf());
			} else if ((COMMAND.SELFTEST.getName().equalsIgnoreCase(command)) || (COMMAND.SELFTEST.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(selfTest());
			} else if ((COMMAND.EXIT.getName().equalsIgnoreCase(command)) || (COMMAND.EXIT.getAbbreviation().equalsIgnoreCase(command))) {
				break;
			} else if ((COMMAND.CONVERT.getName().equalsIgnoreCase(command)) || (COMMAND.CONVERT.getAbbreviation().equalsIgnoreCase(command))) {
					Long timestamp = System.currentTimeMillis();
					try {
						if (params.size() == 1) {
							convert(params.get(0));
						}else{
							convert(null);
						}
						timestamp = System.currentTimeMillis() - timestamp;
						output.println("conversion ended successfully, required time: " + (timestamp / 1000) + " s");
					} catch (Exception e) {
						timestamp = System.currentTimeMillis() - timestamp;
						output.println("CONVERSION ENDED WITH ERRORS, REQUIRED TIME: " + (timestamp / 1000) + " s");
						output.println(PepperUtil.breakString("   ", e.getMessage() + " (" + e.getClass().getSimpleName() + ")"));
						output.println("full stack trace:");
						e.printStackTrace(output);
					}
			} else if ((COMMAND.OSGI.getName().equalsIgnoreCase(command)) || (COMMAND.OSGI.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(osgi());
			} else if ((COMMAND.INSTALL_START.getName().equalsIgnoreCase(command)) || (COMMAND.INSTALL_START.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(installAndStart(params));
				// }else if(
				// (COMMAND.UPDATE.getName().equalsIgnoreCase(command))||
				// (COMMAND.UPDATE.getAbbreviation().equalsIgnoreCase(command))){
				// output.println(update(params));
			} else if ((COMMAND.REMOVE.getName().equalsIgnoreCase(command)) || (COMMAND.REMOVE.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(remove(params));
			} else if ((COMMAND.START_OSGI.getName().equalsIgnoreCase(command)) || (COMMAND.START_OSGI.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(start_osgi());
			} else if ((COMMAND.STOP_OSGI.getName().equalsIgnoreCase(command)) || (COMMAND.STOP_OSGI.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(stop_osgi());
			} else if ((COMMAND.CLEAN.getName().equalsIgnoreCase(command)) || (COMMAND.CLEAN.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(clean());
			} else if ((COMMAND.DEBUG.getName().equalsIgnoreCase(command)) || (COMMAND.DEBUG.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(debug());
			} else {
				output.println("Type 'help' for help.");
			}
		}
	}

	// REMOVED THIS BECAUSE OF DEPENDENCY TO CONCRETE LOGGING FRAMEWORK IS
	// CONTRA SLF4J IDEA.
	// /**
	// * Changes the logger level to debug in case of logback is used and root
	// logger is
	// * of type {@link ch.qos.logback.classic.Logger}.
	// */
	// public static void enableDebug(){
	// Logger rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	// if (rootLogger instanceof ch.qos.logback.classic.Logger){
	// ((ch.qos.logback.classic.Logger)rootLogger).setLevel(Level.DEBUG);
	// }
	// }

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Long timestamp = null;
		PepperStarter starter = null;
		PepperConnector pepper = null;
		boolean endedWithErrors = false;

		try {
			PepperStarterConfiguration pepperProps = new PepperStarterConfiguration();
			pepperProps.load();

			String eMail = pepperProps.getPepperEMail();
			String hp = pepperProps.getPepperHomepage();

			starter = new PepperStarter();

			if ((args.length > 0) && (args[0].equalsIgnoreCase(COMMAND.DEBUG.toString()))) {
				starter.debug();
			}

			logger.info(PepperUtil.getHello(eMail, hp));
			
			pepper = new PepperOSGiConnector();
			pepper.setConfiguration(pepperProps);
			boolean runInteractive= false;
			try{
				starter.setPepper(pepper);
			}catch (Exception e){
				logger.info("An error occured, while starting Pepper. To get more information on that, please check the log file, which is by default located at 'PEPPER_HOME/pepper_out.log'. You now can exit Pepper or try to find out more about that exception using the Pepper console. ", e);	
				runInteractive= true;
			}
			if (	(args.length == 0)||
					(runInteractive)){
				// run interactive console
				try {
					starter.runInteractive();
				} catch (Exception e) {
				}
			} else if ((COMMAND.HELP.getName().equalsIgnoreCase(args[0]) || (COMMAND.HELP.getAbbreviation().equalsIgnoreCase(args[0])))) {
				// print help
				logger.info(starter.help());

			} else if ((COMMAND.LIST.getName().equalsIgnoreCase(args[0]) || (COMMAND.LIST.getAbbreviation().equalsIgnoreCase(args[0])))) {
				if (args.length == 1) {
					logger.info(starter.list());
				} else {
					logger.info(starter.list(args[1]));
				}
			} else if ((COMMAND.SELFTEST.getName().equalsIgnoreCase(args[0]) || (COMMAND.SELFTEST.getAbbreviation().equalsIgnoreCase(args[0])))) {
				logger.info(starter.selfTest());
			} else if (("-p".equalsIgnoreCase(args[0])) || ("-w".equalsIgnoreCase(args[0])) || (args[0] != null)) {
				String workFlowFile = null;
				if (("-p".equalsIgnoreCase(args[0])) || ("-w".equalsIgnoreCase(args[0]))) {
					if (args[1] == null) {
						logger.error("Cannot start conversion, since no workflow description file is given.");
						endedWithErrors = true;
					} else {
						workFlowFile = args[1];
					}
				} else {
					workFlowFile = args[0];
				}
				try{
				timestamp = System.currentTimeMillis();
				if (logger.isDebugEnabled()) {
					for (Object key : pepperProps.keySet()) {
						logger.debug(String.format("%-40s%-16s", key + ":", pepperProps.get(key)));
					}
				}
				logger.debug(pepper.getRegisteredModulesAsString());
				workFlowFile = workFlowFile.replace("\\", "/");

				starter.convert(workFlowFile);

				timestamp = System.currentTimeMillis() - timestamp;
				logger.info("CONVERSION ENDED SUCCESSFULLY, REQUIRED TIME: " + timestamp + " ms");
				}catch (Exception e){
					timestamp = System.currentTimeMillis() - timestamp;
					endedWithErrors = true;
					logger.info("CONVERSION ENDED WITH ERRORS, REQUIRED TIME:  " + timestamp + " ms");
					logger.info(PepperUtil.breakString("   ", e.getMessage() + " (" + e.getClass().getSimpleName() + ")"));
					throw e;
				}
			}
		} catch (Exception e) {
			logger.info("An error occured, to get more information on that, please check the log file, which is by default located at 'PEPPER_HOME/pepper_out.log'. ");
			logger.error(" ", e);
		} finally {
			logger.info("************************************************************************************************************************\n");
		}
		if (endedWithErrors) {
			System.exit(-1);
		} else {
			System.exit(0);
		}
	}
}

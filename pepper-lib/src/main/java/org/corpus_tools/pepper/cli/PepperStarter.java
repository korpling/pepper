/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
package org.corpus_tools.pepper.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperJob;
import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.common.PepperUtil.PepperJobReporter;
import org.corpus_tools.pepper.connectors.PepperConnector;
import org.corpus_tools.pepper.connectors.impl.PepperOSGiConnector;
import org.corpus_tools.pepper.exceptions.PepperException;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperty;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import ch.qos.logback.classic.LoggerContext;

/**
 * The main class to start Pepper from the Console.
 * 
 * @author Florian
 *
 */
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
		if (!getPepper().isInitialized()) {
			getPepper().init();
		}
	}

	/** Configuration for {@link PepperStarter} **/
	private PepperStarterConfiguration pepperConf = null;

	/**
	 * @return configuration for {@link PepperStarter}
	 */
	public PepperStarterConfiguration getPepperConfiguration() {
		return pepperConf;
	}

	/**
	 * 
	 * @param pepperConf
	 *            configuration for {@link PepperStarter}
	 */
	public void setPepperConfiguration(PepperStarterConfiguration pepperConf) {
		this.pepperConf = pepperConf;
	}

	public enum COMMAND {
		VERSION("version", "v", "no arguments", "prints pepper version"),
		//
		PRINT_DEPS("dependencies", "deps", "Bundle id or GROUP_ID::ARTIFACT_ID::VERSION::MAVEN_REPOSITORY_URL or plugin names split by space; parameter all prints dependencies for all plugins", "displays all dependencies of the specified component"),
		//
		UPDATE("update", "u", "module name or location", "Updates the pepper module(s). Parameter \"all\" updates all modules listed in modules.xml."),
		//
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
		CONVERT("convert", "c", "workflow file", "If no workflow file is passed, Pepper opens a conversion wizard, which help you through the definition of a workflow proecess. If a 'worklow file' is passed, this file is load and the described workflow will be started."),
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
		DEBUG("debug", "d", null, "Switches on/off the debug output."), REPEAT("repeat", "r", null, "Repeats the last command.");

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
		String retVal = null;

		Integer[] length = new Integer[4];
		if (getPepperConfiguration().getConsoleWidth() == CONSOLE_WIDTH_80) {
			length[0] = 7;
			length[1] = 5;
			length[2] = 10;
			length[3] = 48;
		} else {
			length[0] = 20;
			length[1] = 5;
			length[2] = 15;
			length[3] = 70;
		}
		String[][] map = new String[COMMAND.values().length + 1][4];
		map[0][0] = "command";
		map[0][1] = "short";
		map[0][2] = "parameters";
		map[0][3] = "description";
		int i = 0;
		for (COMMAND command : COMMAND.values()) {
			i++;
			map[i][0] = command.getName();
			map[i][1] = command.getAbbreviation();
			map[i][2] = (command.getParameters() == null) ? " -- " : command.getParameters();
			map[i][3] = command.getDescription();
		}

		retVal = PepperUtil.createTable(length, map, true, true, true);
		return (retVal);
	}

	/** A map storing all pepper modules and a corresponding number as key. **/
	private Map<Integer, PepperModuleDesc> number2module = null;

	/**
	 * Returns a String containing a table with information about all available
	 * Pepper modules.
	 * 
	 * @return
	 */
	public String list() {
		StringBuilder retVal = new StringBuilder();

		List<PepperModuleDesc> moduleDescs = null;
		try {
			moduleDescs = new ArrayList<PepperModuleDesc>(getPepper().getRegisteredModules());
		} catch (Exception e) {
			if (isDebug) {
				e.printStackTrace();
			}
			retVal.append("Cannot display any Pepper module. Call " + COMMAND.START_OSGI.getName() + " might solve the problem. ");
			return (retVal.toString());
		}
		Collections.sort(moduleDescs);
		number2module = new HashMap<Integer, PepperModuleDesc>(moduleDescs.size());
		retVal.append(PepperUtil.reportModuleList(getPepperConfiguration().getConsoleWidth(), moduleDescs, number2module));
		retVal.append("To get more information on a particular module enter 'list No' or 'list module-name'. ");
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
			retVal.append("Cannot display any Pepper module.");
			return (retVal.toString());
		}
		Integer numOfModule = null;
		try {
			numOfModule = Integer.parseInt(moduleName);
			if (number2module != null) {
				moduleDesc = number2module.get(numOfModule);
			}
		} catch (Exception e) {
			// do nothing
			;
		}
		// try to read module desc by name
		if ((moduleDesc == null) && (moduleName != null) && (moduleDescs != null) && (moduleDescs.size() > 0)) {
			for (PepperModuleDesc desc : moduleDescs) {
				if (moduleName.equalsIgnoreCase(desc.getName())) {
					moduleDesc = desc;
					break;
				}
			}
		}
		if (moduleDesc != null) {

			Integer[] length = new Integer[2];
			if (CONSOLE_WIDTH_80 == getPepperConfiguration().getConsoleWidth()) {
				length[0] = 14;
				length[1] = 60;
			} else {
				length[0] = 14;
				length[1] = 100;
			}

			int numOfEntries = 5;
			if (moduleDesc.getProperties() != null) {
				numOfEntries++;
				numOfEntries++;
				numOfEntries++;
				numOfEntries = numOfEntries + moduleDesc.getProperties().getPropertyDesctriptions().size();
			}

			String[][] map = new String[numOfEntries][2];
			map[0][0] = "name:";
			map[0][1] = moduleDesc.getName();
			map[1][0] = "version:";
			map[1][1] = moduleDesc.getVersion();
			map[2][0] = "supplier:";
			map[2][1] = moduleDesc.getSupplierContact() == null ? "" : moduleDesc.getSupplierContact().toString();
			map[3][0] = "website:";
			map[3][1] = moduleDesc.getSupplierHomepage() == null ? "" : moduleDesc.getSupplierHomepage().toString();
			map[4][0] = "description:";
			map[4][1] = moduleDesc.getDesc();

			if (moduleDesc.getProperties() != null) {
				map[5][0] = "--";
				map[6][1] = "customization properties";
				map[7][0] = "--";
				int i = 8;
				for (PepperModuleProperty<?> prop : moduleDesc.getProperties().getPropertyDesctriptions()) {
					map[i][0] = prop.getName();
					map[i][1] = prop.getDescription();
					i++;
				}
			}
			retVal.append(PepperUtil.createTable(length, map, false, true, true));

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
	 * @param workflowFile
	 */
	public void convert(String workFlowFile) {
		PepperJob pepperJob = null;
		String jobId = null;
		if ((workFlowFile == null) || (workFlowFile.isEmpty())) {
			// if no parameter is given open convert wizard
			ConvertWizardConsole console = new ConvertWizardConsole(PROMPT);
			console.setPepper(getPepper());
			console.isDebug = this.isDebug;
			pepperJob = console.start(input, output);
			if (pepperJob != null) {
				jobId = pepperJob.getId();
			}
		} else {
			if ("water".equalsIgnoreCase(workFlowFile)) {
				String msg = "###############___\n##############)===(\n##############)===(\n##############|H##|\n##############|H##|\n##############|H##|\n#############/=====\\\n############/#######\\\n###########/=========\\\n##########:HHHHHHHH##H:\n##########|HHHHHHHH##H|\n##########|HHHHHHHH##H|\n##########|HHHHHHHH##H|\n##########|===========|\n##########|###########|\n##########|#\\\\########|\n##########|OOO#In#####|#_________\n##########|#OO#vino###||#########|\n##########|##O#veritas|%#########%\n##########|###########|#\\#######/\n##########|===========|##`.###.'\n##########|HHHHHHHH##H|####\\#/\n##########|HHHHHHHH##H|####(#)\n##########|HHHHHHHH##H|####.|.\n###########~~~~~~~~~~~###~~~^~~~\n Cheers!\n";
				output.println(msg.replace("#", " "));
			} else {
				URI workFlowUri = URI.createFileURI(workFlowFile);
				jobId = pepper.createJob();

				pepperJob = pepper.getJob(jobId);
				pepperJob.load(workFlowUri);
			}
		}
		if (pepperJob != null) {
			PepperJobReporter observer = new PepperJobReporter(pepperJob, getPepper().getConfiguration().getReportInterval());
			observer.start();
			Long timestamp = System.currentTimeMillis();
			try {
				pepperJob.convert();
				timestamp = System.currentTimeMillis() - timestamp;
				output.println("conversion ended successfully, required time: " + DurationFormatUtils.formatDurationHMS(timestamp) + " s");
			} catch (Exception e) {
				timestamp = System.currentTimeMillis() - timestamp;
				output.println("CONVERSION ENDED WITH ERRORS, REQUIRED TIME: " + DurationFormatUtils.formatDurationHMS(timestamp) + " s");
				output.println(PepperUtil.breakString("   ", e.getMessage() + " (" + e.getClass().getSimpleName() + ")"));
				output.println("full stack trace:");
				e.printStackTrace(output);
			} finally {
				observer.setStop(true);
				pepper.removeJob(jobId);
			}
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
			return ("No OSGi console available, since Pepper is not running in OSGi mode. ");
		}
	}

	/**
	 * Updates a Pepper module(s).
	 */
	// public String update(List<String> params) {
	// if (getPepper() instanceof PepperOSGiConnector) {
	// OSGiConsole console = new OSGiConsole((PepperOSGiConnector) getPepper(),
	// PROMPT);
	// console.installAndStart(params, output);
	// return ("Updated Pepper module.");
	// } else {
	// return
	// ("No OSGi console available, since Pepper is not running in OSGi mode. ");
	// }
	// }

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
		if (problems.size() == 0) {
			retVal.append("- no problems detected -");
		} else {
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

	/**
	 * this map contains all registered modules with groupId, artifactId and
	 * maven repository. It is filled in the first call of update(List..params)
	 */
	Map<String, Pair<String, String>> moduleTable;

	/**
	 * this method is called by command "update" which triggers the update
	 * process of pepper modules in PepperOSGiConnector depending on the
	 * command's parameters
	 * 
	 * @param command
	 * @return
	 */
	private String update(List<String> params) {
		if (params.size() == 0) {
			return "Please specify a module you want to update.";
		}
		StringBuilder retVal = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		String indent = "\t";

		if (logger.isDebugEnabled()) {
			StringBuilder paramOut = new StringBuilder();
			paramOut.append("update parameters:");
			for (String param : params) {
				paramOut.append(newLine).append(param);
			}
			logger.debug(paramOut.toString());
		}

		boolean isSnapshot = params.size() > 0 && "snapshot".equalsIgnoreCase(params.get(0)) || params.size() > 1 && "snapshot".equalsIgnoreCase(params.get(1));
		boolean ignoreVersion = params.size() > 0 && "iv".equalsIgnoreCase(params.get(0)) || params.size() > 1 && "iv".equalsIgnoreCase(params.get(1));
		PepperOSGiConnector pepperConnector = (PepperOSGiConnector) getPepper();
		try {
			moduleTable = getModuleTable();

			if ("all".equalsIgnoreCase(params.get(0)) || (isSnapshot && !ignoreVersion || ignoreVersion && !isSnapshot) && params.size() > 1 && "all".equalsIgnoreCase(params.get(1)) || isSnapshot && ignoreVersion && params.size() > 2 && "all".equalsIgnoreCase(params.get(2))) {
				List<String> lines = new ArrayList<String>();
				for (String s : moduleTable.keySet()) {
					if (pepperConnector.update(moduleTable.get(s).getLeft(), s, moduleTable.get(s).getRight(), isSnapshot, ignoreVersion)) {
						lines.add(s.concat(" successfully updated."));
					} else {
						lines.add(s.concat(" NOT updated."));
					}
				}
				Collections.<String> sort(lines);
				retVal.append(newLine);
				for (String line : lines) {
					retVal.append(line).append(newLine);
				}
			} else {
				String s = null;
				for (int i = isSnapshot ? (ignoreVersion ? 2 : 1) : (ignoreVersion ? 1 : 0); i < params.size(); i++) {
					s = params.get(i);
					if (moduleTable.keySet().contains(s)) {
						if (pepperConnector.update(moduleTable.get(s).getLeft(), s, moduleTable.get(s).getRight(), isSnapshot, ignoreVersion)) {
							retVal.append("Successfully updated ").append(s).append(" from " + moduleTable.get(s)).append(newLine);
						} else {
							retVal.append("No update was performed for ").append(s).append(".");
						}
					} else if ("config".equals(s)) {
						retVal.append(newLine).append(indent).append("update configuration for pepper modules:").append(newLine).append(newLine);

						for (String module : moduleTable.keySet()) {
							retVal.append(indent).append(module).append(moduleTable.get(module)).append(newLine);
						}
						retVal.append(newLine);
						retVal.append(indent).append("to add/modify a configuration use the following command (update will be executed, too!):").append(newLine).append(newLine).append(indent).append("update GROUP_ID::ARTIFACT_ID::REPOSITORY_URL").append(newLine);
						retVal.append(newLine).append(indent).append("GROUP_ID: the groupId of the pepper module");
						retVal.append(newLine).append(indent).append("ARTIFACT_ID: the artifactId of the pepper module, usually \"pepperModules-___Modules\"");
						retVal.append(newLine).append(indent).append("REPOSITORY_URL: the url of the maven repository that contains the module").append(newLine);
					} else if (s.contains("::")) {
						String[] args = s.split("::");
						if (pepperConnector.update(args[0], args[1], args[2], isSnapshot, ignoreVersion)) {
							retVal.append("Successfully updated ").append(args[0]).append(".").append(args[1]).append(" from maven repository ").append(args[2]).append(".").append(newLine);
							retVal.append(write2ConfigFile(args[0], args[1], args[2]) ? "Configuration written back to modules.xml. Module is now available for direct update calls (e.g. \"update " + args[1] + "\")." : "WARNING: An Error occured while writing groupId, artifactId and repository path to modules.xml.");
							moduleTable.put(args[1], Pair.of(args[0], args[2]));
						} else {
							retVal.append("No update was performed for ").append(args[1]).append(newLine);
						}
					} else if (s.matches("file://.*") || s.matches("https?://.*")) {
						try {
							pepperConnector.installAndCopy(java.net.URI.create(s)).start();
						} catch (BundleException e) {
							logger.debug(s + " caused: " + e.getMessage());
							return "File not installed due to a BundleException";
						}
						return "File installed. No dependency resolution in this mode.";
					} else if ("blacklist".equals(s) || "bl".equals(s)) {
						retVal.append(pepperConnector.getBlacklist());
					} else if ("iv".equalsIgnoreCase(s)) {
						ignoreVersion = true;
					} else if ("snapshot".equalsIgnoreCase(s)) {
						isSnapshot = true;
					} else if ("--help".equalsIgnoreCase(s)) {
						retVal.append(newLine).append(indent).append("update [snapshot] [iv] MODULES_NAME").append(newLine).append(indent).append("updates the specified pepper modules").append(newLine).append(indent).append("snapshot:\tIf the newest version is a snapshot, pepper chooses to install it.").append(newLine).append(indent).append("iv:\t\tIf the pepper modules are depending on another version of pepper, they would usually not be installed.").append(newLine).append(indent).append(indent).append(indent).append("By setting this flag you can override this behaviour.").append(newLine).append(newLine).append(indent).append("update [snapshot] [iv] all").append(newLine).append(indent).append("All pepper modules configured in modules.xml will be updated/installed.").append(newLine).append(newLine).append(indent).append("update config").append(newLine).append(indent).append("displays the configuration given in modules.xml.").append(newLine).append(newLine).append(indent).append("update GROUP_ID::ARTIFACT_ID::REPOSITORY_URL").append(newLine).append(indent).append("adds/modifies a configuration in modules.xml AND starts the update process.").append(newLine).append(newLine).append(indent).append("update URL").append(newLine).append(indent).append("installs a file by its URL. Dependencies will not be resolved.").append(newLine);
					} else {
						retVal.append(indent).append(s).append(" is not a known module.").append(newLine).append(indent).append("For more information type \"u config\"").append(newLine);
					}
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			retVal.append("An error occured during the update.");
		}
		return retVal.toString();
	}

	/**
	 * This String contains the path to the modules.xml file, which provides
	 * Information about the pepperModules to be updated / installed.
	 */
	private static final String MODULES_XML_PATH = "./conf/modules.xml";

	private Map<String, Pair<String, String>> getModuleTable() throws ParserConfigurationException, SAXException, IOException {
		if (this.moduleTable != null) {
			return moduleTable;
		}
		HashMap<String, Pair<String, String>> table = new HashMap<String, Pair<String, String>>();
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		try {
			saxParser.parse(MODULES_XML_PATH, new ModuleTableReader(table));
		} catch (Exception e) {
			logger.debug("Could not parse modules.xml", e);
		}
		return table;
	}

	/**
	 * This method writes a module configuration of GroupId, ArtifactId and
	 * Maven repository back to the modules.xml file
	 */
	private boolean write2ConfigFile(String groupId, String artifactId, String repository) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			boolean changes = false;
			File configFile = new File(MODULES_XML_PATH);
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(configFile);
			NodeList configuredModules = doc.getElementsByTagName(ModuleTableReader.TAG_ARTIFACTID);
			if (configuredModules.getLength() == 0) {
				return false;
			}
			/* check, if the module is already in the modules.xml file */
			Node item = configuredModules.item(0);
			int j = 0;
			while (j + 1 < configuredModules.getLength() && !artifactId.equals(item.getTextContent())) {
				item = configuredModules.item(++j);
			}
			if (artifactId.equals(item.getTextContent())) {// already contained
															// -> edit
				Node itemGroupId = null;
				Node itemRepo = null;
				Node node = null;
				for (int i = 0; i < item.getParentNode().getChildNodes().getLength() && (itemGroupId == null || itemRepo == null); i++) {
					node = item.getParentNode().getChildNodes().item(i);
					if (ModuleTableReader.TAG_GROUPID.equals(node.getLocalName())) {
						itemGroupId = node;
					}
					if (ModuleTableReader.TAG_REPO.equals(node.getLocalName())) {
						itemRepo = node;
					}
				}
				if (itemGroupId != null) {
					itemGroupId.setTextContent(groupId);
					changes = true;
				} else {
					if (!groupId.equals(doc.getElementsByTagName(ModuleTableReader.ATT_DEFAULTGROUPID).item(0).getTextContent())) {
						itemGroupId = doc.createElement(ModuleTableReader.TAG_GROUPID);
						itemGroupId.setTextContent(groupId);
						item.getParentNode().appendChild(itemGroupId);
						changes = true;
					}
				}
				if (itemRepo != null) {
					itemRepo.setTextContent(repository);
					changes = true;
				} else {
					if (!repository.equals(doc.getElementsByTagName(ModuleTableReader.ATT_DEFAULTREPO).item(0).getTextContent())) {
						itemRepo = doc.createElement(ModuleTableReader.TAG_REPO);
						itemRepo.setTextContent(repository);
						item.getParentNode().appendChild(itemRepo);
						changes = true;
					}
				}
				itemGroupId = null;
				itemRepo = null;
				node = null;
			} else {// not contained yet -> insert
				changes = true;
				Node listNode = doc.getElementsByTagName(ModuleTableReader.TAG_LIST).item(0);
				Node newModule = doc.createElement(ModuleTableReader.TAG_ITEM);
				Node groupIdNode = doc.createElement(ModuleTableReader.TAG_GROUPID);
				groupIdNode.appendChild(doc.createTextNode(groupId));
				Node artifactIdNode = doc.createElement(ModuleTableReader.TAG_ARTIFACTID);
				artifactIdNode.appendChild(doc.createTextNode(artifactId));
				Node repositoryNode = doc.createElement(ModuleTableReader.TAG_REPO);
				repositoryNode.appendChild(doc.createTextNode(repository));
				newModule.appendChild(groupIdNode);
				newModule.appendChild(artifactIdNode);
				newModule.appendChild(repositoryNode);
				listNode.appendChild(newModule);

				listNode = null;
				newModule = null;
				groupIdNode = null;
				artifactIdNode = null;
				repository = null;
			}

			if (changes) {
				// write back to file
				TransformerFactory trFactory = TransformerFactory.newInstance();
				// trFactory.setAttribute("indent-number", 2);
				Transformer transformer = trFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				DOMSource src = new DOMSource(doc);
				StreamResult result = new StreamResult(configFile);
				transformer.transform(src, result);

				trFactory = null;
				transformer = null;
				src = null;
				result = null;
			}

			docBuilder = null;
			doc = null;
			configuredModules = null;
			item = null;

		} catch (ParserConfigurationException | SAXException | IOException | FactoryConfigurationError | TransformerFactoryConfigurationError | TransformerException e) {
			logger.error("Could not read module table.");
			logger.trace(" ", e);
			return false;
		}
		return true;
	}

	/**
	 * This class is the call back handler for reading the modules.xml file,
	 * which provides Information about the pepperModules to be updated /
	 * installed.
	 * 
	 * @author klotzmaz
	 *
	 */
	private static class ModuleTableReader extends DefaultHandler2 {
		/**
		 * all read module names are stored here Map: artifactId --> (groupId,
		 * repository)
		 * */
		private Map<String, Pair<String, String>> listedModules;
		/** this string contains the last occurred artifactId */
		private String artifactId;
		/** this string contains the group id */
		private String groupId;
		/** this string contains the repository */
		private String repo;
		/** the name of the tag between the modules are listed */
		private static final String TAG_LIST = "pepperModulesList";
		/**
		 * the name of the tag in the modules.xml file, between which the
		 * modules' properties are listed
		 */
		private static final String TAG_ITEM = "pepperModules";
		/**
		 * the name of the tag in the modules.xml file, between which the
		 * modules' groupId is written
		 */
		private static final String TAG_GROUPID = "groupId";
		/**
		 * the name of the tag in the modules.xml file, between which the
		 * modules' name is written
		 */
		private static final String TAG_ARTIFACTID = "artifactId";
		/**
		 * the name of the tag in the modules.xml file, between which the
		 * modules' source is written
		 */
		private static final String TAG_REPO = "repository";
		/** the name of the attribute for the default repository */
		private static final String ATT_DEFAULTREPO = "defaultRepository";
		/** the name of the attribute for the default groupId */
		private static final String ATT_DEFAULTGROUPID = "defaultGroupId";
		/** contains the default groupId for modules where no groupId is defined */
		private String defaultGroupId;
		/**
		 * contains the default repository for modules where no repository is
		 * defined
		 */
		private String defaultRepository;
		/** is used to read the module name character by character */
		private StringBuilder chars;

		/** this boolean says, whether characters should be read or ignored */
		// private boolean openEyes;

		public ModuleTableReader(Map<String, Pair<String, String>> artifactIdUrlMap) {
			listedModules = artifactIdUrlMap;
			chars = new StringBuilder();
			groupId = null;
			artifactId = null;
			repo = null;
			// openEyes = false;
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			localName = qName.substring(qName.lastIndexOf(":") + 1);
			// openEyes = TAG_GROUPID.equals(localName) ||
			// TAG_ARTIFACTID.equals(localName) || TAG_REPO.equals(localName);
			if (TAG_LIST.equals(localName)) {
				defaultRepository = attributes.getValue(ATT_DEFAULTREPO);
				defaultGroupId = attributes.getValue(ATT_DEFAULTGROUPID);
			}
			chars.delete(0, chars.length());
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			for (int i = start; i < start + length /* && openEyes */; i++) {
				chars.append(ch[i]);
			}
			// openEyes = false;
		}

		@Override
		public void endElement(java.lang.String uri, String localName, String qName) throws SAXException {
			localName = qName.substring(qName.lastIndexOf(":") + 1);
			if (TAG_ARTIFACTID.equals(localName)) {
				artifactId = chars.toString();
				chars.delete(0, chars.length());
			} else if (TAG_GROUPID.equals(localName)) {
				groupId = chars.toString();
				chars.delete(0, chars.length());
			} else if (TAG_REPO.equals(localName)) {
				repo = chars.toString();
				chars.delete(0, chars.length());
			} else if (TAG_ITEM.equals(localName)) {
				groupId = groupId == null ? defaultGroupId : groupId;
				listedModules.put(artifactId, Pair.of(groupId, (repo == null || repo.isEmpty() ? defaultRepository : repo)));
				chars.delete(0, chars.length());
				groupId = null;
				artifactId = null;
				repo = null;
			}
		}
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
		String lastCommand = null;
		List<String> lastParams = null;
		String lastUserInput = null;

		output.println("Welcome to Pepper, type '" + COMMAND.HELP.getName() + "' for help or '" + COMMAND.CONVERT.getName() + "' to start a conversion.");
		while (!exit) {
			try {
				output.print(PROMPT + ">");
				userInput = input.readLine();
			} catch (IOException ioe) {
				output.println("Cannot read command, type in 'help' for help.");
			}
			if (userInput != null) {
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

				if ((COMMAND.EXIT.getName().equalsIgnoreCase(command)) || (COMMAND.EXIT.getAbbreviation().equalsIgnoreCase(command))) {
					// special treatment of "exit" since we have to abort the
					// loop
					break;
				} else if (COMMAND.REPEAT.getName().equalsIgnoreCase(command) || COMMAND.REPEAT.getAbbreviation().equalsIgnoreCase(command)) {
					if (lastUserInput != null && lastCommand != null && lastParams != null) {
						executeSingleCommand(lastUserInput, lastCommand, lastParams);
					} else {
						output.println("Can not repeat if no other command was executed before.");
					}
				} else {
					executeSingleCommand(lastUserInput, command, params);
					// only update the last command if repeat was not executed
					lastCommand = command;
					lastParams = params;
					lastUserInput = userInput;
				}
			} // end while not exiting
		}
	}

	private void executeSingleCommand(String userInput, String command, List<String> params) {

		try {
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
			} else if ((COMMAND.CONVERT.getName().equalsIgnoreCase(command)) || (COMMAND.CONVERT.getAbbreviation().equalsIgnoreCase(command))) {
				if (params.size() == 1) {
					convert(params.get(0));
				} else {
					convert(null);
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
			} else if ((COMMAND.UPDATE.getName().equalsIgnoreCase(command)) || (COMMAND.UPDATE.getAbbreviation().equalsIgnoreCase(command))) {
				output.println(update(params));
			} else if (COMMAND.PRINT_DEPS.getName().equalsIgnoreCase(command) || COMMAND.PRINT_DEPS.getAbbreviation().equalsIgnoreCase(command)) {
				output.print(printDependencies(params));
			} else if (COMMAND.VERSION.getName().equalsIgnoreCase(command) || COMMAND.VERSION.getAbbreviation().equalsIgnoreCase(command)) {
				output.print(((PepperOSGiConnector) getPepper()).getFrameworkVersion().concat(System.lineSeparator()));
			} else {
				output.println("Type 'help' for help.");
			}
		} catch (Exception | Error e) {
			output.print("An error occured while processing '" + userInput + "': " + e.getMessage() + ". ");
			if (!isDebug) {
				output.println("For more details enter '" + COMMAND.DEBUG.getName() + "' and redo last action. ");
			} else {
				String msg = e.getMessage();
				if ((msg != null) && (!msg.isEmpty())) {
					logger.error(" ", e.getMessage());
				} else {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method reads the dependency command's parameters, calls pepper to
	 * invoke the dependency collection and returns the results.
	 * 
	 * @param params
	 *            -- command line parameters
	 * @return dependency tree print
	 */
	private String printDependencies(List<String> params) {
		PepperOSGiConnector connector = (PepperOSGiConnector) getPepper();
		Map<String, Pair<String, String>> moduleTable = null;
		try {
			moduleTable = getModuleTable();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("Could not parse module table.");
			return "No operation performed.";
		}

		StringBuilder retVal = new StringBuilder();
		String groupId = null;
		String version = null;
		for (String param : params) {
			if ("all".equalsIgnoreCase(param)) {
				retVal.delete(0, retVal.length());
				for (String module : moduleTable.keySet()) {
					groupId = moduleTable.get(module).getLeft();
					Bundle bundle = connector.getBundle(groupId, module, null);
					version = bundle == null ? null : bundle.getVersion().toString().replace(".SNAPSHOT", "-SNAPSHOT");
					if (version == null) {
						logger.info(module.concat(" not installed. Collecting dependencies for newest version."));
					}
					retVal.append(connector.printDependencies(moduleTable.get(module).getLeft(), module, version, moduleTable.get(module).getRight())).append(System.lineSeparator()).append(System.lineSeparator());
				}
				return retVal.toString();
			} else if (param.contains("::")) {
				String[] coords = params.get(0).split("::");
				if (coords.length == 4) {
					retVal.append(connector.printDependencies(coords[0], coords[1], coords[2], coords[3])).append(System.lineSeparator());
				}
			} else if (moduleTable.keySet().contains(param)) {
				groupId = moduleTable.get(param).getLeft();
				Bundle bundle = connector.getBundle(groupId, param, null);
				version = bundle == null ? null : bundle.getVersion().toString().replace(".SNAPSHOT", "-SNAPSHOT");
				if (version == null) {
					logger.info(param.concat(" not installed. Collecting dependencies for newest version."));
				}
				retVal.append(connector.printDependencies(moduleTable.get(param).getLeft(), param, version, moduleTable.get(param).getRight())).append(System.lineSeparator());
			} else if (param.matches("#?[0-9]+")) {
				retVal.append(connector.printDependencies(param.replace("#", ""))).append(System.lineSeparator());
			}
		}
		return retVal.toString();
	}

	/** This is the default ending of a Pepper workflow description file. **/
	public static final String FILE_ENDING_PEPPER = "pepper";
	/**
	 * The standard width of the output console of Pepper.
	 */
	public final static int CONSOLE_WIDTH = 120;
	/** The width of the output console of Pepper. */
	public final static int CONSOLE_WIDTH_120 = 120;

	/** The width of the output console of Pepper, when os is windows. */
	public final static int CONSOLE_WIDTH_80 = 80;

	/**
	 * Resolves the current Pepper version from a file named version.properties
	 * in the pepper-lib jar file.
	 * 
	 * @return the current Pepper version
	 */
	public static String getVersion() {
		String version = null;
		Properties versionProp = new Properties();
		try (InputStream is = PepperStarter.class.getResourceAsStream("version.properties")) {
			versionProp.load(is);
			version = versionProp.getProperty("version");
		} catch (IOException | NullPointerException ex) {
			throw new PepperException("Cannot read current Pepper version. ", ex);
		}
		return (version);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Long timestamp = 0l;
		PepperStarter starter = null;
		PepperConnector pepper = null;
		boolean endedWithErrors = false;

		if (LoggerFactory.getILoggerFactory() instanceof LoggerContext) {
			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
			// in our OSGI environment package data extraction
			// results in strange errors when log() is called with an
			// exception as argument
			lc.setPackagingDataEnabled(false);
		}
		PepperStarterConfiguration pepperProps = null;
		try {
			pepperProps = new PepperStarterConfiguration();
			pepperProps.load();

			String eMail = pepperProps.getPepperEMail();
			String hp = pepperProps.getPepperHomepage();

			starter = new PepperStarter();
			starter.setPepperConfiguration(pepperProps);

			if ((args.length > 0) && (args[0].equalsIgnoreCase(COMMAND.DEBUG.toString()))) {
				starter.debug();
			}
			logger.info(Greet.getHello(pepperProps.getConsoleWidth(), eMail, hp));

			pepper = new PepperOSGiConnector();
			pepper.setConfiguration(pepperProps);
			boolean runInteractive = false;
			try {
				starter.setPepper(pepper);
			} catch (Exception e) {
				logger.info("An error occured, while starting Pepper. To get more information on that, please check the log file, which is by default located at 'PEPPER_HOME/pepper_out.log'. You now can exit Pepper or try to find out more about that exception using the Pepper console. ", e);
				runInteractive = true;
			}
			if ((args.length == 0) || (runInteractive)) {
				// run interactive console
				starter.runInteractive();
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
			} else if ((COMMAND.UPDATE.getName().equalsIgnoreCase(args[0])) || (COMMAND.UPDATE.getAbbreviation().equalsIgnoreCase(args[0]))) {
				List<String> params = new Vector<String>();
				for (int i = 1; i < args.length; i++) {
					params.add(args[i]);
				}
				logger.info(starter.update(params));
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
				try {
					timestamp = System.currentTimeMillis();
					if (logger.isDebugEnabled()) {
						for (Map.Entry<Object, Object> entry : pepperProps.entrySet()) {
							logger.debug(String.format("%-40s%-16s", entry.getKey() + ":", entry.getValue()));
						}
					}
					logger.debug(pepper.getRegisteredModulesAsString());
					if (workFlowFile != null) {
						workFlowFile = workFlowFile.replace("\\", "/");

						starter.convert(workFlowFile);
					} else {
						logger.error("The passed workflow file was empty. ");
					}
					timestamp = System.currentTimeMillis() - timestamp;
				} catch (Exception e) {
					timestamp = System.currentTimeMillis() - timestamp;
					endedWithErrors = true;
					throw e;
				}
			}
		} catch (Exception e) {
			logger.info("An error occured, to get more information on that, please check the log file, which is by default located at 'PEPPER_HOME/pepper_out.log'. ");
			logger.error(" ", e);
		} finally {
			if (pepperProps != null) {
				logger.info(Greet.getGoodBye(pepperProps.getConsoleWidth()));
			} else {
				logger.info(Greet.getGoodBye(CONSOLE_WIDTH_120));
			}
		}
		if (endedWithErrors) {
			System.exit(-1);
		} else {
			System.exit(0);
		}
	}
}

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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.StepDesc;

/**
 * This class represents a console to realize a kind of an interactive wizzard
 * to guide the user through the workflow configuration. This is a step by step
 * wizzard in which the the user needs to make an input for each step:
 * <ol>
 * <li>Import phase
 * <ol>
 * <li>choose source corpus</li>
 * <li>choose importer (if possible show a list of recommended importers and the
 * rest)</li>
 * <li>choose configuration properties (wizzard presents a list of possible
 * properties)</li>
 * <li>another importer? press enter for no, corpus path for yes</li>
 * </ol>
 * </li>
 * <li>Manipulation phase
 * <ol>
 * <li>choose manipulator (enter for non)</li>
 * <li>choose configuration properties (wizzard presents a list of possible
 * properties)</li>
 * <li>another manipulator? press enter for no</li>
 * </ol>
 * </li>
 * <li>Export phase
 * <ol>
 * <li>choose target path for corpus</li>
 * <li>choose configuration properties (wizzard presents a list of possible
 * properties)</li>
 * <li>another exporter? press enter for no, corpus path for yes</li>
 * </ol>
 * </li>
 * 
 * </ol>
 * 
 * @author Florian Zipser
 * 
 */
public class ConvertWizzardConsole {

	private static final String PROMPT = "wizzard";
	private String prompt = null;

	/**
	 * Initializes an object.
	 * 
	 * @param prefixPrompt
	 *            the prefix prompt to be displayed, before the prompt of this
	 *            console.
	 */
	public ConvertWizzardConsole(String prefixPrompt) {
		prompt = prefixPrompt + "/" + PROMPT;
	}

	/** The Pepper object, which shall create and run the job **/
	private Pepper pepper = null;

	/**
	 * @return The Pepper object, which shall create and run the job
	 */
	public Pepper getPepper() {
		return pepper;
	}

	/**
	 * @param pepper
	 *            The Pepper object, which shall create and run the job
	 */
	public void setPepper(Pepper pepper) {
		this.pepper = pepper;
	}

	/**
	 * Starts this console, using std in and std out.
	 */
	public void start() {
		start(new BufferedReader(new InputStreamReader(System.in)), System.out);
	}

	/** Input stream to be used for this session to get the users input. **/
	private BufferedReader in = null;
	/** Output stream to be used for this session to give feedback. **/
	private PrintStream out = null;

	/**
	 * Starts the wizzard for a 'session'. A wizzard can only be started as
	 * singleton. That means it needs to be quit before it can be started again.
	 * 
	 * @param in
	 * @param out
	 */
	public synchronized void start(BufferedReader in, PrintStream out) {
		this.in = in;
		this.out = out;
		String jobId = pepper.createJob();
		PepperJob pepperJob = pepper.getJob(jobId);

		String promptOld = prompt;
		prompt = prompt + "/importer";
		importPhase(pepperJob);
		
		prompt= promptOld +"/manipulator";
		manipulationPhase(pepperJob);
		
		prompt= promptOld +"/exporter";
		exportPhase(pepperJob);
		prompt= promptOld;
	}

	/**
	 * A sub wizzard to manage the import phase. Asks all importers from the
	 * user.
	 * <ol>
	 * <li>state 0: reads corpus path, empty input leads to exit import phase</li>
	 * <li>state 1: choose importer</li>
	 * <li>state 2: choose properties, empty input leads to state 0</li>
	 * </ol>
	 * 
	 * @param pepperJob
	 *            the {@link PepperJob} object to be filled.
	 * @return true if an importer was chosen, false if phase was aborted
	 */
	public boolean importPhase(PepperJob pepperJob) {
		int state = 0;
		String input = null;
		StepDesc stepDesc = null;
		out.println("\tPlease enter the path to the corpus you want to convert (import). ");
		// a map containing each registered module and a corresponding number,
		// to make selection easier (key= number, value= module desc)
		Map<Integer, PepperModuleDesc> number2Module = null;
		// a map containing each registered module and a corresponding name,
		// to make selection easier (key= name, value= module desc)
		Map<String, PepperModuleDesc> name2Module = null;
		// the String containing the map to be presented to the user
		String legend = null;
		// the module description which was selected by the user
		PepperModuleDesc moduleDesc = null;
		String promptOld= prompt;
		while (((input = getUserInput(in, out)) != null) || (state == 2)) {

			if (state == 0) {
				if (!input.isEmpty()) {
					// read corpus path

					File corpusPath = new File(input);
					if (!corpusPath.exists()) {
						out.println("\tThe path to corpus does not exist '" + corpusPath.getAbsolutePath() + "' please type in another one. ");
					} else {
						stepDesc = pepperJob.createStepDesc();
						stepDesc.setModuleType(MODULE_TYPE.IMPORTER);
						stepDesc.getCorpusDesc().setCorpusPath(URI.createFileURI(corpusPath.getAbsolutePath()));
						if ((number2Module == null) || (name2Module == null)) {
							number2Module = new HashMap<Integer, PepperModuleDesc>();
							name2Module = new HashMap<String, PepperModuleDesc>();
							legend = createX2ModuleMap(number2Module, name2Module, MODULE_TYPE.IMPORTER);
						}
						out.println(legend);
						out.println("\tPlease enter the number or the name of the importer you wish to use. ");
						state++;
					}
				} else {
					// empty input return
					if (stepDesc != null) {
						// at least one importer was created
						return (true);
					} else {
						return (false);
					}
				}
			} else if (state == 1) {
				// choose importer

				try {
					Integer num = Integer.valueOf(input);
					moduleDesc = number2Module.get(num);
				} catch (NumberFormatException e) {
					moduleDesc = name2Module.get(input);
				}
				if (moduleDesc == null) {
					out.println(legend);
					out.println("\tSorry could not match the input, please enter the number or the name of the importer you wish to use again. ");
				} else {
					out.println("\tchoosed importer: '" + moduleDesc + "'. ");
					stepDesc.setName(moduleDesc.getName());
					pepperJob.addStepDesc(stepDesc);
					out.println("\tTo use a customization property, please enter the number or the name of the property you wish to use, the '=' and its value (name=value, or number=value). Or enter for no customization properties. ");
					state = 2;
					prompt = promptOld + "/prop";
				}
			} else if (state == 2) {
				// choose properties
				
				if (! readProp(input, stepDesc)){
					state = 0;
					prompt = promptOld;
					out.println("\tPlease enter the path to another corpus you want to convert or press enter. ");
				}
			}
		}// end: while
		return (true);
	}

	/**
	 * A sub wizzard to manage the manipulation phase. Asks for all manipulators
	 * from the user.
	 * <ol>
	 * <li>state 1: reads name of manipulator, empty input leads to exit import phase</li>
	 * <li>state 2: asks for properties, input leads to state 1</li>
	 * </ol>
	 * 
	 * @param pepperJob
	 *            the {@link PepperJob} object to be filled.
	 * @return
	 */
	public void manipulationPhase(PepperJob pepperJob) {
		int state = 1;
		String input = null;
		StepDesc stepDesc = null;
		String promptOld= prompt;
		
		// a map containing each registered module and a corresponding number,
		// to make selection easier (key= number, value= module desc)
		Map<Integer, PepperModuleDesc> number2Module = new HashMap<Integer, PepperModuleDesc>();;
		// a map containing each registered module and a corresponding name,
		// to make selection easier (key= name, value= module desc)
		Map<String, PepperModuleDesc> name2Module = new HashMap<String, PepperModuleDesc>();
		// the String containing the map to be presented to the user
		String legend = createX2ModuleMap(number2Module, name2Module, MODULE_TYPE.MANIPULATOR);
		out.println(legend);
		out.println("\tPlease enter the number or the name of the manipulator you wish to use. ");

		// the module description which was selected by the user
		PepperModuleDesc moduleDesc = null;
		while (((input = getUserInput(in, out)) != null) || (state == 2)) {
			
			if (state == 1) {
				// choose manipulator
				if (input.isEmpty()){
					return;
				}
				stepDesc= pepperJob.createStepDesc();
				stepDesc.setModuleType(MODULE_TYPE.MANIPULATOR);
				try {
					Integer num = Integer.valueOf(input);
					moduleDesc = number2Module.get(num);
				} catch (NumberFormatException e) {
					moduleDesc = name2Module.get(input);
				}
				if (moduleDesc == null) {
					out.println(legend);
					out.println("\tSorry, could not match the input, please enter the number or the name of the importer you wish to use again. ");
				} else {
					out.println("\tchoosed manipulator: '" + moduleDesc + "'. ");
					stepDesc.setName(moduleDesc.getName());
					pepperJob.addStepDesc(stepDesc);
					out.println("\tTo use a customization property, please enter the number or the name of the property you wish to use, the '=' and its value (name=value, or number=value). Or enter for no customization properties. ");
					state=2;
					prompt = promptOld + "/prop";
				}
			} else if (state == 2) {
				if (! readProp(input, stepDesc)){
					state = 1;
					prompt = promptOld;
					out.println("\tPlease enter the number or the name of the manipulator you wish to use. ");
				}
			}
		}//end while
		prompt= promptOld;
	}

	/**
	 * A sub wizzard to manage the import phase. Asks all importers from the
	 * user.
	 * <ol>
	 * <li>reads corpus path</li>
	 * <li>choose importer</li>
	 * </ol>
	 * 
	 * @param pepperJob
	 *            the {@link PepperJob} object to be filled.
	 * @return
	 */
	public void exportPhase(PepperJob pepperJob) {

	}

	/**
	 * Fills a map containing each registered module and a corresponding number,
	 * to make selection easier (key= number, value= module desc). Fills a map
	 * containing each registered module and a corresponding name, to make
	 * selection easier (key= name, value= module desc).
	 * 
	 * @param number2Module
	 * @param name2Module
	 * @return legend for the map to be printed
	 */
	private String createX2ModuleMap(Map<Integer, PepperModuleDesc> number2Module, Map<String, PepperModuleDesc> name2Module, MODULE_TYPE moduleType) {
		StringBuilder str = new StringBuilder();
		Integer num = 0;
		for (PepperModuleDesc moduleDesc : getPepper().getRegisteredModules()) {
			if (moduleType.equals(moduleDesc.getModuleType())) {
				number2Module.put(num, moduleDesc);
				name2Module.put(moduleDesc.getName(), moduleDesc);
				str.append("\t");
				str.append(num);
				str.append(":\t");
				str.append(moduleDesc.getName());
				str.append("(");
				int i = 0;
				for (FormatDesc format : moduleDesc.getSupportedFormats()) {
					if (i > 0) {
						str.append("; ");
					}
					str.append(format.getFormatName());
					str.append(", ");
					str.append(format.getFormatVersion());
					i++;
				}
				str.append(")");
				str.append("\n");
				num++;
			}
		}
		return (str.toString());
	}

	/**
	 * Waits for a user input from passed input stream and returns it. If the
	 * command "exit" was entered. null is returned.
	 * 
	 * @param in
	 * @param out
	 * @return
	 */
	private String getUserInput(BufferedReader in, PrintStream out) {
		boolean exit = false;
		String userInput = "";

		if (!exit) {
			try {
				out.print(prompt);
				out.print(">");
				userInput = in.readLine();
				userInput = userInput.trim();
			} catch (IOException ioe) {
				out.println("Cannot read command, type in 'help' for help.");
			}
			if (("exit".equalsIgnoreCase(userInput))) {
				exit = true;
				return (null);
			}
		}
		return (userInput);
	}
	
	/**
	 * Reads a property from the given inpu and returns true, if the input was not empty
	 * and false otherwise. 
	 * @param input the user input
	 * @param stepDesc the {@link StepDesc} object to which the property should be added
	 * @return true if input was not empty 
	 */
	private boolean readProp(String input, StepDesc stepDesc) {
		if (!input.isEmpty()) {
			int eqPosition = StringUtils.indexOf(input, "=");
			if (eqPosition > 0) {
				String qualifier = input.substring(0, eqPosition);
				String value = input.substring(eqPosition + 1, input.length());
				if (stepDesc.getProps() == null) {
					stepDesc.setProps(new Properties());
				}
				stepDesc.getProps().put(qualifier, value);
				out.println("\tAdded property: " + qualifier + " = " + value);
			} else {
				out.println("\tSorry could not match the input. ");
			}
			return (true);
		} else {
			return (false);
		}
	}
}
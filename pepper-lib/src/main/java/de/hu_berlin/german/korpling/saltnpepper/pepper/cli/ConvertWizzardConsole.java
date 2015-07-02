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
package de.hu_berlin.german.korpling.saltnpepper.pepper.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.StepDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperty;

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

	private static final String MSG_IM = "\tPlease enter the number or the name of the importer you want to use. ";
	private static final String MSG_IMPORT_CORPUS = "\tPlease enter a (further) path to corpus you want to import or press enter to skip. When you use a relative path make the relative to:'" + new File("").getAbsolutePath() + "/'. ";
	private static final String MSG_PROP = "\tTo use a customization property, please enter it's number or name, the '=' and a value (e.g. 'name=value', or 'number=value'). To skip the customiazation, press enter. ";
	private static final String MSG_MAN = "\tIf you want to use a manipulator, please enter it's number or name, or press enter to skip. ";
	private static final String MSG_NO_PROPS = "\tNo customization properties available.";
	private static final String MSG_NO_VALID_MODULE = "\tSorry could not match the input, please enter the number or the name of the module again. ";
	private static final String MSG_NO_VALID_PROP = "\tSorry could not match the input, please enter the number or the name of the property followed by '=' and the value again. ";
	private static final String MSG_EX = "\tPlease enter the number or the name of the exporter you want to use. ";
	private static final String MSG_EX_CORPUS = "\tPlease enter a (further) path to which you want to export the corpus or press enter to skip. When you use a relative path make the relative to:'" + new File("").getAbsolutePath() + "/'. ";

	private static final String MSG_ABORTED = "Creating of Pepper workflow aborted by user's input. ";

	/** Determines if debug mode is on or off **/
	public Boolean isDebug = false;

	public enum COMMAND {
		//
		SAVE("save", "s", "path to file", "Stores the Pepper workflow description to passed file location. "),
		//
		CONVERT("convert", "c", null, "Starts the conversion process of the created Pepper workflow. ");

		private String name = null;
		private String abbreviation = null;
		private String parameters = null;
		private String description = null;

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
	 * <ol>
	 * <li>startes wizzard for import phase {@link #importPhase(PepperJob)}</li>
	 * <li>startes wizzard for manipulation phase
	 * {@link #manipulationPhase(PepperJob)}</li>
	 * <li>startes wizzard for export phase {@link #exportPhase(PepperJob)}</li>
	 * <li>requests user input {@value COMMAND#SAVE} to store workflow
	 * description or {@value COMMAND#CONVERT} to start conversion</li>
	 * </ol>
	 * 
	 * @param in
	 * @param out
	 */
	public synchronized PepperJob start(BufferedReader in, PrintStream out) {
		this.in = in;
		this.out = out;
		String jobId = pepper.createJob();
		PepperJob pepperJob = pepper.getJob(jobId);

		try {
			String promptOld = prompt;
			prompt = prompt + "/importer";
			if (!importPhase(pepperJob)) {
				out.println(MSG_ABORTED);
				return (null);
			}

			prompt = promptOld + "/manipulator";
			manipulationPhase(pepperJob);

			prompt = promptOld + "/exporter";
			if (!exportPhase(pepperJob)) {
				out.println(MSG_ABORTED);
				return (null);
			}
			prompt = promptOld;
			out.println("Type 'convert' to start the conversion, 'save' to save the workflow description and 'exit' to exit. ");
			String input = null;
			while ((input = getUserInput(in, out)) != null) {
				String[] parts = input.split(" ");
				String command = parts[0];
				List<String> params = new Vector<String>();
				int i = 0;
				for (String part : parts) {
					if (i > 0) {
						params.add(part);
					}
					i++;
				}
				if ((COMMAND.SAVE.getName().equalsIgnoreCase(command)) || (COMMAND.SAVE.getAbbreviation().equalsIgnoreCase(command))) {
					File outputFile = null;
					if (parts.length == 1) {
						// path to workflow description wasn't given and needs
						// to be
						// requested

						out.println("Please enter the file location to store Pepper workflow description. ");
						while ((input = getUserInput(in, out)) != null) {
							outputFile = new File(input);
							break;
						}
					} else {
						// path to store workflow description was given

						outputFile = new File(params.get(params.size() - 1));
					}
					try {
						deresolveURIs(outputFile, pepperJob);
						URI workflowURI= pepperJob.save(URI.createFileURI(outputFile.getAbsolutePath()));
						out.println("Stored Pepper workflow description at '" + outputFile.getAbsolutePath() + "'. ");
						// because of the deresolving of the URI, the relative
						// path now is incompatible with current working
						// location, to fix this, the Pepper workflow file needs
						// to be stored and reloaded again
						System.out.println("Load new Job ");
						pepperJob= getPepper().getJob(jobId);
						pepperJob.load(workflowURI);
					} catch (Exception e) {
						out.println("Could not store Pepper workflow to '" + outputFile.getAbsolutePath() + "', because of: " + e.getMessage());
						if (isDebug) {
							e.printStackTrace(out);
						}
					}

				} else if ((COMMAND.CONVERT.getName().equalsIgnoreCase(command)) || (COMMAND.CONVERT.getAbbreviation().equalsIgnoreCase(command))) {
					return (pepperJob);
				}
				out.println("Type 'convert' to start the conversion, 'save' to save the workflow description and enter to exit. ");
			}
		} catch (ExitWizzardException e) {
			out.println(MSG_ABORTED);
			return (null);
		}
		return (null);
	}

	/**
	 * Before saving, create relative URIs for Pepper job. Create a base URI to
	 * deresolve relative URIs
	 * 
	 * @param outputFile
	 * @param pepperJob
	 * @throws IOException
	 */
	public static void deresolveURIs(File outputFile, PepperJob pepperJob) throws IOException {
		URI base;
		if (outputFile.isDirectory()) {
			base = URI.createFileURI(outputFile.getCanonicalPath() + "/");
		} else {
			base = URI.createFileURI(outputFile.getCanonicalFile().getParentFile().getCanonicalPath() + "/");
		}
		for (StepDesc stepDesc : pepperJob.getStepDescs()) {
			if ((stepDesc.getCorpusDesc() != null) && (stepDesc.getCorpusDesc().getCorpusPath() != null)) {
				URI before = stepDesc.getCorpusDesc().getCorpusPath();
				stepDesc.getCorpusDesc().setCorpusPath(stepDesc.getCorpusDesc().getCorpusPath().deresolve(base));
				if (!stepDesc.getCorpusDesc().getCorpusPath().equals(before)) {
					// creates a leading './' if URI is relative
					stepDesc.getCorpusDesc().setCorpusPath(URI.createFileURI("./" + stepDesc.getCorpusDesc().getCorpusPath()));
				}
			}
		}
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
		out.println(MSG_IMPORT_CORPUS.replace("(further) ", ""));
		// a map containing each registered module and a corresponding number,
		// to make selection easier (key= number, value= module desc)
		Map<Integer, PepperModuleDesc> number2Module = null;
		// a map containing each registered module and a corresponding name,
		// to make selection easier (key= name, value= module desc)
		Map<String, PepperModuleDesc> name2Module = null;
		// the String containing the map to be presented to the user
		String legend = null;
		// a map containing a number corresponding to a customization property
		// for the current module
		Map<Integer, String> number2PropName = null;
		// stores the legend of the customization properties for the current
		// module
		String propLegend = null;
		// the module description which was selected by the user
		PepperModuleDesc moduleDesc = null;
		String promptOld = prompt;
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
						String path;
						try {
							path = corpusPath.getCanonicalPath();
						} catch (IOException e) {
							path = corpusPath.getAbsolutePath();
						}
						if ((corpusPath.isDirectory()) && (!path.endsWith("/"))) {
							path = path + "/";
						}
						out.println("import corpus from: " + path);
						stepDesc.getCorpusDesc().setCorpusPath(URI.createFileURI(path));

						if ((number2Module == null) || (name2Module == null)) {
							number2Module = new HashMap<Integer, PepperModuleDesc>();
							name2Module = new HashMap<String, PepperModuleDesc>();
							legend = createModuleLegend(stepDesc.getCorpusDesc().getCorpusPath(), number2Module, name2Module, MODULE_TYPE.IMPORTER);
						}
						out.println(legend);
						out.println(MSG_IM);
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
					out.println(MSG_NO_VALID_MODULE);
				} else {
					out.println("\tchoosed importer: '" + moduleDesc + "'. \n");
					stepDesc.setName(moduleDesc.getName());
					pepperJob.addStepDesc(stepDesc);
					if (moduleDesc.getProperties() != null) {
						// module takes customization properties

						state = 2;
						prompt = promptOld + "/prop";
						number2PropName = new HashMap<Integer, String>();
						propLegend = createPropertyLegend(moduleDesc.getProperties(), number2PropName);
						out.println(propLegend);
						out.println(MSG_PROP);
					} else {
						// module does not take customization properties

						out.println(MSG_NO_PROPS);
						out.println(MSG_IMPORT_CORPUS);
						propLegend = null;
						state = 0;
					}
				}
			} else if (state == 2) {
				// choose properties

				if (!readProp(number2PropName, input, stepDesc)) {
					state = 0;
					prompt = promptOld;
					out.println(MSG_IMPORT_CORPUS);
				}
			}
		}// end: while
		return (true);
	}

	/**
	 * A sub wizzard to manage the manipulation phase. Asks for all manipulators
	 * from the user.
	 * <ol>
	 * <li>state 1: reads name of manipulator, empty input leads to exit import
	 * phase</li>
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
		String promptOld = prompt;

		// a map containing each registered module and a corresponding number,
		// to make selection easier (key= number, value= module desc)
		Map<Integer, PepperModuleDesc> number2Module = new HashMap<Integer, PepperModuleDesc>();
		// a map containing each registered module and a corresponding name,
		// to make selection easier (key= name, value= module desc)
		Map<String, PepperModuleDesc> name2Module = new HashMap<String, PepperModuleDesc>();
		// the String containing the map to be presented to the user
		String legend = createModuleLegend(null, number2Module, name2Module, MODULE_TYPE.MANIPULATOR);
		out.println(legend);
		out.println(MSG_MAN);
		// a map containing a number corresponding to a customization property
		// for the current module
		Map<Integer, String> number2PropName = null;
		// stores the legend of the customization properties for the current
		// module
		String propLegend = null;

		// the module description which was selected by the user
		PepperModuleDesc moduleDesc = null;
		while (((input = getUserInput(in, out)) != null) || (state == 2)) {

			if (state == 1) {
				// choose manipulator
				if (input.isEmpty()) {
					return;
				}
				stepDesc = pepperJob.createStepDesc();
				stepDesc.setModuleType(MODULE_TYPE.MANIPULATOR);
				try {
					Integer num = Integer.valueOf(input);
					moduleDesc = number2Module.get(num);
				} catch (NumberFormatException e) {
					moduleDesc = name2Module.get(input);
				}
				if (moduleDesc == null) {
					out.println(legend);
					out.println(MSG_NO_VALID_MODULE);
				} else {
					out.println("\tchoosed manipulator: '" + moduleDesc + "'. \n");
					stepDesc.setName(moduleDesc.getName());
					pepperJob.addStepDesc(stepDesc);

					if (moduleDesc.getProperties() != null) {
						// module takes customization properties
						state = 2;
						prompt = promptOld + "/prop";
						number2PropName = new HashMap<Integer, String>();
						propLegend = createPropertyLegend(moduleDesc.getProperties(), number2PropName);
						out.println(propLegend);
						out.println(MSG_PROP);
					} else {
						// module does not take customization properties

						out.println(MSG_NO_PROPS);
						out.println(MSG_IMPORT_CORPUS);
						propLegend = null;
						state = 0;
					}
				}
			} else if (state == 2) {
				if (!readProp(number2PropName, input, stepDesc)) {
					state = 1;
					prompt = promptOld;
					out.println(legend);
					out.println(MSG_MAN);
				}
			}
		}// end while
		prompt = promptOld;
	}

	/**
	 * A sub wizzard to manage the import phase. Asks all importers from the
	 * user.
	 * <ol>
	 * <li>state 0: choose output path, empty input leads to exit of export
	 * phase</li>
	 * <li>state 1: choose exporter</li>
	 * <li>state 2: choose property, empty input leads to state 0</li>
	 * </ol>
	 * 
	 * @param pepperJob
	 *            the {@link PepperJob} object to be filled.
	 * @return if an exporter was set, false otherwise
	 */
	public boolean exportPhase(PepperJob pepperJob) {
		int state = 0;
		String input = null;
		StepDesc stepDesc = null;
		out.println(MSG_EX_CORPUS.replace("(further) ", ""));
		// a map containing each registered module and a corresponding number,
		// to make selection easier (key= number, value= module desc)
		Map<Integer, PepperModuleDesc> number2Module = null;
		// a map containing each registered module and a corresponding name,
		// to make selection easier (key= name, value= module desc)
		Map<String, PepperModuleDesc> name2Module = null;
		// the String containing the map to be presented to the user
		String legend = null;
		// a map containing a number corresponding to a customization property
		// for the current module
		Map<Integer, String> number2PropName = null;
		// stores the legend of the customization properties for the current
		// module
		String propLegend = null;

		// the module description which was selected by the user
		PepperModuleDesc moduleDesc = null;
		String promptOld = prompt;
		while (((input = getUserInput(in, out)) != null) || (state == 2)) {

			if (state == 0) {
				if (!input.isEmpty()) {
					// read corpus path

					File corpusPath = new File(input);
					if (!corpusPath.exists()) {
						corpusPath.mkdirs();
					}
					String path;
					try {
						path = corpusPath.getCanonicalPath();
					} catch (IOException e) {
						path = corpusPath.getAbsolutePath();
					}
					if (!path.endsWith("/")) {
						path = path + "/";
					}
					out.println("export corpus to: " + path);
					stepDesc = pepperJob.createStepDesc();
					stepDesc.setModuleType(MODULE_TYPE.EXPORTER);
					stepDesc.getCorpusDesc().setCorpusPath(URI.createFileURI(path));
					if ((number2Module == null) || (name2Module == null)) {
						number2Module = new HashMap<Integer, PepperModuleDesc>();
						name2Module = new HashMap<String, PepperModuleDesc>();
						legend = createModuleLegend(null, number2Module, name2Module, MODULE_TYPE.EXPORTER);
					}
					out.println(legend);
					out.println(MSG_EX);
					state++;
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
				// choose exporter

				try {
					Integer num = Integer.valueOf(input);
					moduleDesc = number2Module.get(num);
				} catch (NumberFormatException e) {
					moduleDesc = name2Module.get(input);
				}
				if (moduleDesc == null) {
					out.println(legend);
					out.println(MSG_NO_VALID_MODULE);
				} else {
					out.println("\tchoosed exporter: '" + moduleDesc + "'. \n");
					stepDesc.setName(moduleDesc.getName());
					pepperJob.addStepDesc(stepDesc);
					if (moduleDesc.getProperties() != null) {
						// module takes customization properties

						state = 2;
						prompt = promptOld + "/prop";
						number2PropName = new HashMap<Integer, String>();
						propLegend = createPropertyLegend(moduleDesc.getProperties(), number2PropName);
						out.println(propLegend);
						out.println(MSG_PROP);
					} else {
						// module does not take customization properties

						out.println(MSG_NO_PROPS);
						out.println(MSG_EX_CORPUS);
						propLegend = null;
						state = 0;
					}
				}
			} else if (state == 2) {
				// choose properties

				if (!readProp(number2PropName, input, stepDesc)) {
					state = 0;
					prompt = promptOld;
					out.println(MSG_IMPORT_CORPUS);
				}
			}
		}// end: while
		return (true);
	}

	public class ImporterModuleDesc implements Comparable<ImporterModuleDesc> {
		public Double probability = null;
		public PepperModuleDesc moduleDesc = null;

		public ImporterModuleDesc(PepperModuleDesc moduleDesc, Double probability) {
			this.probability = probability;
			this.moduleDesc = moduleDesc;
		}

		@Override
		public int compareTo(ImporterModuleDesc arg0) {
			if (this.probability == null) {
				this.probability = 0.0;
			}
			if (arg0.probability == null) {
				arg0.probability = 0.0;
			}
			return (Double.compare(this.probability, arg0.probability));
		}

		public String toString() {
			return (probability + " " + moduleDesc.getName());
		}
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
	private String createModuleLegend(URI corpusPath, Map<Integer, PepperModuleDesc> number2Module, Map<String, PepperModuleDesc> name2Module, MODULE_TYPE moduleType) {
		ArrayList<PepperModuleDesc> modules = new ArrayList<PepperModuleDesc>();
		int numOfRecommended = 0;
		// if module is importer, call isImportable
		if (MODULE_TYPE.IMPORTER.equals(moduleType)) {
			List<ImporterModuleDesc> importerModuleDescs = new ArrayList<ConvertWizzardConsole.ImporterModuleDesc>();
			for (PepperModuleDesc moduleDesc : getPepper().getRegisteredModules()) {
				if (MODULE_TYPE.IMPORTER.equals(moduleDesc.getModuleType())) {
					Double isImportable = getPepper().isImportable(corpusPath, moduleDesc);
					if ((isImportable != null) && (isImportable > 0.0)) {
						numOfRecommended++;
					}
					importerModuleDescs.add(new ImporterModuleDesc(moduleDesc, isImportable));
				}
			}
			Collections.reverse(importerModuleDescs);
			for (ImporterModuleDesc moduleDesc : importerModuleDescs) {
				modules.add(moduleDesc.moduleDesc);
			}
		} else {
			for (PepperModuleDesc moduleDesc : getPepper().getRegisteredModules()) {
				if (moduleType.equals(moduleDesc.getModuleType())) {
					modules.add(moduleDesc);
				}
			}
		}

		String retStr = null;
		String[][] map = new String[modules.size() + 1][3];
		map[0][0] = "no";
		map[0][1] = "module name";
		if ((MODULE_TYPE.IMPORTER.equals(moduleType)) || (MODULE_TYPE.EXPORTER.equals(moduleType))) {
			map[0][2] = "format";
		} else {
			map[0][2] = "description";
		}
		Integer[] length = { 5, 30, 40 };
		Integer num = 1;
		for (PepperModuleDesc moduleDesc : modules) {
			number2Module.put(num, moduleDesc);
			name2Module.put(moduleDesc.getName(), moduleDesc);
			String prefix = "";
			if (numOfRecommended > num) {
				prefix = "* ";
			} else {
				prefix = "  ";
			}
			map[num][0] = prefix + num;
			map[num][1] = moduleDesc.getName();
			if ((MODULE_TYPE.IMPORTER.equals(moduleType)) || (MODULE_TYPE.EXPORTER.equals(moduleType))) {
				// module is an importer or exporter

				if (moduleDesc.getSupportedFormats().size() > 0) {
					int i = 0;

					StringBuilder str = new StringBuilder();
					str.append("(");
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
					map[num][2] = str.toString();
				}
			} else {
				// module is a manipulator
				map[num][2] = moduleDesc.getDesc();
			}
			num++;
		}
		retStr = PepperUtil.createTable(length, map, true, true, true);
		return (retStr);
	}

	/**
	 * Fills a map containing each {@link PepperModuleProperty} and a
	 * corresponding number, to make selection easier (key= number, value=
	 * property). Fills a map containing each {@link PepperModuleProperty} and a
	 * corresponding name, to make selection easier (key= name, value=
	 * property).
	 * 
	 * @param number2Module
	 * @param name2Module
	 * @return legend for the map to be printed
	 */
	private String createPropertyLegend(PepperModuleProperties props, Map<Integer, String> number2propName) {
		String retStr = null;
		if (props != null) {
			String[][] map = new String[props.getPropertyDesctriptions().size() + 1][3];
			map[0][0] = "no";
			map[0][1] = "property name";
			map[0][2] = "description";
			Integer[] length = { 3, 30, 40 };
			int i = 1;
			for (PepperModuleProperty<?> prop : props.getPropertyDesctriptions()) {
				map[i][0] = String.valueOf(i);
				map[i][1] = prop.getName();
				map[i][2] = prop.getDescription();
				number2propName.put(i, prop.getName());
				i++;
			}
			retStr = PepperUtil.createTable(length, map, true, true, true);
		}

		return (retStr);
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
		String userInput = "";

		try {
			out.println();
			out.print(prompt);
			out.print(">");
			userInput = in.readLine();
			if (userInput != null) {
				userInput = userInput.trim();
			}
		} catch (IOException ioe) {
			out.println("Cannot read command.");
		}
		if (("exit".equalsIgnoreCase(userInput))) {
			throw new ExitWizzardException();
		}
		return (userInput);
	}

	private class ExitWizzardException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	/**
	 * Reads a property from the given inpu and returns true, if the input was
	 * not empty and false otherwise.
	 * 
	 * @param input
	 *            the user input
	 * @param stepDesc
	 *            the {@link StepDesc} object to which the property should be
	 *            added
	 * @return true if input was not empty
	 */
	private boolean readProp(Map<Integer, String> number2propName, String input, StepDesc stepDesc) {
		if ((input != null) && (!input.isEmpty())) {
			int eqPosition = StringUtils.indexOf(input, "=");
			if (eqPosition > 0) {
				String qualifier = input.substring(0, eqPosition);
				try {
					Integer num = Integer.valueOf(qualifier);
					qualifier = number2propName.get(num);
				} catch (NumberFormatException e) {
					// do nothing
				}
				String value = input.substring(eqPosition + 1, input.length());
				if (stepDesc.getProps() == null) {
					stepDesc.setProps(new Properties());
				}
				stepDesc.getProps().put(qualifier, value);
				out.println("\tAdded property: " + qualifier + " = " + value);
			} else {
				out.println(MSG_NO_VALID_PROP);
			}
			return (true);
		} else {
			return (false);
		}
	}
}
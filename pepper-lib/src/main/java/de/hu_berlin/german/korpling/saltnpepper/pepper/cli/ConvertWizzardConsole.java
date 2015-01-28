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
import java.util.List;
import java.util.Vector;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.impl.PepperOSGiConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl;

/**
 * This class represents a console to realize a kind of an interactive wizzard to guide the user through the
 * workflow configuration. This is a step by step wizzard in which the the user needs to make an input for each
 * step:
 * <ol>
 *  <li>Import phase
 *   <ol>
 *   	<li>choose source corpus</li>
 * 		<li>choose importer (if possible show a list of recommended importers and the rest)</li>
 *      <li>choose configuration properties (wizzard presents a list of possible properties)</li>
 *  	<li>another importer? press enter for no, corpus path for yes</li>
 *   </ol>
 *  </li>
 *  <li>Manipulation phase
 *   <ol>
 *   	<li>choose manipulator (enter for non)</li>
 *      <li>choose configuration properties (wizzard presents a list of possible properties)</li>
 *  	<li>another manipulator? press enter for no</li>
 *   </ol>
 *  </li>
 *  <li>Export phase
 *   <ol>
 *   	<li>choose target path for corpus</li>
 * 		<li>choose configuration properties (wizzard presents a list of possible properties)</li>
 *  	<li>another exporter? press enter for no, corpus path for yes</li>
 *   </ol>
 *  </li>
 * 	
 * </ol> 
 * 
 * @author Florian Zipser
 * 
 */
public class ConvertWizzardConsole {

	private static final String PROMPT = "wizzard>";
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

	/** The Pepper object, which shall create and run the job**/
	private Pepper pepper= null;
	/**
	 * @return The Pepper object, which shall create and run the job
	 */
	public Pepper getPepper() {
		return pepper;
	}

	/**
	 * @param pepper The Pepper object, which shall create and run the job
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


	public void start(BufferedReader in, PrintStream out) {
		boolean exit = false;
		String userInput = null;
		Integer state= 0;
		// the pepper job which has to be created
		PepperJob pepperJob= null;
		out.println("Type in the path to the corpus you want to import and press enter.");
		while (!exit) {
			try {
				out.print(prompt);
				userInput = in.readLine();
			} catch (IOException ioe) {
				out.println("Cannot read command, type in 'help' for help.");
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
			if (state== 0){
				// import phase
				// input source corpus path
				if (	(command== null)||
						(command.isEmpty())){
					exit= true;
				}else{
					File corpusPath= new File(command);
					if (!corpusPath.exists()){
						out.println("The path to corpus does not exist '"+corpusPath.getAbsolutePath()+"' please type in another one. ");
					}else{
						String jobId = pepper.createJob();
						pepperJob = pepper.getJob(jobId);
						
					}
				}
				
			}
			if (("exit".equalsIgnoreCase(command))) {
				exit = true;
			} 
		}
	}
//	
//	public Boolean importPhase(PrintStream out, String command, String... parts){
//		
//	}
}

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.corpus_tools.pepper.connectors.impl.PepperOSGiConnector;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

/**
 * This class represents a simple console for accessing the underlying OSGi
 * environment of a {@link PepperOSGiConnector}. It allows simple commands.
 * 
 * @author Florian Zipser
 * 
 */
public class OSGiConsole {

	private static final String PROMPT = "osgi>";
	private String prompt = null;

	public enum COMMAND {
		SS("ss", "ss", null, "display installed bundles (short status)"), LS("list", "ls", null, "lists all components"), INSTALL("install", "i", "bundle path", "install and optionally start bundle from the given URL"), UNINSTALL("uninstall", "un", "bundle id", "uninstall the specified bundle"), START("start", "s", "bundle id", "start the specified bundle"), STOP("stop", "stop", "bundle id", "stop the specified bundle"), INSTALL_START("install_start", "is", "bundle path", "installs the bundle located at 'bundle path' and starts it"), UPDATE("update", "up", "module-path", "updates the Pepper module, which was installed from 'module path'."), REMOVE("remove", "re", "bundle name", "removes the bundle 'bundle name' from the OSGi context and from plugin folder."), HELP("help", "h", null, "Prints this help."), EXIT("exit", "e", null, "exits Pepper");

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

	private PepperOSGiConnector connector = null;

	public PepperOSGiConnector getConnector() {
		return connector;
	}

	/**
	 * Initializes an object.
	 * 
	 * @param connector
	 *            the {@link PepperOSGiConnector} to be used to access the OSGi
	 *            environment
	 * @param prefixPrompt
	 *            the prefix prompt to be displayed, before the prompt of this
	 *            console.
	 */
	public OSGiConsole(PepperOSGiConnector connector, String prefixPrompt) {
		prompt = prefixPrompt + "/" + PROMPT;
		this.connector = connector;
	}

	/**
	 * Starts this console, using std in and std out.
	 */
	public void start() {
		start(new BufferedReader(new InputStreamReader(System.in)), System.out);
	}

	/**
	 * Returns the help page for this console.
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

	public void start(BufferedReader in, PrintStream out) {
		boolean exit = false;
		String userInput = null;
		while (!exit) {
			try {
				out.print(prompt);
				userInput = in.readLine();
			} catch (IOException ioe) {
				out.println("Cannot read command, type in 'help' for help.");
			}
			if (userInput != null) {
				userInput = userInput.trim();
			}
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

			if (("exit".equalsIgnoreCase(command))) {
				exit = true;
			} else if (("help".equalsIgnoreCase(command))) {
				out.println(help());
			} else if ((COMMAND.SS.getName().equalsIgnoreCase(command)) || (COMMAND.SS.getAbbreviation().equalsIgnoreCase(command))) {
				ss(params, out);
			} else if ((COMMAND.LS.getName().equalsIgnoreCase(command)) || (COMMAND.LS.getAbbreviation().equalsIgnoreCase(command))) {
				ls(params, out);
			} else if ((COMMAND.INSTALL.getName().equalsIgnoreCase(command)) || (COMMAND.INSTALL.getAbbreviation().equalsIgnoreCase(command))) {
				install(params, out);
			} else if ((COMMAND.STOP.getName().equalsIgnoreCase(command)) || (COMMAND.STOP.getAbbreviation().equalsIgnoreCase(command))) {
				stop(params, out);
			} else if ((COMMAND.START.getName().equalsIgnoreCase(command)) || (COMMAND.START.getAbbreviation().equalsIgnoreCase(command))) {
				start(params, out);
			} else if ((COMMAND.UNINSTALL.getName().equalsIgnoreCase(command)) || (COMMAND.UNINSTALL.getAbbreviation().equalsIgnoreCase(command))) {
				uninstall(params, out);
			} else if ((COMMAND.INSTALL_START.getName().equalsIgnoreCase(command)) || (COMMAND.INSTALL_START.getAbbreviation().equalsIgnoreCase(command))) {
				installAndStart(params, out);
			} else if ((COMMAND.UPDATE.getName().equalsIgnoreCase(command)) || (COMMAND.UPDATE.getAbbreviation().equalsIgnoreCase(command))) {
				update(params, out);
			} else if ((COMMAND.REMOVE.getName().equalsIgnoreCase(command)) || (COMMAND.REMOVE.getAbbreviation().equalsIgnoreCase(command))) {
				remove(params, out);
			} else {
				out.println(help());
			}
		}
	}

	/**
	 * Executes OSGi command 'ss'.
	 * 
	 * @param params
	 * @param out
	 */
	public void ss(List<String> params, PrintStream out) {
		String format = "| %1$-10s | %2$-15s | %3$-60s | %4$-20s |\n";
		out.append("+------------+-----------------+----------------------------------------------------------------------------------+\n");
		out.print(String.format(format, "id", "state", "bundle name", "version"));
		out.append("+------------+-----------------+----------------------------------------------------------------------------------+\n");

		if (getConnector().getBundleContext().getBundles() != null) {
			for (Bundle bundle : getConnector().getBundleContext().getBundles()) {
				out.print(String.format(format, bundle.getBundleId(), transformState(bundle.getState()), bundle.getSymbolicName(), bundle.getVersion()));
			}
		}
		out.append("+------------+-----------------+----------------------------------------------------------------------------------+\n");
	}

	/**
	 * Executes OSGi command 'ls'.
	 * 
	 * @param params
	 * @param out
	 */
	public void ls(List<String> params, PrintStream out) {
		String format = "| %1$-10s | %2$-15s | %3$-80s | %4$-80s |\n";
		out.append("+------------+-----------------+----------------------------------------------------------------------------------+----------------------------------------------------------------------------------+\n");
		out.print(String.format(format, "id", "state", "component name", "bundle name"));
		out.append("+------------+-----------------+----------------------------------------------------------------------------------+----------------------------------------------------------------------------------+\n");

		if (getConnector().getBundleContext().getBundles() != null) {
			for (Bundle bundle : getConnector().getBundleContext().getBundles()) {
				if (bundle.getRegisteredServices() != null) {
					for (ServiceReference ref : bundle.getRegisteredServices()) {
						out.print(String.format(format, bundle.getBundleId(), transformState(bundle.getState()), getConnector().getBundleContext().getService(ref), bundle.getSymbolicName()));
					}
				}
			}
		}
		out.append("+------------+-----------------+----------------------------------------------------------------------------------+----------------------------------------------------------------------------------+\n");
	}

	/**
	 * Executes OSGi command 'install'.
	 * 
	 * @param params
	 * @param out
	 */
	public Long install(List<String> params, PrintStream out) {
		Long retVal = null;
		if ((params == null) || (params.size() != 1)) {
			out.println("To install a bundle, you need to pass a path as URI.");
		} else {
			URI bundleURI = null;
			try {
				bundleURI = URI.create(params.get(0));
				if (bundleURI.getScheme() == null) {
					bundleURI = URI.create("file:" + params.get(0));
				}
			} catch (Exception e) {
				out.println("Cannot load bundle at path '" + params.get(0) + "'.");
			}
			if (bundleURI != null) {
				Bundle bundle = null;
				try {
					bundle = getConnector().installAndCopy(bundleURI);
				} catch (BundleException e) {
					out.println("Cannot install bundle at path '" + params.get(0) + "' because of a nested exception. " + e.getMessage());
				} catch (IOException e) {
					out.println("Cannot install bundle at path '" + params.get(0) + "' because of a nested exception. " + e.getMessage());
				}
				if (bundle != null) {
					out.println("bundle installed with id '" + bundle.getBundleId() + "'.");
					retVal = bundle.getBundleId();
				}
			}
		}
		return (retVal);
	}

	/**
	 * Executes OSGi command 'start'.
	 * 
	 * @param params
	 * @param out
	 */
	public void start(List<String> params, PrintStream out) {
		if ((params == null) || (params.size() != 1)) {
			out.println("To start a bundle, you need to pass a bundle id.");
		} else {
			Long bundleId = null;
			try {
				bundleId = Long.valueOf(params.get(0));
			} catch (NumberFormatException e) {
				out.println("The passed bundle id '" + params.get(0) + "'is not a valid id.");
			}
			if (bundleId != null) {
				getConnector().start(bundleId);
				out.println("started bundle with id '" + bundleId + "'");
			}
		}
	}

	/**
	 * Executes command 'launch', calling the OSGi command 'install' and
	 * 'start'.
	 * 
	 * @param params
	 * @param out
	 */
	public void installAndStart(List<String> params, PrintStream out) {
		Long bundleId = install(params, out);
		List<String> params2 = new ArrayList<String>();
		if (bundleId != null) {
			params2.add("" + bundleId);
			start(params2, out);
		}
	}

	/**
	 * Executes command 'update', calling the OSGi commands 'uninstall',
	 * 'install' and 'start'.
	 * 
	 * @param params
	 * @param out
	 */
	public void update(List<String> params, PrintStream out) {
		if (params == null) {
			out.println("stupid help text");
		} else if (params.size() == 1 && params.get(0).equals("all")) {

		}
	}

	/**
	 * Executes OSGi command 'stop'.
	 * 
	 * @param params
	 * @param out
	 */
	public void stop(List<String> params, PrintStream out) {
		Long bundleId = null;
		if ((params == null) || (params.size() != 1)) {
			out.println("To stop a bundle, you need to pass a bundle id.");
		} else {
			try {
				bundleId = Long.valueOf(params.get(0));
			} catch (NumberFormatException e) {
				out.println("The passed bundle id '" + params.get(0) + "'is not a valid id.");
			}
			if (bundleId != null) {
				try {
					getConnector().getBundleContext().getBundle(bundleId).stop();
					out.println("Stopped bundle '" + bundleId + "'");
				} catch (BundleException e) {
					out.println("Cannot stop bundle '" + bundleId + "' because of a nested exception. " + e.getMessage());
				}
			}
		}
	}

	/**
	 * Executes OSGi command 'uninstall'.
	 * 
	 * @param params
	 * @param out
	 */
	public void uninstall(List<String> params, PrintStream out) {
		Long bundleId = null;
		URI bundleURI = null;
		if ((params == null) || (params.size() != 1)) {
			out.println("To uninstall a bundle, you need to pass a bundle id.");
		} else {
			try {
				bundleId = Long.valueOf(params.get(0));
			} catch (NumberFormatException e) {
				try {
					bundleURI = URI.create(params.get(0));
				} catch (IllegalArgumentException e1) {
					out.println("The passed bundle id '" + params.get(0) + "'is neither a valid id nor a valid bundle location. Please use only digits for ids and the URI syntax for bundle locations");
				}
			}
			if (bundleId != null) {
				try {
					getConnector().uninstall(bundleId);
					out.println("Uninstalled bundle '" + bundleId + "'");
				} catch (BundleException e) {
					out.println("Cannot uninstall bundle '" + bundleId + "' because of a nested exception. " + e.getMessage());
				}
			} else if (bundleURI != null) {
				try {
					getConnector().uninstall(bundleURI);
					out.println("Uninstalled bundle from loaction '" + bundleURI + "'");
				} catch (BundleException e) {
					out.println("Cannot uninstall bundle from location '" + bundleURI + "' because of a nested exception. " + e.getMessage());
				}
			}
		}
	}

	/**
	 * Executes command 'remove'.
	 * 
	 * @param params
	 * @param out
	 */
	public void remove(List<String> params, PrintStream out) {
		if ((params == null) || (params.size() > 1)) {
			out.println("To remove a bundle, you need to pass its name.");
		} else {
			try {
				if (getConnector().remove(params.get(0))) {
					out.println("Removed bundle '" + params.get(0) + "'.");
				} else
					out.println("Cannot remove bundle '" + params.get(0) + "'.");
			} catch (BundleException e) {
				out.println("Cannot remove bundle '" + params.get(0) + "', because of nested exception (BundleException): " + e.getMessage());
			} catch (IOException e) {
				out.println("Cannot remove bundle '" + params.get(0) + "', because of nested exception (IOException): " + e.getMessage());
			}
		}
	}

	public String transformState(int state) {
		String retVal = null;
		switch (state) {
		case 32:
			retVal = "ACTIVE";
			break;
		case 2:
			retVal = "INSTALLED";
			break;
		case 4:
			retVal = "RESOLVED";
			break;
		case 1:
			retVal = "SIGNERS_ALL";
			break;
		case 8:
			retVal = "STARTING";
			break;
		case 16:
			retVal = "STOPPING";
			break;
		default:
			retVal = "UNKNOWN";
		}
		return (retVal);
	}
}

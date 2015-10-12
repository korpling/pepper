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
package org.corpus_tools.pepper.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.corpus_tools.pepper.exceptions.PepperException;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleXMLResourceException;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;

public abstract class PepperUtil {

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
	 * Returns a formatted String, a kind of a welcome screen of Pepper.
	 * 
	 * @return welcome screen
	 */
	public static String getHello() {
		return (getHello(CONSOLE_WIDTH, "saltnpepper@lists.hu-berlin.de", "http://u.hu-berlin.de/saltnpepper"));
	}

	/**
	 * Returns a formatted String, a kind of a welcome screen of Pepper.
	 * 
	 * @return welcome screen
	 */
	public static String getHello(int width, String eMail, String hp) {
		StringBuilder retVal = new StringBuilder();

		if (CONSOLE_WIDTH_80 == width) {
			retVal.append("********************************************************************************\n");
			retVal.append("*                    ____                                                      *\n");
			retVal.append("*                   |  _ \\ ___ _ __  _ __   ___ _ __                           *\n");
			retVal.append("*                   | |_) / _ \\ '_ \\| '_ \\ / _ \\ '__|                          *\n");
			retVal.append("*                   |  __/  __/ |_) | |_) |  __/ |                             *\n");
			retVal.append("*                   |_|   \\___| .__/| .__/ \\___|_|                             *\n");
			retVal.append("*                             |_|   |_|                                        *\n");
			retVal.append("*                                                                              *\n");
			retVal.append("********************************************************************************\n");
			retVal.append("* Pepper is a Salt model based converter for a variety of linguistic formats.  *\n");
			retVal.append("* For further information, visit: " + hp + "            *\n");
			retVal.append("* For contact write an eMail to:  " + eMail + "               *\n");
			retVal.append("********************************************************************************\n");
			retVal.append("\n");
		} else {
			retVal.append("************************************************************************************************************************\n");
			retVal.append("*                                         ____                                                                         *\n");
			retVal.append("*                                        |  _ \\ ___ _ __  _ __   ___ _ __                                              *\n");
			retVal.append("*                                        | |_) / _ \\ '_ \\| '_ \\ / _ \\ '__|                                             *\n");
			retVal.append("*                                        |  __/  __/ |_) | |_) |  __/ |                                                *\n");
			retVal.append("*                                        |_|   \\___| .__/| .__/ \\___|_|                                                *\n");
			retVal.append("*                                                  |_|   |_|                                                           *\n");
			retVal.append("*                                                                                                                      *\n");
			retVal.append("************************************************************************************************************************\n");
			retVal.append("* Pepper is a Salt model based converter for a variety of linguistic formats.                                          *\n");
			retVal.append("* For further information, visit: " + hp + "                                                    *\n");
			retVal.append("* For contact write an eMail to:  " + eMail + "                                                       *\n");
			retVal.append("************************************************************************************************************************\n");
			retVal.append("\n");
		}

		return (retVal.toString());
	}

	/**
	 * Breaks the String <code>theString</code> at position
	 * {@link #CONSOLE_WIDTH} and adds a linebreak. The manipulated String is
	 * returned.
	 * 
	 * @param theString
	 *            String to be break
	 * @return the breaked String
	 */
	public static String breakString(String theString) {
		return (breakString("", theString, CONSOLE_WIDTH));
	}

	/**
	 * Breaks the String <code>theString</code> at position <code>length</code>
	 * and adds a linebreak. The manipulated String is returned.
	 * 
	 * @param theString
	 *            String to be break
	 * @param length
	 *            position where to break String
	 * @return the breaked String
	 */
	public static String breakString(String theString, int length) {
		return (breakString("", theString, length));
	}

	/**
	 * Breaks the String <code>theString</code> at position
	 * {@link #CONSOLE_WIDTH} and adds a linebreak. The next line than is
	 * prefixed by<code>linePrefix</code>. The manipulated String is returned.
	 * 
	 * @param theString
	 *            String to be break
	 * @param length
	 *            position where to break String
	 * @param linePrefix
	 *            a prefix for all lines
	 * @return the breaked String
	 */
	public static String breakString(String linePrefix, String theString) {
		return (breakString(linePrefix, theString, CONSOLE_WIDTH));
	}

	/**
	 * Breaks the String <code>theString</code> at position <code>length</code>
	 * and adds a linebreak. The next line than is prefixed by
	 * <code>linePrefix</code>. The manipulated String is returned.
	 * 
	 * @param theString
	 *            String to be break
	 * @param length
	 *            position where to break String
	 * @param linePrefix
	 *            a prefix for all lines
	 * @return the breaked String
	 */
	public static String breakString(String linePrefix, String theString, int length) {
		if (length > theString.length() + linePrefix.length()) {
			return (theString);
		}
		StringBuilder str = new StringBuilder();
		int pos = 0;
		int offset = length - linePrefix.length();
		boolean goOn = true;
		while (goOn) {
			str.append(linePrefix);
			if (pos + offset < theString.length()) {
				str.append(theString.substring(pos, pos + offset));
				str.append("\n");
			} else {
				str.append(theString.substring(pos));
				goOn = false;
			}
			pos = pos + offset;
		}
		return (str.toString());
	}

	/**
	 * Returns rest
	 */
	public static String breakString2(StringBuilder output, final String theString, final int length) {
		if ((theString != null) && (!theString.isEmpty())) {
			// the rest, which has to be returned
			StringBuilder rest = new StringBuilder();

			if (length >= theString.length() + output.toString().length()) {
				output.append(theString);
				return (null);
			}
			char[] chrs = theString.toCharArray();
			HashSet<Character> separators = new HashSet<Character>();
			separators.add(' ');
			separators.add('.');
			separators.add(',');
			separators.add(';');
			separators.add(':');
			separators.add('?');
			separators.add('!');
			separators.add('\"');
			separators.add('\'');
			separators.add('-');
			separators.add('~');
			separators.add('*');
			separators.add('+');
			int currLength = output.toString().length();
			StringBuilder stagingStr = new StringBuilder();
			for (int i = 0; i < length - currLength; i++) {
				stagingStr.append(chrs[i]);
				if (separators.contains(chrs[i])) {
					output.append(stagingStr.toString());
					stagingStr = new StringBuilder();
				}
			}

			if (currLength == output.toString().length()) {
				// in case of nothing was written to output, write staging
				// string to output
				output.append(stagingStr.toString());
			} else {
				// adding staging part to rest, which was not printed
				rest.append(stagingStr.toString());
			}
			// adding rest of theString to rest
			if ((length - currLength) > 0) {
				for (int i = length - currLength; i < theString.length(); i++) {
					rest.append(chrs[i]);
				}
			}
			return (rest.toString());
		}
		return (null);
	}

	/**
	 * Returns a table created from the passed Strings.
	 * 
	 * @param length
	 *            an array of lengths for the columns
	 * @param map
	 *            a map containing the Strings to be printed out sorted as
	 *            [line, column]
	 * @param hasHeader
	 *            determines, if the first line of map contains a header for the
	 *            table
	 * @param hasBlanks
	 *            determines if vertical lines has to be followed by a blank
	 *            e.g. with blanks "| cell1 |" or without blanks "|cell1|"
	 * @param drawInnerVerticalLine
	 *            determines whether an inner vertical line between two cells
	 *            has to be drawn e.g. "|cell1 | cell2|" or "|cell1 cell2|"
	 * @return
	 */
	public static String createTable(Integer[] length, String[][] map, boolean hasHeader, boolean hasBlanks, boolean drawInnerVerticalLine) {
		if (length == null) {
			throw new PepperException("Cannot create a table with empty length. ");
		}

		StringBuilder retVal = new StringBuilder();

		// create horizontal line
		String hr = null;
		StringBuilder hrb = new StringBuilder();
		for (int a : length) {
			hrb.append("+");
			for (int b = 0; b < a; b++) {
				hrb.append("-");
			}
			if (hasBlanks) {
				hrb.append("-");
			}
		}
		if (hasBlanks) {
			hrb.append("-");
		}
		hrb.append("+");
		hrb.append("\n");
		hr = hrb.toString();

		retVal.append(hr);
		for (int line = 0; line < map.length; line++) {

			// initialize current line, with original texts
			String[] currLine = new String[map[line].length];
			for (int col = 0; col < map[line].length; col++) {
				currLine[col] = map[line][col];
			}
			StringBuilder out = new StringBuilder();
			boolean goOn = true;
			if ("--".equalsIgnoreCase(currLine[0])) {
				goOn = false;
				retVal.append(hr);
			}
			while (goOn) {
				goOn = false;
				for (int col = 0; col < currLine.length; col++) {
					currLine[col] = breakString2(out, currLine[col], length[col]);
					int diff = length[col] - out.toString().length();
					if (diff > 0) {
						for (int i = 0; i < diff; i++) {
							out.append(" ");
						}
					}
					if (currLine[col] != null) {
						goOn = true;
					}
					if (drawInnerVerticalLine) {
						retVal.append("|");
					}
					if (hasBlanks) {
						retVal.append(" ");
					}
					retVal.append(out.toString());
					out = new StringBuilder();
				}
				if (hasBlanks) {
					retVal.append(" ");
				}
				retVal.append("|\n");
			}
			// print horizontal line in case of first line is header
			if ((hasHeader) && (line == 0)) {
				retVal.append(hr);
			}
		}
		retVal.append(hr);
		return (retVal.toString());
	}

	/**
	 * Returns a temporary folder, where all tests can store temporary files.
	 * The returned temporary folder is a combination of the systems standard
	 * temp folder, the prefix 'pepper', and the users name or a randomized
	 * unique sequence of characters, if the user name is not available and
	 * suffixed by the passed segments.
	 * 
	 * @return path, where to store temporary files
	 * @param segments
	 *            segments or subfolders to be attached to the created temp
	 *            folder, subfolders are separated by '/'
	 */
	public static synchronized File getTempTestFile(String segments) {
		return (getTempFile(segments, "pepper-test"));
	}

	/**
	 * Returns a temporary folder, where all tests can store temporary files.
	 * The returned temporary folder is a combination of the systems standard
	 * temp folder, the prefix 'pepper', and the users name or a randomized
	 * unique sequence of characters, if the user name is not available and
	 * suffixed by the passed segments.
	 * 
	 * @return path, where to store temporary files
	 */
	public static synchronized File getTempTestFile() {
		return (getTempFile(null, "pepper-test"));
	}

	/**
	 * Returns a temporary folder, where Pepper and all modules can store temp
	 * files. The returned temporary folder is a combination of the systems
	 * standard temp folder, the prefix 'pepper' and the users name or a
	 * randomized unique sequence of characters, if the user name is not
	 * available.
	 * 
	 * @return path, where to store temporary files
	 */
	public static synchronized File getTempFile() {
		return (getTempFile(null));
	}

	/**
	 * Returns a temporary folder, where Pepper and all modules can store temp
	 * files. The returned temporary folder is a combination of the systems
	 * standard temp folder, the prefix 'pepper', and the users name or a
	 * randomized unique sequence of characters, if the user name is not
	 * available and suffixed by the passed segments.
	 * 
	 * @return path, where to store temporary files
	 * @param segments
	 *            segments or subfolders to be attached to the created temp
	 *            folder, subfolders are separated by '/'
	 * @return
	 */
	public static synchronized File getTempFile(String segments) {
		return (getTempFile(segments, "pepper"));
	}

	/**
	 * Returns a temporary folder, where Pepper and all modules can store temp
	 * files. The returned temporary folder is a combination of the systems
	 * standard temp folder, the prefix 'pepper', and the users name or a
	 * randomized unique sequence of characters, if the user name is not
	 * available and suffixed by the passed segments.
	 * 
	 * @return path, where to store temporary files
	 * @param segments
	 *            segments or subfolders to be attached to the created temp
	 *            folder, subfolders are separated by '/'
	 * @param prefix
	 *            the prefix to be used like 'pepper' or pepper-test etc.
	 * @return
	 */
	public static synchronized File getTempFile(String segments, String prefix) {
		String usr = System.getProperty("user.name");
		String path = null;
		if ((usr != null) && (!usr.isEmpty())) {
			path = System.getProperty("java.io.tmpdir");
			if (!path.endsWith("/")) {
				path = path + "/";
			}
			path = path + prefix + "_" + usr + "/";
		} else {
			try {
				path = Files.createTempDirectory(prefix + "_", new FileAttribute<?>[0]).toFile().getAbsolutePath();
				if (!path.endsWith("/")) {
					path = path + "/";
				}
			} catch (IOException e) {
				throw new PepperException("Cannot create temporary folder at " + System.getProperty("java.io.tmpdir") + ". ");
			}
		}

		File file = null;
		if (segments == null) {
			file = new File(path);
		} else {
			file = new File(path + segments);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		return (file);
	}

	/**
	 * Returns a report as String containing the configuration for Pepper.
	 * 
	 * @return
	 */
	public static String reportConfiguration(PepperConfiguration conf) {
		String line = "+----------------------------------------------------------------------------------------------------------------------+\n";
		StringBuilder str = new StringBuilder();
		String format1 = "| %-" + (CONSOLE_WIDTH - 4) + "s |\n";
		String format2 = "| %-40s: %-" + (CONSOLE_WIDTH - 40 - 6) + "s |\n";
		str.append(line);
		str.append(String.format(format1, " configuration for Pepper"));
		str.append(line);
		if ((conf != null) && (conf.size() != 0)) {
			for (Object key : conf.keySet()) {
				str.append(String.format(format2, key, conf.get(key)));
			}
		} else {
			str.append(String.format(format1, "- no configurations set -"));
		}
		str.append(line);
		return (str.toString());
	}

	/**
	 * Creates a table containing all passed Pepper modules corresponding to
	 * their description and their fingerprint
	 * 
	 * @param maximal
	 *            width of the returned string
	 * @param moduleDescs
	 *            all modules to be listed
	 * @param number2module
	 *            a map containing a module description and a corresponding
	 *            number for identification
	 * @return a table displaying all passed modules and a corresponding
	 *         description
	 */
	public static String reportModuleList(final int width, final Collection<PepperModuleDesc> moduleDescs, Map<Integer, PepperModuleDesc> number2module) {
		String retVal = "- no modules registered -\n";
		if ((moduleDescs != null) && (moduleDescs.size() != 0)) {
			String[][] map = new String[moduleDescs.size() + 1][6];
			map[0][0] = "no.";
			map[0][1] = "module-name";
			map[0][2] = "module-version";
			map[0][3] = "module-type";
			map[0][4] = "formats";
			map[0][5] = "website";
			int i = 1;
			for (PepperModuleDesc desc : moduleDescs) {
				map[i][0] = new Integer(i).toString();
				map[i][1] = desc.getName();
				map[i][2] = desc.getVersion();
				map[i][3] = desc.getModuleType().toString();
				String formatString = "";
				if ((desc.getSupportedFormats() != null) && (desc.getSupportedFormats().size() > 0)) {
					int j = 0;
					for (FormatDesc formatDesc : desc.getSupportedFormats()) {
						if (j != 0) {
							formatString = formatString + "; ";
						}
						formatString = formatString + formatDesc.getFormatName() + ", " + formatDesc.getFormatVersion();
						j++;
					}
				}
				map[i][4] = formatString;
				URI contact = desc.getSupplierHomepage();
				if (contact != null) {
					map[i][5] = contact.toString();
				} else {
					map[i][5] = "";
				}
				if (number2module != null) {
					number2module.put(i, desc);
				}

				i++;
			}
			Integer[] length = new Integer[6];
			if (CONSOLE_WIDTH_80 == width) {
				length[0] = 4;
				length[1] = 15;
				length[2] = 10;
				length[3] = 11;
				length[4] = 16;
				length[5] = 10;
			} else {
				length[0] = 4;
				length[1] = 20;
				length[2] = 15;
				length[3] = 11;
				length[4] = 31;
				length[5] = 25;
			}
			retVal = createTable(length, map, true, true, true);
		}
		return (retVal);
	}

	/**
	 * Creates a table containing all passed Pepper modules corresponding to
	 * their description and their fingerprint
	 * 
	 * @param maximal
	 *            width of the returned string
	 * @param moduleDescs
	 *            all modules to be listed
	 * @param number2module
	 *            a map containing a module description and a corresponding
	 *            number for identification
	 * @return a table displaying all passed modules and a corresponding
	 *         description
	 */
	public static String reportModuleList(int width, Collection<PepperModuleDesc> moduleDescs) {
		return (reportModuleList(width, moduleDescs, null));
	}

	/**
	 * Helper method to read an xml file with a {@link DefaultHandler2}
	 * implementation given as <em>contentHandler</em>. It is assumed, that the
	 * file encoding is set to UTF-8.
	 * 
	 * @param contentHandler
	 *            {@link DefaultHandler2} implementation
	 * @param documentLocation
	 *            location of the xml-file
	 */
	public static void readXMLResource(DefaultHandler2 contentHandler, URI documentLocation) {
		if (documentLocation == null)
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the given uri to locate file is null.");

		File resourceFile = new File(documentLocation.toFileString());
		if (!resourceFile.exists())
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the file does not exist: " + resourceFile);

		if (!resourceFile.canRead())
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource, because the file can not be read: " + resourceFile);

		SAXParser parser;
		XMLReader xmlReader;

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			parser = factory.newSAXParser();
			xmlReader = parser.getXMLReader();
			xmlReader.setContentHandler(contentHandler);
		} catch (ParserConfigurationException e) {
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource '" + resourceFile.getAbsolutePath() + "'.", e);
		} catch (Exception e) {
			throw new PepperModuleXMLResourceException("Cannot load a xml-resource '" + resourceFile.getAbsolutePath() + "'.", e);
		}
		try {
			InputStream inputStream = new FileInputStream(resourceFile);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			xmlReader.parse(is);
		} catch (SAXException e) {
			try {
				parser = factory.newSAXParser();
				xmlReader = parser.getXMLReader();
				xmlReader.setContentHandler(contentHandler);
				xmlReader.parse(resourceFile.getAbsolutePath());
			} catch (Exception e1) {
				throw new PepperModuleXMLResourceException("Cannot load a xml-resource '" + resourceFile.getAbsolutePath() + "'.", e1);
			}
		} catch (Exception e) {
			if (e instanceof PepperModuleException) {
				throw (PepperModuleException) e;
			} else {
				throw new PepperModuleXMLResourceException("Cannot read xml-file'" + documentLocation + "', because of a nested exception. ", e);
			}
		}
	}

	/**
	 * Prints the status of the passed {@link PepperJob} object, until {@link
	 * #setStop(Boolean#TRUE)} was called.
	 * 
	 * @author florian
	 * 
	 */
	public static class PepperJobReporter extends Thread {
		private static final Logger logger = LoggerFactory.getLogger(PepperJobReporter.class);

		/**
		 * Sets the {@link PepperJob} object, which is observed
		 * 
		 * @param pepperJob
		 *            the job to observe
		 * @param interval
		 *            the interval in which the status is printed
		 */
		public PepperJobReporter(PepperJob pepperJob, int interval) {
			if (pepperJob == null) {
				throw new PepperException("Cannot observe Pepper job, because it was null.");
			}
			this.pepperJob = pepperJob;
			this.interval = interval;
		}

		/**
		 * Sets the {@link PepperJob} object, which is observed
		 * 
		 * @param pepperJob
		 *            the job to observe
		 */
		public PepperJobReporter(PepperJob pepperJob) {
			this(pepperJob, 10000);
		}

		/** the interval in which the status is printed **/
		private int interval = 10000;

		/**
		 * Returns the interval in which the status is printed
		 * 
		 * @return
		 */
		public int getInterval() {
			return (interval);
		}

		/** {@link PepperJob} object, which is observed. **/
		private PepperJob pepperJob = null;

		/**
		 * Returns the {@link PepperJob} object, which is observed
		 * 
		 * @return
		 */
		public PepperJob getPepperJob() {
			return pepperJob;
		}

		/** flag, determining, that observing has finished **/
		private boolean stop = false;

		/** Returns, if {@link PepperJob} still has to be observed **/
		public boolean isStop() {
			return stop;
		}

		/**
		 * Sets if {@link PepperJob} still has to be observed
		 * 
		 * @param stop
		 */
		public void setStop(boolean stop) {
			this.stop = stop;
		}

		@Override
		public void run() {
			while (!isStop()) {
				String report = null;
				try {
					if (getPepperJob() == null) {
						logger.warn("No status report is available, because no reference to the Pepper job is given. ");
					}
					report = getPepperJob().getStatusReport();
				} catch (Exception e) {
					report = "- no status report is available -";
					logger.warn("No status report is available, because of a nested exception: ", e);
				} finally {
					if (report != null) {
						logger.info(report);
					}
				}
				try {
					Thread.sleep(getInterval());
				} catch (InterruptedException e) {
				}
			}
		}
	}
}

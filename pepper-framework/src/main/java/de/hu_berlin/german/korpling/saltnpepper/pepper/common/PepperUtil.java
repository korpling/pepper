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
package de.hu_berlin.german.korpling.saltnpepper.pepper.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;

public abstract class PepperUtil {

	/**
	 * The width of the output console of Pepper.
	 */
	public final static int CONSOLE_WIDTH = 120;

	/**
	 * Returns a formatted String, a kind of a welcome screen of Pepper.
	 * 
	 * @return welcome screen
	 */
	public static String getHello() {
		return (getHello("saltnpepper@lists.hu-berlin.de", "http://u.hu-berlin.de/saltnpepper"));
	}

	/**
	 * Returns a formatted String, a kind of a welcome screen of Pepper.
	 * 
	 * @return welcome screen
	 */
	public static String getHello(String eMail, String hp) {
		StringBuilder retVal = new StringBuilder();
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
	 * Returns a temporary folder, where all tests can store temporary files. 
	 * The returned temporary folder is a combination
	 * of the systems standard temp folder, the prefix 'pepper', and the users name or 
	 * a randomized unique sequence of characters, if the user name is not available 
	 * and suffixed by the passed segments. 
	 * @return path, where to store temporary files
	 * @param segments segments or subfolders to be attached to the created temp folder, subfolders are separated by '/'
	 */
	public static synchronized File getTempTestFile(String segments) {
		return(getTempFile(segments, "pepper-test"));
	}
	/**
	 * Returns a temporary folder, where all tests can store temporary files. 
	 * The returned temporary folder is a combination
	 * of the systems standard temp folder, the prefix 'pepper', and the users name or 
	 * a randomized unique sequence of characters, if the user name is not available 
	 * and suffixed by the passed segments. 
	 * @return path, where to store temporary files
	 */
	public static synchronized File getTempTestFile() {
		return(getTempFile(null, "pepper-test"));
	}
	
	/**
	 * Returns a temporary folder, where Pepper and all modules can
	 * store temp files. The returned temporary folder is a combination
	 * of the systems standard temp folder, the prefix 'pepper' and the 
	 * users name or a randomized unique sequence of characters, if the user 
	 * name is not available.
	 * @return path, where to store temporary files
	 */
	public static synchronized File getTempFile() {
		return(getTempFile(null));
	}
	/**
	 * Returns a temporary folder, where Pepper and all modules can
	 * store temp files. The returned temporary folder is a combination
	 * of the systems standard temp folder, the prefix 'pepper', and the users name or 
	 * a randomized unique sequence of characters, if the user name is not available 
	 * and suffixed by the passed segments. 
	 * @return path, where to store temporary files
	 * @param segments segments or subfolders to be attached to the created temp folder, subfolders are separated by '/'
	 * @return
	 */
	public static synchronized File getTempFile(String segments) {
		return(getTempFile(segments, "pepper"));
	}
	/**
	 * Returns a temporary folder, where Pepper and all modules can
	 * store temp files. The returned temporary folder is a combination
	 * of the systems standard temp folder, the prefix 'pepper', and the users name or 
	 * a randomized unique sequence of characters, if the user name is not available 
	 * and suffixed by the passed segments. 
	 * @return path, where to store temporary files
	 * @param segments segments or subfolders to be attached to the created temp folder, subfolders are separated by '/'
	 * @param prefix the prefix to be used like 'pepper' or pepper-test etc.
	 * @return
	 */
	public static synchronized File getTempFile(String segments, String prefix) {
		String usr= System.getProperty("user.name");
		String path= null;
		if (	(usr!= null)&&
				(!usr.isEmpty())){
			path= System.getProperty("java.io.tmpdir");
			if (!path.endsWith("/")){
				path= path+"/";
			}
			path= path +prefix+"_"+usr+"/";
		}else{
			try {
				path= Files.createTempDirectory(prefix+"_", new FileAttribute<?>[0]).toFile().getAbsolutePath();
				if (!path.endsWith("/")){
					path= path+"/";
				}
			} catch (IOException e) {
				throw new PepperException("Cannot create temporary folder at "+System.getProperty("java.io.tmpdir")+". ");
			}
		}
		
		File file= null;
		if (segments== null){
			file= new File(path);
		}else{
			file= new File(path+segments);
		}
		if (!file.exists()){
			file.mkdirs();
		}
		return(file);
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

	public static String reportModuleList(Collection<PepperModuleDesc> moduleDescs) {
		StringBuilder retVal = new StringBuilder();
		if ((moduleDescs == null) || (moduleDescs.size() == 0)) {
			retVal.append("- no modules registered -\n");
		} else {
			String format = "| %1$-4s | %2$-20s | %3$-15s | %4$-11s | %5$-31s | %6$-20s |\n";
			String line = "+------+----------------------+-----------------+-------------+---------------------------------+----------------------+\n";
			retVal.append(line);
			retVal.append(String.format(format, "no.", "module-name", "module-version", "module-type", "formats", "supplier-contact"));
			retVal.append(line);
			int no = 1;
			for (PepperModuleDesc desc : moduleDescs) {
				String formatString = "";

				if ((desc.getSupportedFormats() != null) && (desc.getSupportedFormats().size() > 0)) {
					int i = 0;
					for (FormatDesc formatDesc : desc.getSupportedFormats()) {
						if (i != 0) {
							formatString = formatString + "; ";
						}
						formatString = formatString + formatDesc.getFormatName() + ", " + formatDesc.getFormatVersion();
						i++;
					}
				}

				if (desc != null) {
					retVal.append(String.format(format, no, desc.getName(), desc.getVersion(), desc.getModuleType(), formatString, desc.getSupplierContact()));
				}
				no++;
			}
			retVal.append(line);
		}
		return (retVal.toString());
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
			if (pepperJob == null)
				throw new PepperException("Cannot observe Pepper job, because it was null.");
			this.pepperJob = pepperJob;
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
					report = getPepperJob().getStatusReport();
				} catch (Exception e) {
					report = "- no status report is available -";
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

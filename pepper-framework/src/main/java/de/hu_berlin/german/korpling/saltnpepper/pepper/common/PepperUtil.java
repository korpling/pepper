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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;

public abstract class PepperUtil {

	/**
	 * Returns a formatted String, a kind of a welcome screen of Pepper.
	 * @return welcome screen
	 */
	public static String getHello()
	{
		return(getHello("saltnpepper@lists.hu-berlin.de", "http://u.hu-berlin.de/saltnpepper"));
	}
	/**
	 * Returns a formatted String, a kind of a welcome screen of Pepper.
	 * @return welcome screen
	 */
	public static String getHello(String eMail, String hp)
	{
		StringBuilder retVal= new StringBuilder();
		retVal.append("*************************************************************************\n");
		retVal.append("*                  ____                                                 *\n");                            
		retVal.append("*                 |  _ \\ ___ _ __  _ __   ___ _ __                      *\n"); 
		retVal.append("*                 | |_) / _ \\ '_ \\| '_ \\ / _ \\ '__|                     *\n");
		retVal.append("*                 |  __/  __/ |_) | |_) |  __/ |                        *\n");
		retVal.append("*                 |_|   \\___| .__/| .__/ \\___|_|                        *\n");
		retVal.append("*                           |_|   |_|                                   *\n");
		retVal.append("*                                                                       *\n");                            
		retVal.append("*************************************************************************\n");
		retVal.append("* Pepper is a Salt model based converter for a variety of               *\n");
		retVal.append("* linguistic   formats.                                                 *\n");
		retVal.append("* For further information, visit: "+hp+"     *\n");
		retVal.append("* For contact write an eMail to:  "+eMail+"        *\n");
		retVal.append("*************************************************************************\n");
		retVal.append("\n");
		return(retVal.toString());
	}
	
	/**
	 * Prints the status of the passed {@link PepperJob} object, until {@link #setStop(Boolean#TRUE)} was called.
	 * @author florian
	 *
	 */
	public static class PepperJobReporter extends Thread{
		private static final Logger logger= LoggerFactory.getLogger(PepperJobReporter.class); 
		/**
		 * Sets the {@link PepperJob} object, which is observed
		 * @param pepperJob the job to observe
		 * @param interval the interval in which the status is printed 
		 */
		public PepperJobReporter(PepperJob pepperJob, int interval) {
			if (pepperJob== null)
				throw new PepperException("Cannot observe Pepper job, because it was null.");
			this.pepperJob = pepperJob;
		}
		/**
		 * Sets the {@link PepperJob} object, which is observed
		 * @param pepperJob the job to observe
		 */
		public PepperJobReporter(PepperJob pepperJob) {
			this(pepperJob, 10000);
		}
		
		/** the interval in which the status is printed **/
		private int interval= 10000;
		/**
		 * Returns the interval in which the status is printed
		 * @return
		 */
		public int getInterval(){
			return(interval);
		}
		/** {@link PepperJob} object, which is observed. **/
		private PepperJob pepperJob= null;
		/**
		 * Returns the {@link PepperJob} object, which is observed 
		 * @return
		 */
		public PepperJob getPepperJob() {
			return pepperJob;
		}
		
		/** flag, determining, that observing has finished**/
		private boolean stop= false;
		/** Returns, if {@link PepperJob} still has to be observed**/
		public boolean isStop() {
			return stop;
		}
		/**
		 * Sets if {@link PepperJob}  still has to be observed
		 * @param stop
		 */
		public void setStop(boolean stop) {
			this.stop = stop;
		}
		
		
		@Override
		public void run() {
			while (!isStop()){
				String report= null;
				try{
					report= getPepperJob().getStatusReport();
				} catch (Exception e) {
					report= "- no status report is available -";
				}finally{
					if (report!= null){
						logger.info(report);
					}
				}
				try{
					Thread.sleep(getInterval());
				} catch (InterruptedException e) {
				}
			}
		}
		
	}
}

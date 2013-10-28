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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.PepperConverter;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.util.Logger;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.util.TestComponentFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams;

public class PepperConverterAdapter 
{
	@SuppressWarnings("unchecked")
	private Class[] pepperModuleClasses= null;
	
	/**
	 * Returns the Module Classes which shall be tested, to add them to PepperResolver.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class[] getPepperModuleClasses() {
		return pepperModuleClasses;
	}

	/**
	 * Sets the Module Classes which shall be tested, to add them to PepperResolver.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void setPepperModuleClasses(Class[] pepperModuleClasses) {
		this.pepperModuleClasses = pepperModuleClasses;
	}
	
	/**
	 * Path of temprorary files for pepper modules.
	 */
	private URI tmpFolderURI= URI.createFileURI("./TMP/");
	
	/**
	 * Returns path of temprorary files for pepper modules..
	 * @return
	 */
	public URI getTMPFolderURI() {
		return tmpFolderURI;
	}

	/**
	 * Sets path of temprorary files for pepper modules. Default is "./TMP/"
	 * @param tmpFolderURIStr
	 */
	public void setTMPFolderURI(URI tmpFolderURI) {
		this.tmpFolderURI = tmpFolderURI;
	}
	
	/**
	 * path to resources for pepper modules.
	 */
	private String resFolderStr= "./resources/";

	/**
	 * Returns path to resources for pepper modules.
	 * @return
	 */
	public String getResFolderStr() {
		return resFolderStr;
	}

	/**
	 * Sets path to resources for pepper modules. Default is "./resources/".
	 * @param resFolderStr
	 */
	public void setResFolderStr(String resFolderStr) {
		this.resFolderStr = resFolderStr;
	}

	/**
	 * Starts conversion via PepperConverter. Needs to have a pepperParams file which describes 
	 * conversion.
	 * @param pepperParamsStr pepper params file, which contains workflow description
	 * @throws IOException 
	 */
	public void start(String pepperParamsStr) throws IOException
	{
		PepperModuleResolver moduleResolver= null;
		
		{//create PepperModuleResolver
			moduleResolver= PepperFWFactory.eINSTANCE.createPepperModuleResolver();
			
			for (Class<PepperModule> clazz: pepperModuleClasses)
			{
				TestComponentFactory compFac= new TestComponentFactory();
				compFac.setClass(clazz);
				if (PepperImporter.class.isAssignableFrom(clazz))
					moduleResolver.addPepperImporterComponentFactory(compFac);
				else if (PepperExporter.class.isAssignableFrom(clazz))
					moduleResolver.addPepperExporterComponentFactory(compFac);
				else if (PepperManipulator.class.isAssignableFrom(clazz))
					moduleResolver.addPepperManipulatorComponentFactory(compFac);
				else throw new NullPointerException("The class to test is not of type PepperImporter, PepperManipulator or PepperExporter: "+ clazz);
			}	
			
			{//resource and temprorary folder
				File tmpFolder= new File(this.tmpFolderURI.toFileString());
				File resFolder= new File(resFolderStr);
				System.setProperty(moduleResolver.getTemprorariesPropertyName(), tmpFolder.getCanonicalPath());
				System.setProperty(PepperProperties.PROP_PEPPER_MODULE_RESOURCES, resFolder.getCanonicalPath());
				
			}//resource and temprorary folder

		}//create PepperModuleResolver
		
		{//start conversion
			PepperConverter converter= PepperFWFactory.eINSTANCE.createPepperConverter();
			
			{//set log service
				Logger logger= new Logger();
				converter.setLogService(logger);
			}//set log service
			converter.setPepperModuleResolver(moduleResolver);
			try {
				File pepperParamsFile= new File(pepperParamsStr);
				URI pepperParamsURI= URI.createFileURI(pepperParamsFile.getCanonicalPath());
				converter.setPepperParams(pepperParamsURI);	
					
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			converter.start();
		}//start conversion
	}
	
	/**
	 * Starts conversion via PepperConverter. Needs to have a pepperParams file which describes 
	 * conversion.
	 * @param pepperParams pepper params, which contains workflow description
	 * @throws IOException 
	 */
	public void start(PepperParams pepperParams) throws IOException
	{
		PepperModuleResolver moduleResolver= null;
		
		{//create PepperModuleResolver
			moduleResolver= PepperFWFactory.eINSTANCE.createPepperModuleResolver();
			
			for (Class<PepperModule> clazz: pepperModuleClasses)
			{
				TestComponentFactory compFac= new TestComponentFactory();
				compFac.setClass(clazz);
				if (PepperImporter.class.isAssignableFrom(clazz))
					moduleResolver.addPepperImporterComponentFactory(compFac);
				else if (PepperExporter.class.isAssignableFrom(clazz))
					moduleResolver.addPepperExporterComponentFactory(compFac);
				else if (PepperManipulator.class.isAssignableFrom(clazz))
					moduleResolver.addPepperManipulatorComponentFactory(compFac);
				else throw new NullPointerException("The class to test is not of type PepperImporter, PepperManipulator or PepperExporter: "+ clazz);
			}	
			
			{//resource and temprorary folder
				File tmpFolder= new File(tmpFolderURI.toFileString());
				File resFolder= new File(resFolderStr);
				System.setProperty(moduleResolver.getTemprorariesPropertyName(), tmpFolder.getCanonicalPath());
				System.setProperty(PepperProperties.PROP_PEPPER_MODULE_RESOURCES, resFolder.getCanonicalPath());
				
			}//resource and temprorary folder

		}//create PepperModuleResolver
		
		{//start conversion
			PepperConverter converter= PepperFWFactory.eINSTANCE.createPepperConverter();
			
			{//set log service
				Logger logger= new Logger();
				converter.setLogService(logger);
			}//set log service
			converter.setPepperModuleResolver(moduleResolver);
			try {
				converter.setPepperParams(pepperParams);	
					
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			converter.start();
		}//start conversion	
	}
}

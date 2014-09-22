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
package de.hu_berlin.german.korpling.saltnpepper.pepper.core;

import java.util.Properties;

import org.eclipse.emf.common.util.URI;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.StepDesc;

/**
 * This class reads the 'new' workflow description file version 1.0 and fills the passed
 * {@link PepperJob} object with the content of the read file.
 * The workflow description file has to be conform to ./src/main/resources/workflowDescription_10.rnc.
 * @author Florian Zipser
 *
 */
public class WorkflowDescriptionReader extends DefaultHandler2 {
	public static final String TAG_PEPEPR_JOB="pepper-job";
	public static final String TAG_CUSTOMIZATION="customization";
	public static final String TAG_PROP="property";
	public static final String TAG_IMPORTER="importer";
	public static final String TAG_MANIPULATOR="manipulator";
	public static final String TAG_EXPORTER="exporter";
	
	public static final String ATT_ID="id";
	public static final String ATT_VERSION="version";
	public static final String ATT_KEY="key";
	public static final String ATT_PATH="path";
	public static final String ATT_NAME="name";
	public static final String ATT_FORMAT_NAME="formatName";
	public static final String ATT_FORMAT_VERSION="formatVersion";
	
	/** {@link PepperJob} object to be filled with content. **/
	private PepperJob pepperJob= null;
	/** Returns {@link PepperJob} object to be filled with content. **/
	public PepperJob getPepperJob() {
		return pepperJob;
	}
	/** Sets {@link PepperJob} object to be filled with content. **/
	public void setPepperJob(PepperJob pepperJob) {
		this.pepperJob = pepperJob;
	}
	
	/** location of the current parsed xml file**/
	private URI location= null;
	public URI getLocation() {
		return location;
	}
	public void setLocation(URI location) {
		this.location = location;
	}
	/** current step desc **/
	private StepDesc stepDesc= null;
	
	/** current property object for either a module or the entire Pepper job **/
	private Properties props= null;
	/** the name of a property to be added to either a Pepper module or a job **/
	private String propName=null;
	/** the value for the property **/
	private String propValue=null;
	
	/** flag determines, whether the read file is directly mapped or if is delegated to the {@link PepperParamsReader}**/
	private PepperParamsReader delegatee= null;
	
	@Override
	public void startElement(	String uri,
            					String localName,
            					String qName,
            					Attributes attributes)throws SAXException{
		if (	(PepperParamsReader.ELEMENT_PEPPERPARAMS.equals(qName))||
				((PepperParamsReader.PREFIX_PEPPERPARAMS+":"+PepperParamsReader.ELEMENT_PEPPERPARAMS).equals(qName))){
			delegatee= new PepperParamsReader();
			delegatee.setJob(getPepperJob());
			delegatee.setLocation(getLocation());
		}
		else if (TAG_IMPORTER.equals(qName)){
			//create step desc for importer
			stepDesc= new StepDesc();
			stepDesc.setModuleType(MODULE_TYPE.IMPORTER);
			mapStepDesc(stepDesc, attributes);
			getPepperJob().addStepDesc(stepDesc);
		}else if (TAG_MANIPULATOR.equals(qName)){
			//create step desc for manipulator
			stepDesc= new StepDesc();
			stepDesc.setModuleType(MODULE_TYPE.MANIPULATOR);
			mapStepDesc(stepDesc, attributes);
			getPepperJob().addStepDesc(stepDesc);
		}else if (TAG_EXPORTER.equals(qName)){
			//create step desc for exporter
			stepDesc= new StepDesc();
			stepDesc.setModuleType(MODULE_TYPE.EXPORTER);
			mapStepDesc(stepDesc, attributes);
			getPepperJob().addStepDesc(stepDesc);
		}else if (TAG_CUSTOMIZATION.equals(qName)){
			if (stepDesc== null){
				//customization property is for entire pepper job
				//TODO where to store
			}else{
				//customization property is for importer, manipulator or exporter
				props= new Properties();
				stepDesc.setProps(props);
			}
		}else if (TAG_PROP.equals(qName)){
			propName= attributes.getValue(ATT_KEY);
			propName= propName.trim();
		}
		
		if (delegatee!= null){
			delegatee.startElement(uri, localName, qName, attributes);
		}
    }
	
	/** Reads the property values and adds them to property object **/
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (	(props!= null)&&
				(propName!= null)&&
				(!propName.isEmpty())){
			StringBuilder str= new StringBuilder();
			for (int i= start; i< start+ length; i++){
				str.append(ch[i]);
			}
			propValue= str.toString();
		}
	}
	/**
	 * If a property has no value, it must be creted here.
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (TAG_PROP.equals(qName)){
			if (	(props!= null)&&
					(propName!= null)&&
					(!propName.isEmpty())){
				props.put(propName, (propValue== null)?"":propValue);
				propValue=null;
				propName=null;
			}
		}
	}
	
	/**
	 * Fills the passed {@link StepDesc} object by resolving the specific values from the passed {@link Attributes} 
	 * object.
	 * @param stepDesc
	 * @param attributes
	 */
	private void mapStepDesc(StepDesc stepDesc, Attributes attributes){
		if (attributes.getValue(ATT_NAME)!= null){
			stepDesc.setName(attributes.getValue(ATT_NAME));
		}
		if (attributes.getValue(ATT_VERSION)!= null){
			stepDesc.setVersion(attributes.getValue(ATT_VERSION));
		}
		if (	(MODULE_TYPE.IMPORTER.equals(stepDesc.getModuleType()))||
				(MODULE_TYPE.EXPORTER.equals(stepDesc.getModuleType()))){
			if (attributes.getValue(ATT_FORMAT_NAME)!= null){
				stepDesc.getCorpusDesc().getFormatDesc().setFormatName(attributes.getValue(ATT_FORMAT_NAME));
			}
			if (attributes.getValue(ATT_FORMAT_VERSION)!= null){
				stepDesc.getCorpusDesc().getFormatDesc().setFormatVersion(attributes.getValue(ATT_FORMAT_VERSION));
			}
			if (attributes.getValue(ATT_PATH)!= null){
				stepDesc.getCorpusDesc().setCorpusPath(resolveURI(attributes.getValue(ATT_PATH)));
			}
		
		}
	}
	
	/**
	 * Transforms a given String to a URI.
	 * A File-URI is returned if:
	 * <ul>
	 * 	<li>String is relative</li>
	 *  <li> starts with '/'</li>
	 *  <li> second character is 'colon' and third character is '\', this is to support windows pathes </li>
	 *  <li>starts with file scheme,</li>
	 * </ul>
	 * , a generic URI otherwise.
	 * @param path
	 * @return
	 */
	public URI resolveURI(String path){
		URI uri= null;
		if (	(path!= null)&&
				(!path.isEmpty())){
			char[] seq= path.toCharArray();
			
			if (	(path.startsWith("."))&&
					(getLocation()!= null)){
				//if path is relative resolve against location of this document
				uri= URI.createFileURI(path).resolve(getLocation());
			}else if (	(path.startsWith("/"))||
						(	(seq.length>= 2)&&
								(seq[1]==':')&&
								(seq[2]=='\\'))){
				uri= URI.createFileURI(path);
			}else if(path.startsWith("file:")){
				//if path uses file scheme create a file uri
				path= path.replace("file:///", "");
				path= path.replace("file://", "");
				path= path.replace("file:", "");
				uri= URI.createFileURI(path);
			}else{
				//create generic uri
				uri= URI.createURI(path);
			}			
		}
		return(uri);
	}
}

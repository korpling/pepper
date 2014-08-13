package de.hu_berlin.german.korpling.saltnpepper.pepper.core;

import java.io.File;
import java.util.Properties;

import org.eclipse.emf.common.util.URI;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.StepDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;

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
	
	@Override
	public void startElement(	String uri,
            					String localName,
            					String qName,
            					Attributes attributes)throws SAXException
    {
		
		if (TAG_IMPORTER.equals(qName)){
			//create step desc for importer
			stepDesc= new StepDesc();
			stepDesc.setModuleType(MODULE_TYPE.IMPORTER);
			if (attributes.getValue(ATT_NAME)!= null){
				stepDesc.setName(attributes.getValue(ATT_NAME));
			}
			if (attributes.getValue(ATT_FORMAT_NAME)!= null){
				stepDesc.getCorpusDesc().getFormatDesc().setFormatName(attributes.getValue(ATT_FORMAT_NAME));
				if (attributes.getValue(ATT_FORMAT_VERSION)!= null)
					stepDesc.getCorpusDesc().getFormatDesc().setFormatVersion(attributes.getValue(ATT_FORMAT_VERSION));
			}
			if (attributes.getValue(ATT_PATH)!= null){
				File pathFile= resolveFile(attributes.getValue(ATT_PATH));
				if (pathFile!= null){
					URI path= URI.createFileURI(pathFile.getAbsolutePath());
					stepDesc.getCorpusDesc().setCorpusPath(path);	
				}
			}
		}else if (TAG_MANIPULATOR.equals(qName)){
			//create step desc for manipulator
		}else if (TAG_EXPORTER.equals(qName)){
			//create step desc for exporter
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
		}
		
		
		if (stepDesc!= null){
			getPepperJob().addStepDesc(stepDesc);
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
			props.put(propName, str.toString());
		}
	}
	
	public File resolveFile(String fileStr){
		File retFile= null;
		if (	(fileStr!= null)&&
				(!fileStr.isEmpty())){
			//clean str in case of it was uri
				fileStr= fileStr.replace("file:", "");
				fileStr= fileStr.replace("///", "/");
				fileStr= fileStr.replace("//", "/");
			//clean str in case of it was uri
				
			File file= new File(fileStr);
			
			if (file!= null){
				if (!file.isAbsolute()){
					if (getLocation()!= null){
						File location= new File(getLocation().toFileString());
						if (!location.isDirectory()){
							location= location.getParentFile();
						}
						file= new File(fileStr.replace("./", ""));
						retFile= new File(location.getAbsolutePath()+"/"+file);
					}else{
						throw new PepperFWException("An error reading pepper-params file occured, there was an relative uri '"+fileStr+"', but the base path to resolve it (via setLocation()) was not set. "); 
					}
				}else{
					retFile= new File(fileStr);
				}
			}
		}
		return(retFile);
	}
}

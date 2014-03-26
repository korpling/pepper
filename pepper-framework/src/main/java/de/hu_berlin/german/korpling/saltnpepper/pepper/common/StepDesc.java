package de.hu_berlin.german.korpling.saltnpepper.pepper.common;

import java.util.Enumeration;
import java.util.Properties;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;

/**
 * This class represents a description of a single step in Pepper. A step is a conceptual unit, to which exactly
 * one {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} instance belongs to. Such a step can belong to either the import phase,
 * the manipulation phase or the export phase. 
 * <br/>
 * This class is used as a communication bridge between the Pepper framework and the outside. When
 * using Pepper as a library or a service, one can create {@link StepDesc} object which an abstract
 * description of which module is to use (by setting the name of module and its version) and
 * in case of the module is an importer or an exporter, the description of the path, where the 
 * corpus is located.  
 * <br/>
 * The main fields of this class are:
 * <ul>
 * 	<li>{@link #name} - the name of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} to be used to identify {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}</li>
 *  <li>{@link #version} - the version of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} to be used to identify the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}</li>
 *  <li>{@link #corpusDesc} - an object describing the corpus to be imported or exported (just in case of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} is a {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter} or a {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter}). This object contains a description of the format (name and version) and a path, where to find the corpus.</li>
 *  <li>{@link #props} - a general property object (see: {@link Properties}) to customize the mapping process</li>
 * </ul>
 * 
 * @author Florian Zipser
 *
 */
public class StepDesc {
	
	/** Type of module to be used **/
	private MODULE_TYPE moduleType= null;
	/**
	 * Returns the type of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} to be used.
	 * @return module type
	 */
	public MODULE_TYPE getModuleType() {
		return moduleType;
	}
	/**
	 * Sets the type of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} to be used.
	 * @param moduleType
	 */
	public synchronized void setModuleType(MODULE_TYPE moduleType) {
		this.moduleType = moduleType;
	}

	/** Name of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} to be used for this step.**/
	private String name= null;
	/**
	 * Returns the name of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} to be used for this step.
	 * @return name of {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} to be used for this step.
	 * @param name of {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}
	 */
	public synchronized void setName(String name) {
		this.name = name;
	}
	
	/** Version of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} to be used for this step.**/
	private String version= null;
	/**
	 * Returns the version of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} to be used for this step.
	 * @return version of {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * Sets the version of the {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} to be used for this step.
	 * @param version of {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}
	 */
	public synchronized void setVersion(String version) {
		this.version = version;
	}
	/** 
	 * Object to describe all infos about the corpus to im- or export. This includes the following:
	 * <ul>
	 * 	<li>location of the corpus</li>
	 *  <li>name of the format, the corpus is in or has to be exported in</li>
	 *  <li>version of the format, the corpus is in or has to be exported in</li>
	 * </ul>
	 **/
	private CorpusDesc corpusDesc= null;
	/**
	 * Returns an object to describe all infos about the corpus to im- or export. This includes the following:
	 * <ul>
	 * 	<li>location of the corpus</li>
	 *  <li>name of the format, the corpus is in or has to be exported in</li>
	 *  <li>version of the format, the corpus is in or has to be exported in</li>
	 * </ul>
	 * If no {@link CorpusDesc} object is set, one will be created.
	 * @return
	 */
	public CorpusDesc getCorpusDesc() {
		if (corpusDesc== null){
			synchronized (this) {
				if (corpusDesc== null){
					corpusDesc= new CorpusDesc();
				}
			}
		}
		return corpusDesc;
	}
	/**
	 * Sets an object to describe all infos about the corpus to im- or export. This includes the following:
	 * <ul>
	 * 	<li>location of the corpus</li>
	 *  <li>name of the format, the corpus is in or has to be exported in</li>
	 *  <li>version of the format, the corpus is in or has to be exported in</li>
	 * </ul>
	 * @param corpusDesc
	 */
	public synchronized void setCorpusDesc(CorpusDesc corpusDesc) {
		this.corpusDesc = corpusDesc;
	}
	/** object for temporary storage of customization properties **/
	private Properties props= null;
	
	/**
	 * Returns the object for temporary storage of customization properties. This object is
	 * used to create {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties}.
	 * @return
	 */
	public Properties getProps() {
		return props;
	}

	/**
	 * If {@link #setPepperModule(de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule)} was already called, the passed {@link Properties} 
	 * object is used to create a {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties} object, which is set to given
	 * {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}.
	 * @param props properties to customize processing
	 */
	public synchronized void setProps(Properties props) {
		this.props = props;
	}
	
	public void toXML(XMLStreamWriter writer){
		if (writer!= null){
			try {
				writer.writeStartElement("step");
				if (getModuleType()!= null)
					writer.writeAttribute("type", getModuleType().toString());
				if (getName()!= null)
					writer.writeAttribute("moduleName", getName());
				if (getVersion()!= null)
					writer.writeAttribute("moduleVersion", getVersion());
				String formatName= getCorpusDesc().getFormatDesc().getFormatName();
				if (formatName!= null)
					writer.writeAttribute("formatName", formatName);
				String formatVersion= getCorpusDesc().getFormatDesc().getFormatVersion();
				if (formatVersion!= null)
					writer.writeAttribute("formatVersion", formatVersion);
				URI path= getCorpusDesc().getCorpusPath();
				if (path!= null)
					writer.writeAttribute("path", path.toString());
				if (getProps()!= null){
					writer.writeStartElement("customization");
					Enumeration<Object> keys= getProps().keys();
					while (keys.hasMoreElements()){
						String name= keys.nextElement().toString();
						String value= getProps().getProperty(name);
						writer.writeStartElement("property");
						writer.writeAttribute("name", name);
						writer.writeCharacters(value);
						writer.writeEndElement();
					}
					writer.writeEndElement();
				}
			writer.writeEndElement();
			} catch (XMLStreamException e) {
				throw new PepperFWException("Cannot marshall StepDesc.", e);
			}
		}
	}
	/**
	 * Returns a string representation of this object. 
	 * <strong>Note: This representation cannot be used for serialization/deserialization purposes.</strong>
	 */
	@Override
	public String toString(){
		StringBuilder str= new StringBuilder();
		if (getModuleType()!= null){ 
			str.append(getModuleType().toString());
		}else str.append("UNKNOWN_STEP");
		str.append("(");
		str.append((getName()!=null)?getName():"");
		str.append((getVersion()!=null)?", "+getVersion():"");
		str.append(")");
		return(str.toString());
	}
}

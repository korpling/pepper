package org.corpus_tools.pepper.service.adapters;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;
import org.eclipse.emf.common.util.URI;

@XmlRootElement
@XmlSeeAlso({MODULE_TYPE.class, FormatDescMarshallable.class})
public class PepperModuleDescMarshallable implements PepperMarshallable<PepperModuleDesc>{

	public PepperModuleDescMarshallable() {
		this.supportedFormats = new ArrayList<FormatDescMarshallable>();
		this.properties = new ArrayList<PepperModulePropertyMarshallable<?>>();
	}
	
	@Override
	public PepperModuleDesc getPepperObject() {
		PepperModuleDesc retVal = new PepperModuleDesc();
		for(FormatDescMarshallable format : supportedFormats){
			retVal.addSupportedFormat(format.getFormatName(), format.getFormatVersion(), URI.createURI(format.getFormatReferenceURI()));
		}
		retVal.setName(name);
		retVal.setModuleType(moduleType);
		retVal.setDesc(desc);
		PepperModuleProperties props = new PepperModuleProperties();
		for (PepperModulePropertyMarshallable<?> p : properties){
			props.addProperty(p.getPepperObject());
		}
		retVal.setProperties(props);
		retVal.setSupplierContact(URI.createURI(supplierContactURI));
		retVal.setSupplierHomepage(URI.createURI(homepageURI));
		retVal.setVersion(version);
		return retVal;
	}
	
	private String name;
	
	@XmlElement
	public String getName(){
		return name;
	}
	
	@XmlElement
	public void setName(String name){
		this.name = name;
	}
	
	private String version;
	
	@XmlElement
	public String getVersion(){
		return version;
	}
	
	@XmlElement
	public void setVersion(String version){
		this.version = version;
	}
	
	private MODULE_TYPE moduleType;
	
	@XmlElement
	public MODULE_TYPE getModuleType(){
		return moduleType;
	}
	
	@XmlElement
	public void setModuleType(MODULE_TYPE moduleType){
		this.moduleType = moduleType;
	}
	
	private String desc;
	
	@XmlElement
	public String getDesc(){
		return desc;
	}
	
	@XmlElement
	public void setDesc(String desc){
		this.desc = desc;
	}
	
	private String supplierContactURI;
	
	@XmlElement
	public String getSupplierContactURI(){
		return supplierContactURI;
	}
	
	@XmlElement
	public void setSupplierContactURI(String supplierContactURI){
		this.supplierContactURI = supplierContactURI;
	}
	
	private String homepageURI;
	
	@XmlElement
	public String getHomepageURI(){
		return homepageURI;
	}
	
	@XmlElement
	public void setHomepageURI(String homepageURI){
		this.homepageURI = homepageURI;
	}
	
	private List<FormatDescMarshallable> supportedFormats;
	
	@XmlElements({@XmlElement(name="format", type=FormatDescMarshallable.class)})
	@XmlElementWrapper(name="supportedFormats")
	public List<FormatDescMarshallable> getSupportedFormats(){
		return supportedFormats;
	}
	
	private List<PepperModulePropertyMarshallable<?>> properties;
	
	@XmlElements({@XmlElement(name="moduleProperty", type=PepperModulePropertyMarshallable.class)})
	@XmlElementWrapper(name="moduleProperties")
	public List<PepperModulePropertyMarshallable<?>> getProperties(){
		return properties;
	}
	

}

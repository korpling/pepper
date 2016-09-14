package org.corpus_tools.pepper.service.lib.adapters;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;

@XmlRootElement
@XmlSeeAlso({PepperModulePropertyMarshallable.class})
public class PepperModulePropertiesMarshallable implements PepperMarshallable<PepperModuleProperties>{

	private List<PepperModulePropertyMarshallable<?>> properties;
	
	public PepperModulePropertiesMarshallable() {
		properties = new ArrayList<PepperModulePropertyMarshallable<?>>(); 
	}
	
	@Override
	public PepperModuleProperties getPepperObject() {
		PepperModuleProperties retVal = new PepperModuleProperties();
		for (PepperModulePropertyMarshallable<?> p : properties){
			retVal.addProperty(p.getPepperObject());			
		}
		return retVal;
	}
	
//	public void addProperty(PepperModulePropertyMarshallable<?> property){
//		properties.add(property);
//	}	
	
	@XmlElement
	public List<PepperModulePropertyMarshallable<?>> getProperties(){
		return properties;
	}
}

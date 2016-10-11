package org.corpus_tools.pepper.service.adapters;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

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
	
	public PepperModulePropertiesMarshallable(Properties properties){
		this();		
		Enumeration<?> enm = properties.propertyNames();
		String pname = null;
		Object val = null;
		while (enm.hasMoreElements()){
			pname = (String) enm.nextElement();
			val = properties.getProperty(pname);
			if (val instanceof String){
				PepperModulePropertyMarshallable<String> pmp = new PepperModulePropertyMarshallable<String>();
				pmp.setValue((String) val);
				pmp.setName(pname);
			}
			else if (val instanceof Boolean){
				PepperModulePropertyMarshallable<Boolean> pmp = new PepperModulePropertyMarshallable<Boolean>();
				pmp.setValue((Boolean) val); 
				pmp.setName(pname);
			}
			else if (val instanceof Integer){
				PepperModulePropertyMarshallable<Integer> pmp = new PepperModulePropertyMarshallable<Integer>();
				pmp.setValue((Integer) val);
				pmp.setName(pname);
			}
			else if (val instanceof Double){
				PepperModulePropertyMarshallable<Double> pmp = new PepperModulePropertyMarshallable<Double>();
				pmp.setValue((Double) val);
				pmp.setName(pname);
			}
		}
	}
	
	@Override
	public PepperModuleProperties getPepperObject() {
		PepperModuleProperties retVal = new PepperModuleProperties();
		for (PepperModulePropertyMarshallable<?> p : properties){
			retVal.addProperty(p.getPepperObject());			
		}
		return retVal;
	}
	
	@XmlElement
	public List<PepperModulePropertyMarshallable<?>> getProperties(){
		return properties;
	}
}

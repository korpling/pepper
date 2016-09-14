package org.corpus_tools.pepper.service.lib.adapters;

import java.util.Properties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;

/**
 * This class is not necessary. StepDesc has a method toXML(), which can be used for marshalling.
 * For unmarshalling there is a separate reader class.
 * @author klotzmaz
 *
 */
@Deprecated
@XmlRootElement
public class StepDescMarshallable implements PepperMarshallable<StepDesc>{
	
	/**
	 * Constructor for marshalling.
	 * @param desc
	 */
	public StepDescMarshallable(StepDesc desc) {}

	/**
	 * Constructor for unmarshalling.
	 */
	@Override
	public StepDesc getPepperObject() {
		//TODO
		return null;
	}
	
	private MODULE_TYPE moduleType;
	
	@XmlElement
	private void setModuleType(MODULE_TYPE moduleType){
		this.moduleType = moduleType;
	}
	
	private String name;
	
	@XmlElement
	private void setName(String name){
		this.name = name;
	}
	
	private String version;
	
	@XmlElement
	private void setVersion(String version){
		this.version = version;
	}
	
	//note: we don't need to marshal the CorpusDesc object, since this information will be uploaded to the server (TODO Check if that does not cause any trouble)
	
	private Properties properties;
	
	@XmlElement
	public void setProperties(Properties properties){
		this.properties = properties;
	}
	
	

}

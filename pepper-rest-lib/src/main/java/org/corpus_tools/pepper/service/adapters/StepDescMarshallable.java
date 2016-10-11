package org.corpus_tools.pepper.service.adapters;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;

/**
 * This class is not necessary. StepDesc has a method toXML(), which can be used for marshalling.
 * For unmarshalling there is a separate reader class.
 * @author klotzmaz
 *
 */
@XmlRootElement
@XmlSeeAlso({CorpusDescMarshallable.class})
public class StepDescMarshallable implements PepperMarshallable<StepDesc>{
	
	/**
	 * Constructor for marshalling.
	 * @param desc
	 */
	public StepDescMarshallable(StepDesc desc) {
		this.moduleType = desc.getModuleType();
		this.name = desc.getName();
		this.version = desc.getVersion();
		this.properties = new PepperModulePropertiesMarshallable(desc.getProps());
		this.setCorpusDesc(new CorpusDescMarshallable(desc.getCorpusDesc()));
	}
	

	/**
	 * Constructor for unmarshalling.
	 */
	public StepDescMarshallable() {
		this.properties = new PepperModulePropertiesMarshallable();		
	}

	@Override
	public StepDesc getPepperObject() {
		StepDesc stepDesc = new StepDesc();
		if (corpusDesc != null){
			stepDesc.setCorpusDesc(this.corpusDesc.getPepperObject());
		}		
		stepDesc.setModuleType(this.moduleType);
		stepDesc.setName(this.name);
		stepDesc.setProps(getProperties().getPepperObject().getProperties()); //TODO check if that's correct
		stepDesc.setVersion(this.version);
		return stepDesc;
	}
	
	private CorpusDescMarshallable corpusDesc;
	
	@XmlElement
	public CorpusDescMarshallable getCorpusDesc(){
		return this.corpusDesc;
	}
	
	public void setCorpusDesc(CorpusDescMarshallable corpusDesc){
		this.corpusDesc = corpusDesc;
	}
	
	private MODULE_TYPE moduleType;
	
	@XmlElement
	public MODULE_TYPE getModuleType(){
		return this.moduleType;
	}
	
	public void setModuleType(MODULE_TYPE moduleType){
		this.moduleType = moduleType;
	}
	
	private String name;
	
	@XmlElement
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	private String version;
	
	@XmlElement
	public String getVersion(){
		return this.version;
	}
	
	public void setVersion(String version){
		this.version = version;
	}
	
	private PepperModulePropertiesMarshallable properties;
	
	@XmlElement
	public PepperModulePropertiesMarshallable getProperties(){
		return this.properties;
	}
}

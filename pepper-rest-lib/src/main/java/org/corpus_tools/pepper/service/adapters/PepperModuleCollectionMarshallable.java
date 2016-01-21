package org.corpus_tools.pepper.service.adapters;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;

@XmlRootElement
@XmlSeeAlso({PepperModuleDescMarshallable.class})
public class PepperModuleCollectionMarshallable implements PepperMarshallable<Collection<PepperModuleDesc>>{
	private Collection<PepperModuleDescMarshallable> marshallables;
	private Collection<PepperModuleDesc> pepperObjects;
	
	public PepperModuleCollectionMarshallable(){
		this.marshallables = new ArrayList<PepperModuleDescMarshallable>();
	}
	
	public PepperModuleCollectionMarshallable(Collection<PepperModuleDesc> moduleList){
		this();
		for (PepperModuleDesc moduleDesc : moduleList){
			if (moduleDesc!=null) {this.marshallables.add(new PepperModuleDescMarshallable(moduleDesc));}
		}
		this.pepperObjects = moduleList;
	}
	
	@XmlElements({@XmlElement(name="PepperModule", type=PepperModuleDescMarshallable.class)})
	@XmlElementWrapper(name="pepperModuleDescs")
	public Collection<PepperModuleDescMarshallable> getModuleList(){
		return this.marshallables;
	}

	@Override
	public Collection<PepperModuleDesc> getPepperObject() {
		return this.pepperObjects;
	}	
}

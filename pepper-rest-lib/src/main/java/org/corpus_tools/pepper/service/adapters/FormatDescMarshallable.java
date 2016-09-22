package org.corpus_tools.pepper.service.adapters;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;
import org.eclipse.emf.common.util.URI;

@XmlRootElement
public class FormatDescMarshallable implements PepperMarshallable<FormatDesc>{

	public FormatDescMarshallable() {}
	
	public FormatDescMarshallable(FormatDesc formatDesc){
		this.formatName = formatDesc.getFormatName();
		if (formatDesc.getFormatReference()!=null) {this.formatReferenceURI = formatDesc.getFormatReference().toString();}
		this.formatVersion = formatDesc.getFormatVersion();
	}
	
	@Override
	public FormatDesc getPepperObject() {
		FormatDesc retVal = new FormatDesc();
		retVal.setFormatName(formatName);
		retVal.setFormatReference(URI.createURI(formatReferenceURI));
		retVal.setFormatVersion(formatVersion);
		return retVal;
	}	
	
	private String formatName;
	
	@XmlElement
	public String getFormatName(){
		return formatName;
	}
	
	public void setFormatName(String formatName){
		this.formatName = formatName;
	}
	
	private String formatVersion;
	
	@XmlElement
	public String getFormatVersion(){
		return formatVersion;
	}
	
	public void setFormatVersion(String formatVersion){
		this.formatVersion = formatVersion;
	}
	
	private String formatReferenceURI;
	
	@XmlElement
	public String getFormatReferenceURI(){
		return formatReferenceURI;
	}
	
	public void setFormatReferenceURI(String formatReferenceURI){
		this.formatReferenceURI = formatReferenceURI;
	}
	
}

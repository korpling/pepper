package org.corpus_tools.pepper.service.adapters;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;

@XmlRootElement
@XmlSeeAlso({FormatDescMarshallable.class})
public class CorpusDescMarshallable implements PepperMarshallable<CorpusDesc>{
	
	/**
	 * This is the marshalling constructor.
	 */
	public CorpusDescMarshallable() {}
	
	/**
	 * This is the wrapper constructor.
	 * @param corpusDesc
	 */
	public CorpusDescMarshallable(CorpusDesc corpusDesc){
		this.setCorpusPathURI(corpusDesc.getCorpusPath().toString());
		this.setFormatDesc(new FormatDescMarshallable(corpusDesc.getFormatDesc()));
	}
	
	@Override
	public CorpusDesc getPepperObject() {
		CorpusDesc corpusDesc = new CorpusDesc();
		corpusDesc.setCorpusPath(corpusPathURI);
		if (formatDesc != null) {
			corpusDesc.setFormatDesc(this.formatDesc.getPepperObject());
		}
		return corpusDesc;
	}
	
	private String corpusPathURI;
	
	@XmlElement
	public String getCorpusPathURI(){
		return this.corpusPathURI;
	}
	
	public void setCorpusPathURI(String corpusPathURI){
		this.corpusPathURI = corpusPathURI;
	}
	
	private FormatDescMarshallable formatDesc;
	
	@XmlElement
	public FormatDescMarshallable getFormatDesc(){
		return this.formatDesc;
	}
	
	public void setFormatDesc(FormatDescMarshallable formatDesc){
		this.formatDesc = formatDesc;
	}

}

package org.corpus_tools.pepper.service.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PepperSerializer {
	
	private static final Logger logger = LoggerFactory.getLogger(PepperSerializer.class);
	private static final String ERR_MSG_CREATE_MARSHALLER = "An error occured creating marshaller for class ";
	private static final String ERR_MSG_CREATE_UNMARSHALLER = "An error occured creating marshaller for class ";
	
	private final String DATA_FORMAT;
	
	private static PepperSerializer instance;
	
	private PepperSerializer(String dataFormat){
		DATA_FORMAT = dataFormat;
	}
	
	public static PepperSerializer getInstance(String dataFormat){
		if (instance==null){
			instance = new PepperSerializer(dataFormat);
		} else if (dataFormat.equals(instance.getDataFormat())){
			return instance;
		}
		return null;
	}
	
	public String getDataFormat(){
		return DATA_FORMAT;
	}
	
	/*TODO make private, marshal and unmarshal methods can be used by service*/
	public Marshaller getMarshaller(Class<?> clazz){
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, DATA_FORMAT);
			/*DEBUG TODO remove*/marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			return marshaller;
		} catch (JAXBException e) {
			logger.error(ERR_MSG_CREATE_MARSHALLER+clazz);
			/*DEBUG TODO remove*/e.printStackTrace();
		}			
		return null;
	}
	
	/*TODO make private, marshal and unmarshal methods can be used by service*/
	public Unmarshaller getUnmarshaller(Class<?> clazz){
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, DATA_FORMAT);
			return unmarshaller;
		} catch (JAXBException e) {
			logger.error(ERR_MSG_CREATE_UNMARSHALLER+clazz);
			/*DEBUG TODO remove*/e.printStackTrace();
		}		
		return null;
	}
	
	public PepperMarshallable<?> unmarshal(String source, Class<?> targetClass){
		Unmarshaller um = getUnmarshaller(targetClass);
		try {
			return ((PepperMarshallable<?>)um.unmarshal(new StringReader(source)));
		} catch (JAXBException e) {
			logger.warn("");//TODO ERR_MSG
		}
		return null;
	}
	
	public String marshal(PepperMarshallable<?> marshallable){
		Marshaller m = getMarshaller(marshallable.getClass());
		try {
			StringWriter writer = new StringWriter(); 
			m.marshal(marshallable, writer);
			return writer.toString();
		} catch (JAXBException e) {
			logger.warn("");//TODO ERR_MSG
		}
		return null;
	}
}

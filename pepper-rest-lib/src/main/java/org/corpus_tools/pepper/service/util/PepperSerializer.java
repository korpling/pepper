package org.corpus_tools.pepper.service.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;
import org.corpus_tools.pepper.service.interfaces.PepperService;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PepperSerializer {
	private PepperSerializer(){}
	
	private static final Logger logger = LoggerFactory.getLogger(PepperSerializer.class);
	private static final String ERR_MSG_CREATE_MARSHALLER = "An error occured creating marshaller for class ";
	private static final String ERR_MSG_CREATE_UNMARSHALLER = "An error occured creating marshaller for class ";
	
	/*TODO make private, marshal and unmarshal methods can be used by service*/
	public static Marshaller getMarshaller(Class<?> clazz){
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, PepperService.DATA_FORMAT);
			/*DEBUG TODO remove*/marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			return marshaller;
		} catch (JAXBException e) {
			logger.error(ERR_MSG_CREATE_MARSHALLER+clazz);
			/*DEBUG TODO remove*/e.printStackTrace();
		}			
		return null;
	}
	
	/*TODO make private, marshal and unmarshal methods can be used by service*/
	public static Unmarshaller getUnmarshaller(Class<?> clazz){
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, PepperService.DATA_FORMAT);
			return unmarshaller;
		} catch (JAXBException e) {
			logger.error(ERR_MSG_CREATE_UNMARSHALLER+clazz);
			/*DEBUG TODO remove*/e.printStackTrace();
		}		
		return null;
	}
	
	public static PepperMarshallable<?> unmarshal(String source, Class<?> targetClass){
		Unmarshaller um = getUnmarshaller(targetClass);
		try {
			return ((PepperMarshallable<?>)um.unmarshal(new StringReader(source)));
		} catch (JAXBException e) {
			logger.warn("");//TODO ERR_MSG
		}
		return null;
	}
}

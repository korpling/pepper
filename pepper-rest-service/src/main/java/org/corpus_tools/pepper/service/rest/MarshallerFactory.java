package org.corpus_tools.pepper.service.rest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarshallerFactory {
	private MarshallerFactory(){}
	
	private static final Logger logger = LoggerFactory.getLogger(MarshallerFactory.class);
	private static final String ERR_MSG_CREATE_MARSHALLER = "An error occured creating marshaller for class ";
	private static final String ERR_MSG_CREATE_UNMARSHALLER = "An error occured creating marshaller for class ";
	
	public static Marshaller getMarshaller(Class<?> clazz){
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, PepperRESTService.DATA_FORMAT);	
			return marshaller;
		} catch (JAXBException e) {
			logger.error(ERR_MSG_CREATE_MARSHALLER+clazz);
		}			
		return null;
	}
	
	public static Unmarshaller getUnmarshaller(Class<?> clazz){
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, PepperRESTService.DATA_FORMAT);
			return unmarshaller;
		} catch (JAXBException e) {
			logger.error(ERR_MSG_CREATE_UNMARSHALLER+clazz);
		}		
		return null;
	}
}

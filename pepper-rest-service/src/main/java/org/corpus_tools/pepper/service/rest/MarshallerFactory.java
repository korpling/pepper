package org.corpus_tools.pepper.service.rest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

public class MarshallerFactory {
	private MarshallerFactory(){}
	
	public static Marshaller getMarshaller(Class<?> clazz){
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, PepperRESTService.DATA_FORMAT);	
			return marshaller;
		} catch (JAXBException e) {
			//TODO LOGGING
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
			// TODO LOGGING
		}		
		return null;
	}
}

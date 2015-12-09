package org.corpus_tools.pepper.service.rest;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MarshallerFactory {
	private MarshallerFactory(){}
	
	public static Marshaller getMarshaller(Class<?> clazz){
		return null;
	}
	
	public static Unmarshaller getUnmarshaller(Class<?> clazz){
		return null;
	}
}

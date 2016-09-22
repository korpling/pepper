package org.corpus_tools.pepper.service.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides all classes necessary for the webservice.
 * It's the base class refered to in web.xml.
 * */
//@ApplicationPath("/") //<-- this will be overwritten by declarative files
public class WebApplication extends Application {
	
	private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);
	
	@Override
	public Set<Class<?>> getClasses(){
		if (logger.isDebugEnabled()) {
			logger.debug("Providing classes.");
		}
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(PepperRESTService.class);
		classes.add(MultiPartFeature.class);
//		classes.add(ServiceEventListener.class);
		return classes;
	}
}

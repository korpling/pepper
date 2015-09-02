package de.hu_berlin.german.korpling.saltnpepper.pepper.service;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceEventListener implements ApplicationEventListener{

	@Context ServletContext context;
	private static final Logger logger = LoggerFactory.getLogger(ServiceEventListener.class);
	
	@Override
	public void onEvent(ApplicationEvent arg0) {
		if (logger.isDebugEnabled()) {logger.debug("Event triggered: "+arg0.getType());}
		
		if (ApplicationEvent.Type.INITIALIZATION_FINISHED.toString().equals(arg0.getType().toString())){			
			if (logger.isDebugEnabled() && context!=null){
					printPaths();
					printAttributes();
					printResourceDispatcher();
			}
		}
	}

	@Override
	public RequestEventListener onRequest(RequestEvent arg0) {		
		printAttributes();	
		printPaths();
		
		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append(arg0.getUriInfo().getPath()).append(" (path)");
			sb.append(System.lineSeparator()).append(arg0.getUriInfo().getAbsolutePath()).append(" (absolute path)");
			sb.append(System.lineSeparator()).append(arg0.getUriInfo().getBaseUri()).append(" (base uri)");
			sb.append(System.lineSeparator()).append(arg0.getUriInfo().getLocatorSubResources()).append(" (locator sub resources)");
			sb.append(System.lineSeparator()).append(arg0.getUriInfo().getMatchedURIs()).append(" (matched uris)");
			logger.debug(sb.toString());
			printResourceDispatcher();
		}	
		
		return null;
	}
	
	public void printPaths(){
		logger.debug(getPaths("/"));
	}
	
	private String getPaths(String startPath){
		StringBuilder retVal = new StringBuilder();
		Set<?> paths = context.getResourcePaths(startPath);
		Iterator<?> pathIterator = paths.iterator();
		String next = null;
		while (pathIterator.hasNext()){
			next = pathIterator.next().toString();			
			retVal.append(next).append("\treal path:\t").append(context.getRealPath(next)).append(System.lineSeparator());
			retVal.append(getPaths(next));
		}
		return retVal.toString();
	}
	
	private void printResourceDispatcher(){		
		StringBuilder b = new StringBuilder();
		b.append("----------------------------------------").append(System.lineSeparator()).append("resource dispatcher(s)").append(System.lineSeparator());
		b.append(context.getNamedDispatcher("pepper-RESTService")).append(" (dispatcher)").append(System.lineSeparator());		
		b.append(context.getContext("/resource").getNamedDispatcher("pepper-RESTService")).append(" (dispatcher)");		
		logger.debug(b.toString());
	}
	
	private void printAttributes(){
		Enumeration<?> attributes = context.getAttributeNames();
		Object attr = null;
		while (attributes.hasMoreElements()){
			attr = attributes.nextElement();
			logger.debug("\tKEY: "+attr+"\tVAL:"+context.getAttribute(attr.toString()));
		}
	}
	
}

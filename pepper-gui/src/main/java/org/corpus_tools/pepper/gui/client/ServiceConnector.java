package org.corpus_tools.pepper.gui.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.service.adapters.PepperModuleCollectionMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.util.PepperSerializer;
import org.corpus_tools.pepper.service.util.PepperServiceURLDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceConnector implements PepperServiceURLDictionary{
	private String serviceUrl;
	public Client client = null;
	public WebTarget baseTarget = null;	
	
	private final PepperSerializer serializer;
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceConnector.class);
	private static final String ERR_404 = "Requested resource not available";
	
	public ServiceConnector(String serviceURL){
		this.serviceUrl = serviceURL;
		this.client = ClientBuilder.newClient();
		this.baseTarget = client.target(serviceUrl);		
		serializer = PepperSerializer.getInstance("application/xml");
		
		logger.info("ServiceConnector initialized with base target" + baseTarget + " and Serializer " + serializer);
	}

	public Collection<PepperModuleDesc> getAllModules() {
		ArrayList<PepperModuleDesc> moduleList = new ArrayList<PepperModuleDesc>(); 
		WebTarget target = baseTarget!=null? baseTarget.path(PATH_ALL_MODULES) : client.target("http://localhost:8080/pepper-rest/resource/modules");
		Builder rb = target.request();
		Invocation getInvocation = rb.buildGet();
		Future<Response> futResponse = getInvocation.submit();
		Response response = null;
		try {
			response = futResponse.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		logger.info("Received response: " + response);
		logger.info("Response entity:" + response.getEntity());
		if (response.getStatus() == 404){
			logger.error(ERR_404);
		} else {
			for (PepperModuleDescMarshallable pmdm : ((PepperModuleCollectionMarshallable)serializer.unmarshal(null, PepperModuleCollectionMarshallable.class)).getModuleList()){
				moduleList.add(pmdm.getPepperObject());
			}
		}
		return moduleList;
	}
}

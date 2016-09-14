package org.corpus_tools.pepper.gui.client;

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
import org.corpus_tools.pepper.service.lib.adapters.PepperModuleCollectionMarshallable;
import org.corpus_tools.pepper.service.lib.util.PepperSerializer;
import org.corpus_tools.pepper.service.lib.util.PepperServiceURLDictionary;
import org.corpus_tools.pepper.service.rest.PepperRESTService;

public class ServiceConnector implements PepperServiceURLDictionary{
	private String serviceUrl;
	public Client client = null;
	public WebTarget baseTarget = null;
	
	private PepperSerializer serializer = null; 
	
	public ServiceConnector(String serviceURL){
		this.serviceUrl = serviceURL;
		this.client = ClientBuilder.newClient();
		this.baseTarget = client.target(serviceUrl);
		
		serializer = PepperSerializer.getInstance(PepperRESTService.DATA_FORMAT);
	}

	public Collection<PepperModuleDesc> getAllModules() {
		WebTarget target = baseTarget.path(PATH_ALL_MODULES);
		Builder rb = target.request();
		Invocation getInvocation = rb.buildGet();
		Future<Response> futResponse = getInvocation.submit();
		Response response = null;
		try {
			response = futResponse.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("R E S P O N S E:::" + response);
		serializer.getUnmarshaller(PepperModuleCollectionMarshallable.class);
		
		return null;
	}
}

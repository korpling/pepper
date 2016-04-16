package org.corpus_tools.pepper.gui.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ServiceConnector {
	private String serviceUrl;
	public Client client = null;
	public WebTarget baseTarget = null;
	
	public ServiceConnector(String serviceURL){
		this.serviceUrl = serviceURL;
		this.client = ClientBuilder.newClient();
		this.baseTarget = client.target(serviceUrl);
	}
}

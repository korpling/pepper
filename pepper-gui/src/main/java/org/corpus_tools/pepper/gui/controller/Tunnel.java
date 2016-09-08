package org.corpus_tools.pepper.gui.controller;

import org.corpus_tools.pepper.gui.client.ServiceConnector;

public class Tunnel {
	private ServiceConnector serviceConnector = null;
	
	public Tunnel(String serviceURL){
		serviceConnector = new ServiceConnector(serviceURL);
	}
}

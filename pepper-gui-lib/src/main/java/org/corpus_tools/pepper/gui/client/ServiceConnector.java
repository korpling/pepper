package org.corpus_tools.pepper.gui.client;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.service.adapters.PepperModuleCollectionMarshallable;
import org.corpus_tools.pepper.service.interfaces.PepperService;
import org.corpus_tools.pepper.service.util.PepperSerializer;

public class ServiceConnector {
	private String serviceUrl;
	public Client client = null;
	public WebTarget baseTarget = null;
	
	public ServiceConnector(String serviceURL){
		this.serviceUrl = serviceURL;
		this.client = ClientBuilder.newClient();
		this.baseTarget = client.target(serviceUrl);
	}
	
	public Collection<PepperModuleDesc> getAllModules(MODULE_TYPE moduleType){
		PepperModuleCollectionMarshallable coll = (PepperModuleCollectionMarshallable)PepperSerializer.unmarshal(baseTarget.path(PepperService.PATH_ALL_MODULES).request(PepperService.DATA_FORMAT).get().toString(), PepperModuleCollectionMarshallable.class);
		Collection<PepperModuleDesc> modules = new ArrayList<PepperModuleDesc>();
		for (PepperModuleDesc module : coll.getPepperObject()){
			if (moduleType==null || module.getModuleType().equals(moduleType)){
				modules.add(module);
			}
		}
		return modules;
	}
}

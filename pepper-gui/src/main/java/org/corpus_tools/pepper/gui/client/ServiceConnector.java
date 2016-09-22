package org.corpus_tools.pepper.gui.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.service.adapters.PepperModuleCollectionMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.interfaces.PepperServiceImplConstants;
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
	private static final String ERR_404 = "Requested resource not available.";
	private static final String ERR_REQUEST = "An error occured performing the request.";
	
	public ServiceConnector(String serviceURL){
		this.serviceUrl = serviceURL;
		this.client = ClientBuilder.newClient();
		this.baseTarget = client.target(serviceUrl);		
		serializer = PepperSerializer.getInstance(PepperServiceImplConstants.DATA_FORMAT);
		
		logger.info("ServiceConnector initialized with base target" + baseTarget + " and Serializer " + serializer);		
	}

	public Collection<PepperModuleDesc> getAllModules() {
		ArrayList<PepperModuleDesc> moduleList = new ArrayList<PepperModuleDesc>(); 
		try{				
			HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/pepper-rest/resource/modules")).openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", PepperServiceImplConstants.DATA_FORMAT);
			InputStream xml = connection.getInputStream();
			Collection<PepperModuleDescMarshallable> rawList = ((PepperModuleCollectionMarshallable)serializer.unmarshal(xml.toString(), PepperModuleCollectionMarshallable.class)).getModuleList();
			logger.info("Received list: " + rawList);
			for (PepperModuleDescMarshallable pmdm : rawList){
				moduleList.add(pmdm.getPepperObject());
			}
		} catch (IOException e){
			logger.error(ERR_REQUEST);
		}
	
		return moduleList;
	}
}

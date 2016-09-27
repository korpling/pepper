package org.corpus_tools.pepper.gui.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.corpus_tools.pepper.service.adapters.PepperJobMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperJobReportMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperJobReportMarshallable.PathProgress;
import org.corpus_tools.pepper.service.adapters.PepperModuleCollectionMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.adapters.StepDescMarshallable;
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
	private static final String ERR_NO_CONNECTION = "No connection was built. Request was not performed.";
	private static final String ERR_CONNECTION_INPUT = "Could not read connection input.";
	private static final String ERR_BUILD_CONNECTION(String method, String url){
		StringBuilder builder = new StringBuilder();
		return builder.append("An error occured building a connection of type ")
				.append(method.toString()).append(" to ").append(url).toString();
	}
	
	public ServiceConnector(String serviceURL){
		this.serviceUrl = serviceURL;
		this.client = ClientBuilder.newClient();
		this.baseTarget = client.target(serviceUrl);		
		serializer = PepperSerializer.getInstance(PepperServiceImplConstants.DATA_FORMAT);
		
		logger.info("ServiceConnector initialized with base target" + baseTarget + " and Serializer " + serializer);		
	}

	public Collection<PepperModuleDescMarshallable> getAllModules() {
		ArrayList<PepperModuleDescMarshallable> moduleList = new ArrayList<PepperModuleDescMarshallable>(); 
		try{				
			HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/pepper-rest/resource/modules")).openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", PepperServiceImplConstants.DATA_FORMAT);
			InputStreamReader reader = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(reader);			
			StringBuilder xml = new StringBuilder();
			String line = br.readLine();
			while (line != null){
				xml.append(line);
				line = br.readLine();
			}
			Collection<PepperModuleDescMarshallable> rawList = ((PepperModuleCollectionMarshallable)serializer.unmarshal(xml.toString(), PepperModuleCollectionMarshallable.class)).getModuleList();			
			for (PepperModuleDescMarshallable pmdm : rawList){
				moduleList.add(pmdm);
			}
		} catch (IOException e){
			logger.error(ERR_REQUEST);
		}
	
		return moduleList;
	}

	/**
	 * 
	 * @param configs
	 * @return 
	 * 		Job id
	 */
	public String createJob(List<StepDescMarshallable> configs) {
		PepperJobMarshallable jdm = new PepperJobMarshallable();
		jdm.getSteps().addAll(configs);
		
		String data = serializer.marshal(jdm);
		
		logger.info(data); //TODO shift to debug later
		
		try{				
			HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/pepper-rest/resource/job")).openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);			
			connection.setRequestProperty("Content-Type", PepperServiceImplConstants.DATA_FORMAT);
			connection.setRequestProperty("Accept", MediaType.TEXT_PLAIN);
			connection.setRequestProperty("Content-Length", Integer.toString(data.length()));
			connection.connect();
			/*SEND*/
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(data);
			writer.flush();
			/*RECEIVE*/
			if (connection.getResponseCode() == 200){		
				InputStreamReader reader = new InputStreamReader(connection.getInputStream());
				StringBuilder response = new StringBuilder();
				int c = reader.read();
				while (c != -1){
					response.append((char)c);
					c = reader.read();
				}			
				return response.toString();
			} else {
				logger.error(ERR_REQUEST +" RESPONSE CODE: "+ connection.getResponseCode());
			}
		} catch (IOException e){
			logger.error(ERR_REQUEST);
			//DEBUG:
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * TODO ask Thomas how to use one connection kept open
	 */
	public Collection<PathProgress> getProgress(String jobId) {
		HttpURLConnection connection = getConnection(HttpMethod.GET, "http://localhost:8080/pepper-rest/resource/job/".concat(jobId).concat("/report"), null, PepperServiceImplConstants.DATA_FORMAT);
		try {
			if (connection != null){
				connection.connect();
				String data = getConnectionInput(connection);
				logger.info("Received progress response:"+System.lineSeparator()+data);
				PepperJobReportMarshallable report = (PepperJobReportMarshallable) serializer.unmarshal(data, PepperJobReportMarshallable.class);
				return report
						.getProgressByPath()
						.getCollection();
			} else {
				logger.error(ERR_NO_CONNECTION);
			}
		} catch (IOException e) {
			logger.error(ERR_REQUEST);
		}		
		return Collections.<PathProgress>emptySet();
	}
	
	/**
	 * This method creates a http connection to the desired target.
	 * @param method
	 * 			The method to be used, such as GET or POST.
	 * 			Available methods are listed in {@link HttpMethod} interface.
	 * @param targetUrl
	 * 			URL for target of connection, must not be null.
	 * @param sendType
	 * 			The media type to be sent, such as "application/xml" etc.
	 * 			Set to null if not needed.
	 * @param acceptType
	 * 			The media type to be received, such as "application/xml" etc.
	 * 			Set to null if not needed.
	 * @return
	 * 			The {@link HttpURLConnection} to the target (not connected, data length not configured).
	 */
	private HttpURLConnection getConnection(String method, String targetUrl, String sendType, String acceptType){
		HttpURLConnection connection = null;
		if (targetUrl == null){
			return null;
		}
		try {
			connection = (HttpURLConnection) (new URL(targetUrl)).openConnection();
			connection.setRequestMethod(method);
			if (HttpMethod.POST.equals(method)){
				connection.setDoOutput(true);
			}
			if (acceptType == null){
				connection.setDoInput(false);
			} else {
				connection.setRequestProperty("Accept", acceptType);
			}
			if (sendType != null){
				connection.setRequestProperty("Content-Type", sendType);
			}						
		} catch (IOException e) {
			logger.error(ERR_BUILD_CONNECTION(method, targetUrl));
		}		
		return connection;
	}
	
	/**
	 * This method reads the input from the given connection.
	 * @param connection
	 * @return
	 */
	private String getConnectionInput(HttpURLConnection connection){
		StringBuilder data = new StringBuilder();
		try{
			InputStreamReader reader = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(reader);			
			
			String line = br.readLine();
			while (line != null){
				data.append(line);
				line = br.readLine();
			} 
		}catch (IOException e){
			logger.error(ERR_CONNECTION_INPUT);
		}
		return data.toString();
	}
}

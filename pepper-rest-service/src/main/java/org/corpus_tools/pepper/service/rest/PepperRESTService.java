package org.corpus_tools.pepper.service.rest;

import java.net.URI;
import java.util.HashMap;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.service.interfaces.PepperService;
import org.corpus_tools.pepper.service.osgi.Activator;

@WebService
@Path("/resource")
public class PepperRESTService extends Activator implements PepperService{

	public static final String DATA_FORMAT = "application/xml";
	
	@GET
	@Path("compliment")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
    public String getCompliment(@QueryParam("politePhrase") String input){
		if ("stp".equals(input)){
			return "Your laces look ironed.";
		}
		return "You said ".concat(input);
	}
	
	@Override
	public double getProgress(String runningModuleId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public URI getResultsAsZip(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getResultsAsTAR(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getWorkflowXML(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Produces(DATA_FORMAT)
	public String getModuleDescription(String moduleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Produces(DATA_FORMAT)
	public String getModuleProperties(String moduleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Produces(DATA_FORMAT)
	public String getModuleType(String moduleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewJob() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String startConversion(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String killJob(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cancelConversion(String jobId, String DocumentId) {
		// TODO Auto-generated method stub
		return null;
	}
}

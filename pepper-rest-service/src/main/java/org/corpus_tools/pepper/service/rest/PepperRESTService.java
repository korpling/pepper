package org.corpus_tools.pepper.service.rest;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.corpus_tools.pepper.service.interfaces.PepperService;
import org.corpus_tools.pepper.service.osgi.Activator;

@WebService
@Path("/resource")
public class PepperRESTService extends Activator implements PepperService{

	public static final String DATA_FORMAT = MediaType.APPLICATION_XML;
	
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

	/**
	 * This method provides a module description, including supported formats, version,
	 * properties, etc.
	 */
	@GET
	@Path("module")
	@Produces(DATA_FORMAT)
	@Consumes(MediaType.TEXT_PLAIN)
	@Override
	public String moduleDescription(@QueryParam("name") String moduleName) {
		// TODO Auto-generated method stub
		return null;
	}
}

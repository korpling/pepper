package org.corpus_tools.pepper.service.rest;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.service.interfaces.PepperService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@WebService
@Path("/resource")
@Component(name = "PepperRESTService", immediate = true)
public class PepperRESTService implements PepperService{
		
	private Pepper pepper = null;

	@Reference(unbind = "unsetPepper", cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC)
	public void setPepper(Pepper pepperInstance) {
	    this.pepper = pepperInstance;
	    System.out.println("setPepper called");
	}

	public void unsetPepper(Pepper pepperInstance) {
	    this.pepper = null;
	}	

	public static final String DATA_FORMAT = MediaType.APPLICATION_XML;	
	
	@GET
	@Path("compliment")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
    public String getCompliment(@QueryParam("politePhrase") String input){
		if ("stp".equals(input)){
			return "Your laces look ironed.";
		}
		if ("pepper".equals(input)){
			return ""+pepper;
		}
		return "You said "+input;
	}
	
	/**
	 * TEST METHOD
	 */
	public String echo(String param){
		String response = "Hello";
//		if (!param.isEmpty()){
//			PepperModuleDesc next = null; 
//			for (Iterator<PepperModuleDesc> iterator = pepper.getRegisteredModules().iterator(); iterator.hasNext(); next = iterator.next()){
//				response.concat(System.lineSeparator()).concat(next.getName());	
//			}
//		}
		return response;
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.service;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hu_berlin.german.korpling.saltnpepper.pepper.service.osgi.Activator;

@WebService
@Path("/resource")
public class MyResource extends Activator{

	@GET
	@Path("compliment")
	@Produces(MediaType.TEXT_PLAIN)
    public String getCompliment(){
		return "Your laces look ironed.";
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.service;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


/**
 * Root resource (exposed at "myresource" path)
 */
@WebService
@Path("/myresource")
public class MyResource extends Activator{

	@GET
	@Path("compliment")
    public String getCompliment(){
		return "Your laces look ironed.";
	}
	
	@POST
	@Path("setName")
	public void setName(String name){
		System.out.println(name);
	}
}

package org.corpus_tools.pepper.service.rest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.interfaces.PepperService;
import org.corpus_tools.pepper.service.osgi.Activator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@WebService
@Path("/resource")
@Component(name = "PepperRESTService", immediate = true)
public class PepperRESTService extends Activator implements PepperService{
	
	private Pepper pepper = null;

	@Reference(unbind = "unsetPepper", cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC)
	public void setPepper(Pepper pepper) {
	    this.pepper = pepper;
	    System.out.println("setPepper called");
	}

	public void unsetPepper(Pepper pepper) {
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
		PepperModuleDesc moduleDesc = null;
		for (Iterator<PepperModuleDesc> iterator = pepper.getRegisteredModules().iterator(); iterator.hasNext(); moduleDesc = iterator.next()){
			if (moduleName.equals(moduleDesc.getName())){
				break;
			}
			else {
				moduleDesc = null;
			}
		}
		if (moduleDesc!=null){
			Marshaller marshaller = MarshallerFactory.getMarshaller(PepperModuleDescMarshallable.class);
			OutputStream out = new ByteArrayOutputStream();
			try {
				marshaller.marshal(new PepperModuleDescMarshallable(moduleDesc), out);
				return out.toString();
			} catch (JAXBException e) {
				// TODO LOGGING
				return "unknown";
			}
		}
		return "unknown";
	}
	
	/**
	 * TEST METHOD
	 */
	public String echo(String param){
		String response = "Hello";
		if (!param.isEmpty()){
			PepperModuleDesc next = null; 
			for (Iterator<PepperModuleDesc> iterator = pepper.getRegisteredModules().iterator(); iterator.hasNext(); next = iterator.next()){
				response.concat(System.lineSeparator()).concat(next.getName());	
			}
		}
		return response;
	}
}

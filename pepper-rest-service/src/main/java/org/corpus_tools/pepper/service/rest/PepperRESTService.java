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
import javax.xml.transform.stream.StreamSource;

import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.interfaces.PepperService;
import org.corpus_tools.pepper.service.osgi.Activator;

@WebService
@Path("/resource")
public class PepperRESTService extends Activator implements PepperService{

	public static final String DATA_FORMAT = MediaType.APPLICATION_XML;
	public static Pepper pepper = null;
	public static boolean isInit = false;
	
	/* TEMPORARY! FIND A BETTER SOLUTION TODO FIXME */
	public static void setPepper(Pepper pepperInstance){
		if (!isInit){
			pepper = pepperInstance;
			isInit = true;
		}		
	}
	
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
	public OutputStream moduleDescription(@QueryParam("name") String moduleName) {
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
				return out;
			} catch (JAXBException e) {
				// TODO LOGGING
				return null;
			}
		}
		return null;
	}
}

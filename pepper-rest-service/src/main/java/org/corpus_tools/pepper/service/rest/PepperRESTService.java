package org.corpus_tools.pepper.service.rest;

import java.io.ByteArrayOutputStream;

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
import org.corpus_tools.pepper.service.adapters.PepperModuleCollectionMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.exceptions.ErrorsExceptions;
import org.corpus_tools.pepper.service.interfaces.PepperService;
import org.corpus_tools.pepper.service.util.PepperSerializer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService
@Path("/resource")
@Component(name = "PepperRESTService", immediate = true)
public class PepperRESTService implements PepperService{
	
	private static Pepper pepper;
	private static final Logger logger = LoggerFactory.getLogger(PepperRESTService.class);
	
	@Reference(unbind = "unsetPepper", cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC)
	public static void setPepper(Pepper pepperInstance) {
	    pepper = pepperInstance;
	    logger.info("pepper instance found: "+pepperInstance);
	    logger.info("pepper set to:"+pepper);
	}

	public void unsetPepper(Pepper pepper) {
	    pepper = null;
	    logger.info("unset pepper");
	}	
	
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
	 * @throws JAXBException 
	 */
	@GET
	@Path("echo")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String echo(@QueryParam("param") String param){
		String response = "Hello";
		if (!param.isEmpty()){			
			//dummy-code
			PepperModuleDesc moduleDesc = pepper.getRegisteredModules().iterator().next();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			Marshaller m = PepperSerializer.getMarshaller(PepperModuleDescMarshallable.class);
			try {
				m.marshal(new PepperModuleDescMarshallable(moduleDesc), stream);
			} catch (JAXBException e) {
				logger.error(ErrorsExceptions.ERR_MSG_MARSHALLING);
			}
			response = stream.toString();
			//end of dummy-code
		}
		return response;
	}
	
	/* === from here on the "real" stuff === */
	
	@GET
	@Path(PATH_ALL_MODULES)
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(DATA_FORMAT)
	@Override
	public String allModules() {
		PepperModuleCollectionMarshallable modules = new PepperModuleCollectionMarshallable(pepper.getRegisteredModules());
		Marshaller m = PepperSerializer.getMarshaller(PepperModuleCollectionMarshallable.class);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {			
			m.marshal(modules, stream);
		} catch (JAXBException e) {
			logger.error(ErrorsExceptions.ERR_MSG_MARSHALLING);
		}
		return stream.toString();
	}	
	
}

package org.corpus_tools.pepper.service.rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperJob;
import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.core.WorkflowDescriptionReader;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@WebService
@Path("/resource")
@Component(name = "PepperRESTService", immediate = true)
public class PepperRESTService implements PepperService, PepperRESTServicePathDictionary{
		
	public static final String DATA_FORMAT = MediaType.APPLICATION_XML;
	
	private static Pepper pepper;
	private static PepperSerializer serializer = PepperSerializer.getInstance(DATA_FORMAT);
	private static final Logger logger = LoggerFactory.getLogger(PepperRESTService.class);
	
	@Reference(unbind = "unsetPepper", cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC)
	public static void setPepper(Pepper pepperInstance) {
	    pepper = pepperInstance;
	    logger.info("pepper set to:"+pepper);
	}

	public void unsetPepper(Pepper pepperInstance) {
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
			Marshaller m = serializer.getMarshaller(PepperModuleDescMarshallable.class);
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
	public String getModuleList() {
		PepperModuleCollectionMarshallable modules = new PepperModuleCollectionMarshallable(pepper.getRegisteredModules());
		Marshaller m = serializer.getMarshaller(PepperModuleCollectionMarshallable.class);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {			
			m.marshal(modules, stream);
		} catch (JAXBException e) {
			logger.error(ErrorsExceptions.ERR_MSG_MARSHALLING);
		}
		return stream.toString();
	}

	@POST
	@Path(PATH_JOB)
	@Consumes(DATA_FORMAT)
	@Produces(MediaType.TEXT_PLAIN)
	@Override
	public String createJob(String jobDesc) {
		if (logger.isDebugEnabled()){
			logger.debug("Received data on path "+PATH_JOB+"/ (POST): "+System.lineSeparator()+jobDesc);
		}
		String id = pepper.createJob();
		PepperJob job = pepper.getJob(id);
		WorkflowDescriptionReader reader = new WorkflowDescriptionReader();
		reader.setPepperJob(job);
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			InputSource is = new InputSource(new StringReader(jobDesc));
			parser.parse(is, reader);			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//START JOB
		return id;
	}

	@GET
	@Path(PATH_JOB)
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Override
	public String getStatusReport(@QueryParam("id") String jobId) {
		PepperJob job = pepper.getJob(jobId);
		return job==null? "no such job" : job.getStatusReport();
		//TODO we need to implement a method that creates an xml report OR an report object that will be made marshallable
	}

	@GET
	@Path(PATH_MODUL)
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(DATA_FORMAT)
	@Override
	public String getModule(@QueryParam("id") String moduleId) {
		// TODO maybe there's no need for calling for just one module since all the information is given by getAllModules
		return null;
	}
	
	@POST
	@Path(PATH_DATA)
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Override
	public void setData(@QueryParam("id") String jobId, byte[] data) {
		PepperJob job = pepper.getJob(jobId);		
		if (job!=null){
			String jobDirName = jobId+File.separator;
			File jobDir = new File(jobDirName);
			String dataDir = job.getStepDescs().get(0).getCorpusDesc().getCorpusPath().lastSegment();
			jobDir.mkdir();			
			try {
				FileOutputStream outstream = new FileOutputStream(jobDirName+dataDir+".zip");
				outstream.write(data);				
				logger.info("DATA DIR="+dataDir);
				outstream.close();
			} catch (IOException e) {
				logger.error("Writing data failed");
				e.printStackTrace();
			}			
		}		
	}

	@GET
	@Path(PATH_DATA)
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Override
	public byte[] getConvertedDocuments(@QueryParam("id") String jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

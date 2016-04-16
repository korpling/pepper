package org.corpus_tools.pepper.service.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.corpus_tools.pepper.common.JOB_STATUS;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperJob;
import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.core.WorkflowDescriptionReader;
import org.corpus_tools.pepper.service.adapters.PepperModuleCollectionMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.exceptions.ErrorsExceptions;
import org.corpus_tools.pepper.service.interfaces.PepperService;
import org.corpus_tools.pepper.service.interfaces.PepperServiceImplConstants;
import org.corpus_tools.pepper.service.internal.DataManager;
import org.corpus_tools.pepper.service.util.PepperSerializer;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.io.Files;

@WebService
@Path("/resource")
@Component(name = "PepperRESTService", immediate = true)
public class PepperRESTService implements PepperService, PepperServiceImplConstants{
		
	/* statics */
	private static Pepper pepper;
	private static PepperSerializer serializer = PepperSerializer.getInstance(DATA_FORMAT);
	
	private static final Logger logger = LoggerFactory.getLogger(PepperRESTService.class);
	
	@Reference(unbind = "unsetPepper", cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC)
	public static void setPepper(Pepper pepperInstance) {
	    pepper = pepperInstance;
	    logger.info("pepper set to:"+pepper);
	    (new File(LOCAL_JOB_DIR)).mkdir();
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
		job.setBaseDir(job.getBaseDir().appendSegment(id));
		DataManager.getInstance().put(id);//FIXME THIS IS JUST DEBUG ! move to getStatusReport() where job is started and put it on the dataManager as soon as the job is finished.  
		return id;
	}

	@GET
	@Path(PATH_JOB)
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN) //TODO XML
	@Override
	public String getStatusReport(@QueryParam("id") String jobId) {
		PepperJob job = pepper.getJob(jobId);
		if (job==null){
			return "No such job"; //TODO XML
		}
		if (JOB_STATUS.NOT_STARTED.equals(job.getStatus())){
			logger.info("Job "+jobId+" started.");
			job.convert();
		}
		return job.getStatusReport(); //TODO XML
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
	@Path(PATH_DATA+"/{id}/{path}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Override
	public void setData(@PathParam("id") String jobId, @PathParam("path") String importDirName, byte[] data){
		if (importDirName.endsWith("/")){
			importDirName = importDirName.substring(0, importDirName.length()-1);
		}
		PepperJob job = pepper.getJob(jobId);		
		if (job!=null){
			String jobDirName = LOCAL_JOB_DIR+jobId+File.separator;
			(new File(jobDirName)).mkdir();
			String dataDir = importDirName;
			String zipFileName = jobDirName+dataDir+".zip";
			try {
				FileOutputStream outstream = new FileOutputStream(zipFileName);
				outstream.write(data);				
				logger.info("DATA for job "+jobId+" transferred to "+dataDir+".");
				outstream.close();
			} catch (IOException e) {
				logger.error("Writing data failed.");
				e.printStackTrace();
			}
			File stepDir = null;
			String newTarget = null;
			for (StepDesc stepDesc : job.getStepDescs()){
				if (MODULE_TYPE.EXPORTER.equals(stepDesc.getModuleType())){
					newTarget = TARGET_ROOT_DIR+stepDesc.getCorpusDesc().getCorpusPath().lastSegment();
					stepDir = new File(newTarget);
					stepDir.mkdirs();
					stepDesc.getCorpusDesc().setCorpusPath(URI.createURI(newTarget));
				}
			}
			
			/* unzip */
			try {
				ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFileName));
				File file = null;
				FileOutputStream outstream = null;
				ZipEntry entry = zip.getNextEntry();
				byte[] bytes = new byte[1024];
				if (logger.isDebugEnabled()){
					logger.debug("Extracting transferred data ...");
				}
				while (entry!=null){
					if (logger.isDebugEnabled()){
						logger.debug("\t"+entry.getName());
					}
					if (entry.isDirectory()){
						file = new File(jobDirName+importDirName+"/"+entry.getName());
						file.mkdirs();
					} else {
						file = new File(importDirName+"/"+entry.getName());
						outstream = new FileOutputStream(jobDirName+file);	
						while (zip.read(bytes) > 0){
							outstream.write(bytes);
						}
						outstream.close();						
					}
					entry = zip.getNextEntry();
				}
				zip.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	/**
	 * @return The converted data, iff conversion has finished, else null.
	 */
	@GET
	@Path(PATH_DATA+"/{id}/{path}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Override
	public byte[] getConvertedDocuments(@PathParam("id") String jobId, @PathParam("path") String exportPath) {
		if (exportPath.endsWith("/")){
			exportPath = exportPath.substring(0, exportPath.length()-1);
		}
		PepperJob job = pepper.getJob(jobId);
		File dataDir = new File(LOCAL_JOB_DIR+jobId+File.separator+exportPath);
		File zipFile = new File(LOCAL_JOB_DIR+jobId+File.separator+exportPath+".zip");
		if (/*JOB_STATUS.ENDED.equals(job.getStatus()) &&*/ dataDir.exists()){ //TODO what to do in case ENDED_WITH_ERRORS?			
			try{
				logger.info("Providing requested files: "+jobId+File.separator+exportPath);
				if (!zipFile.exists()){				
					FileOutputStream fos = new FileOutputStream(zipFile);
					ZipOutputStream zipout = new ZipOutputStream(fos);
					ZipEntry entry = null;
					for (File file : FileUtils.listFilesAndDirs(dataDir, TrueFileFilter.INSTANCE, DirectoryFileFilter.DIRECTORY)){
						entry = new ZipEntry(file.getName());					
						zipout.putNextEntry(entry);
						if (!file.isDirectory()){
							Files.copy(file, zipout);
						}
						logger.info("\tZipping "+entry.getName());
						zipout.closeEntry();
					}
					zipout.close();				
				}
				FileInputStream fis = new FileInputStream(zipFile);
				BufferedInputStream input = new BufferedInputStream(fis);
				byte[] data = new byte[(int) zipFile.length()];
				input.read(data);
				input.close();
				return data;
			} catch (IOException e) {
				logger.error("Could not zip files.");//TODO
				e.printStackTrace();
			}
		}
		return ArrayUtils.EMPTY_BYTE_ARRAY;
	}

	
}

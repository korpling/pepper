package org.corpus_tools.pepper.service.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.JOB_STATUS;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperJob;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.exceptions.JobNotFoundException;
import org.corpus_tools.pepper.service.adapters.PepperJobMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperJobReportMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModuleCollectionMarshallable;
import org.corpus_tools.pepper.service.adapters.StepDescMarshallable;
import org.corpus_tools.pepper.service.interfaces.JobSignal;
import org.corpus_tools.pepper.service.interfaces.PepperServiceImplConstants;
import org.corpus_tools.pepper.service.internal.PepperServiceJobRunner;
import org.corpus_tools.pepper.service.util.PepperSerializer;
import org.corpus_tools.pepper.service.util.PepperServiceURLDictionary;
import org.eclipse.emf.common.util.URI;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

@WebService
@Path("/resource")
@Component(name = "PepperRESTService", immediate = true)
public class PepperRESTService implements PepperServiceImplConstants, PepperServiceURLDictionary, JobSignal {

	/* statics */
	private static Pepper pepper;
	private static PepperSerializer serializer;

	private static final Logger logger = LoggerFactory.getLogger(PepperRESTService.class);
	
	public static final String ERR_MSG_ZIPPING_ERROR = "An error occured zipping the data.";
	private static final String ERR_JOB_NOT_FOUND = "No such job available.";

	@Reference(unbind = "unsetPepper", cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC)
	public void setPepper(Pepper pepperInstance) {
		pepper = pepperInstance;	
		logger.info("pepper set to:" + pepper);
		(new File(LOCAL_JOB_DIR)).mkdir();
		serializer = PepperSerializer.getInstance(DATA_FORMAT);
	}

	public void unsetPepper(Pepper pepperInstance) {
		pepper = null;
		logger.info("unset pepper");
	}

	// returns all pepper modules
	@GET
	@Path(PATH_ALL_MODULES)
	@Produces(DATA_FORMAT)
	public String getModules() {
		PepperModuleCollectionMarshallable modules = new PepperModuleCollectionMarshallable(
				pepper.getRegisteredModules());
		JAXBContext context;
		Marshaller m;
		StringWriter writer = new StringWriter();
		try {
			context = JAXBContext.newInstance(PepperModuleCollectionMarshallable.class);
			m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(modules, writer);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (writer.toString());
	}
	
	@POST
	@Path("try")
	@Produces(DATA_FORMAT)
	public String getEcho(){
		return "hi";
	}

	// return workflow file for job
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path(PATH_JOB + "/{id}")
	public String getJobDescription(@PathParam("id") String id) {
		try{
			PepperJob job = pepper.getJob(id);
			if (job == null) {
				return "No such job"; // TODO XML
			}
			if (JOB_STATUS.NOT_STARTED.equals(job.getStatus())) {
				logger.info("Job " + id + " started.");
				job.convert();
			}
			return job.getStatusReport(); // TODO XML
		} catch (JobNotFoundException e) {			
			return "404";
		}
	}

	/**
	 * Get status report of job.
	 * @param id
	 * @return
	 */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(DATA_FORMAT)
	@Path(PATH_JOB + "/{id}/report")
	public String getJobReport(@PathParam("id") String id) {
		PepperSerializer serializer = PepperSerializer.getInstance(DATA_FORMAT);
		try {
			PepperJob job = pepper.getJob(id);
			PepperJobReportMarshallable report = new PepperJobReportMarshallable(id, job.getProgressByModules());
			logger.info("Mapping: " + report.getProgressByPath());
			String xml = serializer.marshal(report);
			logger.info("Delivering report: " + xml);
			return xml;
		} catch (JobNotFoundException e) {
			return "404";
		}
	}

	// uploads data to pepper server and returns step id
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Path("job/{id}/")
	public void uploadData(@PathParam("id") String id, @FormDataParam("data") byte[] data) {
		return;
	}

	// uploads path to data to pepper server and returns step id
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Path(PATH_JOB + "/{id}/{path}")
	public void uploadData(@PathParam("id") String jobId, @PathParam("path") String path, @FormDataParam("data") byte[] data) {

		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		PepperJob job = pepper.getJob(jobId);
		if (job != null) {
			String jobDirName = LOCAL_JOB_DIR + jobId + File.separator;
			(new File(jobDirName)).mkdir();
			String dataDir = path;
			String zipFileName = jobDirName + dataDir + ".zip";
			try {
				FileOutputStream outstream = new FileOutputStream(zipFileName);
				outstream.write(data);
				logger.info("DATA for job " + jobId + " transferred to " + dataDir + ".");
				outstream.close();
			} catch (IOException e) {
				logger.error("Writing data failed.");
				e.printStackTrace();
			}
			File stepDir = null;
			String newTarget = null;
			for (StepDesc stepDesc : job.getStepDescs()) {
				if (MODULE_TYPE.EXPORTER.equals(stepDesc.getModuleType())) {
					newTarget = TARGET_ROOT_DIR + stepDesc.getCorpusDesc().getCorpusPath().lastSegment();
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
				if (logger.isDebugEnabled()) {
					logger.debug("Extracting transferred data ...");
				}
				while (entry != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("\t" + entry.getName());
					}
					if (entry.isDirectory()) {
						file = new File(jobDirName + path + "/" + entry.getName());
						file.mkdirs();
					} else {
						file = new File(path + "/" + entry.getName());
						outstream = new FileOutputStream(jobDirName + file);
						while (zip.read(bytes) > 0) {
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

	// downloads data from pepper server
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path(PATH_JOB + "/{id}/{stepNo}")
	public byte[] getData(@PathParam("id") String jobId, @PathParam("stepNo") Integer stepNo) {

		PepperJob job = pepper.getJob(jobId);

		URI exportPath = job.getStepDescs().get(stepNo).getCorpusDesc().getCorpusPath();

		File dataDir = new File(exportPath.toFileString());
		File zipFile = new File(exportPath.toFileString() + ".zip");
		if (/* JOB_STATUS.ENDED.equals(job.getStatus()) && */ dataDir.exists()) {
			// TODO what to do in case ENDED_WITH_ERRORS?
			try {
				logger.info("Providing requested files: " + jobId + File.separator + exportPath);
				if (!zipFile.exists()) {
					FileOutputStream fos = new FileOutputStream(zipFile);
					ZipOutputStream zipout = new ZipOutputStream(fos);
					ZipEntry entry = null;
					for (File file : FileUtils.listFilesAndDirs(dataDir, TrueFileFilter.INSTANCE,
							DirectoryFileFilter.DIRECTORY)) {
						entry = new ZipEntry(file.getName());
						zipout.putNextEntry(entry);
						if (!file.isDirectory()) {
							Files.copy(file, zipout);
						}
						logger.info("\tZipping " + entry.getName());
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
				logger.error(ERR_MSG_ZIPPING_ERROR);// TODO
				e.printStackTrace();
			}
		}
		return ArrayUtils.EMPTY_BYTE_ARRAY;
	}
	
	@POST
	@Consumes(DATA_FORMAT)
	@Produces(MediaType.TEXT_PLAIN)
	@Path(PATH_JOB)
	public String createJob(String jobDescription){	
		serializer = PepperSerializer.getInstance(DATA_FORMAT);
		if (jobDescription == null || jobDescription.trim().isEmpty()){
			logger.warn("No data received.");
			return null;
		}
		logger.info("Received data:" + System.lineSeparator() + jobDescription.toString());
		logger.info("Using serializer: "+serializer);
		Object obj = serializer.unmarshal(jobDescription, PepperJobMarshallable.class);
		logger.info("Unmarshalled received data to: " + obj);
		PepperJobMarshallable desc = (PepperJobMarshallable) obj;
		PepperJob job = pepper.getJob(pepper.createJob());
		job.setBaseDir(job.getBaseDir()); //wtf?
		logger.info("Set basedir to: " + job.getBaseDir());
		boolean allPathsGiven = true;
		for (StepDescMarshallable sdm : desc.getSteps()){
			StepDesc sd = sdm.getPepperObject();
			job.addStepDesc(sd);
			CorpusDesc cd = sd.getCorpusDesc();
			allPathsGiven &= MODULE_TYPE.MANIPULATOR.equals(sd.getModuleType()) || (cd != null && !(cd.getCorpusPath() == null || cd.getCorpusPath().toString().trim().isEmpty()));
		}
		/* if the service runs as server application, the corpus paths will not be set and the
		 * service will have to wait for data to be uploaded 
		 */
		if (allPathsGiven) {
			/*TODO implement a mechanism that keeps track of open threads 
			 * to beware to many threads by too many requests
			 */
			Thread conversionThread = new Thread(new PepperServiceJobRunner(pepper, job.getId()), "ServiceConversion-Thread");
			conversionThread.start();
		}
		return job.getId();
	}
	

	
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path(PATH_JOB + "/job/{id}/{signal}")
	public String signal(@PathParam("id") String jobId, @PathParam("signal") String signal){
		try{
			PepperJob job = pepper.getJob(jobId);
			if (SIG_STOP.equals(signal)){
				// TODO
				throw new UnsupportedOperationException(ERR_SIG_NOT_SUPPORTED);
			}
			else if (SIG_START.equals(signal) && JOB_STATUS.NOT_STARTED.equals(job.getStatus())){
				/*TODO implement a mechanism that keeps track of open threads 
				 * to beware to many threads by too many requests
				 */
				Thread conversionThread = new Thread(new PepperServiceJobRunner(pepper, job.getId()), "ServiceConversion-Thread");
				conversionThread.start();
			}
			return job.getStatus().toString(); 
		} catch (JobNotFoundException e) {
			logger.error(ERR_JOB_NOT_FOUND);
		}
		return "404";
	}	
}

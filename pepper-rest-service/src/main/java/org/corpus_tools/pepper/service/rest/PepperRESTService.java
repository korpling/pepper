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
import org.corpus_tools.pepper.common.JOB_STATUS;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperJob;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.service.interfaces.PepperServiceImplConstants;
import org.corpus_tools.pepper.service.lib.adapters.PepperModuleCollectionMarshallable;
import org.corpus_tools.pepper.service.lib.util.PepperSerializer;
import org.corpus_tools.pepper.service.lib.util.PepperServiceURLDictionary;
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
public class PepperRESTService implements PepperServiceImplConstants, PepperServiceURLDictionary {

	/* statics */
	private static Pepper pepper;
	private static PepperSerializer serializer = PepperSerializer.getInstance(DATA_FORMAT);

	private static final Logger logger = LoggerFactory.getLogger(PepperRESTService.class);
	
	public static final String ERR_MSG_ZIPPING_ERROR = "An error occured zipping the data.";

	@Reference(unbind = "unsetPepper", cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC)
	public void setPepper(Pepper pepperInstance) {
		pepper = pepperInstance;
		logger.info("pepper set to:" + pepper);
		(new File(LOCAL_JOB_DIR)).mkdir();
	}

	public void unsetPepper(Pepper pepperInstance) {
		pepper = null;
		logger.info("unset pepper");
	}

	// returns all pepper modules
	@GET
	@Path(PATH_ALL_MODULES)
	public String getModules() {
		System.out.println("---------------------> Pepper " + pepper.getRegisteredModules());
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

	// return workflow file for job
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path(PATH_JOB + "/{id}")
	public String getJobDescription(@PathParam("id") String id) {
		PepperJob job = pepper.getJob(id);
		if (job == null) {
			return "No such job"; // TODO XML
		}
		if (JOB_STATUS.NOT_STARTED.equals(job.getStatus())) {
			logger.info("Job " + id + " started.");
			job.convert();
		}
		return job.getStatusReport(); // TODO XML
	}

	// status report of job
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path(PATH_JOB + "/{id}/report")
	public String getJobReport(@PathParam("id") String id) {
		return null;
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

}

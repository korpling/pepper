package de.hu_berlin.german.korpling.saltnpepper.pepper.core;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.JOB_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.JobNotFoundException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;

@Component(name = "PepperImpl", immediate = true)
public class PepperImpl implements Pepper {
	private static final Logger logger= LoggerFactory.getLogger(PepperImpl.class);
	
	public static final String DEFAULT_WORKSPACE="/pepper/workspace";
	/** **/
	private PepperConfiguration configuration= null;
	/**
	 * Returns set configuration for {@link Pepper}.
	 * @return
	 */
	public PepperConfiguration getConfiguration() {
		if (configuration== null){
			synchronized (this) {
				if (configuration== null)
					configuration= new PepperConfiguration();
			}
		}
		return configuration;
	}
	/** 
	 * Sets the configuration for {@link Pepper}.
	 * @param configuration
	 */
	public void setConfiguration(PepperConfiguration configuration) {
		this.configuration = configuration;
	}
	
	class JobEntry{
		public PepperJobImpl pepperJob= null;
		public File location= null;
		
		public JobEntry(PepperJobImpl job, File location){
			this.pepperJob= job;
			this.location= location;
		}
	}
	private Map<String, JobEntry> mapOfJobs= null; 
	/**
	 * Returns a map of {@link PepperJobImpl} objects.
	 * @return
	 */
	private Map<String, JobEntry> getMapOfJobs(){
		if (mapOfJobs== null){
			synchronized (this) {
				if (mapOfJobs== null){
					mapOfJobs= new Hashtable<String, PepperImpl.JobEntry>();
				}
			}
		}
		return(mapOfJobs);
	}
	
	/**
	 * Returns the workspace, of where to store {@link PepperJobImpl} objects. Wither a given workspace by {@link PepperConfiguration}
	 * or the default workspace, which is: temporary folder of OS + {@value #DEFAULT_WORKSPACE}
	 * @return
	 */
	private File getWorkspace(){
		File workspace= null;
		if (getConfiguration()!= null)
			workspace= getConfiguration().getWorkspace();
		if (workspace== null){
			workspace= new File(System.getProperty("java.io.tmpdir")+DEFAULT_WORKSPACE);
			workspace.mkdirs();
		}
		return(workspace);
	}
	
	/**
	 * {@inheritDoc Pepper#createJob()}
	 */
	@Override
	public String createJob() {
		PepperJobImpl job= null;
		
		SecureRandom random = new SecureRandom();
		String newId= new BigInteger(130, random).toString(32).substring(0, 8);
		
		while(getMapOfJobs().containsKey(newId)){
			newId= new BigInteger(130, random).toString(32).substring(0, 8);
		}
		//initialize job
			job= new PepperJobImpl(newId);
			job.setModuleResolver(getModuleResolver());
			job.setConfiguration(getConfiguration());
		//initialize job
		File jobFolder= new File(getWorkspace().getAbsolutePath()+"/"+newId);
		jobFolder.mkdirs();
		getMapOfJobs().put(newId, new JobEntry(job, jobFolder));
		return(job.getId());
	}
	/**
	 * Return identifiers of all registered {@link PepperJobImpl} objects.
	 * @return a list of all job identifiers
	 */
	public Set<String> getJobIds(){
		return(getMapOfJobs().keySet());
	}
	/**
	 * {@inheritDoc Pepper#getJob(String)}
	 */
	@Override
	public PepperJob getJob(String id) throws JobNotFoundException {
		return(getPepperJobImpl(id));
	}
	/**
	 * {@inheritDoc Pepper#getJob(String)}
	 */
	public PepperJobImpl getPepperJobImpl(String id) throws JobNotFoundException {
		PepperJobImpl job= null;
		
		JobEntry jobEntry= getMapOfJobs().get(id);
		if (jobEntry== null)
			throw new JobNotFoundException("The Pepper job with id '"+id+"' was not found.");
		job= jobEntry.pepperJob;
		return(job);
	}
	/**
	 * {@inheritDoc Pepper#removeJob(String)}
	 */
	@Override
	public boolean removeJob(String id) throws JobNotFoundException {
		boolean retVal= false;
		
		JobEntry jobEntry= getMapOfJobs().get(id);
		if (jobEntry== null)
			throw new JobNotFoundException("The Pepper job with id '"+id+"' was not found.");
		
		retVal= jobEntry.location.delete();
		
		getMapOfJobs().remove(id);
		return(retVal);
	}
	/**
	 * Returns the location of a job.
	 * @param id
	 * @return
	 * @throws JobNotFoundException
	 */
	public File getLocation(String id) throws JobNotFoundException{
		JobEntry jobEntry= getMapOfJobs().get(id);
		if (jobEntry== null)
			throw new JobNotFoundException("The Pepper job with id '"+id+"' was not found.");
		return(jobEntry.location);
	}
	
	/**
	 * Cleans up current workspace. Removes all non active jobs.
	 */
	public void cleanUp(){
		for (String jobId: getMapOfJobs().keySet()){
			
			JobEntry jobEntry= getMapOfJobs().get(jobId); 
			if (jobEntry== null){
				getMapOfJobs().remove(jobId);
			}
			boolean toRemove= true;
			if	(	(jobEntry!= null)&&
					(jobEntry.pepperJob!= null)){
				if (JOB_STATUS.IN_PROGRESS.equals(jobEntry.pepperJob.getStatus()))
					toRemove= false;
			}
			if (toRemove)
				removeJob(jobId);
		}
	}    
	/**
	 * {@inheritDoc Pepper#getRegisteredModules()}
	 */
	@Override
	public Collection<PepperModuleDesc> getRegisteredModules() {
		Vector<PepperModuleDesc> retVal= new Vector<PepperModuleDesc>();
		
		if (getModuleResolver().getPepperImporters()!= null){
			for (PepperModule pepperModule: getModuleResolver().getPepperImporters()){
				if (pepperModule!= null){
					retVal.add(pepperModule.getFingerprint());
				}
			}
		}
		if (getModuleResolver().getPepperManipulators()!= null){
			for (PepperModule pepperModule: getModuleResolver().getPepperManipulators()){
				if (pepperModule!= null){
					retVal.add(pepperModule.getFingerprint());
				}
			}
		}
		if (getModuleResolver().getPepperExporters()!= null){
			for (PepperModule pepperModule: getModuleResolver().getPepperExporters()){
				if (pepperModule!= null){
					retVal.add(pepperModule.getFingerprint());
				}
			}
		}
		return retVal;
	}
	
// ===================================== start: wirering module resolver via OSGi
	/** The resolver to resolve {@link PepperModule} objects **/
	private ModuleResolver moduleResolver= null;
	/**
	 * Returns the set {@link ModuleResolverImpl} object, to resolve {@link PepperModule} objects. 
	 * @return set set {@link ModuleResolverImpl} object
	 */
	public ModuleResolver getModuleResolver() {
		return moduleResolver;
	}
	/**
	 * Sets a {@link ModuleResolverImpl} object to resolve {@link PepperModule} objects for Pepper jobs.
	 * @param moduleResolver {@link ModuleResolverImpl} object to be used for jobs 
	 */
    @Reference(unbind = "unsetModuleResolver", cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC)
    public void setModuleResolver(ModuleResolver moduleResolver) {
		this.moduleResolver = moduleResolver;
		moduleResolver.setConfiguration(getConfiguration());
	}
    /**
     * Unsets the {@link ModuleResolverImpl} reference. This is necessary for OSGi declarative service.
     */
    public void unsetModuleResolver(ModuleResolver moduleResolver) {
    	this.moduleResolver= null;
    }
// ===================================== end: wirering module resolver via OSGi    

    /**
	 * Checks if the Pepper framework is ready to run. This means, it checks if everything necessary is given and if all registered modules
	 * could be ran. This method can be used as a kind of integration test.
	 * @return returns an empty list, if check was positive; if list is not empty, each entry describes a single problem. 
	 */
    public Collection<String> selfTest() {
		Collection<String> retVal = new Vector<String>();
		if (getModuleResolver() == null)
		    retVal.add("Cannot run Pepper, because no PepperModuleResolver was set.");
		Collection<PepperImporter> importers = getModuleResolver().getPepperImporters();
		if ((importers == null) || (importers.size() == 0)) {
		    retVal.add("Cannot run Pepper, because no importers were given");
		} else {
		    for (PepperImporter importer : importers) {
				try {
				    logger.info("Checking '"+importer.getName()+"'");
				    if (!importer.isReadyToStart())
					retVal.add("A Pepper module '" + importer.getName()
						+ "' is not ready to start.");
				} catch (Exception e) {
				    retVal.add("A Pepper module '" + importer.getName()
					    + "' is not ready to start.");
				}
				if (	(retVal== null)||
						(retVal.size()== 0)){
					logger.info("ok");
				}else{
					logger.info("Problems occured in: "+retVal);
				}
		    }
		}
		Collection<PepperManipulator> manipulators = getModuleResolver().getPepperManipulators();
		if ((manipulators != null) && (manipulators.size() != 0)) {
		    for (PepperManipulator manipulator : manipulators) {
			try {
				logger.info("Checking '"+manipulator.getName()+"'");
			    if (!manipulator.isReadyToStart())
				retVal.add("A Pepper module '" + manipulator.getName()
					+ "' is not ready to start.");
			} catch (Exception e) {
			    retVal.add("A Pepper module '" + manipulator.getName()
				    + "' is not ready to start.");
			}
			if (	(retVal== null)||
					(retVal.size()== 0)){
				logger.info("ok");
			}else{
				logger.info("Problems occured in: "+retVal);
			}
		    }
		}
	
		Collection<PepperExporter> exporters = this.getModuleResolver().getPepperExporters();
		if ((exporters == null) || (exporters.size() == 0)) {
		    retVal.add("Cannot run Pepper, because no exporters were given");
		} else {
		    for (PepperExporter exporter : exporters) {
				try {
					logger.info("Checking '"+exporter.getName()+"'");
				    if (!exporter.isReadyToStart())
					retVal.add("A Pepper module '" + exporter.getName() + "' is not ready to start.");
				} catch (Exception e) {
				    retVal.add("A Pepper module '" + exporter.getName()
					    + "' is not ready to start.");
				}
				if (	(retVal== null)||
						(retVal.size()== 0)){
					logger.info("ok");
				}else{
					logger.info("Problems occured in: "+retVal);
				}
		    }
		}
		return (retVal);
    }	
    @Override
	public String getRegisteredModulesAsString() {
		String retVal= null;
		if (getModuleResolver()!= null){
			retVal= getModuleResolver().getStatus();
		}
		return(retVal);
	}
    /**
     * Returns a string representation of this object. 
	 * <strong>Note: This representation cannot be used for serialization/deserialization purposes.</strong>
     */
    @Override
    public String toString(){
    	StringBuilder str= new StringBuilder();
    	str.append("pepper");
    	str.append("[");
    	str.append("registered jobs: ");
    	getJobIds().size();
    	str.append("]");
    	return(str.toString());
    }
}

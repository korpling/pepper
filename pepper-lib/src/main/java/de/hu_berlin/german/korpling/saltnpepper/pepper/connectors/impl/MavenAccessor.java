package de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositoryEvent;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.examples.util.Booter;
import org.eclipse.aether.examples.util.ConsoleRepositoryListener;
import org.eclipse.aether.examples.util.ConsoleTransferListener;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.util.version.GenericVersionScheme;
import org.eclipse.aether.version.InvalidVersionSpecificationException;
import org.eclipse.aether.version.Version;
import org.eclipse.aether.version.VersionScheme;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MavenAccessor {
	
	private static final Logger logger = LoggerFactory.getLogger(MavenAccessor.class);
	
	/** This is used to install the bundles */
	PepperOSGiConnector pepperOSGiConnector = null;	
	/** this {@link Set} stores all dependencies, that are installed or forbidden. The format of the {@link String}s is GROUP_ID:ARTIFACT_ID:EXTENSION:VERSION, which is also the output format of {@link Dependency#getArtifact()#toString()}.*/
	private Set<String> forbiddenFruits = null;
	/** this map contains already collected pepper parent dependencies (version-String->List<Dependency>) */
	private HashMap<String, List<Dependency>> parentDependencies = null;
	/** contains the path to the blacklist file. */
	private static final String BLACKLIST_PATH = "./conf/dep/blacklist.cfg";
	/** this String contains the artifactId of pepper-framework. */
	public static final String ARTIFACT_ID_PEPPER_FRAMEWORK = "pepper-framework";
	/** this String contains the artifactId of pepper-parentModule. */
	public static final String ARTIFACT_ID_PEPPER_PARENT = "pepper-parentModule";
	/** path to korpling maven repo */
	public static final String KORPLING_MAVEN_REPO = "http://korpling.german.hu-berlin.de/maven2";
	
	/** flag which is add to the blacklist entry of a dependency – a FINAL dependency can not be overridden */
	private static enum STATUS{
		OVERRIDABLE, FINAL; 
	}
	/** delimiter for artifact strings */
	private static final String DELIMITER = ":";
	
	/* MAVEN UTILS */
	/** maven/aether utility */
	RepositorySystem system = null;
	/** this Map contains all repos already used in this pepper session, key is url, value is repo */
	HashMap<String, RemoteRepository> repos = null;
	/** maven/aether utility used to build Objects of class {@link RemoteRepository}. */
	RemoteRepository.Builder repoBuilder = null;
		
	public MavenAccessor(PepperOSGiConnector pepperOSGiConnector){
		this.pepperOSGiConnector = pepperOSGiConnector;
		system = Booter.newRepositorySystem();
		repoBuilder = new RemoteRepository.Builder("", "default", "");
		repos = new HashMap<String, RemoteRepository>();
		forbiddenFruits = new HashSet<String>();
		parentDependencies = new HashMap<String, List<Dependency>>();
		init();
		initDependencies();
	}
	
	/**
	 * This method tries to read the blacklist file, if it already exists
	 */
	private void init(){
		/* init Maven utils */
		/* read/write dependency blacklist */
		File blacklistFile = new File(BLACKLIST_PATH);		
		if (blacklistFile.exists()){
			try {
				FileReader fR = new FileReader(blacklistFile);
				BufferedReader reader = new BufferedReader(fR);
				String line = reader.readLine();
				while (line!=null){
					forbiddenFruits.add(line);
					line = reader.readLine();
				}
				reader.close();
				fR.close();
			} catch (IOException e) {logger.debug("Could not read blacklist file.");}
		}
	}
	
	/**
	 * this method initializes the dependency blacklist
	 */
	private boolean initDependencies(){
		String frameworkVersion = pepperOSGiConnector.getFrameworkVersion();
		if (forbiddenFruits.isEmpty()){
			logger.info("Configuring update mechanism ...");
			/* maven access utils*/
			Artifact pepArt = new DefaultArtifact("de.hu_berlin.german.korpling.saltnpepper", ARTIFACT_ID_PEPPER_PARENT, "pom", frameworkVersion);
	        RepositorySystemSession session = Booter.newRepositorySystemSession( system );
	        ((DefaultRepositorySystemSession)session).setRepositoryListener(repoListener);
	        ((DefaultRepositorySystemSession)session).setTransferListener(transferListener);	        
	        RemoteRepository repo = buildRepo("korpling", KORPLING_MAVEN_REPO);
	        repos.put(KORPLING_MAVEN_REPO, repo);
			
			/* utils for dependency collection */
    		CollectRequest collectRequest = new CollectRequest();
            collectRequest.setRoot( new Dependency( pepArt, "" ) );
            collectRequest.setRepositories(null);
            collectRequest.addRepository(repo);	            
            collectRequest.setRootArtifact(pepArt);
            try {
				CollectResult collectResult = system.collectDependencies( session, collectRequest );
				List<Dependency> allDeps = getAllDependencies(collectResult.getRoot(), false);
				parentDependencies.put(frameworkVersion.replace("-SNAPSHOT", ""), allDeps);
				for (Dependency dependency : allDeps){
					forbiddenFruits.add(dependency.getArtifact().toString()+DELIMITER+STATUS.FINAL);
				}
				write2Blacklist();				
				collectResult = null;
				
			} catch (DependencyCollectionException e) {logger.warn("An error occured initializing the update mechanism. Please check your internet connection.", e); return false;}
            session = null;
			pepArt = null;
			collectRequest = null;			
		}
		write2Blacklist();
		return true;
	}
	
	/** This listener does not write the maven transfer output. */
	private final ConsoleTransferListener transferListener = new ConsoleTransferListener(new PrintStream(new OutputStream(){
		@Override
		public void write(int b){/*DO NOTHING!*/}
	}));
	
	/** This is used to write the Maven output onto our logger. The default {@link ConsoleRepositoryListener} writes it directly to System.out */
	private final ConsoleRepositoryListener repoListener = new ConsoleRepositoryListener(){
		@Override
		public void artifactDeployed( RepositoryEvent event )
	    {
	        logger.trace( "Deployed " + event.getArtifact() + " to " + event.getRepository() );
	    }
		@Override
	    public void artifactDeploying( RepositoryEvent event )
	    {
			logger.trace( "Deploying " + event.getArtifact() + " to " + event.getRepository() );
	    }
		@Override
	    public void artifactDescriptorInvalid( RepositoryEvent event )
	    {
			logger.trace( "Invalid artifact descriptor for " + event.getArtifact() + ": "
	            + event.getException().getMessage() );
	    }
		@Override
	    public void artifactDescriptorMissing( RepositoryEvent event )
	    {
			logger.trace( "Missing artifact descriptor for " + event.getArtifact() );
	    }
		@Override
	    public void artifactInstalled( RepositoryEvent event )
	    {
			logger.trace( "Installed " + event.getArtifact() + " to " + event.getFile() );
	    }
		@Override
	    public void artifactInstalling( RepositoryEvent event )
	    {
			logger.trace( "Installing " + event.getArtifact() + " to " + event.getFile() );
	    }
		@Override
	    public void artifactResolved( RepositoryEvent event )
	    {
			logger.trace( "Resolved artifact " + event.getArtifact() + " from " + event.getRepository() );
	    }
		@Override
	    public void artifactDownloading( RepositoryEvent event )
	    {
			logger.trace( "Downloading artifact " + event.getArtifact() + " from " + event.getRepository() );
	    }
		@Override
	    public void artifactDownloaded( RepositoryEvent event )
	    {
			logger.trace( "Downloaded artifact " + event.getArtifact() + " from " + event.getRepository() );
	    }
		@Override
	    public void artifactResolving( RepositoryEvent event )
	    {
			logger.trace( "Resolving artifact " + event.getArtifact() );
	    }
		@Override
	    public void metadataDeployed( RepositoryEvent event )
	    {
			logger.trace( "Deployed " + event.getMetadata() + " to " + event.getRepository() );
	    }
		@Override
	    public void metadataDeploying( RepositoryEvent event )
	    {
			logger.trace( "Deploying " + event.getMetadata() + " to " + event.getRepository() );
	    }
		@Override
	    public void metadataInstalled( RepositoryEvent event )
	    {
			logger.trace( "Installed " + event.getMetadata() + " to " + event.getFile() );
	    }
		@Override
	    public void metadataInstalling( RepositoryEvent event )
	    {
	    	logger.trace( "Installing " + event.getMetadata() + " to " + event.getFile() );
	    }
		@Override
	    public void metadataInvalid( RepositoryEvent event )
	    {
	    	logger.trace( "Invalid metadata {}", event.getMetadata() );
	    }
		@Override
	    public void metadataResolved( RepositoryEvent event )
	    {
	    	logger.trace( "Resolved metadata " + event.getMetadata() + " from " + event.getRepository() );
	    }
		@Override
	    public void metadataResolving( RepositoryEvent event )
	    {
	    	logger.trace( "Resolving metadata " + event.getMetadata() + " from " + event.getRepository() );
	    }		
	};	
	
	/**
	 * This method checks the pepperModules in the modules.xml for updates
	 * and triggers the installation process if a newer version is available
	 */
	public boolean update(String groupId, String artifactId, String repositoryUrl, boolean isSnapshot, boolean ignoreFrameworkVersion, Bundle installedBundle){
		if (forbiddenFruits.isEmpty() && !initDependencies()){
			logger.warn("Update process could not be performed, because the pepper dependencies could not be listed.");
			return false;
		}
				
		if (logger.isTraceEnabled()){
			logger.trace("Starting update process for "+groupId+", "+artifactId+", "+repositoryUrl+", isSnapshot="+isSnapshot+", ignoreFrameworkVersion="+ignoreFrameworkVersion+", installedBundle="+installedBundle);
		} else {
			logger.info("Starting update process for "+artifactId);
		}	
		
		String newLine = System.getProperty("line.separator");
		
        RepositorySystemSession session = Booter.newRepositorySystemSession( system );
        ((DefaultRepositorySystemSession)session).setRepositoryListener(repoListener);
        ((DefaultRepositorySystemSession)session).setTransferListener(transferListener);
        
        boolean update = true; //MUST be born true       
        
        /*build repository*/
        
        RemoteRepository repo = repos.get(repositoryUrl);
        if (repo==null){
        	repo = buildRepo("any", repositoryUrl);
        	repos.put(repositoryUrl, repo);
        }     
        
        /*build artifact*/
        Artifact artifact = new DefaultArtifact(groupId, artifactId, "zip","[0,)");       
        
        try {
	        /*version range request*/
	        VersionRangeRequest rangeRequest = new VersionRangeRequest();	        
	        rangeRequest.addRepository(repo);
	        rangeRequest.setArtifact(artifact);
	        VersionRangeResult rangeResult = system.resolveVersionRange(session, rangeRequest);
	        rangeRequest.setArtifact( artifact );
	        rangeRequest.setRepositories(Booter.newRepositories(system, session));        
	                
	        /* utils needed for request */            
            ArtifactRequest artifactRequest = new ArtifactRequest();
            artifactRequest.addRepository(repo);
            ArtifactResult artifactResult = null;       
            
            /* get all pepperModules versions listed in the maven repository */
    		List<Version> versions = rangeResult.getVersions();
	        Collections.reverse(versions);	        
    		
	        /* utils for version comparison */
	        Iterator<Version> itVersions = versions.iterator();
    		VersionScheme vScheme = new GenericVersionScheme();
    		boolean srcExists = false;
    		Version installedVersion = installedBundle==null? vScheme.parseVersion("0.0.0") : vScheme.parseVersion(installedBundle.getVersion().toString().replace(".SNAPSHOT", "-SNAPSHOT"));
    		Version newestVersion = null;
    		
    		/* compare, if the listed version really exists in the maven repository */
    		File file = null;	      		
    		while(!srcExists && itVersions.hasNext() && update){
	    			newestVersion = itVersions.next();														    			
	    			artifact = new DefaultArtifact(groupId, artifactId, "zip", newestVersion.toString());
	    			if (!(	artifact.isSnapshot() && !isSnapshot 	)){
		    			update = newestVersion.compareTo(installedVersion) > 0;					    			
		    			artifactRequest.setArtifact(artifact);
		    			try{			    				
		    					artifactResult = system.resolveArtifact(session, artifactRequest);		    			
		    					artifact = artifactResult.getArtifact();
		    					srcExists = update && artifact.getFile().exists();
		    					file = artifact.getFile();
		    				
		    			}catch (ArtifactResolutionException e){
		    					logger.warn("Highest version in repository could not be found. Checking the next lower version ...");
		    			}
	    			}
    		}    	
	    	update&= file!=null;//in case of only snapshots in the maven repository vs. isSnapshot=false	    	
	    	/* if an update is possible/necessary, perform dependency collection and installation */
	    	if (update){
	    		/* remove older version */
	    		if (installedBundle!=null){	    			
	    			try {
						if(!pepperOSGiConnector.remove(installedBundle.getSymbolicName())){
							logger.warn("Could not remove older version. Update process aborted.");
							return false;
						}
					} catch (BundleException | IOException e) {
						logger.warn("An error occured while trying to remove OSGi bundle "+installedBundle.getSymbolicName()+". This may cause update problems. Trying to continue ...");
					}
	    		}
	    		
	    		/* utils for file-collection */
    			List<Artifact> installArtifacts = new ArrayList<Artifact>(); 		
	    		installArtifacts.add(artifact);  		
	    		
	    		/* utils for dependency collection */
	    		CollectRequest collectRequest = new CollectRequest();
	            collectRequest.setRoot( new Dependency( artifact, "" ) );
	            collectRequest.setRepositories(Booter.newRepositories(system, session));
	            collectRequest.addRepository(repo);
	            CollectResult collectResult = system.collectDependencies( session, collectRequest );            
	            List<Dependency> allDependencies = getAllDependencies(collectResult.getRoot(), true);                
	            
            	/* we have to remove the dependencies of pepperParent from the dependency list, since they are (sometimes)
            	 * not already on the blacklist
            	 * */           
	            String parentVersion = null;
	            for(int i=0; i<allDependencies.size()&&parentVersion==null; i++){
	            	if (ARTIFACT_ID_PEPPER_FRAMEWORK.equals(allDependencies.get(i).getArtifact().getArtifactId())){
	            		parentVersion = allDependencies.get(i).getArtifact().getVersion();
	            	}
	            }            	
        		allDependencies = cleanDependencies(allDependencies, session, parentVersion);
            	
	            
	            Bundle bundle = null;
	            Dependency dependency = null;	            
	            //in the following we ignore the first dependency (i=0), because it is the module itself         	            
	            for (int i=1; i<allDependencies.size(); i++){
	            	dependency = allDependencies.get(i);
	            	if (ARTIFACT_ID_PEPPER_FRAMEWORK.equals(dependency.getArtifact().getArtifactId())){            			            		
	            		if (!ignoreFrameworkVersion && !dependency.getArtifact().getVersion().replace("-SNAPSHOT", "").equals(pepperOSGiConnector.getFrameworkVersion().replace("-SNAPSHOT", ""))){	            			
	            			logger.info(
	            					(new StringBuilder())
	            					.append("No update was performed because of a version incompatibility according to pepper-framework: ")
	            					.append(newLine).append(artifactId).append(" needs ").append(dependency.getArtifact().getVersion()).append(", but ").append(pepperOSGiConnector.getFrameworkVersion()).append(" is installed!")
	            					.append(newLine).append("You can make pepper ignore this by using \"update").append(isSnapshot? " snapshot ":" ").append("iv ")
	            					.append(artifactId).append("\"").toString());	            			
	            			return false;
	            		}	            		
	            	}
	            	else {	            		
	            		artifactRequest.setRepositories(Booter.newRepositories(system, session));
	            		artifactRequest.addRepository(repo);
	            		artifactRequest.setArtifact(dependency.getArtifact());
	            		try{
	            			artifactResult = system.resolveArtifact(session, artifactRequest);    			
		            		installArtifacts.add(artifactResult.getArtifact());
	            		}catch (ArtifactResolutionException e){	            			
	            			logger.warn("Artifact "+dependency.getArtifact().getArtifactId()+" could not be resolved. Dependency will not be installed.");	            			
	            		}
	            	}
	            }	            
	            artifact = null;
	            
	            String nxt = null;
	            String[] next = null;
	            Iterator<String> itDeps = null;
	            Artifact installArtifact = null;
	            for (int i=installArtifacts.size()-1; i>=0; i--){	            	
	            	try {	            		
	            		installArtifact = installArtifacts.get(i);	            		
	            		logger.info("installing: "+installArtifact);	            		
	            		bundle = pepperOSGiConnector.installAndCopy(installArtifact.getFile().toURI());
	            		if (i!=0){//the module itself must not be put on the blacklist
	            			itDeps = forbiddenFruits.iterator();
	            			nxt = itDeps.next();
	            			while (nxt!=null){	            				
	            				next = nxt.split(DELIMITER);
	            				if (next[0].equals(installArtifact.getGroupId()) && next[1].equals(installArtifact.getArtifactId())){
	            					forbiddenFruits.remove(nxt);
	            					logger.debug("Removed dependency from blacklist: "+nxt);
	            					nxt = null;
	            				} else {
	            					nxt = itDeps.hasNext()? itDeps.next() : null;
	            				}
	            			}	 
	            			forbiddenFruits.add(installArtifact.toString()+DELIMITER+STATUS.OVERRIDABLE);
	            			logger.debug("Put dependency on blacklist: "+installArtifact.toString());
	            		}
	            		if (bundle!=null){
	            			bundle.start();
	            		}
	            	} catch (IOException | BundleException e) {
	            		if (logger.isTraceEnabled()){
	            			logger.trace("File could not be installed: "+installArtifact+" ("+installArtifact.getFile()+"); "+e.getClass().getSimpleName());
	            		} else {
	            			logger.warn("File could not be installed: "+installArtifact.getFile());
	            		}
	            	}
		    	}
	            /*
	    		 * btw: root is not supposed to be stored as forbidden dependency. This makes the removal of a module much less complicated.
	    		 * If a pepper module would be put onto the blacklist and the bundle would be removed, we always had to make sure, it
	    		 * its entry on the blacklist would be removed. Assuming the entry would remain on the blacklist, the module could be
	    		 * reinstalled, BUT(!) the dependencies would all be dropped and never being installed again, since the modules node
	    		 * dominates all other nodes in the dependency tree.
	    		 */
        		write2Blacklist();	            
			}	    	
    	} catch (VersionRangeResolutionException | InvalidVersionSpecificationException | DependencyCollectionException e) {    		
    		logger.debug("Update failed.", e);
    		update = false;			
		}       		
        return update;
	}
	
	/**
	 * This method returns all dependencies as list.
	 * Elementary dependencies and their daughters are skipped. 
	 */
	private List<Dependency> getAllDependencies(DependencyNode startNode, boolean skipFramework){
		List<Dependency> retVal = new ArrayList<Dependency>();
		retVal.add(startNode.getDependency());
		for (DependencyNode node : startNode.getChildren()){
			if ((!skipFramework || !node.getDependency().getArtifact().getArtifactId().contains("salt-")) && !dependencyAlreadyInstalled(node.getArtifact().toString())) {
				retVal.addAll( getAllDependencies(node, skipFramework) );
			}			
		}
		return retVal;
	}
	
	/**
	 * Checks, if the given coords belong to a dependency that's already
	 * installed
	 * @param artifactString
	 * @return
	 */
	private boolean dependencyAlreadyInstalled(String artifactString){
		String[] coords = artifactString.split(DELIMITER);
		String[] testCoords = null;
		for (String dependencyString : forbiddenFruits){
			testCoords = dependencyString.split(DELIMITER);
			if (STATUS.OVERRIDABLE.equals(testCoords[4]) &&
				coords[1].equals(testCoords[1]) && /*artifactId*/
				coords[0].equals(testCoords[0]) /*groupId*/
				){
				/* check version */
				VersionScheme vScheme = new GenericVersionScheme();
				try {
					Version version = vScheme.parseVersion(coords[3]);
					Version testVersion = vScheme.parseVersion(testCoords[3]);
					if (version.compareTo(testVersion)<=0){
						return true;
					}
					else {
						/* find and delete dependency */
						String bundleName = pepperOSGiConnector.getBundleNameByDependency(coords[0], coords[1]);
						if (bundleName!=null){
							try {
								pepperOSGiConnector.remove(bundleName);
								logger.info("removed dependency "+coords[1]+". Newer version is about to be installed.");
								return false;
							} catch (BundleException | IOException e) {
								logger.warn("Could not delete dependency "+coords[1]+", so its older version remains.");
								return true;
							}
						}
					}
				} catch (InvalidVersionSpecificationException e) {
					logger.warn("Could not compare versions of dependency "+coords[1]+", so it will be dropped.");
				}				
				return true;
			} else if (STATUS.FINAL.equals(testCoords[4])){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * writes the old, freshly installed and forbidden dependencies to the blacklist file.
	 */
	private void write2Blacklist(){
		File blacklistFile = new File(BLACKLIST_PATH);
		if (!blacklistFile.exists()){
			blacklistFile.getParentFile().mkdirs();}
		try {
			blacklistFile.createNewFile();
			PrintWriter fW = new PrintWriter(blacklistFile);	
			BufferedWriter bW = new BufferedWriter(fW);
			for (String s : forbiddenFruits){
				bW.write(s);
				bW.newLine();
			}
			bW.close();
			fW.close();
		} catch (IOException e) {logger.debug("Could not write blacklist file.");}
		
	}
	
	/**
	 * 
	 * @returns the Blacklist of already installed or forbidden dependencies
	 */
	public String getBlacklist(){
		String lineSeparator = System.getProperty("line.separator");
		String indent = "\t";
		StringBuilder retVal = (new StringBuilder()).append(lineSeparator);
		retVal.append(indent).append("installed dependencies:").append(lineSeparator).append(lineSeparator);
		for (String s : forbiddenFruits){
			retVal.append(indent).append(s).append(lineSeparator);
		}
		return retVal.toString();
	}
	
	/**
	 * This method cleans the dependencies. Dependencies inherited from pepperParent as direct dependencies
	 * are removed.
	 * @param dependencies
	 * @param session
	 * @param parentVersion
	 * @return
	 */
	private List<Dependency> cleanDependencies(List<Dependency> dependencies, RepositorySystemSession session, String parentVersion){		
		Dependency pepperFramework = null;
		try {
			List<Dependency> parentDeps = parentDependencies.get(parentVersion.replace("-SNAPSHOT", ""));
			if (parentDeps==null){
				CollectRequest collectRequest = new CollectRequest();
		        collectRequest.setRoot( new Dependency( new DefaultArtifact("de.hu_berlin.german.korpling.saltnpepper", ARTIFACT_ID_PEPPER_PARENT, "pom", parentVersion), "" ) );
		        collectRequest.setRepositories(Booter.newRepositories(system, session));
		        collectRequest.addRepository(repos.get(KORPLING_MAVEN_REPO));
		        CollectResult collectResult;
				collectResult = system.collectDependencies( session, collectRequest );				
				parentDeps = getAllDependencies(collectResult.getRoot(), false);
				parentDependencies.put(parentVersion.replace("-SNAPSHOT", ""), parentDeps);
			} 
			Iterator<Dependency> itDeps = parentDeps.iterator();
			Dependency next = itDeps.next();
			while (!ARTIFACT_ID_PEPPER_FRAMEWORK.equals(next.getArtifact().getArtifactId()) && itDeps.hasNext()){
				next = itDeps.next();
			}
			itDeps = null;			
			int j=0;
			List<Dependency> newDeps = new ArrayList<Dependency>();
			pepperFramework = next;
			newDeps.add(pepperFramework);//we need pepper-framework on the list (TODO is it correct to assume that next==pepper-framework?
			for (int i=0; i<dependencies.size(); i++){
				j=0;
				next = dependencies.get(i);
				while (	j<parentDeps.size() &&
						!(next.getArtifact().getArtifactId().equals(parentDeps.get(j).getArtifact().getArtifactId()))
						){
					j++;
				}					
				if(j==parentDeps.size()){
					newDeps.add(next);
				}else{					
					forbiddenFruits.add(next.getArtifact().toString()+DELIMITER+STATUS.FINAL);
					logger.debug("The following dependency was put on blacklist, because it equals a parent dependency: "+next.getArtifact().toString());
				}
			}
			return newDeps;
		} catch (DependencyCollectionException e) {
			logger.warn("Could not collect dependencies for parent. No dependencies will be installed.");
		}       
		ArrayList<Dependency> retVal = new ArrayList<Dependency>();
		retVal.add(pepperFramework);
		return retVal;
	}
	
	/**
	 * This method builds a RemoteRepository for diverse maven/aether purposes.
	 * @param id
	 * @param url
	 * @return
	 */
	private RemoteRepository buildRepo(String id, String url){
		repoBuilder.setId(id);
		repoBuilder.setUrl(url);
		return repoBuilder.build();
	}
	
}

/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.repository.internal.DefaultArtifactDescriptorReader;
import org.apache.maven.repository.internal.DefaultVersionRangeResolver;
import org.apache.maven.repository.internal.DefaultVersionResolver;
import org.apache.maven.repository.internal.SnapshotMetadataGeneratorFactory;
import org.apache.maven.repository.internal.VersionsMetadataGeneratorFactory;
import org.eclipse.aether.AbstractRepositoryListener;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositoryEvent;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.artifact.DefaultArtifactType;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.collection.DependencyGraphTransformer;
import org.eclipse.aether.collection.DependencyManager;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.collection.DependencyTraverser;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.impl.ArtifactDescriptorReader;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.impl.MetadataGeneratorFactory;
import org.eclipse.aether.impl.VersionRangeResolver;
import org.eclipse.aether.impl.VersionResolver;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transfer.AbstractTransferListener;
import org.eclipse.aether.transfer.ArtifactNotFoundException;
import org.eclipse.aether.transfer.MetadataNotFoundException;
import org.eclipse.aether.transfer.TransferEvent;
import org.eclipse.aether.transfer.TransferResource;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.artifact.DefaultArtifactTypeRegistry;
import org.eclipse.aether.util.graph.manager.ClassicDependencyManager;
import org.eclipse.aether.util.graph.selector.AndDependencySelector;
import org.eclipse.aether.util.graph.selector.ExclusionDependencySelector;
import org.eclipse.aether.util.graph.selector.OptionalDependencySelector;
import org.eclipse.aether.util.graph.selector.ScopeDependencySelector;
import org.eclipse.aether.util.graph.transformer.ChainedDependencyGraphTransformer;
import org.eclipse.aether.util.graph.transformer.ConflictResolver;
import org.eclipse.aether.util.graph.transformer.JavaDependencyContextRefiner;
import org.eclipse.aether.util.graph.transformer.JavaScopeDeriver;
import org.eclipse.aether.util.graph.transformer.JavaScopeSelector;
import org.eclipse.aether.util.graph.transformer.NearestVersionSelector;
import org.eclipse.aether.util.graph.transformer.SimpleOptionalitySelector;
import org.eclipse.aether.util.graph.traverser.FatArtifactTraverser;
import org.eclipse.aether.util.repository.SimpleArtifactDescriptorPolicy;
import org.eclipse.aether.util.version.GenericVersionScheme;
import org.eclipse.aether.version.InvalidVersionSpecificationException;
import org.eclipse.aether.version.Version;
import org.eclipse.aether.version.VersionScheme;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * How does this class work?
 * One elementary part is the dependency blacklist. It stores maven artifact strings in this scheme:
 * 
 * groupId:artifactId:extension:version:STATUS
 * 
 * The status can be either FINAL or OVERRIDABLE. FINAL are only dependencies of pepper framework.
 * These dependencies cannot be overriden by newer versions, whilest dependencies with STATUS OVERRIDABLE
 * can. Dependencies of pepper plugins will be OVERRIDABLE, more details follow. * 
 *     
 * On the first run of pepper, the dependency blacklist is initialized with all dependencies of pepper-parent (includes
 * dependencies of pepper-framework) with STATUS FINAL, since there is no dependency black list file yet (and it HAS to be like that –
 * another option would be an empty black list file – but the assembly should never be allowed to include blacklist.cfg).
 * 
 * The dependency blacklist itself contains all dependencies, which are not supposed to be installed (e.g. dependencies
 * with scope "provided" and already installed dependencies). When update() terminates, this list is saved to the blacklist.cfg,
 * which is loaded on every start-up of pepper.
 * 
 * The core functionality of this class is to perform an update for a specified pepper plugin, when update() is
 * called. The update method uses two elementary methods: getAllDependencies() and cleanDependencies().
 * First of all, getAllDependencies() returns a list of all dependencies of the provided artifact recursively and breadth
 * first. Only dependencies with scope "test" and dependencies found on the blacklist are fully skipped, dependencies with scope "provided" are written
 * to the blacklist (OVERRIDABLE), because another plugin might be interested in installing it. But since another plugin already "knows", that this 
 * dependency is provided, there is no need to install it (it can be even dangerous). All Salt dependencies (salt-saltX itself) are dropped itself and
 * their children. Pepper-framework is put on the dependency list, but it's children are dropped. This is necessary to ensure we can determine the pepper
 * version the plugin was developed for.
 * After having collected all dependencies, they have to be cleaned, because we still have dependencies which are provided (but scope="compile")
 * by the system (e.g. OSGi and EMF dependencies). These are actually dependencies of pepper-Parent which are inherited as direct dependencies.
 * To deal with that, cleanDependencies() collects all dependencies of pepper-Parent (version determined with pepper-framework dependency) and
 * strikes them off the list.
 * One little detail: getAllDependencies() is version sensitive. cleanDependencies() doesn't have to be.
 * 
 */
public class MavenAccessor {
	
	private static final Logger logger = LoggerFactory.getLogger(MavenAccessor.class);
	
	/** This is used to install the bundles */
	private final PepperOSGiConnector pepperOSGiConnector;	
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
	public static final String KORPLING_MAVEN_REPO = "http://korpling.german.hu-berlin.de/maven2/";
	/** path to maven central */
	public static final String CENTRAL_REPO = "http://central.maven.org/maven2/";
	
	/** flag which is added to the blacklist entry of a dependency – a FINAL dependency can not be overridden */
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
		{
			DefaultServiceLocator locator = new DefaultServiceLocator();
	        locator.addService( ArtifactDescriptorReader.class, DefaultArtifactDescriptorReader.class );
	        locator.addService( VersionResolver.class, DefaultVersionResolver.class );
	        locator.addService( VersionRangeResolver.class, DefaultVersionRangeResolver.class );
	        locator.addService( MetadataGeneratorFactory.class, SnapshotMetadataGeneratorFactory.class );
	        locator.addService( MetadataGeneratorFactory.class, VersionsMetadataGeneratorFactory.class );
	        locator.addService( RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class );
	        locator.addService( TransporterFactory.class, FileTransporterFactory.class );
	        locator.addService( TransporterFactory.class, HttpTransporterFactory.class );	        
			system = locator.getService( RepositorySystem.class );			
		}
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
			} catch (IOException e) {logger.debug("Could not read blacklist file.", e);}
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
			
			DefaultRepositorySystemSession session = getNewSession();
			
	        RemoteRepository repo = buildRepo("korpling", KORPLING_MAVEN_REPO);
	        repos.put(KORPLING_MAVEN_REPO, repo);
	        repos.put(CENTRAL_REPO, buildRepo("central", CENTRAL_REPO));	        
			
			/* utils for dependency collection */
    		CollectRequest collectRequest = new CollectRequest();
            collectRequest.setRoot( new Dependency( pepArt, "" ) );
            collectRequest.setRepositories(null);
            collectRequest.addRepository(repo);
            collectRequest.addRepository(repos.get(CENTRAL_REPO));
            collectRequest.setRootArtifact(pepArt);
            try {
				CollectResult collectResult = system.collectDependencies( session, collectRequest );
				List<Dependency> allDeps = getAllDependencies(collectResult.getRoot(), false);
				parentDependencies.put(frameworkVersion.replace("-SNAPSHOT", ""), allDeps);
				Bundle bundle = null;
				String bundleName = null;
				STATUS status = null;
				for (Dependency dependency : allDeps){					
					bundleName = pepperOSGiConnector.getBundleNameByDependency(dependency.getArtifact().getGroupId(), dependency.getArtifact().getArtifactId());					
					bundle = pepperOSGiConnector.getBundle(bundleName, null);					
					status = bundle==null || bundle.getHeaders().get("Bundle-SymbolicName").contains("singleton:=true")? STATUS.FINAL : STATUS.OVERRIDABLE;
					forbiddenFruits.add(dependency.getArtifact().toString()+DELIMITER+status);
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
	
	private DefaultRepositorySystemSession getNewSession(){
		DefaultRepositorySystemSession session = new DefaultRepositorySystemSession();
		LocalRepository localRepo = new LocalRepository( pepperOSGiConnector.getPepperStarterConfiguration().getTempPath()+"/local-repo/" );
		LocalRepositoryManager repoManager = system.newLocalRepositoryManager( session, localRepo );
        session.setLocalRepositoryManager( repoManager );
        session.setRepositoryListener(repoListener);
        session.setTransferListener(transferListener);

        DependencyTraverser depTraverser = new FatArtifactTraverser();
        session.setDependencyTraverser( depTraverser );

        DependencyManager depManager = new ClassicDependencyManager();
        session.setDependencyManager( depManager );

        DependencySelector depFilter =
            new AndDependencySelector( new ScopeDependencySelector( "test"/*, "provided"*/ ),
                                       new OptionalDependencySelector(), new ExclusionDependencySelector() );
        session.setDependencySelector( depFilter );

        DependencyGraphTransformer transformer =
            new ConflictResolver( new NearestVersionSelector(), new JavaScopeSelector(),
                                  new SimpleOptionalitySelector(), new JavaScopeDeriver() );
        new ChainedDependencyGraphTransformer( transformer, new JavaDependencyContextRefiner() );
        session.setDependencyGraphTransformer( transformer );

        DefaultArtifactTypeRegistry stereotypes = new DefaultArtifactTypeRegistry();
        stereotypes.add( new DefaultArtifactType( "pom" ) );
        stereotypes.add( new DefaultArtifactType( "maven-plugin", "jar", "", "java" ) );
        stereotypes.add( new DefaultArtifactType( "jar", "jar", "", "java" ) );
        stereotypes.add( new DefaultArtifactType( "zip", "zip", "", "java" ) );
        stereotypes.add( new DefaultArtifactType( "ejb", "jar", "", "java" ) );
        stereotypes.add( new DefaultArtifactType( "ejb-client", "jar", "client", "java" ) );
        stereotypes.add( new DefaultArtifactType( "test-jar", "jar", "tests", "java" ) );
        stereotypes.add( new DefaultArtifactType( "javadoc", "jar", "javadoc", "java" ) );
        stereotypes.add( new DefaultArtifactType( "java-source", "jar", "sources", "java", false, false ) );
        stereotypes.add( new DefaultArtifactType( "war", "war", "", "java", false, true ) );
        stereotypes.add( new DefaultArtifactType( "ear", "ear", "", "java", false, true ) );
        stereotypes.add( new DefaultArtifactType( "rar", "rar", "", "java", false, true ) );
        stereotypes.add( new DefaultArtifactType( "par", "par", "", "java", false, true ) );
        session.setArtifactTypeRegistry( stereotypes );

        session.setArtifactDescriptorPolicy( new SimpleArtifactDescriptorPolicy( true, true ) );

        Properties sysProps = System.getProperties();
        session.setSystemProperties( sysProps );
        session.setConfigProperties( sysProps );
        
        return session;
	}
	
	/** This listener does not write the maven transfer output. */
	private final MavenTransferListener transferListener = new MavenTransferListener();
	
	/** This is used to write the Maven output onto our logger. The default {@link ConsoleRepositoryListener} writes it directly to System.out */
	private final MavenRepositoryListener repoListener = new MavenRepositoryListener();
	
	/**
	 * This method checks the provided pepper plugin for updates
	 * and triggers the installation process if a newer version is available
	 */
	public boolean update(String groupId, String artifactId, String repositoryUrl, boolean isSnapshot, boolean ignoreFrameworkVersion, Bundle installedBundle){
		if (forbiddenFruits.isEmpty() && !initDependencies()){
			logger.warn("Update could not be performed, because the pepper dependencies could not be listed.");
			return false;
		}
				
		if (logger.isTraceEnabled()){
			logger.trace("Starting update process for "+groupId+", "+artifactId+", "+repositoryUrl+", isSnapshot="+isSnapshot+", ignoreFrameworkVersion="+ignoreFrameworkVersion+", installedBundle="+installedBundle);
		} else {
			logger.info("Starting update process for "+artifactId);
		}	
		
		String newLine = System.getProperty("line.separator");
		
		DefaultRepositorySystemSession session = getNewSession();
        
        boolean update = true; //MUST be born true!       
        
        /*build repository*/
        
        RemoteRepository repo = repos.get(repositoryUrl);
        if (repo==null){
        	repo = buildRepo("repo", repositoryUrl);
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
    		while (!srcExists && itVersions.hasNext() && update){
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
		    					logger.warn("Plugin version "+newestVersion+" could not be found in repository. Checking the next lower version ...");
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
	            collectRequest.addRepository(repos.get(CENTRAL_REPO));
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
	            if (parentVersion==null){	            	
	            	logger.warn(artifactId+": Could not perform update: pepper-parent version could not be determined.");
	            	return false;
	            }            	
	            allDependencies = cleanDependencies(allDependencies, session, parentVersion);
	            Bundle bundle = null;
	            Dependency dependency = null;	            
	            //in the following we ignore the first dependency (i=0), because it is the module itself         	            
	            for (int i=1; i<allDependencies.size(); i++){
	            	dependency = allDependencies.get(i);
	            	if (ARTIFACT_ID_PEPPER_FRAMEWORK.equals(dependency.getArtifact().getArtifactId())){
	            		Version frameworkVersion = vScheme.parseVersion(pepperOSGiConnector.getFrameworkVersion().replace(".SNAPSHOT", "").replace("-SNAPSHOT", ""));
	            		Version depParentVersion = vScheme.parseVersion(parentVersion.replace("-SNAPSHOT", ""));
	            		int m = 1+Integer.parseInt(depParentVersion.toString().split("\\.")[0]);
	            		Version maxVersion = vScheme.parseVersion(m+".0.0");
	            		boolean compatible = depParentVersion.compareTo(frameworkVersion)<=0 && frameworkVersion.compareTo(maxVersion)<0 
	            				&& !(frameworkVersion.equals(depParentVersion) && pepperOSGiConnector.getFrameworkVersion().contains("SNAPSHOT") && !parentVersion.contains("SNAPSHOT"));	            		
	            		if (!ignoreFrameworkVersion && !compatible){	            			
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
	            		artifactRequest.addRepository(repos.get(CENTRAL_REPO));
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
			if (e instanceof DependencyCollectionException){
				Throwable t = e.getCause();
				while(t.getCause()!=null){
					t = t.getCause();
				}
				if (t instanceof ArtifactNotFoundException){
					if (logger.isDebugEnabled()) {
						logger.debug(t.getMessage(), e);
					}else{
						logger.warn("Update of "+artifactId+" failed, could not resolve dependencies "/*, e*/);//TODO decide
					}
				}
			}
    		update = false;			
		}       		
        return update;
	}
	
	/**
	 * This method returns all dependencies as list.
	 * Elementary dependencies and their daughters are skipped. 
	 */
	private List<Dependency> getAllDependencies(DependencyNode startNode, boolean skipFramework){		
		if ("provided".equalsIgnoreCase(startNode.getDependency().getScope())){
			forbiddenFruits.add(startNode.getDependency().getArtifact().toString()+DELIMITER+STATUS.OVERRIDABLE);
			return Collections.<Dependency>emptyList();
		}
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
						/* find dependency and delete old bundle, if it (or the new) is singleton */						
						String bundleName = pepperOSGiConnector.getBundleNameByDependency(coords[0], coords[1]);
						Bundle oldBundle = pepperOSGiConnector.getBundle(bundleName, testCoords[3]);						
						if (bundleName!=null){ //FIXME right now we are assuming, that a bundle's singleton status does never change between versions
							try {
								if (oldBundle.getHeaders().get("Bundle-SymbolicName").contains("singleton:=true")) {pepperOSGiConnector.remove(bundleName);}
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
	 * This method cleans the dependencies, i.e. dependencies inherited from pepperParent as direct dependencies
	 * are removed.
	 * @param dependencies
	 * @param session
	 * @param parentVersion
	 * @return
	 */
	private List<Dependency> cleanDependencies(List<Dependency> dependencies, RepositorySystemSession session, String parentVersion){		
		Dependency pepperFramework = null;
		try {
			final List<Dependency> parentDeps;
			List<Dependency> checkList = parentDependencies.get(parentVersion.replace("-SNAPSHOT", ""));
			if (checkList==null){
				CollectRequest collectRequest = new CollectRequest();
		        collectRequest.setRoot( new Dependency( new DefaultArtifact("de.hu_berlin.german.korpling.saltnpepper", ARTIFACT_ID_PEPPER_PARENT, "pom", parentVersion), "" ) );
		        collectRequest.addRepository(repos.get(CENTRAL_REPO));
		        collectRequest.addRepository(repos.get(KORPLING_MAVEN_REPO));
		        CollectResult collectResult;
				collectResult = system.collectDependencies( session, collectRequest );				
				parentDeps = getAllDependencies(collectResult.getRoot(), false);				
				parentDependencies.put(parentVersion.replace("-SNAPSHOT", ""), parentDeps);
			}else{
				parentDeps = checkList;
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
			STATUS status = null;
			for (int i=0; i<dependencies.size(); i++){
				j=0;
				next = dependencies.get(i);
				status = getDependencyStatus(next.toString());
				while (	j<parentDeps.size() &&
						!(next.getArtifact().getArtifactId().equals(parentDeps.get(j).getArtifact().getArtifactId())||STATUS.OVERRIDABLE.equals(status))
						){
					j++;
				}					
				if(j==parentDeps.size() || STATUS.OVERRIDABLE.equals(status)){
					newDeps.add(next);
				}else{					
					forbiddenFruits.add(next.getArtifact().toString()+DELIMITER+STATUS.FINAL);
					logger.debug("The following dependency was put on blacklist, because it equals a parent dependency: "+next.getArtifact().toString());
				}
			}
			newDeps.add(pepperFramework);//we need pepper-framework on the list
			return newDeps;
		} catch (DependencyCollectionException e) {
			logger.warn("Could not collect dependencies for parent. No dependencies will be installed.");
		}       
		ArrayList<Dependency> retVal = new ArrayList<Dependency>();
		retVal.add(pepperFramework);
		return retVal;
	}
	
	private STATUS getDependencyStatus(String dependencyString){	
		dependencyString = dependencyString.substring(0, dependencyString.lastIndexOf(':'));
		for (String fruit : forbiddenFruits){
			if (fruit.startsWith(dependencyString)){
				if (fruit.split(DELIMITER)[4].equals(STATUS.FINAL)){
					return STATUS.FINAL;
				}
				return STATUS.OVERRIDABLE;				
			}
		}
		return null;
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
	
	private class MavenRepositoryListener extends AbstractRepositoryListener{
		private final boolean TRACE; 
		private MavenRepositoryListener() {
			TRACE = logger.isTraceEnabled();
		}
		
		@Override
		public void artifactDeployed( RepositoryEvent event ){
	        if (TRACE) { logger.trace( "Deployed " + event.getArtifact() + " to " + event.getRepository() );  }
	    }
		@Override
	    public void artifactDeploying( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Deploying " + event.getArtifact() + " to " + event.getRepository() ); }
	    }
		@Override
	    public void artifactDescriptorInvalid( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Invalid artifact descriptor for " + event.getArtifact() + ": "
	            + event.getException().getMessage() ); }
	    }
		@Override
	    public void artifactDescriptorMissing( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Missing artifact descriptor for " + event.getArtifact() ); }
	    }
		@Override
	    public void artifactInstalled( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Installed " + event.getArtifact() + " to " + event.getFile() ); }
	    }
		@Override
	    public void artifactInstalling( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Installing " + event.getArtifact() + " to " + event.getFile() ); }
	    }
		@Override
	    public void artifactResolved( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Resolved artifact " + event.getArtifact() + " from " + event.getRepository() ); }
	    }
		@Override
	    public void artifactDownloading( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Downloading artifact " + event.getArtifact() + " from " + event.getRepository() ); }
	    }
		@Override
	    public void artifactDownloaded( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Downloaded artifact " + event.getArtifact() + " from " + event.getRepository() ); }
	    }
		@Override
	    public void artifactResolving( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Resolving artifact " + event.getArtifact() ); }
	    }
		@Override
	    public void metadataDeployed( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Deployed " + event.getMetadata() + " to " + event.getRepository() ); }
	    }
		@Override
	    public void metadataDeploying( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Deploying " + event.getMetadata() + " to " + event.getRepository() ); }
	    }
		@Override
	    public void metadataInstalled( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Installed " + event.getMetadata() + " to " + event.getFile() ); }
	    }
		@Override
	    public void metadataInstalling( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Installing " + event.getMetadata() + " to " + event.getFile() ); }
	    }
		@Override
	    public void metadataInvalid( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Invalid metadata {}", event.getMetadata() ); }
	    }
		@Override
	    public void metadataResolved( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Resolved metadata " + event.getMetadata() + " from " + event.getRepository() ); }
	    }
		@Override
	    public void metadataResolving( RepositoryEvent event ){
			if (TRACE) { logger.trace( "Resolving metadata " + event.getMetadata() + " from " + event.getRepository() ); }
	    }
	}
	
	private class MavenTransferListener extends AbstractTransferListener{
		private final boolean TRACE;
		
		private MavenTransferListener(){
			TRACE = logger.isTraceEnabled();
		}	    

	    @Override
	    public void transferInitiated( TransferEvent event )
	    {
	        String message = event.getRequestType() == TransferEvent.RequestType.PUT ? "Uploading" : "Downloading";

	        if (TRACE) { logger.trace( message + ": " + event.getResource().getRepositoryUrl() + event.getResource().getResourceName() ); }
	    }

	    @Override
	    public void transferProgressed( TransferEvent event ){	    	
	    }

	    @Override
	    public void transferSucceeded( TransferEvent event )
	    {
	    	if (TRACE){
	    		transferCompleted( event );

		        TransferResource resource = event.getResource();
		        long contentLength = event.getTransferredBytes();
		        if ( contentLength >= 0 ){
		            String type = ( event.getRequestType() == TransferEvent.RequestType.PUT ? "Uploaded" : "Downloaded" );
		            String len = contentLength >= 1024 ? toKB( contentLength ) + " KB" : contentLength + " B";

		            String throughput = "";
		            long duration = System.currentTimeMillis() - resource.getTransferStartTime();
		            if ( duration > 0 )
		            {
		                long bytes = contentLength - resource.getResumeOffset();
		                DecimalFormat format = new DecimalFormat( "0.0", new DecimalFormatSymbols( Locale.ENGLISH ) );
		                double kbPerSec = ( bytes / 1024.0 ) / ( duration / 1000.0 );
		                throughput = " at " + format.format( kbPerSec ) + " KB/sec";
		            }

		            logger.trace( type + ": " + resource.getRepositoryUrl() + resource.getResourceName() + " (" + len + throughput + ")" );
		        }
	    	}	        
	    }

	    @Override
	    public void transferFailed( TransferEvent event ){
	    	if (TRACE){
	    		transferCompleted( event );
		        if ( !( event.getException() instanceof MetadataNotFoundException ) ){
		            logger.trace("An error occured transfering "+event.getResource(), event.getException());		            
		        }
	    	}	        
	    }

	    private void transferCompleted( TransferEvent event ){
	        logger.trace("Transfer completed.");
	    }

	    public void transferCorrupted( TransferEvent event ){
	        if (TRACE){logger.trace("Transfer corrupted.", event.getException());}
	    }

	    protected long toKB( long bytes ){
	        return ( bytes + 1023 ) / 1024;
	    }

	}
}

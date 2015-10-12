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
package org.corpus_tools.pepper.connectors.impl;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.client.HttpResponseException;
import org.apache.maven.repository.internal.DefaultArtifactDescriptorReader;
import org.apache.maven.repository.internal.DefaultVersionRangeResolver;
import org.apache.maven.repository.internal.DefaultVersionResolver;
import org.apache.maven.repository.internal.SnapshotMetadataGeneratorFactory;
import org.apache.maven.repository.internal.VersionsMetadataGeneratorFactory;
import org.codehaus.plexus.util.FileUtils;
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
import org.eclipse.aether.version.VersionRange;
import org.eclipse.aether.version.VersionScheme;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

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
	
	/** path to temporary repository */
	private final String PATH_LOCAL_REPO;
	
	/* MAVEN UTILS */
	/** maven/aether utility */
	RepositorySystem mvnSystem = null;
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
			mvnSystem = locator.getService( RepositorySystem.class );			
		}
		repoBuilder = new RemoteRepository.Builder("", "default", "");
		repos = new HashMap<String, RemoteRepository>();
		forbiddenFruits = new HashSet<String>();
		parentDependencies = new HashMap<String, List<Dependency>>();
		PATH_LOCAL_REPO = pepperOSGiConnector.getPepperStarterConfiguration().getTempPath().getAbsolutePath().concat("/local-repo/");	
		try {
			File lr = new File(PATH_LOCAL_REPO);
			if (lr.exists()){
				FileUtils.deleteDirectory(lr);
			}			
		} catch (IOException e) {
			logger.warn("Failed to clean local repository.");
		}
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
		RemoteRepository repo = getRepo("korpling", KORPLING_MAVEN_REPO);
        getRepo("central", CENTRAL_REPO);	
        
		if (forbiddenFruits.isEmpty()){
			logger.info("Configuring update mechanism ...");
			/* maven access utils*/
			Artifact pepArt = new DefaultArtifact("de.hu_berlin.german.korpling.saltnpepper", ARTIFACT_ID_PEPPER_PARENT, "pom", frameworkVersion);
			
			DefaultRepositorySystemSession session = getNewSession();                
			
			/* utils for dependency collection */
    		CollectRequest collectRequest = new CollectRequest();
            collectRequest.setRoot( new Dependency( pepArt, "" ) );
            collectRequest.setRepositories(null);
            collectRequest.addRepository(repo);
            collectRequest.addRepository(repos.get(CENTRAL_REPO));
            collectRequest.setRootArtifact(pepArt);
            try {
				CollectResult collectResult = mvnSystem.collectDependencies( session, collectRequest );
				List<Dependency> allDeps = getAllDependencies(collectResult.getRoot(), false);
				parentDependencies.put(frameworkVersion.replace("-SNAPSHOT", ""), allDeps);
				Bundle bundle = null;
				STATUS status = null;
				for (Dependency dependency : allDeps){					
					bundle = pepperOSGiConnector.getBundle(dependency.getArtifact().getGroupId(), dependency.getArtifact().getArtifactId(), null);					
					status = bundle==null || bundle.getHeaders().get("Bundle-SymbolicName").contains("singleton:=true")? STATUS.FINAL : STATUS.OVERRIDABLE;
					forbiddenFruits.add(dependency.getArtifact().toString().concat(DELIMITER).concat(status.toString()).concat(DELIMITER).concat(bundle==null?"":bundle.getSymbolicName()));
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
		LocalRepository localRepo = new LocalRepository( PATH_LOCAL_REPO );
		LocalRepositoryManager repoManager = mvnSystem.newLocalRepositoryManager( session, localRepo );
        session.setLocalRepositoryManager( repoManager );
        session.setRepositoryListener(repoListener);
        session.setTransferListener(transferListener);

        DependencyTraverser depTraverser = new FatArtifactTraverser();
        session.setDependencyTraverser( depTraverser );

        DependencyManager depManager = new ClassicDependencyManager();
        session.setDependencyManager( depManager );

        DependencySelector depFilter =
            new AndDependencySelector( new ScopeDependencySelector( "test", "Test"/*, "provided"*/ ),
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
        
        RemoteRepository repo = getRepo("repo", repositoryUrl); 
        
        /*build artifact*/
        Artifact artifact = new DefaultArtifact(groupId, artifactId, "zip","[0,)");       
        
        try {
	        /*version range request*/
	        VersionRangeRequest rangeRequest = new VersionRangeRequest();	        
	        rangeRequest.addRepository(repo);
	        rangeRequest.setArtifact(artifact);
	        VersionRangeResult rangeResult = mvnSystem.resolveVersionRange(session, rangeRequest);
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
		    					artifactResult = mvnSystem.resolveArtifact(session, artifactRequest);		    			
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
	    		/* create list of necessary repositories */
	    		Artifact pom = new DefaultArtifact(artifact.getGroupId(), artifact.getArtifactId(), "pom", artifact.getVersion());
	    		artifactRequest.setArtifact(pom);
	    		boolean pomReadingErrors = false;
	    		try {
	    			artifactResult = null;
					artifactResult = mvnSystem.resolveArtifact(session, artifactRequest);
				} catch (ArtifactResolutionException e1) {
					pomReadingErrors = true;					
				}
	    		List<RemoteRepository> repoList = new ArrayList<RemoteRepository>();
	    		repoList.add(repos.get(CENTRAL_REPO));
	    		repoList.add(repos.get(repositoryUrl));
	    		if (artifactResult!=null && artifactResult.getArtifact().getFile().exists()){
	    			try {
	    				SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();	    			
	    				saxParser.parse(artifactResult.getArtifact().getFile().getAbsolutePath(), new POMReader(repoList));
	    			} catch (SAXException | IOException | ParserConfigurationException e) {
	    				pomReadingErrors = true;
	    			}	    			
	    		}
	    		if (pomReadingErrors) {
	    			logger.warn("Could not determine all relevant repositories, update might fail. Trying to continue ...");
	    			repoList.add(repos.get(KORPLING_MAVEN_REPO));
	    		}    			
	    		
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
	            collectRequest.setRepositories(repoList);
	            CollectResult collectResult = mvnSystem.collectDependencies( session, collectRequest );          
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
	            VersionRange range = isCompatiblePlugin(parentVersion);
	            if (!ignoreFrameworkVersion && range!=null){
        			logger.info(
        					(new StringBuilder())
        					.append("No update was performed because of a version incompatibility according to pepper-framework: ")
        					.append(newLine).append(artifactId).append(" only supports ").append(range.toString()).append(", but ").append(pepperOSGiConnector.getFrameworkVersion()).append(" is installed!")
        					.append(newLine).append("You can make pepper ignore this by using \"update").append(isSnapshot? " snapshot ":" ").append("iv ")
        					.append(artifactId).append("\"").toString());	            			
        			return false;
        		}
	            allDependencies = cleanDependencies(allDependencies, session, parentVersion);
	            Bundle bundle = null;
	            Dependency dependency = null;	      
	            //in the following we ignore the first dependency (i=0), because it is the module itself         	            
	            for (int i=1; i<allDependencies.size(); i++){
	            	dependency = allDependencies.get(i);
	            	if (!ARTIFACT_ID_PEPPER_FRAMEWORK.equals(dependency.getArtifact().getArtifactId())) { 
	            		artifactRequest = new ArtifactRequest();
	            		artifactRequest.setArtifact(dependency.getArtifact());     		
	            		artifactRequest.setRepositories(repoList);
	            		try{
	            			artifactResult = mvnSystem.resolveArtifact(session, artifactRequest);
		            		installArtifacts.add(artifactResult.getArtifact());
	            		}catch (ArtifactResolutionException e){	            			
	            			logger.warn("Artifact "+dependency.getArtifact().getArtifactId()+" could not be resolved. Dependency will not be installed.");	            				            			
	            			if (!Boolean.parseBoolean(pepperOSGiConnector.getPepperStarterConfiguration().getProperty("pepper.forceUpdate").toString())) {
	            				logger.error("Artifact ".concat(artifact.getArtifactId()).concat(" will not be installed. Resolution of dependency ").concat(dependency.getArtifact().getArtifactId()).concat(" failed and \"force update\" is disabled in pepper.properties."));
	            				return false;
	            			}
	            		}
	            	}
	            }	            
	            artifact = null;
	            Artifact installArtifact = null;
	            for (int i=installArtifacts.size()-1; i>=0; i--){	            	
	            	try {	            		
	            		installArtifact = installArtifacts.get(i);	            		
	            		logger.info("installing: "+installArtifact);	            		
	            		bundle = pepperOSGiConnector.installAndCopy(installArtifact.getFile().toURI());
	            		if (i!=0){//the module itself must not be put on the blacklist
	            			putOnBlacklist(installArtifact);	            			
	            		}else if (installedBundle!=null){
	            			pepperOSGiConnector.remove(installedBundle.getSymbolicName());
	            			logger.info("Successfully removed version ".concat(installedBundle.getVersion().toString()).concat(" of ").concat(artifactId));
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
				if (t instanceof HttpResponseException){
					logger.error("Dependency resolution failed!"+System.lineSeparator()+"\tUnsatisfying http response: "+t.getMessage());
				}
			}
    		update = false;			
		}       		
        return update;
	}
	
	private VersionRange isCompatiblePlugin(String pluginFrameworkVersion){
		VersionScheme vScheme = new GenericVersionScheme();
		Version frameworkVersion;
		try {
			frameworkVersion = vScheme.parseVersion(pepperOSGiConnector.getFrameworkVersion().replace(".SNAPSHOT", "-SNAPSHOT"));		
			final Version depParentVersion = vScheme.parseVersion(pluginFrameworkVersion);
			int m = 1+Integer.parseInt(depParentVersion.toString().split("\\.")[0]);
			final Version maxVersion = vScheme.parseVersion(m+".0.0");
			String rangeString = "[".concat(pluginFrameworkVersion).concat(",").concat(maxVersion.toString()).concat(")");
			VersionRange range = vScheme.parseVersionRange(rangeString);
			if (!range.containsVersion(frameworkVersion)){
				return range;
			}
		} catch (InvalidVersionSpecificationException e) {
			logger.error("Could not compare required framework version to running framework. Trying to perform update anyway...");			
		}
		return null;
	}
	
	/**
	 * This method returns all dependencies as list.
	 * Elementary dependencies and their daughters are skipped. 
	 */
	private List<Dependency> getAllDependencies(DependencyNode startNode, boolean skipFramework){		
		List<Dependency> retVal = new ArrayList<Dependency>();
		retVal.add(startNode.getDependency());
		for (DependencyNode node : startNode.getChildren()){
			boolean isFramework = ARTIFACT_ID_PEPPER_FRAMEWORK.equals(node.getArtifact().getArtifactId());
			boolean isSalt = node.getArtifact().getArtifactId().contains("salt-");
			if ((isFramework&&!skipFramework)||(!isFramework&&!isSalt)) {
				String blackListLine = getBlackListString(node.getArtifact());
				if (blackListLine!=null && blackListLine.split(DELIMITER)[4].equals(STATUS.FINAL.toString())){//dependency already installed AND singleton
					//do nothing at the Moment (TODO-> maybe implement a version range check, that enables an exchange of singletons)
				}
				else{//dependency not installed yet or not singleton
					if ("provided".equalsIgnoreCase(startNode.getDependency().getScope())){
						putOnBlacklist(node.getArtifact());
						return Collections.<Dependency>emptyList();
					}else{
						retVal.addAll(getAllDependencies(node, skipFramework));
					}					
				}
			}
			else if (skipFramework&&isFramework){//we need this for checking compatibility
				retVal.add(node.getDependency());
			}
		}
		return retVal;
	}
	
	private void putOnBlacklist(Artifact artifact){
		if (getBlackListString(artifact)==null){//for safety reasons (future use of this method, etc) we do the check 
			Bundle bundle = pepperOSGiConnector.getBundle(artifact.getGroupId(), artifact.getArtifactId(), null);
			STATUS status = bundle==null || !pepperOSGiConnector.isSingleton(bundle)? STATUS.OVERRIDABLE : STATUS.FINAL;
			forbiddenFruits.add(artifact.toString().concat(DELIMITER).concat(status.toString()).concat(DELIMITER).concat(bundle==null?"":bundle.getSymbolicName()));
			logger.debug("Put dependency on blacklist: ".concat(artifact.toString()));
		}
	}
	private String getBlackListString(Artifact artifact){
		String as = artifact.toString().substring(0, artifact.toString().lastIndexOf(DELIMITER.charAt(0)));
		for (String artifactString : forbiddenFruits){
			if (artifactString.startsWith(as) || artifactString.split(":")[1].equals(as.split(":")[1])){				
				return artifactString;
			}
		}
		return null;
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
		try {
			final List<Dependency> parentDeps;
			List<Dependency> checkList = parentDependencies.get(parentVersion.replace("-SNAPSHOT", ""));
			if (checkList==null){
				CollectRequest collectRequest = new CollectRequest();
		        collectRequest.setRoot( new Dependency( new DefaultArtifact("de.hu_berlin.german.korpling.saltnpepper", ARTIFACT_ID_PEPPER_PARENT, "pom", parentVersion), "" ) );
		        collectRequest.addRepository(repos.get(CENTRAL_REPO));
		        collectRequest.addRepository(repos.get(KORPLING_MAVEN_REPO));
		        CollectResult collectResult;
				collectResult = mvnSystem.collectDependencies( session, collectRequest );				
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
			return newDeps;
		} catch (DependencyCollectionException e) {
			logger.warn("Could not collect dependencies for parent. No dependencies will be installed.");
		}       
		ArrayList<Dependency> retVal = new ArrayList<Dependency>();
		return retVal;
	}
	
	private STATUS getDependencyStatus(String dependencyString){	
		dependencyString = dependencyString.substring(0, dependencyString.lastIndexOf(':'));
		for (String fruit : forbiddenFruits){
			if (fruit.startsWith(dependencyString)){
				if (fruit.split(DELIMITER)[4].equals(STATUS.FINAL.toString())){
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
	private RemoteRepository getRepo(String id, String url){
		RemoteRepository repo = repos.get(url);
		if (repo==null){
			repoBuilder.setId(id);
			repoBuilder.setUrl(url);
			repo = repoBuilder.build();
			repos.put(url, repo);
		}		 
		return repo;
	}
	
	/** This method starts invokes the computation of the dependency tree. If no version is
	 * provided, the highest version in the specified maven repository is used. If no repository
	 * is provided, maven central and the korpling maven repository are used for trial. */
	public String printDependencies(String groupId, String artifactId, String version, String repositoryUrl){
		/* repositories */
		RemoteRepository repo = null;
		if (repositoryUrl==null){        	
        	repo = getRepo("korpling", KORPLING_MAVEN_REPO);      	        	
        } else {
	        repo = getRepo("repository", repositoryUrl);        
        }
		/* version range resolution and dependency collection */
		DefaultRepositorySystemSession session = getNewSession();
		Artifact artifact = new DefaultArtifact(groupId, artifactId, "pom", version==null? "[0,)" : version);
		if (version==null){
			VersionRangeRequest request = new VersionRangeRequest();
			request.setArtifact(artifact);
			if (repositoryUrl==null){request.addRepository(repos.get(CENTRAL_REPO));}
			request.addRepository(repo);
			try {
				VersionRangeResult rangeResult = mvnSystem.resolveVersionRange(session, request);
				version = rangeResult.getHighestVersion().toString();
				artifact.setVersion(version);
			} catch (VersionRangeResolutionException e) {
				logger.error("Could not determine newest version.");
				return null;
			}			
		}
		CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot( new Dependency( artifact, "" ) );        
        if (repositoryUrl==null){collectRequest.addRepository(repos.get(CENTRAL_REPO));}
	    collectRequest.addRepository(repo);
        CollectResult collectResult;        
		try {
			collectResult = mvnSystem.collectDependencies( session, collectRequest );			
			return getDependencyPrint(collectResult.getRoot(), 0);
		} catch (DependencyCollectionException e) {
			logger.error("Could not print dependencies for ".concat(artifactId).concat("."));
		}           
         return null;
	}
	
	/** this method recursively computes */
	private String getDependencyPrint(DependencyNode startNode, int depth){
		String d = "";
		for (int i=0; i<depth; i++){
			d+=" ";
		}
		d+= depth==0? "" : "+- ";
		d+=startNode.getArtifact().toString().concat(" (").concat(startNode.getDependency().getScope()).concat(")");
		for (DependencyNode node : startNode.getChildren()){
			d+=System.lineSeparator().concat(getDependencyPrint(node, depth+1));
		}
		return d;
	}
	
	/** This method tries to determine maven project coordinates from a bundle id to
	 * invoke {@link #printDependencies(String, String, String, String)}. */
	protected String printDependencies(Bundle bundle){
		String[] coords = null;
		for (String s : forbiddenFruits){
			coords = s.split(DELIMITER);
			if (bundle!=null && coords.length==6 && coords[5].equals(bundle.getSymbolicName())){
				return printDependencies(coords[0], coords[1], coords[3].replace(".SNAPSHOT", "-SNAPSHOT"), null);				
			}
		}
		//maven coordinates could not be determined, assume, we talk about a pepper plugin:		
		return printDependencies(bundle.getSymbolicName().substring(0, bundle.getSymbolicName().lastIndexOf('.')), 
									bundle.getSymbolicName().substring(bundle.getSymbolicName().lastIndexOf('.')+1), 
									bundle.getVersion().toString(), KORPLING_MAVEN_REPO);
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
	
	private class POMReader extends DefaultHandler2{
		
		private boolean read = true;
		
		static final String TAG_REPOSITORIES = "repositories";
		static final String TAG_REPOSITORY = "repository";
		static final String TAG_URL = "url";
		static final String TAG_ID = "id";
		static final String ROOT_TAG = "project";
		
		private String pppparent = null;
		private String ppparent = null;
		private String pparent = null;
		private String parent = null;
		private String currTag = null;
		private StringBuilder chars = null;
		
		private String url = null;
		private String id = null;
		
		private List<RemoteRepository> resultsList;
		
		private POMReader(List<RemoteRepository> targetList){
			chars = new StringBuilder();
			resultsList = targetList;
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes){
			if (read) {
				localName = qName.substring(qName.lastIndexOf(":") + 1);
				pppparent = ppparent;
				ppparent = pparent;
				pparent = parent;
				parent = currTag;
				currTag = localName;
				chars.delete(0, chars.length());
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (read && ROOT_TAG.equals(ppparent) && TAG_REPOSITORIES.equals(pparent) && TAG_REPOSITORY.equals(parent) && (TAG_ID.equals(currTag)||TAG_URL.equals(currTag))){
				for (int i=start; i<start+length; i++){
					chars.append(ch[i]);
				}
			}			
		}
		
		@Override
		public void endElement(java.lang.String uri, String localName, String qName) throws SAXException {
			if (read) {
				localName = qName.substring(qName.lastIndexOf(":") + 1);				
				boolean constraint = ROOT_TAG.equals(ppparent) && TAG_REPOSITORIES.equals(pparent) && TAG_REPOSITORY.equals(parent);
				if (constraint && TAG_URL.equals(localName)) {
					url = chars.toString();
				}
				else if (constraint && TAG_ID.equals(localName)){
					id = chars.toString();
				}
				else if (ROOT_TAG.equals(pparent) && TAG_REPOSITORIES.equals(parent) && TAG_REPOSITORY.equals(localName)){
					resultsList.add(getRepo(id, url));
				}
				else if (ROOT_TAG.equals(parent) && TAG_REPOSITORIES.equals(localName)) {
					read = false;
				}
				chars.delete(0, chars.length());
				currTag = parent;
				parent = pparent;
				pparent = ppparent;
				ppparent = pppparent;
				pppparent = null;
			}
		}
	}
}

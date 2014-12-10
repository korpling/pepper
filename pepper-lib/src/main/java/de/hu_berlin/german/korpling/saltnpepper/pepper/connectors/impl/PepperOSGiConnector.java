/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.examples.util.Booter;
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
import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.OutputStreamAppender;
import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.PepperStarterConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.exceptions.PepperOSGiException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.exceptions.PepperOSGiFrameworkPluginException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.exceptions.PepperPropertyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.PepperConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperOSGiRunner;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.JobNotFoundException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperConfigurationException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.filefilter.SuffixFileFilter;

/**
 * This class is an implementation of {@link Pepper}. It acts as a bridge
 * between the pure java environment and the Pepper universe inside the OSGi
 * environment. This class should help not dealing with OSGi issues when using
 * Pepper and therefore enables it to use Pepper as an embedded library.
 * 
 * @author Florian Zipser
 * 
 */
public class PepperOSGiConnector implements Pepper, PepperConnector {

	private static final Logger logger = LoggerFactory.getLogger(PepperOSGiConnector.class);

	private Set<String> forbiddenFruits = null;
	private static final String blacklistPath = "./conf/dep/blacklist.cfg";
	/** this String contains the artifactId of pepper-framework */
	private static final String ARTIFACT_ID_PEPPER_FRAMEWORK = "pepper-framework";
	/** contains the version of pepper framework */
	private String frameworkVersion;
	
	/**
	 * FIXME This is just a workaround to set the current version of Pepper,
	 * this is necessary, mark the Pepper package, to be load by the classloader
	 * in and outside of OSGi. This could be removed, when there is a better way
	 * to detect the current Pepper version automatically.
	 */
	public static final String PEPPER_VERSION = "2.0.1";

	/** Determines if this object has been initialized **/
	private boolean isInit = false;

	public boolean isInitialized() {
		return (isInit);
	}
	/**
	 * Starts the OSGi environment and installs and starts all bundles located
	 * in the plugin directory. <br/>
	 * Sets property {@link PepperOSGiRunner#PROP_TEST_DISABLED} to true.
	 */
	@Override
	public void init() {
		if (getPepperStarterConfiguration().getPlugInPath() == null) {
			throw new PepperPropertyException("Cannot start Pepper, because no plugin path is given for Pepper modules.");
		}
		try {
			// disable PepperOSGiRunner
			System.setProperty(PepperOSGiRunner.PROP_TEST_DISABLED, Boolean.TRUE.toString());

			setBundleContext(this.startEquinox());
		} catch (Exception e) {
			throw new PepperOSGiException("The OSGi environment could not have been started. ", e);
		}
		try {
			logger.debug("plugin path:\t\t" + getPepperStarterConfiguration().getPlugInPath());

			logger.debug("installing OSGI-bundles...");
			logger.debug("-------------------- installing bundles --------------------");
			Collection<Bundle> bundles = null;

			// installing module-bundles
			List<URI> dropInURIs = null;
			List<String> dropInRawStrings = getPepperStarterConfiguration().getDropInPaths();
			if (dropInRawStrings != null) {
				dropInURIs = new ArrayList<>(dropInRawStrings.size());
				for (String path : dropInRawStrings) {
					dropInURIs.add(new File(path).toURI());
				}
			}
			logger.debug("\tinstalling OSGI-bundles:");
      
			bundles = this.installBundles(new File(getPepperStarterConfiguration().getPlugInPath()).toURI(),
				dropInURIs);
			logger.debug("----------------------------------------------------------");
			logger.debug("installing OSGI-bundles...FINISHED");
			logger.debug("starting OSGI-bundles...");
			logger.debug("-------------------- starting bundles --------------------");
			if ((bundles == null) || (bundles.isEmpty())) {
				bundles = new ArrayList<>();
				bundleIdMap = new Hashtable<>();
				for (Bundle bundle : getBundleContext().getBundles()) {
					bundles.add(bundle);
					bundleIdMap.put(bundle.getBundleId(), bundle);
				}
			}

			this.startBundles(bundles);
			logger.debug("----------------------------------------------------------");
			logger.debug("starting OSGI-bundles...FINISHED");
		} catch (PepperException e) {
			throw e;
		} catch (Exception e) {
			throw new PepperOSGiException("An exception occured installing bundles for OSGi environment. ", e);
		}
		/* read/write dependency blacklist */
		File blacklistFile = new File(blacklistPath);
		forbiddenFruits = new HashSet<String>();
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
			} catch (IOException e) {}
		}
		frameworkVersion = getFrameworkVersion();
		if (forbiddenFruits.isEmpty()){
			/* maven access utils*/			
			Artifact pepArt = new DefaultArtifact("de.hu_berlin.german.korpling.saltnpepper", ARTIFACT_ID_PEPPER_FRAMEWORK, "jar", frameworkVersion);
			RepositorySystem system = Booter.newRepositorySystem();
	        RepositorySystemSession session = Booter.newRepositorySystemSession( system );
	        RemoteRepository.Builder repoBuilder = new RemoteRepository.Builder("korpling", "default", "http://korpling.german.hu-berlin.de/maven2");
	        RemoteRepository repo = repoBuilder.build();
			
			/* utils for dependency collection */
    		CollectRequest collectRequest = new CollectRequest();
            collectRequest.setRoot( new Dependency( pepArt, "" ) );
            collectRequest.setRepositories(null);
            collectRequest.addRepository(repo);	            
            collectRequest.setRootArtifact(pepArt);
            try {
				CollectResult collectResult = system.collectDependencies( session, collectRequest );
				write2Blacklist(getAllDependencies(collectResult.getRoot(), Collections.<String>emptySet()));
				
			} catch (DependencyCollectionException e) {}            
	        
		}
		isInit = true;
	}

	/**
	 * Starts the OSGi Equinox environment.
	 * 
	 * @return
	 * @throws Exception
	 */
	protected BundleContext startEquinox() throws Exception {
		BundleContext bc = null;

		Map<String, String> frameworkProperties = new HashMap<String, String>();
		frameworkProperties.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, getSharedPackages());
		frameworkProperties.put(EclipseStarter.PROP_CLEAN, "true");
		frameworkProperties.put(EclipseStarter.PROP_CONSOLE, "true");
		frameworkProperties.put(EclipseStarter.PROP_NOSHUTDOWN, "true");
		frameworkProperties.put(EclipseStarter.PROP_INSTALL_AREA, getConfiguration().getTempPath().getCanonicalPath());

		EclipseStarter.setInitialProperties(frameworkProperties);
		bc = EclipseStarter.startup(new String[] {}, null);

		return bc;
	}

	/**
	 * Stops the OSGi environment.
	 * 
	 * @throws Exception
	 */
	public void stopOSGi() throws Exception {
		EclipseStarter.shutdown();
	}

	/** {@link PepperStarterConfiguration} of this object. **/
	private PepperStarterConfiguration properties = null;

	/** {@inheritDoc Pepper#getConfiguration()} **/
	@Override
	public PepperConfiguration getConfiguration() {
		return properties;
	}

	/**
	 * @return configuration as {@link PepperStarterConfiguration}
	 **/
	public PepperStarterConfiguration getPepperStarterConfiguration() {
		return properties;
	}

	@Override
	public void setConfiguration(PepperConfiguration configuration) {
		if (configuration instanceof PepperStarterConfiguration) {
			this.properties = (PepperStarterConfiguration) configuration;
		} else {
			throw new PepperConfigurationException("Cannot set the given configuration, since it is not of type '" + PepperStarterConfiguration.class.getSimpleName() + "'.");
		}
	}

	// ========================================== start: initializing OSGi
	/** a singleton instance of {@link Pepper} **/
	private Pepper pepper = null;

	/**
	 * Returns an instance of {@link Pepper}, which is running inside OSGi. This
	 * class will be resolved via the {@link BundleContext}. If it was resolved
	 * once, a singleton instance of this object is returned.
	 * 
	 * @return {@link Pepper} from inside the OSGi environment.
	 */
	protected Pepper getPepper() {
		if (pepper == null) {
			try {
				ServiceReference serviceReference = getBundleContext().getServiceReference(Pepper.class.getName());
				Pepper pepperOSGi = null;
				if (serviceReference != null) {
					try {
						pepperOSGi = (Pepper) getBundleContext().getService(serviceReference);
					} catch (ClassCastException e) {
						pepperOSGi = (Pepper) getBundleContext().getService(serviceReference);
					}
				} else {
					throw new PepperException("The pepper-framework was not found in OSGi environment for '" + Pepper.class.getName() + "'.");
				}
				pepper = pepperOSGi;
				pepper.setConfiguration(getConfiguration());
			} catch (IllegalStateException e) {

			}
		}
		return (pepper);
	}

	/** The context of all pepper bundles. */
	private BundleContext bundleContext = null;

	/**
	 * Returns the {@link BundleContext} object used for this
	 * {@link PepperConnector}
	 * 
	 * @return
	 */
	public BundleContext getBundleContext() {
		return bundleContext;
	}

	/**
	 * Sets the {@link BundleContext} object used for this connector
	 * 
	 * @param bundleContext
	 *            the object to be set
	 */
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * Returns a String, containing a formatted list of packages to be shared
	 * between current classloader and OSGi classloaders. The list is formatted
	 * as it could be taken of the property
	 * {@link Constants#FRAMEWORK_SYSTEMPACKAGES_EXTRA}.
	 */
	protected String getSharedPackages() {
		StringBuilder retVal = new StringBuilder();

		String sharedPackages = getPepperStarterConfiguration().getSharedPackages();
		if ((sharedPackages != null) && (!sharedPackages.isEmpty())) {
			retVal.append(sharedPackages);
		} else {

			// TODO is it possible, to retrieve this information automatically?
			String pepperVersion = PEPPER_VERSION;

			// pepper.common package
			retVal.append(Pepper.class.getPackage().getName());
			retVal.append(";version=\"" + pepperVersion + "\"");

			retVal.append(", ");

			// pepper.exceptions package
			retVal.append(PepperException.class.getPackage().getName());
			retVal.append(";version=\"" + pepperVersion + "\"");

			retVal.append(", ");

			// emf-util
			retVal.append(org.eclipse.emf.common.util.URI.class.getPackage().getName());
		}
		return (retVal.toString());
	}

	/** name of system property to determine the locations of OSGi bundles **/
	public static final String PROP_OSGI_BUNDLES = "osgi.bundles";

	/**
	 * Tries to install all jar-files, of the given pluginPath. <br/>
	 * Each installed jar will be added to system property
	 * {@value #PROP_OSGI_BUNDLES} as reference:file:JAR_FILE.
	 * 
	 * @param pluginPath
	 *            path where the bundles are
	 * @param bundleAction
	 *            a flag, which shows if bundle has to be started or just
	 *            installed
   * @param dropinPaths A list of additionally paths to load bundles from
   * 
	 * @throws BundleException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	protected Collection<Bundle> installBundles(URI pluginPath, List<URI> dropinPaths) throws BundleException, URISyntaxException, IOException {
    
		ArrayList<Bundle> bundles = new ArrayList<>();
		
		List<URI> loadLocations = new LinkedList<>();
		if(dropinPaths != null)
		{
			loadLocations.addAll(dropinPaths);
		}
		loadLocations.add(pluginPath);
		
		StringBuilder osgiBundlesProp = null;
	
		for (URI dropinLocation : loadLocations) {
			File[] fileLocations =
				new File(dropinLocation.getPath())
					.listFiles((FilenameFilter) new SuffixFileFilter(".jar"));
			for (File bundleJar : fileLocations) {
				// check if file is file-object
				if (bundleJar.isFile() && bundleJar.canRead()) {
					// check if file is file jar
					URI bundleURI = bundleJar.toURI();
					Bundle bundle = install(bundleURI);
					if (bundle != null) {
						bundles.add(bundle);
						logger.debug("\t\tinstalling bundle: " + bundle.
							getSymbolicName() + "-" + bundle.getVersion());

						// set system property for bundle pathes
						if (osgiBundlesProp == null) {
							osgiBundlesProp = new StringBuilder();
						}
						osgiBundlesProp.append("reference:");
						osgiBundlesProp.append(bundleURI);
						osgiBundlesProp.append(",");
					}
				}
			}
		}
		if ((System.getProperty(PROP_OSGI_BUNDLES) == null) || (System.getProperty(PROP_OSGI_BUNDLES).isEmpty())) {
			System.setProperty(PROP_OSGI_BUNDLES, osgiBundlesProp.toString());
		}
		return (bundles);
	}

	/** Stores all bundle ids and the corresponding bundles. */
	private Map<Long, Bundle> bundleIdMap = new Hashtable<Long, Bundle>();
	/** Stores all locations of bundles and the corresponding bundle ids **/
	private Map<URI, Long> locationBundleIdMap = new Hashtable<URI, Long>();

	/**
	 * Returns the bundle id to an already installed bundle from the passed
	 * location.
	 **/
	public Long getBundleId(URI location) {
		if (location != null) {
			return (locationBundleIdMap.get(location));
		}
		return (null);
	}

	/**
	 * Installs the given bundle and copies it to the plugin path, but does not
	 * start it. <br>
	 * If the the URI is of scheme http or https, the file will be downloaded. <br/>
	 * If the URI points to a zip file, it will be extracted and copied.
	 * 
	 * @param bundleURI
	 * @return
	 * @throws BundleException
	 * @throws IOException
	 */
	public Bundle installAndCopy(URI bundleURI) throws BundleException, IOException {
		Bundle retVal = null;
		if (bundleURI != null) {
			String pluginPath = getPepperStarterConfiguration().getPlugInPath();
			if (pluginPath != null) {
				// download file, if file is a web resource
				if (("http".equalsIgnoreCase(bundleURI.getScheme())) || ("https".equalsIgnoreCase(bundleURI.getScheme()))) {
					String tempPath = getPepperStarterConfiguration().getTempPath().getCanonicalPath();
					URL bundleUrl = bundleURI.toURL();
					if (!tempPath.endsWith("/")) {
						tempPath = tempPath + "/";
					}
					String baseName = FilenameUtils.getBaseName(bundleUrl.toString());
					String extension = FilenameUtils.getExtension(bundleUrl.toString());
					File bundleFile = new File(tempPath + baseName + "." + extension);

					org.apache.commons.io.FileUtils.copyURLToFile(bundleURI.toURL(), bundleFile);
					bundleURI = URI.create(bundleFile.getAbsolutePath());
				}
				if (bundleURI.getPath().endsWith("zip")) {
					ZipFile zipFile = null;
					try {
						zipFile = new ZipFile(bundleURI.getPath());
						Enumeration<? extends ZipEntry> entries = zipFile.entries();
						while (entries.hasMoreElements()) {
							ZipEntry entry = entries.nextElement();
							File entryDestination = new File(pluginPath, entry.getName());
							entryDestination.getParentFile().mkdirs();
							if (entry.isDirectory()) {
								entryDestination.mkdirs();
							} else {
								InputStream in = zipFile.getInputStream(entry);
								OutputStream out = new FileOutputStream(entryDestination);
								IOUtils.copy(in, out);
								IOUtils.closeQuietly(in);
								IOUtils.closeQuietly(out);
								if (entryDestination.getName().endsWith(".jar")) {
									retVal = install(entryDestination.toURI());
								}
							}
						}
					} finally {
						zipFile.close();
					}
				} else if (bundleURI.getPath().endsWith("jar")) {
					File bundleFile = new File(bundleURI.getPath());
					File jarFile = new File(pluginPath, bundleFile.getName());
					FileUtils.copyFile(bundleFile, jarFile);
					retVal = install(jarFile.toURI());
				}
			}
		}

		return (retVal);
	}

	/**
	 * Installs the given bundle, but does not start it.
	 * 
	 * @param bundleURI
	 * @return
	 * @throws BundleException
	 */
	public Bundle install(URI bundleURI) throws BundleException {
		Bundle bundle = null;
		try {
			bundle = getBundleContext().installBundle(bundleURI.toString());
		} catch (BundleException e) {
			return (null);
		}
		bundleIdMap.put(bundle.getBundleId(), bundle);

		String osgiBundleProp = System.getProperty(PROP_OSGI_BUNDLES);

		if (osgiBundleProp == null) {
			osgiBundleProp = "";
		} else {
			osgiBundleProp = osgiBundleProp + ",";
		}

		osgiBundleProp = osgiBundleProp + "reference:" + bundleURI;

		System.setProperty(PROP_OSGI_BUNDLES, osgiBundleProp);
		locationBundleIdMap.put(bundleURI, bundle.getBundleId());
		return (bundle);
	}

	/**
	 * Uninstalls a bundle from OSGi context.
	 * 
	 * @throws BundleException
	 */
	public void uninstall(Long bundleId) throws BundleException {
		Bundle bundle = getBundleContext().getBundle(bundleId);
		bundle.uninstall();
	}

	/**
	 * Uninstalls a bundle from OSGi context.
	 * 
	 * @throws BundleException
	 */
	public void uninstall(URI location) throws BundleException {
		if (location != null) {
			Long bundleId = locationBundleIdMap.get(location);
			if (bundleId != null) {
				Bundle bundle = getBundleContext().getBundle(bundleId);
				bundle.uninstall();
			}
		}
	}

	/**
	 * Removes the passed bundle from the OSGi content and removes its jar file
	 * and folder if exist.
	 * 
	 * @throws BundleException
	 * @throws IOException
	 */
	public boolean remove(String bundleName) throws BundleException, IOException {
		boolean retVal = false;
		if ((bundleName != null) && (!bundleName.isEmpty())) {
			for (Bundle bundle : getBundleContext().getBundles()) {
				if (bundle.getSymbolicName().equalsIgnoreCase(bundleName)) {
					for (Map.Entry<URI, Long> entry : locationBundleIdMap.entrySet()) {
						if (entry.getValue().equals(bundle.getBundleId())) {
							// stop bundle
							bundle.stop();
							// uninstall bundle
							bundle.uninstall();
							// remove bundle source
							File fileToRemove = new File(entry.getKey().getPath());
							retVal = fileToRemove.delete();
							// check for folders to be removed
							for (File file : new File(getPepperStarterConfiguration().getPlugInPath()).listFiles()) {
								if (file.getName().startsWith(fileToRemove.getName().replace(".jar", ""))) {
									if (file.isDirectory()) {
										FileUtils.deleteDirectory(file);
									}
								}
							}
							break;
						}
					}
					break;
				}
			}
		}
		return (retVal);
	}

	/**
	 * Starts the passed bundle
	 * 
	 * @param bundle
	 */
	public void start(Long bundleId) {
		Bundle bundle = bundleIdMap.get(bundleId);
		logger.debug("\t\tstarting bundle: " + bundle.getSymbolicName() + "-" + bundle.getVersion());
		if (bundle.getState() != Bundle.ACTIVE) {
			try {
				bundle.start();
			} catch (BundleException e) {
				logger.warn("The bundle '" + bundle.getSymbolicName() + "-" + bundle.getVersion() + "' wasn't started correctly. This could cause other problems. For more details turn on log mode to debug and see log file. ", e);
			}
		}
		if (bundle.getState() != Bundle.ACTIVE) {
			logger.error("The bundle '" + bundle.getSymbolicName() + "-" + bundle.getVersion() + "' wasn't started correctly.");
		}
	}

	/**
	 * Starts all bundle being contained in the given list of bundles.
	 * 
	 * @param bundles
	 *            a list of bundles to start
	 * @throws BundleException
	 */
	protected void startBundles(Collection<Bundle> bundles) throws BundleException {
		if (bundles != null) {
			Bundle pepperBundle = null;
			for (Bundle bundle : bundles) {
				// TODO this is a workaround, to fix that module resolver is
				// loaded as last bundle, otherwise, some modules will be
				// ignored
				if ("de.hu_berlin.german.korpling.saltnpepper.pepper-framework".equalsIgnoreCase(bundle.getSymbolicName())) {
					pepperBundle = bundle;
				} else {
					start(bundle.getBundleId());
				}
			}
			try {
				if (pepperBundle != null) {
					pepperBundle.start();
				}
			} catch (BundleException e) {
				throw new PepperOSGiFrameworkPluginException("The Pepper framework bundle could not have been started. Unfortunatly Pepper cannot be started without that OSGi bundle. ", e);
			}
		}
	}

	// ========================================== end: initializing OSGi

	/**
	 * {@inheritDoc Pepper#createJob()}
	 */
	@Override
	public String createJob() {
		if (getPepper() == null)
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");

		return (getPepper().createJob());
	}

	/**
	 * {@inheritDoc Pepper#getJob(String)}
	 */
	@Override
	public PepperJob getJob(String id) throws JobNotFoundException {
		if (getPepper() == null)
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");

		return (getPepper().getJob(id));
	}

	/**
	 * {@inheritDoc Pepper#removeJob(String)}
	 */
	@Override
	public boolean removeJob(String id) throws JobNotFoundException {
		if (getPepper() == null)
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");

		return (getPepper().removeJob(id));
	}

	/**
	 * {@inheritDoc Pepper#getRegisteredModules()}
	 */
	@Override
	public Collection<PepperModuleDesc> getRegisteredModules() {
		if (getPepper() == null) {
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");
		}
		return (getPepper().getRegisteredModules());
	}

	@Override
	public String getRegisteredModulesAsString() {
		if (getPepper() == null)
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");

		return (getPepper().getRegisteredModulesAsString());
	}

	@Override
	public Collection<String> selfTest() {
		if (getPepper() == null)
			throw new PepperException("We are sorry, but no Pepper has been resolved in OSGi environment. ");
		return (getPepper().selfTest());
	}
	
	
	/**
	 * This method checks the pepperModules in the modules.xml for updates
	 * and triggers the installation process if a newer version is available
	 */
	public boolean update(String groupId, String artifactId, String repositoryUrl, boolean isSnapshot, boolean ignoreFrameworkVersion){
				
		RepositorySystem system = Booter.newRepositorySystem();
        RepositorySystemSession session = Booter.newRepositorySystemSession( system );
        boolean update = true; //MUST be born true
        
        /*build repository*/
        RemoteRepository.Builder repoBuilder = new RemoteRepository.Builder("korpling", "default", repositoryUrl);
        RemoteRepository repo = repoBuilder.build();
        
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
	        
            /* checking, if a correlating bundle already exist, which would mean, the module is already installed */
            Bundle installedBundle = null;
            List<Bundle> bundles = new ArrayList<Bundle>(bundleIdMap.values());
            for (int i=0; i<bundles.size() && installedBundle==null; i++){
            	if ((groupId+"."+artifactId).equals(bundles.get(i).getSymbolicName())){
            		installedBundle = bundles.get(i);
            	}
            }            
            
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
		    					logger.info("Highest version in repository could not be found. Checking the next lower version ...");			    				
		    			}
	    			}
    		}    	
	    	update&= file!=null;//in case of only snapshots in the maven repository vs. isSnapshot=false	    	
	    	/* if an update is possible/necessary, perform dependency collection and installation */
	    	if (update){
	    		
	    		/* utils for file-collection */
    			List<URI> fileURIs = new ArrayList<URI>(); 		
	    		fileURIs.add(artifact.getFile().toURI());  		
	    		
	    		/* utils for dependency collection */
	    		CollectRequest collectRequest = new CollectRequest();
	            collectRequest.setRoot( new Dependency( artifact, "" ) );
	            collectRequest.addRepository(repo);	            
	            collectRequest.setRootArtifact(artifact);
	            CollectResult collectResult = system.collectDependencies( session, collectRequest );
				
	            Bundle bundle = null;
	            List<Dependency> allDependencies = getAllDependencies(collectResult.getRoot(), forbiddenFruits);
	            Dependency dependency = null;	            
	            //in the following we ignore the first dependency, because it is the module itself         	            
	            for (int i=1; i<allDependencies.size(); i++){
	            	dependency = allDependencies.get(i);
	            	if (ARTIFACT_ID_PEPPER_FRAMEWORK.equals(dependency.getArtifact().getArtifactId())){
	            		if (!ignoreFrameworkVersion && !dependency.getArtifact().getVersion().replace("–SNAPSHOT", "").equals(frameworkVersion.replace("-SNAPSHOT", ""))){
	            			logger.info((new StringBuilder()).append("No update was performed because of a version incompatability according to pepper-framework: ").append(artifactId).append(" needs ")
	            					.append(dependency.getArtifact().getVersion()).append(", but ").append(frameworkVersion).append(" is installed!").toString());
	          
	            		}	            		
	            	}
	            	else if(!dependency.getArtifact().getArtifactId().contains("salt-")){	            		
	            		artifactRequest.setRepositories(Booter.newRepositories(system, session));
	            		artifactRequest.addRepository(repo);
	            		artifactRequest.setArtifact(dependency.getArtifact());
	            		try{
	            			artifactResult = system.resolveArtifact(session, artifactRequest);          			
		            		fileURIs.add(artifactResult.getArtifact().getFile().toURI());		            		
	            		}catch (ArtifactResolutionException e){
	            			logger.warn("Artifact "+dependency.getArtifact().getArtifactId()+" could not be resolved. Dependency will not be installed.");
	            		}
	            	}
	            }
	            logger.info("\n");
	            for (int i=fileURIs.size()-1; i>=0; i--){	            	
	            	try {
	            		logger.info("\tinstalling: "+fileURIs.get(i));
	            		bundle = this.installAndCopy(fileURIs.get(i));
	            		if (bundle!=null){
	            			bundleIdMap.put(bundle.getBundleId(), bundle);
	            			locationBundleIdMap.put(URI.create(bundle.getLocation()), bundle.getBundleId());
	            			bundle.start();
	            		}
	            	} catch (IOException | BundleException e) { logger.debug("File could not be installed: "+fileURIs.get(i)+"; "+e.getClass());}	            	
		    	}
	            /*
	    		 * root is not supposed to be stored as forbidden dependency. This makes the removal of a module much less complicated.
	    		 * If a pepper module would be put onto the blacklist and the bundle would be removed, we always had to make sure, it
	    		 * its entry on the blacklist would be removed. Assuming the entry would remain on the blacklist, the module could be
	    		 * reinstalled, BUT(!) the dependencies would all be dropped and never being installed again, since the modules node
	    		 * dominates all other nodes in the dependency tree.
	    		 */
	    		allDependencies.remove(0);
        		write2Blacklist(allDependencies);
	            logger.info("\n");
			}	    	
    	} catch (VersionRangeResolutionException | InvalidVersionSpecificationException | DependencyCollectionException e) {
			update = false;			
		}       
        
        return update;
	}
	
	/**
	 * This method returns all dependencies as list.
	 * Elementary dependencies and their daughters are skipped. 
	 */
	private List<Dependency> getAllDependencies(DependencyNode startNode, Set<String> installedDeps){		
		List<Dependency> retVal = new ArrayList<Dependency>();
		retVal.add(startNode.getDependency());
		for (DependencyNode node : startNode.getChildren()){			
			if (!dependencyAlreadyInstalled(node.getArtifact().toString())) {retVal.addAll(getAllDependencies(node, installedDeps));}
			if (ARTIFACT_ID_PEPPER_FRAMEWORK.equals(node.getArtifact().getArtifactId())){
				retVal.add(node.getDependency());
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
		String[] coords = artifactString.split(":");
		String[] testCoords = null;
		for (String dependencyString : forbiddenFruits){
			testCoords = dependencyString.split(":");
			if (coords[1].equals(testCoords[1]) &&
				coords[0].equals(testCoords[0]) &&
				coords[3].equals(testCoords[3])){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns the version of pepper-framework
	 * @return
	 */
	private String getFrameworkVersion(){
		List<Bundle> bList = new ArrayList<Bundle>();
		bList.addAll(bundleIdMap.values());
		for (int i=0; i<bList.size(); i++){
			if (bList.get(i).getSymbolicName()!=null && bList.get(i).getSymbolicName().contains(ARTIFACT_ID_PEPPER_FRAMEWORK)){
				return bList.get(i).getVersion().toString().replace(".SNAPSHOT", "-SNAPSHOT");
			}
		}
		return null;//TODO
	}
	
	/**
	 * writes the freshly installed dependencies to the blacklist file.
	 */
	private void write2Blacklist(List<Dependency> dependencies){
		File blacklistFile = new File(blacklistPath);		
		for (Dependency dependency : dependencies){
			forbiddenFruits.add(dependency.getArtifact().toString());					
		}				
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
		} catch (IOException e) {}
		
	}
	
	public String getBlacklist(){
		String lineSeparator = System.getProperty("line.separator");
		String indent = "\t";
		StringBuilder retVal = (new StringBuilder()).append(lineSeparator);
		retVal.append(indent).append("installed dependencies:").append(lineSeparator).append(lineSeparator);
		for (String s : forbiddenFruits){
			retVal.append(indent).append(s).append(System.getProperty("line.separator"));
		}
		return retVal.toString();
	}
}

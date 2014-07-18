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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.PepperStarterConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.exceptions.PepperPropertyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.PepperConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperOSGiRunner;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.JobNotFoundException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;

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

	/**
	 * Starts the OSGi environment and installs and starts all bundles located
	 * in the plugin directory. <br/>
	 * Sets property {@link PepperOSGiRunner#PROP_TEST_DISABLED} to true.
	 */
	@Override
	public void init() {
		if (getProperties().getPlugInPath() == null) {
			throw new PepperPropertyException("Cannot start Pepper, because no plugin path is given for Pepper modules.");
		}
		try {
			// disable PepperOSGiRunner
			System.setProperty(PepperOSGiRunner.PROP_TEST_DISABLED, Boolean.TRUE.toString());

			setBundleContext(this.startEquinox());

			logger.debug("plugin path:\t\t" + getProperties().getPlugInPath());

			logger.debug("installing OSGI-bundles...");
			logger.debug("-------------------- installing bundles --------------------");
			Collection<Bundle> bundles = null;

			// installing module-bundles
			logger.debug("\tinstalling OSGI-bundles:");
			bundles = this.installBundles(new File(getProperties().getPlugInPath()).toURI());
			logger.debug("----------------------------------------------------------");
			logger.debug("installing OSGI-bundles...FINISHED");
			logger.debug("starting OSGI-bundles...");
			logger.debug("-------------------- starting bundles --------------------");
			if (	(bundles== null)||
					(bundles.isEmpty())){
				bundles= new ArrayList<Bundle>();
				bundleIdMap= new Hashtable<Long, Bundle>();
				for (Bundle bundle: getBundleContext().getBundles()){
					bundles.add(bundle);
					bundleIdMap.put(bundle.getBundleId(), bundle);
				}
			}
			
			this.startBundles(bundles);
			logger.debug("----------------------------------------------------------");
			logger.debug("starting OSGI-bundles...FINISHED");
		} catch (Exception e) {
			throw new PepperException("An exception occured setting up the OSGi environment. ", e);
		}
	}

	/**
	 * Starts the OSGi Equinox environment.
	 * 
	 * @return
	 * @throws Exception
	 */
	protected BundleContext startEquinox() throws Exception {
		BundleContext bc = null;

		Properties frameworkProperties = new Properties();

		frameworkProperties.setProperty(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, getSharedPackages());

		frameworkProperties.setProperty(EclipseStarter.PROP_CLEAN, "true");
		frameworkProperties.setProperty(EclipseStarter.PROP_CONSOLE, "true");
		frameworkProperties.setProperty(EclipseStarter.PROP_NOSHUTDOWN, "true");
		frameworkProperties.setProperty(EclipseStarter.PROP_INSTALL_AREA, "./_TMP/");

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.pepperStarter.PepperConnector
	 * #
	 * setProperties(de.hu_berlin.german.korpling.saltnpepper.pepper.pepperStarter
	 * .PepperProperties)
	 */
	@Override
	public void setProperties(PepperStarterConfiguration props) {
		if (properties == null) {
			this.properties = props;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hu_berlin.german.korpling.saltnpepper.pepper.pepperStarter.PepperConnector
	 * #getProperties()
	 */
	@Override
	public PepperStarterConfiguration getProperties() {
		return properties;
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

		String sharedPackages = getProperties().getSharedPackages();
		if ((sharedPackages != null) && (!sharedPackages.isEmpty())) {
			retVal.append(sharedPackages);
		} else {

			// TODO is it possible, to retrieve this information automatically?
			String pepperVersion = "2.0.0";

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
	 *            path ere the bundles are
	 * @param bundleAction
	 *            a flag, which shows if bundle has to be started or just
	 *            installed
	 * @throws BundleException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	protected Collection<Bundle> installBundles(URI pluginPath) throws BundleException, URISyntaxException, IOException {
		Collection<Bundle> bundles = new Vector<Bundle>();
		StringBuilder osgiBundlesProp = null;
		for (File bundleJar : new File(pluginPath.getPath()).listFiles()) {
			// check if file is file-object
			if (bundleJar.isFile()) {
				// check if file is file jar
				if (bundleJar.getName().endsWith(".jar")) {
					URI bundleURI = bundleJar.toURI();
					Bundle bundle = install(bundleURI);
					if (bundle != null) {
						bundles.add(bundle);
						logger.debug("\t\tinstalling bundle: " + bundle.getSymbolicName() + "-" + bundle.getVersion());

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
			String pluginPath = getProperties().getPlugInPath();
			if (pluginPath != null) {
				// download file, if file is a web resource
				if (("http".equalsIgnoreCase(bundleURI.getScheme())) || ("https".equalsIgnoreCase(bundleURI.getScheme()))) {
					String tmpPath = System.getProperty("java.io.tmpdir");
					URL bundleUrl = bundleURI.toURL();
					if (!tmpPath.endsWith("/")) {
						tmpPath = tmpPath + "/";
					}
					String baseName = FilenameUtils.getBaseName(bundleUrl.toString());
					String extension = FilenameUtils.getExtension(bundleUrl.toString());
					File bundleFile = new File(tmpPath + baseName + "." + extension);

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
		boolean retVal = true;
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
							for (File file : new File(getProperties().getPlugInPath()).listFiles()) {
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
				logger.warn("The bundle '" + bundle.getSymbolicName() + "-" + bundle.getVersion() + "' wasn't started correctly. This could cause other problems. For more details turn on log mode to debug and see log. ");
				logger.debug("The reason was: ", e);
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
			if (pepperBundle!= null){
				pepperBundle.start();
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
}

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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperParamsException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperModuleResolver;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.util.PepperFWProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ExporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ImporterParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.ModuleParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperJobParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParamsPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Converter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl#getPepperModuleResolver <em>Pepper Module Resolver</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl#getPepperParams <em>Pepper Params</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl#getPepperJobs <em>Pepper Jobs</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl#getPepperJ2CMonitors <em>Pepper J2C Monitors</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl#isParallelized <em>Parallelized</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl#getPepperParamsURI <em>Pepper Params URI</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
@Component(name="PepperConverterImpl", immediate=true)
@Service
public class PepperConverterImpl extends EObjectImpl implements PepperConverter 
{
//	private Logger logger= Logger.getLogger(PepperConverterImpl.class);
/**
	 * The cached value of the '{@link #getPepperModuleResolver() <em>Pepper Module Resolver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperModuleResolver()
	 * @generated
	 * @ordered
	 */
	@Reference(bind="setPepperModuleResolver", unbind="unsetPepperModuleResolver", cardinality=ReferenceCardinality.MANDATORY_UNARY)
	protected PepperModuleResolver pepperModuleResolver;
	/**
	 * This is true if the Pepper Module Resolver containment reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean pepperModuleResolverESet;
	/**
	 * The default value of the '{@link #getPepperParams() <em>Pepper Params</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperParams()
	 * @generated
	 * @ordered
	 */
	protected static final PepperParams PEPPER_PARAMS_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPepperParams() <em>Pepper Params</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperParams()
	 * @generated
	 * @ordered
	 */
	protected PepperParams pepperParams = PEPPER_PARAMS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPepperJobs() <em>Pepper Jobs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperJobs()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperJob> pepperJobs;

	/**
	 * The cached value of the '{@link #getPepperJ2CMonitors() <em>Pepper J2C Monitors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperJ2CMonitors()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperFinishableMonitor> pepperJ2CMonitors;

	/**
	 * The default value of the '{@link #isParallelized() <em>Parallelized</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isParallelized()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PARALLELIZED_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isParallelized() <em>Parallelized</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isParallelized()
	 * @generated
	 * @ordered
	 */
	protected boolean parallelized = PARALLELIZED_EDEFAULT;

	/**
	 * The default value of the '{@link #getProperties() <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected static final Properties PROPERTIES_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected Properties properties = PROPERTIES_EDEFAULT;

	/**
	 * The default value of the '{@link #getPepperParamsURI() <em>Pepper Params URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperParamsURI()
	 * @generated
	 * @ordered
	 */
	protected static final URI PEPPER_PARAMS_URI_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPepperParamsURI() <em>Pepper Params URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperParamsURI()
	 * @generated
	 * @ordered
	 */
	protected URI pepperParamsURI = PEPPER_PARAMS_URI_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public PepperConverterImpl() 
	{
		super();
//		this.pepperModuleResolver= PepperFWFactory.eINSTANCE.createPepperModuleResolver();
	}

	
// ========================================== start: LogService	
	/**
	 * Static logger, which is initialized, when the first {@link PepperConverterImpl} is created. The same object as set 
	 * to that object, is set to the static {@link LogService}. One can get the static {@link LogService} via
	 */
	private static LogService logger= null;
	
	/**
	 * {@inheritDoc PepperConverter#logService}
	 */
	public static LogService getLogger()
	{
		return(logger);
	}
	
	@Reference(bind="setLogService", unbind="unsetLogService", cardinality=ReferenceCardinality.OPTIONAL_UNARY)
	protected LogService logService;

	public void setLogService(LogService logService) 
	{
		if (logger== null)
			logger=logService;
		this.logService = logService;
	}
	
	public LogService getLogService() 
	{
		return(this.logService);
	}
	
	public void unsetLogService(LogService logService) {
		this.logService= null;
	}

// ========================================== end: LogService
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperFWPackage.Literals.PEPPER_CONVERTER;
	}
	
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperModuleResolver getPepperModuleResolver() {
		return pepperModuleResolver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPepperModuleResolver(PepperModuleResolver newPepperModuleResolver, NotificationChain msgs) {
		PepperModuleResolver oldPepperModuleResolver = pepperModuleResolver;
		pepperModuleResolver = newPepperModuleResolver;
		boolean oldPepperModuleResolverESet = pepperModuleResolverESet;
		pepperModuleResolverESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER, oldPepperModuleResolver, newPepperModuleResolver, !oldPepperModuleResolverESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperModuleResolver(PepperModuleResolver newPepperModuleResolver) {
		if (newPepperModuleResolver != pepperModuleResolver) {
			NotificationChain msgs = null;
			if (pepperModuleResolver != null)
				msgs = ((InternalEObject)pepperModuleResolver).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER, null, msgs);
			if (newPepperModuleResolver != null)
				msgs = ((InternalEObject)newPepperModuleResolver).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER, null, msgs);
			msgs = basicSetPepperModuleResolver(newPepperModuleResolver, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPepperModuleResolverESet = pepperModuleResolverESet;
			pepperModuleResolverESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER, newPepperModuleResolver, newPepperModuleResolver, !oldPepperModuleResolverESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicUnsetPepperModuleResolver(NotificationChain msgs) {
		PepperModuleResolver oldPepperModuleResolver = pepperModuleResolver;
		pepperModuleResolver = null;
		boolean oldPepperModuleResolverESet = pepperModuleResolverESet;
		pepperModuleResolverESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER, oldPepperModuleResolver, null, oldPepperModuleResolverESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	public void unsetPepperModuleResolver(PepperModuleResolver moduleResolver) 
	{
		this.unsetPepperModuleResolver();
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPepperModuleResolver() {
		if (pepperModuleResolver != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)pepperModuleResolver).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER, null, msgs);
			msgs = basicUnsetPepperModuleResolver(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPepperModuleResolverESet = pepperModuleResolverESet;
			pepperModuleResolverESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER, null, null, oldPepperModuleResolverESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPepperModuleResolver() {
		return pepperModuleResolverESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperParams getPepperParams() {
		return pepperParams;
	}

	/**
	 * Checks the given parameters and throws an exception, if there is an incorrect entry.
	 * Also completes pathes if necessary and possible. 
	 * @param pepperParams
	 */
	protected void validatePepperParams(PepperParams pepperParams)
	{
		//checking if PepperParams has at least one PepperJob
		if (	(pepperParams.getPepperJobParams()== null) || 
				(pepperParams.getPepperJobParams().size()< 1))
			throw new PepperParamsException("Cannot set parameter to PepperConverter, because no pepperParams are given.");
		for (PepperJobParams jobParams: pepperParams.getPepperJobParams())
		{
			//cheking if every job isn't null
			if (jobParams== null)
				throw new PepperParamsException("Cannot set parameter to PepperConverter, because one of given jobs is null.");
			//checking if job has an id
			if (jobParams.getId()== null)
				throw new PepperParamsException("Cannot set parameter to PepperConverter, because the id of one of the jobs is null.");
			{//checking if at least one importer is given
				if (	(jobParams.getImporterParams()== null) ||
						(jobParams.getImporterParams().size() < 1))
					throw new PepperParamsException("Cannot set parameter to PepperConverter, because no importer is given.");
				//checking importers
				for (ImporterParams importerParams: jobParams.getImporterParams())
				{
					if (importerParams== null)
						throw new PepperParamsException("Cannot set parameter to PepperConverter, because one of the importers is null.");
					if (	(	(importerParams.getModuleName()== null) ||
								(importerParams.getModuleName().isEmpty())) &&
							(	(	(importerParams.getFormatName()== null) ||
									(importerParams.getFormatName().isEmpty()))||
								(	(importerParams.getFormatVersion()== null) ||
									(importerParams.getFormatName().isEmpty())))) 
						throw new PepperParamsException("Cannot set parameter to PepperConverter, because one of the importers does neither have a name nor a format description (format name and format version).");
				}
			}
			{//checking if at least one exporter is given
				if (	(jobParams.getExporterParams()== null) ||
						(jobParams.getExporterParams().size() < 1))
					throw new PepperParamsException("Cannot set parameter to PepperConverter, because no exporter is given.");
				//checking importers
				for (ExporterParams exporterParams: jobParams.getExporterParams())
				{
					if (exporterParams== null)
						throw new PepperParamsException("Cannot set parameter to PepperConverter, because one of the exporters is null.");
					if (	(	(exporterParams.getModuleName()== null) ||
								(exporterParams.getModuleName().isEmpty())) &&
							(	(	(exporterParams.getFormatName()== null) ||
									(exporterParams.getFormatName().isEmpty()))||
								(	(exporterParams.getFormatVersion()== null) ||
									(exporterParams.getFormatName().isEmpty())))) 
						throw new PepperParamsException("Cannot set parameter to PepperConverter, because one of the exporters does neither have a name nor a format description (format name and format version).");
				}
			}
			{//checking the module integrity
				for (ModuleParams moduleParams: jobParams.getModuleParams())
				{
					if (moduleParams== null)
						throw new PepperParamsException("Cannot set parameter to PepperConverter, because one of the modules is null.");
					if (	(moduleParams.getModuleName()== null) &&
							(moduleParams.getModuleName().isEmpty()))
						throw new PepperParamsException("Cannot set parameter to PepperConverter, because one of the modules has no name.");
				}
			}
			{//fullfill all pathes if necessary and possible
				if (this.getPepperParamsURI()!= null)
				{// only if the path of pepper-workflow description is set	
//					File pepperWorkflowDirectory= new File(this.getPepperParamsURI().toFileString());
//					pepperWorkflowDirectory= pepperWorkflowDirectory.getParentFile();
					String errorPart="";
					URI errorURI= null;
					try {
						for (ImporterParams importerParams: jobParams.getImporterParams())
						{//check all uri parameters for importers
							errorPart= "source path for importer";
							errorURI= importerParams.getSourcePath();
							importerParams.setSourcePath(this.checkAndResolveURI(this.getPepperParamsURI(), importerParams.getSourcePath()));
//							importerParams.setSourcePath(this.createAbsoluteURI(pepperWorkflowDirectory, importerParams.getSourcePath()));
							errorPart= "special parameter for importer";
							errorURI= importerParams.getSpecialParams();
							if (	(importerParams.getSpecialParams()!= null)&&
									(!importerParams.getSpecialParams().toFileString().isEmpty()))
							{
								importerParams.setSpecialParams(this.checkAndResolveURI(this.getPepperParamsURI(), importerParams.getSpecialParams()));
//								importerParams.setSpecialParams(this.createAbsoluteURI(pepperWorkflowDirectory, importerParams.getSpecialParams()));
							}
						}//check all uri parameters for importers
						for (ModuleParams manipulatorParams: jobParams.getModuleParams())
						{//check all uri parameters for manipulators
							if (	(!(manipulatorParams instanceof ImporterParams))&&
									(!(manipulatorParams instanceof ExporterParams)))
							{	
								errorPart= "special parameter for manipulator";
								errorURI= manipulatorParams.getSpecialParams();
								if (	(manipulatorParams.getSpecialParams()!= null)&&
										(!manipulatorParams.getSpecialParams().toFileString().isEmpty()))
								{
									
									manipulatorParams.setSpecialParams(this.checkAndResolveURI(this.getPepperParamsURI(), manipulatorParams.getSpecialParams()));
//									manipulatorParams.setSpecialParams(this.createAbsoluteURI(pepperWorkflowDirectory, manipulatorParams.getSpecialParams()));
								}
							}
						}//check all uri parameters for manipulators
						for (ExporterParams exporterParams: jobParams.getExporterParams())
						{//check all uri parameters for exporters
							
							errorPart= "source path for exporter";
							errorURI= exporterParams.getDestinationPath();
							exporterParams.setDestinationPath(this.checkAndResolveURI(this.getPepperParamsURI(), exporterParams.getDestinationPath()));
//							exporterParams.setDestinationPath(this.createAbsoluteURI(pepperWorkflowDirectory, exporterParams.getDestinationPath()));
							errorPart= "special parameter for exporter";
							errorURI= exporterParams.getSpecialParams();
							if (	(exporterParams.getSpecialParams()!= null)&&
									(!exporterParams.getSpecialParams().toFileString().isEmpty()))
							{
								exporterParams.setSpecialParams(this.checkAndResolveURI(this.getPepperParamsURI(), exporterParams.getSpecialParams()));
//								exporterParams.setSpecialParams(this.createAbsoluteURI(pepperWorkflowDirectory, exporterParams.getSpecialParams()));
							}
						}//check all uri parameters for exporters
					} catch (PepperConvertException e) {
						throw new PepperParamsException("Cannot load pepper workflow description '"+errorURI+"', because file was not found for "+errorPart+". Maybe an incorrect using of the URI syntax is the reason. Please use for instance 'file:/home/...', 'file:///home/...', 'file:/c:/...', or 'file:///c:/...' for absolute pathes and './a/b/c/' for relative pathes.",e);
					}
				}// only if the path of pepper-workflow description is set		
			}//fullfill all pathes if necessary and possible
		}
	}
	
	/**
	 * The uri schemes, which are supported by usage in pepper workflow descriptions.
	 */
	public static final String[] supportedURISchemes= {"file"};
	
	/**
	 * Checks if the scheme of the given {@link URI} objects is supported and resolves the uris if possible.
	 * <ul>
	 * 	<li>If the uri <em>resolveURI</em> is absolute, 
	 * 		<ul>
	 * 			<li>if the scheme of <em>resolveURI</em> is supported <em>resolveURI</em> will be returned.</li>
	 * 			<li>else a {@link PepperConvertException} will be thrown.</li>
	 * 		</ul> 
	 * 	<li>If the uri <em>resolveURI</em> is relative,</li>
	 * 	<ul>
	 * 		<li>if the path of the uri <em>resolveURI</em> is absolute, it will be returned.</li>
	 * 		<li>if the path of the uri <em>resolveURI</em> is relative,</li> 
	 * 		<ul>
	 * 			<li>if the <em>baseURI</em> is null a {@link PepperConvertException} will be thrown.</li>
	 * 			<li>the uri will be resolved against <em>baseURI</em>. If the scheme of <em>baseURI</em> is not supported a {@link PepperConvertException} will be thrown.</li>
	 * 		</ul>
	 * 	</ul>
	 * 	<li>If the uri <em>resolveURI</em> is null, null will be returned.</li>
	 * </ul> 
	 * @param baseURI the base uri to resolve against the other one 
	 * @param resolveURI the uri to resolve
	 * @return a supported uri with absolute path
	 */
	protected URI checkAndResolveURI(URI baseURI, URI resolveURI)
	{
		URI retVal= null;
		boolean isSupported= false;
		
		if (resolveURI!= null)
		{
			if (!resolveURI.isRelative())
			{//resolveURI is absolute
				//start: check if scheme of resolveURI is supported
					isSupported= false;
					for (String supportedScheme: supportedURISchemes)
					{
						if (supportedScheme.equals(resolveURI.scheme()))
						{
							isSupported= true;
							break;
						}
					}
					if (!isSupported)
						throw new PepperConvertException("The scheme '"+resolveURI.scheme()+"' of given resolve uri '"+resolveURI+"' is not supported.");
				//end: check if scheme of resolveURI is supported
				retVal= resolveURI;
			}//resolveURI is absolute
			else
			{
				if (resolveURI.path().startsWith("/"))
				{
					retVal= resolveURI;
				}
				else
				{
					if (baseURI== null)
						throw new PepperConvertException("Cannot resolve 'resolveURI' against 'baseURI', because given 'baseURI' is null.");
					//start: check if scheme of baseURI is supported
						isSupported= false;
						for (String supportedScheme: supportedURISchemes)
						{
							if (supportedScheme.equals(baseURI.scheme()))
							{
								isSupported= true;
								break;
							}
						}
						if (!isSupported)
							throw new PepperConvertException("The scheme '"+baseURI.scheme()+"' of given base uri '"+baseURI+"' is not supported.");
					//end: check if scheme of baseURI is supported
					retVal= resolveURI.resolve(baseURI, true);	
				}
			}
		}
		return(retVal);
	}
	
//	/**
//	 * If possible adds the given currentFile to baseDir and returns the result as a canonical path.
//	 * @param baseDir
//	 * @param currentURI
//	 * @return
//	 */
//	private URI createAbsoluteURI(File baseDir, URI currentURI) throws IOException
//	{
////		System.out.println("baseDir: "+ baseDir);
////		System.out.println("baseURI: "+ baseDir.toURI());
////		System.out.println("URI.create baseURI: "+ URI.createURI(baseDir.toURI().toString()));
////		System.out.println("currentURI: "+ currentURI);
////		System.out.println("currentURI isRelative: "+ currentURI.isRelative());
////		System.out.println("resolve: "+ currentURI.resolve(URI.createURI(baseDir.toURI().toString())));
//		
//		URI retVal= null;
//		if (currentURI== null)
//			throw new PepperException("The given file 'currentFile' is null.");
//		File path= null;
//		if (currentURI.toFileString()== null)
//		{
//			if (currentURI.toString()==null)
//				throw new PepperException("Cannot create an absolute uri for current file '"+currentURI+"'.");
//			else 
//			{
//				path= new File(currentURI.toString());
//			}
//		}
//		else 
//		{
//			path= new File(currentURI.toFileString());
//		}
//		if (!path.isAbsolute())
//		{//path is a relative one, complete it to an absolute one (workflow-description-directory + path) 
//			retVal= URI.createFileURI((new File(baseDir +"/"+ currentURI.toFileString())).getCanonicalPath());
//		}//path is a relative one, complete it to an absolute one (workflow-description-directory + path
//		else 
//		{
//			retVal= URI.createFileURI(path.getPath());
//		}
//		return(retVal);
//	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setPepperParams(PepperParams newPepperParams) 
	{
		this.validatePepperParams(newPepperParams);
		PepperParams oldPepperParams = pepperParams;
		pepperParams = newPepperParams;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_CONVERTER__PEPPER_PARAMS, oldPepperParams, pepperParams));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperJob> getPepperJobs() {
		if (pepperJobs == null) {
			pepperJobs = new EObjectContainmentEList<PepperJob>(PepperJob.class, this, PepperFWPackage.PEPPER_CONVERTER__PEPPER_JOBS);
		}
		return pepperJobs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperFinishableMonitor> getPepperJ2CMonitors() {
		if (pepperJ2CMonitors == null) {
			pepperJ2CMonitors = new EObjectContainmentEList<PepperFinishableMonitor>(PepperFinishableMonitor.class, this, PepperFWPackage.PEPPER_CONVERTER__PEPPER_J2C_MONITORS);
		}
		return pepperJ2CMonitors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isParallelized() {
		return parallelized;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParallelized(boolean newParallelized) {
		boolean oldParallelized = parallelized;
		parallelized = newParallelized;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_CONVERTER__PARALLELIZED, oldParallelized, parallelized));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(Properties newProperties) {
		Properties oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_CONVERTER__PROPERTIES, oldProperties, properties));
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI getPepperParamsURI() {
		return pepperParamsURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperParamsURI(URI newPepperParamsURI) {
		URI oldPepperParamsURI = pepperParamsURI;
		pepperParamsURI = newPepperParamsURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_CONVERTER__PEPPER_PARAMS_URI, oldPepperParamsURI, pepperParamsURI));
	}

	/**
	 * Converts the given {@link java.net.URI} object to a {@link URI} object pointing to a pepper workflow description
	 * and calls the method {@link #setPepperParamsURI(URI)}.
	 * @param pepperParamUri a uri (as {@link java.net.URI}) pointing to the file containing the pepper workflow description 
	 */
	public void setPepperParams(java.net.URI pepperParamUri) 
	{
		URI ecoreURI= null;
		try{	
			ecoreURI= URI.createFileURI(pepperParamUri.toString());
			this.setPepperParams(ecoreURI);
		}
		catch (IllegalArgumentException e) {
			throw new PepperParamsException("An exception occured when converting the given uri '"+pepperParamUri+"' into a emf uri.",e);
		}
		
	}
	
	/**
	 * Reads the the given {@link URI} object pointing to a pepper workflow description and creates a {@link PepperParams} object 
	 * filled with the content of the workflow description. After creating that {@link PepperParams} object, the method
	 * {@link #setPepperParams(PepperParams)} is called.
	 * @param pepperParamUri a uri pointing to the file containing the pepper workflow description 
	 */
	public void setPepperParams(URI pepperParamUri) 
	{
		if (pepperParamUri== null)
			throw new PepperParamsException("Cannot set the pepper workflow description, because no uri was given.");
		
		//set the path of this pepper-workflow description
		this.setPepperParamsURI(pepperParamUri);
		
		File file= new File(pepperParamUri.toFileString());
		if (!file.exists())
			throw new PepperException("Cannot read given pepperparams-file '"+file.getAbsolutePath()+"', because it does not exists.");
		//this unnecessary variable must be initialized, because of the initialization of the package (this is a workaround)
		@SuppressWarnings("unused")
		PepperParamsPackage pepperPackage= PepperParamsPackage.eINSTANCE;		

		ResourceSet resourceSet= new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("pepperparams", new XMIResourceFactoryImpl());
		Resource resource= resourceSet.createResource(pepperParamUri);
		
		if (resource== null)
			throw new PepperException("Cannot read given pepperparams-file '"+file.getAbsolutePath()+"', because no resource was found. Please make sure, that file ends with '.pepperParams'."); 
		try {
			resource.load(null);
		} catch (IOException e) 
		{
			throw new PepperException("Cannot load pepper workflow description file '"+ file.getAbsolutePath()+"'.", e);
		}
		this.setPepperParams((PepperParams) resource.getContents().get(0));
	}

	/**
	 * Creates a Pepper job out of the parameters for a job. It also creates a lot of
	 * log messages to report an overview what has happend. <br/>
	 * Attention:	This is probably not a good place for end-user-logging.
	 * @param jobParams
	 * @return
	 */
	private PepperJob createPepperJob(PepperJobParams jobParams)
	{		
		PepperJob pepperJob= PepperFWFactory.eINSTANCE.createPepperJob();
		pepperJob.setLogService(this.getLogService());
		pepperJob.setProperties(this.getProperties());
		//set pepper job id 
		pepperJob.setId(jobParams.getId());
		{//set PepperJobLogger to PepperJob
			PepperJobLogger pepperJobLogger= PepperFWFactory.eINSTANCE.createPepperJobLogger();
			pepperJobLogger.setLogService(this.logService);
			pepperJob.setPepperJobLogger(pepperJobLogger);
		}//set PepperJobLogger to PepperJob
		{//emit all importers and set them to job
			for (ImporterParams imParams: jobParams.getImporterParams())
			{
				if (this.logService!= null) 
				{	
					this.logService.log(LogService.LOG_INFO,"----------------------------------------------------");
					this.logService.log(LogService.LOG_INFO,"importer name:\t\t"+imParams.getModuleName());
					this.logService.log(LogService.LOG_INFO,"source path:\t\t"+imParams.getSourcePath());
					this.logService.log(LogService.LOG_INFO,"format name:\t\t"+imParams.getFormatName());
					this.logService.log(LogService.LOG_INFO,"format version:\t\t"+imParams.getFormatVersion());
				}
				PepperImporter pepperImporter= this.getPepperModuleResolver().getPepperImporter(imParams);
				if (pepperImporter== null)
				{
					if (this.logService!= null) 
						this.logService.log(LogService.LOG_WARNING, "Cannot correct start job "+jobParams.getId()+", because no importer was found for: "+imParams);
				}
				else 
				{
					CorpusDefinition corpusDefinition= PepperFWFactory.eINSTANCE.createCorpusDefinition();
					corpusDefinition.setCorpusPath(imParams.getSourcePath());
					if (	(imParams.getFormatName()!= null) &&
							(!imParams.getFormatName().isEmpty()))
					{
						FormatDefinition formatDefinition= PepperFWFactory.eINSTANCE.createFormatDefinition();
						formatDefinition.setFormatName(imParams.getFormatName());
						formatDefinition.setFormatVersion(imParams.getFormatVersion());
						corpusDefinition.setFormatDefinition(formatDefinition);
					}	
					pepperImporter.setCorpusDefinition(corpusDefinition);
					
					{//setting special parameter
						if (imParams.getSpecialParams()!= null)
							pepperImporter.setSpecialParams(imParams.getSpecialParams());
					}
					pepperJob.getPepperImporters().add(pepperImporter);
				}
				
				if (this.logService!= null) 
				{
					if (pepperImporter!= null)
					{
						this.logService.log(LogService.LOG_INFO,"tmp folder:\t\t"+pepperImporter.getTemproraries());
						this.logService.log(LogService.LOG_INFO,"resource folder:\t"+pepperImporter.getResources());
						this.logService.log(LogService.LOG_INFO,"----------------------------------------------------");
					}
				}
			}	
		}
		if (pepperJob.getPepperImporters().size()<1)
			throw new PepperException("Cannot start job "+jobParams.getId()+", because no importers were found for given pepper parameter: "+ jobParams);
		{//emit all manipulators and set them to job
			for (ModuleParams moduleParams: jobParams.getModuleParams())
			{
				if (this.logService!= null) 
				{	
					this.logService.log(LogService.LOG_INFO,"----------------------------------------------------");
					this.logService.log(LogService.LOG_INFO,"manipulator name:\t"+moduleParams.getModuleName());
				}
				PepperManipulator pepperManipulator= this.getPepperModuleResolver().getPepperManipulator(moduleParams);
				if (pepperManipulator== null)
				{	
					if (this.logService!= null) 
						this.logService.log(LogService.LOG_WARNING, "Cannot correct start job "+jobParams.getId()+", because no manipulator was found for: "+moduleParams);
				}
				else 
				{
					pepperJob.getPepperModules().add(pepperManipulator);
					{//setting special parameter
						if (moduleParams.getSpecialParams()!= null)
							pepperManipulator.setSpecialParams(moduleParams.getSpecialParams());
					}
				}
				if (this.logService!= null) 
				{
					if (pepperManipulator!= null)
					{
						this.logService.log(LogService.LOG_INFO,"tmp folder:\t\t"+pepperManipulator.getTemproraries());
						this.logService.log(LogService.LOG_INFO,"resource folder:\t"+pepperManipulator.getResources());
						this.logService.log(LogService.LOG_INFO,"----------------------------------------------------");
					}
				}
			}	
		}
		
		{//emit all exporters and set them to job
			for (ExporterParams exParams: jobParams.getExporterParams())
			{
				if (this.logService!= null) 
				{
					this.logService.log(LogService.LOG_INFO,"----------------------------------------------------");
					this.logService.log(LogService.LOG_INFO,"exporter name:\t\t"+exParams.getModuleName());
					this.logService.log(LogService.LOG_INFO,"destination path:\t"+exParams.getDestinationPath());
					this.logService.log(LogService.LOG_INFO,"format name:\t\t"+exParams.getFormatName());
					this.logService.log(LogService.LOG_INFO,"format version:\t\t"+exParams.getFormatVersion());
				}
				PepperExporter pepperExporter= this.getPepperModuleResolver().getPepperExporter(exParams);
				if (pepperExporter== null)
				{	
					if (this.logService!= null) 
						this.logService.log(LogService.LOG_WARNING, "Cannot correct start job "+jobParams.getId()+", because no exporter was found for: "+exParams);
				}
				else
				{	
					CorpusDefinition corpusDefinition= PepperFWFactory.eINSTANCE.createCorpusDefinition();
					corpusDefinition.setCorpusPath(exParams.getDestinationPath());
					if (	(exParams.getFormatName()!= null) &&
							(!exParams.getFormatName().isEmpty()))
					{
						FormatDefinition formatDefinition= PepperFWFactory.eINSTANCE.createFormatDefinition();
						formatDefinition.setFormatName(exParams.getFormatName());
						formatDefinition.setFormatVersion(exParams.getFormatVersion());
						corpusDefinition.setFormatDefinition(formatDefinition);
					}
					pepperExporter.setCorpusDefinition(corpusDefinition);
					{//setting special parameter
						if (exParams.getSpecialParams()!= null)
						{
							pepperExporter.setSpecialParams(exParams.getSpecialParams());
						}
					}
					pepperJob.getPepperExporters().add(pepperExporter);
				}
				if (this.logService!= null) 
				{
					if (pepperExporter!= null)
					{
						this.logService.log(LogService.LOG_INFO,"tmp folder:\t\t"+pepperExporter.getTemproraries());
						this.logService.log(LogService.LOG_INFO,"resource folder:\t"+pepperExporter.getResources());
						this.logService.log(LogService.LOG_INFO,"----------------------------------------------------");
					}
				}
			}	
		}
		if (pepperJob.getPepperExporters().size()<1)
			throw new PepperException("Cannot start job "+jobParams.getId()+", because no exporters were found.");
		
		this.getPepperJobs().add(pepperJob);
		return(pepperJob);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void startPepperConvertJob(Integer id) throws PepperException
	{
		if (this.getPepperParams()== null)
			throw new PepperParamsException("Cannot start with job "+id+". Please set pepper params first.");
		
		PepperJobParams currPepperJobParam= null;
		for (PepperJobParams pepperJobParam: this.getPepperParams().getPepperJobParams())
		{
			if (id.equals(pepperJobParam.getId()))
				currPepperJobParam= pepperJobParam;
		}	
		if (currPepperJobParam== null)
			throw new PepperParamsException("Cannot start with job "+id+". A job with this id wasn�t found.");
		
		//create a new PepperJob
		PepperJob pepperJob= this.createPepperJob(currPepperJobParam);
		
		//setting salt project to pepper job
		SaltProject saltProject= SaltCommonFactory.eINSTANCE.createSaltProject();
		pepperJob.setSaltProject(saltProject);
		
		//create job 2 converter monitor 
		PepperFinishableMonitor monitor= PepperFWFactory.eINSTANCE.createPepperFinishableMonitor();
		this.getPepperJ2CMonitors().add(monitor);
		pepperJob.setPepperJ2CMonitor(monitor);
		
		//start job
		if (this.isParallelized())
		{//running jobs parallel 
			Thread jobThread= new Thread(pepperJob, "PepperConvertJob"+id);
			jobThread.start();
		}
		else
		{//running jobs not parallel
			pepperJob.start();
		}	
	}

	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void start() throws PepperException
	{
		{//print all user-defined configurations
			if (this.getLogService()!= null)
			{
				this.getLogService().log(LogService.LOG_INFO, "user-defined properties:");
				for (String prop: PepperFWProperties.ALL_PROP_NAMES)
				{
					this.getLogService().log(LogService.LOG_INFO, "\t"+prop+":\t"+ this.getProperties().getProperty(prop));
				}
			}
		}//print all user-defined configurations
		
		if (this.getPepperModuleResolver()== null)
			throw new PepperException("Cannot start PepperConverter, because no 'PepperModuleResolver' is given.");
		if (this.logService!= null)
			this.logService.log(LogService.LOG_INFO, this.getPepperModuleResolver().getStatus());
		
		if (this.getPepperParams()== null)
			throw new PepperException("Cannot start converting. Please set pepper params first.");
		for (PepperJobParams pepperJobParam: this.getPepperParams().getPepperJobParams())
		{
			this.startPepperConvertJob(pepperJobParam.getId());
		}	
		
		//wait for all jobs and handle Exceptions
		for (PepperFinishableMonitor j2cMonitor : this.getPepperJ2CMonitors())
		{
			j2cMonitor.waitUntilFinished();
			//exception handling for exceptions in modulecontrollers
			if (j2cMonitor.getExceptions().size()> 0)
			{	
				for (PepperException e: j2cMonitor.getExceptions())
				{
					throw e;
				}
			}
		}	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER:
				return basicUnsetPepperModuleResolver(msgs);
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_JOBS:
				return ((InternalEList<?>)getPepperJobs()).basicRemove(otherEnd, msgs);
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_J2C_MONITORS:
				return ((InternalEList<?>)getPepperJ2CMonitors()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER:
				return getPepperModuleResolver();
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_PARAMS:
				return getPepperParams();
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_JOBS:
				return getPepperJobs();
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_J2C_MONITORS:
				return getPepperJ2CMonitors();
			case PepperFWPackage.PEPPER_CONVERTER__PARALLELIZED:
				return isParallelized();
			case PepperFWPackage.PEPPER_CONVERTER__PROPERTIES:
				return getProperties();
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_PARAMS_URI:
				return getPepperParamsURI();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER:
				setPepperModuleResolver((PepperModuleResolver)newValue);
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_PARAMS:
				setPepperParams((PepperParams)newValue);
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_JOBS:
				getPepperJobs().clear();
				getPepperJobs().addAll((Collection<? extends PepperJob>)newValue);
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_J2C_MONITORS:
				getPepperJ2CMonitors().clear();
				getPepperJ2CMonitors().addAll((Collection<? extends PepperFinishableMonitor>)newValue);
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PARALLELIZED:
				setParallelized((Boolean)newValue);
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PROPERTIES:
				setProperties((Properties)newValue);
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_PARAMS_URI:
				setPepperParamsURI((URI)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER:
				unsetPepperModuleResolver();
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_PARAMS:
				setPepperParams(PEPPER_PARAMS_EDEFAULT);
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_JOBS:
				getPepperJobs().clear();
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_J2C_MONITORS:
				getPepperJ2CMonitors().clear();
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PARALLELIZED:
				setParallelized(PARALLELIZED_EDEFAULT);
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PROPERTIES:
				setProperties(PROPERTIES_EDEFAULT);
				return;
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_PARAMS_URI:
				setPepperParamsURI(PEPPER_PARAMS_URI_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_MODULE_RESOLVER:
				return isSetPepperModuleResolver();
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_PARAMS:
				return PEPPER_PARAMS_EDEFAULT == null ? pepperParams != null : !PEPPER_PARAMS_EDEFAULT.equals(pepperParams);
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_JOBS:
				return pepperJobs != null && !pepperJobs.isEmpty();
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_J2C_MONITORS:
				return pepperJ2CMonitors != null && !pepperJ2CMonitors.isEmpty();
			case PepperFWPackage.PEPPER_CONVERTER__PARALLELIZED:
				return parallelized != PARALLELIZED_EDEFAULT;
			case PepperFWPackage.PEPPER_CONVERTER__PROPERTIES:
				return PROPERTIES_EDEFAULT == null ? properties != null : !PROPERTIES_EDEFAULT.equals(properties);
			case PepperFWPackage.PEPPER_CONVERTER__PEPPER_PARAMS_URI:
				return PEPPER_PARAMS_URI_EDEFAULT == null ? pepperParamsURI != null : !PEPPER_PARAMS_URI_EDEFAULT.equals(pepperParamsURI);
		}
		return super.eIsSet(featureID);
	}


/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (pepperParams: ");
		result.append(pepperParams);
		result.append(", parallelized: ");
		result.append(parallelized);
		result.append(", properties: ");
		result.append(properties);
		result.append(", pepperParamsURI: ");
		result.append(pepperParamsURI);
		result.append(')');
		return result.toString();
	}


//========================= start: OSGi-stuff
	protected ComponentContext componentContext= null;
	
	/**
	 * Called by the Service Component Runtime of OSGi when activating this
	 * component.
	 * @param context of this component
	 */
	protected void activate(ComponentContext componentContext) 
	{
		//for DEBUG
//		System.out.println("PepperConverter is initialized...");
		if (this.logService!= null)
			this.logService.log(LogService.LOG_DEBUG, "PepperConverter is initialized...");
	}

	/**
	 * Called by the Service Component Runtime of OSGi when deactivating this
	 * component.
	 * @param context of this component
	 */
	protected void deactivate(ComponentContext componentContext) 
	{
//		System.out.println("goodbye from PepperConverter...");
		if (this.logService!= null)
			this.logService.log(LogService.LOG_DEBUG, "goodbye from PepperConverter...");

		this.componentContext = null;
	}
//========================= end: OSGi-stuff
} //PepperConverterImpl

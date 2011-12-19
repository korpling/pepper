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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PEPPER_SDOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * ...
 * This class also starts an object requesting a given interval the progress of current conversion and prints it to log (info).  
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperJobLoggerImpl#getPepperJob <em>Pepper Job</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PepperJobLoggerImpl extends EObjectImpl implements PepperJobLogger {
	/**
	 * The cached value of the '{@link #getPepperJob() <em>Pepper Job</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPepperJob()
	 * @generated
	 * @ordered
	 */
	protected PepperJob pepperJob;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected PepperJobLoggerImpl() {
		super();
		ProgressRunner runner= new ProgressRunner();
		Thread progressRunnerThread= new Thread(runner, "progress_logger");
		progressRunnerThread.start();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperFWPackage.Literals.PEPPER_JOB_LOGGER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperJob getPepperJob() {
		if (pepperJob != null && pepperJob.eIsProxy()) {
			InternalEObject oldPepperJob = (InternalEObject)pepperJob;
			pepperJob = (PepperJob)eResolveProxy(oldPepperJob);
			if (pepperJob != oldPepperJob) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB, oldPepperJob, pepperJob));
			}
		}
		return pepperJob;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PepperJob basicGetPepperJob() {
		return pepperJob;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPepperJob(PepperJob newPepperJob, NotificationChain msgs) {
		PepperJob oldPepperJob = pepperJob;
		pepperJob = newPepperJob;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB, oldPepperJob, newPepperJob);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPepperJob(PepperJob newPepperJob) {
		if (newPepperJob != pepperJob) {
			NotificationChain msgs = null;
			if (pepperJob != null)
				msgs = ((InternalEObject)pepperJob).eInverseRemove(this, PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER, PepperJob.class, msgs);
			if (newPepperJob != null)
				msgs = ((InternalEObject)newPepperJob).eInverseAdd(this, PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER, PepperJob.class, msgs);
			msgs = basicSetPepperJob(newPepperJob, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB, newPepperJob, newPepperJob));
	}
	
	public static Long LOG_PROGRESS_INTERMEDIATE_TIME= 20000L;  
	
	/**
	 * A class for printing the log status each {@link #LOG_PROGRESS_INTERMEDIATE_TIME} ms.
	 * @author Florian Zipser
	 *
	 */
	class ProgressRunner implements Runnable
	{
		@Override
		public void run() 
		{
			while(true)
			{
				if (getLogService()!= null)
					getLogService().log(LogService.LOG_INFO, getPepperJob().getPepperDocumentController().getStatus4Print());
				try {
					Thread.sleep(LOG_PROGRESS_INTERMEDIATE_TIME);
				} catch (InterruptedException e) {
					throw new PepperConvertException("An error occurs while in thread during waiting phase (only Progress logger).");
				}
			}
		}
	}
	
//=========================== start: setting LogService
	private LogService logService= null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setLogService(LogService logService) 
	{
		this.logService= logService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public LogService getLogService() 
	{
		return(this.logService);
	}

	//=========================== end: setting LogService
	
//	private class LogEvent
//	{
//		public SElementId sElementId= null;
//		public PepperJobLogger_Status status= null;
//		public String modulename= null;
//		
//		public LogEvent(SElementId sElementId, PepperJobLogger_Status status, String moduleName)
//		{
//			this.sElementId= sElementId;
//			this.status= status;
//			this.modulename= moduleName;
//		}
//	}
	
	/**
	 * Stores the given informations in an event list. if document is processed correctly.
	 * The Events will be logged via given logger.
	 * @param sElementId the id of the corpus or document to be logged
	 * @param moduleName the name of the module which worked at the given corpus or document
	 * @param status the status what the module has done with given corpus or document
	 */
	public void logStatus(SElementId sElementId, PEPPER_SDOCUMENT_STATUS status, String moduleName) 
	{
		if (	(this.logService!= null) &&
				(sElementId!= null) &&
				(sElementId.getSIdentifiableElement()!= null))
		{	
			String logStr= "";
			if (sElementId.getSIdentifiableElement() instanceof SCorpus)
				logStr= "corpus";
			else if (sElementId.getSIdentifiableElement() instanceof SDocument)
				logStr= "document";
			logStr= logStr+ " "+sElementId.getSId() + " executed by '"+moduleName+"'is " + status;
			this.getLogService().log(LogService.LOG_INFO, logStr);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB:
				if (pepperJob != null)
					msgs = ((InternalEObject)pepperJob).eInverseRemove(this, PepperFWPackage.PEPPER_JOB__PEPPER_JOB_LOGGER, PepperJob.class, msgs);
				return basicSetPepperJob((PepperJob)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB:
				return basicSetPepperJob(null, msgs);
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
			case PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB:
				if (resolve) return getPepperJob();
				return basicGetPepperJob();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB:
				setPepperJob((PepperJob)newValue);
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
			case PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB:
				setPepperJob((PepperJob)null);
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
			case PepperFWPackage.PEPPER_JOB_LOGGER__PEPPER_JOB:
				return pepperJob != null;
		}
		return super.eIsSet(featureID);
	}

} //PepperJobLoggerImpl

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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperMonitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Monitor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperMonitorImpl#getId <em>Id</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperMonitorImpl#isFinished <em>Finished</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperMonitorImpl#getExceptions <em>Exceptions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PepperMonitorImpl extends EObjectImpl implements PepperMonitor {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isFinished() <em>Finished</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFinished()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FINISHED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFinished() <em>Finished</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFinished()
	 * @generated
	 * @ordered
	 */
	protected boolean finished = FINISHED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExceptions() <em>Exceptions</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExceptions()
	 * @generated
	 * @ordered
	 */
	protected EList<PepperException> exceptions;

	/**
	 * stores the number of created monitors.
	 */
	private static Long numOfMonitors= 0l;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected PepperMonitorImpl() 
	{
		super();
		this.init();
	}

	private void init()
	{
		this.setId(numOfMonitors.toString());
		numOfMonitors++;
		this.exceptions= new BasicEList<PepperException>();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperFWPackage.Literals.PEPPER_MONITOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_MONITOR__ID, oldId, id));
	}

	/**
	 * Lock to lock await and signal methods.
	 */
	protected Lock lock= new ReentrantLock();
	
	/**
	 * Condition, which tells if the monitor don't have to wait any more.
	 */
	private Condition isFinished=lock.newCondition();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void waitUntilFinished() 
	{
		lock.lock();
		try {
		if (!finished)
			isFinished.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new PepperFWException(e.getMessage());
		}
		finally{
			lock.unlock();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void finish() 
	{
		this.lock.lock();
		try {
			this.finished= true;
			isFinished.signal();
		}finally{
			this.lock.unlock();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public boolean isFinished() 
	{
		return(this.finished);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PepperException> getExceptions() {
		if (exceptions == null) {
			exceptions = new EDataTypeUniqueEList<PepperException>(PepperException.class, this, PepperFWPackage.PEPPER_MONITOR__EXCEPTIONS);
		}
		return exceptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_MONITOR__ID:
				return getId();
			case PepperFWPackage.PEPPER_MONITOR__FINISHED:
				return isFinished();
			case PepperFWPackage.PEPPER_MONITOR__EXCEPTIONS:
				return getExceptions();
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
			case PepperFWPackage.PEPPER_MONITOR__ID:
				setId((String)newValue);
				return;
			case PepperFWPackage.PEPPER_MONITOR__EXCEPTIONS:
				getExceptions().clear();
				getExceptions().addAll((Collection<? extends PepperException>)newValue);
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
			case PepperFWPackage.PEPPER_MONITOR__ID:
				setId(ID_EDEFAULT);
				return;
			case PepperFWPackage.PEPPER_MONITOR__EXCEPTIONS:
				getExceptions().clear();
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
			case PepperFWPackage.PEPPER_MONITOR__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case PepperFWPackage.PEPPER_MONITOR__FINISHED:
				return finished != FINISHED_EDEFAULT;
			case PepperFWPackage.PEPPER_MONITOR__EXCEPTIONS:
				return exceptions != null && !exceptions.isEmpty();
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
		result.append(" (id: ");
		result.append(id);
		result.append(", finished: ");
		result.append(finished);
		result.append(", exceptions: ");
		result.append(exceptions);
		result.append(')');
		return result.toString();
	}

} //PepperMonitorImpl

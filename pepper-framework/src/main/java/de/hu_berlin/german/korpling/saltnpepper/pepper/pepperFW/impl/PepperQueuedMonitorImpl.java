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

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJobLogger;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Module2 Module Monitor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperQueuedMonitorImpl#getOrderQueue <em>Order Queue</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperQueuedMonitorImpl#isEmpty <em>Empty</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PepperQueuedMonitorImpl extends PepperMonitorImpl implements PepperQueuedMonitor {
	/**
	 * The default value of the '{@link #getOrderQueue() <em>Order Queue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderQueue()
	 * @ordered
	 */
	protected static final ConcurrentLinkedQueue<SElementId> ORDER_QUEUE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOrderQueue() <em>Order Queue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderQueue()
	 * @ordered
	 */
	protected ConcurrentLinkedQueue<SElementId> orderQueue = ORDER_QUEUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isEmpty() <em>Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEmpty()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EMPTY_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected PepperQueuedMonitorImpl() {
		super();
		this.init();
	}
	
	private void init()
	{
		this.ifEmpty= lock.newCondition();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PepperFWPackage.Literals.PEPPER_QUEUED_MONITOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public ConcurrentLinkedQueue<SElementId> getOrderQueue() {
		return orderQueue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setOrderQueue(ConcurrentLinkedQueue<SElementId> newOrderQueue) {
		ConcurrentLinkedQueue<SElementId> oldOrderQueue = orderQueue;
		orderQueue = newOrderQueue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PepperFWPackage.PEPPER_QUEUED_MONITOR__ORDER_QUEUE, oldOrderQueue, orderQueue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public boolean isEmpty() 
	{
		if (this.orderQueue== null)
			this.orderQueue= new ConcurrentLinkedQueue<SElementId>();
		return(this.orderQueue.size()== 0);
	}

	/**
	 * This constraint manages, if the order-queue is empty. If set a new element
	 * in queue, this constraint shall notify the get method.
	 */
	protected Condition ifEmpty= this.lock.newCondition();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void put(SElementId sElementId) 
	{
		lock.lock();
		//if sElementId is null throw exception
		if (sElementId== null)
			throw new NullPointerException("Cannot put an empty element-id into monitors queue.");
		//create a queue, if there is non
		if (this.orderQueue== null)
			this.orderQueue= new ConcurrentLinkedQueue<SElementId>();
		this.orderQueue.add(sElementId);
		this.ifEmpty.signal();
		lock.unlock();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public SElementId get() 
	{
		lock.lock();
		//create a queue, if there is non
		if (this.orderQueue== null)
			this.orderQueue= new ConcurrentLinkedQueue<SElementId>();
		SElementId sElementId= null;
		if ((this.orderQueue.size()== 0) && (!this.isFinished()))
		{
			try {
				this.ifEmpty.await();
			} catch (InterruptedException e) {
				throw new NullPointerException(e.getMessage());
			}
		}	
		sElementId= (SElementId)this.orderQueue.poll();	
		lock.unlock();
		return(sElementId);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void finish() 
	{
		super.finish();
		this.lock.lock();
		this.ifEmpty.signal();
		this.lock.unlock();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_QUEUED_MONITOR__ORDER_QUEUE:
				return getOrderQueue();
			case PepperFWPackage.PEPPER_QUEUED_MONITOR__EMPTY:
				return isEmpty();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PepperFWPackage.PEPPER_QUEUED_MONITOR__ORDER_QUEUE:
				setOrderQueue((ConcurrentLinkedQueue<SElementId>)newValue);
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
			case PepperFWPackage.PEPPER_QUEUED_MONITOR__ORDER_QUEUE:
				setOrderQueue(ORDER_QUEUE_EDEFAULT);
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
			case PepperFWPackage.PEPPER_QUEUED_MONITOR__ORDER_QUEUE:
				return ORDER_QUEUE_EDEFAULT == null ? orderQueue != null : !ORDER_QUEUE_EDEFAULT.equals(orderQueue);
			case PepperFWPackage.PEPPER_QUEUED_MONITOR__EMPTY:
				return isEmpty() != EMPTY_EDEFAULT;
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
		result.append(" (orderQueue: ");
		result.append(orderQueue);
		result.append(')');
		return result.toString();
	}

} //PepperModule2ModuleMonitorImpl

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
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pepper Module2 Module Monitor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class PepperFinishableMonitorImpl extends PepperMonitorImpl implements PepperFinishableMonitor {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected PepperFinishableMonitorImpl() {
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
		return PepperFWPackage.Literals.PEPPER_FINISHABLE_MONITOR;
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
//		old since 1.7.09
		if (sElementId== null)
			throw new NullPointerException("Cannot put an empty element-id into monitors queue.");
//		System.out.println("(m2m-monitor "+this.getId()+") monitor "+this.getId()+" puttet element: "+ sElementId.getSId());
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

} //PepperModule2ModuleMonitorImpl

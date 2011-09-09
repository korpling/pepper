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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW;

import java.util.concurrent.ConcurrentLinkedQueue;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pepper Module2 Module Monitor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#getOrderQueue <em>Order Queue</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#isEmpty <em>Empty</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperQueuedMonitor()
 * @model
 * @generated
 */
public interface PepperQueuedMonitor extends PepperMonitor {
	/**
	 * Returns the value of the '<em><b>Order Queue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order Queue</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order Queue</em>' attribute.
	 * @see #setOrderQueue(ConcurrentLinkedQueue)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperModule2ModuleMonitor_OrderQueue()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.ConcurrentLinkedQueue"
	 */
	ConcurrentLinkedQueue getOrderQueue();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperQueuedMonitor#getOrderQueue <em>Order Queue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order Queue</em>' attribute.
	 * @see #getOrderQueue()
	 */
	void setOrderQueue(ConcurrentLinkedQueue<SElementId> value);

	/**
	 * Returns the value of the '<em><b>Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Empty</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Empty</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperQueuedMonitor_Empty()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	boolean isEmpty();

	/**
	 * Puts an element-id into the queue of orders. This element-id can be picked up by 
	 * calling the get-method.
	 * @model sElementIdDataType="de.hub.corpling.pepper.pepperInterface.SElementId"
	 * @generated
	 */
	void put(SElementId sElementId);

	/**
	 * Removes and returns an element-id out oof the order-queue.
	 * @model dataType="de.hub.corpling.pepper.pepperInterface.SElementId"
	 * @generated
	 */
	SElementId get();

} // PepperModule2ModuleMonitor

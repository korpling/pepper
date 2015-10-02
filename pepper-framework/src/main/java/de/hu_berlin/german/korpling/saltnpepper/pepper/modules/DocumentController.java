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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules;

import java.util.List;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.DocumentControllerImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleControllerImpl;
import de.hu_berlin.u.saltnpepper.graph.Identifier;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpusGraph;
import de.hu_berlin.u.saltnpepper.salt.common.SDocument;
import de.hu_berlin.u.saltnpepper.salt.common.SDocumentGraph;
import de.hu_berlin.u.saltnpepper.salt.core.SNode;
import de.hu_berlin.u.saltnpepper.salt.core.SRelation;

public interface DocumentController {

	/**
	 * Returns the {@link SDocument} object, to which this object belongs to.
	 * 
	 * @return document id
	 */
	public SDocument getDocument();

	/**
	 * Sets the {@link SDocument} object, to which this object belongs to.
	 * Computes the globalId ( see: {@link #getGlobalId()}). Adds the global id
	 * as processing instruction to the {@link SDocument} object.
	 * 
	 * @param sDocument
	 *            {@link SDocument} object to which this object belongs to
	 */
	public void setDocument(SDocument sDocument);

	/**
	 * Returns the {@link Identifier} of the {@link SDocument} object, to which
	 * this object belongs to.
	 * 
	 * @return document id
	 */
	public Identifier getsDocumentId();

	/**
	 * Returns a global unique (inside one Salt project) id for the contained
	 * {@link SDocument} object. This identifier is computed by mthe number of
	 * the {@link SCorpusGraph} in list, this {@link SDocument} object is
	 * contained in and the {@link Identifier} of the {@link SDocument}. Imagine
	 * the number of the {@link SCorpusGraph} is 1 and the
	 * {@link Identifier#getId()} is /corpus1/document1, than the returned
	 * global id is /1/corpus1/document1.
	 * 
	 * @return a global unique (inside one Salt project) id for the contained
	 *         {@link SDocument} object
	 */
	public String getGlobalId();

	/**
	 * Returns location, where to store {@link SDocumentGraph} when
	 * {@link #sleep()} was called or load when {@link #awake()} was called
	 * 
	 * @return location
	 **/
	public URI getLocation();

	/**
	 * Sets location, where to store {@link SDocumentGraph} when
	 * {@link #sleep()} was called or load when {@link #awake()} was called.
	 * 
	 * @param location
	 *            location as {@link URI}
	 **/
	public void setLocation(URI location);
	/**
	 * Stores the number of {@link SNode}s a document contains, when it ws
	 * send to sleep.
	 * 
	 * @return number of {@link SNode} of the contained document
	 */
	public int getSize_nodes();
	/**
	 * Stores the number of {@link SRelation}s a document contains, when it ws
	 * send to sleep.
	 * 
	 * @return number of {@link SRelation} of the contained document
	 */
	public int getSize_relations();

	// ========================================== start: sleep mechanism
	/**
	 * Returns if the {@link SDocumentGraph} of contained {@link SDocument} is
	 * send to sleep or awake.
	 * 
	 * @return true, if {@link SDocumentGraph} is asleep, false otherwise.
	 */
	public boolean isAsleep();

	/**
	 * Notifies the {@link DocumentControllerImpl} object, that the contained
	 * {@link SDocument} or more precisely the {@link SDocumentGraph} object
	 * could be send to sleep. If no {@link PepperModule} is currently
	 * processing it, the {@link SDocumentGraph} is send to sleep. This means,
	 * the {@link SDocumentGraph} will be stored to local disk and removed from
	 * main memory, by calling
	 * {@link SDocument#saveSDocumentGraph(org.eclipse.emf.common.util.URI)}.
	 * The counterpart to this method is {@link #awake()}. Both methods are
	 * synchronized.
	 */
	public void sendToSleep();

	/**
	 * Sends the {@link DocumentControllerImpl} object, that the contained
	 * {@link SDocument} or more precisely the {@link SDocumentGraph} object
	 * could be send to sleep as method {@link #sendToSleep()}. <br/>
	 * <strong>But note, this method does not check if the
	 * {@link SDocumentGraph} is still in use by any other module. Therfore
	 * handle with care.</strong>
	 */
	public void sendToSleep_FORCE();

	/**
	 * Wakes up the contained {@link SDocument}, which means, it the
	 * {@link SDocumentGraph} of the {@link SDocument} will be load to main
	 * memory again by calling {@link SDocument#loadSDocumentGraph()}. The
	 * counterpart to this method is {@link #sleep()}. Both methods are
	 * synchronized.
	 */
	public void awake();

	// ========================================== end: sleep mechanism

	/**
	 * Returns a list of all {@link ModuleControllerImpl} objects, the here
	 * contained {@link SDocument} object has to pass.
	 * 
	 * @return a list of all {@link ModuleControllerImpl} objects
	 * **/
	public List<ModuleControllerImpl> getModuleControllers();
	/**
	 * Returns the module controller, which is currently processing this document.
	 * 
	 * @return the currently active {@link ModuleController}
	 * **/
	public ModuleController getCurrentModuleController();

	/**
	 * Adds a further {@link ModuleControllerImpl} to internal list of all
	 * {@link ModuleControllerImpl} objects, the here contained
	 * {@link SDocument} object has to pass. <br/>
	 * <strong>Note: You cannot call this method anymore, if the process has
	 * already been started. Which means that the methods
	 * {@link #updateStatus(String, DOCUMENT_STATUS)} has been called.</strong>
	 * 
	 * @param moduleController
	 *            {@link ModuleControllerImpl} the {@link SDocument} also has to
	 *            pass
	 * **/
	public void addModuleControllers(ModuleControllerImpl moduleController);

	/**
	 * Returns the number of {@link PepperModule} currently processing the
	 * {@link SDocument} or more precisly the {@link SDocumentGraph} contained
	 * by this {@link DocumentControllerImpl} object.
	 * 
	 * @return number of processing {@link PepperModule} objects
	 */
	public int getNumOfProcessingModules();

	/**
	 * Updates the status of a specified of contained {@link SDocument} object
	 * corresponding to the {@link ModuleControllerImpl} matching to the passed
	 * id.
	 * 
	 * @param pModuleController
	 *            determines the {@link StepStatus} object
	 * @param status
	 *            the status to which the {@link StepStatus} shall be set to.
	 */
	public void updateStatus(ModuleController moduleController, DOCUMENT_STATUS status);
//	public void updateStatus(String moduleControllerId, DOCUMENT_STATUS status);

	/**
	 * Returns the global status of this object. The global status is determined
	 * by each {@link StepStatus} object being contained in this object.
	 * <ul>
	 * <li>If one of the contained {@link StepStatus} objects status value is
	 * set to {@link DOCUMENT_STATUS#IN_PROGRESS}, the global status will be
	 * {@link DOCUMENT_STATUS#IN_PROGRESS}.</li>
	 * <li>If one of the contained {@link StepStatus} objects status value is
	 * set to {@link DOCUMENT_STATUS#DELETED}, the global status will be
	 * {@link DOCUMENT_STATUS#DELETED}.</li>
	 * <li>If one of the contained {@link StepStatus} objects status value is
	 * set to {@link DOCUMENT_STATUS#FAILED}, the global status will be
	 * {@link DOCUMENT_STATUS#FAILED}.</li>
	 * <li>Only if each contained {@link StepStatus} objects status value is set
	 * to {@link DOCUMENT_STATUS#COMPLETED}, the global status will be
	 * {@link DOCUMENT_STATUS#COMPLETED}.</li>
	 * </ul>
	 * 
	 * @return
	 */
	public DOCUMENT_STATUS getGlobalStatus();

	/**
	 * Returns the progress of the contained {@link SDocument} for all
	 * registered {@link ModuleControllerImpl} objects.
	 * 
	 * @return percentage value between 0 and 1.
	 */
	public double getProgress();

	/**
	 * Returns the processing time of the contained {@link SDocument} object,
	 * needed by all registered {@link PepperModule}, which have already started
	 * the process.
	 */
	public Long getProcessingTime();

}
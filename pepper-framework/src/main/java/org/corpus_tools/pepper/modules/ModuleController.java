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
package org.corpus_tools.pepper.modules;

import java.util.concurrent.Future;

import org.corpus_tools.pepper.common.PepperJob;
import org.corpus_tools.pepper.core.DocumentBus;
import org.corpus_tools.pepper.core.ModuleControllerImpl;
import org.corpus_tools.pepper.core.PepperJobImpl;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;

/**
 * An object of this types contains a {@link PepperModule} and handles as a
 * connector between such an object and the Pepper framework.
 * 
 * @author Florian Zipser
 */
public interface ModuleController {

	/**
	 * Returns the id of this object.
	 * 
	 * @return the stable id
	 */
	public String getId();

	/**
	 * Returns the {@link PepperModule} object, this controller object is
	 * observing.
	 * 
	 * @return observed object
	 */
	public PepperModule getPepperModule();

	/**
	 * Returns the {@link PepperJobImpl} object reference of this object.
	 * 
	 * @return containing {@link PepperJobImpl} object
	 */
	public PepperJobImpl getJob();

	/**
	 * Sets the {@link PepperJobImpl} object reference of this object. And
	 * notifies the reverse method {@link PepperJobImpl}, to set the
	 * {@link ModuleControllerImpl} object to this.
	 * 
	 * @param job
	 *            new {@link PepperJobImpl} object
	 */
	public void setJob(PepperJobImpl job);

	/**
	 * Sets the {@link PepperJobImpl} object reference of this object.
	 * 
	 * @param job
	 *            new {@link PepperJobImpl} object
	 */
	public void setJob_basic(PepperJobImpl job);

	/**
	 * The {@link DocumentBus} object working as input for this
	 * {@link ModuleControllerImpl}. All documents on bus will be processed and
	 * set to {@link #outputDocumentBus}
	 * 
	 * @return bus which is input bus
	 */
	public DocumentBus getInputDocumentBus();

	/**
	 * The {@link DocumentBus} object working as input for this
	 * {@link ModuleControllerImpl}. All documents on bus will be processed and
	 * set to {@link #outputDocumentBus}
	 * 
	 * @param inputDocumentBus
	 *            bus to be used as input bus
	 **/
	public void setInputDocumentBus(DocumentBus inputDocumentBus);

	/**
	 * All documents which were consumed by the {@link PepperModule} contained
	 * in this object are set to this output bus, regarding the status, the
	 * {@link PepperModule} returned.
	 * 
	 * @return bus, which is output bus
	 */
	public DocumentBus getOutputDocumentBus();

	/**
	 * All documents which were consumed by the {@link PepperModule} contained
	 * in this object are set to this output bus, regarding the status, the
	 * {@link PepperModule} returned.
	 * 
	 * @param outputDocumentBus
	 *            bus to be used as input bus
	 */
	public void setOutputDocumentBus(DocumentBus outputDocumentBus);

	/**
	 * Returns the {@link SCorpusGraph} object, the contained
	 * {@link PepperImporter} is mapping. This method will only return a non
	 * empty object, if the contained {@link PepperModule} is an
	 * {@link PepperImporter} and if
	 * {@link #importCorpusStructure(SCorpusGraph)} was called.
	 * 
	 * @return
	 */
	public SCorpusGraph getCorpusGraph();

	/**
	 * Starts the import of corpus structure via the set {@link PepperImporter}
	 * object by calling
	 * {@link PepperImporter#importCorpusStructure(SCorpusGraph)}.
	 *  This method can only be invoked once per for a module.
	 * 
	 * @param sCorpusGraph
	 *            a {@link SCorpusGraph} object, in which the
	 *            {@link PepperImporter} shall import the corpus structure.
	 */
	public void importCorpusStructure(SCorpusGraph sCorpusGraph);

	/**
	 * Returns the next {@link DocumentController} waiting in the input document
	 * bus to be processed by the contained {@link PepperModule}.
	 * 
	 * In contrast to {@link #pop(String)}, if
	 * <code>ignorePermissionForDocument</code> is set to true this method
	 * returns a {@link DocumentController} object even if the {@link PepperJob}
	 * permission does not allow to process a further document. This mechanism
	 * can be used, if a {@link PepperModule} has an own control mechanism of
	 * sending {@link SDocument}s to sleep.
	 * 
	 * @param ignorePermissionForDocument
	 *            if set, a document will be returned even if the Pepper job
	 *            gives no permission
	 * @return next document controller
	 */
	public DocumentController next(boolean ignorePermissionForDocument);

	/**
	 * Returns the next {@link DocumentController} waiting in the input document
	 * bus to be processed by the contained {@link PepperModule}.
	 * 
	 * @return next document controller
	 */
	public DocumentController next();

	/**
	 * Adds the given {@link DocumentController} to the output document bus to
	 * be processed by the next {@link PepperModule} objects.
	 * 
	 * @param documentController
	 *            to be passed to next Pepper module
	 */
	public void complete(DocumentController documentController);

	/**
	 * Notifies the Pepper framework, that the {@link SDocument} being contained
	 * in passed {@link DocumentController} shall not be processed any further
	 * by following Pepper modules.
	 * 
	 * @param sElementId
	 *            the id corresponding to the {@link SDocument} object, which
	 *            shall be not further processed
	 */
	public void delete(DocumentController documentController);

	/**
	 * Returns the progress as a value between 0 and 1 of the contained
	 * {@link PepperModule} object concerning to the {@link SDocument}
	 * corresponding to the passed global identifier.
	 * 
	 * @param globalId
	 *            global id for {@link SDocument}, note, that this is not the
	 *            {@link SElementId}
	 * @return progress of process
	 */
	public Double getProgress(String globalId);

	/**
	 * Sets the {@link PepperModule} object, this controller object is
	 * observing. Also sets the inverse method
	 * {@link PepperModule#setPepperModuleController_basic(ModuleControllerImpl)}
	 * 
	 * @param newPepperModule
	 *            new object to observe
	 **/
	public void setPepperModule(PepperModule newPepperModule);

	/**
	 * Sets the {@link PepperModule} object, this controller object is
	 * observing.
	 * 
	 * @param newPepperModule
	 *            new object to observe
	 **/
	public void setPepperModule_basic(PepperModule newPepperModule);

}
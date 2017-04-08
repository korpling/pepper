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
package org.corpus_tools.pepper.core;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.modules.DocumentController;

/**
 * TODO make docu
 */
public class InitialDocumentBus extends DocumentBus {
	/** id for the initial document bus, which is input for importers **/
	protected static final String ID_INTITIAL = "initial";

	/**
	 * Creates an object of this type and sets the passes the given input
	 * controller and output controller.
	 * 
	 * @param inputModuleControllerId
	 *            identifier of controller, which produces documents for this
	 *            bus
	 * @param outputModuleControllerId
	 *            identifier of controller, which consumes documents for this
	 *            bus
	 */
	public InitialDocumentBus(String outputModuleControllerId) {
		super(ID_INTITIAL, outputModuleControllerId);
	}

	/**
	 * Creates an object of this type and sets the passes the given input
	 * controller and output controller.
	 * 
	 * @param inputModuleControllerId
	 *            identifier of controller, which produces documents for this
	 *            bus
	 * @param outputModuleControllerIds
	 *            list of identifier of controller, which consumes documents for
	 *            this bus
	 */
	public InitialDocumentBus(List<String> outputModuleControllerIds) {
		super(ID_INTITIAL, outputModuleControllerIds);
	}

	/**
	 * Adds the given {@link DocumentController} object to the queue.
	 * 
	 * @param documentController
	 *            the {@link DocumentController} object to be added to the
	 *            queue.
	 */
	@Override
	public void put(DocumentController documentController) {
		if (documentController == null)
			throw new PepperFWException("Cannot add a null value as DocumentController into documentBus.");

		if (!documentController.isAsleep()) {
			documentController.sendToSleep();
		}

		lock.lock();
		try {
			// add new document controller to all queues
			Collection<ConcurrentLinkedQueue<DocumentController>> queues = getDocumentBus().values();
			for (ConcurrentLinkedQueue<DocumentController> queue : queues) {
				queue.add(documentController);
			}

			// notifies condition, that new document is ready to be processed
			waitUntilAllDocumentsArePut.signal();
		} finally {
			lock.unlock();
		}
	}
}

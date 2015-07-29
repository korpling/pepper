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
package de.hu_berlin.german.korpling.saltnpepper.pepper.core;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperFWException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.DocumentController;

/**
 * TODO make docu
 * 
 * @author Florian Zipser
 *
 */
public class TerminalDocumentBus extends DocumentBus {
	/** id for the terminal document bus, which is output for exporters **/
	protected static final String ID_TERMINAL = "terminal";

	/**
	 * Creates an object of this type and sets the passes the given input
	 * controller and output controller.
	 * 
	 * @param inputModuleControllerId
	 *            identifier of controller, which produces documents for this
	 *            bus
	 */
	public TerminalDocumentBus(String inputModuleControllerId) {
		super(inputModuleControllerId, ID_TERMINAL);
	}

	/**
	 * Creates an object of this type and sets all passed in- and output
	 * {@link ModuleControllerImpl} objects. This is important, because an
	 * {@link DocumentController} can only be removed from queue, when all
	 * output {@link ModuleControllerImpl} objects requested the
	 * {@link DocumentController} object.
	 * 
	 * @param inputModuleControllerIds
	 *            list of identifier of controller, which produces documents for
	 *            this bus
	 */
	public TerminalDocumentBus(List<String> inputModuleControllerIds) {
		super(inputModuleControllerIds, ID_TERMINAL);
	}

	/**
	 * Returns a {@link DocumentController} object, which is on first place of
	 * the internal queue (regarding the FIFO principle). When all registered
	 * {@link ModuleControllerImpl} objects popped the
	 * {@link DocumentController} object, it will be removed from internal
	 * queue.
	 * 
	 * @param moduleController
	 *            object requesting the {@link DocumentController} object
	 * @return head of the queue.
	 */
	@Override
	public DocumentController pop(String outputControllerId) {
		DocumentController documentController = null;
		lock.lock();
		try {
			ConcurrentLinkedQueue<DocumentController> queue = getDocumentBus().get(outputControllerId);

			if (queue == null)
				throw new PepperFWException("Document bus '" + getId() + "' cannot pop a document controller, because there is no entry for module controller '" + outputControllerId + "'.");

			if ((queue.size() == 0) && (!this.isFinished())) {
				if ((queue.size() == 0) && (!this.isFinished())) {
					waitUntilAllDocumentsArePut.await();
				}
			}
			if (getPepperJob() != null) {
				getPepperJob().getPermissionForProcessDoument(documentController);
			}
			documentController = queue.poll();
			documentController.sendToSleep();
		} catch (InterruptedException e) {
			throw new PepperFWException("Something went wrong, when waiting for lock 'waitUntilAllDocumentsArePut'.", e);
		} finally {
			lock.unlock();
		}
		return (documentController);
	}
}

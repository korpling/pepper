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
package org.corpus_tools.pepper.common;

import org.corpus_tools.pepper.core.DocumentBus;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SDocumentGraph;

/**
 * Describes the main memory policy of {@link PepperJob}s. More specific, it
 * determines the way of loading and storing {@link SDocumentGraph} objects
 * during the processing. The behavior of the {@link DocumentBus} is described
 * as follows:
 * <ul>
 * <li>{@link MEMORY_POLICY#THRIFTY} - each time, a {@link PepperModule} is
 * setting a {@link SDocument} to {@link DOCUMENT_STATUS#COMPLETED} and no other
 * {@link PepperModule} is currently working on it, it will be send to sleep.
 * </li>
 * <li>{@link MEMORY_POLICY#MODERATE} - each time, a {@link PepperModule} is
 * setting a {@link SDocument} to {@link DOCUMENT_STATUS#COMPLETED}, no other
 * {@link PepperModule} is currently working on it and no other
 * {@link PepperModule} is waiting on it, it will be send to sleep.</li>
 * <li>{@link MEMORY_POLICY#GREEDY} - A {@link SDocumentGraph} will never be
 * send to sleep. In case of the number of maximal processed documents is
 * limited, and was reached, no further {@link SDocumentGraph}s will be imported
 * until one was finished somehow.</li>
 * </ul>
 * 
 * @author Florian Zipser
 *
 */
public enum MEMORY_POLICY {
	THRIFTY, MODERATE, GREEDY
}

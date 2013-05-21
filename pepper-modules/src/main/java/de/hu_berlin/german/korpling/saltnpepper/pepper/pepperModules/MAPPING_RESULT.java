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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * Determines possible return modes of a mapping (see {@link PepperMapper#map()} and {@link PepperMapper#start()}).
 * <ul>
 *   <li>{@link MAPPING_RESULT#FINISHED} means, mapping was successful</li>
 *   <li>{@link MAPPING_RESULT#FAILED} means, mapping was not successful and ended with an error</li>
 *   <li>{@link MAPPING_RESULT#DELETED} means, mapping was successful and Mapped object {@link SDocument} or {@link SCorpus} was deleted (for instance when merging objects, and no further processing of mapped object is necessary)</li>
 * </ul> 
 * @author Florian Zipser
 *
 */
public enum MAPPING_RESULT {FINISHED, FAILED, DELETED}

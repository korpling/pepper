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

import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;

/**
 * <p>
 * A mapping task in the Pepper workflow is not a monolithic block. It consists
 * of several smaller steps.
 * <ul>
 * <li>Declare the fingerprint of the module. This is part of the constructor.
 * </li>
 * <li>Check readyness of the module.</li>
 * <li>Map the document structure and create a mapper for each corpus and
 * document.</li>
 * <li>clean-up</li>
 * </ul>
 * The following describes the single steps in short. To get a more detailed
 * explanation, take a look to the documentations found at
 * <a href="http://u.hu-berlin.de/saltnpepper" >http://u.hu-berlin.de/
 * saltnpepper</a>.
 * </p>
 * <p>
 * <h3>Declare the fingerprint</h3> Initialize the module and set the modules
 * name and description. This is part of the constructor:
 * 
 * <pre>
 * public MyModule() {
 * 	super(&quot;Name of the module&quot;);
 * 	setSupplierContact(URI.createURI(&quot;Contact address of the module's supplier&quot;));
 * 	setSupplierHomepage(URI.createURI(&quot;homepage of the module&quot;));
 * 	setDesc(&quot;A short description of what is the intention of this module, for instance which formats are importable. &quot;);
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * <h3>Check readyness of the module</h3> This method is invoked by the Pepper
 * framework before the mapping process is started. This method must return
 * true, otherwise, this Pepper module could not be used in a Pepper workflow.
 * At this point problems which prevent the module from being used you can
 * report all problems to the user, for instance a database connection could not
 * be established.
 * 
 * <pre>
 * public boolean isReadyToStart() {
 * 	return (true);
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * <h3>Map the document structure</h3> In the method
 * {@link #createPepperMapper(SElementId)} a {@link PepperMapper} object needs
 * to be initialized and returned. The {@link PepperMapper} is the major part
 * major part doing the mapping. It provides the methods
 * {@link PepperMapper#mapSCorpus()} to handle the mapping of a single
 * {@link SCorpus} object and {@link PepperMapper#mapSDocument()} to handle a
 * single {@link SDocument} object. Both methods are invoked by the Pepper
 * framework. The following snippet shows a dummy of that method:
 * 
 * <pre>
 * public PepperMapper createPepperMapper(SElementId sElementId) {
 * 	PepperMapper mapper = new PepperMapperImpl() {
 * 		&#064;Override
 * 		public DOCUMENT_STATUS mapSCorpus() {
 * 			// handling the mapping of a single corpus
 * 
 * 			// accessing the current file or folder
 * 			getResourceURI();
 * 
 * 			// returning, that the corpus was mapped successfully
 * 			return (DOCUMENT_STATUS.COMPLETED);
 * 		}
 * 
 * 		&#064;Override
 * 		public DOCUMENT_STATUS mapSDocument() {
 * 			// handling the mapping of a single document
 * 
 * 			// accessing the current file or folder
 * 			getResourceURI();
 * 
 * 			// returning, that the document was mapped successfully
 * 			return (DOCUMENT_STATUS.COMPLETED);
 * 		}
 * 	};
 * 	return (mapper);
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * <h3>clean-up</h3> Sometimes it might be necessary to clean up after the
 * module did the job. For instance when writing an im- or an exporter it might
 * be necessary to close file streams, a db connection etc. Therefore, after the
 * processing is done, the Pepper framework calls the method described in the
 * following snippet:
 * 
 * <pre>
 * public void end() {
 * 	super.end();
 * 	// do some clean up like closing of streams etc.
 * }
 * </pre>
 * 
 * </p>
 * 
 * @author Florian Zipser
 */
public interface PepperManipulator extends PepperModule {
}

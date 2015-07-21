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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * This is a PepperExporter which extracts and exports the primary text of a
 * Salt model and stores it into a text file. 
 * 
 * @author Florian Zipser
 * @version 1.0
 * 
 */
@Component(name = "TextExporterComponent", factory = "PepperExporterComponentFactory")
public class TextExporter extends PepperExporterImpl implements PepperExporter {
	public static final String MODULE_NAME = "TextExporter";
	public static final String FORMAT_NAME = ENDING_TXT;
	public static final String FORMAT_VERSION = "1.0";

	public TextExporter() {
		// setting name of module
		super(MODULE_NAME);
		setSupplierContact(URI.createURI("saltnpepper@lists.hu-berlin.de"));
		setSupplierHomepage(URI.createURI("https://github.com/korpling/pepper"));
		setDesc("This is a PepperExporter which extracts and exports the primary text of a Salt model and stores it into a text file. ");
		// set list of formats supported by this module
		this.addSupportedFormat(FORMAT_NAME, FORMAT_VERSION, null);
		
		setExportMode(EXPORT_MODE.DOCUMENTS_IN_FILES);
		setSDocumentEnding(ENDING_TXT);
		
	}

	/**
	 * Creates a mapper to export primary texts.
	 */
	@Override
	public PepperMapper createPepperMapper(SElementId sElementId) {
		PepperMapper mapper = new PepperMapperImpl() {
			@Override
			public DOCUMENT_STATUS mapSDocument() {
				if (getSDocument()!= null && getResourceURI()!= null){
					for (STextualDS text: getSDocument().getSDocumentGraph().getSTextualDSs()){
						File outFile= null;
						String uriStr= getResourceURI().toFileString();
						
						if (uriStr== null){
							uriStr= getResourceURI().toString();
						}
						if (getSDocument().getSDocumentGraph().getSTextualDSs().size()>1){
							String extension= "."+ getResourceURI().fileExtension();
							int pos= uriStr.lastIndexOf(extension);
							uriStr= uriStr.substring(0, pos)+"_"+text.getSElementPath().fragment()+extension;
						}
						outFile= new File(uriStr);
						PrintWriter out= null;
						try {
							out = new PrintWriter(outFile);
						} catch (FileNotFoundException e) {
							throw new PepperModuleException(this, "Cannot write primary text '"+text.getSElementId()+"' to file '"+outFile.getAbsolutePath()+"'. ",e );
						}
						if (out!= null){
							out.print(text.getSText());
							out.flush();
							out.close();
						}
					}
				}
				return (DOCUMENT_STATUS.COMPLETED);
			}
		};
		mapper.setResourceURI(getSElementId2ResourceTable().get(sElementId));
		return (mapper);
	}
}

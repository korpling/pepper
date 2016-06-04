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
package org.corpus_tools.pepper.modules.coreModules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.PepperConfiguration;
import org.corpus_tools.pepper.impl.PepperImporterImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.STextualDS;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

/**
 * Maps a file-structure to a corpus-structure and reads the contained txt files
 * to map their content to {@link STextualDS} objects.
 * 
 * @author Florian Zipser
 * 
 */
@Component(name = "TextImporterComponent", factory = "PepperImporterComponentFactory")
public class TextImporter extends PepperImporterImpl implements PepperImporter {
	public static final String MODULE_NAME = "TextImporter";
	public static final String FORMAT_NAME = "txt";
	public static final String FORMAT_VERSION = "0.0";

	/**
	 * Specifies the separator, which has to be set between to the texts of two
	 * token.
	 */
	public TextImporter() {
		super(MODULE_NAME);
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		setDesc("This importer imports a simple text document like .txt etc. . Even other documents can be imported as simple text. ");
		// set list of formats supported by this module
		this.addSupportedFormat(FORMAT_NAME, FORMAT_VERSION, null);
		this.getDocumentEndings().add(FORMAT_NAME);
	}

	@Override
	public Double isImportable(URI corpusPath) {
		return (1.0);
	}

	/**
	 * Creates a mapper of type {@link EXMARaLDA2SaltMapper}.
	 * {@inheritDoc PepperModule#createPepperMapper(Identifier)}
	 */
	@Override
	public PepperMapper createPepperMapper(Identifier sElementId) {
		PepperMapper mapper = new TextMapper();
		mapper.setResourceURI(getIdentifier2ResourceTable().get(sElementId));
		return (mapper);
	}

	/**
	 * Reads the content of txt files and creates a {@link STextualDS} object
	 * for each.
	 * 
	 * @author Florian Zipser
	 *
	 */
	public static class TextMapper extends PepperMapperImpl {
		/**
		 * Reads txt files and maps their content to a {@link STextualDS}
		 * object.
		 */
		@Override
		public DOCUMENT_STATUS mapSDocument() {
			if (getResourceURI() == null) {
				throw new PepperModuleException(this, "Cannot map txt-file, because the given resurce uri is empty.");
			}
			if (getDocument().getDocumentGraph() == null) {
				getDocument().setDocumentGraph(SaltFactory.createSDocumentGraph());
			}
			StringBuilder sb = new StringBuilder();
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(getResourceURI().toFileString()));
				String line = br.readLine();
				boolean isFirstLine = true;
				while (line != null) {
					if (!isFirstLine) {
						sb.append(System.getProperty("line.separator").toString());
					}
					sb.append(line);
					isFirstLine = false;
					line = br.readLine();
				}
			} catch (FileNotFoundException e) {
				throw new PepperModuleException(this, "Cannot read file '" + getResourceURI() + "', because of nested exception: ", e);
			} catch (IOException e) {
				throw new PepperModuleException(this, "Cannot read file '" + getResourceURI() + "', because of nested exception: ", e);
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						throw new PepperModuleException(this, "Cannot close file '" + getResourceURI() + "', because of nested exception: ", e);
					}
				}
			}
			getDocument().getDocumentGraph().createTextualDS(sb.toString());
			return (DOCUMENT_STATUS.COMPLETED);
		}
	}
}

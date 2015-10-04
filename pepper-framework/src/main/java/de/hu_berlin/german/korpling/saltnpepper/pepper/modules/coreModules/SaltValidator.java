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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperManipulatorImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.u.saltnpepper.graph.Identifier;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpus;
import de.hu_berlin.u.saltnpepper.salt.common.SDocument;
import de.hu_berlin.u.saltnpepper.salt.common.STextualDS;
import de.hu_berlin.u.saltnpepper.salt.common.STextualRelation;
import de.hu_berlin.u.saltnpepper.salt.core.SRelation;

/**
 * This class analyzes a Salt model and prints out a report about conflicts in
 * the Salt model.
 * 
 * @author Florian Zipser
 * 
 */
@Component(name = "SaltValidatorManipulatorComponent", factory = "PepperManipulatorComponentFactory")
public class SaltValidator extends PepperManipulatorImpl {
	private static final Logger logger = LoggerFactory.getLogger(SaltValidator.class);

	private static final String MSG_PREFIX = "[validator]: ";
	private static final String MSG_HELP = "You can switch on property '" + SaltValidatorProperties.PROP_CLEAN + "' to try to clean the model, but take care, this could also mean to remove model objects.";

	public SaltValidator() {
		super();
		setName("SaltValidator");
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		this.setProperties(new SaltValidatorProperties());
	}

	@Override
	public PepperMapper createPepperMapper(Identifier sElementId) {
		PepperMapper mapper = new ValidatorMapper();
		return (mapper);
	}

	/**
	 * Checks whether each {@link SCorpus} object contains either a
	 * {@link SCorpus} object or a {@link SDocument} object.
	 */

	public class ValidatorMapper extends PepperMapperImpl {
		/**
		 * <ul>
		 * <li>Checks whether each {@link SCorpus} object contains either a
		 * {@link SCorpus} object or a {@link SDocument} object.
		 * <li/>
		 * </ul>
		 */
		@Override
		public DOCUMENT_STATUS mapSCorpus() {
			if (getCorpus() != null) {
				Boolean isLeafCorpus = null;
				for (SRelation relation : getCorpus().getGraph().getOutRelations(getCorpus().getId())) {
					if (relation.getTarget() instanceof SDocument) {
						if ((isLeafCorpus != null) && (!isLeafCorpus)) {
							logger.info(MSG_PREFIX + "Salt model not valid, the corpus '" + relation.getSource().getId() + "' contains corpora and documents as well.");
						}
						isLeafCorpus = true;
					} else if (relation.getTarget() instanceof SCorpus) {
						if ((isLeafCorpus != null) && (isLeafCorpus)) {
							logger.info(MSG_PREFIX + "Salt model not valid, the corpus '" + relation.getSource().getId() + "' contains corpora and documents as well.");
						}
						isLeafCorpus = true;
					}
				}
			}
			return super.mapSCorpus();
		}

		/**
		 * <ul>
		 * <li>Checks whether {@link STextualDS#getText()} is not empty when an
		 * {@link STextualRelation} is pointing to it.</li>
		 * <li>Checks whether start and end values of {@link STextualRelation}
		 * are not empty and fit into corresponding text</li>
		 * <li>Checks whether all {@link SRelation} object have a source and a
		 * target node</li>
		 * </ul>
		 */
		@Override
		public DOCUMENT_STATUS mapSDocument() {
			List<String> invalidities = new ArrayList<String>();
			if (getDocument().getDocumentGraph() != null) {
				for (SRelation rel : getDocument().getDocumentGraph().getRelations()) {
					if (rel.getSource() == null) {
						// relation has no source

						String msg = "The relation '" + rel.getId() + "' has no source node. ";
						if (((SaltValidatorProperties) getProperties()).isClean()) {
							getDocument().getDocumentGraph().removeRelation(rel);
							msg = msg + "[DELETED] ";
						}
						invalidities.add(msg);
					}
					if (rel.getTarget() == null) {
						// relation has no target

						String msg = "The relation '" + rel.getId() + "' has no target node.";
						if (((SaltValidatorProperties) getProperties()).isClean()) {
							getDocument().getDocumentGraph().removeRelation(rel);
							msg = msg + "[DELETED] ";
						}
						invalidities.add(msg);
					}

					if ((rel.getSource() != null) && (rel.getTarget() != null) && (rel.getSource().equals(rel.getTarget()))) {
						// source and target of relation point to the same node
						if (((SaltValidatorProperties) getProperties()).isSelfRelation()) {
							getDocument().getDocumentGraph().removeRelation(rel);
							invalidities.add("[DELETED] The source and target of relation '" + rel.getId() + "' points to the same node '" + rel.getSource().getId() + "'. ");
						}
					}

					if (rel instanceof STextualRelation) {
						STextualRelation textRel = (STextualRelation) rel;
						if (textRel.getStart() == null) {
							// relation has no start value

							invalidities.add("The " + STextualRelation.class.getSimpleName() + " '" + rel.getId() + "' has no sStart value.");
						}
						if (textRel.getEnd() == null) {
							// relation has no end value

							invalidities.add("The " + STextualRelation.class.getSimpleName() + " '" + rel.getId() + "' has no sEnd value.");
						}
						if (textRel.getTarget().getText() == null) {
							// relation target has no text

							invalidities.add("The " + STextualDS.class.getSimpleName() + " '" + textRel.getTarget().getId() + "' has contains no sText value, but there are '" + STextualRelation.class.getSimpleName() + "' relations refering it.");
						}
						if ((textRel.getStart() > textRel.getTarget().getText().length()) || (textRel.getStart() < 0)) {
							// end value is bigger than size of text or is less
							// than o

							invalidities.add("The sStart value '" + textRel.getStart() + "' of " + STextualRelation.class.getSimpleName() + " '" + rel.getId() + "' is not in range of target text. It's length is '" + textRel.getTarget().getText().length() + "'.");
						}
						if ((textRel.getEnd() > textRel.getTarget().getText().length()) || (textRel.getEnd() < 0)) {
							// end value is bigger than size of text or is less
							// than o

							invalidities.add("The sEnd value '" + textRel.getEnd() + "' of " + STextualRelation.class.getSimpleName() + " '" + rel.getId() + "' is not in range of target text. It's length is '" + textRel.getTarget().getText().length() + "'.");
						}
					}
				}
			}
			if (invalidities.size() != 0) {
				String msg = MSG_PREFIX + "The Salt model is not valid.";
				if (!((SaltValidatorProperties) getProperties()).isClean()) {
					msg = msg + MSG_HELP;
				}
				msg = msg + "The following invalidities have been found in document-structure '" + getDocument().getId() + "':";
				logger.info(msg);
				for (String invalidity : invalidities) {
					logger.info("\t" + invalidity);
				}
			}
			return (DOCUMENT_STATUS.COMPLETED);
		}
	}

}

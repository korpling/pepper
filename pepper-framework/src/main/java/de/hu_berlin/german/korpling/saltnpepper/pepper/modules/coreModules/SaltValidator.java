package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.dot.DOTManipulatorProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperManipulatorImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

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

	public SaltValidator() {
		super();
		setName("SaltValidator");
		this.setProperties(new DOTManipulatorProperties());
	}

	@Override
	public PepperMapper createPepperMapper(SElementId sElementId) {
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
			if (getSCorpus() != null) {
				Boolean isLeafCorpus = null;
				for (Edge edge : getSCorpus().getSCorpusGraph().getOutEdges(getSCorpus().getSId())) {
					if (edge.getTarget() instanceof SDocument) {
						if ((isLeafCorpus != null) && (!isLeafCorpus)) {
							logger.info("Salt model not valid, the corpus '" + edge.getSource().getId() + "' contains corpora and documents as well.");
						}
						isLeafCorpus = true;
					} else if (edge.getTarget() instanceof SCorpus) {
						if ((isLeafCorpus != null) && (isLeafCorpus)) {
							logger.info("Salt model not valid, the corpus '" + edge.getSource().getId() + "' contains corpora and documents as well.");
						}
						isLeafCorpus = true;
					}
				}
			}
			return super.mapSCorpus();
		}

		/**
		 * <ul>
		 * <li>Checks whether {@link STextualDS#getSText()} is not empty when an
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
			if (getSDocument().getSDocumentGraph() != null) {
				for (SRelation rel : getSDocument().getSDocumentGraph().getSRelations()) {
					if (rel.getSSource() == null) {
						invalidities.add("The relation '" + rel.getSId() + "' has no source node.");
					}
					if (rel.getSTarget() == null) {
						invalidities.add("The relation '" + rel.getSId() + "' has no target node.");
					}
					if (rel instanceof STextualRelation) {
						STextualRelation textRel = (STextualRelation) rel;
						if (textRel.getSStart() == null) {
							invalidities.add("The " + STextualRelation.class.getSimpleName() + " '" + rel.getSId() + "' has no sStart value.");
						}
						if (textRel.getSEnd() == null) {
							invalidities.add("The " + STextualRelation.class.getSimpleName() + " '" + rel.getSId() + "' has no sEnd value.");
						}
						if (textRel.getSTextualDS().getSText() == null) {
							invalidities.add("The " + STextualDS.class.getSimpleName() + " '" + textRel.getSTextualDS().getSId() + "' has contains no sText value, but there are '" + STextualRelation.class.getSimpleName() + "' relations refering it.");
						}
						if ((textRel.getSStart() > textRel.getSTextualDS().getSText().length()) || (textRel.getSStart() < 0)) {
							invalidities.add("The sStart value '" + textRel.getSStart() + "' of " + STextualRelation.class.getSimpleName() + " '" + rel.getSId() + "' is not in range of target text. It's length is '" + textRel.getSTextualDS().getSText().length() + "'.");
						}
						if ((textRel.getSEnd() > textRel.getSTextualDS().getSText().length()) || (textRel.getSEnd() < 0)) {
							invalidities.add("The sEnd value '" + textRel.getSEnd() + "' of " + STextualRelation.class.getSimpleName() + " '" + rel.getSId() + "' is not in range of target text. It's length is '" + textRel.getSTextualDS().getSText().length() + "'.");
						}
					}
				}
			}
			if (invalidities.size() != 0) {
				logger.info("The Salt model is not valid. The following invalidities have been found in document-structure '" + getSDocument().getSId() + "':");
				for (String invalidity : invalidities) {
					logger.info(invalidity);
				}
			}
			return (DOCUMENT_STATUS.COMPLETED);
		}
	}

}

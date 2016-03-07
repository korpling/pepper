package ${package};

import java.util.List;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.PepperConfiguration;
import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleNotReadyException;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.graph.Identifier;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

/**
 * This class is a dummy implementation of a {@link PepperExporter} to show how
 * an exporter works in general. This implementation can be used as a template
 * for an own module. Therefore adapt the TODO's. <br/>
 * This dummy implementation just exports the corpus-structure and
 * document-structure to dot formatted files. The dot format is a mechanism to
 * store graph based data for visualizing them. With the tool GraphViz, such a
 * graph could be converted to a png, svg ... file. For more information about
 * dot and GraphViz, see: http://www.graphviz.org/.
 * 
 * If this is the first time, you are implementing a Pepper module, we strongly
 * recommend, to take a look into the 'Developer's Guide for Pepper modules',
 * you will find on
 * <a href="http://corpus-tools.org/pepper/">http://corpus-tools.org/pepper</a>.
 * 
 * @author ${your_name}
 */
@Component(name = "${artifactId}ExporterComponent", factory = "PepperExporterComponentFactory")
public class ${artifactId}Exporter extends PepperExporterImpl implements PepperExporter {
	// =================================================== mandatory
	// ===================================================
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * 
	 * A constructor for your module. Set the coordinates, with which your
	 * module shall be registered. The coordinates (modules name, version and
	 * supported formats) are a kind of a fingerprint, which should make your
	 * module unique.
	 */
	public ${artifactId}Exporter() {
		super();
		setName("${artifactId}Exporter");
		// TODO change suppliers e-mail address
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		// TODO change suppliers homepage
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		// TODO add a description of what your module is supposed to do
		setDesc("The exporter exports the corpus into a format named DOT (see: http://www.graphviz.org/), which can be used to visualize the corpus. ");
		// TODO change "dot" with format name and 1.0 with format version to
		// support
		addSupportedFormat("dot", "1.0", null);
		// TODO change file ending, here it is set to 'dot' to create dot files
		setDocumentEnding("dot");
		// TODO change if necessary, this means, that the method
		// exportCorpusStructure will create a file-structure corresponding to
		// the given corpus-structure. One folder per SCorpus object
		setExportMode(EXPORT_MODE.DOCUMENTS_IN_FILES);
	}

	/**
	 * This method creates a {@link PepperMapper}. <br/>
	 * In this dummy implementation an instance of {@link SampleMapper} is
	 * created and its location to where the document-structure should be
	 * exported to is set.
	 */
	@Override
	public PepperMapper createPepperMapper(Identifier Identifier) {
		PepperMapper mapper = new SampleMapper();
		mapper.setResourceURI(getIdentifier2ResourceTable().get(Identifier));
		return (mapper);
	}

	public static class SampleMapper extends PepperMapperImpl {
		/**
		 * Stores each document-structure to location given by
		 * {@link #getResourceURI()}.
		 */
		@Override
		public DOCUMENT_STATUS mapSDocument() {
			// workaround to deal with a bug in Salt
			SCorpusGraph sCorpusGraph = getDocument().getGraph();

			SaltUtil.save_DOT(getDocument(), getResourceURI());

			// workaround to deal with a bug in Salt
			if (getDocument().getGraph() == null) {
				getDocument().setGraph(sCorpusGraph);
			}

			addProgress(1.0);
			return (DOCUMENT_STATUS.COMPLETED);
		}

		/**
		 * Storing the corpus-structure once
		 */
		@Override
		public DOCUMENT_STATUS mapSCorpus() {
			List<SNode> roots = getCorpus().getGraph().getRoots();
			if ((roots != null) && (!roots.isEmpty())) {
				if (getCorpus().equals(roots.get(0))) {
					SaltUtil.save_DOT(getCorpus().getGraph(), getResourceURI());
				}
			}

			return (DOCUMENT_STATUS.COMPLETED);
		}
	}

	// =================================================== optional
	// ===================================================
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * 
	 * This method is called by the pepper framework after initializing this
	 * object and directly before start processing. Initializing means setting
	 * properties {@link PepperModuleProperties}, setting temporary files,
	 * resources etc. . returns false or throws an exception in case of
	 * {@link PepperModule} instance is not ready for any reason.
	 * 
	 * @return false, {@link PepperModule} instance is not ready for any reason,
	 *         true, else.
	 */
	@Override
	public boolean isReadyToStart() throws PepperModuleNotReadyException {
		// TODO make some initializations if necessary
		return (super.isReadyToStart());
	}
}

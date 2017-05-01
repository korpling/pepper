package ${package};

import java.util.List;
import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.core.SelfTestDesc;
import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleNotReadyException;
import org.corpus_tools.salt.common.SaltProject;
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
 */
//@formatter:off
@Component(name = "${class_prefix}ExporterComponent", factory = "PepperExporterComponentFactory")
//@formatter:on
public class ${class_prefix}Exporter extends PepperExporterImpl implements PepperExporter {
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
	public ${class_prefix}Exporter() {
		super();
		setName("${class_prefix}Exporter");
		// TODO change suppliers e-mail address
		setSupplierContact(URI.createURI("${your_name}@${organisation}"));
		// TODO change suppliers homepage
		setSupplierHomepage(URI.createURI("${organisation}"));
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
	 * In this dummy implementation an instance of {@link ${class_prefix}Mapper} is
	 * created and its location to where the document-structure should be
	 * exported to is set.
	 */
	@Override
	public PepperMapper createPepperMapper(Identifier Identifier) {
		PepperMapper mapper = new ${class_prefix}Mapper();
		mapper.setResourceURI(getIdentifier2ResourceTable().get(Identifier));
		return mapper;
	}

	public static class ${class_prefix}Mapper extends PepperMapperImpl {
		/**
		 * Stores each document-structure to location given by
		 * {@link #getResourceURI()}.
		 */
		@Override
		public DOCUMENT_STATUS mapSDocument() {
			// workaround to deal with a bug in Salt
//			SCorpusGraph sCorpusGraph = getDocument().getGraph();

			SaltUtil.save_DOT(getDocument(), getResourceURI());

//			// workaround to deal with a bug in Salt
//			if (getDocument().getGraph() == null) {
//				getDocument().setGraph(sCorpusGraph);
//			}

			addProgress(1.0);
			return DOCUMENT_STATUS.COMPLETED;
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
			return DOCUMENT_STATUS.COMPLETED;
		}
	}
	
	/**
	 * This method is called by the Pepper framework to run an integration test
	 * for module. When the method returns null, it means that no integration
	 * test is supported. Otherwise, the {@link SelfTestDesc} object needs to
	 * provide an input corpus path and an output corpus path.
	 * 
	 * When this module is:
	 * <ul>
	 * <li>an importer: {@link SelfTestDesc#getInputCorpusPath()} should contain
	 * the format to be imported. {@link SelfTestDesc#getExpectedCorpusPath()}
	 * should contain the expected salt project (for control).</li>
	 * <li>a manipulator: {@link SelfTestDesc#getInputCorpusPath()} should
	 * contain a salt project which is the module's input.
	 * {@link SelfTestDesc#getExpectedCorpusPath()} should contain the expected
	 * salt project (for control).</li>
	 * <li>an exporter: {@link SelfTestDesc#getInputCorpusPath()} should contain
	 * a salt project which is the module's input.
	 * {@link SelfTestDesc#getExpectedCorpusPath()} should contain the expected
	 * corpus in output format.</li>
	 * </ul>
	 * 
	 * When this module is an importer or a manipulator the method
	 * {@link SelfTestDesc#compare(SaltProject, SaltProject)} is called to
	 * compare output salt project with expected salt project. When the module
	 * is an exporter the method {@link SelfTestDesc#compare(URI, URI)} is
	 * called to compare the created output folder with an expected one. By
	 * default this method checks whether the file structure and each file is
	 * equal.
	 * 
	 * @return test description
	 */
	@Override
	public SelfTestDesc getSelfTestDesc() {
		final URI base = getResources().appendSegment("selfTests")
				.appendSegment("${class_prefix}Exporter");
		final URI in = base.appendSegment("in");
		final URI expected = base.appendSegment("expected");
		return SelfTestDesc.create().withInputCorpusPath(in).withExpectedCorpusPath(expected).build();
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
		return super.isReadyToStart();
	}
}

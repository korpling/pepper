package ${package};

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.core.SelfTestDesc;
import org.corpus_tools.pepper.impl.PepperManipulatorImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperManipulator;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleNotReadyException;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.STextualDS;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.core.GraphTraverseHandler;
import org.corpus_tools.salt.core.SAnnotation;
import org.corpus_tools.salt.core.SGraph.GRAPH_TRAVERSE_TYPE;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.core.SRelation;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

/**
 * This is a dummy implementation to show how a {@link PepperManipulator} works.
 * Therefore it just prints out some information about a corpus like the number
 * of nodes, edges and for instance annotation frequencies. <br/>
 * This class can be used as a template for an own implementation of a
 * {@link PepperManipulator} Take a look at the TODO's and adapt the code.
 * If this is the first time, you are implementing a Pepper module, we strongly
 * recommend, to take a look into the 'Developer's Guide for Pepper modules',
 * you will find on
 * <a href="http://corpus-tools.org/pepper/">http://corpus-tools.org/pepper</a>.
 */
@Component(name = "${class_prefix}ManipulatorComponent", factory = "PepperManipulatorComponentFactory")
public class ${class_prefix}Manipulator extends PepperManipulatorImpl {
	// =================================================== mandatory
	// ===================================================
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong> <br/>
	 * A constructor for your module. Set the coordinates, with which your
	 * module shall be registered. The coordinates (modules name, version and
	 * supported formats) are a kind of a fingerprint, which should make your
	 * module unique.
	 */
	public ${class_prefix}Manipulator() {
		super();
		setName("${class_prefix}Manipulator");
		// TODO change suppliers e-mail address
		setSupplierContact(URI.createURI("${your_name}@${organisation}"));
		// TODO change suppliers homepage
		setSupplierHomepage(URI.createURI("${organisation}"));
		// TODO add a description of what your module is supposed to do
		setDesc("The manipulator, traverses over the document-structure and prints out some information about it, like the frequencies of annotations, the number of nodes and edges and so on. ");
	}

	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong> <br/>
	 * This method creates a customized {@link PepperMapper} object and returns
	 * it. You can here do some additional initialisations. Thinks like setting
	 * the {@link Identifier} of the {@link SDocument} or {@link SCorpus} object
	 * and the {@link URI} resource is done by the framework (or more in detail
	 * in method {@link #start()}). The parameter <code>Identifier</code>, if a
	 * {@link PepperMapper} object should be created in case of the object to
	 * map is either an {@link SDocument} object or an {@link SCorpus} object of
	 * the mapper should be initialized differently. <br/>
	 * 
	 * @param Identifier
	 *            {@link Identifier} of the {@link SCorpus} or {@link SDocument}
	 *            to be processed.
	 * @return {@link PepperMapper} object to do the mapping task for object
	 *         connected to given {@link Identifier}
	 */
	public PepperMapper createPepperMapper(Identifier Identifier) {
		${class_prefix}Mapper mapper = new ${class_prefix}Mapper();
		return mapper;
	}

	/**
	 * This class is a dummy implementation for a mapper, to show how it works.
	 * Pepper or more specific this dummy implementation of a Pepper module
	 * creates one mapper object per {@link SDocument} object and
	 * {@link SCorpus} object each. This ensures, that each of those objects is
	 * run independently from another and runs parallelized. <br/>
	 * The method {@link #mapSCorpus()} is supposed to handle all
	 * {@link SCorpus} object and the method {@link #mapSDocument()} is supposed
	 * to handle all {@link SDocument} objects. <br/>
	 * In our dummy implementation, we just print out some information about a
	 * corpus to system.out. This is not very useful, but might be a good
	 * starting point to explain how access the several objects in Salt model.
	 */
	public static class ${class_prefix}Mapper extends PepperMapperImpl implements GraphTraverseHandler {
		/**
		 * Creates meta annotations, if not already exists
		 */
		@Override
		public DOCUMENT_STATUS mapSCorpus() {
			if (getCorpus().getMetaAnnotation("date") == null) {
				getCorpus().createMetaAnnotation(null, "date", "1989-12-17");
			}
			return DOCUMENT_STATUS.COMPLETED;
		}

		/**
		 * prints out some information about document-structure
		 */
		@Override
		public DOCUMENT_STATUS mapSDocument() {
			// create a StringBuilder, to be filled with informations (we need
			// to intermediately store the results, because of parallelism of
			// modules)
			String format = "|%-15s: %15s |%n";
			StringBuilder out = new StringBuilder();
			out.append("\n");
			// print out the id of the document
			out.append(getDocument().getId());
			out.append("\n");
			out.append("+---------------------------------+\n");
			// print out the general number of nodes
			out.append(String.format(format, "nodes", getDocument().getDocumentGraph().getNodes().size()));
			addProgress((double) (1 / 7));
			// print out the general number of relations
			out.append(String.format(format, "relations", getDocument().getDocumentGraph().getRelations().size()));
			addProgress((double) (1 / 7));
			// print out the general number of primary texts
			out.append(String.format(format, "texts", getDocument().getDocumentGraph().getTextualDSs().size()));
			addProgress((double) (1 / 7));
			// print out the general number of tokens
			out.append(String.format(format, "tokens", getDocument().getDocumentGraph().getTokens().size()));
			addProgress((double) (1 / 7));
			// print out the general number of spans
			out.append(String.format(format, "spans", getDocument().getDocumentGraph().getSpans().size()));
			addProgress((double) (1 / 7));
			// print out the general number of structures
			out.append(String.format(format, "structures", getDocument().getDocumentGraph().getStructures().size()));
			addProgress((double) (1 / 7));

			// create alist of all root nodes of the current document-structure
			List<SNode> roots = getDocument().getDocumentGraph().getRoots();
			// traverse the document-structure beginning at the roots in
			// depth-first order top down. The id '${class_prefix}Traversal' is used for
			// uniqueness, in case of one class uses multiple traversals. This
			// object then takes the call-backs implemented with methods
			// checkConstraint, nodeReached and nodeLeft
			getDocument().getDocumentGraph().traverse(roots, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "${class_prefix}Traversal", this);

			// print out computed frequencies
			for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
				out.append(String.format(format, entry.getKey(), entry.getValue()));
			}
			addProgress((double) (1 / 7));
			out.append("+---------------------------------+\n");
			System.out.println(out.toString());

			return DOCUMENT_STATUS.COMPLETED;
		}

		/** A map storing frequencies of annotations of processed documents. */
		private Map<String, Integer> frequencies = new Hashtable<String, Integer>();

		/**
		 * This method is called for each node in document-structure, as long as
		 * {@link #checkConstraint(GRAPH_TRAVERSE_TYPE, String, SRelation, SNode, long)}
		 * returns true for this node. <br/>
		 * In our dummy implementation it just collects frequencies of
		 * annotations.
		 */
		@Override
		public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SNode currNode, SRelation<?,?> relation, SNode fromNode, long order) {
			if (currNode.getAnnotations().size() != 0) {
				// step through all annotations to collect them in frequencies
				// table
				for (SAnnotation annotation : currNode.getAnnotations()) {
					Integer frequence = frequencies.get(annotation.getName());
					// if annotation hasn't been seen yet, create entry in
					// frequencies set frequency to 0
					if (frequence == null) {
						frequence = 0;
					}
					frequence++;
					frequencies.put(annotation.getName(), frequence);
				}
			}
		}

		/**
		 * This method is called on the way back, in depth first mode it is
		 * called for a node after all the nodes belonging to its subtree have
		 * been visited. <br/>
		 * In our dummy implementation, this method is not used.
		 */
		@Override
		public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SNode currNode, SRelation<?,?> relation, SNode fromNode, long order) {
		}

		/**
		 * With this method you can decide if a node is supposed to be visited
		 * by methods
		 * {@link #nodeReached(GRAPH_TRAVERSE_TYPE, String, SNode, SRelation, SNode, long)}
		 * and
		 * {@link #nodeLeft(GRAPH_TRAVERSE_TYPE, String, SNode, SRelation, SNode, long)}
		 * . In our dummy implementation for instance we do not need to visit
		 * the nodes {@link STextualDS}.
		 */
		@Override
		public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SRelation<?,?> relation, SNode currNode, long order) {
			if (currNode instanceof STextualDS) {
				return false;
			} else {
				return true;
			}
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
				.appendSegment("${class_prefix}Manipulator");
		final URI in = base.appendSegment("in");
		final URI expected = base.appendSegment("expected");
		return SelfTestDesc.create().withInputCorpusPath(in).withExpectedCorpusPath(expected).build();
	}
	
	// =================================================== optional
	// ===================================================
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong> <br/>
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

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
package org.corpus_tools.pepper.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.core.SAnnotation;
import org.corpus_tools.salt.core.SAnnotationContainer;
import org.corpus_tools.salt.core.SLayer;
import org.corpus_tools.salt.core.SMetaAnnotation;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.core.SRelation;
import org.corpus_tools.salt.graph.IdentifiableElement;
import org.corpus_tools.salt.graph.Identifier;
import org.corpus_tools.salt.graph.Label;
import org.corpus_tools.salt.graph.Relation;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class is a helper class to perform actions before or after a mapping was
 * done. A mapping could be the mapping of a single corpus or document or even
 * the mapping of an entire corpus structure.
 * </p>
 * <p>
 * This could be helpful, for instance to make some processing after the mapping
 * e.g. adding all created nodes and relations to a layer. To trigger an action
 * for a specific Pepper module a set of customization properties is available.
 * Customization properties triggering a post processing starts with
 * {@value PepperModuleProperties#PREFIX_PEPPER_AFTER}. Such an action could be
 * the enhancement of a corpus with metadata (following the property file
 * syntax) to enhance corpora in formats, which do not support metadata, see
 * {@link #readMeta(Identifier)}. Or the printing of a corpus structure, see
 * {@link #reportCorpusStructure(SNode, String, boolean)}.
 * </p>
 * <p>
 * Objects of this class are used by
 * <ol>
 * <li>{@link PepperModuleImpl} to perform actions before or after a single
 * document or corpus was processed and</li>
 * <li>used by {@link ModuleControllerImpl} to perform actions before or after
 * the entire corpus graph was processed.</li>
 * </ol>
 * </p>
 * <p>
 * This is an overview on the possible actions:
 * <ul>
 * <li>single corpus or document
 * <ul>
 * <li>before
 * <ul>
 * <li>{@link #addSLayers(SDocument, String)}</li>
 * <li>{@link #readMeta(Identifier)}</li>
 * </ul>
 * </li>
 * <li>after
 * <ul>
 * <li>{@link #addSLayers(SDocument, String)}</li>
 * </ul>
 * </li>
 * </ul>
 * </li>
 * <li>entire document structure
 * <ul>
 * <li>before
 * <ul>
 * <li>{@link #reportCorpusStructure(SNode, String, boolean)}</li>
 * </ul>
 * </li>
 * <li>after
 * <ul>
 * <li>{@link #copyResources(String)}</li>
 * </ul>
 * </li>
 * </ul>
 * </li>
 * </ul>
 * </p>
 * 
 * @author florian
 * @since 3.0.0
 *
 */
public class BeforeAfterAction {

	/**
	 * Initializes this object and in case an action should be performed, it
	 * sets the internal {@link PepperModule} which does the action, and the
	 * internal {@link SCorpusGraph} on which the action should be performed.
	 **/
	public BeforeAfterAction(PepperModule pepperModule) {
		this.pepperModule = pepperModule;
		logger = LoggerFactory.getLogger(getPepperModule().getName());
	}

	private PepperModule pepperModule;

	public PepperModule getPepperModule() {
		return pepperModule;
	}

	private Logger logger = LoggerFactory.getLogger("Pepper");

	/**
	 * Invokes an actions, after the mapping of an entire corpus structure was
	 * done. Customization properties triggering a pre processing starts with
	 * {@value PepperModuleProperties#PREFIX_PEPPER_AFTER}. This method is
	 * called before invocation of {@link PepperModule#start()}.
	 * 
	 * @throws PepperModuleException
	 */
	public void before(SCorpusGraph corpusGraph) throws PepperModuleException {
		if (getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_REPORT_CORPUSGRAPH) != null) {
			boolean isReport = Boolean.parseBoolean(getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_REPORT_CORPUSGRAPH).getValue().toString());
			if (isReport && corpusGraph != null) {
				List<SNode> roots = corpusGraph.getRoots();
				if (roots != null) {
					StringBuilder str = new StringBuilder();
					str.append("corpus structure imported by ");
					str.append(getPepperModule().getName());
					for (SNode root : roots) {
						str.append(":\n");
						str.append(getPepperModule().getSaltProject().getCorpusGraphs().indexOf(((SCorpus) root).getGraph()));
						str.append("\n");
						str.append(reportCorpusStructure(corpusGraph, root, "", true));
					}
					logger.info(str.toString());
				}
			}
		}
	}

	/**
	 * Invokes an actions, after the mapping of an entire corpus structure was
	 * done. Customization properties triggering a post processing starts with
	 * {@value PepperModuleProperties#PREFIX_PEPPER_AFTER}. This method is
	 * called after invocation of {@link {@link PepperModule#start()} .
	 * 
	 * @throws PepperModuleException
	 */
	public void after(SCorpusGraph corpusGraph) throws PepperModuleException {
		if (getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_COPY_RES) != null) {
			// copies resources as files from source to target

			String resString = (String) getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_COPY_RES).getValue();
			copyResources(resString);
		}
	}

	/**
	 * Returns the corpus structure as an ascii tree.
	 * 
	 * @param corpusGraph
	 *            the corpus structure to be printed
	 * @param node
	 *            root node to start from
	 * @param prefix
	 * @param isTail
	 * @return
	 */
	protected String reportCorpusStructure(SCorpusGraph corpusGraph, SNode node, String prefix, boolean isTail) {
		StringBuilder retStr = new StringBuilder();
		retStr.append(prefix);
		retStr.append(((isTail ? "└── " : "├── ") + node.getName()));
		retStr.append("\n");
		List<SRelation<SNode, SNode>> outRelations = corpusGraph.getOutRelations(node.getId());
		int i = 0;
		for (Relation out : outRelations) {
			if (i < outRelations.size() - 1) {
				retStr.append(prefix);
				retStr.append(reportCorpusStructure(corpusGraph, (SNode) out.getTarget(), prefix + (isTail ? "    " : "│   "), false));
			} else {
				retStr.append(reportCorpusStructure(corpusGraph, (SNode) out.getTarget(), prefix + (isTail ? "    " : "│   "), true));
			}
			i++;
		}
		return (retStr.toString());
	}

	/**
	 * Reads customization property
	 * {@link PepperModuleProperties#PROP_AFTER_COPY_RES} and copies the listed
	 * resources to the named target folder.
	 */
	public void copyResources(String resString) {
		if ((resString != null) && (!resString.isEmpty())) {
			String[] resources = resString.split(";");
			if (resources.length > 0) {
				for (String resource : resources) {
					resource = resource.trim();
					String[] parts = resource.split("->");
					if (parts.length == 2) {
						String sourceStr = parts[0];
						String targetStr = parts[1];
						sourceStr = sourceStr.trim();
						targetStr = targetStr.trim();

						// check if source and target is given
						boolean copyOk = true;
						if ((sourceStr == null) || (sourceStr.isEmpty())) {
							logger.warn("Cannot copy resources for '" + getPepperModule().getName() + "' because no source file was given in property value '" + resource + "'. ");
							copyOk = false;
						}
						if ((targetStr == null) || (targetStr.isEmpty())) {
							logger.warn("Cannot copy resources for '" + getPepperModule().getName() + "' because no target file was given in property value '" + resource + "'. ");
							copyOk = false;
						}
						if (copyOk) {
							File source = new File(sourceStr);
							File target = new File(targetStr);

							// in case of source or target aren't absolute
							// resolve them against current Job's base directory
							String baseDir = getPepperModule().getModuleController().getJob().getBaseDir().toFileString();
							if (!baseDir.endsWith("/")) {
								baseDir = baseDir + "/";
							}
							if (!source.isAbsolute()) {
								source = new File(baseDir + sourceStr);
							}
							if (!source.exists()) {
								logger.warn("Cannot copy resources for '" + getPepperModule().getName() + "' because source does not exist '" + source.getAbsolutePath() + "'. Check the property value '" + resource + "'. ");
							} else {
								// only copy if source exists

								if (!target.isAbsolute()) {
									target = new File(baseDir + targetStr);
								}
								if (!target.exists()) {
									if (!target.mkdirs()) {
										logger.warn("Cannot create folder {}. ", target);
									}
								}
								try {
									if (source.isDirectory()) {
										targetStr = target.getAbsolutePath();
										if (!targetStr.endsWith("/")) {
											targetStr = targetStr + "/";
										}
										target = new File(targetStr + source.getName());
										FileUtils.copyDirectory(source, target);
										logger.trace("Copied resource from '" + source.getAbsolutePath() + "' to '" + target.getAbsolutePath() + "'.");
									} else {
										targetStr = target.getCanonicalPath();
										if (!targetStr.endsWith("/")) {
											targetStr = targetStr + "/";
										}
										target = new File(targetStr + source.getName());
										FileUtils.copyFile(source, target);
										logger.trace("Copied resource from '" + source.getAbsolutePath() + "' to '" + target.getAbsolutePath() + "'.");
									}
								} catch (IOException e) {
									logger.warn("Cannot copy resources for '" + getPepperModule().getName() + "' because of '" + e.getMessage() + "'. Check the property value '" + resource + "'. ");
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Invokes actions, before the mapping of a corpus or document was started.
	 * This could be helpful, for instance to make some preparations for the
	 * mapping. To trigger this pre processing for a specific Pepper module a
	 * set of customization properties is available. Customization properties
	 * triggering a pre processing starts with
	 * {@value PepperModuleProperties#PREFIX_PEPPER_BEFORE}. This method is
	 * called by the method {@link #map()}, before
	 * {@link PepperMapper#mapSDocument()} was called.
	 * 
	 * @param id
	 *            id of either {@link SDocument} or {@link SCorpus} object to be
	 *            prepared
	 * @throws PepperModuleException
	 */
	public void before(Identifier id) throws PepperModuleException {
		if (getPepperModule().getProperties() != null) {
			if (getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_BEFORE_ADD_SLAYER) != null) {
				// add slayers after processing

				if ((id != null) && (id.getIdentifiableElement() != null)) {
					if (id.getIdentifiableElement() instanceof SDocument) {
						SDocument sDoc = (SDocument) id.getIdentifiableElement();

						// add layers
						String layers = (String) getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_BEFORE_ADD_SLAYER).getValue();
						addSLayers(sDoc, layers);
					} else if (id.getIdentifiableElement() instanceof SCorpus) {

					}
				}
			}
			if ((getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_BEFORE_READ_META) != null) && (getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_BEFORE_READ_META).getValue() != null)) {
				// read meta data
				readMeta(id);
			}
		}
	}

	/**
	 * Invokes actions, after the mapping of a corpus or document is done. This
	 * could be helpful, for instance to make some processing after the mapping
	 * e.g. adding all created nodes and relations to a layer. To trigger this
	 * post processing for a specific Pepper module a set of customization
	 * properties is available. Customization properties triggering a post
	 * processing starts with
	 * {@value PepperModuleProperties#PREFIX_PEPPER_AFTER}. This method is
	 * called by the method {@link #map()}, after
	 * {@link PepperMapper#mapSDocument()} was called.
	 * 
	 * @param id
	 *            id of either {@link SDocument} or {@link SCorpus} object to be
	 *            post processed
	 * @throws PepperModuleException
	 */
	public void after(Identifier id) throws PepperModuleException {
		if (getPepperModule().getProperties() != null) {
			if ((id != null) && (id.getIdentifiableElement() != null)) {
				if (getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_ADD_SLAYER) != null) {
					if (id.getIdentifiableElement() instanceof SDocument) {
						SDocument sDoc = (SDocument) id.getIdentifiableElement();
						// add slayers after processing
						String layers = (String) getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_ADD_SLAYER).getValue();
						addSLayers(sDoc, layers);
					}
				}
				if (getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_RENAME_ANNOTATIONS).getValue() != null) {
					if (id.getIdentifiableElement() instanceof SDocument && ((SDocument) id.getIdentifiableElement()).getDocumentGraph() != null) {
						renameAnnotations(id);
					}
				}
			}
		}
	}

	// ****************************************************************************************
	// *** functions for before() and after()

	/**
	 * Adds the passed layer to all nodes and objects in the passed
	 * {@link SDocument}.
	 * 
	 * @param sDoc
	 * @param layers
	 */
	public void addSLayers(SDocument sDoc, String layers) {
		if ((layers != null) && (!layers.isEmpty())) {
			String[] layerArray = layers.split(";");
			if (layerArray.length > 0) {
				for (String layer : layerArray) {
					layer = layer.trim();
					// create SLayer and add to document-structure
					List<SLayer> sLayers = sDoc.getDocumentGraph().getLayerByName(layer);
					SLayer sLayer = null;
					if ((sLayers != null) && (sLayers.size() > 0)) {
						sLayer = sLayers.get(0);
					}
					if (sLayer == null) {
						sLayer = SaltFactory.createSLayer();
						sLayer.setName(layer);
						sDoc.getDocumentGraph().addLayer(sLayer);
					}
					// add all nodes to new layer
					for (SNode sNode : sDoc.getDocumentGraph().getNodes()) {
						sNode.addLayer(sLayer);
					}
					// add all relations to new layer
					for (SRelation sRel : sDoc.getDocumentGraph().getRelations()) {
						sRel.addLayer(sLayer);
					}
				}
			}
		}
	}

	/**
	 * Loads meta data form a meta data file and adds them to the object
	 * corresponding to the passed {@link Identifier}. The meta data file is
	 * localized in the directory in case of the URI corresponding to passed
	 * {@link Identifier} is a directory or (in case the corresponding URI
	 * addresses a file) in the same directory as the resource corresponding to
	 * the passed {@link Identifier}. The meta data file must have the ending
	 * passed in {@link PepperModuleProperties#PROP_BEFORE_READ_META}.
	 * 
	 * @param id
	 *            identifying the current object
	 */
	public void readMeta(Identifier id) {
		if (getPepperModule() instanceof PepperImporter) {
			URI resourceURI = ((PepperImporter) getPepperModule()).getIdentifier2ResourceTable().get(id);
			Object endingObj = getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_BEFORE_READ_META).getValue();
			if (endingObj != null) {
				String ending = endingObj.toString().trim();
				if (resourceURI != null) {
					File resource = new File(resourceURI.toFileString());
					File metaFile = null;
					if (resource.isDirectory()) {
						// resource is directory, search for meta data file
						// (all files having customized ending)
						File[] files = resource.listFiles();
						if (files != null) {
							for (File file : files) {
								if (file.getName().equalsIgnoreCase(((SNode) id.getIdentifiableElement()).getPath().lastSegment() + "." + ending)) {
									metaFile = file;
									break;
								}
							}
						}
					} else {
						// resource is a file, search for meta data file
						// (file having the same name as current corpus or
						// document and having customized ending)

						String[] parts = resource.getName().split("[.]");
						if (parts != null) {
							metaFile = new File(resource.getAbsolutePath().substring(0, resource.getAbsolutePath().lastIndexOf(".")) + "." + ending);
							if (!metaFile.exists()) {
								metaFile = null;
							}
						}
					}
					if (metaFile != null) {
						Properties props = new Properties();
						try (FileInputStream str = new FileInputStream(metaFile)) {
							props.load(str);
						} catch (IOException e) {
							logger.warn("Tried to load meta data file '" + metaFile.getAbsolutePath() + "', but a problem occured: " + e.getMessage() + ". ", e);
						}
						for (Object key : props.keySet()) {
							IdentifiableElement container = id.getIdentifiableElement();
							if ((container != null) && (container instanceof SAnnotationContainer)) {
								if (!((SAnnotationContainer) container).containsLabel(key.toString())) {
									((SAnnotationContainer) container).createMetaAnnotation(null, key.toString(), props.getProperty(key.toString()));
								} else {
									logger.warn("Cannot add meta annotation '" + key.toString() + "', because it already exist on object '" + id.getId() + "' please check file '" + metaFile.getAbsolutePath() + "'. ");
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Renames all annotations matching the search template to the new
	 * namespace, name or value. To rename an annotation, use the following
	 * syntax:
	 * "old_namespace::old_name=old_value := new_namespace::new_name=new_value",
	 * determining the name is mandatory whereas the namespace and value are
	 * optional. For instance a pos annotation can be renamed as follows:
	 * "salt::pos:=part-of-speech". A list of renamings must be separated with
	 * ";".
	 * 
	 * @param id
	 *            identifying the current object
	 */
	public void renameAnnotations(Identifier id) {
		if (id != null && id.getIdentifiableElement() != null) {
			try {
				String str = (String) getPepperModule().getProperties().getProperty(PepperModuleProperties.PROP_AFTER_RENAME_ANNOTATIONS).getValue();
				Map<String[], String[]> renamingMap = new Hashtable<>();
				// split all single renaming strings
				String[] renamings = str.split(";");
				for (String renaming : renamings) {
					String[] parts = renaming.split(":=");
					renamingMap.put(SaltUtil.unmarshalAnnotation(parts[0]).iterator().next(), SaltUtil.unmarshalAnnotation(parts[1]).iterator().next());
				}
				SDocument document = (SDocument) id.getIdentifiableElement();

				// rename all annotations of nodes
				Iterator<SAnnotationContainer> it = (Iterator<SAnnotationContainer>) (Iterator<? extends SAnnotationContainer>) document.getDocumentGraph().getNodes().iterator();
				rename(it, renamingMap);

				// rename all annotations of relations
				it = (Iterator<SAnnotationContainer>) (Iterator<? extends SAnnotationContainer>) document.getDocumentGraph().getRelations().iterator();
				rename(it, renamingMap);
			} catch (RuntimeException e) {
				logger.warn("Cannot rename labels in object '{}', because of a nested exeption '{}'. ", id, e.getMessage());
			}
		}
	}

	private void rename(Iterator<SAnnotationContainer> it, Map<String[], String[]> renamingMap) {
		while (it.hasNext()) {
			SAnnotationContainer node = it.next();
			for (Map.Entry<String[], String[]> entry : renamingMap.entrySet()) {
				Label label = node.getLabel(entry.getKey()[0], entry.getKey()[1]);
				if (label != null) {
					if (label.getQName().equals(SaltUtil.createQName(entry.getValue()[0], entry.getValue()[1]))) {
						// if only value is different
						label.setValue(entry.getValue()[2]);
					} else {
						// namespace or name are different --> remove label and
						// create a new one
						node.removeLabel(label.getQName());
						if (label instanceof SAnnotation) {
							if (entry.getValue()[2] == null) {
								//copy annotation value
								node.createAnnotation(entry.getValue()[0], entry.getValue()[1], label.getValue());
							} else {
								//use new annotation value
								node.createAnnotation(entry.getValue()[0], entry.getValue()[1], entry.getValue()[2]);
							}
						} else if (label instanceof SMetaAnnotation) {
							if (entry.getValue()[2] == null) {
								//copy annotation value
								node.createMetaAnnotation(entry.getValue()[0], entry.getValue()[1], label.getValue());
							} else {
								//use new annotation value
								node.createMetaAnnotation(entry.getValue()[0], entry.getValue()[1], entry.getValue()[2]);
							}
						}
					}
				}
			}
		}
	}

	// *** functions for before() and after()
	// ****************************************************************************************
}

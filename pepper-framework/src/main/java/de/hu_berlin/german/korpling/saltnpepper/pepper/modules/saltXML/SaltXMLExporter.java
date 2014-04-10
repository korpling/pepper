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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.saltXML;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * This is a PepperExporter which exports a salt model to the SaltXML format.
 * This module stores every document in a sepreate file. These files then contains the document structure.
 * The corpus structure is stored in a single file called saltProject + SALT_FILE_ENDING. The value
 * SALT_FILE_ENDING can be gettet by method getSaltFileEnding().
 * <br/>
 * <br/>
 * When method start() is called, the saltProject will be attached to a resource with the uri 
 * "this.getCorpusDefinition().getCorpusPath().toFileString() +"/"+ "saltProject"+ SALT_ENDING". 
 * Before it can be storeed, all documents have to be processed. 
 * <br/>
 * The module now waits for documents which can be exported. When a document finished all 
 * previous modules, it can be exported. This means, that 1) every document will also get a resource
 * with the uri "this.getCorpusDefinition().getCorpusPath().toFileString() + "/" sElementId.getSElementPath()+ SALT_FILE_ENDING".
 * And 2) the document structure will be stored to file.
 * <br/>
 * After all was done, the saltProject will be exported.
 *    
 * @author Florian Zipser
 * @version 1.0
 *
 */
@Component(name="SaltXMLExporterComponent", factory="PepperExporterComponentFactory")
public class SaltXMLExporter extends PepperExporterImpl implements PepperExporter
{
	public SaltXMLExporter()
	{
		super();
		
		//setting name of module
		setName("SaltXMLExporter");
		
		//set list of formats supported by this module
		this.addSupportedFormat("SaltXML", "1.0", null);
	}
	
	@Activate
	public void activate(ComponentContext componentContext)
	{
		super.activate(componentContext);
	}
	
	/**
	 * The resource set for all resources.
	 */
	private ResourceSet resourceSet= null;
	
	/**
	 * Creates {@link ResourceSet} if not exists and returns it.
	 * @return
	 */
	private ResourceSet getResourceSet()
	{
		if (resourceSet== null)
		{
			synchronized (this)
			{
				if (resourceSet== null)
				{
					// Register XML resource factory
					resourceSet = new ResourceSetImpl();
					resourceSet.getPackageRegistry().put(SaltCommonPackage.eINSTANCE.getNsURI(), SaltCommonPackage.eINSTANCE);
					resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(SaltFactory.FILE_ENDING_SALT, new XMIResourceFactoryImpl());
				}
			}
		}
		return(resourceSet);
	}
	
	/**
	 * Returns the ending of the format saltXML.
	 * @return
	 */
	public String getSaltFileEnding()
	{
		return(SaltFactory.FILE_ENDING_SALT);
	}
	
	/**
	 * Stores the resource for salt project
	 */
	private XMLResource saltProjectResource= null;
	
	/**
	 * Creates a {@link XMLResource} for the {@link SaltProject} object to be stored in. This is neceassary, to persist all {@link SCorpusGraph}
	 * and {@link SDocumentGraph} objects, because they cannot be stored if the containing object is not covered by a {@link Resource} 
	 * (this is a constraint given by the EMF).  
	 * them to the resource.
	 */
	private void createSaltProjectResource()
	{
		if (this.getSaltProject()== null)
			throw new PepperModuleException("Cannot export the SaltProject, because the saltProject is null.");
		if (this.getCorpusDesc()== null)
			throw new PepperModuleException("Cannot export the SaltProject, because no corpus definition is given for export.");
		if (this.getCorpusDesc().getCorpusPath()== null)
			throw new PepperModuleException("Cannot export the SaltProject, because no corpus path is given for export.");
		
		//create export URI
		URI saltProjectURI= URI.createFileURI(this.getCorpusDesc().getCorpusPath().toFileString() +"/"+ SaltFactory.FILE_SALT_PROJECT);
		
		saltProjectResource= (XMLResource)getResourceSet().createResource(saltProjectURI);
		saltProjectResource.getContents().add(this.getSaltProject());
		saltProjectResource.setEncoding("UTF-8");	
	}
	
	private class SaltXMLExporterMapper extends PepperMapperImpl{
		
		/**
		 * {@inheritDoc PepperMapper#setSDocument(SDocument)}
		 * 
		 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
		 */
		@Override
		public DOCUMENT_STATUS mapSDocument() {
			//creating uri for exporting document
			URI sDocumentURI= URI.createFileURI(getCorpusDesc().getCorpusPath().toFileString() +"/"+ getSDocument().getSElementId().getSElementPath()+"."+ SaltFactory.FILE_ENDING_SALT);
			
			XMLResource sDocumentResource= (XMLResource) getResourceSet().createResource(sDocumentURI);
			sDocumentResource.getContents().add(getSDocument().getSDocumentGraph());
			sDocumentResource.setEncoding("UTF-8");
			try {
				sDocumentResource.save(null);
			} catch (IOException e) {
				throw new PepperModuleException("Cannot export document '"+getSDocument().getSId()+"', nested exception is: ", e);
			}
			return(DOCUMENT_STATUS.COMPLETED);
		}
	}
	
	/**
	 * Creates a mapper of type {@link EXMARaLDA2SaltMapper}.
	 * {@inheritDoc PepperModule#createPepperMapper(SElementId)}
	 */
	@Override
	public PepperMapper createPepperMapper(SElementId sElementId)
	{
		SaltXMLExporterMapper mapper= new SaltXMLExporterMapper();
		return(mapper);
	}
	
	/**
	 * Cretaes a {@link Resource} for the {@link SaltProject} to persist contained objects like {@link SDocumentGraph} etc.
	 * This is caused by an EMF constraint.
	 */
	@Override
	public void start() throws PepperModuleException
	{
		//creating resources for saltProject
		this.createSaltProjectResource();
		super.start();
		
		//exporting corpus structure
			try {
				//ToDo a bugfix for The object 'SDocumentGraphImpl(null, sNodes: 557, sRelations: 1618)' is not contained in a resource.
				saltProjectResource.save(null);
			} catch (IOException e) {
				throw new PepperModuleException("Cannot export saltProject, nested exception is: ", e);
			}
		//exporting corpus structure
	}	
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.WorkflowDescriptionReader;
import de.hu_berlin.german.korpling.saltnpepper.pepper.util.XMLStreamWriter;

public class WorkflowDescriptionWriter {
	
	private WorkflowDescriptionWriter(){		
	}

	public static OutputStream toXML(List<ConversionStepDescriptor> configurations){
		List<ConversionStepDescriptor> steps = getGroupedByType(configurations);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			XMLStreamWriter writer = new XMLStreamWriter(XMLOutputFactory.newFactory().createXMLStreamWriter(outStream));
			writer.setPrettyPrint(true);
			writer.writeStartDocument();
			writer.writeProcessingInstruction("http://korpling.german.hu-berlin.de/saltnpepper/pepper/schema/10/pepper.rnc");
			writer.writeAttribute("type", "application/relax-ng-compact-syntax");
			writer.writeStartElement(WorkflowDescriptionReader.TAG_PEPPER_JOB);			
			String tag = null;
			for (ConversionStepDescriptor d : steps){
				tag = MODULE_TYPE.IMPORTER.equals(d.getModuleType())? WorkflowDescriptionReader.TAG_IMPORTER : (
						MODULE_TYPE.MANIPULATOR.equals(d.getModuleType())? WorkflowDescriptionReader.TAG_MANIPULATOR :
							WorkflowDescriptionReader.TAG_EXPORTER);
				writer.writeStartElement(tag);
				if (!MODULE_TYPE.MANIPULATOR.equals(d.getModuleType())){
					if(d.getPath()==null) {throw new NullPointerException();} //TODO
					writer.writeAttribute(WorkflowDescriptionReader.ATT_PATH, d.getPath());					
				}
				writer.writeAttribute(WorkflowDescriptionReader.ATT_NAME, d.getModuleName());
				if (d.getProperties()!=null && !d.getProperties().isEmpty()){
					writer.writeStartElement(WorkflowDescriptionReader.TAG_CUSTOMIZATION);
					for (String p : d.getProperties().keySet()){
						writer.writeStartElement(WorkflowDescriptionReader.TAG_PROP);
						writer.writeAttribute(WorkflowDescriptionReader.ATT_KEY, p);
						writer.writeCharacters(d.getProperties().get(p));
						writer.writeEndElement();
					}
					writer.writeEndElement();//end of customization
				}
				writer.writeEndElement();//end of tag
			}
			writer.writeEndElement();//end of pepper-job
			writer.writeEndDocument();
			
			return outStream;
		} catch (XMLStreamException | FactoryConfigurationError e) {
			//TODO
		}		
		return null;
	}
	
	private static List<ConversionStepDescriptor> getGroupedByType(List<ConversionStepDescriptor> configurations){
		List<ConversionStepDescriptor> importersList = new ArrayList<ConversionStepDescriptor>();		
		List<ConversionStepDescriptor> manipulatorsList = new ArrayList<ConversionStepDescriptor>();
		List<ConversionStepDescriptor> exportersList = new ArrayList<ConversionStepDescriptor>();
		MODULE_TYPE type = null;
		for (ConversionStepDescriptor c : configurations){
			type = c.getModuleType();
			if (MODULE_TYPE.IMPORTER.equals(type)){
				importersList.add(c);
			}
			else if (MODULE_TYPE.MANIPULATOR.equals(type)){
				manipulatorsList.add(c);
			}
			else if (MODULE_TYPE.EXPORTER.equals(type)){
				exportersList.add(c);
			}
		}
		List<ConversionStepDescriptor> retVal = new ArrayList<ConversionStepDescriptor>();
		retVal.addAll(importersList);
		retVal.addAll(manipulatorsList);
		retVal.addAll(exportersList);
		return retVal;
	}
}

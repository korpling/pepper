package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model;

import java.util.Map;

import de.hu_berlin.german.korpling.saltnpepper.pepper.util.XMLStreamWriter;

public class ConversionStep implements ConversionStepDescriptor{
	private Map<String, String> customizations;
	private String moduleName;
	private String path;
	private STEP_TYPE type;
	
	public ConversionStep(String moduleName, Map<String, String> customizations, String path, STEP_TYPE type){
		this.moduleName = moduleName;
		this.customizations = customizations;
		this.type = type;
	}
	
	@Override
	public String toXML(){
		
		return null;
	}
	
	public enum STEP_TYPE{
		IMPORTER, EXPORTER, MANIPULATOR;
	}
}

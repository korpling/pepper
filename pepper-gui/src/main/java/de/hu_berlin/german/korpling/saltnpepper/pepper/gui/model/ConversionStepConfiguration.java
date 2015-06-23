package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model;

import java.util.Map;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;

public class ConversionStepConfiguration implements ConversionStepDescriptor{
	private Map<String, String> customizations;
	private String moduleName;
	private String path;
	private final MODULE_TYPE type;
	
	public ConversionStepConfiguration(String moduleName, Map<String, String> customizations, String path, MODULE_TYPE type){
		this.moduleName = moduleName;
		this.customizations = customizations;
		this.type = type;
	}
	
	@Override
	public String toXML(){
		
		return null;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getModuleName() {
		return moduleName;
	}

	@Override
	public MODULE_TYPE getModuleType() {
		return type;
	}

	@Override
	public Map<String, String> getProperties() {
		return customizations;
	}

	@Override
	public void setPath(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setModuleName(String moduleName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProperties() {
		// TODO Auto-generated method stub
		
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model;

import java.util.Map;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;

public interface ConversionStepDescriptor {
	public String toXML();
	public String getPath();
	public void setPath(String path);
	public String getModuleName();
	public void setModuleName(String moduleName);
	public MODULE_TYPE getModuleType();	
	public Map<String, String> getProperties();
	public void setProperties();
}

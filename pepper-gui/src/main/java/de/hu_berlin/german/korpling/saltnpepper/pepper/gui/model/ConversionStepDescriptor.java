package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model;

import java.util.Map;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;

public interface ConversionStepDescriptor extends Descriptor{
	public String getPath();
	public void setPath(String path);
	public String getModuleName();
	public void setModuleName(String moduleName);
	public MODULE_TYPE getModuleType();
	/*note no method setModuleType(), moduleType is only once set on instantiation*/
	public Map<String, String> getProperties();
	public void setProperties(Map<String, String> properties);
	public void setProperty(String key, String value);
	public String getPropertyValue(String key);
}

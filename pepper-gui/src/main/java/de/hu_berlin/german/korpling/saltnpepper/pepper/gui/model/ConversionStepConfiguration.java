package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import com.google.common.collect.ImmutableMap;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.util.XMLStreamWriter;

public class ConversionStepConfiguration implements ConversionStepDescriptor{
	private Map<String, String> customizations;
	private String moduleName;
	private String path;
	private final MODULE_TYPE type;
	
	private static final String ERR_MSG_NO_TYPE_SELECTED = "Forbidden instantiation: No type was selected for ".concat(ConversionStepConfiguration.class.getCanonicalName()).concat(".");
	
	/**
	 * Builds a new instance of ConversionStepConfiguration.
	 * @param moduleName -- the module's name
	 * @param properties -- the module's properties with values (values may be null)
	 * @param path -- source (for importers) or target (for exporters) path, may be null, ignored for manipulators
	 * @param type -- {@link MODULE_TYPE#IMPORTER}, {@link MODULE_TYPE#EXPORTER} or {@link MODULE_TYPE#MANIPULATOR}, may not be null
	 */
	public ConversionStepConfiguration(String moduleName, Map<String, String> properties, String path, MODULE_TYPE type){
		if (type!=null){
			this.moduleName = moduleName;
			this.customizations = properties;
			this.path = !MODULE_TYPE.MANIPULATOR.equals(type)? path : null;
			this.type = type;			
		}
		else {
			throw new NullPointerException(ERR_MSG_NO_TYPE_SELECTED);
		}
	}
	
	@Override
	public String toString(){//TODO
//		return (moduleName==null?"undefined":moduleName)
//				.concat(":").concat(path)
//				.concat(":")
//				.concat(type.toString())
//				.concat(":")
//				.concat(customizations==null?
//						"null":
//							customizations.toString());
		return "config";
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
		this.path = !MODULE_TYPE.MANIPULATOR.equals(this.type)? path : null;
	}

	@Override
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public void setProperties(Map<String, String> properties) {
		this.customizations = properties;
	}
	
	@Override
	public void setProperty(String key, String value) {
		customizations.put(key, value);
	}

	@Override
	public String getPropertyValue(String key) {
		return customizations.get(key);
	}
}

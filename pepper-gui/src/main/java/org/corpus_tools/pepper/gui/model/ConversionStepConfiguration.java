package org.corpus_tools.pepper.gui.model;

import java.util.Map;
import java.util.Map.Entry;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.PepperModuleProperty;

public class ConversionStepConfiguration implements ConversionStepDescriptor{
	private PepperModuleProperties properties;
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
	public ConversionStepConfiguration(String moduleName, PepperModuleProperties properties, String path, MODULE_TYPE type){
		if (type!=null){
			this.moduleName = moduleName;
			this.properties = properties;
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
	public PepperModuleProperties getProperties() {
		return properties;
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
	public void setProperties(PepperModuleProperties properties) {
		this.properties = properties;
	}

	@Override
	public String getPropertyValue(String key) {
		return properties.get(key);
	}

	@Override
	public StepDesc toStepDesc() {
		return null;
	}

	@Override
	public void fromStepDesc(StepDesc stepDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void setProperty(Class<T> clazz, String name, T value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void setProperty(PepperModuleProperty<T> property) {
		// TODO Auto-generated method stub
		
	}
}

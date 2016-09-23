package org.corpus_tools.pepper.gui.model;

import java.util.Map;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.PepperModuleProperty;

public interface ConversionStepDescriptor extends Descriptor{
	public String getPath();
	public void setPath(String path);
	public String getModuleName();
	public void setModuleName(String moduleName);
	public MODULE_TYPE getModuleType();
	/*note no method setModuleType(), moduleType is only once set on instantiation*/
	public PepperModuleProperties getProperties();
	public void setProperties(PepperModuleProperties properties);
	public <T> void setProperty(Class<T> clazz, String name, T value);
	public <T> void setProperty(PepperModuleProperty<T> property);
	public String getPropertyValue(String propertyName);
	
	/**This method is needed now. But for the future we should replace 
	 * ConversionStepDescriptor and ConversionStepConfiguration with
	 * Pepper's StepDesc object. 
	 * */
	public StepDesc toStepDesc();
	public void fromStepDesc(StepDesc stepDesc);
}

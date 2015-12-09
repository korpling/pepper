package org.corpus_tools.pepper.gui.components;

import java.util.List;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.gui.model.ConversionStepDescriptor;

import com.vaadin.ui.Component;

public interface View {
//	public WorkflowData getData();
//	public void reset();
	public void setConfig(ConversionStepDescriptor config);
	public void setConfig(int id);
	public ConversionStepDescriptor getConfig();
	public MODULE_TYPE getModuleType();
	public void update();
	public void display(boolean visible, Component... c);
	public void add();
	public int getSize();
	public List<ConversionStepDescriptor> getAllConfigurations();
}

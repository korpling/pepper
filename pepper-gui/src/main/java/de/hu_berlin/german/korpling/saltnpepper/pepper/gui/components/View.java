package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import java.util.List;

import com.vaadin.ui.Component;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model.ConversionStepDescriptor;

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

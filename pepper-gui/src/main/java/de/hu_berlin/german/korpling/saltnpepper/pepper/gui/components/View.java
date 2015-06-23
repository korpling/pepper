package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.Component;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model.ConversionStepDescriptor;

public interface View {
//	public WorkflowData getData();
//	public void reset();
	public void setConfig(ConversionStepDescriptor config);
	public ConversionStepDescriptor getConfig();
	public MODULE_TYPE getModuleType();
	public void update();
	public void display(boolean visible, Component... c);
}

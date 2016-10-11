package org.corpus_tools.pepper.gui.components;

import java.util.Collection;
import java.util.List;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.adapters.StepDescMarshallable;

import com.vaadin.ui.Component;

public interface View {
	public void update();
	public void display(boolean visible, Component... c);
	public void add();
	public int getSize();
	public List<StepDescMarshallable> getAllConfigurations();
	public void setAvailableModules(Collection<PepperModuleDescMarshallable> modules);	
	public void setConfig(StepDescMarshallable config);
	public void setConfig(int id);
	public StepDescMarshallable getConfig();	
	public MODULE_TYPE getModuleType();
	public boolean isInit();
}

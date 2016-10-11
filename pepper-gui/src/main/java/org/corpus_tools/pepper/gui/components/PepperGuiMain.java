package org.corpus_tools.pepper.gui.components;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.gui.controller.VIEW_NAME;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.adapters.StepDescMarshallable;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

public class PepperGuiMain extends Panel implements View{
	
	private PepperGuiView importers;
	private PepperGuiView exporters;
	private PepperGuiView manipulators;
	private PepperGuiView results;
	
	public PepperGuiMain(){
		super();
		importers = new PepperGuiImportersView();
		exporters = new PepperGuiExportersView();
		manipulators = new PepperGuiManipulatorsView();
		results = new PepperGuiResultsView();
	}
	
	protected void setView(VIEW_NAME view){
		if (view.equals(VIEW_NAME.IMPORTERS)){
			setContent((Component)importers);
		}
		else if (view.equals(VIEW_NAME.EXPORTERS)){
			setContent((Component)exporters);
		}
		else if (view.equals(VIEW_NAME.MANIPULATORS)){
			setContent((Component)manipulators);
		}
		else{
			setContent((Component)results);
		}
	}
	
	public void setConfig(StepDescMarshallable config){
		((View)getContent()).setConfig(config);
	}
	
	public StepDescMarshallable getConfig(){
		return ((View)getContent()).getConfig();
	}
	
	public MODULE_TYPE getModuleType(){
		return ((View)getContent()).getModuleType();
	}

	@Override
	public void update() {
		((View)getContent()).update();
	}

	@Override
	public void display(boolean visible, Component... c) {
		((View)getContent()).display(visible, c);
	}

	@Override
	public void add() {
		((View)getContent()).add();
	}

	@Override
	public void setConfig(int id) {
		((View)getContent()).setConfig(id);
	}

	@Override
	public int getSize() {
		return ((View)getContent()).getSize();
	}

	@Override
	public List<StepDescMarshallable> getAllConfigurations() {
		List<StepDescMarshallable> configs = new ArrayList<StepDescMarshallable>();
		configs.addAll(importers.getAllConfigurations());
		configs.addAll(manipulators.getAllConfigurations());
		configs.addAll(exporters.getAllConfigurations());
		return configs;
	}

	@Override
	public void setAvailableModules(Collection<PepperModuleDescMarshallable> modules) {
		importers.setAvailableModules(modules);
		exporters.setAvailableModules(modules);
		manipulators.setAvailableModules(modules);
	}

	@Override
	public boolean isInit() {
		return ((View)getContent()).isInit();
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.VIEW_NAME;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model.ConversionStepDescriptor;

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
	
	public void setConfig(ConversionStepDescriptor config){
		((View)getContent()).setConfig(config);
	}
	
	public ConversionStepDescriptor getConfig(){
		return ((View)getContent()).getConfig();
	}
	
	public MODULE_TYPE getModuleType(){
		return ((View)getContent()).getModuleType();
	}

	@Override
	public void update() {
		((View)getContent()).update();
	}
}

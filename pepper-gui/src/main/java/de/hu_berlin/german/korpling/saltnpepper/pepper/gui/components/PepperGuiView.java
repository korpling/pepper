package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIComponentDictionary;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model.ConversionStepDescriptor;

@DesignRoot
public abstract class PepperGuiView extends VerticalLayout implements View, PepperGUIComponentDictionary{
	private ConversionStepDescriptor config = null;
	protected MODULE_TYPE type;
	public PepperGuiView(){
		super();
		Design.read(this);
	}

	@Override
	public final void setConfig(ConversionStepDescriptor config) {
		if (type.equals(config.getModuleType())){
			this.config = config;
			update();
		}		
	}

	@Override
	public final ConversionStepDescriptor getConfig() {
		return config;
	}
	
	@Override
	public final MODULE_TYPE getModuleType(){
		return type;
	}	
	
	protected final void setModuleType(MODULE_TYPE type){
		this.type = type;
	}
	
	@Override
	public final void display(boolean visible, Component... c){
		for (int i=0; i<c.length; i++){
			c[i].setVisible(visible);
		}		
	}
}

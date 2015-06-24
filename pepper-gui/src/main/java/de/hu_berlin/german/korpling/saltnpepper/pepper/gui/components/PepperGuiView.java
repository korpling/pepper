package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import java.io.File;
import java.util.ArrayList;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIComponentDictionary;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model.ConversionStepConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model.ConversionStepDescriptor;

@DesignRoot
public abstract class PepperGuiView extends VerticalLayout implements View, Configurable, PepperGUIComponentDictionary{
	private ConversionStepDescriptor config = null;
	protected MODULE_TYPE type;
	
	private ArrayList<ConversionStepDescriptor> configurations = null; 
	
	public PepperGuiView(){
		super();
		Design.read(this);
		configurations = new ArrayList<ConversionStepDescriptor>();
	}
	
	@Override
	public void attach(){
		super.attach();
		((PepperGUIController)getUI()).debugOut("attached1");
		if (config==null){
			if (!configurations.isEmpty()){
				config = configurations.get(configurations.size()-1);
			} else {
				setConfig(new ConversionStepConfiguration(null, null, null, getModuleType()));
			}
		}
		if (getErrorHandler()!=null){
			setErrorHandler((PepperGUIController)getUI());
		}
	}

	@Override
	public final void setConfig(ConversionStepDescriptor config) {	
		if (type.equals(config.getModuleType())){
			this.config = config;
			if (!configurations.contains(config)){
				configurations.add(config);
			}
			update();
		}		
	}
	
	@Override
	public final void setConfig(int id){
		setConfig(configurations.get(id));
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
		if (this.type==null){
			this.type = type;
		}
	}
	
	@Override
	public final void display(boolean visible, Component... c){
		for (int i=0; i<c.length; i++){
			c[i].setVisible(visible);
		}		
	}
	
	@Override
	public final void add(){
		setConfig(new ConversionStepConfiguration(null, null, null, type));		
		update();
	}
	
	@Override
	public int getSize(){
		return configurations.size();
	}
	
	@Override
	public void update() {
		TextField pathField = getPathField();
		if (pathField!=null && getConfig()!=null && getConfig().getPath()!=null){			
			File f = new File(getConfig().getPath());
			if (f.exists()){
				display(true, getDetailsComponent());
			} else {
				Notification.show(ERR_MSG_LOCATION_DOES_NOT_EXIST);//TODO shift to message label
				display(false, getDetailsComponent());
			}
			{
				pathField.removeTextChangeListener((PepperGUIController)getUI());
				pathField.setValue(getConfig().getPath());
				pathField.addTextChangeListener((PepperGUIController)getUI());
			}
		}
	}
}

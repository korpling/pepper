package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIComponentDictionary;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.VIEW_NAME;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model.ConversionStepDescriptor;

@DesignRoot
public class PepperGUI extends VerticalLayout implements PepperGUIComponentDictionary, View{	
	private Button btnNewWorkflow;
	private Button btnLoadWorkflow;
	private Button btnAbout;
	private Button btnAdd;
	private Button btnImporters;
	private Button btnExporters;
	private Button btnManipulators;
	private Button btnResults;
	private PepperGuiMain main;
	
	private HorizontalLayout configList;	
		
	public PepperGUI(PepperGUIController controller){
		super();
		
		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		Design.read(this);
		btnNewWorkflow.setId(ID_BUTTON_NEW);
		btnLoadWorkflow.setId(ID_BUTTON_LOAD);
		btnAbout.setId(ID_BUTTON_ABOUT);
		btnImporters.setId(ID_BUTTON_IMPORTERS);
		btnExporters.setId(ID_BUTTON_EXPORTERS);
		btnManipulators.setId(ID_BUTTON_MANIPULATORS);
		btnResults.setId(ID_BUTTON_RESULTS);
		btnAdd.setId(ID_BUTTON_ADD);
		
		btnAdd.setIcon(FontAwesome.PLUS);
		
		btnNewWorkflow.addClickListener(controller);		
		btnLoadWorkflow.addClickListener(controller);
		btnAbout.addClickListener(controller);
		btnImporters.addClickListener(controller);
		btnExporters.addClickListener(controller);
		btnManipulators.addClickListener(controller);
		btnResults.addClickListener(controller);
		btnAdd.addClickListener(controller);
		
		configList.setImmediate(true);
		configList.setDefaultComponentAlignment(Alignment.TOP_LEFT);
		
		setErrorHandler(controller);
	}
	
	@Override
	public void attach(){
		super.attach();
		//TODO add initial method calls for main here
	}
	
	public void setView(VIEW_NAME view){
		main.setView(view);
		btnAdd.setEnabled(!VIEW_NAME.RESULTS.equals(view));
		configList.removeAllComponents();
		for (int i=1; i<getSize()+1; i++){			
			addButton(i);
		}
	}
	
	public MODULE_TYPE getModuleType(){
		return main.getModuleType();
	}
	
	public void setConfig(ConversionStepDescriptor config){
		main.setConfig(config);
	}
	
	public ConversionStepDescriptor getConfig(){
		return main.getConfig();
	}
	
	//FIXME remove on release
	public void debugOut(String message){
		((PepperGUIController)getUI()).debugOut(message);
	}

	@Override
	public void update() {
		main.update();
	}

	@Override
	public void display(boolean visible, Component... c) {
		main.display(visible, c);
	}

	@Override
	public void add() {
		if (main.getContent()!=null){						
			main.add();
			addButton(getSize());
		}
	}
	
	private void addButton(int id){
		Button b = new Button(Integer.toString(id));
		b.setId(CONFIG_BUTTON_ID_PREFIX.concat(Integer.toString(id)));		
		configList.addComponent(b);
		b.addClickListener((PepperGUIController)getUI());
	}

	@Override
	public void setConfig(int id) {
		main.setConfig(id);
	}

	@Override
	public int getSize() {
		return main.getSize();
	}
}

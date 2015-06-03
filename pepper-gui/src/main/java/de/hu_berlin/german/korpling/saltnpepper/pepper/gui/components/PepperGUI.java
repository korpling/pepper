package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIComponentDictionary;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.VIEW_NAME;

@DesignRoot
public class PepperGUI extends VerticalLayout implements PepperGUIComponentDictionary{	
	private Button btnNewWorkflow;
	private Button btnLoadWorkflow;
	private Button btnAbout;
	private Button btnImporters;
	private Button btnExporters;
	private Button btnManipulators;
	private Button btnResults;
	private PepperGuiMain main;
	
	private Label lblDebug;
		
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
		
		btnNewWorkflow.addClickListener(controller);		
		btnLoadWorkflow.addClickListener(controller);
		btnAbout.addClickListener(controller);
		btnImporters.addClickListener(controller);
		btnExporters.addClickListener(controller);
		btnManipulators.addClickListener(controller);
		btnResults.addClickListener(controller);
		
//		addButton();
	}
	
	@Override
	public void attach(){
		super.attach();
		//TODO add initial method calls for main here
	}
	
	public void setView(VIEW_NAME view){
		main.setView(view);
	}
	
	//FIXME remove on release
	public void debugOut(String message){
		lblDebug.setCaption(message);
	}
}

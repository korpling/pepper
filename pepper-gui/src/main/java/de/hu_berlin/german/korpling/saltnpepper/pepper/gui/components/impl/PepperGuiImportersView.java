package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components.impl;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components.PepperGuiView;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

@DesignRoot
public class PepperGuiImportersView extends PepperGuiView{
	private Upload upload;
	private Label lblImportersList;
	private ListSelect importersList;	
	private Table propertiesTable;
	private Button btnShowAll;
	private Table descriptionTable;
	private Button btnAddImport;
	private boolean isInit = false;
	
	public PepperGuiImportersView(){
		super();		
		
		btnShowAll.setId(ID_BUTTON_SHOW_ALL);	
	}
	
	@Override
	public void attach(){
		super.attach();
		if (!isInit){
			PepperGUIController controller = (PepperGUIController)getUI();
			upload.setReceiver(controller);
			btnShowAll.addClickListener(controller);
			btnAddImport.addClickListener(controller);
			isInit = true;
		}
	}
}

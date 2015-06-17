package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import java.io.File;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

@DesignRoot
public class PepperGuiImportersView extends PepperGuiView{
//	private Upload upload;
	private Label lblImportersList;
	private ListSelect importersList;	
	private Table propertiesTable;
	private Button btnShowAll;
	private Table descriptionTable;
	private Button btnAddImport;
	private Button btnBrowseLocal;
	private Label selectedPath;
	private boolean isInit = false;
	
	public PepperGuiImportersView(){
		super();	
		btnShowAll.setId(ID_BUTTON_SHOW_ALL);
		btnBrowseLocal.setId(ID_BUTTON_BROWSE_LOCAL);
	}
	
	@Override
	public void attach(){
		super.attach();
		if (!isInit){
			PepperGUIController controller = (PepperGUIController)getUI();
//			upload.setReceiver(controller);
			btnShowAll.addClickListener(controller);
			btnAddImport.addClickListener(controller);
			btnBrowseLocal.addClickListener(controller);
			isInit = true;
		}
	}
	
	protected void displaySelectedPath(String path){
		selectedPath.setCaption(path);
	}
}

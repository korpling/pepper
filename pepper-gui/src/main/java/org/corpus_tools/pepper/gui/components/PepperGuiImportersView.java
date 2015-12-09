package org.corpus_tools.pepper.gui.components;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.gui.controller.PepperGUIController;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

@DesignRoot
public class PepperGuiImportersView extends PepperGuiView{
//	private Upload upload;
	private Label lblImportersList;
	private ListSelect importersList;	
	private Table propertiesTable;
	private Button btnShowAll;
	private Table descriptionTable;
	private Button btnBrowseLocal;
	private TextField importPath;
	private AbsoluteLayout details;
	
	private boolean isInit = false;
	
	public PepperGuiImportersView(){
		super();	
		setModuleType(MODULE_TYPE.IMPORTER);		
		btnShowAll.setId(ID_BUTTON_SHOW_ALL);
		btnBrowseLocal.setId(ID_BUTTON_BROWSE_LOCAL);
		importPath.setId(ID_PATH_FIELD_MAIN);		
	}
	
	@Override
	public void attach(){
		super.attach();
		if (!isInit){			
			PepperGUIController controller = (PepperGUIController)getUI();
//			upload.setReceiver(controller);
			btnShowAll.addClickListener(controller);
			btnBrowseLocal.addClickListener(controller);
			importPath.addTextChangeListener(controller);	
			importPath.setTextChangeEventMode(TextChangeEventMode.LAZY);
			
			isInit = true;
		}
	}

	@Override
	public TextField getPathField() {
		return importPath;
	}

	@Override
	public Table getDescriptionTable() {
		return descriptionTable;
	}

	@Override
	public Table getPropertiesTable() {
		return propertiesTable;
	}

	@Override
	public Component getDetailsComponent() {
		return details;
	}	
}

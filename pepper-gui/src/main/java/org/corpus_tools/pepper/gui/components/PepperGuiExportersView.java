package org.corpus_tools.pepper.gui.components;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.gui.controller.PepperGUIController;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

@DesignRoot
public class PepperGuiExportersView extends PepperGuiView {
	private boolean isInit = false;
	
	private Button btnBrowseLocal; 
	private TextField exportPath;
	private AbsoluteLayout details;
	private ListSelect exportersList;
	private Table propertiesTable;
	private Table descriptionTable;
	
	public PepperGuiExportersView(){
		super();
		setModuleType(MODULE_TYPE.EXPORTER);
		
		btnBrowseLocal.setId(ID_BUTTON_BROWSE_LOCAL);
		exportersList.setImmediate(true);
		exportPath.setId(ID_PATH_FIELD_MAIN);
	}
	
	@Override
	public void attach(){
		super.attach();
		if (!isInit){
			PepperGUIController controller = (PepperGUIController)getUI();
			btnBrowseLocal.addClickListener(controller);
			exportPath.addTextChangeListener(controller);
			
			isInit = true;
		}
	}

	@Override
	public TextField getPathField() {
		return exportPath;
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
	
	@Override
	public ListSelect getModuleSelector() {
		return exportersList;
	}

}

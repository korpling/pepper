package org.corpus_tools.pepper.gui.components;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.gui.controller.PepperGUIController;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Component;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

@DesignRoot
public class PepperGuiManipulatorsView extends PepperGuiView {
	private boolean isInit = false;
	private ListSelect manipulatorsList;
	
	public PepperGuiManipulatorsView(){
		super();
		setModuleType(MODULE_TYPE.MANIPULATOR);
	}
	
	@Override
	public void attach(){
		super.attach();
		if (!isInit){
			PepperGUIController controller = (PepperGUIController)getUI();
			//TODO set controller for elements here
			isInit = true;
		}
	}

	@Override
	public TextField getPathField() {
		return null;
	}

	@Override
	public Table getDescriptionTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table getPropertiesTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getDetailsComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListSelect getModuleSelector() {
		return manipulatorsList;
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.MODULE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

@DesignRoot
public class PepperGuiManipulatorsView extends PepperGuiView {
	private boolean isInit = false;
	
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
}

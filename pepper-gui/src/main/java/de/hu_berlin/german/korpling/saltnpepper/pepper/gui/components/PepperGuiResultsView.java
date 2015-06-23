package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

@DesignRoot
public class PepperGuiResultsView extends PepperGuiView {
	private boolean isInit = false;
	
	public PepperGuiResultsView(){
		super();
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
	public void update() {
		// TODO Auto-generated method stub
		
	}	
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.HorizontalLayout;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

public class PepperGUIBody extends HorizontalLayout {	
	protected static final String GUI_BODY_HEIGHT = "668px";
	
	
	
	private PepperGUIController controller = null;
	
	
	protected PepperGUIBody(PepperGUIController GUIcontroller){
		controller = GUIcontroller;
		setWidth(PepperGUI.GUI_WIDTH);
		setHeight(GUI_BODY_HEIGHT);
	}
	
}

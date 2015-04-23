package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.HorizontalLayout;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

public class PepperGUIMain extends HorizontalLayout {
	protected static final String GUI_BODY_MAIN_WIDTH = "816px";	
	
	protected PepperGUIMain(PepperGUIController guiController) {
		setWidth(GUI_BODY_MAIN_WIDTH);
		setHeight(PepperGUIBody.GUI_BODY_HEIGHT);		
	}

}

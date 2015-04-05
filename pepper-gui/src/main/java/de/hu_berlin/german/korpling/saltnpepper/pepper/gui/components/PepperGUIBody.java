package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

public class PepperGUIBody extends HorizontalLayout {	
	protected static final String GUI_BODY_HEIGHT = "668px";
	
	protected PepperGUIBody(PepperGUIController guiController){		
		/*DEBUG*/setCaption("I AM BODY");
		setWidth(PepperGUI.GUI_WIDTH);
		setHeight(GUI_BODY_HEIGHT);
		addComponent(new PepperGUIStepsMenu(guiController));
		addComponent(new PepperGUIMain(guiController));
	}
	
}

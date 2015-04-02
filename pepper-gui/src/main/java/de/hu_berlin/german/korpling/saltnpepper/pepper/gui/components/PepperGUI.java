package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

public class PepperGUI extends VerticalLayout {
	protected static final String GUI_WIDTH = "1024px";
	protected static final String GUI_HEIGHT = "768px";
	private PepperGUIHeader header;
	private PepperGUIBody body;
	
	/*MAYBE?*/
	private HorizontalLayout footer;
	
	public PepperGUI(PepperGUIController GUIcontroller){
		setWidth(GUI_WIDTH);
		setHeight(GUI_HEIGHT);		
		header = new PepperGUIHeader(GUIcontroller);
		body = new PepperGUIBody(GUIcontroller);
		addComponent(header);
		addComponent(body);
	}
}

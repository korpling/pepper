package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

public class PepperGUI extends VerticalLayout {
	protected static final String GUI_WIDTH = "1024px";
	protected static final String GUI_HEIGHT = "768px";	
		
	public PepperGUI(PepperGUIController GUIcontroller){		
		setWidth(GUI_WIDTH);
		setHeight(GUI_HEIGHT);		
//		addComponent(new PepperGUIHeader(GUIcontroller));				
//		addComponent(new PepperGUIBody(GUIcontroller));
		
		HorizontalLayout header = new HorizontalLayout();
		header.setHeight(PepperGUIHeader.GUI_HEADER_HEIGHT);
		header.setWidth(GUI_WIDTH);
		header.setCaption("HEADER");
		HorizontalLayout body = new HorizontalLayout();
		body.setHeight(PepperGUIBody.GUI_BODY_HEIGHT);
		body.setWidth(GUI_WIDTH);
		body.setCaption("BODY");
		addComponent(header);
		addComponent(body);
	}
}

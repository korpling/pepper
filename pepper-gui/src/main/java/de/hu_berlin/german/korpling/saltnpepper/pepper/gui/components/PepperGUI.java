package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

@DesignRoot
public class PepperGUI extends VerticalLayout {
	protected static final String GUI_WIDTH = "1024px";
	protected static final String GUI_HEIGHT = "768px";	
		
	public PepperGUI(PepperGUIController GUIcontroller){	
//		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//		Design.read("gui.xml", this);		
		
		setWidth(GUI_WIDTH);
		setHeight(GUI_HEIGHT);	
		HorizontalLayout header = new PepperGUIHeader(GUIcontroller);
		HorizontalLayout body = new PepperGUIBody(GUIcontroller);
		addComponent(header);		
		addComponent(body);	
		setExpandRatio(header, 0f);	
		setExpandRatio(body, 1f);
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class PepperGUI extends VerticalLayout {
	protected static final String GUI_WIDTH = "1024px";
	protected static final String GUI_HEIGHT = "768px";
	private PepperGUIHeader header;
	private PepperGUIBody body;
	
	/*MAYBE?*/
	private HorizontalLayout footer;
	
	public PepperGUI(){
		setWidth(GUI_WIDTH);
		setHeight(GUI_HEIGHT);		
		header = new PepperGUIHeader();
		body = new PepperGUIBody();
		addComponent(header);
		addComponent(body);
	}
}

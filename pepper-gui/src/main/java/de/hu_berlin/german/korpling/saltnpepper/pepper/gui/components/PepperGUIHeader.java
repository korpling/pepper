package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.HorizontalLayout;

public class PepperGUIHeader extends HorizontalLayout {
	protected static final String HEADER_HEIGHT = "100px";
	protected static final String HEADER_LEFT_WIDTH = "208px";
	protected static final String HEADER_MENU_WIDTH = "508px";
	protected static final String HEADER_RIGHT_WIDTH = "308px";
	private HorizontalLayout headerLeft;	
	private HorizontalLayout headerRight;
	private HorizontalLayout headerMenu;
	
	public PepperGUIHeader(){
		setWidth(PepperGUI.GUI_WIDTH);
		setHeight(HEADER_HEIGHT);
		headerLeft = new HorizontalLayout();
		headerMenu = new HorizontalLayout();
		headerRight = new HorizontalLayout();
		headerLeft.setWidth(HEADER_LEFT_WIDTH);
		headerLeft.setHeight(HEADER_HEIGHT);
		headerMenu.setWidth(HEADER_MENU_WIDTH);
		headerMenu.setHeight(HEADER_HEIGHT);
		headerRight.setWidth(HEADER_RIGHT_WIDTH);
		headerRight.setHeight(HEADER_HEIGHT);
		
	}
}

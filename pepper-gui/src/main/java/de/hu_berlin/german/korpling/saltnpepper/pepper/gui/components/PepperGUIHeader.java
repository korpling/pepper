package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIComponentDictionary;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

public class PepperGUIHeader extends HorizontalLayout implements PepperGUIComponentDictionary{
	protected static final String HEADER_HEIGHT = "100px";
	protected static final String HEADER_LEFT_WIDTH = "208px";
	protected static final String HEADER_MENU_WIDTH = "508px";
	protected static final String HEADER_RIGHT_WIDTH = "308px";
	private static final String CAPTION_NEW = "New workflow";
	private static final String CAPTION_ABOUT = "About";
	private static final String CAPTION_LOAD = "Load workflow";
	private HorizontalLayout headerLeft = null;	
	private HorizontalLayout headerRight = null;
	private HorizontalLayout headerMenu = null;
	private Button buttonNew = null;
	private Button buttonLoad = null;
	private Button buttonAbout = null;
	
	
	private PepperGUIController controller;
	
	protected PepperGUIHeader(PepperGUIController GUIcontroller){
		controller = GUIcontroller;		
		
		setWidth(PepperGUI.GUI_WIDTH);
		setHeight(HEADER_HEIGHT);
		headerLeft = new HorizontalLayout();
		headerMenu = new HorizontalLayout();
		headerRight = new HorizontalLayout();
		
		initLeft();
		initMenu();
		initRight();
		
		addComponent(headerLeft);
		addComponent(headerMenu);
		addComponent(headerRight);
	}
	
	private void initLeft(){
		headerLeft.setWidth(HEADER_LEFT_WIDTH);
		headerLeft.setHeight(HEADER_HEIGHT);		
	}
	
	private void initMenu(){
		headerMenu.setWidth(HEADER_MENU_WIDTH);
		headerMenu.setHeight(HEADER_HEIGHT);
		
		buttonNew = new Button();
		buttonNew.setCaption(CAPTION_NEW);
		buttonNew.setId(ID_BUTTON_NEW);
		buttonNew.addClickListener(controller);
		buttonLoad = new Button();
		buttonLoad.setCaption(CAPTION_LOAD);
		buttonLoad.setId(ID_BUTTON_LOAD);
		buttonLoad.addClickListener(controller);
		buttonAbout = new Button();
		buttonAbout.setCaption(CAPTION_ABOUT);
		buttonAbout.setId(ID_BUTTON_ABOUT);
		buttonAbout.addClickListener(controller);
		
	}

	private void initRight(){
		headerRight.setWidth(HEADER_RIGHT_WIDTH);
		headerRight.setHeight(HEADER_HEIGHT);		
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIComponentDictionary;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

public class PepperGUIHeader extends HorizontalLayout implements PepperGUIComponentDictionary{
	protected static final String GUI_HEADER_HEIGHT = "100px";
	protected static final String GUI_HEADER_LEFT_WIDTH = "208px";
	protected static final String HEADER_MENU_WIDTH = "508px";
	protected static final String HEADER_RIGHT_WIDTH = "308px";
	private static final String CAPTION_NEW = "New workflow";
	private static final String CAPTION_ABOUT = "About";
	private static final String CAPTION_LOAD = "Load workflow";
	
	protected PepperGUIHeader(PepperGUIController guiController){
		setWidth(PepperGUI.GUI_WIDTH);
		setHeight(GUI_HEADER_HEIGHT);		
		
		addComponent(new PepperGUIHeaderLeft());
		addComponent(new PepperGUIHeaderMenu(guiController));
		addComponent(new PepperGUIHeaderRight());
	}
	
	private class PepperGUIHeaderLeft extends HorizontalLayout{
		
		private PepperGUIHeaderLeft(){
			setWidth(GUI_HEADER_LEFT_WIDTH);
			setHeight(GUI_HEADER_HEIGHT);
		}
	}
	
	private class PepperGUIHeaderMenu extends HorizontalLayout{
		
		private PepperGUIHeaderMenu(PepperGUIController guiController){
			setWidth(HEADER_MENU_WIDTH);
			setHeight(GUI_HEADER_HEIGHT);
			
			Button buttonNew = new Button();
			buttonNew.setCaption(CAPTION_NEW);
			buttonNew.setId(ID_BUTTON_NEW);
			buttonNew.addClickListener(guiController);
			Button buttonLoad = new Button();
			buttonLoad.setCaption(CAPTION_LOAD);
			buttonLoad.setId(ID_BUTTON_LOAD);
			buttonLoad.addClickListener(guiController);
			Button buttonAbout = new Button();
			buttonAbout.setCaption(CAPTION_ABOUT);
			buttonAbout.setId(ID_BUTTON_ABOUT);
			buttonAbout.addClickListener(guiController);
			addComponent(buttonNew);
			addComponent(buttonLoad);
			addComponent(buttonAbout);
			
		}			
	}

	private class PepperGUIHeaderRight extends HorizontalLayout{
		
		private PepperGUIHeaderRight(){
			setWidth(HEADER_RIGHT_WIDTH);
			setHeight(GUI_HEADER_HEIGHT);
		}
	}
}

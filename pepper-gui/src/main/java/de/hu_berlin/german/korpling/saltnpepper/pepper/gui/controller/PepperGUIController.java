package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PepperGUIController implements PepperGUIComponentDictionary, ClickListener{
	
	
	public static void main(String[] args){
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		String id = event.getComponent().getId();
		if (ID_BUTTON_ABOUT.equals(id)){
			
		}
		else if (ID_BUTTON_NEW.equals(id)){
			
		}
		else if (ID_BUTTON_LOAD.equals(id)){
			
		}		
	}

}

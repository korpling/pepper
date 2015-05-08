package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components.PepperGUI;

@Title("Pepper converter framework")
@Theme("valo")
public class PepperGUIController extends UI implements PepperGUIComponentDictionary, ClickListener, ErrorHandler{	
	protected void init(VaadinRequest request){
		setContent(new PepperGUI(this));		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		String id = event.getComponent().getId();
		if (ID_BUTTON_ABOUT.equals(id)){
			
		}
		else if (ID_BUTTON_NEW.equals(id)){
			Notification.show("Coucou");
		}
		else if (ID_BUTTON_LOAD.equals(id)){
			
		}
		else if (ID_BUTTON_IMPORTERS.equals(id)){
			
		}
		else if (ID_BUTTON_EXPORTERS.equals(id)){
			
		}
		else if (ID_BUTTON_MANIPULATORS.equals(id)){
			
		}
		else if (ID_BUTTON_RESULTS.equals(id)){
			
		}
		else if ("test".equals(id)){
			Notification.show("it worked");
		}
		
	}

	@Override
	public void error(com.vaadin.server.ErrorEvent event) {
		// TODO Auto-generated method stub
		
	}

}

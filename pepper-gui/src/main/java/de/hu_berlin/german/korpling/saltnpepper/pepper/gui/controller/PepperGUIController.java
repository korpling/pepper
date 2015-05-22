package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller;

import java.io.OutputStream;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.UploadException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.VerticalLayout;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components.PepperGUI;

@Title("Pepper converter framework")
@Theme("valo")
public class PepperGUIController extends UI implements PepperGUIComponentDictionary, ClickListener, Receiver, ErrorHandler{	
	private PepperGUI gui = null;
	
	protected void init(VaadinRequest request){
		gui = new PepperGUI(this);
		this.setErrorHandler(this);
		setContent(gui);		
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
			gui.setView(VIEW.IMPORTERS);
		}
		else if (ID_BUTTON_EXPORTERS.equals(id)){
			gui.setView(VIEW.EXPORTERS);
		}
		else if (ID_BUTTON_MANIPULATORS.equals(id)){
			gui.setView(VIEW.MANIPULATORS);
		}
		else if (ID_BUTTON_RESULTS.equals(id)){
			gui.setView(VIEW.RESULTS);
		}
		else if ("test".equals(id)){
			Notification.show("it worked");
		}
		
	}

	@Override
	public void error(com.vaadin.server.ErrorEvent event) {
		if (event.getThrowable() instanceof UploadException){
			Notification.show("Please enter a path before uploading");
			/*DEBUG – we will use the upload button as trigger for testing*/
			/*END OF DEBUG*/
		}
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO Auto-generated method stub
		return null;
	}

}

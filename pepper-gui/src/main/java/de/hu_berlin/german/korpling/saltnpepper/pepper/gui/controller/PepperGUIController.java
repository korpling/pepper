package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller;

import java.io.File;
import java.io.OutputStream;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.UploadException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Window;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components.PathSelectDialogue;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components.PepperGUI;

@Title("Pepper converter framework")
@Theme("valo")
public class PepperGUIController extends UI implements PepperGUIComponentDictionary, ClickListener, LayoutClickListener, Receiver, ErrorHandler{	
	private PepperGUI gui = null;
	private Window pathSelectDialogueWindow = null;
	private PathSelectDialogue pathDialogue = null;
	private static final String DEFAULT_DIALOGUE_PATH = "/home/klotzmaz/test"; //TODO set!
	private static final String PATH_DIALOGUE_TITLE = "Select your path, please";
	
	protected void init(VaadinRequest request){		
		gui = new PepperGUI(this);
		setErrorHandler(this);
		
		{//prepare path select window
			Window w = new Window(PATH_DIALOGUE_TITLE);
			pathDialogue = new PathSelectDialogue();
			w.setContent(pathDialogue);
			w.center();
			w.setModal(true);
			pathSelectDialogueWindow = w; //TODO try to finalize the window
		}
		setContent(gui);		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		String id = event.getComponent().getId();
		debugOut("click event, id="+id);
		if (ID_BUTTON_ABOUT.equals(id)){
			
		}
		else if (ID_BUTTON_NEW.equals(id)){
		}
		else if (ID_BUTTON_LOAD.equals(id)){
			
		}
		else if (ID_BUTTON_IMPORTERS.equals(id)){
			gui.setView(VIEW_NAME.IMPORTERS);
		}
		else if (ID_BUTTON_EXPORTERS.equals(id)){
			gui.setView(VIEW_NAME.EXPORTERS);
		}
		else if (ID_BUTTON_MANIPULATORS.equals(id)){
			gui.setView(VIEW_NAME.MANIPULATORS);
		}
		else if (ID_BUTTON_RESULTS.equals(id)){
			gui.setView(VIEW_NAME.RESULTS);
		}
		else if (ID_BUTTON_BROWSE_LOCAL.equals(id)){
			displayPathSelectDialogue(DEFAULT_DIALOGUE_PATH);
		}
		else if (ID_BUTTON_BC_PATH.equals(id)){
			
		}
		else if (id.startsWith("/")){//make OS-sensitive
			displayPathSelectDialogue(id);
		}
		else if ("test".equals(id)){
		}
		
	}
	
	//FIXME remove on release
	public void debugOut(String message){
		gui.debugOut(message);
	}
	
	private void displayPathSelectDialogue(String rootPath){
		String path = rootPath==null || rootPath.isEmpty()? "/" : rootPath; //TODO we still have to look for the operating system 
		FilesystemContainer fsc = new FilesystemContainer(new File(path), "", false);
		pathDialogue.reload(fsc, this);
		fsc = null;
		if (!pathSelectDialogueWindow.isAttached()) {addWindow(pathSelectDialogueWindow);}
	}

	@Override
	public void error(com.vaadin.server.ErrorEvent event) {
		if (event.getThrowable() instanceof UploadException){
			Notification.show("Please enter a path before uploading");
			/*DEBUG – we will use the upload button as trigger for testing*/
			/*END OF DEBUG*/
		}
	}

	/**We need this method when localhost!=host*/
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void layoutClick(LayoutClickEvent event) {	
		Component c = event.getClickedComponent();
		String id = c.getId();
		if (event.isDoubleClick() && ID_PATH_SELECT.equals(id)){
			String newRoot = ((ListSelect)c).getValue().toString();
			displayPathSelectDialogue(newRoot);			
		}
	}

}

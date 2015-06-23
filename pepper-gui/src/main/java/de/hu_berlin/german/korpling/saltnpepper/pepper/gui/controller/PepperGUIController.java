package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.lang3.SystemUtils;

import com.google.gwt.dev.util.collect.HashMap;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Window;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components.PathSelectDialogue;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components.PepperGUI;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model.ConversionStepConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.model.ConversionStepDescriptor;

@Title("Pepper converter framework")
@Theme("valo")
public class PepperGUIController extends UI implements PepperGUIComponentDictionary, ClickListener, LayoutClickListener, TextChangeListener, ValueChangeListener, Receiver, ErrorHandler{	
	private PepperGUI gui = null;
	private Window pathSelectDialogueWindow = null;
	private PathSelectDialogue pathDialogue = null;
	private final String DEFAULT_DIALOGUE_PATH = SystemUtils.getUserHome().getAbsolutePath();
	private static final String PATH_DIALOGUE_TITLE = "Select your path, please";
	private IdProvider idProvider = null;
	
	protected void init(VaadinRequest request){		
		gui = new PepperGUI(this);
		setErrorHandler(this);
		setImmediate(true);
		
		idProvider = new IdProvider();
		
		{//prepare path select window
			Window w = new Window(PATH_DIALOGUE_TITLE);
			pathDialogue = new PathSelectDialogue();
			w.setSizeUndefined();
			w.setContent(pathDialogue);
			w.center();
			w.setModal(true);		
			pathSelectDialogueWindow = w; //TODO try to finalize the window
		}
		setContent(gui);
	}
	
	public String getId(String prefix){
		return idProvider.getId(prefix);
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
			modifyPathSelectDialogue(DEFAULT_DIALOGUE_PATH, false);
		}
		else if (ID_BUTTON_REFRESH_ROOTS.equals(id)){
			pathDialogue.refreshRoots();
		}
		else if (ID_BUTTON_PATH_SELECT.equals(id)){			
			removeWindow(pathSelectDialogueWindow);		
			ConversionStepDescriptor config = gui.getConfig(); 
			if (config==null){
				config = new ConversionStepConfiguration(null, null, pathDialogue.getSelectedPath(), gui.getModuleType());				
				gui.setConfig(config);
			} else {
				config.setPath(pathDialogue.getSelectedPath());
				gui.update();
			}
		}else if (id.startsWith(PATH_PREFIX)){
			modifyPathSelectDialogue(id.substring(1), false);
		}
		else if ("test".equals(id)){//TODO remove before RELEASE
		}
		
	}
	
	//FIXME remove on release
	public void debugOut(String message){
		gui.debugOut(message);
	}
	
	private void modifyPathSelectDialogue(String rootPath, boolean calledByList){
		String path = rootPath==null || rootPath.isEmpty()? SystemUtils.getUserHome().getAbsolutePath() : rootPath; 
		FilesystemContainer fsc = new FilesystemContainer(new File(path), "", false);
		pathDialogue.reload(fsc, this, calledByList);
		fsc = null;
		if (!pathSelectDialogueWindow.isAttached()) {addWindow(pathSelectDialogueWindow);}
	}

	@Override
	public void error(com.vaadin.server.ErrorEvent event) {
		if (event.getThrowable() instanceof UploadException){
			Notification.show("Please enter a path before uploading");
		}
	}

	/**We need this method when localhost!=host*/
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		//TODO
		return null;
	}

	@Override
	public void layoutClick(LayoutClickEvent event) {		
		Component c = event.getClickedComponent();
		String id = c.getId();
		String newRoot = ((ListSelect)c).getValue().toString();
		if (event.isDoubleClick() && ID_PATH_SELECT.equals(id)){			
			modifyPathSelectDialogue(newRoot, true);			
		}
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {		
		/* right now this method must not be used for anything else */
		pathDialogue.setPathLabelValue(pathDialogue.getListValue());
	}

	/*
	 * using textChange() instead of valueChange() has the advantage, that we can identify the trigger component,
	 * which is not provided by a ValueChangeEvent. Further we can now use valueChange() for other purposes.
	 */
	@Override
	public void textChange(TextChangeEvent event) {
		String txt = event.getText();
		String id = event.getComponent().getId();
		debugOut(txt);
		if (ID_PATH_FIELD_DIALOGUE.equals(id)){
			File f = new File(txt);
			if (f.exists() && f.isDirectory()){
				modifyPathSelectDialogue(txt, false);
			}
		}
		else if (ID_PATH_FIELD_MAIN.equals(id)){
			TextField c = (TextField)event.getComponent();
			c.removeTextChangeListener(this);
			ConversionStepDescriptor config = gui.getConfig();
			if (config==null){
				gui.setConfig(new ConversionStepConfiguration(null, null, txt.replace("file://", ""), gui.getModuleType()));
			} else {
				config.setPath(txt.replace("file://", ""));
				gui.update();
			}
			c.addTextChangeListener(this);
		}
	}
}

package org.corpus_tools.pepper.gui.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.Collection;

import org.apache.commons.lang3.SystemUtils;
import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.gui.client.ServiceConnector;
import org.corpus_tools.pepper.gui.components.PathSelectDialogue;
import org.corpus_tools.pepper.gui.components.PepperGUI;
import org.corpus_tools.pepper.gui.components.ProgressDisplay;
import org.corpus_tools.pepper.gui.components.View;
import org.corpus_tools.pepper.service.adapters.CorpusDescMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.adapters.StepDescMarshallable;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Push;
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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Window;

@Title("Pepper converter framework")
@Theme("valo")
@Push
public class PepperGUIController extends UI implements PepperGUIComponentDictionary, ClickListener, LayoutClickListener, TextChangeListener, ValueChangeListener, Receiver, ErrorHandler{	
	private PepperGUI gui = null;
	private Window pathSelectDialogueWindow = null;
	private PathSelectDialogue pathDialogue = null;
	private IdProvider idProvider = null;
	private Window debugWindow = null;
	private ServiceConnector serviceConnector = null;
	private JobManager jobManager = null;	
	private Thread progressThread = null;

	private static final String PATH_DIALOGUE_TITLE = "Select your path, please";	
	private static final String SERVICE_URL = "http://localhost:8080/pepper-rest/resource/";
	private final String DEFAULT_DIALOGUE_PATH = SystemUtils.getUserHome().getAbsolutePath();
	
	private static final Logger logger = LoggerFactory.getLogger(PepperGUIController.class);
	
	/* pepper stuff */
	Collection<PepperModuleDescMarshallable> modules = null;
	
	protected void init(VaadinRequest request){
		setImmediate(true);
		gui = new PepperGUI(this);
		gui.setImmediate(true);
		setErrorHandler(this);
		setImmediate(true);
		
		idProvider = new IdProvider();
		serviceConnector = new ServiceConnector(SERVICE_URL);
		modules = serviceConnector.getAllModules();
		gui.setAvailableModules(modules);
		
		{//prepare path select window
			Window w = new Window(PATH_DIALOGUE_TITLE);
			pathDialogue = new PathSelectDialogue();
			w.setSizeUndefined();
			w.setContent(pathDialogue);
			w.center();
			w.setModal(true);		
			pathSelectDialogueWindow = w; //TODO try to finalize the window
		}
		{//DEBUG
			Window db = new Window("DEBUG");
			db.setWidth("300");
			db.setHeight("800");
			TextArea ta = new TextArea();			
			ta.setReadOnly(false);
			db.setContent(ta);
			ta.setSizeFull();
			addWindow(db);
			db.setPositionX(1000);
			db.setPositionY(0);
			debugWindow = db;
		}//END OF DEBUG
		
		setContent(gui);
	}
	
	public String getId(String prefix){
		return idProvider.getId(prefix);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		String id = event!=null? event.getComponent().getId() : null;
		debugOut("click event, id="+id);
		if (ID_BUTTON_ABOUT.equals(id)){	
			if (modules==null){
				modules = serviceConnector.getAllModules();
			}
			gui.debugOut(modules.toString());
		}
		else if (ID_BUTTON_NEW.equals(id)){
		}
		else if (ID_BUTTON_LOAD.equals(id)){
			
		}
		else if (ID_BUTTON_BROWSE_LOCAL.equals(id)){
			modifyPathSelectDialogue(DEFAULT_DIALOGUE_PATH, false);
		}
		else if (ID_BUTTON_REFRESH_ROOTS.equals(id)){
			pathDialogue.refreshRoots();
		}
		else if (ID_BUTTON_PATH_SELECT.equals(id)){			
			removeWindow(pathSelectDialogueWindow);		
			StepDescMarshallable config = gui.getConfig(); 
			if (config==null){				
				config = new StepDescMarshallable();
				config.setModuleType(gui.getModuleType());
				CorpusDesc corpusDesc = new CorpusDesc();
				corpusDesc.setCorpusPath(URI.createURI(pathDialogue.getSelectedPath()));				
				gui.setConfig(config);
			} else {
				if (config.getCorpusDesc() == null){
					config.setCorpusDesc(new CorpusDescMarshallable());
				}
				config.getCorpusDesc().setCorpusPathURI(pathDialogue.getSelectedPath());
				gui.update();
			}
		}
		else if (id.startsWith(PATH_PREFIX)){
			modifyPathSelectDialogue(id.substring(1), false);
		}
		else if (ID_BUTTON_ADD.equals(id)){
			add();
		}
		else if ("test".equals(id)){//TODO remove before RELEASE
		}
		
	}
	
	public void add(){
		gui.add();
	}
	
	public void setConfig(int id){
		gui.setConfig(id);
	}
	
	private boolean writeWorkflowFile(String absolutePath){
		return false;	
	}
	
	//FIXME remove on release
	public void debugOut(String message){
		TextArea ta = (TextArea)debugWindow.getContent(); 
		ta.setValue(ta.getValue().concat(System.lineSeparator()).concat(message));
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
		StackTraceElement[] trace = event.getThrowable().getStackTrace();
		for (int i=0; i<trace.length; i++){
			debugOut(trace[i].getClassName().substring(trace[i].getClassName().lastIndexOf('.')+1).concat(":").concat(trace[i].getMethodName()).concat(":").concat(Integer.toString(trace[i].getLineNumber())));
		}
		event.getThrowable().printStackTrace();
	}

	/**We need this method when localhost!=host*/
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		//TODO
		return null;
	}

	@Override
	public void layoutClick(LayoutClickEvent event) {		
//		Component c = event.getClickedComponent();
//		String id = c.getId();		
//		if (event.isDoubleClick() && ID_PATH_SELECT.equals(id)){			
//			String newRoot = String.valueOf(((ListSelect)c).getValue());
//			modifyPathSelectDialogue(newRoot, true);			
//		}
//		else if (c instanceof View){
//			((View)c).update();
//		}
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {		
		/* right now this method must not be used for anything else */ 
		if (((Class<?>)event.getProperty().getType()).isAssignableFrom(String.class)){
			pathDialogue.setPathLabelValue(pathDialogue.getListValue());
		}						
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
		else if (ID_PATH_FIELD_MAIN.equals(id)){/*DEAD code, but do not remove yet*/
			TextField c = (TextField)event.getComponent();
			c.removeTextChangeListener(this);
			StepDescMarshallable config = gui.getConfig();
			if (config==null){
				StepDescMarshallable newConfig = new StepDescMarshallable();
				newConfig.setModuleType(gui.getModuleType());
				newConfig.setCorpusDesc(new CorpusDescMarshallable());
				newConfig.getCorpusDesc().setCorpusPathURI(txt.replace("file://", ""));
				gui.setConfig(newConfig);
			} else {
				if (config.getCorpusDesc() == null){
					config.setCorpusDesc(new CorpusDescMarshallable());
				}
				config.getCorpusDesc().setCorpusPathURI(txt.replace("file://", ""));				
				gui.update();
			}
			c.addTextChangeListener(this);
		}
	}

	public String start() {
		// TODO block for further configuration (in which ever sense)
		String jobId = serviceConnector.createJob(gui.getAllConfigurations());
		debugOut("Started job: "+jobId);
		jobManager.checkForProgress(true);
		jobManager.add(jobId);
		progressThread.start();
		return jobId;
	}
	
	public void registerProgressDisplay(ProgressDisplay progressDisplay){
		jobManager = new JobManager(serviceConnector, progressDisplay);
		progressThread = new Thread(jobManager, "JobManager-Thread");		
	}
}

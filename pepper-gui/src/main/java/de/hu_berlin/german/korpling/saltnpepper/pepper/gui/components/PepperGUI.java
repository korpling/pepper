package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIComponentDictionary;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.WINDOW;

@DesignRoot
public class PepperGUI extends VerticalLayout implements PepperGUIComponentDictionary{	
	private Button btnNewWorkflow;
	private Button btnLoadWorkflow;
	private Button btnAbout;
	private Button btnImporters;
	private Button btnExporters;
	private Button btnManipulators;
	private Button btnResults;
	private VerticalLayout start;
	private VerticalLayout importers;
	private VerticalLayout exporters;
	private VerticalLayout manipulators;
	private VerticalLayout results;
	private HorizontalLayout itemList;
	private int buttonCount = 0;
	private PepperGUIController controller = null;
	private PepperGuiMain main;
	
	/*Components of importers section*/
	private Upload upload;
		
	public PepperGUI(PepperGUIController GUIcontroller){
		controller = GUIcontroller;
		
//		main.setController(GUIcontroller);
		
		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		Design.read(this);
		btnNewWorkflow.setId(ID_BUTTON_NEW);
		btnLoadWorkflow.setId(ID_BUTTON_LOAD);
		btnAbout.setId(ID_BUTTON_ABOUT);
		btnImporters.setId(ID_BUTTON_IMPORTERS);
		btnExporters.setId(ID_BUTTON_EXPORTERS);
		btnManipulators.setId(ID_BUTTON_MANIPULATORS);
		btnResults.setId(ID_BUTTON_RESULTS);
		
		btnNewWorkflow.addClickListener(GUIcontroller);		
		btnLoadWorkflow.addClickListener(GUIcontroller);
		btnAbout.addClickListener(GUIcontroller);
		btnImporters.addClickListener(GUIcontroller);
		btnExporters.addClickListener(GUIcontroller);
		btnManipulators.addClickListener(GUIcontroller);
		btnResults.addClickListener(GUIcontroller);
		
//		addButton();
	}
	
	@Override
	public void attach(){
		super.attach();
		main.setController(controller);
	}
	
	private void addButton(){
		Button b = new Button();
		b.setCaption(Integer.toString(buttonCount++));
		b.setWidth("20");
		b.setHeight("20");
		b.addClickListener(controller);
		itemList.addComponent(b);//FIXME --> first check, which itemlist, bla bla bla		
	}
	
	public VerticalLayout setWindow(WINDOW window, VerticalLayout currentWindow){
		currentWindow = currentWindow==null? start : currentWindow;
		currentWindow.setVisible(false);
		if (window.equals(WINDOW.IMPORTERS)){
			currentWindow = importers;
		}
		else if (window.equals(WINDOW.EXPORTERS)){
			currentWindow = exporters;
		}
		else if (window.equals(WINDOW.MANIPULATORS)){
			currentWindow = manipulators;
		}
		else{
			currentWindow = results;
		}
		currentWindow.setVisible(true);
		return currentWindow;
	}
}

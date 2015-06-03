package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.VIEW_NAME;

public class PepperGuiMain extends Panel{
	
	private PepperGuiView importers;
	private PepperGuiView exporters;
	private PepperGuiView manipulators;
	private PepperGuiView results;
	
	public PepperGuiMain(){
		super();
		importers = new PepperGuiImportersView();
		exporters = new PepperGuiExportersView();
		manipulators = new PepperGuiManipulatorsView();
		results = new PepperGuiResultsView();
	}
	
	protected void setView(VIEW_NAME view){
		if (view.equals(VIEW_NAME.IMPORTERS)){
			setContent((Component)importers);
		}
		else if (view.equals(VIEW_NAME.EXPORTERS)){
			setContent((Component)exporters);
		}
		else if (view.equals(VIEW_NAME.MANIPULATORS)){
			setContent((Component)manipulators);
		}
		else{
			setContent((Component)results);
		}
	}
}

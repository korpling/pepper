package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

@DesignRoot
public class PepperGUIMain extends VerticalLayout {
	private VerticalLayout layout;
	
	protected PepperGUIMain(PepperGUIController guiController) {
		Design.read("importers.xml", this);	
		
	}

}

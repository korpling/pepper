package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIComponentDictionary;

@DesignRoot
public abstract class PepperGuiView extends VerticalLayout implements View, PepperGUIComponentDictionary{
	public PepperGuiView(){
		super();
		Design.read(this);
	}
}

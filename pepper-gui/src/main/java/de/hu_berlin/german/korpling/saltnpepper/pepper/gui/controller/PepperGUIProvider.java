package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class PepperGUIProvider extends UIProvider {

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		return PepperGUIController.class;
	}

}

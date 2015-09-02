package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public interface Configurable {
	public TextField getPathField();
	public Table getDescriptionTable();
	public Table getPropertiesTable();
	public Component getDetailsComponent();
}

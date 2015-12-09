package org.corpus_tools.pepper.gui.components;

import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public interface Configurable {
	public TextField getPathField();
	public Table getDescriptionTable();
	public Table getPropertiesTable();
	public Component getDetailsComponent();
}

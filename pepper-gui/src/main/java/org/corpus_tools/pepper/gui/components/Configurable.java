package org.corpus_tools.pepper.gui.components;

import org.corpus_tools.pepper.common.MODULE_TYPE;

import com.vaadin.ui.Component;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public interface Configurable {
	public TextField getPathField();
	public Table getDescriptionTable();
	public Table getPropertiesTable();
	public Component getDetailsComponent();
	public ListSelect getModuleSelector();
	public void clearFields();
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIComponentDictionary;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

public class PepperGUIStepsMenu extends VerticalLayout implements PepperGUIComponentDictionary{
	protected static final String CAPTION_IMPORTERS = "Importers";
	protected static final String CAPTION_EXPORTERS = "Exporters";
	protected static final String CAPTION_MANIPULATORS = "Manipulators";
	protected static final String CAPTION_RESULTS = "Results";
	
	protected PepperGUIStepsMenu(PepperGUIController guiController) {
		/*DEBUG*/setCaption("I AM STEPSMENU");
		setWidth(PepperGUIHeader.GUI_HEADER_LEFT_WIDTH);
		setHeight(PepperGUIBody.GUI_BODY_HEIGHT);
		addComponent(new PepperGUIMenuStep(guiController, CAPTION_IMPORTERS, ID_BUTTON_IMPORTERS));
		addComponent(new PepperGUIMenuStep(guiController, CAPTION_EXPORTERS, ID_BUTTON_EXPORTERS));
		addComponent(new PepperGUIMenuStep(guiController, CAPTION_MANIPULATORS, ID_BUTTON_MANIPULATORS));
		addComponent(new PepperGUIMenuStep(guiController, CAPTION_RESULTS, ID_BUTTON_RESULTS));
	}
	
	private class PepperGUIMenuStep extends HorizontalLayout {
		private static final String GUI_STEPMENU_ELEM_HEIGHT = "204";		
		
		private PepperGUIMenuStep(PepperGUIController guiController, String buttonCaption, String id) {
			/*DEBUG*/setCaption("I AM A STEP SECTION");
			setWidth(PepperGUIHeader.GUI_HEADER_LEFT_WIDTH);
			setHeight(GUI_STEPMENU_ELEM_HEIGHT);
			Button b = new Button();
			b.setCaption(buttonCaption);
			b.setId(id);
			b.addClickListener(guiController);
			addComponent(b);
		}
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

@DesignRoot
public class PepperGuiResultsView extends PepperGuiView {
	private boolean isInit = false;
	private VerticalLayout barLayout;
	private Button btnProgress;
	
	public PepperGuiResultsView(){
		super();
		barLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
		btnProgress.setId(ID_BUTTON_PROGRESS);
	}
	
	@Override
	public void attach(){
		super.attach();
		if (!isInit){
			PepperGUIController controller = (PepperGUIController)getUI();
			btnProgress.addClickListener(controller);
			
			isInit = true;
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TextField getPathField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table getDescriptionTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table getPropertiesTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getDetailsComponent() {
		// TODO Auto-generated method stub
		return null;
	}	
}

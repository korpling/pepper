package org.corpus_tools.pepper.gui.components;

import org.corpus_tools.pepper.gui.controller.PepperGUIController;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@DesignRoot
public class PepperGuiResultsView extends PepperGuiView {
	private boolean isInit = false;
	private VerticalLayout barLayout;
	private Button btnProgress;
	private Button btnStart;
	private Button btnAbort;
	private Button btnSave;
	
	private static final String BUTTON_PROGRESS_CAPTION_MAKE_VISIBLE = "Details ▸";
	private static final String BUTTON_PROGRESS_CAPTION_MAKE_INVISIBLE = "Details ▾";
	
	public PepperGuiResultsView(){
		super();
		barLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
		btnProgress.setId(ID_BUTTON_PROGRESS);
		btnProgress.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Component detailsComponent = PepperGuiResultsView.this.getDetailsComponent();
				detailsComponent.setVisible(!detailsComponent.isVisible());
				((Button)event.getComponent()).setCaption( detailsComponent.isVisible()?
					PepperGuiResultsView.this.BUTTON_PROGRESS_CAPTION_MAKE_INVISIBLE :
					PepperGuiResultsView.this.BUTTON_PROGRESS_CAPTION_MAKE_VISIBLE);
			}
		});
		btnProgress.setImmediate(true);
		btnProgress.setEnabled(false);
		getDetailsComponent().setVisible(false);
	}
	
	@Override
	public void attach(){
		super.attach();
		if (!isInit){			
			btnStart.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					((PepperGUIController)getUI()).start();
				}
			});
		}
		isInit = true;
	}
	
	@Override
	public void update(){
		
	}

	@Override
	public TextField getPathField() {
		return null;
	}

	@Override
	public Table getDescriptionTable() {
		return null;
	}

	@Override
	public Table getPropertiesTable() {
		return null;
	}

	@Override
	public Component getDetailsComponent() {
		return barLayout;
	}

	@Override
	public ListSelect getModuleSelector() {
		return null;
	}
}

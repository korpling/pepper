package org.corpus_tools.pepper.gui.components;

import java.util.HashMap;
import java.util.Set;

import org.corpus_tools.pepper.gui.controller.PepperGUIController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field.ValueChangeEvent;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@DesignRoot
public class PepperGuiResultsView extends PepperGuiView implements ProgressDisplay{
	private boolean isInit = false;
	private VerticalLayout barLayout;
	private Button btnProgress;
	private Button btnStart;
	private Button btnAbort;
	private Button btnSave;
	private ProgressBar progressTotal;
	
	private static final Logger logger = LoggerFactory.getLogger(PepperGuiResultsView.class);
	
	/** A mapping from corpus path to progress display */
	private HashMap<String, ProgressBar> progressComponentMap = null; 
	
	private static final String BUTTON_PROGRESS_CAPTION_MAKE_VISIBLE = "Details ▸";
	private static final String BUTTON_PROGRESS_CAPTION_MAKE_INVISIBLE = "Details ▾";
	
	public PepperGuiResultsView(){
		super();
		progressComponentMap = new HashMap<String, ProgressBar>();
		progressTotal.setImmediate(true);
		barLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
		btnProgress.setId(ID_BUTTON_PROGRESS);
		btnProgress.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				PepperGuiResultsView.this.showDetails(!PepperGuiResultsView.this.getDetailsComponent().isVisible());				
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
					PepperGuiResultsView.this.btnProgress.setEnabled(true);
				}
			});
			((PepperGUIController)getUI()).registerProgressDisplay(this);
		}
		isInit = true;
	}
	
	@Override
	public void update(){
		if (!progressComponentMap.isEmpty()){
			float p = 0.0f;
			for (ProgressBar b : progressComponentMap.values()){
				p += b.getValue();
			}
			progressTotal.setValue(p / progressComponentMap.size());
		}
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

	@Override
	public void setProgress(String elementName, final float progress) {
		ProgressBar b = progressComponentMap.get(elementName);
		if (b != null){
			b.setValue(progress);
		}
	}

	@Override
	public Set<String> getElementNames() {
		return progressComponentMap.keySet();
	}

	@Override
	public void addElement(String elementName) {
		if (!progressComponentMap.containsKey(elementName)){
			ProgressBar b = new ProgressBar();
			b.setImmediate(true);
			b.setCaption(elementName);
			b.setWidth(progressTotal.getWidth(), progressTotal.getWidthUnits());
			barLayout.addComponent(b);
			progressComponentMap.put(elementName, b);
		}
		update();
	}

	@Override
	public void addElements(Set<String> elementNames) {
		for (String e : elementNames){
			addElement(e);
		}
	}

	@Override
	public void removeElement(String elementName) {
		if (progressComponentMap.containsKey(elementName)){
			barLayout.removeComponent(progressComponentMap.get(elementName));
			progressComponentMap.remove(elementName);			
		}
		update();
	}

	@Override
	public void removeAllElements() {
		barLayout.removeAllComponents();
		progressComponentMap.clear();
	}

	@Override
	public void showDetails(boolean showDetails) {
		Component detailsComponent = getDetailsComponent();
		detailsComponent.setVisible(showDetails);
		btnProgress.setCaption( showDetails?
			PepperGuiResultsView.this.BUTTON_PROGRESS_CAPTION_MAKE_INVISIBLE :
			PepperGuiResultsView.this.BUTTON_PROGRESS_CAPTION_MAKE_VISIBLE);
	}
}

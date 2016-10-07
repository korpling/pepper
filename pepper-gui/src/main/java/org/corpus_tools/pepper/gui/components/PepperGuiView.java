package org.corpus_tools.pepper.gui.components;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.gui.controller.PepperGUIComponentDictionary;
import org.corpus_tools.pepper.gui.controller.PepperGUIController;
import org.corpus_tools.pepper.service.adapters.CorpusDescMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModulePropertyMarshallable;
import org.corpus_tools.pepper.service.adapters.StepDescMarshallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public abstract class PepperGuiView extends VerticalLayout implements View, Configurable, PepperGUIComponentDictionary{
	private StepDescMarshallable config = null;
	protected MODULE_TYPE type;
	protected HashMap<String, PepperModuleDescMarshallable> availableModules = null;
	protected HashMap<String, List<Item>> module2ItemsMap = null;
	/** Maps property name to mapping int->obj, where index is the value of the property for the current index */
	protected HashMap<String, HashMap<Integer, Object>> modifiedProperties = null;
	protected String lastSelectedModule = null;
	private int currentIndex = 0;
	
	private boolean isInit = false;
	
	public static final Logger logger = LoggerFactory.getLogger(PepperGuiView.class);
	
	public static final String WARN_METHOD_NA(Class<?> clazz){
		return "Method not implemented for class "+clazz;
	}
	
	private ArrayList<StepDescMarshallable> configurations = null; 
	
	public PepperGuiView(){
		super();
		Design.read(this);
		configurations = new ArrayList<StepDescMarshallable>();	
		module2ItemsMap = new HashMap<String, List<Item>>();
		modifiedProperties = new HashMap<>();
	}
	
	@Override
	public void attach(){
		super.attach();
		if (!isInit){
			PepperGUIController controller = (PepperGUIController)getUI();
			if (getPathField()!=null){
				getPathField().addTextChangeListener(controller);
				getPathField().setTextChangeEventMode(TextChangeEventMode.LAZY);
			}
			this.addLayoutClickListener(controller);
			if (config==null){
				if (!configurations.isEmpty()){
					config = configurations.get(configurations.size()-1);
				} else {
					StepDescMarshallable stepDesc = new StepDescMarshallable();
					stepDesc.setModuleType(getModuleType());
					setConfig(stepDesc);
				}
			}
			if (getErrorHandler()!=null){
				setErrorHandler((PepperGUIController)getUI());
			}
			{
				ListSelect moduleSelector = getModuleSelector();
				if (moduleSelector != null){
					moduleSelector.addValueChangeListener(new ValueChangeListener() {					
						@Override
						public void valueChange(ValueChangeEvent event) {
							logger.info("New value: " + event.getProperty().getValue());
							PepperGuiView.this.update(); 
						}
					});
				}
				
			}
			{
				Table propTable = getPropertiesTable();
				if (propTable != null){
					propTable.addContainerProperty("Property", String.class, "");
					propTable.addContainerProperty("Value", AbstractComponent.class, "");
					propTable.addContainerProperty("Help", Button.class, null);
				}
			}
		}
		isInit = true;
	}
	
	protected int getCurrentIndex(){
		return this.currentIndex;
	}

	/*
	 * TODO make private/protected if possible (and delete from interface, this is a local thing)
	 */
	@Override
	public void setConfig(StepDescMarshallable stepDesc) {
		if (getModuleType() != null){
			if (stepDesc.getModuleType().equals(getModuleType())){
				this.config = stepDesc;
				if (!configurations.contains(config)){
					configurations.add(config);
				}
				update();
			}
		}		
	}
	
	@Override
	public final void setConfig(int id){
		currentIndex = id;
		setConfig(configurations.get(id));
		if (getPathField() != null){
			getPathField().setValue(config.getCorpusDesc().getCorpusPathURI());
		}
		if (getModuleSelector() != null){
			getModuleSelector().setValue(config.getName());
		}
		//TODO table stuff
		
	}

	@Override
	public final StepDescMarshallable getConfig() {
		return config;
	}
	
	@Override
	public final MODULE_TYPE getModuleType(){
		return type;
	}	
	
	protected final void setModuleType(MODULE_TYPE type){
		if (this.type==null){
			this.type = type;
		}
	}
	
	@Override
	public final void display(boolean visible, Component... c){		
		for (Component component : c){
			component.setVisible(visible);
		}
	}
	
	@Override
	public final void add(){
		StepDescMarshallable stepDesc = new StepDescMarshallable();
		stepDesc.setCorpusDesc(new CorpusDescMarshallable());
		stepDesc.getCorpusDesc().setCorpusPathURI("");
		stepDesc.setModuleType(type);
		setConfig(stepDesc);
		clearFields();
		update();
	}
	
	@Override
	public int getSize(){
		return configurations.size();
	}
	
	/*
	 * This method is problematic: It handles too much at a time. When one thing is changed, everything will be reset.
	 */
	@Override
	public void update() {
		TextField pathField = getPathField();
		boolean isManipulator = MODULE_TYPE.MANIPULATOR.equals(getModuleType());
		boolean up = getConfig()!=null 
				 && ((pathField!=null
				 && getConfig().getCorpusDesc() != null 
				 && getConfig().getCorpusDesc().getCorpusPathURI()!=null) || isManipulator);  
		if (up){	
			if (isManipulator || (new File(getConfig().getCorpusDesc().getCorpusPathURI().toString())).exists()){
				display(true, getDetailsComponent());
			}
			if (!isManipulator){
				pathField.removeTextChangeListener((PepperGUIController)getUI());
				pathField.setValue(getConfig().getCorpusDesc().getCorpusPathURI().toString());
				pathField.addTextChangeListener((PepperGUIController)getUI());
			}
			Object val = getModuleSelector().getValue();
			if (val != null && !val.toString().trim().isEmpty()){
				Table propTable = getPropertiesTable();
				propTable.removeAllItems();
				PepperModuleDescMarshallable selectedModule = availableModules.get(val.toString());
				List<Item> items = module2ItemsMap.get(selectedModule);
				if (!selectedModule.getName().equals(lastSelectedModule)){
					if (!selectedModule.getSupportedFormats().isEmpty()) {
						config.getCorpusDesc().setFormatDesc(selectedModule.getSupportedFormats().get(0)); //FIXME
					}
					config.setName(selectedModule.getName());
					if (items != null){
						String propertyName = null;
						for (Item item : items){							
							Item itm = propTable.addItem(item.getItemProperty("Property").getValue());
							propertyName = (String) itm.getItemProperty("Property").getValue();
							itm.getItemProperty("Property").setValue(item.getItemProperty("Property").getValue());
							itm.getItemProperty("Value").setValue(item.getItemProperty("Value").getValue());							
							itm.getItemProperty("Help").setValue(item.getItemProperty("Help").getValue());
							Object value = modifiedProperties.get(propertyName).get(currentIndex);
							if (value != null){
								AbstractField valueField = ((AbstractField)itm.getItemProperty("Value").getValue());
								valueField.setValue(value);
							}
						}
					} else {
						items = new ArrayList<Item>();
						for (PepperModulePropertyMarshallable<?> pmp : selectedModule.getProperties()){					
							Item item = propTable.addItem(pmp.getName());
							item.getItemProperty("Property").setValue(pmp.getName());									
							final boolean isBooleanValued = Boolean.class.isAssignableFrom((pmp.getType()));							
							AbstractField c = isBooleanValued? new ComboBox() : new TextField();						
							if (isBooleanValued){
								((ComboBox)c).addItems("True", "False");
							}
							if (pmp.getRequired()){
								c.setValue(pmp.getValue());
							}
							c.setImmediate(true);
							/*HELP BUTTON*/
							Button help = new Button(FontAwesome.QUESTION);
							final PepperModulePropertyMarshallable<?> mp = pmp;
							help.addClickListener(new ClickListener() {							
								@Override
								public void buttonClick(ClickEvent event) {
									//TODO replace by helper window
									//TODO introduce style interface or enum with heights etc
									Notification.show(mp.getDescription().replaceAll("(.{100})", "$1"+System.lineSeparator()));
								}
							});						
							item.getItemProperty("Value").setValue(c);
							item.getItemProperty("Help").setValue(help);
							items.add(item);
						}
						module2ItemsMap.put(selectedModule.getName(), items);
					}
					lastSelectedModule = selectedModule.getName();
				}
			}
		}
	}
	
	@Override
	public final List<StepDescMarshallable> getAllConfigurations(){
		return Collections.unmodifiableList(configurations);
	}
	
	@Override
	public final void setAvailableModules(Collection<PepperModuleDescMarshallable> modules){
		ListSelect moduleSelector = getModuleSelector();
		if (moduleSelector != null){
			availableModules = new HashMap<String, PepperModuleDescMarshallable>();
			for (PepperModuleDescMarshallable module : modules){
				if (type.equals(module.getModuleType())){
					moduleSelector.addItem(module.getName());
					availableModules.put(module.getName(), module);
				}
			}			
		} else {
			throw new UnsupportedOperationException(WARN_METHOD_NA(this.getClass()));
		}
	}
	
	@Override
	public void clearFields(){
		if (getPathField() != null){
			getPathField().clear();
			display(false, getDetailsComponent());
		}
		if (getModuleSelector() != null){
			getModuleSelector().unselect(getModuleSelector().getValue());			
		}
	}
}

package org.corpus_tools.pepper.gui.components;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ListSelect;
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
	/** Maps property name to mapping int->obj, where index is the value of the property for the current index 
	 * TODO remove*/
	protected HashMap<String, HashMap<Integer, Object>> modifiedProperties = null;
	protected HashMap<Integer, HashMap<String, Object>> mdfProps = null;
	protected String lastSelectedModule = null;
	private int currentIndex = 0;
	
	public static final Logger logger = LoggerFactory.getLogger(PepperGuiView.class);
	
	public static final String TABLE_PROP_PROPERTY = "Property";
	public static final String TABLE_PROP_VALUE = "Value";
	public static final String TABLE_PROP_HELP = "Help";
	
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
		if (!isInit()){
			PepperGUIController controller = (PepperGUIController)getUI();
			if (getPathField()!=null){				
				getPathField().addTextChangeListener(new TextChangeListener() {					
					@Override
					public void textChange(TextChangeEvent event) {
						String path = event.getText();
						if ((new File(path)).exists() || !MODULE_TYPE.IMPORTER.equals(getModuleType())){
							StepDescMarshallable config = PepperGuiView.this.getConfig();
							if (config==null){
								StepDescMarshallable newConfig = new StepDescMarshallable();
								newConfig.setModuleType(PepperGuiView.this.getModuleType());
							}
							if (config.getCorpusDesc() == null){
								config.setCorpusDesc(new CorpusDescMarshallable());
							}
							config.getCorpusDesc().setCorpusPathURI(path.replace("file://", ""));
							PepperGuiView.this.display(true, PepperGuiView.this.getDetailsComponent());
							PepperGuiView.this.getModuleSelector().unselect(PepperGuiView.this.getModuleSelector().getValue());
						} else {
							PepperGuiView.this.display(false, PepperGuiView.this.getDetailsComponent());
						}
					}
				});
				getPathField().setTextChangeEventMode(TextChangeEventMode.LAZY);
			}
			this.addLayoutClickListener(controller);
			if (config==null){
				if (!configurations.isEmpty()){
					config = configurations.get(configurations.size()-1);
				} else {
					StepDescMarshallable stepDesc = new StepDescMarshallable();
					stepDesc.setModuleType(getModuleType());
					stepDesc.setCorpusDesc(new CorpusDescMarshallable());
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
							Object val = event.getProperty().getValue();
							if (val != null){
								StepDescMarshallable config = PepperGuiView.this.getConfig();
								if (config == null){
									config = new StepDescMarshallable();
								}
								if (config.getCorpusDesc() == null){
									config.setCorpusDesc(new CorpusDescMarshallable());
								}
								config.setName(event.getProperty().getValue().toString());							
								/*setup properties table*/
								Table propTable = PepperGuiView.this.getPropertiesTable();
								String module = val.toString();
								logger.info("MODULE SELECTED: "+module);
								if (module != lastSelectedModule){
									config.setName(module);
									lastSelectedModule = module;
									config.getProperties().getProperties().clear();
									PepperModuleDescMarshallable mdesc = availableModules.get(module);
									Item item = null;
									AbstractField valueSetter = null;
									for (PepperModulePropertyMarshallable<?> p : mdesc.getProperties()){
										config.getProperties().getProperties().add(p);
										item = propTable.addItem(p.getName());
										item.getItemProperty(TABLE_PROP_PROPERTY).setValue(p.getName());
										if (p.getType().isAssignableFrom(Boolean.class)){
											valueSetter = new ComboBox();
											((ComboBox)valueSetter).addItems(Boolean.TRUE, Boolean.FALSE);
										} else {
											valueSetter = new TextField();										
										}
										if (p.getRequired()){
											valueSetter.setValue(p.getValue());
										}
										item.getItemProperty(TABLE_PROP_VALUE).setValue(valueSetter);
										item.getItemProperty(TABLE_PROP_HELP).setValue(new HelpButton(p.getDescription()));								
										final String pname = p.getName();
										valueSetter.addValueChangeListener(new ValueChangeListener() {										
											@Override
											public void valueChange(ValueChangeEvent event) {
												PepperGuiView.this.fillPropertiesTable();
											}
										});
									}
								}
							}							
						}
					});
				}
				
			}
			{
				mdfProps = new HashMap<>();				
				Table propTable = getPropertiesTable();
				if (propTable != null){
					propTable.addContainerProperty(TABLE_PROP_PROPERTY, String.class, "");
					propTable.addContainerProperty(TABLE_PROP_VALUE, AbstractComponent.class, "");
					propTable.addContainerProperty(TABLE_PROP_HELP, Button.class, null);
				}
			}
		}
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
			}
		}
		if (getPathField() != null){
			getPathField().setValue(config.getCorpusDesc().getCorpusPathURI());
		}
		if (getModuleSelector() != null){
			getModuleSelector().setValue(config.getName());
		}		
		fillPropertiesTable();
	}
	
	/*
	 * TODO make dynamic	
	 */
	private void fillPropertiesTable(){
		boolean probsAvailable = config.getProperties() != null && config.getProperties().getProperties() != null && !config.getProperties().getProperties().isEmpty();
		if (getPropertiesTable() != null){
			List<PepperModulePropertyMarshallable<?>> properties = 
					probsAvailable?
							config.getProperties().getProperties() :
								(availableModules.containsKey(config.getName())? availableModules.get(config.getName()).getProperties() : Collections.<PepperModulePropertyMarshallable<?>>emptyList());
			if (!probsAvailable){
				config.getProperties().getProperties().addAll(properties);
			}
			for (PepperModulePropertyMarshallable<?> p : properties){
				addPropertyItem(p);
			}
		}
	}
	
	/*
	 * TODO make dynamic	
	 */
	private void addPropertyItem(PepperModulePropertyMarshallable<?> property){
		Table t = getPropertiesTable();
		if (t != null){
			Item itm = t.addItem(property.getName());
			itm.getItemProperty(TABLE_PROP_PROPERTY).setValue(property.getName());
			AbstractField valueSetter = null;
			if (property.getType().isAssignableFrom(Boolean.class)){
				valueSetter = new ComboBox();
				((ComboBox)valueSetter).addItems(Boolean.TRUE, Boolean.FALSE);
			}
			else if (property.getType().isAssignableFrom(String.class)){
				valueSetter = new TextField();
			}
			if (property.getRequired()){
				valueSetter.setValue(property.getValue());
			}
		}
	}	
	
	@Override
	public final void setConfig(int id){
		currentIndex = id;
		setConfig(configurations.get(id));
	}

	@Override
	public final StepDescMarshallable getConfig(){
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
	}
	
	@Override
	public int getSize(){
		return configurations.size();
	}
	
	/*
	 * This method is problematic: It handles too much at a time. When one thing is changed, everything will be reset.
	 * --> Split into writeConfig and readConfig
	 */
	@Override
	public void update() {
		// TODO remove
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

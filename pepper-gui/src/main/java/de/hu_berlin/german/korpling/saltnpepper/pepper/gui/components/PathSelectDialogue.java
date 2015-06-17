package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SystemUtils;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIComponentDictionary;
import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

@DesignRoot
public class PathSelectDialogue extends AbsoluteLayout implements PepperGUIComponentDictionary{
	
	private boolean isInit = false;
	private ListSelect list;
	private PathLayout bcPath;
	private Button btnSelect;
	private TextField pathField;
	private Label lblFinalPath;
	private VerticalLayout rootListLayout;
	
	public PathSelectDialogue(){
		super();		
		Design.read(this);
		list.setId(ID_PATH_SELECT);
		list.setItemCaptionMode(ItemCaptionMode.EXPLICIT);//FIXME
		list.setImmediate(true);
		btnSelect.setClickShortcut(KeyCode.ENTER);
		btnSelect.setId(ID_BUTTON_PATH_SELECT);
		pathField.setId(ID_PATH_FIELD);
		pathField.setImmediate(true);	
		lblFinalPath.setImmediate(true);
		setImmediate(true);
		if (SystemUtils.IS_OS_WINDOWS){
			/*fill in buttons for drives*/
		}
	}
		
	@SuppressWarnings("unchecked")
	public void reload(FilesystemContainer dataSource, PepperGUIController controller, boolean growByOne){		
		if (!dataSource.getItemIds().isEmpty()){
			list.removeValueChangeListener(controller);//TODO is there a better way?
			list.setContainerDataSource(dataSource);
			Collection<File> folders = (Collection<File>)list.getItemIds();
			for (File itemId : folders){
				String caption = itemId.getName();
				list.setItemCaption(itemId, caption);
				list.setItemIcon(itemId, FontAwesome.FOLDER);
			}
			File f = folders.iterator().next();		
			String path = f.getParent();
			if (growByOne){
				bcPath.addNode(path, controller);
			}
			else {bcPath.set(path==null? "/" : path/*TODO make OS-sensitive*/, controller);}
			if (!growByOne) {setPathFieldValue(path);}
			setPathLabelValue(path);
			list.addValueChangeListener(controller);
		}		
	}
	
	public void setPathFieldValue(String value){
		pathField.setValue(value);
	}
	
	public void setPathLabelValue(String value){
		lblFinalPath.setCaption(value);
	}
	
	public String getListValue(){
		return list.getValue().toString();
	}

	
	public String getSelectedPath(){
		return pathField.getValue();
	}
	
	@Override
	public void attach(){
		super.attach();
		if (!isInit){			
			PepperGUIController controller = (PepperGUIController)getUI();
			addLayoutClickListener(controller);		
			pathField.addTextChangeListener(controller);
			pathField.setTextChangeEventMode(TextChangeEventMode.LAZY);
			list.addValueChangeListener(controller);
			btnSelect.addClickListener(controller);
			isInit = true;
		}
	}
}

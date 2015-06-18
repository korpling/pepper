package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang3.SystemUtils;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
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
	private AbsoluteLayout rootListLayout;
	
	public static final String ROOT_BUTTON_WIDTH = "50";
	public static final String PATH_LIST_STYLE = "pathList";
	
	public PathSelectDialogue(){
		super();		
		Design.read(this);
		list.setId(ID_PATH_SELECT);
		list.setImmediate(true);
		list.addStyleName(PATH_LIST_STYLE);
		btnSelect.setClickShortcut(KeyCode.ENTER);
		btnSelect.setId(ID_BUTTON_PATH_SELECT);
		pathField.setId(ID_PATH_FIELD);
		pathField.setImmediate(true);	
		lblFinalPath.setImmediate(true);
		setImmediate(true);				
	}
		
	@SuppressWarnings("unchecked")
	public void reload(FilesystemContainer dataSource, PepperGUIController controller, boolean growByOne){		
		if (!dataSource.getItemIds().isEmpty()){
			list.removeValueChangeListener(controller);//TODO is there a better way?
			pathField.removeTextChangeListener(controller);//TODO is there a better way?
			list.setContainerDataSource(dataSource);
			Collection<File> folders = (Collection<File>)list.getItemIds();
			for (File itemId : folders){
				String caption = itemId.getName();
				list.setItemCaption(itemId, caption);
//				list.setItemIcon(itemId, FontAwesome.FOLDER);//does not work, not supported (yet?)
			}
			File f = folders.iterator().next();		
			String path = f.getParent();
			if (growByOne){
				bcPath.addNode(path, controller);
			}
			else {bcPath.set(path==null? "/" : path/*TODO make OS-sensitive*/, controller);}
			if (!growByOne) {pathField.setValue(path);}
			setPathLabelValue(path);
			pathField.setValue(path);
			list.addValueChangeListener(controller);
			pathField.addTextChangeListener(controller);
		}		
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
			/*add buttons for drives and home folder*/
			int top = 0;
			Button b = new Button();
			b.setWidth(ROOT_BUTTON_WIDTH);
			b.setIcon(FontAwesome.HOME);
			b.setId(PATH_PREFIX.concat(SystemUtils.getUserHome().getAbsolutePath()));
			b.addClickListener(controller);
			rootListLayout.addComponent(b);		
			for (File f : File.listRoots()){
				top+=40;
				b = new Button(f.getAbsolutePath());
				rootListLayout.addComponent(b, "top:"+top);			
				b.setId(PATH_PREFIX.concat(f.getAbsolutePath()));
				b.addClickListener(controller);
				b.setWidth(ROOT_BUTTON_WIDTH);
			}
			if (!SystemUtils.IS_OS_WINDOWS){
				/* get mounted drives */
				//TODO
				/*TODO after having implemented this, we should
				 * refactor this method: First build a huge list
				 * containing 1) Home dir 2) roots 3) mounted 
				 * devices. Then add all the buttons.
				 */
			}
			
			isInit = true;
		}
	}
}

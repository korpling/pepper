package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.SystemUtils;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
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
	private Button btnRefresh;
	

	public static final String PATH_LIST_STYLE = "pathList";
	public static final String MOUNT_FILE_PATH = "/proc/mounts";
	private static final float ROOT_BUTTON_WIDTH_IN_EM = 5;
	
	public PathSelectDialogue(){
		super();	
		Design.read(this);
		list.setId(ID_PATH_SELECT);
		list.setImmediate(true);
		list.addStyleName(PATH_LIST_STYLE);
		btnSelect.setClickShortcut(KeyCode.ENTER);
		btnSelect.setId(ID_BUTTON_PATH_SELECT);
		pathField.setId(ID_PATH_FIELD_DIALOGUE);
		pathField.setImmediate(true);	
		lblFinalPath.setImmediate(true);
		setImmediate(true);		
		rootListLayout.setImmediate(true);
		btnRefresh.setIcon(FontAwesome.REFRESH);
		btnRefresh.setId(ID_BUTTON_REFRESH_ROOTS);
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
		return lblFinalPath.getCaption();
	}
	
	//TODO use dynamic programming, compare to PathLayout, do not compute again and again
	public void refreshRoots(){
		PepperGUIController controller = (PepperGUIController)getUI();
		addRootButton(SystemUtils.getUserHome(), FontAwesome.HOME, controller, 0);
		int top = 40;		
		Resource icon = null;
		for (File f : File.listRoots()){
			icon = f.getAbsolutePath().equals(File.separator)? null : FontAwesome.HDD_O;
			addRootButton(f, icon, controller, top);
			top+=40;
		}		
		if (SystemUtils.IS_OS_LINUX){
			/* get mounted drives */
			try {
				BufferedReader reader = new BufferedReader(new FileReader(new File(MOUNT_FILE_PATH)));
				String line = reader.readLine();
				while (line!=null){
					String[] values = line.split(",")[0].split(" ");
					if (values[0].startsWith("/dev/")&&!values[1].equals("/")){							
						addRootButton(new File(values[1]), FontAwesome.HDD_O, controller, top);
						top+=40;
					}	
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
				//TODO LOGGER
			}
		}
		else if (SystemUtils.IS_OS_MAC){
			//TODO
		}			
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
			isInit = true;
		}
		refreshRoots();
	}
	
	private void addRootButton(File f, Resource icon, PepperGUIController controller, int offset){
		String caption = f.getName()==null||f.getName().isEmpty()? File.separator : f.getName();
		caption = caption.length()>3? caption.substring(0,3).concat("\u2026"): caption;
		Button b = new Button(caption);
		b.setWidth(ROOT_BUTTON_WIDTH_IN_EM, Unit.EM);
		b.setImmediate(true);		
		b.setDescription(f.getAbsolutePath());
		b.setId(PATH_PREFIX.concat(f.getAbsolutePath()));
		b.addClickListener(controller);
		if (icon!=null){b.setIcon(icon);}
		rootListLayout.addComponent(b, "top:".concat(Integer.toString(offset)));		
	}
}

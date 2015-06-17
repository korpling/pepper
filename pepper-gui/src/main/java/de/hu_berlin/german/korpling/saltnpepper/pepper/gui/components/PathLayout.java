package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.components;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

public class PathLayout extends HorizontalLayout {
	/** this is a mapping from button to triggered path/file */
	private ButtonNode root = null;
	
	public PathLayout(){
		super();
		setImmediate(true);
	}
	
	//TODO make OS-sensitive
	protected void set(String absolutePath, PepperGUIController controller){		
		try{//try-catch-clause for debug only(!)			
			removeAllComponents();
			String[] rt = {"/"};//TODO make OS-sensitive			
			String[] pathSegments = absolutePath.length()>1? ArrayUtils.add(absolutePath.substring(/*TODO make OS-sensitive*/1).split(File.separator), 0, "/") : rt;//TODO make OS-sensitive
			String id = pathSegments[0];
			ButtonNode node = root;
			ButtonNode prev = null;
			int k = 0;			
			while (node!=null && node.getButton().getId().equals(id) && k<pathSegments.length-1){
				node.display();
				prev = node;
				node = node.getNext();
				id+=pathSegments[++k].concat(File.separator);
			}
			if (k==pathSegments.length){
				node.head();
			}
			else {
				node = new ButtonNode(pathSegments[k], id, controller);
				if (prev==null){
					root = node;
				}
				else {
					prev.setNext(node);
					node.display();
				}
				for (int i=k+1; i<pathSegments.length; i++){
					id+=pathSegments[i].concat(File.separator);
					node.setNext(pathSegments[i], id, controller);
					node.display();
					node = node.getNext();
				}				
				node.display();
			}
		}
		catch (Exception e){	
			e.printStackTrace();
			Notification.show(e.toString(), Notification.TYPE_ERROR_MESSAGE);
		}
	}
	
	/**We decided to set this method deprecated, since the only components added are buttons embedded in button nodes
	 * and those will be displayed by the button node's display() method */
	@Deprecated
	@Override
	public void addComponent(Component c){}
		
	public void addNode(String nodeValue, PepperGUIController controller){
		ButtonNode node = root;
		while(node.getNext()!=null){
			node = node.getNext();
		}
		node.setNext(nodeValue.substring(nodeValue.lastIndexOf(File.separator)+1), nodeValue, controller);
		node.getNext().display();
	}
	
	private class ButtonNode{
		private Button button = null;
		private ButtonNode next = null;
		
		protected ButtonNode(String caption, String buttonId, PepperGUIController controller){
			button = new Button(caption);			
			button.setId(buttonId);
			button.setImmediate(true);
			button.addClickListener(controller);
		}
		
		protected Button getButton(){
			return button;
		}
		
		protected ButtonNode getNext(){
			return next;
		}
		
		protected void setNext(String caption, String buttonId, PepperGUIController controller){
			next = new ButtonNode(caption, buttonId, controller);
		}
		
		protected void setNext(ButtonNode node){
			next = node;
		}
		
		/** sets this element to head, means next is set to null */
		protected void head(){
			next = null;
		}
		
		protected ButtonNode display(){
			PathLayout.super.addComponent(button);
			return this;
		}
		
		@Override
		public String toString(){
			return (new StringBuilder()).append(button.getId()).append(" ").append(button.getCaption()).append(" ").toString();
		}
	}
}

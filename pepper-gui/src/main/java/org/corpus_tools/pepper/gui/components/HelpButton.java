package org.corpus_tools.pepper.gui.components;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

public class HelpButton extends Button implements Button.ClickListener{

	private String message = null;
	
	public HelpButton(String helpMessage){
		this.message = helpMessage;
		this.setIcon(FontAwesome.QUESTION);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		Notification.show(message.replaceAll("(.{100})", "$1"+System.lineSeparator()));
	}

}

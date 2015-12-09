package org.corpus_tools.pepper.gui.controller.tests;

import org.corpus_tools.pepper.gui.controller.PepperGUIController;
import org.junit.Before;

public class PepperGUIControllerTest {
	private PepperGUIController fixture;
	
	@Before
	public void setUp(){
		setFixture(new PepperGUIController());		
	}
	
	public void setFixture(PepperGUIController fixture){
		this.fixture = fixture;
	}
	
	public PepperGUIController getFixture(){
		return fixture;
	}
	
	
}

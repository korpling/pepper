package de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.tests;

import org.junit.Before;

import de.hu_berlin.german.korpling.saltnpepper.pepper.gui.controller.PepperGUIController;

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

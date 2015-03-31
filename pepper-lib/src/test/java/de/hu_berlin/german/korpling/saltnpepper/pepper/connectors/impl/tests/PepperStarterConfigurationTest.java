package de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.impl.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.cli.PepperStarterConfiguration;

public class PepperStarterConfigurationTest {
	private PepperStarterConfiguration fixture = null;	
	private static final Logger logger = LoggerFactory.getLogger(PepperStarterConfigurationTest.class);
	
	@Before
	public void setUp(){
		setFixture(new PepperStarterConfiguration());
	}
	
	private void setFixture(PepperStarterConfiguration fixture){
		this.fixture = fixture;
	}
	
	private PepperStarterConfiguration getFixture(){
		return fixture;
	}
	
	@Test
	public void testLoad_propertiesFile(){
		PepperStarterConfiguration pSC = getFixture();
		File propertiesFile = new File("./conf/pepper.properties");
		assertTrue(propertiesFile.exists());
		pSC.load();
		assertNotNull(pSC.getConfFolder());
	}
}

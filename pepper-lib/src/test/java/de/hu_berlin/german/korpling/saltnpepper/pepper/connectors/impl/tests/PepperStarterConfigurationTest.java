/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepper.connectors.impl.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

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
package org.corpus_tools.pepper.modules.impl;

import static org.junit.Assert.fail;

import org.corpus_tools.pepper.impl.PepperMapperControllerImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperMapperController;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.junit.Before;
import org.junit.Test;

public class PepperMapperControllerImplTest {

	private PepperMapperController fixture = null;

	public PepperMapperController getFixture() {
		return fixture;
	}

	public void setFixture(PepperMapperController fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp() throws Exception {
		setFixture(new PepperMapperControllerImpl(null, "testThread"));
		getFixture().setPepperMapper(new PepperMapperImpl());
		getFixture().getPepperMapper().setProperties(new PepperModuleProperties());
	}

	@Test
	public void testSetIdentifier() {
		try {
			getFixture().setIdentifier(null);
			fail();
		} catch (PepperModuleException e) {
			// do nothing
		}
	}
}

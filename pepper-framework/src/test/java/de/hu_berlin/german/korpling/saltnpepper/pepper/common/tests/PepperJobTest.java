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
package de.hu_berlin.german.korpling.saltnpepper.pepper.common.tests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl;

public class PepperJobTest extends PepperJob{

	private PepperJob fixture= null;
	
	public PepperJob getFixture() {
		return fixture;
	}

	public void setFixture(PepperJob fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp(){
		setFixture(this);
	}
	
	/**
	 * tests if {@link PepperJobImpl#toString()} always returns a correct value
	 */
	@Test
	public void testToString() {
		assertNotNull(getFixture().toString());
	}

	@Override
	public void convert() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void convertFrom() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void convertTo() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void load(URI uri) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getStatusReport() {
		throw new UnsupportedOperationException();
	}

	@Override
	public URI save(URI uri) {
		throw new UnsupportedOperationException();
	}
}

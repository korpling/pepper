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
package org.corpus_tools.pepper.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class JOB_STATUSTest {

	private JOB_STATUS fixture = null;

	public JOB_STATUS getFixture() {
		return fixture;
	}

	public void setFixture(JOB_STATUS fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInProgress() {
		assertTrue(JOB_STATUS.INITIALIZING.isInProgress());
		assertTrue(JOB_STATUS.IMPORTING_CORPUS_STRUCTURE.isInProgress());
		assertTrue(JOB_STATUS.IMPORTING_DOCUMENT_STRUCTURE.isInProgress());

		assertFalse(JOB_STATUS.NOT_STARTED.isInProgress());
		assertFalse(JOB_STATUS.ENDED.isInProgress());
		assertFalse(JOB_STATUS.ENDED_WITH_ERRORS.isInProgress());
	}

}

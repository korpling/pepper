package de.hu_berlin.german.korpling.saltnpepper.pepper.common.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.JOB_STATUS;

public class JOB_STATUSTest {

	private JOB_STATUS fixture= null;
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

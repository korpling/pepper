package de.hu_berlin.german.korpling.saltnpepper.pepper.common.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;

public class PepperUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBreakString(){
		assertEquals("do nothing", PepperUtil.breakString("do nothing"));
		assertEquals("do no\nthing", PepperUtil.breakString("do nothing", 5));
		assertEquals(">do n\n>othi\n>ng", PepperUtil.breakString(">","do nothing", 5));
	}

}

package de.hu_berlin.german.korpling.saltnpepper.pepper.common.tests;

import static org.junit.Assert.*;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;

public class CorpusDescTest {
	
	private CorpusDesc fixture= null;

	public CorpusDesc getFixture() {
		return fixture;
	}

	public void setFixture(CorpusDesc fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp() throws Exception {
		setFixture(new CorpusDesc());
	}

	@Test
	public void testSetGetCorpusPath() {
		URI uri= URI.createURI("somewhere");
		getFixture().setCorpusPath(uri);
		assertEquals(getFixture().getCorpusPath(), uri);
	}
	
	@Test
	public void testSetGetFormatDesc(){
		assertNotNull(getFixture().getFormatDesc());
		
		FormatDesc oldFormatDesc= getFixture().getFormatDesc();
		FormatDesc newFormatDesc= new FormatDesc();
		getFixture().setFormatDesc(newFormatDesc);
		assertEquals(newFormatDesc, getFixture().getFormatDesc());
		assertNotEquals(oldFormatDesc, getFixture().getFormatDesc());
	}
	
	@Test
	public void testToString(){
		assertNotNull(getFixture().toString());
	}

}

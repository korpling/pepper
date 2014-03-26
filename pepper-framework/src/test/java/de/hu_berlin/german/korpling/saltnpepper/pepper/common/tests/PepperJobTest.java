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
}

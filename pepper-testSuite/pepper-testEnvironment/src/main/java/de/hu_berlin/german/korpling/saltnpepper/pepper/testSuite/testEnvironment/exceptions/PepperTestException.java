package de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.testEnvironment.exceptions;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;

public class PepperTestException extends PepperException{
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 7152733137673010658L;

	public PepperTestException()
	{ super(); }
	
    public PepperTestException(String s)
    { super(s); }
    
	public PepperTestException(String s, Throwable ex)
	{super(s, ex); }
}

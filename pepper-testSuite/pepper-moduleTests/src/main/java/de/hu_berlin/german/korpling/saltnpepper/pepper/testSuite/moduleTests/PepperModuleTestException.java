package de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;

/**
 * This Exception class can be used for Tests (i.e. JUnit tests) of {@link PepperModule} classes.
 *  
 * @author Florian Zipser
 *
 */
public class PepperModuleTestException extends PepperException 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5431375961043975520L;

	public PepperModuleTestException()
	{ super(); }
	
    public PepperModuleTestException(String s)
    { super(s); }
    
	public PepperModuleTestException(String s, Throwable ex)
	{super(s, ex); }
}

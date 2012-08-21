package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.exceptions;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;

public class PepperModulePropertyException extends PepperModuleException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8100717436792012869L;
	
	public PepperModulePropertyException()
	{ super(); }
	
    public PepperModulePropertyException(String s)
    { super(s); }
    
	public PepperModulePropertyException(String s, Throwable ex)
	{super(s, ex); }
}

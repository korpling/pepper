package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions;

public class PepperModuleNotReadyException extends PepperModuleException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8247013698199187030L;
	
	public PepperModuleNotReadyException()
	{ super(); }
	
    public PepperModuleNotReadyException(String s)
    { super(s); }
    
	public PepperModuleNotReadyException(String s, Throwable ex)
	{super(s, ex); }
}

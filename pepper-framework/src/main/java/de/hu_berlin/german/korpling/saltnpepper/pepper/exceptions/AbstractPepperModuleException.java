package de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions;

/**
 * This {@link PepperException} is thrown by {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}s or {@link {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}PepperMapper}
 * objects. The reason of exception can be any and should be further specified by subtypes.
 * This exception just determines, that it occured during the processing in a {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule}. 
 * @author Florian Zipser
 *
 */
public class AbstractPepperModuleException extends PepperException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3563133484909484582L;

	public AbstractPepperModuleException()
	{ super(); }
	
    public AbstractPepperModuleException(String s)
    { super(s); }
    
	public AbstractPepperModuleException(String s, Throwable ex)
	{super(s, ex); }
}

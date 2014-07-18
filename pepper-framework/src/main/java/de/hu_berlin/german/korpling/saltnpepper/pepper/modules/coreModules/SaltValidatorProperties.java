package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperty;

public class SaltValidatorProperties extends PepperModuleProperties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2045401827779201706L;
	
	public static final String PROP_CLEAN="clean";
	
	public SaltValidatorProperties(){
		this.addProperty(new PepperModuleProperty<Boolean>(PROP_CLEAN, Boolean.class, "This property determines, if the validator should try to repair the salt model in case it is invalid. Attention: Handle this property with care, since  repairing could mean to remove invalid objects. ",false, false));
	}
	
	public Boolean isClean(){
		return((Boolean)getProperty(PROP_CLEAN).getValue());
	}
}

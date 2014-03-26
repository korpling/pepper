package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.dot;

import java.io.File;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperty;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;

public class DOTManipulatorProperties extends PepperModuleProperties {
	public static final String PREFIX="dotManipulator.";
	public static final String PROP_OUTPUTFILE= "outputDir";
	public static final String PROP_FILE_ENDING="fileEnding";

	public DOTManipulatorProperties() {
		this.addProperty(new PepperModuleProperty<String>(PROP_OUTPUTFILE, String.class, "The location to where the output shall be written to as File object.", true));
		this.addProperty(new PepperModuleProperty<String>(PROP_FILE_ENDING, String.class, "The file ending of dot files.", SaltFactory.FILE_ENDING_DOT, false));
	}
	
	/**
	 * Returns the location to where the output shall be written to as File object.
	 * @return
	 */
	public File getOutputFile()
	{
		File retVal= null;
		PepperModuleProperty<String> prop= (PepperModuleProperty<String>) this.getProperty(PROP_OUTPUTFILE);
		retVal= new File(prop.getValue());
		return(retVal);
	}
	
	/**
	 * Returns the file ending of dot files.
	 * @return
	 */
	public String getFileEnding()
	{
		PepperModuleProperty<String> prop= (PepperModuleProperty<String>) this.getProperty(PROP_FILE_ENDING);
		return(prop.getValue());
	}
}

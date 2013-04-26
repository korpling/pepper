package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.tests;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.MAPPING_RESULT;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapperConnector;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperMapperImpl;
import junit.framework.TestCase;

public class PepperModuleTest_progress extends TestCase {

	class MyPepperMapper extends PepperMapperImpl
	{

		public MyPepperMapper(PepperMapperConnector connector,
				ThreadGroup threadGroup, String threadName) {
			super(connector, threadGroup, threadName);
		}
		
		@Override
		public MAPPING_RESULT mapSDocument() 
		{
			return(MAPPING_RESULT.FINISHED);
		}
	}
	
	class MyImporter extends PepperImporterImpl
	{
		
	}
	
	/**
	 * Checks, that progress of process is computed correctly by {@link PepperModule}, {@link PepperMapperConnector} and {@link PepperMapper}
	 * objects.
	 */
	public void testGetProgress()
	{
//		PepperMapper mapper= new PepperMapper() {
//
//		}; 
	}
}

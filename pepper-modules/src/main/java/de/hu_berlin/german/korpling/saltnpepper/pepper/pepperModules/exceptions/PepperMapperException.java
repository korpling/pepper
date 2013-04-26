/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.exceptions;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapperConnector;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * This exception class is a general exception class for all exceptions occur in a {@link PepperMapper} object.
 * This objects takes a {@link PepperMapperConnector} object, to create error messages. 
 *
 **/
public class PepperMapperException extends PepperModuleException {
		
    /**
	 * 
	 */
	private static final long serialVersionUID = -1250997974486962691L;

	private static String generateMeassage(PepperMapperConnector connector, String s)
	{
		if (connector!= null)
		{
			String type= "unknown";
			if (	(connector.getSElementId()!= null)&&
					(connector.getSElementId().getSIdentifiableElement()!= null))
			{
				if (connector.getSElementId().getSIdentifiableElement() instanceof SDocument)
					type= "SDocument";
				else if (connector.getSElementId().getSIdentifiableElement() instanceof SCorpus)
					type= "SCorpus";
			}
			return("Exception occured while mapping "+type+" '"+connector.getSElementId()+"' from or to resource '"+connector.getResourceURI()+"'. "+s);
		}
		else
			return(s);
	}
	
	public PepperMapperException(PepperMapperConnector connector, String s)
    { 
		super(generateMeassage(connector, s));
//		super((connector!= null) ? "Exception occured while mapping SDocument '"+connector.getSElementId()+"' from or to resource '"+connector.getResourceURI()+"'. "+s : s);
	}
    
	public PepperMapperException(PepperMapperConnector connector, String s, Throwable ex)
	{
		super((connector!= null) ? "Exception occured while mapping SDocument '"+connector.getSElementId()+"' from or to resource '"+connector.getResourceURI()+"'. "+s : s, ex);
	}
}

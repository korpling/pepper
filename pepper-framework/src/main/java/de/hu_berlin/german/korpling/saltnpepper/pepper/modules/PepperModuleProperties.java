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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Property;

import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModulePropertyException;

/**
 * This class is a container for a set of {@link PepperModuleProperty} objects. This class also offers some methods for accessing and 
 * maintaining the objects.  
 * 
 * @author Florian Zipser
 *
 */
public class PepperModuleProperties implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7802558539252580678L;

	/**
	 * Creates an empty instance of {@link PepperModuleProperties}.
	 */
	public PepperModuleProperties()
	{}
	/**
	 * Loads the given file via {@link Properties#load(java.io.InputStream)} and adds all properties given in the passed {@link Property} object. That means, the corresponding {@link PepperModuleProperty} will be searched
	 * and its value will be set to the one found in the passed {@link Properties} object. If no corresponding {@link PepperModuleProperties} object
	 * corresponds to one of the properties contained in the passed {@link Property} object, a new one will be created.
	 */
	public void addProperties(URI propURI)
	{
		if (propURI!= null)
		{
			this.setPropertyValues(new File(propURI.toFileString()));
		}
	}
	/**
	 * Returns a new {@link Properties} object containing all all property names and their values.
	 * @return new {@link Properties} object 
	 */
	public Properties getProperties() 
	{
		Properties retVal= new Properties();
		Collection<String> names= this.getPropertyNames();
		if (names!= null){
			for (String name: names)
			{
				if (name!= null){
					PepperModuleProperty<?> prop= getProperty(name);
					if (prop.getValue()!= null)
						retVal.put(name, prop.getValue());
				}
			}
		}
		return(retVal);
	}
	
	/**
	 * Loads the given file via {@link Properties#load(java.io.InputStream)} and adds all properties given in the passed {@link Property} object. That means, the corresponding {@link PepperModuleProperty} will be searched
	 * and its value will be set to the one found in the passed {@link Properties} object. If no corresponding {@link PepperModuleProperties} object
	 * corresponds to one of the properties contained in the passed {@link Property} object, a new one will be created.
	 */
	public void setPropertyValues(File propFile)
	{
		if (	(propFile!= null)&&
				(propFile.exists()))
		{
			Properties props= new Properties();
			try {
				props.load(new FileInputStream(propFile));
			} catch (FileNotFoundException e) {
				throw new PepperModulePropertyException("Cannot load property file.", e);
			} catch (IOException e) {
				throw new PepperModulePropertyException("Cannot load property file.", e);
			}
			this.setPropertyValues(props);
		}
	}
	
	/**
	 * Adds all properties given in the passed {@link Property} object. That means, the corresponding {@link PepperModuleProperty} will be searched
	 * and its value will be set to the one found in the passed {@link Properties} object. If no corresponding {@link PepperModuleProperties} object
	 * corresponds to one of the properties contained in the passed {@link Property} object, a new one will be created.
	 */
	public void setPropertyValues(Properties properties)
	{
		if (properties!= null)
		{
			Set<Object>keys= properties.keySet();
			for (Object key: keys)
			{
				this.setPropertyValue(key.toString(), properties.get(key));
			}
		}
	}
	
	/**
	 * Searches for a {@link PepperModuleProperty} object in registered {@link PepperModuleProperty} objects and
	 * sets its value attribute, if a {@link PepperModuleProperty} object was found.
	 * @param propName name of property to search for
	 * @param propValue value to which {@link PepperModuleProperty}s value attribute is set to 
	 */
	public <T> void setPropertyValue(String propName,T propValue)
	{
		PepperModuleProperty<?> prop= this.getProperty(propName);
		if (prop!= null)
			prop.setValueString(propValue.toString());
		else 
		{
			prop= new PepperModuleProperty<String>(propName, String.class, "this entry is automatically created by pepper and no description exists.");
			prop.setValueString(propValue.toString());
			this.addProperty(prop);
		}
		this.checkProperty(prop);
	}
	
	/**
	 * Checks if all properties marked as required are really set. Throws a {@link PepperModulePropertyException} if a 
	 * required value is not set.
	 */ 
	public boolean checkProperties()
	{
		Collection<PepperModuleProperty<?>> properties= this.getPropertyDesctriptions();
		for (PepperModuleProperty<?> prop: properties)
		{
			this.checkProperty(prop);
		}
		return(true);
	}
	
	/**
	 * Checks if the value of given property, when marked as required is really set. 
	 * Throws a {@link PepperModulePropertyException} if a required value is not set.
	 */ 
	public boolean checkProperty(PepperModuleProperty<?> prop)
	{
		if (	(prop.isRequired())&&
				(prop.getValue()== null))
			throw new PepperModulePropertyException("The following property is required, but its value was not set: "+ prop);
		return(true);
	}
	
	/**
	 * Internal map to correspond all {@link PepperModuleProperty} objects to their name.
	 */
	protected Map<String, PepperModuleProperty<?>> pepperModuleProperties= null;
	
	/**
	 * Adds the given {@link PepperModuleProperty} object to the internal list.
	 * @param property
	 */
	public void addProperty(PepperModuleProperty<?> property)
	{
		if (	(property.getName()== null)||
				(property.getName().isEmpty()))
			throw new PepperException("Cannot add a property description without a name.");
		if (pepperModuleProperties== null)
		{
			pepperModuleProperties= new HashMap<String, PepperModuleProperty<?>>();
		}
		pepperModuleProperties.put(property.getName(), property);
	}
	
	/**
	 * Returns a {@link PepperModuleProperty} object corresponding to the given property name.
	 * @param propName name of the property
	 * @return {@link PepperModuleProperty} object
	 */
	public PepperModuleProperty<?> getProperty(String propName)
	{
		PepperModuleProperty<?> retVal= null;
		if (this.pepperModuleProperties!= null)
		{
			retVal= this.pepperModuleProperties.get(propName); 
		}
		return(retVal);
	}
	
	/**
	 * Returns all property names registered in that object, and therefore usable for the corresponding {@link PepperModule}.
	 * @return
	 */
	public Collection<String> getPropertyNames()
	{
		Collection<String> names= new Vector<String>();
		if (pepperModuleProperties!= null)
		{
			Set<String> keys= pepperModuleProperties.keySet();
			for (String key: keys)
				names.add(key);
		}
		return(names);
	}
	
	/**
	 * Returns all registered {@link PepperModuleProperty} objects, which are usable for the corresponding {@link PepperModule}.
	 * @return
	 */
	public Collection<PepperModuleProperty<?>> getPropertyDesctriptions()
	{
		return(pepperModuleProperties.values());
	}
	
	public String toString()
	{
		StringBuffer buf= new StringBuffer();
		buf.append("[");
		for (String name: getPropertyNames())
		{
			PepperModuleProperty prop= getProperty(name);
			buf.append(prop+", ");
		}
		buf.append("]");
		return(buf.toString());
	}
}

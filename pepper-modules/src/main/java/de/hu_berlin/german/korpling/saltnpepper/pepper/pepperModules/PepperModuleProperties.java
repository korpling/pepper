package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.apache.felix.scr.annotations.Property;
import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.exceptions.PepperModulePropertyException;

/**
 * This class is a container for a set of {@link PepperModuleProperty} objects. This class also offers some methods for accessing and 
 * maintaining the objects.  
 * 
 * @author Florian Zipser
 *
 */
public class PepperModuleProperties
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
			this.addProperties(new File(propURI.toFileString()));
		}
	}
	
	/**
	 * Loads the given file via {@link Properties#load(java.io.InputStream)} and adds all properties given in the passed {@link Property} object. That means, the corresponding {@link PepperModuleProperty} will be searched
	 * and its value will be set to the one found in the passed {@link Properties} object. If no corresponding {@link PepperModuleProperties} object
	 * corresponds to one of the properties contained in the passed {@link Property} object, a new one will be created.
	 */
	public void addProperties(File propFile)
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
			this.addProperties(props);
		}
	}
	
	/**
	 * Adds all properties given in the passed {@link Property} object. That means, the corresponding {@link PepperModuleProperty} will be searched
	 * and its value will be set to the one found in the passed {@link Properties} object. If no corresponding {@link PepperModuleProperties} object
	 * corresponds to one of the properties contained in the passed {@link Property} object, a new one will be created.
	 */
	public void addProperties(Properties properties)
	{
		if (properties!= null)
		{
			Set<Object>keys= properties.keySet();
			for (Object key: keys)
			{
				PepperModuleProperty<?> prop= this.getProperty(key.toString());
				if (prop!= null)
				{
					String value= properties.get(key).toString();
					prop.setValueString(value);
				}
				else 
				{
					prop= new PepperModuleProperty<String>(key.toString(), String.class, "this entry is automatically created by pepper and no description exists.");
					String value= properties.get(key).toString();
					prop.setValueString(value);
					this.addProperty(prop);
				}
			}
		}
	}
	
	/**
	 * Internal map to correspond all {@link PepperModuleProperty} objects to their name.
	 */
	private Map<String, PepperModuleProperty<?>> pepperModuleProperties= null;
	
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
	 * Checks if all properties marked as required are really set. Throws a {@link PepperModulePropertyException} if a 
	 * required value is not set.
	 */ 
	public boolean checkProperties()
	{
		Collection<PepperModuleProperty<?>> properties= this.getPropertyDesctriptions();
		for (PepperModuleProperty<?> prop: properties)
		{
			if (	(prop.isRequired())&&
					(prop.getValue()== null))
			throw new PepperModulePropertyException("The following property is required, but its value was not set: "+ prop);
		}
		return(true);
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
	 * Returns all registered {@link PepperModuleProperty} objects, whiach are usable for the corresponding {@link PepperModule}.
	 * @return
	 */
	public Collection<PepperModuleProperty<?>> getPropertyDesctriptions()
	{
		return(pepperModuleProperties.values());
	}
}

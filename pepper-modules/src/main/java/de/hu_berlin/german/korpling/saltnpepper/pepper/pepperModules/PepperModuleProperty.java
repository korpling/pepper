package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules;

import java.io.File;

/**
 * The class {@link PepperModuleProperty} offers a possibility to describe a property, which is used by a specific {@link PepperModule}. 
 * A list of {@link PepperModuleProperty} objects is requested by Pepper to give an overview of properties to the user.
 * T specifies the type of this property.
 * This class contains:
 * <ol>
 * 	<li>the name of this property</li>
 * 	<li>a description of its sense to present it to the user</li>
 * 	<li>a field determining if this property is required by the corresponding {@link PepperModule} object</li>
 * </ol>
 * @author Florian Zipser
 *
 */
public class PepperModuleProperty<T> {
	
	/**
	 * Creates a {@link PepperModuleProperty} instance and sets its values to the given ones.
	 * <em>required</em> and <em>check</em> are set to the default values.
	 * @param name
	 * @param type
	 * @param description
	 */
	public PepperModuleProperty(	final String name, 
									Class<T> clazz,
									final String description)
	{
		this.name= name;
		this.clazz= clazz;
		this.description= description;
	}
	
	/**
	 * Creates a {@link PepperModuleProperty} instance and sets its values to the given ones.
	 * <em>required</em> and <em>check</em> are set to the default values.
	 * @param name
	 * @param type
	 * @param description
	 * @param defaultValue a default value for the value
	 */
	public PepperModuleProperty(	final String name, 
									Class<T> clazz,
									final String description,
									T defaultValue)
	{
		this(name, clazz, description);
		this.value= defaultValue;
	}
	
	/**
	 * Creates a {@link PepperModuleProperty} instance and sets its values to the given ones.
	 * @param name
	 * @param clazz
	 * @param description
	 * @param required
	 */
	public PepperModuleProperty(	final String name, 
			 						Class<T> clazz,
									final String description,
									final boolean required)
	{
		this(name, clazz, description);
		this.required = required;
	}
	
	/**
	 * Creates a {@link PepperModuleProperty} instance and sets its values to the given ones.
	 * @param name
	 * @param clazz
	 * @param description
	 * @param required
	 */
	public PepperModuleProperty(	final String name, 
			 						Class<T> clazz,
									final String description,
									T defaultValue,
									final boolean required)
	{
		this(name, clazz, description, required);
		this.value= defaultValue;
	}
	
	
	

	
	/**
	 * The unique name of the property. This name also serves as an identifier.
	 */
	private String name= null;
	/**
	 * Returns the unique name of the property. This name also serves as an identifier.
	 * @return
	 */
	public String getName() {
		return name;
	} 
	
	public void setType(Class<T> type) {
		this.clazz = type;
	}
	/**
	 * The type of a property. The type can be used to check the integrity of a property in case of the value {@link #isToCheck} ist set. Not for all types this is possible, because only a small set of types is implemented like int, float, long, boolean, File.
	 */
	private Class<T> clazz= null;
	/**
	 * Returns the type of a property. The type can be used to check the integrity of a property in case of the value {@link #isToCheck} ist set. Not for all types this is possible, because only a small set of types is implemented like int, float, long, boolean, File.
	 * @return
	 */
	public Class<T> getType() {
		return clazz;
	}

	/**
	 * A textual description of the property, which is used by Pepper to present it to the user.
	 */
	private String description= null;
	/**
	 * returns the textual description of the property, which is used by Pepper to present it to the user.
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Determines if a property is required or optional. The dafault case is optional.
	 */
	private boolean required= false;
	/**
	 * 
	 * @return
	 */
	public boolean isRequired()
	{ 
		return(required);
	}
			
	/**
	 * Value of this property
	 */
	T value= null;
	
	public void setValue(T value)
	{
		this.value= value;
	}
	
	/**
	 * Sets the given value to the internal one. In case of the type of this property is not a String, this method will try to cast the given 
	 * value. Please note, that only  supported types can be casted.
	 * The following types are provide by a type cast:
	 * <ul>
	 * 	<li>{@link Character}</li>
	 *  <li>{@link Boolean}</li>
	 *  <li>{@link Integer}</li>
	 *  <li>{@link Long}</li>
	 *  <li>{@link Float}</li>
	 *  <li>{@link Double}</li>
	 *  <li>{@link Byte}</li>
	 *  <li>{@link Short}</li>
	 *  <li>{@link File}</li>
	 * </ul>
	 * @param value consumes the given String parameter and transforms it
	 */
	@SuppressWarnings("unchecked")
	public void setValueString(String value)
	{
		if (value== null)
			this.value=null;
		else
		{//start: checks type of value
			if (String.class.isAssignableFrom(clazz))
				this.value= (T)value;
			else if (Character.class.isAssignableFrom(clazz))
				this.value= (T)Character.valueOf(value.charAt(0));
			
			//boolean
			else if (Boolean.class.isAssignableFrom(clazz))
				this.value= (T)Boolean.valueOf(value);
			
			//numeric types
			else if (Integer.class.isAssignableFrom(clazz))
				this.value= (T)Integer.valueOf(value);
			else if (Long.class.isAssignableFrom(clazz))
				this.value= (T)Long.valueOf(value);
			else if (Float.class.isAssignableFrom(clazz))
				this.value= (T)Float.valueOf(value);
			else if (Double.class.isAssignableFrom(clazz))
				this.value= (T)Double.valueOf(value);
			else if (Byte.class.isAssignableFrom(clazz))
				this.value= (T)Byte.valueOf(value);
			else if (Short.class.isAssignableFrom(clazz))
				this.value= (T)Short.valueOf(value);
			
			//File
			else if (File.class.isAssignableFrom(clazz))
				this.value= (T)new File(value);
		}//end: checks type of value
	}
	
	/**
	 * Returns the value of this property.
	 * @return
	 */
	public T getValue()
	{
		return(value);
	}
	
	public String toString()
	{
		return(this.getName()+"="+this.getType()+"::"+this.getValue());
	}
	
}
/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
package org.corpus_tools.pepper.common;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.eclipse.emf.common.util.URI;

/**
 * This class is a kind of a fingerprint of a Pepper module and provides some
 * information about a module.
 * 
 * @author Florian Zipser
 *
 */
@XmlRootElement
public class PepperModuleDesc implements Comparable<PepperModuleDesc> {

	/** name of the Pepper module **/
	private String name = null;

	/**
	 * Returns the name of a Pepper module described by this
	 * {@link PepperModuleDesc} object.
	 * 
	 * @return name of the Pepper module
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of a Pepper module described by this
	 * {@link PepperModuleDesc} object.
	 * 
	 * @param moduleName
	 *            name of the Pepper module
	 */
	@XmlElement
	public void setName(String moduleName) {
		this.name = moduleName;
	}

	/** version of the Pepper module **/
	private String version = null;

	/**
	 * Returns the version of a Pepper module described by this
	 * {@link PepperModuleDesc} object.
	 * 
	 * @return version of the Pepper module
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version of a Pepper module described by this
	 * {@link PepperModuleDesc} object.
	 * 
	 * @param version
	 *            of the Pepper module
	 */
	@XmlElement
	public void setVersion(String version) {
		this.version = version;
	}

	/** type of the pepper module **/
	private MODULE_TYPE moduleType = null;

	/**
	 * Returns the type of this module.
	 * 
	 * @return type of module
	 */
	public MODULE_TYPE getModuleType() {
		return (moduleType);
	}

	/**
	 * Sets the type of this module.
	 * 
	 * @param moduleType
	 *            type of module
	 */
	@XmlAttribute
	public void setModuleType(MODULE_TYPE moduleType) {
		this.moduleType = moduleType;
	}

	/** Some description of the function of this module **/
	private String desc = null;

	/**
	 * Returns a short description of this module. Please support some
	 * information, for the user, of what task this module does.
	 * 
	 * @return a short description of the task of this module
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets a short description of this module. Please support some information,
	 * for the user, of what task this module does.
	 * 
	 * @param desc
	 *            a short description of the task of this module
	 */
	@XmlElement
	public void setDesc(String desc) {
		this.desc = desc;
	}

	protected URI supplierContact = null;

	/**
	 * Returns a uri where to find more information about this module and where
	 * to find some contact information to contact the supllier.
	 * 
	 * @return contact address like eMail address or homepage address
	 */
	public URI getSupplierContact() {
		return (supplierContact);
	}

	/**
	 * Sets a uri where to find more information about this module and where to
	 * find some contact information to contact the supllier.
	 * 
	 * @param uri
	 *            contact address like eMail address or homepage address
	 */
	@XmlElement
	public void setSupplierContact(URI supplierContact) {
		this.supplierContact = supplierContact;
	}

	protected URI hp = null;

	/**
	 * Sets the {@link URI} to the homepage describing the functionality of the
	 * module.
	 * 
	 * @return {@link URI} to the homepage
	 */
	public URI getSupplierHomepage() {
		return (hp);
	}

	/**
	 * Returns the {@link URI} to the homepage describing the functionality of
	 * the module.
	 * 
	 * @param hp
	 *            {@link URI} to the homepage
	 */
	@XmlElement
	public void setSupplierHomepage(URI hp) {
		this.hp = hp;
	}

	/**
	 * a list of all formats supported by the Pepper module, this object
	 * describes
	 */
	private List<FormatDesc> supportedFormats;

	/**
	 * Returns a list of {@link FormatDesc} objects describing all formats
	 * supported by the Pepper module, this object describes.
	 * 
	 * @return list of format descriptions
	 */
	public List<FormatDesc> getSupportedFormats() {
		if (supportedFormats == null) {
			supportedFormats = new Vector<FormatDesc>();
		}		
		return supportedFormats;
	}

	/**
	 * Creates a new {@link FormatDesc} object containing the passed name,
	 * version and reference to the list of of {@link FormatDesc} objects
	 * describing all formats supported by the Pepper module, this object
	 * describes.
	 * 
	 * @param formatName
	 *            name of the supported format
	 * @param formatVersion
	 *            version of the supported format
	 * @param formatReference
	 *            a reference for information about the format if exist
	 * @return a {@link FormatDesc}
	 */
	public FormatDesc addSupportedFormat(String formatName, String formatVersion, URI formatReference) {
		FormatDesc retVal = null;
		retVal = new FormatDesc();
		retVal.setFormatName(formatName);
		retVal.setFormatVersion(formatVersion);
		retVal.setFormatReference(formatReference);
		getSupportedFormats().add(retVal);
		return (retVal);
	}

	/**
	 * A {@link PepperModuleProperties} object containing properties to
	 * customize the behaviour of this {@link PepperModule}.
	 */
	private PepperModuleProperties properties = null;

	/**
	 * Returns a {@link PepperModuleProperties} object containing properties to
	 * customize the behavior of this {@link PepperModule}.
	 * 
	 * @return
	 */
	public PepperModuleProperties getProperties() {
		return (properties);
	}

	/**
	 * Sets the{@link PepperModuleProperties} object containing properties to
	 * customize the behavior of this {@link PepperModule}. Please make sure,
	 * that this method is called in constructor of your module. If not, a
	 * general {@link PepperModuleProperties} object is created by the pepper
	 * framework and will be initialized. This means, when calling this method
	 * later, all properties for customizing the module will be overridden.
	 * 
	 * @param properties
	 */
	@XmlElement
	public void setProperties(PepperModuleProperties properties) {
		this.properties = properties;
	}

	/**
	 * Returns a String representation of this object. <strong>Please note, that
	 * this representation cannot be used for serialization/deserialization
	 * purposes</strong>
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("(");
		str.append(getModuleType());
		str.append("::");
		str.append(getName());
		str.append(", ");
		str.append(getVersion());
		str.append(")");
		return (str.toString());
	}

	/**
	 * Compares the passed {@link PepperModuleDesc} object with this. The
	 * sorting order follows the rules:
	 * <ol>
	 * <li>{@link MODULE_TYPE#IMPORTER}</li>
	 * <li>{@link MODULE_TYPE#MANIPULATOR}</li>
	 * <li>{@link MODULE_TYPE#EXPORTER}</li>
	 * </ol>
	 * and ascending name inside the three types.
	 */
	@Override
	public int compareTo(PepperModuleDesc o) {
		int retVal = 0;
		if (getModuleType() != null && o.getModuleType() != null) {
			retVal = getModuleType().compareTo(o.getModuleType());
		}
		if (retVal == 0) {
			retVal = getName().compareTo(o.getName());
		}
		return retVal;
	}

	/** This method was overwritten because of a findbugs warning **/
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/** This method was overwritten because of a findbugs warning **/
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}

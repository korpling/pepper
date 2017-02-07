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

import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.eclipse.emf.common.util.URI;

/**
 * This class represents a description of a single step in Pepper. A step is a
 * conceptual unit, to which exactly one
 * {@link org.corpus_tools.pepper.modules.PepperModule} instance belongs to.
 * Such a step can belong to either the import phase, the manipulation phase or
 * the export phase. <br/>
 * This class is used as a communication bridge between the Pepper framework and
 * the outside. When using Pepper as a library or a service, one can create
 * {@link StepDesc} object which an abstract description of which module is to
 * use (by setting the name of module and its version) and in case of the module
 * is an importer or an exporter, the description of the path, where the corpus
 * is located. <br/>
 * The main fields of this class are:
 * <ul>
 * <li>{@link #name} - the name of the
 * {@link org.corpus_tools.pepper.modules.PepperModule} to be used to identify
 * {@link org.corpus_tools.pepper.modules.PepperModule}</li>
 * <li>{@link #version} - the version of the
 * {@link org.corpus_tools.pepper.modules.PepperModule} to be used to identify
 * the {@link org.corpus_tools.pepper.modules.PepperModule}</li>
 * <li>{@link #corpusDesc} - an object describing the corpus to be imported or
 * exported (just in case of the
 * {@link org.corpus_tools.pepper.modules.PepperModule} is a
 * {@link org.corpus_tools.pepper.modules.PepperImporter} or a
 * {@link org.corpus_tools.pepper.modules.PepperExporter} ). This object
 * contains a description of the format (name and version) and a path, where to
 * find the corpus.</li>
 * <li>{@link #props} - a general property object (see: {@link Properties}) to
 * customize the mapping process</li>
 * </ul>
 * 
 * @author Florian Zipser
 *
 */
public class StepDesc {
	
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	/** Type of module to be used **/
	private MODULE_TYPE moduleType = null;

	/**
	 * Returns the type of the
	 * {@link org.corpus_tools.pepper.modules.PepperModule} to be used.
	 * 
	 * @return module type
	 */
	public MODULE_TYPE getModuleType() {
		lock.readLock().lock();
		try {
			return moduleType;
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * Sets the type of the {@link org.corpus_tools.pepper.modules.PepperModule}
	 * to be used.
	 * 
	 * @param moduleType
	 * @return 
	 */
	public StepDesc setModuleType(MODULE_TYPE moduleType) {
		lock.writeLock().lock();
		try {
			this.moduleType = moduleType;
		}
		finally {
			lock.writeLock().unlock();
		}
		return (this);
	}

	/**
	 * Name of the {@link org.corpus_tools.pepper.modules.PepperModule} to be
	 * used for this step.
	 **/
	private String name = null;

	/**
	 * Returns the name of the
	 * {@link org.corpus_tools.pepper.modules.PepperModule} to be used for this
	 * step.
	 * 
	 * @return name of {@link org.corpus_tools.pepper.modules.PepperModule}
	 */
	public String getName() {
		lock.readLock().lock();
		try {
			return name;
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * Sets the name of the {@link org.corpus_tools.pepper.modules.PepperModule}
	 * to be used for this step.
	 * 
	 * @param name
	 *            of {@link org.corpus_tools.pepper.modules.PepperModule}
	 * @return 
	 */
	public StepDesc setName(String name) {
		lock.writeLock().lock();
		try {
			this.name = name;
		} finally {
			lock.writeLock().unlock();
		}
		return (this);
	}

	/**
	 * Version of the {@link org.corpus_tools.pepper.modules.PepperModule} to be
	 * used for this step.
	 **/
	private String version = null;

	/**
	 * Returns the version of the
	 * {@link org.corpus_tools.pepper.modules.PepperModule} to be used for this
	 * step.
	 * 
	 * @return version of {@link org.corpus_tools.pepper.modules.PepperModule}
	 */
	public String getVersion() {
		lock.readLock().lock();
		try {
			return version;
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * Sets the version of the
	 * {@link org.corpus_tools.pepper.modules.PepperModule} to be used for this
	 * step.
	 * 
	 * @param version
	 *            of {@link org.corpus_tools.pepper.modules.PepperModule}
	 * @return 
	 */
	public synchronized StepDesc setVersion(String version) {
		lock.writeLock().lock();
		try {
			this.version = version;
		} finally {
			lock.writeLock().unlock();
		}
		return (this);
	}

	/**
	 * Object to describe all infos about the corpus to im- or export. This
	 * includes the following:
	 * <ul>
	 * <li>location of the corpus</li>
	 * <li>name of the format, the corpus is in or has to be exported in</li>
	 * <li>version of the format, the corpus is in or has to be exported in</li>
	 * </ul>
	 **/
	private CorpusDesc corpusDesc = new CorpusDesc();

	/**
	 * Returns an object to describe all infos about the corpus to im- or
	 * export. This includes the following:
	 * <ul>
	 * <li>location of the corpus</li>
	 * <li>name of the format, the corpus is in or has to be exported in</li>
	 * <li>version of the format, the corpus is in or has to be exported in</li>
	 * </ul>
	 * If no {@link CorpusDesc} object is set, one will be created.
	 * 
	 * @return
	 */
	public  CorpusDesc getCorpusDesc() {
		lock.readLock().lock();
		try {
			return corpusDesc;
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * Sets an object to describe all infos about the corpus to im- or export.
	 * This includes the following:
	 * <ul>
	 * <li>location of the corpus</li>
	 * <li>name of the format, the corpus is in or has to be exported in</li>
	 * <li>version of the format, the corpus is in or has to be exported in</li>
	 * </ul>
	 * 
	 * @param corpusDesc
	 * @return 
	 */
	public StepDesc setCorpusDesc(CorpusDesc corpusDesc) {
		lock.writeLock().lock();
		try {
			this.corpusDesc = corpusDesc;
		} finally {
			lock.writeLock().unlock();
		}
		return (this);
	}

	/** object for temporary storage of customization properties **/
	private Properties props = null;

	/**
	 * Returns the object for temporary storage of customization properties.
	 * This object is used to create
	 * {@link org.corpus_tools.pepper.modules.PepperModuleProperties} .
	 * 
	 * @return
	 */
	public Properties getProps() {
		lock.readLock().lock();
		try {
			return props;
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * If {@link #setPepperModule(org.corpus_tools.pepper.modules.PepperModule)}
	 * was already called, the passed {@link Properties} object is used to
	 * create a {@link org.corpus_tools.pepper.modules.PepperModuleProperties}
	 * object, which is set to given
	 * {@link org.corpus_tools.pepper.modules.PepperModule} .
	 * 
	 * @param props
	 *            properties to customize processing
	 * @return 
	 */
	public StepDesc setProps(Properties props) {
		lock.writeLock().lock();
		try {
			this.props = props;
		} finally {
			lock.writeLock().unlock();
		}
		return (this);
	}

	public void toXML(XMLStreamWriter writer) {
		if (writer != null) {
			// Lock this entire block to ensure consistency, since the 
			// lock is reenterant the sub-locking in the getter-functions
			// will not cause any problems.
			lock.readLock().lock();
			try {
				writer.writeStartElement("step");
				if (getModuleType() != null)
					writer.writeAttribute("type", getModuleType().toString());
				if (getName() != null)
					writer.writeAttribute("moduleName", getName());
				if (getVersion() != null)
					writer.writeAttribute("moduleVersion", getVersion());
				String formatName = getCorpusDesc().getFormatDesc().getFormatName();
				if (formatName != null)
					writer.writeAttribute("formatName", formatName);
				String formatVersion = getCorpusDesc().getFormatDesc().getFormatVersion();
				if (formatVersion != null)
					writer.writeAttribute("formatVersion", formatVersion);
				URI path = getCorpusDesc().getCorpusPath();
				if (path != null)
					writer.writeAttribute("path", path.toString());
				if (getProps() != null) {
					writer.writeStartElement("customization");
					Enumeration<Object> keys = getProps().keys();
					while (keys.hasMoreElements()) {
						String name = keys.nextElement().toString();
						String value = getProps().getProperty(name);
						writer.writeStartElement("property");
						writer.writeAttribute("name", name);
						writer.writeCharacters(value);
						writer.writeEndElement();
					}
					writer.writeEndElement();
				}
				writer.writeEndElement();
			} catch (XMLStreamException e) {
				throw new PepperFWException("Cannot marshall StepDesc.", e);
			} finally {
				lock.readLock().unlock();
			}
		}
	}

	/**
	 * Returns a string representation of this object. <strong>Note: This
	 * representation cannot be used for serialization/deserialization
	 * purposes.</strong>
	 * @return 
	 */
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		
		lock.readLock().lock();
		try {

			if (getModuleType() != null) {
				str.append(getModuleType().toString());
			} else
				str.append("UNKNOWN_STEP");
			str.append("(");
			if (getName() != null) {
				str.append((getName() != null) ? getName() : "");
				str.append((getVersion() != null) ? ", " + getVersion() : "");
			} else if (getCorpusDesc().getFormatDesc() != null) {
				str.append("format: ");
				str.append((getCorpusDesc().getFormatDesc().getFormatName() != null)
						? getCorpusDesc().getFormatDesc().getFormatName() : "");
				str.append(",");
				str.append((getCorpusDesc().getFormatDesc().getFormatVersion() != null)
						? getCorpusDesc().getFormatDesc().getFormatVersion() : "");
			}
			str.append(")");
		} finally {
			lock.readLock().unlock();
		}
		return (str.toString());
	}
}

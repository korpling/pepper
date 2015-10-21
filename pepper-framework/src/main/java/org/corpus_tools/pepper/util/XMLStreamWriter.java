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
package org.corpus_tools.pepper.util;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;

/**
 * This class is just a delegator class for a {@link XMLStreamWriter} with a
 * pretty printing possibility.
 * 
 * @author Florian Zipser
 *
 */
public class XMLStreamWriter implements javax.xml.stream.XMLStreamWriter {

	private static final String INTEND = "\t";
	private String intend = INTEND;

	/**
	 * Returns the intend character(s) for pretty printing. Default is
	 * {@link XMLStreamWriter#INTEND}.
	 * 
	 * @param intend
	 */
	public String getIntend() {
		return intend;
	}

	/**
	 * Set the intend character(s) for pretty printing. Default is
	 * {@link XMLStreamWriter#INTEND}.
	 * 
	 * @param intend
	 */
	public void setIntend(String intend) {
		this.intend = intend;
	}

	private Boolean isPrettyPrint = false;

	/**
	 * Returns whether xml should be pretty printed
	 * 
	 * @return
	 */
	public Boolean getPrettyPrint() {
		return isPrettyPrint;
	}

	/**
	 * Sets whether the xml should be pretty printed.
	 * 
	 * @param prettyPrint
	 */
	public void setPrettyPrint(Boolean prettyPrint) {
		this.isPrettyPrint = prettyPrint;
	}

	/**
	 * Current depth for xml pretty printing.
	 */
	private int depth = 0;

	/**
	 * The delegate object
	 */
	private javax.xml.stream.XMLStreamWriter xml = null;

	public XMLStreamWriter(javax.xml.stream.XMLStreamWriter xmlStreamWriter) {
		if (xmlStreamWriter == null) {
			throw new NullPointerException("The passed XMLStreamWriter which acts as a delegatee is null.");
		}
		xml = xmlStreamWriter;
	}

	@Override
	public void close() throws XMLStreamException {
		xml.close();
	}

	@Override
	public void flush() throws XMLStreamException {
		xml.flush();
	}

	@Override
	public NamespaceContext getNamespaceContext() {
		return (xml.getNamespaceContext());
	}

	@Override
	public String getPrefix(String uri) throws XMLStreamException {
		return (xml.getPrefix(uri));
	}

	@Override
	public Object getProperty(String name) throws IllegalArgumentException {
		return xml.getProperty(name);
	}

	@Override
	public void setDefaultNamespace(String uri) throws XMLStreamException {
		xml.setDefaultNamespace(uri);
	}

	@Override
	public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
		xml.setNamespaceContext(context);
	}

	@Override
	public void setPrefix(String prefix, String uri) throws XMLStreamException {
		xml.setPrefix(prefix, uri);
	}

	@Override
	public void writeAttribute(String localName, String value) throws XMLStreamException {
		xml.writeAttribute(localName, value);
	}

	@Override
	public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
		xml.writeAttribute(namespaceURI, localName, value);
	}

	@Override
	public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
		xml.writeAttribute(prefix, namespaceURI, localName, value);
	}

	@Override
	public void writeCData(String data) throws XMLStreamException {
		xml.writeCData(data);
	}

	/** states if current element contains textual content **/
	boolean textualContent = false;

	@Override
	public void writeCharacters(String text) throws XMLStreamException {
		textualContent = true;
		xml.writeCharacters(text);
	}

	@Override
	public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
		textualContent = true;
		xml.writeCharacters(text, start, len);
	}

	@Override
	public void writeComment(String data) throws XMLStreamException {
		if (isPrettyPrint) {
			writeCharacters("\n");
			for (int i = 0; i < depth; i++) {
				writeCharacters(INTEND);
			}
			textualContent = false;
		}
		xml.writeComment(data);
	}

	@Override
	public void writeDTD(String dtd) throws XMLStreamException {
		xml.writeDTD(dtd);
	}

	@Override
	public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
		xml.writeDefaultNamespace(namespaceURI);
	}

	@Override
	public void writeEmptyElement(String localName) throws XMLStreamException {
		if (isPrettyPrint) {
			writeCharacters("\n");
			for (int i = 0; i < depth; i++) {
				writeCharacters(INTEND);
			}
			textualContent = false;
		}
		xml.writeEmptyElement(localName);
	}

	@Override
	public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
		if (isPrettyPrint) {
			writeCharacters("\n");
			for (int i = 0; i < depth; i++) {
				writeCharacters(INTEND);
			}
			textualContent = false;
		}
		xml.writeEmptyElement(namespaceURI, localName);
	}

	@Override
	public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		if (isPrettyPrint) {
			writeCharacters("\n");
			for (int i = 0; i < depth; i++) {
				writeCharacters(INTEND);
			}
			textualContent = false;
		}
		xml.writeEmptyElement(prefix, localName, namespaceURI);
	}

	@Override
	public void writeEndDocument() throws XMLStreamException {
		xml.writeEndDocument();
	}

	@Override
	public void writeEndElement() throws XMLStreamException {
		if (isPrettyPrint) {
			depth--;
			writeCharacters("\n");
			for (int i = 0; i < depth; i++) {
				writeCharacters(INTEND);
			}
			textualContent = false;
		}
		xml.writeEndElement();
	}

	@Override
	public void writeEntityRef(String name) throws XMLStreamException {
		xml.writeEntityRef(name);
	}

	@Override
	public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
		xml.writeNamespace(prefix, namespaceURI);
	}

	@Override
	public void writeProcessingInstruction(String target) throws XMLStreamException {
		xml.writeProcessingInstruction(target);
	}

	@Override
	public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
		xml.writeProcessingInstruction(target, data);
	}

	@Override
	public void writeStartDocument() throws XMLStreamException {
		xml.writeStartDocument();
	}

	@Override
	public void writeStartDocument(String version) throws XMLStreamException {
		xml.writeStartDocument(version);
	}

	@Override
	public void writeStartDocument(String encoding, String version) throws XMLStreamException {
		xml.writeStartDocument(encoding, version);
	}

	@Override
	public void writeStartElement(String localName) throws XMLStreamException {
		if (isPrettyPrint) {
			writeCharacters("\n");
			for (int i = 0; i < depth; i++) {
				writeCharacters(INTEND);
			}
			depth++;
			textualContent = false;
		}
		xml.writeStartElement(localName);
	}

	@Override
	public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
		if (isPrettyPrint) {
			writeCharacters("\n");
			for (int i = 0; i < depth; i++) {
				writeCharacters(INTEND);
			}
			depth++;
			textualContent = false;
		}
		xml.writeStartElement(localName, localName);
	}

	@Override
	public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		if (isPrettyPrint) {
			writeCharacters("\n");
			for (int i = 0; i < depth; i++) {
				writeCharacters(INTEND);
			}
			depth++;
			textualContent = false;
		}
		xml.writeStartElement(prefix, localName, namespaceURI);
	}
}

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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StepDescTest {

	protected StepDesc fixture = null;

	public StepDesc getFixture() {
		return fixture;
	}

	public void setFixture(StepDesc fixture) {
		this.fixture = fixture;
	}

	/**
	 * {@link OutputStream} where the xml nodes are written to.
	 */
	private ByteArrayOutputStream outStream = null;
	/**
	 * XMLWriter to write an xml stream.
	 */
	private XMLStreamWriter xmlWriter = null;

	@Before
	public void setUp() throws XMLStreamException {
		setFixture(new StepDesc());
		outStream = new ByteArrayOutputStream();
		XMLOutputFactory o = XMLOutputFactory.newFactory();
		xmlWriter = o.createXMLStreamWriter(outStream);
	}

	@Test
	public void testMarhallUNmarshall() {
		getFixture().setModuleType(MODULE_TYPE.EXPORTER);
		getFixture().setName("MyExporter");
		getFixture().setVersion("1.0");
		getFixture().getCorpusDesc().setCorpusPath(URI.createFileURI("/somewhere/"));
		getFixture().getCorpusDesc().getFormatDesc().setFormatName("myFormat");
		getFixture().getCorpusDesc().getFormatDesc().setFormatVersion("1.0");
		;
		Properties props = new Properties();
		props.put("propName1", "propValue");
		props.put("propName2", "propValue");
		props.put("propName3", "propValue");
		getFixture().setProps(props);
		getFixture().toXML(xmlWriter);
	}
}

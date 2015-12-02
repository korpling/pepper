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
package org.corpus_tools.pepper.core.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.core.ModuleResolver;
import org.corpus_tools.pepper.core.ModuleResolverImpl;
import org.corpus_tools.pepper.core.PepperJobImpl;
import org.corpus_tools.pepper.core.WorkflowDescriptionReader;
import org.corpus_tools.pepper.impl.PepperManipulatorImpl;
import org.corpus_tools.pepper.modules.PepperModule;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class WorkflowDescriptionReaderTest {

	private WorkflowDescriptionReader fixture = null;

	public WorkflowDescriptionReader getFixture() {
		return fixture;
	}

	public void setFixture(WorkflowDescriptionReader fixture) {
		this.fixture = fixture;
	}

	/**
	 * xml to write an xml stream.
	 */
	private XMLStreamWriter xml = null;

	private XMLReader xmlReader = null;
	private InputSource is = null;
	private SAXParserFactory factory = SAXParserFactory.newInstance();

	@Before
	public void setUp() throws XMLStreamException, IOException, ParserConfigurationException, SAXException {
		setFixture(new WorkflowDescriptionReader());
		PepperJobImpl job = new PepperJobImpl("job1");
		ModuleResolver resolver = new ModuleResolverImpl() {
			public PepperModule getPepperModule(StepDesc stepDesc) {
				PepperModule manipulator = new PepperManipulatorImpl() {
				};
				return (manipulator);
			}
		};
		job.setModuleResolver(resolver);
		getFixture().setPepperJob(job);

		File file = new File(PepperUtil.getTempTestFile("workflowDescriptionTest").getAbsolutePath() + "test.xml");
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		xml = xof.createXMLStreamWriter(new FileWriter(file.getAbsolutePath()));
		getFixture().setLocation(URI.createFileURI(file.getAbsolutePath()));

		SAXParser parser = factory.newSAXParser();
		xmlReader = parser.getXMLReader();
		xmlReader.setContentHandler(getFixture());

		InputStream inputStream = new FileInputStream(file.getAbsolutePath());
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		is = new InputSource(reader);
		is.setEncoding("UTF-8");
	}

	/**
	 * Containing just one importer (resolved by module name), one manipulator
	 * and one exporter (resolved by format description) without customizations.
	 * 
	 * @throws XMLStreamException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void test_simpleJob() throws XMLStreamException, IOException, SAXException {
		xml.writeStartDocument();
		xml.writeStartElement(WorkflowDescriptionReader.TAG_PEPEPR_JOB);
		xml.writeAttribute(WorkflowDescriptionReader.ATT_VERSION, "1.0");
		// importer
		xml.writeStartElement(WorkflowDescriptionReader.TAG_IMPORTER);
		xml.writeAttribute(WorkflowDescriptionReader.ATT_NAME, "myImporter");
		xml.writeAttribute(WorkflowDescriptionReader.ATT_VERSION, "1.0");
		xml.writeAttribute(WorkflowDescriptionReader.ATT_PATH, "/somewhere/");
		xml.writeEndElement();
		// manipulator
		xml.writeStartElement(WorkflowDescriptionReader.TAG_MANIPULATOR);
		xml.writeAttribute(WorkflowDescriptionReader.ATT_NAME, "myManipulator");
		xml.writeAttribute(WorkflowDescriptionReader.ATT_VERSION, "3.0");
		xml.writeEndElement();
		// exporter
		xml.writeStartElement(WorkflowDescriptionReader.TAG_EXPORTER);
		xml.writeAttribute(WorkflowDescriptionReader.ATT_FORMAT_NAME, "anyFormat");
		xml.writeAttribute(WorkflowDescriptionReader.ATT_FORMAT_VERSION, "1.0");
		xml.writeAttribute(WorkflowDescriptionReader.ATT_PATH, "/somewhere/");
		xml.writeEndElement();
		xml.writeEndElement();
		xml.writeEndDocument();
		xml.flush();

		xmlReader.parse(is);

		assertNotNull(getFixture().getPepperJob());
		assertNotNull(getFixture().getPepperJob().getStepDescs());
		assertEquals(3, getFixture().getPepperJob().getStepDescs().size());

		// check importer
		StepDesc stepDesc = getFixture().getPepperJob().getStepDescs().get(0);
		assertNotNull(getFixture().getPepperJob().getStepDescs().get(0));
		assertEquals(MODULE_TYPE.IMPORTER, stepDesc.getModuleType());
		assertEquals(stepDesc.toString(), "myImporter", stepDesc.getName());
		assertEquals(stepDesc.toString(), "1.0", stepDesc.getVersion());
		assertNotNull(stepDesc.getCorpusDesc());
		assertEquals(URI.createFileURI("/somewhere/"), stepDesc.getCorpusDesc().getCorpusPath());

		// check manipulator
		stepDesc = getFixture().getPepperJob().getStepDescs().get(1);
		assertNotNull(getFixture().getPepperJob().getStepDescs().get(0));
		assertEquals(MODULE_TYPE.MANIPULATOR, stepDesc.getModuleType());
		assertEquals(stepDesc.toString(), "myManipulator", stepDesc.getName());
		assertEquals(stepDesc.toString(), "3.0", stepDesc.getVersion());

		// check exporter
		stepDesc = getFixture().getPepperJob().getStepDescs().get(2);
		assertNotNull(getFixture().getPepperJob().getStepDescs().get(0));
		assertEquals(MODULE_TYPE.EXPORTER, stepDesc.getModuleType());
		assertNotNull(stepDesc.getCorpusDesc());
		assertNotNull(stepDesc.getCorpusDesc().getFormatDesc());
		assertEquals("anyFormat", stepDesc.getCorpusDesc().getFormatDesc().getFormatName());
		assertEquals("1.0", stepDesc.getCorpusDesc().getFormatDesc().getFormatVersion());
		assertEquals(URI.createFileURI("/somewhere/"), stepDesc.getCorpusDesc().getCorpusPath());
	}

	/**
	 * Containing just manipulator and some customization properties
	 * 
	 * @throws XMLStreamException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void test_customization() throws XMLStreamException, IOException, SAXException {
		xml.writeStartDocument();
		xml.writeStartElement(WorkflowDescriptionReader.TAG_PEPEPR_JOB);
		xml.writeAttribute(WorkflowDescriptionReader.ATT_VERSION, "1.0");
		// manipulator
		xml.writeStartElement(WorkflowDescriptionReader.TAG_MANIPULATOR);
		xml.writeAttribute(WorkflowDescriptionReader.ATT_NAME, "myManipulator");
		xml.writeAttribute(WorkflowDescriptionReader.ATT_VERSION, "3.0");
		xml.writeStartElement(WorkflowDescriptionReader.TAG_CUSTOMIZATION);
		xml.writeStartElement(WorkflowDescriptionReader.TAG_PROP);
		xml.writeAttribute(WorkflowDescriptionReader.ATT_KEY, "prop1");
		xml.writeCharacters("val1");
		xml.writeEndElement();
		xml.writeStartElement(WorkflowDescriptionReader.TAG_PROP);
		xml.writeAttribute(WorkflowDescriptionReader.ATT_KEY, "prop2");
		xml.writeEndElement();
		xml.writeStartElement(WorkflowDescriptionReader.TAG_PROP);
		xml.writeAttribute(WorkflowDescriptionReader.ATT_KEY, "prop3");
		xml.writeCharacters("5\n");
		xml.writeEndElement();
		xml.writeEndElement();
		xml.writeEndElement();
		xml.writeEndElement();
		xml.writeEndDocument();
		xml.flush();

		xmlReader.parse(is);

		assertNotNull(getFixture().getPepperJob());
		assertNotNull(getFixture().getPepperJob().getStepDescs());
		assertEquals(1, getFixture().getPepperJob().getStepDescs().size());

		// check importer
		StepDesc stepDesc = getFixture().getPepperJob().getStepDescs().get(0);
		assertNotNull(getFixture().getPepperJob().getStepDescs().get(0));
		assertEquals(MODULE_TYPE.MANIPULATOR, stepDesc.getModuleType());
		assertEquals(stepDesc.toString(), "myManipulator", stepDesc.getName());
		assertEquals(stepDesc.toString(), "3.0", stepDesc.getVersion());
		assertNotNull(stepDesc.getProps());
		assertEquals(stepDesc.getProps().toString(), 3, stepDesc.getProps().size());

		assertTrue(stepDesc.getProps().toString(), stepDesc.getProps().containsKey("prop1"));
		assertEquals("val1", stepDesc.getProps().getProperty("prop1"));

		assertTrue(stepDesc.getProps().toString(), stepDesc.getProps().containsKey("prop2"));
		assertEquals("", stepDesc.getProps().getProperty("prop2"));

		assertTrue(stepDesc.getProps().toString(), stepDesc.getProps().containsKey("prop3"));
		assertEquals("5\n", stepDesc.getProps().getProperty("prop3"));
	}

	@Test
	public void testResolveURI() {
		URI uri = null;

		uri = getFixture().resolveURI("./bla");
		assertNotNull(uri);

		uri = getFixture().resolveURI("file:/bla");
		assertEquals("file", uri.scheme());
		assertNotNull(uri);

		uri = getFixture().resolveURI("/bla/");
		assertNotNull(uri);

		uri = getFixture().resolveURI("c:\bla");
		assertNotNull(uri);

		uri = getFixture().resolveURI("c:/bla");
		assertNotNull(uri);
	}
}

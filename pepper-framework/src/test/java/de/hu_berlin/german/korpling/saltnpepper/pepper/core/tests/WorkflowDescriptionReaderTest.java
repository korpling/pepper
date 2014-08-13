package de.hu_berlin.german.korpling.saltnpepper.pepper.core.tests;

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

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperJob;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperUtil;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperParamsReader;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.WorkflowDescriptionReader;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleXMLResourceException;

public class WorkflowDescriptionReaderTest {

	private WorkflowDescriptionReader fixture= null;
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

	private XMLReader xmlReader= null;
	private InputSource is= null;
	private SAXParserFactory factory = SAXParserFactory.newInstance();
	
	@Before
	public void setUp() throws XMLStreamException, IOException, ParserConfigurationException, SAXException {
        File file = new File(PepperUtil.getTempTestFile("workflowDescriptionTest").getAbsolutePath()+"test.xml");
        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        xml = xof.createXMLStreamWriter(new FileWriter(file.getAbsolutePath()));
		setFixture(new WorkflowDescriptionReader());
		getFixture().setPepperJob(new PepperJobImpl("job1"));
		getFixture().setLocation(URI.createFileURI(file.getAbsolutePath()));
			
		SAXParser parser = factory.newSAXParser();
		xmlReader = parser.getXMLReader();
		xmlReader.setContentHandler(getFixture());

		InputStream inputStream = new FileInputStream(file.getAbsolutePath());
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		is = new InputSource(reader);
		is.setEncoding("UTF-8");
	}

	
	@Test
	public void test() throws XMLStreamException, IOException, SAXException {
		xml.writeStartDocument();
		xml.writeStartElement(WorkflowDescriptionReader.TAG_PEPEPR_JOB);
			xml.writeAttribute(WorkflowDescriptionReader.ATT_VERSION, "1.0");
			xml.writeStartElement(WorkflowDescriptionReader.TAG_IMPORTER);
				xml.writeAttribute(WorkflowDescriptionReader.ATT_NAME, "myImporter");
			xml.writeEndElement();
		xml.writeEndElement();
		xml.writeEndDocument();
		xml.flush();
		
		xmlReader.parse(is);
		
		assertNotNull(getFixture().getPepperJob());
		assertNotNull(getFixture().getPepperJob().getStepDescs());
	}
}

package org.corpus_tools.pepper.service.adapters.tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.PepperModuleProperty;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.interfaces.PepperServiceImplConstants;
import org.eclipse.emf.common.util.URI;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.junit.Test;

public class MarshallingTests {
	
	@Test
	public void testPepperModuleDesc() throws JAXBException{
		PepperModuleDesc md = new PepperModuleDesc();
		/*formats*/
		md.addSupportedFormat("TXT", "1.0-k", URI.createURI("http://ref.any"));
		md.addSupportedFormat("XML", "1.0", null);
		/*settings*/
		md.setDesc("this is a module");
		md.setModuleType(MODULE_TYPE.EXPORTER);
		md.setName("TestModule");
		md.setSupplierContact(URI.createURI("me@you.com"));
		md.setSupplierHomepage(URI.createURI("http://any.org"));
		md.setVersion("3.0-SNAPSHOT");
		/*module properties*/
		PepperModuleProperties properties = new PepperModuleProperties();
		PepperModuleProperty<String> propertyA = new PepperModuleProperty<String>("propA", String.class, "this is a property", "hello", false);
		PepperModuleProperty<Boolean> propertyB = new PepperModuleProperty<Boolean>("boolProp", Boolean.class, "a boolean property", false, true);
		properties.addProperty(propertyA);
		properties.addProperty(propertyB);
		md.setProperties(properties);
		
		/*marshalling*/
		JAXBContext jc = JAXBContext.newInstance(PepperModuleDescMarshallable.class);
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, PepperServiceImplConstants.DATA_FORMAT);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream stream = new PrintStream(out);
		marshaller.marshal(new PepperModuleDescMarshallable(md), stream);
		
		/*unmarshalling*/
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, PepperServiceImplConstants.DATA_FORMAT);
		StreamSource xml = new StreamSource(new StringReader(out.toString()));
		PepperModuleDesc ummd = unmarshaller.unmarshal(xml, PepperModuleDescMarshallable.class).getValue().getPepperObject();
		
		/*################
		/*compare objects
		 *###############*/
		
		/*formats*/
		assertEquals(md.getSupportedFormats().size(), ummd.getSupportedFormats().size());
		for (FormatDesc formatDesc : md.getSupportedFormats()){
			ummd.removeSupportedFormat(formatDesc);
		}
		assertEquals(0, ummd.getSupportedFormats().size());
		/*settings*/
		assertEquals(0, md.compareTo(ummd));
		/*module properties*/
		assertEquals(md.getProperties().getPropertyNames().size(), ummd.getProperties().getPropertyNames().size());
		for (String propName : md.getProperties().getPropertyNames()){
			PepperModuleProperty<?> exp = md.getProperties().getProperty(propName);
			PepperModuleProperty<?> is = ummd.getProperties().getProperty(propName);			
			assertEquals(exp.getDescription(), is.getDescription());
			assertEquals(exp.getName(), is.getName());
			assertEquals(exp.getType(), is.getType());
			assertEquals(exp.getValue(), is.getValue());
		}
	}
}

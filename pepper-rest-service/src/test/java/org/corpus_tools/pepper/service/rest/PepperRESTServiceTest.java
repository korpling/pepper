package org.corpus_tools.pepper.service.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.common.PepperModuleDesc;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.service.lib.adapters.PepperModuleCollectionMarshallable;
import org.eclipse.emf.common.util.URI;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

//@RunWith(MockitoJUnitRunner.class)
public class PepperRESTServiceTest extends JerseyTest {

//	private Pepper pepper;
//
//	@Override
//	public ResourceConfig configure() {
//		pepper = mock(Pepper.class);
//		PepperRESTService service = new PepperRESTService();
//		service.setPepper(pepper);
//		return new ResourceConfig().register(service);
//	}
//
//	public class PepperModuleDescBuilder {
//		PepperModuleDesc moduleDesc;
//
//		public PepperModuleDescBuilder() {
//			moduleDesc = new PepperModuleDesc();
//		}
//
//		public PepperModuleDescBuilder name(String moduleName) {
//			moduleDesc.setName(moduleName);
//			return this;
//		}
//
//		public PepperModuleDescBuilder version(String moduleVersion) {
//			moduleDesc.setVersion(moduleVersion);
//			return this;
//		}
//
//		public PepperModuleDescBuilder type(MODULE_TYPE moduleType) {
//			moduleDesc.setModuleType(moduleType);
//			return this;
//		}
//
//		public PepperModuleDescBuilder desc(String desc) {
//			moduleDesc.setDesc(desc);
//			return this;
//		}
//
//		public PepperModuleDescBuilder supplierContact(URI supplierContact) {
//			moduleDesc.setSupplierContact(supplierContact);
//			return this;
//		}
//
//		public PepperModuleDescBuilder hp(URI hp) {
//			moduleDesc.setSupplierHomepage(hp);
//			return this;
//		}
//
//		public PepperModuleDescBuilder properties(PepperModuleProperties properties) {
//			moduleDesc.setProperties(properties);
//			return this;
//		}
//
//		public PepperModuleDesc build() {
//			return moduleDesc;
//		}
//	}
//
//	@Test
//	public void whenGettingModules_thenReturnListOfModules() {
//		Collection<PepperModuleDesc> descs = new ArrayList<>();
//		descs.add(new PepperModuleDescBuilder().name("myModule").version("1.0.0").type(MODULE_TYPE.IMPORTER).build());
//		
//		JAXBContext context;
//		Marshaller m;
//		StringWriter writer = new StringWriter();
//		try {
//			context = JAXBContext.newInstance(PepperModuleCollectionMarshallable.class);
//			m = context.createMarshaller();
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			m.marshal(descs, writer);
//			// } catch (JAXBException e) {
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(writer.toString());
//
//		when(pepper.getRegisteredModules()).thenReturn(descs);
//		Response output = target("modules").request().get();
//		System.out.println("----------------------_> " + output);
//		assertEquals("should return status 200", 200, output.getStatus());
//		assertNotNull("Should return list", output.getEntity());
//	}
}

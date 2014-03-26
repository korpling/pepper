package de.hu_berlin.german.korpling.saltnpepper.pepper.core.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.Pepper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperModuleDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolver;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.ModuleResolverImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperJobImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.JobNotFoundException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperManipulatorImpl;

public class PepperImplTest {

	private PepperImpl fixture= null;
	public PepperImpl getFixture() {
		return fixture;
	}
	public void setFixture(PepperImpl fixture) {
		this.fixture = fixture;
	}
	@Before
	public void setUp(){
		setFixture(new PepperImpl());
	}
	
	/**
	 * Checks whether creation of new job creates a new job, which not existed before and
	 * if it is returnable by {@link Pepper#getJob(String)}
	 */
	@Test
	public void testCreateJob() {
		assertTrue(getFixture().getJobIds().isEmpty());
		String jobId= getFixture().createJob();
		assertNotNull(jobId);
		
		try{
			getFixture().getJob("not existing");
			fail("job 'not existing' should not exist");
		}catch (JobNotFoundException e){
		}
		
		assertNotNull(getFixture().getJob(jobId));
		assertEquals(jobId, getFixture().getJob(jobId).getId());
	}
	
	/**
	 * Checks whether removing of a before created job works correctly.
	 */
	@Test
	public void testRemoveJob() {
		try{
			getFixture().getJob("not existing");
			fail("job 'not existing' should not exist");
		}catch (JobNotFoundException e){
		}
		
		String jobId= getFixture().createJob();
		File location= getFixture().getLocation(jobId);
		assertTrue(location.exists());
		assertNotNull(getFixture().getJob(jobId));
		assertTrue(getFixture().removeJob(jobId));
		
		assertFalse(location.exists());
		try{
			getFixture().getJob(jobId);
			fail("job '"+jobId+"' should not exist any more");
		}catch (JobNotFoundException e){
		}
	}
	
	/**
	 * Checks whether clean up of before created job works correctly.
	 */
	@Test
	public void testCleanUp() {
		String jobId= getFixture().createJob();
		File location= getFixture().getLocation(jobId);
		
		assertTrue(location.exists());
		assertNotNull(getFixture().getJob(jobId));
		
		((PepperImpl)getFixture()).cleanUp();
		
		assertFalse(location.exists());
		try{
			getFixture().getJob(jobId);
			fail("job '"+jobId+"' should not exist any more");
		}catch (JobNotFoundException e){
		}
	}
	/**
	 * Tests get and set of {@link ModuleResolverImpl}
	 */
	@Test
	public void testModuleResolver(){
		ModuleResolver resolver= new ModuleResolverImpl();
		getFixture().setModuleResolver(resolver);
		assertEquals(resolver, getFixture().getModuleResolver());
	}
	/**
	 * Tests if {@link ModuleResolverImpl} is injected to {@link PepperJobImpl}
	 */
	@Test
	public void testModuleResolver_PepperJob(){
		ModuleResolver resolver= new ModuleResolverImpl();
		getFixture().setModuleResolver(resolver);
		String jobId= getFixture().createJob();
		assertEquals(resolver, getFixture().getPepperJobImpl(jobId).getModuleResolver());
	}
	
	@Test
	public void testGetRegisteredModules(){
		
		ModuleResolver resolver= new ModuleResolverImpl() {
			@Override
			public List<PepperImporter> getPepperImporters() {
				Vector<PepperImporter> ret= new Vector<PepperImporter>();
				PepperImporter imp= new PepperImporterImpl() {
					{ setName("MyImporter"); }
				};
				ret.add(imp);
				return(ret);
			}
			@Override
			public List<PepperManipulator> getPepperManipulators() {
				Vector<PepperManipulator> ret= new Vector<PepperManipulator>();
				PepperManipulator exp= new PepperManipulatorImpl() {
					{ setName("MyManipulator"); }
				};
				ret.add(exp);
				return(ret);
			}
			@Override
			public List<PepperExporter> getPepperExporters() {
				Vector<PepperExporter> ret= new Vector<PepperExporter>();
				PepperExporter exp= new PepperExporterImpl() {
					{ setName("MyExporter"); }
				};
				ret.add(exp);
				return(ret);
			}
		};
		
		getFixture().setModuleResolver(resolver);
		Collection<PepperModuleDesc> modules= getFixture().getRegisteredModules();
		
		assertNotNull(modules);
		assertEquals(3, modules.size());		
	}
}

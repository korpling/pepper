package org.corpus_tools.pepper.service.osgi;

import org.corpus_tools.pepper.service.rest.PepperRESTService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator{

	private ServiceRegistration<PepperRESTService> registration;
	
	@Override
	public void start(BundleContext context) throws Exception {		
		registration = context.registerService(PepperRESTService.class, new PepperRESTService(), null);		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
	}

}

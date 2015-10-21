package org.corpus_tools.pepper.service.osgi;

import org.corpus_tools.pepper.service.PepperService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator{

	private ServiceRegistration<PepperService> registration;
	
	@Override
	public void start(BundleContext context) throws Exception {		
		registration = context.registerService(PepperService.class, new PepperService(), null);		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
	}

}

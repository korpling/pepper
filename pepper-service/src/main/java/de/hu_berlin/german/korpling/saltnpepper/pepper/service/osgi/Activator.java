package de.hu_berlin.german.korpling.saltnpepper.pepper.service.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import de.hu_berlin.german.korpling.saltnpepper.pepper.service.PepperService;

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

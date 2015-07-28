package de.hu_berlin.german.korpling.saltnpepper.pepper.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator{

	private ServiceRegistration<MyResource> registration;
	
	@Override
	public void start(BundleContext context) throws Exception {
		registration = context.registerService(MyResource.class, new MyResource(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
	}

}

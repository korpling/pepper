package de.hu_berlin.german.korpling.saltnpepper.pepper.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class AppClassProvider extends Application {
	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(MyResource.class);
		return s;
	}
	
	
}

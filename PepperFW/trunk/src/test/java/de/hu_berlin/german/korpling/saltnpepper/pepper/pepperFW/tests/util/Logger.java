/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.tests.util;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

public class Logger implements LogService
{

	@Override
	public void log(int level, String message) 
	{
		System.out.println(message);
	}

	@Override
	public void log(int level, String message, Throwable exception) 
	{
		System.out.println(message);
	}

	@Override
	public void log(ServiceReference sr, int level, String message) 
	{
		System.out.println(message);
	}

	@Override
	public void log(ServiceReference sr, int level, String message,
			Throwable exception) 
	{
		System.out.println(message);
	}

}

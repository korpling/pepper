/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.coreModules;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.PepperConfiguration;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.u.saltnpepper.graph.Identifier;

@Component(name = "DoNothingExporterComponent", factory = "PepperExporterComponentFactory")
public class DoNothingExporter extends PepperExporterImpl implements PepperExporter {
	public static final String MODULE_NAME = "DoNothingExporter";
	public static final String FORMAT_NAME = "doNothing";
	public static final String FORMAT_VERSION = "0.0";

	@Activate
	public void activate(ComponentContext componentContext) {
		super.activate(componentContext);
	}

	public DoNothingExporter() {
		// setting name of module
		super(MODULE_NAME);
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		setDesc("This is a dummy exporter which exports nothing. This exporter can be used to check if a corpus is importable. ");
		this.addSupportedFormat("doNothing", "0.0", null);
	}

	/**
	 * Creates a mapper of type {@link EXMARaLDA2SaltMapper}. {@inheritDoc
	 * PepperModule#createPepperMapper(Identifier)}
	 */
	@Override
	public PepperMapper createPepperMapper(Identifier sElementId) {
		PepperMapper mapper = new DoNothingMapper();
		return (mapper);
	}

}

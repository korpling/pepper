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
package org.corpus_tools.pepper.core;

import java.util.Properties;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperties;

/**
 * This class is an extension of the class {@link StepDesc} and is for internal
 * use only. This class extends the abstract Step description of
 * {@link StepDesc} with the following:
 * <ul>
 * <li>a unique identifier representing the position of this step in workflow</li>
 * </ul>
 * 
 * @author Florian Zipser
 *
 */
public class Step extends StepDesc {
	/**
	 * Initializes this object and sets its internal id, which represents its
	 * position in list of steps.
	 * 
	 * @param id
	 *            unique id for a {@link PepperJobImpl}
	 */
	public Step(String id) {
		if (id == null)
			throw new PepperFWException("Cannot create a step description with an empty id value.");
		this.id = id;
	}

	/**
	 * Initializes this object and sets its internal id, which represents its
	 * position in list of steps.
	 * 
	 * @param id
	 *            unique id for a {@link PepperJobImpl}
	 * @param stepDesc
	 *            sets the abstract {@link StepDesc} object to used here
	 */
	public Step(String id, StepDesc stepDesc) {
		this(id);
		setName(stepDesc.getName());
		setVersion(stepDesc.getVersion());
		setModuleType(stepDesc.getModuleType());
		setProps(stepDesc.getProps());
		setCorpusDesc(stepDesc.getCorpusDesc());
	}

	/**
	 * If a {@link PepperModule} is set, overrides the set name with the name of
	 * the {@link PepperModule}.
	 */
	public String getName() {
		if ((getModuleController() != null) && (getModuleController().getPepperModule() != null)) {
			return (getModuleController().getPepperModule().getName());
		} else
			return (super.getName());
	}

	/**
	 * internal id, which represents position of the {@link Step} in list of
	 * steps.
	 **/
	private String id = null;

	/**
	 * Returns the internal id, which represents position of the {@link Step} in
	 * list of steps.
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * {@inheritDoc StepDesc#setCorpusDesc(CorpusDesc)} Further passes the given
	 * {@link CorpusDesc} to the {@link ModuleControllerImpl}, if already set.
	 */
	@Override
	public synchronized Step setCorpusDesc(CorpusDesc corpusDesc) {
		super.setCorpusDesc(corpusDesc);
		if ((getModuleController() != null) && (getModuleController().getPepperModule() != null)) {
			if (getModuleController().getPepperModule() instanceof PepperImporter) {
				((PepperImporter) getModuleController().getPepperModule()).setCorpusDesc(getCorpusDesc());
			} else if (getModuleController().getPepperModule() instanceof PepperExporter) {
				((PepperExporter) getModuleController().getPepperModule()).setCorpusDesc(getCorpusDesc());
			}
		}
		return(this);
	}

	/**
	 * The {@link ModuleControllerImpl} object which belongs to this step and
	 * acts as a bridge between Pepper and the Pepper module.
	 **/
	private ModuleControllerImpl moduleController = null;

	/**
	 * Creates a {@link ModuleControllerImpl} object as container for the passed
	 * {@link PepperModule} object. The {@link PepperModule} object is injected
	 * to the {@link ModuleControllerImpl} object. If
	 * {@link #setProps(Properties)} was already called, {@link Properties} will
	 * be passed to {@link PepperModule} to create a
	 * {@link PepperModuleProperties} object.
	 * 
	 * @param pepperModule
	 */
	public synchronized void setPepperModule(PepperModule pepperModule) {
		if (pepperModule == null)
			throw new PepperFWException("Cannot create a step with an empty Pepper module object.");
		synchronized (this) {
			moduleController = new ModuleControllerImpl(getId() + ":" + pepperModule.getName());
			moduleController.setPepperModule(pepperModule);

			if (pepperModule instanceof PepperImporter) {
				((PepperImporter) pepperModule).setCorpusDesc(getCorpusDesc());
			} else if (pepperModule instanceof PepperExporter) {
				((PepperExporter) pepperModule).setCorpusDesc(getCorpusDesc());
			}
			if (getProps() != null) {
				if (pepperModule.getProperties() != null) {
					pepperModule.getProperties().setPropertyValues(getProps());
				}
			}
		}
	}

	/**
	 * The {@link ModuleControllerImpl} object which belongs to this step and
	 * acts as a bridge between Pepper and the Pepper module.
	 * 
	 * @return controller object for {@link PepperModule}
	 */
	public ModuleControllerImpl getModuleController() {
		return (moduleController);
	}

	/**
	 * Returns the type of the {@link PepperModule} to be used. If no
	 * {@link MODULE_TYPE} is set, but the {@link ModuleControllerImpl} is, the
	 * type is estimated.
	 * 
	 * @return module type
	 */
	public MODULE_TYPE getModuleType() {
		if ((super.getModuleType() == null) && (getModuleController() != null) && (getModuleController().getPepperModule() != null)) {
			synchronized (this) {
				if ((super.getModuleType() == null) && (getModuleController() != null) && (getModuleController().getPepperModule() != null)) {
					setModuleType(getModuleController().getPepperModule().getModuleType());
				}
			}
		}
		return super.getModuleType();
	}

	/**
	 * If {@link #setPepperModule(PepperModule)} was already called, the passed
	 * {@link Properties} object is used to create a
	 * {@link PepperModuleProperties} object, which is set to given
	 * {@link PepperModule}.
	 * 
	 * @param props
	 *            properties to customize processing
	 */
	public synchronized Step setProps(Properties props) {
		if (getModuleController() != null) {
			getModuleController().getPepperModule().getProperties().setPropertyValues(props);
		} else {
			super.setProps(props);
		}
		return(this);
	}
}

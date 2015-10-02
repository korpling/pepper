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
package de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperManipulator;
import de.hu_berlin.u.saltnpepper.salt.common.SCorpusGraph;
import de.hu_berlin.u.saltnpepper.salt.common.SaltProject;

/**
 * <p>
 * This class is a helper class for creating tests for {@link PepperManipulator}
 * s. This class provides a fixture declaration which could be called via
 * {@link #setFixture(PepperManipulator)}. The fixture which is returned via
 * {@link #getFixture()} is of type {@link PepperManipulator}. To create an
 * easier access, we recommend to overwrite the method {@link #getFixture()} as
 * follows:
 * 
 * <pre>
 * &#064;Override
 * public MY_MANIPULATOR_CLASS getFixture() {
 * 	return (MY_MANIPULATOR_CLASS) fixture;
 * }
 * </pre>
 * 
 * The method {@link #setFixture(PepperManipulator)} sets the
 * {@link SaltProject} and creates a single {@link SCorpusGraph} object, which
 * is added to the list of corpus structures in the salt project. To access the
 * salt project or the corpus structure use the following code:
 * 
 * <pre>
 * 	getFixture().getSaltProject();
 *  getFixture().getSaltProject().getSCorpusGraphs()
 * </pre>
 * 
 * </p>
 * <p>
 * To run the test call {@link #start()} in your test method. This will start
 * the test environment, which simulates a Pepper conversion process.
 * </p>
 * 
 * @author florian
 *
 */
public abstract class PepperManipulatorTest extends PepperModuleTest {

}

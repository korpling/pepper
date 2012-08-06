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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW;

import java.util.Properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.impl.PepperConverterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperParams.PepperParams;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pepper Converter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperModuleResolver <em>Pepper Module Resolver</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperParams <em>Pepper Params</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperJobs <em>Pepper Jobs</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperJ2CMonitors <em>Pepper J2C Monitors</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperConverter()
 * @model
 */
public interface PepperConverter extends EObject
{
	/**
	 * Returns a static {@link LogService} object, which is created, when the first instance of {@link PepperConverter}
	 * is created. The {@link LogService} object is given by the OSGi environment. Using the {@link PepperConverter#logService}
	 * will enable a static access to a log service. 
	 */
	public static LogService logService= PepperConverterImpl.getLogger();
	
	
	/**
	 * Sets a LogService for logging.
	 * @param logService
	 */
	public void setLogService(LogService logService);
	
	/**
	 * Unsets the logService for logging.
	 * @param logService
	 */
	public void unsetLogService(LogService logService);
	
	/**
	 * Returns the value of the '<em><b>Pepper Module Resolver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Module Resolver</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Module Resolver</em>' containment reference.
	 * @see #isSetPepperModuleResolver()
	 * @see #unsetPepperModuleResolver()
	 * @see #setPepperModuleResolver(PepperModuleResolver)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperConverter_PepperModuleResolver()
	 * @model containment="true" unsettable="true" required="true"
	 * @generated
	 */
	PepperModuleResolver getPepperModuleResolver();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperModuleResolver <em>Pepper Module Resolver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Module Resolver</em>' containment reference.
	 * @see #isSetPepperModuleResolver()
	 * @see #unsetPepperModuleResolver()
	 * @see #getPepperModuleResolver()
	 * @generated
	 */
	void setPepperModuleResolver(PepperModuleResolver value);

	/**
	 * Unsets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperModuleResolver <em>Pepper Module Resolver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPepperModuleResolver()
	 * @see #getPepperModuleResolver()
	 * @see #setPepperModuleResolver(PepperModuleResolver)
	 * @generated
	 */
	void unsetPepperModuleResolver();

	/**
	 * Returns whether the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperModuleResolver <em>Pepper Module Resolver</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Pepper Module Resolver</em>' containment reference is set.
	 * @see #unsetPepperModuleResolver()
	 * @see #getPepperModuleResolver()
	 * @see #setPepperModuleResolver(PepperModuleResolver)
	 * @generated
	 */
	boolean isSetPepperModuleResolver();

	/**
	 * Returns the value of the '<em><b>Pepper Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Params</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Params</em>' attribute.
	 * @see #setPepperParams(PepperParams)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperConverter_PepperParams()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.PepperParams"
	 * @generated
	 */
	PepperParams getPepperParams();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperParams <em>Pepper Params</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Params</em>' attribute.
	 * @see #getPepperParams()
	 * @generated
	 */
	void setPepperParams(PepperParams value);

	/**
	 * Returns the value of the '<em><b>Pepper Jobs</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperJob}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Convert Jobs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Jobs</em>' containment reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperConverter_PepperJobs()
	 * @model containment="true"
	 * @generated
	 */
	EList<PepperJob> getPepperJobs();

	/**
	 * Returns the value of the '<em><b>Pepper J2C Monitors</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFinishableMonitor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper J2C Monitors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper J2C Monitors</em>' containment reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperConverter_PepperJ2CMonitors()
	 * @model containment="true"
	 * @generated
	 */
	EList<PepperFinishableMonitor> getPepperJ2CMonitors();

	/**
	 * Returns the value of the '<em><b>Parallelized</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parallelized</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parallelized</em>' attribute.
	 * @see #setParallelized(boolean)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperConverter_Parallelized()
	 * @model default="false"
	 * @generated
	 */
	boolean isParallelized();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#isParallelized <em>Parallelized</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parallelized</em>' attribute.
	 * @see #isParallelized()
	 * @generated
	 */
	void setParallelized(boolean value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' attribute.
	 * @see #setProperties(Properties)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperConverter_Properties()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.Properties"
	 * @generated
	 */
	Properties getProperties();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getProperties <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' attribute.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(Properties value);

	/**
	 * Returns the value of the '<em><b>Pepper Params URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pepper Params URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pepper Params URI</em>' attribute.
	 * @see #setPepperParamsURI(URI)
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperFWPackage#getPepperConverter_PepperParamsURI()
	 * @model dataType="de.hub.corpling.pepper.pepperFW.URI"
	 * @generated
	 */
	URI getPepperParamsURI();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter#getPepperParamsURI <em>Pepper Params URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pepper Params URI</em>' attribute.
	 * @see #getPepperParamsURI()
	 * @generated
	 */
	void setPepperParamsURI(URI value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pepperParamUriDataType="de.hub.corpling.pepper.pepperFW.URI"
	 * @generated
	 */
	void setPepperParams(URI pepperParamUri);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="de.hub.corpling.pepper.pepperFW.PepperException"
	 * @generated
	 */
	void start() throws PepperModuleException, PepperConvertException, PepperException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="de.hub.corpling.pepper.pepperFW.PepperException"
	 * @generated
	 */
	void startPepperConvertJob(Integer id) throws PepperException;
} // PepperConverter

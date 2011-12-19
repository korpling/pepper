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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.impl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesPackageImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfacePackage;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperInterfacePackageImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.PepperXSLTExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XSLTTransformer;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesPackage;



import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class XsltModulesPackageImpl extends EPackageImpl implements XsltModulesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pepperXSLTExporterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xsltTransformerEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.xsltModules.XsltModulesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private XsltModulesPackageImpl() {
		super(eNS_URI, XsltModulesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link XsltModulesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static XsltModulesPackage init() {
		if (isInited) return (XsltModulesPackage)EPackage.Registry.INSTANCE.getEPackage(XsltModulesPackage.eNS_URI);

		// Obtain or create and register package
		XsltModulesPackageImpl theXsltModulesPackage = (XsltModulesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof XsltModulesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new XsltModulesPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		PepperModulesPackageImpl thePepperModulesPackage = (PepperModulesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PepperModulesPackage.eNS_URI) instanceof PepperModulesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PepperModulesPackage.eNS_URI) : PepperModulesPackage.eINSTANCE);

		// Create package meta-data objects
		theXsltModulesPackage.createPackageContents();
		thePepperModulesPackage.createPackageContents();

		// Initialize created meta-data
		theXsltModulesPackage.initializePackageContents();
		thePepperModulesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theXsltModulesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(XsltModulesPackage.eNS_URI, theXsltModulesPackage);
		return theXsltModulesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPepperXSLTExporter() {
		return pepperXSLTExporterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPepperXSLTExporter_XsltTransformer() {
		return (EReference)pepperXSLTExporterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getXSLTTransformer() {
		return xsltTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XsltModulesFactory getXsltModulesFactory() {
		return (XsltModulesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		pepperXSLTExporterEClass = createEClass(PEPPER_XSLT_EXPORTER);
		createEReference(pepperXSLTExporterEClass, PEPPER_XSLT_EXPORTER__XSLT_TRANSFORMER);

		xsltTransformerEClass = createEClass(XSLT_TRANSFORMER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		PepperModulesPackage thePepperModulesPackage = (PepperModulesPackage)EPackage.Registry.INSTANCE.getEPackage(PepperModulesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		pepperXSLTExporterEClass.getESuperTypes().add(thePepperModulesPackage.getPepperExporter());
		xsltTransformerEClass.getESuperTypes().add(thePepperModulesPackage.getPersistenceConnector());

		// Initialize classes and features; add operations and parameters
		initEClass(pepperXSLTExporterEClass, PepperXSLTExporter.class, "PepperXSLTExporter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPepperXSLTExporter_XsltTransformer(), this.getXSLTTransformer(), null, "xsltTransformer", null, 0, 1, PepperXSLTExporter.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(xsltTransformerEClass, XSLTTransformer.class, "XSLTTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = addEOperation(xsltTransformerEClass, null, "transform", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePepperModulesPackage.getURI(), "sourceURI", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePepperModulesPackage.getURI(), "targetURI", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePepperModulesPackage.getURI(), "xsltURI", 0, 1, IS_UNIQUE, IS_ORDERED);
	}

} //XsltModulesPackageImpl

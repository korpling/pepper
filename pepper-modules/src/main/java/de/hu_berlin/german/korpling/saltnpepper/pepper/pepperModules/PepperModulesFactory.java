/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesPackage
 * @generated
 */
public interface PepperModulesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PepperModulesFactory eINSTANCE = de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperModulesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Pepper Module</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Module</em>'.
	 * @generated
	 */
	PepperModule createPepperModule();

	/**
	 * Returns a new object of class '<em>Pepper Importer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Importer</em>'.
	 * @generated
	 */
	PepperImporter createPepperImporter();

	/**
	 * Returns a new object of class '<em>Pepper Exporter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Exporter</em>'.
	 * @generated
	 */
	PepperExporter createPepperExporter();

	/**
	 * Returns a new object of class '<em>Format Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Format Definition</em>'.
	 * @generated
	 */
	FormatDefinition createFormatDefinition();

	/**
	 * Returns a new object of class '<em>Corpus Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Corpus Definition</em>'.
	 * @generated
	 */
	CorpusDefinition createCorpusDefinition();

	/**
	 * Returns a new object of class '<em>Pepper Manipulator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pepper Manipulator</em>'.
	 * @generated
	 */
	PepperManipulator createPepperManipulator();

	/**
	 * Returns a new object of class '<em>Extension Factory Pair</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extension Factory Pair</em>'.
	 * @generated
	 */
	ExtensionFactoryPair createExtensionFactoryPair();

	/**
	 * Returns a new object of class '<em>Persistence Connector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Persistence Connector</em>'.
	 * @generated
	 */
	PersistenceConnector createPersistenceConnector();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PepperModulesPackage getPepperModulesPackage();

} //PepperModulesFactory

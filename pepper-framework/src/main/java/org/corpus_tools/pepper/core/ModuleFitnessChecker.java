package org.corpus_tools.pepper.core;

import java.util.ArrayList;
import java.util.Collection;

import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.PepperUtil;
import org.corpus_tools.pepper.common.ModuleFitness.Fitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.eclipse.emf.common.util.URI;
import org.hamcrest.core.IsSame;

public class ModuleFitnessChecker {
	private static URI corpusPath= URI.createFileURI(PepperUtil.getTempFile("fitnessCheck").getAbsolutePath());
	
	/**
	 * Returns a {@link ModuleFitness} value for each {@link PepperModule} in specified list.
	 * @param modules
	 * @return
	 */
	public static Collection<ModuleFitness> checkFitness(final Collection<PepperModule> modules) {
		final Collection<ModuleFitness> moduleFitness= new ArrayList<>();
		for (PepperModule module: modules){
			if (module!= null){
				moduleFitness.add(checkFitness(module));
			}
		}
		return moduleFitness;
	}

	/**
	 * Returns a {@link ModuleFitness} value for specified {@link PepperModule}.
	 * @param modules
	 * @return
	 */
	public static ModuleFitness checkFitness(final PepperModule module) {
		if (module== null){
			return null;
		}
		final ModuleFitness fitness= new ModuleFitness(module.getName());
		
		//check important features
		fitness.addFitnessFeature(FitnessFeature.IS_READY_TO_RUN, module.isReadyToStart()? Fitness.FIT: Fitness.CRITICAL);
		
		//check optional features
		fitness.addFitnessFeature(FitnessFeature.SUPPORTS_SUPPLIER_HP, module.getSupplierHomepage()!= null? Fitness.FIT: Fitness.UNFIT);
		fitness.addFitnessFeature(FitnessFeature.SUPPORTS_SUPPLIER_CONTACT, module.getSupplierContact()!= null? Fitness.FIT: Fitness.UNFIT);
		
		if (module instanceof PepperImporter){
			Double isImportableRate= ((PepperImporter)module).isImportable(corpusPath);
			fitness.addFitnessFeature(FitnessFeature.IS_IMPORTABLE, isImportableRate!= null? Fitness.FIT: Fitness.UNFIT);
		}
		return fitness;
	}
}

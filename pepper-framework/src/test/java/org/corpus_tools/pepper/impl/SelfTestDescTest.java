package org.corpus_tools.pepper.impl;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.corpus_tools.pepper.modules.SelfTestDesc;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.samples.*;
import static org.assertj.core.api.Assertions.*;

public class SelfTestDescTest {

	@Test
	public void whenComparingIdenticalModels_thenReturnTrue(){
		final SelfTestDesc selfTestDesc= new SelfTestDesc(URI.createURI(""), URI.createURI(""));
		
		final SaltProject actual= SampleGenerator.createSaltProject();
		final SaltProject expected= SampleGenerator.createSaltProject();
		
		assertThat(selfTestDesc.compare(actual, expected)).isTrue();
	}
	
	@Test
	public void whenComparingDifferentModels_thenReturnFalse(){
		final SelfTestDesc selfTestDesc= new SelfTestDesc(URI.createURI(""), URI.createURI(""));
		
		final SaltProject actual= SampleGenerator.createSaltProject();
		final SaltProject expected= SampleGenerator.createSaltProject();
		expected.getCorpusGraphs().get(0).removeNode(expected.getCorpusGraphs().get(0).getCorpora().get(0));
		
		assertThat(selfTestDesc.compare(actual, expected)).isFalse();
	}
	
}

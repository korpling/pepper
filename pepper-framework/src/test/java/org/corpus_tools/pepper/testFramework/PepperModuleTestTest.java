package org.corpus_tools.pepper.testFramework;

import org.corpus_tools.pepper.impl.PepperManipulatorImpl;
import org.corpus_tools.pepper.impl.PepperModuleImpl;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

public class PepperModuleTestTest {

	private PepperModuleTest fixture;

	@Before
	public void beforeEach() {
		fixture = new PepperModuleTest() {
		};
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatFixtureIsSetAndFixtureIsSet_thenFail() {
		fixture.checkThatFixtureIsSet();
	}

	@Test
	public void whenCheckThatFixtureIsSetAndNoFixtureIsSet_thenSuccess() {
		fixture.setFixture(new PepperManipulatorImpl() {
		});

		fixture.checkThatFixtureIsSet();
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatModuleHasNameAndNoNameIsSet_thenFail() {
		fixture.setFixture(new ModuleWithoutName());
		fixture.checkThatModuleHasName();
	}

	@Test
	public void whenCheckThatModuleHasNameAndNoNameIsSet_thenSuccess() {
		fixture.setFixture(new ModuleWithName());
		fixture.checkThatModuleHasName();
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatResourcePathIsSetAndItIsNotSet_thenFail() {
		fixture.setFixture(new ModuleWithOutResource());
		fixture.checkThatResourcePathIsSet();
	}

	@Test
	public void whenCheckThatResourcePathIsSetAndItIsSet_thenSuccess() {
		fixture.setFixture(new ModuleWithResource());
		fixture.checkThatResourcePathIsSet();
	}

	@Test(expected = AssertionError.class)
	public void whenCheckThatCorpusGraphIsSettableAndCorpusGraphIsNotSettable_thenFail() {
		fixture.setFixture(new ModuleWhoseCorpusGraphIsNotSettable());
		fixture.checkThatCorpusGraphIsSettable();
	}

	@Test
	public void whenCheckThatCorpusGraphIsSettableAndCorpusGraphIsSettable_thenSuccess() {
		fixture.setFixture(new ModuleWithName());
		fixture.checkThatCorpusGraphIsSettable();
	}

	public class ModuleWithName extends PepperModuleImpl {
		public ModuleWithName() {
			setName("AnyName");
		}
	}

	public class ModuleWithoutName extends PepperModuleImpl {
		public ModuleWithoutName() {
			super(null);
		}
	}

	public class ModuleWithResource extends PepperModuleImpl {
		public ModuleWithResource() {
			setResources(URI.createFileURI("."));
		}
	}

	public class ModuleWithOutResource extends PepperModuleImpl {
		@Override
		public void setResources(URI newResources) {
			// do nothing
		}
	}

	public class ModuleWhoseCorpusGraphIsNotSettable extends PepperModuleImpl {
		@Override
		public void setCorpusGraph(SCorpusGraph newSCorpusGraph) {
			// do nothing
		}
	}
}

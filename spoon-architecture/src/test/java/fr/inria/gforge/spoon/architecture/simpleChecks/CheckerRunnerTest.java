package fr.inria.gforge.spoon.architecture.simpleChecks;

import org.junit.Test;
import fr.inria.gforge.spoon.architecture.runner.SpoonArchitecturalCheckerImpl;

public class CheckerRunnerTest {

	@Test
	public void allChecksMustRun() {
		SpoonArchitecturalCheckerImpl.createChecker().runChecks();
	}
}

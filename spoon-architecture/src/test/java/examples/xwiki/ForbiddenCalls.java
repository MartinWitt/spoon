package examples.xwiki;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spoon.architecture.defaultChecks.ForbiddenInvocation;
import spoon.architecture.runner.Architecture;
import spoon.reflect.CtModel;

public class ForbiddenCalls {

	private Map<String, List<String>> methodsByType = new HashMap<>();


	@Architecture
	public void forbiddenCallsTest(CtModel srcModel, CtModel testModel) {
		methodsByType.put("java.io.PrintStream", Arrays.asList("println"));
		ForbiddenInvocation.forbiddenInvocationCheck(methodsByType, System.out::println).runCheck(srcModel);
		ForbiddenInvocation.forbiddenInvocationCheck(methodsByType, System.out::println).runCheck(testModel);
	}
}

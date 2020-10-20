package fr.inria.gforge.spoon.architecture.runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

public abstract class AbstractSpoonArchitecturalChecker {

	final static Logger logger = LoggerFactory.getLogger(AbstractSpoonArchitecturalChecker.class);
	abstract Map<String,CtModel> getModelByName();
	public void runChecks() {
		logger.debug("Starting checks");
		CtModel testModel = getModelByName().get("testmodel");
		testModel.getElements(new TypeFilter<CtMethod<?>>(CtMethod.class)).stream()
				.filter(v -> v.hasAnnotation(Architecture.class))
				.map(v -> v.getReference().getActualMethod())
				.filter(Objects::nonNull)
				.forEach(clazz -> invokeMethod(clazz));
	}

	public void runChecks(String testPath) {
		logger.debug("Starting checks");
		CtModel testModel = getModelByName().get(testPath.toLowerCase());
		testModel.getElements(new TypeFilter<CtMethod<?>>(CtMethod.class)).stream()
				.filter(v -> v.hasAnnotation(Architecture.class))
				.map(v -> v.getReference().getActualMethod())
				.filter(Objects::nonNull)
				.forEach(clazz -> invokeMethod(clazz));
	}
	private void invokeMethod(Method method) {
		System.out.println("Running check: " + method.getName());
		try {
			method.invoke(createCallTarget(method), createArguments(method));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| InstantiationException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private Object createCallTarget(Method method) throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return method.getDeclaringClass().getDeclaredConstructor().newInstance();
	}
	private Object[] createArguments(Method method) {
		System.out.println("creating args for " + method.getName() + " count: " + method.getParameterCount());
		List<Object> modelParameter = new ArrayList<>();
		String[] modelNames = method.getAnnotation(Architecture.class).modelNames();
		for(String name : modelNames) {
			modelParameter.add(getModelByName().get(name.toLowerCase()));
		}
		return modelParameter.toArray();
	}
	public abstract void insertInputPath(String name, String path);
	public abstract void insertInputPath(String name, CtModel model);
}

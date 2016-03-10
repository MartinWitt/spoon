package spoon.test.annotation;

import org.junit.Test;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.test.annotation.testclasses.AnnotationValues;
import spoon.testing.utils.ModelUtils;

import java.lang.annotation.Annotation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static spoon.test.annotation.AnnotationValuesTest.Request.on;
import static spoon.testing.utils.ModelUtils.buildClass;

public class AnnotationValuesTest {
	static int i;
	@Test
	public void testValuesOnJava7Annotation() throws Exception {
		CtType<AnnotationValues> aClass = buildClass(AnnotationValues.class);

		CtAnnotation<?> ctAnnotation = on(aClass).giveMeAnnotation(AnnotationValues.Annotation.class);
		on(ctAnnotation).giveMeAnnotationValue("integer").isTypedBy(CtLiteral.class);
		on(ctAnnotation).giveMeAnnotationValue("integers").isTypedBy(CtNewArray.class);
		on(ctAnnotation).giveMeAnnotationValue("string").isTypedBy(CtLiteral.class);
		on(ctAnnotation).giveMeAnnotationValue("strings").isTypedBy(CtLiteral.class);
		on(ctAnnotation).giveMeAnnotationValue("clazz").isTypedBy(CtFieldAccess.class);
		on(ctAnnotation).giveMeAnnotationValue("classes").isTypedBy(CtNewArray.class);
		on(ctAnnotation).giveMeAnnotationValue("b").isTypedBy(CtLiteral.class);
		on(ctAnnotation).giveMeAnnotationValue("e").isTypedBy(CtFieldAccess.class);
		on(ctAnnotation).giveMeAnnotationValue("ia").isTypedBy(CtAnnotation.class);
		on(ctAnnotation).giveMeAnnotationValue("ias").isTypedBy(CtNewArray.class);
	}

	@Test
	public void testValuesOnJava8Annotation() throws Exception {
		CtType<AnnotationValues> aClass = buildClass(AnnotationValues.class);
		CtConstructorCall aConstructorCall = aClass.getMethod("method").getElements(new TypeFilter<>(CtConstructorCall.class)).get(0);

		CtAnnotation<?> ctAnnotation = on(aConstructorCall.getType()).giveMeAnnotation(AnnotationValues.Annotation.class);
		on(ctAnnotation).giveMeAnnotationValue("integer").isTypedBy(CtLiteral.class);
		on(ctAnnotation).giveMeAnnotationValue("integers").isTypedBy(CtNewArray.class);
		on(ctAnnotation).giveMeAnnotationValue("string").isTypedBy(CtLiteral.class);
		on(ctAnnotation).giveMeAnnotationValue("strings").isTypedBy(CtLiteral.class);
		on(ctAnnotation).giveMeAnnotationValue("clazz").isTypedBy(CtFieldAccess.class);
		on(ctAnnotation).giveMeAnnotationValue("classes").isTypedBy(CtNewArray.class);
		on(ctAnnotation).giveMeAnnotationValue("b").isTypedBy(CtLiteral.class);
		on(ctAnnotation).giveMeAnnotationValue("e").isTypedBy(CtFieldAccess.class);
		on(ctAnnotation).giveMeAnnotationValue("ia").isTypedBy(CtAnnotation.class);
		on(ctAnnotation).giveMeAnnotationValue("ias").isTypedBy(CtNewArray.class);
	}

	@Test
	public void testCtAnnotationAPI() throws Exception {
		Factory factory = ModelUtils.createFactory();
		CtAnnotation<Annotation> annotation = factory.Core().createAnnotation();
		annotation.addValue("integers", 7);

		on(annotation).giveMeAnnotationValue("integers").isTypedBy(CtLiteral.class);

		annotation.addValue("integers", 42);

		on(annotation).giveMeAnnotationValue("integers").isTypedBy(CtNewArray.class);

		annotation.addValue("classes", String.class);

		on(annotation).giveMeAnnotationValue("classes").isTypedBy(CtFieldAccess.class);

		annotation.addValue("classes", Integer.class);

		on(annotation).giveMeAnnotationValue("classes").isTypedBy(CtNewArray.class);

		annotation.addValue("field", AnnotationValuesTest.class.getDeclaredField("i"));

		on(annotation).giveMeAnnotationValue("field").isTypedBy(CtFieldAccess.class);
	}

	static class Request {
		private static Request myself = new Request();
		private static CtElement element;

		public static Request on(CtElement ctElement) {
			assertNotNull(ctElement);
			element = ctElement;
			return myself;
		}

		public <A extends Annotation> CtAnnotation<? extends Annotation> giveMeAnnotation(Class<A> annotation) {
			for (CtAnnotation<? extends Annotation> ctAnnotation : element.getAnnotations()) {
				if (ctAnnotation.getActualAnnotation().annotationType().equals(annotation)) {
					return ctAnnotation;
				}
			}
			return null;
		}

		public Request giveMeAnnotationValue(String key) {
			assertTrue("Element given in the method on should be an CtAnnotation.", element instanceof CtAnnotation);
			CtAnnotation<?> ctAnnotation = (CtAnnotation<?>) element;
			CtExpression value = null;
			try {
				value = ctAnnotation.getValue(key);
			} catch (ClassCastException e) {
				fail("Value of the given key can't be cast to an expression.");
			}
			assertNotNull(value);
			element = value;
			return myself;
		}

		public <T extends CtElement> Request isTypedBy(Class<T> expectedType) {
			try {
				expectedType.cast(element);
			} catch (ClassCastException e) {
				fail("The given element can't be cast by the given type.");
			}
			return myself;
		}
	}
}

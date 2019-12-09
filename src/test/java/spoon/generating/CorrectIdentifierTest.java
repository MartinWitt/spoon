package spoon.generating;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.reference.CtLocalVariableReference;
/**
 * for correct identifier see JLS chapter 3.8 and for keywords 3.9
 */
public class CorrectIdentifierTest {

	@Test
	public void wrongIdentifer() {
	CtLocalVariableReference<Object> localVariableRef = new Launcher().getFactory().createLocalVariableReference();
	assertThrows(IllegalArgumentException.class, () -> localVariableRef.setSimpleName("tacos.EatIt()"));
	}
	@Test
	public void wrongIdentifer2() {
	CtLocalVariableReference<Object> localVariableRef = new Launcher().getFactory().createLocalVariableReference();
	assertThrows(IllegalArgumentException.class, () -> localVariableRef.setSimpleName("1tacos"));
	}
	@Test
	public void keyWord() {
	CtLocalVariableReference<Object> localVariableRef = new Launcher().getFactory().createLocalVariableReference();
	assertThrows(IllegalArgumentException.class, () -> localVariableRef.setSimpleName("class"));
	}
	@Test
	public void keyWord2() {
	CtLocalVariableReference<Object> localVariableRef = new Launcher().getFactory().createLocalVariableReference();
	assertThrows(IllegalArgumentException.class, () -> localVariableRef.setSimpleName("null"));
	}
	@Test
	public void keyWord3() {
	CtLocalVariableReference<Object> localVariableRef = new Launcher().getFactory().createLocalVariableReference();
	assertThrows(IllegalArgumentException.class, () -> localVariableRef.setSimpleName("true"));
	}
	@Test
	public void correctIdentifer() {
	CtLocalVariableReference<Object> localVariableRef = new Launcher().getFactory().createLocalVariableReference();
	assertDoesNotThrow(() -> localVariableRef.setSimpleName("EatIt"));

	}
	@Test
	public void correctIdentifer2() {
	CtLocalVariableReference<Object> localVariableRef = new Launcher().getFactory().createLocalVariableReference();
	assertDoesNotThrow(() -> localVariableRef.setSimpleName("ClassFoo"));
	}
}

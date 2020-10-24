package fr.inria.gforge.spoon.architecture;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.Filter;

public class Precondition<T extends CtElement> implements IPrecondition<T> {

	private Filter<T> typeFilter;
	private Predicate<? super T> condition;
	private Precondition(Filter<T> typeFilter, Predicate<? super T> condition) {
		this.typeFilter = typeFilter;
		this.condition = condition;
	}

	public static <T extends CtElement> Precondition<T> of(Filter<T> elementFilter, Predicate<? super T>...conditions) {
		Predicate<T> startValue = (value) -> true;
		for (Predicate<? super T> condition : conditions) {
			startValue = startValue.and(condition);
		}
		return new Precondition<T>(elementFilter, startValue);
	}
	
	public static <T extends CtElement> Precondition<T> of(Filter<T> elementFilter, Iterable<Predicate<? super T>> conditions) {
		Predicate<T> startValue = value -> true;
		for (Predicate<? super T> condition : conditions) {
			startValue = startValue.and(condition);
		}
		return new Precondition<T>(elementFilter, startValue);
	}

	public static <T extends CtElement> Precondition<T> of(Filter<T> elementFilter, Predicate<? super T> conditions) {
		return new Precondition<T>(elementFilter, conditions);
	}

	public static <T extends CtElement> Precondition<T> of(Filter<T> elementFilter) {
		Predicate<T> startValue = value -> true;
		return new Precondition<T>(elementFilter, startValue);
	}
	@Override
	public Collection<T> apply(CtModel t) {
		return t.getElements(typeFilter).stream().filter(condition).collect(Collectors.toSet());
	}
}
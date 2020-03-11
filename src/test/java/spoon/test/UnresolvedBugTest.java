/**
 * Copyright (C) 2006-2019 INRIA and contributors
 *
 * Spoon is available either under the terms of the MIT License (see LICENSE-MIT.txt) of the Cecill-C License (see LICENSE-CECILL-C.txt). You as the user are entitled to choose the terms under which to adopt Spoon.
 */
package spoon.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import spoon.FluentLauncher;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

public class UnresolvedBugTest {

	private List<CtMethod<?>> testMethods = findTestMethods();


	private List<CtMethod<?>> findTestMethods() {
		return new FluentLauncher().inputResource("src/test/java/spoon")
				.noClasspath(true)
				.disableConsistencyChecks()
				.buildModel()
				.getElements(new TypeFilter<>(CtMethod.class));
	}

	/**
	 * checks that no test method has the category annotation and no github issue
	 * tag. So every unresolved bug needs a github issue.
	 */
	@Test
	public void checkUnresolvedBugAnnotations() throws IOException {
		// contract: every test ignored with @Category(UnresolvedBug.class) has an open
		// issue.
		testMethods = testMethods.stream()
				.filter(v -> v.hasAnnotation(Test.class) && !v.hasAnnotation(GitHubIssue.class) && v.hasAnnotation(Category.class)
								&& v.getAnnotation(Category.class).value()[0].equals(UnresolvedBug.class))
				.collect(Collectors.toList());
		assertEquals(testMethods.size(), 0);
	}
}
